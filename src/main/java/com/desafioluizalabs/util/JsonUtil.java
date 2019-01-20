package com.desafioluizalabs.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
	
	private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	
	public static String toJson(Object object) {
		return GSON.toJson(object);
	}	

}
