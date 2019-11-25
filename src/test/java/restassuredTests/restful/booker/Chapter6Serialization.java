package restassuredTests.restful.booker;

import dataentities.Booking;
import dataentities.Location;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Chapter6Serialization {

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
}
