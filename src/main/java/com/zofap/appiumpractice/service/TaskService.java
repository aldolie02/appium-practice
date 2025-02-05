package com.zofap.appiumpractice.service;

import com.zofap.appiumpractice.util.Constants;

import javax.swing.*;

public class TaskService extends Thread {
    public static JTextArea log;
    public static JButton mulai;
    public static int level;

    public TaskService(JTextArea log, JButton mulai) {
        TaskService.log = log;
        TaskService.mulai = mulai;
        start();
    }

    public static void doJob() {
        Constants.THREAD_RUNNING = true;
        level = Constants.TASKS.get(0).level;
        log.append(Constants.printTaskLog("Memulai Level " + level + ".."));

        boolean result = LogicService.startRobot(Constants.TASKS.get(0), mulai);
        if (!result) {
            Constants.printLogLn("Task Gagal!");
        } else {
            Constants.printLogLn("Task Berhasil!");
        }

        Constants.THREAD_RUNNING = false;
        mulai.setText("Start");
        mulai.setEnabled(true);
    }

    public void run() {
        doJob();
    }
}
