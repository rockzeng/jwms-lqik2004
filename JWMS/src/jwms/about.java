/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jwms;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Administrator
 */
public class about {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        aboutDialog frame=new aboutDialog();
        frame.setTitle("关于");
        frame.setLocationRelativeTo(null);
        frame.setSize(100, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

}
class aboutDialog extends JFrame{

    public aboutDialog() {
        String version=null;
        version="0.141_Alpha";
        JLabel l1=new JLabel("Version：'"+version+"'");
        JLabel l2=new JLabel("Author：刘潮");
        JLabel l3=new JLabel("Update：2009-4-29");
        JLabel l4=new JLabel("Licence：GPL3");
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