package ua.project.services.order;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.project.controller.dto.OrderDto;
import ua.project.entity.Car;
import ua.project.entity.TaxiOrder;
import ua.project.entity.User;
import ua.project.entity.enums.CarStatus;
import ua.project.entity.statistic.OrderCarStatistic;
import ua.project.repos.CarRepository;
import ua.project.repos.OrderRepository;
import ua.project.services.mapper.OrderMapper;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    OrderRepository orderRepository;
    CarRepository carRepository;
    OrderMapper orderMapper;

    @Override
    @Transactional
    public TaxiOrder createOrder(OrderDto orderDto, User user, Car car) {
        logger.info("user" + user.getUsername() +"trying to make order on car with id " +car.getId() );
        TaxiOrder order = orderMapper.mapToEntity(orderDto);
        order.setCar(car);
        order.setUser(user);
        order.setDateTime(LocalDateTime.now());
        car.setCarStatus(CarStatus.IN_RACE);
        carRepository.save(car);
        return orderRepository.save(order);
    }

    @Override
    public Page<TaxiOrder> findAll(Optional<Integer> page, Optional<Integer> size, Sort sort) {
        logger.info("trying to find all orders");
        PageRequest pageRequest;
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
    public Page<TaxiOrder> findAllUsersOrders(Optional<Integer> page, Optional<Integer> size, Sort sort, User user) {
        logger.info("trying to find all orders for user " + user.getUsername());
        PageRequest pageRequest;
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
    public Page<TaxiOrder> findAllByUsersUsername(Optional<Integer> page, Optional<Integer> size, Sort sort, String username) {
        logger.info("trying to find all orders that make user with username LIKE " + username);
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

    @Override
    public Page<OrderCarStatistic> calculateStatistics(Optional<Integer> page, Optional<Integer> size) {
        logger.info("trying to calculate statistics for orders");
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        pageRequest = PageRequest.of(currentPage - 1 , sizeOfPage);
        return orderRepository.sumTotalDistanceAndCosts(pageRequest);
    }
}
