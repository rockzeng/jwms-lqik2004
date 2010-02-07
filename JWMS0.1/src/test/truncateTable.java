/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import method.dbOperation;

/**
 *
 * @author Administrator
 */
public class truncateTable {
    public static void main(String[] args){
        String sql=null;
        String sql1="TRUNCATE TABLE ";
        dbOperation db=new dbOperation();
        db.DBConnect();

    }
}
