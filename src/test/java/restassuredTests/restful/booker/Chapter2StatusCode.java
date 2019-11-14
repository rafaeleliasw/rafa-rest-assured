package restassuredTests.restful.booker;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 *  * @author Rafael Elias
 *  Checking the Response Status Code
 */

public class Chapter2StatusCode {

    @Test
    public void checkStatusCode_expectHttp200() {

        given().
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                assertThat().
                statusCode(200);
    }

}
