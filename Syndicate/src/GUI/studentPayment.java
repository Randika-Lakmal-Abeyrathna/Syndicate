/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.JDBC;
import Classes.ToolsClass;
import static GUI.RegistrationFee.studentid;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;

/**
 *
 * @author Vidyani
 */
public class studentPayment extends javax.swing.JFrame {

    JDBC DB = new JDBC();
    Logger log = Logger.getLogger("Student Payment");

    public studentPayment() {
        initComponents();
        lbl_username.setText(Home.un);
        lbl_userType.setText(Home.ut);
        lbl_date.setText(new Classes.Commons().Date());
        new Classes.Commons().time(lbl_time);
        rad_free.setSelected(false);
        rad_regular.setSelected(false);
        cmb_teacher.setEnabled(false);
        cmb_subject.setEnabled(false);
        dc_date.setMaxSelectableDate(new Date());
        txt_studentId.grabFocus();
        txt_studentId.setText("STU");
        set_date();
        GenerateInvoice();

    }

    public String class_fee;
    public String invoiceid = "";

    ToolsClass tcl = new ToolsClass();

    //-- - - - - -- -  - -  - - - - set Date
    void set_date() {

        try {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.format(d);

            dc_date.setDate(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //-- - - - - - - - - - - - - - - - -Get teacher name
    void teacher_name() {

        try {
            Vector v = new Vector();
            Vector v1 = new Vector();
            String classType= "";
            
            ResultSet rs = DB.getData("SELECT * FROM student_timetable WHERE student_id ='" + txt_studentId.getText() + "'");
            while (rs.next()) {
                ResultSet rs1 = DB.getData("SELECT * FROM class_details where class_id = '" + rs.getString("class_id") + "'");
                while (rs1.next()) {

                    ResultSet rs2 = DB.getData("SELECT * FROM teacher_reg WHERE teacher_id='" + rs1.getString("teacher_id") + "'");
                    while (rs2.next()) {
                        String teacherName = rs2.getString("teacher_id") + "-" + rs2.getString("f_name") + " " + rs2.getString("l_name");
                        v.add(teacherName);

                    }
                    ResultSet rs5 = DB.getData("SELECT * FROM teacher_time_table WHERE teacher_id='"+rs1.getString("teacher_id")+"' AND subject_id='"+rs1.getString("subject_id")+"'");
                    while (rs5.next()) {                        
                        String clastype = rs5.getString("class_type_id");
                        ResultSet rs6 = DB.getData("SELECT * FROM class_type WHERE class_type_id='"+clastype+"'");
                        while (rs6.next()) {                            
                            classType = rs6.getString("class_type");
                        }
                    }

                    ResultSet rs3 = DB.getData("SELECT * FROM subject WHERE subject_id = '" + rs1.getString("subject_id") + "'");
                    while (rs3.next()) {
                        ResultSet rs4 = DB.getData("SELECT medium FROM medium WHERE medium_id = '" + rs3.getString("medium_id") + "'");
                        while (rs4.next()) {

                            String setSubject = rs3.getString("subject_name") + "-" + rs4.getString("medium")+"-"+classType;
                            v1.add(setSubject);
                        }
                    }
                }
            }
            cmb_teacher.removeAllItems();
            cmb_subject.removeAllItems();
            for (int i = 0; i < v.size(); i++) {
                cmb_teacher.addItem(v.get(i));
            }
            for (int i = 0; i < v1.size(); i++) {
                cmb_subject.addItem(v1.get(i));
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(studentPayment.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//- - - - - - - - - - - - - - - - - - - - - - - - - -Class fee Loading
    void fee() {
        if (cmb_teacher.getItemCount() > 0 && cmb_subject.getItemCount() > 0) {
            String teacherID = cmb_teacher.getSelectedItem().toString().split("-")[0];
            String grade = cmb_subject.getSelectedItem().toString().split("-")[0];
            String subject = cmb_subject.getSelectedItem().toString().split("-")[1];

            String medium = cmb_subject.getSelectedItem().toString().split("-")[2];
            String fullSub = grade + "-" + subject;

            String subjectID = "";
            String mediumID = "";

            try {
                ResultSet rs = DB.getData("SELECT * FROM medium WHERE medium = '" + medium + "'");
                while (rs.next()) {
                    mediumID = rs.getString("medium_id");
                }

                ResultSet rs1 = DB.getData("SELECT * FROM subject WHERE subject_name='" + fullSub + "' AND medium_id='" + mediumID + "'");
                while (rs1.next()) {
                    subjectID = rs1.getString("subject_id");
                }
                ResultSet rs2 = DB.getData("SELECT * FROM teacher_time_table WHERE teacher_id = '" + teacherID + "' AND subject_id = '" + subjectID + "'");
                while (rs2.next()) {
                    String fee = rs2.getString("class_fee");
                    lbl_fee.setText(fee+".00");
                }

            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(studentPayment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

//- - - - - - - - - - - - - - - -  -  -  -  - -Save Payments 
    String student_invoice_id;

    void GenerateInvoice() {
        if (!(txt_studentId.getText().equals("") || dc_date.getDate().equals(null))) {
            try {
                student_invoice_id = tcl.invoiceId();

                String amount = lbl_fee.getText();
                String stat = "";
                String student_id = txt_studentId.getText();

                String class_type = "";

                String time = "";
                String admin = "";

                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.format(d);
                String date = new SimpleDateFormat("yyyy-MM-dd").format(dc_date.getDate());

                Date d1 = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("HH-mm-ss");
                sf.format(d1);
                time = d1.toString().substring(11, 19);
                //////combo box-----------------
                
                if (cmb_teacher.getItemCount()>0 && cmb_subject.getItemCount()>0) {
                    
                String class_id = "";
                String teacher_id = cmb_teacher.getSelectedItem().toString().split("-")[0];
                String grade = cmb_subject.getSelectedItem().toString().split("-")[0];
                String sub = cmb_subject.getSelectedItem().toString().split("-")[1];
                String medi = cmb_subject.getSelectedItem().toString().split("-")[2];
                String classType = cmb_subject.getSelectedItem().toString().split("-")[3];
                String fullSub = grade+"-"+sub;
                
                String Medium ="";
                String Subject = "";
                String classTypeID ="";
                    
                    System.out.println("Subject = " + fullSub);
                    
                if (rad_free.isSelected()) {
                    stat = "free";
                } else if (rad_regular.isSelected()) {
                    stat = "regul";
                }
                
                ResultSet rs = DB.getData("SELECT * FROM medium WHERE medium='"+medi+"'");
                while (rs.next()) {                    
                    Medium = rs.getString("medium_id");
                    System.out.println("medium = " + Medium);
                }
                ResultSet rs1 = DB.getData("SELECT * FROM subject WHERE medium_id='"+Medium+"' AND subject_name='"+fullSub+"'");
                while (rs1.next()) {                    
                    Subject = rs1.getString("subject_id");
                    System.out.println("rs1 = " +Subject);
                }
                ResultSet rs3 = DB.getData("SELECT class_type_id FROM class_type WHERE class_type = '"+classType+"'");
                    while (rs3.next()) {                        
                        classTypeID = rs3.getString("class_type_id");
                    }
                ResultSet rs2 = DB.getData("SELECT  * FROM class_details WHERE subject_id='"+Subject+"' AND teacher_id='"+teacher_id+"' AND class_type_id='"+classTypeID+"'");
                while (rs2.next()) {
                        
                    class_id = rs2.getString("class_id");
                    System.out.println(class_id);
                        String user = lbl_username.getText();
                        ResultSet rs5 = DB.getData("SELECT emp_id FROM login WHERE user_name='" + user + "'");
                        while (rs5.next()) {
                        
                                String employeeId = rs5.getString("emp_id");
                            
                            DB.putData("INSERT INTO  student_payment (month,amount,payment_status,class_id) VALUES('" + date + "','" + amount + "','" + stat + "','" + class_id + "')");
                            JOptionPane.showMessageDialog(rootPane, "saved!!");
                            System.out.println("Right!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                            //- - - - - - - - - - - - - - -student_payment_id
                            ResultSet rs6 = DB.getData("SELECT MAX(student_payment_id)AS payment_id FROM student_payment");
                            while (rs6.next()) {
                                String studentPaymentId = rs6.getString("payment_id");

                                DB.putData("INSERT INTO student_payment_invoice(student_invoice_id,date,amount,type,student_payment_id,student_id)VALUES('" + student_invoice_id + "','" + date + "','" + amount + "','class','" + studentPaymentId + "','" + student_id + "')");

                                DB.putData("INSERT INTO income(date,time,amount,emp_id,student_invoice_id)VALUES('" + date + "','" + time + "','" + amount + "','" + employeeId + "','" + student_invoice_id + "')");

                                log.info("student payment at :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

                            }
                        }

                    
                }
                }

            } catch (Exception e) {
                System.out.println("in GenerateInvoice method in class student Payment");
                e.printStackTrace();
            }
        }

    }

    //- - - - - -- - - - - - - - void clear fields
    void clear_fields() {
        try {
            txt_studentId.setText("STU");
            cmb_teacher.removeAllItems();
            cmb_subject.removeAllItems();
            lbl_fee.setText("");

            String date = new SimpleDateFormat("yyyy-MM-dd").format(dc_date.getDate());

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

        btn_group_fee = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        btn_generateInvoice = new javax.swing.JButton();
        pnl_header = new javax.swing.JPanel();
        btn_close = new javax.swing.JButton();
        btn_menu = new javax.swing.JButton();
        lbl_userType = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_studentId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmb_subject = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        lbl_fee = new javax.swing.JLabel();
        cmb_teacher = new javax.swing.JComboBox();
        dc_date = new com.toedter.calendar.JDateChooser();
        lbl_paymentDate = new javax.swing.JLabel();
        lbl_student_invoice_id = new javax.swing.JLabel();
        rad_free = new javax.swing.JRadioButton();
        rad_regular = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        lbl_background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_generateInvoice.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_generateInvoice.setText("Generate Invoice");
        btn_generateInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_generateInvoiceMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btn_generateInvoiceMouseReleased(evt);
            }
        });
        btn_generateInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generateInvoiceActionPerformed(evt);
            }
        });
        btn_generateInvoice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btn_generateInvoiceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btn_generateInvoiceFocusLost(evt);
            }
        });
        btn_generateInvoice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_generateInvoiceKeyReleased(evt);
            }
        });
        jPanel1.add(btn_generateInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 320, 140, -1));

        pnl_header.setBackground(new java.awt.Color(255, 255, 255));
        pnl_header.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnl_header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cancel.png"))); // NOI18N
        btn_close.setContentAreaFilled(false);
        btn_close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });
        pnl_header.add(btn_close, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 20, 16));

        btn_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu.png"))); // NOI18N
        btn_menu.setContentAreaFilled(false);
        btn_menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_menuActionPerformed(evt);
            }
        });
        pnl_header.add(btn_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 9, 32, 32));

        lbl_userType.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_userType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_userType.setText("User type");
        pnl_header.add(lbl_userType, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 150, -1));

        lbl_username.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lbl_username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_username.setText("Username");
        pnl_header.add(lbl_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 220, -1));

        lbl_date.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_date.setText("jLabel3");
        pnl_header.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, -1));

        lbl_time.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_time.setText("jLabel3");
        pnl_header.add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 23, 100, -1));

        jLabel6.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel6.setText("Student Payments");
        pnl_header.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 340, 30));

        jPanel1.add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 50));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student Payments", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel2.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel1.setText("Student ID :");

        txt_studentId.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txt_studentId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_studentIdActionPerformed(evt);
            }
        });
        txt_studentId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_studentIdFocusLost(evt);
            }
        });
        txt_studentId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_studentIdKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_studentIdKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel2.setText("Teacher Name :");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel3.setText("Subject :");

        cmb_subject.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_subject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_subjectActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel4.setText("Fee :");

        lbl_fee.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        cmb_teacher.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_teacher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_teacherActionPerformed(evt);
            }
        });

        dc_date.setDateFormatString("yyyy-MM-dd");
        dc_date.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        lbl_paymentDate.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_paymentDate.setText("Date :");

        btn_group_fee.add(rad_free);
        rad_free.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        rad_free.setText("Free");
        rad_free.setContentAreaFilled(false);
        rad_free.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rad_freeActionPerformed(evt);
            }
        });

        btn_group_fee.add(rad_regular);
        rad_regular.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        rad_regular.setText("Regular");
        rad_regular.setContentAreaFilled(false);
        rad_regular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rad_regularMouseClicked(evt);
            }
        });
        rad_regular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rad_regularActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel5.setText("Payment Type :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(lbl_paymentDate)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(dc_date, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_studentId, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_teacher, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_subject, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_fee, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rad_regular)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rad_free)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(lbl_student_invoice_id, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_studentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(lbl_student_invoice_id, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_teacher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_subject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(rad_regular)
                            .addComponent(rad_free))))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_fee, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dc_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_paymentDate))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 540, 260));

        lbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N
        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 350));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 350));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_closeActionPerformed

    private void btn_generateInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generateInvoiceActionPerformed
        // TODO add your handling code here:
        GenerateInvoice();
        genarateReport();
        clear_fields();
    }//GEN-LAST:event_btn_generateInvoiceActionPerformed

    private void cmb_teacherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_teacherActionPerformed

        lbl_fee.setText("");
    }//GEN-LAST:event_cmb_teacherActionPerformed

    private void btn_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menuActionPerformed
        Menu m = new Menu();
        m.setVisible(true);
    }//GEN-LAST:event_btn_menuActionPerformed

    private void cmb_subjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_subjectActionPerformed

        lbl_fee.setText("");
    }//GEN-LAST:event_cmb_subjectActionPerformed

    private void btn_generateInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_generateInvoiceMouseClicked
        //TODO add your handling code here:
    }//GEN-LAST:event_btn_generateInvoiceMouseClicked

    private void txt_studentIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_studentIdKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentIdKeyTyped

    private void txt_studentIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_studentIdKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentIdKeyReleased

    private void txt_studentIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_studentIdFocusLost

        cmb_teacher.setEnabled(true);
        cmb_subject.setEnabled(true);
        teacher_name();
        //subject();

    }//GEN-LAST:event_txt_studentIdFocusLost

    private void txt_studentIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_studentIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentIdActionPerformed

    private void btn_generateInvoiceMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_generateInvoiceMouseReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_btn_generateInvoiceMouseReleased

    private void btn_generateInvoiceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btn_generateInvoiceFocusLost

    }//GEN-LAST:event_btn_generateInvoiceFocusLost

    private void rad_regularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rad_regularActionPerformed
        if (rad_regular.isSelected()) {
            fee();
        }
    }//GEN-LAST:event_rad_regularActionPerformed

    private void rad_regularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rad_regularMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rad_regularMouseClicked

    private void rad_freeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rad_freeActionPerformed
        if (rad_free.isSelected()) {
            lbl_fee.setText("00.00");
        }
    }//GEN-LAST:event_rad_freeActionPerformed

    private void btn_generateInvoiceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_generateInvoiceKeyReleased
