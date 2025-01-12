package com.analyzer.datahandler.database.utilities;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

public class JsonParser {

    private static JsonParser jsonParser;
    private static Gson gSon;

    private JsonParser(){}


    public static JsonParser getInstance() {

        if(jsonParser == null){

            jsonParser = new JsonParser();
            gSon = new Gson();
        }

        return jsonParser;
    }

    public String serializeJson(Object sourceObject)
    {
        return gSon.toJson(sourceObject);
    }

    public <T> T deserializeJson(String jsonString, Class<T> targetObject)
    {
        T t = gSon.fromJson(jsonString,
                targetObject);

        return t;
    }

}

