/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Classes.JDBC;
import Classes.ToolsClass;
import GUI.Home;
import GUI.Menu;
import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author User
 */
public class TeacherDetails extends javax.swing.JFrame {

    /**
     * Creates new form TeacherDetails
     */
    ToolsClass tool = new ToolsClass();
    JDBC DB = new JDBC();
    org.apache.log4j.Logger logError = org.apache.log4j.Logger.getLogger("ERROR in Admin Teacher Details ");

    public TeacherDetails() {
        initComponents();
        Menu();
        setAll();
        DisableAll();
        lbl_username.setText(Home.un);
        lbl_userType.setText(Home.ut);
        lbl_date.setText(new Classes.Commons().Date());
        new Classes.Commons().time(lbl_time);
    }
    
     //////------------Menu-----------//////
    
    void Menu(){
        try {
            popup_menu.setText("Menu");
            popup_help.setText("Help");
            popup_exit.setText("Exit");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    //--------------------------------------------------------Disable Components Starts---------------------------------
    void DisableAll() {
        cmb_serachResult.setVisible(false);
        cmb_subject.setVisible(false);
    }

    void setVisibaleInTeacherName() {
        cmb_serachResult.setVisible(true);
        cmb_subject.setVisible(false);
        //LoadTeacherDetails();
    }

    void setVisibaleInSubject() {
        cmb_serachResult.setVisible(false);
        cmb_subject.setVisible(true);
        //LoadSubjects();
    }
    //--------------------------------------------------------Disable Components Finish---------------------------------
    //----------------------------------------------Load Como Box  Start--------------------------------------

    //Teacher  Names
    void LoadTeacherDetails() {
        cmb_serachResult.removeAllItems();
        try {
            ResultSet rs = DB.getData("SELECT * FROM teacher_reg");
            Vector v = new Vector();
            while (rs.next()) {
                String id = rs.getString("teacher_id");
                String fname = rs.getString("f_name");
                String lname = rs.getString("l_name");
                String all = id + "-" + fname + " " + lname;
                v.add(all);
            }
            rs.close();

            for (int i = 0; i < v.size(); i++) {
                cmb_serachResult.addItem(v.get(i));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ATD - 0001  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }

    }

    //Subjects
    void LoadSubjects() {
        cmb_subject.removeAllItems();
        try {
            ResultSet rs = DB.getData("SELECT * FROM subject");
            Vector v = new Vector();
            while (rs.next()) {
                String subjectId = rs.getString("subject_id");
                String subjectName = rs.getString("subject_name");
                String all = subjectId + "-" + subjectName;
                v.add(all);
            }
            rs.close();

            for (int i = 0; i < v.size(); i++) {
                cmb_subject.addItem(v.get(i));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ATD - 0002  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }

    }

    //----------------------------------------------Load Como Box Finish--------------------------------------
    //-----------------------------------------------------Serch By Combo box Select Starts ----------------------
    //All, Teacher Name / ID, Subject, Active, Inactive
    String teacherid, nic, fname, lname, email, mobile, qualificationid, no, street1, street2, city, subjectid, date, status;

    //Select All in combo box 
    void setAll() {
        tool.clearTable(tbl_teacherDetails);
        try {
            ResultSet rs = DB.getData("SELECT * FROM teacher_reg ");
            while (rs.next()) {
                teacherid = rs.getString("teacher_id");
                nic = rs.getString("nic");
                fname = rs.getString("f_name");
                lname = rs.getString("l_name");
                email = rs.getString("email");
                mobile = rs.getString("mobile_no");
                qualificationid = rs.getString("qualification_id");
                no = rs.getString("no");
                street1 = rs.getString("street1");
                street2 = rs.getString("street2");
                city = rs.getString("city");
                date = rs.getString("date");
                status = rs.getString("status");
                String statusName;
                if (status.equals("1")) {
                    statusName = "Active";
                } else {
                    statusName = "Inactive";
                }
                ResultSet rs1 = DB.getData("SELECT * FROM teacher_time_table WHERE teacher_id='" + teacherid + "'");
                while (rs1.next()) {
                    ResultSet rs2 = DB.getData("SELECT * FROM qualification WHERE qualification_id='" + qualificationid + "'");
                    while (rs2.next()) {
                        subjectid = "";
                        subjectid = subjectid + "" + rs1.getString("subject_id");
                        String Qualification = rs2.getString("qualification");

                        tool.addToTable(tbl_teacherDetails, teacherid, nic, fname + " " + lname, email, mobile, Qualification, subjectid,
                                no + "," + street1 + "," + street2 + "," + city, date, statusName);
                    }
                    rs2.close();
                }
                rs1.close();

            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ATD - 0003  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    //Select Subject in combo box
    void setSubject(String id) {

        tool.clearTable(tbl_teacherDetails);
        try {
            ResultSet rs = DB.getData("SELECT teacher_id FROM teacher_time_table WHERE subject_id='" + id + "'");
            while (rs.next()) {
                ResultSet rs1 = DB.getData("SELECT * FROM teacher_reg WHERE teacher_id='" + rs.getString("teacher_id") + "'");
                while (rs1.next()) {
                    teacherid = rs1.getString("teacher_id");
                    nic = rs1.getString("nic");
                    fname = rs1.getString("f_name");
                    lname = rs1.getString("l_name");
                email = rs.getString("email");
                mobile = rs.getString("mobile_no");
                    qualificationid = rs1.getString("qualification_id");
                    no = rs1.getString("no");
                    street1 = rs1.getString("street1");
                    street2 = rs1.getString("street2");
                    city = rs1.getString("city");
                    date = rs1.getString("date");
                    status = rs1.getString("status");
                    String statusName;
                    if (status.equals("1")) {
                        statusName = "Active";
                    } else {
                        statusName = "Inactive";
                    }
                    ResultSet rs2 = DB.getData("SELECT * FROM qualification WHERE qualification_id='" + qualificationid + "'");
                    while (rs2.next()) {
                        subjectid = id;
                        String Qualification = rs2.getString("qualification");

                        tool.addToTable(tbl_teacherDetails, teacherid, nic, fname + " " + lname, email, mobile, Qualification, subjectid,
                                no + "," + street1 + "," + street2 + "," + city, date, statusName);
                    }
                    rs2.close();

                }
                rs1.close();
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ATD - 0004  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    //Select TeacherName / ID in combo box 
    void setTeacherNameID(String id) {
        System.out.println(id);
        tool.clearTable(tbl_teacherDetails);
        try {
            ResultSet rs = DB.getData("SELECT * FROM teacher_reg WHERE teacher_id='" + id + "' ");
            while (rs.next()) {
                System.out.println("in");
                teacherid = rs.getString("teacher_id");
                nic = rs.getString("nic");
                fname = rs.getString("f_name");
                lname = rs.getString("l_name");
                email = rs.getString("email");
                mobile = rs.getString("mobile_no");
                qualificationid = rs.getString("qualification_id");
                no = rs.getString("no");
                street1 = rs.getString("street1");
                street2 = rs.getString("street2");
                city = rs.getString("city");
                date = rs.getString("date");
                status = rs.getString("status");
                String statusName;
                if (status.equals("1")) {
                    statusName = "Active";
                } else {
                    statusName = "Inactive";
                }
                ResultSet rs1 = DB.getData("SELECT * FROM teacher_time_table WHERE teacher_id='" + teacherid + "'");
                while (rs1.next()) {
                    ResultSet rs2 = DB.getData("SELECT * FROM qualification WHERE qualification_id='" + qualificationid + "'");
                    while (rs2.next()) {
                        subjectid = "";
                        subjectid = subjectid + "" + rs1.getString("subject_id");
                        String Qualification = rs2.getString("qualification");

                        tool.addToTable(tbl_teacherDetails, teacherid, nic, fname + " " + lname, email, mobile, Qualification, subjectid,
                                no + "," + street1 + "," + street2 + "," + city, date, statusName);
                    }
                    rs2.close();
                }
                rs1.close();

            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ATD - 0005  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    //Select Active in combo box
    void setActive() {
        tool.clearTable(tbl_teacherDetails);
        try {
            ResultSet rs = DB.getData("SELECT * FROM teacher_reg WHERE status='1' ");
            while (rs.next()) {
                teacherid = rs.getString("teacher_id");
                nic = rs.getString("nic");
                fname = rs.getString("f_name");
                lname = rs.getString("l_name");
                email = rs.getString("email");
                mobile = rs.getString("mobile_no");
                qualificationid = rs.getString("qualification_id");
                no = rs.getString("no");
                street1 = rs.getString("street1");
                street2 = rs.getString("street2");
                city = rs.getString("city");
                date = rs.getString("date");
                status = rs.getString("status");
                String statusName;
                if (status.equals("1")) {
                    statusName = "Active";
                } else {
                    statusName = "Inactive";
                }
                ResultSet rs1 = DB.getData("SELECT * FROM teacher_time_table WHERE teacher_id='" + teacherid + "'");
                while (rs1.next()) {
                    ResultSet rs2 = DB.getData("SELECT * FROM qualification WHERE qualification_id='" + qualificationid + "'");
                    while (rs2.next()) {
                        subjectid = "";
                        subjectid = subjectid + "" + rs1.getString("subject_id");
                        String Qualification = rs2.getString("qualification");

                        tool.addToTable(tbl_teacherDetails, teacherid, nic, fname + " " + lname, email, mobile, Qualification, subjectid,
                                no + "," + street1 + "," + street2 + "," + city, date, statusName);
                    }
                    rs2.close();
                }
                rs1.close();

            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ATD - 0006  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));

        }
    }

    //select Inactive in combo box
    void setInactive() {
        tool.clearTable(tbl_teacherDetails);
        try {
            ResultSet rs = DB.getData("SELECT * FROM teacher_reg WHERE status='0' ");
            while (rs.next()) {
                teacherid = rs.getString("teacher_id");
                nic = rs.getString("nic");
                fname = rs.getString("f_name");
                lname = rs.getString("l_name");
                email = rs.getString("email");
                mobile = rs.getString("mobile_no");
                qualificationid = rs.getString("qualification_id");
                no = rs.getString("no");
                street1 = rs.getString("street1");
                street2 = rs.getString("street2");
                city = rs.getString("city");
                date = rs.getString("date");
                status = rs.getString("status");
                String statusName;
                if (status.equals("1")) {
                    statusName = "Active";
                } else {
                    statusName = "Inactive";
                }
                ResultSet rs1 = DB.getData("SELECT * FROM teacher_time_table WHERE teacher_id='" + teacherid + "'");
                while (rs1.next()) {
                    ResultSet rs2 = DB.getData("SELECT * FROM qualification WHERE qualification_id='" + qualificationid + "'");
                    while (rs2.next()) {
                        subjectid = "";
                        subjectid = subjectid + "" + rs1.getString("subject_id");
                        String Qualification = rs2.getString("qualification");

                        tool.addToTable(tbl_teacherDetails, teacherid, nic, fname + " " + lname, email, mobile, Qualification, subjectid,
                                no + "," + street1 + "," + street2 + "," + city, date, statusName);
                    }
                    rs2.close();
                }
                rs1.close();

            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ATD - 0007  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));

        }
    }
    //-----------------------------------------------------Serch By Combo box Select Finish ----------------------
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
        pnl_Expenceheader = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lbl_userType = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cmb_teacherSearchBy = new javax.swing.JComboBox();
        cmb_serachResult = new javax.swing.JComboBox();
        cmb_subject = new javax.swing.JComboBox();
        jPanel33 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_teacherDetails = new javax.swing.JTable();
        btn_teacherDetailsGenerateReport = new javax.swing.JButton();
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
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_Expenceheader.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Expenceheader.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnl_Expenceheader.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_ExpenceheaderMouseClicked(evt);
            }
        });
        pnl_Expenceheader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cancel.png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pnl_Expenceheader.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1340, 10, 16, 16));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu.png"))); // NOI18N
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        pnl_Expenceheader.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 9, 32, 32));

        lbl_userType.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_userType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_userType.setText("User type");
        pnl_Expenceheader.add(lbl_userType, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 30, 220, -1));

        lbl_username.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lbl_username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_username.setText("Username");
        pnl_Expenceheader.add(lbl_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 10, 220, -1));

        lbl_date.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_date.setText("jLabel3");
        pnl_Expenceheader.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, -1));

