package test;

import jwms.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import method.*;
import method.inputIDMake;

/**
 *
 * @author lqik2004
 */
public class TestUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        sellFrame frame = new sellFrame();
        frame.setLocationRelativeTo(null);//一句让窗口居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class sellFrame extends JFrame {

    private Object[] Objyear = {
        "2009", "2010", "2011", "2012"
    };
    private Object[] Objmonth = {
        "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
    };
    private Object[] Objday = {
        "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    };
    private short sellORreturn = 1;
    private int tagrow = 0;
    int sumvalues = 0;
    float sumprice = 0;
    Object[] items = null;
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 647;
    private JTextField ID = new JTextField(12);//x20090330***  共12位
    private JComboBox year = new JComboBox(Objyear);//增加了自动选择时间功能
    private JComboBox month = new JComboBox(Objmonth);
    private JComboBox day = new JComboBox(Objday);
    private JComboBox storeComboBox = new JComboBox();
    private static int exceptionTag = 0;  //异常标记，比如如果没有正确写入信息到数据库就会改写exceptionTag
    private TableUtil table;
    ///////////////////////////////////////////////
    private DataSetUtil dsu = new DataSetUtil();

    public static void setExTag(int tag) {
        exceptionTag = tag;
    }

    public sellFrame() throws Exception {
        //初始化数据库，读入信息
        for(int i=0;i<dsu.storeLoad().size();i++){
             storeComboBox.addItem(dsu.storeLoad().get(i));
        }
       //读入仓库信息
        items = dsu.infoLoad();//读入info信息
        setTitle("销售退货单");//设置标题栏名称
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);//设置大小
        //设置单选按钮
        //同时设置了sellORreturn的值，sell->0,return->1
        ButtonGroup group = new ButtonGroup();//设置按钮组，保证只能单选
        JRadioButton sell = new JRadioButton("销售", true);
        sell.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sellORreturn = 1;
                //System.out.println(sellORreturn);
            }
        });
        JRadioButton Return = new JRadioButton("退货", false);
        Return.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sellORreturn = -1;
                // System.out.println(sellORreturn);
            }
        });
        group.add(sell);
        group.add(Return);
        //设置ID
        JLabel labelID = new JLabel("编号：");//设置文字
        ID.setEditable(false);//不可修改        
//        inputIDMake id=new inputIDMake();
//        IDString=new inputIDMake().showID("I", getDate.getYear(), getDate.getMonth(), getDate.getDay())
        ID.setText(new inputIDMake().showID("S", getDate.getYear(), getDate.getMonth(), getDate.getDay()));   //设置编号，销售单以S开头，这里可能有问题
        ID.setMaximumSize(ID.getPreferredSize());   //使在箱式布局下不会默认取得最大值，保持预定义大小
        Box hbox0 = Box.createHorizontalBox();
        hbox0.add(sell);
        hbox0.add(Return);
        hbox0.add(Box.createHorizontalGlue());
        hbox0.add(labelID);
        hbox0.add(ID);
        hbox0.add(Box.createHorizontalStrut(5));
        //设置日期栏
        JLabel label1 = new JLabel("日期：");
        year.setSelectedIndex(getDate.yearIndex());
        JLabel label2 = new JLabel("年");
        //JTextField month = new JTextField(2);
        month.setSelectedIndex(getDate.monthIndex());
        JLabel label3 = new JLabel("月");
        //JTextField day = new JTextField(2);
        day.setSelectedIndex(getDate.dayIndex());
        JLabel label4 = new JLabel("日");
        year.setMaximumSize(year.getPreferredSize());
        month.setMaximumSize(month.getPreferredSize());
        day.setMaximumSize(day.getPreferredSize());
        Box hbox1 = Box.createHorizontalBox();
        hbox1.add(Box.createHorizontalStrut(5));
        hbox1.add(label1);
        hbox1.add(year);
        hbox1.add(label2);
        hbox1.add(month);
        hbox1.add(label3);
        hbox1.add(day);
        hbox1.add(label4);
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
        vbox.add(Box.createVerticalStrut(6));
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
                exceptionTag = 0;//对异常标签进行初始化
                //按下提交按钮后会出现一个对话框用来确认是否进行提交
                //shwoConfirmDialog,返回一个整型值，判定选择的是那个按钮
                int ifcontinue = JOptionPane.showConfirmDialog(null, "请确认单据过账", "单据确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (ifcontinue == JOptionPane.YES_OPTION) {
                    sell2Main sellBt = new sell2Main();//定义一个新的对象，用以传输数据；
                    inputIDMake idmk = new inputIDMake();//在提交的时候把ID记录进timeLine？？？？？？？？？？
                    sellBt.setYear(year.getSelectedItem().toString());
                    idmk.getYear(year.getSelectedItem().toString().trim());
                    sellBt.setMonth(month.getSelectedItem().toString());
                    idmk.getMonth(month.getSelectedItem().toString().trim());
                    sellBt.setDay(day.getSelectedItem().toString());
                    idmk.getDay(day.getSelectedItem().toString().trim());
                    sellBt.setStore(storeComboBox.getSelectedItem().toString());
                    sellBt.setDate(idmk.showDate());
//                    System.out.println(idmk.showDate());
                    sellBt.setID(idmk.alterID("S"));
                    //把ID号写入文件中
                    for (int i = 0; i < table.model.getRowCount(); i++) {     //防止出现中间出现断行丢失数据的问题
                        if (table.model.getValueAt(i, 1).toString() != "") {  //如果字符串没有，那么此行写入数据库，继续下一行
                            sellBt.setNum(table.model.getValueAt(i, 0).toString());
                            sellBt.setInfo(table.model.getValueAt(i, 1).toString());
                            sellBt.setAmount(table.model.getValueAt(i, 2).toString());
                            sellBt.setOutPrice(table.model.getValueAt(i, 3).toString());
                            sellBt.setSellORreturn(sellORreturn);
                            //sellBt.test();
                            //根据单选按钮的信息来选择使用哪个方法
                            if (sellORreturn == 1) {
                                sellBt.transmitSell();
                            } else if (sellORreturn == -1) {
                                sellBt.transmitReturn();
                            }
                        }
                    }
                    try {
                        propertiesRW.proIDMakeWrite("storeSell", storeComboBox.getSelectedIndex());
                    } catch (IOException ex) {
                        Logger.getLogger(sellFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //如果没有发生异常，那么关闭窗口。异常信息的来源是sell2Main.java，如果写入数据库抛出异常，tag==1;
                    if (exceptionTag == 0) {
                        dispose();
                    }
                }
            }
        });
    }
}

