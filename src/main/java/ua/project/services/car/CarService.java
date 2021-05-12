package ua.project.services.car;

import ua.project.controller.dto.CarDto;
import ua.project.entity.Car;

import java.util.List;

public interface CarService {

    List<Car> findAll();
    void createCar(CarDto carDto );
}
