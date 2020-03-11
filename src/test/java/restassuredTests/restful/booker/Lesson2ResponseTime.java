package restassuredTests.restful.booker;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

/**
 *  @author Rafael Elias
 *  Checking the Response Time
 */

public class Lesson2ResponseTime {

    @Test
    public void checkResponseTime() {

        given().
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                time(lessThan(3000L));
    }
}
