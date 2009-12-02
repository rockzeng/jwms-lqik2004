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

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import method.propertiesRW;

/**
 *
 * @author res0w
 * @since 2009-12-02
 */
public class StoreUtil implements StoreUI {

    private JComboBox storeComboBox = new JComboBox();
    private JPanel panel=new JPanel();
    private DataSetUtil dsu = new DataSetUtil();

    public StoreUtil() {
        //初始化数据库，读入信息
        for (int i = 0; i < dsu.storeLoad().size(); i++) {
            storeComboBox.addItem(dsu.storeLoad().get(i));
        }
        storeComboBox.setMaximumSize(storeComboBox.getPreferredSize());
        storeComboBox.setEditable(false);   //仓库不可直接修改
        Box hbox = Box.createVerticalBox();
        hbox.add(storeComboBox);
        panel.add(hbox);
        panel.setMaximumSize(panel.getPreferredSize());
    }

    public JPanel StorePanel(String readTag) {

        //从properties中读取仓库设置
        storeComboBox.setSelectedIndex(Integer.parseInt(propertiesRW.proIDMakeRead(readTag)));
        return panel;
    }

    public JComboBox getComponent() {
        return storeComboBox;
    }
}
