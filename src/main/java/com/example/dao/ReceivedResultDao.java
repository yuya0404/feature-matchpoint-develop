package com.example.dao;

import java.util.List;

import com.example.entity.ReceivedResult;

public interface ReceivedResultDao {
	public List<ReceivedResult> box(Integer compId, Integer status, String keyword);
	public List<ReceivedResult> search(ReceivedResult receivedResult, String keyword);
	public List<ReceivedResult> searchMatch(ReceivedResult receivedResult);
	public List<ReceivedResult> searchMatchTeam(ReceivedResult receivedResult);
	public int insertMatch (ReceivedResult receivedResult);
	public int insertGameInfo (ReceivedResult receivedResult);
	public int update (ReceivedResult receivedResult);
	public int updateMatch(ReceivedResult receivedResult);
}
