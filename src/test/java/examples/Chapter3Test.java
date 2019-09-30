package examples;

import com.tngtech.java.junit.dataprovider.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Rafael Elias
 */

@RunWith(DataProviderRunner.class)
public class Chapter3Test {

    @DataProvider
    public static Object[][] bookingIdAndNames() {
        return new Object[][] {
            { "1", "Susan", "Smith" }, //bookingId, firstName, lastName
            { "2", "Jim", "Jones" },
            { "3", "Sally", "Ericsson"}
        };
    }

    @Test
    @UseDataProvider("bookingIdAndNames")
    public void requestBookingIdsAndVerifyFirstAndLastName(String bookingId, String firstName, String lastName) {

        given().
            pathParam("bookingId", bookingId).
        when().
            get("https://restful-booker.herokuapp.com/booking/{bookingId}").
        then().
            assertThat().
            body("firstname", equalTo(firstName)).
                body("lastname", equalTo(lastName));
    }
}
