package myuno;

public class netcontrollertel extends controllertel {
	
	chater [] cts;
	
	// ���캯����ft Ϊ������ҵ�ͨ�Ź���
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
	
	// ������ʾ��Ϣ����˭����ʲô�ַ�����
	public boolean sendinfo(int towho, String info)
	{
		return cts[towho].sender("i " + info);
	}
	
	// ���Ϳ�����Ϣ����˭������Ǽ���
	public boolean sendstart(int towho, int pl)
	{
		return cts[towho].sender("s " + pl);
	}
	
	// ����������Ϣ����˭��˭��������ʲô��
	public boolean sendcard(int towho, int pl, int num)
	{
		if (towho == pl)
			return cts[towho].sender("c " + pl + " " + num);
		else
			return cts[towho].sender("c " + pl + " " + (-1));			// ���������ߣ��޷���֪������ʲô
	}
	
	// ���͵�ǰ��ҡ��ơ���ɫ��Ϣ
	public boolean sendnow(int towho, int pl, int num, int cl, int direc)
	{
		return cts[towho].sender("n " + pl + " " + num + " " + cl + " " + direc);
	}
	
	// ���ͳ�����Ϣ����˭��˭�����˵ڼ����ƣ�
	public boolean sendresult(int towho, int pl, int num)
	{
		if (towho == pl)
			return cts[towho].sender("r " + pl + " " + num);
		else
			return cts[towho].sender("r " + pl + " " + (-1));		// ���ǳ����ߣ��޷���֪�����ǵڼ�����
	}
	
	// ���ͳ������󣨸�˭���Ƿ���Ҫ���ƣ�
	public boolean sendneed(int towho, boolean need)
	{
		if (need)
			return cts[towho].sender("1 true");
		else
			return cts[towho].sender("1 false");
	}
	
	// ����ѡ��ɫ���󣨸�˭���Ƿ���Ҫѡ��ɫ��
	public boolean sendneed2(int towho, boolean need)
	{
		if (need)
			return cts[towho].sender("2 true");
		else
			return cts[towho].sender("2 false");
	}
	
	// ����ѡ����ô����
	public int rcvans(int fromwho)
	{
		return cts[fromwho].getans();
	}
	
	// ������ҵ�����Ϣ����˭��˭�����ˣ�
	public boolean sendexit(int towho, int pl)
	{
		return cts[towho].sender("e " + pl);
	}
	
	// ����ʤ����Ϣ����˭��˭Ӯ�ˣ����һ������ʲô��
	public boolean sendwinner(int towho, int pl, int precard)
	{
		return cts[towho].sender("w " + pl + " " + precard);
	}
}

