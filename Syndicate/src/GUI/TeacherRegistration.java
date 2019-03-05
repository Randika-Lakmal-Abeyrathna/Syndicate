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
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;

/**
 *
 * @author Uditha N. Bandara
 */
public class TeacherRegistration extends javax.swing.JFrame {

    JDBC db = new JDBC();
    String pst = "";
    int value;

    String t_id;
    String f_name;
    String l_name;
    String nic;
    String no;
    String street;
    String street2;
    String city;
    String h_no;
    String m_no;
    String reg_date;
    String email;
    String status;
    String salutation;
    String salutation_id;

    String mr = "Mr";
    String mrs = "Mrs";
    String miss = "Miss";
    String rev = "Rev";
    
    String s="";
    String ac ="1";
    String matches;
    String mail1;
    
    

    /**
     * Creates new form StudentRegistration
     */
    
    Logger log=Logger.getLogger("Teacher Registration");
    public TeacherRegistration() {
        
        
        initComponents();
        Menu();
        refresh();
        
        this.setExtendedState(MAXIMIZED_BOTH);
        loadGnder();
        qualification();
        salutaton();
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
    private Pattern pattern;
    private Matcher matcher;
    // This is the Emil patten
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    // Class Constructor

    
    public boolean Emailvalidate(String mail1) {
        
            pattern = Pattern.compile(EMAIL_PATTERN);
        
        mail1 = txt_email.getText();
        matcher = pattern.matcher(mail1);
      
        return matcher.matches();

    }
   

    void loadGnder() {
        try {
            ResultSet rg = db.getData("SELECT * FROM gender");
            Vector v = new Vector();
            while (rg.next()) {
                String gen = rg.getString("gender");
                v.add(gen);
            }
            for (int i = 0; i < v.size(); i++) {
                cmb_gender.addItem(v.get(i));
            }
        } catch (Exception ex) {
            System.out.println("error in loading gender");
            System.out.println(ex);
        }
    }

    void salutaton() {
        try {
            ResultSet rs = db.getData("SELECT * FROM salutation");
            Vector v1 = new Vector();
            while (rs.next()) {
                String sal = rs.getString("salutation");
                v1.add(sal);
                
            }
            cmb_salutation.setModel(new DefaultComboBoxModel(v1));
//            for (int i = 0; i < v1.size(); i++) {
//                cmb_salutation.addItem(v1.get(i));
//            }
        } catch (Exception ex) {
            System.out.println("error in loading saltation");
            System.out.println(ex);
        }
    }
   

    void qualification() {
        try {
            ResultSet rq = db.getData("SELECT * FROM qualification");
            Vector v2 = new Vector();
            while (rq.next()) {
                String qual = rq.getString("qualification");
                v2.add(qual);
            }
            
            cmb_qualifications.setModel(new DefaultComboBoxModel(v2));
//            for (int i = 0; i < v2.size(); i++) {
//                cmb_qualifications.addItem(v2.get(i));
//            }
        } catch (Exception ex) {
            System.out.println("Error in loading qulification");
            System.out.println(ex);
        }
    }

    void addToTable(JTable tbJTable, String... data) {
        DefaultTableModel dtd = (DefaultTableModel) tbl_lecturerDetails.getModel();
        Vector v = new Vector();
        int colCount = tbl_lecturerDetails.getColumnCount();
        for (int i = 0; i < colCount; i++) {
            v.add(data[i]);

        }
        dtd.addRow(v);
    }

    void clearTable(JTable table) {
        int count = table.getRowCount();
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        for (int i = 0; i < count; i++) {
            dtm.removeRow(0);

        }
    }

    public int getIdValue(String id) {
        char[] c = id.toCharArray();
        int i = c.length;
        id = "" + c[i - 4] + c[i - 3] + c[i - 2] + c[i - 1];
        i = Integer.parseInt(id);

        return i;
    }

    public String generateId(int id) {

        String index = "";
        if (id < 9) {
            index = "000" + (id + 1);
        } else if (id < 99) {
            index = "00" + (id + 1);
        } else if (id < 999) {
            index = "0" + (id + 1);
        } else {
            index = "" + (id + 1);
        }
        return index;
    }

    void setTeacherId(JLabel lblJLabel) {
        try {
            ResultSet rs = db.getData("SELECT teacher_id FROM teacher_reg ORDER BY teacher_id DESC LIMIT 1");
            if (rs.next()) {
                lbl_tcher_id.setText("TEC" + generateId(getIdValue(rs.getString("teacher_id"))));
            } else {
                lbl_tcher_id.setText("TEC0001");
            }
        } catch (Exception e) {
            System.out.println("In setTeacherId method ");
            e.printStackTrace();
        }
     
        
    }
     void setSearchId(){
         String s = "TEC";
         txt_search.setText(s);
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
        cmb_gender = new javax.swing.JComboBox();
        cmb_qualifications = new javax.swing.JComboBox();
        cmb_status = new javax.swing.JComboBox();
        cmb_salutation = new javax.swing.JComboBox();
        lbl_tcher_id = new javax.swing.JLabel();
        pnl_header = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lbl_userType = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnl_search = new javax.swing.JPanel();
        txt_search = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        pnl_details = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_lecturerDetails = new javax.swing.JTable();
        btn_refresh = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
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

        pnl_lecturerDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lecturer Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 10), new java.awt.Color(0, 0, 0))); // NOI18N
        pnl_lecturerDetails.setOpaque(false);
        pnl_lecturerDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_lecturerDetailsMouseClicked(evt);
            }
        });

        lbl_id.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_id.setText("Lecturer ID:");

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
        txt_mobileNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_mobileNumberKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_mobileNumberKeyTyped(evt);
            }
        });

        txt_email.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_email.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_emailFocusLost(evt);
            }
        });
        txt_email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_emailKeyPressed(evt);
            }
        });

        txt_homeNumber.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_homeNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_homeNumberKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_homeNumberKeyTyped(evt);
            }
        });

        txt_city.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_city.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cityKeyPressed(evt);
            }
        });

        txt_street2.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_street2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_street2KeyPressed(evt);
            }
        });

        txt_street1.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_street1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_street1KeyPressed(evt);
            }
        });

        txt_no.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_no.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_noKeyPressed(evt);
            }
        });

        txt_lastName.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_lastName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_lastNameKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_lastNameKeyTyped(evt);
            }
        });

        txt_firstName.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_firstName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_firstNameKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_firstNameKeyTyped(evt);
            }
        });

        txt_nic.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_nic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nicKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nicKeyTyped(evt);
            }
        });

        cmb_gender.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        cmb_gender.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_genderKeyPressed(evt);
            }
        });

        cmb_qualifications.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        cmb_qualifications.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_qualificationsKeyPressed(evt);
            }
        });

        cmb_status.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        cmb_status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Active", "Inactive" }));

        cmb_salutation.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        cmb_salutation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_salutationActionPerformed(evt);
            }
        });
        cmb_salutation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_salutationKeyPressed(evt);
            }
        });

        lbl_tcher_id.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lbl_tcher_id.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_tcher_idMouseClicked(evt);
            }
        });
        lbl_tcher_id.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lbl_tcher_idFocusGained(evt);
            }
        });

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
                            .addComponent(lbl_salutation)
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
                        .addGap(18, 18, 18)
                        .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmb_salutation, 0, 296, Short.MAX_VALUE)
                            .addGroup(pnl_lecturerDetailsLayout.createSequentialGroup()
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
                                    .addComponent(cmb_gender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmb_qualifications, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_tcher_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(16, 16, 16))
        );
        pnl_lecturerDetailsLayout.setVerticalGroup(
            pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_lecturerDetailsLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_lecturerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_id)
                    .addComponent(lbl_tcher_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(37, Short.MAX_VALUE))
        );

        getContentPane().add(pnl_lecturerDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 128, 480, 640));

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

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Teacher Registration");
        pnl_header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 340, 30));

        getContentPane().add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 50));

        pnl_search.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 10), new java.awt.Color(0, 0, 0))); // NOI18N
        pnl_search.setOpaque(false);
        pnl_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_searchMouseClicked(evt);
            }
        });

        txt_search.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txt_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_searchMouseClicked(evt);
            }
        });

        btn_search.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_search.setText("Search");
        btn_search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_searchLayout = new javax.swing.GroupLayout(pnl_search);
        pnl_search.setLayout(pnl_searchLayout);
        pnl_searchLayout.setHorizontalGroup(
            pnl_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_searchLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 1190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_search, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
        );
        pnl_searchLayout.setVerticalGroup(
            pnl_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_searchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(pnl_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1366, 75));

        pnl_details.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 10), new java.awt.Color(0, 0, 0))); // NOI18N
        pnl_details.setOpaque(false);
        pnl_details.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_detailsMouseClicked(evt);
            }
        });

        tbl_lecturerDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lecturer ID", "NIC", "Name", "Address", "Home Number", "Mobile Number", "Email", "Reg. Date", "Status"
            }
        ));
        tbl_lecturerDetails.getTableHeader().setReorderingAllowed(false);
        tbl_lecturerDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_lecturerDetailsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_lecturerDetails);

        btn_refresh.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_refresh.setText("Refresh");
        btn_refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        btn_update.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_update.setText("Update");
        btn_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_save.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btn_save.setText("Save");
        btn_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_detailsLayout = new javax.swing.GroupLayout(pnl_details);
        pnl_details.setLayout(pnl_detailsLayout);
        pnl_detailsLayout.setHorizontalGroup(
            pnl_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_detailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
                    .addGroup(pnl_detailsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_detailsLayout.setVerticalGroup(
            pnl_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_detailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_refresh)
                    .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_save))
                .addContainerGap())
        );

        getContentPane().add(pnl_details, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 128, 886, 640));

        lbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N
        lbl_background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 768));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(d);

        if (lbl_tcher_id.getText().isEmpty() || txt_firstName.getText().isEmpty() || txt_lastName.getText().isEmpty() || txt_nic.getText().isEmpty() || txt_no.getText().isEmpty() || txt_street1.getText().isEmpty() || txt_street2.getText().isEmpty() || txt_city.getText().isEmpty() || txt_homeNumber.getText().isEmpty() || txt_mobileNumber.getText().isEmpty() || txt_email.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Fields can't be empty !");
        } else {
            try {

//---------------------------------------------COMBOBOX STRING--------------------------------------------------------------------                
                String gender = cmb_gender.getSelectedItem().toString();
                String s2="Male";
                int i1;
 
                        if(s.equals(s2)){
                                    
                                     i1 = 1;
               
                         }else{
                  
                                    i1=2;
                }
                System.out.println(gender);
                
                
                String s = cmb_status.getSelectedItem().toString();
                String s1="Active";
                int i;
 
                        if(s.equals(s1)){
                                    
                                     i = 1;
               
                         }else{
                  
                                    i=0;
                }
               
                
                
                String qualification = cmb_qualifications.getSelectedItem().toString();
                String salutation = cmb_salutation.getSelectedItem().toString();
                String gen = "";
                String sal = "";
                String quali = "";

                ResultSet rs1 = db.getData("select gender_id from gender where gender='" + gender + "'");
                if (rs1.next()) {
                    gen = rs1.getString("gender_id");
                }
                ResultSet rs2 = db.getData("select salutation_id from salutation where salutation='" + salutation + "'");
                if (rs2.next()) {
                    sal = rs2.getString("salutation_id");
                }
                ResultSet rs3 = db.getData("select qualification_id from qualification where qualification='" + qualification + "'");
                if (rs3.next()) {
                    quali = rs3.getString("qualification_id");
                }

                db.putData("INSERT INTO teacher_reg(teacher_id,nic,f_name,l_name,email,mobile_no,home_no,no,street1,street2,city,date,status,qualification_id,salutation_id,gender_id)VALUES('" + lbl_tcher_id.getText() + "','" + txt_nic.getText() + "','" + txt_firstName.getText() + "','" + txt_lastName.getText() + "','" + txt_email.getText() + "','" + txt_mobileNumber.getText() + "','" + txt_homeNumber.getText() + "','" + txt_no.getText() + "','" + txt_street1.getText() + "','" + txt_street2.getText() + "','" + txt_city.getText() + "','" + date + "','" + i + "','" + quali + "','" + sal + "','" + i1 + "')");
                JOptionPane.showMessageDialog(rootPane, "Successfully Saved..!");
                log.info("Teacher ID : "+lbl_tcher_id.getText()+" Teacher Registerd at :"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            } catch (Exception e) {
               
                System.out.println(e);
                e.printStackTrace();
            }
        }
        String dati = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Vector v = new Vector();
        v.add(lbl_tcher_id.getText());
        v.add(txt_nic.getText());
        v.add(txt_firstName.getText() + " " + txt_lastName.getText());
        v.add(txt_no.getText() + "," + txt_street1.getText() + "," + txt_street2.getText() + "," + txt_city.getText());
        v.add(txt_homeNumber.getText());
        v.add(txt_mobileNumber.getText());
        v.add(txt_email.getText());
        v.add(dati);
        String status = cmb_status.toString();
        v.add(cmb_status.getSelectedItem());

        DefaultTableModel teachertimetable = (DefaultTableModel) tbl_lecturerDetails.getModel();
        teachertimetable.addRow(v);
        //tdt();

        //-------------------------------------clear textfields---------------------------------------------------------//
        lbl_tcher_id.setText("");
        txt_firstName.setText("");
        txt_lastName.setText("");
        txt_nic.setText("");
        txt_no.setText("");
        txt_street1.setText("");
        txt_street2.setText("");
        txt_city.setText("");
        txt_homeNumber.setText("");
        txt_mobileNumber.setText("");
        txt_email.setText("");
        setTeacherId(lbl_tcher_id);
    }//GEN-LAST:event_btn_saveActionPerformed
    
    private void txt_homeNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_homeNumberKeyTyped
        char teacherHmeNo = evt.getKeyChar();
        if (!(Character.isDigit(teacherHmeNo) || teacherHmeNo == KeyEvent.VK_BACK_SPACE || teacherHmeNo == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }

        int l=txt_homeNumber.getText().length();
        if(l==9){
        
            evt.consume();
        }
         
        
     
        
    }//GEN-LAST:event_txt_homeNumberKeyTyped

    private void txt_mobileNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_mobileNumberKeyTyped
        char teacherMbileNo = evt.getKeyChar();       //teacher mobile number-textfield
        if (!(Character.isDigit(teacherMbileNo)) || teacherMbileNo == KeyEvent.VK_BACK_SPACE || teacherMbileNo == KeyEvent.VK_DELETE) {
            getToolkit().beep();
            evt.consume();
        }
         char c = evt.getKeyChar();
        if (Character.isAlphabetic(c)) {
            evt.consume();
            
        }
        int l=txt_mobileNumber.getText().length();
        if(l==9){
        
            evt.consume();
        }
         
     
        
    }//GEN-LAST:event_txt_mobileNumberKeyTyped

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed

        try {
            Date d = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(d);

            String gender = cmb_gender.getSelectedItem().toString();
            String s2="Male";
                int i1;
 
                        if(s.equals(s2)){
                                    
                                     i1 = 1;
               
                         }else{
                  
                                    i1=2;
                }
            String s=cmb_status.getSelectedItem().toString();
            
            String s1="Active";
                int i;
 
                        if(s.equals(s1)){
                                    
                                     i =1;
               
                         }else{
                  
                                    i=0;
                }
               
            String qualification = cmb_qualifications.getSelectedItem().toString();
            String salutation = cmb_salutation.getSelectedItem().toString();
            String gen = "";
            String sal = "";
            String quali = "";

            ResultSet rs1 = db.getData("select gender_id from gender where gender='" + gender + "'");
            if (rs1.next()) {
                gen = rs1.getString("gender_id");
            }
            ResultSet rs2 = db.getData("select salutation_id from salutation where salutation='" + salutation + "'");
            if (rs2.next()) {
                sal = rs2.getString("salutation_id");
            }
            ResultSet rs3 = db.getData("select qualification_id from qualification where qualification='" + qualification + "'");
            if (rs3.next()) {
                quali = rs3.getString("qualification_id");
            }

            db.putData("update teacher_reg set nic='" + txt_nic.getText() + "',f_name='" + txt_firstName.getText() + "',l_name='" + txt_lastName.getText() + "',email='" + txt_email.getText() + "',mobile_no='" + txt_mobileNumber.getText() + "',home_no='" + txt_homeNumber.getText() + "',no='" + txt_no.getText() + "',street1='" + txt_street1.getText() + "',street2='" + txt_street2.getText() + "',city='" + txt_city.getText() + "',date='" + date + "',status='" + i + "',qualification_id='" + quali + "',salutation_id='" + sal + "',gender_id='" + gen + "'where teacher_id='"+lbl_tcher_id.getText()+"'");
            JOptionPane.showMessageDialog(rootPane, "Update Successful..!!!");
            log.info("Teacher ID : "+lbl_tcher_id.getText()+" Teacher Deatails Updated at :"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            cmb_gender.setEnabled(true);
        } catch (Exception e) {
            System.out.println("errr-update-teacher reg");
            System.out.println(e);
            e.printStackTrace();
        }
        refresh();
        btn_save.setEnabled(true);
        
        
        
        
        
        lbl_tcher_id.setText("");
        txt_firstName.setText("");
        txt_lastName.setText("");
        txt_nic.setText("");
        txt_no.setText("");
        txt_street1.setText("");
        txt_street2.setText("");
        txt_city.setText("");
        txt_homeNumber.setText("");
        txt_mobileNumber.setText("");
        txt_email.setText("");

        
    }//GEN-LAST:event_btn_updateActionPerformed

    private void txt_nicKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nicKeyTyped
