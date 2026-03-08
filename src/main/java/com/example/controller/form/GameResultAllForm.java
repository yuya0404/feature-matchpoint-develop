package com.example.controller.form;

public class GameResultAllForm{
	private String recordDate;
	private Integer gameNo;
	private Integer coatNo;
	private String judgeName;
	private Integer tournamentNo;
	private String keyword;
	private String orderBy;
	private Integer recordStatus;

	public String getRecordDate() { return recordDate; }
	public void setRecordDate(String recordDate) { this.recordDate = recordDate; }
	public Integer getGameNo() { return gameNo; }
	public void setGameNo(Integer gameNo) { this.gameNo = gameNo; }
	public Integer getCoatNo() { return coatNo; }
	public void setCoatNo(Integer coatNo) { this.coatNo = coatNo; }
	public String getJudgeName() { return judgeName; }
	public void setJudgeName(String judgeName) { this.judgeName = judgeName; }
	public Integer getTournamentNo() { return tournamentNo; }
	public void setTournamentNo(Integer tournamentNo) { this.tournamentNo = tournamentNo; }
	public String getKeyword() { return keyword; }
	public void setKeyword(String keyword) { this.keyword = keyword; }
	public String getOrderBy() { return orderBy; }
	public void setOrderBy(String orderBy) { this.orderBy = orderBy; }
	public Integer getRecordStatus() { return recordStatus; }
	public void setRecordStatus(Integer recordStatus) { this.recordStatus = recordStatus; }
}