// TODO add your handling code here:
    }//GEN-LAST:event_btn_generateInvoiceKeyReleased

    private void btn_generateInvoiceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btn_generateInvoiceFocusGained

        // TODO add your handling code here:
    }//GEN-LAST:event_btn_generateInvoiceFocusGained

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
            java.util.logging.Logger.getLogger(studentPayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(studentPayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(studentPayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(studentPayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new studentPayment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_close;
    private javax.swing.JButton btn_generateInvoice;
    private javax.swing.ButtonGroup btn_group_fee;
    private javax.swing.JButton btn_menu;
    private javax.swing.JComboBox cmb_subject;
    private javax.swing.JComboBox cmb_teacher;
    private com.toedter.calendar.JDateChooser dc_date;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_fee;
    private javax.swing.JLabel lbl_paymentDate;
    private javax.swing.JLabel lbl_student_invoice_id;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_userType;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JRadioButton rad_free;
    private javax.swing.JRadioButton rad_regular;
    private javax.swing.JTextField txt_studentId;
    // End of variables declaration//GEN-END:variables

//genarate Report
    void genarateReport() {
        String company_email = null, company_number = null;
        String fullName = null;
        try {
            ResultSet rs = DB.getData("SELECT company_email,company_mobile,company_office FROM admin_panel");
            while (rs.next()) {
                company_email = rs.getString("company_email");
                company_number = rs.getString("company_mobile") + " " + rs.getString("company_office");

            }
            ResultSet rs2 = DB.getData("SELECT f_name,l_name FROM student_reg WHERE student_id='" + txt_studentId.getText() + "'");
            while (rs2.next()) {
                fullName = rs2.getString("f_name") + " " + rs2.getString("l_name");
            }
        } catch (Exception e) {
            System.out.println("in genarate Report in class studaent payment ");
            e.printStackTrace();
        }
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("\\Reports\\classFee.jrxml");
            JasperDesign jd = JRXmlLoader.load(is);
            JasperReport jr = JasperCompileManager.compileReport(jd);

            Map<String, Object> p = new HashMap<String, Object>();
            p.put("email", company_email);
            p.put("numbers", company_number);
            p.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            p.put("ID", txt_studentId.getText());
            p.put("Name", fullName);
            p.put("InvoiceID", student_invoice_id);
            p.put("Subject", cmb_subject.getSelectedItem().toString().split("-")[1]);
            p.put("Grade", "Grade :" + cmb_subject.getSelectedItem().toString().split("-")[0]);
            p.put("Amount", lbl_fee.getText());
            p.put("Total", lbl_fee.getText());

            JasperPrint jp = JasperFillManager.fillReport(jr, p, new JREmptyDataSource());
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            System.out.println("in genarateReport method in class RegistrationFee");
            e.printStackTrace();

        }
    }

}
