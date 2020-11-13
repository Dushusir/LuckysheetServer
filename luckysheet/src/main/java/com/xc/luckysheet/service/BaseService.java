package com.xc.luckysheet.service;


import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 */
public abstract class BaseService<T> {

    public abstract T selectById(String id);
    public abstract int updateById(T m);
    public abstract int insert(T m);

    public abstract List<T> selectByKw(int pageNo,HashMap map);
    public abstract int selectByKwCount(HashMap map);


    public static int getInt(Integer i){
        if(i==null){
            i=0;
        }
        return i;
    }
    //获取总页码 ,参数记录总数
    public static int getTotalPage(int total){
        return (total + pageCount - 1) / pageCount;
    }
    public static int getTotalPage(int total,int pageCount){
        return (total + pageCount - 1) / pageCount;
    }


    protected int getIntegerToInt(Integer i){
        return getInt(i);
    }

    public static int pageCount=20;

    //设置分页
    public static void setPage(HashMap<String,Object> _map,int pageNo){
        setPage(_map,pageNo,pageCount);
    }
    public static void setPage(HashMap<String,Object> _map,int pageNo,int pageCount){
        if (pageNo <= 0) {
            pageNo = 1;
        }

        //mysql 分页 limit
        int _start = (pageNo - 1) * pageCount;
        _map.put("pageCount",pageCount);
        _map.put("start",_start);


    }
    protected void setPaging(HashMap<String,Object> _map,int pageNo){
        setPage(_map,pageNo,pageCount);
    }
    protected void setPaging(HashMap<String,Object> _map,int pageNo, int pageCount){
        setPage(_map,pageNo,pageCount);
    }
    protected void setHasHMap(HashMap<String,Object> _map,String k,String v){
        if(v!=null && v.trim().length()>0){
            _map.put(k,v);
        }
    }
    protected void setHasHMap(HashMap<String,Object> _map,String k,Integer v){
        if(v!=null){
            _map.put(k,v);
        }
    }
    protected void setHasHMap(HashMap<String,Object> _map,String k,Long v){
        if(v!=null){
            _map.put(k,v);
        }
    }
    protected void setHasHMap(HashMap<String,Object> _map,String k,Enum v){
        if(v!=null){
            _map.put(k,v);
        }
    }

    //sort_f  排序字段类型 0文件名 1创建时间 2修改时间
    //sort_t 升序降序 0升序  1降序
    protected void setSort(HashMap<String,Object> _map,String sort_f,String sort_t){
        String _field="grid_name";
        if(sort_f!=null){
             if(sort_f.equals("1")){
                 _field="grid_create_time";
             }else  if(sort_f.equals("2")){
                 _field="grid_update_time";
             }
        }

        String _sort="ASC";
        if(sort_t!=null){
            if(sort_t.equals("1")){
                _sort="DESC";
            }
        }

        String _str=" order by "+_field+" "+_sort;
        setHasHMap(_map,"orderBy",_str);

    }

}
