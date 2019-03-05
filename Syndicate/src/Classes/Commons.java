/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Login.PasswordRecovery;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Uditha N. Bandara
 */
public class Commons {

    //Background color of login window
    public static Color Transparent = new Color(0, 0, 0, 180);

    //Opening transition for JFrame
    public static void FadeIn(JFrame fr) {
        for (double i = 0.0; i <= 1.0; i = i + 0.1) {
            String val = i + "F";
            float f = Float.valueOf(val);
            fr.setOpacity(f);
            try {
                Thread.sleep(30);
            } catch (Exception e) {
            }
        }
    }

    //Closing transition for JFrame
    public static void FadeOut(JFrame fr) {
        for (double i = 1.0; i >= 0.0; i = i - 0.1) {
            String val = i + "F";
            float f = Float.valueOf(val);
            fr.setOpacity(f);
            try {
                Thread.sleep(30);
            } catch (Exception e) {
            }
        }
    }

    //Resizing images to component size
    public static Image scaledImage(Image img, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(img, 0, 0, width, height, null);
        g2d.dispose();

        return resizedImage;
    }

    //Setting background image
    private ImageIcon im1;
    private BufferedImage bi;
    private ImageIcon im2;

    public void background(JFrame f, JLabel lbl) {
        try {
            im1 = new ImageIcon(getClass().getResource("/Images/Background.png"));
            Image img = im1.getImage();
            bi = new BufferedImage(im1.getIconWidth(), im1.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.createGraphics();
            im1.paintIcon(null, g, 0, 0);
            g.dispose();

            int width = f.getWidth();
            int height = f.getHeight();

            im2 = new ImageIcon(Classes.Commons.scaledImage(bi, width, height));
            lbl.setIcon(im2);
        } catch (Exception ex) {
            Logger.getLogger(PasswordRecovery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //Set Date
    public String Date() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(d);
        return date;
    }

    //Set Time
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
    
    //Disable buttons
    public void disbleButtons(JButton...btn){
        for (JButton b : btn){
            b.setEnabled(false);
        }
    }
}
