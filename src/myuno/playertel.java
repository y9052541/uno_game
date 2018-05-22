package myuno;

public class playertel {
	public boolean needed;		// 是否需要出牌
	public boolean needed2;		// 是否需要选颜色
	public boolean clicked;		// 是否已经做出选择，而控制部分尚未接收
	public int ans;				// 选择的结果
	
	// 构造函数
	public playertel()
	{
		needed = false;
		needed2 = false;
		clicked = false;
		ans = -2;
	}
	
	// 接收提示信息
	public boolean rcvinfo(String info)
	{
		return true;
	}
	
	// 接收开局信息，初始化（我的编号）
	public boolean rcvstart(int pl)
	{
		needed = false;
		needed2 = false;
		return true;
	}
	
	// 接收摸牌信息（谁，摸到了什么）
	public boolean rcvcard(int pl, int num)
	{
		return true;
	}
	
	// 接收当前玩家、牌、颜色信息
	public boolean rcvnow(int pl, int num, int cl, int dierc)
	{
		return true;
	}
	
	// 接收出牌信息（谁，出了第几张牌）
	public boolean rcvresult(int pl, int num)
	{
		return true;
	}
	
	// 接收出牌请求
	public boolean rcvneed(boolean need)
	{
		needed = need;
		// 清除现有选择
		clicked = false;
		ans = -2;
		return true;
	}
	
	// 接收选颜色请求
	public boolean rcvneed2(boolean need)
	{
		needed2 = need;
		// 清除现有选择
		clicked = false;
		ans = -2;
		return true;
	}
	
	// 发送现在的选择，返回选择结果（-2 表示未选择）
	public int sendans()
	{
		if ((needed || needed2) && clicked)		// 只有需要选择且已经选择，才发送选择结果
		{
			int an = ans;
			clicked = false;			// 清除当前选择
			return an;
		}
		return -2;
	}
	
	// 设置选牌结果为 an（便于Override）
	public boolean setans(int an)
	{
		// 只有需要选择且未选择，才能设置选择结果
		if (needed && !clicked)
		{
			clicked = true;		// 设置已选择
			ans = an;
			return true;
		}
		else
			return false;
	}
	
	// 设置选颜色结果为 an（便于Override）
	public boolean setans2(int an)
	{
		if (needed2 && !clicked)
		{
			clicked = true;
			ans = an;
			return true;
		}
		else
			return false;
	}
	
	// 接收玩家掉线信息（谁掉线了）
	public boolean rcvexit(int pl)
	{
		return true;
	}
	
	// 接收胜者信息（谁赢了，最后一张牌是什么）
	public boolean rcvwinner(int pl, int precard)
	{
		return true;
	}
}
