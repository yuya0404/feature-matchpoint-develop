package com.example.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dao.TeamDao;
import com.example.entity.Team;
import com.example.util.Utility;

@Repository
public class PgTeamDao implements TeamDao{
	private String tableName = "team";

	private final static String ID = "team_id";

	private static final String COLUMN_NAME_COMP_ID = "comp_id";
	private static final String COLUMN_NAME_PLAYER_A_NAME = "player_a_name";
	private static final String COLUMN_NAME_PLAYER_B_NAME = "player_b_name";
	private static final String COLUMN_NAME_TOURNAMENT_NO = "tournament_no";

	private final String SELECT = "SELECT * FROM " + tableName; 
	private final String INSERT = "INSERT INTO " + tableName;
	private final String DELETE = "DELETE FROM " + tableName + " WHERE " + ID + " = :" + ID;
	private final String UPDATE = "UPDATE " + tableName + " set ";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Team> selectAll(Team team, String keyword) {
		String sql = SELECT + PgTeamDao.selectSql(team, keyword) + " ORDER BY "+ ID;
		MapSqlParameterSource param = new MapSqlParameterSource();
		if(Utility.notIsEmptyNull(team.getTeamId())) {
			param.addValue(ID, team.getTeamId());
		}
		if(Utility.notIsEmptyNull(team.getCompId())) {
			param.addValue(COLUMN_NAME_COMP_ID, team.getCompId());
		}
		if(Utility.notIsEmptyNull(team.getPlayerAName())) {
			param.addValue(COLUMN_NAME_PLAYER_A_NAME, team.getPlayerAName());
		}
		if(Utility.notIsEmptyNull(team.getPlayerBName())) {
			param.addValue(COLUMN_NAME_PLAYER_B_NAME, team.getPlayerBName());
		}
		if(Utility.notIsEmptyNull(team.getTournamentNo())) {
			param.addValue(COLUMN_NAME_TOURNAMENT_NO, team.getTournamentNo());
		}
		if(Utility.notIsEmptyNull(keyword)) {
			param.addValue("keyword", '%'+ keyword +'%');
		}
		List<Team> resultList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Team>(Team.class));
		return resultList.isEmpty() ? null : resultList;
	}
	
	@Override
	public List<Team> gameTeam(Integer team1, Integer team2){
		String sql = SELECT + " WHERE team_id = :team_id1 OR team_id = :team_id2";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("team_id1", team1);
		param.addValue("team_id2", team2);
		List<Team> resultList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Team>(Team.class));
		return resultList.isEmpty() ? null : resultList;
	}

	@Override
	public int insertTeam(Team team) {
		String sql = INSERT + PgTeamDao.insertSql(team);
		MapSqlParameterSource param = new MapSqlParameterSource();
		if (Utility.notIsEmptyNull(team.getCompId())) {
			param.addValue(COLUMN_NAME_COMP_ID, team.getCompId());
		}
		if (Utility.notIsEmptyNull(team.getPlayerAName())) {
			param.addValue(COLUMN_NAME_PLAYER_A_NAME, team.getPlayerAName());
		}
		if (Utility.notIsEmptyNull(team.getPlayerBName())) {
			param.addValue(COLUMN_NAME_PLAYER_B_NAME, team.getPlayerBName());
		}
		if (Utility.notIsEmptyNull(team.getTournamentNo())) {
			param.addValue(COLUMN_NAME_TOURNAMENT_NO, team.getTournamentNo());
		}
		return jdbcTemplate.update(sql, param);
	}

	@Override
	public int deleteTeam(Team team) {
		String sql = DELETE;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue(ID, team.getTeamId());
		return jdbcTemplate.update(sql, param);
	}

	@Override
	public int updateTeam(Team team) {
		String sql = UPDATE + updateSql(team);
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue(ID , team.getTeamId());
		if (Utility.notIsEmptyNull(team.getCompId())) {
			param.addValue(COLUMN_NAME_COMP_ID, team.getCompId());
		}
		if (Utility.notIsEmptyNull(team.getPlayerAName())) {
			param.addValue(COLUMN_NAME_PLAYER_A_NAME, team.getPlayerAName());
		}
		if (Utility.notIsEmptyNull(team.getPlayerBName())) {
			param.addValue(COLUMN_NAME_PLAYER_B_NAME, team.getPlayerBName());
		}
		if (Utility.notIsEmptyNull(team.getTournamentNo())) {
			param.addValue(COLUMN_NAME_TOURNAMENT_NO, team.getTournamentNo());
		}
		return jdbcTemplate.update(sql, param);
	}
	
	public static String selectSql(Team team, String keyword) {
		String where = "";
		String columnName = "";
		if (Utility.notIsEmptyNull(team.getTeamId())) {
			columnName = ID + " = :" + ID;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(team.getCompId())) {
			columnName = COLUMN_NAME_COMP_ID + " = :" + COLUMN_NAME_COMP_ID;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(team.getPlayerAName())) {
			columnName = COLUMN_NAME_PLAYER_A_NAME + " = :" + COLUMN_NAME_PLAYER_A_NAME;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(team.getPlayerBName())) {
			columnName = COLUMN_NAME_PLAYER_B_NAME + " = :" + COLUMN_NAME_PLAYER_B_NAME;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(team.getTournamentNo())) {
			columnName = COLUMN_NAME_TOURNAMENT_NO + " = :" + COLUMN_NAME_TOURNAMENT_NO;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(keyword)){
			columnName = COLUMN_NAME_PLAYER_A_NAME + "||" + COLUMN_NAME_PLAYER_B_NAME + " LIKE :keyword";
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		return !where.isEmpty() ? " WHERE " + where : "";
	}
	
	public static String insertSql(Team team) {
		String column = "";
		String values = "";
		String columnName = "";
		if (Utility.notIsEmptyNull(team.getCompId())) {
			columnName = COLUMN_NAME_COMP_ID;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(team.getPlayerAName())) {
			columnName = COLUMN_NAME_PLAYER_A_NAME;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(team.getPlayerBName())) {
			columnName = COLUMN_NAME_PLAYER_B_NAME;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(team.getTournamentNo())) {
			columnName = COLUMN_NAME_TOURNAMENT_NO;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		column = !column.isEmpty() ? column + ")" : column;
		values = !values.isEmpty() ? values + ")" : column;
		return column + values;
	}
	
	public static String updateSql(Team team) {
		String set = "";
		String columnName = "";
		if (Utility.notIsEmptyNull(team.getCompId())) {
			columnName = COLUMN_NAME_COMP_ID + " = :" + COLUMN_NAME_COMP_ID;
			set = !set.isEmpty() ? set + " , " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(team.getPlayerAName())) {
			columnName = COLUMN_NAME_PLAYER_A_NAME + " = :" + COLUMN_NAME_PLAYER_A_NAME;
			set = !set.isEmpty() ? set + " , " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(team.getPlayerBName())) {
			columnName = COLUMN_NAME_PLAYER_B_NAME + " = :" + COLUMN_NAME_PLAYER_B_NAME;
			set = !set.isEmpty() ? set + " , " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(team.getTournamentNo())) {
			columnName = COLUMN_NAME_TOURNAMENT_NO + " = :" + COLUMN_NAME_TOURNAMENT_NO;
			set = !set.isEmpty() ? set + " , " + columnName : columnName;
		}
		return !set.isEmpty() ? set + " WHERE " + ID + " = :" + ID : "";
	}
}