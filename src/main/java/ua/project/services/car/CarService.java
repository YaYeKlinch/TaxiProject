package ua.project.services.car;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.project.controller.dto.CarDto;
import ua.project.entity.Car;
import ua.project.entity.enums.CarStatus;
import ua.project.entity.enums.CarType;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Page<Car> findAll(Optional<Integer> page, Optional<Integer> size);
    void createCar(CarDto carDto );
    void changeCarActivity(Car car);
    void updateCar(CarDto carDto ,Car car);
    Page<Car> findAllActiveCars(Optional<Integer> page, Optional<Integer> size);
    Page<Car> findCarsByTypeAndCapacity(Optional<Integer> page, Optional<Integer> size, CarType carType,int capacity);
    void changeCarStatus(Car car , CarStatus carStatus);
}
