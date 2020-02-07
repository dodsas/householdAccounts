package org.ysnam.householdAccounts.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.ysnam.householdAccounts.core.model.ItemConditionMonthly;
import org.ysnam.householdAccounts.core.model.ItemRepository;
import org.ysnam.householdAccounts.core.model.StatisticsData;
import org.ysnam.householdAccounts.web.data.MainPage;
import org.ysnam.householdAccounts.web.pagination.PaginationInfo;
import org.ysnam.householdAccounts.web.pagination.PaginationServiceMonthly;
import org.ysnam.householdAccounts.web.pagination.enums.Move;
import org.ysnam.householdAccounts.web.util.DateUtils;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class HouseholdMonthly {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    PaginationServiceMonthly paginationServiceMonthly;

    private static List<String> yearList;

    @PostConstruct
    public void init(){
        yearList = itemRepository.getListOfyyyy();
    }

    @RequestMapping(value = "monthly", method = {RequestMethod.POST, RequestMethod.GET})
    public String householdAccountMonth(
            @ModelAttribute(name="mainPage") MainPage mainPage,
            @RequestParam(name="currentPage", required = false) Integer currentPage,
            @RequestParam(name="moveIndicator", required = false) Move moveIndicator,
            @RequestParam(name="startPage", required = false) Integer startPage,
            @RequestParam(name="lastPage", required = false) Integer lastPage,
            @RequestParam(name="year", required = false) String year,
            @RequestParam(name="month", required = false) String month,
            Model model
    ){
        PaginationInfo paginationInfo;

        year = StringUtils.isEmpty(year) ? DateUtils.getCurrentYear() : year;
        month = StringUtils.isEmpty(month) ? DateUtils.getCurrentMonth() : month;

        ItemConditionMonthly itemConditionMonthly = new ItemConditionMonthly();
        itemConditionMonthly.setYear(year);
        itemConditionMonthly.setMonth(month);
        paginationInfo = paginationServiceMonthly.doPagination(currentPage, moveIndicator, startPage, lastPage, itemConditionMonthly);

        model.addAttribute("yearList", yearList);
        model.addAttribute("householdInfo", paginationInfo);
        model.addAttribute("activeMonthList", true);
        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedMonth", month);
        model.addAttribute("callbackUrlOnlyPath", "/householdAccount/monthly");
        model.addAttribute("callbackUrl", "/householdAccount/monthly?year="+year+"&month="+month);
        model.addAttribute("allMonth", DateUtils.getAllMonth());
        return "householdAccountMonth";
    }

    @RequestMapping(value = "monthly/statistics", method = {RequestMethod.POST, RequestMethod.GET})
    public String householdAccountMonthlyStatistics(
            @ModelAttribute(name="mainPage") MainPage mainPage,
            @RequestParam(name="year", required = false) String year,
            @RequestParam(name="month", required = false) String month,
            Model model
    ) throws ParseException {
        year = StringUtils.isEmpty(year) ? DateUtils.getCurrentYear() : year;
        month = StringUtils.isEmpty(month) ? DateUtils.getCurrentMonth() : month;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        List<StatisticsData> dataList = itemRepository.findItemSum(
                simpleDateFormat.parse(year + "/" + month + "/" + "01"),
                simpleDateFormat.parse(year + "/" + month + "/" + "31"));

        model.addAttribute("yearList", yearList);
        model.addAttribute("activeMonthStatistics", true);
        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedMonth", month);
        model.addAttribute("dataList", dataList);
        model.addAttribute("callbackUrlOnlyPath", "/householdAccount/monthly/statistics");
        model.addAttribute("callbackUrl", "/householdAccount/monthly/statistics?year="+year+"&month="+month);
        model.addAttribute("allMonth", DateUtils.getAllMonth());
        return "householdAccountMonthStatistics";
    }
}

