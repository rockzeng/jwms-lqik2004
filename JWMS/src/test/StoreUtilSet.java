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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import method.dbOperation;
import method.propertiesRW;

/**
 *
 * @author res0w
 * @since 2009-12-02
 */
public class StoreUtilSet implements StoreUIer, StoreUtiler {

    private JComboBox storeComboBox = new JComboBox();
    private JPanel panel = new JPanel();
    private DataSetUtil dsu = new DataSetUtil();

    public StoreUtilSet() {
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

    public Object getSelectItem() {
        return storeComboBox.getSelectedItem();
    }

    public int getSelectIndex() {
        return storeComboBox.getSelectedIndex();
    }

    public void tableModelCHGAction(final TableUIer table) {
        storeComboBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                table.setSelectStore(storeComboBox.getSelectedItem().toString().trim());
            }
        });
    }

    /**
     * 查询仓库是否存在，返回boolean值
     * @param store 仓库名称
     * @return true OR false
     */
    public boolean isStoreExist(String store) {
        boolean result = false;
        try {
            String x = store;
            dbOperation isInfoExistDb = new dbOperation();
            isInfoExistDb.DBConnect();
            String sql = "select * from storet where store='" + x + "'";
            ResultSet rs = isInfoExistDb.DBSqlQuery(sql);
            if (rs.next()) {
                result = true;
            }
            isInfoExistDb.DBClosed();
        } catch (SQLException ex) {
            Logger.getLogger(StoreUtilSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
