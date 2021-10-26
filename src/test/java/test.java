import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.hasItems;

public class test {

    Response response = given().when().get("http://ergast.com/api/f1/2017/circuits.json").then().extract().response();
    public String CircuitID1 = response.jsonPath().getString("MRData.CircuitTable.Circuits[1].circuitId");
    public String CircuitID5 = response.jsonPath().getString("MRData.CircuitTable.Circuits[5].circuitId");
    public String CountryName1 = response.jsonPath().getString("MRData.CircuitTable.Circuits[1].Location.country");
    public String CountryName5 = response.jsonPath().getString("MRData.CircuitTable.Circuits[5].Location.country");

    //  Use dataprovider for parametrization of circuitId and country
    @DataProvider(name = "DataProvider")
    public Object[][] dpMethod(){
        return new Object[][]   {{CircuitID1, CountryName1},
                                {CircuitID5, CountryName5}};
    }




//    @DataProvider(name = "DataProvider")
//    public Object[][] dpMethod(){
//        return new Object[][]   {{"americas", "USA"},
//                                {"hungaroring", "Hungary"}};
//    }


    @Test(priority = 1)
    public  void getCircuits(){
        //  Call http://ergast.com/api/f1/2017/circuits.json API
        //  Extract 2 circuits, with index 1 and 5
        Response response = given().when().get("http://ergast.com/api/f1/2017/circuits.json").then().extract().response();
        System.out.println(response.jsonPath().getString("MRData.CircuitTable.Circuits[1]"));
        System.out.println(response.jsonPath().getString("MRData.CircuitTable.Circuits[5]"));


    }


    @Test(dataProvider = "DataProvider", priority = 2)
    public void secondStep(String circuitId, String country){
        //  Call http://ergast.com/api/f1/circuits/{circuitId}.json where circuitId is equals to extracted values from the second step
        //  Validate country is correct in both cases
        given().when().get("http://ergast.com/api/f1/circuits/{circuitId}.json",circuitId).then().assertThat().
                body("MRData.CircuitTable.Circuits.Location.country", hasItems(country)).log().body();
    }

}

