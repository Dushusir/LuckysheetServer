package com.xc.luckysheet.postgre.dao;


import com.google.gson.Gson;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.xc.luckysheet.entity.TestDataModel;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 */
@Slf4j
@Repository
public class P_TUserDao {

    @Resource(name = "postgreJdbcTemplate")
    private JdbcTemplate jdbcTemplate_postgresql;

    //添加jsonb
    //批量添加
    public void InsertIntoBatch(List<TestDataModel>models){
        String sql = "insert into testpg (id,block_id,index,list_id,status,json_data) values " +
                " (nextval('ay_json_test_id_seq'),?,?,?,?,?)";
        List<Object[]>batch=new ArrayList<Object[]>();
        for(TestDataModel b : models){
            List<Object> objectList=new ArrayList<Object>();
            objectList.add(b.getList_id());
            objectList.add(b.getBlock_id());
            objectList.add(b.getIndex());
            objectList.add(b.getStatus());
            objectList.add(b.getJson_data());
            PGobject pg=new PGobject();
            pg.setType("json");
            try {
                pg.setValue(new Gson().toJson(b.getJson_data(),DBObject.class));
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
            objectList.add(pg);

            Object[] params=(Object[])objectList.toArray(new Object[objectList.size()]);
            batch.add(params);
        }

        try{
            //long sttime = System.currentTimeMillis();
            jdbcTemplate_postgresql.batchUpdate(sql,batch);
            //long endtime = System.currentTimeMillis();
            //System.out.println("保存:"+(endtime - sttime) / 1000.0 + "");
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
    }
    public int addJsonb(Integer index,String list_id){
        //String model="{\"name\":\"Sheet1\",\"color\":\"\",\"index\":0,\"chart\":[],\"status\":1,\"order\":0,\"column\":60,\"row\":84,\"celldata\":[],\"visibledatarow\":[],\"visibledatacolumn\":[],\"rowsplit\":[],\"ch_width\":4748,\"rh_height\":1790,\"jfgird_select_save\":{},\"jfgrid_selection_range\":{},\"scrollLeft\":0,\"scrollTop\":0,\"config\":{}}";
        String model="{\"name\":\"Sheet1\",\"color\":\"\",\"index\":0,\"chart\":[],\"status\":1,\"order\":0,\"column\":60,\"row\":84,\"celldata\":[{\"c\":1,\"r\":1,\"v\":\"v1\"},{\"c\":1,\"r\":2,\"v\":\"v2\"},{\"c\":1,\"r\":3,\"v\":\"v3\"},{\"c\":1,\"r\":4,\"v\":\"v4\"}],\"visibledatarow\":[],\"visibledatacolumn\":[],\"rowsplit\":[],\"ch_width\":4748,\"rh_height\":1790,\"jfgird_select_save\":{},\"jfgrid_selection_range\":{},\"scrollLeft\":0,\"scrollTop\":0,\"config\":{}}";
        DBObject bson=(DBObject) JSON.parse(model);
        bson.put("block_id","block_id");
        String block_id="block_id";
        PGobject pg=new PGobject();
        pg.setType("json");
        try {
            //pg.setValue("{\"key\":\"value\"}");
            pg.setValue(new Gson().toJson(bson));
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        String sql = "insert into testpg (id,block_id,index,list_id,status,json_data) values " +
                " (?,?,?,?,?,?)";
        try{
            long l=getMaxId()+1;
            return jdbcTemplate_postgresql.update(sql,l,block_id,index,list_id,0,pg
            );
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return 0;
    }
    public int addJsonb(Integer index,String list_id,boolean error){
        String model="{\"name\":\"Sheet1\",\"color\":\"\",\"index\":0,\"chart\":[],\"status\":1,\"order\":0,\"column\":60,\"row\":84,\"celldata\":[{\"c\":1,\"r\":1,\"v\":\"v1\"},{\"c\":1,\"r\":2,\"v\":\"v2\"},{\"c\":1,\"r\":3,\"v\":\"v3\"},{\"c\":1,\"r\":4,\"v\":\"v4\"}],\"visibledatarow\":[],\"visibledatacolumn\":[],\"rowsplit\":[],\"ch_width\":4748,\"rh_height\":1790,\"jfgird_select_save\":{},\"jfgrid_selection_range\":{},\"scrollLeft\":0,\"scrollTop\":0,\"config\":{}}";
        DBObject bson=(DBObject) JSON.parse(model);
        bson.put("block_id","block_id");
        String block_id="block_id";
        PGobject pg=new PGobject();
        pg.setType("json");
        try {
            pg.setValue(new Gson().toJson(bson));
        } catch (SQLException e) {
            log.error(e.getMessage());
        }


        String sql = "insert into testpg (id,block_id,index,list_id,status,json_data) values " +
                " (?,?,?,?,?,?)";
        try{
            long l=getMaxId()+1;
            if(error){
                l=l-1;
            }
            return jdbcTemplate_postgresql.update(sql,l,block_id,index,list_id,0,pg
            );
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return 0;
    }
    public int addJsonb2(String username,String password,String remark){
        HashMap<String,Object> _map=new HashMap<String,Object>();
        _map.put("number",1);
        _map.put("string","str");
        ArrayList<String> _list=new ArrayList<String>();
        _list.add("list1");
        _list.add("list2");
        _list.add("list3");
        _list.add("list4");
        _map.put("list",_list);


        PGobject pg=new PGobject();
        pg.setType("json");
        try {
            //pg.setValue("{\"key\":\"value\"}");
            pg.setValue(new Gson().toJson(_map));
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        String sql = "insert into testpg (id,block_id,index,list_id,status,json_data) values " +
                " (?,?,?,?,?,?)";
        try{
            long l=getMaxId()+1;
            return jdbcTemplate_postgresql.update(sql,l,username,password,l,remark,new Date(),pg
            );
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return 0;
    }
    //添加hstore  先要执行  create EXTENSION hstore; 字段类型为hstore
    public int addJson(String username,String password,String remark){
        HashMap<String,Object> _map=new HashMap<String,Object>();
        _map.put("number",1);
        _map.put("string","str");
//        ArrayList<String> _list=new ArrayList<String>();
//        _list.add("list1");
//        _list.add("list2");
//        _list.add("list3");
//        _list.add("list4");
//        _map.put("list",_list);


        String sql = "insert into testpg (id,username,password,age,remark,updatetime,hstore) values " +
                " (?,?,?,?,?,?,?)";
        try{
            long l=getMaxId()+1;
            return jdbcTemplate_postgresql.update(sql,l,username,password,l,remark,new Date(),_map
            );
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return 0;
    }
    //添加json
    public int addJson1(String username,String password,String remark){

        PGobject pg=new PGobject();
        pg.setType("json");
        try {
            pg.setValue("{\"key\":\"value\"}");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        String sql = "insert into testpg (id,username,password,age,remark,updatetime,json) values " +
                " (?,?,?,?,?,?,?)";
        try{
            long l=getMaxId()+1;
            return jdbcTemplate_postgresql.update(sql,l,username,password,l,remark,new Date(),pg
            );
//            return jdbcTemplate_postgresql.update(sql,
//                    new Object[]{l,username,password,l,remark,new Date(),pg},
//                    new int[]{Types.OTHER,Types.OTHER,Types.OTHER,Types.OTHER,Types.OTHER,Types.OTHER,Types.OTHER}
//                    );
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return 0;
    }
    //添加hstore  先要执行  create EXTENSION hstore; 字段类型为hstore
    public int addHstore(String username,String password,String remark){
        HashMap<String,Object> _map=new HashMap<String,Object>();
        _map.put("number",1);
        _map.put("string","str");

        String sql = "insert into t_user (id,block_id,index,list_id,status,json_data) values " +
                " (?,?,?,?,?,?)";
        try{
            long l=getMaxId()+1;
            return jdbcTemplate_postgresql.update(sql,l,username,password,l,remark,new Date(),_map
            );
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return 0;
    }
    public TestDataModel selectJsonbById(int id){
        String sql = "select * from t_user where id=?;";
        try{
            return jdbcTemplate_postgresql.queryForObject(sql,new Object[]{id},modelUserRowMapper);
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return null;
    }
    RowMapper<TestDataModel> modelUserRowMapper = new RowMapper<TestDataModel>() {
        @Override
        public TestDataModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        	TestDataModel model = new TestDataModel();

            model.setId(rs.getInt("id"));
            model.setBlock_id(rs.getString("block_id"));
            model.setIndex(rs.getInt("index"));
            model.setList_id(rs.getString("list_id"));
            model.setStatus(rs.getInt("status"));

            PGobject pg=(PGobject)rs.getObject("json_data");
            DBObject db=new Gson().fromJson(pg.toString(),DBObject.class) ;
            model.setJson_data(db);

            return model;
        }
    };

    public int delById(int id){
        String sql = "delete from testpg where id="+id;
        try{
            return jdbcTemplate_postgresql.update(sql);
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return 0;
    }







    //添加
    public int add(String username,String password,String remark){
        String sql = "insert into testpg (id,username,password,age,remark,updatetime) values " +
                " (?,?,?,?,?,?)";
        try{
            long l=getMaxId()+1;
            return jdbcTemplate_postgresql.update(sql,l,username,password,l,remark,new Date());
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return 0;
    }

    //返回json格式数据
    public String getResulJson(){
        String sql="SELECT row_to_json(a.*) from (select b.id,b.username from testpg b where b.id = 21 ) a";
        try{
            return jdbcTemplate_postgresql.queryForObject(sql,new Object[]{},String.class);
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return "";
    }
    public TestDataModel selectById(int id){
        String sql = "select * from testpg where id=?;";
        try{
            return jdbcTemplate_postgresql.queryForObject(sql,new Object[]{id},modelRowMapper);
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return null;
    }
    private long getMaxId(){
        String sql="select max(id) from testpg ";
        try{
            return jdbcTemplate_postgresql.queryForObject(sql,new Object[]{},Long.class);
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return 0l;
    }

    public List<TestDataModel> select(){
        String sql = "select * from testpg";
        try{
            return jdbcTemplate_postgresql.query(sql,new Object[]{},modelRowMapper);
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return null;
    }

    RowMapper<TestDataModel> modelRowMapper = new RowMapper<TestDataModel>() {
        @Override
        public TestDataModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        	TestDataModel model = new TestDataModel();

        	model.setId(rs.getInt("id"));
            model.setBlock_id(rs.getString("block_id"));
            model.setIndex(rs.getInt("index"));
            model.setList_id(rs.getString("list_id"));
            model.setStatus(rs.getInt("status"));

            return model;
        }
    };

}


/*

CREATE TABLE t_user (
  id bigint NOT NULL,
  username character varying(100) ,
  password character varying(100) ,
  email character varying(50) ,
  useable bigint DEFAULT 0,
  addtime character varying(50) ,
  logintime character varying(50) ,
  loginip character varying(50) ,
  age bigint ,
  remark text,
  updatetime timestamp without time zone ,
  user_type_id bigint DEFAULT 0,
  money decimal(10,0) ,
  province character varying(50) ,
  city character varying(50) ,
  area character varying(50) ,
  address character varying(200) ,
  PRIMARY KEY (id)
)


*/
