package ua.project.services.order;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.project.repos.OrderRepository;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    OrderRepository orderRepository;


}
