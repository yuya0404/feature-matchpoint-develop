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

@Controller
public class EditPlayerController {

	@Autowired
	HttpSession session;

	@Autowired
	TeamDao teamDao;

	@Autowired
	CompDao compDao;

	@RequestMapping(value="edit_player", params= "edit")
	public String playerEdit(@Validated @ModelAttribute("edit_player") PlayerForm form, BindingResult bindingResult,Model model) {
		if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId")== null) {
			return "top";
		}
		if (bindingResult.hasErrors()) {

			return "edit_player";
		}

		Comp comp = new Comp();
		comp.setCompId((Integer)session.getAttribute("compId"));
		List<Comp> list = compDao.selectAll(comp);
		Integer tournamentCount = list.get(0).getTournamentCount();
		if(tournamentCount < form.getTournamentNo()) {
			model.addAttribute("errorMsg", tournamentCount + "以下の数字を入力して下さい。");
			return "edit_player";
		}

		Team team = new Team();
		Integer teamId = form.getTeamId();
		Integer compId = (Integer)session.getAttribute("compId"); //結合時に、comp_idをsession.getAttribute();で持たせる予定
		team.setTeamId(teamId);
		team.setCompId(compId);
		team.setPlayerAName(form.getPlayerAName());
		team.setPlayerBName(form.getPlayerBName());
		team.setTournamentNo(form.getTournamentNo());
		int result = teamDao.updateTeam(team);
		if(result == 0) {
			return "edit_player";
		}

		//大会一覧への表示処理
		Team showTeam = new Team();
		showTeam.setCompId(compId);
		List<Team> updateList = teamDao.selectAll(showTeam, "" ); 
		model.addAttribute("allPlayer", updateList);
		return "all_player";
	}


	//選手情報更新画面 delete
	@RequestMapping(value="edit_player", params= "delete")
	public String playerDelete1(@Validated @ModelAttribute("edit_player") PlayerForm form, BindingResult bindingResult,Model model){
		if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId")== null) {
			return "top";
		}

		Team team = new Team();
		Integer teamId = form.getTeamId();
		team.setTeamId(teamId);
		int result = teamDao.deleteTeam(team);
		if(result == 0) {
			return "edit_player";
		}
		Integer compId =(Integer)session.getAttribute("compId");
		Team showTeam = new Team();
		showTeam.setCompId(compId);
		List<Team> deleteList = teamDao.selectAll(showTeam, ""); 
		model.addAttribute("allPlayer", deleteList);



		return "all_player";
	}
}