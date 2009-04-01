/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jwms;

import method.dbOperation;
import method.addDel;
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
    private String num;
    //TEST
    public void test() {
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(id);
        System.out.println(store);
        System.out.println(info);
        System.out.println(num);
        System.out.println(amount);
        System.out.println(outPrice);
        System.out.println(others);

    }

    /**
     * 获得信息方法
     * 12个
     */
    public void setYear(String text) {
        year = Integer.parseInt(text);
    }

    public void setMonth(String text) {
        month = Integer.parseInt(text);
    }

    public void setDay(String text) {
        day = Integer.parseInt(text);
    }

    public void setID(String text) {
        id = text;
    }

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
        inPrice = Double.parseDouble(text);
    }

    public void setOutPrice(String text) {
        outPrice = Double.parseDouble(text);
    }

    public void setStore(String text) {
        store = text;
    }

    public void setAmount(String text) {
        amount = Integer.parseInt(text);
    }

    public void setOthers(String text) {
        others = text;
    }
    
    public void setNum(String text){
        num=text;
    }

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
                sql = "insert into sellt values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + outPrice + "','" + sellORreturn + "','" + others + "','" + num + "')";
                t2Sell.DBSqlExe(sql);
                t2Sell.DBClosed();
            } else {
                JOptionPane.showConfirmDialog(null, "您所销售的货品:" + info + "不存在！");
            }
        } catch (SQLException ex) {
            Logger.getLogger(sell2Main.class.getName()).log(Level.SEVERE, null, ex);
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
            sql = "insert into sellt values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + outPrice + "','" + sellORreturn + "','" + others + "','" + num + "')";
            t2Rturn.DBSqlExe(sql);
            t2Rturn.DBClosed();

        } catch (SQLException ex) {
            Logger.getLogger(sell2Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据未能写入，请检查是否正确设置数据库，如依旧有问题请与作者联系");
        }
    }
    }

