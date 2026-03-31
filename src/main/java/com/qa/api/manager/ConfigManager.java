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
		// InputStream is faster than FIS when used with Reflection.
	 InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config/config.properties"); //Reflection Property
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
