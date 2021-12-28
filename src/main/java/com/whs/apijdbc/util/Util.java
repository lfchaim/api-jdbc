package com.whs.apijdbc.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Util {
	
	public static String toJSON(Object value) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(value);
	}
	
}
