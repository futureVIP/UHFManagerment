package org.lqz.main;

import javax.swing.UIManager;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.lqz.module.view.IndexJFrame;

/**
 * 程序一开始执行的类
 * 
 * @author Administrator
 * 
 */
public class Entrance {

	public static void main(String[] args) {
		try {
			// 设置窗口边框样式
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
			//org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			// 隐藏设置按钮
			UIManager.put("RootPane.setupButtonVisible", false);
			
			String manager = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
			//manager = "com.sun.java.swing.plaf.nimbus.AbstractRegionPainter";
			UIManager.setLookAndFeel(manager);
		} catch (Exception e) {
			
		}
		// 初始化登陆窗口
		new IndexJFrame();
	}
}

