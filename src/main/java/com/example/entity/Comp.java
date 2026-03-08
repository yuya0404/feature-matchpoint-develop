
package com.example.entity;

import java.util.Date;

public class Comp{
	private Integer compId;
	private String compName;
	private Date compDate;
	private String compPlace;
	private String compLoginId;
	private Integer tournamentNo;
	private Integer tournamentCount;
	private Integer gameType;
	private String gameTypeStr;
	private Integer tournamentEditStatus;
	private String memo;

	public Integer getCompId() { return compId; }
	public void setCompId(Integer compId) { this.compId = compId; }
	public String getCompName() { return compName; }
	public void setCompName(String compName) { this.compName = compName; }
	public Date getCompDate() { return compDate; }
	public void setCompDate(Date compDate) { this.compDate = compDate; }
	public String getCompPlace() { return compPlace; }
	public void setCompPlace(String compPlace) { this.compPlace = compPlace; }
	public String getCompLoginId() { return compLoginId; }
	public void setCompLoginId(String compLoginId) { this.compLoginId = compLoginId; }
	public Integer getTournamentNo() { return tournamentNo; }
	public void setTournamentNo(Integer tournamentNo) { this.tournamentNo = tournamentNo; }
	public Integer getTournamentCount() { return tournamentCount; }
	public void setTournamentCount(Integer tournamentCount) { this.tournamentCount = tournamentCount; }
	public Integer getGameType() { return gameType; }
	public void setGameType(Integer gameType) { this.gameType = gameType; }
	public String getGameTypeStr() { return gameTypeStr; }
	public void setGameTypeStr(String gameTypeStr) { this.gameTypeStr = gameTypeStr; }
	public Integer getTournamentEditStatus() { return tournamentEditStatus; }
	public void setTournamentEditStatus(Integer tournamentEditStatus) { this.tournamentEditStatus = tournamentEditStatus; }
	public String getMemo() { return memo; }
	public void setMemo(String memo) { this.memo = memo; }
}
