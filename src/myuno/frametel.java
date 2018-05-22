package myuno;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JOptionPane;

// �ͻ���ͨ�Ź��߿�ܣ��̳����ͨ�Ź���
public class frametel extends playertel {
	unoframe u;				// �û����������
	
    private Socket s = null;
    private BufferedReader br = null;
    private PrintStream ps = null;
    public boolean canRun;
	
	public frametel(unoframe un)
	{
		u = un;
		canRun = false;
	}
	
	class play implements Runnable{
		unoframe u;
		play(unoframe un)
		{
			u = un;
		}
		public void run()
		{
			try {
				canRun = true;
	            while(canRun) {
	            	String str = br.readLine();
	            	String [] rcv = str.split(" ");
	            	switch (rcv[0])
	            	{
	            	case "i":
	            		rcvinfo (rcv[1]);
	            		break;
	            	case "s":
	            		rcvstart (Integer.parseInt(rcv[1]));
	            		break;
	            	case "c":
	            		rcvcard (Integer.parseInt(rcv[1]), Integer.parseInt(rcv[2]));
	            		break;
	            	case "n":
	            		rcvnow (Integer.parseInt(rcv[1]), Integer.parseInt(rcv[2]), Integer.parseInt(rcv[3]), Integer.parseInt(rcv[4]));
	            		break;
	            	case "r":
	            		rcvresult (Integer.parseInt(rcv[1]), Integer.parseInt(rcv[2]));
	            		break;
	            	case "1":
	            		rcvneed (Boolean.parseBoolean(rcv[1]));
	            		break;
	            	case "2":
	            		rcvneed2 (Boolean.parseBoolean(rcv[1]));
	            		break;
	            	case "e":
	            		rcvexit (Integer.parseInt(rcv[1]));
	            		break;
	            	case "w":
	            		rcvwinner (Integer.parseInt(rcv[1]), Integer.parseInt(rcv[2]));
	            		if (s != null)
	            			s.close();
                		canRun = false;
	            		break;
	            	default:
	            		canRun = false;
	            		u.rcvexit(u.mypl);
	            	}
	            }
	        }
	        catch (Exception ex) {
	            canRun=false;
	            u.rcvexit(u.mypl);
	        }
		}
	}
	
	public boolean sender(String s) {
		try {
        	ps.println(s);
        	return true;
        }
		catch (Exception ex) {
			canRun=false;
            u.rcvexit(u.mypl);
            return false;
		}
	}
	
	
	public boolean myconnect () {
		String serverAddress=JOptionPane.showInputDialog("��������� IP��ַ");
		//------------------------------------------------------------- �ӿڣ���ȡ������ Port�˿�
        String serverPortString=JOptionPane.showInputDialog("��������� Port�˿�");
        int serverPort = 0;

        try {
        	serverPort = Integer.parseInt(serverPortString);
			//------------------------------------------------------------- �ӿڣ����ӷ�����
            s = new Socket(serverAddress,serverPort);
            //------------------------------------------------------------- �ӿڣ�����BufferReader���ӷ�����������Ϣ
            InputStream is = s.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            //------------------------------------------------------------- �ӿڣ�����PrintStream���������������Ϣ
            OutputStream os = s.getOutputStream();
            ps = new PrintStream(os);
            
            // ��ʼ�ͻ����߳�
            new Thread(new play(u)).start();
            return true;
        }
        catch (Exception ex) {
            return false;
        }
	
	}
	
	// ���º�����Ϊ��д(Override)������ֵ��true ���ճɹ���false ����ʧ��
	// ������ʾ��Ϣ
	public boolean rcvinfo(String info)
	{
		u.rcvinfo(info);
		return true;
	}
	
	// ���տ�����Ϣ���ҵı�ţ�
	public boolean rcvstart(int pl)
	{
		super.rcvstart(pl);
		u.rcvstart(pl);
		return true;
	}
	
	// ����������Ϣ��˭��������ʲô��
	public boolean rcvcard(int pl, int num)
	{
		u.rcvcard(pl, num);
		return true;
	}
	
	// ���յ�ǰ��ҡ��ơ���ɫ��Ϣ
	public boolean rcvnow(int pl, int num, int cl, int direc)
	{
		u.rcvnow(pl, num, cl, direc);
		return true;
	}
	
	// �����Ƿ���Ҫѡ����ɫ����Ϣ
	public boolean rcvneed2(boolean need)
	{
		super.rcvneed2(need);
		u.rcvneed2(need);
		return true;
	}
	
	// ���ճ�����Ϣ��˭�����˵ڼ����ƣ�
	public boolean rcvresult(int pl, int num)
	{
		u.rcvresult(pl, num);
		return true;
	}
	
	public boolean setans(int an)
	{
		if (!canRun)
			return super.setans(an);
		else
			return sender("1 " + an);
	}
	
	public boolean setans2(int an)	
	{
		if (!canRun)
			return super.setans2(an);
		else
			return sender("2 " + an);
	}
	
	// ������ҵ�����Ϣ��˭�����ˣ�
	public boolean rcvexit(int pl)
	{
		u.rcvexit(pl);
		return true;
	}
	
	// ����ʤ����Ϣ��˭Ӯ�ˣ����һ������ʲô��
	public boolean rcvwinner(int pl, int precard)
	{
		u.rcvwinner(pl, precard);
		return true;
	}
}
