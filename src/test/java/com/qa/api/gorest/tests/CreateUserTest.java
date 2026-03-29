package com.qa.api.gorest.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest
{
	@Test
	public void createUserTest()
	{
		User user = new User("Sudhi", StringUtils.getRandomEmailId(),"male", "active");
		Response response =	restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT,user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON); // restClient is coming from BaseTest class which has Object of RestClient class
		Assert.assertEquals(response.jsonPath().getString("name"),"Sudhi");
		Assert.assertNotNull(response.jsonPath().getString("id"));
	}
	
	@Test
	public void createUserTestWithJsonString()
	{
		String userJson = "{\r\n"
				+ "    \"name\": \"Amith\",\r\n"
				+ "    \"gender\": \"male\",\r\n"
				+ "    \"email\": \""+StringUtils.getRandomEmailId()+"\",\r\n"
				+ "    \"status\": \"active\"\r\n"
				+ "}";

		Response response =	restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT,userJson, null, null, AuthType.BEARER_TOKEN, ContentType.JSON); // restClient is coming from BaseTest class which has Object of RestClient class
		Assert.assertEquals(response.jsonPath().getString("name"),"Amith");
		Assert.assertNotNull(response.jsonPath().getString("id"));
	}
	
	@Test
	public void createUserTestWithJsonFile() throws IOException
	{
		String emailId =StringUtils.getRandomEmailId();
		
		String rawJson = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/user.json")));
		String updatedJson =  rawJson.replace("{{email}}", emailId);
		File userFile = new File("./src/test/resources/jsons/user.json");
		Response response =	restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT,updatedJson, null, null, AuthType.BEARER_TOKEN, ContentType.JSON); // restClient is coming from BaseTest class which has Object of RestClient class
		Assert.assertEquals(response.jsonPath().getString("name"),"AnirudhSV");
		Assert.assertNotNull(response.jsonPath().getString("id"));
	}

}
