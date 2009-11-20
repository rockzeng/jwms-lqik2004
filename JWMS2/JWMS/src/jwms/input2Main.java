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
 * 录入基本信息
 */
public class input2Main {

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
    private String sumPrice;
    private String num;
    private short inputORreturn;
    private String date;

    //输入信息
    public void test() {
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(id);
        System.out.println(store);
        System.out.println(info);
        System.out.println(amount);
        System.out.println(outPrice);
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
        amount = text;
    }

    public void setSumPrice(String text) {
        sumPrice = text;
    }

    public void setNum(String text) {
        num = text;
    }

    public void setInputORreturn(short text) {
        inputORreturn = text;
    }
    public void setDate(String text){
        date=text;
    }

    public void transmitInput() {
        try {
            addDel mainT = new addDel();
            dbOperation t2Input = new dbOperation();
            t2Input.DBConnect();
            String sql;
            sql = "insert into inputt values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + inPrice + "','" + outPrice + "','" + sumPrice + "','" + num + "','" + inputORreturn + "','" + date + "')";
            System.out.print(sql);
            t2Input.DBSqlExe(sql);
            t2Input.DBClosed();
            mainT.setAmount(amount);
            mainT.setColor(color);
            mainT.setInfo(info);
            mainT.setSize(size);
            mainT.setStore(store);
            mainT.setInPrice(inPrice);
            mainT.setOutPrice(outPrice);
            mainT.increaseMethod();
        } catch (SQLException ex) {
            Logger.getLogger(input2Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据未能写入，请检查是否正确设置数据库，如依旧有问题请与作者联系");
            inputFrame.setExTag(1);
        }
    }

    public void transmitReturn() {
        try {
            boolean result;
            addDel mainT = new addDel();
            mainT.setAmount(amount);
            mainT.setColor(color);
            mainT.setInfo(info);
            mainT.setSize(size);
            mainT.setStore(store);
            mainT.setInPrice(inPrice);
            mainT.setOutPrice(outPrice);
            result = mainT.decreaseMethod();
            if (result) {
                dbOperation t2Input = new dbOperation();
                t2Input.DBConnect();
                String sql;
                sql = "insert into inputt values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + inPrice + "','" + outPrice + "','" + sumPrice + "','" + num + "','" + inputORreturn + "','" + date + "')";
                System.out.print(sql);
                t2Input.DBSqlExe(sql);
                t2Input.DBClosed();
            } else {
                JOptionPane.showMessageDialog(null, "货品:" + info + "不存在或库存值为负数");
                inputFrame.setExTag(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(input2Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "数据未能写入，请检查是否正确设置数据库，如依旧有问题请与作者联系");
            inputFrame.setExTag(1);
        }
    }
}

