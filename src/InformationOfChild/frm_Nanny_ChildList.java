/*
 * frm_Nanny_ChildList.java
 *
 * Created on June 24, 2009, 10:56 PM
 */

package InformationOfChild;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author  Tuong Huy
 */
public class frm_Nanny_ChildList extends javax.swing.JInternalFrame {
    int selectedRowChild=-1;
    static String sConn="";
    /** Creates new form frm_Nanny_ChildList */
    public frm_Nanny_ChildList(String _sConn) {
        initComponents();
        sConn = _sConn;
        String sSQL="Select ROW_NUMBER() OVER (ORDER BY Name Asc) AS No, NannyCode, Name, YearOfBirth, ChildAssignedToHer From tbl_Nanny Order By Name Asc";

        //Load data into tblNannyList
        LoadDataIntoJTable(sSQL, sConn);

        //Set default table model for tablChildList
        tblChildList.setModel(new javax.swing.table.DefaultTableModel(new Object [][] {}, new String [] {"ChildCode", "FirstName","LastName", "MiddleName", "DateBirth"}));
    }
    
    private void LoadData(String sSQL){
        LoadDataIntoJTableChildList(sSQL, sConn);
    }
    
    public void LoadDataIntoJTable(String sSQL, String sConn){
        try{
            tableModelChild tblModel = new tableModelChild(sConn);
            tblModel.setQuery(sSQL);
            tbl_NannyList.setModel(tblModel);
            
            ListSelectionModel cellSelectionModel = tbl_NannyList.getSelectionModel();
            cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
                
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) return;
                    
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    if (lsm.isSelectionEmpty()) {
                        //no row selected
                    } else {
                        int selectedRow = lsm.getMinSelectionIndex();
                        String nannyCode = tbl_NannyList.getModel().getValueAt(selectedRow, 1).toString();
                        
                        String sSQL="Select ROW_NUMBER() OVER (ORDER BY LastName Asc) AS No, ChildCode, FirstName, LastName, MiddleName, Convert(varchar(10), DateOfBirth, 103) as DateBirth ";
                        sSQL+="From tbl_Child Where ChildCode in (Select ChildCode From tbl_Child_Nanny Where NannyCode='";
                        sSQL+=Integer.parseInt(nannyCode)+"') Order By LastName Asc";
                         LoadData(sSQL);
                    }
                    
                }
                
            });
            
            //----- Mouse event
            tblChildList.addMouseListener( new MouseAdapter() {
                @Override
                public void mouseClicked( MouseEvent e ) {
                    // Left mouse click
                    if ( SwingUtilities.isLeftMouseButton( e ) ) {
                        // Do something
                    }
                    // Right mouse click
                    else if ( SwingUtilities.isRightMouseButton( e )) {
                        // get the coordinates of the mouse click
                        Point p = e.getPoint();
                        
                        // get the row index that contains that coordinate
                        int rowNumber = tblChildList.rowAtPoint( p );
                        
                        // Get the ListSelectionModel of the JTable
                        ListSelectionModel model = tblChildList.getSelectionModel();
                        
                        // set the selected interval of rows. Using the "rowNumber"
                        // variable for the beginning and end selects only that one row.
                        model.setSelectionInterval( rowNumber, rowNumber );
                    }
                }
            });
            
            // ------------------------------
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void LoadDataIntoJTableChildList(String sSQL, String sConn){
        try{
            tableModelChild tblModel = new tableModelChild(sConn);
            tblModel.setQuery(sSQL);
            tblChildList.setModel(tblModel);
            
            ListSelectionModel cellSelectionModel = tblChildList.getSelectionModel();
            cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
                
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) return;
                    
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    if (lsm.isSelectionEmpty()) {
                        //no row selected
                    } else {
                        selectedRowChild = lsm.getMinSelectionIndex();
                    }
                    
                }
                
            });
            
            //----- Mouse event
            tblChildList.addMouseListener( new MouseAdapter() {
                @Override
                public void mouseClicked( MouseEvent e ) {
                    // Left mouse click
                    if ( SwingUtilities.isLeftMouseButton( e ) ) {
                        // Do something
                    }
                    // Right mouse click
                    else if ( SwingUtilities.isRightMouseButton( e )) {
                        // get the coordinates of the mouse click
                        Point p = e.getPoint();
                        
                        // get the row index that contains that coordinate
                        int rowNumber = tblChildList.rowAtPoint( p );
                        
                        // Get the ListSelectionModel of the JTable
                        ListSelectionModel model = tblChildList.getSelectionModel();
                        
                        // set the selected interval of rows. Using the "rowNumber"
                        // variable for the beginning and end selects only that one row.
                        model.setSelectionInterval( rowNumber, rowNumber );
                    }
                }
            });
            
            // ------------------------------
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlNannyList = new javax.swing.JScrollPane();
        tbl_NannyList = new javax.swing.JTable();
        pnlChildList = new javax.swing.JScrollPane();
        tblChildList = new javax.swing.JTable();
        lblNannyList = new javax.swing.JLabel();
        lblChildList = new javax.swing.JLabel();

        setTitle("List child by nanny");
        tbl_NannyList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        pnlNannyList.setViewportView(tbl_NannyList);

        tblChildList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        pnlChildList.setViewportView(tblChildList);

        lblNannyList.setFont(new java.awt.Font("Tahoma", 1, 16));
        lblNannyList.setText("Nanny list");

        lblChildList.setFont(new java.awt.Font("Tahoma", 1, 16));
        lblChildList.setText("Child list by nanny");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(pnlNannyList, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                        .add(33, 33, 33))
                    .add(layout.createSequentialGroup()
                        .add(lblNannyList)
                        .add(351, 351, 351)))
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblChildList)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlChildList, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(26, 26, 26)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblNannyList)
                    .add(lblChildList))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, pnlChildList, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, pnlNannyList, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblChildList;
    private javax.swing.JLabel lblNannyList;
    private javax.swing.JScrollPane pnlChildList;
    private javax.swing.JScrollPane pnlNannyList;
    private javax.swing.JTable tblChildList;
    private javax.swing.JTable tbl_NannyList;
    // End of variables declaration//GEN-END:variables
    
}
