/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class DAO_Login {

    private String sConnection = "";

    public DAO_Login() {
    }

    public void setConnectionString(String _sConn) {
        sConnection = _sConn;
    }

    public Connection CreateConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            throw new SQLException(ex);
        }
        try {
            // Obtain a connection to the database
            conn = java.sql.DriverManager.getConnection(sConnection);
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return conn;
    }

    public Object[] getConfig(String sFilePath) {
        FileReader file;
        BufferedReader reader;
        String record = null;
        ArrayList loginInfo = new ArrayList();
        Object[] sInfo = null;
        try {
            file = new FileReader(sFilePath);
            reader = new BufferedReader(file);

            while ((record = reader.readLine()) != null) {
                loginInfo.add(record);
            }
            if (!loginInfo.isEmpty()) {
                sInfo = loginInfo.toArray();
            }
            reader.close();
            file.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sInfo;
    }

    public boolean saveConfig(LoginInfo configInfo, String sFilepath) {
        FileWriter f;
        BufferedWriter wr;
        boolean isOK = false;
        if (configInfo == null) {
            isOK = false;
        } else {
            try {
                f = new FileWriter(sFilepath, false);
                wr = new BufferedWriter(f);
                wr.write(configInfo.getServerName() + "\n" + configInfo.getDatabaseName() +
                        "\n" + configInfo.getUserName() + "\n" + configInfo.getPassword());

                wr.flush();
                wr.close();
                f.close();
                isOK = true;
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return isOK;
    }

    public boolean checkFile(String sFilePath) throws IOException {
        boolean isState = true;
        File f = new File(sFilePath);
        if (!f.isFile()) {
            try {
                f.createNewFile();
                isState = false;
            } catch (IOException ex) {
                throw new IOException(ex);
            }
        }
        return isState;
    }

    public Object[] DoLogin(String sUserName, String sPassword) throws SQLException {
        ArrayList function = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        Connection objConn = null;
        
//        String sSQL = "Select FunctionCode From tbl_Functions Where " +
//                "FunctionCode in (Select FunctionCode from tbl_User_Functions Where " +
//                "UserCode = (Select UserCode From tbl_User Where Account=N'" + sUserName + "' " +
//                "And Password=N'" + sPassword + "')) Order By FunctionCode Asc";
        
        String sSQL="proc_GetLogin N'"+sUserName+"', N'"+sPassword+"'";

        try {
            objConn = CreateConnection();
            if (objConn != null) {
                st = objConn.createStatement();

                rs = st.executeQuery(sSQL);
                if (rs != null) {
                    try {
                        while (rs.next()) {
                            function.add(rs.getString("FunctionCode"));
                        }
                    } catch (SQLException sql) {
                        throw new SQLException(sql);
                    }
                } else {
                    throw new SQLException("Can not load data");
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.toString());
        } finally {
            if (st != null)
                st.close();
            if (rs != null)
                rs.close();
            if(objConn != null)
                objConn.close();
        }
        return function.toArray();
    }
}

