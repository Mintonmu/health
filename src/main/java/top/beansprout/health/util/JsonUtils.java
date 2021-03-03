package top.beansprout.health.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Title: JsonUtils</p>
 * <p>Description: Json工具类，依赖 jackson</p>
 * @author beansprout
 * @date 2020/3/18 23:39
 * @version 1.0
 */
@Slf4j
public class JsonUtils {

    private final static ObjectMapper objMapper = new ObjectMapper();

    /**
     * Json字符串转化成对象
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObj(String jsonString, Class<T> clazz) {
        objMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            return objMapper.readValue(jsonString, clazz);
        } catch (final IOException e) {
            log.error("Json string conversion object failed {}", e);
        }
        return null;
    }

    /**
     * javaBean 转化成json字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        if((obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Float) ||
                (obj instanceof Double) || (obj instanceof Boolean) || (obj instanceof String))
			return String.valueOf(obj);
        try {
            return objMapper.writeValueAsString(obj);
        } catch (final JsonProcessingException e) {
            log.error("Json object conversion string failed {}", e);
        }
        return null;
    }

}