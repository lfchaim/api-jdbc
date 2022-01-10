package com.whs.apijdbc.util.controller;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whs.apijdbc.cache.BaseCache;
import com.whs.apijdbc.cache.BaseCacheList;
import com.whs.apijdbc.cache.CacheManager;
import com.whs.apijdbc.util.DBUtil;
import com.whs.apijdbc.util.DataSource;
import com.whs.apijdbc.util.Util;
import com.whs.apijdbc.util.dto.Table;

@RestController
@RequestMapping("/dbmeta")
public class DBMetaController {

	@GetMapping("/tables")
	public Table[] listTables(@RequestParam(required = false) String schema) {
		Table[] tables = null;
		Connection conn = null;

		Date now = new Date();
		String key = "ALL_TABLES";
		int field = BaseCache.MINUTE;
		int quantity = 20; // 20 minutos
		CacheManager cm = CacheManager.getInstance();
		BaseCacheList bcList = (BaseCacheList) cm.get(key);
		if (bcList != null) {
			List list = bcList.getList();
			tables = Util.toArray(list, Table.class);
		} else {
			try {
				Calendar c1 = Calendar.getInstance();
				conn = DataSource.getConnection();
				Calendar c2 = Calendar.getInstance();
				System.out.println("Tempo: " + (c2.getTimeInMillis() - c1.getTimeInMillis()));
				c1 = Calendar.getInstance();
				tables = DBUtil.loadTables(conn, schema, null, null);
				c2 = Calendar.getInstance();
				System.out.println("FIM - " + tables.length + " Tempo: " + (c2.getTimeInMillis() - c1.getTimeInMillis()));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
			bcList = new BaseCacheList();
			bcList.setList(now, field, quantity, Util.toList(tables));
			cm.put(key, bcList);
		}
		return tables;
	}
}
