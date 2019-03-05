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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Vidyani
 */
public class Attendance extends javax.swing.JFrame {

    /**
     * Creates new form Attendance
     */
    org.apache.log4j.Logger logError = org.apache.log4j.Logger.getLogger("ERROR");

    public Attendance() {
        initComponents();
        Menu();
        leavesTextFieldsNonEditable();
        attendanceComboAllInvisible();
        getLeavesDetailsFromAdminPanel();
        lbl_username.setText(Home.un);
        lbl_userType.setText(Home.ut);
        lbl_date.setText(new Classes.Commons().Date());
        new Classes.Commons().time(lbl_time);
    }

    JDBC DB = new Classes.JDBC();
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

    ////////----------Default Leave Details-------////////////
    public void leavesTextFieldsNonEditable() {

        txt_noOfLeaves.setEnabled(false);

    }

    public void textFieldEditable() {

        txt_noOfLeaves.setEnabled(true);

    }

    /////// Combo All Invisible //////////
    void attendanceComboAllInvisible() {

        cmb_attendanceComboDay.setVisible(false);
        cmb_attendanceComboNameID.setVisible(false);
        cmb_attendanceComboDayForNameID.setVisible(false);
        txt_name.setVisible(false);
        lbl_Day.setVisible(false);
        lbl_Month.setVisible(false);
        lbl_year.setVisible(false);
        lbl_from.setVisible(false);
        lbl_to.setVisible(false);
        dc_date.setVisible(false);
        dc_month.setVisible(false);
        dc_Year.setVisible(false);
        dc_from.setVisible(false);
        dc_To.setVisible(false);
    }

    //////////-----ComboBox Daily-----///////////
    void attendanceComboDaily() {
        ////////visible//////////
        lbl_Day.setVisible(true);
        lbl_Month.setVisible(true);
        lbl_year.setVisible(true);
        dc_date.setVisible(true);
        dc_month.setVisible(true);
        dc_Year.setVisible(true);

        ////////Not Visible//////
        lbl_from.setVisible(false);
        lbl_to.setVisible(false);
        dc_from.setVisible(false);
        dc_To.setVisible(false);

    }

    /////--------ComboBox Monthly----/////////////////
    void attendanceComboMonthly() {
        /////////////Visible///////////////////////
        lbl_Month.setVisible(true);
        lbl_year.setVisible(true);
        dc_month.setVisible(true);
        dc_Year.setVisible(true);
        /////////Not Visible//////////
        lbl_Day.setVisible(false);
        lbl_from.setVisible(false);
        lbl_to.setVisible(false);
        dc_date.setVisible(false);
        dc_from.setVisible(false);
        dc_To.setVisible(false);
    }

    /////-------attendance ComboBox Annualy------//////
    void attendanceComboAnnualy() {
        /////////Visible//////////////
        lbl_year.setVisible(true);
        dc_Year.setVisible(true);
        ////////////Not Visible//////
        lbl_Month.setVisible(false);
        lbl_Day.setVisible(false);
        lbl_from.setVisible(false);
        lbl_to.setVisible(false);
        dc_date.setVisible(false);
        dc_from.setVisible(false);
        dc_To.setVisible(false);
        dc_month.setVisible(false);
    }

    /////////------comboBox custome------/////////////
    void attendanceComboCustome() {
        /////////visible///////////
        lbl_from.setVisible(true);
        lbl_to.setVisible(true);
        dc_from.setVisible(true);
        dc_To.setVisible(true);

        dc_from.setMaxSelectableDate(new Date());
        dc_To.setMaxSelectableDate(new Date());
        ////////not visible/////
        lbl_year.setVisible(false);
        lbl_Month.setVisible(false);
        lbl_Day.setVisible(false);
        dc_Year.setVisible(false);
        dc_date.setVisible(false);
        dc_month.setVisible(false);
    }

    /////////--------daily monthly annualy custome select-----//////
    void attendanceComboDaySelect() {
        if (cmb_attendanceComboDay.getSelectedIndex() == 0) {
            attendanceComboDaily();
        }
        if (cmb_attendanceComboDay.getSelectedIndex() == 1) {
            attendanceComboMonthly();
        }
        if (cmb_attendanceComboDay.getSelectedIndex() == 2) {
            attendanceComboAnnualy();
        }
        if (cmb_attendanceComboDay.getSelectedIndex() == 3) {
            attendanceComboCustome();
        }
    }

    /////---------Main combo box select-------////////////
    void attendanceMainComboSelect() {
        attendanceComboAllInvisible();
        if (cmb_attendanceMainCombo.getSelectedIndex() == 0) {
            cmb_attendanceComboDay.setVisible(true);
            cmb_attendanceComboNameID.setVisible(false);
            txt_name.setVisible(false);
        }
        if (cmb_attendanceMainCombo.getSelectedIndex() == 1) {
            cmb_attendanceComboNameID.setVisible(true);
            cmb_attendanceComboDay.setVisible(false);
        }
    }

    ////// ---refresh the day choosers----------/////////////
    void attendanceDayChoosers() {
        lbl_Day.setVisible(false);
        lbl_Month.setVisible(false);
        lbl_from.setVisible(false);
        lbl_to.setVisible(false);
        lbl_year.setVisible(false);
        dc_To.setVisible(false);
        dc_Year.setVisible(false);
        dc_date.setVisible(false);
        dc_from.setVisible(false);
        dc_month.setVisible(false);
    }

