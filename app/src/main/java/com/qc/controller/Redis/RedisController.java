package com.qc.controller.Redis;

import com.qc.domain.BaseListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService; // 假设有一个 RedisService 来处理 Redis 数据操作

    @GetMapping("/get/BaseListVo")
    public ResponseEntity<BaseListVo> getBaseListVo(@RequestParam String key,
                                                    @RequestParam(required = false) String filter) {
        // 根据参数来访问 Redis
        BaseListVo baseListVo = redisService.getBaseListVoByKey(key);
        // 在这里根据 filter 参数来进行处理，比如过滤掉不需要的数据
        return ResponseEntity.ok(baseListVo);

    }

}
