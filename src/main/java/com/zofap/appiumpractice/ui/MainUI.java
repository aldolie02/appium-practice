package com.zofap.appiumpractice.ui;

import com.zofap.appiumpractice.dto.DeviceDto;
import com.zofap.appiumpractice.service.AppiumService;
import com.zofap.appiumpractice.service.LogicService;
import com.zofap.appiumpractice.service.TaskService;
import com.zofap.appiumpractice.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainUI {
    private static int logX = 0;
    private static int logY = 0;

    public static int getLogY() {
        return logY;
    }

    public static int getLogX() {
        return logX;
    }

    public static void main(String[] args) {
        try {
            mainUI();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void mainUI() {
        // Elements
        JFrame frame = new JFrame("LATIHAN-APPIUM v" + Constants.APP_VERSION);

        frame.setResizable(false);

        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("Opsi");
        JMenuItem mRefresh = new JMenuItem("Refresh");
        JMenuItem mExit = new JMenuItem("Exit");

        JPanel bottomPanel = new JPanel();
        JPanel centerPanel = new JPanel();

        // Level
        String[] levelArr = new String[]{"Level 1"};

        JComboBox<String> namaPerangkat = new JComboBox<>();
        JComboBox<String> level = new JComboBox<>(levelArr);
        JButton mulai = new JButton("Mulai");
        JTextArea log = new JTextArea();
        JScrollPane logScroll = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        namaPerangkat.addItem("Nama Perangkat");

        // Action Listeners
//        tipeCrypto.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                Constants.CRYPTO_INDEX = tipeCrypto.getSelectedIndex();
//            }
//        });

        mulai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedPerangkat = namaPerangkat.getSelectedItem().toString();
                int selectedLevel = Integer.parseInt(level.getSelectedItem().toString().replaceAll("[^0-9]",""));
                boolean perangkatError = selectedPerangkat.equalsIgnoreCase("Nama Perangkat");
                boolean levelError = level.getSelectedItem().toString() == null || level.getSelectedItem().toString().isEmpty();
                StringBuilder errorMsg = new StringBuilder("Pastikan ");
                List<String> allErrors = new ArrayList<>();

                if (!perangkatError && !levelError) {
                    try {
                        Constants.appendLog(log, Constants.printLogLnAf("Menjalankan Server Appium.."));
                        String start1Log = AppiumService.startAppiumServer(Constants.DEVICES.get(selectedPerangkat));
                        Constants.appendLog(log, Constants.printLog(start1Log));
                        LogicService.setLog(log);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    //Generate Tasks
                    Constants.appendLog(log, Constants.printLog("Mempersiapkan " + Constants.TASKS.size() + " Tasks.."));
                    int count = 0;

                    Constants.TASKS.add(new DeviceDto(Constants.DEVICES.get(selectedPerangkat), selectedLevel));
                    Constants.appendLog(log, Constants.printLog("Task Level " + selectedLevel + " berhasil dibuat.."));

                    Constants.CURRENT_TASK = 0;
                    Constants.TASK_COUNT = Constants.TASKS.size();

                    //Mulai Tasks
                    TaskService taskService = new TaskService(log, mulai);
                } else {
                    if (perangkatError) {
                        allErrors.add("Perangkat");
                    }
                    if (levelError) {
                        allErrors.add("Level");
                    }

                    for (int i = 0; i < allErrors.size(); i++) {
                        if ((i + 1) == allErrors.size()) {
                            if (!errorMsg.toString().equalsIgnoreCase("Pastikan "))
                                errorMsg.append("dan ");
                            errorMsg.append(allErrors.get(i));
                        } else {
                            errorMsg.append(allErrors.get(i)).append(", ");
                        }
                    }

                    Constants.appendLog(log, Constants.printLog(errorMsg + " Telah Terkonfigurasi."));
                }
            }
        });

        mExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        namaPerangkat.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (namaPerangkat.getSelectedIndex() != 0)
                    Constants.appendLog(log, Constants.printLog("Connected with " + namaPerangkat.getSelectedItem() + " (" + Constants.DEVICES.get(namaPerangkat.getSelectedItem().toString()) + ")."));

            }
        });

        namaPerangkat.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("REFRESH");

                Runtime rt = Runtime.getRuntime();

                String[] commands = {"adb", "devices", "-l"};
                try {
                    Process proc = rt.exec(commands);
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                    String line;
                    String uuid;
                    String device = "Android";
                    while ((line = stdInput.readLine()) != null) {
                        if (!line.startsWith("adb-") && line.contains("model")) {
                            String[] lineSplit = line.split(" ");
                            uuid = lineSplit[0];
                            for (String s : lineSplit) {
                                if (s.contains("model")) {
                                    device = s.replace("model:", "").replace("_", " ");
                                }
                            }
                            if (!Constants.DEVICES.containsKey(device)) {
                                Constants.DEVICES.put(device, uuid);
                                namaPerangkat.addItem(device);
                            }
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        log.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                log.setCaretPosition(log.getText().length());
            }
        });

        mb.add(m1);
        m1.add(mRefresh);
        m1.add(mExit);

        bottomPanel.add(namaPerangkat);
//        bottomPanel.add(Box.createHorizontalStrut(120));
        bottomPanel.add(level);
        bottomPanel.add(Box.createHorizontalStrut(400));
        bottomPanel.add(mulai);

//        centerPanel.add(logScroll);

        frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, logScroll);

        // Configurations
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimPerangkat = new Dimension(200, namaPerangkat.getHeight());
        Dimension dimReferral = new Dimension(100, 26);
        Dimension dimJScrollLog = log.getSize();

        logScroll.setPreferredSize(dimJScrollLog);
        logScroll.setSize(dimJScrollLog);

//        setGlobalSize(referralCode, dimReferral);
        setGlobalSize(namaPerangkat, dimPerangkat);

        frame.setLocation((dim.width / 2 - frame.getWidth()), (dim.height / 2 - frame.getHeight()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        log.setEditable(false);

        logX = log.getX();
        logY = log.getY();
    }

    public static void setGlobalSize(Component component, Dimension dimension) {
        component.setSize(dimension);
        component.setMaximumSize(dimension);
        component.setMinimumSize(dimension);
        component.setPreferredSize(dimension);
    }
}
