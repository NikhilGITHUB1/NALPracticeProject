package com.qa.gorest.client;

import com.qa.gorest.frameworkexception.APIFrameworkException;
import com.qa.gorest.pojo.User;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import java.util.Map;
import java.util.Properties;

public class RestClient {

    private RequestSpecBuilder specBuilder;
    private Properties prop;
    private String baseURI;

    private boolean isAuthorizationHeaderAdded = false;

    public RestClient(Properties prop, String baseURI){
        specBuilder = new RequestSpecBuilder();
        this.prop = prop;
        this.baseURI = baseURI;
    }

    public void addAuthorizationHeader(){
        if(!isAuthorizationHeaderAdded){
            specBuilder.addHeader("Authorization","Bearer " + prop.getProperty("tokenId"));
            isAuthorizationHeaderAdded = true;
        }
    }

    private void setRequestContentType(String contentType){
        switch (contentType.toLowerCase().trim()){
            case "json": specBuilder.setContentType(ContentType.JSON);
            break;
            case "xml":specBuilder.setContentType(ContentType.XML);
            break;
            default:
                System.out.println("content type is not matching");
                throw new APIFrameworkException("CONTENT TYPE IS INCORRECT");
        }

    }

    private RequestSpecification createRequestSpec(boolean includeAuth){
        specBuilder.setBaseUri(baseURI);
        if(includeAuth){
            addAuthorizationHeader();
        }
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Map<String,String> headersMap, boolean includeAuth){
        specBuilder.setBaseUri(baseURI);
        if (includeAuth){
            addAuthorizationHeader();
        }
        if(headersMap!=null){
            specBuilder.addHeaders(headersMap);
        }
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Map<String,String> headersMap,Map<String,Object> queryParams,boolean includeAuth){
        specBuilder.setBaseUri(baseURI);
        if(includeAuth){
            addAuthorizationHeader();
        }
        if(headersMap!=null){
            specBuilder.addHeaders(headersMap);
        }
        if(queryParams!=null){
            specBuilder.addQueryParams(queryParams);
        }
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Object requestBody,String contentType,boolean includeAuth){
        specBuilder.setBaseUri(baseURI);
        setRequestContentType(contentType);
        if(includeAuth){
            addAuthorizationHeader();
        }
        specBuilder.setBody(requestBody);
        return specBuilder.build();
    }

    public Response get(String serviceUrl,Map<String,String> headersMap,Map<String,Object> queryParams,boolean includeAuth,boolean log){
            if(log){
                return RestAssured.given(createRequestSpec(headersMap,queryParams,includeAuth)).log().all().
                        when().get(serviceUrl);
            }
            else{
                return RestAssured.given(createRequestSpec(headersMap,queryParams,includeAuth)).
                        when().get(serviceUrl);
            }
    }

    public Response get(String serviceUrl, boolean log, boolean includeAuth) {
        if (log){
           return RestAssured.given(createRequestSpec(includeAuth))
                    .log().all().
                    when().get(serviceUrl);
            }
        else return RestAssured.given(createRequestSpec(includeAuth)).
                when().get(serviceUrl);
    }


    public Response post(String serviceUrl, String contentType, User user, boolean log, boolean includeAuth) {
        if(log){
            return RestAssured.given(createRequestSpec(user, contentType, includeAuth)).log().all()
                    .when().post(serviceUrl);
        }
        else{
            return RestAssured.given(createRequestSpec(user, contentType, includeAuth))
                    .when().post(serviceUrl);
        }

    }

    public String getAccessToken(String serviceUrl, String grantType, String clientId, String clientSecret) {
        //1. POST - get the access token
        RestAssured.baseURI = "https://test.api.amadeus.com";
        String accessToken  = given().log().all()
                .contentType(ContentType.URLENC)
                .formParam("grant_type",grantType)
                .formParam("client_id",clientId)
                .formParam("client_secret",clientSecret)
                .when().post(serviceUrl)
                .then().assertThat().statusCode(200)
                .extract().path("access_token");

        System.out.println(accessToken);

        return accessToken;

    }
}
