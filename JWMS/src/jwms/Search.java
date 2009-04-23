package jwms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.io.*;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;
import javax.imageio.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import method.dbOperation;
import method.getDate;
import method.propertiesRW;


/**
 *
 * @author lqik2004
 */
public class Search {

    static searchFrame frame = new searchFrame();

    public Search() {

    frame.setLocationRelativeTo (null);//一句让窗口居中

    frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);

    frame.setVisible (true);
    }
    public static Point frameLocateOnScr() {
        return frame.getLocationOnScreen();
    }
}
class searchFrame extends JFrame {

    private Object[] Objyear = {
        "2009", "2010", "2011", "2012"
    };
    private Object[] Objmonth = {
        "01", "02", "03", "04", "05", "06", "07", "09", "10", "11", "12"
    };
    private Object[] Objday = {
        "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    };
    private Object[] type = {"全部类型", "销售单", "进货单", "同价调拨单", "报损单", "报益单", "销售退货单", "进货退货单", "更多组合"};
    private int WIDTH = 750;
    private int HEIGHT = 600;
    private JComboBox bYear = new JComboBox(Objyear);
    private JComboBox bMonth = new JComboBox(Objmonth);
    private JComboBox bDay = new JComboBox(Objday);
    private JComboBox eYear = new JComboBox(Objyear);
    private JComboBox eMonth = new JComboBox(Objmonth);
    private JComboBox eDay = new JComboBox(Objday);
    private JComboBox storeComboBox = new JComboBox();
    private choicePopFrame storePop = new choicePopFrame();//设置仓库弹出窗口
    private static List liststore = new Vector();
    private static List listtypeDB = new Vector();
    private static List listtype = new Vector();
    private static JLabel storeSelect = new JLabel();
    private static JLabel typeSelect = new JLabel();
    DefaultTableModel model1 = new model();
    JTable table1 = new JTable(model1);
    DefaultTableModel model2 = new model();
    JTable table2 = new JTable(model2);
    JLabel storeX = new JLabel();
    JLabel sumpriceX = new JLabel();
    JLabel sumvaluesX = new JLabel();

    @SuppressWarnings("empty-statement")
    public searchFrame() {
        try {
            storeLoad(); //读取仓库信息
        } catch (SQLException ex) {
            Logger.getLogger(searchFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        setTitle("综合查询");
        setSize(WIDTH, HEIGHT);
        imagePanel panel = new imagePanel();
        JLabel label1 = new JLabel("从：");
        bYear.setSelectedIndex(getDate.yearIndex());
        JLabel label2 = new JLabel("年");
        bMonth.setSelectedIndex(getDate.monthIndex());
        JLabel label3 = new JLabel("月");
        bDay.setSelectedIndex(getDate.dayIndex());
        JLabel label4 = new JLabel("日");
        bYear.setMaximumSize(bYear.getPreferredSize());
        bMonth.setMaximumSize(bMonth.getPreferredSize());
        bDay.setMaximumSize(bDay.getPreferredSize());
        JLabel label45 = new JLabel("到：");
        eYear.setSelectedIndex(getDate.yearIndex());
        JLabel label5 = new JLabel("年");
        eMonth.setSelectedIndex(getDate.monthIndex());
        JLabel label6 = new JLabel("月");
        eDay.setSelectedIndex(getDate.dayIndex());
        JLabel label7 = new JLabel("日");
        eYear.setMaximumSize(eYear.getPreferredSize());
        eMonth.setMaximumSize(eMonth.getPreferredSize());
        eDay.setMaximumSize(eDay.getPreferredSize());
        JButton confirm = new JButton("基本查询");

        Box hbox1 = Box.createHorizontalBox();
        hbox1.add(Box.createHorizontalStrut(5));
        hbox1.add(panel);
        hbox1.add(Box.createHorizontalStrut(30));
        hbox1.add(label1);
        hbox1.add(bYear);
        hbox1.add(label2);
        hbox1.add(bMonth);
        hbox1.add(label3);
        hbox1.add(bDay);
        hbox1.add(label4);
        hbox1.add(Box.createHorizontalStrut(20));
        hbox1.add(label45);
        hbox1.add(eYear);
        hbox1.add(label5);
        hbox1.add(eMonth);
        hbox1.add(label6);
        hbox1.add(eDay);
        hbox1.add(label7);
        hbox1.add(Box.createHorizontalStrut(20));
        hbox1.add(confirm);
        hbox1.add(Box.createHorizontalGlue());
        //加入仓库选择
        JLabel labelStore = new JLabel("仓库：");
        //从properties中读取仓库设置
        storeComboBox.setSelectedIndex(Integer.parseInt(propertiesRW.proIDMakeRead("storeWorkingStream")));
        storeComboBox.setMaximumSize(storeComboBox.getPreferredSize());
        storeComboBox.setEditable(false);   //仓库不可直接修改
        //状态标签
        JLabel states = new JLabel("已选仓库：");
        Box hbox2 = Box.createHorizontalBox();
        hbox2.add(Box.createHorizontalStrut(5));
        hbox2.add(labelStore);
        hbox2.add(storeComboBox);
        hbox2.add(Box.createHorizontalStrut(30));
        hbox2.add(states);
        hbox2.add(storeSelect);
        hbox2.add(Box.createHorizontalGlue());
        //加入基本表单
        Object[] colName1 = new Object[4];
        colName1[0] = "仓库";
        colName1[1] = "销售数量";
        colName1[2] = "销售额";
        colName1[3] = "销售利润";
        model1.setColumnCount(4);
        model1.setRowCount(8);
        TableColumnModel tc = table1.getColumnModel();
        tc.getColumn(0).setPreferredWidth(20);
        tc.getColumn(0).setPreferredWidth(25);
        tc.getColumn(0).setPreferredWidth(15);
        model1.setColumnIdentifiers(colName1);//定义列名
        JScrollPane panel1 = new JScrollPane(table1);
        panel1.setPreferredSize(new Dimension(600, 150));

        //加入工具按钮
        JButton tool0 = new JButton("库存盘点");
        JButton tool1 = new JButton("销售排行");
        JButton tool2 = new JButton("利润排行");
        JLabel advLabel = new JLabel("高级工具:");
        Box vhbox3 = Box.createVerticalBox();
        vhbox3.add(advLabel);
        vhbox3.add(Box.createVerticalStrut(10));
        vhbox3.add(tool0);
        vhbox3.add(tool1);
        vhbox3.add(tool2);
        vhbox3.add(Box.createVerticalGlue());
        Box hbox3 = Box.createHorizontalBox();
        hbox3.add(Box.createHorizontalStrut(5));
        hbox3.add(panel1);
        hbox3.add(Box.createHorizontalStrut(10));
        hbox3.add(vhbox3);
        hbox3.add(Box.createHorizontalGlue());


        //hbox4.add(Box.createHorizontalStrut(400));

        //垂直布局
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(hbox1);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox2);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox3);
        vbox.add(Box.createVerticalGlue());
        //显示箱式布局
        add(vbox, BorderLayout.NORTH);

        storeComboBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (storeComboBox.getSelectedItem() == "更多组合...") {
                    Point point = Search.frameLocateOnScr();
                    storePop.setLocation(point.x - 100, point.y + 30);//设置窗口停靠，自动生成在主窗口左侧
                    storePop.setSize(100, 300);
                    storePop.setUndecorated(true);//隐藏标题栏
                    storePop.setVisible(true);
                } else {//实现弹出窗口的自动关闭和打开
                    storePop.dispose();
                    setStoreSelected(storeComboBox.getSelectedItem().toString().trim());
                }
            }
        });
        confirm.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            }
        });
        confirm.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                for (int j = table1.getRowCount() - 1; j >= 0; j--) {
                    for (int i = table1.getColumnCount() - 1; i >= 0; i--) {
                        table1.setValueAt("", j, i);
                    }
                }
                table1.repaint();
                dbOperation init = new dbOperation();
                init.DBConnect();
                String sqlinit;
                sqlinit = "delete from SearchCache";
                try {
                    init.DBSqlExe(sqlinit);
                } catch (SQLException ex) {
                    Logger.getLogger(dbOperation.class.getName()).log(Level.SEVERE, null, ex);
                }
                init.DBClosed();
                ResultSet rs = null;
                String sql;
                String store;
                String byear = bYear.getSelectedItem().toString().trim();
                String bmonth = bMonth.getSelectedItem().toString().trim();
                String bday = bDay.getSelectedItem().toString().trim();
                String eyear = eYear.getSelectedItem().toString().trim();
                String emonth = eMonth.getSelectedItem().toString().trim();
                String eday = eDay.getSelectedItem().toString().trim();
                int sumamount = 0;
                float sumoutprice = 0;
                float income = 0;
                if (storeComboBox.getSelectedItem().toString().trim() == "更多组合...") {
                    for (int k = 0; k < liststore.size(); k++) {
                        store = liststore.get(k).toString().trim();
                        sumamount = 0;
                        sumoutprice = 0;
                        income = 0;
                        for (int year = Integer.parseInt(byear); year <= Integer.parseInt(eyear); year++) {
                            for (int month = Integer.parseInt(bmonth); month <= Integer.parseInt(emonth); month++) {
                                for (int day = Integer.parseInt(bday); day <= Integer.parseInt(eday); day++) {
                                    if (month == Integer.parseInt(emonth) && day > Integer.parseInt(eday)) {
                                        break;
                                    } else {
                                        String syear = getDate.fixYear(String.valueOf(year));
                                        String smonth = getDate.fixMonth(String.valueOf(month));
                                        String sday = getDate.fixDay(String.valueOf(day));
                                        sql = "select amount,outPrice,info from sellt where (store='" + store + "') and year='" + syear + "' and month='" + smonth + "' and day='" + sday + "' and sellorreturn=0 ";
                                        System.out.print(sql);
                                        dbOperation stable = new dbOperation();
                                        stable.DBConnect();
                                        try {
                                            rs = stable.DBSqlQuery(sql);
                                            while (rs.next()) {
                                                int amount = Integer.parseInt(rs.getString(1).trim());
                                                float outprice = Float.parseFloat(rs.getString(2).trim());
                                                sumamount = sumamount + amount;
                                                sumoutprice = sumoutprice + outprice * amount;
                                                dbOperation tem = new dbOperation();
                                                ResultSet inprice = null;
                                                tem.DBConnect();
                                                String s = "select distinct inPrice from inputt where info='" + rs.getString(3).trim() + "'";
                                                //System.out.print(s);
                                                inprice = tem.DBSqlQuery(s);
                                                if (inprice.next()) {
                                                    income = income + (outprice - Float.parseFloat(inprice.getString(1).trim())) * amount;
                                                }
                                                tem.DBClosed();
                                            }
                                            stable.DBClosed();
                                        } catch (SQLException ex) {
                                            Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                            }
                        }
                        dbOperation tem = new dbOperation();
                        tem.DBConnect();
                        String s = "insert into SearchCache values('" + store + "','" + sumamount + "','" + sumoutprice + "','" + income + "')";
                        try {
                            tem.DBSqlExe(s);
                        } catch (SQLException ex) {
                            Logger.getLogger(searchFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        tem.DBClosed();
                    }
                } else if (storeComboBox.getSelectedItem().toString().trim() == "全部仓库") {
                    for (int k = 1; k < storeComboBox.getItemCount() - 1; k++) {
                        store = storeComboBox.getItemAt(k).toString().trim();
                        sumamount = 0;
                        sumoutprice = 0;
                        income = 0;
                        for (int year = Integer.parseInt(byear); year <= Integer.parseInt(eyear); year++) {
                            for (int month = Integer.parseInt(bmonth); month <= Integer.parseInt(emonth); month++) {
                                for (int day = Integer.parseInt(bday); day <= Integer.parseInt(eday); day++) {
                                    if (month == Integer.parseInt(emonth) && day > Integer.parseInt(eday)) {
                                        break;
                                    } else {
                                        String syear = getDate.fixYear(String.valueOf(year));
                                        String smonth = getDate.fixMonth(String.valueOf(month));
                                        String sday = getDate.fixDay(String.valueOf(day));
                                        sql = "select amount,outPrice,info from sellt where (store='" + store + "') and year='" + syear + "' and month='" + smonth + "' and day='" + sday + "' and sellorreturn=0 ";
                                        System.out.print(sql);
                                        dbOperation stable = new dbOperation();
                                        stable.DBConnect();
                                        try {
                                            rs = stable.DBSqlQuery(sql);
                                            while (rs.next()) {
                                                int amount = Integer.parseInt(rs.getString(1).trim());
                                                float outprice = Float.parseFloat(rs.getString(2).trim());
                                                sumamount = sumamount + amount;
                                                sumoutprice = sumoutprice + outprice * amount;
                                                dbOperation tem = new dbOperation();
                                                ResultSet inprice = null;
                                                tem.DBConnect();
                                                String s = "select distinct inPrice from inputt where info='" + rs.getString(3).trim() + "'";
                                                //System.out.print(s);
                                                inprice = tem.DBSqlQuery(s);
                                                if (inprice.next()) {
                                                    income = income + (outprice - Float.parseFloat(inprice.getString(1).trim())) * amount;
                                                }
                                                tem.DBClosed();
                                            }
                                            stable.DBClosed();
                                        } catch (SQLException ex) {
                                            Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                            }
                        }
                        dbOperation tem = new dbOperation();
                        tem.DBConnect();
                        String s = "insert into SearchCache values('" + store + "','" + sumamount + "','" + sumoutprice + "','" + income + "')";
                        try {
                            tem.DBSqlExe(s);
                        } catch (SQLException ex) {
                            Logger.getLogger(searchFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        tem.DBClosed();
                    }
                } else {
                    store = storeComboBox.getSelectedItem().toString().trim();
                    sumamount = 0;
                    sumoutprice = 0;
                    income = 0;
                    for (int year = Integer.parseInt(byear); year <= Integer.parseInt(eyear); year++) {
                        for (int month = Integer.parseInt(bmonth); month <= Integer.parseInt(emonth); month++) {
                            for (int day = Integer.parseInt(bday); day <= Integer.parseInt(eday); day++) {
                                if (month == Integer.parseInt(emonth) && day > Integer.parseInt(eday)) {
                                    break;
                                } else {
                                    String syear = getDate.fixYear(String.valueOf(year));
                                    String smonth = getDate.fixMonth(String.valueOf(month));
                                    String sday = getDate.fixDay(String.valueOf(day));
                                    sql = "select amount,outPrice,info from sellt where (store='" + store + "') and year='" + syear + "' and month='" + smonth + "' and day='" + sday + "' and sellorreturn=0 ";
                                    System.out.print(sql);
                                    dbOperation stable = new dbOperation();
                                    stable.DBConnect();
                                    try {
                                        rs = stable.DBSqlQuery(sql);
                                        while (rs.next()) {
                                            int amount = Integer.parseInt(rs.getString(1).trim());
                                            float outprice = Float.parseFloat(rs.getString(2).trim());
                                            sumamount = sumamount + amount;
                                            sumoutprice = sumoutprice + outprice * amount;
                                            dbOperation tem = new dbOperation();
                                            ResultSet inprice = null;
                                            tem.DBConnect();
                                            String s = "select distinct inPrice from inputt where info='" + rs.getString(3).trim() + "'";
                                            //System.out.print(s);
                                            inprice = tem.DBSqlQuery(s);
                                            if (inprice.next()) {
                                                income = income + (outprice - Float.parseFloat(inprice.getString(1).trim())) * amount;
                                            }
                                            tem.DBClosed();
                                        }
                                        stable.DBClosed();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }
                    dbOperation tem = new dbOperation();
                    tem.DBConnect();
                    String s = "insert into SearchCache values('" + store + "','" + sumamount + "','" + sumoutprice + "','" + income + "')";
                    try {
                        tem.DBSqlExe(s);
                    } catch (SQLException ex) {
                        Logger.getLogger(searchFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tem.DBClosed();
                }

                int k = 0;
                ResultSet RS = null;
                dbOperation c = new dbOperation();
                c.DBConnect();
                sql = "select store,amount,sumprice,income from SearchCache";
                try {
                    RS = c.DBSqlQuery(sql);
                    while (RS.next()) {
                        table1.setValueAt(RS.getString(1), k, 0);
                        table1.setValueAt(RS.getString(2), k, 1);
                        table1.setValueAt(RS.getString(3), k, 2);
                        table1.setValueAt(RS.getString(4), k, 3);
                        k++;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                c.DBClosed();
            }
        });
    }

    private void storeLoad() throws SQLException {
        storeComboBox.addItem("全部仓库");
        dbOperation storeLoad = new dbOperation();
        storeLoad.DBConnect();
        String sql = "select store from storet";
        ResultSet rs = null;
        try {
            try {
                rs = storeLoad.DBSqlQuery(sql);
            } catch (SQLException ex) {
                Logger.getLogger(sellFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (rs.next()) {
                storeComboBox.addItem(rs.getString(1).trim());
                storePop.cb.addItem(rs.getString(1).trim());
            }
            storeLoad.DBClosed();
        } catch (SQLException ex) {
            Logger.getLogger(sellFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        storeComboBox.addItem("更多组合...");
    }
    //从子窗口把仓库选择信息传递回来

    public static void setListStore(String x) {
        liststore.add(x);
    }

    public static void setStoreSelected(String x) {
        storeSelect.setText(x);
    }

    public static void setTypeSelected(String x) {
        typeSelect.setText(x);
    }

    public static void setListTypeDB(String x) {
        listtypeDB.add(x);
    }

    public static void setListType(String x) {
        listtype.add(x);
    }
}


