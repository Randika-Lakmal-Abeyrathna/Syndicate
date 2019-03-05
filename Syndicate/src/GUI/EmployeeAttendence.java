/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.JDBC;
import java.awt.Desktop;
import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;


/**
 *
 * @author Piyumika
 */
public class EmployeeAttendence extends javax.swing.JFrame {

    /**
     * Creates new form EmployeeAttendence
     */
    JDBC DB = new JDBC();
    Logger log=Logger.getLogger("Employee Attendance");
    public EmployeeAttendence() {
        initComponents();
        Menu();
        txt_employeeId.grabFocus();
        //txt_outTime.setVisible(false);
        lbl_date.setText(new Classes.Commons().Date());
        new Classes.Commons().time(lbl_time);
        DateChooser.setMaxSelectableDate(new Date());
        new Classes.Commons().background(this, lbl_background);
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


    //- - - - - - - - - - - Generate Id
    void generate_id() {
        try {

            ResultSet rs1 = DB.getData("SELECT MAX(attendance_id) AS att FROM attendance");
            while (rs1.next()) {
                String attendance_id = rs1.getString("att");
                System.out.println(attendance_id);
                int i = Integer.parseInt(attendance_id);
                int j = i + 1;
                String ID = j + "";
                lbl_attendanceId.setText(ID);
            }

        } catch (Exception ex) {
            System.out.println("in generate_id method in class EmployeeAttendanse ");
            ex.printStackTrace();
        }

    }

/////////////////////////////////// Check Details

    void checkDetails(String employeeId) {
        DateChooser.setEnabled(false);
        txt_inTime.setEditable(true);
        try {
            ResultSet rs = DB.getData("SELECT * FROM attendance WHERE date='" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "' AND emp_id='" + employeeId + "'");
            if (rs.next()) {
                String id = rs.getString("attendance_id");
                String date = rs.getString("date");
                String intime = rs.getString("in_time");

                lbl_attendanceId.setText(id);
                DateChooser.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                txt_inTime.setText(intime);
                txt_inTime.setEditable(false);
                String outTime=new SimpleDateFormat("HH:mm:ss").format(new Date());
                txt_outTime.setText(outTime);
                txt_outTime.setEditable(false);
            } else {
                generate_id();
                txt_inTime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
                DateChooser.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
                txt_inTime.setEditable(false);
                txt_outTime.setEditable(false);
                
            }
        } catch (Exception e) {
            System.out.println("in checkDetails in class EmployeeAttendance");
            e.printStackTrace();
        }
    }

    //-----------------------Save---------------------
    void saveDetails() {

        try {
            if(txt_outTime.getText().equals("")){
                DB.putData("INSERT INTO attendance (date,in_time,out_time,emp_id) VALUES ('" + new SimpleDateFormat("yyyy-MM-dd").format(DateChooser.getDate()) + "','" + txt_inTime.getText() + "','00:00:00','" + txt_employeeId.getText() + "') ");
                log.info("Employee ID : "+txt_employeeId.getText()+"Employee Attendance marked at :" +new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }else {
                DB.putData(" UPDATE attendance SET out_time='"+txt_outTime.getText()+"' WHERE attendance_id='"+lbl_attendanceId.getText()+"'");
                log.info("Employee ID : "+txt_employeeId.getText()+"Employee Attendance Updated at :" +new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
            
        } catch (Exception e) {
            System.out.println("in save Details in class Employee Attendance");
            e.printStackTrace();
        }
        DateChooser.setEnabled(false);
        txt_inTime.setEditable(false);
        txt_outTime.setEditable(false);
        txt_employeeId.setText("");
        lbl_attendanceId.setText("");
        DateChooser.setDate(null);
        txt_inTime.setText("");
        txt_outTime.setText("");
   }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        popup_menu = new javax.swing.JMenuItem();
        popup_help = new javax.swing.JMenuItem();
        popup_exit = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        pnl_1 = new javax.swing.JPanel();
        btn_close = new javax.swing.JButton();
        btn_menu = new javax.swing.JButton();
        lbl_userType = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnl_2 = new javax.swing.JPanel();
        lbl_attendenceID = new javax.swing.JLabel();
        lbl_inTime = new javax.swing.JLabel();
        lbl_outTime = new javax.swing.JLabel();
        lbl_employeeId = new javax.swing.JLabel();
        lbl_attdate = new javax.swing.JLabel();
        txt_inTime = new javax.swing.JTextField();
        txt_outTime = new javax.swing.JTextField();
        txt_employeeId = new javax.swing.JTextField();
        DateChooser = new com.toedter.calendar.JDateChooser();
        lbl_fee = new javax.swing.JLabel();
        lbl_fee1 = new javax.swing.JLabel();
        lbl_fee2 = new javax.swing.JLabel();
        lbl_fee3 = new javax.swing.JLabel();
        lbl_attendanceId = new javax.swing.JLabel();
        btn_outtimeEdit = new javax.swing.JButton();
        btn_dateEdit = new javax.swing.JButton();
        btn_intimeEdit = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
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
        popup_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popup_exitActionPerformed(evt);
            }
        });
        popupMenu.add(popup_exit);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_1.setBackground(new java.awt.Color(255, 255, 255));
        pnl_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnl_1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_1MouseClicked(evt);
            }
        });
        pnl_1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cancel.png"))); // NOI18N
        btn_close.setContentAreaFilled(false);
        btn_close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });
        pnl_1.add(btn_close, new org.netbeans.lib.awtextra.AbsoluteConstraints(514, 0, 16, 16));

        btn_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu.png"))); // NOI18N
        btn_menu.setContentAreaFilled(false);
        btn_menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_menuActionPerformed(evt);
            }
        });
        pnl_1.add(btn_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 32, 32));

        lbl_userType.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_userType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_userType.setText("User type");
        pnl_1.add(lbl_userType, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 220, -1));

        lbl_username.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lbl_username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_username.setText("Username");
        pnl_1.add(lbl_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 220, -1));

        lbl_time.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_time.setText("jLabel3");
        pnl_1.add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 23, 100, -1));

        lbl_date.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_date.setText("jLabel3");
        pnl_1.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, -1));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Attendance");
        pnl_1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 340, 30));

        jPanel1.add(pnl_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, 50));

        pnl_2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Attendance", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 10), new java.awt.Color(0, 0, 0))); // NOI18N
        pnl_2.setOpaque(false);
        pnl_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_2MouseClicked(evt);
            }
        });
        pnl_2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_attendenceID.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_attendenceID.setText("Attendance ID:");
        pnl_2.add(lbl_attendenceID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        lbl_inTime.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_inTime.setText("In Time:");
        pnl_2.add(lbl_inTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        lbl_outTime.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_outTime.setText("Out time:");
        pnl_2.add(lbl_outTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 80, -1));

        lbl_employeeId.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_employeeId.setText("Employee ID:");
        pnl_2.add(lbl_employeeId, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        lbl_attdate.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_attdate.setText("Date:");
        pnl_2.add(lbl_attdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        txt_inTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_inTimeActionPerformed(evt);
            }
        });
        pnl_2.add(txt_inTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 290, 30));
        pnl_2.add(txt_outTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 290, 30));

        txt_employeeId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_employeeIdActionPerformed(evt);
            }
        });
        txt_employeeId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_employeeIdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_employeeIdFocusLost(evt);
            }
        });
        txt_employeeId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_employeeIdKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_employeeIdKeyReleased(evt);
            }
        });
        pnl_2.add(txt_employeeId, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 300, 30));

        DateChooser.setDateFormatString("yyyy-MM-dd");
        DateChooser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                DateChooserFocusLost(evt);
            }
        });
        pnl_2.add(DateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 290, 30));

        lbl_fee.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        pnl_2.add(lbl_fee, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lbl_fee1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        pnl_2.add(lbl_fee1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lbl_fee2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        pnl_2.add(lbl_fee2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lbl_fee3.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        pnl_2.add(lbl_fee3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lbl_attendanceId.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        pnl_2.add(lbl_attendanceId, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 290, 20));

        btn_outtimeEdit.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_outtimeEdit.setText("Edit");
        btn_outtimeEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_outtimeEditActionPerformed(evt);
            }
        });
        pnl_2.add(btn_outtimeEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, -1, -1));

        btn_dateEdit.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_dateEdit.setText("Edit");
        btn_dateEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dateEditActionPerformed(evt);
            }
        });
        pnl_2.add(btn_dateEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, -1, -1));

        btn_intimeEdit.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_intimeEdit.setText("Edit");
        btn_intimeEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_intimeEditActionPerformed(evt);
            }
        });
        pnl_2.add(btn_intimeEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, -1, -1));

        jPanel1.add(pnl_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 510, 240));

        btn_save.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_save.setText("Save");
        btn_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });
        btn_save.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_saveKeyReleased(evt);
            }
        });
        jPanel1.add(btn_save, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 309, 360, 30));

        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, 360));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 360));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_closeActionPerformed

    private void txt_employeeIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_employeeIdActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employeeIdActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        //save_attendance();
        saveDetails();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void txt_inTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_inTimeActionPerformed
        // TODO add your handling code here: 
    }//GEN-LAST:event_txt_inTimeActionPerformed

    private void txt_employeeIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_employeeIdKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employeeIdKeyReleased

    private void btn_saveKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_saveKeyReleased
     
    }//GEN-LAST:event_btn_saveKeyReleased

    private void DateChooserFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_DateChooserFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_DateChooserFocusLost

    private void txt_employeeIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_employeeIdKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employeeIdKeyPressed

    private void btn_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menuActionPerformed
        Menu m = new Menu();
        m.setVisible(true);
    }//GEN-LAST:event_btn_menuActionPerformed

    private void txt_employeeIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_employeeIdFocusLost
        //generate_id();
        if (!txt_employeeId.getText().equals("")) {
            checkDetails(txt_employeeId.getText());
            txt_outTime.setVisible(true);

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employeeIdFocusLost

    private void txt_employeeIdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_employeeIdFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employeeIdFocusGained

    private void btn_dateEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dateEditActionPerformed
        DateChooser.setEnabled(true);
    }//GEN-LAST:event_btn_dateEditActionPerformed

    private void btn_intimeEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_intimeEditActionPerformed
        txt_inTime.setEditable(true);
    }//GEN-LAST:event_btn_intimeEditActionPerformed

    private void btn_outtimeEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_outtimeEditActionPerformed
        txt_outTime.setEditable(true);
    }//GEN-LAST:event_btn_outtimeEditActionPerformed

    private void popup_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_menuActionPerformed
        this.dispose();
        new Menu().setVisible(true);
    }//GEN-LAST:event_popup_menuActionPerformed

    private void popup_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_helpActionPerformed
         try {
            Desktop.getDesktop().open(new File("E:\\Syndicate\\Documentation\\General – Employee Attendance.pdf"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_popup_helpActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel1MouseClicked

    private void popup_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_exitActionPerformed
        this.dispose();
    }//GEN-LAST:event_popup_exitActionPerformed

    private void pnl_1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_1MouseClicked
       if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_1MouseClicked

    private void pnl_2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_2MouseClicked
       if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_2MouseClicked

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
            java.util.logging.Logger.getLogger(EmployeeAttendence.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeAttendence.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeAttendence.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeAttendence.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeAttendence().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateChooser;
    private javax.swing.JButton btn_close;
    private javax.swing.JButton btn_dateEdit;
    private javax.swing.JButton btn_intimeEdit;
    private javax.swing.JButton btn_menu;
    private javax.swing.JButton btn_outtimeEdit;
    private javax.swing.JButton btn_save;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_attdate;
    private javax.swing.JLabel lbl_attendanceId;
    private javax.swing.JLabel lbl_attendenceID;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_employeeId;
    private javax.swing.JLabel lbl_fee;
    private javax.swing.JLabel lbl_fee1;
    private javax.swing.JLabel lbl_fee2;
    private javax.swing.JLabel lbl_fee3;
    private javax.swing.JLabel lbl_inTime;
    private javax.swing.JLabel lbl_outTime;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_userType;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_1;
    private javax.swing.JPanel pnl_2;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem popup_exit;
    private javax.swing.JMenuItem popup_help;
    private javax.swing.JMenuItem popup_menu;
    private javax.swing.JTextField txt_employeeId;
    private javax.swing.JTextField txt_inTime;
    private javax.swing.JTextField txt_outTime;
    // End of variables declaration//GEN-END:variables

}
