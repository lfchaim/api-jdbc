/**
 * 
 */
package com.whs.apijdbc.util.dto;

import java.io.Serializable;

/**
 * @author lchaim
 *
 */
public class ForeignKeyDTO implements Serializable {

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
	
	/**
	 * @return Returns the deferrability.
	 */
	public int getDeferrability() {
		return deferrability;
	}
	/**
	 * @param deferrability The deferrability to set.
	 */
	public void setDeferrability(int deferrability) {
		this.deferrability = deferrability;
	}
	/**
	 * @return Returns the deleteRule.
	 */
	public int getDeleteRule() {
		return deleteRule;
	}
	/**
	 * @param deleteRule The deleteRule to set.
	 */
	public void setDeleteRule(int deleteRule) {
		this.deleteRule = deleteRule;
	}
	/**
	 * @return Returns the fkcolumnName.
	 */
	public String getFkcolumnName() {
		return fkcolumnName;
	}
	/**
	 * @param fkcolumnName The fkcolumnName to set.
	 */
	public void setFkcolumnName(String fkcolumnName) {
		this.fkcolumnName = fkcolumnName;
	}
	/**
	 * @return Returns the fkName.
	 */
	public String getFkName() {
		return fkName;
	}
	/**
	 * @param fkName The fkName to set.
	 */
	public void setFkName(String fkName) {
		this.fkName = fkName;
	}
	/**
	 * @return Returns the fktableCat.
	 */
	public String getFktableCat() {
		return fktableCat;
	}
	/**
	 * @param fktableCat The fktableCat to set.
	 */
	public void setFktableCat(String fktableCat) {
		this.fktableCat = fktableCat;
	}
	/**
	 * @return Returns the fktableName.
	 */
	public String getFktableName() {
		return fktableName;
	}
	/**
	 * @param fktableName The fktableName to set.
	 */
	public void setFktableName(String fktableName) {
		this.fktableName = fktableName;
	}
	/**
	 * @return Returns the fktableSchem.
	 */
	public String getFktableSchem() {
		return fktableSchem;
	}
	/**
	 * @param fktableSchem The fktableSchem to set.
	 */
	public void setFktableSchem(String fktableSchem) {
		this.fktableSchem = fktableSchem;
	}
	/**
	 * @return Returns the keySeq.
	 */
	public int getKeySeq() {
		return keySeq;
	}
	/**
	 * @param keySeq The keySeq to set.
	 */
	public void setKeySeq(int keySeq) {
		this.keySeq = keySeq;
	}
	/**
	 * @return Returns the pkcolumnName.
	 */
	public String getPkcolumnName() {
		return pkcolumnName;
	}
	/**
	 * @param pkcolumnName The pkcolumnName to set.
	 */
	public void setPkcolumnName(String pkcolumnName) {
		this.pkcolumnName = pkcolumnName;
	}
	/**
	 * @return Returns the pkName.
	 */
	public String getPkName() {
		return pkName;
	}
	/**
	 * @param pkName The pkName to set.
	 */
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
	/**
	 * @return Returns the pktableCat.
	 */
	public String getPktableCat() {
		return pktableCat;
	}
	/**
	 * @param pktableCat The pktableCat to set.
	 */
	public void setPktableCat(String pktableCat) {
		this.pktableCat = pktableCat;
	}
	/**
	 * @return Returns the pktableSchem.
	 */
	public String getPktableSchem() {
		return pktableSchem;
	}
	/**
	 * @param pktableSchem The pktableSchem to set.
	 */
	public void setPktableSchem(String pktableSchem) {
		this.pktableSchem = pktableSchem;
	}
	/**
	 * @return Returns the pktableName.
	 */
	public String getPktableName() {
		return pktableName;
	}
	/**
	 * @param pktableName The pktableName to set.
	 */
	public void setPktableName(String pktableName) {
		this.pktableName = pktableName;
	}
	/**
	 * @return Returns the updateRule.
	 */
	public int getUpdateRule() {
		return updateRule;
	}
	/**
	 * @param updateRule The updateRule to set.
	 */
	public void setUpdateRule(int updateRule) {
		this.updateRule = updateRule;
	}
	
}
