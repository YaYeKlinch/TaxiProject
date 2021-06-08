package ua.project.services.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ua.project.controller.dto.OrderDto;
import ua.project.entity.Car;
import ua.project.entity.TaxiOrder;
import ua.project.entity.User;
import ua.project.entity.statistic.OrderCarStatistic;

import java.util.Optional;

public interface OrderService {
    TaxiOrder createOrder(OrderDto orderDto , User user , Car car);
    Page<TaxiOrder> findAll(Optional<Integer> page, Optional<Integer> size, Sort sort);
    Page<TaxiOrder> findAllUsersOrders(Optional<Integer> page, Optional<Integer> size, Sort sort , User user);
    Page<TaxiOrder> findAllByUsersUsername(Optional<Integer> page, Optional<Integer> size, Sort sort , String username);
    Page<OrderCarStatistic> calculateStatistics(Optional<Integer> page, Optional<Integer> size);
}
