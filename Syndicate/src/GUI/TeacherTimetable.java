/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.JDBC;
import Classes.ToolsClass;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Vector;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;

/**
 *
 * @author USER
 */
public class TeacherTimetable extends javax.swing.JFrame {

    /**
     * Creates new form ClassDetails
     */
    JDBC DB = new JDBC();
    ToolsClass tool = new ToolsClass();

    String sub = "";

    Logger log = Logger.getLogger("Teacher Time Table");

    public TeacherTimetable() {
        initComponents();
        Menu();
        lbl_date.setText(new Classes.Commons().Date());
        new Classes.Commons().time(lbl_time);
        new Classes.Commons().background(this, lbl_background);
        loadSubject();
        classTypeToCombo();
        lbl_username.setText(Home.un);
        lbl_userType.setText(Home.ut);
    }
    
    void Menu(){
        popup_menu.setText("Menu");
        popup_help.setText("Help");
        popup_exit.setText("Exit");
    }

    //------------------------------------------------clear table-------------------------------------
    void clearTable(JTable table) {
        int count = table.getRowCount();
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        for (int i = 0; i < count; i++) {
            dtm.removeRow(0);

        }
    }

    //---------------------------addtable------------------
    void addToTable(JTable tbJTable, String... data) {
        DefaultTableModel dtd = (DefaultTableModel) tbl_teachertimetable.getModel();
        Vector v = new Vector();
        int colCount = tbl_teachertimetable.getColumnCount();
        for (int i = 0; i < colCount; i++) {
            v.add(data[i]);

        }
        dtd.addRow(v);
    }

