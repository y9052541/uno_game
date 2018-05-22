package myuno;

/*
 * 本程序创建一个服务器客户端，并显示客户端的 IPAddress:Port 套接字
 * 
 * 服务器可以视为一个黑箱，接收客户端的信息并抄送给所有客户端，可以直接运行
 */
import java.net.*;
import java.util.ArrayList;

public class unoserver implements Runnable {
    private Socket s = null;
    private ServerSocket ss=null;
    public int ServerPort;
    private int clientnum;
    //保存客户端的线程
    private ArrayList<chater> clients=new ArrayList<chater>();
    
    public String hostAddress;
    
    // 暂停函数
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
    	
        // 获得服务器 IP 地址
        InetAddress address = InetAddress.getLocalHost();
        hostAddress = address.getHostAddress();

        // System.out.println("IP: " + hostAddress);
        // System.out.println("Port: " + ServerPort);
        
        // 创建服务器线程
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
