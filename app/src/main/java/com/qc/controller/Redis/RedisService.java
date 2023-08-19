package com.qc.controller.Redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qc.domain.BaseListVo;
import com.qc.utils.BaseUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisService {

    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final int EXPIRATION_SECONDS = 1209600;

    public void setBaseListVoToKey(String key, BaseListVo baseListVo) {
        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            ObjectMapper objectMapper = new ObjectMapper();
            // 将BaseListVo对象转换成JSON字符串
            String baseListVoJson = objectMapper.writeValueAsString(baseListVo);
            jedis.setex(key, EXPIRATION_SECONDS, baseListVoJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public BaseListVo getBaseListVoByKey(String key) {
        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            String storedJson = jedis.get(key);
            ObjectMapper objectMapper = new ObjectMapper();

            if (!BaseUtils.isEmpty(storedJson)) {
                try {
                    BaseListVo storedBaseListVo = objectMapper.readValue(storedJson, BaseListVo.class);
                    return storedBaseListVo;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }
            return null;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}