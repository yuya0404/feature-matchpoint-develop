package com.example.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dao.CompDao;
import com.example.entity.Comp;
import com.example.util.Utility;

@Repository
public class PgCompDao implements CompDao {
	private String tableName = "comp";

	private final static String ID = "comp_id";

	private static final String COLUMN_NAME_COMP_ID = "comp_id";
	private static final String COLUMN_NAME_COMP_IOGIN_ID = "comp_login_id";
	private static final String COLUMN_NAME_COMP_NAME = "comp_name";
	private static final String COLUMN_NAME_COMP_DATE = "comp_date";
	private static final String COLUMN_NAME_COMP_PLACE = "comp_place";
	private static final String COLUMN_NAME_GAME_TYPE = "game_type";
	private static final String COLUMN_NAME_TOURNAMET_COUNT = "tournament_count";
	private static final String COLUMN_NAME_MEMO = "memo";
	private static final String COLUMN_NAME_TORNAMET_EDIT_STATUS = "tournament_edit_status";

	private final String SELECT = "SELECT * FROM " + tableName ;
	private final String INSERT = "INSERT INTO " + tableName;
	private final String DELETE = "DELETE FROM " + tableName + " WHERE " + ID + " = :" + ID;
	private final String UPDATE = "UPDATE " + tableName + " set ";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Comp> selectAll(Comp comp) {
		String sql = SELECT + PgCompDao.selectSql(comp)+ " ORDER BY " + ID + " DESC";
		MapSqlParameterSource param = new MapSqlParameterSource();
		if (Utility.notIsEmptyNull(comp.getCompId())) {
			param.addValue(COLUMN_NAME_COMP_ID, comp.getCompId());
		}
		if (Utility.notIsEmptyNull(comp.getCompLoginId())) {
			param.addValue(COLUMN_NAME_COMP_IOGIN_ID, comp.getCompLoginId());
		}
		if (Utility.notIsEmptyNull(comp.getCompName())) {
			param.addValue(COLUMN_NAME_COMP_NAME, comp.getCompName());
		}
		if (Utility.notIsEmptyNull(comp.getCompDate())) {
			param.addValue(COLUMN_NAME_COMP_DATE, comp.getCompDate());
		}
		if (Utility.notIsEmptyNull(comp.getCompPlace())) {
			param.addValue(COLUMN_NAME_COMP_PLACE, comp.getCompPlace());
		}
		if (Utility.notIsEmptyNull(comp.getGameType())) {
			param.addValue(COLUMN_NAME_GAME_TYPE, comp.getGameType());
		}
		if (Utility.notIsEmptyNull(comp.getTournamentCount())) {
			param.addValue(COLUMN_NAME_TOURNAMET_COUNT, comp.getTournamentCount());
		}
		if (Utility.notIsEmptyNull(comp.getTournamentEditStatus())) {
			param.addValue(COLUMN_NAME_TORNAMET_EDIT_STATUS, comp.getTournamentEditStatus());
		}
		if (Utility.notIsEmptyNull(comp.getMemo())) {

			param.addValue(COLUMN_NAME_MEMO, comp.getMemo());

		}
		List<Comp> resultList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Comp>(Comp.class));
		return resultList.isEmpty() ? null : resultList;
	}

	@Override
	public int insertComp(Comp comp) {
		String sql = INSERT + PgCompDao.insertSql(comp);
		MapSqlParameterSource param = new MapSqlParameterSource();
		if (Utility.notIsEmptyNull(comp.getCompLoginId())) {
			param.addValue(COLUMN_NAME_COMP_IOGIN_ID, comp.getCompLoginId());
		}
		if (Utility.notIsEmptyNull(comp.getCompName())) {
			param.addValue(COLUMN_NAME_COMP_NAME, comp.getCompName());
		}
		if (Utility.notIsEmptyNull(comp.getCompDate())) {
			param.addValue(COLUMN_NAME_COMP_DATE, comp.getCompDate());
		}
		if (Utility.notIsEmptyNull(comp.getCompPlace())) {
			param.addValue(COLUMN_NAME_COMP_PLACE, comp.getCompPlace());
		}
		if (Utility.notIsEmptyNull(comp.getGameType())) {
			param.addValue(COLUMN_NAME_GAME_TYPE, comp.getGameType());
		}
		if (Utility.notIsEmptyNull(comp.getTournamentCount())) {
			param.addValue(COLUMN_NAME_TOURNAMET_COUNT, comp.getTournamentCount());
		}
		if (Utility.notIsEmptyNull(comp.getTournamentEditStatus())) {
			param.addValue(COLUMN_NAME_TORNAMET_EDIT_STATUS, comp.getTournamentEditStatus());
		}
		if (Utility.notIsEmptyNull(comp.getMemo())) {

			param.addValue(COLUMN_NAME_MEMO, comp.getMemo());

		}
		return jdbcTemplate.update(sql, param);
	}

	@Override
	public int deleteComp(Comp comp) {
		String sql = DELETE;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue(ID, comp.getCompId());
		return jdbcTemplate.update(sql, param);
	}

	@Override
	public int updateComp(Comp comp) {
		String sql = UPDATE + updateSql(comp);
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue(ID , comp.getCompId());
		if (Utility.notIsEmptyNull(comp.getCompLoginId())) {
			param.addValue(COLUMN_NAME_COMP_IOGIN_ID, comp.getCompLoginId());
		}
		if (Utility.notIsEmptyNull(comp.getCompName())) {
			param.addValue(COLUMN_NAME_COMP_NAME, comp.getCompName());
		}
		if (Utility.notIsEmptyNull(comp.getCompDate())) {
			param.addValue(COLUMN_NAME_COMP_DATE, comp.getCompDate());
		}
		if (Utility.notIsEmptyNull(comp.getCompPlace())) {
			param.addValue(COLUMN_NAME_COMP_PLACE, comp.getCompPlace());
		}
		if (Utility.notIsEmptyNull(comp.getGameType())) {
			param.addValue(COLUMN_NAME_GAME_TYPE, comp.getGameType());
		}
		if (Utility.notIsEmptyNull(comp.getTournamentCount())) {
			param.addValue(COLUMN_NAME_TOURNAMET_COUNT, comp.getTournamentCount());
		}
		if (Utility.notIsEmptyNull(comp.getTournamentEditStatus())) {
			param.addValue(COLUMN_NAME_TORNAMET_EDIT_STATUS, comp.getTournamentEditStatus());
		}
		if (Utility.notIsEmptyNull(comp.getMemo())) {

			param.addValue(COLUMN_NAME_MEMO, comp.getMemo());

		}
		return jdbcTemplate.update(sql, param);
	}

	public static String selectSql(Comp comp) {
		String where = "";
		String columnName = "";
		if (Utility.notIsEmptyNull(comp.getCompId())) {
			columnName = COLUMN_NAME_COMP_ID + " = :" + COLUMN_NAME_COMP_ID;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getCompLoginId())) {
			columnName = COLUMN_NAME_COMP_IOGIN_ID + " = :" + COLUMN_NAME_COMP_IOGIN_ID;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getCompName())) {
			columnName = COLUMN_NAME_COMP_NAME + " = :" + COLUMN_NAME_COMP_NAME;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getCompPlace())) {
			columnName = COLUMN_NAME_COMP_PLACE + " = :" + COLUMN_NAME_COMP_PLACE;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getCompDate())) {
			columnName = COLUMN_NAME_COMP_DATE + " = :" + COLUMN_NAME_COMP_DATE;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getGameType())) {
			columnName = COLUMN_NAME_GAME_TYPE + " = :" + COLUMN_NAME_GAME_TYPE;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getTournamentCount())) {
			columnName = COLUMN_NAME_TOURNAMET_COUNT + " = :" + COLUMN_NAME_TOURNAMET_COUNT;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getTournamentEditStatus())) {
			columnName = COLUMN_NAME_TORNAMET_EDIT_STATUS + " = :" + COLUMN_NAME_TORNAMET_EDIT_STATUS;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getMemo())) {
			columnName = COLUMN_NAME_MEMO + " = :" + COLUMN_NAME_MEMO;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		return !where.isEmpty() ? " WHERE " + where : "";
	}

	public static String insertSql(Comp comp) {
		String column = "";
		String values = "";
		String columnName = "";
		if (Utility.notIsEmptyNull(comp.getCompLoginId())) {
			columnName = COLUMN_NAME_COMP_IOGIN_ID;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(comp.getCompName())) {
			columnName = COLUMN_NAME_COMP_NAME;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(comp.getCompDate())) {
			columnName = COLUMN_NAME_COMP_DATE;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(comp.getCompPlace())) {
			columnName = COLUMN_NAME_COMP_PLACE;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(comp.getGameType())) {
			columnName = COLUMN_NAME_GAME_TYPE;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(comp.getTournamentCount())) {
			columnName = COLUMN_NAME_TOURNAMET_COUNT;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(comp.getMemo())) {
			columnName = COLUMN_NAME_MEMO;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		column = !column.isEmpty() ? column + ")" : column;
		values = !values.isEmpty() ? values + ")" : column;
		return column + values;
	}

	public static String updateSql(Comp comp) {
		String set = "";
		String columnName = "";
		if (Utility.notIsEmptyNull(comp.getCompId())) {
			columnName = COLUMN_NAME_COMP_ID + " = :" + COLUMN_NAME_COMP_ID;
			set = !set.isEmpty() ? set + " , " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getCompLoginId())) {
			columnName = COLUMN_NAME_COMP_IOGIN_ID + " = :" + COLUMN_NAME_COMP_IOGIN_ID;
			set = !set.isEmpty() ? set + " , " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getCompName())) {
			columnName = COLUMN_NAME_COMP_NAME + " = :" + COLUMN_NAME_COMP_NAME;
			set = !set.isEmpty() ? set + " , " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getCompPlace())) {
			columnName = COLUMN_NAME_COMP_PLACE + " = :" + COLUMN_NAME_COMP_PLACE;
			set = !set.isEmpty() ? set + " , " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getCompDate())) {
			columnName = COLUMN_NAME_COMP_DATE + " = :" + COLUMN_NAME_COMP_DATE;
			set = !set.isEmpty() ? set + " , " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getGameType())) {
			columnName = COLUMN_NAME_GAME_TYPE + " = :" + COLUMN_NAME_GAME_TYPE;
			set = !set.isEmpty() ? set + " , " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getTournamentCount())) {
			columnName = COLUMN_NAME_TOURNAMET_COUNT + " = :" + COLUMN_NAME_TOURNAMET_COUNT;
			set = !set.isEmpty() ? set + " , " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getTournamentEditStatus())) {
			columnName = COLUMN_NAME_TORNAMET_EDIT_STATUS + " = :" + COLUMN_NAME_TORNAMET_EDIT_STATUS;
			set = !set.isEmpty() ? set + " , " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(comp.getMemo())) {
			columnName = COLUMN_NAME_MEMO + " = :" + COLUMN_NAME_MEMO;
			set = !set.isEmpty() ? set + " , " + columnName : columnName;
		}
		return !set.isEmpty() ? set + " WHERE " + ID + " = :" + ID : "";
	}
}