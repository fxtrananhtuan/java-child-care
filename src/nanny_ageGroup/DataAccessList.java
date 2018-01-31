/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanny_ageGroup;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author DThanh
 */
public class DataAccessList {

    public DataAccessList(String connect) {
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

    public void addAgeGroup(int nanny, int ageCode) throws SQLException {
        String sql_ = "insert into tbl_Nanny_AgeGroup(NannyCode,GroupAgeCode) values (" + nanny + "," + ageCode + ")";
        stDetails = cnDetails.createStatement();
        stDetails.execute(sql_);
    }

    public void removeAgeGroup(int nanny, int ageCode) throws SQLException {
        String sql_ = "DELETE tbl_Nanny_AgeGroup WHERE NannyCode = " + nanny + "AND GroupAgeCode = " + ageCode;
        stDetails = cnDetails.createStatement();
        stDetails.execute(sql_);
    }

    public ArrayList showAgeGroupAll(int nannyCode) throws SQLException {
        String sql_ = "Select GroupAgeCode, AgeGroup from tbl_AgeGroup Where GroupAgeCode not in (Select GroupAgeCode from tbl_Nanny_AgeGroup where NannyCode = " + nannyCode + ")";
        ArrayList item = new ArrayList();

        stDetails = cnDetails.createStatement();
        rsDetails = stDetails.executeQuery(sql_);
        while (rsDetails.next()) {
            item.add(rsDetails.getString(1) + "." + rsDetails.getString(2));
        }

        return item;
    }

    public ArrayList showAgeGroupSelected(int nannyCode) throws SQLException {
//        JOptionPane.showMessageDialog(null,"fgdfgf");
        String sql_ = "Select GroupAgeCode, AgeGroup from tbl_AgeGroup Where GroupAgeCode in (Select GroupAgeCode from tbl_Nanny_AgeGroup where NannyCode = " + nannyCode + ")";
        ArrayList item = new ArrayList();

        stDetails = cnDetails.createStatement();
        rsDetails = stDetails.executeQuery(sql_);
        while (rsDetails.next()) {
            item.add(rsDetails.getString(1) + "." + rsDetails.getString(2));
        }

        return item;
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
