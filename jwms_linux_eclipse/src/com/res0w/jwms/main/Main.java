package com.res0w.jwms.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.res0w.jwms.method.PropertiesRW;

/**
 * 
 * @author lqik2004
 */
public class Main {

	/**
	 * @param args
	 *            the command line arguments
	 */
	static mainFrame frame = new mainFrame();

	public static void main(String[] args) {
		// TODO code application logic here

		frame.setSize(500, 120);
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension screenSize = tool.getScreenSize();
		int locateWidth = (screenSize.width - frame.getWidth()) / 2;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("真维斯");
		frame.setLocation(locateWidth, 0);
		frame.setUndecorated(true);
		frame.setVisible(true);

	}

	public static Point frameLocateOnScr() {
		return frame.getLocationOnScreen();
	}
}

class mainFrame extends JFrame {

	private int dialogTag = 0;
	private int setupTag = 0;
	private JButton setup = new JButton();

	public mainFrame() {
		//对系统进行检查
		try {
			if (System.getProperty("os.name").toUpperCase().equals("LINUX")) {
				PropertiesRW.proIDMakeWrite("osname", "/");
				System.out.println(System.getProperty("os.name").toUpperCase());
			} else {
				PropertiesRW.proIDMakeWrite("osname", "\\");
			}
			
		} catch (IOException e1) {
			// TODO
		}
		final String fileURL;
		fileURL = PropertiesRW.proIDMakeRead("osname");
		System.out.println(fileURL);
		final aboutDialog aboutDialog = new aboutDialog();
		final setupDialog setupDialog = new setupDialog();
		JButton sell = new JButton("销售/退货单");
		JButton input = new JButton("进货/退货单");
		JButton equal = new JButton("同价调拨单");
		JButton lose = new JButton("报损报益单");
		JButton stream = new JButton("经营历程");
		JButton search = new JButton("综合查询");
		JButton storeRemain = new JButton("库存管理");

		JButton exit = new JButton("退出");
		JButton about = new JButton("关于");

		setup.setIcon(new ImageIcon("image" + fileURL + "setupico.jpg"));
		// setup.setBorderPainted();
		setup.setPreferredSize(new Dimension(32, 32));
		setup.setMaximumSize(setup.getPreferredSize());
		setup.setMinimumSize(setup.getPreferredSize());
		String font = "JeansWest";
		JLabel label = new JLabel(new ImageIcon("image" + fileURL
				+ "mainLogo.jpg"));
		label.setPreferredSize(new Dimension(203, 49));
		JLabel label2 = new JLabel("测试版");
		label2.setFont(new Font("宋体", Font.ITALIC, 10));
		Box hbox0 = Box.createHorizontalBox();
		hbox0.add(Box.createHorizontalStrut(5));
		hbox0.add(label);
		hbox0.add(Box.createGlue());
		hbox0.add(label2);
		Box hbox1 = Box.createHorizontalBox();
		hbox1.add(Box.createHorizontalStrut(5));
		hbox1.add(sell);
		hbox1.add(Box.createHorizontalStrut(5));
		hbox1.add(input);
		hbox1.add(Box.createHorizontalStrut(5));
		hbox1.add(equal);
		hbox1.add(Box.createHorizontalStrut(5));
		hbox1.add(lose);

		Box hbox2 = Box.createHorizontalBox();
		// hbox2.add(Box.createHorizontalStrut(5));
		hbox2.add(setup);
		hbox2.add(Box.createHorizontalStrut(55));
		hbox2.add(stream);
		hbox2.add(Box.createHorizontalStrut(5));
		hbox2.add(search);
		hbox2.add(Box.createHorizontalStrut(5));
		hbox2.add(storeRemain);
		hbox2.add(Box.createHorizontalStrut(50));

		Box vb0 = Box.createVerticalBox();
		vb0.add(Box.createVerticalStrut(5));
		vb0.add(hbox0);
		vb0.add(Box.createVerticalStrut(2));
		vb0.add(hbox1);
		vb0.add(Box.createVerticalStrut(2));
		vb0.add(hbox2);

		Box vb1 = Box.createVerticalBox();
		vb1.add(exit);
		vb1.add(Box.createVerticalStrut(15));
		vb1.add(about);

		Box hbox3 = Box.createHorizontalBox();
		hbox3.add(vb0);
		hbox3.add(Box.createGlue());
		hbox3.add(vb1);
		hbox3.add(Box.createHorizontalStrut(5));

		add(hbox3, BorderLayout.CENTER);

		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int ifcontinue = JOptionPane
						.showConfirmDialog(null,
								"请确认所有的录入窗口已经关闭，如果继续本程序将退出，所有未过账的单据不会保存！",
								"退出确认", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (ifcontinue == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				throw new UnsupportedOperationException("Not supported yet.");
			}
		});
		sell.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				sellFrame frame = null;
				try {
					frame = new sellFrame();
				} catch (Exception ex) {
					Logger.getLogger(mainFrame.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				frame.setLocationRelativeTo(null);// 一句让窗口居中
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		input.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					inputFrame frame = new inputFrame();
					/**
					 * Toolkit tool = Toolkit.getDefaultToolkit(); Dimension
					 * screenSize = tool.getScreenSize(); int locateHeight =
					 * (screenSize.height - frame.getHeight()) / 2; int
					 * locateWidth = (screenSize.width - frame.getWidth()) / 2;
					 * frame.setLocation(locateWidth, locateHeight);
					 */
					frame.setLocationRelativeTo(null); // 一句让窗口居中
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				} catch (Exception ex) {
					Logger.getLogger(mainFrame.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		});
		equal.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					equalFrame frame = new equalFrame();
					/**
					 * Toolkit tool = Toolkit.getDefaultToolkit(); Dimension
					 * screenSize = tool.getScreenSize(); int locateHeight =
					 * (screenSize.height - frame.getHeight()) / 2; int
					 * locateWidth = (screenSize.width - frame.getWidth()) / 2;
					 * frame.setLocation(locateWidth, locateHeight);
					 */
					frame.setLocationRelativeTo(null); // 一句让窗口居中
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				} catch (Exception ex) {
					Logger.getLogger(mainFrame.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		});
		lose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loseFrame frame = new loseFrame();
					frame.setLocationRelativeTo(null); // 一句让窗口居中
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				} catch (Exception ex) {
					Logger.getLogger(mainFrame.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		});
		stream.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new WorkingStream();
			}
		});
		search.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new Search();
			}
		});
		storeRemain.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				RemainText.main(null);
			}
		});
		about.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (dialogTag == 0) {
					Point point = Main.frameLocateOnScr();
					aboutDialog.setTitle("关于");
					aboutDialog.setSize(200, 90);
					aboutDialog
							.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					aboutDialog.setLocation(point.x + 500, point.y + 30);// 设置窗口停靠，自动生成在主窗口左侧
					aboutDialog.setUndecorated(true);// 隐藏标题栏
					dialogTag = 1;
				}
				if (aboutDialog.isVisible()) {
					aboutDialog.setVisible(false);
				} else if (!aboutDialog.isVisible()) {
					aboutDialog.setVisible(true);
				}
			}
		});
		setup.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (setupTag == 0) {
					Point point = Main.frameLocateOnScr();
					setupDialog.setTitle("设置");
					setupDialog.setSize(140, 70);
					setupDialog
							.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					setupDialog.setLocation(point.x, point.y + 120);// 设置窗口停靠，自动生成在主窗口下方
					setupDialog.setUndecorated(true);// 隐藏标题栏
					setupTag = 1;
				}
				if (setupDialog.isVisible()) {
					setupDialog.setVisible(false);
					// setup.setEnabled(true);
					setup.setIcon(new ImageIcon("image" + fileURL
							+ "setupico.jpg"));
				} else if (!setupDialog.isVisible()) {
					setupDialog.setVisible(true);
					// setup.setEnabled(false);
					setup.setIcon(new ImageIcon("image" + fileURL
							+ "setupico_down.gif"));
				}
			}
		});
	}
}