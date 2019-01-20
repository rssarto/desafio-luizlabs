package com.desafioluizalabs.games;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileNotFoundException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.desafioluizalabs.games.domain.Game;
import com.desafioluizalabs.games.util.AbstractGameTest;
import com.desafioluizalabs.games.util.JsonLoader;
import com.google.gson.Gson;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GamesControllerTest extends AbstractGameTest {
	
	private static final String URI = "/api/v1/game";
	
    @Autowired
    private MockMvc mockMvc;
    
    private Gson gson;
    private Map<String, Game> gamesMapFromJsonFile;
    
    @Before
    public void before() throws FileNotFoundException {
    	this.gson = new Gson();
    	this.gamesMapFromJsonFile = JsonLoader.getListFromJsonFile();
    	assertThat(this.gamesMapFromJsonFile).isNotNull();
    	assertThat(this.gamesMapFromJsonFile.isEmpty()).isFalse();
    }
    
    @Test
    public void shouldNotFindGame() throws Exception {
    	this.mockMvc.perform(get(URI + "/0")).andExpect(status().isNotFound());
    }
    
    @Test
    public void shouldFindAllGamesByIdFromFile() throws FileNotFoundException {
    	this.gamesMapFromJsonFile.entrySet().stream().forEach(entry -> {
    		final Integer gameId = Integer.valueOf(StringUtils.substringAfter(entry.getKey(), "_"));
    		try {
				this.mockMvc.perform(get("/api/v1/game/" + gameId))
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/hal+json;charset=UTF-8"))
					.andDo(mvcResult -> {
						Game fromJson = this.gson.fromJson(mvcResult.getResponse().getContentAsString(), Game.class);
						assertThat(entry.getValue().equals(fromJson)).isTrue();
					});
			} catch (Exception e) {
				e.printStackTrace();
			}
    	});
    }
    
    @Test
    public void shouldMatchAllGamesFromFile() throws Exception {
    	this.mockMvc.perform(get(URI))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(mvcResult -> {
					Map<String, Game> gamesMapFromApi = JsonLoader.jsonFromString(mvcResult.getResponse().getContentAsString());
					this.assertGamesMaps(this.gamesMapFromJsonFile, gamesMapFromApi);
				});
    }
}
