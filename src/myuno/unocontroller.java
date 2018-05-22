package myuno;

import java.util.*;

// ���Ʋ��֣������������
public class unocontroller {
	
	public controllertel tel;					// ͨ�Ź���
	
	public static final int waittime = 200;		// �ȴ�ѡ��ʱ�䣬��ʱ���ж�����
	
	public static final int tot = 108;			// total number of cards
	private int [][] card;						// what cards do they have
	private int [] cardnum;						// how many do they have
	private boolean [] used;					// whether used
	private int usednum;						// how many used
	private int direc;							// ָʾ����1˳ʱ�룬3��ʱ��
	private int nowcolor;						// ��ǰ��ɫ
	
	private Random rd;							// �����������
	private int precard;						// ��ǰ����
	private int nowwho;							// ��ǰ��ұ��
	private boolean [] valid;					// ����Ƿ�����
	private int validnum;						// ��������
	
	
	
	public unocontroller (netcontrollertel ntc)
	{
		rd = new Random();
		card = new int[4][tot];
		cardnum = new int[] {0, 0, 0, 0};
		used = new boolean[tot];
		for (int i = 0; i < tot; i++)
			used[i] = false;
		usednum = 0;
		direc = 1;						// Ĭ��˳ʱ��
		nowcolor = 4;
		nowwho = -1;
		
		valid = new boolean[4];
		for (int i = 0; i < 4; i++)
			valid[i] = true;
		validnum = 4;					// Ĭ��4��
		
		tel = ntc;
	}
	
	public static void play2(netcontrollertel ntc)
	{
		unocontroller uno = new unocontroller(ntc);
		
		// ����
		for (int i = 0; i < 4; i++)
			if (uno.valid[i])
				if (!uno.tel.sendstart(i, i))
					uno.dealwithexit(i);
		if (uno.validnum >= 2)
			uno.setcard();				// ����
		if (uno.validnum >= 2)
		{
			uno.setleader();			// ����
			uno.setfirstcard();			// ��ʼ��
			while (uno.turn() == 0);	// ��������
		}
		uno.setwinner();			// ��ʤ��
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
	
	
	
	// ��ͣ����
	public static void mysleep(int tim)
	{
		try {
			Thread.sleep(tim);
		} catch (InterruptedException e) {
			
		}
	}
	
	// ���캯����ft Ϊ��ҵ�ͨ�Ź���
	public unocontroller(playertel ft)
	{
		// ������ʼ��
		rd = new Random();
		card = new int[4][tot];
		cardnum = new int[] {0, 0, 0, 0};
		used = new boolean[tot];
		for (int i = 0; i < tot; i++)
			used[i] = false;
		usednum = 0;
		direc = 1;						// Ĭ��˳ʱ��
		nowcolor = 4;
		nowwho = -1;
		
		valid = new boolean[4];
		for (int i = 0; i < 4; i++)
			valid[i] = true;
		validnum = 4;					// Ĭ��4��
		
		tel = new controllertel(ft);	// ��ʼ��ͨ�Ź���
	}
	
	// ���ƣ�������������
	private int getnextcard()
	{
		// �ƶ�Ϊ�գ��ѳ������������ƺ͵�ǰ��֮���ȫ���ͻ��ƶ�
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
		// ���ƶ���һ��
		do {
			k = rd.nextInt(tot);
		} while (used[k]);
		used[k] = true;
		usednum++;
		return k;
	}
	
	// �� x �����ƶ�
	private void returncard(int x)
	{
		used[x] = false;
		usednum--;
	}
	
	// һ��ʼ������
	private void setcard()
	{
		// ÿ��7��
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 4; j++)
				if (valid[j])
				{
					card[j][i] = getnextcard();
					cardnum[j]++;
					// �����ƽ�������������
					for (int k = 0; k < 4; k++)
						if (valid[k])
							if (!tel.sendcard(k, j, card[j][i]))
								dealwithexit(k);
					mysleep(100);
				}
	}
	
	// ���ó�ʼ����
	private void setfirstcard()
	{
		int fstcard;
		unocard fst;
		while (true)
		{
			fstcard = getnextcard();
			fst = new unocard(fstcard);
			// �涨���� 0-9
			if (fst.color == 4 || fst.tp >= 10)
				returncard(fstcard);
			else
				break;
		}
		
		// ������ر���
		precard = fstcard;
		nowcolor = fst.color;
	}
	
