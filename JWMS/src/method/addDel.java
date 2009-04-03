/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package method;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lqik2004
 */
public class addDel {

    private String info;
    private String color = "";
    private String size = "";
    private String inPrice;
    private double outPrice = 0;
    private String store;
    private String amount;
    //get some necessary information

    public void setInfo(String text) {
        info = text;
    }

    public void setColor(String text) {
        color = text;
    }

    public void setSize(String text) {
        size = text;
    }

    public void setInPrice(String text) {
        inPrice = text;
    }

    public void setOutPrice(double text) {
        outPrice = text;
    }

    public void setStore(String text) {
        store = text;
    }

    public void setAmount(String text) {
        amount = text;
    }
    //判断增加或者减少主数据库信息是否存在
    //为了给“增加仓库”提供判断方法，增强了判断方法功能

    public boolean isStoreExist(String store) throws SQLException {
        boolean result = false;
        String x = store;
        dbOperation isInfoExistDb = new dbOperation();
        isInfoExistDb.DBConnect();
        String sql = "select * from storet where store='" + x + "'";
        ResultSet rs = isInfoExistDb.DBSqlQuery(sql);
        if (rs.next()) {
            result = true;
        }
        isInfoExistDb.DBClosed();
        return result;
    }

    public boolean isInfoExist(String info) throws SQLException {
        boolean result = false;
        String x = info;

        dbOperation isInfoExistDb = new dbOperation();
        isInfoExistDb.DBConnect();
        String sql = "select * from maint where info='" + x + "'";
        ResultSet rs = isInfoExistDb.DBSqlQuery(sql);
        if (rs.next()) {
            result = true;
        }
        isInfoExistDb.DBClosed();
        return result;
    }
    //core method

    /**
     * 增加库存
     * @throws java.sql.SQLException
     */
    public void increaseMethod() throws SQLException {
        boolean result = isInfoExist(info);
        dbOperation increaseDb = new dbOperation();
        if (result) {
            increaseDb.DBConnect();
            //update maint set amount=amount+"amount" where info="info" and store="store"
            String sql = "update maint set amount=amount+'" + amount + "' where info='" + info + "' and store='" + store + "'";
            increaseDb.DBSqlExe(sql);
            increaseDb.DBClosed();
        } else {
            increaseDb.DBConnect();
            //update maint set amount=amount+"amount" where info="info" and store="store"
            String sql = "insert into maint(info,amount,store,inPrice) values('" + info + "','" + amount + "','" + store + "','" + inPrice + "')";
            increaseDb.DBSqlExe(sql);
            increaseDb.DBClosed();
        }
    }
    //应当放到模块入库之前进行判断

    /**
     * 减少库存
     * @return
     * @throws java.sql.SQLException
     */
    public boolean decreaseMethod() throws SQLException {
        boolean result = isInfoExist(info);
        dbOperation decreaseDb = new dbOperation();
        if (result) {
            decreaseDb.DBConnect();
            //update maint set amount=amount+"amount" where info="info" and store="store"
            String sql = "update maint set amount=amount-'" + amount + "' where info='" + info + "' and store='" + store + "'";
            decreaseDb.DBSqlExe(sql);
            decreaseDb.DBClosed();
        }/*else{

        JOptionPane.showConfirmDialog(null, "您所销售的货品:"+info+"不存在！");
        }*/
        return result;

    }
}
