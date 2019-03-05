/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Dinusha
 */
public class BackupNRestore extends javax.swing.JFrame {

    private String typeID;
    private int recId;

    /**
     * Creates new form UserRejistration
     */
    public BackupNRestore() {
        initComponents();
        Menu();
        lbl_username.setText(GUI.Home.un);
        lbl_userType.setText(GUI.Home.ut);
        new Classes.Commons().background(this, lbl_background);
        rad_backup.setSelected(true);
        btn_pathRestore.setEnabled(false);
        txt_pathRestore.setEnabled(false);
        lbl_date.setText(new Classes.Commons().Date());
        new Classes.Commons().time(lbl_time);
    }
 //////------------Menu-----------//////

    void Menu() {
        try {
            popup_menu.setText("Menu");
            popup_help.setText("Help");
            popup_exit.setText("Exit");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        popupMenu = new javax.swing.JPopupMenu();
        popup_menu = new javax.swing.JMenuItem();
        popup_help = new javax.swing.JMenuItem();
        popup_exit = new javax.swing.JMenuItem();
        btn_submit = new javax.swing.JButton();
        pnl_UsrRegheader = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btn_menu = new javax.swing.JButton();
        lbl_userType = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnl_restore = new javax.swing.JPanel();
        lbl_pathRestore = new javax.swing.JLabel();
        lbl_availability1 = new javax.swing.JLabel();
        txt_pathRestore = new javax.swing.JTextField();
        btn_pathRestore = new javax.swing.JButton();
        pnl_backup = new javax.swing.JPanel();
        Lbl_pathBackup = new javax.swing.JLabel();
        lbl_availability = new javax.swing.JLabel();
        txt_pathBackup = new javax.swing.JTextField();
        btn_pathBackup = new javax.swing.JButton();
        rad_restore = new javax.swing.JRadioButton();
        rad_backup = new javax.swing.JRadioButton();
        lbl_background = new javax.swing.JLabel();

        popup_menu.setText("jMenuItem1");
        popup_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popup_menuActionPerformed(evt);
            }
        });
        popupMenu.add(popup_menu);

        popup_help.setText("jMenuItem2");
        popup_help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popup_helpActionPerformed(evt);
            }
        });
        popupMenu.add(popup_help);

        popup_exit.setText("jMenuItem3");
        popup_exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                popup_exitMouseClicked(evt);
            }
        });
        popup_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popup_exitActionPerformed(evt);
            }
        });
        popupMenu.add(popup_exit);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_submit.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_submit.setText("Submit");
        btn_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submitActionPerformed(evt);
            }
        });
        getContentPane().add(btn_submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 310, -1, -1));

        pnl_UsrRegheader.setBackground(new java.awt.Color(255, 255, 255));
        pnl_UsrRegheader.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnl_UsrRegheader.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_UsrRegheaderMouseClicked(evt);
            }
        });
        pnl_UsrRegheader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cancel.png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pnl_UsrRegheader.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 4, 16, 16));

        btn_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu.png"))); // NOI18N
        btn_menu.setContentAreaFilled(false);
        btn_menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_menuActionPerformed(evt);
            }
        });
        pnl_UsrRegheader.add(btn_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 9, 32, 32));

        lbl_userType.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_userType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_userType.setText("User Type");
        pnl_UsrRegheader.add(lbl_userType, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 220, -1));

        lbl_username.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lbl_username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_username.setText("Username");
        pnl_UsrRegheader.add(lbl_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 220, -1));

        lbl_date.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_date.setText("jLabel3");
        pnl_UsrRegheader.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, -1));

        lbl_time.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_time.setText("jLabel3");
        pnl_UsrRegheader.add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 23, 100, -1));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Backup & Restore");
        pnl_UsrRegheader.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 340, 30));

        getContentPane().add(pnl_UsrRegheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 50));

        pnl_restore.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Restore", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        pnl_restore.setOpaque(false);
        pnl_restore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_restoreMouseClicked(evt);
            }
        });

        lbl_pathRestore.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_pathRestore.setText("Path:");

        lbl_availability1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        txt_pathRestore.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        btn_pathRestore.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_pathRestore.setText("...");
        btn_pathRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pathRestoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_restoreLayout = new javax.swing.GroupLayout(pnl_restore);
        pnl_restore.setLayout(pnl_restoreLayout);
        pnl_restoreLayout.setHorizontalGroup(
            pnl_restoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_restoreLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_restoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_restoreLayout.createSequentialGroup()
                        .addComponent(lbl_pathRestore)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_pathRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_pathRestore))
                    .addComponent(lbl_availability1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70))
        );
        pnl_restoreLayout.setVerticalGroup(
            pnl_restoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_restoreLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(pnl_restoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_pathRestore)
                    .addComponent(txt_pathRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_pathRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(137, 137, 137)
                .addComponent(lbl_availability1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(pnl_restore, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 590, 80));

        pnl_backup.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Backup", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        pnl_backup.setOpaque(false);
        pnl_backup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_backupMouseClicked(evt);
            }
        });

        Lbl_pathBackup.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        Lbl_pathBackup.setText("Path:");

        lbl_availability.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        txt_pathBackup.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        btn_pathBackup.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_pathBackup.setText("...");
        btn_pathBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pathBackupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_backupLayout = new javax.swing.GroupLayout(pnl_backup);
        pnl_backup.setLayout(pnl_backupLayout);
        pnl_backupLayout.setHorizontalGroup(
            pnl_backupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_backupLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_backupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_backupLayout.createSequentialGroup()
                        .addComponent(Lbl_pathBackup)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_pathBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_pathBackup))
                    .addComponent(lbl_availability, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70))
        );
        pnl_backupLayout.setVerticalGroup(
            pnl_backupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_backupLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(pnl_backupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Lbl_pathBackup)
                    .addComponent(txt_pathBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_pathBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(137, 137, 137)
                .addComponent(lbl_availability, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(pnl_backup, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 590, 80));

        buttonGroup1.add(rad_restore);
        rad_restore.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        rad_restore.setText("Restore");
        rad_restore.setOpaque(false);
        rad_restore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rad_restoreActionPerformed(evt);
            }
        });
        getContentPane().add(rad_restore, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, -1, -1));

        buttonGroup1.add(rad_backup);
        rad_backup.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        rad_backup.setText("Backup");
        rad_backup.setOpaque(false);
        rad_backup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rad_backupActionPerformed(evt);
            }
        });
        getContentPane().add(rad_backup, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        lbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N
        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl_background.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_backgroundMouseClicked(evt);
            }
        });
        getContentPane().add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 350));

        setSize(new java.awt.Dimension(591, 350));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submitActionPerformed
        submit();
    }//GEN-LAST:event_btn_submitActionPerformed

    private void btn_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menuActionPerformed
        new Menu().setVisible(true);
    }//GEN-LAST:event_btn_menuActionPerformed

    private void rad_backupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rad_backupActionPerformed
        btn_pathRestore.setEnabled(false);
        txt_pathRestore.setEnabled(false);
        btn_pathBackup.setEnabled(true);
        txt_pathBackup.setEnabled(true);
    }//GEN-LAST:event_rad_backupActionPerformed

    private void rad_restoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rad_restoreActionPerformed
        btn_pathRestore.setEnabled(true);
        txt_pathRestore.setEnabled(true);
        btn_pathBackup.setEnabled(false);
        txt_pathBackup.setEnabled(false);
    }//GEN-LAST:event_rad_restoreActionPerformed

    private void btn_pathBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pathBackupActionPerformed
        backupFile();
    }//GEN-LAST:event_btn_pathBackupActionPerformed

    private void btn_pathRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pathRestoreActionPerformed
        restoreFile();
    }//GEN-LAST:event_btn_pathRestoreActionPerformed

    private void popup_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_menuActionPerformed
        this.dispose();
        new Menu().setVisible(true);       
    }//GEN-LAST:event_popup_menuActionPerformed

    private void popup_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_helpActionPerformed
        try {
            Desktop.getDesktop().open(new File("E:\\Syndicate\\Documentation\\General – Backup and Restore.pdf"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_popup_helpActionPerformed

    private void popup_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_exitActionPerformed
        this.dispose();
    }//GEN-LAST:event_popup_exitActionPerformed

    private void popup_exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_popup_exitMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_popup_exitMouseClicked

    private void pnl_UsrRegheaderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_UsrRegheaderMouseClicked
       if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_UsrRegheaderMouseClicked

    private void pnl_restoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_restoreMouseClicked
       if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_restoreMouseClicked

    private void pnl_backupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_backupMouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_backupMouseClicked

    private void lbl_backgroundMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_backgroundMouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_lbl_backgroundMouseClicked

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
            java.util.logging.Logger.getLogger(BackupNRestore.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BackupNRestore.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BackupNRestore.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BackupNRestore.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BackupNRestore().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Lbl_pathBackup;
    private javax.swing.JButton btn_menu;
    private javax.swing.JButton btn_pathBackup;
    private javax.swing.JButton btn_pathRestore;
    private javax.swing.JButton btn_submit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl_availability;
    private javax.swing.JLabel lbl_availability1;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_pathRestore;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_userType;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_UsrRegheader;
    private javax.swing.JPanel pnl_backup;
    private javax.swing.JPanel pnl_restore;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem popup_exit;
    private javax.swing.JMenuItem popup_help;
    private javax.swing.JMenuItem popup_menu;
    private javax.swing.JRadioButton rad_backup;
    private javax.swing.JRadioButton rad_restore;
    private javax.swing.JTextField txt_pathBackup;
    private javax.swing.JTextField txt_pathRestore;
    // End of variables declaration//GEN-END:variables
    void backupFile() {
        try {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("SQL Files", "sql");
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setFileFilter(filter);
            chooser.showSaveDialog(this);
            File file = chooser.getSelectedFile();
            String filename = file.getAbsolutePath();
            txt_pathBackup.setText(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void restoreFile() {
        try {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("SQL Files", "sql");
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(this);
            File file = chooser.getSelectedFile();
            String filename = file.getAbsolutePath();
            txt_pathRestore.setText(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void submit() {
        if (rad_backup.isSelected()) {
            Calendar cal = new GregorianCalendar();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DATE);
            int hour = cal.get(Calendar.HOUR);
            int min = cal.get(Calendar.MINUTE);
            int sec = cal.get(Calendar.SECOND);
            String s = year + "-" + month + "-" + day;
            String path = txt_pathBackup.getText();

            String sql1 = "C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin\\mysqldump -uroot -p1234 world -r " + path + s + ".sql";

            try {
                Runtime.getRuntime().exec(sql1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            txt_pathBackup.setText(null);
        } else if (rad_restore.isSelected()) {
            String path = txt_pathRestore.getText();

            try {
                String[] executeCmd = new String[]{"C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin\\mysql.exe","syndicate", "-uroot", "-p1234", "-e", "source " + path};
                Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                int processComplete = runtimeProcess.waitFor();

                System.out.println(processComplete);

                if (processComplete == 0) {
                    JOptionPane.showMessageDialog(this, "Database restored successfully...!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Database restoring failed. Please try again later..!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                runtimeProcess.destroy();
                txt_pathRestore.setText(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
