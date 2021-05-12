package ua.project.controller.dto;

import lombok.Getter;
import lombok.Setter;
import ua.project.entity.enums.CarStatus;
import ua.project.entity.enums.CarType;

@Getter
@Setter
public class CarDto {
    private int capacity;
    private String name;
    private String photo;
    private CarStatus carStatus;
    private CarType carType;
}
