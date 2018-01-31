/*
 * frm_UserFunction.java
 *
 * Created on June 22, 2009, 8:03 PM
 */
package UserAndFunctions;

import MainProgram.MainForm;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author  Tuong Huy
 */
public class frm_UserFunction extends javax.swing.JInternalFrame {

    private static String sConn = "";
    private int selectedRow1 = -1;
    private int selectedRow2 = -1;
    private String AccountName = "";
    private DAO_UserFunction daouserfunction = null;
    private static MainForm main = null;
    private String[] sFunctionCode_AccountName;     //get permistion
    private Object[] oPermistionList;               //Array with 3 element: add, delete, edit
    tableModelUserFunction tblModel2 = null;
    tableModelUserFunction tblModel1 = null;

    /** Creates new form frm_UserFunction */
    public frm_UserFunction(java.awt.Frame paren, String _sConn, String[] _sFunctionCode_AccountName) {
        initComponents();
        sFunctionCode_AccountName = _sFunctionCode_AccountName;
        daouserfunction = new DAO_UserFunction(_sConn);
        sConn = _sConn;
        main = (MainForm) paren;
        LoadComboboxAccount();

        tblFunctionNotOfUser.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{}, new String[]{"FunctionCode", "FunctionName"}));
        tblFunctionOfuser.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{}, new String[]{"FunctionCode", "FunctionName"}));

        try {
            oPermistionList = daouserfunction.getPermistionList(_sFunctionCode_AccountName[1].toString(), Integer.parseInt(_sFunctionCode_AccountName[0].toString()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ActiveButton();
    }

    private void ActiveButton() {
        if (Integer.parseInt(sFunctionCode_AccountName[2]) == 0) {
            // Add permistion
            if (Integer.parseInt(oPermistionList[0].toString()) == 1) {
                if (selectedRow2 != -1) {
                    btnAddFunctionForUser.setEnabled(true);
                } else {
                    btnAddFunctionForUser.setEnabled(false);
                }
            } else {
                btnAddFunctionForUser.setEnabled(false);
            }           

            //Edit permistion
            if (Integer.parseInt(oPermistionList[1].toString()) == 1) {
                if (selectedRow1 != -1) {
                    btnChangePermision.setEnabled(true);
                } else {
                    btnChangePermision.setEnabled(false);
                }
            } else {
                btnChangePermision.setEnabled(false);
            }

            // Delete permistion
            if (Integer.parseInt(oPermistionList[2].toString()) == 1) {
                if (selectedRow1 != -1) {
                    btnRemoveFunctionOfUser.setEnabled(true);
                } else {
                    btnRemoveFunctionOfUser.setEnabled(false);
                }
            } else {
                btnRemoveFunctionOfUser.setEnabled(false);
            }
        } else {
            if (selectedRow1 != -1) {
                btnChangePermision.setEnabled(true);
                btnRemoveFunctionOfUser.setEnabled(true);
            } else {
                btnChangePermision.setEnabled(false);
                btnRemoveFunctionOfUser.setEnabled(false);
            }
            if (selectedRow2 != -1) {
                btnAddFunctionForUser.setEnabled(true);
            } else {
                btnAddFunctionForUser.setEnabled(false);
            }
        }
    }

    public void InsertRows(int[] iPermission) {
        String FunctionCode = tblFunctionNotOfUser.getModel().getValueAt(selectedRow2, 0).toString();
        String FunctionName = tblFunctionNotOfUser.getModel().getValueAt(selectedRow2, 1).toString();
        try {
            int iResult = daouserfunction.AddDeleteFunctionForUser(AccountName, Integer.parseInt(FunctionCode), 0, iPermission);
            if (iResult == 1) {
                tblModel1.InsertRow(FunctionCode, FunctionName);
                tblModel2.RemoveRow(selectedRow2);

                selectedRow1 = -1;
                selectedRow2 = -1;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void RemoveRows() {
        String FunctionCode = tblFunctionOfuser.getModel().getValueAt(selectedRow1, 0).toString();
        String FunctionName = tblFunctionOfuser.getModel().getValueAt(selectedRow1, 1).toString();
        int[] iPermission = new int[3];
        try {
            int iResult = daouserfunction.AddDeleteFunctionForUser(AccountName, Integer.parseInt(FunctionCode), 1, iPermission);
            if (iResult == 1) {
                tblModel2.InsertRow(FunctionCode, FunctionName);
                tblModel1.RemoveRow(selectedRow1);
                selectedRow1 = -1;
                selectedRow2 = -1;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int UpdatePermision(int[] iPermission) {
        int iFunctionCode = Integer.parseInt(tblFunctionOfuser.getModel().getValueAt(selectedRow1, 0).toString());
        int iResult = 0;
        try {
            iResult = daouserfunction.AddDeleteFunctionForUser(AccountName, iFunctionCode, 2, iPermission);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return iResult;
    }

    private void LoadComboboxAccount() {
        cbAccountList.removeAllItems();
        try {
            DAO_UserFunction userfunction = new DAO_UserFunction(sConn);
            Object[] sItemList = userfunction.getDataForComboboxAccount();
            int i = 0;
            if (sItemList != null) {
                cbAccountList.addItem("------- Select the account name --------");
                while (i < sItemList.length) {
                    cbAccountList.addItem(sItemList[i]);
                    i++;
                }
            }
            cbAccountList.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    JComboBox cb = (JComboBox) e.getSource();
                    AccountName = (String) cb.getSelectedItem();
                    if (!AccountName.equals("------- Select the account name --------")) {
                        String SQL = "";
                        SQL = "Select FunctionCode, FunctionName From tbl_Functions ";
                        SQL += "Where FunctionCode in (Select FunctionCode From tbl_User_Functions Where ";
                        SQL += "UserCode = (Select UserCode From tbl_User Where Account=N'" + AccountName + "'))";

                        LoadDataIntoJTable1(SQL, tblFunctionOfuser);

                        SQL = "Select FunctionCode, FunctionName From tbl_Functions ";
                        SQL += "Where FunctionCode not in (Select FunctionCode From tbl_User_Functions Where ";
                        SQL += "UserCode = (Select UserCode From tbl_User Where Account=N'" + AccountName + "'))";
                        LoadDataIntoJTable2(SQL, tblFunctionNotOfUser);

                        selectedRow1 = -1;
                        selectedRow2 = -1;
                        ActiveButton();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void LoadDataIntoJTable1(String sSQL, JTable tbl) {
        try {
            tblModel1 = new tableModelUserFunction(sConn);
            tblModel1.setQuery(sSQL);
            tbl.setModel(tblModel1);

            ListSelectionModel cellSelectionModel = tbl.getSelectionModel();
            cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            cellSelectionModel.addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) {
                        return;
                    }

                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    if (lsm.isSelectionEmpty()) {
                        //no row selected
                    } else {
                        selectedRow1 = lsm.getMinSelectionIndex();
                        ActiveButton();
                    }

                }
            });

            //----- Mouse event
            tbl.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    // Left mouse click
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        // Do something
                    } // Right mouse click
                    else if (SwingUtilities.isRightMouseButton(e)) {
                        // get the coordinates of the mouse click
                        Point p = e.getPoint();

                    // get the row index that contains that coordinate
                    //int rowNumber = tbl.rowAtPoint( p );

                    // Get the ListSelectionModel of the JTable
                    //ListSelectionModel model = tbl.getSelectionModel();

                    // set the selected interval of rows. Using the "rowNumber"
                    // variable for the beginning and end selects only that one row.
                    //model.setSelectionInterval( rowNumber, rowNumber );
                    }
                }
            });

        // ------------------------------

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void LoadDataIntoJTable2(String sSQL, JTable tbl) {
        try {
            tblModel2 = new tableModelUserFunction(sConn);
            tblModel2.setQuery(sSQL);
            tbl.setModel(tblModel2);

            ListSelectionModel cellSelectionModel = tbl.getSelectionModel();
            cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            cellSelectionModel.addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) {
                        return;
                    }

                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    if (lsm.isSelectionEmpty()) {
                        //no row selected
                    } else {
                        selectedRow2 = lsm.getMinSelectionIndex();
                        ActiveButton();
                    }

                }
            });

            //----- Mouse event
            tbl.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    // Left mouse click
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        // Do something
                    } // Right mouse click
                    else if (SwingUtilities.isRightMouseButton(e)) {
                        // get the coordinates of the mouse click
                        Point p = e.getPoint();

                    // get the row index that contains that coordinate
                    //int rowNumber = tbl.rowAtPoint( p );

                    // Get the ListSelectionModel of the JTable
                    //ListSelectionModel model = tbl.getSelectionModel();

                    // set the selected interval of rows. Using the "rowNumber"
                    // variable for the beginning and end selects only that one row.
                    //model.setSelectionInterval( rowNumber, rowNumber );
                    }
                }
            });

        // ------------------------------

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cbAccountList = new javax.swing.JComboBox();
        pnlOfUser = new javax.swing.JScrollPane();
        tblFunctionOfuser = new javax.swing.JTable();
        pnlNotOfUser = new javax.swing.JScrollPane();
        tblFunctionNotOfUser = new javax.swing.JTable();
        btnAddFunctionForUser = new javax.swing.JButton();
        btnRemoveFunctionOfUser = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnChangePermision = new javax.swing.JButton();

        setTitle("Add/Remove manager function for user");

        jLabel1.setText("Select account name:");

        cbAccountList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblFunctionOfuser.setModel(new javax.swing.table.DefaultTableModel(
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
        pnlOfUser.setViewportView(tblFunctionOfuser);

        tblFunctionNotOfUser.setModel(new javax.swing.table.DefaultTableModel(
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
        pnlNotOfUser.setViewportView(tblFunctionNotOfUser);

        btnAddFunctionForUser.setText("<< Add");
        btnAddFunctionForUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFunctionForUserActionPerformed(evt);
            }
        });

        btnRemoveFunctionOfUser.setText("Remove >>");
        btnRemoveFunctionOfUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveFunctionOfUserActionPerformed(evt);
            }
        });

        jLabel2.setText("Function of account");

        jLabel3.setText("Function not of account");

        btnChangePermision.setText("Change permision");
        btnChangePermision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePermisionActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cbAccountList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 218, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(layout.createSequentialGroup()
                                .add(jLabel2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(btnChangePermision))
                            .add(pnlOfUser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 408, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, btnAddFunctionForUser, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(btnRemoveFunctionOfUser, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel3)
                            .add(pnlNotOfUser, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(cbAccountList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(19, 19, 19)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(46, 46, 46)
                        .add(btnAddFunctionForUser)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnRemoveFunctionOfUser))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2)
                            .add(jLabel3)
                            .add(btnChangePermision))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(pnlNotOfUser, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                            .add(pnlOfUser, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRemoveFunctionOfUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveFunctionOfUserActionPerformed
        // TODO add your handling code here:
        if (selectedRow1 == -1) {
            JOptionPane.showMessageDialog(this, "You have must be select the a row in left table list.");
            return;
        }
        RemoveRows();
    }//GEN-LAST:event_btnRemoveFunctionOfUserActionPerformed

    private void btnAddFunctionForUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFunctionForUserActionPerformed
        // TODO add your handling code here:
        if (selectedRow2 == -1) {
            JOptionPane.showMessageDialog(this, "You have must be select the a row in right table list.");
            return;
        }
        new dialogPermistion(main, true, this, 0, new Object[3]).setVisible(true);
    }//GEN-LAST:event_btnAddFunctionForUserActionPerformed

    private void btnChangePermisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePermisionActionPerformed
        // TODO add your handling code here:
        if (selectedRow1 == -1) {
            JOptionPane.showMessageDialog(this, "You have must be select the a row in left table list.");
            return;
        }
        try {
            DAO_UserFunction daouser = new DAO_UserFunction(sConn);
            int iFunctionCode = Integer.parseInt(tblFunctionOfuser.getModel().getValueAt(selectedRow1, 0).toString());
            Object[] iPermisionList = daouser.getPermistionList(AccountName, iFunctionCode);
            new dialogPermistion(main, true, this, 1, iPermisionList).setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_btnChangePermisionActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFunctionForUser;
    private javax.swing.JButton btnChangePermision;
    private javax.swing.JButton btnRemoveFunctionOfUser;
    private javax.swing.JComboBox cbAccountList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane pnlNotOfUser;
    private javax.swing.JScrollPane pnlOfUser;
    private javax.swing.JTable tblFunctionNotOfUser;
    private javax.swing.JTable tblFunctionOfuser;
    // End of variables declaration//GEN-END:variables
}
