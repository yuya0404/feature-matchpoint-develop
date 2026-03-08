package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.controller.form.GameInfoForm;
import com.example.controller.form.GamePlayerForm;
import com.example.dao.ReceivedResultDao;
import com.example.dao.ScoreDao;
import com.example.dao.TeamDao;
import com.example.entity.ReceivedResult;
import com.example.entity.Score;
import com.example.entity.Team;
import com.example.util.Utility;

@Controller
public class GameModeController {
	@Autowired
	HttpSession session;

	@Autowired
	ReceivedResultDao receivedResultDao;

	@Autowired
	TeamDao teamDao;

	@Autowired
	ScoreDao scoreDao;

	// 試合設定画面
	@RequestMapping(value = "match")
	public String gameSetting(@ModelAttribute("game_info") GameInfoForm form) {
		if (session.getAttribute("loginId") == null && session.getAttribute("compLoginId") == null) {
			return "top";
		}
		form.setGameNo(1);
		form.setMaxPoint(21);
		form.setGameCount(3);
		return "game_setting";
	}

	@RequestMapping(value = "match_from_tournament")
	public String gameSet(@ModelAttribute("game_info") GameInfoForm form, @RequestParam("game_no") Integer gameNo) {
		if (session.getAttribute("loginId") == null && session.getAttribute("compLoginId") == null) {
			return "top";
		}
		if (gameNo != null) {
			form.setGameNo(gameNo);
		} else {
			form.setGameNo(1);
		}
		form.setMaxPoint(21);
		form.setGameCount(3);
		return "game_setting";
	}

	@RequestMapping(value = "server_setting")
	public String redirectServerSetting(@ModelAttribute("score_setting") GamePlayerForm playerForm) {
		if (session.getAttribute("loginId") == null && session.getAttribute("compLoginId") == null) {
			return "top";
		}
		Integer gameInfoId = (Integer) session.getAttribute("game_info_id");
		ReceivedResult receivedResult = new ReceivedResult();
		receivedResult.setGameInfoId(gameInfoId);
		List<ReceivedResult> list = receivedResultDao.search(receivedResult, "");
		List<Team> resultList = teamDao.gameTeam(list.get(0).getTeamIdA(), list.get(0).getTeamIdB());
		playerForm.setPlayerA(resultList.get(0).getPlayerAName());
		playerForm.setPlayerB(resultList.get(0).getPlayerBName());
		playerForm.setPlayerC(resultList.get(1).getPlayerAName());
		playerForm.setPlayerD(resultList.get(1).getPlayerBName());
		playerForm.setServer("playerB");
		return "server_setting";
	}

	@RequestMapping(value = "server_setting", params = "server_setting")
	public String serverSetting(@Validated @ModelAttribute("game_info") GameInfoForm form, BindingResult bindingResult,
			@ModelAttribute("score_setting") GamePlayerForm playerForm) {
		if (session.getAttribute("loginId") == null && session.getAttribute("compLoginId") == null) {
			return "top";
		}
		if (bindingResult.hasErrors()) {
			form.setGameNo(1);
			return "game_setting";
		}
		ReceivedResult receivedResult = new ReceivedResult();
		receivedResult.setGameNo(form.getGameNo());
		receivedResult.setCompId((Integer) session.getAttribute("compId"));
		List<ReceivedResult> list = receivedResultDao.searchMatch(receivedResult);
		if (list == null) {
			return "game_setting";
		}
		receivedResult.setMatchId(list.get(0).getMatchId());
		receivedResult.setGameCount(form.getGameCount());
		receivedResult.setMaxPoint(form.getMaxPoint());
		Integer i = receivedResultDao.insertGameInfo(receivedResult);
		session.setAttribute("game_info_id", i);
		session.setAttribute("game_count", form.getGameCount());
		session.setAttribute("max_point", form.getMaxPoint());
		return "redirect:/server_setting";
	}

	@RequestMapping(value = "score_setting")
	public String scoreSetting(@ModelAttribute("score_setting") GamePlayerForm form, Model model) {
		if (session.getAttribute("loginId") == null && session.getAttribute("compLoginId") == null) {
			return "top";
		}
		Score score = new Score();
		score.setGameInfoId((Integer) session.getAttribute("game_info_id"));
		List<Score> list = scoreDao.selectAll(score);
		int winCountA = 0;
		int winCountB = 0;
		List<String> scoreList = new ArrayList<String>();
		if (list != null) {
			for (Score s : list) {
				if (s.getTeamAScore() > s.getTeamBScore()) {
					winCountA++;
				} else if (s.getTeamAScore() > s.getTeamBScore()) {
					winCountB++;
				}
				scoreList.add(s.getTeamAScore() + "対" + s.getTeamBScore());
			}
		}
		form.setMaxPoint((Integer) session.getAttribute("max_point"));
		model.addAttribute("score_list", scoreList);
		model.addAttribute("setNumA", winCountA);
		model.addAttribute("setNumB", winCountB);
		session.setAttribute("abcdefg", "abcdefg");
		return "score_setting";
	}

