/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jwms;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class sell2Main {

    private int year;
    private int month;
    private int day;
    private String id;
    private String info;
    private String color = "";
    private String size = "";
    private double inPrice = 0;
    private double outPrice = 0;
    private String store;
    private int amount = 0;
    private short sellORreturn = 0;
    private String others;

    public void transmitSell() {
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
                dbOperation t2Sell = new dbOperation();
                t2Sell.DBConnect();
                String sql;
                sql = "insert into inputt values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + outPrice + "','" + sellORreturn + "','" + others + "')";
                t2Sell.DBSqlExe(sql);
                t2Sell.DBClosed();
            }
        } catch (SQLException ex) {
            Logger.getLogger(input2Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据未能写入，请检查是否正确设置数据库，如依旧有问题请与作者联系");
        }
    }

    public void transmitReturn() {

        try {
            addDel mainT = new addDel();
            mainT.setAmount(amount);
            mainT.setColor(color);
            mainT.setInfo(info);
            mainT.setSize(size);
            mainT.setStore(store);
            mainT.increaseMethod();
            dbOperation t2Rturn = new dbOperation();
            t2Rturn.DBConnect();
            String sql;
            sql = "insert into inputt values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + outPrice + "','" + sellORreturn + "','" + others + "')";
            t2Rturn.DBSqlExe(sql);
            t2Rturn.DBClosed();

        } catch (SQLException ex) {
            Logger.getLogger(input2Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据未能写入，请检查是否正确设置数据库，如依旧有问题请与作者联系");
        }
    }
    }

