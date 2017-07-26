package com.td.admin.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.admin.entity.FinCalender;
import com.td.admin.mapper.FinCalenderMapper;


@Service
public class CalenderDao {
	
	@Autowired
	private FinCalenderMapper finCalenderMapper;

	public List<FinCalender> find(Map<String, Object> map) {
		return finCalenderMapper.find(map);
	}
	public List<FinCalender> queryCalendar(FinCalender calendar) {
		return finCalenderMapper.queryCalendar(calendar);
	}
	
	public int update(FinCalender calender) {
		return finCalenderMapper.updateByPrimaryKeySelective(calender);
	}

	public int add(FinCalender calender) {
		 
		return finCalenderMapper.insertSelective(calender);
	}

	public int delete(Long id) {
		return finCalenderMapper.deleteByPrimaryKey(id);
	}
	
	
}