//       char c = evt.getKeyChar();
//       int i = txt_nic.getText().length();
//            if(Character.isDigit(c)){
//                System.out.println("nic2");
//            }else if(i==9){
//                System.out.println("nic");
//            }
//            evt.consume();
//             
          char nic = evt.getKeyChar();
        if (!(Character.isDigit(nic) || nic == KeyEvent.VK_BACK_SPACE || nic == KeyEvent.VK_DELETE || nic==KeyEvent.VK_V || nic == KeyEvent.VK_ENTER)) {
            getToolkit().beep();
            evt.consume();
        }

        int l=txt_nic.getText().length();
        if(l==12){
           
            evt.consume();
        }
       
    }//GEN-LAST:event_txt_nicKeyTyped

    private void txt_firstNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_firstNameKeyTyped

    }//GEN-LAST:event_txt_firstNameKeyTyped

    private void txt_lastNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_lastNameKeyTyped
       
    }//GEN-LAST:event_txt_lastNameKeyTyped

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
   //------------------------------------------------------------------------search------------------------------------//   
        clearTable(tbl_lecturerDetails);
        try {
          
            System.out.println("searchh1"); 
            ResultSet rs=db.getData("select * from teacher_reg inner join salutation on (teacher_reg.salutation_id=salutation.salutation_id)where teacher_id='"+txt_search.getText()+"'");
            while (rs.next()) {
                System.out.println("search2");
                t_id = rs.getString("teacher_id");
                f_name = rs.getString("f_name");
                l_name = rs.getString("l_name");
                nic = rs.getString("nic");
                no = rs.getString("no");
                street = rs.getString("street1");
                street2 = rs.getString("street2");
                city = rs.getString("city");
                h_no = rs.getString("home_no");
                m_no = rs.getString("mobile_no");
                reg_date = rs.getString("date");
                email = rs.getString("email");
                status = rs.getString("status");
                
                salutation = rs.getString("salutation");
                salutation_id = rs.getString("salutation_id");
                String se="1";
                    System.out.println("search3");
                        if(status.equals(se)){
                            String s = "Active";
                            System.out.println("stateeeeeeee23457");
                             addToTable(tbl_lecturerDetails, t_id, nic, salutation + "." + f_name + " " + l_name, no + "," + street + "," + street2 + "," + city, h_no, m_no, email, reg_date, s);
                            
                            System.out.println("stateeeeeeeeee");
                        }else{
                            String s= "Inactive";
                             addToTable(tbl_lecturerDetails, t_id, nic, salutation + "." + f_name + " " + l_name, no + "," + street + "," + street2 + "," + city, h_no, m_no, email, reg_date, s);
                           System.out.println("state2");
                        }
                        System.out.println("s");
                System.out.println("Check 01");
               
            }
            
            txt_search.setText("");
        } catch (Exception e) {
            System.out.println("error in load to lable");
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_btn_searchActionPerformed

    private void tbl_lecturerDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_lecturerDetailsMouseClicked

        try {
            DefaultTableModel td = (DefaultTableModel) tbl_lecturerDetails.getModel();
            int i = tbl_lecturerDetails.getSelectedRow();

            String t_id = (String) td.getValueAt(i, 0);
            String nic = (String) td.getValueAt(i, 1);
            String name[] = td.getValueAt(i, 2).toString().split(" ");
            String fnam[] = name[0].split(Pattern.quote("."));
            String nm = fnam[1];
            String lnam = name[1];
            String Adress[] = td.getValueAt(i, 3).toString().split(",");
            String no = Adress[0];
            String street1 = Adress[1];
            String Street2 = Adress[2];
            String city = Adress[3];
            String h_no = (String) td.getValueAt(i, 4);
            String m_no = (String) td.getValueAt(i, 5);
            String email = (String) td.getValueAt(i, 6);

            lbl_tcher_id.setText(t_id);
            txt_nic.setText(nic);
            txt_firstName.setText(nm);
            txt_lastName.setText(lnam);
            txt_no.setText(no);
            txt_street1.setText(street1);
            txt_street2.setText(Street2);
            txt_city.setText(city);
            txt_homeNumber.setText(h_no);
            txt_mobileNumber.setText(m_no);
            txt_email.setText(email);
        } catch (Exception e) {
        }
        cmb_gender.setEnabled(false);
        btn_save.setEnabled(false);

    }//GEN-LAST:event_tbl_lecturerDetailsMouseClicked

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
       refresh();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(true);        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_searchMouseClicked
      setSearchId();
    }//GEN-LAST:event_txt_searchMouseClicked

    private void lbl_tcher_idMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_tcher_idMouseClicked
        
    }//GEN-LAST:event_lbl_tcher_idMouseClicked

    private void lbl_tcher_idFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lbl_tcher_idFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_tcher_idFocusGained

    private void txt_nicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nicKeyPressed
        int c =  evt.getKeyCode();
        if(c==10){
             String concat = txt_nic.getText().concat("v");
             txt_nic.setText(concat); 
            cmb_salutation.grabFocus();
            
        }
   
    }//GEN-LAST:event_txt_nicKeyPressed

    private void txt_homeNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_homeNumberKeyPressed
        int c =  evt.getKeyCode();
        if(c==10){
            txt_mobileNumber
                    .grabFocus();
            
        }

    }//GEN-LAST:event_txt_homeNumberKeyPressed

    private void txt_mobileNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_mobileNumberKeyPressed
    int c =  evt.getKeyCode();
        if(c==10){
            txt_email.grabFocus();
            
        }
    }//GEN-LAST:event_txt_mobileNumberKeyPressed

    private void cmb_salutationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_salutationKeyPressed
       int c =  evt.getKeyCode();
        if(c==10){
            txt_firstName.grabFocus();
            
        }
    }//GEN-LAST:event_cmb_salutationKeyPressed

    private void txt_firstNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_firstNameKeyPressed
       int c =  evt.getKeyCode();
        if(c==10){
            txt_lastName.grabFocus();
            
        }
    }//GEN-LAST:event_txt_firstNameKeyPressed

    private void txt_lastNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_lastNameKeyPressed
       int c =  evt.getKeyCode();
        if(c==10){
            cmb_gender.grabFocus();
            
        }
    }//GEN-LAST:event_txt_lastNameKeyPressed

    private void cmb_genderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_genderKeyPressed
        int c =  evt.getKeyCode();
        if(c==10){
            txt_no.grabFocus();
            
        }
    }//GEN-LAST:event_cmb_genderKeyPressed

    private void txt_noKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_noKeyPressed
        int c =  evt.getKeyCode();
        if(c==10){
            txt_street1.grabFocus();
            
        }
    }//GEN-LAST:event_txt_noKeyPressed

    private void txt_street1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_street1KeyPressed
        int c =  evt.getKeyCode();
        if(c==10){
            txt_street2.grabFocus();
            
        }
    }//GEN-LAST:event_txt_street1KeyPressed

    private void txt_street2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_street2KeyPressed
       
        int c =  evt.getKeyCode();
        if(c==10){
            txt_city.grabFocus();
            
        }
    }//GEN-LAST:event_txt_street2KeyPressed

    private void txt_cityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cityKeyPressed
        int c =  evt.getKeyCode();
        if(c==10){
            txt_homeNumber.grabFocus();
            
        }
    }//GEN-LAST:event_txt_cityKeyPressed

    private void txt_emailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_emailKeyPressed
        int c =  evt.getKeyCode();
        if(c==10){
          String mail =  txt_email.getText();
            String s1 = matches;
            
           if(Emailvalidate(matches)){
               cmb_qualifications.grabFocus();
           }else{
               JOptionPane.showMessageDialog(rootPane,"Rechek Email Adress");
           }
            
        }
    }//GEN-LAST:event_txt_emailKeyPressed

    private void cmb_qualificationsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_qualificationsKeyPressed
       
        int c =  evt.getKeyCode();
        if(c==10){
            cmb_status.grabFocus();
            
        }
    }//GEN-LAST:event_cmb_qualificationsKeyPressed

    private void txt_emailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_emailFocusLost
       boolean b= new ToolsClass().Emailvalidate(txt_email.getText());
        if (!b) {
            JOptionPane.showMessageDialog(this, "Invalid E-mail");
            txt_email.grabFocus();
        }
    }//GEN-LAST:event_txt_emailFocusLost

    private void cmb_salutationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_salutationActionPerformed
           try {
            String s1= "Mr";
            String s2= "Mrs";
            String s3= "Miss";
            String cmb=cmb_salutation.getSelectedItem().toString();
               if (cmb.equals(s1)) {
                   cmb_gender.setSelectedItem("Male");
                   cmb_gender.setEnabled(false);
                   
               } else if(cmb.equals(s2) || cmb.equals(s3)) {
                   cmb_gender.setSelectedItem("Female");
                   cmb_gender.setEnabled(false);
               }else{
                   cmb_gender.setEnabled(true);
               }
        } catch (Exception e) {
            e.printStackTrace();
               System.out.println("salutation");
        }
    }//GEN-LAST:event_cmb_salutationActionPerformed

    private void popup_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_menuActionPerformed
        this.dispose();
        new Menu().setVisible(true);
    }//GEN-LAST:event_popup_menuActionPerformed

    private void popup_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_helpActionPerformed
       try {
            Desktop.getDesktop().open(new File("E:\\Syndicate\\Documentation\\General – Teacher Registration.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_popup_helpActionPerformed

    private void popup_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_exitActionPerformed
       this.dispose();
    }//GEN-LAST:event_popup_exitActionPerformed

    private void pnl_lecturerDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_lecturerDetailsMouseClicked
       if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_lecturerDetailsMouseClicked

    private void pnl_headerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_headerMouseClicked
      if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_headerMouseClicked

    private void pnl_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_searchMouseClicked
       if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_searchMouseClicked

    private void pnl_detailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_detailsMouseClicked
     if (evt.getButton() == 3) {
            popupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_pnl_detailsMouseClicked

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
            java.util.logging.Logger.getLogger(TeacherRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TeacherRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TeacherRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TeacherRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TeacherRegistration().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox cmb_gender;
    private javax.swing.JComboBox cmb_qualifications;
    private javax.swing.JComboBox cmb_salutation;
    private javax.swing.JComboBox cmb_status;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel lbl_lastName;
    private javax.swing.JLabel lbl_mobileNumber;
    private javax.swing.JLabel lbl_nic;
    private javax.swing.JLabel lbl_no;
    private javax.swing.JLabel lbl_qualifications;
    private javax.swing.JLabel lbl_salutation;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JLabel lbl_street1;
    private javax.swing.JLabel lbl_street2;
    private javax.swing.JLabel lbl_tcher_id;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_userType;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_details;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_lecturerDetails;
    private javax.swing.JPanel pnl_search;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem popup_exit;
    private javax.swing.JMenuItem popup_help;
    private javax.swing.JMenuItem popup_menu;
    private javax.swing.JTable tbl_lecturerDetails;
    private javax.swing.JTextField txt_city;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_firstName;
    private javax.swing.JTextField txt_homeNumber;
    private javax.swing.JTextField txt_lastName;
    private javax.swing.JTextField txt_mobileNumber;
    private javax.swing.JTextField txt_nic;
    private javax.swing.JTextField txt_no;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_street1;
    private javax.swing.JTextField txt_street2;
    // End of variables declaration//GEN-END:variables

   // private void clearText() {
    //   System.out.println("error--cleartext");
    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    private void tdt() {
        System.out.println("error tdt");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void voidloadtable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setTeacherId(JTextField txt_id) {

        try {
            ResultSet rs = db.getData("SELECT teacher_id FROM teacher_reg ORDER BY teacher_id DESC LIMIT 1");
            if (rs.next()) {
                txt_id.setText("TEC" + generateId(getIdValue(rs.getString("teacher_id"))));
            } else {
                txt_id.setText("");
            }
        } catch (Exception e) {
            System.out.println("In setTeacherId method ");
            e.printStackTrace();
        }
    }
    
    private void cleartexfields(){
        lbl_tcher_id.setText("");
        txt_firstName.setText("");
        txt_lastName.setText("");
        txt_nic.setText("");
        txt_no.setText("");
        txt_street1.setText("");
        txt_street2.setText("");
        txt_city.setText("");
        txt_homeNumber.setText("");
        txt_mobileNumber.setText("");
        txt_email.setText("");

    }
   private  void refresh(){
        try {
            cmb_gender.setEnabled(true);
            txt_search.grabFocus();
            cleartexfields();
            clearTable(tbl_lecturerDetails);
            setTeacherId(lbl_tcher_id);
            qualification();
            salutaton();
            ResultSet rs=db.getData("select * from teacher_reg inner join salutation on (teacher_reg.salutation_id=salutation.salutation_id)");
                 while (rs.next()) {
                t_id = rs.getString("teacher_id");
                f_name = rs.getString("f_name");
                l_name = rs.getString("l_name");
                nic = rs.getString("nic");
                no = rs.getString("no");
                street = rs.getString("street1");
                street2 = rs.getString("street2");
                city = rs.getString("city");
                h_no = rs.getString("home_no");
                m_no = rs.getString("mobile_no");
                reg_date = rs.getString("date");
                email = rs.getString("email");
                status = rs.getString("status");
                salutation = rs.getString("salutation");
                salutation_id = rs.getString("salutation_id");
                        System.out.println("dfg");
                        String se ="1"; 
                     System.out.println("errttt");
                        if(status.equals(se)){
                            
                                String s= "Active";
                            System.out.println("state");
                                 System.out.println("Check 01");
                                 addToTable(tbl_lecturerDetails, t_id, nic, salutation + "." + f_name + " " + l_name, no + "," + street + "," + street2 + "," + city, h_no, m_no, email, reg_date, s);
                
                        }else{
                            System.out.println("state4");
                            String s= "Inactive";
                            System.out.println("state");
                                 System.out.println("Check 01");
                                 addToTable(tbl_lecturerDetails, t_id, nic, salutation + "." + f_name + " " + l_name, no + "," + street + "," + street2 + "," + city, h_no, m_no, email, reg_date, s);
                
                            
                        }
            }
            
        } catch (Exception e) {
            System.out.println("error refresh");
            System.out.println(e);
            e.printStackTrace();
        }
        btn_save.setEnabled(true);
        
   }
  
}


