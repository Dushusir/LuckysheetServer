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
	
	public TuGridModel getGridByUnique(TuGridModel model){
		StringBuffer sql=new StringBuffer("");
		sql.append("select * from t_tugrid qs where qs.grid_creator=? and qs.parent_list_id=? and qs.grid_name=?  and qs.grid_type=? and qs.grid_status = 1 and rownum=1");
	    log.info("qickSheet----sql="+sql.toString());
	    TuGridModel models=null;
	    try{
	    	models=jdbcTemplate.queryForObject(sql.toString(),new Object[]{model.getGrid_creator(),model.getParent_list_id(),model.getGrid_name(),model.getGrid_type().getI()}, modelRowMapper);
	    }catch (EmptyResultDataAccessException e) {
			return null;
		}
		return models;
	}
	
	public Map dirExsitGridByListid(String listid){
		StringBuffer sql=new StringBuffer("");
		sql.append("select LIST_ID,GRID_STATUS,GRID_TYPE from t_tugrid qs ");
		sql.append(" where qs.list_id=? and rownum=1");
	    log.info("qickSheet----sql="+sql.toString());
	    Map<String, Object> models= jdbcTemplate.queryForMap(sql.toString(),new Object[]{listid});
		return models;
	}
	
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
	
	public int insertGridUploadFile(TuGridModel model){
		log.info("model:"+model.getGrid_thumb());
		List arr=new ArrayList();
		StringBuffer sql=new StringBuffer("");
		sql.append(" insert into t_tugrid ");
		sql.append(" (list_id,parent_list_id,grid_name,grid_introduction,grid_tag,mongodbkey,grid_statistic_sheet,grid_share_status,");
		if(model.getGrid_status()==1){
			sql.append("category_id,");
		}
		if(model.getGrid_thumb()!=null){
			sql.append("grid_thumb,");
		}
	    sql.append("uploadsize,mongodbsize,grid_creator,grid_type,grid_create_time)values (?,?,?,?,?,?,?,?");
	    arr.add(model.getList_id());
	    arr.add(model.getParent_list_id());
	    arr.add(model.getGrid_name());
	    arr.add(model.getGrid_introduction());
	    arr.add(model.getGrid_tag());
	    arr.add(model.getMongodbkey());
	    arr.add(model.getGrid_statistic_sheet());
	    arr.add(model.getGrid_share_status());
	    if(model.getGrid_status()==1){
	    	sql.append(",?");
	    	arr.add(model.getCategory_id());
	    }
	    if(model.getGrid_thumb()!=null){
	    	sql.append(",?");
	    	arr.add(model.getGrid_thumb());
	    }
	    sql.append(",?,?,?,?,sysdate)");
	    arr.add(model.getUploadsize());
	    arr.add(model.getMongodbsize());
	    arr.add(model.getGrid_creator());
	    arr.add(model.getGrid_type().getI());
	    log.info("qickSheet----sql="+sql.toString());
	     return jdbcTemplate.update(sql.toString(),arr.toArray());
	}
	
	public void updateGridInfoByListId(TuGridModel model){
		log.info("id:"+model.getList_id());
		StringBuffer sql=new StringBuffer("");
		List arr=new ArrayList();
		sql.append(" update t_tugrid set ");
	    if(model.getGrid_statistic_sheet()!=null){
	    	sql.append("grid_statistic_sheet=?,");
	    	arr.add(model.getGrid_statistic_sheet());
	    }
	    if(model.getMongodbsize()!=null){
	    	sql.append("mongodbsize=?,");
		    arr.add(model.getMongodbsize());
	    }
	    sql.append("grid_update_time = sysdate where list_id = ?");
	    arr.add(model.getList_id());
	    Object[] obj=arr.toArray();
	    log.info("qickSheet----sql="+sql.toString());
	   jdbcTemplate.update(sql.toString(),obj);
	}
	
	RowMapper<TuGridModel> modelRowMapper = new RowMapper()
    {
        public TuGridModel mapRow(ResultSet rs, int rowNum)
                throws SQLException
        {
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
		StringBuffer sql=new StringBuffer("");
		sql.append(" update t_tugrid set ");
	    sql.append("grid_thumb= ?,grid_update_time = sysdate where list_id = ?");
	    log.info("updateGridThumbByMongodbKey----sql="+sql.toString());
	    return jdbcTemplate.update(sql.toString(),new Object[]{model.getGrid_thumb() , model.getList_id()});
	}
     
     public Integer updateReNameByMongdbKey(TuGridModel model) {
 		log.info("id:"+model.getList_id());
 		StringBuffer sql=new StringBuffer("");
 		sql.append(" update t_tugrid set ");
 	    sql.append("grid_name= ?,grid_update_time = sysdate where list_id = ?");
 	    log.info("updateReNameByMongdbKey----sql="+sql.toString());
 	    return jdbcTemplate.update(sql.toString(),new Object[]{model.getGrid_name() , model.getList_id()});
 	}
     
	public Integer updateReName(TuGridModel model) {
		log.info("id:"+model.getList_id());
 		StringBuffer sql=new StringBuffer("");
 		sql.append(" update t_tugrid set ");
 	    sql.append("grid_name= ?,grid_update_time = sysdate where list_id = ? and grid_creator=?");
 	    log.info("updateReName----sql="+sql.toString());
 	    return jdbcTemplate.update(sql.toString(),new Object[]{model.getGrid_name() , model.getList_id() , model.getGrid_creator()});
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
    
	public void updateCountByListId(HashMap map) {
		log.info("map:"+map.toString());
		List arr=new ArrayList();
		StringBuffer sql=new StringBuffer("");
		sql.append("update  t_tugrid set field=? where list_id = ? ");
	    arr.add(Integer.valueOf(map.get("field").toString())+1);
	    arr.add(map.get("list_id"));
	    log.info("updateCountByListId----sql="+sql.toString());
	    jdbcTemplate.update(sql.toString(),arr.toArray());
	}

	public Long selectCountByTable() {
		StringBuffer sql=new StringBuffer("");
		sql.append("select  count(*) from t_tugrid where grid_type>0");
		Long count= jdbcTemplate.queryForObject(sql.toString(),Long.class);
		log.info("selectCountByTable----count="+count);
		return count;
	}

	public String getPath(String list_id) {
        StringBuffer sql=new StringBuffer("");
		sql.append("select wm_concat(t.grid_name||'(=*=)'||t.list_id||'[=*=]') from t_tugrid t start with t.list_id = ? connect by t.list_id = prior t.parent_list_id");
		log.info("getPath----sql="+sql);
		String path= jdbcTemplate.queryForObject(sql.toString(),new Object[]{list_id},String.class);
		return path;
	}

	public TuGridModel selectGridCreatorByMongodbkey(String mongodbkey) {
        StringBuffer sql=new StringBuffer("");
		sql.append("select grid_creator,list_id,grid_status,grid_share_status,mongodbkey,grid_type,grid_statistic_sheet,mongodbsize from t_tugrid qs where mongodbkey=?");
		sql.append(" where qs.list_id=? and rownum>=1");
	    log.info("selectGridCreatorByMongodbkey----sql="+sql.toString());
	    TuGridModel models= jdbcTemplate.queryForObject(sql.toString(),new Object[]{mongodbkey},modelRowMapper);
		return models;
	}

	public TuGridModel selectByUserAndListId(TuGridModel _model) {
		StringBuffer sql=new StringBuffer("");
		sql.append("select * from t_tugrid qs ");
		sql.append(" where qs.list_id=? and qs.grid_creator=? and rownum=1");
	    log.info("selectByUserAndListId----sql="+sql.toString());
	    TuGridModel models= jdbcTemplate.queryForObject(sql.toString(),new Object[]{_model.getList_id(),_model.getGrid_creator()}, modelRowMapper);
		return models;
	}
	

	public List<TuGridModel> selectDirAllByUser(HashMap<String, Object> _map) {
		StringBuffer sql=new StringBuffer("");
		sql.append("select list_id,parent_list_id,grid_name,grid_type,grid_share_status, grid_create_time,grid_update_time,u.user_nickname from t_tugrid tu ");
		sql.append(" LEFT JOIN t_user u ON tu.grid_creator=u.user_id where parent_list_id=? and grid_creator=? and grid_status=1");
	    log.info("selectDirAllByUser----sql="+sql.toString());
		 List<TuGridModel> models=null;
		    try{
		    	models=jdbcTemplate.query(sql.toString(),new Object[]{_map.get("parent_list_id"),_map.get("grid_creator")},new BeanPropertyRowMapper(TuGridModel.class));
		    }catch (EmptyResultDataAccessException e) {
				return null;
			}
		return models;
	}

	public List<TuGridModel> selectDirByUser(HashMap<String, Object> _map) {
		StringBuffer sql=new StringBuffer("");
		sql.append("select list_id,parent_list_id,grid_name,grid_type,grid_share_status,grid_create_time,grid_update_time,u.user_nickname from t_tugrid tu ");
		sql.append(" LEFT JOIN t_user u ON tu.grid_creator=u.user_id where parent_list_id=? and grid_creator=? and grid_status=1 and grid_type=0");
	    log.info("selectDirByUser----sql="+sql.toString());
	    List<TuGridModel> models= jdbcTemplate.query(sql.toString(),new Object[]{_map.get("parent_list_id"),_map.get("grid_creator")},new BeanPropertyRowMapper(TuGridModel.class));
		return models;
	}

	public List<TuGridModel> getChildList(String list_id) {
		StringBuffer sql=new StringBuffer("");
		sql.append("select list_id,grid_type,grid_filepath from t_tugrid t ");
		sql.append(" start with t.list_id = ? connect by t.parent_list_id = prior t.list_id");
	    log.info("getChildList----sql="+sql.toString());
	    List<TuGridModel> models= jdbcTemplate.query(sql.toString(),new Object[]{list_id},new BeanPropertyRowMapper(TuGridModel.class));
		return models;
	}

	public List<TuGridModel> selectCurrentDirByUser(HashMap<String, Object> _map) {
	    List arr=new ArrayList();
		StringBuffer sql=new StringBuffer("");
		sql.append("select list_id,parent_list_id,grid_name,grid_type,grid_share_status,grid_thumb,grid_create_time,grid_update_time,u.user_nickname,");
		sql.append(" case when uploadsize>0 then 1 else 0 end as uploadsize from t_tugrid tu LEFT JOIN t_user u ON tu.grid_creator=u.user_id ");
		sql.append("where parent_list_id=? and grid_creator=? and grid_status=1");
		arr.add(_map.get("parent_list_id"));
		arr.add(_map.get("grid_creator"));
		if(_map.get("orderBy")!=null){
			sql.append("?");
			arr.add(_map.get("orderBy"));
		}
	    log.info("selectCurrentDirByUser----sql="+sql.toString());
	    List<TuGridModel> models= jdbcTemplate.query(sql.toString(),arr.toArray(),new BeanPropertyRowMapper(TuGridModel.class));
		return models;
	}

	public Integer insertDir(TuGridModel model) {
        StringBuffer sql=new StringBuffer("");
        List arr=new ArrayList();
		sql.append("insert into t_tugrid (list_id,parent_list_id,grid_name,grid_creator,grid_type,grid_create_time)values (?,?,?,?,?,sysdate)");
	    arr.add(model.getList_id());
	    arr.add(model.getParent_list_id());
	    arr.add(model.getGrid_name());    
	    arr.add(model.getGrid_creator());
	    arr.add(model.getGrid_type().getI());
	    log.info("insertDir----sql="+sql.toString());
	    return jdbcTemplate.update(sql.toString(),arr.toArray());
	}

	public Integer updateParentListId(TuGridModel model) {
		log.info("id:"+model.getList_id());
		StringBuffer sql=new StringBuffer("");
		sql.append(" update t_tugrid set ");
	    sql.append("parent_list_id= ?,grid_update_time = sysdate where list_id = ? and grid_creator=?");
	    log.info("updateParentListId----sql="+sql.toString());
	    return jdbcTemplate.update(sql.toString(),new Object[]{model.getGrid_thumb() , model.getList_id(),model.getGrid_creator()});
	}

	public List<TuGridModel> selectTableList(HashMap<String, Object> map) {
		List arr=new ArrayList();
		StringBuffer sql=new StringBuffer("");
		sql.append("select list_id,grid_name,grid_type,grid_thumb,grid_create_time,grid_update_time,case when u.user_nickname is not null then u.user_nickname else u.user_socialapp_name end as user_nickname from t_tugrid tu ");
		sql.append("LEFT JOIN t_user u ON tu.grid_creator=u.user_id where grid_status=1 and grid_share_status=1 ");
		if(map.get("grid_creator")!=null){
			sql.append("and grid_creator=?");
			arr.add(map.get("grid_creator"));
		}
		if(map.get("kw")!=null){
			sql.append("( grid_name like '%");
			sql.append(map.get("kw"));
			sql.append("%' or grid_tag like '%");
			sql.append(map.get("kw"));
			sql.append("%' )");
		}
		if(map.get("category_id")!=null){
			sql.append("and category_id in ( select p.id from category p start with p.id = ? connect by p.pid = prior p.id )");
			arr.add(map.get("category_id"));
		}
		if(map.get("orderBy")!=null){
			sql.append("?");
			arr.add(map.get("orderBy"));
		}
	    log.info("selectTableList----sql="+sql.toString());
	    List<TuGridModel> models= jdbcTemplate.query(sql.toString(),arr.toArray(),new BeanPropertyRowMapper(TuGridModel.class));
	    return models;
	}

	public List<TuGridModel> getTrash() {
		List arr=new ArrayList();
		StringBuffer sql=new StringBuffer("");
		sql.append("select list_id,grid_type,grid_filepath from t_tugrid ");
		sql.append("where parent_list_id='-1' and grid_update_time < to_char(sysdate-1) ");
	    log.info("getTrash----sql="+sql.toString());
	    List<TuGridModel> models= jdbcTemplate.query(sql.toString(),arr.toArray(),new BeanPropertyRowMapper(TuGridModel.class));
	    return models;
	}

	public List<TuGridModel> selectByUserKeyWord_Status(HashMap<String, Object> map) {
	        List arr=new ArrayList();
			StringBuffer sql=new StringBuffer("");
			sql.append("select list_id,parent_list_id,grid_name,grid_type,grid_share_status,grid_thumb,grid_create_time,grid_update_time,u.user_nickname from t_tugrid tu ");
			sql.append("LEFT JOIN t_user u ON tu.grid_creator=u.user_id where grid_creator=?");
			arr.add(map.get("grid_creator"));
			if(map.get("grid_status")!=null ){
				if(Integer.valueOf(map.get("grid_status").toString())==1){
					sql.append(" and grid_status=1 ");
				}else if(Integer.valueOf(map.get("grid_status").toString())==-1){
					sql.append(" and grid_status=-1 ");
				}	
			}
			if(map.get("kw")!=null){
				sql.append("( grid_name like '%");
				sql.append(map.get("kw"));
				sql.append("%' or grid_tag like '%");
				sql.append(map.get("kw"));
				sql.append("%' )");
			}
			if(map.get("orderBy")!=null){
				sql.append("?");
				arr.add(map.get("orderBy"));
			}
		    log.info("selectByUserKeyWord_Status----sql="+sql.toString());
		    List<TuGridModel> models= jdbcTemplate.query(sql.toString(),arr.toArray(),new BeanPropertyRowMapper(TuGridModel.class));
		return models;
	}

	public Integer updateShare(TuGridModel model) {
			List arr=new ArrayList();
			StringBuffer sql=new StringBuffer("");
			sql.append("update  t_tugrid set grid_share_status=1,category_id=?,grid_update_time=sysdate where list_id = ? and grid_creator=? ");
		    arr.add(model.getCategory_id());
		    arr.add(model.getList_id());
		    arr.add(model.getGrid_creator());
		    log.info("updateShare----sql="+sql.toString());
		return jdbcTemplate.update(sql.toString(),arr.toArray());
	}

	public Integer updateCancleShare(TuGridModel model) {
        List arr=new ArrayList();
		StringBuffer sql=new StringBuffer("");
		sql.append("update  t_tugrid set grid_share_status=0,category_id=0,grid_update_time=sysdate where list_id = ? and grid_creator=? ");
	    arr.add(model.getList_id());
	    arr.add(model.getGrid_creator());
	    log.info("updateCancleShare----sql="+sql.toString());
	return jdbcTemplate.update(sql.toString(),arr.toArray());
	}

	public Integer setForbidShare(HashMap map) {
         //List arr=new ArrayList();
 		StringBuffer sql=new StringBuffer("");
 		sql.append("update  t_tugrid set grid_share_status=0,category_id=0,allow_share_status=0 where list_id in (?) and grid_type>0 ");
 	    List arr=(List) map.get("list_ids");
 	    String listId="";
 	    for (Object object : arr) {
 	    	listId=listId+object.toString()+",";
		}
 	   listId=listId.substring(0, listId.length()-1);
 	    log.info("setForbidShare----sql="+sql.toString()+";;listId="+listId);
		return jdbcTemplate.update(sql.toString(),new Object[]{listId});
	}

	public Integer updateGridStatusRecovery(TuGridModel model) {
		//文件夹/文件 标记为恢复 回收站 
		List arr=new ArrayList();
		StringBuffer sql=new StringBuffer("");
		sql.append("update t_tugrid set grid_status=1,grid_name=?,parent_list_id=?,grid_update_time=sysdate where list_id = ? and grid_creator=? ");
	    arr.add(model.getGrid_name());
	    arr.add(model.getParent_list_id());
		arr.add(model.getList_id());
	    arr.add(model.getGrid_creator());
	    log.info("updateGridStatusRecovery----sql="+sql.toString());
	    return jdbcTemplate.update(sql.toString(),arr.toArray());
	}

	public void updateGridStatusRecoveryChild(TuGridModel model) {     
			StringBuffer sql=new StringBuffer("");
			sql.append("update t_tugrid set grid_status=1,grid_update_time=sysdate where list_id = in (SELECT list_id from qs_t_tugrid t start with t.list_id = ? connect by t.parent_list_id = prior t.list_id) ");
		    log.info("updateGridStatusRecoveryChild----sql="+sql.toString());
		    jdbcTemplate.update(sql.toString(),new Object[]{model.getList_id()});
	}

	public Integer deleteByIds(List<TuGridModel> models) {
		StringBuffer sql=new StringBuffer("");
 		sql.append("delete from t_tugrid where list_id in (?) ");
 		String listId="";
 		for (TuGridModel tuGridModel : models) {
 			listId=listId+tuGridModel.getList_id()+",";
		}
 		listId=listId.substring(0,listId.length()-1);
 		log.info("deleteByIds--sql:"+sql);
		return jdbcTemplate.update(sql.toString(),new Object[]{listId});
	}

	public Integer updateGridStatusDel(TuGridModel model) {
		StringBuffer sql=new StringBuffer("");
		sql.append("update t_tugrid set  grid_status=-1,parent_list_id='-1',grid_update_time=sysdate where list_id = ? and grid_creator=?");
	    log.info("updateGridStatusDel----sql="+sql.toString());
	    return jdbcTemplate.update(sql.toString(),new Object[]{model.getList_id(),model.getGrid_creator()});
	}

	public int updateGridStatusDelChild(TuGridModel model) {
        StringBuffer sql=new StringBuffer("");
		sql.append("update t_tugrid set grid_status=-1,grid_update_time=sysdate where list_id = in (SELECT list_id from qs_t_tugrid t start with t.list_id = ? connect by t.parent_list_id = prior t.list_id) ");
	    log.info("updateGridStatusDelChild----sql="+sql.toString());
	    return jdbcTemplate.update(sql.toString(),new Object[]{model.getList_id()});
	}

}

	
