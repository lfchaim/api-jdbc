package com.whs.apijdbc.test.main;

import java.sql.Connection;

import com.whs.apijdbc.util.ConnectionManager;
import com.whs.apijdbc.util.DBUtil;
import com.whs.apijdbc.util.dto.Table;

public class SmartReverseTest {

	public static void main(String[] args) {
		process();
	}

	private static void process() {
		Connection conn = null;
		ConnectionManager cm = new ConnectionManager();
		String url = "jdbc:sqlserver://10.164.172.196:1433;databaseName=GeoImovel;";
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String usr = "iisu";
		String pwd = "5$Tj75an";
		try{
			conn = cm.openConnection(driver, url, usr, pwd);
			Table[] tables = DBUtil.loadTables(conn, null, null, null);
			System.out.println("FIM - "+tables.length);
		}catch( Exception e ) {
			e.printStackTrace();
		}finally {
			try {conn.close();}catch(Exception e) {}
		}
	}
}
