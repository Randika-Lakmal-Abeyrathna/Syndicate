 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

/**
 *
 * @author Uditha N. Bandara
 */
public class Theme {
    public static void main(String[] args) {
        try {
            LookAndFeel a = new SyntheticaSimple2DLookAndFeel();
            UIManager.setLookAndFeel(a);
            JFrame.setDefaultLookAndFeelDecorated(false);
            new Login.Login().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}