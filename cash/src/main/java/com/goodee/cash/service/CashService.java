package com.goodee.cash.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodee.cash.mapper.*;
import com.goodee.cash.vo.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CashService implements ICashService {
	
	@Autowired
	private CashMapper cashMapper;
	
	//요청단에서 타겟이 넘어오지 않으면 기본값은 -1
	public Map<String, Object> getCalendarAndCashbookList(String memberId, Integer targetYear, Integer targetMonth) {
	    Map<String, Object> resultMap = new HashMap<>();

	    Calendar firstDate = Calendar.getInstance();
	    firstDate.set(Calendar.DATE, 1);

	    if (targetYear != null && targetMonth != null) {
	        firstDate.set(Calendar.YEAR, targetYear);
	        firstDate.set(Calendar.MONTH, targetMonth);
	    }

	    targetYear = firstDate.get(Calendar.YEAR);
	    targetMonth = firstDate.get(Calendar.MONTH);

	    int lastDate = firstDate.getActualMaximum(Calendar.DATE);
	    int beginBlank = firstDate.get(Calendar.DAY_OF_WEEK) - 1;

	    int endBlank = 0;
	    if ((beginBlank + lastDate + endBlank) % 7 != 0) {
	        endBlank = 7 - ((beginBlank + lastDate + endBlank) % 7);
	    }

	    int totalTd = beginBlank + lastDate + endBlank;

	    resultMap.put("targetYear", targetYear);
	    resultMap.put("targetMonth", targetMonth);
	    resultMap.put("lastDate", lastDate);
	    resultMap.put("beginBlank", beginBlank);
	    resultMap.put("endBlank", endBlank);
	    resultMap.put("totalTd", totalTd);
	    resultMap.put("memberId", memberId);
	    
	    //cashbook 내용과 출력 달의 차이를 없애기 위한 새로운 map선언
	    Map<String, Object> cashbookInputMap = new HashMap<>();
	    cashbookInputMap.put("targetYear", targetYear);
	    cashbookInputMap.put("targetMonth", targetMonth+1);
	    cashbookInputMap.put("memberId", memberId);
	    
	    //resultMap에 DAO 들어간 리스트 put
	    List<Cashbook> cashbookList = cashMapper.selectCashbookListByMonth(cashbookInputMap);
	    resultMap.put("cashbookList", cashbookList);

	    return resultMap;
	}



}
