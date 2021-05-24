package ua.project.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ua.project.entity.TaxiOrder;
import ua.project.entity.statistic.OrderCarStatistic;

import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<TaxiOrder, Long> {

    Page<TaxiOrder> findAll(Pageable pageable);
    Page<TaxiOrder> findOrdersByUser_id(Pageable pageable, @Param("user_id") Long id);
    Page<TaxiOrder> findOrdersByUserUsernameContaining(Pageable pageable, String username);
//    @Query(value = "SELECT new " +
//            "ua.project.entity.statistic.OrderCarStatistic(TaxiOrder.car, " +
//            "SUM(TaxiOrder.costs), SUM(TaxiOrder.distance)) " +
//            "FROM TaxiOrder " +
//            "GROUP BY TaxiOrder.car" )
    @Query(value = "SELECT new " +
            "ua.project.entity.statistic.OrderCarStatistic(t.car, SUM(t.costs), SUM(t.distance)) " +
            "FROM TaxiOrder as t " +
            "GROUP BY t.car" )
    Page<OrderCarStatistic> sumTotalDistanceAndCosts(Pageable pageable);

}
