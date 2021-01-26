package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {

    private static final String PLATFORM_IOS = "ios",
            PLATFORM_ANDROID = "android",
            OS_WIN = "windows",
            OS_MAC = "macos",
            APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    private static Platform instance;

    private Platform() {}

    public static Platform getInstance()
    {
        if(instance == null){
            instance = new Platform();
        }
        return instance;
    }

    public AppiumDriver getDriver() throws Exception
    {
        URL URL = new URL(APPIUM_URL);
        if(this.isAndroid()){
            return new AndroidDriver(URL,this.getAndroidDesiredCapabilities());
        } else if(this.isIOS()){
            return new IOSDriver(URL, this.getIOSDesiredCapabilities());
        } else {
            throw new Exception("Cannot detect type of the Driver. Platform value: " + this.getPlatformVar());
        }
    }

    public boolean isAndroid()
    {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS()
    {
        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        if(getOsType().equals(OS_WIN)){
            capabilities.setCapability("app","D:\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");
        } else if (getOsType().equals(OS_MAC)){
            capabilities.setCapability("app","/Users/agolubkov/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");
        }else{

            throw new Exception("Cannot get os type from env variable. OS type value: " + getOsType());
        }
        return capabilities;
    }
    private DesiredCapabilities getIOSDesiredCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("deviceName","iPhone SE (2nd generation)");
        capabilities.setCapability("platformVersion","13.5");
        capabilities.setCapability("app","/Users/agolubkov/Desktop/JavaAppiumAutomation/apks/Wikipedia.app");
        return capabilities;
    }

    private boolean isPlatform(String my_platform)
    {
        String platform =this.getPlatformVar();
        return my_platform.equals(platform);
    }

    private String getOsType()
    {
       return System.getenv("OS_TYPE");
    }

    private String getPlatformVar()
    {
        return System.getenv("PLATFORM");
    }

}
