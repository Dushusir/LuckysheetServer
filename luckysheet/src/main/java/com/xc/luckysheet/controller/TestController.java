package com.xc.luckysheet.controller;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.xc.common.api.ResponseVO;
import com.xc.common.config.redis.RedisCacheService;
import com.xc.common.utils.JsonUtil;
import com.xc.luckysheet.dao.TestPostgreDao;
import com.xc.luckysheet.entity.PgGridDataModel;
import com.xc.luckysheet.entity.Test;
import com.xc.luckysheet.postgre.server.PostgresJfGridUpdateService;
import com.xc.luckysheet.service.TestServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * window启动
 * java -jar web-test.jar
 * 测试类
 *
 * http://luckysheet.lashuju.com/demo/qkIndex.html
 * http://127.0.0.1:85/luckysheet/demo/
 * @author Administrator
 */
@Slf4j
@RestController
@Api(description = "测试接口")
@RequestMapping("test")
public class TestController {

    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private TestServer testServer;
    @Autowired
    private TestPostgreDao testPostgreDao;
    @Autowired
    private PostgresJfGridUpdateService postgresJfGridUpdateService;


    @GetMapping("constant")
    public String getConstant(String param){
        Map<String,String> map=new HashMap<>();
        map.put("threadName",Thread.currentThread().getName());
        map.put("SUCCESS","true");
        map.put("param",param);

        log.info(JsonUtil.toJson(map));
        return JsonUtil.toJson(map);
    }

    @ApiOperation(value = "redis添加",notes = "保存到redis")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "value", value = "值", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("redis/addCache")
    public ResponseVO addCache(String key, String value){
        redisCacheService.addCache(key,value);
        return ResponseVO.successInstance("ok");
    }
    @ApiOperation(value = "redis查询",notes = "从redis获取")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("redis/getCache")
    public ResponseVO getCache(String key){
        return ResponseVO.successInstance(redisCacheService.getCache(key));
    }


    @ApiOperation(value = "Mysql查询按ID",notes = "")
    @ApiImplicitParam(name="id",value="1",paramType ="查询",required = true, dataType = "Long")
    @GetMapping("queryById")
    public ResponseVO queryById(Long id){
        Map<String,Object> map=new HashMap<>(2);
        if(id!=null){
            map.put("id",id);
        }
        List<Test> model=testServer.select(map);
        if(model!=null) {
            return ResponseVO.successInstance(model);
        }else{
            return ResponseVO.errorInstance("null");
        }
    }

    @ApiOperation(value = "Postgre查询按ID",notes = "")
    @ApiImplicitParam(name="id",value="1",paramType ="查询",required = true, dataType = "Long")
    @GetMapping("queryPostgreById")
    public ResponseVO queryPostgreById(Long id){
        List<Map<String, Object>> model=testPostgreDao.select(id);
        if(model!=null) {
            return ResponseVO.successInstance(model);
        }else{
            return ResponseVO.errorInstance("null");
        }
    }


}
