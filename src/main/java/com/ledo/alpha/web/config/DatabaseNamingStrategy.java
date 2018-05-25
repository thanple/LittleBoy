package com.ledo.alpha.web.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

/**
 * Created by Thanple on 2018/5/9.
 * 数据库命名策略
 * 1.表名：以t_开头，所有驼峰式映射下划线区分字段
 * 2.字段：除id外，其他都以c_开头，所有驼峰式映射下划线区分字段
 */
public class DatabaseNamingStrategy extends SpringPhysicalNamingStrategy {
    private static final String TABLE_PREFFIX = "t_";
    private static final String COLUMN_PREFFIX = "c_";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        Identifier oldIdentifier = super.toPhysicalTableName(name, jdbcEnvironment);
        return getIdentifier( TABLE_PREFFIX + oldIdentifier.getText(), oldIdentifier.isQuoted() , jdbcEnvironment);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        Identifier oldIdentifier = super.toPhysicalColumnName(name, jdbcEnvironment);
        if("id".equals(oldIdentifier.getText())) {
            return oldIdentifier;
        }
        return getIdentifier( COLUMN_PREFFIX + oldIdentifier.getText(), oldIdentifier.isQuoted() , jdbcEnvironment);
    }
}
