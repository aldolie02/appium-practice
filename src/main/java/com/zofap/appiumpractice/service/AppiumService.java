package com.zofap.appiumpractice.service;

import com.zofap.appiumpractice.util.Constants;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;

public class AppiumService {
    public static AndroidDriver driver;
    public static AppiumDriverLocalService service;

    public static AndroidDriver getDriver() {
        return driver;
    }

    public static String startAppiumServer(String uuid) throws IOException {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1")
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                .withLogOutput(new FileOutputStream(Constants.LOG))
                .withAppiumJS(new File(Constants.APPIUM_LOCATION))
                .usingAnyFreePort()
                .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"));
        service = AppiumDriverLocalService.buildService(builder);
        service.start();

        URL url = service.getUrl();

        UiAutomator2Options options = new UiAutomator2Options()
                .setFullReset(false)
                .skipDeviceInitialization()
                .skipUnlock()
                .noReset()
                .noSign()
                .ignoreHiddenApiPolicyError()
                .autoGrantPermissions()
                .setAndroidInstallTimeout(Duration.ofSeconds(60))
                .setUiautomator2ServerInstallTimeout(Duration.ofSeconds(60))
                .setUdid(uuid);
        options.setCapability("automationName", "UiAutomator2");
        options.setCapability("deviceName", "ANDROID");
        options.setCapability("autoGrantPermissions", "true");
        driver = new AndroidDriver(url, options);

        return "Appium Berhasil Berjalan! (URL: " + url + ", SessionID: " + driver.getSessionId() + ")";
    }
}
