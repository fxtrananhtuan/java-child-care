/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * dialogPermistion.java
 *
 * Created on Jun 30, 2009, 9:43:49 PM
 */

package UserAndFunctions;

import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class dialogPermistion extends javax.swing.JDialog {
    private static frm_UserFunction frmUserFunction=null;
    public static int iState;
    private static Object[] listPermision;
    /** Creates new form dialogPermistion */
    public dialogPermistion(java.awt.Frame parent, boolean modal, frm_UserFunction _frmUserFunction, int _iState, Object[] _permistionList) {
        super(parent, modal);
        frmUserFunction = (frm_UserFunction) _frmUserFunction;
        listPermision = _permistionList;
        iState =_iState;
        initComponents();

        if(iState==1){
            if (Integer.parseInt(listPermision[0].toString())==1)
                chkAdd.setSelected(true);
            if (Integer.parseInt(listPermision[1].toString())==1)
                chkUpdate.setSelected(true);
            if (Integer.parseInt(listPermision[2].toString())==1)
                chkDelete.setSelected(true);
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

        btnSaveChange = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        chkAdd = new javax.swing.JCheckBox();
        chkDelete = new javax.swing.JCheckBox();
        chkUpdate = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Select function permission");

        btnSaveChange.setText("Save changes");
        btnSaveChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveChangeActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        chkAdd.setText("Add");

        chkDelete.setText("Delete");

        chkUpdate.setText("Update");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSaveChange, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chkAdd)
                        .addGap(16, 16, 16)
                        .addComponent(chkDelete)
                        .addGap(18, 18, 18)
                        .addComponent(chkUpdate)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkAdd)
                    .addComponent(chkDelete)
                    .addComponent(chkUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveChange)
                    .addComponent(btnCancel))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveChangeActionPerformed
        // TODO add your handling code here:
        int[] iPermisstion = new int[3];
        for (int i=0; i<iPermisstion.length; i++)
            iPermisstion[i]=0;
        if (chkAdd.isSelected())
            iPermisstion[0]=1;
        if (chkDelete.isSelected())
            iPermisstion[1]=1;
        if (chkUpdate.isSelected())
            iPermisstion[2]=1;

        if (iState==0){
            frmUserFunction.InsertRows(iPermisstion);
            JOptionPane.showMessageDialog(this,"Add new successful.");
        }
        else{
            frmUserFunction.UpdatePermision(iPermisstion);
            JOptionPane.showMessageDialog(this,"Update successful.");
        }
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnSaveChangeActionPerformed

//    private void ViewPermistion(){
//        DAO_UserFunction daouser=new DAO_UserFunction(_con)
//    }
    /**
    * @param args the command line arguments
    */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                dialogPermistion dialog = new dialogPermistion(new javax.swing.JFrame(), true, frmUserFunction);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSaveChange;
    private javax.swing.JCheckBox chkAdd;
    private javax.swing.JCheckBox chkDelete;
    private javax.swing.JCheckBox chkUpdate;
    // End of variables declaration//GEN-END:variables

}