	// ��������
	private void setleader()
	{
		int maxcard = unocard.score(card[0][0]);
		int nxtcard;
		nowwho = 0;
		// ѡ�����Ʒ�����������б����С��
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
	
	// һ�غ�
	// ����ֵ��0 ������һ�غϣ�1 ֻʣ 1 �����ߣ�2 �������ߣ�-1 ʤ���Ѿ���
	public int turn()
	{
		int nam = 0;
		int turnresult = 0;
		
		// ���͵�ǰ�ƾ���Ϣ
		// ��Ҫ�������Ƿ�����
		for (int i = 0; i < 4; i++)
			if (valid[i])
				if (!tel.sendnow(i, nowwho, precard, nowcolor))
					turnresult = dealwithexit(i);
		// ����������������ǰ��ҵ��ߣ�ֱ�ӷ���
		if (turnresult > 0 || !valid[nowwho])
			return turnresult;
		
		// ��ǰ��ҷ��ͳ�������
		if (!tel.sendneed(nowwho, true))
			return dealwithexit(nowwho);
		
		int ans = -2;
		int ti;
		// �ȴ���ǰ���ѡ��
		for (ti = 0; ti < waittime; ti++)
		{
			// ���Խ��ջظ�
			ans = tel.rcvans(nowwho);
			// �յ�ĳ���Ƶı�ţ��ж��Ƿ����
			if (ans >= 0 && ans < cardnum[nowwho])
			{
				nam = card[nowwho][ans];
				// +4 ֻ���������ƿ��òſ���
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
				// ������Ҵ��Ʋ����ã�����ǰ��ҵ�����ֱ�ӷ���
				if (!tel.sendinfo(nowwho, "���Ʋ����á�"))
					return dealwithexit(nowwho);
			}
			// �յ���������
			else if (ans == -1)
			{
				boolean ok = true;
				// ������ƿ��ã��Ͳ�������
				for (int j = 0; j < cardnum[nowwho]; j++)
					if (unocard.valid(precard, card[nowwho][j], nowcolor))
					{
						ok = false;
						break;
					}
				if (ok)
					break;
				// ����������ƿ��ã�����ǰ��ҵ�����ֱ�ӷ���
				if (!tel.sendinfo(nowwho, "���ƿ��ã�����ץ�ơ�"))
					return dealwithexit(nowwho);
			}
			mysleep(100);	// �� 0.1 ���ٴγ��Խ���
		}
		
		// ��ʱ����Ϊ����
		if (ti >= waittime)
			return dealwithexit(nowwho);
		
		// ������������
		if (!tel.sendneed(nowwho, false))
			return dealwithexit(nowwho);
		
		// ����
		if (ans < 0)
		{
			int i = cardnum[nowwho];
			int j = getnextcard();
			card[nowwho][i] = j;
			cardnum[nowwho]++;
			
			// �����ƽ�������������
			for (int k = 0; k < 4; k++)
				if (valid[k])
					if (!tel.sendcard(k, nowwho, j))
						turnresult = dealwithexit(k);
			if (turnresult > 0 || !valid[nowwho])
				return turnresult;
			mysleep(300);
			// �ж��Ƿ����������Ƿ���ã����þͳ���
			if (unocard.valid(precard, j, nowcolor))
			{
				ans = i;
				nam = j;
			}
		}
		
		// ���ƿ��ã�������һ�غ�
		if (ans < 0)
		{
			do {
				nowwho = (nowwho + direc) % 4;
			}while (!valid[nowwho]);
			return 0;
		}
		
		int nxtget = 0;				// ��һ��ҵ�ǿ��������
		boolean skip = false;		// �Ƿ�������һ���
		
		// ����Ч������
		// ��ɫ�� +4
		if (nam >= 100)
		{
			int ans2 = -2;
			// ��ǰ��ҷ���ѡ��ɫ����
			if (!tel.sendneed2(nowwho, true))
				return dealwithexit(nowwho);
			// �ȴ���ǰ���ѡ��ɫ
			for (ti = 0; ti < waittime; ti++)
			{
				ans2 = tel.rcvans(nowwho);
				if (ans2 >= 0 && ans2 < 4)
					break;
				mysleep(100);
			}
			// ��ʱ�ж�
			if (ti >= waittime)
				return dealwithexit(nowwho);
			// ����ѡ��ɫ
			if (!tel.sendneed2(nowwho, false))
				return dealwithexit(nowwho);
			// �����ƾ���Ϣ
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
		
		// ȥ���ѳ�����
		for (int i = ans + 1; i < cardnum[nowwho]; i++)
			card[nowwho][i - 1] = card[nowwho][i];
		cardnum[nowwho]--;
		
		// ���ͳ�����Ϣ
		for (int i = 0; i < 4; i++)
			if (valid[i])
				if (!tel.sendresult(i, nowwho, ans))
					turnresult = dealwithexit(i);
		if (turnresult > 0)
			return turnresult;
		
		// �жϵ�ǰ����Ƿ����
		if (valid[nowwho] && cardnum[nowwho] == 0)
			return -1;
		
		// ����һ�����
		do {
			nowwho = (nowwho + direc) % 4;
		}while (!valid[nowwho]);
		
		// ǿ������
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
		
		// ����
		if (skip)
		{
			do {
				nowwho = (nowwho + direc) % 4;
			}while (!valid[nowwho]);
		}
		
		return 0;
	}
	
	// ��ʤ�߻�Ψһ������
	public void setwinner()
	{
		if (validnum > 0)			// ��������
		{
			int winner = -1;
			for (int i = 0; i < 4; i++)
				if (valid[i] && (validnum == 1 || cardnum[i] == 0))
				{
					winner = i;
					break;
				}
			// ��ʤ�ߴ��ڣ������ÿһ�����
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
		
		// ����
		for (int i = 0; i < 4; i++)
			if (uno.valid[i])
				if (!uno.tel.sendstart(i, i))
					uno.dealwithexit(i);
		if (uno.validnum >= 2)
			uno.setcard();				// ����
		if (uno.validnum >= 2)
		{
			uno.setleader();			// ����
			uno.setfirstcard();			// ��ʼ��
			while (uno.valid[0] && uno.turn() == 0);	// ��������
		}
		uno.setwinner();			// ��ʤ��
	}
	
	// ������ҵ������
	// ����ֵ��0 ���� 2 �����ߣ�1 ֻʣ 1 �����ߣ�2 ��������
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
			
			// ��ǰ��ҵ��ߣ�Ӧ�ҳ���һ�����Ϊ��ǰ���
			if (pl == nowwho)
			{
				while (!valid[nowwho])
					nowwho = (nowwho + direc) % 4;
			}
			
			// �����������
			for (int i = 0; i < 4; i++)
				if (valid[i])
					if (!tel.sendexit(i, pl))
						return dealwithexit(i);
		}
		return 0;
	}
}
