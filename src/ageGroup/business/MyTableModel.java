package ageGroup.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import ageGroup.dataObject.AgeGroupDAO;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DThanh
 */
public class MyTableModel extends AbstractTableModel {

    /**
     * Builds an immutable table model from getting metadata about a table
     * in the jdbc database.
     * @param tableName the database table name
     */
    public MyTableModel(String connect, String tableName) throws SQLException {
        super();
        dao = new AgeGroupDAO(connect);
        getTableContents(tableName);
    }

    /**
     * This method is created to retrieve the column metadata for the table
     * and for each row, it gets each column's value.
     * @param tableName the table name
     * @throws java.sql.SQLException type of exception it may throw
     */
    public void getTableContents(String tableName)
            throws SQLException {

        this.results = dao.getResultSetForDatabaseMetaData(tableName);

        // Declare two objects of ArrayList class to store the column name
        // and column data type
        ArrayList colNamesList = new ArrayList();
        ArrayList colClassesList = new ArrayList();

        while (results.next()) {
            colNamesList.add(results.getString("COLUMN_NAME"));
            int dbType = results.getInt("DATA_TYPE");

            // Check for the data type and add the data type to ArrayList
            // object colClassesList
            switch (dbType) {
                case Types.INTEGER:
                    colClassesList.add(Integer.class);
                    break;
                case Types.FLOAT:
                    colClassesList.add(Float.class);
                    break;
                case Types.DOUBLE:
                case Types.DECIMAL:
                case Types.REAL:
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

        // Declare a String array having the same size as the ArrayList and
        // store the data in the String Array
        columnNames_temp = new String[colNamesList.size()];
        colNamesList.toArray(columnNames_temp);

        // Declare a Class array having the same size as the ArrayList and
        // store the data in the Class Array
        columnClasses = new Class[colClassesList.size()];
        colClassesList.toArray(columnClasses);

        // Retrieve all data from the table and put into the contents array
        this.results = dao.getResultSetDetail(tableName);
        ArrayList rowList = new ArrayList();


        while (results.next()) {
            ArrayList cellList = new ArrayList();

            for (int i = 0; i < columnClasses.length; i++) {
                Object cellValue = null;

                // Check the data type with each element of ArrayList class and
                // if a match is found it stores it to the Object class object
                if (columnClasses[i] == String.class) {
                    cellValue = results.getString(columnNames_temp[i]);
                } else if (columnClasses[i] == Integer.class) {
                    cellValue = new Integer(
                            results.getInt(columnNames_temp[i]));
                } else if (columnClasses[i] == Float.class) {
                    cellValue = new Float(
                            results.getInt(columnNames_temp[i]));
                } else if (columnClasses[i] == Double.class) {
                    cellValue = new Double(
                            results.getDouble(columnNames_temp[i]));
                } else if (columnClasses[i] == java.sql.Date.class) {
                    cellValue = results.getDate(columnNames_temp[i]);
                } else {
                    System.out.println("Can't assign " + columnNames_temp[i]);
                }
                cellList.add(cellValue);
            }

            Object[] cells = cellList.toArray();
            rowList.add(cells);
        }

        // Create a two-dimensional array contents
        contents = new Object[rowList.size()][];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = (Object[]) rowList.get(i);
        }

        dao.closeConnection();

    }

    /**
     * This method is invoked to return the number of rows.
     * @return int Returns the integer values for the number of rows
     */
    public int getRowCount() {
        return contents.length;
    }

    /**
     * This method is invoked to return the number of columns.
     * @return int Returns the integer values for the number of columns
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * This method is invoked to override for which AbstractTableModel has
     * trivial implementations.
     * @param column Stores the number of columns present
     * @param row Stores the number of rows present
     * @return Object Returns an object of Object class
     */
    public Object getValueAt(int row, int column) {
        return contents[row][column];
    }

    /**
     * This method is invoked to return the data type of each column.
     * @param col Stores the number of columns present
     * @return Class Returns the data type of the column number
     */
    @Override
    public Class getColumnClass(int col) {
        return columnClasses[col];
    }

    /**
     * This method is invoked to return the column name.
     * @param col Stores the name of the columns present
     * @return String Returns the String values for the column name
     */
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    /**
     * ResultSet object to hold a reference to the ResultSet object.
     */
    ResultSet results;
    /**
     * Variable to store an array of column names of the table.
     */
    String[] columnNames = {"Group age code:", "Child age group:", "Charges as per the group:"};
    /**
     * AgeGroupDAO variable to store the connect to database
     */
    AgeGroupDAO dao = null;
    /**
     * Variable to store two-dimensional Object array named contents.
     */
    Object[][] contents;
    /**
     * Variable to store an array of column names and store metadata for
     * column name of the table.
     */
    String[] columnNames_temp;
    /**
     * Variable to build up arrays and store the metadata for the column types
     * of the table.
     */
    Class[] columnClasses;
}
