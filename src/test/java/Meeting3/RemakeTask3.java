package Meeting3;

import Models.Resoponse.Failure;
import Models.Resoponse.Success;
import Models.Resoponse.UserInfo;
import Steps.Registration;
import Steps.UserCheck;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RemakeTask3 {
    String id = "4";
    String token = "QpwL5tke4Pnpja7X4";
    String errorMessage = "Missing password";
    String name = "morpheus";
    String job = "leader";

    Registration registration = new Registration();
    UserCheck usercheck = new UserCheck();
    Success success = new Success();
    Failure failure = new Failure();
    UserInfo userInfo = new UserInfo();


    @Test( priority = 1)
    public void FailedRegistration() throws JsonProcessingException {
        //Validate error, id and token values
        Response response = registration.request(registration.falseMail());
        failure = registration.deserializeFailed(response);

        if(response.statusCode() == 400) {
            Assert.assertEquals(failure.getError(), errorMessage);

        }

    }

    @Test( priority = 2)
    public void SuccessRegistration() throws JsonProcessingException {
        //Validate error, id and token values
        Response response = registration.request(registration.correctMailAndPassword());
        success = registration.deserializeSuccess(response);

        if(response.statusCode()==200){
            Assert.assertEquals(success.getId(),id);
            Assert.assertEquals(success.getToken(), token);
        }

    }



    @Test(priority = 3)
    public void userInfoCheck() throws IOException {
        Response response = usercheck.request1(usercheck.uploadNameAndJob());
        userInfo = usercheck.deserialize(response);
        if(response.statusCode()==201){
            Assert.assertEquals(userInfo.getName(),name);
            Assert.assertNotNull(userInfo.getId());
            Assert.assertEquals(userInfo.getJob(),job);
            Assert.assertNotNull(userInfo.getCreatedAt());



        }
    }

}
