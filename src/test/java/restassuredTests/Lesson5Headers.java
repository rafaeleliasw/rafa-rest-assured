package restassuredTests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import restassuredTests.testutil.TestUtils;
import io.restassured.response.Response;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Lesson5Headers {

    public static final String username = "admin";
    public static final String password = "password123";
    public static String token = "";
    public static String cookieToken = "";
    private static RequestSpecification requestSpec;
    private static Integer bookingIdToBeDeleted;

    @BeforeClass
    public static void beforeTests() throws IOException {

        //Set BaseURL
        requestSpec = new RequestSpecBuilder().
                setBaseUri("https://restful-booker.herokuapp.com/booking/").
                build();

        //Get Encoded Token in Base64
        token = TestUtils.getEncodedToken(username, password);

        //Creates a new auth token to use for access to the PUT and DELETE /booking
        getCookieToken();

        // Get the latest Booking Id from the list to delete it in a test
        getBookingIdToBeDeleted();
    }

    public static void getCookieToken() throws IOException {
        String payload = TestUtils.generateStringFromResource("./Files/CookieToken.json");

        Response response =
                given().
                        header("Content-Type", "application/json").
                        body(payload).
                        when().
                        post("https://restful-booker.herokuapp.com/auth");

        cookieToken = response.jsonPath().get("token");
    }

    private static void getBookingIdToBeDeleted() {

        Response response =
                given().
                        header("Content-Type", "application/json").
                        spec(requestSpec).
                        when().
                        get();

        bookingIdToBeDeleted = response.jsonPath().get("bookingid[-1]");
    }


    @Test(priority = 3)
    public void checkingJsonResponses() {

        given().
                header("Accept", "application/json").
                spec(requestSpec).
                when().
                get("1").
                then().
                log().all().
                assertThat().
                body("firstname", equalTo("Rafa")).
                body("bookingdates.checkin", equalTo("2013-02-23"));
    }

    @Test(priority = 2)
    public void checkingXmlResponses() {

        given().
                header("Accept", "application/xml").
                spec(requestSpec).
                when().
                get("1").
                then().
                log().all().
                assertThat().
                body("booking.firstname", equalTo("Rafa")).
                body("booking.bookingdates.checkin", equalTo("2013-02-23"));
    }


    @Test(priority = 1)
    public void updateABookingAuthorizationHeader() throws IOException {

        String payload = TestUtils.generateStringFromResource("./Files/UpdateBooking.json");

        given().
                header("Authorization", "Basic "+token).
                header("Content-Type", "application/json").
                spec(requestSpec).
                body(payload).
                when().
                put("1").
                then().
                statusCode(200).
                body("firstname", equalTo("Rafa")).
                body("lastname", equalTo("test Update")).
                body("totalprice", equalTo(111)).
                body("bookingdates.checkin", equalTo("2013-02-23"));
    }

    @Test(priority = 4)
    public void DeleteABookingCookie() throws IOException {

        given().
                cookie("token", cookieToken).
                header("Content-Type", "application/json").
                spec(requestSpec).
                when().
                log().all().
                delete(bookingIdToBeDeleted.toString()).
                then().
                log().all().
                statusCode(201);
    }
}
