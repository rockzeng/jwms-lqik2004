package jwms;

import method.inputIDMake;
import java.awt.BorderLayout;
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
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import method.*;

/**
 *
 * @author lqik2004
 */
public class inputUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        inputFrame frame = new inputFrame();
        /**Toolkit tool = Toolkit.getDefaultToolkit();
        Dimension screenSize = tool.getScreenSize();
        int locateHeight = (screenSize.height - frame.getHeight()) / 2;
        int locateWidth = (screenSize.width - frame.getWidth()) / 2;
        frame.setLocation(locateWidth, locateHeight);
         */
        frame.setLocationRelativeTo(null);//一句让窗口居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class inputFrame extends JFrame {

    private Object[] Objyear = {
        "2009", "2010", "2011", "2012"
    };
    private Object[] Objmonth = {
        "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
    };
    private Object[] Objday = {
        "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    };
    private float sumprice = 0; //表单中的总价
    private int sumvalues = 0;  //表单中的总数量
    private int tagrow = 0;//定义一个标记行
    Object[] items = null;
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 647;
    private JComboBox NameCombo;
    private JTextField ID = new JTextField(12);//x20090330***  共12位
    private JComboBox year = new JComboBox(Objyear);//增加了自动选择时间功能
    private JComboBox month = new JComboBox(Objmonth);
    private JComboBox day = new JComboBox(Objday);
    private JComboBox storeComboBox = new JComboBox();
    private TableModel model = new inputPlanetTableModel();
    private JTable table = new JTable(model);
    private JTextField sumPrice = new JTextField(6);// 总计金额最多6位，包括小数点和小数点后一位
    private JTextField sumValues = new JTextField(3);
    private String[] tableOldInfo = new String[model.getRowCount()];
    private String[] tableOldAmount = new String[model.getRowCount()];
    private String[] tableOldPrice = new String[model.getRowCount()];
    private short inputORreturn = 0;
    private static int exceptionTag = 0;  //异常标记

    public static void setExTag(int tag) {
        exceptionTag = tag;
    }

    public inputFrame() throws Exception {
        //初始化数据库，读入信息
        storeLoad();//读入仓库信息
        items = infoLoad();
        setTitle("进货单");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        //设置单选按钮
        ButtonGroup group = new ButtonGroup();//设置按钮组，保证只能单选
        JRadioButton input = new JRadioButton("进货", true);
        input.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                inputORreturn = 0;
            }
        });
        JRadioButton Return = new JRadioButton("进货退货", false);
        Return.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                inputORreturn = 1;
            }
        });
        group.add(input);
        group.add(Return);
        //设置ID
        JLabel labelID = new JLabel("编号：");
        ID.setEditable(false);//不可修改
        ID.setText(new inputIDMake().showID("I", getDate.getYear(), getDate.getMonth(), getDate.getDay()));
        ID.setMaximumSize(ID.getPreferredSize());   //使在箱式布局下不会默认取得最大值，保持预定义大小
        Box hbox0 = Box.createHorizontalBox();
        hbox0.add(input);
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
        storeComboBox.setSelectedIndex(Integer.parseInt(propertiesRW.proIDMakeRead("storeInput")));
        storeComboBox.setMaximumSize(storeComboBox.getPreferredSize());
        storeComboBox.setEditable(false);   //仓库不可直接修改
        JButton addStore = new JButton("添加仓库");
        Box hbox2 = Box.createHorizontalBox();
        hbox2.add(Box.createHorizontalStrut(5));
        hbox2.add(labelStore);
        hbox2.add(storeComboBox);
        hbox2.add(Box.createHorizontalStrut(10));
        hbox2.add(addStore);
        hbox2.add(Box.createHorizontalGlue());
        //加入列表栏

        table.setRowSelectionAllowed(false);
        table.setDefaultRenderer(Object.class, new ColorRenderer());
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
        sumPrice.setMaximumSize(ID.getPreferredSize());   //使在箱式布局下不会默认取得最大值，保持预定义大小
        JLabel labelSumValues = new JLabel("总数量：");
        sumValues.setEditable(false);//不可修改
        sumValues.setMaximumSize(ID.getPreferredSize());   //使在箱式布局下不会默认取得最大值，保持预定义大小
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
        vbox.add(hbox0);
        vbox.add(Box.createVerticalStrut(4));
        vbox.add(hbox1);
        vbox.add(Box.createVerticalStrut(6));
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
                exceptionTag = 0;//对异常标签进行初始化
                int ifcontinue = JOptionPane.showConfirmDialog(null, "请确认单据过账", "单据确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (ifcontinue == JOptionPane.YES_OPTION) {
                    inputIDMake idmk = new inputIDMake();
                    input2Main inputBt = new input2Main();//定义一个新的对象，用以传输数据；

                    idmk.getYear(year.getSelectedItem().toString());
                    inputBt.setYear(year.getSelectedItem().toString());
                    idmk.getMonth(month.getSelectedItem().toString());
                    inputBt.setMonth(month.getSelectedItem().toString());
                    idmk.getDay(day.getSelectedItem().toString());
                    inputBt.setDay(day.getSelectedItem().toString());
                    inputBt.setDate(idmk.showDate());
                    inputBt.setID(idmk.alterID("I"));
                    inputBt.setStore(storeComboBox.getSelectedItem().toString());
                    //未完成：如果是新加入的仓库，把新仓库加入到“仓库”数据库中；并且设置这个仓库为首选仓库修改properties文件
                    //for (int i = 0; i < storeComboBox.getItemCount(); i++) {
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
                            inputBt.setNum(model.getValueAt(i, 0).toString());
                            inputBt.setInfo(model.getValueAt(i, 1).toString());
                            inputBt.setAmount(model.getValueAt(i, 2).toString());
                            inputBt.setInPrice(model.getValueAt(i, 3).toString());
                            inputBt.setOutPrice(model.getValueAt(i, 4).toString());
                            inputBt.setSumPrice(model.getValueAt(i, 5).toString());
                            inputBt.test();
                            inputBt.setInputORreturn(inputORreturn);
                            if (inputORreturn == 0) {
                                inputBt.transmitInput();
                            } else {
                                inputBt.transmitReturn();
                            }
                        }
                    }
                    try {
                        //把现在使用的仓库写入到properties文件，等下次打开时自动变成上次使用的仓库
                        propertiesRW.proIDMakeWrite("storeInput", storeComboBox.getSelectedIndex());
                    } catch (IOException ex) {
                        Logger.getLogger(inputFrame.class.getName()).log(Level.SEVERE, null, ex);
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
            try {
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

                if (!tb.isCellEditable(selectingrow, selectingcol)) {
                    return;
                }
                if (selectingcol == 1) {
                    tb.editCellAt(selectingrow, selectingcol);//使得选中的单元格处于编辑状态
                    (((DefaultCellEditor) tb.getCellEditor(selectingrow, selectingcol)).getComponent()).requestFocus();//当键盘或者鼠标选中单元格的时候，自动获得焦点，进入编辑模式
                    ((JTextField) ((DefaultCellEditor) tb.getCellEditor(selectingrow, selectingcol)).getComponent()).selectAll();//对与jtextfield，默认进行全选
                    tb.scrollRectToVisible(new java.awt.Rectangle((selectingcol - 1) *
                            tb.getColumnModel().getColumn(0).getWidth(), (selectingrow - 1) * tb.getRowHeight(), 200, 200));
                }
                ResultSet rs = null;
                //信息改变事件
                if (tableOldInfo[selectingrow] != model.getValueAt(selectingrow, 1)) {
                    String amount = null;
                    String out = null;
                    String in = null;
                    dbOperation findMain = new dbOperation();
                    findMain.DBConnect();
                    String sql = "select distinct amount,inPrice,outPrice from maint where " +
                            "info='" + model.getValueAt(selectingrow, 1).toString() + "'";
                    rs = findMain.DBSqlQuery(sql);
                    while (rs.next()) {
                        amount = rs.getString(1);
                        in = rs.getString(2);
                        out = rs.getString(3);
                        break;
                    }
                    findMain.DBClosed();
                    if (amount != null && in != null && out != null) {
                        model.setValueAt(amount, selectingrow, 2);
                        model.setValueAt(in, selectingrow, 3);
                        model.setValueAt(out, selectingrow, 4);
                        float value = Float.parseFloat(model.getValueAt(selectingrow, 2).toString().trim());
                        float price = Float.parseFloat(model.getValueAt(selectingrow, 3).toString().trim());
                        float sp = value * price;
                        model.setValueAt(String.valueOf(sp), selectingrow, 5);
                    }
                    tableOldAmount[selectingrow] = amount;
                    tableOldPrice[selectingrow] = in;
                    tableOldInfo[selectingrow] = model.getValueAt(selectingrow, 1).toString();
                    table.repaint();
                }
                //数量或者价格改变
                if (tableOldAmount[selectingrow] != model.getValueAt(selectingrow, 2) ||
                        tableOldPrice[selectingrow] != model.getValueAt(selectingrow, 3)) {
                    float value = Float.parseFloat(model.getValueAt(selectingrow, 2).toString().trim());
                    float price = Float.parseFloat(model.getValueAt(selectingrow, 3).toString().trim());
                    float sp = value * price;
                    model.setValueAt(String.valueOf(sp), selectingrow, 5);
                    tableOldAmount[selectingrow] = model.getValueAt(selectingrow, 2).toString().trim();
                    tableOldPrice[selectingrow] = model.getValueAt(selectingrow, 3).toString().trim();
                    table.repaint();
                }
                sumprice = 0;//清空总价
                sumvalues = 0;//清空总数量
                for (int i = 0; i <= model.getRowCount(); i++) {
                    String sprice = model.getValueAt(i, 5).toString().trim();
                    String samount = model.getValueAt(i, 2).toString().trim();
                    sumprice = sumprice + Float.parseFloat(sprice);
                    sumvalues = sumvalues + Integer.parseInt(samount);
                    sumPrice.setText(String.valueOf(sumprice));
                    sumValues.setText(String.valueOf(sumvalues));
                }
            } catch (Exception ex) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class inputPlanetTableModel extends AbstractTableModel {

    public inputPlanetTableModel() {
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

