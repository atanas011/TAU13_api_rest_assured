package tests;

import com.tngtech.java.junit.dataprovider.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@RunWith(DataProviderRunner.class)
public class ParameterizedTest {

    @Test
    @UseDataProvider("zipCodesAndPlaces")
    public void checkResBody(String country, String zipCode, String place) {
        given().
            pathParam("country", country).pathParam("zipCode", zipCode).
        when().
            get("http://zippopotam.us/{country}/{zipCode}").
        then().
            assertThat().
            body("places[0].'place name'", equalTo(place));
    }

    @DataProvider
    public static Object[][] zipCodesAndPlaces() {
        return new Object[][]{
                {"us", "90210", "Beverly Hills"},
                {"us", "12345", "Schenectady"},
                {"ca", "B2R", "Waverley"},
                {"nl", "1001", "Amsterdam"}
        };
    }
}