package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.JsonUtils;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetSingleUserWithDeserializationTest extends BaseTest
{
	private String tokenId;
	@BeforeClass
	public void setupToken() // Bearer token method(setupToken) is in @BeforeClass as the token generation is not dynamic. Hence @BeforeMethod is not required to be used. 
	{
		tokenId = "1f0fe4cd6134811ae608a07db6f26ebd5bc0fd661f98793aaca526df1ccd0915";
	    ConfigManager.set("bearertoken", tokenId);
	}
	@Test
	public void createUserTest()
	{
		User user = new User(null,"Sudhi", StringUtils.getRandomEmailId(),"male", "active"); // Even though null is passed for id in the constructor, as part of the response it will be excluded due to @JsonInclude(Include.NON_NULL) added in User.java class
		Response response =	restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT,user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON); // restClient is coming from BaseTest class which has Object of RestClient class
		Assert.assertEquals(response.jsonPath().getString("name"),"Sudhi");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		
		String userId = response.jsonPath().getString("id");
		
		//GET
		//2. GET : Fetch the user using same user id
		Response responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGet.statusLine().contains("OK"));
		
		User userResponse = JsonUtils.deserialize(responseGet, User.class);
		
		Assert.assertEquals(userResponse.getName(), user.getName());
	}
	
}
