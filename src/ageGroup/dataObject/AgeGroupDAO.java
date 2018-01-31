/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ageGroup.dataObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import ageGroup.valuesObject.AgeGroupVO;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;

/**
 *
 * @author DThanh
 */
public class AgeGroupDAO {

    public AgeGroupDAO(String connect) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            // Obtain a connection to the database
            cnDetails = java.sql.DriverManager.getConnection(connect);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }

    public void addActivities(int ageCode, int activeCode) throws SQLException {
        String sql_ = "insert into tbl_Active_AgeGroup(GroupAgeCode,ActiveCode) values (" + ageCode + "," + activeCode + ")";
        stDetails = cnDetails.createStatement();
        stDetails.execute(sql_);
    }

    public void removeActivities(int ageCode, int activeCode) throws SQLException {
        String sql_ = "DELETE tbl_Active_AgeGroup WHERE GroupAgeCode = " + ageCode + "AND ActiveCode = " + activeCode;
        stDetails = cnDetails.createStatement();
        stDetails.execute(sql_);
    }

    public ArrayList showActivityAll(int ageCode) throws SQLException {
        String sql_ = "Select ActiveCode, NameActive from tbl_Activities Where ActiveCode not in (Select ActiveCode from tbl_Active_AgeGroup where GroupAgeCode = " + ageCode + ")";
        ArrayList item = new ArrayList();

        stDetails = cnDetails.createStatement();
        rsDetails = stDetails.executeQuery(sql_);
        while (rsDetails.next()) {
            item.add(rsDetails.getString(1) + "." + rsDetails.getString(2));
        }

        return item;
    }

    public ArrayList showActivitySelected(int ageCode) throws SQLException {
        String sql_ = "Select ActiveCode, NameActive from tbl_Activities Where ActiveCode in (Select ActiveCode from tbl_Active_AgeGroup where GroupAgeCode = " + ageCode + ")";
        ArrayList item = new ArrayList();

        stDetails = cnDetails.createStatement();
        rsDetails = stDetails.executeQuery(sql_);
        while (rsDetails.next()) {
            item.add(rsDetails.getString(1) + "." + rsDetails.getString(2));
        }

        return item;
    }

    public ResultSet getResultSetForDatabaseMetaData(String tableName) throws SQLException {
        // Retrieve the DatabaseMetaDeta object that contains metadata about
        // the database to which Connection object represents a connection
        DatabaseMetaData meta = cnDetails.getMetaData();

        // Retrieve the description of the table columns
        ResultSet results = meta.getColumns(null, null, tableName, null);
        return results;
    }

    public ResultSet getResultSetDetail(String tableName) throws SQLException {
        Statement stDetails_;
        ResultSet rsDetails_ = null;

        stDetails_ = cnDetails.createStatement();
        rsDetails_ = stDetails_.executeQuery("SELECT * FROM " + tableName);
        return rsDetails_;
    }

    public void insertRecord(AgeGroupVO ageVO) {
        try {
            callableStatement = cnDetails.prepareCall("{call insertAgeGroup(?,?)}");
            callableStatement.setString(1, ageVO.getChildAge());
            callableStatement.setDouble(2, ageVO.getCharges());
            callableStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteReord(AgeGroupVO ageVO) {
        try {
            callableStatement = cnDetails.prepareCall("{call deleteAgeGroup(?)}");
            callableStatement.setDouble(1, ageVO.getAgeCode());
            callableStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateRecord(AgeGroupVO ageVO) {
        try {
            callableStatement = cnDetails.prepareCall("{call updateAgeGroup(?,?,?)}");
            callableStatement.setInt(1, ageVO.getAgeCode());
            callableStatement.setString(2, ageVO.getChildAge());
            callableStatement.setDouble(3, ageVO.getCharges());
            callableStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (cnDetails != null) {
                cnDetails.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Variable to store a reference to the Connection object.
     */
    private Connection cnDetails = null;
    private CallableStatement callableStatement = null;
    /**
     * Statement object to hold a reference to the Statement object.
     */
    private Statement stDetails;
    /**
     * ResultSet object to hold a reference to the ResultSet object.
     */
    private ResultSet rsDetails;
}
