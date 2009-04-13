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
 * 报损/报益 表
 */
public class lose2Main {

    private String year;
    private String month;
    private String day;
    private String id;
    private String info;
    private String color = "";
    private String size = "";
    private String store;
    private String inPrice;
    private String amount;
    private short loseORgain;//用来判断是报损还是报益
    private String others = "";
    private String num;
    private String date;
    private String outPrice;
//TEST 测试类

    public void test() {
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(id);
        System.out.println(store);
        System.out.println(info);

        System.out.println(amount);

        System.out.println(others);

    }

    /**
     * 获得信息方法
     * 
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

    public void setStore(String text) {
        store = text;
    }

    public void setAmount(String text) {
        amount = text;
    }

    public void setOthers(String text) {
        others = text;
    }

    public void setNum(String text) {
        num = text;
    }

    public void setLoseORgain(short text) {
        loseORgain = text;
    }
    public void setDate(String text){
        date=text;
    }
    //报损方法

    public void transmit2Lose() {
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
                dbOperation t2Lose = new dbOperation();
                t2Lose.DBConnect();
                String sql;
                sql = "insert into loset values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + inPrice + "','" + loseORgain + "','" + others + "','" + num + "','" + date + "')";
                t2Lose.DBSqlExe(sql);
                t2Lose.DBClosed();
            } else {
                JOptionPane.showMessageDialog(null, "报损商品：'" + info + "'库存数低于报损数，请检查输入");
                loseFrame.setExTag(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(lose2Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据未能写入，请检查是否正确设置数据库，如依旧有问题请与作者联系");
            loseFrame.setExTag(1);
        }
    }

    //报益方法
    public void transmit2Gain() {
        ResultSet rs = null;
        dbOperation findMain = new dbOperation();
        findMain.DBConnect();
        String sqll = "select distinct outPrice from maint where info='" + info + "'";
        try {
            rs = findMain.DBSqlQuery(sqll);
        } catch (SQLException ex) {
            Logger.getLogger(sell2Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (rs.next()) {
                outPrice = rs.getString(1);
                break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(sell2Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        findMain.DBClosed();
        if (outPrice == null) {
            outPrice = inPrice;
        }
        try {
            addDel mainT = new addDel();
            mainT.setAmount(amount);
            mainT.setColor(color);
            mainT.setInfo(info);
            mainT.setSize(size);
            mainT.setStore(store);
            mainT.setInPrice(inPrice);
            mainT.setOutPrice(outPrice);
            mainT.increaseMethod();
            dbOperation t2Gain = new dbOperation();
            t2Gain.DBConnect();
            String sql;
            sql = "insert into loset values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + inPrice + "','" + loseORgain + "','" + others + "','" + num + "','" + date + "')";
            System.out.println(sql);
            t2Gain.DBSqlExe(sql);
            t2Gain.DBClosed();

        } catch (SQLException ex) {
            Logger.getLogger(lose2Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据未能写入，请检查是否正确设置数据库，如依旧有问题请与作者联系");
            loseFrame.setExTag(1);
        }
    }
}
