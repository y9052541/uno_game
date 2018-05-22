package myuno;

// UNO���࣬����������Ϣת��
public class unocard {
	public int cardname;		// �Ƶı��
	public int tp;				// �Ƶ����ͣ�0-12����Ϊ0-9, skip, reverse, +2; ��ɫ = 0, +4 = 1
	public int color;			// �Ƶ���ɫ��0-4����Ϊ���졢�ơ������̡���ɫ
	
	// ���캯������ţ�
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
	
	// ���ñ��
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
	
	// ��������ɫ = +4 = 50, skip = reverse = +2 = 20, ���� = ��Ӧ����
	public int score()
	{
		if (color == 4)
			return 50;
		if (tp >= 10)
			return 20;
		return tp;
	}
	
	// ��ǰ�Ʊ��Ϊ nam1����ɫΪ cl ʱ�����Ϊ nam2 �����Ƿ����
	public static boolean valid(int nam1, int nam2, int cl)
	{
		unocard u1 = new unocard(nam1);
		unocard u2 = new unocard(nam2);
		// ͬɫ����
		if (u1.color == u2.color)
			return true;
		// ��ɫ����
		if (u2.color == 4)
			return true;
		// ��ǰ��ɫ����ɫ��ȷ����
		if (u1.color == 4 && u2.color == cl)
			return true;
		// ��ǰ�Ǳ�ɫ��������ͬ����
		if (u1.color != 4 && u1.tp == u2.tp)
			return true;
		return false;
	}
	
	// ����ת��
	public static int score(int nam)
	{
		unocard u = new unocard(nam);
		return u.score();
	}
	
	// ���תͼƬ�ļ���������·������չ��
	// ����·�� "unoimg\\"+unocard.filename(nam)+".png"
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
