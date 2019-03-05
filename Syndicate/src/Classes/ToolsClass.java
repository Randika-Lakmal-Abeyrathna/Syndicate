/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.toedter.calendar.JDateChooser;
import groovy.xml.dom.DOMCategory;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class ToolsClass {

    JDBC DB = new JDBC();

    private Pattern pattern;
    private Matcher matcher;
    // This is the Emil patten
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    // Class Constructor
    public ToolsClass() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    // -------------------------------------- Validations --------------------------------------------//
    //Email Validate Method
    public boolean Emailvalidate(final String email) {

        matcher = pattern.matcher(email);
        return matcher.matches();

    }
    // Only Numbers Validate Method
    public KeyListener NumbersOnly = new KeyAdapter() {

        @Override
        public void keyTyped(KeyEvent e) {
            if (!Character.isDigit(e.getKeyChar())) {
                e.consume();
            }
        }

    };
    //Only Letters Validate Method
    public KeyListener LetersOnly = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            if (Character.isDigit(e.getKeyChar())) {
                e.consume();
            }
        }
    };
    //Only Letters and Space Validate Method
    public KeyListener LetersAndSpaece = new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
            if (Character.isLetter(e.getKeyChar()) || e.getKeyChar() == ' ') {

            } else {
                e.consume();
            }
        }
    };
    //All
    public KeyListener all = new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
            e.consume();
        }
    };

    //------------------------------------------------------ End of Validation -----------------------------//
    //----------------------------------------- Table -----------------------------------------------//
    //Table Clear Method
    public void clearTable(JTable table) {
        int count = table.getRowCount();
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        for (int i = 0; i < count; i++) {
            dtm.removeRow(0);

        }
    }

    //Add Data to Any Table
    public void addToTable(JTable table, String... data) {
        DefaultTableModel dfm = (DefaultTableModel) table.getModel();
        Vector v = new Vector();
        int colCount = table.getColumnCount();
        for (int i = 0; i < colCount; i++) {
            v.add(data[i]);

        }
        dfm.addRow(v);
    }

    //get total values
    public int getTotalValue(JTable table, int row) {
        int rowCount = table.getRowCount();
        int totalValue = 0;
        if (rowCount > 0) {
            for (int i = 0; i < rowCount; i++) {
                totalValue = totalValue + Integer.parseInt(table.getValueAt(i, row).toString());
            }
        }

        return totalValue;

    }

    public double getTotalValueInDouble(JTable table, int row) {
        int rowCount = table.getRowCount();
        double totalValue = 00.00;
        if (rowCount > 0) {
            for (int i = 0; i < rowCount; i++) {
                totalValue = totalValue + Double.parseDouble(table.getValueAt(i, row).toString());
            }
        }

        return totalValue;

    }

    //-------------------------------------------------End of Table -----------------------------------------//
    //----------------------------------------Date & Time Start ----------------------------------------------//
    public String Date() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(d);
        return date;
    }

    public void time(final JLabel l) {

        new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Date d = new Date();
                SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
                String date = df.format(d);
                l.setText(date);

            }
        }).start();

    }

    //----------------------------------------Date & Time Finish ----------------------------------------------//
    //-----------------------------------------------Grabe Forcase ------------------------------------------//
    //Text Field to Text Field
    public void grabFocus(final JTextField first, final JTextField last) {
        first.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10 && !first.getText().equals("")) {
                    last.grabFocus();
                }
            }
        });
    }
    //Text Field to DateChooser
