package jwms;

import method.dbOperation;
import method.addDel;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lqik2004
 * 销售单/退货单
 */
public class sell2Main {

    private String year;
    private String month;
    private String day;
    private String id;
    private String info;
    private String color = "";
    private String size = "";
    private String inPrice;
    private String outPrice;
    private String store;
    private String amount;
    private short sellORreturn = 0; //判断是进货还是退货；
    private String others;
    private int num;
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

    public void setInPrice(String text) {
        inPrice = text;
    }

    public void setOutPrice(String text) {
        outPrice = text;
    }

    public void setStore(String text) {
        store = text;
    }

    public void setAmount(String text) {
        amount =text;
    }

    public void setOthers(String text) {
        others = text;
    }
    
    public void setNum(String text){
        num=Integer.parseInt(text);
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
                System.out.println(sql);
                t2Sell.DBSqlExe(sql);
                t2Sell.DBClosed();
            } else {
                JOptionPane.showMessageDialog(null, "您所销售的货品:" + info + "不存在！");
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
            mainT.setInPrice(outPrice);//退货的时候售价等于进货价
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

