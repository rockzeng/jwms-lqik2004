package method;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class EmbeddedDerbyTester {

    public static void main(String[] args) {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";//在derby.jar里面
        String dbName = "123";
        String dbURL = "jdbc:derby:" + dbName + ";create=true";//create=true表示当数据库不存在时就创建它
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbURL);//启动嵌入式数据库
            java.sql.Statement st = conn.createStatement();
            
            //st.execute("create table maint (info char(15),amount int,store char(10),inPrice float,outPrice float)");
            st.execute("create table equalt (id char(20) ,yea char(4),MONTH char(4),DAY char(4),info char(15),amount int,color char(10),size char(10),inStore char(10),outStore char(10),others char(10),num int,DATE char(15))");//创建foo表
            st.execute("create table inputt (id char(20) ,yea char(4),month char(4),day char(4),info char(15),amount int,color char(10),size char(10),inPrice float,outPrice float,sumPrice float,num int,inputORreturn int,date char(15))");//创建foo表
            st.execute("create table loset (id char(20) ,yea char(4),month char(4),day char(4),info char(15),amount int,color char(10),size char(10),store char(10),inPrice float,loseORgain int,others char(10),num int,date char(15))");//创建foo表
            st.execute("create table maint (info char(15),amount int,store char(10),inPrice float,outPrice float)");
            st.execute("create table SearchCache (store char(10),amount char(20),sumprice char(20),income char(20))");
            st.execute("create table sellt (id char(20) ,yea char(4),month char(4),day char(4),info char(15),amount int,color char(10),size char(10),store char(10),outPrice float,sellORreturn int,others char(15),num int,date char(15)))");//创建foo表
            st.execute("create table storet (store char(10))");
            st.execute("create table StreamCache (datetag char(15),id char(15),date char(10),type char(20))");
            //插入一条数据
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}