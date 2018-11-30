package org.lqz.module.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import org.lqz.module.services.Impl.StockOrderServiceImpl;
import org.lqz.module.services.Impl.WarehouseServiceImpl;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

public class CountTimerManagerJPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// 定义全局组件
	JPanel backgroundPanel, topPanel, toolPanel, footerRightPanel, tablePanel;
	//private static final String INITIAL_LABEL_TEXT = "00:00:00";
	private static final String INITIAL_LABEL_TEXT = "00:00:00 000";

	// 计数线程
	private CountingThread thread = new CountingThread();

	// 记录程序开始时间
	private long programStart = System.currentTimeMillis();

	// 程序一开始就是暂停的
	private long pauseStart = programStart;

	// 程序暂停的总时间
	private long pauseCount = 0;

	private JLabel label = new JLabel(INITIAL_LABEL_TEXT);

	private JButton startPauseButton = new JButton("开始");

	private JButton resetButton = new JButton("清零");

	public CountTimerManagerJPanel() {
		setLayout(new BorderLayout(0, 0));
		backgroundPanel = new JPanel();
		backgroundPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		
	    //setupBorder();  
	    //setupLabel();  
		
	    setupButtonsPanel();  
        
        thread.start(); // 计数线程一直就运行着  
		
		add(backgroundPanel);
		backgroundPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel CountTimerPanel = new JPanel();
		backgroundPanel.add(CountTimerPanel);
		CountTimerPanel.setLayout(new BorderLayout(0, 0));
		CountTimerPanel.add(label);
		label.setForeground(Color.RED);
		
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setFont(new Font("宋体", Font.PLAIN, 26));
		
		JPanel ControlTimerPanel = new JPanel();
		backgroundPanel.add(ControlTimerPanel);
		ControlTimerPanel.setLayout(new GridLayout(0, 2, 0, 0));
		ControlTimerPanel.add(startPauseButton);
		ControlTimerPanel.add(resetButton);
		resetButton.addActionListener(resetButtonListener);  
		
		startPauseButton.addActionListener(startPauseButtonListener);  
	}
	
	private ActionListener startPauseButtonListener = new ActionListener() {  
        public void actionPerformed(ActionEvent e) {  
            if (thread.stopped) {  
                pauseCount += (System.currentTimeMillis() - pauseStart);  
                thread.stopped = false;  
                startPauseButton.setText("暂停");  
            } else {  
                pauseStart = System.currentTimeMillis();  
                thread.stopped = true;  
                startPauseButton.setText("继续");  
            }  
        }  
    };  
   
    private ActionListener resetButtonListener = new ActionListener() {  
        public void actionPerformed(ActionEvent e) {  
            pauseStart = programStart;  
            pauseCount = 0;  
            thread.stopped = true;  
            label.setText(INITIAL_LABEL_TEXT);  
            startPauseButton.setText("开始");  
        }  
    }; 
    
    
    // 为窗体面板添加边框  
    private void setupBorder() {  
//    	setLayout(new BorderLayout(0, 0));
//        JPanel contentPane = new JPanel(new BorderLayout());  
//        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  
//        //this.setContentPane(contentPane);  
//        add(contentPane);
    }  
   
    // 配置按钮  
    private void setupButtonsPanel() {
    }  
   
    // 配置标签  
    private void setupLabel() {  
        label.setHorizontalAlignment(SwingConstants.CENTER);  
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 40));  
        this.add(label, BorderLayout.CENTER);  
    }  

	private class CountingThread extends Thread {

		public boolean stopped = true;

		private CountingThread() {
			setDaemon(true);
		}

		@Override
		public void run() {
			while (true) {
				if (!stopped) {
					long elapsed = System.currentTimeMillis() - programStart
							- pauseCount;
					label.setText(format(elapsed));
				}

				try {
					sleep(1); // 1毫秒更新一次显示
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}

		// 将毫秒数格式化
		private String format(long elapsed) {
			int hour, minute, second, milli;

			milli = (int) (elapsed % 1000);
			elapsed = elapsed / 1000;

			second = (int) (elapsed % 60);
			elapsed = elapsed / 60;

			minute = (int) (elapsed % 60);
			elapsed = elapsed / 60;

			hour = (int) (elapsed % 60);

			//return String.format("%02d:%02d:%02d", hour, minute, second);
		    return String.format("%02d:%02d:%02d %03d", hour, minute, second,milli);
		}
	}
}
