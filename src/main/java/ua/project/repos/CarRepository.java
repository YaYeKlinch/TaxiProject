package ua.project.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.project.entity.Car;
import ua.project.entity.enums.CarStatus;
import ua.project.entity.enums.CarType;

public interface CarRepository extends PagingAndSortingRepository<Car, Long> {

    Page<Car> findAll(Pageable pageable);
    Page<Car> findCarsByActive(Pageable pageable, boolean active);
    Page<Car> findCarsByCapacityAndCarTypeAndActiveAndCarStatus(Pageable pageable, int capacity, CarType carType, boolean active, CarStatus carStatus);
    Page<Car> findCarsByCapacityAndActiveAndCarStatus(Pageable pageable, int capacity,boolean active, CarStatus carStatus);
}
