package com.xc.common.entity.base;

import lombok.Data;

import java.io.Serializable;

/**
 * redis信息推送基类
 * @author Administrator
 */
@Data
public class RedisEntity implements Serializable,Cloneable  {
    /**
     * 节点端口与ip （或者其他唯一标示）
     * 127.0.0.1:8080
     */
    String ipAndPort;


}
