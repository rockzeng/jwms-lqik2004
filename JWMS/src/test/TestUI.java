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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import method.*;

/**
 *
 * @author res0w
 * @since 2009-12-1
 *
 */
public class TestUI extends JFrame {
    Object[] items = null;
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 647;
    private JComboBox storeComboBox = new JComboBox();
    private static int exceptionTag = 0;  //异常标记，比如如果没有正确写入信息到数据库就会改写exceptionTag
    ///////////////////////////////////////////////
    private TableUI table;
    private DataSetUtil dsu = new DataSetUtil();
    private DateUI du = new DateUtil();
    private GroupButtonUI gb = new GroupButtonUI("销售", "退货");
    private IdUI idu=new IDMakeUtil();

    public static void setExTag(int tag) {
        exceptionTag = tag;
    }

    public TestUI() throws Exception {
        //初始化数据库，读入信息
        for (int i = 0; i < dsu.storeLoad().size(); i++) {
            storeComboBox.addItem(dsu.storeLoad().get(i));
        }
        items = dsu.infoLoad();//读入info信息
        setTitle("销售退货单");//设置标题栏名称
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);//设置大小
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
        //从properties中读取仓库设置
        storeComboBox.setSelectedIndex(Integer.parseInt(propertiesRW.proIDMakeRead("storeSell")));
        storeComboBox.setMaximumSize(storeComboBox.getPreferredSize());
        storeComboBox.setEditable(false);   //仓库不可直接修改

        Box hbox2 = Box.createHorizontalBox();
        hbox2.add(Box.createHorizontalStrut(5));
        hbox2.add(labelStore);
        hbox2.add(storeComboBox);
        hbox2.add(Box.createHorizontalGlue());
        //加入列表栏
        table = new TableUtil(items, storeComboBox.getSelectedItem().toString().trim());
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
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox2);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox3);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(hbox4);
        vbox.add(Box.createVerticalStrut(5));
        add(vbox, BorderLayout.CENTER);
        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        storeComboBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                table.setSelectStore(storeComboBox.getSelectedItem().toString().trim());
                //当此时数据表中有数据时最好给予提示
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
            sellBt.setStore(storeComboBox.getSelectedItem().toString());
            sellBt.setDate(du.getSelectionDate());
            sellBt.setID(idu.setGetID("S", du.getSelectionYear(),
                    du.getSelectionMonth(), du.getSelectionDay()));
            //把ID号写入文件中
            for (int i = 0; i < table.getModel().getRowCount(); i++) {     //防止出现中间出现断行丢失数据的问题
                if (table.getModel().getValueAt(i, 1).toString() != "") {  //如果字符串没有，那么此行写入数据库，继续下一行
                    sellBt.setNum(table.getModel().getValueAt(i, 0).toString());
                    sellBt.setInfo(table.getModel().getValueAt(i, 1).toString());
                    sellBt.setAmount(table.getModel().getValueAt(i, 2).toString());
                    sellBt.setOutPrice(table.getModel().getValueAt(i, 3).toString());
                    sellBt.setSellORreturn((short) gb.getSelectIndex());
                    //根据单选按钮的信息来选择使用哪个方法
                    if (gb.getSelectIndex() == 1) {
                        sellBt.transmitSell();
                    } else if (gb.getSelectIndex() == 2) {
                        sellBt.transmitReturn();
                    }
                }
            }
            try {
                propertiesRW.proIDMakeWrite("storeSell", storeComboBox.getSelectedIndex());
            } catch (IOException ex) {
                Logger.getLogger(TestUI.class.getName()).log(Level.SEVERE, null, ex);
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

