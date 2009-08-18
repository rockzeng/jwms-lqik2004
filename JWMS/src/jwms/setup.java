package jwms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
        frame.setSize(300, 100);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}

class setupDialog extends JWindow {

    private JButton initInfo = new JButton("初始化仓储信息");
    private JButton initProgram = new JButton("初始化程序设置");
    private JButton clear0info =new JButton("清空零库存");

    static initInfoFrame frame = null;

    public setupDialog() {
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createHorizontalStrut(10));
        vbox.add(initInfo);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(initProgram);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(clear0info);
        vbox.add(Box.createVerticalGlue());
        add(vbox);
        initInfo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

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
        clear0info.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               Clear0Info c=new Clear0Info();
               JOptionPane.showMessageDialog(null, "零库存已清除完毕");
            }
        });
    }
    
    public static Point frameLocateOnScr() {
        return frame.getLocationOnScreen();
    }
}

class importSetupDialog extends JFrame {
    /*
    @param filename 输入文件名；
     * @param sheetNum  输入工作簿的序号，从0开始；
     * @param beRow 工作表开始的行号，从0开始（例如：第一行，即为0）；
     * @param endRow 工作表结束的行号；
     * @param infoNum  “商品全名”列号从零开始，（例如：A，为0）；
     * @param amountNum “库存数量”；
     * @param priceNum  “成本均价”；
     * @param allPrsNum “库存总价”；
     */

    private JLabel sheetNumLabel = new JLabel("输入工作簿序号");
    private JLabel beRowLabel = new JLabel("开始行数：");
    private JLabel enRowLabel = new JLabel("结束行数：");
    private JLabel infoNumLabel = new JLabel("商品全名列号：");
    private JLabel amountNumLabel = new JLabel("库存数量列号：");
    private JLabel priceNumLabel = new JLabel("成本均价列号：");
    private JLabel allPrsNumLabel = new JLabel("库存总价列号：");
    private JTextField sheetNum = new JTextField("0", 2);
    private JTextField beRow = new JTextField(3);
    private JTextField enRow = new JTextField(3);
    private JTextField infoNum = new JTextField("3", 2);
    private JTextField amountNum = new JTextField("4", 2);
    private JTextField priceNum = new JTextField("5", 2);
    private JTextField allPrsNum = new JTextField("6", 2);
    private JButton filename = new JButton("选择Excel文件");
    private JButton confirm = new JButton("确定");
    private String file = null;
    public static Object[][] array = null;
    public static int arrayRowCount;

