package com.example.dao;

import java.util.List;

import com.example.entity.GameResultAll;

public interface GameResultAllDao{
	public List<GameResultAll> search(String keyword, Integer status);
	public List<GameResultAll> select(Integer compId, Integer status);
	public List<GameResultAll> select(Integer status);
}