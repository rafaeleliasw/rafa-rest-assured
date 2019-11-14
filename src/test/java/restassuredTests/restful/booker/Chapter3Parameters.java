package restassuredTests.restful.booker;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Rafael Elias
 * Parameters in RESTful APIs
 */


public class Chapter3Parameters {

    public static final String bookingId = "1";
    public static final String firstName = "Eric";
    public static final String lastName = "Jackson";

    @DataProvider(name = "zipCodesAndPlaces")
    public static Object[][] zipCodesAndPlaces() {
        return new Object[][] {
                { "us", "90210", "Beverly Hills" },
                { "us", "12345", "Schenectady" },
                { "ca", "B2R", "Waverley"}
        };
    }

    @DataProvider(name = "comments")
    public static Object[][] comments() {
        return new Object[][] {
                { 1, "Eliseo@gardner.biz"},       //postId, Email
                { 2, "Presley.Mueller@myrl.com" },
                { 3, "Veronica_Goodwin@timmothy.net"}
        };
    }

    @Test
    public void requestBookingIdsAndVerifyFirstAndLastName() {

        given().
                pathParam("bookingId", bookingId).
                when().
                get("https://restful-booker.herokuapp.com/booking/{bookingId}").
                then().
                log().all().
                assertThat().
                body("firstname", equalTo(firstName)).
                body("lastname", equalTo(lastName));
    }

    @Test(dataProvider = "zipCodesAndPlaces")
    public void requestZipCodesFromCollection_checkPlaceNameInResponseBody_expectSpecifiedPlaceName(String countryCode, String zipCode, String expectedPlaceName)
    {
        given().
                pathParam("countryCode", countryCode).pathParam("zipCode", zipCode).
                when().
                get("http://zippopotam.us/{countryCode}/{zipCode}").
                then().
                assertThat().
                body("places[0].'place name'", equalTo(expectedPlaceName));
    }

    @Test(dataProvider = "comments")
    public void getCommetsOfAPost(int postId, String email) {

         given()
                .queryParam("postId", postId).
                log().all().
                when().
                get("https://jsonplaceholder.typicode.com/comments").
                then().
                log().all().
                assertThat().
                body("email[0]", equalTo(email));
        }

}
