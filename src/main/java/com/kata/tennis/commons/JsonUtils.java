package com.kata.tennis.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtils {
    public static <T> Map<String, String> objectToMap(T obj) {
        ObjectMapper oMapper = new ObjectMapper();
        return oMapper.convertValue(obj, Map.class);
    }
    public static <T> String toJsonString(T obj) throws JsonProcessingException {
        ObjectMapper oMapper = new ObjectMapper();
        return oMapper.copy().writeValueAsString(obj);
    }
    public static <T> T jsonTooObject(String json, Class<T> contentClass) throws JsonProcessingException {
        ObjectMapper oMapper = new ObjectMapper();
        oMapper.findAndRegisterModules();
        return oMapper.readValue(json, contentClass);
    }
}

