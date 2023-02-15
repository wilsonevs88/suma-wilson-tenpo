package com.wilson.sumawilsontenpo.config.seralizer;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilson.sumawilsontenpo.models.UserDataRedis;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class UserDataRedisSerializer implements RedisSerializer<UserDataRedis> {

    private static final String CLASS_FIELD_VALUE = "@class";
    private static final String CLASS_CANONICAL_NAME = UserDataRedis.class.getCanonicalName();
    private static final String CLASS_FIELD =
            "{\"" + CLASS_FIELD_VALUE + "\":\"" + CLASS_CANONICAL_NAME + "\",";

    private final ObjectMapper om;

    /**
     * <pre>
     * Jackson needs a @class field to be able to Serialize objects.
     * As we insert directly object's fields we need to add the porperty @class to be able
     * to Deserialize the received object.
     * If the entity is Serialized by Jackson, it will add the @class field.
     * </pre>
     */
    public UserDataRedisSerializer() {
        this.om = new ObjectMapper();
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);
        om.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Deserialize fixing the @class field just in case it's missing.
     *
     * @param userDataRedis Send object to convert it to bytes.
     * @return Make the conversion
     * @throws SerializationException serializationException
     */
    @Override
    public byte[] serialize(UserDataRedis userDataRedis) {
        try {
            return om.writeValueAsBytes(userDataRedis);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new SerializationException(jsonProcessingException.getMessage(),
                    jsonProcessingException);
        }
    }

    /**
     * Deserialize fixing the @class field just in case it's missing.
     *
     * @param bytes received object bytes
     * @return CustomObject instance
     * @throws SerializationException serializationException
     */
    @Override
    public UserDataRedis deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        var data = new String(bytes, StandardCharsets.UTF_8);
        data = Optional
                .of(data)
                .filter(str -> !str.contains(CLASS_FIELD_VALUE))
                .map(str -> CLASS_FIELD.concat(str.substring(1)))
                .orElse(data);

        try {
            return om.readValue(data, UserDataRedis.class);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new SerializationException(jsonProcessingException.getMessage(),
                    jsonProcessingException);
        }
    }

}
