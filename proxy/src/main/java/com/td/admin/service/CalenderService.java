package com.td.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.dao.CalenderDao;
import com.td.admin.entity.FinCalender;

/**
 * 日历管理Controller
 * @author lianjie
 */
@Service
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class CalenderService {
	
	@Autowired
	private CalenderDao calenderDao;

	public List<FinCalender> find(String date) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", date);
		return calenderDao.find(map);
	}
	public List<FinCalender> queryCalendar(FinCalender calendar) {
		
		return calenderDao.queryCalendar(calendar);
	}
	public int update(FinCalender calender) {
		return calenderDao.update(calender);
	}

	public int add(FinCalender calender) {
		return calenderDao.add(calender);
	}

	public int delete(Long id) {
		return calenderDao.delete(id);
	}
	
}
