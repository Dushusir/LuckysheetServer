package com.xc.luckysheet.redisserver;

import com.mongodb.DBObject;
import com.xc.common.config.redis.RedisCacheService;
import com.xc.common.config.redis.RedisQueueService;
import com.xc.luckysheet.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.LinkedHashMap;
import java.util.List;


/**
 * @author Administrator
 */
@Slf4j
@Service
public class GridFileRedisCacheService {
    @Autowired
    private RedisCacheService redisCache;
    @Autowired
    private RedisQueueService redisQueueService;

    /**
     * redis存入包名
     */
    public String email_check_content="email_recheck_num:";

    /**
     * oracle查询数据库数据源保存入redis
     */
    public String oracle_check_content="mb:oracle:";

    /**
     * 场景计数
     */
    private String stock_plate_check_content="qk:stock_plate:";

    /**
     * 批量更新数据
     */
    private String dbdata_content="qk:dbdata:";

    /**
     * 收集指令信息
     */
    private String hand_flag_content="qk:upflag:";

    /**
     * 收集指令信息内容
     */
    private String qk_handle_content="qk:handler:";

    /**
     * 新增email的验证码信息
     * @param key
     * @param val
     */
    public void raddEmaiContent(String key, Object val) {
        String redisKey = email_check_content + key;
        log.info("raddEmaraddEmaiContentiContent---redisKey="+redisKey+"val="+val);
        redisCache.addCache(redisKey, val, 5);
    }

    /**
     * 根据key 获得email验证码信息
     * @param key
     */
    public Object rgetEmailContent(String key) {
        String redisKey = email_check_content + key;
        log.info("raddEmaiContent---redisKey="+redisKey);
        return redisCache.getCache(redisKey);
    }

    public int removeRedisMail(String key){
        String redisKey = email_check_content + key;
        log.info("removeRedisMail---redisKey="+redisKey);
        return redisCache.deleteCaches(redisKey);
    }

    public void raddMD5ToOraleContent(String sql , Object val){
        String redisKey=oracle_check_content+MD5Util.MD5Encode(sql);
        redisCache.addCache(redisKey, val, 3);
    }

    public List<LinkedHashMap<String, Object>> rgetMD5ToOralceContent(String sql){
        try{
            String redisKey=oracle_check_content+MD5Util.MD5Encode(sql);
            Object obj= redisCache.getCache(redisKey);
            if(obj==null || "".equals(obj)){
                return null;
            }
            List<LinkedHashMap<String, Object>> llm=(List<LinkedHashMap<String, Object>>) obj;
            return llm;
        }catch(Exception ex){
            return null;
        }
    }


    public List<DBObject> rgetDbDataContent(String key){
        try{
            String redisKey=dbdata_content+key;
            List<DBObject> lists=redisCache.getListBySize(redisKey, -1);
            if(lists!=null && lists.size()>0){
                //从redis中删除
                redisCache.delList(redisKey, lists.size());
                return lists;
            }
        }catch(Exception ex){
            return null;
        }
        return null;
    }


    /**
     *
     * @param key
     * @param db
     */
    public void raddDbContent(String key,DBObject db){
        String redisKey=dbdata_content+key;
        redisCache.addList(redisKey, db);
    }


    /**
     * 存入启用存储指令信息
     * @param key
     * @param val
     */
    public void raddFlagContent(String key, Object val) {
        String redisKey = hand_flag_content + key;
        log.info("raddFlagContent---redisKey="+redisKey+"val="+val);
        redisCache.addCache(redisKey, val, 240);
    }

    /**
     * 根据key 获得email验证码信息
     * @param key
     */
    public Boolean rgetFlagContent(String key) {
        Boolean flag=false;
        try{
            String redisKey = hand_flag_content + key;
            log.info("rgetFlagContent---redisKey="+redisKey);
            flag=(Boolean) redisCache.getCache(redisKey);
        }catch (Exception e) {
            // TODO: handle exception
        }
        return flag;
    }


    /**
     * 获取数据
     * @param key
     * @return
     */
    public List<String> rgetHandlerContent(String key){
        try{
            String redisKey=qk_handle_content+key;
            //多节点使用
            List<String> lists=redisQueueService.popList(redisKey,String.class,500);
            return lists;
            //单节点使用
//            List<String> lists=redisCache.getListBySize(redisKey, -1);
//            if(lists!=null && lists.size()>0){
//                //从redis中删除
//                redisCache.delList(redisKey, lists.size());
//                return lists;
//            }
        }catch(Exception ex){
            return null;
        }
    }

    //场景访问量保存.

    public void raddHandlerContent(String key,String db){
        String redisKey=qk_handle_content+key;
        redisCache.addList(redisKey, db);
    }
}
