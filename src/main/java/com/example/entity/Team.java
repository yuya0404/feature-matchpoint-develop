package com.example.entity;

public class Team{

	private Integer teamId;
	private Integer compId;
	private String playerAName;
	private String playerBName;
	private Integer tournamentNo;

	public Integer getTeamId() { return teamId; }
	public void setTeamId(Integer teamId) { this.teamId = teamId; }
	public Integer getCompId() { return compId; }
	public void setCompId(Integer compId) { this.compId = compId; }
	public String getPlayerAName() { return playerAName; }
	public void setPlayerAName(String playerAName) { this.playerAName = playerAName; }
	public String getPlayerBName() { return playerBName; }
	public void setPlayerBName(String playerBName) { this.playerBName = playerBName; }
	public Integer getTournamentNo() { return tournamentNo; }
	public void setTournamentNo(Integer tournamentNo) { this.tournamentNo = tournamentNo; }
}
