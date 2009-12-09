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

import method.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author res0w
 * @since 2009-12-9
 */
public class MainT_DBOperate {

    private String store;
    private String info;
    private String amount;
    private String inPrice;
    private String outPrice;

    private boolean isInfoExist(String info, String store) throws SQLException {
        boolean result = false;
        String x = info;
        String y = store;
        dbOperation isInfoExistDb = new dbOperation();
        isInfoExistDb.DBConnect();
        String sql = "select * from maint where store='" + y + "' and info='" + x + "'";
        ResultSet rs = isInfoExistDb.DBSqlQuery(sql);
        if (rs.next()) {
            result = true;
        }
        isInfoExistDb.DBClosed();
        return result;
    }

    /**
     * 增加库存
     * @throws java.sql.SQLException
     */
    public void increaseMethod(String info, String amount, String inPrice, 
            String outPrice, String store) throws SQLException {

        boolean result = isInfoExist(info, store);
        dbOperation increaseDb = new dbOperation();
        if (result) {
            increaseDb.DBConnect();
            String sql = "update maint set amount=amount+'" + amount + "' where info='" + info + "' and store='" + store + "'";
            increaseDb.DBSqlExe(sql);
            increaseDb.DBClosed();
        } else {
            increaseDb.DBConnect();
            String sql = "insert into maint(info,amount,store,inPrice,outPrice) values('" + info + "','" + amount + "','" + store + "','" + inPrice + "','" + outPrice + "')";
            increaseDb.DBSqlExe(sql);
            increaseDb.DBClosed();
        }
    }

    /**
     * 减少库存
     * @return
     * @throws java.sql.SQLException
     */
    public boolean decreaseMethod(String info, String amount, String store) throws SQLException {
        
        boolean result = isInfoExist(info, store);
        ResultSet rs = null;
        dbOperation decreaseDb = new dbOperation();
        decreaseDb.DBConnect();
        String sql = "select amount from maint where info='" + info + "' and store='" + store + "'";
        rs = decreaseDb.DBSqlQuery(sql);
        if (rs.next()) {
            if (Integer.parseInt(rs.getString(1)) < Integer.parseInt(amount)) {
                result = false;
            }
        } else {
            result = false;
        }
        decreaseDb.DBClosed();
        if (result) {
            decreaseDb.DBConnect();
            sql = "update maint set amount=amount-'" + amount + "' where info='" + info + "' and store='" + store + "'";
            decreaseDb.DBSqlExe(sql);
            decreaseDb.DBClosed();
        }
        return result;
    }
}
