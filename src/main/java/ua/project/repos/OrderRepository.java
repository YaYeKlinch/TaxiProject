package ua.project.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ua.project.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    Page<Order> findAll(Pageable pageable);
    Page<Order> findOrdersByUser_id(Pageable pageable, @Param("user_id") Long id);
    Page<Order> findOrdersByUserUsernameContaining(Pageable pageable, String username);
}
