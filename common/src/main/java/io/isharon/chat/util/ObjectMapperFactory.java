package io.isharon.chat.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by zhengwenzhu on 15-6-27.
 */

public class ObjectMapperFactory {

    private static ObjectMapper objectMapper ;

    static  {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getMapper() {
        return objectMapper;
    }
}