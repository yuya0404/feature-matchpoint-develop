package com.example.entity;

public class Winner {

	private Integer gameNo;
	private Integer winnerTeamId;

	public Winner() {
	}

	public Winner(Integer gameNo, Integer winnerTeamId) {
		this.gameNo = gameNo;
		this.winnerTeamId = winnerTeamId;
	}

	public Integer getGameNo() { return gameNo; }
	public void setGameNo(Integer gameNo) { this.gameNo = gameNo; }
	public Integer getWinnerTeamId() { return winnerTeamId; }
	public void setWinnerTeamId(Integer winnerTeamId) { this.winnerTeamId = winnerTeamId; }
}
