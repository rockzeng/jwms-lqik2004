package jwms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
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
public class Search {

    static searchFrame frame = new searchFrame();

    public Search() {

        frame.setLocationRelativeTo(null);//一句让窗口居中

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setVisible(true);
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
        "01", "02", "03", "04", "05", "06", "07","08", "09", "10", "11", "12"
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
    private choicePopFrameSearch storePop = new choicePopFrameSearch();//设置仓库弹出窗口
    private static List liststore = new Vector();
    public static JLabel storeSelect = new JLabel();
    DefaultTableModel model1 = new model();
    public JTable table1 = new JTable(model1);
    JLabel storeX = new JLabel();
    JLabel sumpriceX = new JLabel();
    JLabel sumvaluesX = new JLabel();
    // drawPanel drawpanel = new drawPanel();
    JButton tool0 = new JButton("销售利润");
    JButton tool1 = new JButton("销售金额");
    JButton tool2 = new JButton("销售数量");
    Box vbox = Box.createVerticalBox();
    // JProgressBar progressBar=new JProgressBar();

    @SuppressWarnings("empty-statement")
    public searchFrame() {
        tool0.setEnabled(false);
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
        storeSelect.setText("全部仓库");
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
        model1.setRowCount(0);
         table1.setDefaultRenderer(Object.class, new ColorRenderer());
        TableColumnModel tc = table1.getColumnModel();
        tc.getColumn(0).setPreferredWidth(20);
        tc.getColumn(0).setPreferredWidth(25);
        tc.getColumn(0).setPreferredWidth(15);
        model1.setColumnIdentifiers(colName1);//定义列名
        JScrollPane panel1 = new JScrollPane(table1);
        panel1.setPreferredSize(new Dimension(600, 150));

        //加入工具按钮

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


        /* drawpanel.setPreferredSize(new Dimension(600, 300));
        drawpanel.setMinimumSize(getPreferredSize());
        drawpanel.setMaximumSize(getPreferredSize());
         */
        //垂直布局

        vbox.add(Box.createVerticalStrut(5));
        vbox.add(hbox1);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox2);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox3);
        vbox.add(Box.createVerticalStrut(10));
        // vbox.add(drawpanel);
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
                model1.setRowCount(0);
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

                String byear = bYear.getSelectedItem().toString().trim();
                String bmonth = bMonth.getSelectedItem().toString().trim();
                String bday = bDay.getSelectedItem().toString().trim();
                String eyear = eYear.getSelectedItem().toString().trim();
                String emonth = eMonth.getSelectedItem().toString().trim();
                String eday = eDay.getSelectedItem().toString().trim();
                int dayValue = (Integer.parseInt(eyear) - Integer.parseInt(byear)) * 371 + (Integer.parseInt(emonth) - Integer.parseInt(bmonth)) * 31 + (Integer.parseInt(eday) - Integer.parseInt(bday));
                final ProgressBarDialog proBar = new ProgressBarDialog();
                Point point = Search.frameLocateOnScr();
                proBar.setLocation(point.x + 250, point.y + 300);//设置窗口停靠，自动生成在主窗口左侧
                proBar.adoptDeterminate(dayValue);

