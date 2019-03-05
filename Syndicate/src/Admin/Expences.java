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
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
public class Expences extends javax.swing.JFrame {

    /**
     * Creates new form Expences
     */
    org.apache.log4j.Logger logError = org.apache.log4j.Logger.getLogger("ERROR");

    public Expences() {
        initComponents();
        Menu();
        lbl_userType.setText(Home.un);
        lbl_username.setText(Home.ut);
        salaryDetailsAllInvisble();
        teacherPaymentAllInvisible();
        otherExpencesAllInvisible();
        salaryDetailsBasicSalaryHide();
        salaryDetailsSetBasicSalaryToCombo();
        salaryDetailsEtfTextEditableFalse();
        dc_otherExpenceFrom.setMaxSelectableDate(new Date());
        dc_otherExpenceTo.setMaxSelectableDate(new Date());
        dc_teacherPayFrom.setMaxSelectableDate(new Date());
        dc_teacherPayTo.setMaxSelectableDate(new Date());
        txt_salaryDetailsSalaryPerDay.setEnabled(false);

        epfSetaAllInvisible();
        lbl_date.setText(new Classes.Commons().Date());
        new Classes.Commons().time(lbl_time);
    }

    JDBC DB = new JDBC();
    Classes.ToolsClass tool = new ToolsClass();
    
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

    //////////get tatal of table/////////////////////
    public double getTotalValue(JTable table, int row) {
        int rowCount = table.getRowCount();
        double totalValue = 0.0;
        if (rowCount > 0) {
            for (int i = 0; i < rowCount; i++) {
                totalValue = totalValue + Double.parseDouble(table.getValueAt(i, row).toString());
            }
        }

        return totalValue;

    }

    ///////////////////////////////////////////////////////////////////////////////
    //////----------------------------Salary Details seach panel------------------/////
    /////////////////////////////////////////////////////////////////////////////
    void salaryDetailsAllInvisble() {

        cmb_salaryDetailsDay.setVisible(false);
        cmb_salaryDetailsJobTitle.setVisible(false);

        lbl_salaryDetailsMonth.setVisible(false);

        lbl_salaryDetailsYear.setVisible(false);
        dc_salaryDetailsYear.setVisible(false);

        dc_salaryDetailsMonth.setVisible(false);

    }

    void salaryDetailsComboMonthly() {
        ///////Visible///////
        lbl_salaryDetailsYear.setVisible(true);
        lbl_salaryDetailsMonth.setVisible(true);
        dc_salaryDetailsYear.setVisible(true);
        dc_salaryDetailsMonth.setVisible(true);

    }

    void salaryDetailsComboAnnualy() {
        /////////Visible//////
        lbl_salaryDetailsYear.setVisible(true);
        dc_salaryDetailsYear.setVisible(true);

        ///////Not Visible/////
        lbl_salaryDetailsMonth.setVisible(false);

        dc_salaryDetailsMonth.setVisible(false);

    }

    /////////-------------salary details Main combo box-------------------///////
    void salaryDetailesMainCombo() {
        salaryDetailsAllInvisble();
        if (cmb_salaryDetailsMainCombo.getSelectedIndex() == 0) {
            cmb_salaryDetailsJobTitle.setVisible(true);
            cmb_salaryDetailsJobTitle.setEnabled(false);
            cmb_salaryDetailsDay.setVisible(true);

        } else if (cmb_salaryDetailsMainCombo.getSelectedIndex() == 1) {

            salaryDetailsEmployeesLoadToCombo();
            cmb_salaryDetailsJobTitle.setEnabled(true);
            cmb_salaryDetailsDay.setVisible(true);
            cmb_salaryDetailsJobTitle.setVisible(true);
        }
    }

    ///////Salary Details Daily,Monthly.Annualy and Custom Selecting
    void salaryDetailsComboDaySelect() {

        if (cmb_salaryDetailsDay.getSelectedIndex() == 0) {
            salaryDetailsComboMonthly();
        }
        if (cmb_salaryDetailsDay.getSelectedIndex() == 1) {
            salaryDetailsComboAnnualy();
        }

    }

