package com.example.dao;

import java.util.List;

import com.example.entity.Manage;

public interface ManageDao{
	public List<Manage> selectAll(Manage manage);
}