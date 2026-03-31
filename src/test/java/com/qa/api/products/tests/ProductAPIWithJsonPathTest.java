package com.qa.api.products.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidatorUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPIWithJsonPathTest extends BaseTest 
{
	@Test
	public void getProductTest()
	{
		Response response = restClient.get(BASE_URL_PRODUCTS, PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
		
		List<Number>prices = JsonPathValidatorUtil.readList(response, "$[?(@.price>50)].price");
		
		List<Number>ids = JsonPathValidatorUtil.readList(response, "$[?(@.price>50)].id");
		
		List<Number>rates = JsonPathValidatorUtil.readList(response, "$[?(@.price>50)].rating.rate");
		
		List<Integer>counts = JsonPathValidatorUtil.readList(response, "$[?(@.price>50)].rating.count");
		
		List<Map<String, Object>> idTitleList = JsonPathValidatorUtil.readListOfMaps(response, "$.[*].['id','title']");
		System.out.println(idTitleList);
		
		List<Map<String, Object>> idTitleCategoryList = JsonPathValidatorUtil.readListOfMaps(response, "$.[*].['id','title','category']");
		System.out.println(idTitleCategoryList);
		
		
		Double price = JsonPathValidatorUtil.read(response, "min($[*].price)");
		System.out.println(price);
		
	
	}
}
