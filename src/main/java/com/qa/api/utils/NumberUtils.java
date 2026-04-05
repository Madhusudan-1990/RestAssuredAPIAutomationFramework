package com.qa.api.utils;

import java.util.Random;

public class NumberUtils 
{
	 static Random random= new Random();
	public static int generateRandomNumber()
	{
		return random.nextInt(1000);
	}
}
