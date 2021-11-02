package Steps;

import Models.Resoponse.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class UserCheck {
    UserInfo info = new UserInfo();
    ObjectMapper objectMapper = new ObjectMapper();

    public Response request1(String parameters) {
//        RestAssured.baseURI = "https://reqres.in/api/users";
        return given()
                .contentType("application/json")
                .body(parameters)
                .post("https://reqres.in/api/users");
    }

    public String uploadNameAndJob() throws JsonProcessingException {
        info.setName("morpheus");
        info.setJob("leader");
        return objectMapper.writeValueAsString(info);
    }


    public UserInfo deserialize(Response response){
        return response.getBody().as(UserInfo.class);
    }

}
