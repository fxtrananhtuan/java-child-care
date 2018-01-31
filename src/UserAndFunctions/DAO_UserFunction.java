/*
 * DAO_UserFunction.java
 *
 * Created on June 22, 2009, 10:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package UserAndFunctions;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
/**
 *
 * @author Tuong Huy
 */
public class DAO_UserFunction {
    String con="";
    /** Creates a new instance of DAO_UserFunction */
    public DAO_UserFunction(String _con) {
        con = _con;
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
    
    public int executeNotQuery(CallableStatement cstmt, Connection conn, String sParaResult) throws SQLException{
        try{
            return cstmt.executeUpdate();
        }catch(SQLException ex){
            throw new SQLException(ex.toString());
        }
    }
    
    public Vector getFunctionOfUser(String sSQL) throws SQLException{
        Vector items=new Vector();
        Statement st=null;
        ResultSet rs=null;
        Connection objConn=null;
        
        try{
            objConn = this.getConnection();
            if (objConn != null){
                st = objConn.createStatement();
                
                rs = st.executeQuery(sSQL);
                
                if (rs!=null){
                    try{
                        String[] record;
                        while (rs.next()) {
                            record= new String[2];
                            record[0]=rs.getString("FunctionCode");
                            record[1]=rs.getString("FunctionName");
                            items.add(record);                            
                        }
                    }catch(SQLException sql){
                        sql.printStackTrace();
                    }
                } else{
                    return null;
                }
            }
            return items;
        }catch(SQLException ex){
            throw new SQLException(ex.toString());
        } finally{
            if (st!=null)
                st.close();
            if (rs!=null)
                rs.close();
            if (objConn!=null)
                objConn.close();
        }
        
    }
    
    public Object[] getDataForComboboxAccount() throws SQLException{
        ArrayList items=new ArrayList();
        Statement st=null;
        ResultSet rs=null;
        Connection objConn=null;
        String sSQL="Select Account From tbl_User Where IsFullControl='0'";
        try{
            objConn = this.getConnection();
            if (objConn != null){
                st = objConn.createStatement();
                
                rs = st.executeQuery(sSQL);
                
                if (rs!=null){
                    try{
                        while (rs.next()) {
                            items.add(rs.getString("Account"));
                        }
                    }catch(SQLException sql){
                        sql.printStackTrace();
                    }
                } else{
                    return items.toArray();
                }
            }
            return items.toArray();
        }catch(SQLException ex){
            throw new SQLException(ex.toString());
        } finally{
            if (st!=null)
                st.close();
            if (rs!=null)
                rs.close();
            if (objConn!=null)
                objConn.close();
        }        
    }

    public int checkIsFullControl(String sUserName) throws SQLException{
        int iResult=0;
        Statement st=null;
        ResultSet rs=null;
        Connection objConn=null;
        String sSQL="Select isFullControl From tbl_User Where Account=N'"+sUserName+"'";
        try{
            objConn = this.getConnection();
            if (objConn != null){
                st = objConn.createStatement();

                rs = st.executeQuery(sSQL);

                if (rs!=null){
                    try{
                        while (rs.next()) {
                            iResult=rs.getInt("isFullControl");
                        }
                    }catch(SQLException sql){
                        sql.printStackTrace();
                    }
                }
            }
           return iResult;
        }catch(SQLException ex){
            throw new SQLException(ex.toString());
        } finally{
            if (st!=null)
                st.close();
            if (rs!=null)
                rs.close();
            if (objConn!=null)
                objConn.close();
        }
    }

    public Object[] getPermistionList(String sAccountName, int iFunctionCode) throws SQLException{
        ArrayList items=new ArrayList();
        Statement st=null;
        ResultSet rs=null;
        Connection objConn=null;
        String sSQL="proc_GetPermistion '"+iFunctionCode+"', N'"+sAccountName+"'";
        try{
            objConn = this.getConnection();
            if (objConn != null){
                st = objConn.createStatement();
                
                rs = st.executeQuery(sSQL);

                if (rs!=null){
                    try{
                        while (rs.next()) {
                            items.add(rs.getInt("Add"));
                            items.add(rs.getInt("Edit"));
                            items.add(rs.getInt("Del"));
                        }
                    }catch(SQLException sql){
                        sql.printStackTrace();
                    }
                } else{
                    return items.toArray();
                }
            }
            return items.toArray();
        }catch(SQLException ex){
            throw new SQLException(ex.toString());
        } finally{
            if (st!=null)
                st.close();
            if (rs!=null)
                rs.close();
            if (objConn!=null)
                objConn.close();
        }

    }
    
    public int AddDeleteFunctionForUser(String _userName, int _iFunctionCode, int _iState, int[] iPermission) throws SQLException{
        Connection objConn=null;
        CallableStatement cstmt = null;        
        int iResult=-1;       
        try{
            
            objConn = getConnection();
            String sSQL="{call proc_Add_DeleteFunction(?,?,?,?,?,?,?)}";
            
            cstmt = objConn.prepareCall(sSQL);
            
            cstmt.setString("accountname", _userName);
            cstmt.setInt("functioncode", _iFunctionCode);
            cstmt.setInt("state", _iState);

            cstmt.setInt("addnew", iPermission[0]);
            cstmt.setInt("delete", iPermission[1]);
            cstmt.setInt("update", iPermission[2]);
            
            cstmt.registerOutParameter("result", java.sql.Types.INTEGER);
            cstmt.execute();
            iResult = cstmt.getInt(1);
            
            return iResult;
        }catch(SQLException ex){
            throw new SQLException(ex.toString());
        }finally {
            try{
                if (objConn!=null){
                    objConn.close();
                }
                if (cstmt!=null){
                    cstmt.close();
                }
            }catch(SQLException ex){
                throw new SQLException(ex.toString());
            }
        }
    }
    
    public int ChangePass(String _userName, String sOldPassWord, String sNewPassword) throws SQLException{
        Connection objConn=null;
        CallableStatement cstmt = null;        
        int iResult=-1;
        
        Date d = new Date();
        try{
            
            objConn = getConnection();
            String sSQL="{call proc_ChangePassword(?,?,?,?)}";
            
            cstmt = objConn.prepareCall(sSQL);

            cstmt.registerOutParameter("result", java.sql.Types.INTEGER);
            cstmt.setString("accountname", _userName);
            cstmt.setString("oldpass", sOldPassWord);
            cstmt.setString("newpass", sNewPassword);
            
            
            cstmt.execute();
            iResult = cstmt.getInt(1);
            
            return iResult;
        }catch(SQLException ex){
            throw new SQLException(ex.toString());
        }finally {
            try{
                if (objConn!=null){
                    objConn.close();
                }
                if (cstmt!=null){
                    cstmt.close();
                }
            }catch(SQLException ex){
                throw new SQLException(ex.toString());
            }
        }
    }
}
