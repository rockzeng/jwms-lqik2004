package jwms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
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
    public static void main(String[] args) {
        // TODO code application logic here
        sellFrame frame = new sellFrame();
        Toolkit tool = Toolkit.getDefaultToolkit();
        Dimension screenSize = tool.getScreenSize();
        int locateHeight = (screenSize.height - frame.getHeight()) / 2;
        int locateWidth = (screenSize.width - frame.getWidth()) / 2;
        frame.setLocation(locateWidth, locateHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class sellFrame extends JFrame {

    public sellFrame() {
        setTitle("销售退货单");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        //设置ID
        JLabel labelID = new JLabel("编号：");
        JTextField ID = new JTextField(11);//x20090330**  共11位
        ID.setEditable(false);//不可修改
        ID.setMaximumSize(ID.getPreferredSize());   //使在箱式布局下不会默认取得最大值，保持预定义大小
        Box hbox0 = Box.createHorizontalBox();
        hbox0.add(Box.createHorizontalGlue());
        hbox0.add(labelID);
        hbox0.add(ID);
        //设置日期栏
        JLabel label1 = new JLabel("日期：");
        JComboBox year = new JComboBox(Objyear);//增加了自动选择时间功能
        year.setSelectedIndex(getDate.yearIndex());
        JLabel label2 = new JLabel("年");
        //JTextField month = new JTextField(2);
        JComboBox month=new JComboBox(Objmonth);
        month.setSelectedIndex(getDate.monthIndex());
        JLabel label3 = new JLabel("月");
        //JTextField day = new JTextField(2);
        JComboBox day=new JComboBox(Objday);
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
        JComboBox storeComboBox = new JAutoCompleteComboBox(store);
        Box hbox2 = Box.createHorizontalBox();
        hbox2.add(labelStore);
        hbox2.add(storeComboBox);
        hbox2.add(Box.createHorizontalGlue());
        //加入列表栏
        TableModel model = new PlanetTableModel();
        JTable table = new JTable(model);
        table.setRowSelectionAllowed(false);
        addEditEvent(table);
        // set up renderers and editors
        //table.setDefaultRenderer(Color.class, new ColorTableCellRenderer());
        //table.setDefaultEditor(Color.class, new ColorTableCellEditor());
        //排序内容
        //java.util.ArrayList list = new java.util.ArrayList(Arrays.asList(items));
        //Collections.sort(list);
        //JComboBox cmb = new JAutoCompleteComboBox(list.toArray());
                 Object[] items = new Object[]{
                         "zzz", "zba", "aab", "abc", "acb", "dfg", "aba", "hpp", "pp", "hlp"
                     };
        //Arrays.sort(items);//对item进行排序
                AutoCompleter.setItems(items);
        //把单元格改造成JAutoCompleteComboBox
                NameCombo = new JAutoCompleteComboBox(items);
                NameCombo.addActionListener(NameCombo);

        TableColumnModel columnModel = table.getColumnModel();
        TableColumn NameColumn = columnModel.getColumn(1);
        NameColumn.setCellEditor(new DefaultCellEditor(NameCombo));
        //设置合计栏
        JLabel labelSumPrice = new JLabel("总价：");
        JTextField sumPrice = new JTextField(6);// 总计金额最多6位，包括小数点和小数点后一位
        sumPrice.setEditable(false);//不可修改
        sumPrice.setMaximumSize(ID.getPreferredSize());   //使在箱式布局下不会默认取得最大值，保持预定义大小
        JLabel labelSumValues = new JLabel("总数量：");
        JTextField sumValues = new JTextField(3);
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
    private JComboBox NameCombo;
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    private Object[] store = {
        "丰南", "玉田", "丰润"
    };
    private Object[] Objyear= {
         "2009", "2010","2011","2012"
    };
     private Object[] Objmonth= {
        "01", "02", "03","04","05","06","07","09","10","11","12"
    };
      private Object[] Objday={
        "01", "02", "03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"
    };
      
     
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
    private Object[][] cells ={
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
        };
    private String[] columnNames = {"编号", "商品名称", "数量", "单价", "备注"};
}

