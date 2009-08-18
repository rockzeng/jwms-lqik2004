package method;

import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FileTransferClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author res0w
 */
class testmain {

    public static void main(String[] args) {
            FtpTrans ftp = new FtpTrans();
            ftp.ftpDownload("ftp.res0w.com",  "jwms@res0w.com", "881010", "temp");
    }
}

public class FtpTrans {

    FileTransferClient ftp;
    String host = "ftp.res0w.com";
    String uname = "jwms@res0w.com";
    String pwd = "881010";

    public FtpTrans() {
        ftp = new FileTransferClient();
    }

    private void ftpConnect() {

        try {
            ftp.setRemoteHost(host);
            ftp.setUserName(uname);
            ftp.setPassword(pwd);
            ftp.setRemotePort(21);
            ftp.setTimeout(1200);
            ftp.getAdvancedFTPSettings().setConnectMode(FTPConnectMode.PASV);
            ftp.connect();
            System.out.println(host);
            System.out.println(uname);
            System.out.println(pwd);
        } catch (Exception e) {
            System.out.println("ftp连接错误");
        }
    }

    private void ftpDiscont() throws FTPException {
        try {
            ftp.disconnect();
        } catch (IOException ex) {
            Logger.getLogger(FtpTrans.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ftpDownload(String host, String usr, String pwd, String localFilePath) {
        String remoteFilename;
        this.host = host;
        this.pwd = pwd;
        this.uname = usr;
        ftpConnect();
        remoteFilename=ftpNameSelect();
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

    private String ftpNameSelect() {
        String fileName = null;
        try {
            String[] file = ftp.directoryNameList();
            int preName = -1;
            int mark = -1;
            for (int i = 0; i < file.length; i++) {
                if (!file[i].startsWith(".") && Integer.parseInt(file[i].substring(0, 2)) >= preName) {
                    preName = Integer.parseInt(file[i].substring(0, 2));
                    mark = i;
                }
            }
            if (mark != -1) {
                fileName = file[mark];
            }
//            System.out.println(fileName);
        } catch (FTPException ex) {
            Logger.getLogger(FtpTrans.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FtpTrans.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }
}
