/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package method;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class Clear0Info {

    public Clear0Info() {
        try {
            dbOperation c = new dbOperation();
            c.DBConnect();
            String del;
            del = "delete from maint where amount=0";
            c.DBSqlExe(del);
        } catch (SQLException ex) {
            Logger.getLogger(Clear0Info.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据库错误");
        }
    }

    public Clear0Info(String store) {

         try {
            dbOperation c = new dbOperation();
            c.DBConnect();
            String del;
            del = "delete from maint where amount=0 and store='"+store+"'";
            c.DBSqlExe(del);
        } catch (SQLException ex) {
            Logger.getLogger(Clear0Info.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据库错误");
        }

    }


}