package restassuredTests.restful.booker;

import dataentities.Booking;
import dataentities.Bookingdates;
import dataentities.Location;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import restassuredTests.TestUtils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Chapter6Serialization {

    public static final String username = "";
    public static final String password = "";
    public static String token = "";

    @BeforeClass
    public static void beforeTests() throws IOException {
        //Get Encoded Token in Base64
        token = TestUtils.getEncodedToken(username, password);
    }


    @DataProvider(name = "zipCodesAndPlaces")
    public static Object[][] zipCodesAndPlaces() {
        return new Object[][] {
                {"us", "United States", "90210", "Beverly Hills", 0}, //countryCode, countryName, zipCode, placeName, index
                {"ar", "Argentina", "3153", "victoria", 1},
                {"ar", "Argentina", "2000", "rosario", 25}
        };
    }


    @Test(dataProvider = "zipCodesAndPlaces")
    public void validateCountryCode(String countryCode, String countryName, String zipCode, String expectedPlaceName, int i) {

        Location location =

                given().
                        pathParam("countryCode", countryCode).pathParam("zipCode", zipCode).
                        when().
                        get("http://api.zippopotam.us/{countryCode}/{zipCode}").
                        as(Location.class);

        Assert.assertEquals(countryCode.toUpperCase(), location.getCountryAbbreviation().toUpperCase());
        Assert.assertEquals(countryName.toUpperCase(), location.getCountry().toUpperCase());
        Assert.assertEquals(zipCode, location.getPostCode());
        Assert.assertEquals(expectedPlaceName.toUpperCase(), location.getPlaces().get(i).getPlaceName().toUpperCase());
    }

    @Test
    public void requestBookingIdsAndVerifyFirstAndLastName() {

        Booking booking =
            given().
                    when().
                    get("https://restful-booker.herokuapp.com/booking/2").
                    as(Booking.class);

        Assert.assertEquals("Susan", booking.getFirstname());
        Assert.assertEquals (574, booking.getTotalprice());

    }

    @Test
    public void sendLvZipCode1050_checkStatusCode_expect200() {

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2020-02-23");
        bookingdates.setCheckout("2020-02-28");

        Booking booking = new Booking();
        booking.setFirstname("Rafa");
        booking.setLastname("Elias");
        booking.setDepositpaid(true);
        booking.setTotalprice(200);
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Testing serialization");


        given().
                header("Authorization", "Basic "+token).
                header("Content-Type", "application/json").
                body(booking).
                log().body().
                when().
                put("https://restful-booker.herokuapp.com/booking/1").
                then().
                statusCode(200);
    }
}
