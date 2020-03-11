package restassuredTests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * @author Rafael Elias
 * Checking the Response Body
 */

public class Lesson2ResponseBody {

    @Test
    public void checkFirstName_expectedJim() {

        given().
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                assertThat().
                body("firstname", equalTo("Jim"));
    }

    @Test
    public void checkPlaceName_expectedBeverlyHills() {

        given().
                when().
                get("https://api.zippopotam.us/us/90210").
                then().
                log().all().
                assertThat().
                body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    @Test
    public void checkNumberOfElementsInResponseBody_expectFive() {

        given().
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                log().all().
                assertThat().
                body("size()", is(6));
    }

    @Test
    public void checkIfHasItem() {

        given().
                when().
                get("https://api.zippopotam.us/us/90210").
                then().
                log().all().
                assertThat().
                body("places.state", hasItem("California"));
    }



    @Test
    public void checkCountry_NotEqualToArgentina() {

        given().
                when().
                get("https://api.zippopotam.us/us/90210").
                then().
                log().all().
                assertThat().
                body("country", not(equalTo("Argentina")));
    }

    @Test
    public void checkIfGreaterThan_expectTrue() {

        given().
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                log().all().
                assertThat().
                body("totalprice", greaterThan(0));
    }

    @Test
    public void checkingXmlResponses() {

        given().
                header("Accept", "application/xml").
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                log().all().
                assertThat().
                body("booking.firstname", equalTo("Jim")).
                body("booking.bookingdates.checkin", equalTo("2017-03-13"));
    }
}
