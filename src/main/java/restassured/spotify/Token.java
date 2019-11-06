package restassured.spotify;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Time;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Token {

    public static String getEncodedToken(String clientid, String clientSecret){
        String idSecret = clientid +":"+clientSecret;
        byte[] bytesEncoded = Base64.encodeBase64(idSecret.getBytes());
        return new String(bytesEncoded);
    }

    public static String  getSpotifyAccessToken(String email, String password, String clientId, String  redirect_uri, String scope, String response_type, int  state) throws InterruptedException, UnsupportedEncodingException {

        System.setProperty("webdriver.chrome.driver", "C:\\Chromedriver\\chromedriver.exe");
        String encodedUri = URLEncoder.encode(redirect_uri, "UTF-8");
        String query = "client_id="+clientId+"&redirect_uri="+encodedUri+"&scope="+scope+"&response_type="+response_type+"&state="+state;
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://accounts.spotify.com/authorize?"+query);
        // Set Username
        WebElement username = driver.findElement(By.id("login-username"));
        username.sendKeys(email);
        //Set Password
        WebElement userpwd = driver.findElement(By.id("login-password"));
        userpwd.sendKeys(password);
        //Click on Log In
        WebElement login = driver.findElement(By.id("login-button"));
        login.click();
        Thread.sleep(2000);
        String uri = driver.getCurrentUrl();
        UriComponents uriInfo = UriComponentsBuilder.fromUriString(uri).build();
        Map<String, String> data = parseUrlFragment(uriInfo.getFragment());
        String access_token = data.get ("access_token");
        driver.quit();

        return access_token;

    }

    public static Map<String, String> parseUrlFragment(String url) {

        Map<String, String> output = new LinkedHashMap<>();
        String[] keys = url.split ("&");
        for (String key : keys) {
            String[] values = key.split ("=");
            output.put (values[0], (values.length > 1 ? values[1] : ""));
        }
        return output;
    }


}
