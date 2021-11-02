package Meeting3;
import OldMeeting3Task.Registration.Create;
import OldMeeting3Task.Registration.Failure;
import OldMeeting3Task.Registration.Success;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class test2 {


    @BeforeMethod
    public void baseURI() {
        RestAssured.baseURI = "https://reqres.in/api/register";
    }

    @Test(priority = 1)
    public void Success() {

        JSONObject requestParams = new JSONObject();

        requestParams.put("email", "eve.holt@reqres.in");
        requestParams.put("password", "pistol");


        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .when()
                .post()
                .then().extract().response();



        ResponseBody body = response.getBody();

        // Use the RegistrationSuccessResponse class instance to Assert the values of Response.
        // Deserialize the Response body into RegistrationSuccessResponse
        if (response.statusCode() == 200) {
            Success Success = body.as(Success.class);
            Assert.assertEquals(Success.token, "QpwL5tke4Pnpja7X4");
            Assert.assertEquals(Success.id, 4);
            System.out.println(Success.token);
            System.out.println(Success.id);
        }
    }






    @Test(priority = 2)
    public void Failure() {
        JSONObject requestParams = new JSONObject();

        requestParams.put("email", "sydney@fife");

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .when()
                .post()
                .then().extract().response();

        ResponseBody body = response.getBody();

        if (response.statusCode() == 400) {

            Failure Failure = body.as(Failure.class);
            Assert.assertEquals(Failure.error, "Missing password");
            System.out.println(Failure.error);
        }
    }


    @Test(priority = 3)
    public void createAndCall() {
        RestAssured.baseURI = "https://reqres.in/api/users";

        JSONObject requestParams = new JSONObject();

        requestParams.put("name", "morpheus");
        requestParams.put("job", "leader");

        Response response = given().header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .when()
                .post()
                .then().extract().response();

        ResponseBody body = response.getBody();

        if (response.statusCode() == 201) {
            Create Create = body.as(Create.class);
            System.out.println(Create.name);
            System.out.println(Create.job);
            System.out.println(Create.id);
            System.out.println(Create.createdAt);

        }
    }
}



