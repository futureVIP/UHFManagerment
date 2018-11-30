package org.lqz.module.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.lqz.framework.util.BaseTableModule;
import org.lqz.framework.util.MyFont;
import org.lqz.framework.util.StringOutPutInfo;
import org.lqz.framework.util.SystemTime;
import org.lqz.framework.util.TableStyleTools;
import serialport.service.SerialPortService;
import serialport.service.impl.SerialPortServiceImpl;
import com.jietong.rfid.uhf.dao.impl.Reader;
import com.jietong.rfid.uhf.entity.ConnectInfo;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.util.Regex;

import java.awt.Font;

@SuppressWarnings("unchecked")
public class SerialPortSocketManagerJPanel extends JPanel implements
		ActionListener, MouseListener, DocumentListener {
	private static final long serialVersionUID = 1L;

	// 定义全局组件
	private JPanel backgroundPanel, topPanel, toolPanel, footerRightPanel, tablePanel;
	private JPanel bottomPanel;
	private BaseTableModule baseTableModule;
	private JTable table;
	private JScrollPane jScrollPane;
	private JLabel tool_add, tool_modify, tool_delete;
	// 用户对象
	private Reader reader;
	private JButton btnGetCommSocket;
	private JLabel lblLocalHost;
	private JLabel lblPort;
	private JTextField tfLocalhost;
	private JTextField tfPort;
	private JButton btnServiceSocket;
	private JLabel lblSerialPortNo;
	private JComboBox cbbSerialPorts;
	private JButton btnSerialPortConnect;
	private JButton btnClientSocket;
	private JPanel footerLeftPanel;
	private JLabel lblVersoinInfo;
	private JButton btnSearchVersion;
	private JButton btnRefresh;
	private JRadioButton rdbtnRS232;
	private JRadioButton rdbtnTCPOrIP;
	private Vector<Vector> vector = new Vector<Vector>();;
	/**
	 * 显示通讯方式IP或COM
	 */
	public static DefaultTableModel tableConnectModel;
	public static ArrayList<ConnectInfo> listConnect = new ArrayList<ConnectInfo>();
	private JLabel lblVersionNo;

	public SerialPortSocketManagerJPanel() {
		setLayout(new BorderLayout(0, 0));
		backgroundPanel = new JPanel(new BorderLayout());
		add(backgroundPanel);
		backgroundPanel.setFont(MyFont.Static);
		backgroundPanel.setForeground(Color.black);
		inital();
	}

	private void inital() {
		initTopPanel();
		initTablePanel();
		initbottomPanel();
	}

	private void initbottomPanel() {
		bottomPanel = new JPanel(new BorderLayout());
		backgroundPanel.add(bottomPanel, "South");
		initGetVersionPanel();
	}

	// 初始化顶部面板
	public void initTopPanel() {
		topPanel = new JPanel(new BorderLayout());
		initSearchPanel();
		backgroundPanel.add(topPanel, "North");
	}

	private void initSearchPanel() {
		aotuGetConnect();
		socketConnect();
		serialPortConnect();
	}

	private void serialPortConnect() {
		JPanel serialPortConnect = new JPanel();
		serialPortConnect.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u4E32\u53E3\u8FDE\u63A5",
				TitledBorder.CENTER, TitledBorder.TOP, MyFont.Static,
				new Color(0, 0, 0)));
		topPanel.add(serialPortConnect, "South");
		serialPortConnect.setLayout(new BorderLayout(0, 0));

		lblSerialPortNo = new JLabel("串口号:");
		lblSerialPortNo.setFont(MyFont.Static);
		lblSerialPortNo.setForeground(Color.black);
		serialPortConnect.add(lblSerialPortNo, "West");

		cbbSerialPorts = new JComboBox();
		cbbSerialPorts.setFont(MyFont.Static);
		cbbSerialPorts.setForeground(Color.black);
		serialPortConnect.add(cbbSerialPorts, "Center");
		btnSerialPortConnect = new JButton("连接");
		btnSerialPortConnect.addMouseListener(this);
		btnSerialPortConnect.setPreferredSize(new Dimension(80, 30));
		btnSerialPortConnect.setForeground(Color.white);
		btnSerialPortConnect.setFont(MyFont.Static);
		btnSerialPortConnect.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		serialPortConnect.add(btnSerialPortConnect, "East");
		
		loadSerialPorts();
		loadLocalhost();
		toggleRS232();
	}

	private void socketConnect() {
		JPanel socketConnect = new JPanel();
		socketConnect.setLayout(new BorderLayout(0, 0));
		socketConnect.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u7F51\u7EDC\u8FDE\u63A5",
				TitledBorder.CENTER, TitledBorder.TOP, MyFont.Static,new Color(0, 0, 0)));

		JPanel socketLeftPanel = new JPanel();
		lblLocalHost = new JLabel("本机IP:");
		lblLocalHost.setFont(MyFont.Static);
		lblLocalHost.setHorizontalAlignment(SwingConstants.CENTER);
		lblPort = new JLabel("端口:");
		lblPort.setFont(MyFont.Static);
		lblPort.setHorizontalAlignment(SwingConstants.CENTER);
		socketLeftPanel.setLayout(new GridLayout(2, 1, 0, 5));
		socketLeftPanel.add(lblLocalHost);
		socketLeftPanel.add(lblPort);

		JPanel socketCenterPanel = new JPanel();
		socketCenterPanel.setLayout(new GridLayout(2, 1, 0, 5));
		tfLocalhost = new JTextField();
		socketCenterPanel.add(tfLocalhost);
		tfLocalhost.setColumns(10);
		tfPort = new JTextField();
		socketCenterPanel.add(tfPort);
		tfPort.setColumns(10);

		JPanel socketRightPanel = new JPanel();
		socketRightPanel.setPreferredSize(new Dimension(80, 50));
		socketRightPanel.setLayout(new GridLayout(2, 1, 0, 5));

		btnClientSocket = new JButton("开启客户端");
		btnClientSocket.addMouseListener(this);
		btnClientSocket.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.lightBlue));
		btnClientSocket.setForeground(Color.white);
		btnClientSocket.setFont(MyFont.Static);
		socketRightPanel.add(btnClientSocket);

		btnServiceSocket = new JButton("开启服务器");
		btnServiceSocket.addMouseListener(this);
		
		btnServiceSocket.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.lightBlue));
		btnServiceSocket.setForeground(Color.white);
		btnServiceSocket.setFont(MyFont.Static);
		socketRightPanel.add(btnServiceSocket);

		socketConnect.add(socketLeftPanel, "West");
		socketConnect.add(socketCenterPanel, "Center");
		socketConnect.add(socketRightPanel, "East");

		topPanel.add(socketConnect, "Center");

	}

	private void aotuGetConnect() {
		JPanel antoConnect = new JPanel();
		antoConnect.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),"\u81EA\u52A8\u83B7\u53D6\u8FDE\u63A5\u4FE1\u606F",
		TitledBorder.CENTER, TitledBorder.TOP, MyFont.Static,new Color(0, 0, 0)));
		topPanel.add(antoConnect, "North");
		btnGetCommSocket = new JButton("获取");

		ButtonGroup bg = new ButtonGroup();
		btnGetCommSocket.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.lightBlue));
		btnGetCommSocket.setForeground(Color.white);
		btnGetCommSocket.setFont(MyFont.Static);
		btnGetCommSocket.addMouseListener(this);
		antoConnect.setLayout(new GridLayout(0, 3, 0, 0));

		rdbtnRS232 = new JRadioButton("RS232");
		rdbtnRS232.addMouseListener(this);
		antoConnect.add(rdbtnRS232);

		rdbtnTCPOrIP = new JRadioButton("TCP/IP");
		rdbtnTCPOrIP.addMouseListener(this);
		
		antoConnect.add(rdbtnTCPOrIP);
		bg.add(rdbtnRS232);
		bg.add(rdbtnTCPOrIP);

		btnGetCommSocket.setPreferredSize(new Dimension(100, 30));

		antoConnect.add(btnGetCommSocket);

	}

	// 初始化搜素条件面板
	public void initGetVersionPanel() {
		footerRightPanel = new JPanel();

		footerRightPanel.setPreferredSize(new Dimension(120, 30));

		bottomPanel.add(footerRightPanel, "East");
		footerRightPanel.setLayout(new GridLayout(0, 2, 5, 0));

		btnSearchVersion = new JButton("查询");
		btnSearchVersion.addMouseListener(this);
		
		footerRightPanel.add(btnSearchVersion);
		btnSearchVersion.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.lightBlue));
		btnSearchVersion.setForeground(Color.white);
		btnSearchVersion.setFont(MyFont.Static);

		btnRefresh = new JButton("刷新");
		btnRefresh.addMouseListener(this);
		
		btnRefresh.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.lightBlue));
		btnRefresh.setForeground(Color.white);
		btnRefresh.setFont(MyFont.Static);
		footerRightPanel.add(btnRefresh);

		footerLeftPanel = new JPanel();

		bottomPanel.add(footerLeftPanel, BorderLayout.WEST);
		footerLeftPanel.setLayout(new BorderLayout(0, 0));

		lblVersoinInfo = new JLabel("版本号 :   ");
		footerLeftPanel.add(lblVersoinInfo, BorderLayout.WEST);
		
		lblVersionNo = new JLabel("");
		lblVersionNo.setFont(new Font("宋体", Font.PLAIN, 14));
		lblVersionNo.setHorizontalAlignment(SwingConstants.CENTER);
		lblVersionNo.setForeground(Color.RED);
		footerLeftPanel.add(lblVersionNo);

	}
	
	private Vector<Vector> vector(){
		Vector<Vector> rows = new Vector<Vector>();
		Vector  list = new Vector();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("4");
		list.add("4");
		rows.add(list);
		return rows;
	}

	// 初始化数据表格面板
	@SuppressWarnings("serial")
	public void initTablePanel() {
		String params[] = {"序号", "IP/COM", "端口", "状态"};
		tableConnectModel = new DefaultTableModel(null, params){
			 public boolean isCellEditable(int row, int column) {
	            return false;
			 }
        };
		table = new JTable(tableConnectModel);
		
		TableColumn secondColumn = table.getColumnModel().getColumn(1);
		secondColumn.setPreferredWidth(100);
		secondColumn.setMaxWidth(100);
		secondColumn.setMinWidth(100);
		
		TableColumn thirdColumn = table.getColumnModel().getColumn(2);
		thirdColumn.setPreferredWidth(60);
		thirdColumn.setMaxWidth(60);
		thirdColumn.setMinWidth(60);
		
		TableStyleTools.setTableStyle(table);
		
		jScrollPane = new JScrollPane(table);
		TableStyleTools.setJspStyle(jScrollPane);
		tablePanel = new JPanel(new BorderLayout());
		tablePanel.setOpaque(false);
		tablePanel.add(jScrollPane);
		backgroundPanel.add(tablePanel, "Center");
	}

	// 更新数据表格
	public void refreshTablePanel() {
		backgroundPanel.remove(tablePanel);
		String params[] = {"id", "IP/COM", "端口", "状态"};
		
		DefaultTableModel dcm = new DefaultTableModel(null,params);// 获取列模型
		
		baseTableModule = new BaseTableModule(params, vector);
		
		table = new JTable(dcm);
		
		//table = new JTable(baseTableModule);
		TableStyleTools.setTableStyle(table);
		
//		DefaultTableColumnModel dcm = (DefaultTableColumnModel) table.getColumnModel();// 获取列模型
//
//		dcm.getColumn(0).setMinWidth(0); // 将第一列的最小宽度、最大宽度都设置为0
//		dcm.getColumn(0).setMaxWidth(0);

		jScrollPane = new JScrollPane(table);
		TableStyleTools.setJspStyle(jScrollPane);

		tablePanel = new JPanel(new BorderLayout());
		tablePanel.setOpaque(false);

		tablePanel.add(jScrollPane);

		backgroundPanel.add(tablePanel, "Center");
		backgroundPanel.validate();
	}

	/*******************************************************************************************************/
	private void loadLocalhost() {
		try {
			String localhost = InetAddress.getLocalHost().getHostAddress();
			tfLocalhost.setText(localhost);
			tfPort.setText("8000");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadSerialPorts() {
		SerialPortService serialPortService = new SerialPortServiceImpl();
		List<String> serialPorts = serialPortService.findSerialPorts();
		cbbSerialPorts.removeAllItems();
		for (String comm : serialPorts) {
			cbbSerialPorts.addItem(comm);
		}
	}

	private void toggleRS232() {
		btnClientSocket.setEnabled(false);
		btnServiceSocket.setEnabled(false);
		rdbtnRS232.setSelected(true);
		btnSerialPortConnect.setEnabled(true);
	}

	private void toggleTCPOrIP() {
		btnSerialPortConnect.setEnabled(false);
		rdbtnTCPOrIP.setSelected(true);
		btnClientSocket.setEnabled(true);
		btnServiceSocket.setEnabled(true);
	}
	
	private void serialPortConnectDevice() {
		String serialPort = cbbSerialPorts.getSelectedItem().toString();
		int baudRate = 115200;
		ReaderService  readerService = new ReaderServiceImpl();
		reader = readerService.connect(serialPort, baudRate);
		if(null != reader){
			btnSerialPortConnect.setText("断开");
			System.out.println(SystemTime.currentTime() + "连接成功!");
			addToList(listConnect, serialPort, baudRate, "连接", 0);
		}else{
			System.out.println(SystemTime.currentTime() + StringOutPutInfo.connectFailure + "通讯错误!");
		}
	}
	
	private void serialPortDisconnectDevice() {
		ReaderService  readerService = new ReaderServiceImpl();
		boolean disconnect = readerService.disconnect(reader);
		if(disconnect){
			btnSerialPortConnect.setText("连接");
			System.out.println(SystemTime.currentTime() + "断开成功!");
			for (int j = 0; j < listConnect.size(); j++) {
				ConnectInfo connectInfo= listConnect.get(j);
				if(reader.host.equals(connectInfo.getDeviceNo())&& reader.host.length() < 5){
					readerService.disconnect(reader);
					reader = null;
					listConnect.remove(j);
					tableConnectModel.removeRow(j);
					break;
				}
			}
		}else{
			System.out.println(SystemTime.currentTime() + StringOutPutInfo.connectFailure + "断开失败!");
		}
	}
	

	
	public static void addToList(final List<ConnectInfo> list, String device,int port, String state, int id) {
		// 第一次读入数据
		if (list.isEmpty()) {
			list.clear();
			ConnectInfo connectInfo = new ConnectInfo();
			connectInfo.setId(list.size() + 1);
			connectInfo.setDeviceNo(device);
			connectInfo.setPort(port);
			connectInfo.setState(state);
			list.add(connectInfo);
		} else {
			boolean falg = false;
			for (int i = 0; i < list.size(); i++) {
				ConnectInfo connectInfo = list.get(i);
				// list中有此EPC
				if (device.equals(connectInfo.getDeviceNo())
						&& device.length() < 6) {
					connectInfo.setId(connectInfo.getId() + 1);
					list.set(i, connectInfo);
					falg = true;
					break;
				}
			}
			if (!falg) {
				// list中没有此epc
				ConnectInfo connect = new ConnectInfo();
				connect.setDeviceNo(device);
				connect.setPort(port);
				connect.setState(state);
				list.add(connect);
			}
		}
		tableConnectModel.setRowCount(0);
		int idcount = 1;
		tableConnectModel.getDataVector().removeAllElements();
		for (ConnectInfo connectInfo : list) {
			Object[] rowValues = { idcount, connectInfo.getDeviceNo(),connectInfo.getPort(), connectInfo.getState(), 0 };
			tableConnectModel.addRow(rowValues);
			idcount++;
		}
	}
	
	private void searchVersion() {
		ReaderService readerService = new ReaderServiceImpl();
		String version = readerService.version(reader);
		lblVersionNo.setText("");
		if(null != version){
			lblVersionNo.setText(version);
		}else{
			
		}
	}
	
	private void clientSocketConnect() {
		ReaderService readerService = new ReaderServiceImpl();
		String portName = tfLocalhost.getText();
		String baudRateStr = tfPort.getText();
		boolean result = Regex.isDecNumber(baudRateStr);
		if(result){
			int baudRate = Integer.parseInt(baudRateStr);
			readerService.connect(portName, baudRate);
		}
	}

	private void serviceSocketConnect() {
		
	}
	
	/*************************************************************** footer event ***********************************************************************/

	// 下拉框改变事件
	@Override
	public void actionPerformed(ActionEvent e) {

	}

	// 鼠标点击事件
	@Override
	public void mouseClicked(MouseEvent e) {
		Object value = e.getSource();
		if (value == btnGetCommSocket) {
			loadSerialPorts();
			loadLocalhost();
		} else if (value == rdbtnRS232) {
			toggleRS232();
		} else if (value == rdbtnTCPOrIP) {
			toggleTCPOrIP();
		} else if(value == btnSerialPortConnect){
			String btnName = btnSerialPortConnect.getText();
			if(btnName.equals("连接")){
				serialPortConnectDevice();
			}else if(btnName.equals("断开")){
				serialPortDisconnectDevice();
			}
		}else if(value == btnRefresh){
			lblVersionNo.setText("");
		}else if(value == btnSearchVersion){
			searchVersion();
		}else if(value == btnServiceSocket){
			serviceSocketConnect();
		}else if(value == btnClientSocket){
			clientSocketConnect();
		}
	}

	// 鼠标划入事件
	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == tool_add) {
			tool_add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (e.getSource() == tool_modify) {
			tool_modify.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else if (e.getSource() == tool_delete) {
			tool_delete.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

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
	public void changedUpdate(DocumentEvent e) {

	}

	// 文本框插入内容事件
	@Override
	public void insertUpdate(DocumentEvent e) {
		//refreshTablePanel();
	}

	// 文本框删除内容事件
	@Override
	public void removeUpdate(DocumentEvent e) {
		//refreshTablePanel();
	}

}
