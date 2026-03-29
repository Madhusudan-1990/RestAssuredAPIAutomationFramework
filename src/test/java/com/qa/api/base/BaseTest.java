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
	
	
	//****************************** API EndPoints *******************************/
	protected final static String GOREST_USERS_ENDPOINT = "/public/v2/users";
	
	//3a
	@BeforeTest
	public void setup() 
	{
			restClient = new RestClient();
	}
}
