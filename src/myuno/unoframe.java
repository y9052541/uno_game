package myuno;

import java.awt.EventQueue;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;


// �û�������
public class unoframe extends JFrame {

	// Ĭ��
	private static final long serialVersionUID = 1L;
	
	public static final String rules = "<html><body>��Ϸ����UNOС��Ϸ<br>" + 
			"<br>" + 
			"���ߣ������á�Ҷ������������<br>" + 
			"<br>" + 
			"ͼƬ�������ز��������硣<br>" + 
			"<br>" + 
			"UNO���ͺ͹���˵����<br>" + 
			"108���ƣ���ͨ��76�� = 4ɫ * 19�ţ�ÿ����ɫ��1 * (0) + 2 * (1..9)��������24�� = 3�� * 4ɫ * 2�ţ�3�ֱַ�Ϊskip��reverse��+2��������8�� = 4�ű�ɫ + 4��(+4)��������������Ϊ50��������Ϊ20�����ఴ���ִ�С��<br>" + 
			"<br>" + 
			"��ϷΪ4��һ�֡���;������Ϊ�˳���Ϸ��<br>" + 
			"���ַ��ƣ�ÿ��7�š����ŷ��������֮�б����С��Ϊ���֡�����һ����Ϊ���ֳ��Ƶĸ��ݣ���������ͨ�������·��ơ�<br>" + 
			"����ͷָʾ�ķ�λ����ɫ�������ƣ�û������������������Ҳֻ�ܷ����ֳ����͵�ǰ��ͬɫ��ͬ���͵���Ϊ���ã������Ƴ��⣩��������Ƶ��ҽ���û�п��õ��ơ�����1�ź��������ƿ��ã����Զ�����������ֵ���һ�ҳ��ơ�����ƶѱ����꣬�Ͱѳ���������е��ƺ͵�ǰ��֮��������·����ƶѡ�<br>" + 
			"skip������һ�ң�reverse�������ֻ�����ˣ����൱��skip����+2��һ����2������������ɫ��+4��ָ����һ��ɫ��+4�����������ƿ��õ������ʹ�ã���ɫ���޴����ơ�<br>" + 
			"���һ�ſ�����������ơ�ʤ��Ϊ���ȳ��������Ƶ���ң�����Ψһ���ߵ���ҡ�û�е������������Ƶ��������<br>" + 
			"20�벻�����ж�Ϊ��ʱ���ߡ�<br>" +
			"<br>" + 
			"�����淨���Ƚ���������������65535���ڵķǸ�������Ϊ�˿ںš�֮�󣬸�λ��ҵ��������ģʽ����ť��������Ӧ��IP��ַ�Ͷ˿ںţ��ȴ�ƥ�䡣ƥ����4�˼��ɿ�ʼ��Ϸ��<br>" + 
			"<br>" + 
			"�ɰ�F5����ͣ/���ű������֡�ף��Ϸ��죡<body></html>";

	// ͨ�Ź���
	public frametel tel;
	public unoserver sv;
	public mymusic msc;
	
	// buttons & labels
	public JButton start;
	public JButton restart;
	public JLabel title1;
	public JLabel title2;
	public JButton start2;
	public JButton start3;
	public JButton help;
	
	public JButton ret;
	public JLabel help1;
	public JLabel help2;
	
	private JPanel contentPane;			// Ĭ��
	
	public JButton [] mycard;
	public JLabel [] othcard;
	public JLabel [] oppcard;
	public JLabel [] othnum;
	
	public JButton [] choosecolor;		// ��ɫ��ѡ��ɫ��ť
	public JLabel info;					// ��ʾ��Ϣ
	
	public JLabel nowcard;				// ��ǰ����
	public JButton newcard;				// ���ư�ť
	public JLabel nowwho;				// ָʾ��ǰ���
	
	
	// ��ͼ��������
	private static final int windoww = 1000;
	private static final int windowh = 600;
	
	private static final int bod = 60;		// ��Ե���
	
	private static final int cardw = 60;
	private static final int cardh = 93;
	private static final int cardd = 5;		// �Ƽ��
	
	// mycard λ��
	private static final int cardy = windowh - bod - cardh;
	
	// othcard λ��
	private static final int [] backx = new int[] {bod, windoww - bod - cardw};
	private static final int backy = (windowh - cardh) / 2;
	
	// othnum λ��
	private static final int numw = 30;
	private static final int numh = 30;
	
