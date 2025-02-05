package com.zofap.appiumpractice.service;

import com.zofap.appiumpractice.dto.DeviceDto;
import com.zofap.appiumpractice.util.Constants;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.zofap.appiumpractice.service.TaskService.mulai;

public class LogicService extends Thread {
    static AndroidDriver driver;
    static DeviceDto dcDTO;

    static String errMsg = "";
    static JTextArea log = new JTextArea();
    static List<String> names = new ArrayList<>();
    static Random rand = new Random();
    static String uuid = "";
    static int level;
    static String lastError = "";

    static List<WebElement> findElement = new ArrayList<>();
    static String lastType;
    static String lastLocator;
    static String lastCommand;

    public static void setLog(Component log) {
        LogicService.log = (JTextArea) log;
    }

    public static void setStatus(String email, String task) {
        log.append(Constants.printTaskLog(email + ": " + task));
        errMsg = task;
        log.setCaretPosition(log.getDocument().getLength());
    }

    public static boolean level1(DeviceDto dto) {
        // Level 1 ini adalah contoh, saya akan buka Google Calculator..
        // lalu saya otomatis lakukan (2 x 2) + 100.
        // setelah itu saya cek text field bagian result apakah sesuai dengan 104..
        // jika sesuai maka result berhasil, jika berbeda maka result gagal..

        ///// disini saya akan menggunakan syntax appium seperti:
        // - ((InteractsWithApps) driver).isAppInstalled(#appPackage.. contoh: com.google.calculator); = memeriksa apakah aplikasi terinstall
        // - ((InteractsWithApps) driver).activateApp(#appPackage.. contoh: com.google.calculator); = membuka aplikasi
        // - ((InteractsWithApps) driver).terminateApp(#appPackage.. contoh: com.google.calculator); = menutup aplikasi

        // Catatan: kalau kamu mau terminateApp, activateApp, wajib kamu cast ke object InteractsWithApps biar stabil.
        //          kalau hanya untuk find elements dan interaksi, ga perlu kamu cast ke object ini.

        // - driver.findElements(AppiumBy.xpath(//xpath)); = mencari banyak element yang sesuai dengan syntax pencarian xpath
        // - driver.findElement(AppiumBy.xpath(//xpath)); = mencari 1 element yang sesuai dengan syntax pencarian xpath
        // - driver.findElements(AppiumBy.xpath(//xpath)); = mencari banyak element yang sesuai dengan syntax pencarian xpath
        // - WebElement.click(); = menekan element yang ditemukan dari hasil pencarian
        // - WebElement.getText(); = mengambil text dari element yang ditemukan dari hasil pencarian

        dcDTO = dto;
        boolean running = true;
        driver = AppiumService.getDriver();

        // Mengatur Aplikasi yang akan digunakan bot
        String appPackage = "com.google.android.calculator";

        while (running) {
            try {
                Constants.appendLog(log, Constants.printLog("Menjalankan Aplikasi '" + appPackage + "'.."));


                // Menutup & Menjalankan Ulang Aplikasi
                ((InteractsWithApps) driver).terminateApp(appPackage);
                ((InteractsWithApps) driver).activateApp(appPackage);
                if (!((InteractsWithApps) driver).isAppInstalled(appPackage)) {
                    Constants.appendLog(log, Constants.printLog("Kamu wajib install Google Calculator untuk menjalankan Level 1."));
                    return false;
                }

                // Bisa seperti ini, kamu save dulu ke WebElement biar bisa kamu pencet berkali2/ lakuin operasi lain tanpa harus cari lagi (ngurangin redundancy)
                WebElement tombol_2 = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofMillis(100))
                        .ignoring(NoSuchElementException.class)
                        .until(driver1 -> driver1.findElement(AppiumBy.xpath("//android.widget.ImageButton[@content-desc=\"2\"]")));

                tombol_2.click();

                // Atau  kalau cuman dipencet 1x, bisa langsung click spt ini tanpa harus save ke Object WebElement
                new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofMillis(100))
                        .ignoring(NoSuchElementException.class)
                        .until(driver1 -> driver1.findElement(AppiumBy.xpath("//android.widget.ImageButton[@content-desc=\"multiply\"]"))).click();

                // Ini contoh operasi pada object WebElement yang sudah disave
                tombol_2.click();

                /// diatas udah 2x2, sekarang kita tambah 100:

                // Klik tombol penjumlahan
                new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofMillis(100))
                        .ignoring(NoSuchElementException.class)
                        .until(driver1 -> driver1.findElement(AppiumBy.xpath("//android.widget.ImageButton[@content-desc=\"plus\"]"))).click();

                // Klik 100
                WebElement tombol_1 = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofMillis(100))
                        .ignoring(NoSuchElementException.class)
                        .until(driver1 -> driver1.findElement(AppiumBy.xpath("//android.widget.ImageButton[@content-desc=\"1\"]")));

                tombol_1.click();

                WebElement tombol_0 = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofMillis(100))
                        .ignoring(NoSuchElementException.class)
                        .until(driver1 -> driver1.findElement(AppiumBy.xpath("//android.widget.ImageButton[@content-desc=\"0\"]")));

                tombol_0.click();
                tombol_0.click();

                // Lalu klik tombol sama dengan, dan kita get text. if equalsIgnoreCase 104, maka success, jika bukan maka fail.
                new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofMillis(100))
                        .ignoring(NoSuchElementException.class)
                        .until(driver1 -> driver1.findElement(AppiumBy.xpath("//android.widget.ImageButton[@content-desc=\"equals\"]"))).click();

                String hasil = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofMillis(100))
                        .ignoring(NoSuchElementException.class)
                        .until(driver1 -> driver1.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.google.android.calculator:id/result_final\"]")))
                        .getText(); // <- ini untuk ambil text dari element

                // Tutup Aplikasi
                ((InteractsWithApps) driver).terminateApp(appPackage);

                if (hasil.equalsIgnoreCase("104")) {
                    Constants.appendLog(log, Constants.printLogLn("Berhasil! Hasil (2x2) + 100 adalah 104."));
                } else {
                    Constants.appendLog(log, Constants.printLogLn("Gagal! Hasil (2x2) + 100-nya malah menjadi " + hasil + "."));
                    break;
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                running = false;
            }
            if (!running) {
                break;
            }
        }

        return false;
    }

    public static boolean startRobot(DeviceDto device, JButton mulai) {
        try {
            System.out.println("Robot Start");
            uuid = device.perangkat;
            level = device.level;

            mulai.setEnabled(false);
            mulai.setText("Berjalan");
            System.out.println("Level: " + level);
            if (level == 1) {
                System.out.println("Level 1 Detected");
                if (level1(device)) {
                    System.out.println("Level 1 Berhasil");
                    setStatus(dcDTO.perangkat, "Level " + level + " Berhasil!");
                } else {
                    System.out.println("Level 1 Gagal.");
                    setStatus(dcDTO.perangkat, "Level " + level + " Gagal.");
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void setDto(DeviceDto dto) {
        dcDTO = dto;
        uuid = dcDTO.perangkat;
        level = dcDTO.level;
    }

    public void run() {
        startRobot(dcDTO, mulai);
    }
}