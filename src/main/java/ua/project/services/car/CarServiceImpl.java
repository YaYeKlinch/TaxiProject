package ua.project.services.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.project.controller.dto.CarDto;
import ua.project.entity.Car;
import ua.project.entity.enums.CarStatus;
import ua.project.repos.CarRepository;
import ua.project.services.mapper.CarMapper;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository carRepository;
    @Autowired
    CarMapper carMapper;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public void createCar(CarDto carDto) {
        Car car = carMapper.mapToEntity(carDto);
        carRepository.save(car);
    }

    @Override
    public void changeCarActivity(Car car) {
        car.setActive(!car.isActive());
        carRepository.save(car);
    }

    @Override
    public void updateCar(CarDto carDto, Car car) {
        carMapper.updateCar(car , carDto);
            carRepository.save(car);
    }

}
