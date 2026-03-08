package com.example.entity;

import java.util.Date;

public class ReceivedResult {

	private Integer matchId;
	private Integer compId;
	private Integer gameInfoId;
	private Integer gameNo;
	private Integer coatNo;
	private String judgeName;
	private Integer recordStatus;
	private Date recordDate;
	private Integer maxPoint;
	private Integer gameCount;
	private Integer tournamentNo;
	private Integer teamIdA;
	private String teamAPlayer1;
	private String teamAPlayer2;
	private Integer teamIdB;
	private String teamBPlayer1;
	private String teamBPlayer2;

	public Integer getMatchId() { return matchId; }
	public void setMatchId(Integer matchId) { this.matchId = matchId; }
	public Integer getCompId() { return compId; }
	public void setCompId(Integer compId) { this.compId = compId; }
	public Integer getGameInfoId() { return gameInfoId; }
	public void setGameInfoId(Integer gameInfoId) { this.gameInfoId = gameInfoId; }
	public Integer getGameNo() { return gameNo; }
	public void setGameNo(Integer gameNo) { this.gameNo = gameNo; }
	public Integer getCoatNo() { return coatNo; }
	public void setCoatNo(Integer coatNo) { this.coatNo = coatNo; }
	public String getJudgeName() { return judgeName; }
	public void setJudgeName(String judgeName) { this.judgeName = judgeName; }
	public Integer getRecordStatus() { return recordStatus; }
	public void setRecordStatus(Integer recordStatus) { this.recordStatus = recordStatus; }
	public Date getRecordDate() { return recordDate; }
	public void setRecordDate(Date recordDate) { this.recordDate = recordDate; }
	public Integer getMaxPoint() { return maxPoint; }
	public void setMaxPoint(Integer maxPoint) { this.maxPoint = maxPoint; }
	public Integer getGameCount() { return gameCount; }
	public void setGameCount(Integer gameCount) { this.gameCount = gameCount; }
	public Integer getTournamentNo() { return tournamentNo; }
	public void setTournamentNo(Integer tournamentNo) { this.tournamentNo = tournamentNo; }
	public Integer getTeamIdA() { return teamIdA; }
	public void setTeamIdA(Integer teamIdA) { this.teamIdA = teamIdA; }
	public String getTeamAPlayer1() { return teamAPlayer1; }
	public void setTeamAPlayer1(String teamAPlayer1) { this.teamAPlayer1 = teamAPlayer1; }
	public String getTeamAPlayer2() { return teamAPlayer2; }
	public void setTeamAPlayer2(String teamAPlayer2) { this.teamAPlayer2 = teamAPlayer2; }
	public Integer getTeamIdB() { return teamIdB; }
	public void setTeamIdB(Integer teamIdB) { this.teamIdB = teamIdB; }
	public String getTeamBPlayer1() { return teamBPlayer1; }
	public void setTeamBPlayer1(String teamBPlayer1) { this.teamBPlayer1 = teamBPlayer1; }
	public String getTeamBPlayer2() { return teamBPlayer2; }
	public void setTeamBPlayer2(String teamBPlayer2) { this.teamBPlayer2 = teamBPlayer2; }
}
