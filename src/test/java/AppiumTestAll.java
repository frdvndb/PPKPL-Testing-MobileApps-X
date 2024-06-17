// This sample code supports Appium Java client >=9
// https://github.com/appium/java-client

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AppiumTestAll {

    private AndroidDriver driver;


    @Before
    public void setUp() {
        var options = new BaseOptions()
                .amend("platformName", "Android")
                .amend("appium:platformVersion", "14")
                .amend("appium:automationName", "UiAutomator2")
                .amend("appium:deviceName", "emulator-5554")
                .amend("appium:appPackage", "com.twitter.android")
                .amend("appium:appActivity", "com.twitter.android.StartActivity")
                .amend("appium:noReset", true)
                .amend("appium:fullReset", false)
                .amend("appium:ensureWebviewsHavePages", true)
                .amend("appium:nativeWebScreenshot", true)
                .amend("appium:newCommandTimeout", 3600)
                .amend("appium:connectHardwareKeyboard", true);

        driver = new AndroidDriver(this.getUrl(), options);

    }

    URL getUrl() {
        try {
            return new URL("http://127.0.0.1:4723/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test1RegisterPositive() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Create account\")")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(0)")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/name_edit_text"))).sendKeys("validname0015");
        driver.findElement(AppiumBy.id("com.twitter.android:id/phone_or_email_edit_text")).sendKeys(generateRandomEmail());
        driver.findElement(AppiumBy.id("com.twitter.android:id/birthday_edit_text")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"2023\")"))).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(0)")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/ocf_text_input_edit")));
    }

    @Test
    public void test2RegisterNegative() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        goBack(5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"Continue with Google\")")));
        performTap(driver, 500, 450);

        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"Create account\")"))).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(0)")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/name_edit_text"))).sendKeys("asaaaaeaeaseaweawveav23va23va23va2v3a2v32av342asd23");
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/textinput_error")));
    }

    @Test
    public void test3LoginNegative() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        goBack(2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"Continue with Google\")")));
        performTap(driver, 500, 450);

        performTap(driver, 661, 2249);
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/ocf_text_input_edit"))).sendKeys("wrongemail2024@wrong.com");
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(1)")).click();
        assert(driver.findElement(By.xpath("//android.widget.Toast")).getText().contains("Sorry, we could not find your account."));
    }

    @Test
    public void test4LoginPositive() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        goBack(1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"Continue with Google\")")));
        performTap(driver, 500, 450);


        performTap(driver, 661, 2249);
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/ocf_text_input_edit"))).sendKeys("ekag6422@gmail.com");
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(1)")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/password_edit_text")));
    }

    @Test
    public void test5LoginPositive() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        goBack(1);
        checkIfAskToSignIn();

        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"Continue with Google\")"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.LinearLayout\").instance(4)"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/channels")));
    }

    @Test
    public void test6PostFeedPositive() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        driver.findElement(AppiumBy.accessibilityId("New post")).click();
        driver.findElement(AppiumBy.accessibilityId("New post")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"com.twitter.android:id/tweet_text\"]"))).sendKeys(generateRandomText(5, 10));
        driver.findElement(AppiumBy.id("com.twitter.android:id/button_tweet")).click();
        WebElement toastTitleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.twitter.android:id/toast_title")));
        assert(toastTitleElement.getText().contains("Your post was sent"));
    }

    @Test
    public void test7PostFeedNegative() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        driver.findElement(AppiumBy.accessibilityId("New post")).click();
        driver.findElement(AppiumBy.accessibilityId("New post")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"com.twitter.android:id/tweet_text\"]"))).sendKeys("Hopes and dreams were dashed that day. It should have been expected, but it still came as a shock. The warning signs had been ignored in favor of the possibility, however remote, that it could actually happen. That possibility had grown from hope to an undeniable belief it must be destiny. That was until it wasn't and the hopes and dreams came crashing down.");
        driver.findElement(AppiumBy.id("com.twitter.android:id/button_tweet")).click();
        driver.findElement(AppiumBy.id("com.twitter.android:id/tweet_character_warning_count")).isDisplayed();
    }

    @Test
    public void test8EditProfilePositive() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Navigate up"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("android:id/button2"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Show navigation drawer"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"Profile\")"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/button_edit_profile"))).click();
        String newInput = "bio-" + generateRandomText(1, 4);
        WebElement InputField = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/edit_bio")));
        InputField.clear();
        InputField.sendKeys(newInput);
        driver.findElement(AppiumBy.id("com.twitter.android:id/save")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(newInput))).isDisplayed();
    }

    @Test
    public void test9EditProfileNegative() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        driver.findElement(AppiumBy.id("com.twitter.android:id/button_edit_profile")).click();
        String newInput = "site" + generateRandomText(1, 4);
        WebElement InputField = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/edit_web_url")));
        InputField.clear();
        InputField.sendKeys(newInput);
        driver.findElement(AppiumBy.id("com.twitter.android:id/save")).click();
        assert(driver.findElement(By.xpath("//android.widget.Toast")).getText().contains("Please enter a valid URL for your website."));
    }

    @Test
    public void test10CommunitySearchPositive() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        goBack(1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("android:id/button1"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/button_edit_profile")));
        goBack(1);

        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/communities"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Search"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/query_view"))).sendKeys("Chelsea");
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.ViewGroup[@resource-id=\"com.twitter.android:id/search_compose_view\"]/android.view.View/android.view.View/android.view.View[1]")));
    }

    @Test
    public void test11CommunitySearchNegative() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        goBack(2);

        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/communities"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Search"))).click();
        String newInput = "!@#$%^&*()";
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.twitter.android:id/query_view"))).sendKeys(newInput);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"No results for " + newInput + "\")")).click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    public void goBack(int max){
        for (int i = 0; i < max; i++){
            driver.executeScript("mobile: pressKey", Map.ofEntries(Map.entry("keycode", 4)));
        }
    }

    public void checkIfAskToSignIn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By buttonLocator = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(1)");
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(buttonLocator));
        if (button != null) {
            performTap(driver, 500, 450);
        }
    }

    public static String generateRandomText(int minLength, int maxLength) {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        int length = random.nextInt(maxLength - minLength + 1) + minLength; // Random length between minLength and maxLength

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allowedChars.length());
            sb.append(allowedChars.charAt(index));
        }

        return sb.toString();
    }

    public static String generateRandomEmail() {
        String domain = "@testing15.com";
        String username = generateRandomText(5, 10);
        return username + domain;
    }

    private static void performTap(AndroidDriver driver, int x, int y) {
        final var finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        var tapPoint = new Point(x, y);
        var tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), tapPoint.x, tapPoint.y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(50)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(tap));
    }
}

