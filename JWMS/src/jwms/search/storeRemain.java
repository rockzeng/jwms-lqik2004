/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jwms.search;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import method.dbOperation;
import method.getDate;

/**
 *
 * @author Administrator
 */
class RemainText {     

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        storeRemain frame=new storeRemain();
        frame.setSize(200, 400);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
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

    public void setByear(String x) {
        byear = Integer.parseInt(x);
    }

    public void setEyear(String x) {
        byear = Integer.parseInt(x);
    }

    public void setBmonth(String x) {
        byear = Integer.parseInt(x);
    }

    public void setEmonth(String x) {
        byear = Integer.parseInt(x);
    }

    public void setBday(String x) {
        byear = Integer.parseInt(x);
    }

    public void setEday(String x) {
        byear = Integer.parseInt(x);
    }

    public void store(String x) {
        store = x;
    }

    public storeRemain() {
        Object[] colName1 = new Object[2];
        colName1[0] = "商品名称";
        colName1[1] = "仓库";
        model1.setColumnCount(2);
        model1.setRowCount(5000);
        model1.setColumnIdentifiers(colName1);//定义列名
        table1.getColumnModel().getColumn(0).setPreferredWidth(130);
        table1.getColumnModel().getColumn(1).setPreferredWidth(70);
        JScrollPane panel1 = new JScrollPane(table1);
        panel1.setPreferredSize(new Dimension(200, 400));
        add(panel1);
    }

    public void search() {
        new Thread() {
            @Override
            public void run() {
                String sql;
                ResultSet rs = null;
                String syear = getDate.fixYear(String.valueOf(eyear));
                String smonth = getDate.fixMonth(String.valueOf(emonth));
                String sday = getDate.fixDay(String.valueOf(eday));
                String info;
                int amount;
                sql = "select info,amount from mainline where '" + store + "' and  date='" + syear + smonth + sday + "'";
                System.out.print(sql);
                dbOperation stable = new dbOperation();
                stable.DBConnect();
                try {
                    rs = stable.DBSqlQuery(sql);
                    while (rs.next()) {
                        info = rs.getString(1).trim();
                        amount = Integer.parseInt(rs.getString(2).trim());
                    }
                    //设置表格
                    stable.DBClosed();
                } 
                catch (SQLException ex) {
                    Logger.getLogger(storeRemain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
}
class modelStoreRemain extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}