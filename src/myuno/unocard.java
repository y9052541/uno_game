package myuno;

// UNO牌类，用于牌面信息转换
public class unocard {
	public int cardname;		// 牌的编号
	public int tp;				// 牌的类型：0-12依次为0-9, skip, reverse, +2; 变色 = 0, +4 = 1
	public int color;			// 牌的颜色：0-4依次为：红、黄、蓝、绿、变色
	
	// 构造函数（编号）
	public unocard(int nam)
	{
		int k;
		cardname = nam;
		
		if (nam >= 100)
		{
			color = 4;
			tp = (nam - 100) / 4;
		}
		else
		{
			color = nam / 25;
			k = nam % 25;
			if (k == 0)
				tp = 0;
			else
				tp = (k + 1) / 2;
		}
	}
	
	// 设置编号
	public void setname(int nam)
	{
		int k;
		cardname = nam;
		if (nam >= 100)
		{
			color = 4;
			tp = (nam - 100) / 4;
		}
		else
		{
			color = nam / 25;
			k = nam % 25;
			if (k == 0)
				tp = 0;
			else
				tp = (k + 1) / 2;
		}
	}
	
	// 分数：变色 = +4 = 50, skip = reverse = +2 = 20, 其余 = 对应数字
	public int score()
	{
		if (color == 4)
			return 50;
		if (tp >= 10)
			return 20;
		return tp;
	}
	
	// 当前牌编号为 nam1，颜色为 cl 时，编号为 nam2 的牌是否可用
	public static boolean valid(int nam1, int nam2, int cl)
	{
		unocard u1 = new unocard(nam1);
		unocard u2 = new unocard(nam2);
		// 同色可用
		if (u1.color == u2.color)
			return true;
		// 变色可用
		if (u2.color == 4)
			return true;
		// 当前变色，颜色正确可用
		if (u1.color == 4 && u2.color == cl)
			return true;
		// 当前非变色，类型相同可用
		if (u1.color != 4 && u1.tp == u2.tp)
			return true;
		return false;
	}
	
	// 分数转换
	public static int score(int nam)
	{
		unocard u = new unocard(nam);
		return u.score();
	}
	
	// 编号转图片文件名，不含路径和扩展名
	// 完整路径 "unoimg\\"+unocard.filename(nam)+".png"
	public static String filename(int nam)
	{
		String cl;
		unocard u = new unocard(nam);
		switch(u.color)
		{
		case 0:
			cl = new String("r");
			break;
		case 1:
			cl = new String("y");
			break;
		case 2:
			cl = new String("b");
			break;
		case 3:
			cl = new String("g");
			break;
		default:
			if (u.tp == 0)
				return "wild";
			else
				return "wild4";
		}
		switch (u.tp)
		{
		case 10:
			return cl + "skip";
		case 11:
			return cl + "reverse";
		case 12:
			return cl + "draw2";
		default:
			return cl + String.valueOf(u.tp);
		}
	}
}
