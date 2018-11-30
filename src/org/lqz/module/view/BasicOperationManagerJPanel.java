package org.lqz.module.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableColumnModel;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.lqz.framework.util.BaseTableModule;
import org.lqz.framework.util.Item;
import org.lqz.framework.util.MyFont;
import org.lqz.framework.util.TableStyleTools;
import org.lqz.module.entity.User;
import org.lqz.module.services.Impl.CategoryServiceImpl;
import org.lqz.module.services.Impl.SaleOrderServiceImpl;
import org.lqz.module.services.Impl.WarehouseServiceImpl;
import org.lqz.module.view.old.AddSaleOrderJFrame;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Font;

public class BasicOperationManagerJPanel implements ActionListener, MouseListener, DocumentListener {

	// 定义全局组件
	JPanel backgroundPanel, topPanel, headPanel, searchPanel, tablePanel;
	BaseTableModule baseTableModule;
	JTable table;
	JScrollPane jScrollPane;

	// 用户对象
	User user;
	private JPanel bottomPanel;
	private JButton btnInvOnce;
	private JButton btnStart;
	private JButton btnStop;
	private JButton btnClear;
	private JLabel lblButtonRightNull;
	private JLabel lblButtonLeftNull;
	private JPanel headLeftPanel;
	private JPanel cmdExcuteSpeedPanel;
	private JLabel lblKeep;

	public BasicOperationManagerJPanel(User user) {
		this.user = user;

		backgroundPanel = new JPanel(new BorderLayout());
		initTopPanel();
		initTablePanel();
	}

	// 初始化顶部面板
	public void initTopPanel() {
		topPanel = new JPanel(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(100,30));
		initToolPanel();
		initSearchPanel();

		backgroundPanel.add(topPanel, "North");
	}

	// 初始化工具面板
	public void initToolPanel() {
		headPanel = new JPanel();
		headPanel.setPreferredSize(new Dimension(800,30));
		// 工具图标
		Icon icon_add = new ImageIcon("image/add.png");
		topPanel.add(headPanel, "West");
		headPanel.setLayout(new BorderLayout(0, 0));
		
		headLeftPanel = new JPanel();
		headPanel.add(headLeftPanel,"West");
		headLeftPanel.setLayout(new BorderLayout(0, 0));
		
		lblKeep = new JLabel("预留");
		lblKeep.setHorizontalAlignment(SwingConstants.CENTER);
		headLeftPanel.add(lblKeep);
	}

	// 初始化搜素条件面板
	public void initSearchPanel() {

		searchPanel = new JPanel();
		CategoryServiceImpl categoryService = new CategoryServiceImpl();
		List<Object[]> list_category = null;
		try {
			list_category = categoryService.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list_category != null) {
			for (Object[] object : list_category) {
				//select_category.addItem(new Item((String) object[0], (String) object[1]));
			}
		}
		WarehouseServiceImpl warehouseService = new WarehouseServiceImpl();
		List<Object[]> list_warehouse = null;
		try {
			list_warehouse = warehouseService.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list_warehouse != null) {
			for (Object[] object : list_warehouse) {
				//select_warehouse.addItem(new Item((String) object[0], (String) object[1]));
			}
		}

		topPanel.add(searchPanel, "East");
		searchPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		cmdExcuteSpeedPanel = new JPanel();
		searchPanel.add(cmdExcuteSpeedPanel);
		cmdExcuteSpeedPanel.setLayout(new BorderLayout(0, 0));
	}

	// 初始化数据表格面板
	public void initTablePanel() {

		String conditionParams[] = { "", "全部", "全部" };
		String params[] = {"序号id", "序号", "EPC", "次数", "Rssi", "天线号", "设备号", "IP/COM"};
		
		//SaleOrderService saleOrderService = new SaleOrderServiceImpl();
		Vector<Vector> vector = new Vector<Vector>();
		try {
			//vector = saleOrderService.selectByCondition(conditionParams);
		} catch (Exception e) {
			e.printStackTrace();
		}

		baseTableModule = new BaseTableModule(params, vector);

		table = new JTable(baseTableModule);
		TableStyleTools.setTableStyle(table);
		DefaultTableColumnModel dcm = (DefaultTableColumnModel) table.getColumnModel();// 获取列模型
		dcm.getColumn(0).setMinWidth(0); // 将第一列的最小宽度、最大宽度都设置为0
		dcm.getColumn(0).setMaxWidth(0);

		jScrollPane = new JScrollPane(table);
		TableStyleTools.setJspStyle(jScrollPane);

		tablePanel = new JPanel(new BorderLayout());
		tablePanel.setOpaque(false);

		tablePanel.add(jScrollPane);

		backgroundPanel.add(tablePanel, "Center");
		
		bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(100,40));
		
		backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new GridLayout(1, 4, 15, 0));
		
		lblButtonLeftNull = new JLabel("");
		bottomPanel.add(lblButtonLeftNull);
		
		btnInvOnce = new JButton("寻卡一次");
		btnInvOnce.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		btnInvOnce.setForeground(Color.white);
		btnInvOnce.setFont(MyFont.Static);
		
		bottomPanel.add(btnInvOnce);
		
		btnStart = new JButton("连续读卡");
		btnStart.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		btnStart.setForeground(Color.white);
		btnStart.setFont(MyFont.Static);
		bottomPanel.add(btnStart);
		
		btnStop = new JButton("停止");
		btnStop.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		btnStop.setForeground(Color.white);
		btnStop.setFont(MyFont.Static);
		bottomPanel.add(btnStop);
		
		btnClear = new JButton("清空");
		btnClear.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		btnClear.setForeground(Color.white);
		btnClear.setFont(MyFont.Static);
		bottomPanel.add(btnClear);
		
		lblButtonRightNull = new JLabel("");
		bottomPanel.add(lblButtonRightNull);
	}

	// 更新数据表格
	public void refreshTablePanel() {

		backgroundPanel.remove(tablePanel);
		//Item item_category = (Item) select_category.getSelectedItem();
		//Item item_warehouse = (Item) select_warehouse.getSelectedItem();
		String params[] = {"序号id", "EPC", "次数", "Rssi", "天线号", "设备号", "IP/COM" };
		SaleOrderServiceImpl saleOrderService = new SaleOrderServiceImpl();
		Vector<Vector> vector = new Vector<Vector>();
		try {
			//vector = saleOrderService.selectByCondition(conditionParams);
		} catch (Exception e) {
			e.printStackTrace();
		}

		baseTableModule = new BaseTableModule(params, vector);

		table = new JTable(baseTableModule);
		TableStyleTools.setTableStyle(table);
		DefaultTableColumnModel dcm = (DefaultTableColumnModel) table.getColumnModel();// 获取列模型
		dcm.getColumn(0).setMinWidth(0); // 将第一列的最小宽度、最大宽度都设置为0
		dcm.getColumn(0).setMaxWidth(0);

		jScrollPane = new JScrollPane(table);
		TableStyleTools.setJspStyle(jScrollPane);

		tablePanel = new JPanel(new BorderLayout());
		tablePanel.setOpaque(false);

		tablePanel.add(jScrollPane);

		backgroundPanel.add(tablePanel, "Center");
		backgroundPanel.validate();
	}

	// 下拉框改变事件
	@Override
	public void actionPerformed(ActionEvent e) {

	}

	// 鼠标点击事件
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	// 鼠标划入事件
	@Override
	public void mouseEntered(MouseEvent e) {
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
		refreshTablePanel();
	}

	// 文本框删除内容事件
	@Override
	public void removeUpdate(DocumentEvent e) {
		refreshTablePanel();
	}

}
