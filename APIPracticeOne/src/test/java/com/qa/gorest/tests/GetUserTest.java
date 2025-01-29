package com.qa.gorest.tests;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetUserTest extends BaseTest {

    @BeforeClass
    public void getUserSetUp(){
        restClient = new RestClient(prop,baseURI);
    }

    @Test
    public void getAllUsersTest(){
        restClient.get(GOREST_ENDPOINT,true,false)
                .then().log().all().assertThat().statusCode(200);
    }

}
