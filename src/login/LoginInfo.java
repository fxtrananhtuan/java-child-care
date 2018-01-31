/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

/**
 *
 * @author admin
 */
public class LoginInfo {

    private String serverName;
    private String userName;
    private String password;
    private String databasename;

    public LoginInfo(String sServerName, String sUserName, String sPassword, String sDatabaseName) {
        serverName = sServerName;
        userName = sUserName;
        password = sPassword;
        databasename = sDatabaseName;
    }

    public LoginInfo(){
        serverName="";
        userName="";
        password="";
        databasename="";
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDatabaseName(){
        return this.databasename;
    }

    public void setDatabaseName(String sDatabaseName){
        this.databasename = sDatabaseName;
    }
}

