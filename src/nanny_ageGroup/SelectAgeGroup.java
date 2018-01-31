package nanny_ageGroup;

import Nanny.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author DThanh
 */
public class SelectAgeGroup extends javax.swing.JDialog {

    /** Creates new form SelectActivities */
    public SelectAgeGroup(java.awt.Frame parent, boolean modal, String connect, Entity nanny) {
        super(parent, modal);
        dao = new DataAccessList(connect);
        this.connect = connect;
        this.nanny = nanny;

        try {
            listModelAll = new MyListModelAll1(connect, nanny.getCode());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            listModelSelected = new MyListModelSelected1(connect, nanny.getCode());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        initComponents();
        lblAgeGroupOf.setText(nanny.getName());

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblHeader = new javax.swing.JLabel();
        lblAgeGroupAll = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblAgeGroupOf = new javax.swing.JLabel();
        scpAgeGroupAll = new javax.swing.JScrollPane();
        lstAgeGroupAll = new javax.swing.JList();
        pnlButton = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        scpActivityOf = new javax.swing.JScrollPane();
        lstAgeGroupOf = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Select AgeGroup to Nanny ");
        getContentPane().setLayout(new java.awt.FlowLayout());

        lblHeader.setFont(new java.awt.Font("VNI-Present", 1, 24)); // NOI18N
        lblHeader.setForeground(new java.awt.Color(0, 0, 102));
        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setText("SELECT AGE GROUP");
        lblHeader.setPreferredSize(new java.awt.Dimension(500, 50));
        getContentPane().add(lblHeader);

        lblAgeGroupAll.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAgeGroupAll.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAgeGroupAll.setText("Age Group List");
        lblAgeGroupAll.setPreferredSize(new java.awt.Dimension(200, 20));
        getContentPane().add(lblAgeGroupAll);

        jLabel1.setPreferredSize(new java.awt.Dimension(100, 20));
        getContentPane().add(jLabel1);

        lblAgeGroupOf.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAgeGroupOf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAgeGroupOf.setText("To Nanny");
        lblAgeGroupOf.setPreferredSize(new java.awt.Dimension(200, 20));
        getContentPane().add(lblAgeGroupOf);

        scpAgeGroupAll.setPreferredSize(new java.awt.Dimension(200, 250));

        lstAgeGroupAll.setModel(listModelAll);
        lstAgeGroupAll.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstAgeGroupAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lstAgeGroupAllMousePressed(evt);
            }
        });
        scpAgeGroupAll.setViewportView(lstAgeGroupAll);

        getContentPane().add(scpAgeGroupAll);

        pnlButton.setInheritsPopupMenu(true);
        pnlButton.setPreferredSize(new java.awt.Dimension(100, 250));

        jLabel2.setPreferredSize(new java.awt.Dimension(34, 50));
        pnlButton.add(jLabel2);

        btnAdd.setText("Add ->>");
        btnAdd.setEnabled(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(100, 23));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        pnlButton.add(btnAdd);

        btnRemove.setText("<<- Remove");
        btnRemove.setEnabled(false);
        btnRemove.setPreferredSize(new java.awt.Dimension(100, 23));
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        pnlButton.add(btnRemove);

        btnClose.setText("Close");
        btnClose.setPreferredSize(new java.awt.Dimension(100, 23));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        pnlButton.add(btnClose);

        getContentPane().add(pnlButton);

        scpActivityOf.setPreferredSize(new java.awt.Dimension(200, 250));

        lstAgeGroupOf.setModel(listModelSelected);
        lstAgeGroupOf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lstAgeGroupOfMousePressed(evt);
            }
        });
        scpActivityOf.setViewportView(lstAgeGroupOf);

        getContentPane().add(scpActivityOf);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-550)/2, (screenSize.height-400)/2, 550, 400);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
            this.setVisible(false);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        int selectRow_;
        selectRow_ = lstAgeGroupAll.getSelectedIndex();
        String row_ = (String) lstAgeGroupAll.getSelectedValue();
        int ageCode_ = Integer.parseInt(row_.substring(0, row_.indexOf(".")));
        if (selectRow_ != -1) {
            try {
                dao.addAgeGroup(nanny.getCode(), ageCode_);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            btnAdd.setEnabled(false);
            btnRemove.setEnabled(false);
            refreshList();

        } else if (selectRow_ == -1) {
            JOptionPane.showMessageDialog(this, "You have must be select the a row in left list.");
            return;
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // TODO add your handling code here:
        int selectRow_ = -1;
        selectRow_ = lstAgeGroupOf.getSelectedIndex();
        String row_ = (String) lstAgeGroupOf.getSelectedValue();
        int ageCode_ = Integer.parseInt(row_.substring(0, row_.indexOf(".")));
        if (selectRow_ != -1) {
            try {
                dao.removeAgeGroup(nanny.getCode(), ageCode_);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            btnAdd.setEnabled(false);
            btnRemove.setEnabled(false);
            refreshList();
        } else if (selectRow_ == -1) {
            JOptionPane.showMessageDialog(this, "You have must be select the a row in right list.");
            return;
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void lstAgeGroupAllMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstAgeGroupAllMousePressed
        try {
            // TODO add your handling code here:
            lstAgeGroupOf.removeSelectionInterval(0, dao.showAgeGroupSelected(nanny.getCode()).size() - 1);
            btnRemove.setEnabled(false);
            if (dao.showAgeGroupAll(nanny.getCode()).isEmpty()) {
                btnAdd.setEnabled(false);
            } else {
                btnAdd.setEnabled(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
}//GEN-LAST:event_lstAgeGroupAllMousePressed

    private void lstAgeGroupOfMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstAgeGroupOfMousePressed
        try {
            // TODO add your handling code here:
            lstAgeGroupAll.removeSelectionInterval(0, dao.showAgeGroupAll(nanny.getCode()).size() - 1);
            btnAdd.setEnabled(false);
            if (dao.showAgeGroupSelected(nanny.getCode()).isEmpty()) {
                btnRemove.setEnabled(false);
            } else {
                btnRemove.setEnabled(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
}//GEN-LAST:event_lstAgeGroupOfMousePressed

    private void refreshList() {
        try {
            listModelAll = new MyListModelAll1(connect, nanny.getCode());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            listModelSelected = new MyListModelSelected1(connect, nanny.getCode());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        lstAgeGroupOf.setModel(listModelSelected);
        scpActivityOf.setViewportView(lstAgeGroupOf);
        lstAgeGroupAll.setModel(listModelAll);
        scpAgeGroupAll.setViewportView(lstAgeGroupAll);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRemove;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblAgeGroupAll;
    private javax.swing.JLabel lblAgeGroupOf;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JList lstAgeGroupAll;
    private javax.swing.JList lstAgeGroupOf;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JScrollPane scpActivityOf;
    private javax.swing.JScrollPane scpAgeGroupAll;
    // End of variables declaration//GEN-END:variables
    private MyListModelAll1 listModelAll;
    private MyListModelSelected1 listModelSelected;
    private DataAccessList dao;
    private String connect = "";
    private Entity nanny = null;
}
