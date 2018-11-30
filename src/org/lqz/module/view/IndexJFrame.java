package org.lqz.module.view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.*;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jfree.chart.labels.BubbleXYItemLabelGenerator;
import org.lqz.framework.util.ImagePanel;
import org.lqz.framework.util.MyFont;
import org.lqz.framework.util.WindowOpacity;
import org.lqz.module.entity.User;

import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class IndexJFrame extends JFrame implements MouseListener, ActionListener {

	// 定义用户对象
	private User user;

	// 定义辅助变量
	int sign_home = 0;
	int sign_baseData = 0;
	int sign_purchase_sale_stock = 0;
	int sign_userManager = 0;

	// 获得屏幕的大小
	final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;

	// 定义全局组件
	JPanel backgroundPanel, topPanel, topMenu, topPrompt, centerPanel,
			splitPaneLeft, splitPaneRight, subPanel, subMenu, bottomPanel;
	JTabbedPane jTabbedPane;

	JLabel home, baseData, purchase_sale_stock, userManager;
	private JSplitPane splitPane;

	public IndexJFrame() {
		// 设置tab面板缩进
		UIManager.put("TabbedPane.tabAreaInsets",
				new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0));
		try {
			Image imgae = ImageIO.read(new File("image/logo.png"));
			this.setIconImage(imgae);
		} catch (IOException e) {
			e.printStackTrace();
		}

		initBackgroundPanel();
		this.setTitle("捷通科技RFID_demo");
		this.setSize((int) (width * 0.55f), (int) (height * 0.6f));
		this.setLocationRelativeTo(null); // 此窗口将置于屏幕的中央。
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public IndexJFrame(User user) {

		this.user = user;

		// 窗口淡入淡出
		// new WindowOpacity(this);

		// 设置tab面板缩进
		UIManager.put("TabbedPane.tabAreaInsets",
				new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0));
		try {
			Image imgae = ImageIO.read(new File("image/logo.png"));
			this.setIconImage(imgae);
		} catch (IOException e) {
			e.printStackTrace();
		}

		initBackgroundPanel();

		this.setTitle("销售管理系统");
		this.setSize((int) (width * 0.6f), (int) (height * 0.7f));
		this.setVisible(true);
		this.setLocationRelativeTo(null); // 此窗口将置于屏幕的中央。
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// 初始化背景面板
	public void initBackgroundPanel() {
		backgroundPanel = new JPanel(new BorderLayout());// 1.
		
		initTop(backgroundPanel);// 2.
		initCenterPanel(backgroundPanel);// 3.
		initBottom(backgroundPanel);// 20181126
		
		getContentPane().add(backgroundPanel);// 6.
	}

	// 初始化顶部顶部面板
	public void initTop(JPanel backgroundPanel) {
		initTopMenu();
		initTopPrompt();

		topPanel = new JPanel(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(width, 40));

		topPanel.add(topMenu, "West");
		topPanel.add(topPrompt, "East");
		topPrompt.setLayout(new BorderLayout(0, 0));
		
		lblLanguageSet = new JLabel("Language(语言)");
		topPrompt.add(lblLanguageSet, BorderLayout.WEST);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"中文(Chinese)", "EngLish"}));
		topPrompt.add(comboBox, BorderLayout.CENTER);
		
		backgroundPanel.add(topPanel, "North");// 4.
	}

	// 初始化顶部菜单
	public void initTopMenu() {
		topMenu = new JPanel();
		topMenu.setPreferredSize(new Dimension(400, 40));
		topMenu.setOpaque(false);
		String[] nameStrings = { "首页", "分立器", "四通道和多通道", "用户管理" };
		home = CreateMenuLabel(home, nameStrings[0], "home", topMenu);
		home.setName("home");
		baseData = CreateMenuLabel(baseData, nameStrings[1], "baseData",topMenu);
		baseData.setName("baseData");
		purchase_sale_stock = CreateMenuLabel(purchase_sale_stock,nameStrings[2], "purchase_sale_stock", topMenu);
		purchase_sale_stock.setName("purchase_sale_stock");
		userManager = CreateMenuLabel(userManager, nameStrings[3],"userManager", topMenu);
		userManager.setName("userManager");
	}

	// 初始化顶部欢迎面板
	public void initTopPrompt() {
		Icon icon = new ImageIcon("image/male.png");
		topPrompt = new JPanel();
		topPrompt.setPreferredSize(new Dimension(200, 40));
		topPrompt.setOpaque(false);
	}

	/******************************************* 2018-11-27 start ********************************/
	// 初始化顶部底部面板
	public void initBottom(JPanel backgroundPanel) {
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setPreferredSize(new Dimension(180, 140));
		
		initBottomMenu();
		initBottomPrompt();

		footerTopPanel = new JPanel();
		footerTopPanel.setPreferredSize(new Dimension(180, 40));
		
		footerCenterPanel = new JPanel();
		
		footerCenterPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		bottomPanel.add(footerTopPanel,BorderLayout.NORTH);
		footerTopPanel.setLayout(new BorderLayout(0, 0));
		
		footerOperationInfoLeftPanel = new JPanel();
		footerTopPanel.add(footerOperationInfoLeftPanel,BorderLayout.WEST);
		footerOperationInfoLeftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblNewLabel_1 = new JLabel("操作记录:");
		footerOperationInfoLeftPanel.add(lblNewLabel_1);
		
		chckbxNewCheckBox = new JCheckBox("自动清空");
		footerOperationInfoLeftPanel.add(chckbxNewCheckBox);
		
		footerOperationInfoRightPanel = new JPanel();
		footerOperationInfoRightPanel.setPreferredSize(new Dimension(80,30));
		footerTopPanel.add(footerOperationInfoRightPanel, BorderLayout.EAST);
		footerOperationInfoRightPanel.setLayout(new BorderLayout(0, 0));
		
		bottomPanel.add(footerCenterPanel,BorderLayout.CENTER);
		footerCenterPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		textArea = new JTextArea();
		JScrollPane jScrollPane = new JScrollPane(textArea);
		footerCenterPanel.add(jScrollPane);
		backgroundPanel.add(bottomPanel, "South");// 20181126
	}

	private void initBottomPrompt() {
		Icon icon = new ImageIcon("image/male.png");
	}

	private void initBottomMenu() {
	}

	/******************************************* 2018-11-27 end ********************************/
	// 创建顶部菜单Label
	public JLabel CreateMenuLabel(JLabel jlabel, String text, String name,
			JPanel jpanel) {
		JLabel line = new JLabel("<html>&nbsp;<font color='#D2D2D2'>|</font>&nbsp;</html>");
		Icon icon = new ImageIcon("image/" + name + ".png");
		jlabel = new JLabel(icon);
		jlabel.setText(text);
		jlabel.addMouseListener(this);
		jlabel.setFont(MyFont.Static);
		jpanel.add(jlabel);
		if (!"userManager".equals(name)) {
			jpanel.add(line);
		}
		return jlabel;
	}

	// 初始化中心面板
	public void initCenterPanel(JPanel backgroundPanel) {
		centerPanel = new JPanel();
		centerPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		centerPanel.setLayout(new BorderLayout(0, 0));// 6.
		splitPane = new JSplitPane();// 20181126
		splitPane.setDividerLocation(280);// 20181126
		centerPanel.add(splitPane);// 20181126
		
		splitPaneLeft = new JPanel();// 20181126
		splitPane.setLeftComponent(splitPaneLeft);// 20181126
		splitPaneLeft.setLayout(new BorderLayout(0, 0));// 20181126
		
		SerialPortSocketManagerJPanel serialPortSocketManagerJPanel = new SerialPortSocketManagerJPanel();
		
		splitPaneLeft.add(serialPortSocketManagerJPanel);// 20181126
		
		splitPaneRight = new JPanel();// 20181126
		splitPane.setRightComponent(splitPaneRight);// 20181126
		splitPaneRight.setLayout(new BorderLayout(0, 0));
		// "首页"两字变为蓝色
		home.setText("首页");
		home.setFont(MyFont.Static);
		creatHome();
		centerPanel.setOpaque(false);// 设置控件透明
		
		backgroundPanel.add(centerPanel, "Center");// 5.centerPanel
	}

	// 初始化辅助变量
	public void initSign() {
		sign_home = 0;
		sign_baseData = 0;
		sign_purchase_sale_stock = 0;
		sign_userManager = 0;
	}

	// 创建首页面板
	Image bgimg;
	private JPanel footerCenterPanel;
	private JPanel footerTopPanel;
	private JLabel lblNewLabel_1;
	private JPanel footerOperationInfoLeftPanel;
	private JCheckBox chckbxNewCheckBox;
	private JPanel footerOperationInfoRightPanel;
	private JTextArea textArea;
	private JPanel showTagInofPanel;
	private JPanel rightTopPanel;
	private JPanel rightCenterPanel;
	private JPanel rightBottomPanel;
	private JPanel centerTopPanel;
	private JPanel centerBottomPanel;
	private JLabel lblTagEPCCount;
	private JLabel lblTagTidCount;
	private JLabel lblLanguageSet;
	private JComboBox comboBox;
	private JTextField textField;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	private JLabel lblNewLabel_9;
	private JLabel lblNewLabel_10;
	private JLabel lblNewLabel_11;

	public void creatHome() {
		if(null == splitPaneRight){
			return;
		}
		splitPaneRight.removeAll();
		//try {
			//bgimg = ImageIO.read(new File("image/indexbackground.jpg"));
			//ImagePanel centerBackground = new ImagePanel(bgimg);// 20181126
			//splitPaneRight.add(centerBackground);// 20181126
			creatpurchaseSaleStockTab();
		//} catch (IOException e) {
			//e.printStackTrace();
		//}
		
	}

	// 创建基础数据面板
	public void creatBaseDataTab() {
		
		splitPaneRight.removeAll();
		
		// 设置tab标题位置
		jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		// 设置tab布局
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		jTabbedPane.setFont(MyFont.Static);

		jTabbedPane.addTab("商品管理", new GoodsManagerJPanel().backgroundPanel);
		splitPaneRight.add(jTabbedPane, "Center");
	}

	// 创建进销存管理面板
	public void creatpurchaseSaleStockTab() {
		splitPaneRight.removeAll();
		// 设置tab标题位置
		jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		// 设置tab布局
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		jTabbedPane.setFont(MyFont.Static);
		
		jTabbedPane.addTab("基本操作",new BasicOperationManagerJPanel(user).backgroundPanel);
		jTabbedPane.addTab("标签操作",new TagOperationManagerJPanel(user).backgroundPanel);
		jTabbedPane.addTab("设备参数",new DeviceParamsManagerJPanel(user).backgroundPanel);
		jTabbedPane.addTab("通信参数设置", new CommunicationParameterManagerJPanel().backgroundPanel);

		splitPaneRight.add(jTabbedPane, BorderLayout.CENTER);
		
		showTagInofPanel = new JPanel();
		showTagInofPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		showTagInofPanel.setPreferredSize(new Dimension(180, 20));
		splitPaneRight.add(showTagInofPanel, BorderLayout.EAST);
		showTagInofPanel.setLayout(new BorderLayout(0, 0));
		
		rightTopPanel = new JPanel();
		rightTopPanel.setLayout(new BorderLayout(0, 0));
		
		CountTimerManagerJPanel countTimerManagerJPanel = new CountTimerManagerJPanel();
		rightTopPanel.add(countTimerManagerJPanel, BorderLayout.CENTER);
		countTimerManagerJPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		rightTopPanel.setPreferredSize(new Dimension(100,60));
		showTagInofPanel.add(rightTopPanel, BorderLayout.NORTH);
		
		rightCenterPanel = new JPanel();
		showTagInofPanel.add(rightCenterPanel, BorderLayout.CENTER);
		rightCenterPanel.setLayout(new GridLayout(2, 2, 0, 0));
		
		centerTopPanel = new JPanel();
		rightCenterPanel.add(centerTopPanel);
		centerTopPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblNewLabel_11 = new JLabel("已查询到的");
		lblNewLabel_11.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel_11.setForeground(Color.RED);
		centerTopPanel.add(lblNewLabel_11);
		
		lblTagEPCCount = new JLabel("标签EPC数量(个):");
		lblTagEPCCount.setFont(new Font("宋体", Font.PLAIN, 16));
		lblTagEPCCount.setHorizontalAlignment(SwingConstants.CENTER);
		centerTopPanel.add(lblTagEPCCount);
		
		JLabel lblTagEpcCount = new JLabel("0");
		lblTagEpcCount.setFont(new Font("宋体", Font.PLAIN, 40));
		lblTagEpcCount.setHorizontalAlignment(SwingConstants.CENTER);
		centerTopPanel.add(lblTagEpcCount);
		
		JLabel lblTagTIDCount = new JLabel("标签TID数量(个)");
		lblTagTIDCount.setFont(new Font("宋体", Font.PLAIN, 16));
		lblTagTIDCount.setHorizontalAlignment(SwingConstants.CENTER);
		centerTopPanel.add(lblTagTIDCount);
		
		lblTagTidCount = new JLabel("0");
		lblTagTidCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblTagTidCount.setFont(new Font("宋体", Font.PLAIN, 40));
		centerTopPanel.add(lblTagTidCount);
		
		centerBottomPanel = new JPanel();
		rightCenterPanel.add(centerBottomPanel);
		centerBottomPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		panel = new JPanel();
		centerBottomPanel.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblNewLabel = new JLabel("命令执行");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel);
		
		lblNewLabel_5 = new JLabel("速度(个/秒)");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_5);
		
		lblNewLabel_2 = new JLabel("0");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("时间(毫秒)");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("0");
		lblNewLabel_4.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_4);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(0, 0, 0)));
		centerBottomPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblNewLabel_8 = new JLabel("累计");
		lblNewLabel_8.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel_8.setForeground(Color.RED);
		panel_1.add(lblNewLabel_8);
		
		lblNewLabel_9 = new JLabel("标签数量(个)");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_9);
		
		lblNewLabel_10 = new JLabel("0");
		lblNewLabel_10.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_10);
		
		lblNewLabel_6 = new JLabel("运行时间(毫秒)");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_6);
		
		lblNewLabel_7 = new JLabel("0");
		lblNewLabel_7.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_7);
		
		rightBottomPanel = new JPanel();
		rightBottomPanel.setPreferredSize(new Dimension(100,40));
		showTagInofPanel.add(rightBottomPanel, BorderLayout.SOUTH);
	}

	// 创建用户管理面板
	public void creatUserManagerTab() {
		splitPaneRight.removeAll();
		// 设置tab标题位置
		jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		// 设置tab布局
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		jTabbedPane.setFont(MyFont.Static);

		jTabbedPane.addTab("用户管理",	new UserManagerJPanel(user, this).backgroundPanel);
		splitPaneRight.add(jTabbedPane, "Center");
	}

	// 鼠标点击事件
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == home) {
			//home();
		} else if (e.getSource() == baseData) {
			//baseData();
		} else if (e.getSource() == purchase_sale_stock) {
			//purchaseSaleStock();
		} else if (e.getSource() == userManager) {
			//userManager();
		} else {
			//System.out.println("ok");
		}
	}
	private void home(){
		initSign();
		sign_home = 1;
		creatHome();
		userManagerSetText();
		homeSetText();
		baseDataSetText();
		purchaseSaleStockSetText();
	}
	
	private void baseData(){
		initSign();
		sign_baseData = 1;
		creatBaseDataTab();
		userManagerSetText();
		homeSetText();
		baseDataSetText();
		purchaseSaleStockSetText();
	}
	
	private void purchaseSaleStock(){
		initSign();
		sign_purchase_sale_stock = 1;
		creatpurchaseSaleStockTab();
		userManagerSetText();
		homeSetText();
		baseDataSetText();
		purchaseSaleStockSetText();
	}
	
	private void userManager(){
		initSign();
		sign_userManager = 1;
		creatUserManagerTab();
		userManagerSetText();
		homeSetText();
		baseDataSetText();
		purchaseSaleStockSetText();
	}
	
	void homeSetText(){
		home.setText("首页");
		home.setFont(MyFont.Static2);
	}
	
	void baseDataSetText(){
		baseData.setText("分立器");
		baseData.setFont(MyFont.Static2);
	}
	
	void purchaseSaleStockSetText(){
		purchase_sale_stock.setText("四通道和多通道");
		purchase_sale_stock.setFont(MyFont.Static2);
	}
	
	void userManagerSetText(){
		userManager.setText("用户管理");
		userManager.setFont(MyFont.Static2);
	}
	
	
	void homeSetTextOut(){
		home.setText("首页");
		home.setFont(MyFont.Static);
	}
	
	void baseDataSetTextOut(){
		baseData.setText("分立器");
		baseData.setFont(MyFont.Static);
	}
	
	void purchaseSaleStockSetTextOut(){
		purchase_sale_stock.setText("四通道和多通道");
		purchase_sale_stock.setFont(MyFont.Static);
	}
	
	void userManagerSetTextOut(){
		userManager.setText("用户管理");
		userManager.setFont(MyFont.Static);
	}
	
	// 鼠标划入事件
	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == home) {
			// 鼠标改变形状
			home.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			homeSetText();
		} else if (e.getSource() == baseData) {
			baseData.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			baseDataSetText();
		} else if (e.getSource() == purchase_sale_stock) {
			purchase_sale_stock.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));
			purchaseSaleStockSetText();
		} else if (e.getSource() == userManager) {
			userManager.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			userManagerSetText();
		}
	}

	// 鼠标划出事件
	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == home) {
			if (sign_home == 0) {
				homeSetTextOut();
			}
		} else if (e.getSource() == baseData) {
			if (sign_baseData == 0) {
				baseDataSetTextOut();
			}
		} else if (e.getSource() == purchase_sale_stock) {
			if (sign_purchase_sale_stock == 0) {
				purchaseSaleStockSetTextOut();
			}
		} else if (e.getSource() == userManager) {
			if (sign_userManager == 0) {
				userManagerSetTextOut();
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
