package oldPacket;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * 
 * @author lqik2004
 * 此类提供对tag,judge数据的持久化
 * 使得不会两个变量的数据不会由于关闭程序而消失
 * 文件更新的过程
 * 1）当用户生成一个窗口的时候自动生成编号，从而自动读取文件信息
 * 2）当用户关闭窗口的时候有两种情况：①退出未提交，不更新文件信息，也就是说下次生成窗口编号不增加。
 *                                  ②提交，更新文件信息，下一个编号自动增加
 * 
 */
public class tagJudgeRW {
  

    public static String readFile(String file) {
        String data = null;
        try {
            FileReader fr = new FileReader("data\\"+file+".txt");
            BufferedReader br = new BufferedReader(fr);
            String temp=br.readLine();
            while(temp!=null){
                data=temp;
                temp=br.readLine();
            }         
            br.close();
            fr.close();
        } catch (IOException e) {
            Logger.getLogger(tagJudgeRW.class.getName()).log(Level.SEVERE, null, e);
        }
        return data;
    }

    public static void writeFile(String file,int tag) {
        int data=tag;
        try {
            FileWriter fw = new FileWriter("data\\"+file+".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data+ "\r\n");
            bw.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(tagJudgeRW.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
