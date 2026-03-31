package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest 
{
	/*
	 * Delete User 
	 */
	
	@Test
	public void deleteUserTest()
	{
		/*
		 * Complete Delete User Flow Test.
		 */
		//1. Create a User --> POST
//		User user = new User("Madhu", StringUtils.getRandomEmailId(),"male", "active");
		
		User user	=	User.builder()
						.name("Madhu")
						.email(StringUtils.getRandomEmailId())
						.status("inactive")
						.gender("male")
						.build();
		
		Response responsePost =	restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT,user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON); // restClient is coming from BaseTest class which has Object of RestClient class
		Assert.assertEquals(responsePost.jsonPath().getString("name"),"Madhu");
		Assert.assertNotNull(responsePost.jsonPath().getString("id"));
		
		//Fetch the User ID 
		String userId = responsePost.jsonPath().getString("id");
		System.out.println("User ID ===========> " + userId);
		
		//2. Fetch the User ID --> GET
		
		Response responseGet =	restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGet.statusLine().contains("OK"));
		Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
		
		//3. Delete the user using the same User Id --> DELETE
		
		Response responseDelete = restClient.delete(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null,  AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseDelete.statusLine().contains("No Content"));
		
		//4. Again validate the User after Update --> GET
		responseGet =	restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.statusCode(), 404); 
		Assert.assertEquals(responseGet.jsonPath().getString("message"),"Resource not found");
		
	}
}
