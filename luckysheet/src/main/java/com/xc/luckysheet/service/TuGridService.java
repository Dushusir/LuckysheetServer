package com.xc.luckysheet.service;


import com.xc.luckysheet.dao.QsTuGridDao;
import com.xc.luckysheet.entity.TuGridModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 */
@Slf4j
@Service
public class TuGridService extends BaseService<TuGridModel> {

    @Autowired
    private QsTuGridDao qsTuGridDao;

    @Override
    public TuGridModel selectById(String id) {
       try{
           return qsTuGridDao.getGridModelByListid(id);
       }catch (Exception ex){
           log.error(ex.toString());
           return null;
       }
    }

    @Override
    public int updateById(TuGridModel m) {
        return 0;
    }

    @Override
    public int insert(TuGridModel m) {
        try{
            return getIntegerToInt(qsTuGridDao.insert(m));
        }catch (Exception ex){
            log.error(ex.toString());
            return 0;
        }
    }

    @Override
    public List<TuGridModel> selectByKw(int pageNo, HashMap _map) {
        setPage(_map, pageNo);
        List<TuGridModel>_models= qsTuGridDao.selectByMap(_map);
        TuGridModel.sizeImghandle(_models);
        return _models;
    }

    @Override
    public int selectByKwCount(HashMap map) {
        return getInt(qsTuGridDao.selectCountByMap(map));
    }

    ///////////////////////////////////////

    /**
     * 修改 文件 名称   使用list_id   *****
     * @param model
     * @return
     */
    public int updateReNameByMongdbKey(TuGridModel model){
        try{
            int _i=getIntegerToInt(qsTuGridDao.updateReNameByMongdbKey(model));
            return _i;
        }catch (Exception ex){
            log.error(ex.toString());
            return 0;
        }
    }

    /**
     * 修改 缩略图     使用list_id    ****
     * @param model
     * @return
     */
    public Integer updateGridThumbByMongodbKey(TuGridModel model){
        try{
            int _i=getIntegerToInt(qsTuGridDao.updateGridThumbByMongodbKey(model));
            log.info("updateGridThumbByMongodbKey---i="+_i);
            return _i;
        }catch (Exception ex){
            log.error(ex.toString());
            return 0;
        }
    }
}
