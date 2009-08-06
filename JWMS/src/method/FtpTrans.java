package method;

import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FileTransferClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author res0w
 */
class testmain{
    public static void main(String[] args){
        try {
            FtpTrans ftp = new FtpTrans();
            ftp.ftpConnect();
            ftp.ftpNameList();
            ftp.ftpDiscont();
        } catch (FTPException ex) {
            Logger.getLogger(testmain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
public class FtpTrans {

    FileTransferClient ftp;
    String host="ftp.res0w.com";
    String uname="jwms@res0w.com";
    String pwd="881010";

    public FtpTrans() {
        ftp = new FileTransferClient();
    }

    public void ftpConnect() {

        try {
            ftp.setRemoteHost(host);
            ftp.setUserName(uname);
            ftp.setPassword(pwd);
            ftp.connect();
        } catch (Exception e) {
            System.out.println("ftp连接错误");
        }
    }

    public void ftpDiscont() throws FTPException {
        try {
            ftp.disconnect();
        } catch (IOException ex) {
            Logger.getLogger(FtpTrans.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ftpDownload(String host,String usr,String pwd,String localFilePath,String remoteFilename){
        this.host=host;
        this.pwd=pwd;
        this.uname=usr;
        ftpConnect();
        try {
            ftp.downloadFile(localFilePath, remoteFilename);
        } catch (FTPException ex) {
            Logger.getLogger(FtpTrans.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FtpTrans.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ftpDiscont();
        } catch (FTPException ex) {
            Logger.getLogger(FtpTrans.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void ftpNameList(){
        try {
            String[] file = ftp.directoryNameList();
            int i=0;
            while(!file[i].equals(null)){
                System.out.println(file[i]);
                i++;
            }
        } catch (FTPException ex) {
            Logger.getLogger(FtpTrans.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FtpTrans.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
