package ua.project.services.car;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.project.controller.dto.CarDto;
import ua.project.entity.Car;
import ua.project.entity.enums.CarStatus;
import ua.project.entity.enums.CarType;
import ua.project.repos.CarRepository;
import ua.project.services.mapper.CarMapper;
import ua.project.services.order.OrderServiceImpl;


import java.util.Optional;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private static final Logger logger = LogManager.getLogger(CarServiceImpl.class);
    CarRepository carRepository;
    CarMapper carMapper;

    @Override
    public Page<Car> findAll(Optional<Integer> page, Optional<Integer> size) {
        logger.info("trying to find all cars");
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        pageRequest = PageRequest.of(currentPage -1, sizeOfPage);
        return carRepository.findAll(pageRequest);
    }

    @Override
    public void createCar(CarDto carDto) {
        logger.info("trying to create car orders with name" + carDto.getName()
                + " capacity " + carDto.getCapacity() + " photo " + carDto.getPhoto());
        Car car = carMapper.mapToEntity(carDto);
        carRepository.save(car);
    }

    @Override
    public void changeCarActivity(Car car) {
        logger.info("trying to change activity for car with id " + car.getId());
        car.setActive(!car.isActive());
        carRepository.save(car);
    }

    @Override
    public void updateCar(CarDto carDto, Car car) {
        logger.info("trying to update car with id " + car.getId());
        carMapper.updateCar(car , carDto);
        carRepository.save(car);
    }

    @Override
    public Page<Car> findAllActiveCars(Optional<Integer> page, Optional<Integer> size)
    {
        logger.info("trying to find all active cars");
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        pageRequest = PageRequest.of(currentPage -1, sizeOfPage);

        return carRepository.findCarsByActive(pageRequest, true);
    }

    @Override
    public Page<Car> findCarsByTypeAndCapacity(Optional<Integer> page, Optional<Integer> size, CarType carType, int capacity) {
        logger.info("trying to find all active cars with type " + carType.name() +
                " and capacity " + capacity);
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        pageRequest = PageRequest.of(currentPage -1, sizeOfPage);

        return carRepository.findCarsByCapacityAndCarTypeAndActiveAndCarStatus(pageRequest, capacity, carType,true, CarStatus.READY);
    }

    @Override
    public void changeCarStatus(Car car, CarStatus carStatus) {
        logger.info("trying to change status for car with id" + car.getId());
        car.setCarStatus(carStatus);
        carRepository.save(car);
    }

    @Override
    public Page<Car> findCarsByCapacity(Optional<Integer> page, Optional<Integer> size, int capacity) {
        logger.info("trying to find all active cars with  capacity " + capacity);
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        pageRequest = PageRequest.of(currentPage -1, sizeOfPage);
        return carRepository.findCarsByCapacityAndActiveAndCarStatus(pageRequest, capacity , true , CarStatus.READY);
    }

}
