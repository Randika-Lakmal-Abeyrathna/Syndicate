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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author USER
 */
public class Leaves extends javax.swing.JFrame {

    /**
     * Creates new form ClassDetails
     */
    ToolsClass tool = new ToolsClass();
    JDBC DB = new JDBC();
    Logger log=Logger.getLogger("Leaves details");
    public Leaves() {
        initComponents();
        Menu();
        new Classes.Commons().background(this, lbl_background);
        dc_setDate.setMinSelectableDate(new Date());
        lbl_username.setText(Home.un);
        lbl_userType.setText(Home.ut);
        lbl_date.setText(new Classes.Commons().Date());
        new Classes.Commons().time(lbl_time);
        txt_employeeId.setText("EMP");
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

        popupMenu = new javax.swing.JPopupMenu();
        popup_menu = new javax.swing.JMenuItem();
        popup_help = new javax.swing.JMenuItem();
        popup_exit = new javax.swing.JMenuItem();
        pnl_background = new javax.swing.JPanel();
        pnl_header = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        lbl_userType = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnl_classTimetable = new javax.swing.JPanel();
        lbl_availableLeaves = new javax.swing.JLabel();
        lbl_leavesDate = new javax.swing.JLabel();
        lbl_name = new javax.swing.JLabel();
        txt_numberOfLeaves = new javax.swing.JTextField();
        lbl_numberOfLeaves = new javax.swing.JLabel();
        txt_employeeId = new javax.swing.JTextField();
        lbl_employeeId = new javax.swing.JLabel();
        dc_setDate = new com.toedter.calendar.JDateChooser();
        lbl_valueOfAvailableLeves = new javax.swing.JLabel();
        lbl_employeeName = new javax.swing.JLabel();
        pnl_table = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txta_discription = new javax.swing.JTextArea();
        lbl_availableLeaves1 = new javax.swing.JLabel();
        btn_add = new javax.swing.JButton();
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

        pnl_background.setBackground(new java.awt.Color(255, 255, 255));
        pnl_background.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_backgroundMouseClicked(evt);
            }
        });
        pnl_background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_header.setBackground(new java.awt.Color(255, 255, 255));
        pnl_header.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnl_header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_headerMouseClicked(evt);
            }
        });
        pnl_header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cancel.png"))); // NOI18N
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        pnl_header.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 16, 16));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu.png"))); // NOI18N
        jButton6.setContentAreaFilled(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        pnl_header.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 9, 32, 32));

        lbl_userType.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_userType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_userType.setText("User type");
        pnl_header.add(lbl_userType, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 220, -1));

        lbl_username.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lbl_username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_username.setText("Username");
        pnl_header.add(lbl_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 220, -1));

        lbl_time.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_time.setText("jLabel3");
        pnl_header.add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 23, 100, -1));

        lbl_date.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_date.setText("jLabel3");
        pnl_header.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, -1));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Leaves");
        pnl_header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 340, 30));

        pnl_background.add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 50));

        pnl_classTimetable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Leaves", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 10), new java.awt.Color(0, 0, 0))); // NOI18N
        pnl_classTimetable.setOpaque(false);
        pnl_classTimetable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_classTimetableMouseClicked(evt);
            }
        });

        lbl_availableLeaves.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_availableLeaves.setText("Available Leaves:");

        lbl_leavesDate.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_leavesDate.setText("Date:");

        lbl_name.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_name.setText("Employee Name:");

        txt_numberOfLeaves.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        txt_numberOfLeaves.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_numberOfLeavesFocusLost(evt);
            }
        });
        txt_numberOfLeaves.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numberOfLeavesKeyTyped(evt);
            }
        });

        lbl_numberOfLeaves.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_numberOfLeaves.setText("Number of Leaves:");

        txt_employeeId.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_employeeId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_employeeIdFocusLost(evt);
            }
        });

        lbl_employeeId.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_employeeId.setText("Employee ID:");

        dc_setDate.setDateFormatString("yyyy-MM-dd");
        dc_setDate.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N

        lbl_valueOfAvailableLeves.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N

        lbl_employeeName.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N

        javax.swing.GroupLayout pnl_classTimetableLayout = new javax.swing.GroupLayout(pnl_classTimetable);
        pnl_classTimetable.setLayout(pnl_classTimetableLayout);
        pnl_classTimetableLayout.setHorizontalGroup(
            pnl_classTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_classTimetableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_classTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_leavesDate)
                    .addComponent(lbl_availableLeaves)
                    .addComponent(lbl_name)
                    .addComponent(lbl_numberOfLeaves)
                    .addComponent(lbl_employeeId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_classTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_employeeId)
                    .addComponent(lbl_valueOfAvailableLeves, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_employeeName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_numberOfLeaves)
                    .addComponent(dc_setDate, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_classTimetableLayout.setVerticalGroup(
            pnl_classTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_classTimetableLayout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(pnl_classTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_employeeId, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_employeeId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_classTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_name)
                    .addComponent(lbl_employeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_classTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_availableLeaves)
                    .addComponent(lbl_valueOfAvailableLeves, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(pnl_classTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_numberOfLeaves, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_numberOfLeaves))
                .addGroup(pnl_classTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_classTimetableLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lbl_leavesDate)
                        .addGap(73, 73, 73))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_classTimetableLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dc_setDate, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64))))
        );

        pnl_background.add(pnl_classTimetable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 67, 420, 320));

        pnl_table.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Leave Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 10))); // NOI18N
        pnl_table.setOpaque(false);

        txta_discription.setColumns(20);
        txta_discription.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        txta_discription.setRows(5);
        jScrollPane2.setViewportView(txta_discription);

        lbl_availableLeaves1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_availableLeaves1.setText("Description :");
        lbl_availableLeaves1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_availableLeaves1MouseClicked(evt);
            }
        });

        btn_add.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_add.setText("Add");
        btn_add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_tableLayout = new javax.swing.GroupLayout(pnl_table);
        pnl_table.setLayout(pnl_tableLayout);
        pnl_tableLayout.setHorizontalGroup(
            pnl_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_tableLayout.createSequentialGroup()
                .addGroup(pnl_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_tableLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_availableLeaves1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_tableLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_tableLayout.setVerticalGroup(
            pnl_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_tableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_availableLeaves1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btn_add)
                .addGap(39, 39, 39))
        );

        pnl_background.add(pnl_table, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, 440, 310));

        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnl_background.add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 390));

        getContentPane().add(pnl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        if (!(txt_employeeId.getText().equals("") & txt_numberOfLeaves.getText().equals("") & dc_setDate.getDate().equals("") & txta_discription.getText().equals(""))) {
            String Date = new SimpleDateFormat("yyyy-MM-dd").format(dc_setDate.getDate());
            int leaves = 0;
            try {
                ResultSet rs = DB.getData("SELECT available_leaves FROM employee_reg WHERE emp_id='" + txt_employeeId.getText() + "'");
                while (rs.next()) {
                    leaves = Integer.parseInt(rs.getString("available_leaves"));
                }
                leaves = leaves - Integer.parseInt(txt_numberOfLeaves.getText());
                DB.putData("UPDATE employee_reg SET available_leaves='" + leaves + "' WHERE emp_id='" + txt_employeeId.getText() + "'");
                DB.putData("INSERT INTO leaves(taken_leaves,date,description,emp_id) values('" + Integer.parseInt(txt_numberOfLeaves.getText()) + "','" + Date + "','" + txta_discription.getText() + "','" + txt_employeeId.getText() + "')");
                
                log.info("Leaves Add to Employee"+txt_employeeId.getText()+"Leaves Added at :" +new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                        
                tool.clearComponets(txt_employeeId, lbl_employeeName, lbl_valueOfAvailableLeves, txt_numberOfLeaves, dc_setDate, txta_discription);
            } catch (Exception e) {
                System.out.println("in add btn in class leaves");
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_btn_addActionPerformed

    private void txt_employeeIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_employeeIdFocusLost
        if (!"".equals(txt_employeeId.getText())) {
            try {
                ResultSet rs = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + txt_employeeId.getText() + "'");
                while (rs.next()) {
                    String fname = rs.getString("f_name");
                    String lname = rs.getString("l_name");
                    lbl_employeeName.setText(fname + " " + lname);
                    lbl_valueOfAvailableLeves.setText(rs.getString("available_leaves"));
                }
            } catch (Exception e) {
                System.out.println("in txt_employeeId focus lost in class Leaves");
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(this, "Enter Employee ID");
        }

    }//GEN-LAST:event_txt_employeeIdFocusLost

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        new Menu().setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txt_numberOfLeavesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_numberOfLeavesFocusLost
        if (!("".equals(txt_numberOfLeaves.getText()) | "".equals(lbl_valueOfAvailableLeves.getText()))) {
            int available = Integer.parseInt(lbl_valueOfAvailableLeves.getText());
            int value = Integer.parseInt(txt_numberOfLeaves.getText());
            if (value > available) {
                JOptionPane.showMessageDialog(this, "Check The amount of Leaves");
                txt_numberOfLeaves.grabFocus();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Enter A Value");
            txt_employeeId.grabFocus();
        }
    }//GEN-LAST:event_txt_numberOfLeavesFocusLost

    private void txt_numberOfLeavesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numberOfLeavesKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_numberOfLeavesKeyTyped

    private void popup_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_exitActionPerformed
        this.dispose();
    }//GEN-LAST:event_popup_exitActionPerformed

    private void popup_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_helpActionPerformed
      try {
            Desktop.getDesktop().open(new File("E:\\Syndicate\\Documentation\\General – Leaves.pdf"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_popup_helpActionPerformed

    private void popup_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_menuActionPerformed
        this.dispose();
        new Menu().setVisible(true);
    }//GEN-LAST:event_popup_menuActionPerformed

    private void pnl_backgroundMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_backgroundMouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_backgroundMouseClicked

    private void pnl_headerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_headerMouseClicked
      if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_headerMouseClicked

    private void pnl_classTimetableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_classTimetableMouseClicked
       if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_classTimetableMouseClicked

    private void lbl_availableLeaves1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_availableLeaves1MouseClicked
       if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_lbl_availableLeaves1MouseClicked

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
            java.util.logging.Logger.getLogger(Leaves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Leaves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Leaves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Leaves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Leaves().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private com.toedter.calendar.JDateChooser dc_setDate;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_availableLeaves;
    private javax.swing.JLabel lbl_availableLeaves1;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_employeeId;
    private javax.swing.JLabel lbl_employeeName;
    private javax.swing.JLabel lbl_leavesDate;
    private javax.swing.JLabel lbl_name;
    private javax.swing.JLabel lbl_numberOfLeaves;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_userType;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JLabel lbl_valueOfAvailableLeves;
    private javax.swing.JPanel pnl_background;
    private javax.swing.JPanel pnl_classTimetable;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_table;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem popup_exit;
    private javax.swing.JMenuItem popup_help;
    private javax.swing.JMenuItem popup_menu;
    private javax.swing.JTextField txt_employeeId;
    private javax.swing.JTextField txt_numberOfLeaves;
    private javax.swing.JTextArea txta_discription;
    // End of variables declaration//GEN-END:variables
}
