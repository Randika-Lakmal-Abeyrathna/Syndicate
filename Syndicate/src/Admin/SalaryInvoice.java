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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
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
 * @author Vidyani
 */
public class SalaryInvoice extends javax.swing.JFrame {

    /**
     * Creates new form AdminPanel
     */
    public SalaryInvoice() {
        initComponents();
        setAllInvisible();
        Menu();
        tbl_salary.setEnabled(false);
        tbl_salaryAdvance.setEnabled(false);
        dc_from.setMaxSelectableDate(new Date());
        dc_to.setMaxSelectableDate(new Date());
        lbl_username.setText(Home.un);
        lbl_userType.setText(Home.ut);
        lbl_date.setText(new Classes.Commons().Date());
        new Classes.Commons().time(lbl_time);
    }
    JDBC DB = new JDBC();
    Classes.ToolsClass tool = new ToolsClass();
    org.apache.log4j.Logger logError = org.apache.log4j.Logger.getLogger("ERROR in Admin Salary Invoice ");
    
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

    //////----- set all invisible------------------//////
    void setAllInvisible() {

        lbl_from.setVisible(false);
        lbl_month.setVisible(false);
        lbl_to.setVisible(false);
        lbl_year.setVisible(false);

        dc_from.setVisible(false);
        dc_month.setVisible(false);
        dc_to.setVisible(false);
        dc_year.setVisible(false);
    }

    /////----comboBox Selecting--------------------/////////
    void comboSelectforDays() {

        if (cmb_dayChoose.getSelectedItem().toString().equals("Monthly")) {
            setAllInvisible();
            lbl_month.setVisible(true);
            lbl_year.setVisible(true);
            dc_month.setVisible(true);
            dc_year.setVisible(true);

        }
        if (cmb_dayChoose.getSelectedItem().toString().equals("Annually")) {
            setAllInvisible();
            lbl_year.setVisible(true);
            dc_year.setVisible(true);
        }
        if (cmb_dayChoose.getSelectedItem().toString().equals("Custome")) {
            setAllInvisible();
            lbl_from.setVisible(true);
            lbl_to.setVisible(true);
            dc_from.setVisible(true);
            dc_to.setVisible(true);
        }
    }

    //////--- salary daychoosers---------------/////
    void salaryDayChoosers() {
        lbl_from.setVisible(false);
        lbl_month.setVisible(false);
        lbl_to.setVisible(false);
        lbl_year.setVisible(false);
        dc_from.setVisible(false);
        dc_month.setVisible(false);
        dc_to.setVisible(false);
        dc_year.setVisible(false);
    }

    //////// set salary choosing combo----------------------//////////
    void salarySelectingCombo() {
        if (cmb_salaryType.getSelectedItem().toString().equals("Salary")) {
            salaryDayChoosers();
            tbl_salary.setEnabled(true);
            tbl_salaryAdvance.setEnabled(false);
        }
        if (cmb_salaryType.getSelectedItem().toString().equals("Salary Advance")) {
            salaryDayChoosers();
            tbl_salary.setEnabled(false);
            tbl_salaryAdvance.setEnabled(true);
        }
    }

