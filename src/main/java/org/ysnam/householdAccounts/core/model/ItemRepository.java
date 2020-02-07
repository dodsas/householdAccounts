package org.ysnam.householdAccounts.core.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.ysnam.householdAccounts.core.model.StatisticsData;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, ItemKey>, JpaSpecificationExecutor<Item> {

    @Query(value = "select yyyy from household_account group by yyyy order by yyyy desc", nativeQuery = true)
    List<String> getListOfyyyy();

    @Override
    Page<Item> findAll(Pageable pageable);

    @Override
    Page<Item> findAll(Specification<Item> spec, Pageable pageable);

    @Query("SELECT NEW org.ysnam.householdAccounts.core.model.StatisticsData(v.merchantType , SUM(v.amount)) " +
            "FROM org.ysnam.householdAccounts.core.model.Item v "+
            "WHERE v.date>=:startDate and v.date<=:endDate " +
            "GROUP BY v.merchantType "
    )
    List<StatisticsData> findItemSum(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