//        public void grabFocus(final JTextField first, final JDateChooser last){
//            first.addKeyListener(new KeyAdapter() {
//                public void keyReleased(KeyEvent e){
//                    if (e.getKeyCode()==10 && !first.getText().equals("")) {
//                        last.grabFocus();
//                    }
//                }
//            });
//        }

    // Text Field to Button
    public void grabFocus(final JTextField first, final JButton last) {
        first.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10 && !first.getText().equals("")) {
                    last.grabFocus();
                }
            }
        });
    }

    //Textfield to Combo Box
    public void grabFocus(final JTextField first, final JComboBox last) {
        first.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10 && !first.getText().equals("")) {
                    last.grabFocus();
                }
            }
        });
    }

    //Combo box to Text Field
    public void grabFocus(final JComboBox first, final JTextField last) {
        first.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    last.grabFocus();
                }
            }
        });
    }

    //Combo box to Combo box
    public void grabFocus(final JComboBox first, final JComboBox last) {
        first.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    last.grabFocus();
                }
            }
        });
    }
    //-----------------------------------------------End of Grabe Forcase ------------------------------------------//
    //------------------------------------------Genarate ID -------------------------------------------------------//

    //Check Value of ID
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

    //Value of ID
    public int getIdValue(String id) {
        char[] c = id.toCharArray();
        int i = c.length;
        id = "" + c[i - 4] + c[i - 3] + c[i - 2] + c[i - 1];
        i = Integer.parseInt(id);

        return i;
    }

    //Student Id 
    public void setStudentId(JLabel studentIdLabel) {
        try {
            ResultSet rs = DB.getData("SELECT student_id FROM student_reg ORDER BY student_id DESC LIMIT 1 ");
            if (rs.next()) {
                studentIdLabel.setText("STU" + generateId(getIdValue(rs.getString("student_id"))));
            } else {
                studentIdLabel.setText("STU0001");
            }
        } catch (Exception e) {
            System.out.println("In setStudentId method");
            e.printStackTrace();
        }

    }

    //Teacher Id
    public void setTeacherId(JLabel teacherIdLable) {
        try {
            ResultSet rs = DB.getData("SELECT teachetid FROM teacher_reg ORDER BY teacherid DESC LIMIT 1");
            if (rs.next()) {
                teacherIdLable.setText("TEA" + generateId(getIdValue(rs.getString("teacherid"))));
            } else {
                teacherIdLable.setText("TEA0001");
            }
        } catch (Exception e) {
            System.out.println("In setTeacherId method ");
            e.printStackTrace();
        }
    }

    //Employee Id
    public void setEmployeeId(JLabel employeeIdLabel) {
        try {
            ResultSet rs = DB.getData("SELECT emp_id FROM employee_reg ORDER BY emp_id DESC LIMIT 1");
            if (rs.next()) {
                employeeIdLabel.setText("EMP" + generateId(getIdValue(rs.getString("emp_id"))));
            } else {
                employeeIdLabel.setText("EMP0001");
            }
        } catch (Exception e) {
            System.out.println("In setEmployeeId method");
            e.printStackTrace();
        }
    }

//    //Guardion Id
//    public void setGurdion(JTextField gurdionid) {
//        try {
//            ResultSet rs = DB.getData("SELECT emp_id FROM employee_reg ORDER BY emp_id DESC LIMIT 1");
//            if (rs.next()) {
//                gurdionid.setText("EMP" + generateId(getIdValue(rs.getString("emp_id"))));
//            } else {
//                gurdionid.setText("EMP0001");
//            }
//        } catch (Exception e) {
//            System.out.println("In setEmployeeId method");
//            e.printStackTrace();
//        }
//    }
    //Recover Id
//   public String setRecoverId() {
//       String id=null;
//        try {
//            ResultSet rs = DB.getData("SELECT emp_id FROM employee_reg ORDER BY empl_id DESC LIMIT 1");
//            if (rs.next()) {
//              id= ("EMP" + generateId(getIdValue(rs.getString("emp_id"))));
//              return id;
//            } else {
//              id= "EMP0001";
//              return id;
//            }
//        } catch (Exception e) {
//            System.out.println("In setEmployeeId method");
//            e.printStackTrace();
//            return null;
//        }
//       
//    }
    //Student Registr Fee Invoice ID
    public void setStudentRegistrFeeInvoiceId(JLabel studentRegistrFeeInvoiceId) {
        try {

        } catch (Exception e) {
        }
    }

    // Salary Invoic ID
    public String setSalaryInvoiceId() {
        String id = null;
        try {
            ResultSet rs = DB.getData("SELECT salary_invoice_id FROM salary_invoice ORDER BY salary_invoice_id DESC LIMIT 1");
            if (rs.next()) {
                id = "SLI" + generateId(getIdValue(rs.getString("salary_invoice_id")));
                return id;
            } else {
                id = "SLI0001";
                return id;
            }
        } catch (Exception e) {
            System.out.println("In setEmployeeId method");
            e.printStackTrace();
            return null;
        }

    }

    //------------------------------------------Finish Genarate ID -------------------------------------------------------//
    // ---------------------------------------- Clear Components Start------------------------------------------------------------//     
    public void clearComponets(Component... com) {
        for (int i = 0; i < com.length; i++) {
            Component c = com[i];
            if (c instanceof JTextField) {
                JTextField textField = (JTextField) c;
                textField.setText("");
            } else if (c instanceof JTable) {
                JTable table = (JTable) c;
                clearTable(table);
            } else if (c instanceof JComboBox) {
                JComboBox comboBox = (JComboBox) c;
                comboBox.removeAllItems();
            } else if (c instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) c;
                radioButton.setSelected(false);
            } else if (c instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) c;
                radioButton.setSelected(false);
            } else if (c instanceof JLabel) {
                JLabel lable = (JLabel) c;
                lable.setText("");
            } else if (c instanceof JDateChooser) {
                JDateChooser date = (JDateChooser) c;
                date.setDate(null);
            } else if (c instanceof JTextArea) {
                JTextArea textArea = (JTextArea) c;
                textArea.setText("");
            } else if (c instanceof JPasswordField) {
                JPasswordField password = (JPasswordField) c;
                password.setText(null);
            }
        }

    }
    // ---------------------------------------- Clear Components Finish ------------------------------------------------------------//

