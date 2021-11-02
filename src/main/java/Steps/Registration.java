package Steps;

import Models.Request.Request;
import Models.Resoponse.Failure;
import Models.Resoponse.Success;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Registration {
    String email = "eve.holt@reqres.in";
    String password = "pistol";
    String emailForFailure = "sydney@fife";

    Request request = new Request();
    ObjectMapper objectMapper = new ObjectMapper();

    public Response request(String parameters) {
        return given()
                .contentType("application/json")
                .body(parameters)
                .post("https://reqres.in/api/register");
    }

    //Implement SUCCESSFUL scenario with the following parameters
    public String correctMailAndPassword() throws JsonProcessingException {
        request.setEmail(email);
        request.setPassword(password);
        return objectMapper.writeValueAsString(request);
    }

    //Implement UNSUCCESSFUL scenario with the following parameters
    public String falseMail() throws JsonProcessingException {
        request.setEmail(emailForFailure);
        return objectMapper.writeValueAsString(request);
    }


    //Deserialize JSON based on status code
    public Success deserializeSuccess(Response response){
        return response.getBody().as(Success.class);
    }

    public Failure deserializeFailed(Response response){
        return response.getBody().as(Failure.class);

    }

}