    public importSetupDialog() {
        sheetNum.setEditable(true);
        sheetNum.setEnabled(true);
        beRow.setEditable(true);
        beRow.setEnabled(true);
        enRow.setEditable(true);
        infoNum.setEditable(true);
        amountNum.setEditable(true);
        priceNum.setEditable(true);
        allPrsNum.setEditable(true);

        Box vbox = Box.createVerticalBox();
        Box hbox0 = Box.createHorizontalBox();
        hbox0.add(sheetNumLabel);
        hbox0.add(sheetNum);
        Box hbox1 = Box.createHorizontalBox();
        hbox1.add(beRowLabel);
        hbox1.add(beRow);
        Box hbox2 = Box.createHorizontalBox();
        hbox2.add(enRowLabel);
        hbox2.add(enRow);
        Box hbox3 = Box.createHorizontalBox();
        hbox3.add(infoNumLabel);
        hbox3.add(infoNum);
        Box hbox4 = Box.createHorizontalBox();
        hbox4.add(amountNumLabel);
        hbox4.add(amountNum);
        Box hbox5 = Box.createHorizontalBox();
        hbox5.add(priceNumLabel);
        hbox5.add(priceNum);
        Box hbox6 = Box.createHorizontalBox();
        hbox6.add(allPrsNumLabel);
        hbox6.add(allPrsNum);
        Box hbox7 = Box.createHorizontalBox();
        hbox7.add(filename);
        hbox7.add(Box.createHorizontalGlue());
        Box hbox8 = Box.createHorizontalBox();
        hbox8.add(confirm);
        vbox.add(hbox0);
        vbox.add(hbox1);
        vbox.add(hbox2);
        vbox.add(hbox3);
        vbox.add(hbox4);
        vbox.add(hbox5);
        vbox.add(hbox6);
        vbox.add(hbox7);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(hbox8);
        add(vbox);
        filename.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();//默认从我的文档打开文件
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile().getAbsolutePath();
                }


//                JFileChooser chooser = new JFileChooser();
//                File file = chooser.getSelectedFile();
//                ReadExcel rd = new ReadExcel();

//                rd.ReadExcelforInitStore(file, sumvalues, ERROR, tagrow, WIDTH, WIDTH, WIDTH, tagrow);

            }
        });
        confirm.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (file.equals(null)) {
                    JOptionPane.showMessageDialog(null, "请选择一个Excel文件");
                } else {
                    ReadExcel rd = new ReadExcel();
                    int berow = Integer.parseInt(beRow.getText());
                    int enrow = Integer.parseInt(enRow.getText());
                    int infonum = Integer.parseInt(infoNum.getText());
                    int amoutnum = Integer.parseInt(amountNum.getText());
                    int pricenum = Integer.parseInt(priceNum.getText());
                    int allprsnum = Integer.parseInt(allPrsNum.getText());

                    array = rd.ReadExcelforInitStore(file, Integer.parseInt(sheetNum.getText()),
                            berow - 1, enrow - 1,
                            infonum - 1, amoutnum - 1,
                            pricenum - 1, allprsnum - 1);

                    System.out.println(array[0][1]);
                    arrayRowCount = enrow - berow + 1;
                    initInfoFrame.refreshTable();
                    dispose();
                }

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
    public static  ArrayList list = new ArrayList();
    private static TableModel  model = new initInfoPlanetTableModel();
    private JTable table = new JTable(model);
    private JTextField sumPrice = new JTextField(6);// 总计金额最多6位，包括小数点和小数点后一位
    private JTextField sumValues = new JTextField(3);
    private static int exceptionTag = 0;  //异常标记
    boolean importSetupdiaSwitch = true;
    importSetupDialog im = new importSetupDialog();


    public static void setExTag(int tag) {
        exceptionTag = tag;
    }

    public initInfoFrame() throws Exception {
       /* for(int i=0;i<Integer.parseInt(propertiesRW.proIDMakeRead("tablerow"));i++){
            columnBean p=new columnBean();
            list.add(p);
        }*/
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
        JButton importFromExcel = new JButton("从Excel导入");
        Box hbox2 = Box.createHorizontalBox();
        hbox2.add(Box.createHorizontalStrut(5));
        hbox2.add(labelStore);
        hbox2.add(storeComboBox);
        hbox2.add(Box.createHorizontalStrut(20));
        hbox2.add(addStore);
        hbox2.add(Box.createHorizontalStrut(20));
        hbox2.add(importFromExcel);
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
        /*
         * 从excel导入
         * 
         */

        importFromExcel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                /*完成单击按钮打开和关闭效果*/
                if (importSetupdiaSwitch) {
                    Point point = setupDialog.frameLocateOnScr();
                    im.setSize(150, 220);
                    im.setLocation(point.x + 400, point.y + 30);
                    im.setTitle("设置导入Excel文件属性");
                    im.setUndecorated(true);
                    im.setVisible(true);
                    importSetupdiaSwitch = false;
                } else {
                    im.dispose();
                    importSetupdiaSwitch = true;
                }

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

    public static void refreshTable(){
        for(int i=0;i<importSetupDialog.arrayRowCount;i++){
          /*  columnBean c=new columnBean();
            c.setNum(String.valueOf(i+1));
            c.setInfo(importSetupDialog.array[i][0].toString());
            c.setAmount(importSetupDialog.array[i][1].toString());
            c.setInPrice(importSetupDialog.array[i][2].toString());
            c.setSumPrice(importSetupDialog.array[i][3].toString());
            */
                model.setValueAt(String.valueOf(i+1), i, 0);
                model.setValueAt(importSetupDialog.array[i][0].toString(), i, 1);
                model.setValueAt(importSetupDialog.array[i][1].toString(), i, 2);
                model.setValueAt(importSetupDialog.array[i][2].toString(), i, 3);
                model.setValueAt(importSetupDialog.array[i][3].toString(), i, 5);

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
}/*
class initInfoPlanetTableModel extends AbstractTableModel {

    private ArrayList list = new ArrayList();
    private String[] column = {"编号", "商品名称", "数量", "入库单价", "零售价", "合计金额"};

    public initInfoPlanetTableModel() {

    }

    public initInfoPlanetTableModel(ArrayList list) {
        this();
        setList(list);
    }

    public int getColumnCount() {
        return column.length;
    }

    public int getRowCount() {
        return list.size();
    }

    public Object getValueAt(int arg0, int arg1) {
        columnBean p = (columnBean) list.get(arg0);
        return getPropertyValueByCol(p, arg1);
    }

    @Override
    public void setValueAt(Object arg0, int arg1, int arg2) {
        columnBean p = (columnBean) list.get(arg1);
        switch (arg2) {
            case 0:
                p.setNum(arg0.toString());
                return;
            case 1:
                p.setInfo(arg0.toString());
                return;
            case 2:
                p.setAmount(arg0.toString());
                return;
            case 3:
                p.setInPrice(arg0.toString());
                return;
            case 4:
                p.setOutPrice(arg0.toString());
                return;
            case 5:
                p.setSumPrice(arg0.toString());
                return;
        }
        super.setValueAt(arg0, arg1, arg2);
        fireTableDataChanged();
    }

    public void addList(int index, columnBean p) {
        if (index < 0 || index > list.size() - 1) {
            list.add(p);
            fireTableRowsInserted(list.size(), list.size());
        } else {
            list.add(index, p);
            fireTableRowsInserted(index, index);
        }
    }

    public boolean deleteList(int index) {
        if (index >= 0 && index < list.size()) {
            list.remove(index);
            fireTableRowsDeleted(index, index);
            return true;

        } else {
            return false;
        }
    }

    public boolean updateList(int index, columnBean p) {
        if (index >= 0 && index < list.size()) {
            list.set(index, p);
            fireTableRowsUpdated(index, index);
            return true;
        } else {
            return false;
        }
    }

    public columnBean getList(int index) {
        if (index >= 0 && index < list.size()) {
            return (columnBean) list.get(index);

        } else {
            return null;
        }
    }

    public ArrayList getList() {
        return list;
    }

    public void setList(ArrayList list) {
        this.list = list;
        fireTableDataChanged();
    }

    public String getColumnName(int i) {
        return column[i];
    }

    public void setColumn(String[] column) {
        this.column = column;
    }

    public Object getPropertyValueByCol(columnBean p, int col) {
        switch (col) {
            case 0:
                return p.getNum();
            case 1:
                return p.getInfo();
            case 2:
                return p.getAmount();
            case 3:
                return p.getInprice();
            case 4:
                return p.getOutprice();
            case 5:
                return p.getSumPrice();
        }
        return null;
    }

    public boolean isCellEditable(int row, int c) {
        return c == 1 || c == 2 || c == 3 || c == 4 || c == 5;
    }
}

class columnBean {

    private String num=null;
    private String info=null;
    private String amount=null;
    private String inPrice=null;
    private String outPrice=null;
    private String sumPrice=null;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getInprice() {
        return inPrice;
    }

    public void setInPrice(String inprice) {
        this.inPrice = inprice;
    }

    public String getOutprice() {
        return outPrice;
    }

    public void setOutPrice(String outprice) {
        this.outPrice = outprice;
    }

    public String getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }
}
*/