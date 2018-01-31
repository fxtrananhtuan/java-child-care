/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJInternalFrame.java
 *
 * Created on Jun 28, 2009, 10:04:35 PM
 */
package Activity;

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
public class ActivityList extends javax.swing.JInternalFrame {

    private TableModel model;
    private static MainForm main = null;
    private String sConn = "";
    private String[] sFunctionCode_AccountName;     //get permistion
    private Object[] oPermistionList;               //Array with 3 element: add, delete, edit
    int numcol = -1;

    public ActivityList(MainForm aThis, String sConn, String[] _sFunctionCode_AccountName) {
        this.sConn = sConn;
        main = (MainForm) aThis;
        sFunctionCode_AccountName = _sFunctionCode_AccountName;
        try {
            model = (TableModel) new MyTableModel("tbl_Activities", "", sConn);
            DAO_UserFunction daouser = new DAO_UserFunction(sConn);
            oPermistionList = daouser.getPermistionList(sFunctionCode_AccountName[1].toString(), Integer.parseInt(sFunctionCode_AccountName[0].toString()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        ImageIcon icon = new ImageIcon(getClass().getResource("/images/logo.gif"));
        this.setFrameIcon(icon);
        initComponents();
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
            model = (TableModel) new MyTableModel(tableName, search, sConn);
        } catch (SQLException ex) {
            Logger.getLogger(ActivityList.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblActivity.setModel(model);

        try {
            if (new Business(tableName, sConn).searchActivity(tableName, search).next()) {
                lblSearch.setText("");
            } else {
                lblSearch.setText("Not Found Activity");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActivityList.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void deleteEvent(String tableName) {
        numcol = tblActivity.getSelectedRow();
        if (numcol != -1) {
            try {
                String activeCode = model.getValueAt(numcol, 1).toString();

                ImageIcon icon = new ImageIcon(getClass().getResource("/images/quest.png"));
                int choice = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete Activity", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
                if (choice == JOptionPane.YES_OPTION) {
                    new Business(tableName, sConn).deleteActivity(tableName, activeCode);
                }


            } catch (SQLException ex) {
                Logger.getLogger(ActivityList.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                model = (TableModel) new MyTableModel("tbl_Activities", "", sConn);
            } catch (SQLException ex) {
                Logger.getLogger(ActivityList.class.getName()).log(Level.SEVERE, null, ex);
            }

            tblActivity.setModel(model);
        } else {
            JOptionPane.showMessageDialog(this, "You have must be select the a row in table list below.");
        }
    }

    private void showAdd() {
        addActivity dialog = new addActivity(main, true, sConn);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
            }
        });
        dialog.setVisible(true);
    }

    private void showEdit() {
        numcol = tblActivity.getSelectedRow();
        if (numcol != -1) {
            editActivity dialog = new editActivity(main, true, model.getValueAt(numcol, 2).toString(), model.getValueAt(numcol, 3).toString(), model.getValueAt(numcol, 1).toString(), sConn);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                }
            });
            dialog.setVisible(true);
        }else {
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
        tblActivity = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblSearch = new javax.swing.JLabel();

        setTitle("Activity");
        setPreferredSize(new java.awt.Dimension(800, 600));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setForeground(java.awt.SystemColor.activeCaption);
        jLabel1.setText("Name Active :");

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        tblActivity.setModel(model
        );
        tblActivity.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblActivity.getTableHeader().setResizingAllowed(false);
        tblActivity.getTableHeader().setReorderingAllowed(false);
        tblActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblActivityListMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblActivity);

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel2.setForeground(java.awt.SystemColor.activeCaption);
        jLabel2.setText("Rates ($) : An hour per child");

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

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/activity.png"))); // NOI18N

        lblSearch.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblSearch.setForeground(new java.awt.Color(255, 0, 0));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(21, 21, 21)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtSearch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 189, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(btnSearch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 160, Short.MAX_VALUE)
                .add(jLabel3))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 489, Short.MAX_VALUE)
                        .add(lblSearch)))
                .addContainerGap())
            .add(layout.createSequentialGroup()
                .add(187, 187, 187)
                .add(btnAdd)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnEdit)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnDelete)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnClose)
                .addContainerGap(130, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(layout.createSequentialGroup()
                        .add(31, 31, 31)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel1)
                            .add(txtSearch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(btnSearch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 281, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(lblSearch))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnClose)
                    .add(btnDelete)
                    .add(btnEdit)
                    .add(btnAdd))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-665)/2, (screenSize.height-451)/2, 665, 451);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        searchEvent("tbl_Activities");
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if (numcol != -1) {
            deleteEvent("tbl_Activities");
        } else {
            JOptionPane.showMessageDialog(this, "You have must be select the a row in table list above.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        showAdd();
        try {
            model = (TableModel) new MyTableModel("tbl_Activities", "", sConn);
        } catch (SQLException ex) {
            Logger.getLogger(ActivityList.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblActivity.setModel(model);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        if (numcol != -1) {
            showEdit();
            try {
                model = (TableModel) new MyTableModel("tbl_Activities", "", sConn);
            } catch (SQLException ex) {
                Logger.getLogger(ActivityList.class.getName()).log(Level.SEVERE, null, ex);
            }
            tblActivity.setModel(model);
        } else {
            JOptionPane.showMessageDialog(this, "You have must be select the a row in table list above.");
        }

    }//GEN-LAST:event_btnEditActionPerformed

    private void tblActivityListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblActivityListMousePressed
        // TODO add your handling code here:
        numcol = tblActivity.getSelectedRow();
        ActiveButton();
    }//GEN-LAST:event_tblActivityListMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTable tblActivity;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
