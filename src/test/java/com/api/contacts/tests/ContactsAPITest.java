package com.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsCredentials;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactsAPITest extends BaseTest
{
	private String tokenId;
	@BeforeMethod
	public void getToken() // Adding getToken() under @BeforeMethod as to prevent testcase failing from token expiry. The token will be generated each time before each @Test()method. 
	{
	  ContactsCredentials creds = ContactsCredentials.builder()
							.email("sdettester2025@gmail.com")
							.password("Test@1234")
							.build();
	  
	 Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_ENDPOINT, creds, null, null, AuthType.NO_AUTH, ContentType.JSON);
	 Assert.assertEquals(response.statusCode(),200);
	 tokenId = response.jsonPath().getString("token");
	 System.out.println("Contacts Login Token =========> " + tokenId);
	 ConfigManager.set("bearertoken", tokenId); // Updating the bearer token to ContactsCredentials token
	 
	}
	
	@Test
	public  void getAllContactsTest()
	{
		
		restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	}
}
