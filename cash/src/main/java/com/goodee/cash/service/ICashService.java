package com.goodee.cash.service;

import java.util.Map;

public interface ICashService {
	Map<String, Object> getCalendarAndCashbookList(String memberId, Integer targetYear, Integer targetMonth);

}
