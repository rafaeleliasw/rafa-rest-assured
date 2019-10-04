package restassuredTests.restful.booker;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Rafael Elias
 */

public class Example4Test {

    private static RequestSpecification requestSpec;

    @BeforeClass
    public static void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("https://restful-booker.herokuapp.com/booking/").
            build();
    }

    @Test
    public void requestBookingIdWithRequestSpec() {

        given().
            spec(requestSpec).
        when().
            get("1").
        then().
            assertThat().
            statusCode(200);
    }

    private static ResponseSpecification responseSpec;

    @BeforeClass
    public static void createResponseSpecification() {

        responseSpec = new ResponseSpecBuilder().
            expectStatusCode(200).
            expectContentType(ContentType.JSON).
            build();
    }

    @Test
    public void requestBookingIdWithRequestSpecAndResponseSpec() {

        given().
            spec(requestSpec).
        when().
            get("https://restful-booker.herokuapp.com/booking/1").
        then().
            spec(responseSpec).
        and().
            assertThat().
            body("depositpaid", equalTo(true));
    }

    @Test
    public void extractFirstNameFromResponseBody_assertEqualToJim() {

        String firstName =

        given().
            spec(requestSpec).
        when().
            get("https://restful-booker.herokuapp.com/booking/1").
        then().
            extract().
            path("firstname");

        Assert.assertEquals("Jim", firstName);
    }
}
