package myuno;

/*
 * �����򴴽�һ���������ͻ��ˣ�����ʾ�ͻ��˵� IPAddress:Port �׽���
 * 
 * ������������Ϊһ�����䣬���տͻ��˵���Ϣ�����͸����пͻ��ˣ�����ֱ������
 */
import java.net.*;
import java.util.ArrayList;

public class unoserver implements Runnable {
    private Socket s = null;
    private ServerSocket ss=null;
    public int ServerPort;
    private int clientnum;
    //����ͻ��˵��߳�
    private ArrayList<chater> clients=new ArrayList<chater>();
    
    public String hostAddress;
    
    // ��ͣ����
 	public static void mysleep(int tim)
 	{
 		try {
 			Thread.sleep(tim);
 		} catch (InterruptedException e) {
 			
 		}
 	}
    
    public unoserver(int myport) throws Exception {
    	
    	clientnum = 0;
    	ServerPort = myport;
    	
        // ��÷����� IP ��ַ
        InetAddress address = InetAddress.getLocalHost();
        hostAddress = address.getHostAddress();

        // System.out.println("IP: " + hostAddress);
        // System.out.println("Port: " + ServerPort);
        
        // �����������߳�
        ss = new ServerSocket(ServerPort);
        new Thread(this).start();
    }

    class play implements Runnable{
    	netcontrollertel ntc;
		play(netcontrollertel nt)
		{
			ntc = nt;
		}
		public void run()
		{
			unocontroller.play2(ntc);
		}
	}
    
    @Override
    public void run() {
        try {
            while(true) {
                s = ss.accept();
                chater ct = new chater(s);
                clients.add(ct);
                ct.start();
                clientnum++;
                if (clientnum % 4 == 0)
                {
                	chater [] cts = new chater [4];
                	for (int i = 0; i < 4; i++)
                		cts[i] = clients.get(clientnum - 4 + i);
                	netcontrollertel ntc = new netcontrollertel(cts);
                	new Thread(new play(ntc)).start();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }
}
