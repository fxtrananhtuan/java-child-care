/*
 * DAO_Child.java
 *
 * Created on June 7, 2009, 5:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package InformationOfChild;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tuong Huy
 */
public class DAO_Child {

    String con = "";

    /** Creates a new instance of DAO_Child */
    public DAO_Child(String _con) {
        con = _con;
    }

    public ResultSet readData(Connection c, String sSQL, Statement stmt) throws SQLException {
        if (c == null) {
            return null;
        }

        try {
            return stmt.executeQuery(sSQL);
        } catch (SQLException ex) {
            throw new SQLException(ex.toString());
        }
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

    public boolean blCheckIsNumber(String sNumber) throws NumberFormatException {
        boolean isNumber = true;
        try {
            Double.parseDouble(sNumber);
        } catch (NumberFormatException e) {
            isNumber = false;
            throw new NumberFormatException(e.toString());
        }
        return isNumber;
    }

    public boolean isValidDate(String inDate) {

        if (inDate == null) {
            return false;
        }

        //set the format to use as a constructor argument :: yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (inDate.trim().length() != dateFormat.toPattern().length()) {
            return false;
        }

        dateFormat.setLenient(false);

        try {
            //parse the inDate parameter
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public boolean isEmail(String email) {
        //Input the string for validation= "xyz@hotmail.com";

        //Set the email pattern string
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

        //Match the given string with the pattern
        Matcher m = p.matcher(email);

        //check whether match is found
        boolean matchFound = m.matches();

        if (matchFound) {
            return true;
        } else {
            return false;
        }
    }

    public int executeNotQuery(CallableStatement cstmt, Connection conn, String sParaResult) throws SQLException {
        try {
            return cstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.toString());
        }
    }

    public Child getInformationChildFromDatabase(String childCode) throws SQLException {
        Child child = null;
        Statement st = null;
        ResultSet rs = null;
        Connection objConn = null;

        String sSQL = "Select c.ChildCode, c.GroupAgeCode, ag.AgeGroup, c.LastName, c.FirstName, c.MiddleName, CONVERT(varchar(10), c.DateOfBirth, 103) as DateBirth, ";
        sSQL += "CurrentMedication, PastIllness, DoctorName, ParentName, ParentWorkNumber, ";
        sSQL += "ParentMobileNumber, ParentEmailAddress, NextOfKinContact, NoteAboutChild, CONVERT(varchar(10), RegistrationDate, 103) as RegisDate, ";
        sSQL += "CONVERT(varchar(10), DateReceived, 103) as Date_Received from tbl_Child c, tbl_AgeGroup ag Where c.ChildCode='" + Integer.parseInt(childCode) + "'";
        sSQL += " and c.GroupAgeCode=ag.GroupAgeCode";

        try {
            objConn = getConnection();
            if (objConn != null) {

                st = objConn.createStatement();
                rs = st.executeQuery(sSQL);
                if (rs != null) {
                    rs.next();
                    child = new Child();
                    child.setChildCode(rs.getString("ChildCode"));
                    child.setFirstName(rs.getString("FirstName"));
                    child.setLastName(rs.getString("LastName"));
                    child.setMiddleName(rs.getString("MiddleName"));
                    child.setDateOfBirth(rs.getString("DateBirth"));
                    child.setCurrentMedication(rs.getString("CurrentMedication"));
                    child.setPassIllness(rs.getString("PastIllness"));
                    child.setDoctorName(rs.getString("DoctorName"));
                    child.setParentName(rs.getString("ParentName"));
                    child.setParentWorkNumber(rs.getString("ParentWorkNumber"));
                    child.setParentMobileNumber(rs.getString("ParentMobileNumber"));
                    child.setParentEmailAddress(rs.getString("ParentEmailAddress"));
                    child.setnextOfKinContact(rs.getString("NextOfKinContact"));
                    child.setnoteAboutChild(rs.getString("NoteAboutChild"));
                    child.setDateRegistration(rs.getString("RegisDate"));
                    child.setDateReceived(rs.getString("Date_Received"));
                    child.setGroupAgeCode(rs.getString("AgeGroup"));
                } else {
                    return null;
                }
            }
            return child;
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

    public String getNannyName(String childCode) throws SQLException {
        String sNannyName = "";
        String sSQL = "";

        Statement st = null;
        ResultSet rs = null;
        Connection objConn = null;

        try {
            objConn = getConnection();

            if (objConn != null) {
                if (!childCode.equals("")) {
                    sSQL = "Select [Name] From tbl_Nanny n, tbl_Child_Nanny cn Where n.NannyCode=cn.NannyCode And cn.ChildCode='" + childCode + "'";
                    st = objConn.createStatement();
                    rs = st.executeQuery(sSQL);

                    if (rs != null) {
                        try {
                            while (rs.next()) {
                                sNannyName = rs.getString("Name");
                            }
                        } catch (SQLException ex) {
                            throw new SQLException(ex.toString());
                        }
                    }
                }
            }
            return sNannyName;
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

    public Object[] showComboxData(String sSQL) throws SQLException {
        ArrayList items = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        Connection objConn = null;

        try {
            objConn = getConnection();
            if (objConn != null) {
                st = objConn.createStatement();

                rs = st.executeQuery(sSQL);
                if (rs != null) {
                    try {
                        while (rs.next()) {
                            items.add(rs.getString("AgeGroup"));
                        }
                    } catch (SQLException sql) {
                        sql.printStackTrace();
                    }
                } else {
                    throw new SQLException("Can not load data");
                }
            }
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
        return items.toArray();
    }

    public int AddChild(Child _child) throws SQLException {
        Child child = (Child) _child;
        Connection objConn = null;
        CallableStatement cstmt = null;
        int iResult = -1;

        try {

            objConn = getConnection();
            String sSQL = "{call proc_Child_AddNewChild(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

            cstmt = objConn.prepareCall(sSQL);

            cstmt.setString("childcode", child.getChildCode());
            cstmt.setString("nannycode", child.getNannyCode());
            cstmt.setString("groupagename", child.getGroupAgeCode());
            cstmt.setString("lastname", child.getLastName());
            cstmt.setString("firstname", child.getFirstName());
            cstmt.setString("middlename", child.getMiddleName());

            cstmt.setDate("dateofbirth", (java.sql.Date) getInsertDate(child.getDateOfBirth()));

            cstmt.setString("currentmedication", child.getCurrentMedication());
            cstmt.setString("passillness", child.getPassIllness());
            cstmt.setString("doctorname", child.getDoctorName());
            cstmt.setString("parentname", child.getParentName());
            cstmt.setString("parentworknumber", child.getParentWorkNumber());
            cstmt.setString("parentmobilenumber", child.getParentMobileNumber());
            cstmt.setString("parentemailaddress", child.getParentEmailAddress());
            cstmt.setString("parentmobilenumber", child.getParentMobileNumber());
            cstmt.setString("nextofkincontact", child.getnextOfKinContact());
            cstmt.setString("noteaboutchild", child.getnoteAboutChild());

            java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());

            cstmt.setDate("registrationdate", sqlDate);
            cstmt.setDate("daterecived", sqlDate);

            cstmt.registerOutParameter("result", java.sql.Types.INTEGER);
            cstmt.execute();
            iResult = cstmt.getInt(1);

            return iResult;
        } catch (SQLException ex) {
            throw new SQLException(ex.toString());
        } finally {
            try {
                if (objConn != null) {
                    objConn.close();
                }
                if (cstmt != null) {
                    cstmt.close();
                }
            } catch (SQLException ex) {
                throw new SQLException(ex.toString());
            }
        }
    }

    public int UpdateChild(Child _child) throws SQLException {
        Child child = (Child) _child;
        Connection objConn = null;
        CallableStatement cstmt = null;
        int iResult = -1;

        try {

            objConn = getConnection();
            String sSQL = "{call proc_Child_UpdateChild(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

            cstmt = objConn.prepareCall(sSQL);

            cstmt.setString("childcode", child.getChildCode());
            cstmt.setString("groupagename", child.getGroupAgeCode());
            cstmt.setString("lastname", child.getLastName());
            cstmt.setString("firstname", child.getFirstName());
            cstmt.setString("middlename", child.getMiddleName());

            cstmt.setDate("dateofbirth", (java.sql.Date) getInsertDate(child.getDateOfBirth()));
            cstmt.setString("currentmedication", child.getCurrentMedication());
            cstmt.setString("pastillness", child.getPassIllness());
            cstmt.setString("doctorname", child.getDoctorName());
            cstmt.setString("parentname", child.getParentName());

            cstmt.setString("parentworknumber", child.getParentWorkNumber());
            cstmt.setString("parentmobilenumber", child.getParentMobileNumber());
            cstmt.setString("parentemailaddress", child.getParentEmailAddress());
            cstmt.setString("parentmobilenumber", child.getParentMobileNumber());

            cstmt.setString("nextofkincontact", child.getnextOfKinContact());
            cstmt.setString("noteaboutchild", child.getnoteAboutChild());
            cstmt.setDate("registrationdate", (java.sql.Date) getInsertDate(child.getDateRegistration()));
            cstmt.setDate("daterecived", (java.sql.Date) getInsertDate(child.getDateReceived()));

            cstmt.registerOutParameter("result", java.sql.Types.INTEGER);
            cstmt.execute();
            iResult = cstmt.getInt(1);

            return iResult;
        } catch (SQLException ex) {
            throw new SQLException(ex.toString());
        } finally {
            try {
                if (objConn != null) {
                    objConn.close();
                }
                if (cstmt != null) {
                    cstmt.close();
                }
            } catch (SQLException ex) {
                throw new SQLException(ex.toString());
            }
        }
    }

    public int DeleteChild(int childCode) throws SQLException {
        Connection objConn = null;
        CallableStatement cstmt = null;
        int iResult = -1;

        try {

            objConn = getConnection();
            String sSQL = "{call proc_Child_DeleteChild(?,?)}";

            cstmt = objConn.prepareCall(sSQL);

            cstmt.setInt("childcode", childCode);
            cstmt.registerOutParameter("result", java.sql.Types.INTEGER);
            cstmt.execute();
            iResult = cstmt.getInt(1);

            return iResult;
        } catch (SQLException ex) {
            throw new SQLException(ex.toString());
        } finally {
            try {
                if (objConn != null) {
                    objConn.close();
                }
                if (cstmt != null) {
                    cstmt.close();
                }
            } catch (SQLException ex) {
                throw new SQLException(ex.toString());
            }
        }
    }

    public int ChangeNannyForChild(int _childCode, int _nannyCode) throws SQLException {
        Connection objConn = null;
        CallableStatement cstmt = null;
        int iResult = -1;
        try {

            objConn = getConnection();
            String sSQL = "{call proc_Child_Nanny(?,?,?)}";

            cstmt = objConn.prepareCall(sSQL);

            cstmt.setInt("childcode", _childCode);
            cstmt.setInt("nannycode", _nannyCode);
            cstmt.registerOutParameter("result", java.sql.Types.INTEGER);
            cstmt.execute();
            iResult = cstmt.getInt(1);

            return iResult;
        } catch (SQLException ex) {
            throw new SQLException(ex.toString());
        } finally {
            try {
                if (objConn != null) {
                    objConn.close();
                }
                if (cstmt != null) {
                    cstmt.close();
                }
            } catch (SQLException ex) {
                throw new SQLException(ex.toString());
            }
        }
    }

    public int countChild() throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        Connection objConn = null;
        String sSQL = "Select COUNT(ChildCode) as numOfChild From tbl_Child";
        int numOfChild = 0;
        try {
            objConn = getConnection();
            if (objConn != null) {
                st = objConn.createStatement();

                rs = st.executeQuery(sSQL);
                if (rs != null) {
                    rs.next();
                    numOfChild = rs.getInt("numOfChild");
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.toString());
        } finally {
            st.close();
            rs.close();
            objConn.close();
        }
        return numOfChild;
    }

    public static Date getInsertDate(String s) {
        java.sql.Date dte = null;
        try {
            String str = s;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date dt = formatter.parse(str);
            dte = new java.sql.Date(dt.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dte;
    }
}
