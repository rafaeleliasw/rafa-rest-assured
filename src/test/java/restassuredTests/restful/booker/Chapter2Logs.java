package restassuredTests.restful.booker;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Rafael Elias
 * Logging Request and Response Details
 */

public class Chapter2Logs {

    @Test
    public void logRequest1() {

        given().
                log().all().
                when().
                get("https://restful-booker.herokuapp.com/booking/1");
    }

    @Test
    public void logRequest2() {

        given().
                log().params().
                when().
                get("https://restful-booker.herokuapp.com/booking/1");
    }


    @Test
    public void logRequest3() {

        given().
                log().body().
                when().
                get("https://restful-booker.herokuapp.com/booking/1");
    }

    @Test
    public void logRequest4() {

        given().
                log().headers().
                when().
                get("https://restful-booker.herokuapp.com/booking/1");
    }

    @Test
    public void logRequest15() {

        given().
                log().cookies().
                when().
                get("https://restful-booker.herokuapp.com/booking/1");
    }

    @Test
    public void logRequest6() {

        given().
                log().method().
                when().
                get("https://restful-booker.herokuapp.com/booking/1");
    }

    @Test
    public void logResponse1() {

        given().
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                log().all();
    }

    @Test
    public void logResponse2() {

        given().
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                log().status();
    }

    @Test
    public void logResponse3() {

        given().
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                log().headers();
    }

    @Test
    public void logResponse4() {

        given().
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                log().cookies();
    }

    @Test
    public void logResponse5() {

        given().
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                log().ifStatusCodeIsEqualTo(200);
    }

    @Test
    public void logResponse6() {

        given().
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                log().ifStatusCodeMatches(equalTo(200));
    }

}
