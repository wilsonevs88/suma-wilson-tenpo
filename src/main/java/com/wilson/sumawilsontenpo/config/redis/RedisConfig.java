package com.wilson.sumawilsontenpo.config.redis;

import com.wilson.sumawilsontenpo.config.properties.RedisLettuceProperties;
import com.wilson.sumawilsontenpo.config.properties.RedisProperties;
import com.wilson.sumawilsontenpo.config.seralizer.UserDataRedisSerializer;
import com.wilson.sumawilsontenpo.config.seralizer.UserDataRetryRedisSerializer;
import com.wilson.sumawilsontenpo.models.UserDataRedis;
import com.wilson.sumawilsontenpo.models.UserDataRetryRedis;

import javax.validation.Valid;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
public class RedisConfig {

    private static LettuceClientConfiguration getLettuceClientConfiguration(
            GenericObjectPoolConfig<?> poolConfig,
            Integer commandTimeoutReadinessSeconds) {
        return LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.of(commandTimeoutReadinessSeconds, ChronoUnit.SECONDS))
                .poolConfig(poolConfig).build();
    }

    /**
     * Return new instance of redis standalone Configuration.
     *
     * @param redisProperties properties.
     * @return redis standalone Configuration.
     */
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration(final @Valid RedisProperties redisProperties) {
        val configuration = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
        if (configuration.getPassword() != null) {
            configuration.setPassword(redisProperties.getPassword());
        }
        return configuration;
    }

    /**
     * Return new pool config for lettuce client.
     *
     * @param redisLettuceProperties Properties.
     * @return GenericObjectPoolConfig
     */
    @Bean
    public GenericObjectPoolConfig<RedisLettuceProperties> poolConfig(final @Valid RedisLettuceProperties redisLettuceProperties) {
        GenericObjectPoolConfig<RedisLettuceProperties> configuration = new GenericObjectPoolConfig<>();
        configuration.setMaxTotal(redisLettuceProperties.getMaxActive());
        configuration.setMaxIdle(redisLettuceProperties.getMaxIdle());
        configuration.setMinIdle(redisLettuceProperties.getMinIdle());
        configuration.setMaxWaitMillis(redisLettuceProperties.getMaxWait());
        return configuration;
    }

    /**
     * Return new instance of Connection Factory (Used Letucce).
     *
     * @param redisStandaloneConfiguration Fetch configuration from redis.
     * @param poolConfig                   redis connections.
     * @param redisLettuceProperties       redis configuration
     * @return redis connection factory.
     */
    @Primary
    @Bean("initialValidateConnection")
    public RedisConnectionFactory connectionFactory(
            final RedisStandaloneConfiguration redisStandaloneConfiguration,
            final GenericObjectPoolConfig<?> poolConfig,
            @Valid final RedisLettuceProperties redisLettuceProperties) {
        return new LettuceConnectionFactory(
                redisStandaloneConfiguration,
                getLettuceClientConfiguration(poolConfig, redisLettuceProperties.getCommandTimeoutSeconds()));
    }

    /**
     * RedisTemplate RetriesCounter which adds a prefix to the key.
     *
     * @param connectionFactory Create a connection with a provider.
     * @return {@link RedisTemplate}
     */
    @Bean("userDataRedisTemplate")
    public RedisTemplate<String, UserDataRedis> userDataRedisTemplate(
            @Qualifier("initialValidateConnection") RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, UserDataRedis> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new UserDataRedisSerializer());
        template.setEnableDefaultSerializer(true);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * RedisTemplate RetriesCounter which adds a prefix to the key.
     *
     * @param connectionFactory Create a connection with a provider.
     * @return {@link RedisTemplate}
     */
    @Bean("userDataRetryRedisTemplate")
    public RedisTemplate<String, UserDataRetryRedis> userDataRetryRedisTemplate(
            @Qualifier("initialValidateConnection") RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, UserDataRetryRedis> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new UserDataRetryRedisSerializer());
        template.setEnableDefaultSerializer(true);
        template.afterPropertiesSet();
        return template;
    }


}
