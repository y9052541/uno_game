package myuno;

// ���Ʋ��֣���������ͨ�Ź��߿��
public class controllertel {
	
	public playertel [] ftel;		// 4 �����ͨ�Ź��ߵ�����
	
	public controllertel()
	{
		
	}
	
	public void mystop(int pl)
	{
		if (pl == 0)
			ftel[0].rcvexit(pl);
	}
	
	// ���캯����ft Ϊ������ҵ�ͨ�Ź���
	public controllertel(playertel ft)
	{
		ftel = new playertel[4];
		ftel[0] = ft;
		
		// ���� 3 ���������Ϊ AI
		for (int i = 1; i < 4; i++)
			ftel[i] = new ai();
	}
	
	// ������ʾ��Ϣ����˭����ʲô�ַ�����
	public boolean sendinfo(int towho, String info)
	{
		return ftel[towho].rcvinfo(info);
	}
	
	// ���Ϳ�����Ϣ����˭������Ǽ���
	public boolean sendstart(int towho, int pl)
	{
		return ftel[towho].rcvstart(pl);
	}
	
	// ����������Ϣ����˭��˭��������ʲô��
	public boolean sendcard(int towho, int pl, int num)
	{
		if (towho == pl)
			return ftel[towho].rcvcard(pl, num);
		else
			return ftel[towho].rcvcard(pl, -1);			// ���������ߣ��޷���֪������ʲô

	}
	
	// ���͵�ǰ��ҡ��ơ���ɫ��Ϣ
	public boolean sendnow(int towho, int pl, int num, int cl)
	{
		return ftel[towho].rcvnow(pl, num, cl);
	}
	
	// ���ͳ�����Ϣ����˭��˭�����˵ڼ����ƣ�
	public boolean sendresult(int towho, int pl, int num)
	{
		if (towho == pl)
			return ftel[towho].rcvresult(pl, num);
		else
			return ftel[towho].rcvresult(pl, -1);		// ���ǳ����ߣ��޷���֪�����ǵڼ�����
	}
	
	// ���ͳ������󣨸�˭���Ƿ���Ҫ���ƣ�
	public boolean sendneed(int towho, boolean need)
	{
		return ftel[towho].rcvneed(need);
	}
	
	// ����ѡ��ɫ���󣨸�˭���Ƿ���Ҫѡ��ɫ��
	public boolean sendneed2(int towho, boolean need)
	{
		return ftel[towho].rcvneed2(need);
	}
	
	// ����ѡ����ô����
	public int rcvans(int fromwho)
	{
		return ftel[fromwho].sendans();
	}
	
	// ������ҵ�����Ϣ����˭��˭�����ˣ�
	public boolean sendexit(int towho, int pl)
	{
		return ftel[towho].rcvexit(pl);
	}
	
	// ����ʤ����Ϣ����˭��˭Ӯ�ˣ����һ������ʲô��
	public boolean sendwinner(int towho, int pl, int precard)
	{
		return ftel[towho].rcvwinner(pl, precard);
	}
}

