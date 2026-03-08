package com.example.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.controller.form.CompForm;
import com.example.dao.CompDao;
import com.example.entity.Comp;

@Controller
public class CompDetailController{
	@Autowired
	HttpSession session;
	
	@Autowired
	CompDao compDao;
	
	//大会詳細画面へ
	@RequestMapping(value="comp_detail")
	public String compDetail(@ModelAttribute("comp_detail") CompForm form, Model model) {
		if(session.getAttribute("loginId") == null &&session.getAttribute("compLoginId")== null) {
			return "top";
		}
		Comp comp = new Comp();
		
		//初期値を入力
		comp.setCompId((Integer)session.getAttribute("compId"));
		List<Comp> compDetail = compDao.selectAll(comp);
		
		model.addAttribute("comp_detail", compDetail.get(0));
		return "comp_detail";
	}
	
	//大会削除
	@RequestMapping(value="comp_delete")
	public String compDelete(@ModelAttribute("comp_detail") CompForm form, Model model) {
		if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId")== null) {
			return "top";
		}
		Comp comp = new Comp();
		comp.setCompId((Integer)session.getAttribute("compId"));
		compDao.deleteComp(comp);
			
		model.addAttribute("resultList", compDao.selectAll(new Comp()));
		
		return "comp_list";
	}
		
		@RequestMapping(value="comp_detail_edit")
		public String compDetailUpdate(@ModelAttribute("comp_detail") CompForm form, Model model) {
			if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId")== null) {
				return "top";
			}
			Comp comp = new Comp();
			
			//初期値を入力
			comp.setCompId((Integer)session.getAttribute("compId"));
			
			List<Comp> compDetail = compDao.selectAll(comp);
			
			model.addAttribute("comp_detail", compDetail.get(0));
			return "comp_detail_update";
		}
		
		//大会編集画面
		@RequestMapping(value="comp_detail_update", params="completion")
		public String compUpdatePage(@Validated @ModelAttribute("comp_detail") CompForm form,
				BindingResult bindingResult, Model model) {
			if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId")== null) {
				return "top";
			}
			if(bindingResult.hasErrors()) {
				return "comp_detail_update";
			}
			
			Comp comp = new Comp();
			comp.setCompLoginId(form.getCompLoginId());
			List<Comp> list = compDao.selectAll(comp);
			
			if(list != null) {
				if(list.get(0).getCompId() != (Integer)session.getAttribute("compId")) {
					model.addAttribute("errorMsg", "大会ログインIDが重複しています。");
					return "comp_detail_update";
				}
			}

			Date date= Date.valueOf(form.getCompDate());
			comp.setCompId((Integer)session.getAttribute("compId"));
			comp.setCompName(form.getCompName());
			comp.setCompDate(date);
			comp.setCompPlace(form.getCompPlace());
			
			comp.setTournamentCount(form.getTournamentCount());
			comp.setGameType(form.getGameType());
			comp.setMemo(form.getMemo());
			
			
			compDao.updateComp(comp);
			
			List<Comp> compDetail = compDao.selectAll(comp);
			
			model.addAttribute("comp_detail", compDetail.get(0));
			return "comp_detail";
		}
		
		//大会編集画面から戻る
		@RequestMapping(value="comp_detail_update", params="back")
		public String compDetailBack(@ModelAttribute("comp_detail") CompForm form, Model model) {
			if(session.getAttribute("loginId") == null && session.getAttribute("compLoginId")== null) {
				return "top";
			}
			Comp comp = new Comp();
			
			comp.setCompId((Integer)session.getAttribute("compId"));
			
			List<Comp> compDetail = compDao.selectAll(comp);
			
			model.addAttribute("comp_detail", compDetail.get(0));
			return "comp_detail";
			
		}
}