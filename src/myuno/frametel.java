package myuno;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JOptionPane;

// 客户端通信工具框架，继承玩家通信工具
public class frametel extends playertel {
	unoframe u;				// 用户对象的引用
	
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
		String serverAddress=JOptionPane.showInputDialog("输入服务器 IP地址");
		//------------------------------------------------------------- 接口，读取服务器 Port端口
        String serverPortString=JOptionPane.showInputDialog("输入服务器 Port端口");
        int serverPort = 0;

        try {
        	serverPort = Integer.parseInt(serverPortString);
			//------------------------------------------------------------- 接口，连接服务器
            s = new Socket(serverAddress,serverPort);
            //------------------------------------------------------------- 接口，利用BufferReader流从服务器接收消息
            InputStream is = s.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            //------------------------------------------------------------- 接口，利用PrintStream流向服务器发送消息
            OutputStream os = s.getOutputStream();
            ps = new PrintStream(os);
            
            // 开始客户端线程
            new Thread(new play(u)).start();
            return true;
        }
        catch (Exception ex) {
            return false;
        }
	
	}
	
	// 以下函数均为重写(Override)。返回值：true 接收成功，false 接收失败
	// 接收提示信息
	public boolean rcvinfo(String info)
	{
		u.rcvinfo(info);
		return true;
	}
	
	// 接收开局信息（我的编号）
	public boolean rcvstart(int pl)
	{
		super.rcvstart(pl);
		u.rcvstart(pl);
		return true;
	}
	
	// 接收摸牌信息（谁，摸到了什么）
	public boolean rcvcard(int pl, int num)
	{
		u.rcvcard(pl, num);
		return true;
	}
	
	// 接收当前玩家、牌、颜色信息
	public boolean rcvnow(int pl, int num, int cl, int direc)
	{
		u.rcvnow(pl, num, cl, direc);
		return true;
	}
	
	// 接收是否需要选择颜色的信息
	public boolean rcvneed2(boolean need)
	{
		super.rcvneed2(need);
		u.rcvneed2(need);
		return true;
	}
	
	// 接收出牌信息（谁，出了第几张牌）
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
	
	// 接收玩家掉线信息（谁掉线了）
	public boolean rcvexit(int pl)
	{
		u.rcvexit(pl);
		return true;
	}
	
	// 接收胜者信息（谁赢了，最后一张牌是什么）
	public boolean rcvwinner(int pl, int precard)
	{
		u.rcvwinner(pl, precard);
		return true;
	}
}
