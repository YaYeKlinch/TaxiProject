package ua.project.services.mapper.impl;

import org.springframework.stereotype.Component;
import ua.project.controller.dto.CarDto;
import ua.project.entity.Car;
import ua.project.entity.enums.CarStatus;
import ua.project.services.mapper.CarMapper;

@Component
public class CarMapperImpl implements CarMapper {

    @Override
    public Car mapToEntity(CarDto carDto) {
        if(carDto== null){
            return null;
        }
        Car car = Car.builder()
                .name(carDto.getName())
                .photo(carDto.getPhoto())
                .capacity(carDto.getCapacity())
                .carStatus(CarStatus.INACTIVE)
                .carType(carDto.getCarType())
                .active(true)
                .build();
        return car;
    }

    public CarDto mapToDto(Car entity) {
        if(entity== null){
            return null;
        }
        CarDto carDto = CarDto.builder().name(entity.getName())
                .capacity(entity.getCapacity())
                .photo(entity.getPhoto())
                .carType(entity.getCarType())
                .build();
        return carDto;
    }

    @Override
    public void updateCar(Car car, CarDto carDto) {
        car.setCarType(carDto.getCarType());
        car.setCapacity(carDto.getCapacity());
        car.setName(carDto.getName());
        car.setPhoto(carDto.getPhoto());
    }
}
