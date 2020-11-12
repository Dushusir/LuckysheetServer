package com.xc.luckysheet.controller;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.xc.common.api.ResponseVO;
import com.xc.common.config.redis.RedisCacheService;
import com.xc.common.utils.JsonUtil;
import com.xc.common.utils.RandomUtil;
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
 * http://localhost:9003/dataqk/test/constant?param=%E6%B5%8B%E8%AF%95
 * http://localhost:9003/webls/test/constant?param=测试
 * http://localhost:9003/webls/swagger-ui.html
 * http://localhost:9003/webls/swagger-ui.html
 * http://localhost:9003/webls/doc.html#/home
 *
 *
 * http://localhost:9003/dataqk/test/constant?param=测试
 * http://localhost:9003/dataqk/swagger-ui.html
 * http://localhost:9003/dataqk/swagger-ui.html
 * http://localhost:9003/dataqk/doc.html#/home
 *
 * http://localhost:8080/dataqk/doc.html#/home
 * http://localhost:8080/staticft/tpreport/static/qksheet/yhIndex.html
 * http://localhost:8080/staticqk/qkIndex.html
 * http://localhost:85/staticqk/qkIndex.html
 *
 * http://luckysheet.lashuju.com/demo/qkIndex.html
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
        map.put("random", RandomUtil.getRandom()+"");
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

    ////////////////////

    //postgre添加记录
    @ApiOperation(value = "postgre添加sheet记录",notes = "postgre添加sheet记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "list_id", value = "list_id", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("postgre/sheet/add")
    public ResponseVO postgreSheetAdd(String list_id){
        //初始化对象
        String strSheet="{\"name\":\"Sheet1\",\"color\":\"\",\"index\":0,\"chart\":[],\"status\":1,\"order\":0,\"column\":60,\"row\":84,\"celldata\":[{\"c\":1,\"r\":1,\"v\":\"v1\"},{\"c\":1,\"r\":2,\"v\":\"v2\"},{\"c\":1,\"r\":3,\"v\":\"v3\"},{\"c\":1,\"r\":4,\"v\":\"v4\"}],\"visibledatarow\":[],\"visibledatacolumn\":[],\"rowsplit\":[],\"ch_width\":4748,\"rh_height\":1790,\"jfgird_select_save\":{},\"jfgrid_selection_range\":{},\"scrollLeft\":0,\"scrollTop\":0,\"config\":{}}";
        DBObject bson=(DBObject) JSON.parse(strSheet);

        PgGridDataModel model=new PgGridDataModel();
        model.setBlock_id("block_id");
        model.setIndex("1");
        model.setIs_delete(0);
        model.setJson_data(bson);
        model.setStatus(1);
        model.setOrder(0);
        model.setList_id(list_id);

        String result=postgresJfGridUpdateService.insert(model);

        return ResponseVO.successInstance(result);
    }

}
