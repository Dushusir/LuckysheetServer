package com.xc.common.enumeration;

/**
 * redis管道信息推送类型
 */
public enum RedisReceiverTypeEnum {
    /**
     * 发送全部用户（大屏与小程序）
     */
    ALL,
    /**
     * 只发送给大屏
     */
    BiGSCREEN,
    /**
     * 只发给全部小程序
     */
    USERS,
    /**
     * 只发给指定小程序用户
     */
    SPECIFIC_USER,
    /**
     * 排除特定小程序用户
     */
    EXCLUDE_USER

}
