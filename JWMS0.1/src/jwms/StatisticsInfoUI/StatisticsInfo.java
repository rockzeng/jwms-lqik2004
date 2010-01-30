/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jwms.StatisticsInfoUI;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author res0w
 * @since 2009-11-10
 * 
 * @version 0.1
 * 
 * 
 */
public class StatisticsInfo extends JFrame {

    private JPanel panel;
    public StatisticsInfo() {
        panel = new SInfoPanel();
        this.getContentPane().add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(panel.getPreferredSize().width, panel.getPreferredSize().height));
        this.pack();
    }
    
    public static void main(String[] args) {
        JFrame frame = new StatisticsInfo();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
