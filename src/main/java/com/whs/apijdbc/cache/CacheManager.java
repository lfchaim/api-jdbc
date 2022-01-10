package com.whs.apijdbc.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {

	private static CacheManager me;
	private static Map map = new HashMap();

	protected CacheManager() {

	}

	public static CacheManager getInstance() {
		if (me == null)
			me = new CacheManager();
		return me;
	}

	public void put(String val, Object obj) {
		map.put(val, obj);
	}

	public Object get(String val) {
		return map.get(val);
	}

	public Object remove(String val) {
		return map.remove(val);
	}

	public void clear() {
		map.clear();
	}

	public boolean contains(String val) {
		return (map.get(val) != null);
	}

}
