package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//Part 2
public class ConfigManager 
{
	private static Properties properties = new Properties();
	//2a Load Properties
	static // This will be executed at the time of class loading. This is will be called before main method
	{
		
		//mvn clean install -Denv=qa/stage/dev/uat/prod
		//mvn clean install  --> If the env is not given, then run test cases on QA env by default.
		//env --> environment system variable
		String envName = System.getProperty("env","prod");
		System.out.println("Running tests on env : " + envName);
		
		String fileName = "config_"+ envName + ".properties"; //config_qa.properties
		
		// InputStream is faster than FIS when used with Reflection.
	 InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(fileName); //Reflection Property
	 	if(input != null)
	 	{
	 		try {
				properties.load(input);
			} catch (IOException e) {
			
				e.printStackTrace();
			}
	 	}
	}
	
	//2b Return key
	public static String get(String key)
	{
		return properties.getProperty(key).trim();
	}
	
	//2c Set key and Value
	public static void set(String key,String value)
	{
		 properties.setProperty(key,value);
	}
	
	
}
