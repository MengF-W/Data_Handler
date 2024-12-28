package com.analyzer.datahandler.database.utilities;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {

    private Gson gSon;

    @Bean
    public Gson getInstance() {

        if(gSon == null){

            gSon = new Gson();
        }

        return gSon;
    }

    public <T> T deserializeJson(String jsonString, Class<T> targetObject)
    {
        T t = gSon.fromJson(jsonString,
                targetObject);

        return t;
    }

}

