package jwms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import method.*;

/**
 *
 * @author lqik2004
 */
public class setup {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        setupDialog frame = new setupDialog();
//        frame.setTitle("程序设置");
        frame.setLocationRelativeTo(null);
        frame.setSize(250, 100);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}

class setupDialog extends JWindow {

    private JButton initInfo = new JButton("初始化仓储信息");
    private JButton initProgram = new JButton("初始化程序设置");

    public setupDialog() {
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createHorizontalStrut(10));
        vbox.add(initInfo);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(initProgram);
        vbox.add(Box.createVerticalGlue());
        add(vbox);
        initInfo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                initInfoFrame frame = null;
                try {
                    frame = new initInfoFrame();
                } catch (Exception ex) {
                    Logger.getLogger(setupDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame.setLocationRelativeTo(null);//一句让窗口居中
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class initInfoFrame extends JFrame {

    private float sumprice = 0; //表单中的总价
    private int sumvalues = 0;  //表单中的总数量
    private int tagrow = 0;//定义一个标记行
    Object[] items = null;
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 647;
    private JComboBox NameCombo;
    private JComboBox storeComboBox = new JComboBox();
    private TableModel model = new initInfoPlanetTableModel();
    private JTable table = new JTable(model);
    private JTextField sumPrice = new JTextField(6);// 总计金额最多6位，包括小数点和小数点后一位
    private JTextField sumValues = new JTextField(3);
    private static int exceptionTag = 0;  //异常标记

    public static void setExTag(int tag) {
        exceptionTag = tag;
    }

    public initInfoFrame() throws Exception {
        //初始化数据库，读入信息
        storeLoad();//读入仓库信息
        items = infoLoad();
        setTitle("初始化商品信息");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        //设置仓库栏
        JLabel labelStore = new JLabel("仓库：");
//        storeComboBox.setSelectedIndex(1);
        storeComboBox.setPreferredSize(new Dimension(100, 20));
        storeComboBox.setMaximumSize(storeComboBox.getPreferredSize());
        storeComboBox.setEditable(false);   //仓库不可直接修改
        JButton addStore = new JButton("添加仓库");
        Box hbox2 = Box.createHorizontalBox();
        hbox2.add(Box.createHorizontalStrut(5));
        hbox2.add(labelStore);
        hbox2.add(storeComboBox);
        hbox2.add(Box.createHorizontalStrut(20));
        hbox2.add(addStore);
        hbox2.add(Box.createHorizontalGlue());
        //加入列表栏

        table.setRowSelectionAllowed(false);
        addEditEvent(table);
        // set up renderers and editors
        //table.setDefaultRenderer(Color.class, new ColorTableCellRenderer());
        //table.setDefaultEditor(Color.class, new ColorTableCellEditor());
        //排序内容
        //java.util.ArrayList list = new java.util.ArrayList(Arrays.asList(items));
        //Collections.sort(list);
        //JComboBox cmb = new JAutoCompleteComboBox(list.toArray());
        //Arrays.sort(items);//对item进行排序
        AutoCompleter.setItems(items);
        table.setDefaultRenderer(Object.class, new ColorRenderer());    //设置每一行的背景颜色
        //把单元格改造成JAutoCompleteComboBox
        NameCombo = new JAutoCompleteComboBox(items);
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn NameColumn = columnModel.getColumn(1);
        NameColumn.setCellEditor(new DefaultCellEditor(NameCombo));

        //table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//自动调整方式，未开启
        table.getColumnModel().getColumn(0).setPreferredWidth(30);//设置第一列列宽
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        //为“编号”列赋初值
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(i + 1, i, 0);
        }
        JScrollPane tablePane = new JScrollPane(table);
        Box hboxPane = Box.createHorizontalBox();
        hboxPane.add(Box.createHorizontalStrut(5));
        hboxPane.add(tablePane);
        hboxPane.add(Box.createHorizontalStrut(5));
        //设置合计栏
        JLabel labelSumPrice = new JLabel("总价：");
        sumPrice.setEditable(false);//不可修改
        sumPrice.setMaximumSize(addStore.getPreferredSize());   //使在箱式布局下不会默认取得最大值，保持预定义大小
        JLabel labelSumValues = new JLabel("总数量：");
        sumValues.setEditable(false);//不可修改
        sumValues.setMaximumSize(addStore.getPreferredSize());   //使在箱式布局下不会默认取得最大值，保持预定义大小
        Box hbox3 = Box.createHorizontalBox();
        hbox3.add(Box.createHorizontalGlue());
        hbox3.add(labelSumValues);
        hbox3.add(sumValues);
        hbox3.add(Box.createHorizontalStrut(30));
        hbox3.add(labelSumPrice);
        hbox3.add(sumPrice);
        hbox3.add(Box.createHorizontalStrut(5));
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
        vbox.add(hbox2);
        vbox.add(Box.createVerticalStrut(8));
        vbox.add(hboxPane);
        vbox.add(Box.createVerticalStrut(4));
        vbox.add(hbox3);
        vbox.add(Box.createVerticalStrut(15));
        vbox.add(hbox4);
        vbox.add(Box.createVerticalStrut(10));
        add(vbox, BorderLayout.CENTER);
        //add(new JScrollPane(table), BorderLayout.CENTER);
        //“添加仓库”按钮设计
        addStore.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                storeComboBox.setEditable(true);//设置仓库JComboBox可编辑
                storeComboBox.setSelectedIndex(-1);//把内容清空，提高用户体验
            }
        });

