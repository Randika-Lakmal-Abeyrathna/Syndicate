/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author USER
 */
public class ToolsClassTest {
    
    public ToolsClassTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Emailvalidate method, of class ToolsClass.
     */
    @Test
    public void testEmailvalidate() {
        System.out.println("Emailvalidate");
        String email = "";
        String test1="randika.help@gmail.com";
        String test2="coolbreze071@gmail.com";
        String test3="@gmail.com";
        String test4="randika.help.com";
        ToolsClass instance = new ToolsClass();
        boolean expResult = false;
        boolean result = instance.Emailvalidate(test1);
        boolean result1 = instance.Emailvalidate(test2);
        boolean result2 = instance.Emailvalidate(test3);
        boolean result3 = instance.Emailvalidate(test4);
        assertEquals(expResult, result);
        assertEquals(expResult, result1);
        assertEquals(expResult, result2);
        assertEquals(expResult, result3);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of clearTable method, of class ToolsClass.
     */
    @Test
    public void testClearTable() {
        System.out.println("clearTable");
        JTable table = null;
        ToolsClass instance = new ToolsClass();
        instance.clearTable(table);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addToTable method, of class ToolsClass.
     */
    @Test
    public void testAddToTable() {
        System.out.println("addToTable");
        JTable table = null;
        String[] data = null;
        ToolsClass instance = new ToolsClass();
        instance.addToTable(table, data);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalValue method, of class ToolsClass.
     */
    @Test
    public void testGetTotalValue() {
        System.out.println("getTotalValue");
        JTable table = null;
        int row = 0;
        ToolsClass instance = new ToolsClass();
        int expResult = 0;
        int result = instance.getTotalValue(table, row);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalValueInDouble method, of class ToolsClass.
     */
    @Test
    public void testGetTotalValueInDouble() {
        System.out.println("getTotalValueInDouble");
        JTable table = null;
        int row = 0;
        ToolsClass instance = new ToolsClass();
        double expResult = 0.0;
        double result = instance.getTotalValueInDouble(table, row);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Date method, of class ToolsClass.
     */
    @Test
    public void testDate() {
        System.out.println("Date");
        ToolsClass instance = new ToolsClass();
        String expResult = "";
        String result = instance.Date();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of time method, of class ToolsClass.
     */
    @Test
    public void testTime() {
        System.out.println("time");
        JLabel l = null;
        ToolsClass instance = new ToolsClass();
        instance.time(l);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of grabFocus method, of class ToolsClass.
     */
    @Test
    public void testGrabFocus_JTextField_JTextField() {
        System.out.println("grabFocus");
        JTextField first = null;
        JTextField last = null;
        ToolsClass instance = new ToolsClass();
        instance.grabFocus(first, last);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of grabFocus method, of class ToolsClass.
     */
    @Test
    public void testGrabFocus_JTextField_JButton() {
        System.out.println("grabFocus");
        JTextField first = null;
        JButton last = null;
        ToolsClass instance = new ToolsClass();
        instance.grabFocus(first, last);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of grabFocus method, of class ToolsClass.
     */
    @Test
    public void testGrabFocus_JTextField_JComboBox() {
        System.out.println("grabFocus");
        JTextField first = null;
        JComboBox last = null;
        ToolsClass instance = new ToolsClass();
        instance.grabFocus(first, last);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of grabFocus method, of class ToolsClass.
     */
    @Test
    public void testGrabFocus_JComboBox_JTextField() {
        System.out.println("grabFocus");
        JComboBox first = null;
        JTextField last = null;
        ToolsClass instance = new ToolsClass();
        instance.grabFocus(first, last);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of grabFocus method, of class ToolsClass.
     */
    @Test
    public void testGrabFocus_JComboBox_JComboBox() {
        System.out.println("grabFocus");
        JComboBox first = null;
        JComboBox last = null;
        ToolsClass instance = new ToolsClass();
        instance.grabFocus(first, last);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateId method, of class ToolsClass.
     */
    @Test
    public void testGenerateId() {
        System.out.println("generateId");
        int id = 0;
        ToolsClass instance = new ToolsClass();
        String expResult = "";
        String result = instance.generateId(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIdValue method, of class ToolsClass.
     */
    @Test
    public void testGetIdValue() {
        System.out.println("getIdValue");
        String id = "";
        ToolsClass instance = new ToolsClass();
        int expResult = 0;
        int result = instance.getIdValue(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStudentId method, of class ToolsClass.
     */
    @Test
    public void testSetStudentId() {
        System.out.println("setStudentId");
        JLabel studentIdLabel = null;
        ToolsClass instance = new ToolsClass();
        instance.setStudentId(studentIdLabel);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTeacherId method, of class ToolsClass.
     */
    @Test
    public void testSetTeacherId() {
        System.out.println("setTeacherId");
        JLabel teacherIdLable = null;
        ToolsClass instance = new ToolsClass();
        instance.setTeacherId(teacherIdLable);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEmployeeId method, of class ToolsClass.
     */
    @Test
    public void testSetEmployeeId() {
        System.out.println("setEmployeeId");
        JLabel employeeIdLabel = null;
        ToolsClass instance = new ToolsClass();
        instance.setEmployeeId(employeeIdLabel);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStudentRegistrFeeInvoiceId method, of class ToolsClass.
     */
    @Test
    public void testSetStudentRegistrFeeInvoiceId() {
        System.out.println("setStudentRegistrFeeInvoiceId");
        JLabel studentRegistrFeeInvoiceId = null;
        ToolsClass instance = new ToolsClass();
        instance.setStudentRegistrFeeInvoiceId(studentRegistrFeeInvoiceId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSalaryInvoiceId method, of class ToolsClass.
     */
    @Test
    public void testSetSalaryInvoiceId() {
        System.out.println("setSalaryInvoiceId");
        ToolsClass instance = new ToolsClass();
        String expResult = "";
        String result = instance.setSalaryInvoiceId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clearComponets method, of class ToolsClass.
     */
    @Test
    public void testClearComponets() {
        System.out.println("clearComponets");
        Component[] com = null;
        ToolsClass instance = new ToolsClass();
        instance.clearComponets(com);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGuardianId method, of class ToolsClass.
     */
    @Test
    public void testSetGuardianId() {
        System.out.println("setGuardianId");
        JTextField guardianIdTextfield = null;
        ToolsClass instance = new ToolsClass();
        instance.setGuardianId(guardianIdTextfield);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setExpenceId method, of class ToolsClass.
     */
    @Test
    public void testSetExpenceId() {
        System.out.println("setExpenceId");
        JTextField expenceIdTextfield = null;
        ToolsClass instance = new ToolsClass();
        instance.setExpenceId(expenceIdTextfield);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStuPayInvoiceId method, of class ToolsClass.
     */
    @Test
    public void testSetStuPayInvoiceId() {
        System.out.println("setStuPayInvoiceId");
        JTextField StuPayInvoiceId = null;
        ToolsClass instance = new ToolsClass();
        instance.setStuPayInvoiceId(StuPayInvoiceId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incomeId method, of class ToolsClass.
     */
    @Test
    public void testIncomeId() {
        System.out.println("incomeId");
        int incomeID = 0;
        ToolsClass instance = new ToolsClass();
        int expResult = 0;
        int result = instance.incomeId(incomeID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of classId method, of class ToolsClass.
     */
    @Test
    public void testClassId() {
        System.out.println("classId");
        int classId = 0;
        ToolsClass instance = new ToolsClass();
        int expResult = 0;
        int result = instance.classId(classId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
