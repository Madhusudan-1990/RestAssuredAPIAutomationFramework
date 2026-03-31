package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest 
{
	@Test
	public void updateUserTest()
	{
		/*
		 * Complete Update User Flow Test.
		 */
		//1. Create a User --> POST
//		User user = new User("Sudhi", StringUtils.getRandomEmailId(),"male", "active");
		
		User user	=	User.builder()
						.name("Sudhi")
						.email(StringUtils.getRandomEmailId())
						.status("active")
						.gender("male")
						.build();
		
		Response responsePost =	restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT,user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON); // restClient is coming from BaseTest class which has Object of RestClient class
		Assert.assertEquals(responsePost.jsonPath().getString("name"),"Sudhi");
		Assert.assertNotNull(responsePost.jsonPath().getString("id"));
		
		//Fetch the User ID 
		String userId = responsePost.jsonPath().getString("id");
		System.out.println("User ID ===========> " + userId);
		
		//2. Fetch the User ID --> GET
		
		Response responseGet =	restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGet.statusLine().contains("OK"));
		Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
		
		//3. Update the user using the same User Id --> PUT
		
		user.setName("Sudhi Automation"); //Updating name using setters
		user.setStatus("inactive"); //Updating status using setters
		Response responsePut = restClient.put(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, user, null, null,  AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responsePut.statusLine().contains("OK"));
		Assert.assertEquals(responsePut.jsonPath().getString("id"), userId);
		Assert.assertEquals(responsePut.jsonPath().getString("name"), "Sudhi Automation");
		Assert.assertEquals(responsePut.jsonPath().getString("status"), "inactive");
		
		//4. Again validate the User after Update --> GET
		responseGet =	restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGet.statusLine().contains("OK"));
		Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
		Assert.assertEquals(responsePut.jsonPath().getString("name"), "Sudhi Automation");
		Assert.assertEquals(responsePut.jsonPath().getString("status"), "inactive");
		
	}
}
