package com.qa.api.mocking.tests;

import java.io.IOException;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.mocking.APIMocks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class MockCreateUserAPITest extends BaseTest
{	
	@Test
	public void createFakeUserTests() throws IOException
	{
		APIMocks.defineCreateUserMock();
		String dummyUserJson = APIMocks.updateJsonFileWithRandomId();
		Response response = restClient.post(BASE_URL_MOCK_SERVER, MOCK_SERVER_ENDPOINT, dummyUserJson, null,null, AuthType.NO_AUTH, ContentType.JSON);
		response.then().assertThat().statusCode(201);
	}
}
