package myuno;

import java.util.Random;

// AI 框架，继承玩家通信工具
/*
	策略：只记录自己的编号和自己牌的数量
	选择出牌时，先尝试摸牌，再按从旧到新的顺序尝试出牌
	选择颜色时，随机选择
*/
public class ai extends playertel {
	
	private int mypl;		// 自己的编号
	private int mynum;		// 自己有几张牌
	private Random rd;		// 随机数生成器
	
	// 构造函数
	public ai()
	{
		mynum = 0;
		rd = new Random();
	}
	
	// 暂停函数
	public static void mysleep(int tim)
	{
		try {
			Thread.sleep(tim);
		} catch (InterruptedException e) {

		}
	}
	
	
	// 选择出牌的线程
	class guessans implements Runnable{
		public void run()
		{
			mysleep(1000);					// 给人类玩家的反应时间
			for (int i = -1; i < mynum; i++)	// 先尝试摸牌，再按从旧到新的顺序尝试出牌
			{
				while (needed && clicked)	// 如果不合法，服务器将拒绝接收。此时clicked变为false，可尝试下一个
					mysleep(60);			// 每隔0.06秒检查clicked即可
				if (needed)					// 如果合法，needed将变为false
					setans(i);
				else
					break;
			}
		}
	}
	
	// 选择颜色的线程
	class guessans2 implements Runnable{
		public void run()
		{
			mysleep(1000);
			setans2(rd.nextInt(4));		// 随机选一个
		}
	}
	
	// 以下函数均为重写(Override)。返回值：true 接收成功，false 接收失败
	// 接收开局信息，初始化编号
	public boolean rcvstart(int pl)
	{
		super.rcvstart(pl);
		mypl = pl;
		mynum = 0;
		return true;
	}
	
	// 接收摸牌信息（谁，摸到了什么）
	public boolean rcvcard(int pl, int num)
	{
		if (pl == mypl)
			mynum++;
		return true;
	}
	
	// 接收出牌信息（谁，出了第几张牌）
	public boolean rcvresult(int pl, int num)
	{
		if (pl == mypl)
			mynum--;
		return true;
	}
	
	// 接收出牌请求
	public boolean rcvneed(boolean need)
	{
		super.rcvneed(need);					// 处理锁
		new Thread(new guessans()).start();		// 创建线程选牌出
		return true;
	}
	
	// 接收选颜色请求
	public boolean rcvneed2(boolean need)
	{
		super.rcvneed2(need);					// 处理锁
		new Thread(new guessans2()).start();	// 创建线程选择颜色
		return true;
	}
}
