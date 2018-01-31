/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nobitawin
 */
public class MyTableModelUser extends AbstractTableModel {

    public MyTableModelUser(String tableName, String search, String sConn) throws SQLException {
        super();
        dao = new DataAccessTableUser(tableName, sConn);
        this.results = dao.getResultSerForDatabaseMetaData(tableName);
        getTableContents(tableName, search);
    }

    protected void getTableContents(String tableName, String search)
            throws SQLException {

        ArrayList colNamesList = new ArrayList();
        ArrayList colClassesList = new ArrayList();

        int flag = 0;
        while (results.next()) {
            if (flag == 0) {
                colNamesList.add(results.getString("COLUMN_NAME"));
                int dbType = results.getInt("DATA_TYPE");
                flag = 1;

                switch (dbType) {
                    case Types.INTEGER:
                    case Types.NUMERIC:
                        colClassesList.add(String.class);
                        break;
                    case Types.FLOAT:
                        colClassesList.add(Float.class);
                        break;
                    case Types.DOUBLE:
                    case Types.REAL:
                    case Types.DECIMAL:
                        colClassesList.add(Double.class);
                        break;
                    case Types.DATE:
                    case Types.TIME:
                    case Types.TIMESTAMP:
                        colClassesList.add(java.sql.Date.class);
                        break;
                    default:
                        colClassesList.add(String.class);
                        break;
                }
            }

            colNamesList.add(results.getString("COLUMN_NAME"));
            int dbType = results.getInt("DATA_TYPE");

            switch (dbType) {


                case Types.BIT:
                    colClassesList.add(Boolean.class);
                    break;
                case Types.INTEGER:
                case Types.NUMERIC:
                    colClassesList.add(String.class);
                    break;
                case Types.FLOAT:
                    colClassesList.add(Float.class);
                    break;
                case Types.DOUBLE:
                case Types.REAL:
                case Types.DECIMAL:
                    colClassesList.add(Double.class);
                    break;
                case Types.DATE:
                case Types.TIME:
                case Types.TIMESTAMP:
                    colClassesList.add(java.sql.Date.class);
                    break;
                default:
                    colClassesList.add(String.class);
                    break;
            }
        }

        columnNamesTemp = new String[colNamesList.size()];
        colNamesList.toArray(columnNamesTemp);

        columnClasses = new Class[colClassesList.size()];
        colClassesList.toArray(columnClasses);

        if (search.equalsIgnoreCase("")) {
            this.results = dao.getResultSetDetail(tableName);
        } else {
            this.results = dao.getResultSetSearch(tableName, search);
        }

        ArrayList rowList = new ArrayList();

        while (results.next()) {
            ArrayList cellList = new ArrayList();

            for (int i = 0; i < columnClasses.length; i++) {
                Object cellValue = null;
                if (i != 4) {
                    if (columnClasses[i] == String.class) {
                        cellValue = results.getString(i + 1);
                    } else if (columnClasses[i] == Integer.class) {
                        cellValue = new Integer(
                                results.getInt(columnNamesTemp[i]));
                    } else if (columnClasses[i] == Float.class) {
                        cellValue = new Float(
                                results.getInt(columnNamesTemp[i]));
                    } else if (columnClasses[i] == Double.class) {
                        cellValue = new Double(
                                results.getDouble(columnNamesTemp[i]));
                    } else if (columnClasses[i] == java.sql.Date.class) {
                        cellValue = results.getDate(columnNamesTemp[i]);
                    } else if (columnClasses[i] == Boolean.class) {
                        cellValue = results.getBoolean(columnNamesTemp[i]);
                    } else {
                        System.out.println("Can't assign " + columnNamesTemp[i]);
                    }
                } else {
                    cellValue = "*********";
                }
                cellList.add(cellValue);
            }

            Object[] cells = cellList.toArray();
            rowList.add(cells);
        }

        contents = new Object[rowList.size()][];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = (Object[]) rowList.get(i);
        }
    }

    public int getRowCount() {
        return contents.length;
    }

    public int getColumnCount() {
//        if (contents.length == 0) {
//            return 0;
//        } else {
//            return contents[0].length;
//        }
        return columnNames.length;
    }

    public Object getValueAt(int row, int column) {
        return contents[row][column];
    }

    @Override
    public Class getColumnClass(int col) {
        //   return columnClasses[col];
        return getValueAt(0, col).getClass();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    DataAccessTableUser dao = null;
    ResultSet results;
    String[] columnNamesTemp;
    String[] columnNames = {"No.", "User Code", "Name", "Account", "Password", "FullControl"};
    Class[] columnClasses;
    Object[][] contents;
}
