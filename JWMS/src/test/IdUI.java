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

import javax.swing.JPanel;

/**
 *
 * @author res0w
 * @since 2009-12-1
 */
public interface IdUI {

    /**
     *
     * @param tag 写入单据tag，如销售单据为'S'
     * @return 返回包含ID的panel
     */
    public JPanel IdUI(String tag);

    /**
     * 此方法可以同时返回String格式的完整ID编号，同时也会写入数据库，但不会修改UI显示，谨慎使用。
     * @param tag 写入单据tag，如销售单据为'S'
     * @param year 年份
     * @param month 月份
     * @param day 日
     * @return 返回完整ID
     * @deprecated 请谨慎使用，下个版本可能删除
     */
    public String setGetID(String tag, String year, String month, String day);

    /**
     * 修改IDUI显示
     * @param tag 写入单据tag，如销售单据为'S'
     * @param year 年份
     * @param month 月份
     * @param day 日
     */
    public void setUIId(String tag, String year, String month, String day);
}
