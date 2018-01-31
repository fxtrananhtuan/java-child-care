/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Nanny;

import java.sql.CallableStatement;
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
        //return url + serverName + ";database=" + databaseName;
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
        rsDetails_ = stDetails_.executeQuery("SELECT ROW_NUMBER() OVER (ORDER BY Name Asc), NannyCode, Name, YearOfBirth, Address, Phone, Mail, Charge, WorkingHours, ChildAssignedToHer FROM " + tableName + " ORDER BY Name Asc");

        return rsDetails_;
    }

    public ResultSet getResultSetSearch(String tableName, String search) throws SQLException {
        String sql = "SELECT ROW_NUMBER() OVER (ORDER BY Name Asc), NannyCode, Name, YearOfBirth, Address, Phone, Mail, Charge, WorkingHours, ChildAssignedToHer FROM " + tableName + " WHERE Name LIKE ? ORDER BY Name Asc";

        PreparedStatement pstSearch_;
        ResultSet rsSearch_ = null;

        pstSearch_ = cnDetails.prepareStatement(sql);

        pstSearch_.setString(1, "%" + search + "%");

        rsSearch_ = pstSearch_.executeQuery();

        return rsSearch_;
    }

    private CallableStatement cs = null;
     private Connection cnDetails = null;

    public void insert(Entity nanny) {
        try {
            cs = cnDetails.prepareCall("{call naAddNanny(?,?,?,?,?,?,?,?)}");
            cs.setString(1, nanny.getName());
            cs.setInt(2, nanny.getYearofbirth());
            cs.setString(3, nanny.getAddress());
            cs.setString(4, nanny.getPhone());
            cs.setString(5, nanny.getMail());
            cs.setDouble(6, nanny.getCharge());
            cs.setInt(7, nanny.getWorkinghours());
            cs.setInt(8, nanny.getChildassignedtoher());

            cs.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cnDetails != null) {
                    cnDetails.close();
                }
                if (cs != null) {
                    cs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void update(Entity nanny) {
        try {
            cs = cnDetails.prepareCall("{call naUpdateNanny(?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, nanny.getCode());
            cs.setString(2, nanny.getName());
            cs.setInt(3, nanny.getYearofbirth());
            cs.setString(4, nanny.getAddress());
            cs.setString(5, nanny.getPhone());
            cs.setString(6, nanny.getMail());
            cs.setDouble(7, nanny.getCharge());
            cs.setInt(8, nanny.getWorkinghours());
            cs.setInt(9, nanny.getChildassignedtoher());

            cs.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cnDetails != null) {
                    cnDetails.close();
                }
                if (cs != null) {
                    cs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    
    public ResultSet getResultSetNameActive(String tableName, String nameActive) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE Name LIKE ?";
        PreparedStatement pstNameActive_;
        ResultSet checkNameActive_ = null;

        pstNameActive_ = cnDetails.prepareStatement(sql);

        pstNameActive_.setString(1, nameActive);

        checkNameActive_ = pstNameActive_.executeQuery();

        return checkNameActive_;

    }

    public void delete(String tableName, String NannyCode) throws SQLException {
        Statement stDelete_;

        stDelete_ = cnDetails.createStatement();
        stDelete_.executeUpdate("delete from " + tableName + " where NannyCode = '" + NannyCode + "'");
    }
//
//    public void insertDatabase(String tableName, String nameActive, String rates) throws SQLException {
//        String sql = "INSERT INTO " + tableName + " VALUES (?,?)";
//
//        PreparedStatement pstInsert_;
//        ResultSet rsSearch_ = null;
//
//        pstInsert_ = cnDetails.prepareStatement(sql);
//
//        pstInsert_.setString(1, nameActive);
//        pstInsert_.setDouble(2, Double.parseDouble(rates));
//
//        pstInsert_.executeUpdate();
//    }
//
//    public void updateDatabase(String tableName, String nameActive, String rates, String activeCode) throws SQLException {
//        String sql = "UPDATE " + tableName + " SET nameActive = ?, rates = ? WHERE ActiveCode = '" + activeCode + "'";
//
//        PreparedStatement pstInsert_;
//        ResultSet rsSearch_ = null;
//
//        pstInsert_ = cnDetails.prepareStatement(sql);
//
//        pstInsert_.setString(1, nameActive);
//        pstInsert_.setDouble(2, Double.parseDouble(rates));
//
//        pstInsert_.executeUpdate();
//    }



    private String sConn = null;
 
}
