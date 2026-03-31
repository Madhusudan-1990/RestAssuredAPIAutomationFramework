package com.qa.api.schema.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GoRestUserAPIScehmaTest extends BaseTest
{
	
	@Test
	public void getUsersAPISchemaTest()
	{
		ConfigManager.set("bearertoken", "1f0fe4cd6134811ae608a07db6f26ebd5bc0fd661f98793aaca526df1ccd0915");
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
		
		Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/getuserschema.json"));
	}
	
	@Test
	public void createUserAPISchemaTest()
	{
		ConfigManager.set("bearertoken", "1f0fe4cd6134811ae608a07db6f26ebd5bc0fd661f98793aaca526df1ccd0915");
		RestAssured.baseURI = "https://gorest.co.in";
		
			User user =	User.builder()
						.name("api")
						.status("active")
						.email(StringUtils.getRandomEmailId())
						.gender("female")
						.build();
		
//		RestAssured.given().log().all()
//						.header("Authorization","Bearer 1f0fe4cd6134811ae608a07db6f26ebd5bc0fd661f98793aaca526df1ccd0915")
//						.body(new File("./src/test/resources/jsons/createuser.json"))
//						.contentType(ContentType.JSON)
//					.when()
//						.post("/public/v2/users")
//					.then().log().all()
//						.assertThat()
//							.statusCode(201)
//							.and().body(matchesJsonSchemaInClasspath("createuserschema.json"));
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null,null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/createuserschema.json"));
								
	}

}
