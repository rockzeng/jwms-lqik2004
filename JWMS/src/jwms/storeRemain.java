package jwms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import method.dbOperation;

/**
 *
 * @author lqik2004
 */
class RemainText {

    /**
     * @param args the command line arguments
     */
    static storeRemain frame = new storeRemain();

    public static void main(String[] args) {
        // TODO code application logic here

        frame.setSize(200, 400);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        frame.setTitle("库存查询");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static Point frameLocateOnScr() {
        return frame.getLocationOnScreen();
    }
}

public class storeRemain extends JFrame {

    private int byear;
    private int bmonth;
    private int bday;
    private int eyear;
    private int emonth;
    private int eday;
    private String store;
    private DefaultTableModel model1 = new modelStoreRemain();
    private JTable table1 = new JTable(model1);
    private JComboBox storeComboBox = new JComboBox();
    private JLabel states = new JLabel("已选仓库：");
    private static JLabel storel = new JLabel();
    private static List liststore = new Vector();
    choicePopFrameRemain rFrame = new choicePopFrameRemain();
    private JButton confirm = new JButton("查询");

    public storeRemain() {
        try {
            storeLoad();
        } catch (SQLException ex) {
            Logger.getLogger(storeRemain.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object[] colName1 = new Object[3];
        colName1[0] = "商品名称";
        colName1[1] = "商品数量";
        colName1[2] = "仓库";
        model1.setColumnCount(3);
        model1.setRowCount(0);
        model1.setColumnIdentifiers(colName1);//定义列名
        table1.getColumnModel().getColumn(0).setPreferredWidth(130);
        table1.getColumnModel().getColumn(1).setPreferredWidth(70);
        table1.getColumnModel().getColumn(2).setPreferredWidth(70);
        JScrollPane panel1 = new JScrollPane(table1);
        panel1.setPreferredSize(new Dimension(270, 400));

        Box hbox0 = Box.createHorizontalBox();
        hbox0.add(storeComboBox);
        hbox0.add(Box.createHorizontalStrut(20));
        hbox0.add(confirm);
        hbox0.add(Box.createHorizontalGlue());

        Box hbox1 = Box.createHorizontalBox();
        hbox1.add(states);
        //hbox0.add(Box.createHorizontalStrut(5));
        hbox1.add(storel);
        hbox1.add(Box.createHorizontalGlue());

        Box vbox = Box.createVerticalBox();
        vbox.add(hbox0);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(hbox1);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(panel1);
        vbox.add(Box.createVerticalGlue());
        add(vbox);

        storeComboBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (storeComboBox.getSelectedItem() == "更多组合...") {
                    Point point = RemainText.frameLocateOnScr();
                    rFrame.setLocation(point.x - 100, point.y + 30);//设置窗口停靠，自动生成在主窗口左侧
                    rFrame.setSize(100, 300);
                    rFrame.setUndecorated(true);//隐藏标题栏
                    rFrame.setVisible(true);
                } else {//实现弹出窗口的自动关闭和打开
                    rFrame.dispose();
                    setStoreSelected(storeComboBox.getSelectedItem().toString().trim());
                }
            }
        });
        confirm.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
    }

    public static void setStoreSelected(String x) {
        storel.setText(x);
    }

    public static void setListStore(String x) {
        if (x == "clear") {
            liststore.clear();
        } else {
            liststore.add(x);
        }
    }

    public void search() {
        new Thread() {

            @Override
            public void run() {
                model1.setRowCount(0);
                String sql;
                ResultSet rs = null;
                String info;
                int amount;
                int rowTag = 0;
                if (storeComboBox.getSelectedItem().toString() == "全部仓库") {
                    sql = "select info,amount,store from maint order by store";
                    System.out.print(sql);
                    dbOperation stable = new dbOperation();
                    stable.DBConnect();
                    try {
                        rs = stable.DBSqlQuery(sql);
                        while (rs.next()) {
                            Object[] data = new Object[3];
                            data[0] = rs.getString(1).trim();
                            data[1] = rs.getString(2).trim();
                            data[2] = rs.getString(3).trim();
                            /*  table1.setValueAt(rs.getString(1).trim(), rowTag, 0);
                            table1.setValueAt(rs.getString(2).trim(), rowTag, 1);
                            table1.setValueAt(rs.getString(3).trim(), rowTag, 2);*/
                            model1.addRow(data);
                        }
                        //设置表格
                        stable.DBClosed();
                    } catch (SQLException ex) {
                        Logger.getLogger(storeRemain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (storeComboBox.getSelectedItem().toString() == "更多组合...") {
                    for (int i = 0; i < liststore.size(); i++) {
                        store = liststore.get(i).toString().trim();
                        sql = "select info,amount,store from maint where store='" + store + "' ";
                        System.out.print(sql);
                        dbOperation stable = new dbOperation();
                        stable.DBConnect();
                        try {
                            rs = stable.DBSqlQuery(sql);
                            while (rs.next()) {
                                Object[] data = new Object[3];
                                data[0] = rs.getString(1).trim();
                                data[1] = rs.getString(2).trim();
                                data[2] = rs.getString(3).trim();
                                /*  table1.setValueAt(rs.getString(1).trim(), rowTag, 0);
                                table1.setValueAt(rs.getString(2).trim(), rowTag, 1);
                                table1.setValueAt(rs.getString(3).trim(), rowTag, 2);*/
                                model1.addRow(data);
                            }
                            //设置表格
                            stable.DBClosed();
                        } catch (SQLException ex) {
                            Logger.getLogger(storeRemain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    store = storeComboBox.getSelectedItem().toString().trim();
                    sql = "select info,amount,store from maint where store='" + store + "' ";
                    System.out.print(sql);
                    dbOperation stable = new dbOperation();
                    stable.DBConnect();
                    try {
                        rs = stable.DBSqlQuery(sql);
                        while (rs.next()) {
                            Object[] data = new Object[3];
                            data[0] = rs.getString(1).trim();
                            data[1] = rs.getString(2).trim();
                            data[2] = rs.getString(3).trim();
                            /*  table1.setValueAt(rs.getString(1).trim(), rowTag, 0);
                            table1.setValueAt(rs.getString(2).trim(), rowTag, 1);
                            table1.setValueAt(rs.getString(3).trim(), rowTag, 2);*/
                            model1.addRow(data);
                        }
                        //设置表格
                        stable.DBClosed();
                    } catch (SQLException ex) {
                        Logger.getLogger(storeRemain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
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
                rFrame.cb.addItem(rs.getString(1).trim());
            }

            rFrame.cb.addItem("");
            storeLoad.DBClosed();
        } catch (SQLException ex) {
            Logger.getLogger(sellFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        storeComboBox.addItem("更多组合...");
    }
}

class modelStoreRemain extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

class choicePopFrameRemain extends JFrame {

    public JComboBox cb;        //开放使得可以loadstore方法可以写入数据
    private DefaultTableModel model = new DefaultTableModel();
    private JTable table = new JTable(model);
    private JButton confirmBt = new JButton("确认");

    public choicePopFrameRemain() {
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
                storeRemain.setListStore("clear");
                String temStoreSelect = "";
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (model.getValueAt(i, 0) != null) {
                        storeRemain.setListStore(model.getValueAt(i, 0).toString());
                        temStoreSelect = temStoreSelect + model.getValueAt(i, 0).toString().trim() + "  ";
                    }
                }
                System.out.print(temStoreSelect);
                storeRemain.setStoreSelected(temStoreSelect.trim());
                dispose();
            }
        });
    }
}