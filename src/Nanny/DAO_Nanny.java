/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Nanny;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Bao Minh
 */
public class DAO_Nanny {
    String con = "";
    public DAO_Nanny(String sConn){
        con=sConn;
    }
    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            return null;
        }

        try {
            return DriverManager.getConnection(con);
        } catch (SQLException ex) {
            return null;
        }
    }
    public Entity getInformationNanny(String nannyCode) throws SQLException {
        Entity nanny = null;
        Statement st = null;
        ResultSet rs = null;
        Connection objConn = null;

        String sSQL = "Select * from tbl_Nanny Where NannyCode='"+Integer.parseInt(nannyCode)+"'";

        try {
            objConn = getConnection();
            if (objConn != null) {

                st = objConn.createStatement();
                rs = st.executeQuery(sSQL);
                if (rs != null) {
                    rs.next();
                    nanny=new Entity(rs.getString("Name"), rs.getInt("YearOfBirth"), rs.getString("Address"),
                            rs.getString("Phone"), rs.getString("Mail"), rs.getDouble("Charge"), rs.getInt("WorkingHours"), rs.getInt("ChildAssignedToHer"));
                } else {
                    return null;
                }
            }
            return nanny;
        } catch (SQLException ex) {
            throw new SQLException(ex.toString());
        } finally {
            if (st != null) {
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (objConn != null) {
                objConn.close();
            }
        }
    }
}