    ///////// Loding employee names to combo box according to job title---//////
    void salaryDetailsEmployeesLoadToCombo() {
        try {
            ResultSet rs = DB.getData("SELECT * FROM job_title");
            Vector v = new Vector();
            while (rs.next()) {
                ResultSet rs1 = DB.getData("SELECT * FROM employee_reg WHERE job_title_id='" + rs.getString("job_title_id") + "'");

                while (rs1.next()) {
                    System.out.println("rs2");
                    
                    String empId = rs1.getString("emp_id");
                    String empName = rs1.getString("f_name") + " " + rs1.getString("l_name");
                    String fullData = empId + "-" + empName;
                    v.add(fullData);
                }
                rs1.close();
            }
            rs.close();
            cmb_salaryDetailsJobTitle.removeAllItems();
            for (int i = 0; i < v.size(); i++) {
                cmb_salaryDetailsJobTitle.addItem(v.get(i));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR - AdEX - 0001 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }
    ///////-------search Salary details and loading to table name and day choose given-----/////

    void salaryDetailsSeachToTable() {

        //////--- search from job title-----------------
        if (cmb_salaryDetailsMainCombo.getSelectedItem().toString().equals("Job ID/Name")) {

            int year = dc_salaryDetailsYear.getYear();
            int month = dc_salaryDetailsMonth.getMonth() + 1;
            String fullDate;

            if (month > 9) {
                fullDate = year + "-" + month;
            } else {
                fullDate = year + "-0" + month;
            }

            String fullYear = Integer.toString(year);
            String empID = cmb_salaryDetailsJobTitle.getSelectedItem().toString().split("-")[0];
            String empName = cmb_salaryDetailsJobTitle.getSelectedItem().toString().split("-")[1];
            //////// search by Month------------
            if (cmb_salaryDetailsDay.getSelectedItem().toString().equals("Monthly")) {

                tool.clearTable(tbl_salary);
                String Salary = "";
                String attendance = "0";
                String leaves = "0";
                String payment = "";
                String salPayDay = "";

                try {
                    ResultSet rs = DB.getData("SELECT * FROM salary WHERE emp_id='" + empID + "' AND month LIKE '" + fullDate + "%'");
                    while (rs.next()) {
                        String epf = "00.00";

                        attendance = rs.getString("attendance");
                        leaves = rs.getString("taken_leaves");
                        payment = rs.getString("payment");
                        ResultSet rs1 = DB.getData("SELECT * FROM etf_epf WHERE salary_id='" + rs.getString("salary_id") + "'");
                        while (rs1.next()) {
                            epf = rs1.getString("amount");
                        }
                        ResultSet rs2 = DB.getData("SELECT * FROM salary_invoice WHERE salary_id = '" + rs.getString("salary_id") + "'");
                        while (rs2.next()) {
                            Salary = rs2.getString("amount");
                        }
                        String advance = "00.00";
                        ResultSet rs3 = DB.getData("SELECT * FROM salary_advance WHERE emp_id='" + empID + "' AND month LIKE '" + fullDate + "%'");
                        if (rs3.next()) {
                            advance = rs3.getString("amount");
                        } else {
                            advance = "00.00";
                        }
                        tool.addToTable(tbl_salary, empID, empName, fullDate, attendance, leaves, payment, advance, epf, Salary);
                    }
                    double Value = getTotalValue(tbl_salary, 8);
                    lbl_salaryDetailsTotal.setText(Value + "0");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0002-01-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    ex.printStackTrace();
                }
            }
            ///// search from year------------------------------
            if (cmb_salaryDetailsDay.getSelectedItem().toString().equals("Annually")) {
                tool.clearTable(tbl_salary);
                String Salary = "";
                String attendance = "0";
                String leaves = "0";
                String payment = "";
                String date = "";
                String salaryMonth = "";

                try {
                    ResultSet rs = DB.getData("SELECT * FROM salary WHERE emp_id='" + empID + "' AND month LIKE '" + fullYear + "%'");
                    while (rs.next()) {
                        String epf = "00.00";
                        date = rs.getString("month");
                        attendance = rs.getString("attendance");
                        leaves = rs.getString("taken_leaves");
                        payment = rs.getString("payment");
                        ////// split date----
                        String salDate = rs.getString("month");
                        String salYear = salDate.split("-")[0];
                        String salMonth = salDate.split("-")[1];
                        salaryMonth = salYear+"-"+salMonth;
                        ResultSet rs1 = DB.getData("SELECT * FROM etf_epf WHERE salary_id='" + rs.getString("salary_id") + "'");
                        while (rs1.next()) {
                            epf = rs1.getString("amount");
                        }
                        ResultSet rs2 = DB.getData("SELECT * FROM salary_invoice WHERE salary_id = '" + rs.getString("salary_id") + "'");
                        while (rs2.next()) {
                            Salary = rs2.getString("amount");
                        }
                        String advance = "00.00";
                        ResultSet rs3 = DB.getData("SELECT * FROM salary_advance WHERE emp_id='" + empID + "' AND month LIKE '" + salaryMonth + "%'");
                        if (rs3.next()) {
                            advance = rs3.getString("amount");
                        } else {
                            advance = "00.00";
                        }
                        tool.addToTable(tbl_salary, empID, empName, date, attendance, leaves, payment, advance, epf, Salary);
                    }
                    double Value = getTotalValue(tbl_salary, 8);
                    lbl_salaryDetailsTotal.setText(Value + "0");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0002-01-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    ex.printStackTrace();
                }
            }

        }
        ////// seach by date----------------------------
        if (cmb_salaryDetailsMainCombo.getSelectedItem().toString().equals("Date")) {
            int month = (dc_salaryDetailsMonth.getMonth()) + 1;
            int year = dc_salaryDetailsYear.getYear();

            String fullDate;
            if (month > 9) {
                fullDate = year + "-" + month;
            } else {
                fullDate = year + "-0" + month;
            }

            String fullYear = Integer.toString(year);
            ///// get data by month---------------
            if (cmb_salaryDetailsDay.getSelectedItem().toString().equals("Monthly")) {
                tool.clearTable(tbl_salary);
                String Salary = "";
                String salAdvance = "";
                try {
                    ResultSet rs = DB.getData("SELECT * FROM salary WHERE month LIKE '" + fullDate + "%'");
                    while (rs.next()) {
                        ResultSet rs5 = DB.getData("SELECT * FROM salary_invoice WHERE salary_id='" + rs.getString("salary_id") + "'");
                        while (rs5.next()) {
                            Salary = rs5.getString("amount");
                        }
                        rs5.close();
                        ResultSet rs1 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                        while (rs1.next()) {
                            ////search from etf / epf---------------
                            ResultSet rs3 = DB.getData("SELECT * FROM etf_epf WHERE salary_id='" + rs.getString("salary_id") + "'");
                            while (rs3.next()) {
                                /////search from salary advance------------
                                ResultSet rs4 = DB.getData("SELECT * FROM salary_advance WHERE month LIKE '" + fullDate + "%' AND emp_id='" + rs.getString("emp_id") + "'");
                                if (rs4.next()) {
                                    System.out.println("in");
                                    salAdvance = rs4.getString("amount");
                                } else {
                                    salAdvance = "0.00";
                                }

                                System.out.println("salary advance");
                                String empID = rs1.getString("emp_id");
                                String empName = rs1.getString("f_name") + " " + rs1.getString("l_name");
                                String yearMonth = rs.getString("month");
                                String attendance = rs.getString("attendance");
                                String leaves = rs.getString("taken_leaves");
                                String amount = rs.getString("payment");
                                String advance = salAdvance;
                                System.out.println(advance);
                                String epf = rs3.getString("amount");

                                tool.addToTable(tbl_salary, empID, empName, yearMonth, attendance, leaves, amount, advance, epf, Salary);

                            }
                            rs3.close();

                        }
                        rs1.close();
                    }
                    rs.close();
                    double Value = getTotalValue(tbl_salary, 8);
                    lbl_salaryDetailsTotal.setText(Value + "0");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0002-02-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    ex.printStackTrace();
                }
            }
            /////// get data by year--------------
            if (cmb_salaryDetailsDay.getSelectedItem().toString().equals("Annually")) {
                tool.clearTable(tbl_salary);
                String Salary = "";
                String salAdvance = "";
                String salaryMonth = "";

                try {
                    ResultSet rs = DB.getData("SELECT * FROM salary WHERE month LIKE '" + fullYear + "%'");
                    while (rs.next()) {
                        ResultSet rs5 = DB.getData("SELECT * FROM salary_invoice WHERE salary_id='" + rs.getString("salary_id") + "'");
                        while (rs5.next()) {
                            
                            Salary = rs5.getString("amount");
                            
                            String salDate = rs.getString("month");
                        String salYear = salDate.split("-")[0];
                        String salMonth = salDate.split("-")[1];
                        salaryMonth = salYear+"-"+salMonth;
                        }
                        
                        ResultSet rs1 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                        while (rs1.next()) {

                            ////search from etf / epf---------------
                            ResultSet rs3 = DB.getData("SELECT * FROM etf_epf WHERE salary_id='" + rs.getString("salary_id") + "'");
                            while (rs3.next()) {
                                /////search from salary advance------------
                                ResultSet rs4 = DB.getData("SELECT * FROM salary_advance WHERE month LIKE '" + salaryMonth + "%' AND emp_id='" + rs1.getString("emp_id") + "'");
                                if (rs4.next()) {
                                    System.out.println("in");
                                    salAdvance = rs4.getString("amount");
                                } else {
                                    salAdvance = "0.00";
                                }

                                String empID = rs1.getString("emp_id");
                                String empName = rs1.getString("f_name") + " " + rs1.getString("l_name");
                                String yearMonth = rs.getString("month");
                                String attendance = rs.getString("attendance");
                                String leaves = rs.getString("taken_leaves");
                                String amount = rs.getString("payment");
                                String advance = salAdvance;
                                String epf = rs3.getString("amount");

                                tool.addToTable(tbl_salary, empID, empName, yearMonth, attendance, leaves, amount, advance, epf, Salary);

                                rs4.close();
                            }
                            rs3.close();

                        }
                        rs1.close();
                    }
                    rs.close();
                    double Value = getTotalValue(tbl_salary, 8);
                    lbl_salaryDetailsTotal.setText(Value + "0");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0002-02-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    ex.printStackTrace();
                }
            }
        }

    }

    ///// set basic salary to combo-------
    void salaryDetailsSetBasicSalaryToCombo() {
        try {
            ResultSet rs = DB.getData("SELECT * FROM job_title");
            Vector v = new Vector();

            while (rs.next()) {
                ResultSet rs1 = DB.getData("SELECT * FROM basic_salary WHERE job_title_id='" + rs.getString("job_title_id") + "'");
                while (rs1.next()) {
                    String job = rs.getString("job_name");
                    String salary = rs1.getString("amount");
                    String all = job + "-" + salary;
                    v.add(all);
                }
                rs1.close();
                for (int i = 0; i < v.size(); i++) {
                    cmb_salaryDetailsJob.addItem(v.get(i));
                }
            }
            rs.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR - AdEX 0003 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    ////// update basic salary--------
    void salaryDetailsUpdateBasicSalary() {
        try {
            String salaryName = cmb_salaryDetailsJob.getSelectedItem().toString().split("-")[0];
            ResultSet rs = DB.getData("SELECT job_title_id FROM job_title WHERE job_name='" + salaryName + "'");
            if (rs.next()) {
                ResultSet rs1 = DB.getData("SELECT * FROM basic_salary WHERE job_title_id='" + rs.getString("job_title_id") + "'");
                if (rs1.next()) {
                    DB.putData("UPDATE basic_salary SET amount='" + txt_salaryDetailsChangeSalary.getText() + "' WHERE basic_salary_id='" + rs1.getString("basic_salary_id") + "'");
                    salaryDetailsSetBasicSalaryToCombo();
                }
                rs1.close();
            }
            rs.close();
            //DB.putData("UPDATE basic_salary SET amount='" + txt_salaryDetailsChangeSalary.getText() + "'");
            //salaryDetailsSetBasicSalaryToCombo();
            JOptionPane.showMessageDialog(rootPane, "Successfully Updated");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR - AdEX 0004 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    ///--Basic Salary---------------//////
    void salaryDetailsBasicSalaryHide() {

        txt_salaryDetailsChangeSalary.setVisible(false);
        lbl_salaryDetailsChangeSalary.setVisible(false);
        btn_salaryDetailsChangeSalary.setVisible(false);
    }

    void salaryDetailsBasicSalaryVisible() {

        txt_salaryDetailsChangeSalary.setVisible(true);
        lbl_salaryDetailsChangeSalary.setVisible(true);
        btn_salaryDetailsChangeSalary.setVisible(true);
    }

    //////---------ETF EPF----------------/////////////////////////////////////
    void salaryDetailsEtfTextEditableFalse() {
        txt_salaryDetailsCompanyRate.setEnabled(false);
        txt_salaryDetailsEmployeeRate.setEnabled(false);
    }

    void salaryDetailsEtfTextEditableTrue() {
        txt_salaryDetailsCompanyRate.setEnabled(true);
        txt_salaryDetailsEmployeeRate.setEnabled(true);
    }

    void salaryDetailsUpdateEtfRates() {
        String employee = txt_salaryDetailsEmployeeRate.getText();
        String company = txt_salaryDetailsCompanyRate.getText();
        try {
            DB.putData("UPDATE admin_panel SET etf/epf_employee='" + employee + "',etf/epf_company='" + company + "'");
            JOptionPane.showMessageDialog(rootPane, "Successfully Updated");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR - AdEX 0005 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    //////////---------Salary per Day---------------/////////////////////////
    void salaryDetailsUpdateSalaryPerDay() {
        String salaryPerDay = txt_salaryDetailsSalaryPerDay.getText();
        try {
            DB.putData("UPDATE admin_panel SET employee_salary_payday='" + salaryPerDay + "'");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR - AdEX 0006 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    ////////////////////////////////////////////////////////////////////////
    /////----------------------Teacher Payments Search panel-----------//////////
    //////////////////////////////////////////////////////////////////////
    void teacherPaymentAllInvisible() {

        cmb_teacherPayDayChoose.setVisible(false);
        cmb_teacherPayName.setVisible(false);

        lbl_teacherPayFrom.setVisible(false);
        lbl_teacherPayTo.setVisible(false);
        lbl_teacherPayDay.setVisible(false);
        lbl_teacherPayMonth.setVisible(false);
        lbl_teacherPayYear.setVisible(false);
        dc_teacherPayYear.setVisible(false);
        dc_teacherPayFrom.setVisible(false);
        dc_teacherPayTo.setVisible(false);
        dc_teacherPayDate.setVisible(false);
        dc_teacherPayMonth.setVisible(false);

    }

    void teacherPaymentComboDaily() {
        //////////Visible///////
        lbl_teacherPayDay.setVisible(true);
        lbl_teacherPayMonth.setVisible(true);
        lbl_teacherPayYear.setVisible(true);
        dc_teacherPayDate.setVisible(true);
        dc_teacherPayMonth.setVisible(true);
        dc_teacherPayYear.setVisible(true);

        ////////////not visible//////
        lbl_teacherPayFrom.setVisible(false);
        lbl_teacherPayTo.setVisible(false);
        dc_teacherPayFrom.setVisible(false);
        dc_teacherPayTo.setVisible(false);

    }

    void teacherPaymentComboMonthly() {

        ///////Visible/////
        lbl_teacherPayMonth.setVisible(true);
        lbl_teacherPayYear.setVisible(true);

        dc_teacherPayMonth.setVisible(true);
        dc_teacherPayYear.setVisible(true);

        ////////Not Visible/////
        lbl_teacherPayFrom.setVisible(false);
        lbl_teacherPayTo.setVisible(false);
        lbl_teacherPayDay.setVisible(false);
        dc_teacherPayFrom.setVisible(false);
        dc_teacherPayTo.setVisible(false);
        dc_teacherPayDate.setVisible(false);

    }

    void teacherPaymentComboAnnualy() {

        /////////Visible//////////
        lbl_teacherPayYear.setVisible(true);
        dc_teacherPayYear.setVisible(true);

        /////////Not Visible///////
        lbl_teacherPayFrom.setVisible(false);
        lbl_teacherPayTo.setVisible(false);
        lbl_teacherPayDay.setVisible(false);
        lbl_teacherPayMonth.setVisible(false);
        dc_teacherPayFrom.setVisible(false);
        dc_teacherPayTo.setVisible(false);
        dc_teacherPayDate.setVisible(false);
        dc_teacherPayMonth.setVisible(false);

    }

    void teacherPaymentComboCustom() {

        //////////Visible//////////
        lbl_teacherPayFrom.setVisible(true);
        lbl_teacherPayTo.setVisible(true);
        dc_teacherPayFrom.setVisible(true);
        dc_teacherPayTo.setVisible(true);

        ///////////Not Visible///////////
        lbl_teacherPayYear.setVisible(false);
        lbl_teacherPayDay.setVisible(false);
        lbl_teacherPayMonth.setVisible(false);
        dc_teacherPayDate.setVisible(false);
        dc_teacherPayMonth.setVisible(false);
        dc_teacherPayYear.setVisible(false);

    }

    //// Teacher Payments Main combo box selecting
    void teacherPaymentsMainCombo() {

        teacherPaymentAllInvisible();
        if (cmb_teacherPayMainCombo.getSelectedIndex() == 0) {
            cmb_teacherPayDayChoose.setVisible(true);
            cmb_teacherPayName.setVisible(true);
            cmb_teacherPayName.setEnabled(false);

        }
        if (cmb_teacherPayMainCombo.getSelectedIndex() == 1) {
            teacherPaymentLoadTeacherDetailsToCombo();
            cmb_teacherPayDayChoose.setVisible(true);
            cmb_teacherPayName.setVisible(true);
            cmb_teacherPayName.setEnabled(true);

        }
    }

    ///// teacher names loading to combo box----------
    void teacherPaymentLoadTeacherDetailsToCombo() {
        String full = "";
        try {

            String Name = "";
            Vector v = new Vector();
            ResultSet rs = DB.getData("SELECT * FROM teacher_time_table");
            while (rs.next()) {

                ResultSet rs1 = DB.getData("SELECT * FROM teacher_reg WHERE teacher_id='" + rs.getString("teacher_id") + "'");
                while (rs1.next()) {
                    Name = rs1.getString("f_name") + "-" + rs1.getString("l_name");
                }
                ResultSet rs2 = DB.getData("SELECT * FROM subject WHERE subject_id='" + rs.getString("subject_id") + "'");
                while (rs2.next()) {
                    ResultSet rs3 = DB.getData("SELECT * FROM medium WHERE medium_id='" + rs2.getString("medium_id") + "'");
                    while (rs3.next()) {
                        String subject = rs2.getString("subject_name");
                        String medium = rs3.getString("medium");
                        String teacherID = rs.getString("teacher_id");
                        full = teacherID + "-" + Name + "-" + subject + "-" + medium;
                        v.add(full);
                    }
                    rs3.close();
                }
                rs2.close();
            }
            rs.close();
            for (int i = 0; i < v.size(); i++) {
                cmb_teacherPayName.addItem(v.get(i));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR - AdEX 0007 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    ////////////////Teacher Paymnt Daily,Monthly,Annualy,Custome selection//////////
    void teacherPayComboDaySelect() {
        if (cmb_teacherPayDayChoose.getSelectedIndex() == 0) {
            teacherPaymentComboDaily();
        }
        if (cmb_teacherPayDayChoose.getSelectedIndex() == 1) {
            teacherPaymentComboMonthly();
        }
        if (cmb_teacherPayDayChoose.getSelectedIndex() == 2) {
            teacherPaymentComboAnnualy();
        }
        if (cmb_teacherPayDayChoose.getSelectedIndex() == 3) {
            teacherPaymentComboCustom();
        }
    }

    /////// ----------teacher payment main seach---------------------------///////
    //----------------------------------------------------------------////
    void teacherPaymentSearch() {
        int day = dc_teacherPayDate.getDay();
        int month = dc_teacherPayMonth.getMonth() + 1;
        int year = dc_teacherPayYear.getYear();

        String fullDay;
        String fullMonth;
        if (month > 9) {
            fullDay = year + "-" + month;
            fullMonth = year + "-" + month;
        } else {
            fullDay = year + "-0" + month;
            fullMonth = year + "-0" + month;
        }

        fullDay = year + "-" + month + "-" + day;

        String fullYear = Integer.toString(year);

        if (cmb_teacherPayMainCombo.getSelectedItem().toString().equals("Date")) {
            if (cmb_teacherPayDayChoose.getSelectedItem().toString().equals("Daily")) {
                try {
                    tool.clearTable(tbl_teacherPay);
                    ///// get data from payment invoice---------------
                    ResultSet rs = DB.getData("SELECT * FROM teacher_payment_invoice WHERE date='" + fullDay + "'");
                    while (rs.next()) {
                        /// get data from payment table-----------------
                        ResultSet rs1 = DB.getData("SELECT * FROM teacher_payment WHERE teacher_payment_id = '" + rs.getString("teacher_payment_id") + "'");
                        while (rs1.next()) {
                            /// get data from teacher reg-------------
                            ResultSet rs2 = DB.getData("SELECT * FROM teacher_reg WHERE teacher_id='" + rs1.getString("teacher_id") + "'");
                            while (rs2.next()) {
                                //// get data from class details----------------
                                ResultSet rs3 = DB.getData("SELECT * FROM class_details WHERE teacher_id='" + rs2.getString("teacher_id") + "'");
                                while (rs3.next()) {
                                    //// get data from subject----------------
                                    ResultSet rs4 = DB.getData("SELECT * FROM subject WHERE subject_id='" + rs3.getString("subject_id") + "'");
                                    while (rs4.next()) {
                                        //// get data from medium-----------------
                                        ResultSet rs5 = DB.getData("SELECT * FROM medium  WHERE medium_id = '" + rs4.getString("medium_id") + "'");
                                        while (rs5.next()) {
                                            String teaID = rs2.getString("teacher_id");
                                            String teaName = rs2.getString("f_name") + " " + rs2.getString("l_name");
                                            String invoiceID = rs.getString("teacher_payment_invoice_id");
                                            String amount = rs.getString("amount");
                                            String date = rs.getString("date");
                                            String subject = rs4.getString("subject_name");
                                            String medium = rs5.getString("medium");

                                            tool.addToTable(tbl_teacherPay, teaID, teaName, invoiceID, amount, date, subject, medium);
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
                    double Value = getTotalValue(tbl_teacherPay, 3);
                    lbl_teacherPaymentTotal.setText(Value + "");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0008-01-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
            ///// seach payments by monthly--------------------
            if (cmb_teacherPayDayChoose.getSelectedItem().toString().equals("Monthly")) {
                try {
                    tool.clearTable(tbl_teacherPay);
                    ///// get data from payment invoice---------------
                    ResultSet rs = DB.getData("SELECT * FROM teacher_payment_invoice WHERE date LIKE'" + fullMonth + "%'");
                    while (rs.next()) {
                        /// get data from payment table-----------------
                        ResultSet rs1 = DB.getData("SELECT * FROM teacher_payment WHERE teacher_payment_id = '" + rs.getString("teacher_payment_id") + "'");
                        while (rs1.next()) {
                            /// get data from teacher reg-------------
                            ResultSet rs2 = DB.getData("SELECT * FROM teacher_reg WHERE teacher_id='" + rs1.getString("teacher_id") + "'");
                            while (rs2.next()) {
                                //// get data from class details----------------
                                ResultSet rs3 = DB.getData("SELECT * FROM class_details WHERE teacher_id='" + rs2.getString("teacher_id") + "'");
                                while (rs3.next()) {
                                    //// get data from subject----------------
                                    ResultSet rs4 = DB.getData("SELECT * FROM subject WHERE subject_id='" + rs3.getString("subject_id") + "'");
                                    while (rs4.next()) {
                                        //// get data from medium-----------------
                                        ResultSet rs5 = DB.getData("SELECT * FROM medium  WHERE medium_id = '" + rs4.getString("medium_id") + "'");
                                        while (rs5.next()) {
                                            String teaID = rs2.getString("teacher_id");
                                            String teaName = rs2.getString("f_name") + " " + rs2.getString("l_name");
                                            String invoiceID = rs.getString("teacher_payment_invoice_id");
                                            String amount = rs.getString("amount");
                                            String date = rs.getString("date");
                                            String subject = rs4.getString("subject_name");
                                            String medium = rs5.getString("medium");

                                            tool.addToTable(tbl_teacherPay, teaID, teaName, invoiceID, amount, date, subject, medium);
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
                    double Value = getTotalValue(tbl_teacherPay, 3);
                    lbl_teacherPaymentTotal.setText(Value + "");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0008-01-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
            ////// search data by Annually-----------------
            if (cmb_teacherPayDayChoose.getSelectedItem().toString().equals("Annually")) {
                tool.clearTable(tbl_teacherPay);
                try {
                    ///// get data from payment invoice---------------
                    ResultSet rs = DB.getData("SELECT * FROM teacher_payment_invoice WHERE date LIKE '" + fullYear + "%'");
                    while (rs.next()) {
                        /// get data from payment table-----------------
                        ResultSet rs1 = DB.getData("SELECT * FROM teacher_payment WHERE teacher_payment_id = '" + rs.getString("teacher_payment_id") + "'");
                        while (rs1.next()) {
                            /// get data from teacher reg-------------
                            ResultSet rs2 = DB.getData("SELECT * FROM teacher_reg WHERE teacher_id='" + rs1.getString("teacher_id") + "'");
                            while (rs2.next()) {
                                //// get data from class details----------------
                                ResultSet rs3 = DB.getData("SELECT * FROM class_details WHERE teacher_id='" + rs2.getString("teacher_id") + "'");
                                while (rs3.next()) {
                                    //// get data from subject----------------
                                    ResultSet rs4 = DB.getData("SELECT * FROM subject WHERE subject_id='" + rs3.getString("subject_id") + "'");
                                    while (rs4.next()) {
                                        //// get data from medium-----------------
                                        ResultSet rs5 = DB.getData("SELECT * FROM medium  WHERE medium_id = '" + rs4.getString("medium_id") + "'");
                                        while (rs5.next()) {
                                            String teaID = rs2.getString("teacher_id");
                                            String teaName = rs2.getString("f_name") + " " + rs2.getString("l_name");
                                            String invoiceID = rs.getString("teacher_payment_invoice_id");
                                            String amount = rs.getString("amount");
                                            String date = rs.getString("date");
                                            String subject = rs4.getString("subject_name");
                                            String medium = rs5.getString("medium");

                                            tool.addToTable(tbl_teacherPay, teaID, teaName, invoiceID, amount, date, subject, medium);
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
                    double Value = getTotalValue(tbl_teacherPay, 3);
                    lbl_teacherPaymentTotal.setText(Value + "");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0008-01-03 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
            /////// search by custome date
            if (cmb_teacherPayDayChoose.getSelectedItem().toString().equals("Custome")) {
                tool.clearTable(tbl_teacherPay);
                Date d = new Date();
                String from = new SimpleDateFormat("yyyy-MM-dd").format(dc_teacherPayFrom.getDate());
                String to = new SimpleDateFormat("yyyy-MM-dd").format(dc_teacherPayTo.getDate());

                try {
                    ///// get data from payment invoice---------------
                    ResultSet rs = DB.getData("SELECT * FROM teacher_payment_invoice WHERE date BETWEEN '" + from + "' AND '" + to + "'");
                    while (rs.next()) {
                        /// get data from payment table-----------------
                        ResultSet rs1 = DB.getData("SELECT * FROM teacher_payment WHERE teacher_payment_id = '" + rs.getString("teacher_payment_id") + "'");
                        while (rs1.next()) {
                            /// get data from teacher reg-------------
                            ResultSet rs2 = DB.getData("SELECT * FROM teacher_reg WHERE teacher_id='" + rs1.getString("teacher_id") + "'");
                            while (rs2.next()) {
                                //// get data from class details----------------
                                ResultSet rs3 = DB.getData("SELECT * FROM class_details WHERE teacher_id='" + rs2.getString("teacher_id") + "'");
                                while (rs3.next()) {
                                    //// get data from subject----------------
                                    ResultSet rs4 = DB.getData("SELECT * FROM subject WHERE subject_id='" + rs3.getString("subject_id") + "'");
                                    while (rs4.next()) {
                                        //// get data from medium-----------------
                                        ResultSet rs5 = DB.getData("SELECT * FROM medium  WHERE medium_id = '" + rs4.getString("medium_id") + "'");
                                        while (rs5.next()) {
                                            String teaID = rs2.getString("teacher_id");
                                            String teaName = rs2.getString("f_name") + " " + rs2.getString("l_name");
                                            String invoiceID = rs.getString("teacher_payment_invoice_id");
                                            String amount = rs.getString("amount");
                                            String date = rs.getString("date");
                                            String subject = rs4.getString("subject_name");
                                            String medium = rs5.getString("medium");

                                            tool.addToTable(tbl_teacherPay, teaID, teaName, invoiceID, amount, date, subject, medium);
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
                    double Value = getTotalValue(tbl_teacherPay, 3);
                    lbl_teacherPaymentTotal.setText(Value + "");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0008-01-04 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
        }
        /////search by  teacher Details----------------------
        if (cmb_teacherPayMainCombo.getSelectedItem().toString().equals("Teacher Details")) {
            String cmboTeaId = cmb_teacherPayName.getSelectedItem().toString().split("-")[0];
            String cmboTeaName = cmb_teacherPayName.getSelectedItem().toString().split("-")[1];
            String cmboTeaGrde = cmb_teacherPayName.getSelectedItem().toString().split("-")[2];
            String cmboTeasub = cmb_teacherPayName.getSelectedItem().toString().split("-")[3];
            String cmboMedium = cmb_teacherPayName.getSelectedItem().toString().split("-")[4];
            String fullSub = cmboTeaGrde + "-" + cmboTeasub;
            ///// seach by teacher details-daily
            if (cmb_teacherPayDayChoose.getSelectedItem().equals("Daily")) {

                tool.clearTable(tbl_teacherPay);
                try {

                    ResultSet rs = DB.getData("SELECT * FROM teacher_payment WHERE teacher_id='" + cmboTeaId + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM teacher_payment_invoice WHERE teacher_payment_id = '" + rs.getString("teacher_payment_id") + "' AND date = '" + fullDay + "'");
                        while (rs1.next()) {
                            String teaID = cmboTeaId;
                            String teaName = cmboTeaName;
                            String invoice = rs1.getString("teacher_payment_invoice_id");
                            String amount = rs1.getString("amount");
                            String date = rs1.getString("date");
                            String subject = fullSub;
                            String medium = cmboMedium;

                            tool.addToTable(tbl_teacherPay, teaID, teaName, invoice, amount, date, subject, medium);
                        }
                        rs1.close();
                    }
                    rs.close();
                    double Value = getTotalValue(tbl_teacherPay, 3);
                    lbl_teacherPaymentTotal.setText(Value + "");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0008-02-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
            ///// seach by teacher details- monthly--------------------
            if (cmb_teacherPayDayChoose.getSelectedItem().equals("Monthly")) {
                tool.clearTable(tbl_teacherPay);
                try {
                    ResultSet rs = DB.getData("SELECT * FROM teacher_payment WHERE teacher_id='" + cmboTeaId + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM teacher_payment_invoice WHERE teacher_payment_id = '" + rs.getString("teacher_payment_id") + "' AND date LIKE '" + fullMonth + "%'");
                        while (rs1.next()) {
                            String teaID = cmboTeaId;
                            String teaName = cmboTeaName;
                            String invoice = rs1.getString("teacher_payment_invoice_id");
                            String amount = rs1.getString("amount");
                            String date = rs1.getString("date");
                            String subject = fullSub;
                            String medium = cmboMedium;

                            tool.addToTable(tbl_teacherPay, teaID, teaName, invoice, amount, date, subject, medium);
                        }
                        rs1.close();
                    }
                    rs.close();
                    double Value = getTotalValue(tbl_teacherPay, 3);
                    lbl_teacherPaymentTotal.setText(Value + "");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0008-02-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }

            /// seach details by teacher details-- annually-------------------
            if (cmb_teacherPayDayChoose.getSelectedItem().equals("Annually")) {
                System.out.println("annually");
                tool.clearTable(tbl_teacherPay);
                try {
                    ResultSet rs = DB.getData("SELECT * FROM teacher_payment WHERE teacher_id='" + cmboTeaId + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM teacher_payment_invoice WHERE teacher_payment_id = '" + rs.getString("teacher_payment_id") + "' AND date LIKE '" + fullYear + "%'");
                        while (rs1.next()) {
                            String teaID = cmboTeaId;
                            String teaName = cmboTeaName;
                            String invoice = rs1.getString("teacher_payment_invoice_id");
                            String amount = rs1.getString("amount");
                            String date = rs1.getString("date");
                            String subject = fullSub;
                            String medium = cmboMedium;

                            tool.addToTable(tbl_teacherPay, teaID, teaName, invoice, amount, date, subject, medium);
                        }
                        rs1.close();
                    }
                    rs.close();
                    double Value = getTotalValue(tbl_teacherPay, 3);
                    lbl_teacherPaymentTotal.setText(Value + "");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0008-02-03 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
            if (cmb_teacherPayDayChoose.getSelectedItem().equals("Custome")) {
                tool.clearTable(tbl_teacherPay);

                Date d = new Date();
                String from = new SimpleDateFormat("yyyy-MM-dd").format(dc_teacherPayFrom.getDate());
                String to = new SimpleDateFormat("yyyy-MM-dd").format(dc_teacherPayTo.getDate());
                try {
                    ResultSet rs = DB.getData("SELECT * FROM teacher_payment WHERE teacher_id='" + cmboTeaId + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM teacher_payment_invoice WHERE teacher_payment_id = '" + rs.getString("teacher_payment_id") + "' AND date BETWEEN '" + from + "' AND '" + to + "'");
                        while (rs1.next()) {
                            String teaID = cmboTeaId;
                            String teaName = cmboTeaName;
                            String invoice = rs1.getString("teacher_payment_invoice_id");
                            String amount = rs1.getString("amount");
                            String date = rs1.getString("date");
                            String subject = fullSub;
                            String medium = cmboMedium;

                            tool.addToTable(tbl_teacherPay, teaID, teaName, invoice, amount, date, subject, medium);
                        }
                        rs1.close();
                    }
                    rs.close();
                    double Value = getTotalValue(tbl_teacherPay, 3);
                    lbl_teacherPaymentTotal.setText(Value + "");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0008-02-04 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }

        }
    }

    /////////////////////////////////////////////////////////////////////////////
    ////////-----------------Other Expences search Panel---------------//////////
    /////////////////////////////////////////////////////////////////////////////
    void otherExpencesAllInvisible() {
        lbl_otherExpenceDay.setVisible(false);
        lbl_otherExpenceMonth.setVisible(false);
        lbl_otherExpenceYear.setVisible(false);
        dc_otherExpenceDate.setVisible(false);
        dc_otherExpenceMonth.setVisible(false);
        dc_otherExpenceYear.setVisible(false);
        lbl_otherExpenceFrom.setVisible(false);
        lbl_otherExpenceTo.setVisible(false);
        dc_otherExpenceFrom.setVisible(false);
        dc_otherExpenceTo.setVisible(false);
    }

    void otherExpencesComboDaily() {

        ////////visible/////////
        lbl_otherExpenceDay.setVisible(true);
        lbl_otherExpenceMonth.setVisible(true);
        lbl_otherExpenceYear.setVisible(true);
        dc_otherExpenceDate.setVisible(true);
        dc_otherExpenceMonth.setVisible(true);
        dc_otherExpenceYear.setVisible(true);

        /////////not visible/////////
        lbl_otherExpenceFrom.setVisible(false);
        lbl_otherExpenceTo.setVisible(false);
        dc_otherExpenceFrom.setVisible(false);
        dc_otherExpenceTo.setVisible(false);

    }

    void otherExpencesComboMonthly() {

        ///////////visible////////////
        lbl_otherExpenceMonth.setVisible(true);
        lbl_otherExpenceYear.setVisible(true);
        dc_otherExpenceMonth.setVisible(true);
        dc_otherExpenceYear.setVisible(true);
        ///////Not visible/////////
        lbl_otherExpenceDay.setVisible(false);
        lbl_otherExpenceFrom.setVisible(false);
        lbl_otherExpenceTo.setVisible(false);
        dc_otherExpenceFrom.setVisible(false);
        dc_otherExpenceTo.setVisible(false);
        dc_otherExpenceDate.setVisible(false);

    }

    void otherExpencesComboAnnualy() {

        ////////////Visible////////////
        lbl_otherExpenceYear.setVisible(true);
        dc_otherExpenceYear.setVisible(true);

        ////////////Not Visible////////////
        lbl_otherExpenceDay.setVisible(false);
        lbl_otherExpenceMonth.setVisible(false);
        lbl_otherExpenceFrom.setVisible(false);
        lbl_otherExpenceTo.setVisible(false);
        dc_otherExpenceFrom.setVisible(false);
        dc_otherExpenceTo.setVisible(false);
        dc_otherExpenceMonth.setVisible(false);
        dc_otherExpenceDate.setVisible(false);
    }

    void otherExpencesComboCustome() {

        /////////Visible//////////
        lbl_otherExpenceFrom.setVisible(true);
        lbl_otherExpenceTo.setVisible(true);
        dc_otherExpenceFrom.setVisible(true);
        dc_otherExpenceTo.setVisible(true);
        ////////Not Visible///////
        lbl_otherExpenceYear.setVisible(false);
        lbl_otherExpenceDay.setVisible(false);
        lbl_otherExpenceMonth.setVisible(false);
        dc_otherExpenceYear.setVisible(false);
        dc_otherExpenceMonth.setVisible(false);
        dc_otherExpenceDate.setVisible(false);
    }

    ////////////  Other Expences daily Monthly annualy custome selecting///////
    void otherExpencesComboDaySelect() {
        if (cmb_otherExpenceCombo.getSelectedIndex() == 0) {
            otherExpencesComboDaily();
        }
        if (cmb_otherExpenceCombo.getSelectedIndex() == 1) {
            otherExpencesComboMonthly();
        }
        if (cmb_otherExpenceCombo.getSelectedIndex() == 2) {
            otherExpencesComboAnnualy();
        }
        if (cmb_otherExpenceCombo.getSelectedIndex() == 3) {
            otherExpencesComboCustome();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////
    ////////------- Other Expenses Details search query-----------------------/////
    //////////////////////////////////////////////////////////////////////////

    void OtherExpensesSearchDetails() {

        int year = dc_otherExpenceYear.getYear();
        int month = dc_otherExpenceMonth.getMonth() + 1;
        int day = dc_otherExpenceDate.getDay();
        String other = "Other";
        ////---- seach details--Daily---------------
        if (cmb_otherExpenceCombo.getSelectedItem().toString().equals("Daily")) {
            tool.clearTable(tbl_otherExpences);
            String fullDate;
            if (month > 9) {
                fullDate = year + "-" + month + "-" + day;
            } else {
                fullDate = year + "-0" + month + "-" + day;
            }

            try {
                ResultSet rs = DB.getData("SELECT * FROM expence WHERE date='" + fullDate + "' AND expence_type='" + other + "'");
                while (rs.next()) {
                    String ID = rs.getString("expence_id");
                    String date = rs.getString("date");
                    String description = rs.getString("description");
                    String amount = rs.getString("amount");

                    tool.addToTable(tbl_otherExpences, ID, date, description, amount);

                }
                rs.close();
                double Value = getTotalValue(tbl_otherExpences, 3);
                lbl_otherExpencesTotal.setText(Value + "");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ERROR - AdEX 0009-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

        }
        /////---- search details---Monthly--------------------
        if (cmb_otherExpenceCombo.getSelectedItem().toString().equals("Monthly")) {
            tool.clearTable(tbl_otherExpences);
            String fullDate;
            if (month > 9) {
                fullDate = year + "-" + month;
            } else {
                fullDate = year + "-0" + month;
            }

            try {
                ResultSet rs = DB.getData("SELECT * FROM expence WHERE date LIKE '" + fullDate + "%' AND expence_type='" + other + "'");
                while (rs.next()) {
                    String ID = rs.getString("expence_id");
                    String date = rs.getString("date");
                    String description = rs.getString("description");
                    String amount = rs.getString("amount");

                    tool.addToTable(tbl_otherExpences, ID, date, description, amount);

                }
                rs.close();
                double Value = getTotalValue(tbl_otherExpences, 3);
                lbl_otherExpencesTotal.setText(Value + "");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ERROR - AdEX 0009-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

        }
        /////-----search details---Annually-----------------
        if (cmb_otherExpenceCombo.getSelectedItem().toString().equals("Annually")) {
            System.out.println("annuall");
            tool.clearTable(tbl_otherExpences);
            String fullDate = Integer.toString(year);
            try {
                ResultSet rs = DB.getData("SELECT * FROM expence WHERE date LIKE '" + fullDate + "%' AND expence_type='" + other + "'");
                while (rs.next()) {
                    System.out.println("");
                    String ID = rs.getString("expence_id");
                    String date = rs.getString("date");
                    String description = rs.getString("description");
                    String amount = rs.getString("amount");

                    tool.addToTable(tbl_otherExpences, ID, date, description, amount);

                }
                rs.close();
                double Value = getTotalValue(tbl_otherExpences, 3);
                lbl_otherExpencesTotal.setText(Value + "");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ERROR - AdEX 0009-03 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));

            }

        }
        /////---search details---Custome-----------------
        if (cmb_otherExpenceCombo.getSelectedItem().toString().equals("Custome")) {
            tool.clearTable(tbl_otherExpences);

            Date d = new Date();
            String from = new SimpleDateFormat("yyyy-MM-dd").format(dc_otherExpenceFrom.getDate());
            String to = new SimpleDateFormat("yyyy-MM-dd").format(dc_otherExpenceTo.getDate());

            try {
                ResultSet rs = DB.getData("SELECT * FROM expence WHERE date BETWEEN '" + from + "' AND '" + to + "'");
                while (rs.next()) {
                    ResultSet rs1 = DB.getData("SELECT * FROM expence WHERE expence_type='" + other + "'");
                    while (rs1.next()) {
                        String ID = rs.getString("expence_id");
                        String date = rs1.getString("date");
                        String description = rs1.getString("description");
                        String amount = rs1.getString("amount");

                        tool.addToTable(tbl_otherExpences, ID, date, description, amount);
                    }
                    rs1.close();
                }
                rs.close();
                double Value = getTotalValue(tbl_otherExpences, 3);
                lbl_otherExpencesTotal.setText(Value + "");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ERROR - AdEX 0009-04 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));

            }

        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////---------- EPF ETF-------------------------------/////////////////////////////////////
    ////////////////-------------------------------------------------------------------------------/////////////////
    ///////////------------------- Set all invisible---------------------------------------------////////////////////
    void epfSetaAllInvisible() {

        cmb_epfDateChooser.setVisible(false);
        txt_epfEmployeeID.setVisible(false);
        dc_epfMonth.setVisible(false);
        dc_epfYear.setVisible(false);
        dc_epfTo.setVisible(false);
        dc_EPFFROM.setVisible(false);
        lbl_epfFrom.setVisible(false);
        lbl_epfTo.setVisible(false);

    }

    /////////////// --------------------------set all visible---------------------------------/////////////////////
    void epfSetaAllVisible() {

        txt_epfEmployeeID.setVisible(true);
        cmb_epfDateChooser.setVisible(true);

        cmb_epfDateChooser.setEnabled(false);
        txt_epfEmployeeID.setEnabled(false);

    }

    ///////////---------------------etf epf search data fro DB to table---------------------------------/////////////////////
    void epfSearchDateToTable() {
        if (cmb_epfDateOrID.getSelectedItem().toString().equals("Date")) {
            int year = dc_epfYear.getYear();
            int month = dc_epfMonth.getMonth() + 1;
            String fullDate = "";
            String fullYear = Integer.toString(year);
            double epfEmployee = 0.0;
            String empID = "";
            String empFullName = "";
            if (month > 9) {
                fullDate = year + "-" + month;
            } else {
                fullDate = year + "-0" + month;
            }
            if (cmb_epfDateChooser.getSelectedItem().toString().equals("Monthly")) {
                try {
                    ResultSet rs = DB.getData("SELECT * FROM salary WHERE month LIKE '" + fullDate + "%'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM etf_epf WHERE salary_id='" + rs.getString("salary_id") + "'");
                        while (rs1.next()) {
                            epfEmployee = Double.parseDouble(rs1.getString("amount"));
                        }
                        rs1.close();
                        ResultSet rs4 = DB.getData("SELECT f_name,l_name FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                        while (rs4.next()) {
                            System.out.println("rs4");
                            String fNmae = rs4.getString("f_name");
                            String lName = rs4.getString("l_name");
                            empFullName = fNmae + " " + lName;
                            empID = rs.getString("emp_id");
                        }
                        rs4.close();
                        ResultSet rs2 = DB.getData("SELECT * FROM salary_invoice WHERE salary_id='" + rs.getString("salary_id") + "' AND date LIKE '" + fullDate + "%'");
                        while (rs2.next()) {
                            System.out.println("rs2");
                            ResultSet rs3 = DB.getData("SELECT * FROM expence WHERE salary_invoice_id='" + rs2.getString("salary_invoice_id") + "' AND date LIKE '" + fullDate + "%' AND expence_type='" + "etf/epf" + "'");
                            while (rs3.next()) {
                                System.out.println("rs3");
                                //////----calculating company epf/etf amount-------//////
                                double epfAll = Double.parseDouble(rs3.getString("amount"));
                                String epfCompany = Double.toString(epfAll - epfEmployee) + "0";
                                String date = rs3.getString("date");
                                String fullAmount = Double.toString(epfAll) + "0";
                                String epfEmployeAll = Double.toString(epfEmployee) + "0";
                                String invoiceID = rs2.getString("salary_invoice_id");
                                tool.addToTable(tbl_epfTable, empID, empFullName, date, invoiceID, epfCompany, epfEmployeAll, fullAmount);

                            }
                            rs3.close();
                        }
                        rs2.close();
                    }
                    rs.close();
                    double CompanyValue = getTotalValue(tbl_epfTable, 4);
                    lbl_epfCompanyTotal.setText(CompanyValue + "0");
                    double employeeValue = getTotalValue(tbl_epfTable, 5);
                    lbl_epfEmployeeTotal.setText(employeeValue + "0");
                    double Value = getTotalValue(tbl_epfTable, 6);
                    lbl_epfTotal.setText(Value + "0");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0010-01-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
            if (cmb_epfDateChooser.getSelectedItem().toString().equals("Annually")) {
                try {
                    ResultSet rs = DB.getData("SELECT * FROM salary WHERE month LIKE '" + fullYear + "%'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM etf_epf WHERE salary_id='" + rs.getString("salary_id") + "'");
                        while (rs1.next()) {
                            epfEmployee = Double.parseDouble(rs1.getString("amount"));
                        }
                        rs1.close();
                        ResultSet rs4 = DB.getData("SELECT f_name,l_name FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                        while (rs4.next()) {
                            String fNmae = rs4.getString("f_name");
                            String lName = rs4.getString("l_name");
                            empFullName = fNmae + " " + lName;
                            empID = rs.getString("emp_id");
                        }
                        rs4.close();
                        ResultSet rs2 = DB.getData("SELECT * FROM salary_invoice WHERE salary_id='" + rs.getString("salary_id") + "' AND date LIKE '" + fullYear + "%'");
                        while (rs2.next()) {
                            ResultSet rs3 = DB.getData("SELECT * FROM expence WHERE salary_invoice_id='" + rs2.getString("salary_invoice_id") + "' AND date LIKE '" + fullYear + "%' AND expence_type='" + "etf/epf" + "'");
                            while (rs3.next()) {
                                //////----calculating company epf/etf amount-------//////
                                double epfAll = Double.parseDouble(rs3.getString("amount"));
                                String epfCompany = Double.toString(epfAll - epfEmployee) + "0";
                                String date = rs3.getString("date");
                                String fullAmount = Double.toString(epfAll) + "0";
                                String epfEmployeAll = Double.toString(epfEmployee) + "0";
                                String invoiceID = rs2.getString("salary_invoice_id");
                                tool.addToTable(tbl_epfTable, empID, empFullName, date, invoiceID, epfCompany, epfEmployeAll, fullAmount);

                            }
                            rs3.close();
                        }
                        rs2.close();
                    }
                    rs.close();
                    double CompanyValue = getTotalValue(tbl_epfTable, 4);
                    lbl_epfCompanyTotal.setText(CompanyValue + "0");
                    double employeeValue = getTotalValue(tbl_epfTable, 5);
                    lbl_epfEmployeeTotal.setText(employeeValue + "0");
                    double Value = getTotalValue(tbl_epfTable, 6);
                    lbl_epfTotal.setText(Value + "0");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0010-01-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
            if (cmb_epfDateChooser.getSelectedItem().toString().equals("Custome")) {
                Date d = new Date();
                String from = new SimpleDateFormat("yyyy-MM-dd").format(dc_EPFFROM.getDate());
                String to = new SimpleDateFormat("yyyy-MM-dd").format(dc_epfTo.getDate());
                try {
                    ResultSet rs = DB.getData("SELECT * FROM salary WHERE month BETWEEN '" + from + "' AND '" + to + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM etf_epf WHERE salary_id='" + rs.getString("salary_id") + "'");
                        while (rs1.next()) {
                            epfEmployee = Double.parseDouble(rs1.getString("amount"));
                        }
                        rs1.close();
                        ResultSet rs4 = DB.getData("SELECT f_name,l_name FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                        while (rs4.next()) {
                            String fNmae = rs4.getString("f_name");
                            String lName = rs4.getString("l_name");
                            empFullName = fNmae + " " + lName;
                            empID = rs.getString("emp_id");
                        }
                        rs4.close();
                        ResultSet rs2 = DB.getData("SELECT * FROM salary_invoice WHERE salary_id='" + rs.getString("salary_id") + "' AND date BETWEEN '" + from + "' AND '" + to + "'");
                        while (rs2.next()) {
                            ResultSet rs3 = DB.getData("SELECT * FROM expence WHERE salary_invoice_id='" + rs2.getString("salary_invoice_id") + "' AND date BETWEEN '" + from + "' AND '" + to + "' AND expence_type='" + "etf/epf" + "'");
                            while (rs3.next()) {
                                //////----calculating company epf/etf amount-------//////
                                double epfAll = Double.parseDouble(rs3.getString("amount"));
                                String epfCompany = Double.toString(epfAll - epfEmployee) + "0";
                                String date = rs3.getString("date");
                                String fullAmount = Double.toString(epfAll) + "0";
                                String epfEmployeAll = Double.toString(epfEmployee) + "0";
                                String invoiceID = rs2.getString("salary_invoice_id");
                                tool.addToTable(tbl_epfTable, empID, empFullName, date, invoiceID, epfCompany, epfEmployeAll, fullAmount);

                            }
                            rs3.close();
                        }
                        rs2.close();
                    }
                    rs.close();
                    double CompanyValue = getTotalValue(tbl_epfTable, 4);
                    lbl_epfCompanyTotal.setText(CompanyValue + "0");
                    double employeeValue = getTotalValue(tbl_epfTable, 5);
                    lbl_epfEmployeeTotal.setText(employeeValue + "0");
                    double Value = getTotalValue(tbl_epfTable, 6);
                    lbl_epfTotal.setText(Value + "0");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0010-01-03 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
        }
        if (cmb_epfDateOrID.getSelectedItem().toString().equals("Employee ID")) {
            int year = dc_epfYear.getYear();
            int month = dc_epfMonth.getMonth() + 1;
            String fullDate = "";
            String fullYear = Integer.toString(year);
            double epfEmployee = 0.0;
            String empID = txt_epfEmployeeID.getText();
            String empFullName = "";
            if (month > 9) {
                fullDate = year + "-" + month;
            } else {
                fullDate = year + "-0" + month;
            }

            if (cmb_epfDateChooser.getSelectedItem().toString().equals("Monthly")) {
                try {
                    ResultSet rs = DB.getData("SELECT * FROM salary WHERE month LIKE '" + fullDate + "%' AND emp_id='" + empID + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM etf_epf WHERE salary_id='" + rs.getString("salary_id") + "'");
                        while (rs1.next()) {
                            epfEmployee = Double.parseDouble(rs1.getString("amount"));
                        }
                        rs1.close();
                        ResultSet rs4 = DB.getData("SELECT f_name,l_name FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                        while (rs4.next()) {
                            String fNmae = rs4.getString("f_name");
                            String lName = rs4.getString("l_name");
                            empFullName = fNmae + " " + lName;
                            empID = rs.getString("emp_id");
                        }
                        rs4.close();
                        ResultSet rs2 = DB.getData("SELECT * FROM salary_invoice WHERE salary_id='" + rs.getString("salary_id") + "' AND date LIKE '" + fullDate + "%'");
                        while (rs2.next()) {
                            ResultSet rs3 = DB.getData("SELECT * FROM expence WHERE salary_invoice_id='" + rs2.getString("salary_invoice_id") + "' AND date LIKE '" + fullDate + "%' AND expence_type='" + "etf/epf" + "'");
                            while (rs3.next()) {
                                //////----calculating company epf/etf amount-------//////
                                double epfAll = Double.parseDouble(rs3.getString("amount"));
                                String epfCompany = Double.toString(epfAll - epfEmployee) + "0";
                                String date = rs3.getString("date");
                                String fullAmount = Double.toString(epfAll) + "0";
                                String epfEmployeAll = Double.toString(epfEmployee) + "0";
                                String invoiceID = rs2.getString("salary_invoice_id");
                                tool.addToTable(tbl_epfTable, empID, empFullName, date, invoiceID, epfCompany, epfEmployeAll, fullAmount);

                            }
                            rs3.close();
                        }
                        rs2.close();
                    }
                    rs.close();
                    double CompanyValue = getTotalValue(tbl_epfTable, 4);
                    lbl_epfCompanyTotal.setText(CompanyValue + "0");
                    double employeeValue = getTotalValue(tbl_epfTable, 5);
                    lbl_epfEmployeeTotal.setText(employeeValue + "0");
                    double Value = getTotalValue(tbl_epfTable, 6);
                    lbl_epfTotal.setText(Value + "0");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0010-02-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
            if (cmb_epfDateChooser.getSelectedItem().toString().equals("Annually")) {
                try {
                    ResultSet rs = DB.getData("SELECT * FROM salary WHERE month LIKE '" + fullYear + "%' AND emp_id='" + empID + "'");
                    while (rs.next()) {
                        ResultSet rs1 = DB.getData("SELECT * FROM etf_epf WHERE salary_id='" + rs.getString("salary_id") + "'");
                        while (rs1.next()) {
                            epfEmployee = Double.parseDouble(rs1.getString("amount"));
                        }
                        rs1.close();
                        ResultSet rs4 = DB.getData("SELECT f_name,l_name FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                        while (rs4.next()) {
                            String fNmae = rs4.getString("f_name");
                            String lName = rs4.getString("l_name");
                            empFullName = fNmae + " " + lName;
                            empID = rs.getString("emp_id");
                        }
                        rs4.close();
                        ResultSet rs2 = DB.getData("SELECT * FROM salary_invoice WHERE salary_id='" + rs.getString("salary_id") + "' AND date LIKE '" + fullYear + "%'");
                        while (rs2.next()) {
                            ResultSet rs3 = DB.getData("SELECT * FROM expence WHERE salary_invoice_id='" + rs2.getString("salary_invoice_id") + "' AND date LIKE '" + fullYear + "%' AND expence_type='" + "etf/epf" + "'");
                            while (rs3.next()) {
                                //////----calculating company epf/etf amount-------//////
                                double epfAll = Double.parseDouble(rs3.getString("amount"));
                                String epfCompany = Double.toString(epfAll - epfEmployee) + "0";
                                String date = rs3.getString("date");
                                String fullAmount = Double.toString(epfAll) + "0";
                                String epfEmployeAll = Double.toString(epfEmployee) + "0";
                                String invoiceID = rs2.getString("salary_invoice_id");
                                tool.addToTable(tbl_epfTable, empID, empFullName, date, invoiceID, epfCompany, epfEmployeAll, fullAmount);

                            }
                            rs3.close();
                        }
                        rs2.close();
                    }
                    rs.close();
                    double CompanyValue = getTotalValue(tbl_epfTable, 4);
                    lbl_epfCompanyTotal.setText(CompanyValue + "0");
                    double employeeValue = getTotalValue(tbl_epfTable, 5);
                    lbl_epfEmployeeTotal.setText(employeeValue + "0");
                    double Value = getTotalValue(tbl_epfTable, 6);
                    lbl_epfTotal.setText(Value + "0");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - AdEX 0010-02-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            }
            if (cmb_epfDateChooser.getSelectedItem().toString().equals("Custome")) {
                Date d = new Date();
                Date d1 = dc_EPFFROM.getDate();
                Date d2 = dc_epfTo.getDate();
                String fromString = d1 + "";
                String ToString = d2 + "";
                System.out.println(fromString);
                System.out.println(ToString);
                if (fromString.equals(null + "") || ToString.equals(null + "")) {
                    JOptionPane.showMessageDialog(this, "Please Enter the Date");
                } else if (!(fromString.equals(null + "") && ToString.equals(null + ""))) {
                    String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(dc_EPFFROM.getDate());
                    String toDate = new SimpleDateFormat("yyyy-MM-dd").format(dc_epfTo.getDate());
                    try {
                        ResultSet rs = DB.getData("SELECT * FROM salary WHERE emp_id='" + empID + "' AND month BETWEEN '" + fromDate + "' AND '" + toDate + "'");
                        while (rs.next()) {
                            ResultSet rs1 = DB.getData("SELECT * FROM etf_epf WHERE salary_id='" + rs.getString("salary_id") + "'");
                            while (rs1.next()) {
                                epfEmployee = Double.parseDouble(rs1.getString("amount"));
                            }
                            rs1.close();
                            ResultSet rs4 = DB.getData("SELECT f_name,l_name FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                            while (rs4.next()) {
                                String fNmae = rs4.getString("f_name");
                                String lName = rs4.getString("l_name");
                                empFullName = fNmae + " " + lName;
                                empID = rs.getString("emp_id");
                            }
                            ResultSet rs2 = DB.getData("SELECT * FROM salary_invoice WHERE salary_id='" + rs.getString("salary_id") + "' AND date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
                            while (rs2.next()) {
                                ResultSet rs3 = DB.getData("SELECT * FROM expence WHERE salary_invoice_id='" + rs2.getString("salary_invoice_id") + "' AND date BETWEEN '" + fromDate + "' AND '" + toDate + "' AND expence_type='" + "etf/epf" + "'");
                                while (rs3.next()) {
                                    //////----calculating company epf/etf amount-------//////
                                    double epfAll = Double.parseDouble(rs3.getString("amount"));
                                    String epfCompany = Double.toString(epfAll - epfEmployee) + "0";
                                    String date = rs3.getString("date");
                                    String fullAmount = Double.toString(epfAll) + "0";
                                    String epfEmployeAll = Double.toString(epfEmployee) + "0";
                                    String invoiceID = rs2.getString("salary_invoice_id");
                                    tool.addToTable(tbl_epfTable, empID, empFullName, date, invoiceID, epfCompany, epfEmployeAll, fullAmount);

                                }
                                rs3.close();
                            }
                            rs2.close();
                        }
                        rs.close();
                        double CompanyValue = getTotalValue(tbl_epfTable, 4);
                        lbl_epfCompanyTotal.setText(CompanyValue + "0");
                        double employeeValue = getTotalValue(tbl_epfTable, 5);
                        lbl_epfEmployeeTotal.setText(employeeValue + "0");
                        double Value = getTotalValue(tbl_epfTable, 6);
                        lbl_epfTotal.setText(Value + "0");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "ERROR - AdEX 0010-02-03 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                        logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    }
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
        jLabel7 = new javax.swing.JLabel();
        Expences = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmb_salaryDetailsMainCombo = new javax.swing.JComboBox();
        btn_salaryDetailsSearch = new javax.swing.JButton();
        cmb_salaryDetailsDay = new javax.swing.JComboBox();
        dc_salaryDetailsMonth = new com.toedter.calendar.JMonthChooser();
        dc_salaryDetailsYear = new com.toedter.calendar.JYearChooser();
        lbl_salaryDetailsYear = new javax.swing.JLabel();
        lbl_salaryDetailsMonth = new javax.swing.JLabel();
        cmb_salaryDetailsJobTitle = new javax.swing.JComboBox();
        jPanel19 = new javax.swing.JPanel();
        btn_salaryGenerateReport = new javax.swing.JButton();
        lbl_salaryDetailsTotal = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_salary = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_salaryDetailsChangeSalary = new javax.swing.JTextField();
        btn_salaryDetailsEdit = new javax.swing.JButton();
        cmb_salaryDetailsJob = new javax.swing.JComboBox();
        btn_salaryDetailsChangeSalary = new javax.swing.JButton();
        lbl_salaryDetailsChangeSalary = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_salaryDetailsSalaryPerDay = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbl_salaryDetailsEmployeeRate = new javax.swing.JLabel();
        lbl_salaryDetailsCompanyRate = new javax.swing.JLabel();
        txt_salaryDetailsCompanyRate = new javax.swing.JTextField();
        txt_salaryDetailsEmployeeRate = new javax.swing.JTextField();
        btn_salaryDetailsEtfAdd = new javax.swing.JButton();
        btn_salaryDetailsEtfEdit = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmb_teacherPayMainCombo = new javax.swing.JComboBox();
        btn_teacherPaySearch = new javax.swing.JButton();
        cmb_teacherPayDayChoose = new javax.swing.JComboBox();
        cmb_teacherPayName = new javax.swing.JComboBox();
        dc_teacherPayYear = new com.toedter.calendar.JYearChooser();
        dc_teacherPayFrom = new com.toedter.calendar.JDateChooser();
        lbl_teacherPayFrom = new javax.swing.JLabel();
        lbl_teacherPayTo = new javax.swing.JLabel();
        dc_teacherPayTo = new com.toedter.calendar.JDateChooser();
        dc_teacherPayMonth = new com.toedter.calendar.JMonthChooser();
        lbl_teacherPayDay = new javax.swing.JLabel();
        dc_teacherPayDate = new com.toedter.calendar.JDayChooser();
        lbl_teacherPayYear = new javax.swing.JLabel();
        lbl_teacherPayMonth = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_teacherPay = new javax.swing.JTable();
        jLabel45 = new javax.swing.JLabel();
        lbl_teacherPaymentTotal = new javax.swing.JLabel();
        btn_teacherPaymentGenerateReport1 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmb_otherExpenceCombo = new javax.swing.JComboBox();
        btn_otherExpenceSearch = new javax.swing.JButton();
        dc_otherExpenceDate = new com.toedter.calendar.JDayChooser();
        dc_otherExpenceTo = new com.toedter.calendar.JDateChooser();
        lbl_otherExpenceFrom = new javax.swing.JLabel();
        lbl_otherExpenceDay = new javax.swing.JLabel();
        dc_otherExpenceYear = new com.toedter.calendar.JYearChooser();
        dc_otherExpenceFrom = new com.toedter.calendar.JDateChooser();
        dc_otherExpenceMonth = new com.toedter.calendar.JMonthChooser();
        lbl_otherExpenceTo = new javax.swing.JLabel();
        lbl_otherExpenceYear = new javax.swing.JLabel();
        lbl_otherExpenceMonth = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btn_OtherpaymentGenerateReport2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_otherExpences = new javax.swing.JTable();
        lbl_otherExpencesTotal = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btn_OtherpaymentGenerateReport3 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_epfTable = new javax.swing.JTable();
        lbl_epfTotal = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        lbl_epfCompanyTotal = new javax.swing.JLabel();
        lbl_epfEmployeeTotal = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cmb_epfDateOrID = new javax.swing.JComboBox();
        cmb_epfDateChooser = new javax.swing.JComboBox();
        txt_epfEmployeeID = new javax.swing.JTextField();
        dc_epfMonth = new com.toedter.calendar.JMonthChooser();
        dc_epfYear = new com.toedter.calendar.JYearChooser();
        jButton4 = new javax.swing.JButton();
        dc_epfTo = new com.toedter.calendar.JDateChooser();
        dc_EPFFROM = new com.toedter.calendar.JDateChooser();
        lbl_epfFrom = new javax.swing.JLabel();
        lbl_epfTo = new javax.swing.JLabel();
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

        jLabel7.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel7.setText("Admin Panel - Expenses");
        pnl_header.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 340, 30));

        jPanel1.add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 50));

        Expences.setOpaque(false);
        Expences.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExpencesMouseClicked(evt);
            }
        });

        jTabbedPane4.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jTabbedPane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane4MouseClicked(evt);
            }
        });

        jPanel12.setOpaque(false);
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel18.setOpaque(false);
        jPanel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel18MouseClicked(evt);
            }
        });
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jLabel3.setText("Search By :");
        jPanel18.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 30));

        cmb_salaryDetailsMainCombo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_salaryDetailsMainCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Date", "Job ID/Name" }));
        cmb_salaryDetailsMainCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_salaryDetailsMainComboActionPerformed(evt);
            }
        });
        jPanel18.add(cmb_salaryDetailsMainCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 200, -1));

        btn_salaryDetailsSearch.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_salaryDetailsSearch.setText("Search");
        btn_salaryDetailsSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salaryDetailsSearchActionPerformed(evt);
            }
        });
        jPanel18.add(btn_salaryDetailsSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 114, -1));

        cmb_salaryDetailsDay.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_salaryDetailsDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Monthly", "Annually" }));
        cmb_salaryDetailsDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_salaryDetailsDayActionPerformed(evt);
            }
        });
        jPanel18.add(cmb_salaryDetailsDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 200, -1));

        dc_salaryDetailsMonth.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        dc_salaryDetailsMonth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_salaryDetailsMonthFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_salaryDetailsMonthFocusGained(evt);
            }
        });
        jPanel18.add(dc_salaryDetailsMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 150, 30));

        dc_salaryDetailsYear.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_salaryDetailsYearFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_salaryDetailsYearFocusGained(evt);
            }
        });
        jPanel18.add(dc_salaryDetailsYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 150, 30));

        lbl_salaryDetailsYear.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_salaryDetailsYear.setText("Year :");
        jPanel18.add(lbl_salaryDetailsYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 50, 30));

        lbl_salaryDetailsMonth.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_salaryDetailsMonth.setText("Month :");
        jPanel18.add(lbl_salaryDetailsMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, -1, 30));

        cmb_salaryDetailsJobTitle.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_salaryDetailsJobTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_salaryDetailsJobTitleActionPerformed(evt);
            }
        });
        jPanel18.add(cmb_salaryDetailsJobTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 200, -1));

        jPanel12.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 590, 189));

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Result", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel19.setOpaque(false);
        jPanel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel19MouseClicked(evt);
            }
        });

        btn_salaryGenerateReport.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_salaryGenerateReport.setText("Generate Report");
        btn_salaryGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salaryGenerateReportActionPerformed(evt);
            }
        });

        lbl_salaryDetailsTotal.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        lbl_salaryDetailsTotal.setText("00.00");

        jLabel41.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel41.setText("Full Amount :");

        tbl_salary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Employee Name", "Month", "Attendance", "Leaves", "Amount", "Advance", "ETF/EPF", "Salary"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_salary.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tbl_salary);
        if (tbl_salary.getColumnModel().getColumnCount() > 0) {
            tbl_salary.getColumnModel().getColumn(2).setResizable(false);
            tbl_salary.getColumnModel().getColumn(5).setResizable(false);
            tbl_salary.getColumnModel().getColumn(5).setHeaderValue("Amount");
            tbl_salary.getColumnModel().getColumn(6).setResizable(false);
            tbl_salary.getColumnModel().getColumn(6).setHeaderValue("Advance");
            tbl_salary.getColumnModel().getColumn(7).setResizable(false);
            tbl_salary.getColumnModel().getColumn(7).setHeaderValue("ETF/EPF");
            tbl_salary.getColumnModel().getColumn(8).setResizable(false);
            tbl_salary.getColumnModel().getColumn(8).setHeaderValue("Salary");
        }

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1300, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_salaryDetailsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_salaryGenerateReport, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_salaryDetailsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_salaryGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel12.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 195, 1332, -1));

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Basic salary", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel20.setOpaque(false);
        jPanel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel20MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jLabel4.setText("Job Category :");

        txt_salaryDetailsChangeSalary.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        btn_salaryDetailsEdit.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        btn_salaryDetailsEdit.setText("Edit");
        btn_salaryDetailsEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salaryDetailsEditActionPerformed(evt);
            }
        });

        cmb_salaryDetailsJob.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        btn_salaryDetailsChangeSalary.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        btn_salaryDetailsChangeSalary.setText("Add");
        btn_salaryDetailsChangeSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salaryDetailsChangeSalaryActionPerformed(evt);
            }
        });

        lbl_salaryDetailsChangeSalary.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_salaryDetailsChangeSalary.setText("New Payment :");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jLabel5.setText("Salary per Hour :");

        txt_salaryDetailsSalaryPerDay.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        txt_salaryDetailsSalaryPerDay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_salaryDetailsSalaryPerDayMouseClicked(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jButton3.setText("Add");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(lbl_salaryDetailsChangeSalary)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_salaryDetailsChangeSalary, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(cmb_salaryDetailsJob, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_salaryDetailsSalaryPerDay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_salaryDetailsChangeSalary, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(btn_salaryDetailsEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmb_salaryDetailsJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_salaryDetailsEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_salaryDetailsChangeSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_salaryDetailsChangeSalary)
                    .addComponent(lbl_salaryDetailsChangeSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_salaryDetailsSalaryPerDay)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        jPanel12.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, 400, 190));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ETF/EPF Rates", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel2.setOpaque(false);

        lbl_salaryDetailsEmployeeRate.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_salaryDetailsEmployeeRate.setText("Employee :");

        lbl_salaryDetailsCompanyRate.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_salaryDetailsCompanyRate.setText("Company :");

        txt_salaryDetailsCompanyRate.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N

        txt_salaryDetailsEmployeeRate.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        txt_salaryDetailsEmployeeRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_salaryDetailsEmployeeRateActionPerformed(evt);
            }
        });
        txt_salaryDetailsEmployeeRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_salaryDetailsEmployeeRateKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_salaryDetailsEmployeeRateKeyTyped(evt);
            }
        });

        btn_salaryDetailsEtfAdd.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        btn_salaryDetailsEtfAdd.setText("Add");
        btn_salaryDetailsEtfAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salaryDetailsEtfAddActionPerformed(evt);
            }
        });

