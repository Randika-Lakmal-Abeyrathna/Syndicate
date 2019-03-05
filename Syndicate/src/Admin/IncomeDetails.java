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
public class IncomeDetails extends javax.swing.JFrame {

    /**
     * Creates new form IncomeDetails
     */
    JDBC DB = new Classes.JDBC();
    ToolsClass tool = new Classes.ToolsClass();

    org.apache.log4j.Logger logError = org.apache.log4j.Logger.getLogger("ERROR in Admin Income Details ");

    public IncomeDetails() {
        initComponents();
        Menu();
        comboDay();
        comboClassFeeDay();
        loadTeacherName();
        setValuseFromAdmin();
        comboCompanyIncomeDay();
        tbl_all.setVisible(false);
        txt_classFeeNewAmount.setEnabled(false);
        txt_newAmount.setEnabled(false);
        txt_registrationFee.setEnabled(false);
        txt_commissinRate.setEnabled(false);
        txt_companyIncomeNewAmount.setEnabled(false);
        lbl_username.setText(Home.un);
        lbl_userType.setText(Home.ut);
        dc_companyIncomeFrom.setMaxSelectableDate(new Date());
        dc_companyIncomeTo.setMaxSelectableDate(new Date());
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

    //------------------Set Values from Admin Panel--------
    void setValuseFromAdmin() {
        try {
            ResultSet rs = DB.getData("SELECT * FROM admin_panel");
            while (rs.next()) {
                String studentRegistrationFee = rs.getString("student_reg_fee");
                String commisionRate = rs.getString("commision_rate");
                txt_registrationFee.setText(studentRegistrationFee);
                txt_commissinRate.setText(commisionRate);

            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - AID - 0001 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    //------------------End of Set Values from Admin Panel--------
    //--------------------------------Student Registration Income Start-----------------------------------------
    //Student Registration Combo box --- Day
    void comboDay() {
        //False
        dc_stuRegFrom.setVisible(false);
        dc_stuRegTo.setVisible(false);
        lbl_from.setVisible(false);
        lbl_to.setVisible(false);
        //True
        dc_stuRegYear.setVisible(true);
        dc_stuRegMonth.setVisible(true);
        dc_stuRegDay.setVisible(true);
        lbl_day.setVisible(true);
        lbl_month.setVisible(true);
        lbl_year.setVisible(true);

        dc_stuRegDay.setMaxSelectableDate(new Date());
    }

    // Student Registration combo box --- Month
    void comboMonth() {
        //False
        dc_stuRegDay.setVisible(false);
        dc_stuRegFrom.setVisible(false);
        dc_stuRegTo.setVisible(false);
        lbl_day.setVisible(false);
        lbl_from.setVisible(false);
        lbl_to.setVisible(false);
        //True
        dc_stuRegYear.setVisible(true);
        dc_stuRegMonth.setVisible(true);
        lbl_month.setVisible(true);
        lbl_year.setVisible(true);
    }

    // Student Registration combo box --- Annualy
    void comboAnnualy() {
        //False
        dc_stuRegDay.setVisible(false);
        dc_stuRegFrom.setVisible(false);
        dc_stuRegTo.setVisible(false);
        dc_stuRegMonth.setVisible(false);
        lbl_month.setVisible(false);
        lbl_day.setVisible(false);
        lbl_from.setVisible(false);
        lbl_to.setVisible(false);
        //True
        dc_stuRegYear.setVisible(true);
        lbl_year.setVisible(true);

        //TODO
        //Year Eka thoranakota Eka mae awrudda arae idiri awrudu walata hadanna epa
    }

    // Student Registration combo box --- Custom
    void comboCustom() {
        //False
        dc_stuRegDay.setVisible(false);
        lbl_day.setVisible(false);
        dc_stuRegMonth.setVisible(false);
        lbl_month.setVisible(false);
        dc_stuRegYear.setVisible(false);
        lbl_year.setVisible(false);
        //True
        dc_stuRegFrom.setVisible(true);
        dc_stuRegTo.setVisible(true);
        lbl_from.setVisible(true);
        lbl_to.setVisible(true);

        //set Max Date
        dc_stuRegTo.setMaxSelectableDate(new Date());
        dc_stuRegFrom.setMaxSelectableDate(new Date());

    }

    // ----------------------------------------End Of Student Registration Income----------------------------------------
    // ----------------------------------------Start Class Fee Income----------------------------------------
    //combo Class Fee search By --Day
    void comboClassFeeDay() {
        //False
        dc_classFeeFrom.setVisible(false);
        dc_classFeeTo.setVisible(false);
        lbl_classFeeFrom.setVisible(false);
        lbl_classFeeTo.setVisible(false);
        //True
        dc_classFeeYear.setVisible(true);
        dc_classFeeMonth.setVisible(true);
        dc_classFeeDay.setVisible(true);
        lbl_classFeeDay.setVisible(true);
        lbl_classFeeMonth.setVisible(true);
        lbl_classFeeYear.setVisible(true);

        dc_classFeeDay.setMaxSelectableDate(new Date());
    }

    //combo Class Fee search By --Month
    void comboClassFeeMonth() {
        //False
        dc_classFeeFrom.setVisible(false);
        dc_classFeeTo.setVisible(false);
        lbl_classFeeFrom.setVisible(false);
        lbl_classFeeTo.setVisible(false);
        dc_classFeeDay.setVisible(false);
        lbl_classFeeDay.setVisible(false);
        //True
        dc_classFeeYear.setVisible(true);
        dc_classFeeMonth.setVisible(true);
        lbl_classFeeMonth.setVisible(true);
        lbl_classFeeYear.setVisible(true);

    }

    //combo Class fee search by --- Annualy
    void comboClassFeeAnnualy() {
        //False
        dc_classFeeFrom.setVisible(false);
        dc_classFeeTo.setVisible(false);
        dc_classFeeMonth.setVisible(false);
        lbl_classFeeFrom.setVisible(false);
        lbl_classFeeTo.setVisible(false);
        dc_classFeeDay.setVisible(false);
        lbl_classFeeDay.setVisible(false);
        lbl_classFeeMonth.setVisible(false);
        //True
        dc_classFeeYear.setVisible(true);
        lbl_classFeeYear.setVisible(true);
    }

    //combo class Fee Sawerch by ---- Custom
    void comboClassFeeCustom() {
        //False
        dc_classFeeMonth.setVisible(false);
        dc_classFeeDay.setVisible(false);
        lbl_classFeeDay.setVisible(false);
        lbl_classFeeMonth.setVisible(false);
        dc_classFeeYear.setVisible(false);
        lbl_classFeeYear.setVisible(false);
        //True
        dc_classFeeFrom.setVisible(true);
        dc_classFeeTo.setVisible(true);
        lbl_classFeeFrom.setVisible(true);
        lbl_classFeeTo.setVisible(true);

        dc_classFeeFrom.setMaxSelectableDate(new Date());
        dc_classFeeTo.setMaxSelectableDate(new Date());
    }

    //Combo Teacher Name search Load
    void loadTeacherName() {
        try {
            ResultSet rs = DB.getData("SELECT * From teacher_reg ");
            Vector v = new Vector();
            while (rs.next()) {
                String teacherId = rs.getString("teacher_id");
                String teacherFName = rs.getString("f_name");
                String teacherNameAndId = teacherId + "-" + teacherFName;
                v.add(teacherNameAndId);
            }
            rs.close();
            for (int i = 0; i < v.size(); i++) {
                cmb_classFeeRateTeacherName.addItem(v.get(i));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - AID - 0002 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    //combo Teacher Class details Load on Click of Teacher Name Combo box
    void loadTeacherClass(String id) {
        try {
            cmb_classFeeRateClass.removeAllItems();
            ResultSet rs = DB.getData("SELECT * FROM teacher_time_table WHERE teacher_id='" + id + "'");
            Vector v = new Vector();
            while (rs.next()) {
                String subjectId = rs.getString("subject_id");
                String classFee = rs.getString("class_fee");
                ResultSet rs1 = DB.getData("SELECT * FROM subject WHERE subject_id='" + subjectId + "'");
                while (rs1.next()) {
                    String subjectName = rs1.getString("subject_name");
                    v.add(subjectId + "-" + subjectName + "-" + classFee);
                }
                rs1.close();
            }
            rs.close();
            for (int i = 0; i < v.size(); i++) {
                cmb_classFeeRateClass.addItem(v.get(i));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - AID - 0003 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

// ----------------------------------------End Of Class Fee Income----------------------------------------
// ----------------------------------------Class Income----------------------------------------
    //load Combo Search 
    void comboSearchInCompanyIncome(String Type1) {
        if (Type1.equals("Day")) {
            //Daily, Monthly, Annualy,Custom
            cmb_companyIncometype.setVisible(true);
            cmb_companyIncomeTypeResults.setVisible(false);
            cmb_companyIncometype.removeAllItems();
            Vector v = new Vector();
            v.add("Daily");
            v.add("Monthly");
            v.add("Annualy");
            v.add("Custom");

            for (int i = 0; i < v.size(); i++) {
                cmb_companyIncometype.addItem(v.get(i));

            }

            dc_companyIncomeDay.setVisible(true);
            dc_companyIncomeMonth.setVisible(true);
            dc_companyIncomeYear.setVisible(true);

            lbl_companyIncomeDay.setVisible(true);
            lbl_companyIncomeMonth.setVisible(true);
            lbl_companyIncomeYear.setVisible(true);

        } else if (Type1.equals("Type")) {
            // Student Registration Fee , Class Fee
            cmb_companyIncometype.setVisible(false);
            cmb_companyIncomeTypeResults.setVisible(true);
            cmb_companyIncomeTypeResults.removeAllItems();

            Vector v = new Vector();
            v.add("Registration Fee");
            v.add("Class Fee");

            for (int i = 0; i < v.size(); i++) {
                cmb_companyIncomeTypeResults.addItem(v.get(i));

            }

            dc_companyIncomeDay.setVisible(false);
            dc_companyIncomeMonth.setVisible(false);
            dc_companyIncomeYear.setVisible(false);
            dc_companyIncomeFrom.setVisible(false);
            dc_companyIncomeTo.setVisible(false);

            lbl_companyIncomeDay.setVisible(false);
            lbl_companyIncomeMonth.setVisible(false);
            lbl_companyIncomeYear.setVisible(false);
            lbl_companyIncomeFrom.setVisible(false);
            lbl_companyIncomeTo.setVisible(false);

        }
    }

    //combo Company Income search By --Day
    void comboCompanyIncomeDay() {
        //False
        dc_companyIncomeFrom.setVisible(false);
        dc_companyIncomeTo.setVisible(false);
        lbl_companyIncomeFrom.setVisible(false);
        lbl_companyIncomeTo.setVisible(false);
        //True
        dc_companyIncomeYear.setVisible(true);
        dc_companyIncomeMonth.setVisible(true);
        dc_companyIncomeDay.setVisible(true);
        lbl_companyIncomeDay.setVisible(true);
        lbl_companyIncomeMonth.setVisible(true);
        lbl_companyIncomeYear.setVisible(true);

        dc_companyIncomeDay.setMaxSelectableDate(new Date());
    }

    //combo Company Income search By --Month
    void comboCompanyIncomeMonth() {
        //False
        dc_companyIncomeFrom.setVisible(false);
        dc_companyIncomeTo.setVisible(false);
        dc_companyIncomeDay.setVisible(false);
        lbl_companyIncomeDay.setVisible(false);
        lbl_companyIncomeFrom.setVisible(false);
        lbl_companyIncomeTo.setVisible(false);
        //True
        dc_companyIncomeYear.setVisible(true);
        dc_companyIncomeMonth.setVisible(true);
        lbl_companyIncomeMonth.setVisible(true);
        lbl_companyIncomeYear.setVisible(true);

    }

    //combo Company Income search By --Annualy
    void comboCompanyIncomeAnnualy() {
        //False
        dc_companyIncomeFrom.setVisible(false);
        dc_companyIncomeTo.setVisible(false);
        dc_companyIncomeDay.setVisible(false);
        dc_companyIncomeMonth.setVisible(false);
        lbl_companyIncomeMonth.setVisible(false);
        lbl_companyIncomeDay.setVisible(false);
        lbl_companyIncomeFrom.setVisible(false);
        lbl_companyIncomeTo.setVisible(false);
        //True
        dc_companyIncomeYear.setVisible(true);
        lbl_companyIncomeYear.setVisible(true);

    }

    //combo Company Income search By --Custom
    void comboCompanyIncomeCustom() {
        //False
        dc_companyIncomeDay.setVisible(false);
        dc_companyIncomeMonth.setVisible(false);
        dc_companyIncomeYear.setVisible(false);
        lbl_companyIncomeDay.setVisible(false);
        lbl_companyIncomeMonth.setVisible(false);
        lbl_companyIncomeYear.setVisible(false);
        //True
        dc_companyIncomeFrom.setVisible(true);
        dc_companyIncomeTo.setVisible(true);
        lbl_companyIncomeFrom.setVisible(true);
        lbl_companyIncomeTo.setVisible(true);

    }

    // combo company Income type -- registraion fee & class Fee
    void comboCompanyIncomeFee() {
        //False
        dc_companyIncomeDay.setVisible(false);
        dc_companyIncomeYear.setVisible(false);
        dc_companyIncomeYear.setVisible(false);
        dc_companyIncomeFrom.setVisible(false);
        dc_companyIncomeTo.setVisible(false);
        lbl_companyIncomeYear.setVisible(false);
        lbl_companyIncomeDay.setVisible(false);
        lbl_companyIncomeYear.setVisible(false);
        lbl_companyIncomeFrom.setVisible(false);
        lbl_companyIncomeTo.setVisible(false);

    }

// ----------------------------------------End Of Class Income----------------------------------------
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
        pnl_header = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lbl_userType = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Income = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        pnl_studentRegistion = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txt_registrationFee = new javax.swing.JTextField();
        btn_edit = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txt_newAmount = new javax.swing.JTextField();
        btn_add = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        btn_stuRegSearch = new javax.swing.JButton();
        cmb_stuRegSearchBy = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        dc_stuRegYear = new com.toedter.calendar.JYearChooser();
        dc_stuRegFrom = new com.toedter.calendar.JDateChooser();
        dc_stuRegTo = new com.toedter.calendar.JDateChooser();
        dc_stuRegMonth = new com.toedter.calendar.JMonthChooser();
        dc_stuRegDay = new com.toedter.calendar.JDayChooser();
        lbl_year = new javax.swing.JLabel();
        lbl_month = new javax.swing.JLabel();
        lbl_from = new javax.swing.JLabel();
        lbl_to = new javax.swing.JLabel();
        lbl_day = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_stuReg = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        btn_StudentRegistrationGenerateReport = new javax.swing.JButton();
        lbl_stuRegTotal = new javax.swing.JLabel();
        pnl_classFees = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmb_classFeeRateTeacherName = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        cmb_classFeeRateClass = new javax.swing.JComboBox();
        txt_classFeeNewAmount = new javax.swing.JTextField();
        btn_classFeeAdd = new javax.swing.JButton();
        btn_classFeeEdit = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        cmb_calssFeeSearchBy = new javax.swing.JComboBox();
        btn_classFeeSearch = new javax.swing.JButton();
        dc_classFeeDay = new com.toedter.calendar.JDayChooser();
        dc_classFeeYear = new com.toedter.calendar.JYearChooser();
        dc_classFeeFrom = new com.toedter.calendar.JDateChooser();
        dc_classFeeTo = new com.toedter.calendar.JDateChooser();
        dc_classFeeMonth = new com.toedter.calendar.JMonthChooser();
        lbl_classFeeFrom = new javax.swing.JLabel();
        lbl_classFeeMonth = new javax.swing.JLabel();
        lbl_classFeeDay = new javax.swing.JLabel();
        lbl_classFeeYear = new javax.swing.JLabel();
        lbl_classFeeTo = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbl_classFee = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        lbl_classFeeTotal = new javax.swing.JLabel();
        btn_classFeeGenerateReport = new javax.swing.JButton();
        pnl_companyIncome = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txt_commissinRate = new javax.swing.JTextField();
        btn_companyIncomeAdd = new javax.swing.JButton();
        btn_companyIncomeEdit = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txt_companyIncomeNewAmount = new javax.swing.JTextField();
        jPanel38 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbl_totalIncome = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        lbl_companyIncomeTotal = new javax.swing.JLabel();
        btn_CompanyIncomeGenerateReport = new javax.swing.JButton();
        jPanel39 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        cmb_companyIncomeSearchBy = new javax.swing.JComboBox();
        btn_search = new javax.swing.JButton();
        cmb_companyIncometype = new javax.swing.JComboBox();
        dc_companyIncomeDay = new com.toedter.calendar.JDayChooser();
        dc_companyIncomeYear = new com.toedter.calendar.JYearChooser();
        dc_companyIncomeFrom = new com.toedter.calendar.JDateChooser();
        dc_companyIncomeMonth = new com.toedter.calendar.JMonthChooser();
        dc_companyIncomeTo = new com.toedter.calendar.JDateChooser();
        lbl_companyIncomeYear = new javax.swing.JLabel();
        lbl_companyIncomeMonth = new javax.swing.JLabel();
        lbl_companyIncomeDay = new javax.swing.JLabel();
        lbl_companyIncomeFrom = new javax.swing.JLabel();
        lbl_companyIncomeTo = new javax.swing.JLabel();
        cmb_companyIncomeTypeResults = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        cmb_profitCombo = new javax.swing.JComboBox();
        dc_profitMonth = new com.toedter.calendar.JMonthChooser();
        dc_profitYear = new com.toedter.calendar.JYearChooser();
        lbl_profitYear = new javax.swing.JLabel();
        lbl_profitMonth = new javax.swing.JLabel();
        dtn_profitSearch = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_profitIncomeDetails = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_profitExpenceDetails = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_all = new javax.swing.JTable();
        lbl_totalExpence = new javax.swing.JLabel();
        lbl_totalIncome = new javax.swing.JLabel();
        lbl_valuetotalIncome = new javax.swing.JLabel();
        lbl_valuetotalExpence = new javax.swing.JLabel();
        lbl_totalProfit = new javax.swing.JLabel();
        lbl_valuetotalProfit = new javax.swing.JLabel();
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
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jLabel12.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel12.setText("Admin Panel - Income Details");
        pnl_header.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 340, 30));

        lbl_date.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_date.setText("jLabel3");
        pnl_header.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, -1));

        lbl_time.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_time.setText("jLabel3");
        pnl_header.add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 23, 100, -1));

        getContentPane().add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 50));

        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Income.setOpaque(false);
        Income.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IncomeMouseClicked(evt);
            }
        });

        jTabbedPane3.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jTabbedPane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane3MouseClicked(evt);
            }
        });

        pnl_studentRegistion.setOpaque(false);
        pnl_studentRegistion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_studentRegistionMouseClicked(evt);
            }
        });

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registration Fees", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel22.setOpaque(false);
        jPanel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel22MouseClicked(evt);
            }
        });
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel8.setText("Registration Fee:");
        jPanel22.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        txt_registrationFee.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jPanel22.add(txt_registrationFee, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 160, -1));

        btn_edit.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_edit.setText("Edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        jPanel22.add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 80, -1));

        jLabel9.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel9.setText("New Amount :");
        jPanel22.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        txt_newAmount.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jPanel22.add(txt_newAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 160, -1));

        btn_add.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_add.setText("Add");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });
        jPanel22.add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 80, -1));

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel23.setOpaque(false);
        jPanel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel23MouseClicked(evt);
            }
        });
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_stuRegSearch.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_stuRegSearch.setText("Search");
        btn_stuRegSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_stuRegSearchActionPerformed(evt);
            }
        });
        jPanel23.add(btn_stuRegSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 130, -1, -1));

        cmb_stuRegSearchBy.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_stuRegSearchBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Daily", "Monthly", "Annualy", "Custom" }));
        cmb_stuRegSearchBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_stuRegSearchByActionPerformed(evt);
            }
        });
        jPanel23.add(cmb_stuRegSearchBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 28, 180, -1));

        jLabel10.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel10.setText("Search By :");
        jPanel23.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 30, -1, 23));

        dc_stuRegYear.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_stuRegYearFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_stuRegYearFocusLost(evt);
            }
        });
        jPanel23.add(dc_stuRegYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 110, 30));

        dc_stuRegFrom.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        dc_stuRegFrom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_stuRegFromFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_stuRegFromFocusLost(evt);
            }
        });
        jPanel23.add(dc_stuRegFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 150, 30));

        dc_stuRegTo.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        dc_stuRegTo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_stuRegToFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_stuRegToFocusLost(evt);
            }
        });
        jPanel23.add(dc_stuRegTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 150, 30));

        dc_stuRegMonth.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        dc_stuRegMonth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_stuRegMonthFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_stuRegMonthFocusLost(evt);
            }
        });
        jPanel23.add(dc_stuRegMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 110, 30));

        dc_stuRegDay.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel23.add(dc_stuRegDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 330, 140));

        lbl_year.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_year.setText("Year :");
        jPanel23.add(lbl_year, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        lbl_month.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_month.setText("Month :");
        jPanel23.add(lbl_month, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, -1, -1));

        lbl_from.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_from.setText("From :");
        jPanel23.add(lbl_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        lbl_to.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_to.setText("To :");
        jPanel23.add(lbl_to, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, -1, -1));

        lbl_day.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_day.setText("Day :");
        jPanel23.add(lbl_day, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, -1));

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Result", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel24.setOpaque(false);
        jPanel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel24MouseClicked(evt);
            }
        });

        tbl_stuReg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Student Name", "Date", "Amount", "Employee Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_stuReg.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tbl_stuReg);

        jLabel27.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel27.setText("Full Amount :");

        btn_StudentRegistrationGenerateReport.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_StudentRegistrationGenerateReport.setText("Generate Report");
        btn_StudentRegistrationGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_StudentRegistrationGenerateReportActionPerformed(evt);
            }
        });

        lbl_stuRegTotal.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        lbl_stuRegTotal.setText("00.00");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1307, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(32, 32, 32)
                                .addComponent(lbl_stuRegTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_StudentRegistrationGenerateReport, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_stuRegTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_StudentRegistrationGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_studentRegistionLayout = new javax.swing.GroupLayout(pnl_studentRegistion);
        pnl_studentRegistion.setLayout(pnl_studentRegistionLayout);
        pnl_studentRegistionLayout.setHorizontalGroup(
            pnl_studentRegistionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_studentRegistionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_studentRegistionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_studentRegistionLayout.createSequentialGroup()
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 814, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_studentRegistionLayout.createSequentialGroup()
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_studentRegistionLayout.setVerticalGroup(
            pnl_studentRegistionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_studentRegistionLayout.createSequentialGroup()
                .addGroup(pnl_studentRegistionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(265, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Student Registration", pnl_studentRegistion);

        pnl_classFees.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        pnl_classFees.setOpaque(false);
        pnl_classFees.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_classFeesMouseClicked(evt);
            }
        });

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Class fee Rates", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel25.setOpaque(false);
        jPanel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel25MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel5.setText("Teacher Name :");

        cmb_classFeeRateTeacherName.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_classFeeRateTeacherName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_classFeeRateTeacherNameActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel6.setText("Select Class :");

        cmb_classFeeRateClass.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        txt_classFeeNewAmount.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        btn_classFeeAdd.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_classFeeAdd.setText("Add");
        btn_classFeeAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_classFeeAddActionPerformed(evt);
            }
        });

        btn_classFeeEdit.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_classFeeEdit.setText("Edit");
        btn_classFeeEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_classFeeEditActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel7.setText("New Amount :");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmb_classFeeRateClass, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_classFeeRateTeacherName, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_classFeeNewAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_classFeeEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_classFeeAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_classFeeRateTeacherName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addComponent(cmb_classFeeRateClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_classFeeNewAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(btn_classFeeEdit))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_classFeeAdd)
                            .addComponent(jLabel7))))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel26.setOpaque(false);
        jPanel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel26MouseClicked(evt);
            }
        });
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel11.setText("Search By :");
        jPanel26.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        cmb_calssFeeSearchBy.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_calssFeeSearchBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Daily", "Monthly", "Annualy", "Custom" }));
        cmb_calssFeeSearchBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_calssFeeSearchByActionPerformed(evt);
            }
        });
        jPanel26.add(cmb_calssFeeSearchBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 210, 30));

        btn_classFeeSearch.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_classFeeSearch.setText("Search");
        btn_classFeeSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_classFeeSearchActionPerformed(evt);
            }
        });
        jPanel26.add(btn_classFeeSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 160, 80, -1));

        dc_classFeeDay.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel26.add(dc_classFeeDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 320, 150));

        dc_classFeeYear.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_classFeeYearFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_classFeeYearFocusLost(evt);
            }
        });
        jPanel26.add(dc_classFeeYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 110, 30));

        dc_classFeeFrom.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        dc_classFeeFrom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_classFeeFromFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_classFeeFromFocusLost(evt);
            }
        });
        jPanel26.add(dc_classFeeFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 150, 30));

        dc_classFeeTo.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        dc_classFeeTo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_classFeeToFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_classFeeToFocusLost(evt);
            }
        });
        jPanel26.add(dc_classFeeTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 150, 30));

        dc_classFeeMonth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_classFeeMonthFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_classFeeMonthFocusLost(evt);
            }
        });
        jPanel26.add(dc_classFeeMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 110, 30));

        lbl_classFeeFrom.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_classFeeFrom.setText("From :");
        jPanel26.add(lbl_classFeeFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 70, -1));

        lbl_classFeeMonth.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_classFeeMonth.setText("Month :");
        jPanel26.add(lbl_classFeeMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, -1, -1));

        lbl_classFeeDay.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_classFeeDay.setText("Day :");
        jPanel26.add(lbl_classFeeDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, 20));

        lbl_classFeeYear.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_classFeeYear.setText("Year :");
        jPanel26.add(lbl_classFeeYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 50, -1));

        lbl_classFeeTo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_classFeeTo.setText("To :");
        jPanel26.add(lbl_classFeeTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 70, -1));

        jPanel35.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Result", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel35.setOpaque(false);
        jPanel35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel35MouseClicked(evt);
            }
        });

        tbl_classFee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Student Name", "Subject", "Teacher Name", "Amount", "Date", "Employee Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_classFee.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tbl_classFee);

        jLabel29.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel29.setText("Full Amount :");

        lbl_classFeeTotal.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        lbl_classFeeTotal.setText("00.00");

        btn_classFeeGenerateReport.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_classFeeGenerateReport.setText("Generate Report");
        btn_classFeeGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_classFeeGenerateReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(28, 28, 28)
                        .addComponent(lbl_classFeeTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_classFeeGenerateReport, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_classFeeTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_classFeeGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_classFeesLayout = new javax.swing.GroupLayout(pnl_classFees);
        pnl_classFees.setLayout(pnl_classFeesLayout);
        pnl_classFeesLayout.setHorizontalGroup(
            pnl_classFeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_classFeesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_classFeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_classFeesLayout.createSequentialGroup()
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_classFeesLayout.setVerticalGroup(
            pnl_classFeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_classFeesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_classFeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(284, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Class Fees", pnl_classFees);

        pnl_companyIncome.setOpaque(false);
        pnl_companyIncome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_companyIncomeMouseClicked(evt);
            }
        });

        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Commision rate", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel29.setOpaque(false);

        jLabel13.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel13.setText("Commission Rate :");

        txt_commissinRate.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        btn_companyIncomeAdd.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_companyIncomeAdd.setText("Add");
        btn_companyIncomeAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_companyIncomeAddActionPerformed(evt);
            }
        });

        btn_companyIncomeEdit.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_companyIncomeEdit.setText("Edit");
        btn_companyIncomeEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_companyIncomeEditActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel15.setText("New Amount :");

        txt_companyIncomeNewAmount.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_commissinRate)
                    .addComponent(txt_companyIncomeNewAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_companyIncomeEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addComponent(btn_companyIncomeAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_commissinRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_companyIncomeEdit))
                .addGap(40, 40, 40)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txt_companyIncomeNewAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_companyIncomeAdd))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel38.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Result", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel38.setOpaque(false);

        tbl_totalIncome.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice No", "Type", "Amount", "Date", "Employee Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_totalIncome.getTableHeader().setReorderingAllowed(false);
        jScrollPane9.setViewportView(tbl_totalIncome);

        jLabel31.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel31.setText("Full Amount :");

        lbl_companyIncomeTotal.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        lbl_companyIncomeTotal.setText("00.00");

        btn_CompanyIncomeGenerateReport.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_CompanyIncomeGenerateReport.setText("Generate Report");
        btn_CompanyIncomeGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CompanyIncomeGenerateReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1288, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addGap(28, 28, 28)
                                .addComponent(lbl_companyIncomeTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_CompanyIncomeGenerateReport, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_companyIncomeTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addComponent(btn_CompanyIncomeGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel39.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel39.setOpaque(false);
        jPanel39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel32.setText("Search By :");
        jPanel39.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        cmb_companyIncomeSearchBy.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        cmb_companyIncomeSearchBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Day", "Type" }));
        cmb_companyIncomeSearchBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_companyIncomeSearchByActionPerformed(evt);
            }
        });
        jPanel39.add(cmb_companyIncomeSearchBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 210, 30));

        btn_search.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_search.setText("Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jPanel39.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 150, 90, -1));

        cmb_companyIncometype.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        cmb_companyIncometype.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Daily", "Monthly", "Annualy", "Custom" }));
        cmb_companyIncometype.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmb_companyIncometypeMousePressed(evt);
            }
        });
        cmb_companyIncometype.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_companyIncometypeItemStateChanged(evt);
            }
        });
        cmb_companyIncometype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_companyIncometypeActionPerformed(evt);
            }
        });
        cmb_companyIncometype.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmb_companyIncometypeFocusGained(evt);
            }
        });
        cmb_companyIncometype.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                cmb_companyIncometypeCaretPositionChanged(evt);
            }
        });
        jPanel39.add(cmb_companyIncometype, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 210, 30));

        dc_companyIncomeDay.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel39.add(dc_companyIncomeDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 320, 140));

        dc_companyIncomeYear.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_companyIncomeYearFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_companyIncomeYearFocusLost(evt);
            }
        });
        jPanel39.add(dc_companyIncomeYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 110, 30));

        dc_companyIncomeFrom.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        dc_companyIncomeFrom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_companyIncomeFromFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_companyIncomeFromFocusLost(evt);
            }
        });
        jPanel39.add(dc_companyIncomeFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 150, 30));

        dc_companyIncomeMonth.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        dc_companyIncomeMonth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_companyIncomeMonthFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_companyIncomeMonthFocusLost(evt);
            }
        });
        jPanel39.add(dc_companyIncomeMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 110, 30));

        dc_companyIncomeTo.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        dc_companyIncomeTo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_companyIncomeToFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_companyIncomeToFocusLost(evt);
            }
        });
        jPanel39.add(dc_companyIncomeTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 150, 30));

        lbl_companyIncomeYear.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_companyIncomeYear.setText("Year :");
        jPanel39.add(lbl_companyIncomeYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 50, -1));

        lbl_companyIncomeMonth.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_companyIncomeMonth.setText("Month :");
        jPanel39.add(lbl_companyIncomeMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, -1, -1));

        lbl_companyIncomeDay.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_companyIncomeDay.setText("Day :");
        jPanel39.add(lbl_companyIncomeDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, -1, 20));

        lbl_companyIncomeFrom.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_companyIncomeFrom.setText("From :");
        jPanel39.add(lbl_companyIncomeFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 80, -1));

        lbl_companyIncomeTo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_companyIncomeTo.setText("To :");
        jPanel39.add(lbl_companyIncomeTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, -1, -1));

        cmb_companyIncomeTypeResults.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        cmb_companyIncomeTypeResults.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Registration Fees", "Class Fees" }));
        cmb_companyIncomeTypeResults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_companyIncomeTypeResultsActionPerformed(evt);
            }
        });
        jPanel39.add(cmb_companyIncomeTypeResults, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 210, 30));

        javax.swing.GroupLayout pnl_companyIncomeLayout = new javax.swing.GroupLayout(pnl_companyIncome);
        pnl_companyIncome.setLayout(pnl_companyIncomeLayout);
        pnl_companyIncomeLayout.setHorizontalGroup(
            pnl_companyIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_companyIncomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_companyIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_companyIncomeLayout.createSequentialGroup()
                        .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        pnl_companyIncomeLayout.setVerticalGroup(
            pnl_companyIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_companyIncomeLayout.createSequentialGroup()
                .addGroup(pnl_companyIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(298, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Company Income", pnl_companyIncome);

        jPanel2.setOpaque(false);
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Search"));
        jPanel3.setOpaque(false);
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel33.setText("Search By :");

        cmb_profitCombo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_profitCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Monthly", "Annually" }));
        cmb_profitCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_profitComboActionPerformed(evt);
            }
        });

        dc_profitMonth.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        lbl_profitYear.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_profitYear.setText("Year :");

        lbl_profitMonth.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_profitMonth.setText("Month :");

        dtn_profitSearch.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        dtn_profitSearch.setText("Search");
        dtn_profitSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dtn_profitSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33)
                .addGap(41, 41, 41)
                .addComponent(cmb_profitCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(lbl_profitYear, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dc_profitYear, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(lbl_profitMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dc_profitMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(dtn_profitSearch)
                .addContainerGap(297, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_profitYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dc_profitYear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel33)
                                    .addComponent(cmb_profitCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lbl_profitMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(87, 87, 87))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dtn_profitSearch)
                            .addComponent(dc_profitMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Income Details", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel4.setOpaque(false);
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        tbl_profitIncomeDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Description", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_profitIncomeDetails.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_profitIncomeDetails);
        if (tbl_profitIncomeDetails.getColumnModel().getColumnCount() > 0) {
            tbl_profitIncomeDetails.getColumnModel().getColumn(0).setResizable(false);
            tbl_profitIncomeDetails.getColumnModel().getColumn(1).setResizable(false);
            tbl_profitIncomeDetails.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Expence Details", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel5.setOpaque(false);
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_profitExpenceDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Description", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_profitExpenceDetails.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tbl_profitExpenceDetails);
        if (tbl_profitExpenceDetails.getColumnModel().getColumnCount() > 0) {
            tbl_profitExpenceDetails.getColumnModel().getColumn(0).setResizable(false);
            tbl_profitExpenceDetails.getColumnModel().getColumn(1).setResizable(false);
            tbl_profitExpenceDetails.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 27, 666, 387));

        tbl_all.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Income Date", "Income Description", "Income Amount", "Expence Date", "Expence Description", "Expence Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbl_all);
        if (tbl_all.getColumnModel().getColumnCount() > 0) {
            tbl_all.getColumnModel().getColumn(0).setResizable(false);
            tbl_all.getColumnModel().getColumn(1).setResizable(false);
            tbl_all.getColumnModel().getColumn(2).setResizable(false);
            tbl_all.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel5.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, 150));

        lbl_totalExpence.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_totalExpence.setText("Total Expence :");

        lbl_totalIncome.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_totalIncome.setText("Total Income :");

        lbl_valuetotalIncome.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_valuetotalIncome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_valuetotalIncome.setText("00.00");

        lbl_valuetotalExpence.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_valuetotalExpence.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_valuetotalExpence.setText("00.00");

        lbl_totalProfit.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_totalProfit.setText("Total Profit :");

        lbl_valuetotalProfit.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_valuetotalProfit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_valuetotalProfit.setText("00.00");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_totalProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addComponent(lbl_totalIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_valuetotalIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_totalExpence, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_valuetotalExpence, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_valuetotalProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_totalExpence, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_totalIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_valuetotalIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_valuetotalExpence, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_totalProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_valuetotalProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(277, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Profit", jPanel2);

        javax.swing.GroupLayout IncomeLayout = new javax.swing.GroupLayout(Income);
        Income.setLayout(IncomeLayout);
        IncomeLayout.setHorizontalGroup(
            IncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IncomeLayout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 62, Short.MAX_VALUE))
        );
        IncomeLayout.setVerticalGroup(
            IncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IncomeLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );

        jPanel1.add(Income, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N
        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1400, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void dc_stuRegYearFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_stuRegYearFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_stuRegYearFocusGained

    private void dc_stuRegYearFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_stuRegYearFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_stuRegYearFocusLost

    private void dc_stuRegFromFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_stuRegFromFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_stuRegFromFocusGained

    private void dc_stuRegFromFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_stuRegFromFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_stuRegFromFocusLost

    private void dc_stuRegToFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_stuRegToFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_stuRegToFocusGained

    private void dc_stuRegToFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_stuRegToFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_stuRegToFocusLost

    private void dc_stuRegMonthFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_stuRegMonthFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_stuRegMonthFocusGained

    private void dc_stuRegMonthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_stuRegMonthFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_stuRegMonthFocusLost

    private void dc_classFeeYearFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_classFeeYearFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_classFeeYearFocusGained

    private void dc_classFeeYearFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_classFeeYearFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_classFeeYearFocusLost

    private void dc_classFeeFromFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_classFeeFromFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_classFeeFromFocusGained

    private void dc_classFeeFromFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_classFeeFromFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_classFeeFromFocusLost

    private void dc_classFeeToFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_classFeeToFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_classFeeToFocusGained

    private void dc_classFeeToFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_classFeeToFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_classFeeToFocusLost

    private void dc_classFeeMonthFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_classFeeMonthFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_classFeeMonthFocusGained

    private void dc_classFeeMonthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_classFeeMonthFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_classFeeMonthFocusLost

    private void dc_companyIncomeYearFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_companyIncomeYearFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_companyIncomeYearFocusGained

    private void dc_companyIncomeYearFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_companyIncomeYearFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_companyIncomeYearFocusLost

    private void dc_companyIncomeFromFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_companyIncomeFromFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_companyIncomeFromFocusGained

    private void dc_companyIncomeFromFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_companyIncomeFromFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_companyIncomeFromFocusLost

    private void dc_companyIncomeMonthFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_companyIncomeMonthFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_companyIncomeMonthFocusGained

    private void dc_companyIncomeMonthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_companyIncomeMonthFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_companyIncomeMonthFocusLost

    private void dc_companyIncomeToFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_companyIncomeToFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_companyIncomeToFocusGained

    private void dc_companyIncomeToFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_companyIncomeToFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_companyIncomeToFocusLost

    private void cmb_stuRegSearchByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_stuRegSearchByActionPerformed
        //Combo box Action
        //Daily, Monthly, Annualy,Custom
        if (cmb_stuRegSearchBy.getSelectedItem().equals("Daily")) {
            comboDay();
        } else if (cmb_stuRegSearchBy.getSelectedItem().equals("Monthly")) {
            comboMonth();
        } else if (cmb_stuRegSearchBy.getSelectedItem().equals("Annualy")) {
            comboAnnualy();
        } else if (cmb_stuRegSearchBy.getSelectedItem().equals("Custom")) {
            comboCustom();
        }

    }//GEN-LAST:event_cmb_stuRegSearchByActionPerformed

    private void cmb_calssFeeSearchByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_calssFeeSearchByActionPerformed
        //Combo box Class Fee Search by Action
        //Daily, Monthly, Annualy,Custom
        if (cmb_calssFeeSearchBy.getSelectedItem().equals("Daily")) {
            comboClassFeeDay();
        } else if (cmb_calssFeeSearchBy.getSelectedItem().equals("Monthly")) {
            comboClassFeeMonth();
        } else if (cmb_calssFeeSearchBy.getSelectedItem().equals("Annualy")) {
            comboClassFeeAnnualy();
        } else if (cmb_calssFeeSearchBy.getSelectedItem().equals("Custom")) {
            comboClassFeeCustom();
        }

    }//GEN-LAST:event_cmb_calssFeeSearchByActionPerformed

    private void cmb_classFeeRateTeacherNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_classFeeRateTeacherNameActionPerformed
        String selectedTeacher = cmb_classFeeRateTeacherName.getSelectedItem().toString();
        String teacherId = selectedTeacher.split("-")[0];
        loadTeacherClass(teacherId);
    }//GEN-LAST:event_cmb_classFeeRateTeacherNameActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        txt_newAmount.setEnabled(true);
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_classFeeEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_classFeeEditActionPerformed
        txt_classFeeNewAmount.setEnabled(true);
        String selectedIdem = cmb_classFeeRateClass.getSelectedItem().toString();
        String classFee = selectedIdem.split("-")[3];
        txt_classFeeNewAmount.setText(classFee);

    }//GEN-LAST:event_btn_classFeeEditActionPerformed

    private void cmb_companyIncomeSearchByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_companyIncomeSearchByActionPerformed
        String name = cmb_companyIncomeSearchBy.getSelectedItem().toString();
        comboSearchInCompanyIncome(name);
        System.out.println(name);
    }//GEN-LAST:event_cmb_companyIncomeSearchByActionPerformed

    private void cmb_companyIncometypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_companyIncometypeActionPerformed
        int select;
        if (cmb_companyIncomeSearchBy.getSelectedItem().toString().equals("Day")) {
            select = cmb_companyIncometype.getSelectedIndex();
            if (select == 0) {
                comboCompanyIncomeDay();
            } else if (select == 1) {
                comboCompanyIncomeMonth();
            } else if (select == 2) {
                comboCompanyIncomeAnnualy();
            } else if (select == 3) {
                comboCompanyIncomeCustom();
            } else {

            }
        } else if (cmb_companyIncomeSearchBy.getSelectedItem().toString().equals("Type")) {
//            select="no"; 
            select = cmb_companyIncometype.getSelectedIndex();
        }

    }//GEN-LAST:event_cmb_companyIncometypeActionPerformed

    private void cmb_companyIncometypeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmb_companyIncometypeMousePressed

    }//GEN-LAST:event_cmb_companyIncometypeMousePressed

    private void cmb_companyIncometypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_companyIncometypeItemStateChanged

    }//GEN-LAST:event_cmb_companyIncometypeItemStateChanged

    private void cmb_companyIncometypeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmb_companyIncometypeFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_cmb_companyIncometypeFocusGained

    private void cmb_companyIncometypeCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_cmb_companyIncometypeCaretPositionChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cmb_companyIncometypeCaretPositionChanged

    private void btn_stuRegSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_stuRegSearchActionPerformed
        //Student Registration Fee Serch Button
        String studentName;
        String studentId;
        String date;
        String amount;
        String employeeName;
        String selectCobo = cmb_stuRegSearchBy.getSelectedItem().toString();
        tool.clearTable(tbl_stuReg);
        if (selectCobo.equals("Daily")) {
            int year = dc_stuRegYear.getValue();
            int month = dc_stuRegMonth.getMonth() + 1;
            int day = dc_stuRegDay.getDay();
            String fullDay;
            if (month > 9) {
                fullDay = year + "-" + month;

            } else {
                fullDay = year + "-0" + month;

            }
            fullDay = fullDay + "-" + day;
            lbl_stuRegTotal.setText("00.00");
            try {
                ResultSet rs = DB.getData("SELECT * FROM income WHERE date='" + fullDay + "'");
                while (rs.next()) {
                    ResultSet rs1 = DB.getData("SELECT * FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");
                    while (rs1.next()) {
                        if (rs1.getString("type").toLowerCase().equals("reg")) {
                            ResultSet rs2 = DB.getData("SELECT * FROM student_reg WHERE student_id='" + rs1.getString("student_id") + "'");
                            while (rs2.next()) {
                                ResultSet rs3 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                                while (rs3.next()) {
                                    studentId = rs1.getString("student_id");
                                    studentName = rs2.getString("f_name") + " " + rs2.getString("l_name");
                                    date = rs.getString("date");
                                    amount = rs.getString("amount");
                                    employeeName = rs3.getString("f_name") + " " + rs3.getString("l_name");

                                    tool.addToTable(tbl_stuReg, studentId, studentName, date, amount, employeeName);

                                }
                                rs3.close();
                            }
                            rs2.close();
                        }
                    }
                    rs1.close();

                }
                rs.close();
                int Value = tool.getTotalValue(tbl_stuReg, 3);
                lbl_stuRegTotal.setText(Value + "");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AID - 0002-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }
//////////////////////////////////TODO
        } else if (selectCobo.equals("Monthly")) {
            int year = dc_stuRegYear.getValue();
            int month = dc_stuRegMonth.getMonth() + 1;
            String fullMonth;
            if (month > 9) {
                fullMonth = year + "-" + month;

            } else {
                fullMonth = year + "-0" + month;

            }
            System.out.println(fullMonth);
            lbl_stuRegTotal.setText("00.00");
            try {
                ResultSet rs = DB.getData("SELECT * FROM income where date LIKE'" + fullMonth + "%'");
                while (rs.next()) {
                    ResultSet rs1 = DB.getData("SELECT * FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");
                    while (rs1.next()) {
                        if (rs1.getString("type").toLowerCase().equals("reg")) {
                            ResultSet rs2 = DB.getData("SELECT * FROM student_reg WHERE student_id='" + rs1.getString("student_id") + "'");
                            while (rs2.next()) {
                                ResultSet rs3 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                                while (rs3.next()) {
                                    studentId = rs1.getString("student_id");
                                    studentName = rs2.getString("f_name") + " " + rs2.getString("l_name");
                                    date = rs.getString("date");
                                    amount = rs.getString("amount");
                                    employeeName = rs3.getString("f_name") + " " + rs3.getString("l_name");

                                    tool.addToTable(tbl_stuReg, studentId, studentName, date, amount, employeeName);

                                }
                                rs3.close();
                            }
                            rs2.close();
                        }
                    }
                    rs1.close();

                }
                rs.close();
                int Value = tool.getTotalValue(tbl_stuReg, 3);
                lbl_stuRegTotal.setText(Value + "");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AID - 0004-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

        } else if (selectCobo.equals("Annualy")) {
            int year = dc_stuRegYear.getValue();

            String fullYear = year + "";
            lbl_stuRegTotal.setText("00.00");
            try {

                ResultSet rs = DB.getData("SELECT * FROM income where date LIKE'" + fullYear + "%'");
                while (rs.next()) {

                    ResultSet rs1 = DB.getData("SELECT * FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");

                    while (rs1.next()) {
                        if (rs1.getString("type").toLowerCase().equals("reg")) {

                            ResultSet rs2 = DB.getData("SELECT * FROM student_reg WHERE student_id='" + rs1.getString("student_id") + "'");
                            while (rs2.next()) {

                                ResultSet rs3 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                                while (rs3.next()) {
                                    studentId = rs1.getString("student_id");
                                    studentName = rs2.getString("f_name") + " " + rs2.getString("l_name");
                                    date = rs.getString("date");
                                    amount = rs.getString("amount");
                                    employeeName = rs3.getString("f_name") + " " + rs3.getString("l_name");

                                    tool.addToTable(tbl_stuReg, studentId, studentName, date, amount, employeeName);

                                }
                                rs3.close();
                            }
                            rs2.close();
                        }
                    }
                    rs1.close();

                }
                rs.close();
                int Value = tool.getTotalValue(tbl_stuReg, 3);
                lbl_stuRegTotal.setText(Value + "");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AID - 0004-03  \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }
        } else if (selectCobo.equals("Custom")) {
            tool.clearTable(tbl_stuReg);
            Date d = new Date();
            Date from = dc_stuRegFrom.getDate();
            Date to = dc_stuRegTo.getDate();
            String fromString = from + "";
            String ToString = to + "";
            lbl_stuRegTotal.setText("00.00");
            if (fromString.equals(null + "") || ToString.equals(null + "")) {
                JOptionPane.showMessageDialog(this, "Please Enter the Date");
            } else if (!(fromString.equals(null + "") && ToString.equals(null + ""))) {
                String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(from);
                String toDate = new SimpleDateFormat("yyyy-MM-dd").format(to);
                try {
                    ResultSet rs = DB.getData("SELECT * FROM income where date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
                    while (rs.next()) {

                        ResultSet rs1 = DB.getData("SELECT * FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");

                        while (rs1.next()) {
                            if (rs1.getString("type").toLowerCase().equals("reg")) {

                                ResultSet rs2 = DB.getData("SELECT * FROM student_reg WHERE student_id='" + rs1.getString("student_id") + "'");
                                while (rs2.next()) {

                                    ResultSet rs3 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                                    while (rs3.next()) {
                                        studentId = rs1.getString("student_id");
                                        studentName = rs2.getString("f_name") + " " + rs2.getString("l_name");
                                        date = rs.getString("date");
                                        amount = rs.getString("amount");
                                        employeeName = rs3.getString("f_name") + " " + rs3.getString("l_name");

                                        tool.addToTable(tbl_stuReg, studentId, studentName, date, amount, employeeName);

                                    }
                                    rs3.close();
                                }
                                rs2.close();
                            }
                        }
                        rs1.close();

                    }
                    rs.close();
                    int Value = tool.getTotalValue(tbl_stuReg, 3);
                    lbl_stuRegTotal.setText(Value + "");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERROR - AID - 0004-04 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }

        }
    }//GEN-LAST:event_btn_stuRegSearchActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        String newAmountOfRegistrationFee = txt_newAmount.getText();
        try {
            DB.putData("UPDATE admin_panel student_registration_fee='" + newAmountOfRegistrationFee + "'");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - AID - 0005 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }

    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_classFeeSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_classFeeSearchActionPerformed
        //Class Fees Search Button
        String studentName;
        String studentId;
        String subject;
        String teacherName;
        String date;
        String amount;
        String employeeName;
        String selectCobo = cmb_calssFeeSearchBy.getSelectedItem().toString();
        tool.clearTable(tbl_classFee);
        if (selectCobo.equals("Daily")) {
            int year = dc_classFeeYear.getValue();
            int month = dc_classFeeMonth.getMonth() + 1;
            int day = dc_classFeeDay.getDay();

            String fullDay;
            if (month > 9) {
                fullDay = year + "-" + month;

            } else {
                fullDay = year + "-0" + month;

            }
            fullDay = fullDay + "-" + day;
            lbl_classFeeTotal.setText("00.00");
            try {
                ResultSet rs = DB.getData("SELECT * FROM income WHERE date='" + fullDay + "'");
                while (rs.next()) {
                    ResultSet rs1 = DB.getData("SELECT * FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");
                    while (rs1.next()) {
                        if (rs1.getString("type").toLowerCase().equals("class")) {
                            ResultSet rs2 = DB.getData("SELECT * FROM student_reg WHERE student_id='" + rs1.getString("student_id") + "'");
                            while (rs2.next()) {
                                ResultSet rs3 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                                while (rs3.next()) {
                                    ResultSet rs4 = DB.getData("SELECT * FROM student_payment WHERE student_payment_id='" + rs1.getString("student_payment_id") + "'");
                                    while (rs4.next()) {
                                        ResultSet rs5 = DB.getData("SELECT * FROM class_details WHERE class_id='" + rs4.getString("class_id") + "'");
                                        while (rs5.next()) {
                                            ResultSet rs6 = DB.getData("SELECT * FROM subject WHERE subject_id='" + rs5.getString("subject_id") + "'");
                                            while (rs6.next()) {
                                                ResultSet rs7 = DB.getData("SELECT * FROM teacher_reg WHERE teacher_id='" + rs5.getString("teacher_id") + "'");
                                                while (rs7.next()) {
                                                    studentId = rs1.getString("student_id");
                                                    studentName = rs2.getString("f_name") + " " + rs2.getString("l_name");
                                                    subject = rs6.getString("subject_name");
                                                    teacherName = rs7.getString("f_name") + " " + rs7.getString("l_name");
                                                    amount = rs.getString("amount");
                                                    date = rs.getString("date");
                                                    employeeName = rs3.getString("f_name") + " " + rs3.getString("l_name");

                                                    tool.addToTable(tbl_classFee, studentId, studentName, subject, teacherName, amount, date, employeeName);

                                                }
                                                rs7.close();

                                            }
                                            rs6.close();
                                        }
                                        rs5.close();
                                    }
                                    rs4.close();
                                }
                                rs3.close();
                            }
                            rs2.close();
                        }
                    }
                    rs1.close();
                }
                rs.close();
                int Value = tool.getTotalValue(tbl_classFee, 4);
                lbl_classFeeTotal.setText(Value + "");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AID - 0006-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

        } else if (selectCobo.equals("Monthly")) {
            int year = dc_classFeeYear.getValue();
            int month = dc_classFeeMonth.getMonth() + 1;

            String fullMonth;
            if (month > 9) {
                fullMonth = year + "-" + month;

            } else {
                fullMonth = year + "-0" + month;

            }
            lbl_classFeeTotal.setText("00.00");
            try {
                ResultSet rs = DB.getData("SELECT * FROM income where date LIKE'" + fullMonth + "%'");
                while (rs.next()) {

                    ResultSet rs1 = DB.getData("SELECT * FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");
                    while (rs1.next()) {
                        if (rs1.getString("type").toLowerCase().equals("class")) {
                            ResultSet rs2 = DB.getData("SELECT * FROM student_reg WHERE student_id='" + rs1.getString("student_id") + "'");
                            while (rs2.next()) {
                                ResultSet rs3 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                                while (rs3.next()) {
                                    ResultSet rs4 = DB.getData("SELECT * FROM student_payment WHERE student_payment_id='" + rs1.getString("student_payment_id") + "'");
                                    while (rs4.next()) {
                                        ResultSet rs5 = DB.getData("SELECT * FROM class_details WHERE class_id='" + rs4.getString("class_id") + "'");
                                        while (rs5.next()) {
                                            ResultSet rs6 = DB.getData("SELECT * FROM subject WHERE subject_id='" + rs5.getString("subject_id") + "'");
                                            while (rs6.next()) {
                                                ResultSet rs7 = DB.getData("SELECT * FROM teacher_reg WHERE teacher_id='" + rs5.getString("teacher_id") + "'");
                                                while (rs7.next()) {
                                                    studentId = rs1.getString("student_id");
                                                    studentName = rs2.getString("f_name") + " " + rs2.getString("l_name");
                                                    subject = rs6.getString("subject_name");
                                                    teacherName = rs7.getString("f_name") + " " + rs7.getString("l_name");
                                                    amount = rs.getString("amount");
                                                    date = rs.getString("date");
                                                    employeeName = rs3.getString("f_name") + " " + rs3.getString("l_name");

                                                    tool.addToTable(tbl_classFee, studentId, studentName, subject, teacherName, amount, date, employeeName);

                                                }
                                                rs7.close();

                                            }
                                            rs6.close();
                                        }
                                        rs5.close();
                                    }
                                    rs4.close();
                                }
                                rs3.close();
                            }
                            rs2.close();
                        }
                    }
                    rs1.close();
                }
                rs.close();
                int Value = tool.getTotalValue(tbl_classFee, 4);
                lbl_classFeeTotal.setText(Value + "");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AID - 0006-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

        } else if (selectCobo.equals("Annualy")) {
            int year = dc_classFeeYear.getValue();

            String fullYear = year + "";
            lbl_classFeeTotal.setText("00.00");
            try {
                ResultSet rs = DB.getData("SELECT * FROM income where date LIKE'" + fullYear + "%'");
                while (rs.next()) {
                    ResultSet rs1 = DB.getData("SELECT * FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");
                    while (rs1.next()) {
                        if (rs1.getString("type").toLowerCase().equals("class")) {
                            ResultSet rs2 = DB.getData("SELECT * FROM student_reg WHERE student_id='" + rs1.getString("student_id") + "'");
                            while (rs2.next()) {
                                ResultSet rs3 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                                while (rs3.next()) {
                                    ResultSet rs4 = DB.getData("SELECT * FROM student_payment WHERE student_payment_id='" + rs1.getString("student_payment_id") + "'");
                                    while (rs4.next()) {
                                        ResultSet rs5 = DB.getData("SELECT * FROM class_details WHERE class_id='" + rs4.getString("class_id") + "'");
                                        while (rs5.next()) {
                                            ResultSet rs6 = DB.getData("SELECT * FROM subject WHERE subject_id='" + rs5.getString("subject_id") + "'");
                                            while (rs6.next()) {
                                                ResultSet rs7 = DB.getData("SELECT * FROM teacher_reg WHERE teacher_id='" + rs5.getString("teacher_id") + "'");
                                                while (rs7.next()) {
                                                    studentId = rs1.getString("student_id");
                                                    studentName = rs2.getString("f_name") + " " + rs2.getString("l_name");
                                                    subject = rs6.getString("subject_name");
                                                    teacherName = rs7.getString("f_name") + " " + rs7.getString("l_name");
                                                    amount = rs.getString("amount");
                                                    date = rs.getString("date");
                                                    employeeName = rs3.getString("f_name") + " " + rs3.getString("l_name");

                                                    tool.addToTable(tbl_classFee, studentId, studentName, subject, teacherName, amount, date, employeeName);

                                                }
                                                rs7.close();

                                            }
                                            rs6.close();
                                        }
                                        rs5.close();
                                    }
                                    rs4.close();
                                }
                                rs3.close();
                            }
                            rs2.close();
                        }
                    }
                    rs1.close();
                }
                rs.close();
                int Value = tool.getTotalValue(tbl_classFee, 4);
                lbl_classFeeTotal.setText(Value + "");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AID - 0006-03 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }
        } else if (selectCobo.equals("Custom")) {
            Date d = new Date();
            Date from = dc_classFeeFrom.getDate();
            Date to = dc_classFeeTo.getDate();
            String Stringfrom = from + "";
            String Stringto = to + "";

            lbl_classFeeTotal.setText("00.00");
            if (Stringfrom.equals(null + "") || Stringto.equals(null + "")) {
                JOptionPane.showMessageDialog(this, "Please Enter The Date");
            } else {
                try {
                    String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(from);
                    String toDate = new SimpleDateFormat("yyyy-MM-dd").format(to);
                    ResultSet rs = DB.getData("SELECT * FROM income where date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
                    while (rs.next()) {
                        System.out.println(rs.getString("student_invoice_id"));
                        ResultSet rs1 = DB.getData("SELECT * FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");//System.out.println(rs1.next());
                        while (rs1.next()) {
                            String type = rs1.getString("type").toLowerCase();
                            System.out.println(type);
                            if (type.equals("class")) {
                                ResultSet rs2 = DB.getData("SELECT * FROM student_reg WHERE student_id='" + rs1.getString("student_id") + "'");
                                while (rs2.next()) {
                                    ResultSet rs3 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                                    while (rs3.next()) {
                                        ResultSet rs4 = DB.getData("SELECT * FROM student_payment WHERE student_payment_id='" + rs1.getString("student_payment_id") + "'");
                                        while (rs4.next()) {
                                            ResultSet rs5 = DB.getData("SELECT * FROM class_details WHERE class_id='" + rs4.getString("class_id") + "'");
                                            while (rs5.next()) {
                                                ResultSet rs6 = DB.getData("SELECT * FROM subject WHERE subject_id='" + rs5.getString("subject_id") + "'");
                                                while (rs6.next()) {
                                                    ResultSet rs7 = DB.getData("SELECT * FROM teacher_reg WHERE teacher_id='" + rs5.getString("teacher_id") + "'");
                                                    while (rs7.next()) {
                                                        studentId = rs1.getString("student_id");
                                                        studentName = rs2.getString("f_name") + " " + rs2.getString("l_name");
                                                        subject = rs6.getString("subject_name");
                                                        teacherName = rs7.getString("f_name") + " " + rs7.getString("l_name");
                                                        amount = rs.getString("amount");
                                                        date = rs.getString("date");
                                                        employeeName = rs3.getString("f_name") + " " + rs3.getString("l_name");

                                                        tool.addToTable(tbl_classFee, studentId, studentName, subject, teacherName, amount, date, employeeName);

                                                    }
                                                    rs7.close();

                                                }
                                                rs6.close();
                                            }
                                            rs5.close();
                                        }
                                        rs4.close();
                                    }
                                    rs3.close();
                                }
                                rs2.close();
                            }
                        }
                        rs1.close();
                    }
                    rs.close();
                    int Value = tool.getTotalValue(tbl_classFee, 4);
                    lbl_classFeeTotal.setText(Value + "");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERROR - AID - 0006-04 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
        }
    }//GEN-LAST:event_btn_classFeeSearchActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        //total Income Details
        //Daily, Monthly, Annualy, Custom
        String type = null;
        if (cmb_companyIncomeTypeResults.isShowing()) {
            type = cmb_companyIncomeTypeResults.getSelectedItem().toString();
        }
        if (cmb_companyIncometype.isVisible()) {
            type = cmb_companyIncometype.getSelectedItem().toString();
        }
        String searchBy = cmb_companyIncomeSearchBy.getSelectedItem().toString();
        String invoiceNo;
        String feeType;
        double amount;
        String date;
        String employeeName;
        tool.clearTable(tbl_totalIncome);

        if (searchBy.equals("Day")) {

            if (type.equals("Daily")) {
                int year = dc_companyIncomeYear.getValue();
                int month = dc_companyIncomeMonth.getMonth() + 1;
                int day = dc_companyIncomeDay.getDay();

                String fullDay;
                if (month > 9) {
                    fullDay = year + "-" + month;

                } else {
                    fullDay = year + "-0" + month;

                }
                fullDay = fullDay + "-" + day;
                lbl_companyIncomeTotal.setText("00.00");
                try {
                    ResultSet rs = DB.getData("SELECT * FROM income WHERE date='" + fullDay + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT type FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");
                        while (rs1.next()) {
                            ResultSet rs2 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                            while (rs2.next()) {

                                invoiceNo = rs.getString("student_invoice_id");
                                feeType = rs1.getString("type").toLowerCase();
                                amount = Double.parseDouble(rs.getString("amount"));

                                date = rs.getString("date");

                                employeeName = rs2.getString("f_name") + " " + rs2.getString("l_name");

                                tool.addToTable(tbl_totalIncome, invoiceNo, feeType, amount + "", date, employeeName);
                            }
                            rs2.close();
                        }
                        rs1.close();

                    }
                    rs.close();
                    double Value = tool.getTotalValueInDouble(tbl_totalIncome, 2);
                    lbl_companyIncomeTotal.setText(Value + "");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERROR - AID - 0007-01-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }

            } else if (type.equals("Monthly")) {
                int year = dc_companyIncomeYear.getValue();
                int month = dc_companyIncomeMonth.getMonth() + 1;

                String fullMonth;
                if (month > 9) {
                    fullMonth = year + "-" + month;

                } else {
                    fullMonth = year + "-0" + month;

                }
                lbl_companyIncomeTotal.setText("00.00");
                try {
                    ResultSet rs = DB.getData("SELECT * FROM income where date LIKE'" + fullMonth + "%'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT type FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");
                        while (rs1.next()) {
                            ResultSet rs2 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                            while (rs2.next()) {

                                invoiceNo = rs.getString("student_invoice_id");
                                feeType = rs1.getString("type").toLowerCase();
                                date = rs.getString("date");
                                amount = Double.parseDouble(rs.getString("amount"));

                                employeeName = rs2.getString("f_name") + " " + rs2.getString("l_name");

                                tool.addToTable(tbl_totalIncome, invoiceNo, feeType, amount + "", date, employeeName);
                            }
                            rs2.close();
                        }
                        rs1.close();

                    }
                    rs.close();
                    double Value = tool.getTotalValueInDouble(tbl_totalIncome, 2);
                    lbl_companyIncomeTotal.setText(Value + "");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERROR - AID - 0007-01-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }

            } else if (type.equals("Annualy")) {
                int year = dc_companyIncomeYear.getValue();

                String fullYear = year + "";
                lbl_companyIncomeTotal.setText("00.00");
                try {
                    ResultSet rs = DB.getData("SELECT * FROM income where date LIKE'" + fullYear + "%'");

                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT type FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");
                        while (rs1.next()) {
                            ResultSet rs2 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                            while (rs2.next()) {
                                invoiceNo = rs.getString("student_invoice_id");
                                feeType = rs1.getString("type").toLowerCase();
                                date = rs.getString("date");
                                amount = Double.parseDouble(rs.getString("amount"));

                                employeeName = rs2.getString("f_name") + " " + rs2.getString("l_name");

                                tool.addToTable(tbl_totalIncome, invoiceNo, feeType, amount + "", date, employeeName);
                            }
                            rs2.close();
                        }
                        rs1.close();

                    }
                    rs.close();
                    double value = tool.getTotalValueInDouble(tbl_totalIncome, 2);
                    lbl_companyIncomeTotal.setText(value + "");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERROR - AID - 0007-01-03 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            } else if (type.equals("Custom")) {
                Date d = new Date();
                Date from = dc_companyIncomeFrom.getDate();
                Date to = dc_companyIncomeTo.getDate();
                String Stringfrom = from + "";
                String Stringto = to + "";
                lbl_companyIncomeTotal.setText("00.00");
                if (Stringfrom.equals(null + "") || Stringto.equals(null + "")) {
                    JOptionPane.showMessageDialog(this, "Please Enter the date");
                } else {
                    try {
                        String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(from);
                        String toDate = new SimpleDateFormat("yyyy-MM-dd").format(to);
                        ResultSet rs = DB.getData("SELECT * FROM income where date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
                        while (rs.next()) {
                            ResultSet rs1 = DB.getData("SELECT type FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");
                            while (rs1.next()) {
                                ResultSet rs2 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                                while (rs2.next()) {

                                    invoiceNo = rs.getString("student_invoice_id");
                                    feeType = rs1.getString("type").toLowerCase();
                                    date = rs.getString("date");
                                    amount = Double.parseDouble(rs.getString("amount"));

                                    employeeName = rs2.getString("f_name") + " " + rs2.getString("l_name");

                                    tool.addToTable(tbl_totalIncome, invoiceNo, feeType, amount + "", date, employeeName);
                                }
                                rs2.close();
                            }
                            rs1.close();

                        }
                        rs.close();
                        double Value = tool.getTotalValueInDouble(tbl_totalIncome, 2);
                        System.out.println(Value + "");
                        lbl_companyIncomeTotal.setText(Value + "");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "ERROR - AID - 0007-01-04 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                        logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    }
                }
            }
        } else {
            //Registration Fees, Class Fees
            ResultSet rs1;
            String paymentType;
            if (type.equals("Registration Fee")) {
                lbl_companyIncomeTotal.setText("00.00");
                try {
                    ResultSet rs = DB.getData("SELECT * FROM income");
                    while (rs.next()) {
                        rs1 = DB.getData("SELECT * FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");
                        while (rs1.next()) {
                            ResultSet rs2 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                            while (rs2.next()) {
                                paymentType = rs1.getString("type").toLowerCase();
                                if (paymentType.equals("reg")) {
                                    invoiceNo = rs.getString("student_invoice_id");
                                    feeType = paymentType;
                                    amount = Double.parseDouble(rs.getString("amount"));
                                    date = rs.getString("date");
                                    employeeName = rs2.getString("f_name") + " " + rs2.getString("l_name");
 
                                    tool.addToTable(tbl_totalIncome, invoiceNo, feeType, amount + "", date, employeeName);
                                }
                            }
                            rs2.close();

                        }
                        rs1.close();
                    }
                    rs.close();
                    double Value = tool.getTotalValueInDouble(tbl_totalIncome, 2);
                    lbl_companyIncomeTotal.setText(Value + "");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERROR - AID - 0007-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            } else if (type.equals("Class Fee")) {
                lbl_companyIncomeTotal.setText("00.00");
                try {
                    ResultSet rs = DB.getData("SELECT * FROM income");
                    while (rs.next()) {
                        rs1 = DB.getData("SELECT * FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");
                        while (rs1.next()) {
                            ResultSet rs2 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "' ");
                            while (rs2.next()) {
                                paymentType = rs1.getString("type").toLowerCase();

                                if (paymentType.equals("class")) {
                                    invoiceNo = rs.getString("student_invoice_id");
                                    feeType = paymentType;
                                    String comi = txt_commissinRate.getText();
                                    amount = Double.parseDouble(rs.getString("amount"));
                                    //String tot = ((Double.parseDouble(amount) * Double.parseDouble(comi)) / 100) + "";
                                    date = rs.getString("date");
                                    employeeName = rs2.getString("f_name") + " " + rs2.getString("l_name");

                                    tool.addToTable(tbl_totalIncome, invoiceNo, feeType, amount + "", date, employeeName);
                                }
                            }
                            rs2.close();

                        }
                        rs1.close();
                    }
                    rs.close();
                    double Value = tool.getTotalValueInDouble(tbl_totalIncome, 2);
                    lbl_companyIncomeTotal.setText(Value + "");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERROR - AID - 0007-03 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
        }
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_classFeeAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_classFeeAddActionPerformed
        if (cmb_classFeeRateTeacherName.getItemCount() > 0 && cmb_classFeeRateClass.getItemCount() > 0) {
            String teacherName = cmb_classFeeRateTeacherName.getSelectedItem().toString();
            String teacherId = teacherName.split("-")[0];
            String subject = cmb_classFeeRateClass.getSelectedItem().toString();
            String subjectId = subject.split("-")[0];

            try {
                DB.putData("UPDATE teacher_time_table set class_fee='" + txt_classFeeNewAmount.getText() + "' WHERE teacher_id='" + teacherId + "' AND subject_id='" + subjectId + "' ");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AID - 0008 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

            loadTeacherClass(cmb_classFeeRateTeacherName.getSelectedItem().toString());
            txt_classFeeNewAmount.setEnabled(false);
        }

    }//GEN-LAST:event_btn_classFeeAddActionPerformed

    private void cmb_companyIncomeTypeResultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_companyIncomeTypeResultsActionPerformed

    }//GEN-LAST:event_cmb_companyIncomeTypeResultsActionPerformed

    private void btn_companyIncomeAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_companyIncomeAddActionPerformed
        if (!(txt_companyIncomeNewAmount.getText().equals(""))) {
            try {
                DB.putData("UPDATE admin_panel SET commision_rate='" + txt_companyIncomeNewAmount.getText() + "' ");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AID - 0009 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }
            txt_companyIncomeNewAmount.setEnabled(false);
            setValuseFromAdmin();
        }
    }//GEN-LAST:event_btn_companyIncomeAddActionPerformed

    private void btn_companyIncomeEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_companyIncomeEditActionPerformed
        txt_companyIncomeNewAmount.setEnabled(true);
    }//GEN-LAST:event_btn_companyIncomeEditActionPerformed

    private void btn_StudentRegistrationGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_StudentRegistrationGenerateReportActionPerformed
        //Daily, Monthly, Annualy, Custom
        String company_email = null, company_number = null, details_by;

        try {
            ResultSet rs = DB.getData("SELECT company_email,company_mobile,company_office FROM admin_panel");
            while (rs.next()) {
                company_email = rs.getString("company_email");
                company_number = rs.getString("company_mobile") + " " + rs.getString("company_office");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - AID - 0010-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
        String Details_By = null;
        details_by = cmb_stuRegSearchBy.getSelectedItem().toString().trim();
        if (details_by.equals("Custom")) {
            String FromDate = new SimpleDateFormat("yyyy-MM-dd").format(dc_stuRegFrom.getDate());
            String ToDate = new SimpleDateFormat("yyyy-MM-dd").format(dc_stuRegTo.getDate());

            Details_By = "Custom Date : From-" + FromDate + " To-" + ToDate;
        } else if (details_by.equals("Daily")) {
            int year = dc_stuRegYear.getValue();
            int month = dc_stuRegMonth.getMonth() + 1;
            int day = dc_stuRegDay.getDay();
            String fullDay;
            if (month > 9) {
                fullDay = year + "-" + month;

            } else {
                fullDay = year + "-0" + month;

            }
            fullDay = fullDay + "-" + day;
            Details_By = "Date :" + fullDay;
        } else if (details_by.equals("Monthly")) {
            int year = dc_stuRegYear.getValue();
            int month = dc_stuRegMonth.getMonth() + 1;
            String fullDay;
            if (month > 9) {
                fullDay = year + "-" + month;

            } else {
                fullDay = year + "-0" + month;

            }
            Details_By = "Selected month :" + fullDay;
        } else if (details_by.equals("Annualy")) {
            int year = dc_stuRegYear.getValue();
            Details_By = "Year :" + year;
        }
        int i = tbl_stuReg.getRowCount();
        if (i > 0) {
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("\\Reports\\studen tRegistartion.jrxml");
                JasperDesign jd = JRXmlLoader.load(is);
                JasperReport jr = JasperCompileManager.compileReport(jd);

                JRTableModelDataSource tm = new JRTableModelDataSource(tbl_stuReg.getModel());

                Map<String, Object> p = new HashMap<String, Object>();
                p.put("full_amount", lbl_stuRegTotal.getText());
                p.put("email", company_email);
                p.put("numbers", company_number);
                p.put("details by", Details_By);

                JasperPrint jp = JasperFillManager.fillReport(jr, p, tm);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AID - 0010-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

        } else {
            JOptionPane.showMessageDialog(this, "There is no Data to Print");
        }

    }//GEN-LAST:event_btn_StudentRegistrationGenerateReportActionPerformed

    private void btn_CompanyIncomeGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CompanyIncomeGenerateReportActionPerformed
        //Day, Type
        //Daily, Monthly, Annualy, Custom
        String company_email = null, company_number = null, details_by;
        try {
            ResultSet rs = DB.getData("SELECT company_email,company_mobile,company_office FROM admin_panel");
            while (rs.next()) {
                company_email = rs.getString("company_email");
                company_number = rs.getString("company_mobile") + " " + rs.getString("company_office");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - AID - 0011-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
        String incomeSerachtype = cmb_companyIncomeSearchBy.getSelectedItem().toString().trim();
        String Details_By = null;
        if (incomeSerachtype.equals("Day")) {
            String daytype = cmb_companyIncometype.getSelectedItem().toString().trim();
            if (daytype.equals("Daily")) {
                int year = dc_companyIncomeYear.getValue();
                int month = dc_companyIncomeMonth.getMonth() + 1;
                int day = dc_companyIncomeDay.getDay();
                String fullDay;
                if (month > 9) {
                    fullDay = year + "-" + month;

                } else {
                    fullDay = year + "-0" + month;

                }
                fullDay = fullDay + "-" + day;
                Details_By = "Date :" + fullDay;
            } else if (daytype.equals("Monthly")) {
                int year = dc_companyIncomeYear.getValue();
                int month = dc_companyIncomeMonth.getMonth() + 1;
                String fullDay;
                if (month > 9) {
                    fullDay = year + "-" + month;

                } else {
                    fullDay = year + "-0" + month;

                }
                Details_By = "Month:" + fullDay;
            } else if (daytype.equals("Annualy")) {
                int year = dc_companyIncomeYear.getValue();

                Details_By = "Year :" + year;
            } else if (daytype.equals("Custom")) {
                String FromDate = new SimpleDateFormat("yyyy-MM-dd").format(dc_companyIncomeFrom.getDate());
                String ToDate = new SimpleDateFormat("yyyy-MM-dd").format(dc_companyIncomeTo.getDate());

                Details_By = "Custom Date : From-" + FromDate + " To-" + ToDate;
            }
        } else if (incomeSerachtype.equals("Type")) {
            String type = cmb_companyIncomeTypeResults.getSelectedItem().toString().trim();
            System.out.println(type);
            //Registration Fees, Class Fees
            if (type.equals("Registration Fee")) {
                Details_By = "Registration Fees";
            } else if (type.equals("Class Fee")) {
                Details_By = "Class Fees";
            }
        }
        details_by = cmb_companyIncometype.getSelectedItem().toString().trim();
        int i = tbl_totalIncome.getRowCount();
        if (i > 0) {
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("\\Reports\\company income.jrxml");
                JasperDesign jd = JRXmlLoader.load(is);
                JasperReport jr = JasperCompileManager.compileReport(jd);

                JRTableModelDataSource tm = new JRTableModelDataSource(tbl_totalIncome.getModel());

                Map<String, Object> p = new HashMap<String, Object>();
                p.put("full_amount", lbl_companyIncomeTotal.getText());
                p.put("email", company_email);
                p.put("numbers", company_number);
                p.put("details by", Details_By);

                JasperPrint jp = JasperFillManager.fillReport(jr, p, tm);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AID - 0011-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

        } else {
            JOptionPane.showMessageDialog(this, "There is no Data to print");
        }
    }//GEN-LAST:event_btn_CompanyIncomeGenerateReportActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new Menu().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_classFeeGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_classFeeGenerateReportActionPerformed
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
            JOptionPane.showMessageDialog(this, "ERROR - AID - 0012-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
        String Details_By = null;
        details_by = cmb_calssFeeSearchBy.getSelectedItem().toString().trim();
        if (details_by.equals("Custom")) {
            String FromDate = new SimpleDateFormat("yyyy-MM-dd").format(dc_classFeeFrom.getDate());
            String ToDate = new SimpleDateFormat("yyyy-MM-dd").format(dc_classFeeTo.getDate());

            Details_By = "Custom Date : From -" + FromDate + "  " + " To -" + ToDate;
        } else if (details_by.equals("Daily")) {
            int year = dc_classFeeYear.getValue();
            int month = dc_classFeeMonth.getMonth() + 1;
            int day = dc_classFeeDay.getDay();
            String fullDay;
            if (month > 9) {
                fullDay = year + "-" + month;

            } else {
                fullDay = year + "-0" + month;

            }
            fullDay = fullDay + "-" + day;
            Details_By = "Date :" + fullDay;
        } else if (details_by.equals("Monthly")) {
            int year = dc_classFeeYear.getValue();
            int month = dc_classFeeMonth.getMonth() + 1;
            String fullDay;
            if (month > 9) {
                fullDay = year + "-" + month;

            } else {
                fullDay = year + "-0" + month;

            }
            Details_By = "Selected month :" + fullDay;
        } else if (details_by.equals("Annualy")) {
            int year = dc_classFeeYear.getValue();
            Details_By = "Year :" + year;
        }
        int i = tbl_classFee.getRowCount();
        if (i > 0) {
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("\\Reports\\classfee1.jrxml");
                JasperDesign jd = JRXmlLoader.load(is);
                JasperReport jr = JasperCompileManager.compileReport(jd);

                JRTableModelDataSource tm = new JRTableModelDataSource(tbl_classFee.getModel());

                Map<String, Object> p = new HashMap<String, Object>();
                p.put("full_amount", lbl_classFeeTotal.getText());
                p.put("email", company_email);
                p.put("numbers", company_number);
                p.put("details by", Details_By);

                JasperPrint jp = JasperFillManager.fillReport(jr, p, tm);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AID - 0012-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

        } else {
            JOptionPane.showMessageDialog(this, "There is no Data to Print");
        }
    }//GEN-LAST:event_btn_classFeeGenerateReportActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        setValuseFromAdmin();
    }//GEN-LAST:event_formWindowGainedFocus

    private void cmb_profitComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_profitComboActionPerformed
        setProfitCombo();
    }//GEN-LAST:event_cmb_profitComboActionPerformed

    private void dtn_profitSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dtn_profitSearchActionPerformed
        if (cmb_profitCombo.getSelectedItem().toString().trim().equals("Monthly")) {
            int year = dc_profitYear.getValue();
            int month = dc_profitMonth.getMonth() + 1;
            String fullyear;
            if (month > 9) {
                fullyear = year + "-" + month;
            } else {
                fullyear = year + "-0" + month;
            }
            getDataByMonth(fullyear);
        } else if (cmb_profitCombo.getSelectedItem().toString().trim().equals("Annually")) {
            int year = dc_profitYear.getValue();
            getDataByYear(year + "");
        }
    }//GEN-LAST:event_dtn_profitSearchActionPerformed

    private void popup_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_menuActionPerformed
        this.dispose();
        new Menu().setVisible(true);
    }//GEN-LAST:event_popup_menuActionPerformed

    private void popup_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_helpActionPerformed
        try {
            Desktop.getDesktop().open(new File("E:\\Syndicate\\Documentation\\Admin – Income Details.pdf"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_popup_helpActionPerformed

    private void popup_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_exitActionPerformed
        this.dispose();
    }//GEN-LAST:event_popup_exitActionPerformed

    private void pnl_headerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_headerMouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_headerMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel1MouseClicked

    private void IncomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncomeMouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_IncomeMouseClicked

    private void pnl_studentRegistionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_studentRegistionMouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_studentRegistionMouseClicked

    private void jPanel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel22MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel22MouseClicked

    private void jPanel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel23MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel23MouseClicked

    private void jPanel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel24MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel24MouseClicked

    private void pnl_classFeesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_classFeesMouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_classFeesMouseClicked

    private void jPanel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel25MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel25MouseClicked

    private void jPanel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel26MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel26MouseClicked

    private void jPanel35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel35MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel35MouseClicked

    private void pnl_companyIncomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_companyIncomeMouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_companyIncomeMouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jTabbedPane3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane3MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTabbedPane3MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
         if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel4MouseClicked

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
            java.util.logging.Logger.getLogger(IncomeDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IncomeDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IncomeDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IncomeDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IncomeDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Income;
    private javax.swing.JButton btn_CompanyIncomeGenerateReport;
    private javax.swing.JButton btn_StudentRegistrationGenerateReport;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_classFeeAdd;
    private javax.swing.JButton btn_classFeeEdit;
    private javax.swing.JButton btn_classFeeGenerateReport;
    private javax.swing.JButton btn_classFeeSearch;
    private javax.swing.JButton btn_companyIncomeAdd;
    private javax.swing.JButton btn_companyIncomeEdit;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_stuRegSearch;
    private javax.swing.JComboBox cmb_calssFeeSearchBy;
    private javax.swing.JComboBox cmb_classFeeRateClass;
    private javax.swing.JComboBox cmb_classFeeRateTeacherName;
    private javax.swing.JComboBox cmb_companyIncomeSearchBy;
    private javax.swing.JComboBox cmb_companyIncomeTypeResults;
    private javax.swing.JComboBox cmb_companyIncometype;
    private javax.swing.JComboBox cmb_profitCombo;
    private javax.swing.JComboBox cmb_stuRegSearchBy;
    private com.toedter.calendar.JDayChooser dc_classFeeDay;
    private com.toedter.calendar.JDateChooser dc_classFeeFrom;
    private com.toedter.calendar.JMonthChooser dc_classFeeMonth;
    private com.toedter.calendar.JDateChooser dc_classFeeTo;
    private com.toedter.calendar.JYearChooser dc_classFeeYear;
    private com.toedter.calendar.JDayChooser dc_companyIncomeDay;
    private com.toedter.calendar.JDateChooser dc_companyIncomeFrom;
    private com.toedter.calendar.JMonthChooser dc_companyIncomeMonth;
    private com.toedter.calendar.JDateChooser dc_companyIncomeTo;
    private com.toedter.calendar.JYearChooser dc_companyIncomeYear;
    private com.toedter.calendar.JMonthChooser dc_profitMonth;
    private com.toedter.calendar.JYearChooser dc_profitYear;
    private com.toedter.calendar.JDayChooser dc_stuRegDay;
    private com.toedter.calendar.JDateChooser dc_stuRegFrom;
    private com.toedter.calendar.JMonthChooser dc_stuRegMonth;
    private com.toedter.calendar.JDateChooser dc_stuRegTo;
    private com.toedter.calendar.JYearChooser dc_stuRegYear;
    private javax.swing.JButton dtn_profitSearch;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_classFeeDay;
    private javax.swing.JLabel lbl_classFeeFrom;
    private javax.swing.JLabel lbl_classFeeMonth;
    private javax.swing.JLabel lbl_classFeeTo;
    private javax.swing.JLabel lbl_classFeeTotal;
    private javax.swing.JLabel lbl_classFeeYear;
    private javax.swing.JLabel lbl_companyIncomeDay;
    private javax.swing.JLabel lbl_companyIncomeFrom;
    private javax.swing.JLabel lbl_companyIncomeMonth;
    private javax.swing.JLabel lbl_companyIncomeTo;
    private javax.swing.JLabel lbl_companyIncomeTotal;
    private javax.swing.JLabel lbl_companyIncomeYear;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_day;
    private javax.swing.JLabel lbl_from;
    private javax.swing.JLabel lbl_month;
    private javax.swing.JLabel lbl_profitMonth;
    private javax.swing.JLabel lbl_profitYear;
    private javax.swing.JLabel lbl_stuRegTotal;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_to;
    private javax.swing.JLabel lbl_totalExpence;
    private javax.swing.JLabel lbl_totalIncome;
    private javax.swing.JLabel lbl_totalProfit;
    private javax.swing.JLabel lbl_userType;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JLabel lbl_valuetotalExpence;
    private javax.swing.JLabel lbl_valuetotalIncome;
    private javax.swing.JLabel lbl_valuetotalProfit;
    private javax.swing.JLabel lbl_year;
    private javax.swing.JPanel pnl_classFees;
    private javax.swing.JPanel pnl_companyIncome;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_studentRegistion;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem popup_exit;
    private javax.swing.JMenuItem popup_help;
    private javax.swing.JMenuItem popup_menu;
    private javax.swing.JTable tbl_all;
    private javax.swing.JTable tbl_classFee;
    private javax.swing.JTable tbl_profitExpenceDetails;
    private javax.swing.JTable tbl_profitIncomeDetails;
    private javax.swing.JTable tbl_stuReg;
    private javax.swing.JTable tbl_totalIncome;
    private javax.swing.JTextField txt_classFeeNewAmount;
    private javax.swing.JTextField txt_commissinRate;
    private javax.swing.JTextField txt_companyIncomeNewAmount;
    private javax.swing.JTextField txt_newAmount;
    private javax.swing.JTextField txt_registrationFee;
    // End of variables declaration//GEN-END:variables

    //-----------------start profit --------------------------------------------------//
    //set combo
    void setProfitCombo() {
        if (cmb_profitCombo.getSelectedItem().toString().trim().equals("Monthly")) {
            lbl_profitMonth.setVisible(true);
            lbl_profitYear.setVisible(true);
            dc_profitMonth.setVisible(true);
            dc_profitYear.setVisible(true);
        } else if (cmb_profitCombo.getSelectedItem().toString().trim().equals("Annually")) {
            lbl_profitMonth.setVisible(false);
            lbl_profitYear.setVisible(true);
            dc_profitMonth.setVisible(false);
            dc_profitYear.setVisible(true);
        }
    }

    void getDataByMonth(String selectedDate) {
        tool.clearTable(tbl_profitIncomeDetails);
        tool.clearTable(tbl_profitExpenceDetails);
        tool.clearTable(tbl_all);
        try {
            ResultSet rs = DB.getData("SELECT * FROM income WHERE date LIKE'" + selectedDate + "%' ");
            while (rs.next()) {
                ResultSet rs1 = DB.getData("SELECT * FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");
                while (rs1.next()) {
                    String date = rs1.getString("date");
                    String discription = rs1.getString("type");
                    double amount = Double.parseDouble(rs1.getString("amount"));

                    tool.addToTable(tbl_profitIncomeDetails, date, discription, amount + "");
                }
                rs1.close();
            }
            rs.close();
            double Value = tool.getTotalValueInDouble(tbl_profitIncomeDetails, 2);
            lbl_valuetotalIncome.setText(Value + "");
            ResultSet rse = DB.getData("SELECT * FROM expence WHERE date LIke'" + selectedDate + "%'");
            while (rse.next()) {
                String date = rse.getString("date");
                String discription = rse.getString("expence_type");
                double amount = Double.parseDouble(rse.getString("amount"));

                tool.addToTable(tbl_profitExpenceDetails, date, discription, amount + "");
            }
            rse.close();
            double Value1 = tool.getTotalValueInDouble(tbl_profitExpenceDetails, 2);
            lbl_valuetotalExpence.setText(Value1 + "");

            double total = Value - Value1;
            lbl_valuetotalProfit.setText(total + "");

            //ResultSet all1=DB.getData("SELECT * FROM expence WHERE date LIKE '"+selectedDate+"%'");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - AID - 0013 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }

    }

    void getDataByYear(String date) {
        tool.clearTable(tbl_profitIncomeDetails);
        tool.clearTable(tbl_profitExpenceDetails);
        try {
            ResultSet rs = DB.getData("SELECT * FROM income WHERE date LIKE'" + date + "%' ");
            while (rs.next()) {
                ResultSet rs1 = DB.getData("SELECT * FROM student_payment_invoice WHERE student_invoice_id='" + rs.getString("student_invoice_id") + "'");
                while (rs1.next()) {
                    String date1 = rs1.getString("date");
                    String discription = rs1.getString("type");
                    double amount = Double.parseDouble(rs1.getString("amount"));

                    tool.addToTable(tbl_profitIncomeDetails, date1, discription, amount + "");
                }
                rs1.close();
            }
            rs.close();
            double Value = tool.getTotalValueInDouble(tbl_profitIncomeDetails, 2);
            lbl_valuetotalIncome.setText(Value + "");
            ResultSet rse = DB.getData("SELECT * FROM expence WHERE date LIke'" + date + "%'");
            while (rse.next()) {
                String date2 = rse.getString("date");
                String discription = rse.getString("expence_type");
                double amount = Double.parseDouble(rse.getString("amount"));

                tool.addToTable(tbl_profitExpenceDetails, date2, discription, amount + "");
            }
            rse.close();
            double Value1 = tool.getTotalValueInDouble(tbl_profitExpenceDetails, 2);
            lbl_valuetotalExpence.setText(Value1 + "");

            double total = Value - Value1;
            lbl_valuetotalProfit.setText(total + "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - AID - 0014 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }

    }

    //-----------------Finish profit --------------------------------------------------//
}
