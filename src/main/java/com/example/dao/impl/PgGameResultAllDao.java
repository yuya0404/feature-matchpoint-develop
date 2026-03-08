package com.example.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dao.GameResultAllDao;
import com.example.entity.GameResultAll;
import com.example.util.Utility;


@Repository
public class PgGameResultAllDao implements GameResultAllDao{
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<GameResultAll> search(String keyword, Integer status){
		if(Utility.notIsEmptyNull(keyword)) {
			String sql = "select g.record_date, m.game_no, g.coat_no, g.judge_name, t.tournament_no, g.record_status from match m join game_info g on m.match_id = g.match_id join team t on t.team_id = m.team_id_a where g.record_status = :record_status and g.judge_name like :keyword order by g.record_date desc";
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("keyword", '%'+ keyword + '%');
	        param.addValue("record_status", status);
			List<GameResultAll> resultList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<GameResultAll>(GameResultAll.class));
			return resultList.isEmpty() ? null : resultList;
		}
		return null;
	}
	
	public List<GameResultAll> select(Integer compId, Integer status){
		String sql = "SELECT g.record_date, m.game_no, g.coat_no, g.judge_name, "
				+ "t.tournament_no "
				+ "FROM match m "
				+ "JOIN game_info g ON m.match_id = g.match_id "
				+ "JOIN team t ON t.team_id = m.team_id_a "
				+ "WHERE g.record_status = :record_status "
				+ "ORDER BY g.record_date desc";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("record_status", status);
		List<GameResultAll> resultList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<GameResultAll>(GameResultAll.class));
		return resultList.isEmpty() ? null : resultList;
	}

	@Override
	public List<GameResultAll> select(Integer status) {
		String sql = "SELECT g.record_date, m.game_no, g.coat_no, g.judge_name, "
				+ "t.tournament_no "
				+ "FROM match m "
				+ "JOIN game_info g ON m.match_id = g.match_id "
				+ "JOIN team t ON t.team_id = m.team_id_a "
				+ "WHERE g.record_status = :record_status "
				+ "ORDER BY g.record_date desc";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("record_status", status);
		List<GameResultAll> resultList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<GameResultAll>(GameResultAll.class));
		return resultList.isEmpty() ? null : resultList;
	}
}