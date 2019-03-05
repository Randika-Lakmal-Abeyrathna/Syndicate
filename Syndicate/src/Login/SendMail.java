/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import static Login.PasswordRecovery.u;
import com.sun.mail.util.MailConnectException;
import java.security.MessageDigest;
import java.security.Security;
import java.sql.ResultSet;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

/**
 *
 * @author Uditha N. Bandara
 */
public class SendMail extends javax.swing.JFrame {

    /**
     * Creates new form PasswordRecovery
     */
    public SendMail() {
        initComponents();
        new Classes.Commons().background(this, lbl_background);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setUndecorated(true);
        com.sun.awt.AWTUtilities.setWindowOpaque(this, false);
        lbl_username.setText(PasswordRecovery.u);
        getMail();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_background1 = new javax.swing.JPanel();
        pnl_background2 = new javax.swing.JPanel();
        pnl_email = new javax.swing.JPanel();
        lbl_email = new javax.swing.JLabel();
        lbl_messagel1 = new javax.swing.JLabel();
        lbl_message2 = new javax.swing.JLabel();
        btn_yes = new javax.swing.JButton();
        btn_back = new javax.swing.JButton();
        btn_no = new javax.swing.JButton();
        lbl_username = new javax.swing.JLabel();
        lbl_background = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnl_background1.setBackground(Classes.Commons.Transparent);
        pnl_background1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_background2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)));
        pnl_background2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_email.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Email", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 10), new java.awt.Color(0, 0, 0))); // NOI18N
        pnl_email.setOpaque(false);

        lbl_email.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_email.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_email.setText("email@gmail.com");

        lbl_messagel1.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_messagel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_messagel1.setText("Is this you email address? If you press \"Yes\", a email with");

        lbl_message2.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_message2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_message2.setText("a security code will be sent to above email address.");

        btn_yes.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_yes.setText("Yes");
        btn_yes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_yesActionPerformed(evt);
            }
        });

        btn_back.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_back.setText("<< Back");
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });

        btn_no.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_no.setText("No");
        btn_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_noActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_emailLayout = new javax.swing.GroupLayout(pnl_email);
        pnl_email.setLayout(pnl_emailLayout);
        pnl_emailLayout.setHorizontalGroup(
            pnl_emailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_emailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_emailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_messagel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_message2, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                    .addComponent(lbl_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_emailLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_yes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_no, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)))
                .addContainerGap())
        );
        pnl_emailLayout.setVerticalGroup(
            pnl_emailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_emailLayout.createSequentialGroup()
                .addComponent(lbl_email, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_messagel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lbl_message2)
                .addGap(18, 18, 18)
                .addGroup(pnl_emailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_yes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_no, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        pnl_background2.add(pnl_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 400, 180));

        lbl_username.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lbl_username.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_username.setText("Username");
        pnl_background2.add(lbl_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 420, 40));

        lbl_background.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lbl_background.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)));
        pnl_background2.add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 270));

        pnl_background1.add(pnl_background2, new org.netbeans.lib.awtextra.AbsoluteConstraints(463, 249, 440, 270));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cancelWhite.png"))); // NOI18N
        jButton1.setToolTipText("Close");
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pnl_background1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1340, 10, 16, 16));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1366, 768));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Classes.Commons.FadeOut(this);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_yesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_yesActionPerformed
        sendMail();
    }//GEN-LAST:event_btn_yesActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Classes.Commons.FadeIn(this);
    }//GEN-LAST:event_formWindowOpened

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        Classes.Commons.FadeOut(this);
        this.dispose();
        new PasswordRecovery().setVisible(true);
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_noActionPerformed
        Classes.Commons.FadeOut(this);
        this.dispose();
        new PasswordRecovery().setVisible(true);
    }//GEN-LAST:event_btn_noActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SendMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SendMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SendMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SendMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SendMail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_no;
    private javax.swing.JButton btn_yes;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_message2;
    private javax.swing.JLabel lbl_messagel1;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_background1;
    private javax.swing.JPanel pnl_background2;
    private javax.swing.JPanel pnl_email;
    // End of variables declaration//GEN-END:variables
void getMail() {
        String un = lbl_username.getText();

        try {
            ResultSet rs1 = new Classes.JDBC().getData("SELECT login.user_name, "
                    + "employee_reg.email FROM login INNER JOIN employee_reg "
                    + "ON (login.emp_id=employee_reg.emp_id) WHERE user_name='" + un + "'");

            ResultSet rs2 = new Classes.JDBC().getData("SELECT login.user_name, "
                    + "teacher_reg.email FROM login INNER JOIN teacher_reg "
                    + "ON (login.teacher_id=teacher_reg.teacher_id) WHERE user_name='" + un + "'");
            
            ResultSet rs3 = new Classes.JDBC().getData("SELECT company_email FROM admin_panel");

            if (rs1.next()) {
                u = rs1.getString("user_name");
                String email = rs1.getString("email");

                lbl_email.setText(email);
            } else if (rs2.next()) {
                u = rs2.getString("user_name");
                String email = rs2.getString("email");

                lbl_email.setText(email);
            } else if (rs3.next()) {
                String email = rs3.getString("company_email");
                
                lbl_email.setText(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendMail() {
        try {
            String un = lbl_username.getText();
            ResultSet rs = new Classes.JDBC().getData("SELECT password FROM login WHERE user_name='" + un + "'");

            if (rs.next()) {
                String pw = rs.getString("password");

                String original = pw;
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(original.getBytes());
                byte[] diegest = md.digest();
                StringBuffer sb = new StringBuffer();
                for (byte b : diegest) {
                    sb.append(String.format("%02x", b & 0xff));
                }
                
                String password = sb.toString();

                String code = password;
                String[] to = {lbl_email.getText()};
                Classes.SenderClass.emailSender("syndicateteam2016@gmail.com", "nwbgudunxuqvabfd", "This is your Password recovery code:\n " + code
                        +"\n\nCopy and paste this to 'Enter Security Code' window to reset your password.", to);
                JOptionPane.showMessageDialog(this, "Email sent successfully.", "Email Confirmation", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please check your Internet Connection.", "Connection Unavailable", JOptionPane.INFORMATION_MESSAGE);
        }

        Classes.Commons.FadeOut(this);
        this.dispose();
        new EnterSecurityCode().setVisible(true);
    }
}