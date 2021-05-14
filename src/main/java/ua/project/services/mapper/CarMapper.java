package ua.project.services.mapper;

import ua.project.controller.dto.CarDto;
import ua.project.entity.Car;

public interface CarMapper extends Mapper<Car, CarDto>{
    void updateCar(Car car , CarDto carDto);
}
