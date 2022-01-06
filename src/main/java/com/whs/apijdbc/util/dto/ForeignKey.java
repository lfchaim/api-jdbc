package com.whs.apijdbc.util.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ForeignKey implements Serializable {

	public static final long serialVersionUID = 1l;
	
	private int keySeq;
	private int deleteRule;
	private String fktableSchem;
	private String pktableName;
	private int updateRule;
	private String pktableSchem;
	private String fkcolumnName;
	private String pkcolumnName;
	private String pkName;
	private int deferrability;
	private String fkName;
	private String pktableCat;
	private String fktableCat;
	private String fktableName;
		
}
