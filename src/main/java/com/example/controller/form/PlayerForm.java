package com.example.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PlayerForm{

	private Integer teamId;
	private Integer compId;

	@NotBlank
	@Size(max = 15, message = "名前は15文字以内で入力してください")
	private String playerAName;

	@NotBlank
	@Size(max = 15, message = "名前は15文字以内で入力してください")
	private String playerBName;

	@NotNull(message = "トーナメント番号を入力してください")
	private Integer tournamentNo;

	private String keyword;

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
	public String getKeyword() { return keyword; }
	public void setKeyword(String keyword) { this.keyword = keyword; }
}