//Hasitha EDIT        
//--------------------------------Guardian Id--------------------------------//
    public void setGuardianId(JTextField guardianIdTextfield) {
        try {
            ResultSet rs = DB.getData("SELECT guardion_id FROM guardion_details ORDER BY guardion_id DESC LIMIT 1");
            if (rs.next()) {
                guardianIdTextfield.setText(Integer.parseInt(rs.getString("guardion_id")) + 1 + "");
            } else {
                guardianIdTextfield.setText("1");
            }
        } catch (Exception e) {
            System.out.println("In setGuardianId method");
            e.printStackTrace();
        }
    }
//--------------------------------End of Genarating Guardian Id--------------------------------//

//--------------------------------Expence Id--------------------------------//
    public void setExpenceId(JTextField expenceIdTextfield) {
        try {
            ResultSet rs = DB.getData("SELECT expence_id FROM expence ORDER BY expence_id DESC LIMIT 1");
            if (rs.next()) {
                expenceIdTextfield.setText(Integer.parseInt(rs.getString("expence_id")) + 1 + "");
            } else {
                expenceIdTextfield.setText("1");
            }
        } catch (Exception e) {
            System.out.println("In setExpenceId method" + e);
            e.printStackTrace();
        }
    }
//--------------------------------End of Genarating Expence Id--------------------------------//

//--------------------------------Student Invoice Id--------------------------------//
    public void setStuPayInvoiceId(JTextField StuPayInvoiceId) {
        try {
            ResultSet rs = DB.getData("SELECT student_invoice_id FROM student_payment_invoice ORDER BY student_invoice_id DESC LIMIT 1");
            if (rs.next()) {
                StuPayInvoiceId.setText("SPI" + generateId(getIdValue(rs.getString("student_invoice_id"))));
            } else {
                StuPayInvoiceId.setText("SPI0001");
            }
        } catch (Exception e) {
            System.out.println("In StuPayInvoiceId method");
            e.printStackTrace();
        }
    }
//--------------------------------End of Student Invoice Id--------------------------------//

//--------------------------------Income Id--------------------------------//
    public int incomeId(int incomeID) {
        try {
            ResultSet rs = DB.getData("SELECT income_id FROM income ORDER BY income_id DESC LIMIT 1");
            if (rs.next()) {
                incomeID = Integer.parseInt(rs.getString("income_id") + 1);
            } else {
                incomeID = 1;
            }
        } catch (Exception e) {
            System.out.println("In StuPayInvoiceId method");
            e.printStackTrace();
        }
        return incomeID;
    }
//--------------------------------End of Income Id--------------------------------//

//--------------------------------Class Id--------------------------------//
    public int classId(int classId) {
        try {
            ResultSet rs = DB.getData("SELECT class_id FROM class_details ORDER BY class_id DESC LIMIT 1");
            if (rs.next()) {
                classId = Integer.parseInt(rs.getString("class_id")) + 1;
            } else {
                classId = 1;
            }
        } catch (Exception e) {
            System.out.println("In classId() method");
            e.printStackTrace();
        }
        return classId;
    }
//--------------------------------End of Class Id--------------------------------//
//// Pumika akka 
    //------------------------------------------Genarate Invoice Id-------------------------------------------------------//

    public String invoiceId() {
        String ID;
        try {
            ResultSet rs = DB.getData("SELECT student_invoice_id FROM student_payment_invoice ORDER BY student_invoice_id DESC LIMIT 1 ");
            if (rs.next()) {
                return ID = "SPI" + generateId(getIdValue(rs.getString("student_invoice_id")));
            } else {
                return ID = "SPI0001";
            }
        } catch (Exception e) {
            System.out.println("In invoiceId method" + e);
            e.printStackTrace();
        }
        return null;
    }
    //------------------------------------------End of Genarating invoice Id-------------------------------------------------------//

    //senani
    //----------------------------------------- Teacher Invoice ID Start------------------------------------------------
    public String setTeacherInvoiceId() {
        String id = null;
        try {
            ResultSet rs = DB.getData("SELECT teacher_payment_invoice_id FROM teacher_payment_invoice ORDER BY teacher_payment_invoice_id DESC LIMIT 1");
            if (rs.next()) {
                id = ("TPI" + generateId(getIdValue(rs.getString("teacher_payment_invoice_id"))));

                return id;

            } else {
                id = "TEA0001";
                return id;
            }
        } catch (Exception e) {
            System.out.println("In setTeacherId method ");
            e.printStackTrace();
            return id;
        }
    }
    //----------------------------------------- Teacher Invoice ID End------------------------------------------------

}
