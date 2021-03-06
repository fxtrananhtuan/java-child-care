package ageGroup.view;

import ageGroup.business.MyListModelAll;
import ageGroup.business.MyListModelSelected;
import ageGroup.dataObject.AgeGroupDAO;
import ageGroup.valuesObject.AgeGroupVO;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author DThanh
 */
public class SelectActivities extends javax.swing.JDialog {

    /** Creates new form SelectActivities */
    public SelectActivities(java.awt.Frame parent, boolean modal, String connect, AgeGroupVO ageVO) {
        super(parent, modal);
        dao = new AgeGroupDAO(connect);
        this.connect = connect;
        this.ageVO = ageVO;

        try {
            listModelAll = new MyListModelAll(connect, ageVO.getAgeCode());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            listModelSelected = new MyListModelSelected(connect, ageVO.getAgeCode());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        initComponents();
        lblActivityOf.setText(ageVO.getChildAge());

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
        lblActivityList = new javax.swing.JLabel();
        lblAgeGroup = new javax.swing.JLabel();
        lblActivityOf = new javax.swing.JLabel();
        scpActivities = new javax.swing.JScrollPane();
        lstActivities = new javax.swing.JList();
        pnlButton = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        scpActivityOf = new javax.swing.JScrollPane();
        lstActivityOf = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Select Activities");
        getContentPane().setLayout(new java.awt.FlowLayout());

        lblHeader.setFont(new java.awt.Font("Tahoma", 0, 24));
        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setText("Select Activity");
        lblHeader.setPreferredSize(new java.awt.Dimension(500, 30));
        getContentPane().add(lblHeader);

        lblActivityList.setFont(new java.awt.Font("Tahoma", 0, 14));
        lblActivityList.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActivityList.setText("Activities List");
        lblActivityList.setPreferredSize(new java.awt.Dimension(200, 20));
        getContentPane().add(lblActivityList);

        lblAgeGroup.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAgeGroup.setPreferredSize(new java.awt.Dimension(100, 20));
        getContentPane().add(lblAgeGroup);

        lblActivityOf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblActivityOf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActivityOf.setText("Activities of ");
        lblActivityOf.setPreferredSize(new java.awt.Dimension(200, 20));
        getContentPane().add(lblActivityOf);

        scpActivities.setPreferredSize(new java.awt.Dimension(200, 250));

        lstActivities.setModel(listModelAll);
        lstActivities.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstActivities.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lstActivitiesMousePressed(evt);
            }
        });
        scpActivities.setViewportView(lstActivities);

        getContentPane().add(scpActivities);

        pnlButton.setInheritsPopupMenu(true);
        pnlButton.setPreferredSize(new java.awt.Dimension(100, 250));

        btnAdd.setText("Add");
        btnAdd.setEnabled(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(100, 23));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        pnlButton.add(btnAdd);

        btnRemove.setText("Remove");
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

        lstActivityOf.setModel(listModelSelected);
        lstActivityOf.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstActivityOf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lstActivityOfMousePressed(evt);
            }
        });
        scpActivityOf.setViewportView(lstActivityOf);

        getContentPane().add(scpActivityOf);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-550)/2, (screenSize.height-400)/2, 550, 400);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(this, "Are you exit?", "Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            this.setVisible(false);
        }
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        int selectRow_ = -1;
        selectRow_ = lstActivities.getSelectedIndex();
        String row_ = (String) lstActivities.getSelectedValue();
        int activeCode_ = Integer.parseInt(row_.substring(0, row_.indexOf(".")));
        if (selectRow_ != -1) {

            try {
                dao.addActivities(ageVO.getAgeCode(), activeCode_);
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
        selectRow_ = lstActivityOf.getSelectedIndex();
        String row_ = (String) lstActivityOf.getSelectedValue();
        int activeCode_ = Integer.parseInt(row_.substring(0, row_.indexOf(".")));
        if (selectRow_ != -1) {
            try {
                dao.removeActivities(ageVO.getAgeCode(), activeCode_);
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

    private void lstActivitiesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstActivitiesMousePressed
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
            lstActivityOf.removeSelectionInterval(0, dao.showActivitySelected(ageVO.getAgeCode()).size() - 1);
            btnRemove.setEnabled(false);
            if (dao.showActivityAll(ageVO.getAgeCode()).isEmpty()) {
                btnAdd.setEnabled(false);
            } else {
                btnAdd.setEnabled(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_lstActivitiesMousePressed

    private void lstActivityOfMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstActivityOfMousePressed
         try {
            // TODO add your handling code here:
            lstActivities.removeSelectionInterval(0, dao.showActivityAll(ageVO.getAgeCode()).size() - 1);
            btnAdd.setEnabled(false);
            if (dao.showActivitySelected(ageVO.getAgeCode()).isEmpty()) {
                btnRemove.setEnabled(false);
            } else {
                btnRemove.setEnabled(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_lstActivityOfMousePressed

    private void refreshList() {
        try {
            listModelAll = new MyListModelAll(connect, ageVO.getAgeCode());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            listModelSelected = new MyListModelSelected(connect, ageVO.getAgeCode());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        lstActivityOf.setModel(listModelSelected);
        scpActivityOf.setViewportView(lstActivityOf);
        lstActivities.setModel(listModelAll);
        scpActivities.setViewportView(lstActivities);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRemove;
    private javax.swing.JLabel lblActivityList;
    private javax.swing.JLabel lblActivityOf;
    private javax.swing.JLabel lblAgeGroup;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JList lstActivities;
    private javax.swing.JList lstActivityOf;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JScrollPane scpActivities;
    private javax.swing.JScrollPane scpActivityOf;
    // End of variables declaration//GEN-END:variables
    private MyListModelAll listModelAll;
    private MyListModelSelected listModelSelected;
    private AgeGroupDAO dao;
    private String connect = "";
    private AgeGroupVO ageVO = null;
}
