/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nobitawin
 */
public class DataAccessTableUser {

    public DataAccessTableUser(String tableName, String sConn) {

        this.sConn = sConn;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataAccessTableUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            cnDetails = java.sql.DriverManager.getConnection(getConnectionUrl());

            if (cnDetails != null) {
                getResultSerForDatabaseMetaData(tableName);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataAccessTableUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getConnectionUrl() {
        return this.sConn;
    }

    public ResultSet getResultSerForDatabaseMetaData(String tableName) throws SQLException {
        DatabaseMetaData meta = cnDetails.getMetaData();
        ResultSet results = meta.getColumns(null, null, tableName, null);

        return results;
    }

    public ResultSet getResultSetDetail(String tableName) throws SQLException {
        Statement stDetails_;
        ResultSet rsDetails_ = null;

        stDetails_ = cnDetails.createStatement();
        rsDetails_ = stDetails_.executeQuery("SELECT ROW_NUMBER() OVER (ORDER BY Account Asc), UserCode, NameOfUser, Account, Password, IsFullControl FROM " + tableName + " ORDER BY Account Asc");

        return rsDetails_;
    }

    public ResultSet getResultSetSearch(String tableName, String search) throws SQLException {
        String sql = "SELECT ROW_NUMBER() OVER (ORDER BY Account Asc), UserCode, NameOfUser, Account, Password, IsFullControl FROM " + tableName + " WHERE Account LIKE ? ORDER BY Account Asc";

        PreparedStatement pstSearch_;
        ResultSet rsSearch_ = null;

        pstSearch_ = cnDetails.prepareStatement(sql);

        pstSearch_.setString(1, "%" + search + "%");

        rsSearch_ = pstSearch_.executeQuery();

        return rsSearch_;
    }

    public ResultSet getResultSetAccount(String tableName, String account) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE account = ?";
        PreparedStatement pstNameActive_;
        ResultSet checkNameActive_ = null;

        pstNameActive_ = cnDetails.prepareStatement(sql);

        pstNameActive_.setString(1, account);

        checkNameActive_ = pstNameActive_.executeQuery();

        return checkNameActive_;

    }

    public void deleteDatabase(String tableName, String userCode) throws SQLException {
        Statement stDelete_;

        stDelete_ = cnDetails.createStatement();
        stDelete_.executeUpdate("delete from " + tableName + " where usercode = '" + userCode + "'");
    }

    public void insertDatabase(String tableName, String name, String account, String password, int isFullControl) throws SQLException {
        String sql = "INSERT INTO " + tableName + " VALUES (?,?,?,?)";

        PreparedStatement pstInsert_;
        //ResultSet rsSearch_ = null;

        pstInsert_ = cnDetails.prepareStatement(sql);

        pstInsert_.setString(1, name);
        pstInsert_.setString(2, account);
        pstInsert_.setString(3, password);
        pstInsert_.setInt(4, isFullControl);

        pstInsert_.executeUpdate();
    }

    public void updateDatabaseResetPassword(String tableName, String name, String password, int isFullControl, String userCode) throws SQLException {
        String sql = "UPDATE " + tableName + " SET nameofuser = ?, password = ?, isfullcontrol = ? WHERE userCode = '" + userCode + "'";

        PreparedStatement pstInsert_;


        pstInsert_ = cnDetails.prepareStatement(sql);

        String isFullControl_;
        if (isFullControl == 1) {
            isFullControl_ = "true";
        } else {
            isFullControl_ = "false";
        }
        pstInsert_.setString(1, name);
        pstInsert_.setString(2, password);
        pstInsert_.setString(3, isFullControl_);

        pstInsert_.executeUpdate();
    }

    public void updateDatabase(String tableName, String name, int isFullControl, String userCode) throws SQLException {
        String sql = "UPDATE " + tableName + " SET nameofuser = ?, isfullcontrol = ? WHERE userCode = '" + userCode + "'";

        PreparedStatement pstInsert_;


        pstInsert_ = cnDetails.prepareStatement(sql);

        String isFullControl_;
        if (isFullControl == 1) {
            isFullControl_ = "true";
        } else {
            isFullControl_ = "false";
        }
        pstInsert_.setString(1, name);
        pstInsert_.setString(2, isFullControl_);

        pstInsert_.executeUpdate();
    }
    private String sConn = null;
    private Connection cnDetails = null;
}
