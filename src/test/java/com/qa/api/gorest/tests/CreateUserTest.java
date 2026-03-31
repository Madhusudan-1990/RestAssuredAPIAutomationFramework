package com.qa.api.gorest.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.ExcelUtil;
import com.qa.api.utils.StringUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Epic("Epic 100 : Go Rest Create User API Feature")
@Story("US 100 : Feature go rest api - get user api")
public class CreateUserTest extends BaseTest
{
	private String tokenId;
	@BeforeClass
	public void setupToken() // Bearer token method(setupToken) is in @BeforeClass as the token generation is not dynamic. Hence @BeforeMethod is not required to be used. 
	{
		tokenId = "1f0fe4cd6134811ae608a07db6f26ebd5bc0fd661f98793aaca526df1ccd0915";
	    ConfigManager.set("bearertoken", tokenId);
	}
	
	@DataProvider
	public Object[][] getUserData()
	{
		return new Object[][] {
				{"Priyanka","female","active"},
				{"Ranjit","male","inactive"},
				{"Elmar","male","active"}
		};
	}
	
	@DataProvider
	public Object[][] getUserExcelData()
	{
		return ExcelUtil.readExcelData(AppConstants.CREATE_USER_SHEET_NAME);
	}
	
	@Description("Creating a User")
	@Owner("Madhusudan J")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider = "getUserExcelData")
	public void createUserTest(String name,String gender,String status)
	{
		ChainTestListener.log("Starting Create User Test......");;
		User user = new User(null,name, StringUtils.getRandomEmailId(),gender, status);// Even though null is passed for id in the constructor, as part of the response it will be excluded due to @JsonInclude(Include.NON_NULL) added in User.java class
		Response response =	restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT,user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON); // restClient is coming from BaseTest class which has Object of RestClient class
		Assert.assertEquals(response.jsonPath().getString("name"),name);
		Assert.assertEquals(response.jsonPath().getString("gender"),gender);
		Assert.assertEquals(response.jsonPath().getString("status"),status);
		Assert.assertNotNull(response.jsonPath().getString("id"));
		ChainTestListener.log("User ID is : " +response.jsonPath().getString("id"));
	}
	@Description("Creating a User with Json")
	@Owner("Madhusudan J")
	@Severity(SeverityLevel.CRITICAL)
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
	@Description("Creating a User with Json File")
	@Owner("Madhusudan J")
	@Severity(SeverityLevel.CRITICAL)
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
