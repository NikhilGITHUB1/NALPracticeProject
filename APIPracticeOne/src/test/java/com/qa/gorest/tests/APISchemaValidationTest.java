package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class APISchemaValidationTest extends BaseTest {

    @BeforeClass
    public void setUp(){
        restClient = new RestClient(prop,baseURI);
    }

    @DataProvider
    public Object[][] data(){

        return new Object[][]{
                {"nikhil", "male", "active"},
                {"kalyani", "female", "active"}
        };
    }


    @Test(dataProvider = "data")
    public void createUserTest(String name,String gender,String status){

        User user = new User(name, StringUtils.getRandomEmailId(),gender,status);

        restClient.post(GOREST_ENDPOINT,"json",user,true, true)
                .then().log().all().assertThat().statusCode(APIHttpStatus.CREATED_201.getCode()).
                body(matchesJsonSchemaInClasspath("createuserschema.json"));

    }


}
