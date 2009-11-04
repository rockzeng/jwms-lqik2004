package jwms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import method.*;
import method.inputIDMake;

/**
 *
 * @author lqik2004
 */
public class sellUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        sellFrame frame = new sellFrame();
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
    private short sellORreturn;
    private int tagrow = 0;
    int sumvalues = 0;
    float sumprice = 0;
    Object[] items = null;
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 647;
    private JComboBox NameCombo;
    private JTextField ID = new JTextField(12);//x20090330***  共12位
    private JComboBox year = new JComboBox(Objyear);//增加了自动选择时间功能
    private JComboBox month = new JComboBox(Objmonth);
    private JComboBox day = new JComboBox(Objday);
    private JComboBox storeComboBox = new JComboBox();
    private TableModel model = new PlanetTableModel();
    private JTable table = new JTable(model);
    private JTextField sumPrice = new JTextField(6);// 总计金额最多6位，包括小数点和小数点后一位
    private JTextField sumValues = new JTextField(3);
    private static int exceptionTag = 0;  //异常标记，比如如果没有正确写入信息到数据库就会改写exceptionTag
    //使得table中的“总金额”一列可以修改，但在修改数量或者单价还会自动修改
    private String[] tableOldInfo = new String[model.getRowCount()];
    private String[] tableOldAmount = new String[model.getRowCount()];
    private String[] tableOldPrice = new String[model.getRowCount()];
    private String IDString = null;

    public static void setExTag(int tag) {
        exceptionTag = tag;
    }

    public sellFrame() throws Exception {
        //初始化数据库，读入信息
        storeLoad();//读入仓库信息
        items = infoLoad();//读入info信息
        setTitle("销售退货单");//设置标题栏名称
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);//设置大小
        //设置单选按钮
        //同时设置了sellORreturn的值，sell->0,return->1
        ButtonGroup group = new ButtonGroup();//设置按钮组，保证只能单选
        JRadioButton sell = new JRadioButton("销售", true);
        sell.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sellORreturn = 0;
                //System.out.println(sellORreturn);
            }
        });
        JRadioButton Return = new JRadioButton("退货", false);
        Return.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sellORreturn = 1;
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

        table.setRowSelectionAllowed(false);    //不可选中行
        // table.setShowHorizontalLines(false);
        addEditEvent(table);    //给table加入了对键盘鼠标的事件响应
        // set up renderers and editors
        //table.setDefaultRenderer(Color.class, new ColorTableCellRenderer());
        //table.setDefaultEditor(Color.class, new ColorTableCellEditor());
        //排序内容
        //java.util.ArrayList list = new java.util.ArrayList(Arrays.asList(items));
        //Collections.sort(list);
        //JComboBox cmb = new JAutoCompleteComboBox(list.toArray());
        //Arrays.sort(items);//对item进行排序
        AutoCompleter.setItems(items);  //把infoLoad()得来的信息传递给
        //把单元格改造成JAutoCompleteComboBox
        NameCombo = new JAutoCompleteComboBox(items);
        NameCombo.addActionListener(NameCombo);


        TableColumnModel columnModel = table.getColumnModel();
        TableColumn NameColumn = columnModel.getColumn(1);
        NameColumn.setCellEditor(new DefaultCellEditor(NameCombo));
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//自动调整方式，未开启
        table.getColumnModel().getColumn(0).setPreferredWidth(30);//设置第一列列宽
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        //table.getColumnModel().getColumn(3).setPreferredWidth(6);
        table.setDefaultRenderer(Object.class, new ColorRenderer());    //设置每一行的背景颜色
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
        /** addStore.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
        storeComboBox.setEditable(true);//设置仓库JComboBox可编辑
        storeComboBox.setSelectedIndex(-1);//把内容清空，提高用户体验
        }
        });*/
        //退出按钮设计
        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
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

                    for (int i = 0; i < model.getRowCount(); i++) {     //防止出现中间出现断行丢失数据的问题
                        if (model.getValueAt(i, 1).toString() != "") {  //如果字符串没有，那么此行写入数据库，继续下一行
                            sellBt.setNum(model.getValueAt(i, 0).toString());
                            sellBt.setInfo(model.getValueAt(i, 1).toString());
                            sellBt.setAmount(model.getValueAt(i, 2).toString());
                            sellBt.setOutPrice(model.getValueAt(i, 3).toString());
                            sellBt.setOthers(model.getValueAt(i, 5).toString());
                            sellBt.setSellORreturn(sellORreturn);
                            //sellBt.test();
                            //根据单选按钮的信息来选择使用哪个方法
                            if (sellORreturn == 0) {
                                sellBt.transmitSell();
                            } else if (sellORreturn == 1) {
                                sellBt.transmitReturn();
                            }
                        }
                    }
                    //把ID号写入文件中
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

    /**
     * 新增加了两个方法：storeLoad和infoLoad
     * 实现了数据从数据库的读取
     */
    //从storet中读取仓库的信息，比如有多少个仓库
    private void storeLoad() {
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
            }
            storeLoad.DBClosed();
        } catch (SQLException ex) {
            Logger.getLogger(sellFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //在maint中读取商品信息info，目的：提供给“自动完成”模块使用。

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

    private void addEditEvent(JTable tb) {

        //分别添加了鼠标事件和键盘事件
        tb.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable tb = (JTable) e.getSource();
                addKeyDowntoEditEvent(tb);
            }
        });
        //tb.addToolTipText("上下键及Tab键进入编辑状态，Esc取消编辑状态");
        tb.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
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
                }
                tb.scrollRectToVisible(new java.awt.Rectangle((selectingcol - 1) *
                        tb.getColumnModel().getColumn(0).getWidth(), (selectingrow - 1) * tb.getRowHeight(), 200, 200));
                ResultSet rs = null;
                //信息改变事件
                if (tableOldInfo[selectingrow] != model.getValueAt(selectingrow, 1)) {
                    String amount = null;
                    String out = null;
                    dbOperation findMain = new dbOperation();
                    findMain.DBConnect();
                    String sql = "select distinct amount,outPrice from maint where " +
                            "info='" + model.getValueAt(selectingrow, 1).toString() + "' " +
                            "and store='"+storeComboBox.getSelectedItem().toString().trim()+"'";
                    rs = findMain.DBSqlQuery(sql);
                    while (rs.next()) {
                        amount = rs.getString(1);
                        out = rs.getString(2);
                        break;
                    }
                    findMain.DBClosed();
                    model.setValueAt(amount, selectingrow, 2);
                    model.setValueAt(out, selectingrow, 3);
                    float value = Float.parseFloat(model.getValueAt(selectingrow, 2).toString().trim());
                    float price = Float.parseFloat(model.getValueAt(selectingrow, 3).toString().trim());
                    float sp = value * price;
                    model.setValueAt(String.valueOf(sp), selectingrow, 4);
                    tableOldAmount[selectingrow] = amount;
                    tableOldPrice[selectingrow] = out;
                    tableOldInfo[selectingrow] = model.getValueAt(selectingrow, 1).toString();
                    table.repaint();
                }
                //数量或者价格改变
                if (tableOldAmount[selectingrow] != model.getValueAt(selectingrow, 2) ||
                        tableOldPrice[selectingrow] != model.getValueAt(selectingrow, 3)) {
                    float value = Float.parseFloat(model.getValueAt(selectingrow, 2).toString().trim());
                    float price = Float.parseFloat(model.getValueAt(selectingrow, 3).toString().trim());
                    float sp = value * price;
                    model.setValueAt(String.valueOf(sp), selectingrow, 4);
                    tableOldAmount[selectingrow] = model.getValueAt(selectingrow, 2).toString().trim();
                    tableOldPrice[selectingrow] = model.getValueAt(selectingrow, 3).toString().trim();
                    table.repaint();
                }
                sumprice = 0;//清空总价
                sumvalues = 0;//清空总数量
                for (int i = 0; i <= model.getRowCount(); i++) {
                    String sprice = model.getValueAt(i, 4).toString().trim();
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

class ColorRenderer extends DefaultTableCellRenderer {

    public ColorRenderer() {
        super();
        //setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    }
    Color customCol = new Color(215, 226, 247);

    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected) {
            comp.setForeground(table.getSelectionForeground());
            comp.setBackground(table.getSelectionBackground());
        } else if (row % 2 == 0) {
            comp.setForeground(table.getForeground());
            comp.setBackground(Color.white);
        } else {
            comp.setForeground(table.getForeground());
            comp.setBackground(customCol);
        }
        return comp;
    }
}

class PlanetTableModel extends AbstractTableModel {

    public static final int NAME = 1;
    public static final int VALUES = 2;
    public static final int PRICE = 3;
    public static final int SUM = 4;
    private static Object[][] cells = new Object[Integer.parseInt(propertiesRW.proIDMakeRead("tablerow"))][5];
    private String[] columnNames = {"编号", "商品名称", "数量", "单价", "金额"};

    @SuppressWarnings("empty-statement")
    public PlanetTableModel() {
        for (int i = 0; i < Integer.parseInt(propertiesRW.proIDMakeRead("tablerow")); i++) {
            for (int k = 0; k < 5; k++) {
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
        return c == NAME || c == VALUES || c == PRICE || c == SUM;
    }
}

