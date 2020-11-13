package com.xc.luckysheet.dao;

import com.xc.luckysheet.entity.TuGridModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class QsTuGridDao {

	@Resource(name = "mysqlJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	

	public TuGridModel getGridModelByListid(String listid){
		TuGridModel models=null;
		try{
			StringBuffer sql=new StringBuffer("");
			sql.append("select * from t_tugrid qs ");
			sql.append(" where qs.list_id=? and rownum=1");
		    log.info("qickSheet----sql="+sql.toString());
		    models= jdbcTemplate.queryForObject(sql.toString(),new Object[]{listid}, modelRowMapper);
		}catch (Exception e) {
			log.error("getGridModelByListid---error:"+"listid"+e);
		}
		return models;
	}
	

	
	RowMapper<TuGridModel> modelRowMapper = new RowMapper(){
        public TuGridModel mapRow(ResultSet rs, int rowNum)
                throws SQLException {
        	TuGridModel model = new TuGridModel();
            model.setList_id(rs.getString("LIST_ID"));
            model.setParent_list_id(rs.getString("PARENT_LIST_ID"));
            model.setGrid_name(rs.getString("GRID_NAME"));
            model.setGrid_introduction(rs.getString("GRID_INTRODUCTION"));
            model.setGrid_thumb(rs.getBytes("GRID_THUMB"));
            model.setGrid_creator(rs.getLong("GRID_CREATOR"));
            model.setMongodbkey(rs.getString("MONGODBKEY"));
            model.setGrid_statistic_sheet(rs.getInt("GRID_STATISTIC_SHEET"));
            model.setGrid_filepath(rs.getString("GRID_FILEPATH"));
            model.setGrid_status(rs.getInt("GRID_STATUS"));
            model.setGrid_datashow_key(rs.getString("GRID_DATASHOW_KEY"));
            model.setGrid_datashow_public(rs.getInt("GRID_DATASHOW_PUBLIC"));
            model.setGrid_share_status(rs.getInt("GRID_SHARE_STATUS"));
            model.setAllow_share_status(rs.getInt("ALLOW_SHARE_STATUS"));
            model.setCategory_id(rs.getLong("CATEGORY_ID"));
            model.setGrid_tag(rs.getString("GRID_TAG"));
            model.setGrid_statistic_favorite(rs.getInt("GRID_STATISTIC_FAVORITE"));
            model.setGrid_statistic_comment(rs.getInt("GRID_STATISTIC_COMMENT"));
            model.setGrid_statistic_like(rs.getInt("GRID_STATISTIC_LIKE"));
            model.setGrid_statistic_view(rs.getLong("GRID_STATISTIC_VIEW"));
            model.setUploadsize(rs.getLong("UPLOADSIZE"));
            model.setMongodbsize(rs.getLong("MONGODBSIZE"));
            return model;
        }
    };
    
   

	public Integer updateGridThumbByMongodbKey(TuGridModel model) {
		log.info("id:"+model.getList_id());
		StringBuffer sql=new StringBuffer();
		sql.append(" update t_tugrid set ");
	    sql.append("grid_thumb= ?,grid_update_time = sysdate where list_id = ?");
	    log.info("updateGridThumbByMongodbKey----sql="+sql.toString());
	    return jdbcTemplate.update(sql.toString(),new Object[]{model.getGrid_thumb() , model.getList_id()});
	}
     
     public Integer updateReNameByMongdbKey(TuGridModel model) {
 		log.info("id:"+model.getList_id());
 		StringBuffer sql=new StringBuffer();
 		sql.append(" update t_tugrid set ");
 	    sql.append("grid_name= ?,grid_update_time = sysdate where list_id = ?");
 	    log.info("updateReNameByMongdbKey----sql="+sql.toString());
 	    return jdbcTemplate.update(sql.toString(),new Object[]{model.getGrid_name() , model.getList_id()});
 	}
     


	public Integer insert(TuGridModel model) {
		log.info("model:"+model.getGrid_thumb());
		List arr=new ArrayList();
		StringBuffer sql=new StringBuffer("");
		sql.append("insert into t_tugrid (list_id,grid_name,grid_introduction,grid_creator,grid_type,grid_create_time)values (?,?,?,?,?,sysdate)");
	    arr.add(model.getList_id());
	    arr.add(model.getGrid_name());
	    arr.add(model.getGrid_introduction());
	    arr.add(model.getGrid_creator());
	    arr.add(model.getGrid_type().getI());
	    log.info("qickSheet----sql="+sql.toString());
	    return jdbcTemplate.update(sql.toString(),arr.toArray());
	}

	public List<TuGridModel> selectByMap(HashMap map) {
		StringBuffer sql=new StringBuffer("");
		sql.append("select *,getcategorypath(category_id) as category_path,CASE when grid_type=0 THEN getUserDirSum(list_id) ELSE 0 END as dirsize,");
	    sql.append("CASE when user_nickname is NOT NULL THEN user_nickname ELSE user_socialapp_name  END as user_nickname1,u.user_id as user_id1,u.user_email as user_email1 from ");
		sql.append("t_tugrid LEFT JOIN t_user u on t_tugrid.grid_creator=u.user_id where 1=1 and grid_type>0 ");
		List arr=new ArrayList();
		if(map.get("grid_name") != null && map.get("grid_name") !=""){
			sql.append("and grid_name =? ");
			arr.add(map.get("grid_name"));
		}
		if(map.get("grid_type") != null){
			sql.append("and grid_type =? ");
			arr.add(map.get("grid_type"));
		}
		
		if(map.get("category_id") != null){
			sql.append("and category_id =? ");
			arr.add(map.get("category_id"));
		}
		if(map.get("grid_share_status") != null){
			sql.append("and grid_share_status =? ");
			arr.add(map.get("grid_share_status"));
		}
		if(map.get("grid_creator") != null){
			sql.append("and grid_creator =? ");
			arr.add(map.get("grid_creator"));
		}
		if(map.get("grid_status") != null){
			sql.append("and grid_status =? ");
			arr.add(map.get("grid_status"));
		}
		
		if(map.get("allow_share_status") != null){
			sql.append("and allow_share_status =0 ");
		}
		
		if(map.get("start_grid_create_time") != null){
			sql.append("and grid_create_time =? ");
			arr.add(map.get("start_grid_create_time"));
		}
		
		if(map.get("end_grid_create_time") != null){
			sql.append("and grid_create_time =? ");
			arr.add(map.get("end_grid_create_time"));
		}
		
		if(map.get("start_grid_update_time") != null){
			sql.append("and grid_update_time =? ");
			arr.add(map.get("start_grid_update_time"));
		}
		if(map.get("end_grid_update_time") != null){
			sql.append("and grid_update_time =? ");
			arr.add(map.get("end_grid_update_time"));
		}
		sql.append("order by list_id desc limit ?,? ");
		arr.add(map.get("start"));
		arr.add(map.get("pageCount"));
	    log.info("selectByMap----sql="+sql.toString());
	    List<TuGridModel> models=null;
	    try{
	    	models=jdbcTemplate.query(sql.toString(),arr.toArray(),new BeanPropertyRowMapper(TuGridModel.class));
	    	
	    }catch (EmptyResultDataAccessException e) {
			return null;
		}
	    return models;
	}
    
    public Integer selectCountByMap(HashMap map) {
		StringBuffer sql=new StringBuffer("");
		List arr=new ArrayList();
		sql.append("select count(*) from t_tugrid qs ");
		sql.append("  where 1=1 and grid_type>0 ");
		if(map.get("grid_name") != null && map.get("grid_name") !=""){
			sql.append("and grid_name =? ");
			arr.add(map.get("grid_name"));
		}
		if(map.get("grid_type") != null){
			sql.append("and grid_type =? ");
			arr.add(map.get("grid_type"));
		}
		
		if(map.get("category_id") != null){
			sql.append("and category_id =? ");
			arr.add(map.get("category_id"));
		}
		if(map.get("grid_share_status") != null){
			sql.append("and grid_share_status =? ");
			arr.add(map.get("grid_share_status"));
		}
		if(map.get("grid_creator") != null){
			sql.append("and grid_creator =? ");
			arr.add(map.get("grid_creator"));
		}
		if(map.get("grid_status") != null){
			sql.append("and grid_status =? ");
			arr.add(map.get("grid_status"));
		}
		
		if(map.get("allow_share_status") != null){
			sql.append("and allow_share_status =0 ");
		}
		
		if(map.get("start_grid_create_time") != null){
			sql.append("and grid_create_time =? ");
			arr.add(map.get("start_grid_create_time"));
		}
		
		if(map.get("end_grid_create_time") != null){
			sql.append("and grid_create_time =? ");
			arr.add(map.get("end_grid_create_time"));
		}
		
		if(map.get("start_grid_update_time") != null){
			sql.append("and grid_update_time =? ");
			arr.add(map.get("start_grid_update_time"));
		}
		if(map.get("end_grid_update_time") != null){
			sql.append("and grid_update_time =? ");
			arr.add(map.get("end_grid_update_time"));
		}
	    log.info("selectCountByMap----sql="+sql.toString());
	    Integer count= jdbcTemplate.queryForObject(sql.toString(),arr.toArray(),Integer.class);
	    log.info("selectCountByMap-----count:"+count);
	    return count;
	}
    


}

	
