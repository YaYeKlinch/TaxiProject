package ua.project.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ua.project.entity.enums.CarType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CarDto {
    @NotNull(message = "Enter capacity")
    @Min(0)
    private int capacity;

    @NotBlank(message = "Enter name")
    private String name;

    @NotBlank(message = "Enter link to photo")
    private String photo;

    private CarType carType;
    public CarDto(){}

    @Builder
    public CarDto(@NotNull(message = "Enter capacity") int capacity, @NotBlank(message = "Enter name") String name, @NotBlank(message = "Enter link to photo") String photo, CarType carType) {
        this.capacity = capacity;
        this.name = name;
        this.photo = photo;
        this.carType = carType;
    }
}
