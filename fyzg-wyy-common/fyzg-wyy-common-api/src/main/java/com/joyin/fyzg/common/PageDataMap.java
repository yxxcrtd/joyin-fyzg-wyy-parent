package com.joyin.fyzg.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;

/**
 * 页面提交的数据Map
 * <br/>
 *
 * @author pengzhen
 * @date 2019/11/8 0008 上午 9:28
 */
public class PageDataMap<K,V> extends HashMap<K,V> {

	@JsonIgnore
	public boolean isCreate() {
		return this.get(SysConstant.TableField.rId) == null;
	}

	@JsonIgnore
	public V getRId() {
		return this.get(SysConstant.TableField.rId);
	}

	@JsonIgnore
	public V getDFlag() {
		return this.get(SysConstant.TableField.dFlag);
	}

	@JsonIgnore
	public V getOType() {
		return this.get(SysConstant.TableField.oType);
	}

	@JsonIgnore
	public V getOCode(String oType) {
		return this.get(oType + "_" + SysConstant.TableField.oCode);
	}

	@JsonIgnore
	public V getOName(String oType) {
		return this.get(oType + "_" + SysConstant.TableField.oName);
	}

	@JsonIgnore
	public V getPOCode(String oType) {
		return this.get(oType + "_" + SysConstant.TableField.pOCode);
	}

	@JsonIgnore
	public V getInstanceId() {
		return this.get(SysConstant.TableField.instanceId);
	}


}
