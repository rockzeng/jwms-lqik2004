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
import javax.swing.table.TableModel;

/**
 *
 * @author res0w
 * @since 2009-12-1
 */
public interface TableUIer {

    public JPanel getPanel();

    public void setSelectStore(String store);

    public TableModel getModel();

    public Object[][] getTableDataSet();
}
