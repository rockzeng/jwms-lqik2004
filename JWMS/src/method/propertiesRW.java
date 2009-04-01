package method;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lqik2004
 * 应用
 */
public class propertiesRW {
    public static String proIDMakeRead(String x){
        String returnFiled = null;
        
        Properties fieldName = new Properties();
        try {
            fieldName.load(new FileInputStream("data\\properties.dat"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"IO操作错误,不能正确读取文件");
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
        String transY = String.valueOf(y);
        try {
            Properties fieldName = new Properties();
            fieldName.load(new FileInputStream("data\\properties.dat"));
            fieldName.setProperty(x, transY);
            fieldName.store(new FileOutputStream("data\\properties.dat"), "");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(propertiesRW.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"IO操作错误,不能正确写入文件");
        }
    }
}
