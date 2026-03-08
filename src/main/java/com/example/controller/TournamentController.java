package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.controller.form.CompForm;
import com.example.dao.CompDao;
import com.example.dao.ManageDao;
import com.example.dao.ReceivedResultDao;
import com.example.dao.ScoreDao;
import com.example.dao.TeamDao;
import com.example.entity.Comp;

@Controller
public class TournamentController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ManageDao manageDao;
	
	@Autowired
	ScoreDao scoreDao;

	@Autowired
	CompDao compDao;
	
	@Autowired
	TeamDao teamDao;

	@Autowired
	ReceivedResultDao receivedResultDao;
	
	
	//トーナメントへ
		@RequestMapping(value="tournament")
		public String tournament(@ModelAttribute("compInfo") CompForm form, Model model) {
			if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId")== null) {
				return "top";
			}
			Integer compId =form.getCompId();
			
			//セッションがないけどcompIdがセットされている（初期状態）
			if((Integer)session.getAttribute("compId") == null && compId != null) {
				session.setAttribute("compId", compId);
				
			//compIdがセットされているとき（セッションも破棄されていない）
			}else if(compId != null) {
				session.setAttribute("compId", compId);
				
			//sessionに値がセットされていない時
			}else if ((Integer)session.getAttribute("compId") == null){
				
				//運営側でヘッダーからアクセスするときのための条件分岐
				if(session.getAttribute("compLoginId")== null) {
					Comp comp = new Comp();
					model.addAttribute("resultList", compDao.selectAll(comp));
					return "comp_list";
				}
			}else {
				compId = (Integer)session.getAttribute("compId");
			}
			Comp newComp = new Comp();
			newComp.setCompId(compId);
			Comp result = compDao.selectAll(newComp).get(0);
			session.setAttribute("tournamentEditStatus",result.getTournamentEditStatus());
			return "tournament";
			
		}

	//トーナメント表編集
	@RequestMapping(value="edit_tournament")
	public String editTournament(Model model) {
		if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId")== null) {
			return "top";
		}
		Integer compId = (Integer)session.getAttribute("compId");
		Comp comp = new Comp();
		comp.setCompId(compId);
		Comp compList = compDao.selectAll(comp).get(0);
		int status = compList.getTournamentEditStatus();
		
		switch(status) {
		case 0, 1: 
			return "edit_tournament";
			
		case 2: 
			model.addAttribute("msg","編集は完了しています。");
			return "tournament";
		}
		return "all_player";
	}
	
	
	
	//トーナメント表のセーブ
	@RequestMapping(value="submit_edition")
	public String tournamentSave() {
		if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId") == null) {
			return "top";
		}
		return "edit_tournament";
	}
	
	//トーナメント表からDBに更新をかける
	@RequestMapping(value="save_tournament")
	public String saveTournament(Model model) {
		if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId") == null) {
			return "top";
		}
		Comp comp = new Comp();
		Integer compId = (Integer)session.getAttribute("compId");
		comp.setCompId(compId);
		comp.setTournamentEditStatus(2);
		int count = compDao.updateComp(comp);
		session.setAttribute("tournamentEditStatus",2);
		if(count!= 2) {
			model.addAttribute("msg", "トーナメント情報を確定しました");
		}else {
			return "edit_tournament";
		}
		
		return "tournament";
	}
//	//試合番号ボタンクリック
//	@RequestMapping(value="registered_game_result")
//	public String gameResult(@RequestParam("gameNo")Integer gameNo, Model model) {
//		if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId")== null) {
//			return "top";
//		}
//		ReceivedResult result = new ReceivedResult();
//		result.setGameNo(gameNo);
//		result.setRecordStatus(1);
//		
//		List<ReceivedResult> resultList = receivedResultDao.search(result, null);
//		ReceivedResult matchInfo = resultList.get(0);
//		model.addAttribute("matchInfo",resultList);
//		
//		Score score = new Score();
//		score.setGameInfoId(matchInfo.getGameInfoId());
//		List<Score> scoreList = scoreDao.selectAll(score);
//		model.addAttribute("scoreList", scoreList);
//		
//		return "game_result_final_registered";
//	}
	
	//試合結果からトーナメントへ
	@RequestMapping(value="tournament_back")
	public String tournamentBack(Model model) {
		if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId")== null) {
			return "top";
		}

		return "tournament";
	}
	//大会一覧へ戻る
	@RequestMapping(value="comp_list_back")
	public String compListBack(@ModelAttribute("compInfo") CompForm form, Model model) {
		if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId")== null) {
			return "top";
		}else if(session.getAttribute("loginId") == null) {
			return "comp_login";
		}
		session.setAttribute("compId",null);
		Comp comp = new Comp();
		model.addAttribute("resultList", compDao.selectAll(comp));
		return "comp_list";
	}
}