        lbl_time.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_time.setText("jLabel3");
        pnl_Expenceheader.add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 23, 100, -1));

        jLabel12.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel12.setText("Admin Panel - Teacher Details");
        pnl_Expenceheader.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 340, 30));

        getContentPane().add(pnl_Expenceheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 50));

        jPanel4.setOpaque(false);
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel32.setOpaque(false);
        jPanel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel32MouseClicked(evt);
            }
        });
        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel16.setText("Search By :");
        jPanel32.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 31, -1, -1));

        cmb_teacherSearchBy.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_teacherSearchBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Teacher Name / ID", "Subject", "Active", "Inactive" }));
        cmb_teacherSearchBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_teacherSearchByActionPerformed(evt);
            }
        });
        jPanel32.add(cmb_teacherSearchBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 28, 194, -1));

        cmb_serachResult.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_serachResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmb_serachResultMouseClicked(evt);
            }
        });
        cmb_serachResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_serachResultActionPerformed(evt);
            }
        });
        jPanel32.add(cmb_serachResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 28, 218, -1));

        cmb_subject.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_subject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmb_subjectMouseClicked(evt);
            }
        });
        cmb_subject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_subjectActionPerformed(evt);
            }
        });
        jPanel32.add(cmb_subject, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 28, 218, -1));

        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Result", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel33.setOpaque(false);
        jPanel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel33MouseClicked(evt);
            }
        });

        tbl_teacherDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Teacher ID", "NIC", "Name", "Email", "Mobile No", "Latest Qualification", "Subjects", "Address", "Registered Date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_teacherDetails.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tbl_teacherDetails);

        btn_teacherDetailsGenerateReport.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_teacherDetailsGenerateReport.setText("Generate Report");
        btn_teacherDetailsGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_teacherDetailsGenerateReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1318, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_teacherDetailsGenerateReport)))
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_teacherDetailsGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1360, 710));

        lbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N
        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, 0, 1370, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmb_teacherSearchByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_teacherSearchByActionPerformed
        //All, Teacher Name / ID, Subject, Active, Inactive
        String selectedString = cmb_teacherSearchBy.getSelectedItem().toString().trim();
        if (selectedString.equals("All")) {
            setAll();
            DisableAll();
        } else if (selectedString.equals("Teacher Name / ID")) {
            setVisibaleInTeacherName();
            LoadTeacherDetails();
        } else if (selectedString.equals("Active")) {
            DisableAll();
            setActive();
        } else if (selectedString.equals("Inactive")) {
            DisableAll();
            setInactive();
        } else if (selectedString.equals("Subject")) {
            setVisibaleInSubject();
            LoadSubjects();
        }
    }//GEN-LAST:event_cmb_teacherSearchByActionPerformed

    private void cmb_subjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_subjectActionPerformed
        int itemCount = cmb_subject.getItemCount();
        if (itemCount > 0) {
            String id = cmb_subject.getSelectedItem().toString().trim().split("-")[0];
            if (!id.equals("")) {
                setSubject(id);

            }
        }
    }//GEN-LAST:event_cmb_subjectActionPerformed

    private void cmb_serachResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_serachResultActionPerformed
        int itemCount = cmb_serachResult.getItemCount();
        if (itemCount > 0) {
            if (!(cmb_serachResult.getSelectedItem().toString().equals(""))) {

                setTeacherNameID(cmb_serachResult.getSelectedItem().toString().split("-")[0]);
            }
        }
    }//GEN-LAST:event_cmb_serachResultActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new Menu().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cmb_subjectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmb_subjectMouseClicked
