package com.qa.api.mocking.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.mocking.APIMocks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class MockGetUserAPITets extends BaseTest
{	
	@Test
	public void getDummyUserMockAPITest() throws IOException
	{
	
		 APIMocks.getDummyUser(); 
		Response response = restClient.get(BASE_URL_MOCK_SERVER, MOCK_SERVER_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		
		response.then().assertThat().statusCode(200);
		
	}
	
	@Test
	public void defineGetUserMockWithJsonFileTest() throws IOException
	{
		APIMocks.defineGetUserMockWithJsonFile(); 
		Response response = restClient.get(BASE_URL_MOCK_SERVER, MOCK_SERVER_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
		
	}

	@Test
	public void defineGetUserMockWithQueryParamTest() throws IOException
	{
		 APIMocks.defineGetUserMockWithQueryParam();
		Map<String, String> userQueryMap = new HashMap<String, String>();
		userQueryMap.put("name", "tom");
		Response response = restClient.get(BASE_URL_MOCK_SERVER, MOCK_SERVER_ENDPOINT, userQueryMap, null, AuthType.NO_AUTH, ContentType.ANY);
		
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
		
	}
}
