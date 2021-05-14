package ua.project.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
