package com.whs.apijdbc.util.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Table implements Serializable {
	
	public static final long serialVersionUID = 1l;

	private String tableSchem;
	private String remarks;
	private String tableName;
	private String tableCat;
	private String tableType;
	
	private Column[] column;
	private PK[] pk;
	private ForeignKey[] importedKey;
	private ForeignKey[] exportedKey;
	
}
