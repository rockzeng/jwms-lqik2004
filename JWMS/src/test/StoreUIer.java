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
 * @since 2009-12-2
 */
public interface StoreUIer {

    /**
     * 返回panel类型的store面板
     * @param readTag readTag是导入仓库初始化选择序列信息的关键字，保存在properties文件
     *                 中。可以从下列选择一个：inStoreEqual 同价调拨进货仓库
     *                  outStoreEqual 同价调拨出货仓库
     *                  ...待续
     * @return 返回panel类型的store面板
     */
    public JPanel StorePanel(String readTag);

    public Object getSelectItem();

    public int getSelectIndex();

    /**
     * 当仓库的选择发生变化的时候带动talble的模型数据也发生变化
     * 由于此方法需配合TableUI使用，所以需要在tableui创建之后使用，且必须使用
     * @param table 传入TableUI类型的TABLE，用来控制table
     * @important 由于此方法需配合TableUI使用，所以需要在tableui创建之后使用，且必须使用
     */
    public void tableModelCHGAction(final TableUIer table);
}
