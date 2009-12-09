/*
# JWMS2 is based on JWMS.JWMS is short for JeanWest store-sell Management System
# Copyright (C) 2009,res0w
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import jwms.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import method.*;

/**
 *
 * @author res0w
 * @since 2009-12-1
 * @version 0.5
 *
 */
public class TestUI extends JFrame {

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 647;
    private static int exceptionTag = 0;  //异常标记，比如如果没有正确写入信息到数据库就会改写exceptionTag
    ///////////////////////////////////////////////
    //表格UI相关接口
    private TableUIer table;
    //时间UI相关接口
    private DateUIer du = new DateUtil();
    //选择组UI相关接口
    private GroupButtonUI gb = new GroupButtonUI("销售", "退货");
    //ID标签UI相关接口
    private IdUIer idu = new IDMakeUtil();
    //仓库UI相关接口
    private StoreUIer sui = new StoreUtilSet();

    public static void setExTag(int tag) {
        exceptionTag = tag;
    }

    public TestUI() throws Exception {
        setTitle("销售退货单");//设置标题栏名称
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);//设置大小

        //设置按钮组和ID
        Box hbox0 = Box.createHorizontalBox();
        hbox0.add(gb.getPanel());
        hbox0.add(Box.createHorizontalGlue());
        hbox0.add(idu.IdUI("S"));
        hbox0.add(Box.createHorizontalStrut(5));

        //设置日期栏
        Box hbox1 = Box.createHorizontalBox();
        hbox1.add(Box.createHorizontalStrut(4));
        hbox1.add(du.DateSelectionUI());
        hbox1.add(Box.createHorizontalGlue());

        //设置仓库栏
        JLabel labelStore = new JLabel("仓库：");
        Box hbox2 = Box.createHorizontalBox();
        hbox2.add(Box.createHorizontalStrut(5));
        hbox2.add(labelStore);
        hbox2.add(sui.StorePanel("storeSell"));
        hbox2.add(Box.createHorizontalGlue());

        //加入列表栏
        table = new TableUtil(sui.getSelectItem().toString().trim());
        Box hbox3 = Box.createHorizontalBox();
        hbox3.add(table.getPanel());

        //设置提交按钮
        JButton referButton = new JButton("提交");
        
        JButton exit = new JButton("关闭");
        Box hbox4 = Box.createHorizontalBox();
        hbox4.add(Box.createHorizontalGlue());
        hbox4.add(referButton);
        hbox4.add(Box.createHorizontalStrut(50));
        hbox4.add(exit);
        hbox4.add(Box.createHorizontalStrut(15));

        //设置垂直箱式布局
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(8));
        vbox.add(hbox0);
        vbox.add(Box.createVerticalStrut(4));
        vbox.add(hbox1);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(hbox2);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(hbox3);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(hbox4);
        vbox.add(Box.createVerticalStrut(5));
        add(vbox, BorderLayout.CENTER);

        //开启选择仓库改变tableModel功能
        sui.tableModelCHGAction(table);

        //开启选择时间改变ID编号功能
        du.idCHGAction("S", idu);

        //退出按钮功能
        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        //提交按钮设计
        referButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                referFunction();
            }
        });
    }

    private void referFunction() {
        exceptionTag = 0;//对异常标签进行初始化
        //按下提交按钮后会出现一个对话框用来确认是否进行提交
        //shwoConfirmDialog,返回一个整型值，判定选择的是那个按钮
        int ifcontinue = JOptionPane.showConfirmDialog(null, "请确认单据过账", "单据确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (ifcontinue == JOptionPane.YES_OPTION) {
            sell2Main sellBt = new sell2Main();//定义一个新的对象，用以传输数据；
            sellBt.setYear(du.getSelectionYear());
            sellBt.setMonth(du.getSelectionMonth());
            sellBt.setDay(du.getSelectionDay());
            sellBt.setDate(du.getSelectionDate());
            sellBt.setStore(sui.getSelectItem().toString());
            sellBt.setID(idu.getIdString());
            sellBt.setTableModel(table.getModel());
            sellBt.setSellORreturn((short) gb.getSelectIndex());
            sellBt.autoRefer();
            //把当前选择仓库写入文件，以便下次调用。
            try {
                propertiesRW.proIDMakeWrite("storeSell", sui.getSelectIndex());
            } catch (IOException ex) {
                Logger.getLogger(TestUI.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "文件写入错误！");
            }
            //如果没有发生异常，那么关闭窗口。异常信息的来源是sell2Main.java，如果写入数据库抛出异常，tag==1;
            if (exceptionTag == 0) {
                dispose();
            }
        }
    }

    /**
     * 测试类
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        TestUI frame = new TestUI();
        frame.setLocationRelativeTo(null);//一句让窗口居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

