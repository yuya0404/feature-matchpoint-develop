package com.example.dao;

import java.util.List;

import com.example.entity.Score;

public interface ScoreDao{
	public List<Score> selectAll(Score score);
	public void insertScore(Score score);
	public void deleteScore(Score score);
	public void updateScore(Score score);
}