package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class CoreTestCase extends TestCase {

    private static final String PLATFORM_IOS = "ios",
                                PLATFORM_ANDROID = "android",
                                OS_WIN = "windows",
                                OS_MAC = "macos";

    protected AppiumDriver driver;
    private static String AppiumURL="http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        driver = getDriverByPlatformEnv(); //получить драйвер в зависимости от переменной
        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception {

        driver.quit();

        super.tearDown();
    }

    protected void rotateScreenPortrait(){
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape(){
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds){
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }

    private AppiumDriver getDriverByPlatformEnv() throws  Exception
    {
        String platform = System.getenv("PLATFORM");
        if(platform.equals(PLATFORM_ANDROID)){
            return new AndroidDriver(new URL(AppiumURL),this.getCapabilitiesByPlatformEnv());
        } else if (platform.equals(PLATFORM_IOS)){
            return new IOSDriver(new URL(AppiumURL),this.getCapabilitiesByPlatformEnv());
        }else{
            throw new Exception("Cannot get run platform from env variable. Platform value: "+ platform);
        }
    }

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception
    {
       String platform = System.getenv("PLATFORM");
        //по аналогии с платформой сделал определение операционной системы, поскольку тесты живут на разных машинах и приложение имеет разный путь
       String os = System.getenv("OS_TYPE");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if(platform.equals(PLATFORM_ANDROID)){
            capabilities.setCapability("platformName","Android");
            capabilities.setCapability("deviceName","AndroidTestDevice");
            capabilities.setCapability("platformVersion","8.0");
            capabilities.setCapability("automationName","Appium");
            capabilities.setCapability("appPackage","org.wikipedia");
            capabilities.setCapability("appActivity",".main.MainActivity");
            if(os.equals(OS_WIN)){
                capabilities.setCapability("app","D:\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");
            } else if (os.equals(OS_MAC)){
                capabilities.setCapability("app","/Users/agolubkov/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");
            }else{
                throw new Exception("Cannot get os type from env variable. OS type value: "+ os);
            }

        } else if(platform.equals(PLATFORM_IOS)){
            capabilities.setCapability("platformName","iOS");
            capabilities.setCapability("deviceName","iPhone SE (2nd generation)");
            capabilities.setCapability("platformVersion","13.5");
            capabilities.setCapability("app","/Users/agolubkov/Desktop/JavaAppiumAutomation/apks/Wikipedia.app");
        } else{
            throw new Exception("Cannot get run platform from env variable. Platform value: "+ platform);
        }

        return capabilities;
    }
}
