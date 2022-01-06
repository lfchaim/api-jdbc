package com.whs.apijdbc.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;

    static {
        config.setJdbcUrl( System.getProperty("DB_GI_URL") );
        config.setUsername( System.getProperty("DB_GI_USR") );
        config.setPassword( System.getProperty("DB_GI_PWD") );
        config.setDriverClassName( System.getProperty("DB_GI_DRIVER") );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
	
}
