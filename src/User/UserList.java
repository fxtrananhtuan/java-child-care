/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJInternalFrame.java
 *
 * Created on Jun 28, 2009, 10:04:35 PM
 */
package User;

import MainProgram.MainForm;
import UserAndFunctions.DAO_UserFunction;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 *
 * @author Nobitawin
 */
public class UserList extends javax.swing.JInternalFrame {

    private TableModel model;
    private static MainForm main = null;
    private String sConn = "";
    private String[] sFunctionCode_AccountName;     //get permistion
    private Object[] oPermistionList;               //Array with 3 element: add, delete, edit
    int numcol = -1;

    public UserList(MainForm aThis, String sConn, String[] _sFunctionCode_AccountName) {
        this.sConn = sConn;
        main = (MainForm) aThis;
        sFunctionCode_AccountName = _sFunctionCode_AccountName;

        try {
            model = new MyTableModelUser("tbl_User", "", sConn);
            DAO_UserFunction daouser = new DAO_UserFunction(sConn);
            oPermistionList = daouser.getPermistionList(_sFunctionCode_AccountName[1].toString(), Integer.parseInt(_sFunctionCode_AccountName[0].toString()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        String[] password = new String[model.getRowCount()];
        for (int i = 0; i < model.getRowCount(); i++) {
            password[i] = model.getValueAt(i, 4).toString();

        }

        initComponents();

        ImageIcon icon = new ImageIcon(getClass().getResource("/images/logo.gif"));
        this.setFrameIcon(icon);
        ActiveButton();
    }

    private void ActiveButton() {
        if (Integer.parseInt(sFunctionCode_AccountName[2]) == 0) {
            // Add permistion
            if (Integer.parseInt(oPermistionList[0].toString()) == 1) {
                btnAdd.setEnabled(true);
            } else {
                btnAdd.setEnabled(false);
            }

            //Edit permistion
            if (Integer.parseInt(oPermistionList[1].toString()) == 1) {
                if (numcol != -1) {
                    btnEdit.setEnabled(true);
                } else {
                    btnEdit.setEnabled(false);
                }
            } else {
                btnEdit.setEnabled(false);
            }

            // Delete permistion
            if (Integer.parseInt(oPermistionList[2].toString()) == 1) {
                if (numcol != -1) {
                    btnDelete.setEnabled(true);
                } else {
                    btnDelete.setEnabled(false);
                }
            } else {
                btnDelete.setEnabled(false);
            }
        }
    }

    private void searchEvent(String tableName) {
        String search = txtSearch.getText().trim();
        try {
            model = (TableModel) new MyTableModelUser(tableName, search, sConn);
        } catch (SQLException ex) {
            Logger.getLogger(UserList.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblUser.setModel(model);

        try {
            if (new BusinessUser(tableName, sConn).searchAccount(tableName, search).next()) {
                lblSearch.setText("");
            } else {
                lblSearch.setText("Not Found Account");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserList.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void deleteEvent(String tableName) {
        numcol = tblUser.getSelectedRow();
        if (numcol != -1) {
            try {
                String userCode = model.getValueAt(numcol, 1).toString();

                ImageIcon icon = new ImageIcon(getClass().getResource("/images/quest.png"));
                int choice = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete User", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
                if (choice == JOptionPane.YES_OPTION) {
                    new BusinessUser(tableName, sConn).deleteAccount(tableName, userCode);
                }


            } catch (SQLException ex) {
                Logger.getLogger(UserList.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                model = (TableModel) new MyTableModelUser("tbl_User", "", sConn);
            } catch (SQLException ex) {
                Logger.getLogger(UserList.class.getName()).log(Level.SEVERE, null, ex);
            }

            tblUser.setModel(model);
        } else {
            JOptionPane.showMessageDialog(this, "You have must be select the a row in table list below.");
        }
    }

    private void showAdd() {
        AddUser dialog = new AddUser(new javax.swing.JFrame(), true, sConn);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
            }
        });
        dialog.setVisible(true);
    }
//

    private void showEdit() {
        int isFullControl = 0;
        numcol = tblUser.getSelectedRow();

        if (numcol != -1) {

            if (model.getValueAt(numcol, 5).toString().equalsIgnoreCase("true")) {
                isFullControl = 1;
            } else {
                isFullControl = 0;
            }
            editUser dialog = new editUser(new javax.swing.JFrame(), true, model.getValueAt(numcol, 2).toString(), model.getValueAt(numcol, 3).toString(), isFullControl, model.getValueAt(numcol, 1).toString(), sConn);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                }
            });
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "You have must be select the a row in table list below.");
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        lblSearch = new javax.swing.JLabel();

        setTitle("User");
        setPreferredSize(new java.awt.Dimension(800, 600));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setForeground(java.awt.SystemColor.activeCaption);
        jLabel1.setText("Account :");

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        tblUser.setModel(model
        );
        tblUser.setDoubleBuffered(true);
        tblUser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblUser.getTableHeader().setResizingAllowed(false);
        tblUser.getTableHeader().setReorderingAllowed(false);
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblUserMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblUser);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/del.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close2.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        lblSearch.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblSearch.setForeground(new java.awt.Color(255, 0, 0));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(21, 21, 21)
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(txtSearch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 189, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(btnSearch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, lblSearch)))
                    .add(layout.createSequentialGroup()
                        .add(187, 187, 187)
                        .add(btnAdd)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnEdit)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnDelete)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnClose)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(31, 31, 31)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(txtSearch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnSearch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(19, 19, 19)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 281, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(lblSearch)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnClose)
                    .add(btnDelete)
                    .add(btnEdit)
                    .add(btnAdd))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-665)/2, (screenSize.height-451)/2, 665, 451);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        searchEvent("tbl_User");
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteEvent("tbl_User");
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        showAdd();
        try {
            model = (TableModel) new MyTableModelUser("tbl_User", "", sConn);
        } catch (SQLException ex) {
            Logger.getLogger(UserList.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblUser.setModel(model);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        showEdit();
        try {
            model = (TableModel) new MyTableModelUser("tbl_User", "", sConn);
        } catch (SQLException ex) {
            Logger.getLogger(UserList.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblUser.setModel(model);

    }//GEN-LAST:event_btnEditActionPerformed

    private void tblUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMousePressed
        // TODO add your handling code here:
        numcol = tblUser.getSelectedRow();
        ActiveButton();
    }//GEN-LAST:event_tblUserMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTable tblUser;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
