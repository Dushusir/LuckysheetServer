package com.xc.common.config.mybatisplus;


import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * mybatis配置
 * @author Administrator
 */
@Slf4j
@Configuration
@MapperScan("com.xc.*.mapper")
public class MyBatisPlusConfig {

    /**
     * 读取配置文件
     */
    @Autowired
    private Environment env;
    /**
     * 数据源
     */
    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "globalConfig") GlobalConfig globalConfig) throws Exception{
        log.info("初始化SqlSessionFactory");
        MybatisSqlSessionFactoryBean sqlSessionFactory=new MybatisSqlSessionFactoryBean();
        //是否显示Sql语句
        Boolean showSql = env.getProperty("showSql", Boolean.class);
        if (showSql != null && showSql) {
            MybatisConfiguration configuration = new MybatisConfiguration();
            configuration.setLogImpl(StdOutImpl.class);
            sqlSessionFactory.setConfiguration(configuration);
        }

        //设置分页插件
        Interceptor[] plugins = {paginationInterceptor()};
        if (!ObjectUtils.isEmpty(plugins)) {
            sqlSessionFactory.setPlugins(plugins);
        }

        //设置全局配置
        sqlSessionFactory.setGlobalConfig(globalConfig);

        //设置数据源
        sqlSessionFactory.setDataSource(masterDataSource);
        return sqlSessionFactory.getObject();
    }


    /**
     * mybatis plus 全局配置
     * @return
     */
    @Bean(name = "globalConfig")
    public GlobalConfig globalConfig(){
        log.info("GlobalConfig");
        GlobalConfig globalConfig = new GlobalConfig();
        //自动填充处理类
        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
        //自定义方法
        globalConfig.setSqlInjector(new MySqlInjector());
        return globalConfig;
    }

    /**
     * mybatis-plus分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }


}
