
import io.restassured.response.Response;

import org.json.simple.JSONObject;

import org.testng.annotations.Test;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.lessThan;


public class basics2 {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String currentTime = dtf.format(now);

    //  - Call https://chercher.tech/sample/api/product/read API
    //  - Validate last record's name value
    //  - Validate that all records 'created' time is less than current time
    @Test(priority = 1)
    public  void test1(){
        Response response = given()
                .when()
                .get("https://chercher.tech/sample/api/product/read")
                .then().assertThat().
                        body("records.created", everyItem((lessThan(currentTime)))).extract().response();

        System.out.println("Last record's name value: " + response.jsonPath().getString("records[-1].name"));


    }

    //  - Call https://reqres.in/api/users
    //  - Post new user with any 2 parameters
    //  - Log response data if status code is equls to 201
    @Test(priority = 2)
    public  void test2(){

        JSONObject requestParams = new JSONObject();
        requestParams.put("first_name", "Nika");
        requestParams.put("last_name", "Jvelauri");

        given().
                header("Content-Type", "application/json").
                body(requestParams.toJSONString()).
                when().
                post("https://reqres.in/api/users").
                then().assertThat().statusCode(201).log().body();

    }
}
