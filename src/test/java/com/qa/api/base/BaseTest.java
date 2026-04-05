package com.qa.api.base;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.qa.api.client.RestClient;
import com.qa.api.manager.ConfigManager;
import com.qa.api.mocking.WireMockSetup;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

//@Listeners(ChainTestListener.class)
public class BaseTest 
{
	protected RestClient restClient;
	
	//****************************** API Base URLs *******************************/
	protected  static String BASE_URL_GOREST;
	protected  static String BASE_URL_CONTACTS;
	protected  static String BASE_URL_RESTFUL;
	protected  static String BASE_URL_BASIC_AUTH;
	protected  static String BASE_URL_PRODUCTS;
	protected  static String BASE_URL_SPOTIFY_ACCOUNT;
	protected  static String BASE_URL_SPOTIFY_API;
	protected  static String BASE_URL_ERGAST_CIRCUIT;
	protected  static String BASE_URL_MOCK_SERVER = "http://localhost:8089"; 

	
	//****************************** API EndPoints *******************************/
	protected final static String GOREST_USERS_ENDPOINT = "/public/v2/users";
	protected final static String CONTACTS_LOGIN_ENDPOINT = "/users/login";
	protected final static String CONTACTS_ENDPOINT = "/contacts";
	protected final static String RESTFUL_OBJECTS_ENDPOINT = "/objects";
	protected final static String BASIC_AUTH_ENDPOINT = "/basic_auth";
	protected final static String PRODUCTS_ENDPOINT = "/products";
	protected final static String SPOTIFY_ACCOUNT_ENDPOINT = "/api/token";
	protected final static String SPOTIFY_API_ENDPOINT = "/v1/albums/4aawyAB9vmqN3uQ7FjRGTy";
	protected final static String ERGAST_CIRCUIT_ENDPOINT = "/api/f1/2017/circuits.xml";
	protected final static String MOCK_SERVER_ENDPOINT = "/api/users";
	
	@BeforeTest
	public void initSetup()
	{
		RestAssured.filters(new AllureRestAssured());
		BASE_URL_GOREST = ConfigManager.get("baseurl.gorest").trim();
		BASE_URL_CONTACTS = ConfigManager.get("baseurl.contact").trim();
		BASE_URL_RESTFUL = ConfigManager.get("baseurl.restful").trim();
		BASE_URL_BASIC_AUTH = ConfigManager.get("baseurl.basicsauth").trim();
		BASE_URL_PRODUCTS = ConfigManager.get("baseurl.products").trim();
		BASE_URL_SPOTIFY_ACCOUNT = ConfigManager.get("baseurl.spotifyaccount").trim();
		BASE_URL_SPOTIFY_API = ConfigManager.get("baseurl.spotifyapi").trim();
		BASE_URL_ERGAST_CIRCUIT = ConfigManager.get("baseurl.circuit").trim();
	}
	
	//3a
	@BeforeTest
	public void setup() 
	{
			restClient = new RestClient();
			WireMockSetup.startWireMockServer();
	}
	
	@AfterTest
	public void stopMockServer() 
	{
			WireMockSetup.stopWireMockServer();
	}
}
