package br.com.worker.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class RedisRepository {

    private RedisTemplate redisTemplate;
    private RedisTemplate redisCacheTemplate;
    private HashOperations hashOperations;
    private ChannelTopic topic;

    @Autowired
    public RedisRepository(RedisTemplate redisTemplate, ChannelTopic topic, RedisTemplate redisCacheTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisCacheTemplate = redisCacheTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
        this.topic = topic;
    }

    public void setCache(Object key, Object value) {
        redisCacheTemplate.opsForValue().set(key, value, 12, TimeUnit.HOURS);
    }

    public Object getCache(Object key) {
        return redisCacheTemplate.opsForValue().get(key);
    }

    public void deleteCache(Object key) {
        redisCacheTemplate.delete(key);
    }
}
