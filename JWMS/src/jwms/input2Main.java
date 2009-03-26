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
public class input2Main {

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

    public void transmit() {
        try {           
            addDel mainT = new addDel();
            dbOperation t2Input = new dbOperation();          
            t2Input.DBConnect();
            String sql;
            sql = "insert into inputt values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + inPrice + "','" + outPrice + "')";
            t2Input.DBSqlExe(sql);
            t2Input.DBClosed();         
            mainT.setAmount(amount);
            mainT.setColor(color);
            mainT.setInfo(info);
            mainT.setSize(size);
            mainT.setStore(store);
            mainT.increaseMethod();
        } catch (SQLException ex) {
            Logger.getLogger(input2Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据未能写入，请检查是否正确设置数据库，如依旧有问题请与作者联系");
        }
    }
}

