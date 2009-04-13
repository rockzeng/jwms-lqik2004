/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jwms;

import java.sql.ResultSet;
import method.dbOperation;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import method.addDel;

/**
 *
 * @author lqik2004
 * 同价调拨单
 * 
 */
public class equal2Main {

    private String year;
    private String month;
    private String day;
    private String id;
    private String info;
    private String color = "";
    private String size = "";
    private String inStore;//调入仓库 getin store
    private String outStore;//调出仓库 getout store
    private String amount;
    private String others = "";
    private String num;
    private String inPrice;
    private String outPrice;
    private String date;
//录入信息

    public void test() {
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(id);
        System.out.println(inStore);
        System.out.println(info);
        System.out.println(outStore);
        System.out.println(amount);
        System.out.println(others);

    }

    /**
     * 获得信息方法
     * 12个
     */
    public void setYear(String text) {
        year = text;
    }

    public void setMonth(String text) {
        month = text;
    }

    public void setDay(String text) {
        day = text;
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

    public void setINStore(String text) {
        inStore = text;
    }

    public void setOUTStore(String text) {
        outStore = text;
    }

    public void setAmount(String text) {
        amount = text;
    }

    public void setNum(String text) {
        num = text;
    }

    public void setOthers(String text) {
        others = text;
    }
    public void setDate(String text){
        date=text;
    }

    public void transmit() {
        try {
            boolean result;
            addDel mainT = new addDel();
            //对库存的增减

            mainT.setAmount(amount);
            mainT.setColor(color);
            mainT.setInfo(info);
            mainT.setSize(size);
            mainT.setStore(outStore);
            result = mainT.decreaseMethod();
            if (result == true) {
                mainT.setStore(inStore);
                boolean result1 = mainT.isInfoExist(info, inStore);
                if (result1 == false) {
                    ResultSet rs = null;
                    dbOperation findMain = new dbOperation();
                    findMain.DBConnect();
                    String sqll = "select distinct inPrice,outPrice from maint where info='" + info + "'";
                    try {
                        rs = findMain.DBSqlQuery(sqll);
                    } catch (SQLException ex) {
                        Logger.getLogger(sell2Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        while (rs.next()) {
                            inPrice = rs.getString(1);
                            outPrice = rs.getString(2);
                            break;
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(sell2Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    findMain.DBClosed();
                    mainT.setInPrice(inPrice);
                    mainT.setOutPrice(outPrice);
                }
                mainT.increaseMethod();
                dbOperation t2Equal = new dbOperation();
                t2Equal.DBConnect();
                String sql;
                sql = "insert into equalt values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + inStore + "','" + outStore + "','" + others + "','" + num + "','" + date + "')";
                t2Equal.DBSqlExe(sql);
                t2Equal.DBClosed();
            } else {
                JOptionPane.showMessageDialog(null, "所调拨的商品：'" + info + "'库存为零或不存在！");
                equalFrame.setExTag(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(equal2Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据未能写入，请检查是否正确设置数据库，如依旧有问题请与作者联系");
            equalFrame.setExTag(1);
        }
    }
}