                new Thread() {

                    @Override
                    public void run() {

                        String sql;
                        ResultSet rs = null;
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
                        dbOperation stable = new dbOperation();
                        stable.DBConnect();
                        String bDate = byear + bmonth + bday;
                        String eDate = eyear + emonth + eday;

                        if (storeComboBox.getSelectedItem().toString().trim() == "更多组合...") {
                            int proBarValue = 0;
                            for (int k = 0; k < liststore.size(); k++) {
                                store = liststore.get(k).toString().trim();
                                sumamount = 0;
                                sumoutprice = 0;
                                income = 0;
                                sql = "select amount,outPrice,info from sellt where (store='" + store + "') and date<='" + eDate + "' and date>='" + bDate + "' and sellorreturn=0 ";
                                System.out.print(sql);
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
                                   
                                } catch (SQLException ex) {
                                    Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                proBar.setValue(proBarValue++);
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
                            int proBarValues = 0;
                            for (int k = 1; k < storeComboBox.getItemCount() - 1; k++) {
                                store = storeComboBox.getItemAt(k).toString().trim();
                                sumamount = 0;
                                sumoutprice = 0;
                                income = 0;
                                sql = "select amount,outPrice,info from sellt where (store='" + store + "') and date<='" + eDate + "' and date>='" + bDate + "' and sellorreturn=0 ";
                                System.out.print(sql);
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
                                   
                                } catch (SQLException ex) {
                                    Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                proBar.setValue(proBarValues++);
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
                            int proBarValues = 0;
                            store = storeComboBox.getSelectedItem().toString().trim();
                            sumamount = 0;
                            sumoutprice = 0;
                            income = 0;
                            sql = "select amount,outPrice,info from sellt where (store='" + store + "') and date<='" + eDate + "' and date>='" + bDate + "' and sellorreturn=0 ";
                            System.out.print(sql);
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
                               
                            } catch (SQLException ex) {
                                Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            proBar.setValue(proBarValues++);

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
                        stable.DBClosed();
                        int k = 0;
                        ResultSet RS = null;
                        dbOperation c = new dbOperation();
                        c.DBConnect();
                        sql = "select store,amount,sumprice,income from SearchCache";
                        try {
                            RS = c.DBSqlQuery(sql);
                            while (RS.next()) {
                                Object[] obj = new Object[4];
                                obj[0] = RS.getString(1);
                                obj[1] = RS.getString(2);
                                obj[2] = RS.getString(3);
                                obj[3] = RS.getString(4);
                                model1.addRow(obj);
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        c.DBClosed();

                        darwMethod(tool0.getText().trim());
                        proBar.finishDeterminate();

                    }
                }.start();
                proBar.setVisible(true);
            }
        });
        tool0.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                /* storeRemain remain = new storeRemain();
                if (storeComboBox.getSelectedItem().toString().trim() == "全部仓库") {
                remain.store(null);
                } else if (storeComboBox.getSelectedItem().toString().trim() == "更多组合") {
                //整理仓库信息，直接变成可执行的SQL语句块
                String tem = "store=" + liststore.get(0);
                for (int i = 1; i < liststore.size() - 1; i++) {
                tem = liststore.get(i) + " and store=";
                }
                tem = tem + liststore.get(liststore.size() - 1);
                remain.store(tem);
                } else {
                remain.store("store=" + storeComboBox.getSelectedItem().toString().trim());
                }*/
                tool1.setEnabled(true);
                tool0.setEnabled(false);
                tool2.setEnabled(true);
                //只有在查询之后才会绘制图表，防止出错
                if (table1.getValueAt(0, 0).toString() != "") {
                    darwMethod(tool0.getText().trim());
                }

            }
        });
        tool1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                tool1.setEnabled(false);
                tool0.setEnabled(true);
                tool2.setEnabled(true);
                //只有在查询之后才会绘制图表，防止出错
                if (table1.getValueAt(0, 0).toString() != "") {
                    darwMethod(tool1.getText().trim());
                }

            }
        });
        tool2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                tool1.setEnabled(true);
                tool0.setEnabled(true);
                tool2.setEnabled(false);
                //只有在查询之后才会绘制图表，防止出错
                if (table1.getValueAt(0, 0).toString() != null) {
                    darwMethod(tool2.getText().trim());
                }

            }
        });
    }

    public void darwMethod(String button) {
        int swit = -1;    //作为不同按钮的功能选择变量
        if (button == "销售利润") {
            swit = 3;
        } else if (button == "销售金额") {
            swit = 2;
        } else {
            swit = 1;
        }

        drawPanel draw = new drawPanel();
        draw.num = table1.getRowCount();
        draw.perSize = draw.standardWidth / draw.num;
        draw.maxNumSize = 0;
        draw.v.add(table1.getValueAt(0, swit)); //根据参数取得货物数量或者利润或者销售总量
        draw.vName.add(table1.getValueAt(0, 0));//取得仓库的名字
        draw.test=0;
        for (int i = 1; i <
                table1.getRowCount() && table1.getValueAt(i, 0) != ""; i++) {
            draw.v.add(table1.getValueAt(i, swit)); //根据参数取得货物数量或者利润或者销售总量
            draw.vName.add(table1.getValueAt(i, 0));//取得仓库的名字
            if (Float.parseFloat(table1.getValueAt(i, swit).toString().trim()) > draw.maxNumSize) {
                draw.maxNumSize = Float.parseFloat(table1.getValueAt(i, swit).toString().trim());
            }

        }
        //drawpanel.add(draw);
        draw.prePaint();    //提出数据处理部分，独立成为一个方法
          validate();
        add(draw);
        validate(); //更新组件，进行绘图

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
        if (x == "clear") {
            liststore.clear();
        } else {
            liststore.add(x);
        }

    }

    public static void setStoreSelected(String x) {
        storeSelect.setText(x);
    }
