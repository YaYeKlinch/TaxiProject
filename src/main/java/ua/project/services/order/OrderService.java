package ua.project.services.order;

import ua.project.controller.dto.OrderDto;
import ua.project.entity.Car;
import ua.project.entity.User;

public interface OrderService {
    void createOrder(OrderDto orderDto , User user , Car car);
}
