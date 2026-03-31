package com.qa.api.spotify.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.utils.JsonPathValidatorUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SpotifyAPITest extends BaseTest
{
	private String accessTokenId;
	@BeforeMethod
	public void getOAuth2Tokens()
	{
		Response response = restClient.post(BASE_URL_SPOTIFY_ACCOUNT, SPOTIFY_ACCOUNT_ENDPOINT,ConfigManager.get("granttype"),
							ConfigManager.get("spotifyclientid"),ConfigManager.get("spotifyclientsecret"),
							ContentType.URLENC);
		
		accessTokenId = response.jsonPath().getString("access_token");
		System.out.println("Access Token Id =============> " + accessTokenId);
		ConfigManager.set("bearertoken", accessTokenId); // Updating the Bearer Token
		
	}
	
	@Test
	public void getAlbumTest()
	{
	  Response responseGet = restClient.get(BASE_URL_SPOTIFY_API, SPOTIFY_API_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
	   List<Map<String, Object>> data = JsonPathValidatorUtil.readListOfMaps(responseGet,"$.images[?(@.height==300)].['url','width']" );
	   Assert.assertEquals(responseGet.getStatusCode(), 200);
  
	}

}