	@RequestMapping(value = "game_set_result")
	public String gameSetResult(@ModelAttribute("score_setting") GamePlayerForm form, Model model) {
		if (session.getAttribute("loginId") == null && session.getAttribute("compLoginId") == null) {
			return "top";
		}
		Score score = new Score();
		score.setGameInfoId((Integer) session.getAttribute("game_info_id"));
		List<Score> list = scoreDao.selectAll(score);
		score.setTeamAScore(form.getTeam1Point());
		score.setTeamBScore(form.getTeam2Point());
		if (list == null) {
			score.setSetNo(1);
			list = new ArrayList<Score>();
		} else {
			score.setSetNo(list.size() + 1);
		}

		if (session.getAttribute("abcdefg") != null) {
			list.add(score);
			scoreDao.insertScore(score);
			session.setAttribute("abcdefg", null);
		}

		int winCountA = 0;
		int winCountB = 0;
		List<String> scoreList = new ArrayList<String>();
		if (list != null) {
			for (Score s : list) {
				if (s.getTeamAScore() > s.getTeamBScore()) {
					winCountA++;
				} else if (s.getTeamAScore() < s.getTeamBScore()) {
					winCountB++;
				}
				scoreList.add(s.getTeamAScore() + "対" + s.getTeamBScore());
			}
		}
		String btnStr = "";
		int winNum = (Integer) session.getAttribute("game_count") / 2 + 1;
		if (winNum <= winCountA) {
			btnStr = "試合終了";
			session.setAttribute("winner", "teamA");
		} else if (winNum <= winCountB) {
			btnStr = "試合終了";
			session.setAttribute("winner", "teamB");
		} else {
			btnStr = "次のゲームへ";
		}
		model.addAttribute("btn", btnStr);
		model.addAttribute("set", (winCountA + winCountB) + "/" + session.getAttribute("game_count"));
		model.addAttribute("playerA", form.getPlayerA());
		model.addAttribute("playerB", form.getPlayerB());
		model.addAttribute("playerC", form.getPlayerC());
		model.addAttribute("playerD", form.getPlayerD());
		model.addAttribute("score_list", scoreList);
		model.addAttribute("setNumA", winCountA);
		model.addAttribute("setNumB", winCountB);
		return "game_set_result";
	}

	@RequestMapping(value = "game_result", params = "next")
	public String nextGame(@ModelAttribute("score_setting") GamePlayerForm form,
			@ModelAttribute("update_game_info") GameInfoForm gameInfoForm, Model model) {
		if (session.getAttribute("loginId") == null && session.getAttribute("compLoginId") == null) {
			return "top";
		}
		if (session.getAttribute("winner") == null) {
			return "redirect:/server_setting";
		} else {
			Score score = new Score();
			score.setGameInfoId((Integer) session.getAttribute("game_info_id"));
			List<Score> list = scoreDao.selectAll(score);
			int winCountA = 0;
			int winCountB = 0;
			if (list != null) {
				for (Score s : list) {
					if (s.getTeamAScore() > s.getTeamBScore()) {
						winCountA++;
					} else if (s.getTeamAScore() < s.getTeamBScore()) {
						winCountB++;
					}
				}
			}
			ReceivedResult receivedResult = new ReceivedResult();
			receivedResult.setGameInfoId((Integer) session.getAttribute("game_info_id"));
			List<ReceivedResult> resultList = receivedResultDao.search(receivedResult, "");
			session.setAttribute("winner", null);
			model.addAttribute("game_no", resultList.get(0).getGameNo());
			model.addAttribute("final_score", winCountA + "-" + winCountB);
			model.addAttribute("playerA", form.getPlayerA());
			model.addAttribute("playerB", form.getPlayerB());
			model.addAttribute("playerC", form.getPlayerC());
			model.addAttribute("playerD", form.getPlayerD());
			return "game_result";
		}
	}

	@RequestMapping(value = "update_game_info")
	public String updateGameInfo(@ModelAttribute("update_game_info") GameInfoForm form) {
		if (session.getAttribute("loginId") == null && session.getAttribute("compLoginId") == null) {
			return "top";
		}
		ReceivedResult receivedResult = new ReceivedResult();
		receivedResult.setGameInfoId((Integer) session.getAttribute("game_info_id"));
		receivedResult.setCoatNo(form.getCoatNo());
		receivedResult.setJudgeName(form.getJudgeName());
		if(Utility.notIsEmptyNull(form.getCoatNo()) || Utility.notIsEmptyNull(form.getJudgeName())) {
			receivedResultDao.update(receivedResult);
		}
		return "tournament";
	}
}