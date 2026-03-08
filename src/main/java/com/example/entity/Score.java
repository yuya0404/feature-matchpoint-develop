package com.example.entity;

public class Score{
	private Integer scoreId;
	private Integer gameInfoId;
	private Integer setNo;
	private Integer teamAScore;
	private Integer teamBScore;

	public Integer getScoreId() { return scoreId; }
	public void setScoreId(Integer scoreId) { this.scoreId = scoreId; }
	public Integer getGameInfoId() { return gameInfoId; }
	public void setGameInfoId(Integer gameInfoId) { this.gameInfoId = gameInfoId; }
	public Integer getSetNo() { return setNo; }
	public void setSetNo(Integer setNo) { this.setNo = setNo; }
	public Integer getTeamAScore() { return teamAScore; }
	public void setTeamAScore(Integer teamAScore) { this.teamAScore = teamAScore; }
	public Integer getTeamBScore() { return teamBScore; }
	public void setTeamBScore(Integer teamBScore) { this.teamBScore = teamBScore; }
}
