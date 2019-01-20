package com.desafioluizalabs.games.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

import com.desafioluizalabs.games.domain.Game;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class JsonLoader {
	private static final String JSON_FILE_PATH = "src/test/resources/response.json";
	private static Gson GSON = new Gson();
	private static Type TYPE = new TypeToken<Map<String, Game>>(){}.getType();
	
	public static Map<String, Game> getListFromJsonFile() throws FileNotFoundException{
		JsonReader reader = new JsonReader(new FileReader(JSON_FILE_PATH));
		Map<String, Game> gamesMap = jsonFromReader(reader);
		return gamesMap;
	}
	
	public static Map<String, Game> jsonFromReader(JsonReader reader){
		return GSON.fromJson(reader, TYPE);
	}
	
	public static Map<String, Game> jsonFromString(String content){
		return GSON.fromJson(content, TYPE);
	}
}
