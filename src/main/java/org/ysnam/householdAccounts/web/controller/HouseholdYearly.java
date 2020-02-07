package org.ysnam.householdAccounts.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.ysnam.householdAccounts.core.model.ItemConditionYearly;
import org.ysnam.householdAccounts.core.model.ItemRepository;
import org.ysnam.householdAccounts.web.data.MainPage;
import org.ysnam.householdAccounts.web.pagination.PaginationInfo;
import org.ysnam.householdAccounts.web.pagination.PaginationServiceYearly;
import org.ysnam.householdAccounts.web.pagination.enums.Move;

import javax.annotation.PostConstruct;

@Controller
public class HouseholdYearly {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    PaginationServiceYearly paginationServiceYearly;

    @PostConstruct
    public void init(){
    }

    @RequestMapping(value = {"", "yearly"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String householdAccountYear(
            @ModelAttribute(name="mainPage") MainPage mainPage,
            @RequestParam(name="currentPage", required = false) Integer currentPage,
            @RequestParam(name="moveIndicator", required = false) Move moveIndicator,
            @RequestParam(name="startPage", required = false) Integer startPage,
            @RequestParam(name="lastPage", required = false) Integer lastPage,
            Model model
    ){

        PaginationInfo paginationInfo;
        ItemConditionYearly itemConditionYearly = new ItemConditionYearly();
        paginationInfo = paginationServiceYearly.doPagination(currentPage, moveIndicator, startPage, lastPage, itemConditionYearly);

        model.addAttribute("callbackUrl", "yearly");
        model.addAttribute("householdInfo", paginationInfo);
        model.addAttribute("activeYearList", true);

        return "householdAccountYear";
    }


}

