package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.utils.JsonPathValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.invoke.StringConcatFactory;
import java.util.List;

public class CircuitTest extends BaseTest {

    @BeforeClass
    public void setUp(){
        restClient = new RestClient(prop,baseURI);
    }

    @Test
    public void getCircuitTest(){

        Response circuitResponse = restClient.get(CIRCUIT_ENDPOINT+"/2017/circuits.json",false,false);

        circuitResponse.then().
                        assertThat().
                                statusCode(APIHttpStatus.OK_200.getCode());

        JsonPathValidator js = new JsonPathValidator();
        List<String> countryList = js.readList(circuitResponse,"$.MRData.CircuitTable.Circuits[?(@.circuitId == 'shanghai')].Location.country");
        Assert.assertTrue(countryList.contains("China"));
    }

}
