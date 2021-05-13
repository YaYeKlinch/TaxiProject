package ua.project.services.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.project.controller.dto.CarDto;
import ua.project.entity.Car;
import ua.project.entity.enums.CarStatus;
import ua.project.repos.CarRepository;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository carRepository;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public void createCar(CarDto carDto) {
        Car car = Car.builder()
                .name(carDto.getName())
                .photo(carDto.getPhoto())
                .capacity(carDto.getCapacity())
                .carStatus(CarStatus.INACTIVE)
                .carType(carDto.getCarType())
                .active(true)
                .build();
        carRepository.save(car);
    }
}
