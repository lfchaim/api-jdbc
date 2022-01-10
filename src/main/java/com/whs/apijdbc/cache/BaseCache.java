package com.whs.apijdbc.cache;

import java.util.Calendar;
import java.util.Date;

public class BaseCache {

	public static final int DAY_OF_MONTH = Calendar.DAY_OF_MONTH;
	public static final int HOUR = Calendar.HOUR;
	public static final int MINUTE = Calendar.MINUTE;
	public static final int SECOND = Calendar.SECOND;

	private Date expiration;

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public void setExpiration(Date baseDate, int field, int quantity) {
		Calendar c = Calendar.getInstance();
		c.setTime(baseDate);
		c.add(field, quantity);
		this.expiration = c.getTime();
	}

	public boolean expirated(Date baseDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(expiration);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(baseDate);
		return c1.before(c2);
	}

}
