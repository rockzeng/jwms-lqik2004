/*
# JWMS2 is based on JWMS.JWMS is short for JeanWest store-sell Management System
# Copyright (C) 2009,res0w
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author res0w
 * @since 2009-12-1
 *
 */
public class GroupButtonUI {

    private int selectIndex=1;
    private ArrayList<String> typeList = new ArrayList<String>();
    private Box hbox=Box.createHorizontalBox();
/**
 * @deprecated 暂时不推荐使用,未测试
 * @param list 当可选项多于两个的时候可以使用此方法，把选项名称通过列表传送进来
 */
    public GroupButtonUI(ArrayList<String> list) {
        ButtonGroup group = new ButtonGroup();
        typeList = list;
        for (int i = 0; i < typeList.size(); i++) {
            final int m=i+1;
            JRadioButton itemButton = new JRadioButton(list.get(i), true);
            itemButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selectIndex = m;
                }
            });
            group.add(itemButton);
        }
    }

    public GroupButtonUI(String firstType, String secondType) {
        ButtonGroup group = new ButtonGroup();//设置按钮组，保证只能单选
        JRadioButton first = new JRadioButton(firstType, true);
        first.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                selectIndex = 1;
            }
        });
        JRadioButton second = new JRadioButton(secondType, false);
        second.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                selectIndex = 2;
            }
        });
        group.add(first);
        group.add(second);
        hbox.add(first);
        hbox.add(second);
        hbox.add(Box.createHorizontalGlue());
    }

    public JPanel getPanel() {
        JPanel panel=new JPanel();
        panel.add(hbox);
        panel.setMaximumSize(panel.getPreferredSize());
        return panel;
    }
    /**
     * @return 返回所选的选项Index
     */
    public int getSelectIndex(){
     return selectIndex;
    }
}
