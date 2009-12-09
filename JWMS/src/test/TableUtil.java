/*
# JWMS2 is based on JWMS.JWMS is short for JeanWest store-sell Management System
# Copyright (C) 2009,res0w
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package test;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import method.AutoCompleter;
import method.JAutoCompleteComboBox;
import method.dbOperation;


/**
 * @since 2009-12-01
 * @author res0w
 * 生成包含table的panel，在UI上模块化增加代码复用率
 */
public class TableUtil implements TableUIer{

    private JTextField sumPrice = new JTextField(6);
    private JTextField sumValues = new JTextField(3);
    private TableModel model = new PlanetTableModel();
    private JTable table = new JTable(model);
    private String[] tableOldInfo = new String[model.getRowCount()];
    private String[] tableOldAmount = new String[model.getRowCount()];
    private String[] tableOldPrice = new String[model.getRowCount()];
    private final JAutoCompleteComboBox NameCombo;
    private String selectStore = null;
    private float sumprice;
    private int sumvalues;
    private DataSetUtil dsu=new DataSetUtil();
    Box vbox1 = Box.createVerticalBox();

    public TableUtil(String store) {
        Object[] items = dsu.infoLoad();//读入info信息
        selectStore = store;
        table.setRowSelectionAllowed(false);    //不可选中行
        addEditEvent(table);    //给table加入了对键盘鼠标的事件响应
        AutoCompleter.setItems(items);  //把infoLoad()得来的信息传递给
        //把单元格改造成JAutoCompleteComboBox
        NameCombo = new JAutoCompleteComboBox(items);
        NameCombo.addActionListener(NameCombo);

        TableColumnModel columnModel = table.getColumnModel();
        TableColumn NameColumn = columnModel.getColumn(1);
        NameColumn.setCellEditor(new DefaultCellEditor(NameCombo));
        table.getColumnModel().getColumn(0).setPreferredWidth(30);//设置第一列列宽
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.setDefaultRenderer(Object.class, new ColorRenderer());//设置每一行的背景颜色
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(i + 1, i, 0);
        }
        //init Layout
        JScrollPane tablePanel = new JScrollPane(table);
        tablePanel.setSize(380, 440);
        tablePanel.setPreferredSize(new Dimension(tablePanel.getSize()));
        vbox1.add(tablePanel);
        JLabel labelSumPrice = new JLabel("总价：");
        sumPrice.setEditable(false);//不可修改
        sumPrice.setMaximumSize(new Dimension(80, 30));   //使在箱式布局下不会默认取得最大值，保持预定义大小
        JLabel labelSumValues = new JLabel("总数量：");
        sumValues.setEditable(false);//不可修改
        sumValues.setMaximumSize(new Dimension(80, 30));   //使在箱式布局下不会默认取得最大值，保持预定义大小
        Box hbox = Box.createHorizontalBox();
        hbox.add(Box.createHorizontalGlue());
        hbox.add(labelSumValues);
        hbox.add(sumValues);
        hbox.add(Box.createHorizontalStrut(30));
        hbox.add(labelSumPrice);
        hbox.add(sumPrice);
        hbox.add(Box.createHorizontalStrut(5));
        vbox1.add(Box.createHorizontalStrut(5));
        vbox1.add(hbox);
    }
    public TableModel getModel() {
        return this.model;
    }
    public JPanel getPanel() {
        JPanel tablePanel=new JPanel();
        tablePanel.add(vbox1);
        return tablePanel;
    }
    public void setSelectStore(String store){
        selectStore=store;
        //最好增加验证，当此时数据表中有数据的时候
    }

    public Object[][] getTableDataSet() {
       Object[][] dataSet=new Object[model.getRowCount()][model.getColumnCount()];
        for(int i=0;i<model.getRowCount();i++){
            for(int n=0;n<model.getColumnCount();n++){
                dataSet[i][n]=model.getValueAt(i, n);
            }
        }
        return dataSet;
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
                            "and store='" + selectStore + "'";
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
class PlanetTableModel extends AbstractTableModel {

    public static final int NAME = 1;
    public static final int VALUES = 2;
    public static final int PRICE = 3;
    public static final int SUM = 4;
    private static Object[][] cells = new Object[100][5];
    private String[] columnNames = {"编号", "商品名称", "数量", "单价", "金额"};

    @SuppressWarnings("empty-statement")
    public PlanetTableModel() {
        for (int i = 0; i < 100; i++) {
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

class ColorRenderer extends DefaultTableCellRenderer {

    public ColorRenderer() {
        super();
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
