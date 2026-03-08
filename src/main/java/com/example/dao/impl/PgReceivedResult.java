package com.example.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dao.ReceivedResultDao;
import com.example.entity.ReceivedResult;
import com.example.util.Utility;

@Repository
public class PgReceivedResult implements ReceivedResultDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	private String tableName = "received_result";
	private String gameInfoTbl ="game_info";
	private String matchTbl="match";
	private String matchTeam ="match_team";
	private final static String ID = "game_info_id";

	private static final String COLUMN_NAME_MATCHID = "match_id";
	private static final String COLUMN_NAME_COMPID = "comp_id";
	private static final String COLUMN_NAME_GAMENO = "game_no";
	private static final String COLUMN_NAME_COATNO = "coat_no";
	private static final String COLUMN_NAME_JUDGENAME = "judge_name";
	private static final String COLUMN_NAME_RECORDSTATUS = "record_status";
	private static final String COLUMN_NAME_RECORDDATE = "record_date";
	private static final String COLUMN_NAME_MAXPOINT = "max_point";
	private static final String COLUMN_NAME_GAMECOUNT = "game_count";
	private static final String COLUMN_NAME_TOURNAMENTNO = "tournament_no";
	private static final String COLUMN_NAME_TEAMIDA = "team_id_a";
	private static final String COLUMN_NAME_TEAMIDB = "team_id_b";

	private final String SELECT = "SELECT * FROM " + tableName;
	private final String INSERTMATCH = "INSERT INTO " + matchTbl;
	private final String INSERTGAMEINFO = "INSERT INTO " + gameInfoTbl;
	private final String UPDATE = "UPDATE " + gameInfoTbl + " set ";
	private final String UPDATEMATCH = "UPDATE " + matchTbl + " set ";

	@Override
	public List<ReceivedResult> box(Integer compId, Integer status, String keyword){
		String sql = SELECT + " WHERE comp_id = :comp_id AND record_status = :record_status AND judge_name LIKE :keyword";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("comp_id", compId);
		param.addValue("record_status", status);
		param.addValue("keyword", '%' + keyword + '%');
		List<ReceivedResult> list = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<ReceivedResult>(ReceivedResult.class));
		return list;
	}
	
	@Override
	public List<ReceivedResult> search(ReceivedResult result, String keyword) {
		String sql = SELECT + selectSql(result, keyword);
		MapSqlParameterSource param = new MapSqlParameterSource();
		if (Utility.notIsEmptyNull(result.getGameInfoId())) {
			param.addValue(ID, result.getGameInfoId());
		}
		if (Utility.notIsEmptyNull(result.getRecordStatus())) {
			param.addValue(COLUMN_NAME_RECORDSTATUS, result.getRecordStatus());
		}
		if(Utility.notIsEmptyNull(result.getRecordDate())) {
			param.addValue(COLUMN_NAME_RECORDDATE, result.getRecordDate());
		}
		if(Utility.notIsEmptyNull(result.getMatchId())) {
			param.addValue(COLUMN_NAME_MATCHID, result.getMatchId());
		}
		if (Utility.notIsEmptyNull(keyword)) {
			param.addValue("keyword", '%'+ keyword +'%');
		}
		if (Utility.notIsEmptyNull(result.getCompId())) {
			param.addValue(COLUMN_NAME_COMPID, result.getCompId());
		}
		if (Utility.notIsEmptyNull(result.getGameNo())) {
			param.addValue(COLUMN_NAME_GAMENO, result.getGameNo());
		}
		List<ReceivedResult> resultList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<ReceivedResult>(ReceivedResult.class));
		return resultList.isEmpty() ? null : resultList;
	}
	
	@Override
	public List<ReceivedResult> searchMatch(ReceivedResult receivedResult){
		String sql = "SELECT * FROM " + matchTbl + searchSqlMatch(receivedResult);
		MapSqlParameterSource param = new MapSqlParameterSource();
		if (Utility.notIsEmptyNull(receivedResult.getCompId())) {
			param.addValue(COLUMN_NAME_COMPID, receivedResult.getCompId());
		}
		if (Utility.notIsEmptyNull(receivedResult.getGameNo())) {
			param.addValue(COLUMN_NAME_GAMENO, receivedResult.getGameNo());
		}
		List<ReceivedResult> resultList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<ReceivedResult>(ReceivedResult.class));
		return resultList.isEmpty() ? null : resultList;
	}

	@Override
	public List<ReceivedResult> searchMatchTeam(ReceivedResult receivedResult){
		String sql = "SELECT * FROM " + matchTeam + searchSqlMatch(receivedResult);
		MapSqlParameterSource param = new MapSqlParameterSource();
		if (Utility.notIsEmptyNull(receivedResult.getCompId())) {
			param.addValue(COLUMN_NAME_COMPID, receivedResult.getCompId());
		}
		if (Utility.notIsEmptyNull(receivedResult.getGameNo())) {
			param.addValue(COLUMN_NAME_GAMENO, receivedResult.getGameNo());
		}
		List<ReceivedResult> resultList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<ReceivedResult>(ReceivedResult.class));
		return resultList.isEmpty() ? null : resultList;
	}
	
	//insertはmatchとgame_infoのみinsertかけられる感じで書いてます。
	//teamにもinsert必要な場合は、teamDaoを使う想定で。
	//matchとgame_infoを別々にinsert書けることがあるかもと思ってメソッド分けてるけど、
	//セットでinsertかけることがあるならメソッドまとめるのもあり。
	@Override
	public int insertMatch(ReceivedResult result) {
		String sql = INSERTMATCH + insertSqlMatch(result);
		MapSqlParameterSource param = new MapSqlParameterSource();
		if (Utility.notIsEmptyNull(result.getCompId())) {
			param.addValue(COLUMN_NAME_COMPID, result.getCompId());
		}
		if (Utility.notIsEmptyNull(result.getGameNo())) {
			param.addValue(COLUMN_NAME_GAMENO, result.getGameNo());
		}
		if (Utility.notIsEmptyNull(result.getTeamIdA())) {
			param.addValue(COLUMN_NAME_TEAMIDA, result.getTeamIdA());
		}
		if (Utility.notIsEmptyNull(result.getTeamIdB())) {
			param.addValue(COLUMN_NAME_TEAMIDB, result.getTeamIdB());
		}

		return jdbcTemplate.update(sql, param);
	}
	
	@Override
	public int insertGameInfo(ReceivedResult result) {
		String sql = INSERTGAMEINFO + insertSqlGameInfo(result);
		MapSqlParameterSource param = new MapSqlParameterSource();
		if (Utility.notIsEmptyNull(result.getMatchId())) {
			param.addValue(COLUMN_NAME_MATCHID, result.getMatchId());
		}
		if (Utility.notIsEmptyNull(result.getCoatNo())) {
			param.addValue(COLUMN_NAME_COATNO, result.getCoatNo());
		}
		if (Utility.notIsEmptyNull(result.getJudgeName())) {
			param.addValue(COLUMN_NAME_JUDGENAME, result.getJudgeName());
		}
			
		if (Utility.notIsEmptyNull(result.getMaxPoint())) {
			param.addValue(COLUMN_NAME_MAXPOINT, result.getMaxPoint());
		}
		if (Utility.notIsEmptyNull(result.getGameCount())) {
			param.addValue(COLUMN_NAME_GAMECOUNT, result.getGameCount());
		}
		//record statusはinsertするとき基本0
		param.addValue(COLUMN_NAME_RECORDSTATUS, 0);
		//dateはinsertかけたら自動的に入力される
		Timestamp date = new Timestamp(System.currentTimeMillis());
		param.addValue(COLUMN_NAME_RECORDDATE, date);
		jdbcTemplate.update(sql, param);
		ReceivedResult receivedResult = new ReceivedResult();
		receivedResult.setRecordDate(date);
		List<ReceivedResult> list = this.search(receivedResult, "");
		return list.get(0).getGameInfoId();
	}

	@Override
	public int update(ReceivedResult result) {
		String sql = UPDATE + updateSql(result);
		MapSqlParameterSource param = new MapSqlParameterSource();
		if (Utility.notIsEmptyNull(result.getRecordStatus())) {
			param.addValue(COLUMN_NAME_RECORDSTATUS, result.getRecordStatus());
		}
		if (Utility.notIsEmptyNull(result.getCoatNo())) {
			param.addValue(COLUMN_NAME_COATNO, result.getCoatNo());
		}
		if (Utility.notIsEmptyNull(result.getJudgeName())) {
			param.addValue(COLUMN_NAME_JUDGENAME, result.getJudgeName());
		}
		param.addValue(ID, result.getGameInfoId());
		return jdbcTemplate.update(sql, param);
	}
	
	@Override
	public int updateMatch(ReceivedResult result) {
		String sql = UPDATEMATCH + updateMatchSql(result);
		MapSqlParameterSource param = new MapSqlParameterSource();
		if (Utility.notIsEmptyNull(result.getCompId())) {
			param.addValue(COLUMN_NAME_COMPID, result.getCompId());
		}
		if (Utility.notIsEmptyNull(result.getGameNo())) {
			param.addValue(COLUMN_NAME_GAMENO, result.getGameNo());
		}
		if (Utility.notIsEmptyNull(result.getTeamIdA())) {
			param.addValue(COLUMN_NAME_TEAMIDA, result.getTeamIdA());
		}
		if (Utility.notIsEmptyNull(result.getTeamIdB())) {
			param.addValue(COLUMN_NAME_TEAMIDB, result.getTeamIdB());
		}
			param.addValue(COLUMN_NAME_MATCHID, result.getGameInfoId());
			
			return jdbcTemplate.update(sql, param);
	}
	

	public static String selectSql(ReceivedResult result, String keyword) {
		String where = "";
		String columnName = "";
		if (Utility.notIsEmptyNull(result.getGameInfoId())) {
			columnName = ID + " = :" + ID;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(result.getRecordStatus())) {
			columnName = COLUMN_NAME_RECORDSTATUS + " = :" + COLUMN_NAME_RECORDSTATUS;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(result.getRecordDate())) {
			columnName = COLUMN_NAME_RECORDDATE + " = :" + COLUMN_NAME_RECORDDATE;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(result.getMatchId())) {
			columnName = COLUMN_NAME_MATCHID + " = :" + COLUMN_NAME_MATCHID;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(result.getGameNo())) {
			columnName = COLUMN_NAME_GAMENO + " = :" + COLUMN_NAME_GAMENO;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(result.getCompId())) {
			columnName = COLUMN_NAME_COMPID + " = :" + COLUMN_NAME_COMPID;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(keyword)) {
			columnName = COLUMN_NAME_JUDGENAME + " || " + COLUMN_NAME_MATCHID + "||" + COLUMN_NAME_COATNO + "||"
					+ COLUMN_NAME_TOURNAMENTNO + " LIKE :" + "keyword";
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}

		return !where.isEmpty() ? " WHERE " + where : "";
	}
	
	public static String searchSqlMatch(ReceivedResult result) {
		String where = "";
		String columnName = "";
		if (Utility.notIsEmptyNull(result.getCompId())) {
			columnName = COLUMN_NAME_COMPID + " = :" + COLUMN_NAME_COMPID;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(result.getGameNo())) {
			columnName = COLUMN_NAME_GAMENO + " = :" + COLUMN_NAME_GAMENO;
			where = !where.isEmpty() ? where + " AND " + columnName : columnName;
		}
		
		return !where.isEmpty() ? " WHERE " + where : "";
	}

	public static String insertSqlMatch(ReceivedResult result) {
		String column = "";
		String values = "";
		String columnName = "";
		if (Utility.notIsEmptyNull(result.getCompId())) {
			columnName = COLUMN_NAME_COMPID;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(result.getGameNo())) {
			columnName = COLUMN_NAME_GAMENO;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(result.getTeamIdA())) {
			columnName = COLUMN_NAME_TEAMIDA;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(result.getTeamIdB())) {
			columnName = COLUMN_NAME_TEAMIDB;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		
		column = !column.isEmpty() ? column + ")" : column;
		values = !values.isEmpty() ? values + ")" : column;
		return column + values;
	}
	
	public static String insertSqlGameInfo(ReceivedResult result) {
		String column = "";
		String values = "";
		String columnName = "";
		if (Utility.notIsEmptyNull(result.getMatchId())) {
			columnName = COLUMN_NAME_MATCHID;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(result.getCoatNo())) {
			columnName = COLUMN_NAME_COATNO;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(result.getJudgeName())) {
			columnName = COLUMN_NAME_JUDGENAME;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(result.getMaxPoint())) {
			columnName = COLUMN_NAME_MAXPOINT;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		if (Utility.notIsEmptyNull(result.getGameCount())) {
			columnName = COLUMN_NAME_GAMECOUNT;
			column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
			values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		}
		//record_statusは自動的にセットされる
		columnName = COLUMN_NAME_RECORDSTATUS;
		column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
		values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		
		//record_dateはどうせinsertするときにカラムに値が入っていない
		columnName = COLUMN_NAME_RECORDDATE;
		column = !column.isEmpty() ? column + ", " + columnName : " (" + columnName;
		values = !values.isEmpty() ? values + ", :" + columnName : " values(:" + columnName;
		
		column = !column.isEmpty() ? column + ")" : column;
		values = !values.isEmpty() ? values + ")" : column;
		return column + values;
	}

	public static String updateSql(ReceivedResult result) {
		String set = "";
		String columnName = "";
		if (Utility.notIsEmptyNull(result.getRecordStatus())) {
			columnName = COLUMN_NAME_RECORDSTATUS + " = :" + COLUMN_NAME_RECORDSTATUS;
			set = !set.isEmpty() ? set + "," + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(result.getCoatNo())) {
			columnName = COLUMN_NAME_COATNO + " = :" + COLUMN_NAME_COATNO;
			set = !set.isEmpty() ? set + "," + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(result.getJudgeName())) {
			columnName = COLUMN_NAME_JUDGENAME + " = :" + COLUMN_NAME_JUDGENAME;
			set = !set.isEmpty() ? set + "," + columnName : columnName;
		}
		return !set.isEmpty() ? set + " WHERE " + ID + " = :" + ID : "";
	}
	public static String updateMatchSql(ReceivedResult result) {
		String set = "";
		String columnName = "";
		if (Utility.notIsEmptyNull(result.getCompId())) {
			columnName = COLUMN_NAME_COMPID+ " = :" + COLUMN_NAME_COMPID;
			set = !set.isEmpty() ? set + "," + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(result.getGameNo())) {
			columnName = COLUMN_NAME_GAMENO+ " = :" + COLUMN_NAME_GAMENO;
			set = !set.isEmpty() ? set + "," + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(result.getTeamIdA())) {
			columnName = COLUMN_NAME_TEAMIDA+ " = :" + COLUMN_NAME_TEAMIDA;
			set = !set.isEmpty() ? set + "," + columnName : columnName;
		}
		if (Utility.notIsEmptyNull(result.getTeamIdB())) {
			columnName = COLUMN_NAME_TEAMIDB+ " = :" + COLUMN_NAME_TEAMIDB;
			set = !set.isEmpty() ? set + "," + columnName : columnName;
		}

		return !set.isEmpty() ? set + " WHERE " + COLUMN_NAME_MATCHID + " = :" + COLUMN_NAME_MATCHID : "";
	}

}
