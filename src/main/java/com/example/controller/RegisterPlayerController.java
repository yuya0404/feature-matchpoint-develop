package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.controller.form.PlayerForm;
import com.example.dao.CompDao;
import com.example.dao.TeamDao;
import com.example.entity.Comp;
import com.example.entity.Team;

//ユーザー情報 Controller

@Controller
public class RegisterPlayerController {
	@Autowired
	HttpSession session;

	@Autowired
	TeamDao teamDao;

	@Autowired
	CompDao compDao;
	
	//選手登録へ
	@RequestMapping(value="insert_player")
	public String playerInsertPage(@ModelAttribute("insert_player") PlayerForm form, Model model) {
		if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId")== null) {
			return "top";
		}
		session.setAttribute("srcf", "taisaku");
		return "insert_player";
	}

	//選手登録 登録ボタン
	@RequestMapping(value="insert_player", params="insert")
	public String playerInsert(@Validated@ModelAttribute("insert_player")  PlayerForm form, BindingResult bindingResult,Model model) {
		if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId")== null) {
			return "top";
		}
		if(bindingResult.hasErrors()) {
			if(form.getPlayerAName().isEmpty()) {
				model.addAttribute("errorMsg1", "※プレイヤー名が記入されていません。");
			}
			if(form.getPlayerBName().isEmpty()) {
				model.addAttribute("errorMsg2", "※プレイヤー名が記入されていません。");
			}
			if(form.getTournamentNo() == null) {
				model.addAttribute("errorMsg3", "※トーナメント番号を入力してください。");
			}
			return "insert_player";
		}
		
		Comp comp = new Comp();
		comp.setCompId((Integer)session.getAttribute("compId"));
		List<Comp> list = compDao.selectAll(comp);
		Integer tournamentCount = list.get(0).getTournamentCount();
		if(tournamentCount < form.getTournamentNo()) {
			model.addAttribute("errorMsg3", tournamentCount + "以下の数字を入力して下さい。");
			return "insert_player";
		}

		Team team = new Team();
		Integer compId = (Integer)session.getAttribute("compId");
		team.setCompId(compId);
		team.setPlayerAName(form.getPlayerAName());
		team.setPlayerBName(form.getPlayerBName());
		team.setTournamentNo(form.getTournamentNo());
		if("taisaku".equals(session.getAttribute("srcf"))) {
			teamDao.insertTeam(team);
			session.setAttribute("srcf", null);
		}
		
		team = new Team();
		team.setCompId(compId);
		List<Team> registList = teamDao.selectAll(team,"");//
		model.addAttribute("allPlayer", registList);
		return "all_player";
	}
}