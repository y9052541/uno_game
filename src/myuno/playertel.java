package myuno;

public class playertel {
	public boolean needed;		// �Ƿ���Ҫ����
	public boolean needed2;		// �Ƿ���Ҫѡ��ɫ
	public boolean clicked;		// �Ƿ��Ѿ�����ѡ�񣬶����Ʋ�����δ����
	public int ans;				// ѡ��Ľ��
	
	// ���캯��
	public playertel()
	{
		needed = false;
		needed2 = false;
		clicked = false;
		ans = -2;
	}
	
	// ������ʾ��Ϣ
	public boolean rcvinfo(String info)
	{
		return true;
	}
	
	// ���տ�����Ϣ����ʼ�����ҵı�ţ�
	public boolean rcvstart(int pl)
	{
		needed = false;
		needed2 = false;
		return true;
	}
	
	// ����������Ϣ��˭��������ʲô��
	public boolean rcvcard(int pl, int num)
	{
		return true;
	}
	
	// ���յ�ǰ��ҡ��ơ���ɫ��Ϣ
	public boolean rcvnow(int pl, int num, int cl, int dierc)
	{
		return true;
	}
	
	// ���ճ�����Ϣ��˭�����˵ڼ����ƣ�
	public boolean rcvresult(int pl, int num)
	{
		return true;
	}
	
	// ���ճ�������
	public boolean rcvneed(boolean need)
	{
		needed = need;
		// �������ѡ��
		clicked = false;
		ans = -2;
		return true;
	}
	
	// ����ѡ��ɫ����
	public boolean rcvneed2(boolean need)
	{
		needed2 = need;
		// �������ѡ��
		clicked = false;
		ans = -2;
		return true;
	}
	
	// �������ڵ�ѡ�񣬷���ѡ������-2 ��ʾδѡ��
	public int sendans()
	{
		if ((needed || needed2) && clicked)		// ֻ����Ҫѡ�����Ѿ�ѡ�񣬲ŷ���ѡ����
		{
			int an = ans;
			clicked = false;			// �����ǰѡ��
			return an;
		}
		return -2;
	}
	
	// ����ѡ�ƽ��Ϊ an������Override��
	public boolean setans(int an)
	{
		// ֻ����Ҫѡ����δѡ�񣬲�������ѡ����
		if (needed && !clicked)
		{
			clicked = true;		// ������ѡ��
			ans = an;
			return true;
		}
		else
			return false;
	}
	
	// ����ѡ��ɫ���Ϊ an������Override��
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
	
	// ������ҵ�����Ϣ��˭�����ˣ�
	public boolean rcvexit(int pl)
	{
		return true;
	}
	
	// ����ʤ����Ϣ��˭Ӯ�ˣ����һ������ʲô��
	public boolean rcvwinner(int pl, int precard)
	{
		return true;
	}
}
