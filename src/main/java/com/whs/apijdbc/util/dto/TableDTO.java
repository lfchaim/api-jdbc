package com.whs.apijdbc.util.dto;

import java.io.Serializable;

public class TableDTO implements Serializable {
	
	public static final long serialVersionUID = 1l;

	private String tableSchem;
	private String remarks;
	private String tableName;
	private String tableCat;
	private String tableType;
	
	private ColumnDTO[] columnDTO;
	private PKDTO[] pkDTO;
	private ForeignKeyDTO[] importedKey;
	private ForeignKeyDTO[] exportedKey;
	
	public String getTableSchem() {
		return tableSchem;
	}
	public void setTableSchem(String tableSchem) {
		this.tableSchem = tableSchem;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableCat() {
		return tableCat;
	}
	public void setTableCat(String tableCat) {
		this.tableCat = tableCat;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public ColumnDTO[] getColumnDTO() {
		return columnDTO;
	}
	public void setColumnDTO(ColumnDTO[] columnDTO) {
		this.columnDTO = columnDTO;
	}
	public PKDTO[] getPkDTO() {
		return pkDTO;
	}
	public void setPkDTO(PKDTO[] pkDTO) {
		this.pkDTO = pkDTO;
	}
	public ForeignKeyDTO[] getImportedKey() {
		return importedKey;
	}
	public void setImportedKey(ForeignKeyDTO[] importedKey) {
		this.importedKey = importedKey;
	}
	public ForeignKeyDTO[] getExportedKey() {
		return exportedKey;
	}
	public void setExportedKey(ForeignKeyDTO[] exportedKey) {
		this.exportedKey = exportedKey;
	}
	
}
