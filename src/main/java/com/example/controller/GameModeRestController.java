package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.ReceivedResultDao;
import com.example.dao.TeamDao;
import com.example.entity.ReceivedResult;

@RestController
public class GameModeRestController{
	@Autowired
	HttpSession session;
	@Autowired
	ReceivedResultDao receivedResultDao;
	@Autowired
	TeamDao teamDao;

	@RequestMapping("searchMember")
	public ReceivedResult searchMember(@RequestParam("gameNo") Integer gameNo) {
		ReceivedResult receivedResult = new ReceivedResult();
		receivedResult.setCompId((Integer)session.getAttribute("compId"));
		receivedResult.setGameNo(gameNo);
		List<ReceivedResult> list = receivedResultDao.searchMatchTeam(receivedResult);
		return list != null ? list.get(0) : receivedResult;
	}
}