/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jwms;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import method.dbOperation;

/**
 *
 * @author Administrator
 */
public class pressText {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        dbOperation increaseDb = new dbOperation();
        for(int i=0;i<300;i++){
            String info="91-"+i;
            int amount=i+1;
            String store="丰南";
            int inPrice=i*3;
            int outPrice=i*6;
        increaseDb.DBConnect();
        //update maint set amount=amount+"amount" where info="info" and store="store"
        String sql = "insert into maint(info,amount,store,inPrice,outPrice) values('" + info + "','" + amount + "','" + store + "','" + inPrice + "','" + outPrice + "')";
            try {
                increaseDb.DBSqlExe(sql);
            } catch (SQLException ex) {
                Logger.getLogger(pressText.class.getName()).log(Level.SEVERE, null, ex);
            }
        increaseDb.DBClosed();}
    }
}
