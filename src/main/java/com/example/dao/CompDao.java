
package com.example.dao;

import java.util.List;

import com.example.entity.Comp;

public interface CompDao{
	public List<Comp> selectAll(Comp comp);
	public int insertComp(Comp comp);
	public int deleteComp(Comp comp);
	public int updateComp(Comp comp);
}
