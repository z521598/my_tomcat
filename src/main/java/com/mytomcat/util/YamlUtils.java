package com.mytomcat.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * yaml和object相互转换的工具类
 * Created by liming07 on 2018/9/5.
 */
public class YamlUtils {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final ObjectMapper IGNORE_UNKNOWN_PROPS_MAPPER = new ObjectMapper(new YAMLFactory());
    private static final ObjectMapper FAIL_ON_UNKNOWN_PROPS_MAPPER = new ObjectMapper(new YAMLFactory());

    static {
        IGNORE_UNKNOWN_PROPS_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        IGNORE_UNKNOWN_PROPS_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        IGNORE_UNKNOWN_PROPS_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        IGNORE_UNKNOWN_PROPS_MAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));

        FAIL_ON_UNKNOWN_PROPS_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        FAIL_ON_UNKNOWN_PROPS_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        FAIL_ON_UNKNOWN_PROPS_MAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));
    }

    private YamlUtils() {
        // cannot init
    }

    private static ObjectMapper yamlObjectMapper(boolean ignoreUnknownProps) {
        return ignoreUnknownProps ? IGNORE_UNKNOWN_PROPS_MAPPER : FAIL_ON_UNKNOWN_PROPS_MAPPER;
    }

    /**
     * 将yaml转换成object
     */
    public static <T> T toObject(String yamlString, Class<T> clazz, boolean... ignoreUnknownProps) throws IOException {
        if (StringUtils.isBlank(yamlString) || null == clazz) {
            throw new IOException("empty input");
        }
        boolean ignore = isIgnoreUnknownProps(ignoreUnknownProps);
        return yamlObjectMapper(ignore).readValue(yamlString, clazz);
    }

    public static <T> T toObject(InputStream inputStream, Class<T> clazz, boolean... ignoreUnknownProps)
            throws IOException {
        if (null == inputStream || null == clazz) {
            throw new IOException("empty input");
        }
        boolean ignore = isIgnoreUnknownProps(ignoreUnknownProps);
        return yamlObjectMapper(ignore).readValue(inputStream, clazz);
    }

    /**
     * 将yaml转换成object
     */
    public static <T> T toObject(String yamlString, TypeReference<T> type, boolean... ignoreUnknownProps)
            throws IOException {
        if (StringUtils.isBlank(yamlString) || null == type) {
            throw new IOException("empty input");
        }

        boolean ignore = isIgnoreUnknownProps(ignoreUnknownProps);
        return yamlObjectMapper(ignore).readValue(yamlString, type);
    }

    private static boolean isIgnoreUnknownProps(boolean... ignoreUnkownProps) {
        boolean ignore = true;
        if (null != ignoreUnkownProps && ignoreUnkownProps.length > 0) {
            ignore = ignoreUnkownProps[0];
        }
        return ignore;
    }

    public static String toYaml(Object obj) {
        try {
            return yamlObjectMapper(true).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
