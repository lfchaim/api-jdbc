package com.whs.apijdbc.util.controller;

import java.sql.Connection;
import java.util.Calendar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whs.apijdbc.util.DBUtil;
import com.whs.apijdbc.util.DataSource;
import com.whs.apijdbc.util.dto.Table;

@RestController
@RequestMapping("/dbmeta")
public class DBMetaController {

	@GetMapping("/tables")
	public Table[] listTables(@RequestParam(required = false) String schema) {
		Table[] tables = null;
		Connection conn = null;
		try{
			Calendar c1 = Calendar.getInstance();
			conn = DataSource.getConnection();
			Calendar c2 = Calendar.getInstance();
			System.out.println("Tempo: "+(c2.getTimeInMillis()-c1.getTimeInMillis()));
			c1 = Calendar.getInstance();
			tables = DBUtil.loadTables(conn, schema, null, null);
			c2 = Calendar.getInstance();
			System.out.println("FIM - "+tables.length+" Tempo: "+(c2.getTimeInMillis()-c1.getTimeInMillis()));
		}catch( Exception e ) {
			e.printStackTrace();
		}finally {
			try {conn.close();}catch(Exception e) {}
		}
		return tables;
	}
}
