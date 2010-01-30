/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package method;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 *
 * @author Administrator
 */
public class ProgressBarDialog extends JFrame{
    JProgressBar progressBar=new JProgressBar();
    public ProgressBarDialog() {
        progressBar.setForeground(new Color(255, 0, 0));
        progressBar.setStringPainted(true);
        add(progressBar);
        //setLocationRelativeTo(null);//一句让窗口居中
        setUndecorated(true);
        pack();
        setVisible(true);
    }
    public void adoptDeterminate(int maxValue){
        progressBar.setMaximum(maxValue);
        progressBar.setVisible(true);
    }
    public void setValue(int value){
        progressBar.setValue(value);
        if(value>=progressBar.getMaximum()){
            progressBar.setString("正在处理数据，请稍候...");
        }
    }
    public void finishDeterminate(){
        
        this.dispose();
    }
}