    //////////salary Advance table///////////////////////////////////////////
    /////////--------------------------------------------///////////////////
    void loadToSalaryAdvanceTable() {
        if (cmb_salaryType.getSelectedItem().toString().equals("Salary")) {
            int month = dc_month.getMonth() + 1;
            int year = dc_year.getYear();
            String fullDate;
            String empID = "";
            String empName;
            if (month > 9) {
                fullDate = year + "-" + month;
            } else {
                fullDate = year + "-0" + month;
            }
            /////////// serch by monthly------------------------------
            if (cmb_dayChoose.getSelectedItem().toString().equals("Monthly")) {
                tool.clearTable(tbl_salary);
                try {
                    ResultSet rs = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + txt_employeeID.getText() + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM job_title WHERE job_title_id='" + rs.getString("job_title_id") + "'");
                        while (rs1.next()) {
                            ResultSet rs2 = DB.getData("SELECT * FROM basic_salary WHERE job_title_id='" + rs1.getString("job_title_id") + "'");

                            while (rs2.next()) {
                                ResultSet rs3 = DB.getData("SELECT * FROM salary WHERE month LIKE '" + fullDate + "%' AND emp_id='" + txt_employeeID.getText() + "'");
                                while (rs3.next()) {
                                    ResultSet rs4 = DB.getData("SELECT * FROM etf_epf WHERE salary_id='" + rs3.getString("salary_id") + "'");
                                    while (rs4.next()) {
                                        ResultSet rs5 = DB.getData("SELECT * FROM salary_invoice WHERE salary_id='" + rs3.getString("salary_id") + "'");
                                        while (rs5.next()) {
                                            String invoice = rs5.getString("salary_invoice_id");
                                            empID = txt_employeeID.getText();
                                            empName = rs.getString("f_name") + " " + rs.getString("l_name");
                                            String attendance = rs3.getString("attendance");
                                            String leaves = rs3.getString("taken_leaves");
                                            String pay = rs3.getString("payment");
                                            String basic = rs2.getString("amount");
                                            String etf = rs4.getString("amount");
                                            String salary = rs5.getString("amount");
                                            String date = rs5.getString("date");

                                            tool.addToTable(tbl_salary, invoice, empID, empName, attendance, leaves, pay, basic, etf, salary, date);
                                        }
                                        rs5.close();
                                    }
                                    rs4.close();
                                }
                                rs3.close();

                            }
                            rs2.close();

                        }
                        rs1.close();

                    }
                    rs.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - ASI - 0001-01-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }

            }
            /////// seasrch by monthly-----------------------------
            if (cmb_dayChoose.getSelectedItem().toString().equals("Annually")) {
                tool.clearTable(tbl_salary);
                String fullYear = Integer.toString(year);
                try {
                    ResultSet rs = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + txt_employeeID.getText() + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM job_title WHERE job_title_id='" + rs.getString("job_title_id") + "'");
                        while (rs1.next()) {
                            ResultSet rs2 = DB.getData("SELECT * FROM basic_salary WHERE job_title_id='" + rs1.getString("job_title_id") + "'");

                            while (rs2.next()) {
                                ResultSet rs3 = DB.getData("SELECT * FROM salary WHERE month LIKE '" + fullYear + "%' AND emp_id='" + txt_employeeID.getText() + "'");
                                while (rs3.next()) {
                                    ResultSet rs4 = DB.getData("SELECT * FROM etf_epf WHERE salary_id='" + rs3.getString("salary_id") + "'");
                                    while (rs4.next()) {
                                        ResultSet rs5 = DB.getData("SELECT * FROM salary_invoice WHERE salary_id='" + rs3.getString("salary_id") + "'");
                                        while (rs5.next()) {
                                            String invoice = rs5.getString("salary_invoice_id");
                                            empID = txt_employeeID.getText();
                                            empName = rs.getString("f_name") + " " + rs.getString("l_name");
                                            String attendance = rs3.getString("attendance");
                                            String leaves = rs3.getString("taken_leaves");
                                            String pay = rs3.getString("payment");
                                            String basic = rs2.getString("amount");
                                            String etf = rs4.getString("amount");
                                            String salary = rs5.getString("amount");
                                            String date = rs5.getString("date");

                                            tool.addToTable(tbl_salary, invoice, empID, empName, attendance, leaves, pay, basic, etf, salary, date);
                                        }
                                        rs5.close();
                                    }
                                    rs4.close();
                                }
                                rs3.close();

                            }
                            rs2.close();

                        }
                        rs1.close();

                    }
                    rs.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - ASI - 0001-01-02  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
            if (cmb_dayChoose.getSelectedItem().toString().equals("Custome")) {
                tool.clearTable(tbl_salary);
                String from = new SimpleDateFormat("yyyy-MM-dd").format(dc_from.getDate());
                String to = new SimpleDateFormat("yyyy-MM-dd").format(dc_to.getDate());
                try {
                    ResultSet rs = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + txt_employeeID.getText() + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM job_title WHERE job_title_id='" + rs.getString("job_title_id") + "'");
                        while (rs1.next()) {
                            ResultSet rs2 = DB.getData("SELECT * FROM basic_salary WHERE job_title_id='" + rs1.getString("job_title_id") + "'");

                            while (rs2.next()) {
                                ResultSet rs3 = DB.getData("SELECT * FROM salary WHERE month BETWEEN '" + from + "' AND '" + to + "' AND emp_id='" + txt_employeeID.getText() + "'");
                                while (rs3.next()) {
                                    ResultSet rs4 = DB.getData("SELECT * FROM etf_epf WHERE salary_id='" + rs3.getString("salary_id") + "'");
                                    while (rs4.next()) {
                                        ResultSet rs5 = DB.getData("SELECT * FROM salary_invoice WHERE salary_id='" + rs3.getString("salary_id") + "'");
                                        while (rs5.next()) {
                                            String invoice = rs5.getString("salary_invoice_id");
                                            empID = txt_employeeID.getText();
                                            empName = rs.getString("f_name") + " " + rs.getString("l_name");
                                            String attendance = rs3.getString("attendance");
                                            String leaves = rs3.getString("taken_leaves");
                                            String pay = rs3.getString("payment");
                                            String basic = rs2.getString("amount");
                                            String etf = rs4.getString("amount");
                                            String salary = rs5.getString("amount");
                                            String date = rs5.getString("date");

                                            tool.addToTable(tbl_salary, invoice, empID, empName, attendance, leaves, pay, basic, etf, salary, date);
                                        }
                                        rs5.close();
                                    }
                                    rs4.close();
                                }
                                rs3.close();
                            }
                            rs2.close();
                        }
                        rs1.close();
                    }
                    rs.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - ASI - 0001-01-03  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
        }
        ////-------- when selecting salary advance---------------
        if (cmb_salaryType.getSelectedItem().toString().equals("Salary Advance")) {
            int month = dc_month.getMonth() + 1;
            int year = dc_year.getYear();
            String fullDate;

            if (month > 9) {
                fullDate = year + "-" + month;
            } else {
                fullDate = year + "-0" + month;
            }
            ////////when selecting daily--------------------
            if (cmb_dayChoose.getSelectedItem().toString().equals("Monthly")) {
                tool.clearTable(tbl_salaryAdvance);
                try {
                    ResultSet rs = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + txt_employeeID.getText() + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM salary_advance WHERE month LIKE '" + fullDate + "%' AND emp_id='" + txt_employeeID.getText() + "'");
                        while (rs1.next()) {
                            ResultSet rs2 = DB.getData("SELECT * FROM salary_invoice WHERE salary_advance_id='" + rs1.getString("salary_advance_id") + "'");
                            while (rs2.next()) {
                                String empID = txt_employeeID.getText();
                                String empName = rs.getString("f_name") + " " + rs.getString("l_name");
                                String invoice = rs2.getString("salary_invoice_id");
                                String advance = rs2.getString("amount");
                                String date = rs2.getString("date");

                                tool.addToTable(tbl_salaryAdvance, invoice, empID, empName, advance, date);

                            }
                            rs2.close();
                        }
                        rs1.close();
                    }
                    rs.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - ASI - 0001-02-01  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }

            }
            ///// when selecting annually
            if (cmb_dayChoose.getSelectedItem().toString().equals("Annually")) {
                tool.clearTable(tbl_salaryAdvance);
                String fullYear = Integer.toString(year);
                try {
                    ResultSet rs = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + txt_employeeID.getText() + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM salary_advance WHERE month LIKE '" + fullYear + "%' AND emp_id='" + txt_employeeID.getText() + "'");
                        while (rs1.next()) {
                            ResultSet rs2 = DB.getData("SELECT * FROM salary_invoice WHERE salary_advance_id='" + rs1.getString("salary_advance_id") + "'");
                            while (rs2.next()) {
                                String empID = txt_employeeID.getText();
                                String empName = rs.getString("f_name") + " " + rs.getString("l_name");
                                String invoice = rs2.getString("salary_invoice_id");
                                String advance = rs2.getString("amount");
                                String date = rs2.getString("date");

                                tool.addToTable(tbl_salaryAdvance, invoice, empID, empName, advance, date);

                            }
                            rs2.close();
                        }
                        rs1.close();
                    }
                    rs.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - ASI - 0001-02-02  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }

            }
            ////// when selecting custome------------------
            if (cmb_dayChoose.getSelectedItem().toString().equals("Custome")) {
                tool.clearTable(tbl_salaryAdvance);
                String from = new SimpleDateFormat("yyyy-MM-dd").format(dc_from.getDate());
                String to = new SimpleDateFormat("yyyy-MM-dd").format(dc_to.getDate());
                try {
                    ResultSet rs = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + txt_employeeID.getText() + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM salary_advance WHERE month  BETWEEN '" + from + "' AND '" + to + "' AND emp_id='" + txt_employeeID.getText() + "'");
                        while (rs1.next()) {
                            ResultSet rs2 = DB.getData("SELECT * FROM salary_invoice WHERE salary_advance_id='" + rs1.getString("salary_advance_id") + "'");
                            while (rs2.next()) {
                                String empID = txt_employeeID.getText();
                                String empName = rs.getString("f_name") + " " + rs.getString("l_name");
                                String invoice = rs2.getString("salary_invoice_id");
                                String advance = rs2.getString("amount");
                                String date = rs2.getString("date");

                                tool.addToTable(tbl_salaryAdvance, invoice, empID, empName, advance, date);

                            }
                            rs2.close();
                        }
                        rs1.close();
                    }
                    rs.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - ASI - 0001-02-03  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        popup_menu = new javax.swing.JMenuItem();
        popup_help = new javax.swing.JMenuItem();
        popup_exit = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        pnl_header = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lbl_userType = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmb_salaryType = new javax.swing.JComboBox();
        cmb_dayChoose = new javax.swing.JComboBox();
        txt_employeeID = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        dc_month = new com.toedter.calendar.JMonthChooser();
        dc_year = new com.toedter.calendar.JYearChooser();
        lbl_year = new javax.swing.JLabel();
        lbl_month = new javax.swing.JLabel();
        lbl_from = new javax.swing.JLabel();
        dc_from = new com.toedter.calendar.JDateChooser();
        lbl_to = new javax.swing.JLabel();
        dc_to = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_salaryAdvance = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_salary = new javax.swing.JTable();
        btn_SalaryInvoiceGenerateReport = new javax.swing.JButton();
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

        pnl_header.setBackground(new java.awt.Color(255, 255, 255));
        pnl_header.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnl_header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_headerMouseClicked(evt);
            }
        });
        pnl_header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cancel.png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pnl_header.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1340, 10, 16, 16));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu.png"))); // NOI18N
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        pnl_header.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 9, 32, 32));

        lbl_userType.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_userType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_userType.setText("User type");
        pnl_header.add(lbl_userType, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 30, 220, -1));

        lbl_username.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lbl_username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_username.setText("Username");
        pnl_header.add(lbl_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 10, 220, -1));

        lbl_date.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_date.setText("jLabel3");
        pnl_header.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, -1));

        lbl_time.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_time.setText("jLabel3");
        pnl_header.add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 23, 100, -1));

        jLabel12.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel12.setText("Admin Panel - Salary Invoice");
        pnl_header.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 340, 30));

        jPanel1.add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 50));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel1.setText("Employee ID :");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        cmb_salaryType.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_salaryType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Salary", "Salary Advance" }));
        cmb_salaryType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_salaryTypeActionPerformed(evt);
            }
        });
        jPanel2.add(cmb_salaryType, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 164, -1));

        cmb_dayChoose.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_dayChoose.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Monthly", "Annually", "Custome" }));
        cmb_dayChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_dayChooseActionPerformed(evt);
            }
        });
        jPanel2.add(cmb_dayChoose, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 164, -1));

        txt_employeeID.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txt_employeeID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_employeeIDFocusGained(evt);
            }
        });
        jPanel2.add(txt_employeeID, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 164, -1));

        btn_search.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        btn_search.setText("Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jPanel2.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 100, 110, -1));

        dc_month.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jPanel2.add(dc_month, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 160, 30));
        jPanel2.add(dc_year, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 160, 30));

        lbl_year.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_year.setText("Year :");
        jPanel2.add(lbl_year, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, -1, 30));

        lbl_month.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_month.setText("Month :");
        jPanel2.add(lbl_month, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, -1, 30));

        lbl_from.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_from.setText("From :");
        jPanel2.add(lbl_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, -1, 30));

        dc_from.setDateFormatString("yyyy-MM-dd");
        dc_from.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jPanel2.add(dc_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, 160, 30));

        lbl_to.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_to.setText("To :");
        jPanel2.add(lbl_to, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 100, -1, 30));

        dc_to.setDateFormatString("yyyy-MM-dd");
        dc_to.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jPanel2.add(dc_to, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 100, 160, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1350, 190));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Advance Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel3.setOpaque(false);

        tbl_salaryAdvance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice ID", "Emp ID", "Emp Name", "Amount", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_salaryAdvance.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_salaryAdvance);
        if (tbl_salaryAdvance.getColumnModel().getColumnCount() > 0) {
            tbl_salaryAdvance.getColumnModel().getColumn(0).setResizable(false);
            tbl_salaryAdvance.getColumnModel().getColumn(1).setResizable(false);
            tbl_salaryAdvance.getColumnModel().getColumn(2).setResizable(false);
            tbl_salaryAdvance.getColumnModel().getColumn(3).setResizable(false);
            tbl_salaryAdvance.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 520, 470));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Salary Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        tbl_salary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice ID", "Em ID", "Emp Name", "Attendance", "Leaves", "payment", "Basic Salary", "Etf / Epf", "Month Salary", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_salary.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tbl_salary);
        if (tbl_salary.getColumnModel().getColumnCount() > 0) {
            tbl_salary.getColumnModel().getColumn(0).setResizable(false);
            tbl_salary.getColumnModel().getColumn(1).setResizable(false);
            tbl_salary.getColumnModel().getColumn(2).setResizable(false);
            tbl_salary.getColumnModel().getColumn(3).setResizable(false);
            tbl_salary.getColumnModel().getColumn(4).setResizable(false);
            tbl_salary.getColumnModel().getColumn(5).setResizable(false);
            tbl_salary.getColumnModel().getColumn(6).setResizable(false);
            tbl_salary.getColumnModel().getColumn(7).setResizable(false);
            tbl_salary.getColumnModel().getColumn(8).setResizable(false);
            tbl_salary.getColumnModel().getColumn(9).setResizable(false);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 250, 830, 470));

        btn_SalaryInvoiceGenerateReport.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_SalaryInvoiceGenerateReport.setText("Generate Report");
        btn_SalaryInvoiceGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SalaryInvoiceGenerateReportActionPerformed(evt);
            }
        });
        jPanel1.add(btn_SalaryInvoiceGenerateReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 730, -1, -1));

        lbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N
        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1400, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmb_dayChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_dayChooseActionPerformed
        comboSelectforDays();
    }//GEN-LAST:event_cmb_dayChooseActionPerformed

    private void cmb_salaryTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_salaryTypeActionPerformed
        salarySelectingCombo();
    }//GEN-LAST:event_cmb_salaryTypeActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        loadToSalaryAdvanceTable();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new Menu().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_SalaryInvoiceGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SalaryInvoiceGenerateReportActionPerformed
        //Daily, Monthly, Annualy, Custom
        String company_email = null, company_number = null, details_by;

        try {
            ResultSet rs = DB.getData("SELECT company_email,company_mobile,company_office FROM admin_panel");
            while (rs.next()) {
                company_email = rs.getString("company_email");
                company_number = rs.getString("company_mobile") + " " + rs.getString("company_office");

            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ASI - 0002-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
        String Details_By = null;
        String employeeID, employeeName, Salarydate, SalaryinvoiceID, workingHours, basicSalary, etfepf, monthlySalar, amount, Advance="00.00", AdvanceInvoice="-";
        double grossTotal,Total,TotalDeduction;
        if (tbl_salary.getRowCount() > 0) {
            for (int i = 0; i < tbl_salary.getRowCount(); i++) {

                employeeID = (String) tbl_salary.getValueAt(i, 1);
                SalaryinvoiceID = (String) tbl_salary.getValueAt(i, 0);
                employeeName = (String) tbl_salary.getValueAt(i, 2);
                Salarydate = (String) tbl_salary.getValueAt(i, 9);
                workingHours = (String) tbl_salary.getValueAt(i, 5);
                basicSalary = (String) tbl_salary.getValueAt(i, 6);
                etfepf = (String) tbl_salary.getValueAt(i, 7);
                amount = (String) tbl_salary.getValueAt(i, 8);

                if (tbl_salaryAdvance.getRowCount() > 0) {
                    for (int j = 0; j < tbl_salaryAdvance.getRowCount(); j++) {
                        String AdvaceDate = (String) tbl_salaryAdvance.getValueAt(j, 4);
                        String ADate = AdvaceDate.split("-")[0] + "-" + AdvaceDate.split("-")[1];
                        String SDate = Salarydate.split("-")[0] + "-" + Salarydate.split("-")[1];
                        if (ADate.equals(SDate)) {
                            Advance = (String) tbl_salaryAdvance.getValueAt(j, 3);
                            AdvanceInvoice = (String) tbl_salaryAdvance.getValueAt(j, 0);
                        } else {
                            Advance = "00.00";
                            AdvanceInvoice = "-";
                        }

                    }
                }
                grossTotal=Double.parseDouble(basicSalary)+Double.parseDouble(workingHours);
                TotalDeduction=Double.parseDouble(etfepf)+Double.parseDouble(Advance);
                Total=grossTotal-TotalDeduction;
                try {
                    ResultSet rs1 = DB.getData("SELECT f_name,l_name FROM employee_reg WHERE emp_id='" + employeeID + "'");
                    while (rs1.next()) {
                        String fname = rs1.getString("f_name");
                        String lname = rs1.getString("l_name");

                        employeeName = fname + " " + lname;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERROR - ASI - 0002-02  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }

                try {
                    InputStream is = this.getClass().getClassLoader().getResourceAsStream("\\Reports\\salarysheet.jrxml");
                    JasperDesign jd = JRXmlLoader.load(is);
                    JasperReport jr = JasperCompileManager.compileReport(jd);

                    Map<String, Object> p = new HashMap<String, Object>();

                    p.put("email", company_email);
                    p.put("numbers", company_number);
                    p.put("month", Salarydate);
                    p.put("emp_id", employeeID);
                    p.put("emp_Name", employeeName);
                    p.put("salaryInvoice", SalaryinvoiceID);
                    p.put("salaryAdvance", AdvanceInvoice);
                    p.put("basic", basicSalary + "");
                    p.put("working", workingHours + "");
                    p.put("epf", etfepf + "");
                    p.put("subTot", grossTotal + "");
                    p.put("grossPay", grossTotal + "");
                    p.put("advnce", Advance + "");
                    p.put("total", TotalDeduction + "");
                    p.put("netPay", Total + "");
                    p.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

                    JasperPrint jp = JasperFillManager.fillReport(jr, p, new JREmptyDataSource());
                    JasperViewer.viewReport(jp, false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERROR - ASI - 0002-03  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }

            }
        }


    }//GEN-LAST:event_btn_SalaryInvoiceGenerateReportActionPerformed

    private void popup_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_menuActionPerformed
      this.dispose();
      new Menu().setVisible(true);
    }//GEN-LAST:event_popup_menuActionPerformed

    private void popup_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_exitActionPerformed
       this.dispose();
    }//GEN-LAST:event_popup_exitActionPerformed

    private void popup_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_helpActionPerformed
        try {
            Desktop.getDesktop().open(new File("E:\\Syndicate\\Documentation\\Admin – Salary Invoice.pdf"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
                
    }//GEN-LAST:event_popup_helpActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel1MouseClicked

    private void pnl_headerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_headerMouseClicked
        if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_pnl_headerMouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
       if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel4MouseClicked

    private void txt_employeeIDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_employeeIDFocusGained
        txt_employeeID.setText("EMP");
    }//GEN-LAST:event_txt_employeeIDFocusGained

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
            java.util.logging.Logger.getLogger(SalaryInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalaryInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalaryInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalaryInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalaryInvoice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_SalaryInvoiceGenerateReport;
    private javax.swing.JButton btn_search;
    private javax.swing.JComboBox cmb_dayChoose;
    private javax.swing.JComboBox cmb_salaryType;
    private com.toedter.calendar.JDateChooser dc_from;
    private com.toedter.calendar.JMonthChooser dc_month;
    private com.toedter.calendar.JDateChooser dc_to;
    private com.toedter.calendar.JYearChooser dc_year;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_from;
    private javax.swing.JLabel lbl_month;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_to;
    private javax.swing.JLabel lbl_userType;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JLabel lbl_year;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem popup_exit;
    private javax.swing.JMenuItem popup_help;
    private javax.swing.JMenuItem popup_menu;
    private javax.swing.JTable tbl_salary;
    private javax.swing.JTable tbl_salaryAdvance;
    private javax.swing.JTextField txt_employeeID;
    // End of variables declaration//GEN-END:variables
}
