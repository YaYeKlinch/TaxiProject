package ua.project.controller.dto;

import lombok.Getter;
import lombok.Setter;
import ua.project.entity.enums.CarStatus;
import ua.project.entity.enums.CarType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CarDto {
    @NotNull(message = "Enter capacity")
    private int capacity;
    @NotBlank(message = "Enter name")
    private String name;
    @NotBlank(message = "Enter link to photo")
    private String photo;
    private CarType carType;
}
