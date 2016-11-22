package com.aliyun.odps.ogg.handler.datahub.util;

import com.google.common.base.Strings;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by liqiang on 15/4/17.
 */
public class JsonHelper {
    private static final Logger LOG = LoggerFactory.getLogger(JsonHelper.class);
    private static final ObjectMapper objmapper = new ObjectMapper();
    static {
        objmapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonHelper() {}

    public static JsonNode getJsonNodeFromString(String jsonString) {
        try {
            return objmapper.readTree(jsonString);
        } catch (IOException e) {
            LOG.error("", e);
            return null;
        }
    }

    public static String beanToJson(Object bean) {
        StringWriter sw = new StringWriter();
        try {
            JsonGenerator jsongenerator = objmapper.getJsonFactory().createJsonGenerator(sw);
            objmapper.writeValue(jsongenerator, bean);
            jsongenerator.close();
        } catch (IOException e) {
            LOG.error("", e);
            return "";
        }
        return sw.toString();

    }

    public static <T> T jsonToBean(String jsonString, Class<T> clazz) {

        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }
        try {
            return objmapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            LOG.error("", e);
            return null;
        }
    }

    public static <T> T jsonToBean(String jsonString, TypeReference valueTypeRef) {

        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }
        try {
            return objmapper.readValue(jsonString, valueTypeRef);
        } catch (IOException e) {
            LOG.error("", e);
            return null;
        }
    }

    public static <T> T jsonToBeanWithException(String jsonString, Class<T> clazz) {

        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }
        try {
            return objmapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            LOG.error("", e);
            throw new RuntimeException("Deserialize failed", e);
        }
    }

}
