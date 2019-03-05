/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

/**
 *
 * @author Randika
 */
public class loggers {
    public static void setLoger(String Name){
        try {
            String filePath = Name+"\\"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".html";
            
            RollingFileAppender appender = new RollingFileAppender(new HTMLLayout(), filePath);
            
            appender.setName("MyLog");
            appender.setMaxFileSize("1MB");
            appender.activateOptions();
            
            Logger.getRootLogger().addAppender(appender);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void setErrorLogger(String Name){
    try {
            String filePath = Name+"\\"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".html";
            
            RollingFileAppender appender = new RollingFileAppender(new HTMLLayout(), filePath);
            
            appender.setName("MyLog");
            appender.setMaxFileSize("1MB");
            appender.activateOptions();
            
            Logger.getRootLogger().addAppender(appender);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //setLoger();
    }
}