        btn_salaryDetailsEtfEdit.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        btn_salaryDetailsEtfEdit.setText("Edit");
        btn_salaryDetailsEtfEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salaryDetailsEtfEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_salaryDetailsEtfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_salaryDetailsEtfAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_salaryDetailsCompanyRate)
                            .addComponent(lbl_salaryDetailsEmployeeRate))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_salaryDetailsEmployeeRate, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                            .addComponent(txt_salaryDetailsCompanyRate)))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_salaryDetailsEmployeeRate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_salaryDetailsEmployeeRate))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_salaryDetailsCompanyRate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_salaryDetailsCompanyRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_salaryDetailsEtfAdd)
                    .addComponent(btn_salaryDetailsEtfEdit))
                .addGap(0, 35, Short.MAX_VALUE))
        );

        jPanel12.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 0, 340, 190));

        jTabbedPane4.addTab("Salary Details", jPanel12);

        jPanel13.setOpaque(false);
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
        });

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel9.setOpaque(false);
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jLabel2.setText("Search By :");
        jPanel9.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 30));

        cmb_teacherPayMainCombo.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        cmb_teacherPayMainCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Date", "Teacher Details" }));
        cmb_teacherPayMainCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_teacherPayMainComboActionPerformed(evt);
            }
        });
        jPanel9.add(cmb_teacherPayMainCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 170, -1));

        btn_teacherPaySearch.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_teacherPaySearch.setText("Search");
        btn_teacherPaySearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_teacherPaySearchActionPerformed(evt);
            }
        });
        jPanel9.add(btn_teacherPaySearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 140, 114, -1));

        cmb_teacherPayDayChoose.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        cmb_teacherPayDayChoose.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Daily", "Monthly", "Annually", "Custome" }));
        cmb_teacherPayDayChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_teacherPayDayChooseActionPerformed(evt);
            }
        });
        jPanel9.add(cmb_teacherPayDayChoose, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, 170, -1));

        cmb_teacherPayName.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        cmb_teacherPayName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_teacherPayNameActionPerformed(evt);
            }
        });
        jPanel9.add(cmb_teacherPayName, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 290, -1));

        dc_teacherPayYear.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_teacherPayYearFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_teacherPayYearFocusLost(evt);
            }
        });
        jPanel9.add(dc_teacherPayYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 150, 30));

        dc_teacherPayFrom.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        dc_teacherPayFrom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_teacherPayFromFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_teacherPayFromFocusLost(evt);
            }
        });
        jPanel9.add(dc_teacherPayFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 150, 30));

        lbl_teacherPayFrom.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_teacherPayFrom.setText("From :");
        jPanel9.add(lbl_teacherPayFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 50, 30));

        lbl_teacherPayTo.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_teacherPayTo.setText("To :");
        jPanel9.add(lbl_teacherPayTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, -1, 30));

        dc_teacherPayTo.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        dc_teacherPayTo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_teacherPayToFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_teacherPayToFocusLost(evt);
            }
        });
        jPanel9.add(dc_teacherPayTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, 150, 30));

        dc_teacherPayMonth.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        dc_teacherPayMonth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_teacherPayMonthFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_teacherPayMonthFocusLost(evt);
            }
        });
        jPanel9.add(dc_teacherPayMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, 150, 30));

        lbl_teacherPayDay.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_teacherPayDay.setText("Day :");
        jPanel9.add(lbl_teacherPayDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 70, -1, 30));

        dc_teacherPayDate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.add(dc_teacherPayDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, -1, 140));

        lbl_teacherPayYear.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_teacherPayYear.setText("Year :");
        jPanel9.add(lbl_teacherPayYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 50, 30));

        lbl_teacherPayMonth.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_teacherPayMonth.setText("Month :");
        jPanel9.add(lbl_teacherPayMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 70, -1, 30));

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Result", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel17.setOpaque(false);
        jPanel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel17MouseClicked(evt);
            }
        });

        tbl_teacherPay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Teacher ID", "Teacher Name", "Invoice ID", "Amount", "Date", "Subject", "Medium"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_teacherPay.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tbl_teacherPay);

        jLabel45.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel45.setText("Full Amount :");

        lbl_teacherPaymentTotal.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        lbl_teacherPaymentTotal.setText("00.00");

        btn_teacherPaymentGenerateReport1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_teacherPaymentGenerateReport1.setText("Generate Report");
        btn_teacherPaymentGenerateReport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_teacherPaymentGenerateReport1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1290, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_teacherPaymentGenerateReport1)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel45)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_teacherPaymentTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_teacherPaymentTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_teacherPaymentGenerateReport1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Teacher Payments", jPanel13);

        jPanel14.setOpaque(false);
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel7.setOpaque(false);
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel1.setText("Search By :");
        jPanel7.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 31, -1, -1));

        cmb_otherExpenceCombo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_otherExpenceCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Daily", "Monthly", "Annually", "Custome" }));
        cmb_otherExpenceCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_otherExpenceComboActionPerformed(evt);
            }
        });
        jPanel7.add(cmb_otherExpenceCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 180, -1));

        btn_otherExpenceSearch.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_otherExpenceSearch.setText("Search");
        btn_otherExpenceSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_otherExpenceSearchActionPerformed(evt);
            }
        });
        jPanel7.add(btn_otherExpenceSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 114, 30));

        dc_otherExpenceDate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel7.add(dc_otherExpenceDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, -1, 140));

        dc_otherExpenceTo.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        dc_otherExpenceTo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_otherExpenceToFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_otherExpenceToFocusGained(evt);
            }
        });
        jPanel7.add(dc_otherExpenceTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, 150, 30));

        lbl_otherExpenceFrom.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_otherExpenceFrom.setText("From :");
        jPanel7.add(lbl_otherExpenceFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, 30));

        lbl_otherExpenceDay.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_otherExpenceDay.setText("Day :");
        jPanel7.add(lbl_otherExpenceDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, -1, 30));

        dc_otherExpenceYear.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_otherExpenceYearFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_otherExpenceYearFocusGained(evt);
            }
        });
        jPanel7.add(dc_otherExpenceYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 150, 30));

        dc_otherExpenceFrom.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        dc_otherExpenceFrom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_otherExpenceFromFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_otherExpenceFromFocusGained(evt);
            }
        });
        jPanel7.add(dc_otherExpenceFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 150, 30));

        dc_otherExpenceMonth.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        dc_otherExpenceMonth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_otherExpenceMonthFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_otherExpenceMonthFocusGained(evt);
            }
        });
        jPanel7.add(dc_otherExpenceMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, 150, 30));

        lbl_otherExpenceTo.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_otherExpenceTo.setText("To :");
        jPanel7.add(lbl_otherExpenceTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 50, 30));

        lbl_otherExpenceYear.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_otherExpenceYear.setText("Year :");
        jPanel7.add(lbl_otherExpenceYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 50, 30));

        lbl_otherExpenceMonth.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_otherExpenceMonth.setText("Month :");
        jPanel7.add(lbl_otherExpenceMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, -1, 30));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Result", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel8.setOpaque(false);
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });

        btn_OtherpaymentGenerateReport2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_OtherpaymentGenerateReport2.setText("Generate Report");
        btn_OtherpaymentGenerateReport2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_OtherpaymentGenerateReport2ActionPerformed(evt);
            }
        });

        tbl_otherExpences.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Expence ID", "Date", "Description", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_otherExpences.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_otherExpences);

        lbl_otherExpencesTotal.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        lbl_otherExpencesTotal.setText("00.00");

        jLabel51.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel51.setText("Full Amount :");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1310, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addGap(27, 27, 27)
                        .addComponent(lbl_otherExpencesTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(btn_OtherpaymentGenerateReport2)
                        .addGap(52, 52, 52))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(lbl_otherExpencesTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_OtherpaymentGenerateReport2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 1322, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Other expences", jPanel14);

        jPanel3.setOpaque(false);
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Result", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel10.setOpaque(false);
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        btn_OtherpaymentGenerateReport3.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_OtherpaymentGenerateReport3.setText("Generate Report");
        btn_OtherpaymentGenerateReport3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_OtherpaymentGenerateReport3ActionPerformed(evt);
            }
        });

        tbl_epfTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Employee Name", "Date", "Salary Invoice ID", "Company Amount", "Employee Amount", "Full Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_epfTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tbl_epfTable);

        lbl_epfTotal.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        lbl_epfTotal.setText("00.00");

        jLabel52.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel52.setText("Full Amount :");
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel52MouseClicked(evt);
            }
        });

        lbl_epfCompanyTotal.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        lbl_epfCompanyTotal.setText("00.00");

        lbl_epfEmployeeTotal.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        lbl_epfEmployeeTotal.setText("00.00");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1310, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(603, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addGap(58, 58, 58)
                        .addComponent(lbl_epfCompanyTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(lbl_epfEmployeeTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(lbl_epfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(btn_OtherpaymentGenerateReport3)
                        .addGap(52, 52, 52))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_epfTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel52)
                    .addComponent(lbl_epfCompanyTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_epfEmployeeTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_OtherpaymentGenerateReport3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel6.setText("Search By :");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 30));

        cmb_epfDateOrID.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_epfDateOrID.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Date", "Employee ID" }));
        cmb_epfDateOrID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_epfDateOrIDActionPerformed(evt);
            }
        });
        jPanel4.add(cmb_epfDateOrID, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 157, -1));

        cmb_epfDateChooser.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_epfDateChooser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Monthly", "Annually", "Custome" }));
        cmb_epfDateChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_epfDateChooserActionPerformed(evt);
            }
        });
        jPanel4.add(cmb_epfDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 160, -1));

        txt_epfEmployeeID.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txt_epfEmployeeID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_epfEmployeeIDFocusGained(evt);
            }
        });
        jPanel4.add(txt_epfEmployeeID, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 150, -1));

        dc_epfMonth.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jPanel4.add(dc_epfMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 30, 140, 30));
        jPanel4.add(dc_epfYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 30, 80, 30));

        jButton4.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jButton4.setText("Search");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 30, 140, 30));

        dc_epfTo.setDateFormatString("yyyy-MM-dd");
        dc_epfTo.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jPanel4.add(dc_epfTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 30, 150, 30));

        dc_EPFFROM.setDateFormatString("yyyy-MM-dd\n");
        dc_EPFFROM.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jPanel4.add(dc_EPFFROM, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, 150, 30));

        lbl_epfFrom.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_epfFrom.setText("From :");
        jPanel4.add(lbl_epfFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 60, 30));

        lbl_epfTo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_epfTo.setText("To :");
        jPanel4.add(lbl_epfTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 30, -1, 30));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("ETF/EPF Amount", jPanel3);

        javax.swing.GroupLayout ExpencesLayout = new javax.swing.GroupLayout(Expences);
        Expences.setLayout(ExpencesLayout);
        ExpencesLayout.setHorizontalGroup(
            ExpencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExpencesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ExpencesLayout.setVerticalGroup(
            ExpencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExpencesLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jTabbedPane4)
                .addContainerGap())
        );

        jPanel1.add(Expences, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, -1));

        lbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N
        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1400, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void dc_salaryDetailsMonthFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_salaryDetailsMonthFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_salaryDetailsMonthFocusGained

    private void dc_salaryDetailsMonthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_salaryDetailsMonthFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_salaryDetailsMonthFocusLost

    private void dc_salaryDetailsYearFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_salaryDetailsYearFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_salaryDetailsYearFocusGained

    private void dc_salaryDetailsYearFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_salaryDetailsYearFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_salaryDetailsYearFocusLost

    private void dc_teacherPayYearFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_teacherPayYearFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_teacherPayYearFocusGained

    private void dc_teacherPayYearFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_teacherPayYearFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_teacherPayYearFocusLost

    private void dc_teacherPayFromFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_teacherPayFromFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_teacherPayFromFocusGained

    private void dc_teacherPayFromFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_teacherPayFromFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_teacherPayFromFocusLost

    private void dc_teacherPayToFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_teacherPayToFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_teacherPayToFocusGained

    private void dc_teacherPayToFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_teacherPayToFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_teacherPayToFocusLost

    private void dc_teacherPayMonthFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_teacherPayMonthFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_teacherPayMonthFocusGained

    private void dc_teacherPayMonthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_teacherPayMonthFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_teacherPayMonthFocusLost

    private void dc_otherExpenceToFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_otherExpenceToFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_otherExpenceToFocusGained

    private void dc_otherExpenceToFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_otherExpenceToFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_otherExpenceToFocusLost

    private void dc_otherExpenceYearFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_otherExpenceYearFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_otherExpenceYearFocusGained

    private void dc_otherExpenceYearFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_otherExpenceYearFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_otherExpenceYearFocusLost

    private void dc_otherExpenceFromFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_otherExpenceFromFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_otherExpenceFromFocusGained

    private void dc_otherExpenceFromFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_otherExpenceFromFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_otherExpenceFromFocusLost

    private void dc_otherExpenceMonthFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_otherExpenceMonthFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_otherExpenceMonthFocusGained

    private void dc_otherExpenceMonthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_otherExpenceMonthFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_otherExpenceMonthFocusLost

    private void btn_salaryDetailsChangeSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salaryDetailsChangeSalaryActionPerformed
        int dialog = JOptionPane.showConfirmDialog(rootPane, "Do you want Update Basic Salary", "Please Confirm", JOptionPane.YES_NO_OPTION);
        if (dialog == 0) {
            salaryDetailsUpdateBasicSalary();
        }
        if (dialog == 1) {
            dispose();
        }

        txt_salaryDetailsChangeSalary.setEnabled(false);
    }//GEN-LAST:event_btn_salaryDetailsChangeSalaryActionPerformed

    private void cmb_salaryDetailsMainComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_salaryDetailsMainComboActionPerformed

        salaryDetailesMainCombo();

    }//GEN-LAST:event_cmb_salaryDetailsMainComboActionPerformed

    private void btn_salaryDetailsEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salaryDetailsEditActionPerformed
        salaryDetailsBasicSalaryVisible();
    }//GEN-LAST:event_btn_salaryDetailsEditActionPerformed

    private void cmb_salaryDetailsDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_salaryDetailsDayActionPerformed
        salaryDetailsComboDaySelect();
    }//GEN-LAST:event_cmb_salaryDetailsDayActionPerformed

    private void cmb_teacherPayMainComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_teacherPayMainComboActionPerformed
        teacherPaymentsMainCombo();
    }//GEN-LAST:event_cmb_teacherPayMainComboActionPerformed

    private void cmb_teacherPayDayChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_teacherPayDayChooseActionPerformed
        teacherPayComboDaySelect();
    }//GEN-LAST:event_cmb_teacherPayDayChooseActionPerformed

    private void cmb_teacherPayNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_teacherPayNameActionPerformed

    }//GEN-LAST:event_cmb_teacherPayNameActionPerformed

    private void cmb_otherExpenceComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_otherExpenceComboActionPerformed
        otherExpencesComboDaySelect();
    }//GEN-LAST:event_cmb_otherExpenceComboActionPerformed

    private void cmb_salaryDetailsJobTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_salaryDetailsJobTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_salaryDetailsJobTitleActionPerformed

    private void btn_salaryDetailsSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salaryDetailsSearchActionPerformed
        salaryDetailsSeachToTable();
    }//GEN-LAST:event_btn_salaryDetailsSearchActionPerformed

    private void btn_teacherPaySearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_teacherPaySearchActionPerformed
        teacherPaymentSearch();
    }//GEN-LAST:event_btn_teacherPaySearchActionPerformed

    private void btn_otherExpenceSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_otherExpenceSearchActionPerformed
        OtherExpensesSearchDetails();
    }//GEN-LAST:event_btn_otherExpenceSearchActionPerformed

    private void btn_salaryGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salaryGenerateReportActionPerformed
        String company_email = null, company_number = null, details_by;
        try {
            ResultSet rs = DB.getData("SELECT company_email,company_mobile,company_office FROM admin_panel");
            while (rs.next()) {
                company_email = rs.getString("company_email");
                company_number = rs.getString("company_mobile") + " " + rs.getString("company_office");

            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - AdEX 0011-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
        details_by = cmb_salaryDetailsDay.getSelectedItem().toString().trim();
        int i = tbl_salary.getRowCount();
        if (i > 0) {
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("\\Reports\\salary Details.jrxml");
                JasperDesign jd = JRXmlLoader.load(is);
                JasperReport jr = JasperCompileManager.compileReport(jd);

                JRTableModelDataSource tm = new JRTableModelDataSource(tbl_salary.getModel());

                Map<String, Object> p = new HashMap<String, Object>();
                p.put("full_amount", lbl_salaryDetailsTotal.getText());
                p.put("email", company_email);
                p.put("numbers", company_number);
                p.put("details by", details_by);

                JasperPrint jp = JasperFillManager.fillReport(jr, p, tm);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AdEX 0011-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_btn_salaryGenerateReportActionPerformed

    private void btn_teacherPaymentGenerateReport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_teacherPaymentGenerateReport1ActionPerformed
        String company_email = null, company_number = null, details_by;
        try {
            ResultSet rs = DB.getData("SELECT company_email,company_mobile,company_office FROM admin_panel");
            while (rs.next()) {
                company_email = rs.getString("company_email");
                company_number = rs.getString("company_mobile") + " " + rs.getString("company_office");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - AdEX 0012-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
        details_by = cmb_teacherPayDayChoose.getSelectedItem().toString().trim();
        int i = tbl_teacherPay.getRowCount();
        if (i > 0) {
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("\\Reports\\Teacher payments.jrxml");
                JasperDesign jd = JRXmlLoader.load(is);
                JasperReport jr = JasperCompileManager.compileReport(jd);

                JRTableModelDataSource tm = new JRTableModelDataSource(tbl_teacherPay.getModel());

                Map<String, Object> p = new HashMap<String, Object>();
                p.put("full_amount", lbl_teacherPaymentTotal.getText());
                p.put("email", company_email);
                p.put("numbers", company_number);
                p.put("details by", details_by);

                JasperPrint jp = JasperFillManager.fillReport(jr, p, tm);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AdEX 0012-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

        }
    }//GEN-LAST:event_btn_teacherPaymentGenerateReport1ActionPerformed

    private void btn_OtherpaymentGenerateReport2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_OtherpaymentGenerateReport2ActionPerformed
        String company_email = null, company_number = null, details_by;
        try {
            ResultSet rs = DB.getData("SELECT company_email,company_mobile,company_office FROM admin_panel");
            while (rs.next()) {
                company_email = rs.getString("company_email");
                company_number = rs.getString("company_mobile") + " " + rs.getString("company_office");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - AdEX 0013-01 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
        details_by = cmb_otherExpenceCombo.getSelectedItem().toString().trim();
        int i = tbl_otherExpences.getRowCount();
        if (i > 0) {
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("\\Reports\\Other Expencers.jrxml");
                JasperDesign jd = JRXmlLoader.load(is);
                JasperReport jr = JasperCompileManager.compileReport(jd);

                JRTableModelDataSource tm = new JRTableModelDataSource(tbl_otherExpences.getModel());

                Map<String, Object> p = new HashMap<String, Object>();
                p.put("full_amount", lbl_otherExpencesTotal.getText());
                p.put("email", company_email);
                p.put("numbers", company_number);
                p.put("details by", details_by);

                JasperPrint jp = JasperFillManager.fillReport(jr, p, tm);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - AdEX 0013-02 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

        }
    }//GEN-LAST:event_btn_OtherpaymentGenerateReport2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new Menu().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_salaryDetailsEmployeeRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_salaryDetailsEmployeeRateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_salaryDetailsEmployeeRateActionPerformed

    private void btn_salaryDetailsEtfEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salaryDetailsEtfEditActionPerformed
        salaryDetailsEtfTextEditableTrue();
    }//GEN-LAST:event_btn_salaryDetailsEtfEditActionPerformed

    private void btn_salaryDetailsEtfAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salaryDetailsEtfAddActionPerformed
        int dialog = JOptionPane.showConfirmDialog(rootPane, "Do you want Update ETF/EPF rates", "Please Confirm", JOptionPane.YES_NO_OPTION);
        if (dialog == 0) {
            salaryDetailsUpdateEtfRates();
        }
        if (dialog == 1) {
            dispose();
        }
        salaryDetailsEtfTextEditableFalse();
    }//GEN-LAST:event_btn_salaryDetailsEtfAddActionPerformed

    private void txt_salaryDetailsSalaryPerDayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_salaryDetailsSalaryPerDayMouseClicked
        txt_salaryDetailsSalaryPerDay.setEnabled(true);
    }//GEN-LAST:event_txt_salaryDetailsSalaryPerDayMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int dialog = JOptionPane.showConfirmDialog(rootPane, "Do you want Update Salary Per Day", "Please Confirm", JOptionPane.YES_NO_OPTION);
        if (dialog == 0) {
            salaryDetailsUpdateSalaryPerDay();
        }
        if (dialog == 1) {
            dispose();
        }

        txt_salaryDetailsSalaryPerDay.setEnabled(false);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_OtherpaymentGenerateReport3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_OtherpaymentGenerateReport3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_OtherpaymentGenerateReport3ActionPerformed

    private void cmb_epfDateOrIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_epfDateOrIDActionPerformed
        if (cmb_epfDateOrID.getSelectedItem().toString().equals("Employee ID")) {
            epfSetaAllVisible();
            cmb_epfDateChooser.setEnabled(true);
            txt_epfEmployeeID.setEnabled(true);
            dc_epfMonth.setEnabled(true);
            dc_epfYear.setEnabled(true);

            dc_epfMonth.setVisible(false);
            dc_epfYear.setVisible(false);
            lbl_epfFrom.setVisible(false);
            lbl_epfTo.setVisible(false);
            dc_epfTo.setVisible(false);
            dc_EPFFROM.setVisible(false);

        }
        if (cmb_epfDateOrID.getSelectedItem().toString().equals("Date")) {
            epfSetaAllVisible();
            cmb_epfDateChooser.setEnabled(true);
            txt_epfEmployeeID.setEnabled(false);
            dc_epfMonth.setEnabled(true);
            dc_epfYear.setEnabled(true);

            dc_epfMonth.setVisible(false);
            dc_epfYear.setVisible(false);
            lbl_epfFrom.setVisible(false);
            lbl_epfTo.setVisible(false);
            dc_epfTo.setVisible(false);
            dc_EPFFROM.setVisible(false);
        }

    }//GEN-LAST:event_cmb_epfDateOrIDActionPerformed

    private void txt_epfEmployeeIDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_epfEmployeeIDFocusGained
        txt_epfEmployeeID.setText("EMP");
    }//GEN-LAST:event_txt_epfEmployeeIDFocusGained

    private void cmb_epfDateChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_epfDateChooserActionPerformed
        if (cmb_epfDateChooser.getSelectedItem().toString().equals("Custome")) {
            lbl_epfFrom.setVisible(true);
            lbl_epfTo.setVisible(true);
            dc_epfTo.setVisible(true);
            dc_EPFFROM.setVisible(true);

            dc_epfMonth.setVisible(false);
            dc_epfYear.setVisible(false);
        }
        if (cmb_epfDateChooser.getSelectedItem().toString().equals("Monthly")) {
            dc_epfMonth.setVisible(true);
            dc_epfYear.setVisible(true);

            lbl_epfFrom.setVisible(false);
            lbl_epfTo.setVisible(false);
            dc_epfTo.setVisible(false);
            dc_EPFFROM.setVisible(false);
        }
        if (cmb_epfDateChooser.getSelectedItem().toString().equals("Annually")) {

            dc_epfYear.setVisible(true);
            dc_epfMonth.setVisible(false);
            lbl_epfFrom.setVisible(false);
            lbl_epfTo.setVisible(false);
            dc_epfTo.setVisible(false);
            dc_EPFFROM.setVisible(false);
        }

    }//GEN-LAST:event_cmb_epfDateChooserActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        epfSearchDateToTable();
    }//GEN-LAST:event_jButton4ActionPerformed
    int i1 = 0;
    private void txt_salaryDetailsEmployeeRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_salaryDetailsEmployeeRateKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {

            if (txt_salaryDetailsEmployeeRate.getText().contains(".")) {
                i1--;
            } else {
                i1 = 0;
            }
            System.out.println("im back space, i1 is :" + i1);
        }
    }//GEN-LAST:event_txt_salaryDetailsEmployeeRateKeyPressed

    private void txt_salaryDetailsEmployeeRateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_salaryDetailsEmployeeRateKeyTyped
        if (Character.isDigit(evt.getKeyChar())) {
            if (!txt_salaryDetailsEmployeeRate.getText().equals("")) {
                if (txt_salaryDetailsEmployeeRate.getText().contains(".")) {
                    if (i1 >= 2) {
                        evt.consume();
                    } else {
                        i1++;
                        System.out.println("im in other");
                    }

                    System.out.println("this is i1: " + i1);
                } else {
                    int l = txt_salaryDetailsEmployeeRate.getText().length();
                    if (l >= 2) {
                        evt.consume();
                    } else {
                        i1 = 0;
                    }
                }
            }

        } else if (evt.getKeyChar() == '.') {
            if (txt_salaryDetailsEmployeeRate.getText().contains(".")) {
                evt.consume();
            }
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txt_salaryDetailsEmployeeRateKeyTyped

    private void popup_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_menuActionPerformed
         this.dispose();
      new Menu().setVisible(true);
    }//GEN-LAST:event_popup_menuActionPerformed

    private void popup_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_helpActionPerformed
        try {
            Desktop.getDesktop().open(new File("E:\\Syndicate\\Documentation\\Admin – Expenses.pdf"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_popup_helpActionPerformed

    private void popup_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_exitActionPerformed
       this.dispose();
    }//GEN-LAST:event_popup_exitActionPerformed

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

    private void ExpencesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExpencesMouseClicked
        if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_ExpencesMouseClicked

    private void jTabbedPane4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane4MouseClicked
        if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jTabbedPane4MouseClicked

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel12MouseClicked

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
       if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel13MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
      if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseClicked
       if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel17MouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
       if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel14MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jLabel52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel52MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
       if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
      if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
       if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseClicked
      if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel20MouseClicked

    private void jPanel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseClicked
        if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel18MouseClicked

    private void jPanel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel19MouseClicked
       if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel19MouseClicked

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
            java.util.logging.Logger.getLogger(Expences.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Expences.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Expences.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Expences.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Expences().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Expences;
    private javax.swing.JButton btn_OtherpaymentGenerateReport2;
    private javax.swing.JButton btn_OtherpaymentGenerateReport3;
    private javax.swing.JButton btn_otherExpenceSearch;
    private javax.swing.JButton btn_salaryDetailsChangeSalary;
    private javax.swing.JButton btn_salaryDetailsEdit;
    private javax.swing.JButton btn_salaryDetailsEtfAdd;
    private javax.swing.JButton btn_salaryDetailsEtfEdit;
    private javax.swing.JButton btn_salaryDetailsSearch;
    private javax.swing.JButton btn_salaryGenerateReport;
    private javax.swing.JButton btn_teacherPaySearch;
    private javax.swing.JButton btn_teacherPaymentGenerateReport1;
    private javax.swing.JComboBox cmb_epfDateChooser;
    private javax.swing.JComboBox cmb_epfDateOrID;
    private javax.swing.JComboBox cmb_otherExpenceCombo;
    private javax.swing.JComboBox cmb_salaryDetailsDay;
    private javax.swing.JComboBox cmb_salaryDetailsJob;
    private javax.swing.JComboBox cmb_salaryDetailsJobTitle;
    private javax.swing.JComboBox cmb_salaryDetailsMainCombo;
    private javax.swing.JComboBox cmb_teacherPayDayChoose;
    private javax.swing.JComboBox cmb_teacherPayMainCombo;
    private javax.swing.JComboBox cmb_teacherPayName;
    private com.toedter.calendar.JDateChooser dc_EPFFROM;
    private com.toedter.calendar.JMonthChooser dc_epfMonth;
    private com.toedter.calendar.JDateChooser dc_epfTo;
    private com.toedter.calendar.JYearChooser dc_epfYear;
    private com.toedter.calendar.JDayChooser dc_otherExpenceDate;
    private com.toedter.calendar.JDateChooser dc_otherExpenceFrom;
    private com.toedter.calendar.JMonthChooser dc_otherExpenceMonth;
    private com.toedter.calendar.JDateChooser dc_otherExpenceTo;
    private com.toedter.calendar.JYearChooser dc_otherExpenceYear;
    private com.toedter.calendar.JMonthChooser dc_salaryDetailsMonth;
    private com.toedter.calendar.JYearChooser dc_salaryDetailsYear;
    private com.toedter.calendar.JDayChooser dc_teacherPayDate;
    private com.toedter.calendar.JDateChooser dc_teacherPayFrom;
    private com.toedter.calendar.JMonthChooser dc_teacherPayMonth;
    private com.toedter.calendar.JDateChooser dc_teacherPayTo;
    private com.toedter.calendar.JYearChooser dc_teacherPayYear;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_epfCompanyTotal;
    private javax.swing.JLabel lbl_epfEmployeeTotal;
    private javax.swing.JLabel lbl_epfFrom;
    private javax.swing.JLabel lbl_epfTo;
    private javax.swing.JLabel lbl_epfTotal;
    private javax.swing.JLabel lbl_otherExpenceDay;
    private javax.swing.JLabel lbl_otherExpenceFrom;
    private javax.swing.JLabel lbl_otherExpenceMonth;
    private javax.swing.JLabel lbl_otherExpenceTo;
    private javax.swing.JLabel lbl_otherExpenceYear;
    private javax.swing.JLabel lbl_otherExpencesTotal;
    private javax.swing.JLabel lbl_salaryDetailsChangeSalary;
    private javax.swing.JLabel lbl_salaryDetailsCompanyRate;
    private javax.swing.JLabel lbl_salaryDetailsEmployeeRate;
    private javax.swing.JLabel lbl_salaryDetailsMonth;
    private javax.swing.JLabel lbl_salaryDetailsTotal;
    private javax.swing.JLabel lbl_salaryDetailsYear;
    private javax.swing.JLabel lbl_teacherPayDay;
    private javax.swing.JLabel lbl_teacherPayFrom;
    private javax.swing.JLabel lbl_teacherPayMonth;
    private javax.swing.JLabel lbl_teacherPayTo;
    private javax.swing.JLabel lbl_teacherPayYear;
    private javax.swing.JLabel lbl_teacherPaymentTotal;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_userType;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem popup_exit;
    private javax.swing.JMenuItem popup_help;
    private javax.swing.JMenuItem popup_menu;
    private javax.swing.JTable tbl_epfTable;
    private javax.swing.JTable tbl_otherExpences;
    private javax.swing.JTable tbl_salary;
    private javax.swing.JTable tbl_teacherPay;
    private javax.swing.JTextField txt_epfEmployeeID;
    private javax.swing.JTextField txt_salaryDetailsChangeSalary;
    private javax.swing.JTextField txt_salaryDetailsCompanyRate;
    private javax.swing.JTextField txt_salaryDetailsEmployeeRate;
    private javax.swing.JTextField txt_salaryDetailsSalaryPerDay;
    // End of variables declaration//GEN-END:variables
}
