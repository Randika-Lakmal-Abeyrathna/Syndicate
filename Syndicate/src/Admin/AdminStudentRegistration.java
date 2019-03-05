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
import org.apache.log4j.Logger;

/**
 *
 * @author Randika
 */
public class AdminStudentRegistration extends javax.swing.JFrame {

    /**
     * Creates new form AdminStudentRegistration/
     */
    ToolsClass tool = new ToolsClass();
    JDBC DB = new JDBC();
    Logger logError = Logger.getLogger("ERROR");

    public AdminStudentRegistration() {
        initComponents();
        Menu();
        dc_from.setMaxSelectableDate(new Date());
        dc_to.setMaxSelectableDate(new Date());
        setSelectName();
        lbl_username.setText(Home.un);
        lbl_userType.setText(Home.ut);
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

    //------------------------- Desable Components Start ------------------------------------//
    //Name, Register Date, Class, Class Type
    //setSelectName
    void setSelectName() {
        cmb_classes.setVisible(false);
        cmb_classType.setVisible(false);
        txt_SearchByName.setVisible(true);
        dc_from.setVisible(false);
        dc_to.setVisible(false);
        lbl_from.setVisible(false);
        lbl_to.setVisible(false);
        btn_search.setVisible(true);
    }

    //set Form Date
    void selectDate() {
        cmb_classes.setVisible(false);
        cmb_classType.setVisible(false);
        txt_SearchByName.setVisible(false);
        dc_from.setVisible(true);
        dc_to.setVisible(true);
        lbl_from.setVisible(true);
        lbl_to.setVisible(true);
        btn_search.setVisible(true);
    }

    //set from Class
    void setClass() {
        cmb_classes.setVisible(true);
        cmb_classType.setVisible(false);
        txt_SearchByName.setVisible(false);
        dc_from.setVisible(false);
        dc_to.setVisible(false);
        btn_search.setVisible(false);
        lbl_from.setVisible(false);
        lbl_to.setVisible(false);
        setClasses();
    }

    //set from Class type
    void setClassType() {
        cmb_classes.setVisible(false);
        cmb_classType.setVisible(true);
        txt_SearchByName.setVisible(false);
        dc_from.setVisible(false);
        dc_to.setVisible(false);
        btn_search.setVisible(false);
        lbl_from.setVisible(false);
        lbl_to.setVisible(false);
        setClassTypetoCombo();
    }
    //------------------------- Desable Components Finish------------------------------------//

    //-------------------------Set Combo box Details Start ---------------------------------------
    //set classes to combo box
    void setClasses() {
        try {
            ResultSet rs = DB.getData("SELECT * FROM class_details");
            Vector v = new Vector();
            while (rs.next()) {
                String classId = rs.getString("class_id");
                String day = rs.getString("day");
                String subjectId = rs.getString("subject_id");
                ResultSet rs1 = DB.getData("SELECT * FROM subject WHERE subject_id='" + subjectId + "'");
                while (rs1.next()) {
                    String subject = rs1.getString("subject_name");
                    String all = classId + "-" + day + "-" + subject;
                    v.add(all);
                }
            }
            cmb_classes.removeAllItems();
            for (int i = 0; i < v.size(); i++) {
                cmb_classes.addItem(v.get(i));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ASR 0001 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    //setclass types for combo box
    void setClassTypetoCombo() {
        try {
            ResultSet rs = DB.getData("SELECT * FROM class_type");
            Vector v = new Vector();
            while (rs.next()) {
                String classType = rs.getString("class_type");
                v.add(classType);
            }
            cmb_classType.removeAllItems();
            for (int i = 0; i < v.size(); i++) {
                cmb_classType.addItem(v.get(i));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ASR 0002 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }
    //-------------------------Set Combo box Details Finish---------------------------------------

    //-----------------------Set Details To Table Start ------------------------------------
    //Name, Register Date, Class, Class Type
    //By Name
    String studentId, fname, lname, addressno, street1, street2, city, date, mobileno, birthday, status, gardianId, gardianName, gardianContactNo, gardianType;

    void searchFromName(String name) {
        tool.clearTable(tbl_studentDetails);
        try {
            ResultSet rs = DB.getData("SELECT * FROM student_reg WHERE "
                    + (name.contains(" ")
                    ? ("f_name LIKE '" + name.split(" ")[0] + "%' AND l_name LIKE '" + name.split(" ")[1] + "%' ")
                    : ("f_name LIKE '" + name + "%' OR l_name LIKE '" + name + "%'")));
            while (rs.next()) {
                studentId = rs.getString("student_id");
                fname = rs.getString("f_name");
                lname = rs.getString("l_name");
                addressno = rs.getString("no");
                street1 = rs.getString("street1");
                street2 = rs.getString("street2");
                city = rs.getString("city");
                date = rs.getString("date");
                birthday = rs.getString("birthday");
                status = rs.getString("status");
                mobileno=rs.getString("mobile_no");
                String statusName;
                if (status.equals("1")) {
                    statusName = "Active";
                } else {
                    statusName = "Inactive";
                }
                gardianId = rs.getString("guardion_Id");

                ResultSet rs1 = DB.getData("SELECT * FROM guardion_details WHERE guardion_id='" + gardianId + "' ");
                while (rs1.next()) {
                    String gfname = rs1.getString("f_name");
                    String glname = rs1.getString("l_name");
                    gardianType = rs1.getString("type");
                    gardianContactNo = rs1.getString("mobile_no");
                    gardianName = gfname + " " + glname;
                    String StudentName = fname + " " + lname;
                    String address = addressno + "," + street1 + "," + street2 + "," + city;
                    tool.addToTable(tbl_studentDetails, studentId, StudentName, address, date, mobileno, birthday, statusName,
                            gardianName, gardianContactNo, gardianType);

                }
                rs1.close();
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ASR 0003 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    //By Register Date
    void searchFromDate(String fromDate, String toDate) {
        tool.clearTable(tbl_studentDetails);
        try {
            ResultSet rs = DB.getData("SELECT * FROM student_reg WHERE date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
            while (rs.next()) {
                studentId = rs.getString("student_id");
                fname = rs.getString("f_name");
                lname = rs.getString("l_name");
                addressno = rs.getString("no");
                street1 = rs.getString("street1");
                street2 = rs.getString("street2");
                city = rs.getString("city");
                date = rs.getString("date");
                birthday = rs.getString("birthday");
                status = rs.getString("status");
                mobileno=rs.getString("mobile_no");
                String statusName;
                if (status.equals("1")) {
                    statusName = "Active";
                } else {
                    statusName = "Inactive";
                }
                gardianId = rs.getString("guardion_Id");

                ResultSet rs1 = DB.getData("SELECT * FROM guardion_details WHERE guardion_id='" + gardianId + "' ");
                while (rs1.next()) {
                    String gfname = rs1.getString("f_name");
                    String glname = rs1.getString("l_name");
                    gardianType = rs1.getString("type");
                    gardianContactNo = rs1.getString("mobile_no");
                    gardianName = gfname + " " + glname;
                    String StudentName = fname + " " + lname;
                    String address = addressno + "," + street1 + "," + street2 + "," + city;
                    tool.addToTable(tbl_studentDetails, studentId, StudentName, address, date, mobileno, birthday, statusName,
                            gardianName, gardianContactNo, gardianType);
                }
                rs1.close();
            }
            rs.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ASR 0004 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    //By Classes
    void searchByClasses(String id) {
        tool.clearTable(tbl_studentDetails);
        try {
            ResultSet rs = DB.getData("SELECT * FROM student_timetable WHERE class_id='" + id + "'");
            while (rs.next()) {
                ResultSet rs1 = DB.getData("SELECT * FROM student_reg WHERE student_id='" + rs.getString("student_id") + "'");
                while (rs1.next()) {
                    studentId = rs1.getString("student_id");
                    fname = rs1.getString("f_name");
                    lname = rs1.getString("l_name");
                    addressno = rs1.getString("no");
                    street1 = rs1.getString("street1");
                    street2 = rs1.getString("street2");
                    city = rs1.getString("city");
                    date = rs1.getString("date");
                    birthday = rs1.getString("birthday");
                    status = rs1.getString("status");
                    mobileno=rs1.getString("mobile_no");
                    String statusName;
                    if (status.equals("1")) {
                        statusName = "Active";
                    } else {
                        statusName = "Inactive";
                    }
                    gardianId = rs1.getString("guardion_Id");

                    ResultSet rs2 = DB.getData("SELECT * FROM guardion_details WHERE guardion_id='" + gardianId + "' ");
                    while (rs2.next()) {
                        String gfname = rs2.getString("f_name");
                        String glname = rs2.getString("l_name");
                        gardianType = rs2.getString("type");
                        gardianContactNo = rs2.getString("mobile_no");
                        gardianName = gfname + " " + glname;
                        String StudentName = fname + " " + lname;
                        String address = addressno + "," + street1 + "," + street2 + "," + city;
                        tool.addToTable(tbl_studentDetails, studentId, StudentName, address, date, mobileno, birthday, statusName,
                                gardianName, gardianContactNo, gardianType);

                    }
                    rs2.close();
                }
                rs1.close();
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ASR 0005 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
    }

    //By Class Type
    void searchByClassType(String ClassType) {
        tool.clearTable(tbl_studentDetails);
        try {
            ResultSet rs = DB.getData("SELECT * FROM class_type WHERE class_type='" + ClassType + "'");
            while (rs.next()) {
                ResultSet rs1 = DB.getData("SELECT * FROM class_details WHERE class_type_id='" + rs.getString("class_type_id") + "'");
                while (rs1.next()) {
                    ResultSet rs2 = DB.getData("SELECT * FROM student_reg WHERE student_id=(SELECT student_id FROM student_timetable WHERE class_id='" + rs1.getString("class_id") + "')");
                    while (rs2.next()) {
                        studentId = rs2.getString("student_id");
                        fname = rs2.getString("f_name");
                        lname = rs2.getString("l_name");
                        addressno = rs2.getString("no");
                        street1 = rs2.getString("street1");
                        street2 = rs2.getString("street2");
                        city = rs2.getString("city");
                        date = rs2.getString("date");
                        birthday = rs2.getString("birthday");
                        status = rs2.getString("status");
                        mobileno=rs2.getString("mobile_no");
                        String statusName;
                        if (status.equals("1")) {
                            statusName = "Active";
                        } else {
                            statusName = "Inactive";
                        }
                        gardianId = rs2.getString("guardion_Id");

                        ResultSet rs3 = DB.getData("SELECT * FROM guardion_details WHERE guardion_id='" + gardianId + "' ");
                        while (rs3.next()) {
                            String gfname = rs3.getString("f_name");
                            String glname = rs3.getString("l_name");
                            gardianType = rs3.getString("type");
                            gardianContactNo = rs3.getString("mobile_no");
                            gardianName = gfname + " " + glname;
                            String StudentName = fname + " " + lname;
                            String address = addressno + "," + street1 + "," + street2 + "," + city;
                            tool.addToTable(tbl_studentDetails, studentId, StudentName, address, date, mobileno, birthday, statusName,
                                    gardianName, gardianContactNo, gardianType);

                        }
                        rs3.close();
                    }
                    rs2.close();

                }
                rs1.close();
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ASR 0006 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }

    }
    //-----------------------Set Details To Table Finish ------------------------------------
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
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel34 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmb_serchby = new javax.swing.JComboBox();
        btn_search = new javax.swing.JButton();
        cmb_classes = new javax.swing.JComboBox();
        txt_SearchByName = new javax.swing.JTextField();
        dc_from = new com.toedter.calendar.JDateChooser();
        lbl_from = new javax.swing.JLabel();
        lbl_to = new javax.swing.JLabel();
        dc_to = new com.toedter.calendar.JDateChooser();
        cmb_classType = new javax.swing.JComboBox();
        pnl_details = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_studentDetails = new javax.swing.JTable();
        btn_refresh = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_teacherDetailsGenerateReport = new javax.swing.JButton();
        pnl_Expenceheader = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lbl_userType = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
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

        jPanel5.setOpaque(false);
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jTabbedPane2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane2MouseClicked(evt);
            }
        });

        jPanel34.setOpaque(false);
        jPanel34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel34MouseClicked(evt);
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

        cmb_serchby.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_serchby.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Name", "Register Date", "Class", "Class Type" }));
        cmb_serchby.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_serchbyActionPerformed(evt);
            }
        });
        jPanel9.add(cmb_serchby, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 170, -1));

        btn_search.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        btn_search.setText("Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jPanel9.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, 114, 30));

        cmb_classes.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_classes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_classesActionPerformed(evt);
            }
        });
        jPanel9.add(cmb_classes, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 200, 30));

        txt_SearchByName.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_SearchByName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_SearchByNameKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_SearchByNameKeyTyped(evt);
            }
        });
        jPanel9.add(txt_SearchByName, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 197, 30));

        dc_from.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_fromFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_fromFocusLost(evt);
            }
        });
        jPanel9.add(dc_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 170, 30));

        lbl_from.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_from.setText("From :");
        jPanel9.add(lbl_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 50, 30));

        lbl_to.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        lbl_to.setText("To :");
        jPanel9.add(lbl_to, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, -1, 30));

        dc_to.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dc_toFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_toFocusLost(evt);
            }
        });
        jPanel9.add(dc_to, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 80, 200, 30));

        cmb_classType.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cmb_classType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmb_classType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_classTypeActionPerformed(evt);
            }
        });
        jPanel9.add(cmb_classType, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 200, 30));

        pnl_details.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Result", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        pnl_details.setOpaque(false);
        pnl_details.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_detailsMouseClicked(evt);
            }
        });

        tbl_studentDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Name", "Address", "Reg. Date", "Mobile Number", "Birthday", "Status", "Guardian Name", "Contact Number", "Guadian Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_studentDetails.setColumnSelectionAllowed(true);
        tbl_studentDetails.setEnabled(false);
        tbl_studentDetails.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tbl_studentDetails);
        tbl_studentDetails.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tbl_studentDetails.getColumnModel().getColumnCount() > 0) {
            tbl_studentDetails.getColumnModel().getColumn(0).setResizable(false);
            tbl_studentDetails.getColumnModel().getColumn(1).setResizable(false);
            tbl_studentDetails.getColumnModel().getColumn(2).setResizable(false);
            tbl_studentDetails.getColumnModel().getColumn(3).setResizable(false);
            tbl_studentDetails.getColumnModel().getColumn(4).setResizable(false);
            tbl_studentDetails.getColumnModel().getColumn(5).setResizable(false);
            tbl_studentDetails.getColumnModel().getColumn(6).setResizable(false);
            tbl_studentDetails.getColumnModel().getColumn(7).setResizable(false);
            tbl_studentDetails.getColumnModel().getColumn(8).setResizable(false);
            tbl_studentDetails.getColumnModel().getColumn(9).setResizable(false);
        }

        btn_refresh.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_refresh.setText("Refresh");
        btn_refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btn_delete.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btn_update.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_update.setText("Update");
        btn_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btn_save.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_save.setText("Save");
        btn_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_teacherDetailsGenerateReport.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_teacherDetailsGenerateReport.setText("Generate Report");
        btn_teacherDetailsGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_teacherDetailsGenerateReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_detailsLayout = new javax.swing.GroupLayout(pnl_details);
        pnl_details.setLayout(pnl_detailsLayout);
        pnl_detailsLayout.setHorizontalGroup(
            pnl_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_detailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_detailsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnl_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_detailsLayout.createSequentialGroup()
                                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_teacherDetailsGenerateReport, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        pnl_detailsLayout.setVerticalGroup(
            pnl_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_detailsLayout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_teacherDetailsGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addGroup(pnl_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_refresh))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 1330, Short.MAX_VALUE)
                    .addComponent(pnl_details, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnl_details, javax.swing.GroupLayout.PREFERRED_SIZE, 498, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Student Details", jPanel34);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 55, 1360, 720));

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

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Admin Panel - Student Details");
        pnl_Expenceheader.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 340, 30));

        lbl_time.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_time.setText("jLabel3");
        pnl_Expenceheader.add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 23, 100, -1));

        lbl_date.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_date.setText("jLabel3");
        pnl_Expenceheader.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, -1));

        getContentPane().add(pnl_Expenceheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 50));

        lbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N
        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_saveActionPerformed

    private void dc_fromFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_fromFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_fromFocusGained

    private void dc_fromFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_fromFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_fromFocusLost

    private void dc_toFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_toFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_toFocusGained

    private void dc_toFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_toFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dc_toFocusLost

    private void cmb_serchbyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_serchbyActionPerformed
        //Name, Register Date, Class, Class Type
        String selectedString = cmb_serchby.getSelectedItem().toString().trim();
        if (selectedString.equals("Name")) {
            setSelectName();
        } else if (selectedString.equals("Register Date")) {
            selectDate();
        } else if (selectedString.equals("Class")) {
            setClass();
        } else if (selectedString.equals("Class Type")) {
            setClassType();
        }
    }//GEN-LAST:event_cmb_serchbyActionPerformed

    private void cmb_classesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_classesActionPerformed
        int i = cmb_classes.getItemCount();
        if (i > 0) {
            String selectedClass = cmb_classes.getSelectedItem().toString().trim().split("-")[0];
            searchByClasses(selectedClass);
        }
    }//GEN-LAST:event_cmb_classesActionPerformed

    private void cmb_classTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_classTypeActionPerformed
        int i = cmb_classType.getItemCount();
        if (i > 0) {
            String classType = cmb_classType.getSelectedItem().toString().trim();
            searchByClassType(classType);
        }
    }//GEN-LAST:event_cmb_classTypeActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        String SelectedValue = cmb_serchby.getSelectedItem().toString().trim();
        if (SelectedValue.equals("Name")) {
            if (!txt_SearchByName.getText().equals("")) {
                searchFromName(txt_SearchByName.getText());

            } else {
                JOptionPane.showMessageDialog(this, "Enter The Name to Search ");
            }
        } else if (SelectedValue.equals("Register Date")) {
            Date from = dc_from.getDate();
            Date to = dc_to.getDate();
            String Stringfrom = from + "";
            String Stringto = to + "";
            if (Stringfrom.equals(null + "") || Stringto.equals(null + "")) {
                JOptionPane.showMessageDialog(this, "Please Enter the Date");
            } else if (!(Stringfrom.equals(null + "") && Stringto.equals(null + ""))) {
                searchFromDate(new SimpleDateFormat("yyyy-MM-dd").format(dc_from.getDate()), new SimpleDateFormat("yyyy-MM-dd").format(dc_to.getDate()));

            }

        }

    }//GEN-LAST:event_btn_searchActionPerformed

    private void txt_SearchByNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchByNameKeyReleased

    }//GEN-LAST:event_txt_SearchByNameKeyReleased

    private void txt_SearchByNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchByNameKeyTyped
        if (Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_SearchByNameKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new Menu().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_teacherDetailsGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_teacherDetailsGenerateReportActionPerformed
        genarateReport();
    }//GEN-LAST:event_btn_teacherDetailsGenerateReportActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel5MouseClicked

    private void popup_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_menuActionPerformed
        this.dispose();
        new Menu().setVisible(true);
    }//GEN-LAST:event_popup_menuActionPerformed

    private void popup_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_helpActionPerformed
        try {
            Desktop.getDesktop().open(new File("E:\\Syndicate\\Documentation\\Admin - Student Details.pdf"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_popup_helpActionPerformed

    private void popup_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_exitActionPerformed
        this.dispose();
    }//GEN-LAST:event_popup_exitActionPerformed

    private void jPanel34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel34MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel34MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jPanel9MouseClicked

    private void pnl_detailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_detailsMouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_detailsMouseClicked

    private void pnl_ExpenceheaderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_ExpenceheaderMouseClicked
        if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_ExpenceheaderMouseClicked

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked
         if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTabbedPane2MouseClicked

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
            java.util.logging.Logger.getLogger(AdminStudentRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminStudentRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminStudentRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminStudentRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminStudentRegistration().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_teacherDetailsGenerateReport;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox cmb_classType;
    private javax.swing.JComboBox cmb_classes;
    private javax.swing.JComboBox cmb_serchby;
    private com.toedter.calendar.JDateChooser dc_from;
    private com.toedter.calendar.JDateChooser dc_to;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_from;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_to;
    private javax.swing.JLabel lbl_userType;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_Expenceheader;
    private javax.swing.JPanel pnl_details;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem popup_exit;
    private javax.swing.JMenuItem popup_help;
    private javax.swing.JMenuItem popup_menu;
    private javax.swing.JTable tbl_studentDetails;
    private javax.swing.JTextField txt_SearchByName;
    // End of variables declaration//GEN-END:variables

    //Genareate Report
    void genarateReport() {
        //Name, Register Date, Class, Class Type
        String company_email = null, company_number = null, details_by;

        try {
            ResultSet rs = DB.getData("SELECT company_email,company_mobile,company_office FROM admin_panel");
            while (rs.next()) {
                company_email = rs.getString("company_email");
                company_number = rs.getString("company_mobile") + " " + rs.getString("company_office");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR - ASR 0007 - 1 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
            logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
        }
        String Details_By = null;
        details_by = cmb_serchby.getSelectedItem().toString().trim();
        if (details_by.equals("Name")) {
            String Name = txt_SearchByName.getText();
            if (!Name.equals("")) {
                Details_By = "Student Name :";

            }
        } else if (details_by.equals("Register Date")) {

            Details_By = "Register Date :" + " From :" + new SimpleDateFormat("yyyy-MM-dd").format(dc_from.getDate()) + " To :" + new SimpleDateFormat("yyyy-MM-dd").format(dc_to.getDate());
        } else if (details_by.equals("Class")) {

            Details_By = "Class Details :" + cmb_classes.getSelectedItem().toString().trim();
        } else if (details_by.equals("Class Type")) {
            Details_By = "Class Type :" + cmb_classType.getSelectedItem().toString().trim();
        }
        int i = tbl_studentDetails.getRowCount();
        if (i > 0) {
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("\\Reports\\Student Details.jrxml");
                JasperDesign jd = JRXmlLoader.load(is);
                JasperReport jr = JasperCompileManager.compileReport(jd);

                JRTableModelDataSource tm = new JRTableModelDataSource(tbl_studentDetails.getModel());

                Map<String, Object> p = new HashMap<String, Object>();
                p.put("email", company_email);
                p.put("numbers", company_number);
                p.put("details by", Details_By);

                JasperPrint jp = JasperFillManager.fillReport(jr, p, tm);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR - ASR 0007-2 \nPlease contact Development Team", "ERROR", JOptionPane.ERROR_MESSAGE);
                logError.error(e + " at : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            }

        } else {
            JOptionPane.showMessageDialog(this, "There is No data to Print");
        }
    }

}
