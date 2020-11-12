package com.xc.luckysheet.postgre.dao;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务测试
 * @author Administrator
 */
@Slf4j
@Repository
public class P_TestTranDao {

    @Autowired
    private P_TUserDao p_tUserDao;

    @Transactional("txPostgresqlManager")
    public void test(){
        int i=p_tUserDao.addJsonb(0,"12345681745dfwf545fw23fw45fwf12");
        if(i!=1){
            throw new RuntimeException("添加错误");
        }
        i=p_tUserDao.addJsonb(1,"123456817452fwf545fw24fw45fwf12",false);
        if(i!=1){
            throw new RuntimeException("添加错误");
        }
    }
}
