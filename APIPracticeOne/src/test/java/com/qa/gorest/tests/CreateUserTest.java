package com.qa.gorest.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.StringUtils;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class CreateUserTest extends BaseTest {

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

        Integer id = restClient.post(GOREST_ENDPOINT,"json",user,true, true)
                .then().log().all().assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
                .body("name",equalTo(name))
                .body("email",notNullValue())
                .body("id",notNullValue())
                .extract().path("id");

        RestClient getClient = new RestClient(prop,baseURI);
        getClient.get(GOREST_ENDPOINT +"/"+id,true,true).then()
                .assertThat().statusCode(APIHttpStatus.OK_200.getCode());

    }


    @Test(dataProvider = "data")
    public void deserializeUserTest(String name,String gender,String status){

        User user = new User(name, StringUtils.getRandomEmailId(),gender,status);

        Response res = restClient.post(GOREST_ENDPOINT,"json",user,true, true);
        String responseBody = res.getBody().asString();

        ObjectMapper obj  = new ObjectMapper();
        try {
            User user1 = obj.readValue(responseBody, User.class);
            Assert.assertEquals(user.getName(),user1.getName());



        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

}
