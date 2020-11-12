package com.xc.luckysheet.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 测试类
 * @author Administrator
 */
@Slf4j
@Repository
public class TestPostgreDao {

    @Resource(name = "postgreJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> select(Long id) {
        String sql = "select * from test ";
        if(id!=null){
            sql=sql+" where id="+id;
        }
        try {
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            log.error("查询报错{}", e.getMessage());
            return null;
        }
    }
}
