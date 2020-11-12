package com.xc.common.config.mybatisplus.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据条件物理删除
 */
public class PhysicsDelete extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sqlMethod = "<script>\nDELETE FROM %s %s\n</script>";
        String sql = String.format(sqlMethod, tableInfo.getTableName(), this.sqlWhereEntityWrapper(true, tableInfo));
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, modelClass);
        return this.addDeleteMappedStatement(mapperClass, "physicsDelete", sqlSource);
    }
}
