package com.xc.common.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


/**
 * 数据源配置
 * @author Administrator
 */
@Configuration
@Slf4j
public class DataSourceConfig {

    @Primary
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "db.master.druid")
    public DataSource masterDataSource(){
        //return DataSourceBuilder.create().build();
        DataSource master = DataSourceBuilder.create().type(DruidDataSource.class).build();
        log.debug("数据源master",master);
        return master;
    }
    @Bean(name = "postgreDataSource")
    @ConfigurationProperties(prefix = "db.postgre.druid")
    public DataSource postgreDataSource(){
        //return DataSourceBuilder.create().build();
        DataSource postgre = DataSourceBuilder.create().type(DruidDataSource.class).build();
        log.debug("数据源postgre",postgre);
        return postgre;
    }

    /**
     * 读写分离
     * http://vlambda.com/wz_wO385s4TWK.html
     *
    @Bean
    @ConfigurationProperties("spring.datasource.slave1")
    public DataSource slave1DataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean
    @ConfigurationProperties("spring.datasource.slave2")
    public DataSource slave2DataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean
    public DataSource myRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                          @Qualifier("slave1DataSource") DataSource slave1DataSource,
                                          @Qualifier("slave2DataSource") DataSource slave2DataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MASTER, masterDataSource);
        targetDataSources.put(DBTypeEnum.SLAVE1, slave1DataSource);
        targetDataSources.put(DBTypeEnum.SLAVE2, slave2DataSource);
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }
     */
}
