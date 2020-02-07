package org.ysnam.householdAccounts.web.pagination;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.ysnam.householdAccounts.core.model.Item;

import java.util.List;

@Data
public class PaginationInfo<T> {

    private Page<T> itemList;
    private List<PageInfo> pageList;
    private boolean hasNextPage;
    private boolean hasPrevPage;

    public void setItemList(Page<T> itemList){
        this.itemList = itemList;
        hasNextPage = itemList.hasNext();
        hasPrevPage = itemList.hasPrevious();
    }

    public Integer startPage(){
        if(pageList.isEmpty()){
            return 0;
        }
        return pageList.get(0).getNumber();
    }

    public Integer lastPage(){
        if(pageList.isEmpty()){
            return 0;
        }
        return pageList.get(pageList.size() - 1).getNumber();
    }
}
