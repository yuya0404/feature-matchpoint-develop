package com.example.dao;

import java.util.List;

import com.example.entity.Team;

public interface TeamDao{
	public List<Team> selectAll(Team team, String keyword);
	public List<Team> gameTeam(Integer team1, Integer team2);
	public int insertTeam(Team team);
	public int deleteTeam(Team team);
	public int updateTeam(Team team);
}