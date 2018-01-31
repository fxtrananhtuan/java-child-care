/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Activity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Nobitawin
 */
public class Business {

    public Business(String tableName, String sConn) {
        dao = new DataAccessTable(tableName, sConn);
    }

    public void deleteActivity(String tableName, String activeCode) throws SQLException {
        dao.deleteDatabase(tableName, activeCode);
    }

    public void insertActivity(String tableName, String nameActive, String rates) throws SQLException {
        dao.insertDatabase(tableName, nameActive, rates);
    }

    public void updateActivity(String tableName, String nameActive, String rates, String activeCode) throws SQLException {
        dao.updateDatabase(tableName, nameActive, rates, activeCode);
    }

    public ResultSet searchActivity(String tableName, String search) throws SQLException {
        return dao.getResultSetSearch(tableName, search);
    }

    public ResultSet checkActivity(String tableName, String nameActive) throws SQLException {
        return dao.getResultSetNameActive(tableName, nameActive);
    }
    DataAccessTable dao = null;
}

