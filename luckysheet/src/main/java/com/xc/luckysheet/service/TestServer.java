package com.xc.luckysheet.service;

import com.xc.luckysheet.entity.Test;
import com.xc.luckysheet.mapper.TestDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
public class TestServer {

    @Resource
    private TestDao testDao;

    /**
     * 查询
     * @param param
     * @return
     */
    public List<Test> select(Map<String, Object> param){
        return testDao.select(param);
    }
}
