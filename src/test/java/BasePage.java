import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.Step;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import javafx.scene.paint.Color;
import org.apache.commons.logging.Log;
import org.apache.log4j.LogManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class BasePage extends BaseTest {
    @Step("<int> saniye kadar bekle")
    public void waitForSecond(int s) throws InterruptedException {
        Thread.sleep(1000 * s);
    }

    @Step("<id> Id'li elementin görünür olduğunu kontrol et")
    public void checkElementId(String id) {
        MobileElement element = appiumDriver.findElement(By.id(id));
        Logger.info(id + " Elementinin gorunurlugu kontrol ediliyor...");
        Assert.assertTrue(element.isDisplayed());
        if (element.isDisplayed()){
            Logger.info(id + " elementi gorunur.");
        }
        else {
            Logger.info(id + " elementi gorunur degil.");
        }

    }

    @Step("<xpath> Xpath'li elementin görünür olduğunu kontrol et")
    public void checkElementXpath(String xpath) {
        MobileElement element = appiumDriver.findElement(By.xpath(xpath));
        Logger.info(xpath + " Elementinin gorunurlugu kontrol ediliyor...");
        Assert.assertTrue(element.isDisplayed());
        if (element.isDisplayed()){
            Logger.info(xpath + " elementi gorunur.");
        }
        else {
            Logger.info(xpath + " elementi gorunur degil.");
        }

    }

    @Step("Sayfayı aşağı kaydır")
    public void swipeUp() {
        final int ANIMATION_TIME = 200; // ms
        final int PRESS_TIME = 200; // ms
        int edgeBorder = 10; // better avoid edges
        PointOption pointOptionStart, pointOptionEnd;
        Dimension dims = appiumDriver.manage().window().getSize();
        Logger.info("Telefon Ekran Boyutu" + dims);
        pointOptionStart = PointOption.point(dims.width / 4, dims.height / 2);
        Logger.info("Baslangic noktasi" + pointOptionStart);
        pointOptionEnd = PointOption.point(dims.width / 6, edgeBorder);
        Logger.info("Bitis noktasi" + pointOptionStart);
        try {
            new TouchAction(appiumDriver)
                    .press(pointOptionStart)
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
            return;
        }

        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
        }
    }

    @Step("<xpath> Xpath'li elementi bul ve tıkla")
    public void clickByxpath(String xpath) {

        try {
            appiumDriver.findElement(By.xpath(xpath)).click();
            Logger.info(xpath + " elementine tiklandi");
        } catch (Exception e) {
            Logger.info(e + " Hatasi alindi.");
        }
    }

    @Step("<id> Id'li elementi bul ve tıkla")
    public void clickByid(String id) {
        try {
            appiumDriver.findElement(By.id(id)).click();
            Logger.info(id + " elementine tiklandi.");

        } catch (Exception e) {
            Logger.info(e + " Hatasi alindi. ");
        }
    }

    @Step("<xpath> Xpath'li elementleri rastgele bul ve tikla")
    public void getRandomElementByXPath(String xpath) {
       if (appiumDriver.findElement(By.xpath(xpath)).isDisplayed()){
        List<MobileElement> webElements = appiumDriver.findElements(By.xpath(xpath));
        int elementCount = webElements.size();
        Random random = new Random();
        int randomCount = random.nextInt(elementCount);
        Logger.info(randomCount + 1 + ". urun gelmistir.");
        Assert.assertTrue(webElements.get(randomCount).isDisplayed());
        webElements.get(randomCount).click();

       }
       else {
        Logger.info(xpath+" elementi bulunamamıştır.");
       }


    }

    @Step("<id> Id'li elementi bul ve <text> textini yaz")
    public void sendKeys(String id, String text) {
        if (appiumDriver.findElement(By.id(id)).isDisplayed()){
        appiumDriver.findElement(By.id(id)).sendKeys(text);
        Assert.assertEquals("Girilen text eslesiyor.",text,appiumDriver.findElement(By.id(id)).getText());
        Logger.info(text + " texti elemente yazildi.");}
        else {
            Logger.info(id+" elementi bulunamadi.");
        }

    }

}
