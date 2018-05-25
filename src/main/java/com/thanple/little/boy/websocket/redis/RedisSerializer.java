package com.thanple.little.boy.websocket.redis;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Created by Thanple on 2018/5/25.
 */
public class RedisSerializer<T> {

    private static final byte[] EMPTY_ARRAY = new byte[0];

    private final JavaType javaType;

    private ObjectMapper objectMapper = new ObjectMapper();


    public RedisSerializer(Class<T> type) {
        this.javaType = getJavaType(type);
    }


    public RedisSerializer(JavaType javaType) {
        this.javaType = javaType;
    }

    private static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    @SuppressWarnings("unchecked")
    public T deserialize(byte[] bytes) throws RedisSerializationException {
        if (isEmpty(bytes)) {
            return null;
        }
        try {
            return (T) this.objectMapper.readValue(bytes, 0, bytes.length, javaType);
        } catch (Exception ex) {
            throw new RedisSerializationException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    public byte[] serialize(Object t) throws RedisSerializationException {
        if (t == null) {
            return EMPTY_ARRAY;
        }
        try {
            return this.objectMapper.writeValueAsBytes(t);
        } catch (Exception ex) {
            throw new RedisSerializationException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        if(null == objectMapper){
            throw new NullPointerException("objectMapper' must not be null");
        }
        this.objectMapper = objectMapper;
    }

    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }

}
