package jwms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import method.ProgressBarDialog;
import method.dbOperation;
import method.getDate;
import method.propertiesRW;

/**
 *
 * @author lqik2004
 */
public class workingStream {

    static workingFrame frame = new workingFrame();

    public workingStream() {

        frame.setLocationRelativeTo(null);//一句让窗口居中
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static Point frameLocateOnScr() {
        return frame.getLocationOnScreen();
    }
}

class workingFrame extends JFrame {

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
    private JComboBox typeComboBox = new JComboBox(type);
    private choicePopFrame storePop = new choicePopFrame();//设置仓库弹出窗口
    private choicePopFrameType typePop = new choicePopFrameType();//设置查看种类弹出窗口
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
    public workingFrame() {
        try {
            storeLoad(); //读取仓库信息
        } catch (SQLException ex) {
            Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        setTitle("经营历程");
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
        JButton confirm = new JButton("查询");

        Box hbox1 = Box.createHorizontalBox();
        hbox1.add(Box.createHorizontalStrut(5));
        hbox1.add(panel);
        hbox1.add(Box.createHorizontalStrut(50));
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
        hbox1.add(Box.createHorizontalStrut(30));
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

        JLabel labelType = new JLabel("单据类型：");
        JLabel stateType = new JLabel("已选单据类型：");
        typeComboBox.setMaximumSize(typeComboBox.getPreferredSize());
        //typeComboBox.setMinimumSize(getPreferredSize());
        Box hbox3 = Box.createHorizontalBox();
        hbox3.add(Box.createHorizontalStrut(5));
        hbox3.add(labelType);
        hbox3.add(typeComboBox);
        hbox3.add(Box.createHorizontalStrut(30));
        hbox3.add(stateType);
        hbox3.add(typeSelect);
        hbox3.add(Box.createHorizontalGlue());
        //加入表单
        Object[] colName1 = new Object[3];
        colName1[0] = "日期";
        colName1[1] = "ID";
        colName1[2] = "单据类型";
        model1.setColumnCount(3);
        model1.setRowCount(0);
        model1.setColumnIdentifiers(colName1);//定义列名
        table1.getColumnModel().getColumn(0).setPreferredWidth(70);
        table1.getColumnModel().getColumn(1).setPreferredWidth(100);
        table1.getColumnModel().getColumn(2).setPreferredWidth(90);
        JScrollPane panel1 = new JScrollPane(table1);
        panel1.setPreferredSize(new Dimension(300, 400));
        table1.setDefaultRenderer(Object.class, new ColorRenderer());
        //table1.setSelectionBackground(Color.RED);
        table1.setShowHorizontalLines(false);
        table1.setShowVerticalLines(false);
        Object[] colName2 = new Object[3];
        colName2[0] = "日期";
        colName2[1] = "ID";
        colName2[2] = "单据类型";
        model2.setColumnCount(3);
        model2.setRowCount(0);
        TableColumnModel tc2 = table2.getColumnModel();
        tc2.getColumn(0).setPreferredWidth(20);
        tc2.getColumn(0).setPreferredWidth(25);
        tc2.getColumn(0).setPreferredWidth(15);
        model2.setColumnIdentifiers(colName1);//定义列名
        table2.setDefaultRenderer(Object.class, new ColorRenderer());
        table2.setShowHorizontalLines(false);
        JScrollPane panel2 = new JScrollPane(table2);
        panel2.setPreferredSize(new Dimension(450, 400));
        Box hbox4 = Box.createHorizontalBox();
        hbox4.add(Box.createHorizontalStrut(5));
        hbox4.add(panel1);
        hbox4.add(Box.createHorizontalStrut(30));
        Box vhbox4 = Box.createVerticalBox();
        hbox4.add(vhbox4);
        JLabel store = new JLabel("仓库：");

        JLabel sumprice = new JLabel("合计金额：");

        JLabel sumvalues = new JLabel("合计数量：");

        Box h4 = Box.createHorizontalBox();
        h4.add(store);
        h4.add(storeX);
        h4.add(Box.createHorizontalGlue());
        h4.add(sumvalues);
        h4.add(sumvaluesX);
        h4.add(Box.createHorizontalStrut(20));
        h4.add(sumprice);
        h4.add(sumpriceX);
        vhbox4.add(h4);
        vhbox4.add(panel2);

        //hbox4.add(Box.createHorizontalStrut(400));
        hbox4.add(Box.createHorizontalGlue());
        //垂直布局
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(hbox1);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox2);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox3);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox4);
        vbox.add(Box.createVerticalGlue());
        //显示箱式布局
        add(vbox, BorderLayout.NORTH);

        storeComboBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (storeComboBox.getSelectedItem() == "更多组合...") {
                    Point point = workingStream.frameLocateOnScr();
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
        typeComboBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (typeComboBox.getSelectedItem() == "更多组合") {
                    Point point = workingStream.frameLocateOnScr();
                    typePop.setLocation(point.x - 100, point.y + 60);//设置窗口停靠，自动生成在主窗口左侧
                    typePop.setSize(100, 300);
                    typePop.setUndecorated(true);//隐藏标题栏
                    typePop.setVisible(true);
                } else {//实现弹出窗口的自动关闭和打开
                    typePop.dispose();
                    setTypeSelected(typeComboBox.getSelectedItem().toString().trim());
                }
            }
        });
        table1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String keyWord;
                String keyStore;
                String sql = null;
                ResultSet rs = null;
                if (e.getButton() == MouseEvent.BUTTON1) {// 单击鼠标左键
                    if (e.getClickCount() == 2) {
                        /*for (int j = table2.getRowCount() - 1; j >= 0; j--) {
                        for (int i = table2.getColumnCount() - 1; i >= 0; i--) {
                        table2.setValueAt("", j, i);
                        }
                        }*/
                        model2.setRowCount(0);
                        table2.repaint();
                        keyWord = table1.getModel().getValueAt(table1.getSelectedRow(), 1).toString().trim();
                        if (keyWord.startsWith("S")) {
                            Object[] colName = new Object[6];
                            colName[0] = "编号";
                            colName[1] = "商品名称";
                            colName[2] = "数量";
                            colName[3] = "单价";
                            colName[4] = "金额";
                            colName[5] = "备注";
                            model2.setColumnCount(6);
                            model2.setColumnIdentifiers(colName);
                            keyStore = "sellt";
                            sql = "select num,info,amount,outprice,others,store from " + keyStore + " where id='" + keyWord + "'order by num";
                            System.out.print(sql);
                            dbOperation st = new dbOperation();
                            st.DBConnect();
                            try {
                                int i = 0;
                                rs = st.DBSqlQuery(sql);
                                int sum1 = 0;//合计数量
                                float sum2 = 0;//合计金额
                                while (rs.next()) {
                                    Object[] data = new Object[6];
                                    data[0] = rs.getString(1).trim();
                                    data[1] = rs.getString(2).trim();
                                    data[2] = rs.getString(3).trim();
                                    float sum = Float.parseFloat(rs.getString(3).trim()) * Float.parseFloat(rs.getString(4).trim());
                                    data[3] = rs.getString(4).trim();
                                    data[4] = sum;
                                    data[5] = rs.getString(5).trim();
                                    model2.addRow(data);
                                    storeX.setText(rs.getString(6).trim());
                                    sum1 = sum1 + Integer.parseInt(data[2].toString().trim());
                                    sum2 = sum2 + sum;

                                }
                                sumvaluesX.setText(String.valueOf(sum1));
                                sumpriceX.setText(String.valueOf(sum2));
                            } catch (SQLException ex) {
                                Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            st.DBClosed();
                        } else if (keyWord.startsWith("E")) {
                            Object[] colName = new Object[4];
                            colName[0] = "编号";
                            colName[1] = "商品名称";
                            colName[2] = "数量";
                            colName[3] = "备注";
                            model2.setColumnCount(4);
                            model2.setColumnIdentifiers(colName);
                            keyStore = "equalt";
                            sql = "select num,info,amount,others,inStore,outStore from " + keyStore + " where id='" + keyWord + "' order by num";
                            System.out.print(sql);
                            dbOperation st = new dbOperation();
                            st.DBConnect();
                            try {
                                int i = 0;
                                rs = st.DBSqlQuery(sql);
                                int sum1 = 0;//合计数量
                                float sum2 = 0;//合计金额
                                while (rs.next()) {
                                    Object[] data = new Object[4];
                                    data[0] = rs.getString(1).trim();
                                    data[1] = rs.getString(2).trim();
                                    data[2] = rs.getString(3).trim();
                                    data[3] = rs.getString(4).trim();
                                    model2.addRow(data);
                                    storeX.setText("'" + rs.getString(5) + "' 收  '" + rs.getString(6) + "' 发");
                                    sum1 = sum1 + Integer.parseInt(data[2].toString().trim());
                                }
                                sumvaluesX.setText(String.valueOf(sum1));
                                sumpriceX.setText("NULL");
                            } catch (SQLException ex) {
                                Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            st.DBClosed();


                        } else if (keyWord.startsWith("L")) {
                            keyStore = "loset";
                            Object[] colName = new Object[6];
                            colName[0] = "编号";
                            colName[1] = "商品名称";
                            colName[2] = "数量";
                            colName[3] = "单价";
                            colName[4] = "金额";
                            colName[5] = "备注";
                            model2.setColumnCount(6);
                            model2.setColumnIdentifiers(colName);

                            sql = "select num,info,amount,inprice,others,store from " + keyStore + " where id='" + keyWord + "'order by num";
                            System.out.print(sql);
                            dbOperation st = new dbOperation();
                            st.DBConnect();
                            try {
                                int i = 0;
                                rs = st.DBSqlQuery(sql);
                                int sum1 = 0;//合计数量
                                float sum2 = 0;//合计金额
                                while (rs.next()) {
                                    Object[] data = new Object[6];
                                    data[0] = rs.getString(1).trim();
                                    data[1] = rs.getString(2).trim();
                                    data[2] = rs.getString(3).trim();
                                    float sum = Float.parseFloat(rs.getString(3).trim()) * Float.parseFloat(rs.getString(4).trim());
                                    data[3] = rs.getString(4).trim();
                                    data[4] = sum;
                                    data[5] = rs.getString(5).trim();
                                    model2.addRow(data);
                                    storeX.setText(rs.getString(6).trim());
                                    sum1 = sum1 + Integer.parseInt(data[2].toString().trim());
                                    sum2 = sum2 + sum;
                                }
                                sumvaluesX.setText(String.valueOf(sum1));
                                sumpriceX.setText(String.valueOf(sum2));
                            } catch (SQLException ex) {
                                Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            st.DBClosed();
                        } else if (keyWord.startsWith("I")) {
                            keyStore = "inputt";
                            Object[] colName = new Object[6];
                            colName[0] = "编号";
                            colName[1] = "商品名称";
                            colName[2] = "数量";
                            colName[3] = "入库单价";
                            colName[4] = "零售价";
                            colName[5] = "合计金额";
                            model2.setColumnCount(6);
                            model2.setColumnIdentifiers(colName);

                            sql = "select num,info,amount,inprice,outPrice,sumprice,store from " + keyStore + " where id='" + keyWord + "'order by num";
                            System.out.print(sql);
                            dbOperation st = new dbOperation();
                            st.DBConnect();
                            try {
                                int i = 0;
                                rs = st.DBSqlQuery(sql);
                                int sum1 = 0;//合计数量
                                float sum2 = 0;//合计金额
                                while (rs.next()) {
                                    Object[] data = new Object[6];
                                    data[0] = rs.getString(1).trim();
                                    data[1] = rs.getString(2).trim();
                                    data[2] = rs.getString(3).trim();
                                    float sum = Float.parseFloat(rs.getString(3).trim()) * Float.parseFloat(rs.getString(4).trim());
                                    data[3] = rs.getString(4).trim();
                                    data[4] = rs.getString(5).trim();
                                    data[5] = sum;
                                    model2.addRow(data);
                                    storeX.setText(rs.getString(7).trim());
                                    sum1 = sum1 + Integer.parseInt(data[2].toString().trim());
                                    sum2 = sum2 + sum;

                                }
                                sumvaluesX.setText(String.valueOf(sum1));
                                sumpriceX.setText(String.valueOf(sum2));
                            } catch (SQLException ex) {
                                Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            st.DBClosed();
                        }

                    /*
                    int colummCount = table1.getModel().getColumnCount();// 列数
                    for (int i = 0; i < colummCount; i++) {
                    System.out.print(table1.getModel().getValueAt(table1.getSelectedRow(), i).toString() + " ");
                    }
                    System.out.println();
                     * */
                    }
                }
            }
        });
        confirm.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                /*for (int j = table1.getRowCount() - 1; j >= 0; j--) {
                for (int i = table1.getColumnCount() - 1; i >= 0; i--) {
                table1.setValueAt("", j, i);
                }
                }*/
                model1.setRowCount(0);
                table1.repaint();
                dbOperation init = new dbOperation();
                init.DBConnect();
                String sqlinit;
                sqlinit = "delete from StreamCache";
                try {
                    init.DBSqlExe(sqlinit);
                } catch (SQLException ex) {
                    Logger.getLogger(dbOperation.class.getName()).log(Level.SEVERE, null, ex);
                }
                init.DBClosed();
                String byear = bYear.getSelectedItem().toString().trim();
                String bmonth = bMonth.getSelectedItem().toString().trim();
                String bday = bDay.getSelectedItem().toString().trim();
                String eyear = eYear.getSelectedItem().toString().trim();
                String emonth = eMonth.getSelectedItem().toString().trim();
                String eday = eDay.getSelectedItem().toString().trim();
                int dayValue = (Integer.parseInt(eyear) - Integer.parseInt(byear)) * 371 + (Integer.parseInt(emonth) - Integer.parseInt(bmonth)) * 31 + (Integer.parseInt(eday) - Integer.parseInt(bday));
                final ProgressBarDialog proBar = new ProgressBarDialog();
                Point point = workingStream.frameLocateOnScr();
                proBar.setLocation(point.x + 300, point.y + 320);//设置窗口停靠，自动生成在主窗口左侧
                proBar.adoptDeterminate(dayValue);

                new Thread() {

                    @Override
                    public void run() {
                        ResultSet rs = null;
                        String sql;
                        String store = null;
                        String byear = bYear.getSelectedItem().toString().trim();
                        String bmonth = bMonth.getSelectedItem().toString().trim();
                        String bday = bDay.getSelectedItem().toString().trim();
                        String eyear = eYear.getSelectedItem().toString().trim();
                        String emonth = eMonth.getSelectedItem().toString().trim();
                        String eday = eDay.getSelectedItem().toString().trim();
                        dbOperation stable = new dbOperation();
                        stable.DBConnect();
                        String bDate=byear+bmonth+bday;
                        String eDate=eyear+emonth+eday;

                        if (typeComboBox.getSelectedItem().toString().trim() == "更多组合") {
                            int proBarValues = 0;
                            for (int i = 0; i < listtypeDB.size(); i++) {
                                if (storeComboBox.getSelectedItem().toString().trim() == "更多组合...") {
                                    store = liststore.get(0).toString().trim();
                                    for (int k = 1; k < liststore.size(); k++) {
                                        store = store + "' or store= '" + liststore.get(k).toString().trim();
                                    }

                                    sql = "select distinct date,id from " + listtypeDB.get(i) + " (store='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"' ";
                                    System.out.print(sql);

                                    try {
                                        rs = stable.DBSqlQuery(sql);
                                        while (rs.next()) {
                                            String date = rs.getString(1);
                                            dbOperation tem = new dbOperation();
                                            tem.DBConnect();
                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + listtype.get(i).toString().trim() + "')";
                                            System.out.print(s);
                                            tem.DBSqlExe(s);
                                            tem.DBClosed();
                                        }

                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                } else if (storeComboBox.getSelectedItem().toString().trim() == "全部仓库") {
                                    sql = "select distinct date,id from " + listtypeDB.get(i) + "  date<='"+eDate+"' and date>='"+bDate+"' ";
                                    System.out.print(sql);

                                    try {
                                        rs = stable.DBSqlQuery(sql);
                                        while (rs.next()) {
                                            String date = rs.getString(1);

                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + listtype.get(i).toString().trim() + "')";
                                            System.out.print(s);
                                            stable.DBSqlExe(s);

                                        }

                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                } else {
                                    store = storeComboBox.getSelectedItem().toString().trim();
                                    sql = "select distinct date,id from " + listtypeDB.get(i) + " (store='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                    System.out.print(sql);

                                    try {
                                        rs = stable.DBSqlQuery(sql);

                                        while (rs.next()) {
                                            dbOperation tem = new dbOperation();
                                            tem.DBConnect();
                                            String date = rs.getString(1).substring(0, 8);
                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + listtype.get(i).toString().trim() + "')";
                                            System.out.print(s);
                                            tem.DBSqlExe(s);
                                            tem.DBClosed();
                                        }


                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                }
                            }
                        } else if (typeComboBox.getSelectedItem().toString().trim() == "全部类型") {
                            int proBarValues = 0;
                            List type = new Vector();
                            type.add("销售单");
                            type.add("sellt where sellORreturn=0 and");
                            type.add("销售退货单");
                            type.add("sellt where sellORreturn=1 and");
                            type.add("进货退货单");
                            type.add("inputt where inputORreturn=1 and");
                            type.add("进货单");
                            type.add("inputt where inputORreturn=0 and");
                            type.add("报损单");
                            type.add("loset where loseORgain=0 and");
                            type.add("报益单");
                            type.add("loset where loseORgain=1 and");
                            type.add("同价调拨单");
                            type.add("equalt where");
                            for (int i = 0; i < type.size(); i = i + 2) {
                                if (storeComboBox.getSelectedItem().toString().trim() == "更多组合...") {
                                    //如果当出现“同价调拨单”的时候sql语句会发生变化，所以采用了分支
                                    if (type.get(i) == "同价调拨单") {
                                        store = "instore=  '" + liststore.get(0).toString().trim() + "' or outstore= '" + liststore.get(0).toString().trim() + "'";
                                        for (int k = 1; k < liststore.size(); k++) {
                                            store += "or instore= '" + liststore.get(k).toString().trim() + "' or outstore= '" + liststore.get(k).toString().trim() + "'";
                                        }
                                        sql = "select distinct date,id from " + type.get(i + 1) + " (" + store + ") and date<'"+eDate+"' and date>'"+bDate+"'  ";
                                    } else {
                                        store = liststore.get(0).toString().trim();
                                        for (int k = 1; k < liststore.size(); k++) {
                                            store = store + "' or store= '" + liststore.get(k).toString().trim();
                                        }
                                        sql = "select distinct date,id from " + type.get(i + 1) + " (store='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                    }
                                    System.out.println(sql);

                                    try {
                                        rs = stable.DBSqlQuery(sql);
                                        while (rs.next()) {
                                            String date = rs.getString(1);
                                            dbOperation tem = new dbOperation();
                                            tem.DBConnect();
                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + type.get(i).toString().trim() + "')";
                                            System.out.print(s);
                                            tem.DBSqlExe(s);
                                            tem.DBClosed();
                                        }

                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                } else if (storeComboBox.getSelectedItem().toString().trim() == "全部仓库") {
                                    sql = "select distinct date,id from " + type.get(i + 1) + "  date<='"+eDate+"' and date>='"+bDate+"'  ";
                                    //System.out.println(sql);
                                    //System.out.println(syear + smonth + sday);
                                    try {
                                        rs = stable.DBSqlQuery(sql);
                                        while (rs.next()) {
                                        String date = rs.getString(1);
                                            dbOperation tem = new dbOperation();
                                            tem.DBConnect();
                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + type.get(i).toString().trim() + "')";
                                            //System.out.println(s);

                                            tem.DBSqlExe(s);
                                            tem.DBClosed();
                                        }

                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                } else {
                                    store = storeComboBox.getSelectedItem().toString().trim();
                                    if (type.get(i) == "同价调拨单") {
                                        sql = "select distinct date,id from " + type.get(i + 1) + " (instore='" + store + "' or outstore='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                    } else {
                                        sql = "select distinct date,id from " + type.get(i + 1) + " (store='" + store + "') and date=<'"+eDate+"' and date>='"+bDate+"'  ";
                                    }
                                    System.out.print(sql);

                                    try {
                                        rs = stable.DBSqlQuery(sql);
                                        while (rs.next()) {
                                            String date = rs.getString(1);
                                            dbOperation tem = new dbOperation();
                                            tem.DBConnect();
                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + type.get(i).toString().trim() + "')";
                                            System.out.print(s);
                                            tem.DBSqlExe(s);
                                            tem.DBClosed();
                                        }

                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                }

                            }
                        } else {
                            int proBarValues = 0;
                            String type = null;
                            if (typeComboBox.getSelectedItem().toString().trim() == "销售单") {
                                type = "sellt where sellORreturn=0 and";
                            } else if (typeComboBox.getSelectedItem().toString().trim() == "销售退货单") {
                                type = "sellt where sellORreturn=1 and";
                            } else if (typeComboBox.getSelectedItem().toString().trim() == "进货退货单") {
                                type = "inputt where inputORreturn=1 and";
                            } else if (typeComboBox.getSelectedItem().toString().trim() == "进货单") {
                                type = "inputt where inputORreturn=0 and";
                            } else if (typeComboBox.getSelectedItem().toString().trim() == "报损单") {
                                type = "loset where loseORgain=0 and";
                            } else if (typeComboBox.getSelectedItem().toString().trim() == "报益单") {
                                type = "loset where loseORgain=1 and";
                            } else if (typeComboBox.getSelectedItem().toString().trim() == "同价调拨单") {
                                type = "equalt where ";
                            }
                            if (storeComboBox.getSelectedItem().toString().trim() == "更多组合...") {
                                if (type == "equalt where ") {
                                    store = "instore=  '" + liststore.get(0).toString().trim() + "' or outstore= '" + liststore.get(0).toString().trim() + "'";
                                    for (int k = 1; k < liststore.size(); k++) {
                                        store += "or instore= '" + liststore.get(k).toString().trim() + "' or outstore= '" + liststore.get(k).toString().trim() + "'";
                                    }
                                    sql = "select distinct date,id from eqult where (" + store + ") and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                } else {
                                    store = liststore.get(0).toString().trim();
                                    for (int k = 1; k < liststore.size(); k++) {
                                        store = store + "' or store= '" + liststore.get(k).toString().trim();
                                    }
                                    sql = "select distinct date,id from " + type + " (store='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                    System.out.println(sql);

                                    try {
                                        rs = stable.DBSqlQuery(sql);
                                        while (rs.next()) {
                                            String date = rs.getString(1);
                                            dbOperation tem = new dbOperation();
                                            tem.DBConnect();
                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + typeComboBox.getSelectedItem().toString().trim() + "')";
                                            System.out.print(s);
                                            tem.DBSqlExe(s);
                                            tem.DBClosed();
                                        }

                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                }

                            } else if (storeComboBox.getSelectedItem().toString().trim() == "全部仓库") {
                                sql = "select distinct date,id from " + type + "  date<='"+eDate+"' and date>='"+bDate+"'  ";
                                System.out.print(sql);

                                try {
                                    rs = stable.DBSqlQuery(sql);
                                    while (rs.next()) {
                                        String date = rs.getString(1);
                                        dbOperation tem = new dbOperation();
                                        tem.DBConnect();
                                        String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + typeComboBox.getSelectedItem().toString().trim() + "')";
                                        System.out.print(s);
                                        tem.DBSqlExe(s);
                                        tem.DBClosed();
                                    }

                                } catch (SQLException ex) {
                                    Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                proBar.setValue(proBarValues++);

                            } else {
                                store = storeComboBox.getSelectedItem().toString().trim();

                                if (type == "equalt where ") {
                                    sql = "select distinct date,id from " + type + " (instore='" + store + "' or outstore='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                } else {
                                    sql = "select distinct date,id from " + type + " (store='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                }
                                System.out.print(sql);

                                try {
                                    rs = stable.DBSqlQuery(sql);
                                    while (rs.next()) {
                                        String date = rs.getString(1);
                                        dbOperation tem = new dbOperation();
                                        tem.DBConnect();
                                        String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + typeComboBox.getSelectedItem().toString().trim() + "')";
                                        System.out.print(s);
                                        tem.DBSqlExe(s);
                                        tem.DBClosed();
                                    }

                                } catch (SQLException ex) {
                                    Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                proBar.setValue(proBarValues++);
                            }
                        }
                        stable.DBClosed();
                        ResultSet RS = null;
                        dbOperation c = new dbOperation();
                        c.DBConnect();
                        sql = "select date,id,type from StreamCache order by date ASC";
                        try {
                            RS = c.DBSqlQuery(sql);
                            while (RS.next()) {
                                Object[] data = new Object[3];
                                data[0] = RS.getString(1).substring(0, 8).trim();
                                data[1] = RS.getString("id").trim();
                                data[2] = RS.getString("type").trim();
                                model1.addRow(data);
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        c.DBClosed();
                        proBar.finishDeterminate();
                    }
                }.start();
                proBar.setVisible(true);
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
            storePop.cb.addItem(null);
            storeLoad.DBClosed();
        } catch (SQLException ex) {
            Logger.getLogger(sellFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        storeComboBox.addItem("更多组合...");
    }
//从子窗口把仓库选择信息传递回来

    public static void setListStore(String x) {
        if (x == "clear") {
            liststore.clear();
        } else {
            liststore.add(x);
        }

    }

    public static void setStoreSelected(String x) {
        storeSelect.setText(x);
    }

    public static void setTypeSelected(String x) {
        typeSelect.setText(x);
    }

    public static void setListTypeDB(String x) {
        if (x == "clear") {
            listtypeDB.clear();
        } else {
            listtypeDB.add(x);
        }

    }

    public static void setListType(String x) {
        if (x == "clear") {
            listtype.clear();
        } else {
            listtype.add(x);
        }






    }
}

class imagePanel extends JPanel {

    private Image image;

    public imagePanel() {
        setPreferredSize(new Dimension(153, 54));
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        try {
            image = ImageIO.read(new File("image\\logo.gif"));
        } catch (IOException ex) {
            Logger.getLogger(imagePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            return;
        }
        g.drawImage(image, 0, 0, null);
    }
}

class choicePopFrame extends JFrame {

    public JComboBox cb;
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);

    public choicePopFrame() {
        Object[] colName = new Object[1];
        colName[0] = "更多仓库组合...";
        model.setColumnCount(1);
        model.setRowCount(7);
        model.setColumnIdentifiers(colName);//定义列名
        table.setRowHeight(30);
        cb = new JComboBox();
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn popColumn = columnModel.getColumn(0);
        popColumn.setCellEditor(new DefaultCellEditor(cb));
        JScrollPane panel = new JScrollPane(table);
        panel.setPreferredSize(new Dimension(100, 200));
        JButton confirmBt = new JButton("确认");

        Box vbox = Box.createVerticalBox();
        vbox.add(panel);
        vbox.add(confirmBt);
        add(vbox, BorderLayout.CENTER);

        confirmBt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                workingFrame.setListStore("clear");
                String temStoreSelect = "";
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (model.getValueAt(i, 0) != null) {
                        workingFrame.setListStore(model.getValueAt(i, 0).toString());
                        temStoreSelect = temStoreSelect + model.getValueAt(i, 0).toString().trim() + "  ";
                    }
                }
                System.out.print(temStoreSelect);
                workingFrame.setStoreSelected(temStoreSelect);
                dispose();
            }
        });
    }
}

class model extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

class choicePopFrameType extends JFrame {

    public JComboBox cb;
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);

    public choicePopFrameType() {
        Object[] colName = new Object[1];
        colName[0] = "更多单据组合...";
        Object[] type = {"销售单", "进货单", "同价调拨单", "报损单", "报益单", "销售退货单", "进货退货单", ""}; //增加了清空项
        model.setColumnCount(1);
        model.setRowCount(8);
        model.setColumnIdentifiers(colName);//定义列名
        table.setRowHeight(30);
        cb = new JComboBox(type);
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn popColumn = columnModel.getColumn(0);
        popColumn.setCellEditor(new DefaultCellEditor(cb));
        JScrollPane panel = new JScrollPane(table);
        panel.setPreferredSize(new Dimension(100, 200));
        JButton confirmBt = new JButton("确认");

        Box vbox = Box.createVerticalBox();
        vbox.add(panel);
        vbox.add(confirmBt);
        add(vbox, BorderLayout.CENTER);

        confirmBt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                workingFrame.setListTypeDB("clear");
                workingFrame.setListType("clear");
                String temStoreSelect = "";
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (model.getValueAt(i, 0) != null) {
                        if (model.getValueAt(i, 0).toString() != "") {      //判断当选项不为空的时候才进行判断操作，如果为空则不操作
                            if (model.getValueAt(i, 0).toString() == "销售单") {
                                workingFrame.setListTypeDB("sellt where sellORreturn=0 and");
                            } else if (model.getValueAt(i, 0).toString() == "销售退货单") {
                                workingFrame.setListTypeDB("sellt where sellORreturn=1 and");
                            } else if (model.getValueAt(i, 0).toString() == "进货退货单") {
                                workingFrame.setListTypeDB("inputt where inputORreturn=1 and");
                            } else if (model.getValueAt(i, 0).toString() == "进货单") {
                                workingFrame.setListTypeDB("inputt where inputORreturn=0 and");
                            } else if (model.getValueAt(i, 0).toString() == "报损单") {
                                workingFrame.setListTypeDB("loset where loseORgain=0 and");
                            } else if (model.getValueAt(i, 0).toString() == "报益单") {
                                workingFrame.setListTypeDB("loset where loseORgain=1 and");
                            } else if (model.getValueAt(i, 0).toString() == "同价调拨单") {
                                workingFrame.setListTypeDB("equalt where ");
                            }
                            workingFrame.setListType(model.getValueAt(i, 0).toString().trim());
                            temStoreSelect = temStoreSelect + model.getValueAt(i, 0).toString().trim() + "  ";
                        }
                    }
                }
                System.out.print(temStoreSelect);
                workingFrame.setTypeSelected(temStoreSelect);
                dispose();
            }
        });
    }
}