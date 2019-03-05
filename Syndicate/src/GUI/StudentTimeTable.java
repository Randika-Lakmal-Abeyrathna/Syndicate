/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.JDBC;
import Classes.ToolsClass;
import java.awt.Desktop;
import java.io.File;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Vidyani
 */
public class StudentTimeTable extends javax.swing.JFrame {

    /**
     * Creates new form StudentTimeTable
     */
    public StudentTimeTable() {
        initComponents();
        Menu();
        loadTeacherNameToCombo();
        lbl_studentID.setText(studentId);
        lbl_username.setText(userName);
        lbl_userType.setText(userType);
        lbl_date.setText(new Classes.Commons().Date());
        new Classes.Commons().time(lbl_time);
    }
    JDBC DB = new JDBC();
    Classes.ToolsClass tool = new ToolsClass();
    StudentRegistration st = new StudentRegistration();
    static String studentId = StudentRegistration.studentID;
    static String userName = StudentRegistration.userName;
    static String userType = StudentRegistration.userType;
    
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

    ///// set teacher name to combo////
    void loadTeacherNameToCombo() {
        try {
            Vector v = new Vector();
            ResultSet rs = DB.getData("SELECT * FROM teacher_reg WHERE status = '0'");
            while (rs.next()) {
                String teacherID = rs.getString("teacher_id");
                String fname = rs.getString("f_name");
                String lname = rs.getString("l_name");
                String fullName = teacherID + "-" + fname + " " + lname;
                v.add(fullName);
            }
            cmb_teacher.removeAllItems();
            for (int i = 0; i < v.size(); i++) {
                cmb_teacher.addItem(v.get(i));
            }

        } catch (Exception ex) {
            Logger.getLogger(StudentTimeTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /////  load subject to combo box////
    void loadSubjectToCombo() {
        String teacherId = cmb_teacher.getSelectedItem().toString().split("-")[0];
        String classType = "";
        String subject = "";
        String medium = "";
        Vector v = new Vector();
        try {
            ResultSet rs = DB.getData("SELECT * FROM class_details WHERE teacher_id='" + teacherId + "'");
            while (rs.next()) {
                ResultSet rs1 = DB.getData("SELECT * FROM class_type WHERE class_type_id='" + rs.getString("class_type_id") + "'");
                while (rs1.next()) {
                    classType = rs1.getString("class_type");
                }
                ResultSet rs2 = DB.getData("SELECT * FROM subject WHERE subject_id='" + rs.getString("subject_id") + "'");
                while (rs2.next()) {
                    subject = rs2.getString("subject_name");
                    ResultSet rs3 = DB.getData("SELECT * FROM medium WHERE medium_id='" + rs2.getString("medium_id") + "'");
                    while (rs3.next()) {
                        medium = rs3.getString("medium");
                    }
                }
                String subjectAll = subject + "-" + medium + "-" + classType;
                v.add(subjectAll);
            }
            cmb_subject.removeAllItems();
            for (int i = 0; i < v.size(); i++) {
                cmb_subject.addItem(v.get(i));
            }
        } catch (Exception ex) {
            Logger.getLogger(StudentTimeTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ////// add dta to table//////
    void saveToDB() {
        try {

            String studentId = lbl_studentID.getText();
            String teacherId = cmb_teacher.getSelectedItem().toString().split("-")[0];
            String teacherName = cmb_teacher.getSelectedItem().toString().split("-")[1];
            String grade = cmb_subject.getSelectedItem().toString().split("-")[0];
            String subject = cmb_subject.getSelectedItem().toString().split("-")[1];
            String medium = cmb_subject.getSelectedItem().toString().split("-")[2];
            String type = cmb_subject.getSelectedItem().toString().split("-")[3];
            String subjectFull = grade + "-" + subject;

            String mediumID = "";
            String subjectID = "";
            String classTypeID = "";
            String classID = "";
            String classFee = "";
            ResultSet rs = DB.getData("SELECT * FROM medium WHERE medium='" + medium + "'");
            while (rs.next()) {
                mediumID = rs.getString("medium_id");
            }
            ResultSet rs1 = DB.getData("SELECT * FROM subject WHERE subject_name ='" + subjectFull + "' AND medium_id='" + mediumID + "'");
            while (rs1.next()) {
                subjectID = rs1.getString("subject_id");
            }
            ResultSet rs2 = DB.getData("SELECT * FROM class_type WHERE class_type = '" + type + "'");
            while (rs2.next()) {
                classTypeID = rs2.getString("class_type_id");
            }
            ResultSet rs4 = DB.getData("SELECT * FROM class_details WHERE class_type_id='" + classTypeID + "' AND subject_id='" + subjectID + "' AND teacher_id = '" + teacherId + "'");
            while (rs4.next()) {
                classID = rs4.getString("class_id");
            }
            ResultSet rs5 = DB.getData("SELECT * FROM teacher_time_table WHERE class_type_id='" + classTypeID + "' AND subject_id='" + subjectID + "' AND teacher_id = '" + teacherId + "'");
            while (rs5.next()) {
                classFee = rs4.getString("class_fee");
            }
            tool.addToTable(tbl_studentTimeTable, teacherName, subjectFull, medium, type, classFee);

            /////// save to DB-----------------------------
            DB.putData("INSERT INTO student_timetable VALUES('" + classID + "','" + studentId + "')");

        } catch (Exception ex) {
            Logger.getLogger(StudentTimeTable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //// load data from table--------
    void loadDataFromTable() {
        cmb_subject.removeAllItems();
        cmb_teacher.removeAllItems();
        loadTeacherNameToCombo();
        DefaultTableModel dft = (DefaultTableModel) tbl_studentTimeTable.getModel();
        int sr = tbl_studentTimeTable.getSelectedRow();
        String teacherName = dft.getValueAt(sr, 0).toString();
        String subject = dft.getValueAt(sr, 1).toString();
        String medium = dft.getValueAt(sr, 2).toString();
        String classType = dft.getValueAt(sr, 3).toString();
        
        String fName = teacherName.split(" ")[0];
        String lName = teacherName.split(" ")[1];
        String teacherID = "";
        try {
            Vector v = new Vector();
            Vector v1 = new Vector();
                
            ResultSet rs = DB.getData("SELECT teacher_id FROM teacher_reg WHERE f_name='"+fName+"' AND l_name = '"+lName+"'");
            while (rs.next()) {                
                teacherID = rs.getString("teacher_id");
                String teacherData = teacherID+"-"+fName+" "+lName;
                v.add(teacherData);
            }
            cmb_subject.removeAllItems();
            cmb_teacher.removeAllItems();
            for (int i = 0; i < v.size(); i++) {
                cmb_teacher.addItem(v.get(i));
            }
            for (int i = 0; i < v1.size(); i++) {
                cmb_subject.addItem(v1.get(i));
            }
            
            String mediumID = "";
            String subjectID = "";
            String classTypeID = "";
            String classID = "";
            String classFee = "";
            ResultSet rs1 = DB.getData("SELECT * FROM medium WHERE medium='" + medium + "'");
            while (rs1.next()) {
                mediumID = rs1.getString("medium_id");
            }
            ResultSet rs2 = DB.getData("SELECT * FROM subject WHERE subject_name ='" + subject + "' AND medium_id='" + mediumID + "'");
            while (rs2.next()) {
                subjectID = rs2.getString("subject_id");
            }
            ResultSet rs3 = DB.getData("SELECT * FROM class_type WHERE class_type = '" + classType + "'");
            while (rs3.next()) {
                classTypeID = rs3.getString("class_type_id");
            }
            
        } catch (Exception ex) {
            Logger.getLogger(StudentTimeTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        popup_menu = new javax.swing.JMenuItem();
        popup_help = new javax.swing.JMenuItem();
        popup_exit = new javax.swing.JMenuItem();
        pnl_UserRegDetails = new javax.swing.JPanel();
        lbl_labelStudentID = new javax.swing.JLabel();
        lbl_subject = new javax.swing.JLabel();
        lbl_teacher = new javax.swing.JLabel();
        lbl_studentID = new javax.swing.JLabel();
        cmb_subject = new javax.swing.JComboBox();
        cmb_teacher = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_studentTimeTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        pnl_UsrRegheader = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btn_menu = new javax.swing.JButton();
        lbl_userType = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
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

        pnl_UserRegDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Set Class to Student", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        pnl_UserRegDetails.setOpaque(false);
        pnl_UserRegDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_UserRegDetailsMouseClicked(evt);
            }
        });

        lbl_labelStudentID.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_labelStudentID.setText("Student ID :");

        lbl_subject.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_subject.setText("Subject :");

        lbl_teacher.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_teacher.setText("Teacher :");

        lbl_studentID.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        cmb_subject.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        cmb_teacher.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_teacher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_teacherActionPerformed(evt);
            }
        });

        tbl_studentTimeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Teacher Name", "Subject", "Medium", "Class type", "Class Fee"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_studentTimeTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_studentTimeTable);

        jButton2.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jButton3.setText("Update");

        jButton4.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_UserRegDetailsLayout = new javax.swing.GroupLayout(pnl_UserRegDetails);
        pnl_UserRegDetails.setLayout(pnl_UserRegDetailsLayout);
        pnl_UserRegDetailsLayout.setHorizontalGroup(
            pnl_UserRegDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_UserRegDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_UserRegDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                    .addGroup(pnl_UserRegDetailsLayout.createSequentialGroup()
                        .addGroup(pnl_UserRegDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_labelStudentID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_subject)
                            .addComponent(lbl_teacher)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_UserRegDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_UserRegDetailsLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmb_subject, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmb_teacher, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_studentID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_UserRegDetailsLayout.setVerticalGroup(
            pnl_UserRegDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_UserRegDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_UserRegDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_labelStudentID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_studentID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(pnl_UserRegDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_teacher)
                    .addComponent(cmb_teacher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_UserRegDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_subject)
                    .addComponent(cmb_subject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_UserRegDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(pnl_UserRegDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 610, 390));

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
        pnl_UsrRegheader.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 16, 16));

        btn_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu.png"))); // NOI18N
        btn_menu.setContentAreaFilled(false);
        btn_menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnl_UsrRegheader.add(btn_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 9, 32, 32));

        lbl_userType.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_userType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_userType.setText("User Type");
        pnl_UsrRegheader.add(lbl_userType, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, 220, -1));

        lbl_username.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lbl_username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_username.setText("Username");
        pnl_UsrRegheader.add(lbl_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 220, -1));

        lbl_date.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_date.setText("jLabel3");
        pnl_UsrRegheader.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, -1));

        lbl_time.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_time.setText("jLabel3");
        pnl_UsrRegheader.add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 23, 100, -1));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Student Time Table");
        pnl_UsrRegheader.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 250, 30));

        getContentPane().add(pnl_UsrRegheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 50));

        lbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N
        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 440));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        tool.clearTable(tbl_studentTimeTable);
        saveToDB();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cmb_teacherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_teacherActionPerformed
        loadSubjectToCombo();
    }//GEN-LAST:event_cmb_teacherActionPerformed

    private void popup_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_menuActionPerformed
       this.dispose();
       new Menu().setVisible(true);
    }//GEN-LAST:event_popup_menuActionPerformed

    private void popup_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_helpActionPerformed
        try {
            Desktop.getDesktop().open(new File("E:\\Syndicate\\Documentation\\General – Student Time Table.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_popup_helpActionPerformed

    private void popup_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_exitActionPerformed
     this.dispose();
    }//GEN-LAST:event_popup_exitActionPerformed

    private void pnl_UserRegDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_UserRegDetailsMouseClicked
     if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_UserRegDetailsMouseClicked

    private void pnl_UsrRegheaderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_UsrRegheaderMouseClicked
      if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_UsrRegheaderMouseClicked

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
            java.util.logging.Logger.getLogger(StudentTimeTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentTimeTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentTimeTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentTimeTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentTimeTable().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_menu;
    private javax.swing.JComboBox cmb_subject;
    private javax.swing.JComboBox cmb_teacher;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_labelStudentID;
    private javax.swing.JLabel lbl_studentID;
    private javax.swing.JLabel lbl_subject;
    private javax.swing.JLabel lbl_teacher;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_userType;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_UserRegDetails;
    private javax.swing.JPanel pnl_UsrRegheader;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem popup_exit;
    private javax.swing.JMenuItem popup_help;
    private javax.swing.JMenuItem popup_menu;
    private javax.swing.JTable tbl_studentTimeTable;
    // End of variables declaration//GEN-END:variables
}
