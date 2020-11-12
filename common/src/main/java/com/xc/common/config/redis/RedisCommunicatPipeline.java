package com.xc.common.config.redis;

import com.xc.common.entity.base.RedisEntity;
import com.xc.common.enumeration.RedisReceiverTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * redis通信管道类
 * 将要推送到websocket的数据保存到redis中
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RedisCommunicatPipeline extends RedisEntity {

    /**
     * 场景id sceneid
     */
    Long sceneId;
    /**
     * 发送的内容(直接返回到前端)
     */
    String data;

    /**
     * 推送目标对象的类型
     */
    RedisReceiverTypeEnum redisReceiverTypeEnum;
    /**
     * 小程序用户openid（多个用户,分割）
     */
    List<String> xcxOpenIds;

    /**
     * 数据检查
     * @return
     */
    public boolean check(){
        if(this.redisReceiverTypeEnum==null||this.sceneId==null||StringUtils.isBlank(this.data)){
            return false;
        }
        return true;
    }
}
