/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import method.dbOperation;


/**
 *
 * @author res0w
 * @since 2009-12-01
 */
public class DataSetUtil {

    public DataSetUtil() {
    }

     public ArrayList storeLoad() {
        dbOperation storeLoad = new dbOperation();
        storeLoad.DBConnect();
        String sql = "select store from storet";
        ResultSet rs = null;
        ArrayList re=new ArrayList();
        try {
            try {
                rs = storeLoad.DBSqlQuery(sql);
            } catch (SQLException ex) {
                Logger.getLogger(DataSetUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (rs.next()) {
                re.add(rs.getString(1).trim());
            }
            storeLoad.DBClosed();
        } catch (SQLException ex) {
            Logger.getLogger(DataSetUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return re;
    }
    //在maint中读取商品信息info，目的：提供给“自动完成”模块使用。

    public Object[] infoLoad() {
        List v = new Vector();
        dbOperation infoLoad = new dbOperation();
        infoLoad.DBConnect();
        String sql = "select distinct info from maint";
        ResultSet rs = null;
        try {
            rs = infoLoad.DBSqlQuery(sql);
            while (rs.next()) {
                v.add(rs.getString("info").trim());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataSetUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        infoLoad.DBClosed();
        return v.toArray();
    }
}
