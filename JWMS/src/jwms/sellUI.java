package jwms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
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
        JTextField year = new JTextField(4);
        JLabel label2 = new JLabel("年");
        JTextField month = new JTextField(2);
        JLabel label3 = new JLabel("月");
        JTextField day = new JTextField(2);
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
        Arrays.sort(items);//对item进行排序
        AutoCompleter.setItems(items);

        //把单元格改造成JAutoCompleteComboBox
        JComboBox moonCombo = new JAutoCompleteComboBox(items);
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn moonColumn = columnModel.getColumn(2);
        moonColumn.setCellEditor(new DefaultCellEditor(moonCombo));
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
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    private Object[] store = {
        "丰南", "玉田", "丰润"
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
        return cells[0].length;
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
        return c == PLANET_COLUMN || c == MOONS_COLUMN || c == GASEOUS_COLUMN || c == COLOR_COLUMN;
    }
    public static final int PLANET_COLUMN = 0;
    public static final int MOONS_COLUMN = 2;
    public static final int GASEOUS_COLUMN = 3;
    public static final int COLOR_COLUMN = 4;
    private Object[][] cells =
            {
        {"Mercury", 2440.0, "", false, Color.yellow, new ImageIcon("Mercury.gif")},
        {"Venus", 6052.0, "", false, Color.yellow, new ImageIcon("Venus.gif")},
        {"Earth", 6378.0, "", false, Color.blue, new ImageIcon("Earth.gif")},
        {"Mars", 3397.0, "", false, Color.red, new ImageIcon("Mars.gif")},
        {"Jupiter", 71492.0, "", true, Color.orange, new ImageIcon("Jupiter.gif")},
        {"Saturn", 60268.0, "", true, Color.orange, new ImageIcon("Saturn.gif")},
        {"Uranus", 25559.0, "", true, Color.blue, new ImageIcon("Uranus.gif")},
        {"Neptune", 24766.0, "", true, Color.blue, new ImageIcon("Neptune.gif")},
        {"Pluto", 1137.0, "", false, Color.black, new ImageIcon("Pluto.gif")}};
    private String[] columnNames = {"Planet", "Radius", "Moons", "Gaseous", "Color", "Image"};
}

