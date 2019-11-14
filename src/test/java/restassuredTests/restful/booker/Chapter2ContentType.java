package restassuredTests.restful.booker;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 *  * @author Rafael Elias
 *  Checking the Response Content Type
 */

public class Chapter2ContentType {

    @Test
    public void checkContentType_expectApplicationJson() {

        given().
                when().
                get("https://restful-booker.herokuapp.com/booking/1").
                then().
                assertThat().
                contentType(ContentType.JSON);
    }

}
