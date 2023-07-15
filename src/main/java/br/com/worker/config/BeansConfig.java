package br.com.worker.config;

import br.com.worker.domain.FilaRedis;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.rabbitmq.client.ConnectionFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Slf4j
@Configuration
public class BeansConfig {

    @Autowired
    MongoProperties mongoProperties;

    @Resource
    private Environment env;

    @Bean
    public ConnectionFactory customConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(env.getProperty("spring.rabbitmq.host"));
        connectionFactory.setPort(Integer.valueOf(env.getProperty("spring.rabbitmq.port")));
        connectionFactory.setUsername(env.getProperty("spring.rabbitmq.username"));
        connectionFactory.setPassword(env.getProperty("spring.rabbitmq.password"));
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(10000);
        return connectionFactory;
    }

    @Bean
    public MongoClient mongo() {

        log.info("CONFIG MONGOOOO ====== " + "mongodb://" + env.getProperty("spring.data.mongodb.host") + ":" + Integer.valueOf(env.getProperty("spring.data.mongodb.port")) + "/" + env.getProperty("spring.data.mongodb.database"));

        ConnectionString connectionString = new ConnectionString("mongodb://" + env.getProperty("spring.data.mongodb.host") + ":" + Integer.valueOf(env.getProperty("spring.data.mongodb.port")) + "/" + env.getProperty("spring.data.mongodb.database"));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), env.getProperty("spring.data.mongodb.database"));
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();

        JedisClientConfiguration cf = JedisClientConfiguration.builder().readTimeout(Duration.ofSeconds(0)).connectTimeout(Duration.ofSeconds(0)).build();

        rsc.setHostName(env.getProperty("spring.redis.host"));
        rsc.setPort(Integer.valueOf(env.getProperty("spring.redis.port")));

        return new JedisConnectionFactory(rsc, cf);
    }

    @Bean
    public JedisConnectionFactory jedisCacheConnectionFactory() {
        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();

        JedisClientConfiguration cf = JedisClientConfiguration.builder().readTimeout(Duration.ofSeconds(0)).connectTimeout(Duration.ofSeconds(0)).build();

        rsc.setHostName(env.getProperty("spring.redis.host"));
        rsc.setPort(Integer.valueOf(env.getProperty("spring.redis.portCache")));

        return new JedisConnectionFactory(rsc, cf);
    }

    @Bean
    public RedisTemplate<String, FilaRedis> redisTemplate() {
        RedisTemplate<String, FilaRedis> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<FilaRedis>(FilaRedis.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, Object> redisCacheTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisCacheConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setValueSerializer(new JsonRedisSerializer());
        return template;
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic(env.getProperty("spring.redis.pub"));
    }
}
