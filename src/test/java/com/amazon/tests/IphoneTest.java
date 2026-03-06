package com.amazon.tests;

import com.amazon.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class IphoneTest extends BaseTest {

    @Test
    public void searchIphoneAndAddToCart() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));


        By searchBoxLocator = By.id("twotabsearchtextbox");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBoxLocator));
        searchBox.sendKeys("iphone");

        By searchButtonLocator = By.id("nav-search-submit-button");
        WebElement searchButton = wait.until(ExpectedConditions.visibilityOfElementLocated(searchButtonLocator));
        searchButton.click();


        By iphonesRows = By.xpath("//div[@role='listitem']");
        List<WebElement> iphonesList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(iphonesRows));


        WebElement firstRow = iphonesList.get(0);
        WebElement productLink = firstRow.findElement(By.tagName("h2"));

        String parentWindowHandle = getDriver().getWindowHandle();
        productLink.click();


        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String window : getDriver().getWindowHandles()) {
            if (!window.equals(parentWindowHandle)) {
                getDriver().switchTo().window(window);
                break;
            }
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("productTitle")));

        By addToCartLocator = By.xpath(
                "//div[contains(@class,'a-accordion-active')]//input[@id='add-to-cart-button']"
        );

        WebElement addToCartBtn = wait.until(
                ExpectedConditions.elementToBeClickable(addToCartLocator)
        );

        js.executeScript("arguments[0].scrollIntoView(true);", addToCartBtn);

        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));

        addToCartBtn.click();

        By totalPriceLocator = By.id("sw-subtotal");
        WebElement totalPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(totalPriceLocator));

        By wholePriceLocator = By.className("a-price-whole");
        WebElement wholePrice = totalPrice.findElement(wholePriceLocator);

        By fractionPriceLocator = By.className("a-price-fraction");
        WebElement fractionPrice = totalPrice.findElement(fractionPriceLocator);

        System.out.println("Price of the product: ₹" + wholePrice.getText() + "." + fractionPrice.getText());
    }
}