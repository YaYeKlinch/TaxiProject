package ua.project.services.order;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.project.controller.dto.OrderDto;
import ua.project.entity.Car;
import ua.project.entity.Order;
import ua.project.entity.User;
import ua.project.repos.OrderRepository;
import ua.project.services.mapper.OrderMapper;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Override
    public Page<Order> findAll(Optional<Integer> page, Optional<Integer> size, Sort sort) {
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        if(sort==null){
            pageRequest = PageRequest.of(currentPage -1, sizeOfPage);
        }
        else {
            pageRequest = PageRequest.of(currentPage - 1 , sizeOfPage , sort);
        }
        return orderRepository.findAll(pageRequest);
    }

    @Override
    public Page<Order> findAllUsersOrders(Optional<Integer> page, Optional<Integer> size, Sort sort, User user) {
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        if(sort==null){
            pageRequest = PageRequest.of(currentPage -1, sizeOfPage);
        }
        else {
            pageRequest = PageRequest.of(currentPage - 1 , sizeOfPage , sort);
        }
        return orderRepository.findOrdersByUser_id(pageRequest , user.getId());
    }

    @Override
    public Page<Order> findAllByUsersUsername(Optional<Integer> page, Optional<Integer> size, Sort sort, String username) {
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        if(sort==null){
            pageRequest = PageRequest.of(currentPage -1, sizeOfPage);
        }
        else {
            pageRequest = PageRequest.of(currentPage - 1 , sizeOfPage , sort);
        }
        return orderRepository.findOrdersByUserUsernameContaining(pageRequest, username);
    }
}
