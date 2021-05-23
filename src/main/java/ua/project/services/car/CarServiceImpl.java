package ua.project.services.car;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.project.controller.dto.CarDto;
import ua.project.entity.Car;
import ua.project.entity.enums.CarStatus;
import ua.project.entity.enums.CarType;
import ua.project.repos.CarRepository;
import ua.project.services.mapper.CarMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    
    CarRepository carRepository;
    
    CarMapper carMapper;

    @Override
    public Page<Car> findAll(Optional<Integer> page, Optional<Integer> size) {
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        pageRequest = PageRequest.of(currentPage -1, sizeOfPage);
        return carRepository.findAll(pageRequest);
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

    @Override
    public Page<Car> findAllActiveCars(Optional<Integer> page, Optional<Integer> size)
    {
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        pageRequest = PageRequest.of(currentPage -1, sizeOfPage);

        return carRepository.findCarsByActive(pageRequest, true);
    }

    @Override
    public Page<Car> findCarsByTypeAndCapacity(Optional<Integer> page, Optional<Integer> size, CarType carType, int capacity) {
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        pageRequest = PageRequest.of(currentPage -1, sizeOfPage);

        return carRepository.findCarsByCapacityAndCarTypeAAndActive(pageRequest, capacity, carType,true);
    }

}
