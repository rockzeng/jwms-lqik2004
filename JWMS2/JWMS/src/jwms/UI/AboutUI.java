/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jwms.UI;

import jwms.*;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JWindow;
import method.propertiesRW;

/**
 *
 * @author res0w
 */
public class AboutUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        aboutDialog frame=new aboutDialog();
//        frame.setTitle("关于");
        frame.setLocationRelativeTo(null);
        frame.setSize(100, 250);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

}
class aboutDialog extends JWindow{

    public aboutDialog() {
        String version=null;
        version=propertiesRW.proIDMakeRead("currentversion");
        JLabel l1=new JLabel("Version:0.180 r2009111613");
        JLabel l2=new JLabel("Author：res0w");
        JLabel l3=new JLabel("Update：2009-11-16");
        JLabel l4=new JLabel("Licence：GPL2");
        Box h0=Box.createVerticalBox();
        h0.add(Box.createVerticalStrut(5));
        h0.add(l1);
        h0.add(Box.createVerticalStrut(5));
        h0.add(l2);
        h0.add(Box.createVerticalStrut(5));
        h0.add(l3);
        h0.add(Box.createVerticalStrut(5));
        h0.add(l4);
        h0.add(Box.createVerticalStrut(5));
        add(h0);
    }

}