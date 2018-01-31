/*
 * tableModelChild.java
 *
 * Created on June 7, 2009, 5:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package InformationOfChild;

import java.util.*;
import javax.swing.table.AbstractTableModel;
import java.sql.*;
/**
 *
 * @author Tuong Huy
 */
public class tableModelChild extends AbstractTableModel{
    Vector cache;           // will hold String[] objects . . .
    int colCount=0;
    String[] headers;
    String conn = "";
    DAO_Child daochild = null;
    
    /** Creates a new instance of tableModelChild */
    public tableModelChild(String _conn) {
        conn=_conn;
    }
    /** Creates a new instance of CustomModel */
    public String getColumnName(int i) {
        return headers[i];
    }
    
    public int getColumnCount() {
        return colCount;
    }
    
    public int getRowCount() {
        return cache.size();
    }
    
    public Object getValueAt(int row, int col) {
        return ((String[]) cache.elementAt(row))[col];
    }
    
    public void setQuery(String sSQL) {
        
        cache = new Vector();        
        Statement st=null;
        ResultSet rs=null;
        Connection objConn=null;
        daochild = new DAO_Child(conn);
        try {
            // Execute the query and store the result set and its metadata
            objConn = daochild.getConnection();
            st = objConn.createStatement();
            rs = st.executeQuery(sSQL);
            ResultSetMetaData meta = rs.getMetaData();
            colCount = meta.getColumnCount();
            
            // Now we must rebuild the headers array with the new column names
            headers = new String[colCount];
            for (int h = 1; h <= colCount; h++) {
                headers[h - 1] = meta.getColumnName(h);
            }
            
            // and file the cache with the records from our query. This would
            // not be
            // practical if we were expecting a few million records in response
            // to our
            // query, but we aren't, so we can do this.
            while (rs.next()) {
                String[] record = new String[colCount];
                for (int i = 0; i < colCount; i++) {
                    record[i] = rs.getString(i + 1);
                }
                cache.addElement(record);                          
            }
            fireTableChanged(null); // notify everyone that we have a new table.
        } catch (Exception e) {
            cache = new Vector(); // blank it out and keep going.
            e.printStackTrace();
        } finally{
            try{
                if (st != null) {
                    st.close();
                }
                if (objConn != null) {
                    objConn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            
        }
    }
}
