package Login;

import java.awt.Toolkit;
import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author Uditha N. Bandara
 */
public class Login extends javax.swing.JFrame {

    int restrict = 0;
    int checkStatus;
    public static String username;
    public static String userType;
    public static String jobName;
    String status;
    String password;
    public static String homeSetType;
    String getPwLbl;

    public Login() {
        this.setUndecorated(true);
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        lbl_getPassword.setVisible(false);
        lbl_setPassword.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_background = new javax.swing.JPanel();
        pnl_login = new javax.swing.JPanel();
        btn_login = new javax.swing.JButton();
        btn_cancel = new javax.swing.JButton();
        lbl_username = new javax.swing.JLabel();
        lbl_password = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        txt_password = new javax.swing.JPasswordField();
        btn_forgotPassword = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        lbl_warning = new javax.swing.JLabel();
        lbl_getPassword = new javax.swing.JLabel();
        lbl_setPassword = new javax.swing.JLabel();
        lbl_background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setBackground(Classes.Commons.Transparent);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_background.setBackground(new java.awt.Color(255, 255, 255));
        pnl_background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_login.setOpaque(false);

        btn_login.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_login.setText("Login");
        btn_login.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        btn_cancel.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_cancel.setText("Cancel");
        btn_cancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        lbl_username.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_username.setText("Username:");

        lbl_password.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_password.setText("Password:");

        txt_username.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        txt_password.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        btn_forgotPassword.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_forgotPassword.setText("Forgot password?");
        btn_forgotPassword.setContentAreaFilled(false);
        btn_forgotPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_forgotPassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btn_forgotPassword.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_forgotPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_forgotPasswordActionPerformed(evt);
            }
        });

        btn_clear.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_clear.setText("Clear");
        btn_clear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        lbl_warning.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_warning.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_getPassword.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_getPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_setPassword.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_setPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnl_loginLayout = new javax.swing.GroupLayout(pnl_login);
        pnl_login.setLayout(pnl_loginLayout);
        pnl_loginLayout.setHorizontalGroup(
            pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_loginLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_loginLayout.createSequentialGroup()
                        .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_loginLayout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(btn_forgotPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_loginLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_username)
                            .addComponent(lbl_password))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_username)
                            .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbl_warning, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_loginLayout.createSequentialGroup()
                    .addContainerGap(97, Short.MAX_VALUE)
                    .addComponent(lbl_getPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(57, 57, 57)))
            .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_loginLayout.createSequentialGroup()
                    .addContainerGap(107, Short.MAX_VALUE)
                    .addComponent(lbl_setPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(47, 47, 47)))
        );
        pnl_loginLayout.setVerticalGroup(
            pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_loginLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lbl_warning, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_username)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_password)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_clear)
                    .addComponent(btn_cancel)
                    .addComponent(btn_login))
                .addGap(17, 17, 17)
                .addComponent(btn_forgotPassword)
                .addGap(52, 52, 52))
            .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_loginLayout.createSequentialGroup()
                    .addGap(46, 46, 46)
                    .addComponent(lbl_getPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(226, Short.MAX_VALUE)))
            .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_loginLayout.createSequentialGroup()
                    .addGap(56, 56, 56)
                    .addComponent(lbl_setPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(216, Short.MAX_VALUE)))
        );

        pnl_background.add(pnl_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(433, 0, 500, 300));

        lbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/LoginBack.png"))); // NOI18N
        pnl_background.add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 300));

        getContentPane().add(pnl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 234, 1366, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        Classes.Commons.FadeOut(this);
        System.exit(0);
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        Login();
    }//GEN-LAST:event_btn_loginActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        clear();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Classes.Commons.FadeIn(this);
    }//GEN-LAST:event_formWindowOpened

    private void btn_forgotPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_forgotPasswordActionPerformed
        Classes.Commons.FadeOut(this);
        this.dispose();
        new PasswordRecovery().setVisible(true);
    }//GEN-LAST:event_btn_forgotPasswordActionPerformed
    /**
     * @param args the command line arguments
     */
    private static Login UI;

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UI = new Login();
                UI.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_forgotPassword;
    private javax.swing.JButton btn_login;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_getPassword;
    private javax.swing.JLabel lbl_password;
    private javax.swing.JLabel lbl_setPassword;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JLabel lbl_warning;
    private javax.swing.JPanel pnl_background;
    private javax.swing.JPanel pnl_login;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
    void Login() {
        String un = txt_username.getText();
        String pw = new String(txt_password.getPassword());
        Logger log = Logger.getLogger("Details");
        try {
            restrict++;

            ResultSet rs1 = new Classes.JDBC().getData(
                    "SELECT\n"
                    + "  login.user_name,\n"
                    + "  login.password,\n"
                    + "  employee_reg.status,\n"
                    + "  job_title.job_name\n"
                    + "FROM\n"
                    + "  login\n"
                    + "INNER JOIN\n"
                    + "  employee_reg\n"
                    + "INNER JOIN\n"
                    + "  job_title\n"
                    + "ON (login.system_id=employee_reg.emp_id\n"
                    + "    AND\n"
                    + "    employee_reg.job_title_id=job_title.job_title_id)\n"
                    + "WHERE\n"
                    + "  user_name='" + un + "'\n"
                    + "AND\n"
                    + "  password='" + pw + "'");

            ResultSet rs2 = new Classes.JDBC().getData(
                    "SELECT\n"
                    + "  login.user_name,\n"
                    + "  login.password,\n"
                    + "  teacher_reg.status\n"
                    + "FROM\n"
                    + "  login\n"
                    + "INNER JOIN\n"
                    + "  teacher_reg\n"
                    + "ON (login.system_id=teacher_reg.teacher_id)\n"
                    + "WHERE\n"
                    + "  user_name='" + un + "' "
                    + "AND "
                    + "  password='" + pw + "'");

            ResultSet rs3 = new Classes.JDBC().getData(
                    "SELECT\n"
                    + "  user_type.user_type,\n"
                    + "  login.user_name,\n"
                    + "  login.password\n"
                    + "FROM\n"
                    + "  login\n"
                    + "INNER JOIN\n"
                    + "  user_type\n"
                    + "ON\n"
                    + "  (login.user_type_id=user_type.user_type_id\n"
                    + "  AND\n"
                    + "  login.system_id=user_type.user_type_id)\n"
                    + "WHERE\n"
                    + "  user_name='" + un + "'\n"
                    + "AND\n"
                    + "  password='" + pw + "'");

            if (rs1.next()) {
                username = rs1.getString("user_name");
                password = rs1.getString("password");
                status = rs1.getString("status");
                jobName = rs1.getString("job_name");

                checkStatus = Integer.parseInt(status);

                if (username.equals(un)) {
                    if (password.equals(pw)) {
                        if (checkStatus == 0) {
                            JOptionPane.showMessageDialog(this, "Your account has been deactivated.\nPlease countact your admin.");
                            clear();
                        } else if (checkStatus == 1) {
                            System.out.println(restrict);
                            Classes.Commons.FadeOut(this);
                            homeSetType = jobName;
                            this.dispose();
                            // Loggers Start
                            createFoldaerToName(username);
                            log.info("Login at: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                            //Loggers End
                            new GUI.Home().setVisible(true);
                        }
                    }
                }
            } else if (rs2.next()) {
                username = rs2.getString("user_name");
                password = rs2.getString("password");
                status = rs2.getString("status");

                checkStatus = Integer.parseInt(status);

                if (username.equals(un)) {
                    if (password.equals(pw)) {
                        if (checkStatus == 0) {
                            JOptionPane.showMessageDialog(this, "Your account has been deactivated.\nPlease countact your admin.");
                            clear();
                        } else if (checkStatus == 1) {
                            System.out.println(restrict);
                            Classes.Commons.FadeOut(this);
                            homeSetType = jobName;
                            this.dispose();
                            // Loggers Start
                            createFoldaerToName(username);
                            log.info("Login at: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                            //Loggers End
                            new GUI.Home().setVisible(true);
                        }
                    }
                }
            } else if (rs3.next()) {
                username = rs3.getString("user_name");
                password = rs3.getString("password");
                userType = rs3.getString("user_type");

                if (username.equals(un)) {
                    if (password.equals(pw)) {
                        System.out.println(restrict);
                        Classes.Commons.FadeOut(this);
                        homeSetType = "Admin";
                        this.dispose();
                        // Loggers Start
                        createFoldaerToName(username);
                        log.info("Login at: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        //Loggers End
                        new GUI.Home().setVisible(true);
                    }
                }
            } else {
                System.out.println(restrict);
                if (restrict == 1) {
                    System.out.println(restrict);
                    Toolkit.getDefaultToolkit().beep();
                    lbl_warning.setText("2 attempts left");
                    clear();
                } else if (restrict == 2) {
                    System.out.println(restrict);
                    Toolkit.getDefaultToolkit().beep();
                    lbl_warning.setText("1 attempt left");
                    clear();
                } else if (restrict == 3) {
                    System.out.println(restrict);
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Sorry,\nYour Login Has Been Restricted", "Restricted !", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createFoldaerToName(String name) {
        try {
            String path = "E:\\Syndicate";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            String path2 = path + "\\" + name;
            File file1 = new File(path2);
            if (!file1.exists()) {
                file1.mkdir();
            }
            Classes.loggers.setLoger(path2);
        } catch (Exception e) {
            System.out.println("in createFoldaerToName method in class Login");
            e.printStackTrace();
        }
    }

    void clear() {
        txt_username.setText(null);
        txt_password.setText(null);
        txt_username.grabFocus();
    }
}
