package myuno;

import java.util.*;

// 控制部分（服务器）框架
public class unocontroller {
	
	public controllertel tel;					// 通信工具
	
	public static final int waittime = 200;		// 等待选择时间，超时则判定掉线
	
	public static final int tot = 108;			// total number of cards
	private int [][] card;						// what cards do they have
	private int [] cardnum;						// how many do they have
	private boolean [] used;					// whether used
	private int usednum;						// how many used
	private int direc;							// 指示方向，1顺时针，3逆时针
	private int nowcolor;						// 当前颜色
	
	private Random rd;							// 随机数生成器
	private int precard;						// 当前的牌
	private int nowwho;							// 当前玩家编号
	private boolean [] valid;					// 玩家是否还在线
	private int validnum;						// 在线人数
	
	
	
	public unocontroller (netcontrollertel ntc)
	{
		rd = new Random();
		card = new int[4][tot];
		cardnum = new int[] {0, 0, 0, 0};
		used = new boolean[tot];
		for (int i = 0; i < tot; i++)
			used[i] = false;
		usednum = 0;
		direc = 1;						// 默认顺时针
		nowcolor = 4;
		nowwho = -1;
		
		valid = new boolean[4];
		for (int i = 0; i < 4; i++)
			valid[i] = true;
		validnum = 4;					// 默认4人
		
		tel = ntc;
	}
	
	public static void play2(netcontrollertel ntc)
	{
		unocontroller uno = new unocontroller(ntc);
		
		// 开局
		for (int i = 0; i < 4; i++)
			if (uno.valid[i])
				if (!uno.tel.sendstart(i, i))
					uno.dealwithexit(i);
		if (uno.validnum >= 2)
			uno.setcard();				// 发牌
		if (uno.validnum >= 2)
		{
			uno.setleader();			// 先手
			uno.setfirstcard();			// 初始牌
			while (uno.turn() == 0);	// 轮流出牌
		}
		uno.setwinner();			// 找胜者
		for (int i = 0; i < 4; i++)
		{
			try {
				if (ntc.cts[i].s != null)
					ntc.cts[i].s.close();
				ntc.cts[i].canRun = false;
			} catch (Exception ex) {
				
	        }
		}
	}
	
	
	
	// 暂停函数
	public static void mysleep(int tim)
	{
		try {
			Thread.sleep(tim);
		} catch (InterruptedException e) {
			
		}
	}
	
	// 构造函数，ft 为玩家的通信工具
	public unocontroller(playertel ft)
	{
		// 变量初始化
		rd = new Random();
		card = new int[4][tot];
		cardnum = new int[] {0, 0, 0, 0};
		used = new boolean[tot];
		for (int i = 0; i < tot; i++)
			used[i] = false;
		usednum = 0;
		direc = 1;						// 默认顺时针
		nowcolor = 4;
		nowwho = -1;
		
		valid = new boolean[4];
		for (int i = 0; i < 4; i++)
			valid[i] = true;
		validnum = 4;					// 默认4人
		
		tel = new controllertel(ft);	// 初始化通信工具
	}
	
	// 摸牌，返回摸到的牌
	private int getnextcard()
	{
		// 牌堆为空，把除了玩家手里的牌和当前牌之外的全部送回牌堆
		if (usednum == tot)
		{
			int i, j;
			for (i = 0; i < tot; i++)
				used[i] = false;
			usednum = 0;
			for (i = 0; i < 4; i++)
				if (valid[i])
				{
					for (j = 0; j < cardnum[i]; j++)
					{
						used[card[i][j]] = true;
						usednum++;
					}
				}
			used[precard] = true;
			usednum++;
		}
		int k;
		// 从牌堆摸一张
		do {
			k = rd.nextInt(tot);
		} while (used[k]);
		used[k] = true;
		usednum++;
		return k;
	}
	
	// 把 x 还回牌堆
	private void returncard(int x)
	{
		used[x] = false;
		usednum--;
	}
	
