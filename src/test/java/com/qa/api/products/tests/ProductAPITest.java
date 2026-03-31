package com.qa.api.products.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Product;
import com.qa.api.utils.JsonUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITest extends BaseTest
{
	//Deserialization : Json Array to POJO Class
	@Test
	public void getProductsTest()
	{
	 Response response = restClient.get(BASE_URL_PRODUCTS, PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
	 Assert.assertEquals(response.statusCode(), 200);
	 
	 Product[] product= JsonUtils.deserialize(response, Product[].class);
	 for(Product p : product)
		{
			System.out.println("Product ID : " + p.getId());
			System.out.println("Title : " + p.getTitle());
			System.out.println("Price : " + p.getPrice());
			System.out.println(" Description : " + p.getDescription());
			System.out.println("Image ID : " + p.getImage());
			System.out.println("Rate  : " + p.getRating().getRate());
			System.out.println("Count  : " + p.getRating().getCount());
		}
	 
	}

}
