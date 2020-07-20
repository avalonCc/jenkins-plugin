package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        try {
            // 安装失败的插件的名称
            Set<String> hashSet = new HashSet<>();
            //指定驱动路径
            System.setProperty("webdriver.chrome.driver","D:\\download\\chromedriver_win32\\chromedriver.exe");
            WebDriver driver =new ChromeDriver();
            //jenkins的访问首页地址
            driver.get("http://10.10.10.10:8888/");
            Thread.sleep(2000);
            WebElement j_username = driver.findElement(By.id("j_username"));
            WebElement j_password = driver.findElement(By.name("j_password"));
            // jenkins用户名
            j_username.sendKeys("xxx");
            // jenkins密码
            j_password.sendKeys("xxx");
            WebElement submit = driver.findElement(By.name("Submit"));
            submit.click();
            WebElement manager = driver.findElement(By.linkText("Manage Jenkins"));
            manager.click();
            WebElement plugin = driver.findElement(By.xpath("//a[@title='Manage Plugins']"));
            plugin.click();
            WebElement installed = driver.findElement(By.linkText("Installed"));
            installed.click();
            List<WebElement> parents = driver.findElements(By.xpath("//tr[@class='hoverback']"));
            for (WebElement parent : parents) {
                List<WebElement> elements = parent.findElements(By.xpath("//td[@class='pane']/div"));
                for (int i = 0; i < elements.size(); i++){
                    if (i%2==0){
                        String text = elements.get(i).getText();
                        hashSet.add(text);
                    }
                }
            }
            for (String s : hashSet) {
                installPlugin(hashSet, driver, s);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private static void installPlugin(Set<String> hashSet, WebDriver driver, String pluginName) throws InterruptedException {
        WebElement manager = driver.findElement(By.linkText("Manage Jenkins"));
        manager.click();
        WebElement plugin = driver.findElement(By.xpath("//a[@title='Manage Plugins']"));
        plugin.click();
        WebElement available = driver.findElement(By.linkText("Available"));
        available.click();
        driver.findElement(By.className("plugin-manager__search-input")).sendKeys(pluginName);
        Thread.sleep(3000);
        String boxName = "plugin."+pluginName+".default";
        driver.findElement(By.xpath("//input[@name='"+boxName+"']")).click();
        driver.findElement(new By.ById("yui-gen5-button")).click();

    }
}