	private static final ImageIcon back = new ImageIcon("unoimg\\back.png");
	
	// ��������
	static final int tot = 108;			// ������
	
	private int [] cardnum;				// �����Ƶ������������
	public int mypl;					// �ҵı��
	
	// ��ͣ����
	public static void mysleep(int tim)
	{
		try {
			Thread.sleep(tim);
		} catch (InterruptedException e) {
			
		}
	}
	
	// ���ת��λ
	public String posname(int pl)
	{
		switch((pl + 4 - mypl) % 4)
		{
		case 0:
			return "��";
		case 1:
			return "��";
		case 2:
			return "��";
		default:
			return "��";
		}
	}
	
	// ���ת��λ��ͷ
	public String posarrow(int pl)
	{
		switch((pl + 4 - mypl) % 4)
		{
		case 0:
			return "��";
		case 1:
			return "��";
		case 2:
			return "��";
		default:
			return "��";
		}
	}
	
	// ��ɫ���ת��ɫ
	public Color colorname(int cl)
	{
		switch(cl)
		{
		case 0:
			return Color.RED;
		case 1:
			return Color.YELLOW;
		case 2:
			return Color.BLUE;
		default:
			return Color.GREEN;
		}
	}
	
	// ˢ�¶������
	public void opprefresh()
	{
		int oppnum = cardnum[(mypl + 2) % 4];
		int x = (windoww - oppnum * cardw - (oppnum - 1) * cardd) / 2;
		for (int i = 0; i < oppnum; i++)
		{
			oppcard[i].setBounds(x + i * (cardw + cardd), bod, cardw, cardh);
			if (!oppcard[i].isVisible())
				oppcard[i].setVisible(true);
		}
		for (int i = oppnum; (i < tot && oppcard[i].isVisible()); i++)
			oppcard[i].setVisible(false);
	}
	
	// ˢ���ҵ��ƣ�������������
	public void myrefresh()
	{
		int x = (windoww - cardnum[mypl] * cardw - (cardnum[mypl] - 1) * cardd) / 2;
		for (int i = 0; i < cardnum[mypl]; i++)
		{
			mycard[i].setBounds(x + i * (cardw + cardd), cardy, cardw, cardh);
			if (!mycard[i].isVisible())
				mycard[i].setVisible(true);
		}
		for (int i = cardnum[mypl]; (i < tot && mycard[i].isVisible()); i++)
			mycard[i].setVisible(false);
	}
	
	// ˢ�����ҵ���
	public void othrefresh()
	{
		for (int i = 0; i < 2; i++)
		{
			int num = cardnum[(mypl + i * 2 + 1) % 4];
			othnum[i].setText(String.valueOf(num));
			if (othnum[i].isVisible() && num == 0)
			{
				othnum[i].setVisible(false);
				othcard[i].setVisible(false);
			}
			if (!othnum[i].isVisible() && num > 0)
			{
				othnum[i].setVisible(true);
				othcard[i].setVisible(true);
			}
		}
	}
	
	// ������ʾ��Ϣ
	public void rcvinfo(String inf)
	{
		info.setText(inf);
	}
	
	// ���տ�����Ϣ�������ҵı��
	public void rcvstart(int pl)
	{
		clearinfo();
		mypl = pl;
		for (int i = 0; i < 4; i++)
			cardnum[i] = 0;
		drawcards();
	}
	
	// ����������Ϣ��1�ţ���ˢ�£�˭��������ʲô��
	public void rcvcard(int pl, int nam)
	{
		++cardnum[pl];
		if (pl == mypl)
		{
			mycard[cardnum[mypl] - 1].setIcon(new ImageIcon("unoimg\\"+unocard.filename(nam)+".png"));
			myrefresh();
		}
		else if (pl == (mypl + 2) % 4)
		{
			opprefresh();
		}
		else
		{
			othrefresh();
		}
		clearinfo();
	}
	
	// ���յ�ǰ��ҡ��ơ���ɫ��Ϣ
	public void rcvnow(int pl, int nam, int cl)
	{
		// ���õ�ǰ����
		nowcard.setIcon(new ImageIcon("unoimg\\"+unocard.filename(nam)+".png"));
		if (!nowcard.isVisible())
			nowcard.setVisible(true);
		
		// ָʾ��ǰ���
		nowwho.setText(posarrow(pl));
		if (cl % 2 == 0)
			nowwho.setForeground(Color.WHITE);
		else
			nowwho.setForeground(Color.BLACK);
		nowwho.setBackground(colorname(cl));
		
		if (!nowwho.isVisible())
			nowwho.setVisible(true);
	}
	
