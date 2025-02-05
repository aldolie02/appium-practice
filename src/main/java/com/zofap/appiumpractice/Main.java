package com.zofap.appiumpractice;

import com.zofap.appiumpractice.ui.MainUI;
import com.zofap.appiumpractice.util.Constants;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static Path cfgPath = Path.of("C:/Users/Public/BNAEQA BINARY STUDIO/botocrypto.bbc");

    public static void main(String[] args) throws IOException {
//        boolean startFromLauncher = false;
//        boolean developer = false;
//
//        System.out.println("Args:");
//        for (String c : args) {
//            if (c.equals("--launcher=true")) {
//                System.out.println("Launched from Launcher");
//                startFromLauncher = true;
//            }
//
//            if (c.equals("--developer=true")) {
//                System.out.println("Is Developer");
//                developer = true;
//            }
//        }
//
//        if (!startFromLauncher) {
//            Constants.showErrorDialog("Program ini hanya bisa dibuka menggunakan launcher!");
//            System.exit(-1);
//        }

        if (Files.notExists(Path.of(Constants.CACHE_LOCATION))) {
            Files.createDirectories(Path.of(Constants.CACHE_LOCATION));
            Files.createFile(Path.of(Constants.LOG));
        }

//        System.out.println(Path.of(System.getProperty("user.dir") + "/lib"));
//        if (!developer && Files.notExists(Path.of(System.getProperty("user.dir") + "/lib"))) {
//            Constants.showErrorDialog("Program tidak terpasang dengan benar! Silahkan download ulang dan matikan antivirus.");
//            System.exit(-1);
//        }

        try {
            MainUI.mainUI();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

