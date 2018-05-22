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
                String str = br.readLine(); //��ȡ��Socket��������Ϣ
                String [] rcv = str.split(" ");
                switch(rcv[0])
                {
                case "1":
                	if (needed && !clicked)
            		{
            			clicked = true;		// ������ѡ��
            			ans = Integer.parseInt(rcv[1]);
            		}
                	break;
                case "2":
                	if (needed2 && !clicked)
            		{
            			clicked = true;		// ������ѡ��
            			ans = Integer.parseInt(rcv[1]);
            		}
                	break;
                default:
                	canRun = false;
                }
            }
        }
        catch (Exception ex) {
            //�˴����Խ���ͻ��쳣��������
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
    		// �������ѡ��
    		clicked = false;
    		ans = -2;
    	}
    	else if (rcv[0].equals("2"))
    	{
    		needed2 = Boolean.parseBoolean(rcv[1]);
    		// �������ѡ��
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
		if ((needed || needed2) && clicked)		// ֻ����Ҫѡ�����Ѿ�ѡ�񣬲ŷ���ѡ����
		{
			int an = ans;
			clicked = false;			// �����ǰѡ��
			return an;
		}
		return -2;
	}
    
    
    public boolean needed;		// �Ƿ���Ҫ����
	public boolean needed2;		// �Ƿ���Ҫѡ��ɫ
	public boolean clicked;		// �Ƿ��Ѿ�����ѡ�񣬶����Ʋ�����δ����
	public int ans;				// ѡ��Ľ��
	
	
}
