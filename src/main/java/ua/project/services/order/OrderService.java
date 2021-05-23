package ua.project.services.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ua.project.controller.dto.OrderDto;
import ua.project.entity.Car;
import ua.project.entity.Order;
import ua.project.entity.User;

import java.util.Optional;

public interface OrderService {
    void createOrder(OrderDto orderDto , User user , Car car);
    Page<Order> findAll(Optional<Integer> page, Optional<Integer> size, Sort sort);
    Page<Order> findAllUsersOrders(Optional<Integer> page, Optional<Integer> size, Sort sort , User user);
    Page<Order> findAllByUsersUsername(Optional<Integer> page, Optional<Integer> size, Sort sort , String username);
}
