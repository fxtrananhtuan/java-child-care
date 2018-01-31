package Nanny;

//import Activity.NewJInternalFrame;
import MainProgram.MainForm;
import UserAndFunctions.DAO_UserFunction;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import nanny_ageGroup.SelectAgeGroup;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NannyList.java
 *
 * Created on Jun 29, 2009, 3:38:09 PM
 */
/**
 *
 * @author CristianoT
 */
public class NannyList extends javax.swing.JInternalFrame {

    /** Creates new form NannyList */
    private TableModel model;
    private static MainForm main = null;
    private String sConn = "";
    private String[] sFunctionCode_AccountName;     //get permistion
    private Object[] oPermistionList;               //Array with 3 element: add, delete, edit
    int numcol=-1;

    public NannyList(MainForm aThis, String sConn, String[] _sFunctionCode_AccountName) {
        this.sConn = sConn;
        main = (MainForm) aThis;
        sFunctionCode_AccountName=_sFunctionCode_AccountName;
        try {
            model = (TableModel) new MyTableModel("tbl_Nanny", "", sConn);
            DAO_UserFunction daouser = new DAO_UserFunction(sConn);
            oPermistionList = daouser.getPermistionList(_sFunctionCode_AccountName[1].toString(), Integer.parseInt(_sFunctionCode_AccountName[0].toString()));
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
                if(numcol!=-1){
                    btnEdit.setEnabled(true);
                    btnSelect.setEnabled(true);
                }
                else{
                    btnEdit.setEnabled(false);
                    btnSelect.setEnabled(false);
                }
            } else {
                btnEdit.setEnabled(false);
                btnSelect.setEnabled(false);
            }

            // Delete permistion
            if (Integer.parseInt(oPermistionList[2].toString()) == 1) {
                if(numcol!=-1){
                    btnDelete.setEnabled(true);
                }
                else{
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
            Logger.getLogger(NannyList.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblNanny.setModel(model);

        try {
            if (new Business(tableName, sConn).searchNanny(tableName, search).next()) {
                lblSearch.setText("");
            } else {
                lblSearch.setText("* Cannot Found Nanny !!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NannyList.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void deleteEvent(String tableName) {
        numcol = tblNanny.getSelectedRow();
        if (numcol != -1) {
            try {
                String NannyCode = model.getValueAt(numcol, 1).toString();

                ImageIcon icon = new ImageIcon(getClass().getResource("/images/quest.png"));
                int choice = JOptionPane.showConfirmDialog(this, "Do you want to delete this nanny?", "Delete This Nanny", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
                if (choice == JOptionPane.YES_OPTION) {
                    new Business(tableName, sConn).deleteNanny(tableName, NannyCode);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            try {
                model = (TableModel) new MyTableModel("tbl_Nanny", "", sConn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }            
            tblNanny.setModel(model);
            numcol=-1;
            ActiveButton();
        }
        else{
            JOptionPane.showMessageDialog(main, "You must select a row in nanny list above");
        }
    }

    private void showAdd() {
        AddDialog dialog = new AddDialog(main, true, sConn);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
            }
        });
        dialog.setVisible(true);
    }

    private void showEdit() {
//        JOptionPane.showConfirmDialog(this, numcol);
        numcol = tblNanny.getSelectedRow();

        if (numcol != -1) {

            int code_ = Integer.parseInt(tblNanny.getValueAt(tblNanny.getSelectedRow(), 1).toString());
            String name_ = tblNanny.getValueAt(tblNanny.getSelectedRow(), 2).toString();
            int year_ = Integer.parseInt(tblNanny.getValueAt(tblNanny.getSelectedRow(), 3).toString());
            String address_ = tblNanny.getValueAt(tblNanny.getSelectedRow(), 4).toString();
            String phone_ = tblNanny.getValueAt(tblNanny.getSelectedRow(), 5).toString();
            String mail_ = tblNanny.getValueAt(tblNanny.getSelectedRow(), 6).toString();
            Double charge_ = Double.parseDouble(tblNanny.getValueAt(tblNanny.getSelectedRow(), 7).toString());
            int workinghours_ = Integer.parseInt(tblNanny.getValueAt(tblNanny.getSelectedRow(), 8).toString());
            int child_ = Integer.parseInt(tblNanny.getValueAt(tblNanny.getSelectedRow(), 9).toString());

            Entity nanny = new Entity(code_, name_, year_, address_, phone_, mail_, charge_, workinghours_, child_);

            EditDialog dialog = new EditDialog(main, rootPaneCheckingEnabled, nanny, sConn);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                }
            });
            dialog.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(main, "You must select a row in nanny list above");
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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNanny = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        lblSearch = new javax.swing.JLabel();
        btnSelect = new javax.swing.JButton();

        setTitle("The list of Nannies");
        setPreferredSize(new java.awt.Dimension(1122, 600));

        jLabel1.setText("Type nanny's name you find :");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nanny.png"))); // NOI18N

        tblNanny.setModel(model);
        tblNanny.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblNanny.getTableHeader().setReorderingAllowed(false);
        tblNanny.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNannyMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblNanny);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nanny_add.png"))); // NOI18N
        btnAdd.setText("Add   ");
        btnAdd.setToolTipText("To add a new Nanny");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nanny_edit.png"))); // NOI18N
        btnEdit.setText("Edit  ");
        btnEdit.setToolTipText("To edit this Nanny");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nanny_del.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setToolTipText("To del this Nanny");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nanny_close.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.setToolTipText("Back to Main");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        lblSearch.setFont(new java.awt.Font("Tahoma", 2, 11));
        lblSearch.setForeground(new java.awt.Color(255, 0, 51));

        btnSelect.setText("Select Age Group");
        btnSelect.setPreferredSize(new java.awt.Dimension(115, 29));
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 512, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1102, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1102, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(293, 293, 293)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClose)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch))
                        .addGap(22, 22, 22)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnAdd)
                    .addComponent(btnDelete)
                    .addComponent(btnSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClose))
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1122)/2, (screenSize.height-500)/2, 1122, 500);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        searchEvent("tbl_Nanny");
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        showAdd();
        try {
            model = (TableModel) new MyTableModel("tbl_Nanny", "", sConn);
        } catch (SQLException ex) {
            Logger.getLogger(NannyList.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblNanny.setModel(model);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteEvent("tbl_Nanny");
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        showEdit();
        try {
            model = (TableModel) new MyTableModel("tbl_Nanny", "", sConn);
        } catch (SQLException ex) {
            Logger.getLogger(NannyList.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblNanny.setModel(model);
    }//GEN-LAST:event_btnEditActionPerformed

    private void tblNannyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNannyMousePressed
        // TODO add your handling code here:
        numcol=tblNanny.getSelectedRow();
        ActiveButton();
    }//GEN-LAST:event_tblNannyMousePressed

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        // TODO add your handling code here:
        numcol = tblNanny.getSelectedRow();

        if (numcol != -1) {
            int code_ = Integer.parseInt(tblNanny.getValueAt(tblNanny.getSelectedRow(), 1).toString());
            String name_ = tblNanny.getValueAt(tblNanny.getSelectedRow(), 2).toString();
            int year_ = Integer.parseInt(tblNanny.getValueAt(tblNanny.getSelectedRow(), 3).toString());
            String address_ = tblNanny.getValueAt(tblNanny.getSelectedRow(), 4).toString();
            String phone_ = tblNanny.getValueAt(tblNanny.getSelectedRow(), 5).toString();
            String mail_ = tblNanny.getValueAt(tblNanny.getSelectedRow(), 6).toString();
            Double charge_ = Double.parseDouble(tblNanny.getValueAt(tblNanny.getSelectedRow(), 7).toString());
            int workinghours_ = Integer.parseInt(tblNanny.getValueAt(tblNanny.getSelectedRow(), 8).toString());
            int child_ = Integer.parseInt(tblNanny.getValueAt(tblNanny.getSelectedRow(), 9).toString());

            Entity nanny = new Entity(code_, name_, year_, address_, phone_, mail_, charge_, workinghours_, child_);
            new SelectAgeGroup(main, closable, sConn, nanny).setVisible(true);            
        }
        else{
            JOptionPane.showMessageDialog(main, "You must select a row in nanny list above");
        }
    }//GEN-LAST:event_btnSelectActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTable tblNanny;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
