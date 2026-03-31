package com.qa.api.base;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
//3

import com.qa.api.client.RestClient;
public class BaseTest 
{
	protected RestClient restClient;
	
	//****************************** API Base URLs *******************************/
	protected final static String BASE_URL_GOREST = "https://gorest.co.in";
	protected final static String BASE_URL_CONTACTS = "https://thinking-tester-contact-list.herokuapp.com";
	protected final static String BASE_URL_RESTFUL = "https://api.restful-api.dev";
	protected final static String BASE_URL_BASIC_AUTH = "https://the-internet.herokuapp.com";
	protected final static String BASE_URL_PRODUCTS = "https://fakestoreapi.com";
	protected final static String BASE_URL_SPOTIFY_ACCOUNT = "https://accounts.spotify.com";
	protected final static String BASE_URL_SPOTIFY_API = "https://api.spotify.com";
	
	
	//****************************** API EndPoints *******************************/
	protected final static String GOREST_USERS_ENDPOINT = "/public/v2/users";
	protected final static String CONTACTS_LOGIN_ENDPOINT = "/users/login";
	protected final static String CONTACTS_ENDPOINT = "/contacts";
	protected final static String RESTFUL_OBJECTS_ENDPOINT = "/objects";
	protected final static String BASIC_AUTH_ENDPOINT = "/basic_auth";
	protected final static String PRODUCTS_ENDPOINT = "/products";
	protected final static String SPOTIFY_ACCOUNT_ENDPOINT = "/api/token";
	protected final static String SPOTIFY_API_ENDPOINT = "/v1/albums/4aawyAB9vmqN3uQ7FjRGTy";
	
	//3a
	@BeforeTest
	public void setup() 
	{
			restClient = new RestClient();
	}
}
