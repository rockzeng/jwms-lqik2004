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
public class equal2Main {

    private int year;
    private int month;
    private int day;
    private String id;
    private String info;
    private String color = "";
    private String size = "";
    private String inStore;//调入仓库 getin store
    private String outStore;//调出仓库 getout store
    private int amount = 0;
    private String others;

    public void transmit() {
        try {
            addDel mainT = new addDel();
            dbOperation t2Equal = new dbOperation();
            t2Equal.DBConnect();
            String sql;
            sql = "insert into inputt values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + inStore + "','" + outStore + "','"+others+"')";
            t2Equal.DBSqlExe(sql);
            t2Equal.DBClosed();
            //对库存的增减
            mainT.setAmount(amount);
            mainT.setColor(color);
            mainT.setInfo(info);
            mainT.setSize(size);
            mainT.setStore(inStore);
            mainT.increaseMethod();
            mainT.setStore(outStore);
            mainT.decreaseMethod();
        } catch (SQLException ex) {
            Logger.getLogger(equal2Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据未能写入，请检查是否正确设置数据库，如依旧有问题请与作者联系");
        }
    }
}
