package com.example.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dao.ScoreDao;
import com.example.entity.Score;
import com.example.util.Utility;

@Repository
public class PgScoreDao implements ScoreDao {
	private String tableName = "score";

	private final static String ID = "score_id";

	private static final String COLUMN_NAME_SCORE_ID = "score_id";
	private static final String COLUMN_NAME_GAME_INFO_ID = "game_info_id";
	private static final String COLUMN_NAME_SET_NO = "set_no";
	private static final String COLUMN_NAME_TEAM_A_SCORE = "team_a_score";
	private static final String COLUMN_NAME_TEAM_B_SCORE = "team_b_score";

	private final String SELECT = "SELECT * FROM " + tableName;
	private final String INSERT = "INSERT INTO " + tableName;
	private final String DELETE = "DELETE FROM " + tableName + " WHERE " + ID + " = :" + ID;
	private final String UPDATE = "UPDATE " + tableName + " set ";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Score> selectAll(Score score) {
		String sql = SELECT + PgScoreDao.selectSql(score) + " ORDER BY " + ID;
		MapSqlParameterSource param = new MapSqlParameterSource();
		if(Utility.notIsEmptyNull(score.getScoreId())) {
			param.addValue(COLUMN_NAME_SCORE_ID, score.getScoreId());
		}
		if(Utility.notIsEmptyNull(score.getGameInfoId())) {
			param.addValue(COLUMN_NAME_GAME_INFO_ID, score.getGameInfoId());
		}
		if(Utility.notIsEmptyNull(score.getSetNo())) {
			param.addValue(COLUMN_NAME_SET_NO, score.getSetNo());
		}
		if(Utility.notIsEmptyNull(score.getTeamAScore())) {
			param.addValue(COLUMN_NAME_TEAM_A_SCORE, score.getTeamAScore());
		}
		if(Utility.notIsEmptyNull(score.getTeamBScore())) {
			param.addValue("team_b_score", score.getTeamBScore());
		}
		List<Score> resultList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Score>(Score.class));
		return resultList.isEmpty() ? null : resultList;
	}

	@Override
	public void insertScore(Score score) {
		String sql = INSERT + PgScoreDao.insertSql(score);
		MapSqlParameterSource param = new MapSqlParameterSource();
		if(Utility.notIsEmptyNull(score.getGameInfoId())) {
			param.addValue(COLUMN_NAME_GAME_INFO_ID, score.getGameInfoId());
		}
		if(Utility.notIsEmptyNull(score.getSetNo())) {
			param.addValue(COLUMN_NAME_SET_NO, score.getSetNo());
		}
		if(Utility.notIsEmptyNull(score.getTeamAScore())) {
			param.addValue(COLUMN_NAME_TEAM_A_SCORE, score.getTeamAScore());
		}
		if(Utility.notIsEmptyNull(score.getTeamBScore())) {
			param.addValue(COLUMN_NAME_TEAM_B_SCORE, score.getTeamBScore());
		}
		jdbcTemplate.update(sql, param);
	}

	@Override
	public void deleteScore(Score score) {
		String sql = DELETE;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue(ID , score.getScoreId());
		jdbcTemplate.update(sql, param);
	}

	@Override
	public void updateScore(Score score) {
		String sql = UPDATE + updateSql(score);
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue(COLUMN_NAME_SCORE_ID, score.getScoreId());
		if(Utility.notIsEmptyNull(score.getGameInfoId())) {
			param.addValue(COLUMN_NAME_GAME_INFO_ID, score.getGameInfoId());
		}
		if(Utility.notIsEmptyNull(score.getSetNo())) {
			param.addValue(COLUMN_NAME_SET_NO, score.getSetNo());
		}
		if(Utility.notIsEmptyNull(score.getTeamAScore())) {
			param.addValue(COLUMN_NAME_TEAM_A_SCORE, score.getTeamAScore());
		}
		if(Utility.notIsEmptyNull(score.getTeamBScore())) {
			param.addValue(COLUMN_NAME_TEAM_B_SCORE, score.getTeamBScore());
		}
		jdbcTemplate.update(sql, param);
	}

	public static String selectSql(Score score) {
		String where = "";
		String columnName = "";
		if (Utility.notIsEmptyNull(score.getGameInfoId())) {
			columnName = COLUMN_NAME_GAME_INFO_ID + " = :" + COLUMN_NAME_GAME_INFO_ID;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(score.getSetNo())) {
			columnName = COLUMN_NAME_SET_NO + " = :" + COLUMN_NAME_SET_NO;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(score.getTeamAScore())) {
			columnName = COLUMN_NAME_TEAM_A_SCORE + " = :" + COLUMN_NAME_TEAM_A_SCORE;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(score.getTeamBScore())) {
			columnName = COLUMN_NAME_TEAM_B_SCORE + " = :" + COLUMN_NAME_TEAM_B_SCORE;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		return !where.isEmpty() ? " WHERE " + where : "";
	}
	
	public static String insertSql(Score score) {
		String column = "";
		String values = "";
		String columnName = "";
		if( Utility.notIsEmptyNull(score.getScoreId()) ) {
			columnName = COLUMN_NAME_SCORE_ID;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if( Utility.notIsEmptyNull(score.getGameInfoId()) ) {
			columnName = COLUMN_NAME_GAME_INFO_ID;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if( Utility.notIsEmptyNull(score.getSetNo()) ) {
			columnName = COLUMN_NAME_SET_NO;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if( Utility.notIsEmptyNull(score.getTeamAScore()) ) {
			columnName = COLUMN_NAME_TEAM_A_SCORE;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if( Utility.notIsEmptyNull(score.getTeamBScore()) ) {
			columnName = COLUMN_NAME_TEAM_B_SCORE;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		
		return column + ")" + values + ")";
	}
	
	public static String updateSql(Score score) {
		String set = "";
		String columnName = "";
		if(Utility.notIsEmptyNull(score.getGameInfoId())) {
			columnName = COLUMN_NAME_GAME_INFO_ID + " = :" + COLUMN_NAME_GAME_INFO_ID;
			set = !set.isEmpty() ? set + ", " + columnName : columnName;
		}
		if(Utility.notIsEmptyNull(score.getSetNo())) {
			columnName = COLUMN_NAME_SET_NO + " = :" + COLUMN_NAME_SET_NO;
			set = !set.isEmpty() ? set + "," + columnName : columnName;
		}
		if(Utility.notIsEmptyNull(score.getTeamAScore())) {
			columnName = COLUMN_NAME_TEAM_A_SCORE + " = :" + COLUMN_NAME_TEAM_A_SCORE;
			set = !set.isEmpty() ? set + ", " + columnName : columnName;
		}
		if(Utility.notIsEmptyNull(score.getTeamBScore())) {
			columnName = COLUMN_NAME_TEAM_B_SCORE + " = :" + COLUMN_NAME_TEAM_B_SCORE;
			set = !set.isEmpty() ? set + "," + columnName : columnName;
		}
		return !set.isEmpty() ? set + " WHERE " + ID + " = :" + ID: "";
	}
}