//设置初始数据，X为需要输入的JTABLE，y为需要输入的列
}

class drawPanel extends JPanel {

    public int standardHeight = 280;//最高块的高度
    public int standardWidth = 600;
    public int num = 1;
    //public static int perSize = standardWidth / num;//每个块的宽度
    public float maxNumSize; //取得最大的数字
    public List v = new Vector();
    public List vName = new Vector();
    public int perSize;
    private String storeName = "kdkdk";
    //数据结构：0：X,1:Y,2:WIDTH,3:HEIGHT,4:STORE    每次步进5
    //private Vector locData = new Vector();
    public ArrayList<Rectangle2D> rectes;
    private int colorChgTag = -1; //标记那个矩形的颜色会发生变化,初始量为一个不存在的index
    //public Rectangle2D current;
    public int test;

    public drawPanel() {
        rectes = new ArrayList<Rectangle2D>();
        addMouseMotionListener(new MouseMovingHandler());
    }

    public void setStore(String x) {
        storeName = x;
    }

    public void prePaint() {
        rectes.clear();//每次绘图的时候对以前保留的矩形数组进行清空
        System.out.println();
        System.out.print(rectes.isEmpty());
        for (int i = 0; i < v.size(); i++) {
            double height = ((Float.parseFloat(v.get(i).toString().trim()) * standardHeight) / maxNumSize) - 5;//防止出现最大矩形不会显示上边线的情况
            double width = perSize;
            double leftX = i * (width) + 5;//加上5个单位保持和左侧边框距离保持一段距离
            double topY = standardHeight - height;
            System.out.println(topY);
            Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
            rectes.add(rect);//把绘制的举行加入到列表中
        }
         
        test=1;
    }

    @Override
    public void paintComponent(Graphics g) {
        //update(g);
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        System.out.print(test);
        for (int i = 0; i < rectes.size(); i++) {
            if (i == colorChgTag) {
                g2.setColor(Color.BLACK);
                g2.draw(rectes.get(i));//绘制矩形
                g2.setColor(Color.BLUE);
                g2.fill(rectes.get(i));
                //g2.setFont(new Font(storeName, Font.CENTER_BASELINE, 18));
                g2.drawString(vName.get(i).toString().trim(), (float) (rectes.get(i).getX()) + 15, standardHeight + 12); //绘制矩形下方的仓库信息
            } else {
                g2.setColor(Color.BLACK);
                g2.draw(rectes.get(i));//绘制矩形
                g2.drawString(vName.get(i).toString().trim(), (float) (rectes.get(i).getX()) + 15, standardHeight + 12); //绘制矩形下方的仓库信息
            }
        }
    }
    //如果在鼠标当前位置发现矩形，那么返回矩形在arrayList中的index，没有发现则返回-1

    public int find(Point2D point) {
        for (int i = 0; i < rectes.size(); i++) {
            if (rectes.get(i).contains(point)) {
                return i;
            }
        }
        return -1;
    }

    private class MouseMovingHandler implements MouseMotionListener {

        public void mouseMoved(MouseEvent event) {
            if (find(event.getPoint()) != -1) {
                colorChgTag = find(event.getPoint());
                repaint();
            }
        }

        public void mouseDragged(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

class choicePopFrameSearch extends JFrame {

    public JComboBox cb;        //开放使得可以loadstore方法可以写入数据
    private DefaultTableModel model = new DefaultTableModel();
    private JTable table = new JTable(model);
    private JButton confirmBt = new JButton("确认");

    public choicePopFrameSearch() {
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
        Box vbox = Box.createVerticalBox();
        vbox.add(panel);
        vbox.add(confirmBt);
        add(vbox, BorderLayout.CENTER);
        confirmBt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                searchFrame.setListStore("clear");
                String temStoreSelect = "";
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (model.getValueAt(i, 0) != null) {
                        searchFrame.setListStore(model.getValueAt(i, 0).toString());
                        temStoreSelect = temStoreSelect + model.getValueAt(i, 0).toString().trim() + "  ";
                    }
                }
                System.out.print(temStoreSelect);
                searchFrame.setStoreSelected(temStoreSelect);
                dispose();
            }
        });
    }
}
