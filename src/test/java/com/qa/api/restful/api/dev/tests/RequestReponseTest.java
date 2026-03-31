package com.qa.api.restful.api.dev.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RequestReponseTest extends BaseTest
{
	@Test
	public void getDevTest()
	{
		Response response = restClient.get(BASE_URL_RESTFUL, RESTFUL_OBJECTS_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY); // Pass ContentType.ANY when not sure of ContentType
		Assert.assertEquals(response.statusCode(), 200);
	}

}