    ///----------------------------------load class type to combo-------------------------------
    void classTypeToCombo() {
        Vector v = new Vector();
        try {
            ResultSet rs = DB.getData("SELECT * FROM class_type");
            while (rs.next()) {
                String type = rs.getString("class_type");
                v.add(type);
            }
            for (int i = 0; i < v.size(); i++) {
                cmb_classType.addItem(v.get(i));
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TeacherTimetable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //---------------------------------------------- Load Subjects Start------------------------------
    void loadSubject() {
        try {
            ResultSet rs = DB.getData("SELECT * FROM subject");
            Vector v = new Vector();
            while (rs.next()) {
                String sub = rs.getString("subject_name");

                String me_id = rs.getString("medium_id");
                ResultSet rs1 = DB.getData("SELECT * FROM medium where medium_id='" + me_id + "'");
                while (rs1.next()) {
                    String medium = rs1.getString("medium");

                    String load = sub + "-" + medium;

                    v.add(load);

                }

            }

            cmb_subject.removeAllItems();
            for (int i = 0; i < v.size(); i++) {
                cmb_subject.addItem(v.get(i));

            }
        } catch (Exception e) {
            System.out.println("in loadSubject method in class Teacher Time Tabele");
            e.printStackTrace();
        }
    }
    //---------------------------------------------- Load Subjects Finish------------------------------

    //----------------------------------------------- Search Method Start-------------------------------------------------
    void setSearch(String id) {
        clearTable(tbl_teachertimetable);
        String timetableid = null, classFees = null, subjectId, fname, lname, all = null, subject = null;
        try {
            ResultSet rs = DB.getData("SELECT * FROM teacher_time_table WHERE teacher_id='" + txt_search.getText() + "'");
            while (rs.next()) {
                String cls_typ_id=rs.getString("class_type_id");
                timetableid = rs.getString("time_table_id");
                classFees = rs.getString("class_fee");
                subjectId = rs.getString("subject_id");
                ResultSet rs2 = DB.getData("SELECT * FROM subject WHERE subject_id='" + subjectId + "'");
                while (rs2.next()) {
                    subject = rs2.getString("subject_name");
                    String m_id = rs2.getString("medium_id");
                        ResultSet rs3 = DB.getData("SELECT * FROM medium WHERE medium_id='"+m_id+"'");
                            while(rs3.next()){
                                String medium=rs3.getString("medium");
                                    ResultSet rs4 = DB.getData("SELECT * FROM class_type WHERE class_type_id='"+cls_typ_id+"'");
                                        while(rs4.next()){
                                            String class_type=rs4.getString("class_type");
            ResultSet rs1 = DB.getData("SELECT * FROM teacher_reg WHERE teacher_id='" + id + "'");
            while (rs1.next()) {
                fname = rs1.getString("f_name");
                lname = rs1.getString("l_name");
                all = fname + " " + lname;
                lbl_teacher_id.setText(id);
                lbl_tacherName.setText(all);
                /// txt_classfee.setText(classFees);
            
            tool.addToTable(tbl_teachertimetable, all, subject, medium,class_type, classFees);
            }
            }
            }
                }
            }
        } catch (Exception e) {
            System.out.println("in setSearch mwthod in class teacher time table");
            e.printStackTrace();
        }
    }
    //----------------------------------------------- Search Method Finish-------------------------------------------------

    //------------------------------------------------ Add to DataBase Start -----------------------------------------------
    void addValuesToDB() {

        if (txt_classfee.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Fielda can't be empty!!!");
        } else {
            try {
                String classTypeID = "";
                String fullSub = "";
                String mediumID = "";
                String SubjectID = "";
                ResultSet rs1 = DB.getData("SELECT * FROM class_type WHERE class_type='" + cmb_classType.getSelectedItem().toString() + "'");
                while (rs1.next()) {
                    classTypeID = rs1.getString("class_type_id");
                }
                String grade = cmb_subject.getSelectedItem().toString().split("-")[0];
                String sub_nme = cmb_subject.getSelectedItem().toString().split("-")[1];
                String medium = cmb_subject.getSelectedItem().toString().split("-")[2];
                fullSub = grade + "-" + sub_nme;

                ResultSet rs2 = DB.getData("SELECT * FROM medium WHERE medium='" + medium + "'");
                while (rs2.next()) {
                    mediumID = rs2.getString("medium_id");
                }

                ResultSet rs3 = DB.getData("SELECT * FROM subject WHERE subject_name='" + fullSub + "' AND medium_id = '" + mediumID + "'");
                while (rs3.next()) {
                    SubjectID = rs3.getString("subject_id");
                }

                DB.putData("INSERT INTO teacher_time_table (class_fee,teacher_id,subject_id,class_type_id) VALUES ('" + txt_classfee.getText() + "','" + lbl_teacher_id.getText() + "','" + SubjectID + "','" + classTypeID + "')");
                JOptionPane.showMessageDialog(rootPane, "Successfully saved!!!");
                log.info("Teacher Time Table data added at : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                ResultSet rs = DB.getData("SELECT MAX(time_table_id) as time_id FROM teacher_time_table");
                while (rs.next()) {
                    String t_tid = rs.getString("time_id");
                    System.out.println("adddto");
                    Vector v = new Vector();
                    v.add(lbl_tacherName.getText());
                    v.add(sub_nme);
                    v.add(medium);
                    v.add(cmb_classType.getSelectedItem().toString());
                    v.add(txt_classfee.getText());
                    v.add(t_tid);

                    DefaultTableModel teachertimetable = (DefaultTableModel) tbl_teachertimetable.getModel();
                    teachertimetable.addRow(v);
                }

            } catch (Exception e) {
                System.out.println("in addValuesToDB in class teacher Time table");
                e.printStackTrace();
            }
        }
    }

    void addtoTable1() {
        try {
            System.out.println("suggg");
            ResultSet rs1 = DB.getData("SELECT * FROM teacher_time_table where teacher_id='" + txt_search.getText() + "'");
            while (rs1.next()) {
                System.out.println("subbb");
                String tcher_id = rs1.getString("teacher_id");
                String t_id = rs1.getString("time_table_id");
                String cls_fee = rs1.getString("class_fee");
                String tch_id = rs1.getString("subject_id");
                String cls_ty_id=rs1.getString("class_type_id");
                ResultSet rs3 = DB.getData("SELECT * FROM subject WHERE subject_id = '" + tch_id + "' ");
                while (rs3.next()) {
                    System.out.println("sub11");
                    String sub_nme = rs3.getString("subject_name");
                    String m_id=rs3.getString("medium_id");
                    ResultSet rs2 = DB.getData("select * from teacher_reg inner join salutation on (teacher_reg.salutation_id=salutation.salutation_id)where teacher_id='" + txt_search.getText() + "'");
                    while (rs2.next()) {
                        System.out.println("sub333");
                        String t_id2 = rs2.getString("teacher_id");
                        System.out.println(t_id2);
                        String t_fnme = rs2.getString("f_name");
                        String l_nme = rs2.getString("l_name");
                        String s_id = rs2.getString("salutation");
                        String name = s_id + "." + t_fnme + " " + l_nme;
                          ResultSet rs4=DB.getData("SELECT * FROM medium WHERE medium_id='"+m_id+"'");
                            while(rs4.next()){
                                String medium=rs4.getString("medium");
                                    ResultSet rs5=DB.getData("SELECT * FROM class_type WHERE class_type_id='"+cls_ty_id+"'");
                                        while(rs5.next()){
                                            String class_type=rs5.getString("class_type");
                                            
                                       

                        System.out.println("sub555");
//                                     Vector v=new Vector();
//                    

                        addToTable(tbl_teachertimetable, name, sub_nme, medium, class_type, cls_fee);
                                        }
                            }

                    }

                }
            }
            ResultSet rs4 = DB.getData("select * from teacher_reg inner join salutation on (teacher_reg.salutation_id=salutation.salutation_id)where teacher_id='" + txt_search.getText() + "'");
            while (rs4.next()) {
                System.out.println("sub333");
                String t_id2 = rs4.getString("teacher_id");
                System.out.println(t_id2);
                String t_fnme = rs4.getString("f_name");
                String l_nme = rs4.getString("l_name");
                String s_id = rs4.getString("salutation");
                String name = s_id + "." + t_fnme + " " + l_nme;

                lbl_teacher_id.setText(t_id2);
                lbl_tacherName.setText(name);
                loadSubject();

            }

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    //------------------------------------------------ Add to DataBase Finish -----------------------------------------------
    void mouseClicked() {
        try {
            DefaultTableModel td = (DefaultTableModel) tbl_teachertimetable.getModel();
            int i = tbl_teachertimetable.getSelectedRow();

            String cls_fee = (String) td.getValueAt(i, 2);

            txt_classfee.setText(cls_fee);

        } catch (Exception e) {
        }

    }

    //--------------------------------update---------------------------------------
    void update() {
        try {
            DefaultTableModel td = (DefaultTableModel) tbl_teachertimetable.getModel();
            int i = tbl_teachertimetable.getSelectedRow();

            String time_tid = (String) td.getValueAt(i, 3);
            String mediumID = "";
            String SubjectID = "";
            String fullSub = "";

            String grade = cmb_subject.getSelectedItem().toString().split("-")[0];
            String sub_nme = cmb_subject.getSelectedItem().toString().split("-")[1];
            String medium = cmb_subject.getSelectedItem().toString().split("-")[2];
            String cls_typ=cmb_classType.getSelectedItem().toString();
            fullSub = grade + "-" + sub_nme;
            
            ResultSet rs2 = DB.getData("SELECT * FROM medium WHERE medium='" + medium + "'");
                while (rs2.next()) {
                    mediumID = rs2.getString("medium_id");
                   String  medium1 =rs2.getString("medium");
                

                ResultSet rs3 = DB.getData("SELECT * FROM subject WHERE subject_name='" + fullSub + "' AND medium_id = '" + mediumID + "'");
                while (rs3.next()) {
                    SubjectID = rs3.getString("subject_id");
                

            DB.putData("update teacher_time_table set subject_id='" + SubjectID + "',class_fee='" + txt_classfee.getText() + "'where time_table_id='" + time_tid + "'");
            JOptionPane.showMessageDialog(rootPane, "Update Successful..!!!");
            log.info("Teacher Time table ID :" + time_tid + "Teacher Time Table data Updated at : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            System.out.println("adddto");
            Vector v = new Vector();
            v.add(lbl_tacherName.getText());
            v.add(sub_nme);
            v.add(medium1);
            v.add(cls_typ);
            v.add(txt_classfee.getText());
           

            DefaultTableModel teachertimetable = (DefaultTableModel) tbl_teachertimetable.getModel();
            teachertimetable.addRow(v);
            
                }
                }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("update error");
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

        pnl_lecturerDetails = new javax.swing.JPanel();
        lbl_id = new javax.swing.JLabel();
        lbl_nic = new javax.swing.JLabel();
        lbl_salutation = new javax.swing.JLabel();
        lbl_firstName = new javax.swing.JLabel();
        lbl_lastName = new javax.swing.JLabel();
        lbl_address = new javax.swing.JLabel();
        lbl_no = new javax.swing.JLabel();
        lbl_street1 = new javax.swing.JLabel();
        lbl_street2 = new javax.swing.JLabel();
        lbl_city = new javax.swing.JLabel();
        lbl_gender = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        lbl_qualifications = new javax.swing.JLabel();
        lbl_homeNumber = new javax.swing.JLabel();
        lbl_mobileNumber = new javax.swing.JLabel();
        lbl_status = new javax.swing.JLabel();
        txt_mobileNumber = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_homeNumber = new javax.swing.JTextField();
        txt_city = new javax.swing.JTextField();
        txt_street2 = new javax.swing.JTextField();
        txt_street1 = new javax.swing.JTextField();
        txt_no = new javax.swing.JTextField();
        txt_lastName = new javax.swing.JTextField();
        txt_firstName = new javax.swing.JTextField();
        txt_nic = new javax.swing.JTextField();
        txt_id = new javax.swing.JTextField();
        cmb_gender = new javax.swing.JComboBox();
        cmb_qualifications = new javax.swing.JComboBox();
        cmb_status = new javax.swing.JComboBox();
        cmb_salutation = new javax.swing.JComboBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        popup_menu = new javax.swing.JMenuItem();
        popup_help = new javax.swing.JMenuItem();
        popup_exit = new javax.swing.JMenuItem();
        pnl_background = new javax.swing.JPanel();
        pnl_header = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        lbl_userType = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnl_search = new javax.swing.JPanel();
        btn_Search = new javax.swing.JButton();
        lbl_searchBy = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();
        pnl_teacherTimetable = new javax.swing.JPanel();
        lbl_id1 = new javax.swing.JLabel();
        lbl_subject = new javax.swing.JLabel();
        cmb_subject = new javax.swing.JComboBox();
        lbl_teacherName = new javax.swing.JLabel();
        btn_add = new javax.swing.JButton();
        lbl_time1 = new javax.swing.JLabel();
        txt_classfee = new javax.swing.JTextField();
        lbl_teacher_id = new javax.swing.JLabel();
        lbl_tacherName = new javax.swing.JLabel();
        cmb_classType = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        pnl_table = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_teachertimetable = new javax.swing.JTable();
        btn_update = new javax.swing.JButton();
        lbl_background = new javax.swing.JLabel();

        pnl_lecturerDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lecturer Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 10), new java.awt.Color(0, 0, 0))); // NOI18N
        pnl_lecturerDetails.setOpaque(false);
        pnl_lecturerDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_lecturerDetailsMouseClicked(evt);
            }
        });

        lbl_id.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_id.setText("Employee ID:");

        lbl_nic.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_nic.setText("NIC:");

        lbl_salutation.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_salutation.setText("Salutation:");

        lbl_firstName.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_firstName.setText("First Name:");

        lbl_lastName.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_lastName.setText("Last Name:");

        lbl_address.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_address.setText("Address:-");

        lbl_no.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_no.setText("No:");

        lbl_street1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_street1.setText("Street 01:");

        lbl_street2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_street2.setText("Street 02:");

        lbl_city.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_city.setText("City:");

        lbl_gender.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_gender.setText("Gender:");

        lbl_email.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_email.setText("Email:");

        lbl_qualifications.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_qualifications.setText("Qualifications:");

        lbl_homeNumber.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_homeNumber.setText("Home Number:");

        lbl_mobileNumber.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_mobileNumber.setText("Mobile Number:");

        lbl_status.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_status.setText("Status:");

        txt_mobileNumber.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        txt_email.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        txt_homeNumber.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        txt_city.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        txt_street2.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        txt_street1.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        txt_no.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        txt_lastName.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        txt_firstName.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        txt_nic.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        txt_id.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        cmb_gender.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        cmb_gender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmb_qualifications.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        cmb_qualifications.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmb_status.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        cmb_status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmb_salutation.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        cmb_salutation.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnl_lecturerDetailsLayout = new javax.swing.GroupLayout(pnl_lecturerDetails);
        pnl_lecturerDetails.setLayout(pnl_lecturerDetailsLayout);
        pnl_lecturerDetailsLayout.setHorizontalGroup(
            pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_lecturerDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_lecturerDetailsLayout.createSequentialGroup()
                        .addComponent(lbl_status)
                        .addGap(91, 91, 91)
                        .addComponent(cmb_status, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_lecturerDetailsLayout.createSequentialGroup()
                        .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_mobileNumber)
                            .addComponent(lbl_email)
                            .addComponent(lbl_qualifications)
                            .addComponent(lbl_salutation))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmb_salutation, 0, 296, Short.MAX_VALUE)
                            .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_email, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                .addComponent(txt_mobileNumber)
                                .addComponent(txt_homeNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                .addComponent(txt_city, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                .addComponent(txt_street2, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                .addComponent(txt_street1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                .addComponent(txt_no, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                .addComponent(txt_lastName, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                .addComponent(txt_firstName, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                .addComponent(txt_nic, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                .addComponent(txt_id, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                .addComponent(cmb_gender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmb_qualifications, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(pnl_lecturerDetailsLayout.createSequentialGroup()
                        .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_id)
                            .addComponent(lbl_nic)
                            .addComponent(lbl_firstName)
                            .addComponent(lbl_lastName)
                            .addComponent(lbl_address)
                            .addGroup(pnl_lecturerDetailsLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_street1)
                                    .addComponent(lbl_no)
                                    .addComponent(lbl_street2)
                                    .addComponent(lbl_city)))
                            .addComponent(lbl_gender)
                            .addComponent(lbl_homeNumber))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(16, 16, 16))
        );
        pnl_lecturerDetailsLayout.setVerticalGroup(
            pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_lecturerDetailsLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_id)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_nic)
                    .addComponent(txt_nic, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_salutation)
                    .addComponent(cmb_salutation, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_firstName)
                    .addComponent(txt_firstName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_lastName)
                    .addComponent(txt_lastName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_gender)
                    .addComponent(cmb_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_address)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_no)
                    .addComponent(txt_no, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_street1)
                    .addComponent(txt_street1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_street2)
                    .addComponent(txt_street2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_city)
                    .addComponent(txt_city, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_homeNumber)
                    .addComponent(txt_homeNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_mobileNumber)
                    .addComponent(txt_mobileNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_email)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_qualifications)
                    .addComponent(cmb_qualifications, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_status)
                    .addComponent(cmb_status, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        popup_menu.setText("jMenuItem1");
        popup_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popup_menuActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popup_menu);

        popup_help.setText("jMenuItem2");
        popup_help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popup_helpActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popup_help);

        popup_exit.setText("jMenuItem3");
        popup_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popup_exitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popup_exit);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

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
        pnl_header.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 0, 16, 16));

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
        pnl_header.add(lbl_userType, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 30, 220, -1));

        lbl_username.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lbl_username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_username.setText("Username");
        pnl_header.add(lbl_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, 220, -1));

        lbl_date.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_date.setText("jLabel3");
        pnl_header.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, -1));

        lbl_time.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        lbl_time.setText("jLabel3");
        pnl_header.add(lbl_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 23, 100, -1));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Teacher Timetable");
        pnl_header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 340, 30));

        pnl_background.add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 50));

        pnl_search.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 10)))); // NOI18N
        pnl_search.setOpaque(false);
        pnl_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_searchMouseClicked(evt);
            }
        });

        btn_Search.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_Search.setText("Search");
        btn_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SearchActionPerformed(evt);
            }
        });

        lbl_searchBy.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_searchBy.setText("Search : ");

        txt_search.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_searchMouseClicked(evt);
            }
        });
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_searchLayout = new javax.swing.GroupLayout(pnl_search);
        pnl_search.setLayout(pnl_searchLayout);
        pnl_searchLayout.setHorizontalGroup(
            pnl_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_searchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_searchBy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_search, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        pnl_searchLayout.setVerticalGroup(
            pnl_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_searchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Search)
                    .addComponent(lbl_searchBy)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pnl_background.add(pnl_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 960, 80));

        pnl_teacherTimetable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Teachet Timetable", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 10), new java.awt.Color(0, 0, 0))); // NOI18N
        pnl_teacherTimetable.setOpaque(false);
        pnl_teacherTimetable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_teacherTimetableMouseClicked(evt);
            }
        });

        lbl_id1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_id1.setText("Teacher ID:");

        lbl_subject.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_subject.setText("Subject:");

        cmb_subject.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        cmb_subject.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_subjectKeyPressed(evt);
            }
        });

        lbl_teacherName.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_teacherName.setText("Teacher Name:");

        btn_add.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_add.setText("Add");
        btn_add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        lbl_time1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_time1.setText("Class Fee:");

        txt_classfee.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_classfee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_classfeeKeyReleased(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_classfeeKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_classfeeKeyTyped(evt);
            }
        });

        lbl_teacher_id.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        lbl_tacherName.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        cmb_classType.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel2.setText("Class Type :");

        javax.swing.GroupLayout pnl_teacherTimetableLayout = new javax.swing.GroupLayout(pnl_teacherTimetable);
        pnl_teacherTimetable.setLayout(pnl_teacherTimetableLayout);
        pnl_teacherTimetableLayout.setHorizontalGroup(
            pnl_teacherTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_teacherTimetableLayout.createSequentialGroup()
                .addGroup(pnl_teacherTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_teacherTimetableLayout.createSequentialGroup()
                        .addGroup(pnl_teacherTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_id1)
                            .addComponent(lbl_subject)
                            .addComponent(lbl_teacherName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(47, 47, 47)
                        .addGroup(pnl_teacherTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmb_subject, 0, 207, Short.MAX_VALUE)
                            .addComponent(lbl_teacher_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_tacherName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmb_classType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_teacherTimetableLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_teacherTimetableLayout.createSequentialGroup()
                        .addComponent(lbl_time1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnl_teacherTimetableLayout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(txt_classfee)))
                .addGap(15, 15, 15))
        );
        pnl_teacherTimetableLayout.setVerticalGroup(
            pnl_teacherTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_teacherTimetableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_teacherTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_id1)
                    .addComponent(lbl_teacher_id, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_teacherTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_teacherName)
                    .addComponent(lbl_tacherName, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_teacherTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_subject, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_subject))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(pnl_teacherTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmb_classType, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_teacherTimetableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_time1)
                    .addComponent(txt_classfee, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btn_add)
                .addGap(49, 49, 49))
        );

        pnl_background.add(pnl_teacherTimetable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 400, 310));

        pnl_table.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Time Table", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 10))); // NOI18N
        pnl_table.setOpaque(false);
        pnl_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_tableMouseClicked(evt);
            }
        });

        tbl_teachertimetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Teacher Name", "Subject", "Medium", "Class Type", "Class Fee"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_teachertimetable.getTableHeader().setReorderingAllowed(false);
        tbl_teachertimetable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_teachertimetableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_teachertimetable);

        btn_update.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_tableLayout = new javax.swing.GroupLayout(pnl_table);
        pnl_table.setLayout(pnl_tableLayout);
        pnl_tableLayout.setHorizontalGroup(
            pnl_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_tableLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_update, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnl_tableLayout.setVerticalGroup(
            pnl_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_tableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pnl_background.add(pnl_table, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 150, 560, 310));

        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnl_background.add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 470));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btn_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SearchActionPerformed
