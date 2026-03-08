package com.example.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CompForm{

	private Integer compId;
	@NotBlank
	private String compName;
	@NotBlank
	private String compDate;
	@NotBlank
	private String compPlace;
	@NotBlank
	private String compLoginId;
	@NotNull
	private Integer tournamentCount;
	private Integer gameType;
	private String gameTypeStr;
	private String memo;
	private Integer tournamentEditStatus;

	public Integer getCompId() { return compId; }
	public void setCompId(Integer compId) { this.compId = compId; }
	public String getCompName() { return compName; }
	public void setCompName(String compName) { this.compName = compName; }
	public String getCompDate() { return compDate; }
	public void setCompDate(String compDate) { this.compDate = compDate; }
	public String getCompPlace() { return compPlace; }
	public void setCompPlace(String compPlace) { this.compPlace = compPlace; }
	public String getCompLoginId() { return compLoginId; }
	public void setCompLoginId(String compLoginId) { this.compLoginId = compLoginId; }
	public Integer getTournamentCount() { return tournamentCount; }
	public void setTournamentCount(Integer tournamentCount) { this.tournamentCount = tournamentCount; }
	public Integer getGameType() { return gameType; }
	public void setGameType(Integer gameType) { this.gameType = gameType; }
	public String getGameTypeStr() { return gameTypeStr; }
	public void setGameTypeStr(String gameTypeStr) { this.gameTypeStr = gameTypeStr; }
	public String getMemo() { return memo; }
	public void setMemo(String memo) { this.memo = memo; }
	public Integer getTournamentEditStatus() { return tournamentEditStatus; }
	public void setTournamentEditStatus(Integer tournamentEditStatus) { this.tournamentEditStatus = tournamentEditStatus; }
}
