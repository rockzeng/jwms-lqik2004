package jwms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class importSetupDialog extends JInternalFrame{
    /*
    @param filename 输入文件名；
     * @param sheetNum  输入工作簿的序号，从0开始；
     * @param beRow 工作表开始的行号，从0开始（例如：第一行，即为0）；
     * @param endRow 工作表结束的行号；
     * @param infoNum  “商品全名”列号从零开始，（例如：A，为0）；
     * @param amountNum “库存数量”；
     * @param priceNum  “成本均价”；
     * @param allPrsNum “库存总价”；
     */

    private JLabel sheetNumLabel = new JLabel("输入工作簿序号");
    private JLabel beRowLabel = new JLabel("开始行数：");
    private JLabel enRowLabel = new JLabel("结束行数：");
    private JLabel infoNumLabel = new JLabel("商品全名列号：");
    private JLabel amountNumLabel = new JLabel("库存数量列号：");
    private JLabel priceNumLabel = new JLabel("成本均价列号：");
    private JLabel allPrsNumLabel = new JLabel("库存总价列号：");
    private JTextField sheetNum = new JTextField("0",2);
    private JTextField beRow = new JTextField(3);
    private JTextField enRow = new JTextField(3);
    private JTextField infoNum = new JTextField("2",2);
    private JTextField amountNum = new JTextField("3",2);
    private JTextField priceNum = new JTextField("4",2);
    private JTextField allPrsNum = new JTextField("5",2);
    private JButton filename = new JButton("选择Excel文件");
    private JButton confirm = new JButton("确定");

    public importSetupDialog() {
        sheetNum.setEditable(true);
        sheetNum.setEnabled(true);
        beRow.setEditable(true);
        beRow.setEnabled(true);
        enRow.setEditable(true);
        infoNum.setEditable(true);
        amountNum.setEditable(true);
        priceNum.setEditable(true);
        allPrsNum.setEditable(true);

        Box vbox = Box.createVerticalBox();
        Box hbox0 = Box.createHorizontalBox();
        hbox0.add(sheetNumLabel);
        hbox0.add(sheetNum);
        Box hbox1 = Box.createHorizontalBox();
        hbox1.add(beRowLabel);
        hbox1.add(beRow);
        Box hbox2 = Box.createHorizontalBox();
        hbox2.add(enRowLabel);
        hbox2.add(enRow);
        Box hbox3 = Box.createHorizontalBox();
        hbox3.add(infoNumLabel);
        hbox3.add(infoNum);
        Box hbox4 = Box.createHorizontalBox();
        hbox4.add(amountNumLabel);
        hbox4.add(amountNum);
        Box hbox5 = Box.createHorizontalBox();
        hbox5.add(priceNumLabel);
        hbox5.add(priceNum);
        Box hbox6 = Box.createHorizontalBox();
        hbox6.add(allPrsNumLabel);
        hbox6.add(allPrsNum);
        Box hbox7 = Box.createHorizontalBox();
        hbox7.add(filename);
        vbox.add(hbox0);
        vbox.add(hbox1);
        vbox.add(hbox2);
        vbox.add(hbox3);
        vbox.add(hbox4);
        vbox.add(hbox5);
        vbox.add(hbox6);
        vbox.add(hbox7);

        add(vbox);


        confirm.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            }
        });
    }
    public  static void main(String[] args){
        importSetupDialog im=new importSetupDialog();
        im.setSize(150, 200);
        im.setVisible(true);
    }
}
