package restassuredTests.restful.booker;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Rafael Elias
 */

public class Example2Test {

    @Test
    public void checkStatusCode_expectHttp200() {

        given().
        when().
            get("https://restful-booker.herokuapp.com/booking/1").
        then().
            assertThat().
                statusCode(200);

    }

    @Test
    public void checkContentType_expectApplicationJson() {

        given().
        when().
            get("https://restful-booker.herokuapp.com/booking/1").
        then().
            assertThat().
            contentType(ContentType.JSON);
    }

    @Test
    public void logRequestAndResponseDetails() {

        given().
            log().all().
        when().
            get("https://restful-booker.herokuapp.com/booking/1").
        then().
            log().body();
    }


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
    public void checkNumberOfElementsInResponseBody_expectFive() {

        given().
        when().
            get("https://restful-booker.herokuapp.com/booking/1").
        then().
            assertThat().
            body("size()", is(5));
    }
}