	// 一开始的摸牌
	private void setcard()
	{
		// 每人7张
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 4; j++)
				if (valid[j])
				{
					card[j][i] = getnextcard();
					cardnum[j]++;
					// 把摸牌结果告诉所有玩家
					for (int k = 0; k < 4; k++)
						if (valid[k])
							if (!tel.sendcard(k, j, card[j][i]))
								dealwithexit(k);
					mysleep(100);
				}
	}
	
	// 设置初始的牌
	private void setfirstcard()
	{
		int fstcard;
		unocard fst;
		while (true)
		{
			fstcard = getnextcard();
			fst = new unocard(fstcard);
			// 规定它是 0-9
			if (fst.color == 4 || fst.tp >= 10)
				returncard(fstcard);
			else
				break;
		}
		
		// 设置相关变量
		precard = fstcard;
		nowcolor = fst.color;
	}
	
	// 设置先手
	private void setleader()
	{
		int maxcard = unocard.score(card[0][0]);
		int nxtcard;
		nowwho = 0;
		// 选首张牌分数最大的玩家中编号最小的
		for (int i = 1; i < 4; i++)
			if (valid[i])
			{
				nxtcard = unocard.score(card[i][0]);
				if (nxtcard > maxcard)
				{
					maxcard = nxtcard;
					nowwho = i;
				}
			}
	}
	
	// 一回合
	// 返回值：0 继续下一回合，1 只剩 1 人在线，2 无人在线，-1 胜者已决出
	public int turn()
	{
		int nam = 0;
		int turnresult = 0;
		
		// 发送当前牌局信息
		// 需要检查玩家是否在线
		for (int i = 0; i < 4; i++)
			if (valid[i])
				if (!tel.sendnow(i, nowwho, precard, nowcolor))
					turnresult = dealwithexit(i);
		// 如果掉线人数过多或当前玩家掉线，直接返回
		if (turnresult > 0 || !valid[nowwho])
			return turnresult;
		
		// 向当前玩家发送出牌请求
		if (!tel.sendneed(nowwho, true))
			return dealwithexit(nowwho);
		
		int ans = -2;
		int ti;
		// 等待当前玩家选牌
		for (ti = 0; ti < waittime; ti++)
		{
			// 尝试接收回复
			ans = tel.rcvans(nowwho);
			// 收到某张牌的编号，判断是否可用
			if (ans >= 0 && ans < cardnum[nowwho])
			{
				nam = card[nowwho][ans];
				// +4 只有无其它牌可用才可用
				if (nam >= 104)
				{
					boolean ok = true;
					for (int j = 0; j < cardnum[nowwho]; j++)
						if (card[nowwho][j] < 104 && unocard.valid(precard, card[nowwho][j], nowcolor))
						{
							ok = false;
							break;
						}
					if (ok)
						break;
				}
				else if (unocard.valid(precard, nam, nowcolor))
					break;
				// 告诉玩家此牌不可用，若当前玩家掉线则直接返回
				if (!tel.sendinfo(nowwho, "此牌不可用。"))
					return dealwithexit(nowwho);
			}
			// 收到摸牌请求
			else if (ans == -1)
			{
				boolean ok = true;
				// 如果有牌可用，就不能摸牌
				for (int j = 0; j < cardnum[nowwho]; j++)
					if (unocard.valid(precard, card[nowwho][j], nowcolor))
					{
						ok = false;
						break;
					}
				if (ok)
					break;
				// 告诉玩家有牌可用，若当前玩家掉线则直接返回
				if (!tel.sendinfo(nowwho, "有牌可用，不能抓牌。"))
					return dealwithexit(nowwho);
			}
			mysleep(100);	// 等 0.1 秒再次尝试接收
		}
		
		// 超时，判为掉线
		if (ti >= waittime)
			return dealwithexit(nowwho);
		
		// 结束出牌请求
		if (!tel.sendneed(nowwho, false))
			return dealwithexit(nowwho);
		
		// 摸牌
		if (ans < 0)
		{
			int i = cardnum[nowwho];
			int j = getnextcard();
			card[nowwho][i] = j;
			cardnum[nowwho]++;
			
			// 把摸牌结果告诉所有玩家
			for (int k = 0; k < 4; k++)
				if (valid[k])
					if (!tel.sendcard(k, nowwho, j))
						turnresult = dealwithexit(k);
			if (turnresult > 0 || !valid[nowwho])
				return turnresult;
			mysleep(300);
			// 判断是否摸到的牌是否可用，可用就出牌
			if (unocard.valid(precard, j, nowcolor))
			{
				ans = i;
				nam = j;
			}
		}
		
		// 无牌可用，进入下一回合
		if (ans < 0)
		{
			do {
				nowwho = (nowwho + direc) % 4;
			}while (!valid[nowwho]);
			return 0;
		}
		
		int nxtget = 0;				// 下一玩家的强制摸牌数
		boolean skip = false;		// 是否跳过下一玩家
		
		// 出牌效果设置
		// 变色或 +4
		if (nam >= 100)
		{
			int ans2 = -2;
			// 向当前玩家发送选颜色请求
			if (!tel.sendneed2(nowwho, true))
				return dealwithexit(nowwho);
			// 等待当前玩家选颜色
			for (ti = 0; ti < waittime; ti++)
			{
				ans2 = tel.rcvans(nowwho);
				if (ans2 >= 0 && ans2 < 4)
					break;
				mysleep(100);
			}
			// 超时判定
			if (ti >= waittime)
				return dealwithexit(nowwho);
			// 结束选颜色
			if (!tel.sendneed2(nowwho, false))
				return dealwithexit(nowwho);
			// 设置牌局信息
			nowcolor = ans2;
			precard = nam;
			// +4
			if (nam >= 104)
			{
				skip = true;
				nxtget = 4;
			}
		}
		else
		{
			unocard anscard = new unocard(nam);
			nowcolor = anscard.color;
			precard = nam;
			// skip
			if (anscard.tp == 10)
				skip = true;
			// reverse
			else if (anscard.tp == 11)
			{
				direc = 4 - direc;
				if (validnum == 2)
					skip = true;
			}
			// +2
			else if (anscard.tp == 12)
			{
				skip = true;
				nxtget = 2;
			}
		}
		
		// 去掉已出的牌
		for (int i = ans + 1; i < cardnum[nowwho]; i++)
			card[nowwho][i - 1] = card[nowwho][i];
		cardnum[nowwho]--;
		
		// 发送出牌信息
		for (int i = 0; i < 4; i++)
			if (valid[i])
				if (!tel.sendresult(i, nowwho, ans))
					turnresult = dealwithexit(i);
		if (turnresult > 0)
			return turnresult;
		
		// 判断当前玩家是否出完
		if (valid[nowwho] && cardnum[nowwho] == 0)
			return -1;
		
		// 找下一个玩家
		do {
			nowwho = (nowwho + direc) % 4;
		}while (!valid[nowwho]);
		
		// 强制摸牌
		while (nxtget > 0)
		{
			nxtget--;
			int i = cardnum[nowwho];
			int j = getnextcard();
			card[nowwho][i] = j;
			cardnum[nowwho]++;
			for (int k = 0; k < 4; k++)
				if (valid[k])
					if (!tel.sendcard(k, nowwho, j))
						turnresult = dealwithexit(k);
			if (turnresult > 0)
				return turnresult;
			mysleep(50);
		}
		
		// 跳过
		if (skip)
		{
			do {
				nowwho = (nowwho + direc) % 4;
			}while (!valid[nowwho]);
		}
		
		return 0;
	}
	
	// 找胜者或唯一在线者
	public void setwinner()
	{
		if (validnum > 0)			// 有人在线
		{
			int winner = -1;
			for (int i = 0; i < 4; i++)
				if (valid[i] && (validnum == 1 || cardnum[i] == 0))
				{
					winner = i;
					break;
				}
			// 若胜者存在，则告诉每一个玩家
			if (winner >= 0)
			{
				for (int i = 0; i < 4; i++)
					if (valid[i])
						tel.sendwinner(i, winner, precard);
			}
		}
	}
	
	public static void play(unoframe u)
	{
		unocontroller uno = new unocontroller(u.tel);
		
		// 开局
		for (int i = 0; i < 4; i++)
			if (uno.valid[i])
				if (!uno.tel.sendstart(i, i))
					uno.dealwithexit(i);
		if (uno.validnum >= 2)
			uno.setcard();				// 发牌
		if (uno.validnum >= 2)
		{
			uno.setleader();			// 先手
			uno.setfirstcard();			// 初始牌
			while (uno.valid[0] && uno.turn() == 0);	// 轮流出牌
		}
		uno.setwinner();			// 找胜者
	}
	
	// 处理玩家掉线情况
	// 返回值：0 至少 2 人在线，1 只剩 1 人在线，2 无人在线
	public int dealwithexit(int pl)
	{
		if (valid[pl])
		{
			valid[pl] = false;
			validnum--;
			cardnum[pl] = 0;
			
			tel.mystop(pl);
			
			if (validnum == 1)
				return 1;
			else if (validnum <= 0)
				return 2;
			
			// 当前玩家掉线，应找出下一玩家作为当前玩家
			if (pl == nowwho)
			{
				while (!valid[nowwho])
					nowwho = (nowwho + direc) % 4;
			}
			
			// 告诉其他玩家
			for (int i = 0; i < 4; i++)
				if (valid[i])
					if (!tel.sendexit(i, pl))
						return dealwithexit(i);
		}
		return 0;
	}
}
