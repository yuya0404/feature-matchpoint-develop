package com.example.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dao.ManageDao;
import com.example.entity.Manage;
import com.example.util.Utility;

@Repository
public class PgManageDao implements ManageDao{
	private String tableName = "management";
	
	private static final String COLUMN_NAME_LOGIN_ID = "login_id";
	private static final String COLUMN_NAME_PASSWORD = "password";
	
	private final String SELECT = "SELECT * FROM " + tableName;
	
	@Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Manage> selectAll(Manage manage) {
		String sql = SELECT + PgManageDao.selectSql(manage);
		MapSqlParameterSource param = new MapSqlParameterSource();
		if(Utility.notIsEmptyNull(manage.getLoginId())) {
			param.addValue(COLUMN_NAME_LOGIN_ID, manage.getLoginId());
		}
		if(Utility.notIsEmptyNull(manage.getPassword())) {
			param.addValue(COLUMN_NAME_PASSWORD, manage.getPassword());
		}
		List<Manage> resultList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Manage>(Manage.class));
		return resultList.isEmpty() ? null : resultList;
	}

	public static String selectSql(Manage manage) {
		String where = "";
		String columnName = "";
		if(Utility.notIsEmptyNull(manage.getLoginId())) {
			columnName = COLUMN_NAME_LOGIN_ID + " = :" + COLUMN_NAME_LOGIN_ID;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if(Utility.notIsEmptyNull(manage.getPassword())) {
			columnName = COLUMN_NAME_PASSWORD + " = :" + COLUMN_NAME_PASSWORD;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		return !where.isEmpty() ? " WHERE " + where : "";
	}
	
}