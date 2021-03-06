package restassuredTests.restful.booker;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 *  * @author Rafael Elias
 */

public class Chapter1Example {

    @Test
    public void getBooking1() {

        given().
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                assertThat().
                body("bookingdates.checkin", equalTo("2015-04-08"));
    }
}
