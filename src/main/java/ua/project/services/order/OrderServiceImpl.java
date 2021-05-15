package ua.project.services.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.project.controller.dto.OrderDto;
import ua.project.entity.Car;
import ua.project.entity.Order;
import ua.project.entity.User;
import ua.project.repos.OrderRepository;
import ua.project.services.mapper.OrderMapper;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    OrderRepository orderRepository;
    OrderMapper orderMapper;

    @Override
    public void createOrder(OrderDto orderDto, User user, Car car) {
        Order order = orderMapper.mapToEntity(orderDto);
        order.setCar(car);
        order.setUser(user);
        order.setDateTime(LocalDateTime.now());
        orderRepository.save(order);
    }
}
