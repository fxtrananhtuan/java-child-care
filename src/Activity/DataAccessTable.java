/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Activity;

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
public class DataAccessTable {

    public DataAccessTable(String tableName, String sConn) {

        this.sConn = sConn;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataAccessTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            cnDetails = java.sql.DriverManager.getConnection(getConnectionUrl());

            if (cnDetails != null) {
                getResultSerForDatabaseMetaData(tableName);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataAccessTable.class.getName()).log(Level.SEVERE, null, ex);
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
        rsDetails_ = stDetails_.executeQuery("SELECT ROW_NUMBER() OVER (ORDER BY NameActive Asc), ActiveCode, NameActive, Rates FROM " + tableName + " ORDER BY NameActive Asc");

        return rsDetails_;
    }

    public ResultSet getResultSetSearch(String tableName, String search) throws SQLException {
        String sql = "SELECT ROW_NUMBER() OVER (ORDER BY NameActive Asc), ActiveCode, NameActive, Rates FROM " + tableName + " WHERE NameActive LIKE ? ORDER BY NameActive Asc";

        PreparedStatement pstSearch_;
        ResultSet rsSearch_ = null;

        pstSearch_ = cnDetails.prepareStatement(sql);

        pstSearch_.setString(1, "%" + search + "%");

        rsSearch_ = pstSearch_.executeQuery();

        return rsSearch_;
    }

    public ResultSet getResultSetNameActive(String tableName, String nameActive) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE NameActive LIKE ?";
        PreparedStatement pstNameActive_;
        ResultSet checkNameActive_ = null;

        pstNameActive_ = cnDetails.prepareStatement(sql);

        pstNameActive_.setString(1, nameActive);

        checkNameActive_ = pstNameActive_.executeQuery();

        return checkNameActive_;

    }

    public void deleteDatabase(String tableName, String activeCode) throws SQLException {
        Statement stDelete_;

        stDelete_ = cnDetails.createStatement();
        stDelete_.executeUpdate("delete from " + tableName + " where activecode = '" + activeCode + "'");
    }

    public void insertDatabase(String tableName, String nameActive, String rates) throws SQLException {
        String sql = "INSERT INTO " + tableName + " VALUES (?,?)";

        PreparedStatement pstInsert_;

        pstInsert_ = cnDetails.prepareStatement(sql);

        pstInsert_.setString(1, nameActive);
        pstInsert_.setDouble(2, Double.parseDouble(rates));

        pstInsert_.executeUpdate();
    }

    public void updateDatabase(String tableName, String nameActive, String rates, String activeCode) throws SQLException {
        String sql = "UPDATE " + tableName + " SET nameActive = ?, rates = ? WHERE ActiveCode = '" + activeCode + "'";

        PreparedStatement pstInsert_;

        pstInsert_ = cnDetails.prepareStatement(sql);

        pstInsert_.setString(1, nameActive);
        pstInsert_.setDouble(2, Double.parseDouble(rates));

        pstInsert_.executeUpdate();
    }
    private String sConn = null;
    private Connection cnDetails = null;
}
