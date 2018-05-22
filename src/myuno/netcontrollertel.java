package myuno;

public class netcontrollertel extends controllertel {
	
	chater [] cts;
	
	// 构造函数，ft 为单机玩家的通信工具
	public netcontrollertel(chater [] ct)
	{
		cts = ct;
	}
	
	public void mystop(int pl)
	{
		try {
			if (cts[pl].s != null)
				cts[pl].s.close();
			cts[pl].canRun = false;
		} catch (Exception ex) {
			
        }
	}
	
	// 发送提示信息（给谁，发什么字符串）
	public boolean sendinfo(int towho, String info)
	{
		return cts[towho].sender("i " + info);
	}
	
	// 发送开局信息（给谁，编号是几）
	public boolean sendstart(int towho, int pl)
	{
		return cts[towho].sender("s " + pl);
	}
	
	// 发送摸牌信息（给谁，谁，摸到了什么）
	public boolean sendcard(int towho, int pl, int num)
	{
		if (towho == pl)
			return cts[towho].sender("c " + pl + " " + num);
		else
			return cts[towho].sender("c " + pl + " " + (-1));			// 不是摸牌者，无法获知摸到了什么
	}
	
	// 发送当前玩家、牌、颜色信息
	public boolean sendnow(int towho, int pl, int num, int cl, int direc)
	{
		return cts[towho].sender("n " + pl + " " + num + " " + cl + " " + direc);
	}
	
	// 发送出牌信息（给谁，谁，出了第几张牌）
	public boolean sendresult(int towho, int pl, int num)
	{
		if (towho == pl)
			return cts[towho].sender("r " + pl + " " + num);
		else
			return cts[towho].sender("r " + pl + " " + (-1));		// 不是出牌者，无法获知出的是第几张牌
	}
	
	// 发送出牌请求（给谁，是否需要出牌）
	public boolean sendneed(int towho, boolean need)
	{
		if (need)
			return cts[towho].sender("1 true");
		else
			return cts[towho].sender("1 false");
	}
	
	// 发送选颜色请求（给谁，是否需要选颜色）
	public boolean sendneed2(int towho, boolean need)
	{
		if (need)
			return cts[towho].sender("2 true");
		else
			return cts[towho].sender("2 false");
	}
	
	// 看看选的怎么样了
	public int rcvans(int fromwho)
	{
		return cts[fromwho].getans();
	}
	
	// 发送玩家掉线信息（给谁，谁掉线了）
	public boolean sendexit(int towho, int pl)
	{
		return cts[towho].sender("e " + pl);
	}
	
	// 发送胜者信息（给谁，谁赢了，最后一张牌是什么）
	public boolean sendwinner(int towho, int pl, int precard)
	{
		return cts[towho].sender("w " + pl + " " + precard);
	}
}

