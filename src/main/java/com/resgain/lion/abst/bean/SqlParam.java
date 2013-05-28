package com.resgain.lion.abst.bean;

import java.util.List;

public class SqlParam {
	
	private String sql;
	private List<Object> params;

	public SqlParam(String sql, List<Object> params) {
		super();
		this.sql = sql;
		this.params = params;
	}

	public String getSql() {
		return sql;
	}
	public List<Object> getParams() {
		return params;
	}
}
