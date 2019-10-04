package restassuredTests.restful.booker;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;
import restassuredTests.BaseTest;

import java.io.IOException;

import static io.restassured.RestAssured.*;

/**
 * @author Rafael Elias
 *
 * In this class I'm checking different status code
 */
public class Example5Test extends BaseTest {

    @BeforeClass
    public static void beforeTests() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
    }

    @Test
    public void checkStatusCode_expectHttp404() {

        given().
                when().
                get("/booking/1000").
                then().
                assertThat().
                statusCode(404);
    }

    @Test
    public void checkStatusCode_expectHttp400() throws IOException {

        String payload = generateStringFromResource("./Files/BookingBadRequest.json");

        given().
                when().
                header("Content-type", "application/json").
                body(payload).
                log().all().
                post("/booking").
                then().
                assertThat().
                statusCode(400);
    }

    @Test
    public void checkStatusCode_expectHttp500() throws IOException {

        String payload = generateStringFromResource("./Files/BookingInternalServerError.json");

        given().
                when().
                header("Content-type", "application/json").
                body(payload).
                post("/booking").
                then().
                assertThat().
                statusCode(500);
    }
}
