package myuno;

import java.util.Random;

// AI ��ܣ��̳����ͨ�Ź���
/*
	���ԣ�ֻ��¼�Լ��ı�ź��Լ��Ƶ�����
	ѡ�����ʱ���ȳ������ƣ��ٰ��Ӿɵ��µ�˳���Գ���
	ѡ����ɫʱ�����ѡ��
*/
public class ai extends playertel {
	
	private int mypl;		// �Լ��ı��
	private int mynum;		// �Լ��м�����
	private Random rd;		// �����������
	
	// ���캯��
	public ai()
	{
		mynum = 0;
		rd = new Random();
	}
	
	// ��ͣ����
	public static void mysleep(int tim)
	{
		try {
			Thread.sleep(tim);
		} catch (InterruptedException e) {

		}
	}
	
	
	// ѡ����Ƶ��߳�
	class guessans implements Runnable{
		public void run()
		{
			mysleep(1000);					// ��������ҵķ�Ӧʱ��
			for (int i = -1; i < mynum; i++)	// �ȳ������ƣ��ٰ��Ӿɵ��µ�˳���Գ���
			{
				while (needed && clicked)	// ������Ϸ������������ܾ����ա���ʱclicked��Ϊfalse���ɳ�����һ��
					mysleep(60);			// ÿ��0.06����clicked����
				if (needed)					// ����Ϸ���needed����Ϊfalse
					setans(i);
				else
					break;
			}
		}
	}
	
	// ѡ����ɫ���߳�
	class guessans2 implements Runnable{
		public void run()
		{
			mysleep(1000);
			setans2(rd.nextInt(4));		// ���ѡһ��
		}
	}
	
	// ���º�����Ϊ��д(Override)������ֵ��true ���ճɹ���false ����ʧ��
	// ���տ�����Ϣ����ʼ�����
	public boolean rcvstart(int pl)
	{
		super.rcvstart(pl);
		mypl = pl;
		mynum = 0;
		return true;
	}
	
	// ����������Ϣ��˭��������ʲô��
	public boolean rcvcard(int pl, int num)
	{
		if (pl == mypl)
			mynum++;
		return true;
	}
	
	// ���ճ�����Ϣ��˭�����˵ڼ����ƣ�
	public boolean rcvresult(int pl, int num)
	{
		if (pl == mypl)
			mynum--;
		return true;
	}
	
	// ���ճ�������
	public boolean rcvneed(boolean need)
	{
		super.rcvneed(need);					// ������
		new Thread(new guessans()).start();		// �����߳�ѡ�Ƴ�
		return true;
	}
	
	// ����ѡ��ɫ����
	public boolean rcvneed2(boolean need)
	{
		super.rcvneed2(need);					// ������
		new Thread(new guessans2()).start();	// �����߳�ѡ����ɫ
		return true;
	}
}
