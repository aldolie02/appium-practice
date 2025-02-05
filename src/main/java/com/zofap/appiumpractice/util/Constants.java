package com.zofap.appiumpractice.util;

import com.zofap.appiumpractice.dto.DeviceDto;

import javax.swing.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {
    //CONFIG
    public static int CRYPTO_INDEX = 0;
    public static String REFERRAL_CODE = "";
    public static HashMap<String,String> ACCOUNTS = new HashMap<>();

    public static String DEFAULT_LOCATION = System.getProperty("user.home");
    public static String CACHE_LOCATION = DEFAULT_LOCATION + File.separator + "BNAEQA_BINARY_STUDIO" + File.separator + "botocrypto_cache";
    public static String APPIUM_LOCATION = DEFAULT_LOCATION + File.separator + "AppData" + File.separator + "Roaming" + File.separator + "npm" +
            File.separator + "node_modules" + File.separator + "appium" + File.separator + "build" + File.separator + "lib" + File.separator + "main.js";
    public static String LOG = CACHE_LOCATION + File.separator + "cd.log";
    public static String CONFIG = CACHE_LOCATION + File.separator + "config.bbscf";

    public static String APP_VERSION = "1.0 BASIC";


    public static HashMap<String,String> DEVICES = new HashMap<>();
    public static List<DeviceDto> TASKS = new ArrayList<>();

    public static long APPIUM_POLLING = 100;
    public static long APPIUM_TIMEOUT = 100;



//    public static String LAST_PASSWORD = "";

    public static boolean CONFIGURED = false;
    public static boolean THREAD_RUNNING = false;

    public static int CURRENT_TASK = 0;
    public static int TASK_COUNT = 0;
    public static int STEP = 0;
    public static int LOOP = 0;

    public static void showErrorDialog(String message) {
        System.out.println(message);
        JOptionPane.showMessageDialog(new JFrame(), message, "Error!",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoDialog(String message) {
        System.out.println(message);
        JOptionPane.showMessageDialog(new JFrame(), message, "Information!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showWarningDialog(String message) {
        System.out.println(message);
        JOptionPane.showMessageDialog(new JFrame(), message, "Warning!",
                JOptionPane.WARNING_MESSAGE);
    }

    public static String printLog(String log) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now) + " : " + log + "\n");
    }

    public static String printTaskLog(String log) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now) + " : " + log + "\n");
    }

    public static String printTaskLogLnAf(String log) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now) + " : " + log + "\n\n");
    }

    public static void appendLog(JTextArea log, String msg) {
        log.append(msg);
        log.setCaretPosition(log.getDocument().getLength());
    }

    public static String printLogLn(String log) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return ("\n" + dtf.format(now) + " : " + log + "\n");
    }

    public static String printLogLnAf(String log) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now) + " : " + log + "\n\n");
    }

    public static String currentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now));
    }
}
