package com.example.controller.form;

import javax.validation.constraints.NotNull;

public class GameInfoForm{

	@NotNull
	private Integer gameNo;
	private Integer maxPoint;
	private Integer gameCount;
	private Integer coatNo;
	private String judgeName;

	public Integer getGameNo() { return gameNo; }
	public void setGameNo(Integer gameNo) { this.gameNo = gameNo; }
	public Integer getMaxPoint() { return maxPoint; }
	public void setMaxPoint(Integer maxPoint) { this.maxPoint = maxPoint; }
	public Integer getGameCount() { return gameCount; }
	public void setGameCount(Integer gameCount) { this.gameCount = gameCount; }
	public Integer getCoatNo() { return coatNo; }
	public void setCoatNo(Integer coatNo) { this.coatNo = coatNo; }
	public String getJudgeName() { return judgeName; }
	public void setJudgeName(String judgeName) { this.judgeName = judgeName; }
}
