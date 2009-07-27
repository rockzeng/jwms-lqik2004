/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.res0w.jwms.main;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.res0w.jwms.method.*;

/**
 *
 * @author lqik2004
 */
public class PressText {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DBOperation increaseDb = new DBOperation();
        for(int i=0;i<20000;i++){
            String info="92-"+i;
            int amount=i+1;
            String store="ÍòÂ¡";
            int inPrice=i*3;
            int outPrice=i*6;
        increaseDb.DBConnect();
        //update maint set amount=amount+"amount" where info="info" and store="store"
        String sql = "insert into maint(info,amount,store,inPrice,outPrice) values('" + info + "','" + amount + "','" + store + "','" + inPrice + "','" + outPrice + "')";
            try {
                increaseDb.DBSqlExe(sql);
            } catch (SQLException ex) {
                Logger.getLogger(PressText.class.getName()).log(Level.SEVERE, null, ex);
            }
        increaseDb.DBClosed();
        System.out.println(i);}
    }
}
