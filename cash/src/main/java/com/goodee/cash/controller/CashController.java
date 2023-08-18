package com.goodee.cash.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.cash.service.ICashService;
import com.goodee.cash.vo.Cashbook;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CashController {
	@Autowired private ICashService cashService;
	
	@GetMapping("/calendar")
	public String calendar(
			HttpSession session, Model model
			,@RequestParam(required = false, name="targetYear") Integer targetYear
			, @RequestParam(required = false, name="targetMonth") Integer targetMonth
			) {
		log.debug("CashController.calendar() param targetYear : " + targetYear);
		log.debug("CashController.calendar() param targetMonth : " + targetMonth);
		String memberId = "test1";

	    // memberId를 직접 전달하여 호출
		Map<String, Object> resultMap = cashService.getCalendarAndCashbookList(memberId, targetYear, targetMonth);
		log.debug("CashService.getCalendar() resultMap: " + resultMap.toString());
		

		List<Cashbook> dbCashbook = (List<Cashbook>)resultMap.get("cashbookList");
		log.debug("CashService.getCalendar() dbCashbook: " + dbCashbook.toString());
		
		
		// view에 넘겨줄 model
		model.addAttribute("dbCashbook", dbCashbook);
		model.addAttribute("targetYear", resultMap.get("targetYear"));
		model.addAttribute("targetMonth", resultMap.get("targetMonth"));
		model.addAttribute("lastDate", resultMap.get("lastDate"));
		model.addAttribute("beginBlank", resultMap.get("beginBlank"));
		model.addAttribute("endBlank", resultMap.get("endBlank"));
		model.addAttribute("totalTd", resultMap.get("totalTd"));
		
		return "calendar";
	}
}
