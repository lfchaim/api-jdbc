package com.whs.apijdbc.cache;

import java.util.Date;
import java.util.List;

public class BaseCacheList extends BaseCache {

	private List list;

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public void setList(Date expiration, List list) {
		this.setExpiration(expiration);
		this.list = list;
	}

	public void setList(Date baseDate, int field, int quantity, List list) {
		this.setExpiration(baseDate, field, quantity);
		this.list = list;
	}

}