	// ���ճ�����Ϣ��ˢ�£�˭�����˵ڼ����ƣ�
	public void rcvresult(int pl, int num)
	{
		--cardnum[pl];
		if (pl == mypl)
		{
			int nu = cardnum[mypl];
			for (int i = num + 1; i <= nu; i++)
				mycard[i - 1].setIcon(mycard[i].getIcon());
			myrefresh();
		}
		else if (pl == (mypl + 2) % 4)
		{
			opprefresh();
		}
		else
		{
			othrefresh();
		}
		if (cardnum[pl] == 1)
		{
			if (pl == mypl)
				info.setText("UNO!");
			else
				info.setText(posname(pl) + "����ң�UNO!");
		}
		else
			clearinfo();
	}
	
	// �����Ƿ���Ҫѡ����ɫ����Ϣ
	public void rcvneed2(boolean need)
	{
		for (int i = 0; i < 4; i++)
			choosecolor[i].setVisible(need);
		if (need)
			info.setText("��ѡ����ɫ");
		else
			clearinfo();
	}
	
	// ������ҵ�����Ϣ��˭�����ˣ�
	public void rcvexit(int pl)
	{
		if (pl == mypl)
		{
			info.setText("��ʧ���� -_-||");
			for (int i = 0; i < 4; i++)
				choosecolor[i].setVisible(false);
			restart.setVisible(true);
		}
		else
			info.setText(posname(pl) + "������˳�����Ϸ��");
		cardnum[pl] = 0;
		if (pl == mypl)
		{
			myrefresh();
		}
		else if (pl == (mypl + 2) % 4)
		{
			opprefresh();
		}
		else
		{
			othrefresh();
		}
	}
	
	// ����ʤ����Ϣ����ʾ��Ϸ�����˭Ӯ�ˣ����һ������ʲô��
	public void rcvwinner(int pl, int nam)
	{
		nowcard.setIcon(new ImageIcon("unoimg\\"+unocard.filename(nam)+".png"));
		if (!nowcard.isVisible())
			nowcard.setVisible(true);
		for (int i = 0; i < 4; i++)
			choosecolor[i].setVisible(false);
		restart.setVisible(true);
		if (pl == mypl)
			info.setText("��Ӯ�ˣ���ϲ O(��_��)O");
		else
			info.setText("������ (T_T) " + posname(pl) + "����һ�ʤ��");
	}
	
	// Ĭ��
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					unoframe frame = new unoframe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// ���� unocontroller.play �߳�
	class play implements Runnable{
		unoframe u;
		play(unoframe un)
		{
			u = un;
		}
		public void run()
		{
			unocontroller.play(u);
		}
	}
	
	// ������ͼ
	/*
	public JButton start;
	public JButton restart;
	private JPanel contentPane;
	public JButton [] mycard;
	public JLabel [] othcard;
	public JLabel [] oppcard;
	public JLabel [] othnum;
	
	public JButton [] choosecolor;
	public JLabel info;
	
	public JLabel nowcard;
	public JButton newcard;
	public JLabel nowwho;
	*/
	
	void drawstart()
	{
		start.setVisible(true);
		start2.setVisible(true);
		start3.setVisible(true);
		help.setVisible(true);
		title1.setVisible(true);
		title2.setVisible(true);
	}
	
	void drawcards()
	{
		newcard.setVisible(true);
		info.setText("");
		info.setVisible(true);
	}
	
	void clearstart()
	{
		start.setVisible(false);
		start2.setVisible(false);
		start3.setVisible(false);
		help.setVisible(false);
		title1.setVisible(false);
		title2.setVisible(false);
		clearinfo();
	}
	
	// ����ƾ�
	void clearcards()
	{
		int i;
		for (i = 0; i < tot; i++)
		{
			mycard[i].setVisible(false);
			oppcard[i].setVisible(false);
		}
		for (i = 0; i < 2; i++)
		{
			othcard[i].setVisible(false);
			othnum[i].setVisible(false);
		}
		nowcard.setVisible(false);
		newcard.setVisible(false);
		nowwho.setVisible(false);
		for (i = 0; i < 4; i++)
			choosecolor[i].setVisible(false);
		clearinfo();
		restart.setVisible(false);
		clearinfo();
	}
	
