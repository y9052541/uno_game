package myuno;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class chater extends Thread {
	public Socket s;
    private BufferedReader br = null;
    private PrintStream ps = null;
    public boolean canRun=true;
    public chater(Socket s) throws Exception {
    	this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());
        
        needed = false;
		needed2 = false;
		clicked = false;
		ans = -2;
    }

    @Override
    public void run() {
        try {
            while(canRun) {
                String str = br.readLine(); //读取该Socket传来的信息
                String [] rcv = str.split(" ");
                switch(rcv[0])
                {
                case "1":
                	if (needed && !clicked)
            		{
            			clicked = true;		// 设置已选择
            			ans = Integer.parseInt(rcv[1]);
            		}
                	break;
                case "2":
                	if (needed2 && !clicked)
            		{
            			clicked = true;		// 设置已选择
            			ans = Integer.parseInt(rcv[1]);
            		}
                	break;
                default:
                	canRun = false;
                }
            }
        }
        catch (Exception ex) {
            //此处可以解决客户异常下线问题
            canRun = false;
        }
    }
    
    public boolean sender(String str)
    {
    	if (!canRun)
    		return false;
    	String [] rcv = str.split(" ");
    	if (rcv[0].equals("1"))
    	{
    		needed = Boolean.parseBoolean(rcv[1]);
    		// 清除现有选择
    		clicked = false;
    		ans = -2;
    	}
    	else if (rcv[0].equals("2"))
    	{
    		needed2 = Boolean.parseBoolean(rcv[1]);
    		// 清除现有选择
    		clicked = false;
    		ans = -2;
    	}
    	try {
    		ps.println(str);
    		return true;
    	}
        catch (Exception ex) {
            canRun = false;
            return false;
        }
    }
    
    public int getans()
	{
		if ((needed || needed2) && clicked)		// 只有需要选择且已经选择，才发送选择结果
		{
			int an = ans;
			clicked = false;			// 清除当前选择
			return an;
		}
		return -2;
	}
    
    
    public boolean needed;		// 是否需要出牌
	public boolean needed2;		// 是否需要选颜色
	public boolean clicked;		// 是否已经做出选择，而控制部分尚未接收
	public int ans;				// 选择的结果
	
	
}
