package org.ysnam.householdAccounts.web.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.ysnam.householdAccounts.core.model.ItemRepository;
import org.ysnam.householdAccounts.web.pagination.enums.Move;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginationService <T, Condition, Finder extends Findable<T, Condition>>{

    private static int dataPageSize = 6;
    private static int paginationSize = 6;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    Finder finder;

    private int totalPage(){

        return (int) Math.ceil(finder.count() / (double)dataPageSize);
    }

    public PaginationInfo initPage(Condition condition){

        PaginationInfo<T> paginationInfo = new PaginationInfo<>();

        int page = 0;
        int currentPage = 0;

        Page<T> itemList = createItemList(page, condition);

        int totalSize = itemList.getTotalPages();
        int lastPage = paginationSize > totalSize ? totalSize : paginationSize;
        List<PageInfo> pageList = createPageList(currentPage, lastPage, currentPage);

        paginationInfo.setPageList(pageList);
        paginationInfo.setItemList(itemList);

        setHsaPageInfo(0, lastPage, paginationInfo);

        return paginationInfo;
    }

    private Page<T> createItemList(int page, Condition condition) {
        Pageable pageable = PageRequest.of(page, dataPageSize, Sort.by("Date").descending());
        return finder.findAll(pageable, condition);
    }

    public PaginationInfo currPage(Integer startPage, Integer lastPage, Integer currentPage, Condition condition){

        PaginationInfo<T> paginationInfo = new PaginationInfo<>();

        paginationInfo.setItemList(createItemList(currentPage, condition));
        paginationInfo.setPageList(createPageList(startPage, lastPage + 1, currentPage));

        setHsaPageInfo(startPage, lastPage, paginationInfo);

        return paginationInfo;
    }

    private void setHsaPageInfo(Integer startPage, Integer lastPage, PaginationInfo paginationInfo) {
        int totalPage = paginationInfo.getItemList().getTotalPages();
        if (startPage - paginationSize < 0) {
            paginationInfo.setHasPrevPage(false);
        }
        if (lastPage + 1 >= totalPage) {
            paginationInfo.setHasNextPage(false);
        }
    }

    public PaginationInfo nextPage(Integer startPage, Integer lastPage, Condition condition) {

        startPage = lastPage + 1;
        return movePage(startPage, Move.Next, condition);
    }

    public PaginationInfo prevPage(Integer startPage, Integer lastPage, Condition condition) {

        startPage = startPage - paginationSize < 0
                ? paginationSize - startPage
                : startPage - paginationSize;

        return movePage(startPage, Move.Prev, condition);
    }

    private PaginationInfo movePage(Integer startPage, Move moveIndicator, Condition condition) {
        int lastPage;
        PaginationInfo<T> paginationInfo = new PaginationInfo<>();

        paginationInfo.setItemList(createItemList(startPage, condition));

        int totalPage = paginationInfo.getItemList().getTotalPages();
        lastPage = startPage + paginationSize;
        lastPage = lastPage > totalPage ? totalPage : lastPage;


        int currentPage = moveIndicator == Move.Prev ? lastPage - 1 : startPage;
        paginationInfo.setPageList(createPageList(startPage, lastPage, currentPage));

        setHsaPageInfo(startPage, lastPage, paginationInfo);

        return paginationInfo;
    }

    private List<PageInfo> createPageList(Integer startPage, Integer lastPage, Integer currentPage) {
        List<PageInfo> pageInfoList = new ArrayList<>();

        for (int i = startPage; i < lastPage; i++) {
            PageInfo pageInfo = new PageInfo(i);
            if(i == currentPage){
                pageInfo.active();
            }
            pageInfoList.add(pageInfo);
        }

        return pageInfoList;
    }

    public PaginationInfo doPagination(Integer currentPage, Move moveIndicator, Integer startPage, Integer lastPage, Condition condition) {
        PaginationInfo paginationInfo;
        if(moveIndicator == null){
            paginationInfo = initPage(condition);
        }
        else {
            // Page
            switch (moveIndicator) {
                case Next:
                    paginationInfo = nextPage(startPage, lastPage, condition);
                    break;

                case Prev:
                    paginationInfo = prevPage(startPage, lastPage, condition);
                    break;

                default:
                    paginationInfo = currPage(startPage, lastPage, currentPage, condition);
                    break;
            }
        }
        return paginationInfo;
    }
}