        //退出按钮设计
        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        //提交按钮设计
        referButton.addActionListener(new ActionListener() {

            /**
             * 给按钮加入响应，用以“持久化”tag和judge两个文件，更新数据
             */
            public void actionPerformed(ActionEvent e) {
                int ifcontinue = JOptionPane.showConfirmDialog(null, "请确认单据过账", "单据确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (ifcontinue == JOptionPane.YES_OPTION) {
                    try {
                        if (!new addDel().isStoreExist(storeComboBox.getSelectedItem().toString())) {
                            dbOperation inputStore = new dbOperation();
                            inputStore.DBConnect();
                            String sql = "insert into storet (store) values ('" + storeComboBox.getSelectedItem().toString() + "')";
                            System.out.println(sql);
                            inputStore.DBSqlExe(sql);
                            try {
                                propertiesRW.proIDMakeWrite("storeInput", storeComboBox.getItemCount());
                            } catch (IOException ex) {
                                Logger.getLogger(inputFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(inputFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 1).toString() != "") {  //如果字符串没有，那么不进行继续写入数据库
                            transmitMain(i);
                        }
                    }
                    if (exceptionTag == 0) {
                        dispose();
                    }
                }
            }
        });
    }

    private void storeLoad() {
        dbOperation storeLoad = new dbOperation();
        storeLoad.DBConnect();
        String sql = "select store from storet";
        ResultSet rs = null;
        try {
            rs = storeLoad.DBSqlQuery(sql);
            while (rs.next()) {
                storeComboBox.addItem(rs.getString(1).trim());
            }
            storeLoad.DBClosed();
        } catch (SQLException ex) {
            Logger.getLogger(inputFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Object[] infoLoad() {
        List v = new Vector();
        dbOperation infoLoad = new dbOperation();
        infoLoad.DBConnect();
        String sql = "select distinct info from maint";
        ResultSet rs = null;
        try {
            rs = infoLoad.DBSqlQuery(sql);
            while (rs.next()) {
                v.add(rs.getString("info").trim());
            }

        } catch (SQLException ex) {
            Logger.getLogger(AutoCompleter.class.getName()).log(Level.SEVERE, null, ex);
        }
        infoLoad.DBClosed();
        return v.toArray();
    }

    public void addEditEvent(JTable tb) {
        //tb.addToolTipText("上下键及Tab键进入编辑状态，Esc取消编辑状态");
        tb.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                JTable tb = (JTable) e.getSource();
                addKeyDowntoEditEvent(tb);
            }
        });
        tb.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable tb = (JTable) e.getSource();
                addKeyDowntoEditEvent(tb);
            }
        });
    }

    private void addKeyDowntoEditEvent(JTable tb) {
        try {
            int rows = tb.getRowCount();
            int cols = tb.getColumnCount();

            int selectingrow = tb.getSelectedRow();
            int selectingcol = tb.getSelectedColumn();

            if (selectingrow < 0 || selectingcol < 0) {
                return;
            }

            try {
                tb.getCellEditor(selectingrow, selectingcol).stopCellEditing();
            } catch (Exception ex) {
            }
            /*
            switch (key) {
            /*
            case KeyEvent.VK_ENTER: {
            break;
            }
            case KeyEvent.VK_ESCAPE: {
            //stopEditing(tb);
            return;
            }
            default: {
            return;
            }
            }
             */
            try {
                /**
                if (selectingrow >= rows) {
                selectingrow = 0;
                selectingcol++;
                }
                if (selectingcol >= cols) {
                selectingcol = 0;
                }
                if (selectingcol >= cols) {
                selectingcol = 0;
                selectingrow++;
                }
                if (selectingrow >= rows) {
                selectingrow = 0;
                }
                 */
                if (!tb.isCellEditable(selectingrow, selectingcol)) {
                    return;
                }

                //                                 tb.setRowSelectionInterval(selectingrow,selectingrow);
                //                                 tb.setColumnSelectionInterval(selectingcol,selectingcol);
                tb.editCellAt(selectingrow, selectingcol);
                (((DefaultCellEditor) tb.getCellEditor(selectingrow, selectingcol)).getComponent()).requestFocus();
                ((JTextField) ((DefaultCellEditor) tb.getCellEditor(selectingrow, selectingcol)).getComponent()).selectAll();
                tb.scrollRectToVisible(new java.awt.Rectangle((selectingcol - 1) * tb.getColumnModel().getColumn(0).getWidth(), (selectingrow - 1) * tb.getRowHeight(), 200, 200));
                ResultSet rs = null;
                if (selectingrow != tagrow) { //为了处理对不同列进行的修改，特别加入判断语句，并且记录上一次保存的行数
                    /**
                     * 对Tagrow的库存价进行查找
                     */
                    if (model.getValueAt(tagrow, 3).toString() == "" && model.getValueAt(tagrow, 4).toString() == "") {
                        String in = null;
                        String out = null;
                        dbOperation findMain = new dbOperation();
                        findMain.DBConnect();
                        String sql = "select distinct inPrice,outPrice from maint where info='" + model.getValueAt(tagrow, 1).toString() + "'";
                        rs = findMain.DBSqlQuery(sql);
                        while (rs.next()) {
                            in = rs.getString(1);
                            out = rs.getString(2);
                            break;
                        }
                        findMain.DBClosed();
                        model.setValueAt(in, tagrow, 3);
                        model.setValueAt(out, tagrow, 4);
                        table.repaint();
                    }
                    /**
                     * 对tagrow 的总价进行计算
                     */
                    if (model.getValueAt(tagrow, 2).toString() != "" && model.getValueAt(tagrow, 3).toString() != "") {
                        float value = Float.parseFloat(model.getValueAt(tagrow, 2).toString().trim());
                        float price = Float.parseFloat(model.getValueAt(tagrow, 3).toString().trim());
                        float sp = value * price;
                        model.setValueAt(String.valueOf(sp), tagrow, 5);
                        table.repaint();//刷新table;
                    }
                    tagrow = selectingrow;
                }
                /**
                 * 对Tagrow的库存价进行查找
                 */
                if (model.getValueAt(selectingrow, 1).toString() != "" && model.getValueAt(selectingrow, 3).toString() == "" && model.getValueAt(selectingrow, 4).toString() == "") {
                    String in = null;
                    String out = null;
                    dbOperation findMain = new dbOperation();
                    findMain.DBConnect();
                    String sql = "select distinct inPrice,outPrice from maint where info='" + model.getValueAt(selectingrow, 1).toString() + "'";
                    rs = findMain.DBSqlQuery(sql);
                    while (rs.next()) {
                        in = rs.getString(1);
                        out = rs.getString(2);
                        break;
                    }
                    findMain.DBClosed();
                    model.setValueAt(in, selectingrow, 3);
                    model.setValueAt(out, selectingrow, 4);
                    table.repaint();
                }
                /**
                 * 对tagrow 的总价进行计算
                 */
                if (model.getValueAt(selectingrow, 2).toString() != "" && model.getValueAt(selectingrow, 3).toString() != "") {
                    float value = Float.parseFloat(model.getValueAt(selectingrow, 2).toString().trim());
                    float price = Float.parseFloat(model.getValueAt(selectingrow, 3).toString().trim());
                    float sp = value * price;
                    model.setValueAt(String.valueOf(sp), selectingrow, 5);
                    table.repaint();
                }
                sumvalues = 0;//清空总数量值
                for (int i = 0; i <= model.getRowCount(); i++) {
                    String value = model.getValueAt(i, 2).toString().trim();
                    if (value == "") {
                        break;
                    }
                    sumvalues = sumvalues + Integer.parseInt(value);
                    sumValues.setText(String.valueOf(sumvalues));
                }
                sumprice = 0;//清空总价
                for (int i = 0; i <= model.getRowCount(); i++) {
                    String value = model.getValueAt(i, 5).toString().trim();
                    if (value == "") {
                        break;
                    }
                    sumprice = sumprice + Float.parseFloat(value);
                    sumPrice.setText(String.valueOf(sumprice));
                }
            } catch (Exception ex) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void transmitMain(int selectedrow) {
        try {
            addDel mainT = new addDel();
            mainT.setAmount(model.getValueAt(selectedrow, 2).toString().trim());
            mainT.setInfo(model.getValueAt(selectedrow, 1).toString().trim());
            mainT.setStore(storeComboBox.getSelectedItem().toString().trim());
            mainT.setInPrice(model.getValueAt(selectedrow, 3).toString().trim());
            mainT.setOutPrice(model.getValueAt(selectedrow, 4).toString().trim());
            mainT.increaseMethod();
        } catch (SQLException ex) {
            Logger.getLogger(initInfoFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据未能写入，请检查是否正确设置数据库，如依旧有问题请与作者联系");
            initInfoFrame.setExTag(1);
        }
    }
}

class initInfoPlanetTableModel extends AbstractTableModel {

    public initInfoPlanetTableModel() {
        for (int i = 0; i < Integer.parseInt(propertiesRW.proIDMakeRead("tablerow")); i++) {
            for (int k = 0; k < 6; k++) {
                cells[i][k] = "";
            }
        }
    }

    @Override
    public String getColumnName(int c) {
        return columnNames[c];
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return cells.length;
    }

    public Object getValueAt(int r, int c) {
        return cells[r][c];
    }

    @Override
    public void setValueAt(Object obj, int r, int c) {
        cells[r][c] = obj;
    }

    @Override
    public boolean isCellEditable(int r, int c) {
        return c == NAME || c == VALUES || c == INPRICE || c == OUTPRICE || c == SUMPRICE;
    }
    public static final int NAME = 1;
    public static final int VALUES = 2;
    public static final int INPRICE = 3;
    public static final int OUTPRICE = 4;
    public static final int SUMPRICE = 5;
    //public static final int OTHERS = 6;
    private Object[][] cells = new Object[Integer.parseInt(propertiesRW.proIDMakeRead("tablerow"))][6];
    private String[] columnNames = {"编号", "商品名称", "数量", "入库单价", "零售价", "合计金额"};
}
