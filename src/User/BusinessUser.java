/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Nobitawin
 */
public class BusinessUser {

    public BusinessUser(String tableName, String sConn) {
        dao = new DataAccessTableUser(tableName, sConn);
    }

    public void deleteAccount(String tableName, String userCode) throws SQLException {
        dao.deleteDatabase(tableName, userCode);
    }

    public void insertUser(String tableName, String name, String account, String password, int isFullControl) throws SQLException {
        dao.insertDatabase(tableName, name, account, password, isFullControl);
    }
//

    public void updateUser(String tableName, String name, String account, int isFullControl, String userCode, int reset) throws SQLException {
        if (reset == 1) {
            dao.updateDatabaseResetPassword(tableName, name, account, isFullControl, userCode);
        } else {
            dao.updateDatabase(tableName, name, isFullControl, userCode);
        }
    }

    public ResultSet searchAccount(String tableName, String search) throws SQLException {
        return dao.getResultSetSearch(tableName, search);
    }

    public ResultSet checkAccount(String tableName, String account) throws SQLException {
        return dao.getResultSetAccount(tableName, account);
    }
    DataAccessTableUser dao = null;
}