    ///////--------ComboBox Name or ID select-----////////////
    void attendanceNameIdSelect() {
        if (cmb_attendanceComboNameID.getSelectedIndex() == 0) {
            attendanceDayChoosers();
            txt_name.setVisible(true);
            cmb_attendanceComboDayForNameID.setVisible(true);
        }
        if (cmb_attendanceComboNameID.getSelectedIndex() == 1) {
            attendanceDayChoosers();
            txt_name.setVisible(true);
            cmb_attendanceComboDayForNameID.setVisible(true);
        }
    }

    ///////------------get Leaves Details form admin Panel--------//////////
    void getLeavesDetailsFromAdminPanel() {
        try {
            ResultSet rs = DB.getData("SELECT default_leaves FROM admin_panel");

            while (rs.next()) {
                txt_noOfLeaves.setText(rs.getString("default_leaves"));

            }
            rs.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR - ATT 0001 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }

    }

    //////--------update leaves Datails to admin panel------//////////////
    void updateLaevesDetailsToAdminPanel() {
        try {
            DB.putData("UPDATE admin_panel SET dafault_leaves='" + txt_noOfLeaves + "'");

            JOptionPane.showConfirmDialog(rootPane, "Successfully Updated");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR - ATT 0002 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    //////-------------- Search Button----------------------------------///////
    void searchDetailsFromDB() {
        System.out.println("search data");
        //// -----Employee Attendance Daily-----
        String comboDate = cmb_attendanceComboDay.getSelectedItem().toString();
        String comboName = cmb_attendanceComboNameID.getSelectedItem().toString();
        String comboMain = cmb_attendanceMainCombo.getSelectedItem().toString();
        String comboDateForName = cmb_attendanceComboDayForNameID.getSelectedItem().toString();

        if (comboMain.equals("Date")) {
            System.out.println("date");
            if (comboDate.equals("Daily")) {
                tool.clearTable(tbl_attendance);
                try {
                    int year = dc_Year.getYear();
                    int month = dc_month.getMonth() + 1;
                    int day = dc_date.getDay();

                    String fullDay;
                    if (month > 9) {
                        fullDay = year + "-" + month + "-" + day;
                    } else {
                        fullDay = year + "-0" + month + "-" + day;
                    }

                    //// data from attendance table----
                    ResultSet rs = DB.getData("SELECT * FROM attendance WHERE date='" + fullDay + "'");

                    while (rs.next()) {
                        System.out.println("full date-daily");
                        ////// data from employee registration table-------
                        ResultSet rs1 = DB.getData("SELECT * FROM employee_reg WHERE emp_id = '" + rs.getString("emp_id") + "'");

                        while (rs1.next()) {
                            System.out.println("employee-daily");
                            ///// data from leaves table------

                            System.out.println("leaves-daily");

                            String empId = rs.getString("emp_id");
                            String empName = rs1.getString("f_name") + " " + rs1.getString("l_name");
                            String date = rs.getString("date");
                            String intime = rs.getString("in_time");
                            String outTime = rs.getString("out_time");

                            //// leave type set--------/////
                            //// counting hours--------//////////
                            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                            Date timeIn = df.parse(intime);
                            Date timeOut = df.parse(outTime);

                            long time = timeOut.getTime() - timeIn.getTime();

                            String hours = Long.toString(time / (60 * 60 * 1000) % 24);
                            String mints = Long.toString(time / (60 * 1000) % 60);
                            String seconds = Long.toString(time / 1000 % 60);

                            String totHours = hours + ":" + mints + ":" + seconds;
                            //////-----adding data to table--------
                            tool.addToTable(tbl_attendance, empId, empName, date, intime, outTime, totHours);
                            JOptionPane.showConfirmDialog(rootPane, "complete");

                        }
                        rs1.close();
                    }
                    rs.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - ATT 0003-1-1 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }
            } /////-------Employee Attendance Monthly---------///////
            else if (comboDate.equals("Monthly")) {
                tool.clearTable(tbl_attendance);
                System.out.println("monthly");
                int year = dc_Year.getYear();
                int month = dc_month.getMonth() + 1;

                String fullDate;
                if (month > 9) {
                    fullDate = year + "-" + month;
                } else {
                    fullDate = year + "-0" + month;
                }
                String empId = "";
                try {
                    //// data from attendance table---

                    ResultSet rs = DB.getData("SELECT * FROM attendance WHERE date LIKE '" + fullDate + "%'");
                    while (rs.next()) {
                        System.out.println("get date");

                        //// data from employee reg table--------
                        ResultSet rs1 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                        while (rs1.next()) {
                            empId = rs.getString("emp_id");
                            System.out.println("get employee");

                            String empName = rs1.getString("f_name") + " " + rs1.getString("l_name");
                            String date = rs.getString("date");
                            String intime = rs.getString("in_time");
                            String outTime = rs.getString("out_time");

                            //// counting hours--------//////////
                            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                            Date timeIn = df.parse(intime);
                            Date timeOut = df.parse(outTime);

                            long time = timeOut.getTime() - timeIn.getTime();

                            String hours = Long.toString(time / (60 * 60 * 1000) % 24);
                            String mints = Long.toString(time / (60 * 1000) % 60);
                            String seconds = Long.toString(time / 1000 % 60);

                            String totHours = hours + ":" + mints + ":" + seconds;
                            //////-----adding data to table--------
                            tool.addToTable(tbl_attendance, empId, empName, date, intime, outTime, totHours);
                        }
                        rs1.close();

                    }
                    rs.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - ATT 0003-1-2 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }

            } ////// ---------employee attendance Monthly----------------
            else if (comboDate.equals("Annually")) {
                tool.clearTable(tbl_attendance);
                int year = dc_Year.getYear();
                String fullDate = Integer.toString(year);

                try {
                    //// data from attendance table---
                    ResultSet rs = DB.getData("SELECT * FROM attendance WHERE date LIKE'" + fullDate + "%'");
                    while (rs.next()) {
                        //// data from employee reg table--------
                        ResultSet rs1 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                        while (rs1.next()) {
                            //// data from leaves table------

                            String empId = rs.getString("emp_id");
                            String empName = rs1.getString("f_name") + " " + rs1.getString("l_name");
                            String date = rs.getString("date");
                            String intime = rs.getString("in_time");
                            String outTime = rs.getString("out_time");

                            //// leave type set--------/////
                            //// counting hours--------//////////
                            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                            Date timeIn = df.parse(intime);
                            Date timeOut = df.parse(outTime);

                            long time = timeOut.getTime() - timeIn.getTime();

                            String hours = Long.toString(time / (60 * 60 * 1000) % 24);
                            String mints = Long.toString(time / (60 * 1000) % 60);
                            String seconds = Long.toString(time / 1000 % 60);

                            String totHours = hours + ":" + mints + ":" + seconds;
                            //////-----adding data to table--------
                            tool.addToTable(tbl_attendance, empId, empName, date, intime, outTime, totHours);
                        }
                        rs1.close();

                    }
                    rs.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - ATT 0003-1-3 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }

            } /////---------------Employee Attendance Custome-----------------
            else if (comboDate.equals("Custome")) {
                tool.clearTable(tbl_attendance);
                Date d = new Date();
                String from = new SimpleDateFormat("yyyy-MM-dd").format(dc_from.getDate());
                String to = new SimpleDateFormat("yyyy-MM-dd").format(dc_To.getDate());
                try {
                    ///// get data from attendance---------
                    ResultSet rs = DB.getData("SELECT * FROM attendance WHERE date BETWEEN '" + from + "' AND '" + to + "'");
                    while (rs.next()) {
                        ///// get data from employee reg-----------
                        ResultSet rs1 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                        while (rs1.next()) {
                            //// data from leaves table------

                            String empId = rs.getString("emp_id");
                            String empName = rs1.getString("f_name") + " " + rs1.getString("l_name");
                            String date = rs.getString("date");
                            String intime = rs.getString("in_time");
                            String outTime = rs.getString("out_time");

                            //// counting hours--------//////////
                            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                            Date timeIn = df.parse(intime);
                            Date timeOut = df.parse(outTime);

                            long time = timeOut.getTime() - timeIn.getTime();

                            String hours = Long.toString(time / (60 * 60 * 1000) % 24);
                            String mints = Long.toString(time / (60 * 1000) % 60);
                            String seconds = Long.toString(time / 1000 % 60);

                            String totHours = hours + ":" + mints + ":" + seconds;

                            //////-----adding data to table--------
                            tool.addToTable(tbl_attendance, empId, empName, date, intime, outTime, totHours);
                        }
                        rs1.close();

                    }
                    rs.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR - ATT 0003-1-4 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                    logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                }

            }
        } ////-------- Search According to employee details----------------//////
        else if (comboMain.equals("Employee Details")) {
            ///--- search according to name---------------
            if (comboName.equals("Name")) {
                String name = txt_name.getText();
                ///--- search details according to daily-----------
                if (comboDateForName.equals("Daily")) {
                    tool.clearTable(tbl_attendance);
                    try {
                        ////-----get data fromemployee_reg------------
//                        ResultSet rs = DB.getData("SELECT * FROM employee_reg WHERE f_name LIKE '" + name + "%' OR l_name LIKE'" + name + "%'");
                        ResultSet rs = DB.getData("SELECT * FROM employee_reg WHERE "
                                + (name.contains(" ")
                                ? ("f_name LIKE '" + name.split(" ")[0] + "%' AND l_name LIKE'" + name.split(" ")[1] + "%'")
                                : ("f_name LIKE '" + name + "%' OR l_name LIKE'" + name + "%'")));
                        while (rs.next()) {
                            int year = dc_Year.getYear();
                            int month = dc_month.getMonth() + 1;
                            int day = dc_date.getDay();
                            String fullDate;
                            if (month > 9) {
                                fullDate = year + "-" + month + "-" + day;
                            } else {
                                fullDate = year + "-0" + month + "-" + day;
                            }

                            /// get details from attendance------------
                            ResultSet rs1 = DB.getData("SELECT * FROM attendance WHERE emp_id='" + rs.getString("emp_id") + "' AND date='" + fullDate + "'");

                            while (rs1.next()) {

                                String empId = rs.getString("emp_id");
                                String empName = rs.getString("f_name") + " " + rs.getString("l_name");
                                String date = rs1.getString("date");
                                String intime = rs1.getString("in_time");
                                String outTime = rs1.getString("out_time");

                                //// counting hours--------//////////
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                Date timeIn = df.parse(intime);
                                Date timeOut = df.parse(outTime);

                                long time = timeOut.getTime() - timeIn.getTime();

                                String hours = Long.toString(time / (60 * 60 * 1000) % 24);
                                String mints = Long.toString(time / (60 * 1000) % 60);
                                String seconds = Long.toString(time / 1000 % 60);

                                String totHours = hours + ":" + mints + ":" + seconds;

                                //////-----adding data to table--------
                                tool.addToTable(tbl_attendance, empId, empName, date, intime, outTime, totHours);
                            }
                            rs1.close();

                        }
                        rs.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "ERROR - ATT 0003-2-1-1 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                        logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    }
                } //// -----seach according to Monthly----------------------
                else if (comboDateForName.equals("Monthly")) {
                    tool.clearTable(tbl_attendance);

                    try {
                        ////-----get data fromemployee_reg------------
                        ResultSet rs = DB.getData("SELECT * FROM employee_reg WHERE "
                                + (name.contains(" ")
                                ? ("f_name LIKE '" + name.split(" ")[0] + "%' AND l_name LIKE'" + name.split(" ")[1] + "%'")
                                : ("f_name LIKE '" + name + "%' OR l_name LIKE'" + name + "%'")));
                        while (rs.next()) {
                            int year = dc_Year.getYear();
                            int month = dc_month.getMonth() + 1;

                            String fullDate;
                            if (month > 9) {
                                fullDate = year + "-" + month;
                            } else {
                                fullDate = year + "-0" + month;
                            }

                            /// get details from attendance------------
                            ResultSet rs1 = DB.getData("SELECT * FROM attendance WHERE emp_id='" + rs.getString("emp_id") + "' AND date LIKE'" + fullDate + "%'");

                            while (rs1.next()) {

                                String empId = rs.getString("emp_id");
                                String empName = rs.getString("f_name") + " " + rs.getString("l_name");
                                String date = rs1.getString("date");
                                String intime = rs1.getString("in_time");
                                String outTime = rs1.getString("out_time");

                                //// counting hours--------//////////
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                Date timeIn = df.parse(intime);
                                Date timeOut = df.parse(outTime);

                                long time = timeOut.getTime() - timeIn.getTime();

                                String hours = Long.toString(time / (60 * 60 * 1000) % 24);
                                String mints = Long.toString(time / (60 * 1000) % 60);
                                String seconds = Long.toString(time / 1000 % 60);

                                String totHours = hours + ":" + mints + ":" + seconds;

                                //////-----adding data to table--------
                                tool.addToTable(tbl_attendance, empId, empName, date, intime, outTime, totHours);
                            }
                            rs1.close();

                        }
                        rs.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "ERROR - ATT 0003-2-1-2 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                        logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    }
                } /////------ Search according to Annually-----------------/////
                else if (comboDateForName.equals("Annually")) {
                    tool.clearTable(tbl_attendance);

                    try {
                        ////-----get data fromemployee_reg------------
                        ResultSet rs = DB.getData("SELECT * FROM employee_reg WHERE "
                                + (name.contains(" ")
                                ? ("f_name LIKE '" + name.split(" ")[0] + "%' AND l_name LIKE'" + name.split(" ")[1] + "%'")
                                : ("f_name LIKE '" + name + "%' OR l_name LIKE'" + name + "%'")));
                        while (rs.next()) {
                            int year = dc_Year.getYear();

                            String fullDate = Integer.toString(year);

                            /// get details from attendance------------
                            ResultSet rs1 = DB.getData("SELECT * FROM attendance WHERE emp_id='" + rs.getString("emp_id") + "' AND date LIKE'" + fullDate + "%'");

                            while (rs1.next()) {

                                String empId = rs.getString("emp_id");
                                String empName = rs.getString("f_name") + " " + rs.getString("l_name");
                                String date = rs1.getString("date");
                                String intime = rs1.getString("in_time");
                                String outTime = rs1.getString("out_time");

                                //// counting hours--------//////////
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                Date timeIn = df.parse(intime);
                                Date timeOut = df.parse(outTime);

                                long time = timeOut.getTime() - timeIn.getTime();

                                String hours = Long.toString(time / (60 * 60 * 1000) % 24);
                                String mints = Long.toString(time / (60 * 1000) % 60);
                                String seconds = Long.toString(time / 1000 % 60);

                                String totHours = hours + ":" + mints + ":" + seconds;

                                //////-----adding data to table--------
                                tool.addToTable(tbl_attendance, empId, empName, date, intime, outTime, totHours);
                            }
                            rs1.close();

                        }
                        rs.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "ERROR - ATT 0003-2-1-3 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                        logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    }
                } /////---- Seach etails According to custome date-------------///
                else if (comboDateForName.equals("Custome")) {
                    tool.clearTable(tbl_attendance);

                    try {
                        ////-----get data fromemployee_reg------------
                        ResultSet rs = DB.getData("SELECT * FROM employee_reg WHERE "
                                + (name.contains(" ")
                                ? ("f_name LIKE '" + name.split(" ")[0] + "%' AND l_name LIKE'" + name.split(" ")[1] + "%'")
                                : ("f_name LIKE '" + name + "%' OR l_name LIKE'" + name + "%'")));
                        while (rs.next()) {

                            /// get details from attendance------------
                            ResultSet rs1 = DB.getData("SELECT * FROM attendance WHERE emp_id='" + rs.getString("emp_id") + "'");

                            while (rs1.next()) {

                                ///// geta data from attendance matching date------------
                                Date d = new Date();
                                String from = new SimpleDateFormat("yyyy-MM-dd").format(dc_from.getDate());
                                String to = new SimpleDateFormat("yyyy-MM-dd").format(dc_To.getDate());

                                ResultSet rs2 = DB.getData("SELECT * FROM income where date BETWEEN '" + from + "' AND '" + to + "'");
                                while (rs2.next()) {

                                    String empId = rs2.getString("emp_id");
                                    String empName = rs.getString("f_name") + " " + rs.getString("l_name");
                                    String date = rs2.getString("date");
                                    String intime = rs2.getString("in_time");
                                    String outTime = rs2.getString("out_time");

                                    //// counting hours--------//////////
                                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                    Date timeIn = df.parse(intime);
                                    Date timeOut = df.parse(outTime);

                                    long time = timeOut.getTime() - timeIn.getTime();

                                    String hours = Long.toString(time / (60 * 60 * 1000) % 24);
                                    String mints = Long.toString(time / (60 * 1000) % 60);
                                    String seconds = Long.toString(time / 1000 % 60);

                                    String totHours = hours + ":" + mints + ":" + seconds;

                                    //////-----adding data to table--------
                                    tool.addToTable(tbl_attendance, empId, empName, date, intime, outTime, totHours);

                                }
                                rs2.close();
                            }
                            rs1.close();

                        }
                        rs.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "ERROR - ATT 0003-2-1-4 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                        logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    }
                }
            }
            /// ----------search data according to  employee ID-------------------//
            if (comboName.equals("ID")) {
                String id = txt_name.getText();
                ///----------- search by daily--------------
                if (comboDateForName.equals("Daily")) {
                    tool.clearTable(tbl_attendance);
                    int year = dc_Year.getYear();
                    int month = dc_month.getMonth() + 1;
                    int day = dc_date.getDay();
                    //String fullDate = year + "-" + month + "-" + day;
                    String fullDate;
                    if (month > 9) {
                        fullDate = year + "-" + month + "-" + day;
                    } else {
                        fullDate = year + "-0" + month + "-" + day;
                    }
                    try {
                        //// search from attendance-----------------
                        ResultSet rs = DB.getData("SELECT * FROM attendance emp_id='" + id + "' AND date='" + fullDate + "'");
                        while (rs.next()) {
                            ///// seach from employee reg---------------
                            ResultSet rs1 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                            while (rs1.next()) {

                                String empId = rs.getString("emp_id");
                                String empName = rs1.getString("f_name") + " " + rs1.getString("l_name");
                                String date = rs.getString("date");
                                String intime = rs.getString("in_time");
                                String outTime = rs.getString("out_time");

                                //// counting hours--------//////////
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                Date timeIn = df.parse(intime);
                                Date timeOut = df.parse(outTime);

                                long time = timeOut.getTime() - timeIn.getTime();

                                String hours = Long.toString(time / (60 * 60 * 1000) % 24);
                                String mints = Long.toString(time / (60 * 1000) % 60);
                                String seconds = Long.toString(time / 1000 % 60);

                                String totHours = hours + ":" + mints + ":" + seconds;

                                //////-----adding data to table--------
                                tool.addToTable(tbl_attendance, empId, empName, date, intime, outTime, totHours);

                            }
                            rs1.close();
                        }
                        rs.close();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "ERROR - ATT 0003-2-2-1 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                        logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    }
                }
                ////-----search data by Monthly-------------------
                if (comboDateForName.equals("Monthly")) {
                    tool.clearTable(tbl_attendance);
                    int year = dc_Year.getYear();
                    int month = dc_month.getMonth() + 1;

                    //String fullDate = year + "-" + month ;
                    String fullDate;
                    if (month > 9) {
                        fullDate = year + "-" + month;
                    } else {
                        fullDate = year + "-0" + month;
                    }
                    try {
                        //// search from attendance-----------------
                        ResultSet rs = DB.getData("SELECT * FROM attendance WHERE emp_id='" + id + "' AND date LIKE '" + fullDate + "%'");
                        while (rs.next()) {
                            ///// seach from employee reg---------------
                            ResultSet rs1 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                            while (rs1.next()) {

                                String empId = rs.getString("emp_id");
                                String empName = rs1.getString("f_name") + " " + rs1.getString("l_name");
                                String date = rs.getString("date");
                                String intime = rs.getString("in_time");
                                String outTime = rs.getString("out_time");

                                //// counting hours--------//////////
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                Date timeIn = df.parse(intime);
                                Date timeOut = df.parse(outTime);

                                long time = timeOut.getTime() - timeIn.getTime();

                                String hours = Long.toString(time / (60 * 60 * 1000) % 24);
                                String mints = Long.toString(time / (60 * 1000) % 60);
                                String seconds = Long.toString(time / 1000 % 60);

                                String totHours = hours + ":" + mints + ":" + seconds;

                                //////-----adding data to table--------
                                tool.addToTable(tbl_attendance, empId, empName, date, intime, outTime, totHours);

                            }
                            rs1.close();
                        }
                        rs.close();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "ERROR - ATT 0003-2-2-2 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                        logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    }
                }
                /////------- search by Annually-----------------------------------
                if (comboDateForName.equals("Annually")) {
                    tool.clearTable(tbl_attendance);
                    int year = dc_Year.getYear();

                    String fullDate = Integer.toString(year);
                    try {
                        //// search from attendance-----------------
                        ResultSet rs = DB.getData("SELECT * FROM attendance WHERE emp_id='" + id + "' AND date LIKE '" + fullDate + "%'");
                        while (rs.next()) {
                            ///// seach from employee reg---------------
                            ResultSet rs1 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs.getString("emp_id") + "'");
                            while (rs1.next()) {

                                String empId = rs.getString("emp_id");
                                String empName = rs1.getString("f_name") + " " + rs1.getString("l_name");
                                String date = rs.getString("date");
                                String intime = rs.getString("in_time");
                                String outTime = rs.getString("out_time");

                                //// counting hours--------//////////
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                Date timeIn = df.parse(intime);
                                Date timeOut = df.parse(outTime);

                                long time = timeOut.getTime() - timeIn.getTime();

                                String hours = Long.toString(time / (60 * 60 * 1000) % 24);
                                String mints = Long.toString(time / (60 * 1000) % 60);
                                String seconds = Long.toString(time / 1000 % 60);

                                String totHours = hours + ":" + mints + ":" + seconds;

                                //////-----adding data to table--------
                                tool.addToTable(tbl_attendance, empId, empName, date, intime, outTime, totHours);

                            }
                            rs1.close();
                        }
                        rs.close();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "ERROR - ATT 0003-2-2-3 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                        logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    }
                }
                //// -----------------search by Custome date----------------
                if (comboDateForName.equals("Custome")) {
                    tool.clearTable(tbl_attendance);
                    try {
                        ///search from attendance-------------
                        ResultSet rs = DB.getData("SELECT * FROM attendance WHERE emp_id='" + id + "'");
                        while (rs.next()) {
                            Date d = new Date();
                            String from = new SimpleDateFormat("yyyy-MM-dd").format(dc_from.getDate());
                            String to = new SimpleDateFormat("yyyy-MM-dd").format(dc_To.getDate());

                            ////seach from attendance-----------------
                            ResultSet rs1 = DB.getData("SELECT * FROM attendance WHERE date BETWEEN '" + from + "' AND '" + to + "'");
                            while (rs1.next()) {
                                ///// data from employee_reg---------------
                                ResultSet rs2 = DB.getData("SELECT * FROM employee_reg WHERE emp_id='" + rs1.getString("emp_id") + "'");
                                while (rs2.next()) {

                                    String empId = rs2.getString("emp_id");
                                    String empName = rs2.getString("f_name") + " " + rs2.getString("l_name");
                                    String date = rs1.getString("date");
                                    String intime = rs1.getString("in_time");
                                    String outTime = rs1.getString("out_time");

                                    //// counting hours--------//////////
                                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                    Date timeIn = df.parse(intime);
                                    Date timeOut = df.parse(outTime);

                                    long time = timeOut.getTime() - timeIn.getTime();

                                    String hours = Long.toString(time / (60 * 60 * 1000) % 24);
                                    String mints = Long.toString(time / (60 * 1000) % 60);
                                    String seconds = Long.toString(time / 1000 % 60);

                                    String totHours = hours + ":" + mints + ":" + seconds;

                                    //////-----adding data to table--------
                                    tool.addToTable(tbl_attendance, empId, empName, date, intime, outTime, totHours);

                                }
                                rs2.close();
                            }
                            rs1.close();
                                    
                        }
                        rs.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "ERROR - ATT 0003-2-2-4 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                        logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
                    }

                }
            }
        }
    }

    ///// -----------------------code for Reset Button--------------///////////////
    void resetButton() {
        String availableLeaves = txt_noOfLeaves.getText();
        try {
            ResultSet rs = DB.getData("SELECT emp_id FROM employee_reg WHERE status='" + 1 + "'");
            while (rs.next()) {
                DB.putData("UPDATE employee_reg SET available_leaves='" + availableLeaves + "' WHERE emp_id='" + rs.getString("emp_id") + "'");
                JOptionPane.showMessageDialog(rootPane, "Succesfully Updated Leaves to Employees", "Updated", JOptionPane.OK_OPTION);
            }
            rs.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR - ATT 0004 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(ex + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
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
        jPanel1 = new javax.swing.JPanel();
        pnl_header = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lbl_userType = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmb_attendanceMainCombo = new javax.swing.JComboBox();
        btn_search = new javax.swing.JButton();
        cmb_attendanceComboDay = new javax.swing.JComboBox();
        txt_name = new javax.swing.JTextField();
        dc_date = new com.toedter.calendar.JDayChooser();
        dc_month = new com.toedter.calendar.JMonthChooser();
        dc_To = new com.toedter.calendar.JDateChooser();
        dc_Year = new com.toedter.calendar.JYearChooser();
        dc_from = new com.toedter.calendar.JDateChooser();
        lbl_from = new javax.swing.JLabel();
        lbl_Day = new javax.swing.JLabel();
        lbl_to = new javax.swing.JLabel();
        lbl_year = new javax.swing.JLabel();
        lbl_Month = new javax.swing.JLabel();
        cmb_attendanceComboNameID = new javax.swing.JComboBox();
        cmb_attendanceComboDayForNameID = new javax.swing.JComboBox();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_attendance = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        txt_noOfLeaves = new javax.swing.JTextField();
        btn_edit = new javax.swing.JButton();
        btn_add = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        btn_AttendanceGenerateReport = new javax.swing.JButton();
        lbl_background = new javax.swing.JLabel();

        popup_menu.setText("jMenuItem1");
        popup_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popup_menuActionPerformed(evt);
            }
        });
        popupMenu.add(popup_menu);

        popup_help.setText("jMenuItem1");
        popup_help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popup_helpActionPerformed(evt);
            }
        });
        popupMenu.add(popup_help);

        popup_exit.setText("jMenuItem2");
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

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Admin Panel - Attendance");
        pnl_header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 340, 30));

        lbl_date.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_date.setText("jLabel3");
        pnl_header.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, -1));

        lbl_time.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_time.setText("jLabel3");
        pnl_header.add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 23, 100, -1));

        jPanel1.add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 50));

        jPanel12.setOpaque(false);
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel18.setOpaque(false);
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        jLabel3.setText("Search By :");
        jPanel18.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        cmb_attendanceMainCombo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_attendanceMainCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Date", "Employee Details" }));
        cmb_attendanceMainCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_attendanceMainComboActionPerformed(evt);
            }
        });
        jPanel18.add(cmb_attendanceMainCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 196, -1));

        btn_search.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_search.setText("Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jPanel18.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 114, 30));

        cmb_attendanceComboDay.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_attendanceComboDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Daily", "Monthly", "Annually", "Custome" }));
        cmb_attendanceComboDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_attendanceComboDayActionPerformed(evt);
            }
        });
        jPanel18.add(cmb_attendanceComboDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 200, -1));

        txt_name.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nameActionPerformed(evt);
            }
        });
        txt_name.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_nameFocusGained(evt);
            }
        });
        jPanel18.add(txt_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 200, 30));

        dc_date.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        dc_date.setDay(7);
        jPanel18.add(dc_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, -1, 140));

        dc_month.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        dc_month.setMonth(1);
        dc_month.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_monthFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_monthFocusLost(evt);
            }
        });
        jPanel18.add(dc_month, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, 150, 30));

        dc_To.setDateFormatString("yyyy-MM-dd");
        dc_To.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        dc_To.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_ToFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_ToFocusLost(evt);
            }
        });
        jPanel18.add(dc_To, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, 150, 30));

        dc_Year.setYear(2016);
        dc_Year.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_YearFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_YearFocusLost(evt);
            }
        });
        jPanel18.add(dc_Year, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 150, 30));

        dc_from.setDateFormatString("yyyy-MM-dd");
        dc_from.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        dc_from.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_fromFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_fromFocusLost(evt);
            }
        });
        jPanel18.add(dc_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 150, 30));

        lbl_from.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_from.setText("From :");
        jPanel18.add(lbl_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 50, -1));

        lbl_Day.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_Day.setText("Day :");
        jPanel18.add(lbl_Day, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 130, -1, -1));

        lbl_to.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_to.setText("To :");
        jPanel18.add(lbl_to, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, -1, -1));

        lbl_year.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_year.setText("Year :");
        jPanel18.add(lbl_year, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 50, -1));

        lbl_Month.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_Month.setText("Month :");
        jPanel18.add(lbl_Month, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, -1, -1));

        cmb_attendanceComboNameID.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_attendanceComboNameID.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Name", "ID" }));
        cmb_attendanceComboNameID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cmb_attendanceComboNameIDMouseReleased(evt);
            }
        });
        cmb_attendanceComboNameID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_attendanceComboNameIDActionPerformed(evt);
            }
        });
        cmb_attendanceComboNameID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cmb_attendanceComboNameIDKeyReleased(evt);
            }
        });
        jPanel18.add(cmb_attendanceComboNameID, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 196, -1));

        cmb_attendanceComboDayForNameID.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_attendanceComboDayForNameID.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Daily", "Monthly", "Annually", "Custome" }));
        cmb_attendanceComboDayForNameID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmb_attendanceComboDayForNameIDMouseClicked(evt);
            }
        });
        cmb_attendanceComboDayForNameID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_attendanceComboDayForNameIDActionPerformed(evt);
            }
        });
        jPanel18.add(cmb_attendanceComboDayForNameID, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 200, -1));

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Result", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel19.setOpaque(false);

        tbl_attendance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Employee Name", "Date", "In Time", "Out Time", "Hours"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_attendance.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tbl_attendance);
        if (tbl_attendance.getColumnModel().getColumnCount() > 0) {
            tbl_attendance.getColumnModel().getColumn(0).setResizable(false);
            tbl_attendance.getColumnModel().getColumn(1).setResizable(false);
            tbl_attendance.getColumnModel().getColumn(2).setResizable(false);
            tbl_attendance.getColumnModel().getColumn(3).setResizable(false);
            tbl_attendance.getColumnModel().getColumn(4).setResizable(false);
            tbl_attendance.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1338, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Default Leave Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12))); // NOI18N
        jPanel20.setOpaque(false);
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_noOfLeaves.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_noOfLeaves.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_noOfLeavesActionPerformed(evt);
            }
        });
        jPanel20.add(txt_noOfLeaves, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 130, -1));

        btn_edit.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_edit.setText("Edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        jPanel20.add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 73, -1));

        btn_add.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btn_add.setText("Add");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });
        jPanel20.add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, 80, -1));

        jLabel7.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel7.setText("Number of Leaves :");
        jPanel20.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jButton3.setFont(new java.awt.Font("Ubuntu", 0, 11)); // NOI18N
        jButton3.setText("Reset");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel20.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 70, -1));

        btn_AttendanceGenerateReport.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_AttendanceGenerateReport.setText("Generate Report");
        btn_AttendanceGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AttendanceGenerateReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_AttendanceGenerateReport)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_AttendanceGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 50, 1370, 720));

        lbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N
        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void dc_monthFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_monthFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_monthFocusGained

    private void dc_monthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_monthFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_monthFocusLost

    private void dc_ToFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_ToFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_ToFocusGained

    private void dc_ToFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_ToFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_ToFocusLost

    private void dc_YearFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_YearFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_YearFocusGained

    private void dc_YearFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_YearFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_YearFocusLost

    private void dc_fromFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_fromFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_fromFocusGained

    private void dc_fromFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_fromFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_fromFocusLost

    private void txt_noOfLeavesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_noOfLeavesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_noOfLeavesActionPerformed

    private void cmb_attendanceComboDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_attendanceComboDayActionPerformed
        attendanceComboDaySelect();
    }//GEN-LAST:event_cmb_attendanceComboDayActionPerformed

    private void cmb_attendanceComboNameIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_attendanceComboNameIDActionPerformed
        attendanceNameIdSelect();
        if (cmb_attendanceComboNameID.getSelectedItem().toString().equals("ID")) {
            txt_name.setText("EMP");

        }
    }//GEN-LAST:event_cmb_attendanceComboNameIDActionPerformed

    private void cmb_attendanceMainComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_attendanceMainComboActionPerformed
        attendanceMainComboSelect();
    }//GEN-LAST:event_cmb_attendanceMainComboActionPerformed

    private void cmb_attendanceComboDayForNameIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_attendanceComboDayForNameIDActionPerformed

        if (cmb_attendanceComboDayForNameID.getSelectedIndex() == 0) {
            attendanceComboDaily();
        }
        if (cmb_attendanceComboDayForNameID.getSelectedIndex() == 1) {
            attendanceComboMonthly();
        }
        if (cmb_attendanceComboDayForNameID.getSelectedIndex() == 2) {
            attendanceComboAnnualy();
        }
        if (cmb_attendanceComboDayForNameID.getSelectedIndex() == 3) {
            attendanceComboCustome();
        }
    }//GEN-LAST:event_cmb_attendanceComboDayForNameIDActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        searchDetailsFromDB();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int dialog = JOptionPane.showConfirmDialog(rootPane, "Do you want to reset the Employee leaves", "Please Confirm", JOptionPane.YES_NO_OPTION);
        if (dialog == 0) {
            resetButton();
        }
        if (dialog == 1) {
            dispose();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cmb_attendanceComboDayForNameIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmb_attendanceComboDayForNameIDMouseClicked

    }//GEN-LAST:event_cmb_attendanceComboDayForNameIDMouseClicked

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        updateLaevesDetailsToAdminPanel();
        txt_noOfLeaves.setEnabled(false);
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        textFieldEditable();
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_AttendanceGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AttendanceGenerateReportActionPerformed
        String company_email = null, company_number = null, details_by;
        try {
            ResultSet rs = DB.getData("SELECT company_email,company_mobile,company_office FROM admin_panel");
            while (rs.next()) {
                company_email = rs.getString("company_email");
                company_number = rs.getString("company_mobile") + " " + rs.getString("company_office");

            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ATT 0005 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
        details_by = cmb_attendanceComboDay.getSelectedItem().toString().trim();
        int i = tbl_attendance.getRowCount();
        if (i > 0) {
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("\\Reports\\attendance.jrxml");
                JasperDesign jd = JRXmlLoader.load(is);
                JasperReport jr = JasperCompileManager.compileReport(jd);

                JRTableModelDataSource tm = new JRTableModelDataSource(tbl_attendance.getModel());

                Map<String, Object> p = new HashMap<String, Object>();

                p.put("email", company_email);
                p.put("numbers", company_number);
                p.put("details by", details_by);

                JasperPrint jp = JasperFillManager.fillReport(jr, p, tm);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - ATT 0006 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

        }
    }//GEN-LAST:event_btn_AttendanceGenerateReportActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new Menu().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_nameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nameFocusGained

    }//GEN-LAST:event_txt_nameFocusGained

    private void txt_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nameActionPerformed

    private void cmb_attendanceComboNameIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_attendanceComboNameIDKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_attendanceComboNameIDKeyReleased

    private void cmb_attendanceComboNameIDMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmb_attendanceComboNameIDMouseReleased

    }//GEN-LAST:event_cmb_attendanceComboNameIDMouseReleased

    private void popup_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_menuActionPerformed
       this.dispose();
      new Menu().setVisible(true);       
    }//GEN-LAST:event_popup_menuActionPerformed

    private void popup_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_helpActionPerformed
        try {
            Desktop.getDesktop().open(new File("E:\\Syndicate\\Documentation\\Admin – Attendance.pdf"));
            
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

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        if (evt.getButton()==3) {
            popupMenu.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_jPanel12MouseClicked

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
            java.util.logging.Logger.getLogger(Attendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Attendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Attendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Attendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Attendance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_AttendanceGenerateReport;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_search;
    private javax.swing.JComboBox cmb_attendanceComboDay;
    private javax.swing.JComboBox cmb_attendanceComboDayForNameID;
    private javax.swing.JComboBox cmb_attendanceComboNameID;
    private javax.swing.JComboBox cmb_attendanceMainCombo;
    private com.toedter.calendar.JDateChooser dc_To;
    private com.toedter.calendar.JYearChooser dc_Year;
    private com.toedter.calendar.JDayChooser dc_date;
    private com.toedter.calendar.JDateChooser dc_from;
    private com.toedter.calendar.JMonthChooser dc_month;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbl_Day;
    private javax.swing.JLabel lbl_Month;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_from;
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
    private javax.swing.JTable tbl_attendance;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_noOfLeaves;
    // End of variables declaration//GEN-END:variables
}
