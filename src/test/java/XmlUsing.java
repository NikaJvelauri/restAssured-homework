import io.restassured.RestAssured;
import io.restassured.internal.path.xml.NodeChildrenImpl;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class XmlUsing {

    @BeforeMethod
    public void baseURI() {
        RestAssured.baseURI = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";
    }

    @Test(priority =1)
    public void getCountryCount(){
        NodeChildrenImpl country =  RestAssured
                .given()
                .when()
                .get("/ListOfContinentsByName")
                .then().extract().path("ArrayOftContinent.tContinent.sName");

        //Using XMLPath  validate count of all 'sName' node
        System.out.println("Count of all 'sName' node: " + country.size());
        Assert.assertEquals(country.size(),6);


    }

    @Test(priority = 2)
    public void getAllNames(){
        NodeChildrenImpl country =  RestAssured
                .given()
                .when()
                .get("/ListOfContinentsByName")
                .then().extract().path("ArrayOftContinent.tContinent.sName");

        //Using XMLPath  validate list of all 'sName' node's value
        System.out.println("List of all 'sName' node's value: " + country.list());
        List<String> list1 = Arrays.asList("Africa", "Antarctica", "Asia", "Europe", "Ocenania", "The Americas");
        Assert.assertEquals(country.list().toString(), list1.toString());

    }

    @Test(priority = 3)
    public void validateNameWithCode(){
        Response country =  RestAssured
                .given()
                .when()
                .get("/ListOfContinentsByName")
                .then().extract().response();

        //Using XMLPath validate 'sName' node result with value of sCode equals to 'AN
        //https://stackoverflow.com/questions/59501477/getting-a-value-from-xml-response-in-rest-assured
        //ამ კოდის წყარო:დ ცოტა მაწვალა ამან  :დდ
        String stringResponse = country.asString();
        XmlPath xmlPath = new XmlPath(stringResponse);
        String status = xmlPath.get("ArrayOftContinent.tContinent.findAll { it.sCode == 'AN' }.sName");
        System.out.println(status);
        Assert.assertEquals(status,"Antarctica");

    }


    @Test(priority = 4)
    public void lasttContinentName(){
        String country =  RestAssured
                .given()
                .when()
                .get("/ListOfContinentsByName")
                .then().extract().path("ArrayOftContinent.tContinent[-1].sName");

        //Using XMLPath  validate the last tContinent node's sName value
        System.out.println(country);
        Assert.assertEquals(country,"The Americas");

    }
}
