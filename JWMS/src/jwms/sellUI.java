package jwms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
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

    private Object[] store = {
        "丰南", "玉田", "丰润"
    };
    private Object[] Objyear = {
        "2009", "2010", "2011", "2012"
    };
    private Object[] Objmonth = {
        "01", "02", "03", "04", "05", "06", "07", "09", "10", "11", "12"
    };
    private Object[] Objday = {
        "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    };
    Object[] items = new Object[]{
        "zzz", "zba", "aab", "abc", "acb", "dfg", "aba", "hpp", "pp", "hlp"
    };
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    private JComboBox NameCombo;
    private JTextField ID = new JTextField(12);//x20090330***  共12位
    private JComboBox year = new JComboBox(Objyear);//增加了自动选择时间功能
    private JComboBox month = new JComboBox(Objmonth);
    private JComboBox day = new JComboBox(Objday);
    private JComboBox storeComboBox = new JAutoCompleteComboBox(store);
    private TableModel model = new PlanetTableModel();
    private JTable table = new JTable(model);
    private JTextField sumPrice = new JTextField(6);// 总计金额最多6位，包括小数点和小数点后一位
    private JTextField sumValues = new JTextField(3);

    public sellFrame() throws Exception {
        setTitle("销售退货单");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        //设置ID
        JLabel labelID = new JLabel("编号：");
        ID.setEditable(false);//不可修改        
        ID.setText(new idMake().idMake("S"));   //设置编号，销售单以S开头
        ID.setMaximumSize(ID.getPreferredSize());   //使在箱式布局下不会默认取得最大值，保持预定义大小
        Box hbox0 = Box.createHorizontalBox();
        hbox0.add(Box.createHorizontalGlue());
        hbox0.add(labelID);
        hbox0.add(ID);
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
        JButton addStore=new JButton("添加仓库");
        Box hbox2 = Box.createHorizontalBox();
        hbox2.add(labelStore);
        hbox2.add(storeComboBox);
        hbox2.add(Box.createHorizontalStrut(10));
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
        //把单元格改造成JAutoCompleteComboBox
        NameCombo = new JAutoCompleteComboBox(items);
        NameCombo.addActionListener(NameCombo);

        TableColumnModel columnModel = table.getColumnModel();
        TableColumn NameColumn = columnModel.getColumn(1);
        NameColumn.setCellEditor(new DefaultCellEditor(NameCombo));
        //为“编号”列赋初值
        for(int i=0;i<model.getRowCount();i++){ 
        model.setValueAt(i+1, i,0);}
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
        vbox.add(Box.createVerticalStrut(4));
        vbox.add(hbox2);
        vbox.add(Box.createVerticalStrut(4));
        vbox.add(new JScrollPane(table));
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
                try {
                    //tagJudgeRW.writeFile("tag", idMake.tag);  老方法，此文件在测试包中的oldPacket
                    //tagJudgeRW.writeFile("judge", idMake.judge);
                    propertiesRW.proIDMakeWrite("tag", idMake.tag);
                    propertiesRW.proIDMakeWrite("judge", idMake.judge);
                    //把现在使用的仓库写入到properties文件，等下次打开时自动变成上次使用的仓库
                    propertiesRW.proIDMakeWrite("storeSell",storeComboBox.getSelectedIndex());
                } catch (IOException ex) {
                    Logger.getLogger(sellFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            sell2Main sellBt=new sell2Main();//定义一个新的对象，用以传输数据；
                sellBt.setID(ID.getText());
                sellBt.setYear(year.getSelectedItem().toString());
                sellBt.setMonth(month.getSelectedItem().toString());
                sellBt.setDay(day.getSelectedItem().toString());
                sellBt.setStore(storeComboBox.getSelectedItem().toString());
                //未完成：如果是新加入的仓库，把新仓库加入到“仓库”数据库中；并且设置这个仓库为首选仓库修改properties文件
                for(int i=0;i<model.getRowCount();i++){
                    sellBt.setNum(model.getValueAt(i, 0).toString());
                sellBt.setInfo(model.getValueAt(i, 1).toString());
                sellBt.setAmount(model.getValueAt(i, 2).toString());
                sellBt.setOutPrice(model.getValueAt(i, 3).toString());
                sellBt.setOthers(model.getValueAt(i, 4).toString());
                sellBt.test();
                }
                
                

            }
        });
    //获取信息
    //1）info
        
    

    }

    public static void addEditEvent(JTable tb) {
        //tb.addToolTipText("上下键及Tab键进入编辑状态，Esc取消编辑状态");
        tb.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                JTable tb = (JTable) e.getSource();
                addKeyDowntoEditEvent(tb, key);
            }
        });
    }

    private static void addKeyDowntoEditEvent(JTable tb, int key) {
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

                //                                 tb.setRowSelectionInterval(selectingrow,selectingrow);   
                //                                 tb.setColumnSelectionInterval(selectingcol,selectingcol);   
                tb.editCellAt(selectingrow, selectingcol);
                ((JTextField) ((DefaultCellEditor) tb.getCellEditor(selectingrow, selectingcol)).getComponent()).requestFocus();
                ((JTextField) ((DefaultCellEditor) tb.getCellEditor(selectingrow, selectingcol)).getComponent()).selectAll();
                ((JAutoCompleteComboBox) ((DefaultCellEditor) tb.getCellEditor(selectingrow, selectingcol)).getComponent()).requestFocus();
                //((JAutoCompleteComboBox) ((DefaultCellEditor) tb.getCellEditor(selectingrow, selectingcol)).getComponent()).requestFocus();
                tb.scrollRectToVisible(new java.awt.Rectangle((selectingcol - 1) * tb.getColumnModel().getColumn(0).getWidth(), (selectingrow - 1) * tb.getRowHeight(), 200, 200));
            } catch (Exception ex) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class PlanetTableModel extends AbstractTableModel {

    @Override
    public String getColumnName(int c) {
        return columnNames[c];
    }

    @Override
    public Class getColumnClass(int c) {
        return cells[0][c].getClass();
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
        return c == NAME || c == VALUES || c == PRICE || c == OTHERS;
    }
    public static final int NAME = 1;
    public static final int VALUES = 2;
    public static final int PRICE = 3;
    public static final int OTHERS = 4;
    private Object[][] cells = {
        {"", "", "", "", ""},
        {"", "", "", "", ""},
        {"", "", "", "", ""},
        {"", "", "", "", ""},
                        };
    private String[] columnNames = {"编号", "商品名称", "数量", "单价", "备注"};
}

