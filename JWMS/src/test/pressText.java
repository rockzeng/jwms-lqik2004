/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import method.dbOperation;

/**
 *
 * @author lqik2004
 */
public class pressText {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        dbOperation increaseDb = new dbOperation();
        for(int i=0;i<2000;i++){
            String info="92-"+i;
            int amount=i+1;
            String store="nc";
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
        increaseDb.DBClosed();
        System.out.println(sql);}
    }
}
