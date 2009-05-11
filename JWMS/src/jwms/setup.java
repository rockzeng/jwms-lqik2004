
package jwms;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author lqik2004
 */
public class setup {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        setupDialog frame=new setupDialog();
        frame.setTitle("程序设置");
        frame.setLocationRelativeTo(null);
        frame.setSize(250, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

}
class setupDialog extends JFrame{
    private JButton initInfo=new JButton("初始化仓储信息");
    private JButton initProgram=new JButton("初始化程序设置");

    public setupDialog() {
        Box vbox=Box.createVerticalBox();
        vbox.add(Box.createHorizontalStrut(10));
        vbox.add(initInfo);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(initProgram);
        vbox.add(Box.createVerticalGlue());
        add(vbox);
    }

}