//        String id=cmb_subject.getSelectedItem().toString().trim().split("-")[0];
//        if (!id.equals("")) {
//        setSubject(id);
//            
//        }
    }//GEN-LAST:event_cmb_subjectMouseClicked

    private void cmb_serachResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmb_serachResultMouseClicked
//        if (!(cmb_serachResult.getSelectedItem().toString().equals(""))) {
//
//            setTeacherNameID(cmb_serachResult.getSelectedItem().toString().split("-")[0]);
//        }
    }//GEN-LAST:event_cmb_serachResultMouseClicked

    private void btn_teacherDetailsGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_teacherDetailsGenerateReportActionPerformed
        //All, Teacher Name / ID, Subject, Active, Inactive
        String company_email = null, company_number = null, details_by;

        try {
            ResultSet rs = DB.getData("SELECT company_email,company_mobile,company_office FROM admin_panel");
            while (rs.next()) {
                company_email = rs.getString("company_email");
                company_number = rs.getString("company_mobile") + " " + rs.getString("company_office");

            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ATD - 0008-01  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
        String Details_By = null;
        details_by = cmb_teacherSearchBy.getSelectedItem().toString().trim();
        if (details_by.equals("All")) {

            Details_By = "All Teachers";
        } else if (details_by.equals("Teacher Name / ID")) {

            Details_By = "Teacher Deatils By :" + cmb_serachResult.getSelectedItem().toString().trim();
        } else if (details_by.equals("Subject")) {

            Details_By = "Subject Details :" + cmb_subject.getSelectedItem().toString().trim();
        } else if (details_by.equals("Active")) {
            Details_By = "Active Teachers";
        } else if (details_by.equals("Inactive")) {
            Details_By = "Inactive Teachers";
        }
        int i = tbl_teacherDetails.getRowCount();
        if (i > 0) {
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("\\Reports\\teacher Details.jrxml");
                JasperDesign jd = JRXmlLoader.load(is);
                JasperReport jr = JasperCompileManager.compileReport(jd);

                JRTableModelDataSource tm = new JRTableModelDataSource(tbl_teacherDetails.getModel());

                Map<String, Object> p = new HashMap<String, Object>();
                p.put("email", company_email);
                p.put("numbers", company_number);
                p.put("details by", Details_By);

                JasperPrint jp = JasperFillManager.fillReport(jr, p, tm);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ATD - 0008-02  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

        } else {
            JOptionPane.showMessageDialog(this, "There is no Data to Print");
        }
    }//GEN-LAST:event_btn_teacherDetailsGenerateReportActionPerformed

    private void popup_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_menuActionPerformed
        this.dispose();
        new Menu().setVisible(true);
    }//GEN-LAST:event_popup_menuActionPerformed

    private void popup_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_helpActionPerformed
        try {
            Desktop.getDesktop().open(new File("E:\\Syndicate\\Documentation\\Admin – Teacher Details.pdf"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_popup_helpActionPerformed

    private void popup_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_exitActionPerformed
        this.dispose();
    }//GEN-LAST:event_popup_exitActionPerformed

    private void pnl_ExpenceheaderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_ExpenceheaderMouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_ExpenceheaderMouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
       if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel32MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel32MouseClicked

    private void jPanel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel33MouseClicked
       if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel33MouseClicked

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
            java.util.logging.Logger.getLogger(TeacherDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TeacherDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TeacherDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TeacherDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TeacherDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_teacherDetailsGenerateReport;
    private javax.swing.JComboBox cmb_serachResult;
    private javax.swing.JComboBox cmb_subject;
    private javax.swing.JComboBox cmb_teacherSearchBy;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_userType;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_Expenceheader;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem popup_exit;
    private javax.swing.JMenuItem popup_help;
    private javax.swing.JMenuItem popup_menu;
    private javax.swing.JTable tbl_teacherDetails;
    // End of variables declaration//GEN-END:variables
}
