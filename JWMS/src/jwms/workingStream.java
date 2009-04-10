/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Administrator
 */
public class workingStream {

    static workingFrame frame = new workingFrame();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        frame.setLocationRelativeTo(null);//一句让窗口居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    private int WIDTH = 800;
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
    private static List listtype = new Vector();
    private static JLabel storeSelect = new JLabel();
    private static JLabel typeSelect = new JLabel();

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
                    Point point = workingStream.frameLocateOnScr();
                    storePop.setLocation(point.x - 100, point.y + 30);//设置窗口停靠，自动生成在主窗口左侧
                    storePop.setSize(100, 300);
                    storePop.setUndecorated(true);//隐藏标题栏
                    storePop.setVisible(true);
                } else {//实现弹出窗口的自动关闭和打开
                    storePop.dispose();
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
                }
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

class choicePopFrameType extends JFrame {

    public JComboBox cb;
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);

    public choicePopFrameType() {
        Object[] colName = new Object[1];
        colName[0] = "更多单据组合...";
        Object[] type = {"销售单", "进货单", "同价调拨单", "报损单", "报益单", "销售退货单", "进货退货单"};
        model.setColumnCount(1);
        model.setRowCount(7);
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
                String temStoreSelect = "";
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (model.getValueAt(i, 0) != null) {
                        workingFrame.setListStore(model.getValueAt(i, 0).toString());
                        temStoreSelect = temStoreSelect + model.getValueAt(i, 0).toString().trim() + "  ";
                    }
                }
                System.out.print(temStoreSelect);
                workingFrame.setTypeSelected(temStoreSelect);
                dispose();
            }
        });
    }
}