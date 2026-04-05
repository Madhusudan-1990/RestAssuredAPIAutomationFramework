package com.qa.api.mocking;

import  static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import  static com.github.tomakehurst.wiremock.client.WireMock.get;
import  static com.github.tomakehurst.wiremock.client.WireMock.post;
import  static com.github.tomakehurst.wiremock.client.WireMock.put;
import  static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.azure.core.annotation.Post;
import com.ctc.wstx.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qa.api.utils.NumberUtils;

import  static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import  static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import  static com.github.tomakehurst.wiremock.client.WireMock.equalTo; 
public class APIMocks 
{
	public static String updateJsonFileWithRandomId() throws IOException
	{
	    ObjectMapper mapper = new ObjectMapper();

	    ObjectNode jsonNode = (ObjectNode) mapper.readTree(
	        new File("./src/test/resources/__files/mockuser.json"));

	    jsonNode.put("_id", NumberUtils.generateRandomNumber()); // ✅ int

	    return mapper.writeValueAsString(jsonNode);
	}
	//**************************Create Mock/Stub for GET CALL****************************//
	public static void getDummyUser() throws IOException
	{
		
		//http://localhost:8089/api/users
		stubFor(get(urlEqualTo("/api/users"))
			.willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{\r\n"
						+ "    \"name\" : \"tom\"\r\n"
						+ "}")
				)
			);
	}
	
	public static void defineGetUserMockWithJsonFile()
	{
		
		stubFor(get(urlEqualTo("/api/users"))
				.willReturn(aResponse()
					.withStatus(200)
					.withHeader("Content-Type","application/json")
					.withHeader("server-name", "bankserver")
					.withBodyFile("mockuser.json")
					)
				);
				
	}
	
	public static void defineGetUserMockWithQueryParam()
	{
		stubFor(get(urlPathEqualTo("/api/users"))
				.withQueryParam("name",equalTo("tom"))
				.willReturn(aResponse()
					.withStatus(200)
					.withHeader("Content-Type","application/json")
					.withHeader("server-name", "bankserver")
					.withBodyFile("mockuser.json")
					)
				);
				
	}
	
	//**************************Create Mock/Stub for POST CALL****************************//
	
	public static void defineCreateUserMock() throws IOException
	{
		
		stubFor(post(urlEqualTo("/api/users"))
					.withHeader("Content-Type",equalTo("application/json"))
					.willReturn(aResponse()
						.withStatus(201)
						.withHeader("Content-Type","application/json")
						.withHeader("server-name", "bankserver")
						.withBody(updateJsonFileWithRandomId())
						)
					);
	}
}