	void clearinfo()
	{
		info.setText("");
	}
	
	// ���ƺ�ѡ��ɫ
	private class mybtnclick implements ActionListener
	{
		int index;		// ��ť���
		int mod;		// ���� mod = 1; ѡ��ɫ mod = 2
		public mybtnclick(int i, int md)
		{
			index = i;
			mod = md;
		}
		// ������¼�
		public void actionPerformed(ActionEvent arg0) {
			clearinfo();
			if (mod == 1)
				tel.setans(index);
			else if (mod == 2)
				tel.setans2(index);
		}
	}
	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public unoframe() {
		setBackground(new Color(144, 238, 144));
		setTitle("UNOС��Ϸ");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ���ô���λ��
		int windowx = (Toolkit.getDefaultToolkit().getScreenSize().width - windoww) / 2;
		int windowy = (Toolkit.getDefaultToolkit().getScreenSize().height - windowh) / 2;
		if (windowy >= 40)
			windowy -= 20;
		
		setBounds(windowx, windowy, windoww, windowh);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(144, 238, 144));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// �����Լ�
		unoframe u = this;
		
		// ��ʼ��buttons & labels
		title1 = new JLabel("UNOС��Ϸ", JLabel.CENTER);
		title1.setFont(new Font("���Ĳ���", Font.PLAIN, 72));
		title1.setBounds(100, 100, 800, 200);
		contentPane.add(title1);
		
		title2 = new JLabel("���ߣ������á�Ҷ������������");
		title2.setFont(new Font("����", Font.PLAIN, 24));
		title2.setBounds(580, 280, 450, 100);
		contentPane.add(title2);
		
