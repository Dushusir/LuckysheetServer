package com.xc.common.config.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.xc.common.config.mybatisplus.method.DeleteAll;
import com.xc.common.config.mybatisplus.method.PhysicsDelete;

import java.util.List;


/**
 * 自定义 SqlInjector
 * 注册自定义方法
 * @author Administrator
 */
public class MySqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        //增加自定义方法
        methodList.add(new DeleteAll());
        methodList.add(new PhysicsDelete());
        return methodList;
    }
}
