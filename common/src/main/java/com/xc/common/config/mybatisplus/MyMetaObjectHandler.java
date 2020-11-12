package com.xc.common.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 自动填充处理类
 * @author Administrator
 **/
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler{
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        String threadName = Thread.currentThread().getName();
        if (threadName.contains("http-nio")) {
            this.setFieldValByName("createUser", obtainUserName(), metaObject);
        }
        this.setFieldValByName("createTm", new Date(), metaObject);
        this.setFieldValByName("isDelete", "0", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setFieldValByName("updateTm", new Date(), metaObject);
        String threadName = Thread.currentThread().getName();
        if (threadName.contains("http-nio")) {
            this.setFieldValByName("updateUser", obtainUserName(), metaObject);
        }
    }

    /**
     * RequestContextHolder是请求的上下文
     * 原理是在请求进来的时候把request放入到了threadLocal里面
     * 获取的取值其实是获取当前的线程名称去threadLocal里取值
     * 获取用户名
     *
     * @return
     */
    private String obtainUserName() {
        String threadName = Thread.currentThread().getName();
        Thread.currentThread().getContextClassLoader();
        log.info("threadName:{}", threadName);
        String userName = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            userName = request.getHeader("userName");
        } catch (Exception e) {
            log.error("获取request上下文异常：{}", e.getMessage());
        }
        return userName;
    }
}