		start = new JButton("����ģʽ");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tel.canRun = false;
				clearstart();
				new Thread(new play(u)).start();
			}
		});
		start.setFont(new Font("����", Font.PLAIN, 18));
		start.setBounds(160, 420, 140, 60);
		contentPane.add(start);
		
		start2 = new JButton("����ģʽ");
		start2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearinfo();
				mypl = -1;
				if (tel.myconnect())
				{
					clearstart();
					rcvinfo("�����ӣ��ȴ�ƥ��...");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "����ʧ�� -_-||", "����", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		start2.setFont(new Font("����", Font.PLAIN, 18));
		start2.setBounds(340, 420, 140, 60);
		contentPane.add(start2);
		
		start3 = new JButton("�½�������");
		start3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearinfo();
				if (sv == null)
				{
					try {
						String str = JOptionPane.showInputDialog("��������� Port �˿�");
						if (sv == null)
						{
							sv = new unoserver (Integer.parseInt(str));
							JOptionPane.showInputDialog("�����ɹ����˿ڣ�" + sv.ServerPort + "��IP���£�", sv.hostAddress);
						}
						else
							JOptionPane.showInputDialog("�������Ѵ��ڡ��˿ڣ�" + sv.ServerPort + "��IP���£�", sv.hostAddress);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "����ʧ�� -_-||", "����", JOptionPane.ERROR_MESSAGE);
					}
				}
				else
					JOptionPane.showInputDialog("�������Ѵ��ڡ��˿ڣ�" + sv.ServerPort + "��IP���£�", sv.hostAddress);
				
			}
		});
		start3.setFont(new Font("����", Font.PLAIN, 18));
		start3.setBounds(700, 420, 140, 60);
		contentPane.add(start3);
		
		help = new JButton("��Ϸ˵��");
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearstart();
				help1.setVisible(true);
				help2.setVisible(true);
				ret.setVisible(true);
			}
		});
		help.setFont(new Font("����", Font.PLAIN, 18));
		help.setBounds(520, 420, 140, 60);
		contentPane.add(help);
		
		newcard = new JButton("");
		newcard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tel.setans(-1);
			}
		});
		newcard.setIcon(back);
		newcard.setBorderPainted(false);
		newcard.setBackground(new Color(144, 238, 144));
		newcard.setBounds((windoww - bod)/2 - cardw, (windowh - cardh)/2, cardw, cardh);
		contentPane.add(newcard);
		newcard.setVisible(false);
		
		nowcard = new JLabel("");
		nowcard.setBounds((windoww + bod)/2, (windowh - cardh)/2, cardw, cardh);
		contentPane.add(nowcard);
		nowcard.setVisible(false);
		
		nowwho = new JLabel("", JLabel.CENTER);
		nowwho.setFont(new Font("����", Font.PLAIN, 20));
		nowwho.setOpaque(true);
		nowwho.setBounds((windoww - numw)/2, (windowh - cardh - bod)/2 - numh, numw, numh);
		contentPane.add(nowwho);
		nowwho.setVisible(false);
		
		mycard = new JButton[tot];
		for (int i = 0; i < tot; i++)
		{
			mycard[i] = new JButton("");
			mycard[i].addActionListener(new mybtnclick(i, 1));
			mycard[i].setBounds(0, 0, cardw, cardh);
			mycard[i].setBorderPainted(false);
			mycard[i].setVisible(false);
			mycard[i].setBackground(new Color(144, 238, 144));
			contentPane.add(mycard[i]);
		}
		
		othcard = new JLabel[2];
		othnum = new JLabel[2];
		for (int i = 0; i < 2; i++)
		{
			othcard[i] = new JLabel("");
			othcard[i].setBounds(backx[i], backy, cardw, cardh);
			othcard[i].setIcon(back);
			othcard[i].setVisible(false);
			contentPane.add(othcard[i]);
			
			othnum[i] = new JLabel("", JLabel.CENTER);
			othnum[i].setFont(new Font("����", Font.PLAIN, 20));
			othnum[i].setBounds(backx[i] + (cardw - numw) / 2, backy - cardd - numh, numw, numh);
			othnum[i].setVisible(false);
			contentPane.add(othnum[i]);
		}
		
		
		oppcard = new JLabel[tot];
		for (int i = 0; i < tot; i++)
		{
			oppcard[i] = new JLabel("");
			oppcard[i].setBounds(0, 0, cardw, cardh);
			oppcard[i].setIcon(back);
			oppcard[i].setVisible(false);
			contentPane.add(oppcard[i]);
		}
		
		choosecolor = new JButton[4];
		int chsclx = (windoww - numw * 4 - cardd * 3) / 2;
		for (int i = 0; i < 4; i++)
		{
			choosecolor[i] = new JButton("");
			choosecolor[i].addActionListener(new mybtnclick(i, 2));
			choosecolor[i].setBounds(chsclx + i * (numw + cardd), (windowh + cardh + bod)/2, numw, numh);
			choosecolor[i].setBackground(colorname(i));
			choosecolor[i].setVisible(false);
			contentPane.add(choosecolor[i]);
		}
		
		info = new JLabel("", JLabel.CENTER);
		info.setFont(new Font("����", Font.PLAIN, 24));
		info.setBounds(bod, bod - numh - cardd * 3, windoww - bod * 2, numh);
		info.setVisible(true);
		contentPane.add(info);
		
		restart = new JButton("���ر���");
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearcards();
				drawstart();
			}
		});
		restart.setFont(new Font("����", Font.PLAIN, 24));
		restart.setBounds((windoww - 150) / 2, (windowh + cardh + bod)/2, 150, 50);
		restart.setVisible(false);
		contentPane.add(restart);
		
		help1 = new JLabel("��Ϸ˵��", JLabel.CENTER);
		help1.setFont(new Font("����", Font.PLAIN, 28));
		help1.setBounds(400, 20, 200, 40);
		help1.setVisible(false);
		contentPane.add(help1);
		
		help2 = new JLabel(rules);
		help2.setFont(new Font("����", Font.PLAIN, 16));
		help2.setBounds(50, 60, 900, 480);
		help2.setVisible(false);
		contentPane.add(help2);
		
		ret = new JButton("����");
		ret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				help1.setVisible(false);
				help2.setVisible(false);
				ret.setVisible(false);
				drawstart();
			}
		});
		ret.setFont(new Font("����", Font.PLAIN, 18));
		ret.setBounds(860, 500, 80, 40);
		ret.setVisible(false);
		contentPane.add(ret);

		// ��ʼ����������
		cardnum = new int[] {0, 0, 0, 0};
		mypl = 0;
		tel = new frametel(u);
		sv = null;
		
		msc = new mymusic();
		msc.init();
		
		JRootPane rp = this.getRootPane(); 
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_F5,0); 
		InputMap inputMap = rp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW); inputMap.put(stroke, KeyEvent.VK_F5); rp.getActionMap().put(KeyEvent.VK_F5, (Action) new AbstractAction() {
		    public void actionPerformed(ActionEvent e) { 
		    	msc.changestate();
		    } 
		});
	}
}
