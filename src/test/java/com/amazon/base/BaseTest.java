package com.amazon.base;

import com.amazon.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void setDriver(){
         String browser = PropertiesReader.readyKey("browser");
        browser=browser.toLowerCase();

        switch (browser){

            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                driver.set(new ChromeDriver(options));
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                driver.set(new EdgeDriver(edgeOptions));
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            default:
                System.out.println("Browser not Supported: "+browser);
        }
    }

    public static WebDriver getDriver(){
        return driver.get();
    }


    public static void quitDriver(){
        if(driver.get()!=null){
            driver.get().quit();
            driver.remove();
        }
    }

    @BeforeMethod
    public void setup(){
        setDriver();
        String url =PropertiesReader.readyKey("url");
        getDriver().get(url);
    }

    @AfterMethod
    public void tearDown(){
        quitDriver();
    }

}
