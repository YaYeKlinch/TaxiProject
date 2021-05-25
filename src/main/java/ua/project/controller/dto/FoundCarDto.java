package ua.project.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import ua.project.entity.Car;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoundCarDto {
   private Page<Car> foundCars;
   private boolean alternate;
}
