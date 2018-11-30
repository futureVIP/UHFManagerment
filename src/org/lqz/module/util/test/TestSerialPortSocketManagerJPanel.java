package org.lqz.module.util.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.lqz.module.view.SerialPortSocketManagerJPanel;
import java.awt.BorderLayout;
import java.awt.Toolkit;

public class TestSerialPortSocketManagerJPanel extends JFrame {

	// 获得屏幕的大小
	final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;

	public TestSerialPortSocketManagerJPanel() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(new SerialPortSocketManagerJPanel());
		// 设置tab面板缩进
		UIManager.put("TabbedPane.tabAreaInsets",new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0));
		this.setTitle("捷通科技RFID_demo");
		this.setSize(445, 597);
		this.setLocationRelativeTo(null); // 此窗口将置于屏幕的中央。
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String manager = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		//manager = "com.sun.java.swing.plaf.nimbus.AbstractRegionPainter";
		try {
			UIManager.setLookAndFeel(manager);
		} catch (Exception e) {
			e.printStackTrace();
		}
		new TestSerialPortSocketManagerJPanel();
	}
}
