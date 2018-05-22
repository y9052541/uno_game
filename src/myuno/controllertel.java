package myuno;

// 控制部分（服务器）通信工具框架
public class controllertel {
	
	public playertel [] ftel;		// 4 个玩家通信工具的引用
	
	public controllertel()
	{
		
	}
	
	public void mystop(int pl)
	{
		if (pl == 0)
			ftel[0].rcvexit(pl);
	}
	
	// 构造函数，ft 为单机玩家的通信工具
	public controllertel(playertel ft)
	{
		ftel = new playertel[4];
		ftel[0] = ft;
		
		// 另外 3 个玩家设置为 AI
		for (int i = 1; i < 4; i++)
			ftel[i] = new ai();
	}
	
	// 发送提示信息（给谁，发什么字符串）
	public boolean sendinfo(int towho, String info)
	{
		return ftel[towho].rcvinfo(info);
	}
	
	// 发送开局信息（给谁，编号是几）
	public boolean sendstart(int towho, int pl)
	{
		return ftel[towho].rcvstart(pl);
	}
	
	// 发送摸牌信息（给谁，谁，摸到了什么）
	public boolean sendcard(int towho, int pl, int num)
	{
		if (towho == pl)
			return ftel[towho].rcvcard(pl, num);
		else
			return ftel[towho].rcvcard(pl, -1);			// 不是摸牌者，无法获知摸到了什么

	}
	
	// 发送当前玩家、牌、颜色信息
	public boolean sendnow(int towho, int pl, int num, int cl)
	{
		return ftel[towho].rcvnow(pl, num, cl);
	}
	
	// 发送出牌信息（给谁，谁，出了第几张牌）
	public boolean sendresult(int towho, int pl, int num)
	{
		if (towho == pl)
			return ftel[towho].rcvresult(pl, num);
		else
			return ftel[towho].rcvresult(pl, -1);		// 不是出牌者，无法获知出的是第几张牌
	}
	
	// 发送出牌请求（给谁，是否需要出牌）
	public boolean sendneed(int towho, boolean need)
	{
		return ftel[towho].rcvneed(need);
	}
	
	// 发送选颜色请求（给谁，是否需要选颜色）
	public boolean sendneed2(int towho, boolean need)
	{
		return ftel[towho].rcvneed2(need);
	}
	
	// 看看选的怎么样了
	public int rcvans(int fromwho)
	{
		return ftel[fromwho].sendans();
	}
	
	// 发送玩家掉线信息（给谁，谁掉线了）
	public boolean sendexit(int towho, int pl)
	{
		return ftel[towho].rcvexit(pl);
	}
	
	// 发送胜者信息（给谁，谁赢了，最后一张牌是什么）
	public boolean sendwinner(int towho, int pl, int precard)
	{
		return ftel[towho].rcvwinner(pl, precard);
	}
}

