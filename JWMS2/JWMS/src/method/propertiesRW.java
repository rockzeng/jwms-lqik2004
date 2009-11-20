package method;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author lqik2004
 * 目前无法把配置文件放入到源文件中
 * 经过尝试，文件的读取可以完成，但是文件的写入
 * 无论如何不能顺利写入
 * 所以，暂且保持配置文件外放
 * #根据jwms 2009年7月开发的linux版本重新引入了系统判别
 * 
 */
public class propertiesRW {

    public static String proIDMakeRead(String x) {
        String propertiesURL;
        String returnFiled = null;
        if (System.getProperty("os.name").toUpperCase().equals("LINUX")) {
            propertiesURL = "data/properties.txt";
        } else {
            propertiesURL = "data\\properties.txt";
        }
        Properties fieldName = new Properties();
        try {
            fieldName.load(new FileInputStream(propertiesURL));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO操作错误,不能正确读取文件");
        }
        returnFiled = fieldName.getProperty(x);
        return returnFiled;
    }

    /**
     *
     * @param x     NAMEkey
     * @param y     NAMEvalue
     * @throws java.io.IOException
     *
     * 在对properties进行写的时候，先要做LOAD,然后设置(setProperties),最后写入到文件（store）
     */
    public static void proIDMakeWrite(String x, int y) throws IOException {
        String propertiesURL;
        String transY = String.valueOf(y);
        if (System.getProperty("os.name").toUpperCase().equals("LINUX")) {
            propertiesURL = "data/properties.txt";
        } else {
            propertiesURL = "data\\properties.txt";
        }
        try {
            Properties fieldName = new Properties();
            fieldName.load(new FileInputStream(propertiesURL));
            fieldName.setProperty(x, transY);
            fieldName.store(new FileOutputStream(propertiesURL), "");
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "IO操作错误,不能正确写入文件");
        }
    }
}
