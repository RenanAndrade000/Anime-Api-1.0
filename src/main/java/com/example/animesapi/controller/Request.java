package com.example.animesapi.controller;

import com.example.animesapi.model.Anime;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.apache.coyote.http11.Constants.a;

@RestController
public class Request {

    @GetMapping("/test")
    public List<Anime> requestAnimes() throws Exception{
        URL url = new URL("https://api.jikan.moe/v4/recommendations/anime?page=1");
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        String aux = "";
        StringBuilder jsonAnime = new StringBuilder();

        while((aux = bufferedReader.readLine()) != null){
            jsonAnime.append(aux);
        }


        JSONObject jsonObject = new JSONObject(jsonAnime.toString());
        JSONArray data = jsonObject.getJSONArray("data");
        List<String> dataEntry = new ArrayList<>();



        for (int i = 0; i < data.length(); i++) {
            JSONObject dataObject = data.getJSONObject(i);
            JSONArray entry = dataObject.getJSONArray("entry");
            for (int j = 0; j < entry.length(); j++) {

                dataEntry.add(Collections.singletonList(entry.get(j).toString()).toString());
            }
        }

        Gson gson = new Gson();
        Object[][] animeArray = gson.fromJson(dataEntry.toString(), Object[][].class);
        Object[] auxJson = new Object[animeArray.length];
        List<Anime> animeList = new ArrayList<>();

        //o json eninhado que eu estava tentando arrumar era uma lista que continha um json dentro e este foi um jeito de
        //transformar esta lista que continha um json dentro em um json válido
        for(int i = 0; i < animeArray.length; i++){
            auxJson[i] = animeArray[i][0];

        }
        String readyJson = gson.toJson(auxJson);
        JSONArray jsonArray = new JSONArray(readyJson);


        for(int i =0; i < auxJson.length; i++){
            Anime animeAux = new Anime(); //me fodi nessa hora por que tinha criado o anime antes do looping dai ele ficava resetando no primeiro looping e trazia os mesmos dados
            animeAux.setTitle(jsonArray.getJSONObject(i).get("title").toString());
            animeAux.setMal_id(jsonArray.getJSONObject(i).get("mal_id").toString());
            animeAux.setImage_url(jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("jpg").get("image_url").toString());
            animeList.add(animeAux);
        }



        return animeList;



    }
}