//        if (!(txt_search.getText().equals(""))) {
//            setSearch(txt_search.getText().trim());
//        }
        txt_classfee.setText("");
        clearTable(tbl_teachertimetable);
        addtoTable1();
        btn_update.setEnabled(false);
        btn_add.setEnabled(true);

    }//GEN-LAST:event_btn_SearchActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        addValuesToDB();
        txt_classfee.setText("");
        // addtoTable();
    }//GEN-LAST:event_btn_addActionPerformed

    private void tbl_teachertimetableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_teachertimetableMouseClicked
        mouseClicked();
        btn_add.setEnabled(false);
        btn_update.setEnabled(true);
    }//GEN-LAST:event_tbl_teachertimetableMouseClicked

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed

    }//GEN-LAST:event_txt_searchActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        update();
        btn_add.setEnabled(true);
        txt_classfee.setText("");
        clearTable(tbl_teachertimetable);
        addtoTable1();

    }//GEN-LAST:event_btn_updateActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        new Menu().setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void cmb_subjectKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_subjectKeyPressed

    }//GEN-LAST:event_cmb_subjectKeyPressed

    private void txt_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_searchMouseClicked
        txt_search.setText("TEC");
    }//GEN-LAST:event_txt_searchMouseClicked
    int i1 = 0;
    private void txt_classfeeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_classfeeKeyTyped
        if (Character.isDigit(evt.getKeyChar())) {
            if (!txt_classfee.getText().equals("")) {
                if (txt_classfee.getText().contains(".")) {
                    if (i1 >= 2) {
                        evt.consume();
                    } else {
                        i1++;
                        System.out.println("im in other");
                    }

                    System.out.println("this is i1: " + i1);
                } else {
                    i1 = 0;
                }
            }

        } else if (evt.getKeyChar() == '.') {
            if (txt_classfee.getText().contains(".")) {
                evt.consume();
            }
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txt_classfeeKeyTyped

    private void txt_classfeeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_classfeeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {

            if (txt_classfee.getText().contains(".")) {
                i1--;
            } else {
                i1 = 0;
            }
        }
    }//GEN-LAST:event_txt_classfeeKeyPressed

    private void txt_classfeeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_classfeeKeyReleased
        if (!txt_classfee.getText().equals("")) {
            if (txt_classfee.getText().contains(".")) {

            } else {
                i1 = 0;
            }
        }
    }//GEN-LAST:event_txt_classfeeKeyReleased

    private void popup_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_exitActionPerformed
        this.dispose();
    }//GEN-LAST:event_popup_exitActionPerformed

    private void popup_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_menuActionPerformed
        this.dispose();
      new Menu().setVisible(true);
    }//GEN-LAST:event_popup_menuActionPerformed

    private void popup_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_helpActionPerformed
         try {
            Desktop.getDesktop().open(new File("E:\\Syndicate\\Documentation\\General -Teacher Time Table.pdf"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_popup_helpActionPerformed

    private void pnl_backgroundMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_backgroundMouseClicked
         if (evt.getButton()==3) {
            jPopupMenu1.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_pnl_backgroundMouseClicked

    private void pnl_headerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_headerMouseClicked
         if (evt.getButton()==3) {
            jPopupMenu1.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_pnl_headerMouseClicked

    private void pnl_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_searchMouseClicked
        if (evt.getButton()==3) {
            jPopupMenu1.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_pnl_searchMouseClicked

    private void pnl_teacherTimetableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_teacherTimetableMouseClicked
         if (evt.getButton()==3) {
            jPopupMenu1.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_pnl_teacherTimetableMouseClicked

    private void pnl_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_tableMouseClicked
         if (evt.getButton()==3) {
            jPopupMenu1.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_pnl_tableMouseClicked

    private void pnl_lecturerDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_lecturerDetailsMouseClicked
       if (evt.getButton()==3) {
            jPopupMenu1.show(this, evt.getX() ,evt.getY());
        }
    }//GEN-LAST:event_pnl_lecturerDetailsMouseClicked

    /*
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
            java.util.logging.Logger.getLogger(TeacherTimetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TeacherTimetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TeacherTimetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TeacherTimetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TeacherTimetable().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Search;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox cmb_classType;
    private javax.swing.JComboBox cmb_gender;
    private javax.swing.JComboBox cmb_qualifications;
    private javax.swing.JComboBox cmb_salutation;
    private javax.swing.JComboBox cmb_status;
    private javax.swing.JComboBox cmb_subject;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_address;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_city;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_firstName;
    private javax.swing.JLabel lbl_gender;
    private javax.swing.JLabel lbl_homeNumber;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JLabel lbl_id1;
    private javax.swing.JLabel lbl_lastName;
    private javax.swing.JLabel lbl_mobileNumber;
    private javax.swing.JLabel lbl_nic;
    private javax.swing.JLabel lbl_no;
    private javax.swing.JLabel lbl_qualifications;
    private javax.swing.JLabel lbl_salutation;
    private javax.swing.JLabel lbl_searchBy;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JLabel lbl_street1;
    private javax.swing.JLabel lbl_street2;
    private javax.swing.JLabel lbl_subject;
    private javax.swing.JLabel lbl_tacherName;
    private javax.swing.JLabel lbl_teacherName;
    private javax.swing.JLabel lbl_teacher_id;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_time1;
    private javax.swing.JLabel lbl_userType;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_background;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_lecturerDetails;
    private javax.swing.JPanel pnl_search;
    private javax.swing.JPanel pnl_table;
    private javax.swing.JPanel pnl_teacherTimetable;
    private javax.swing.JMenuItem popup_exit;
    private javax.swing.JMenuItem popup_help;
    private javax.swing.JMenuItem popup_menu;
    private javax.swing.JTable tbl_teachertimetable;
    private javax.swing.JTextField txt_city;
    private javax.swing.JTextField txt_classfee;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_firstName;
    private javax.swing.JTextField txt_homeNumber;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_lastName;
    private javax.swing.JTextField txt_mobileNumber;
    private javax.swing.JTextField txt_nic;
    private javax.swing.JTextField txt_no;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_street1;
    private javax.swing.JTextField txt_street2;
    // End of variables declaration//GEN-END:variables

}
