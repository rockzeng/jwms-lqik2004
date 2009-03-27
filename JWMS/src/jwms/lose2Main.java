/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jwms;

import method.dbOperation;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import method.addDel;

/**
 *
 * @author Administrator
 */
public class lose2Main {

    private int year;
    private int month;
    private int day;
    private String id;
    private String info;
    private String color = "";
    private String size = "";
    private String store;
    private int amount = 0;
    private short loseORgain = 0;
    private String others;

    public void transmit2Lose() {
        boolean result;
        try {
            addDel mainT = new addDel();
            mainT.setAmount(amount);
            mainT.setColor(color);
            mainT.setInfo(info);
            mainT.setSize(size);
            mainT.setStore(store);
            result = mainT.decreaseMethod();
            if (result) {
                dbOperation t2Lose = new dbOperation();
                t2Lose.DBConnect();
                String sql;
                sql = "insert into loset values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + loseORgain + "'," + others + "')";
                t2Lose.DBSqlExe(sql);
                t2Lose.DBClosed();
            }
        } catch (SQLException ex) {
            Logger.getLogger(lose2Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据未能写入，请检查是否正确设置数据库，如依旧有问题请与作者联系");
        }
    }

    public void transmit2Gain() {
        try {
            addDel mainT = new addDel();
            mainT.setAmount(amount);
            mainT.setColor(color);
            mainT.setInfo(info);
            mainT.setSize(size);
            mainT.setStore(store);
            mainT.increaseMethod();
            dbOperation t2Gain = new dbOperation();
            t2Gain.DBConnect();
            String sql;
            sql = "insert into loset values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + loseORgain + "'," + others + "')";
            t2Gain.DBSqlExe(sql);
            t2Gain.DBClosed();

        } catch (SQLException ex) {
            Logger.getLogger(lose2Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据未能写入，请检查是否正确设置数据库，如依旧有问题请与作者联系");
        }
    }
}
