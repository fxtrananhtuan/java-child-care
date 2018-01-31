/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Nanny;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author CristianoT
 */
public class Business {

    DataAccessTable dao = null;

    public Business(String tableName, String sConn) {
        dao = new DataAccessTable(tableName, sConn);
    }

    public void deleteNanny(String tableName, String NannyCode) throws SQLException {
        dao.delete(tableName, NannyCode);
    }

    public ResultSet searchNanny(String tableName, String search) throws SQLException {
        return dao.getResultSetSearch(tableName, search);
    }

     public ResultSet checkActivity(String tableName, String nameActive) throws SQLException {
        return dao.getResultSetNameActive(tableName, nameActive);
    }
}
