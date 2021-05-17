package ua.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.project.controller.dto.CarDto;
import ua.project.entity.Car;
import ua.project.services.car.CarService;
import ua.project.services.mapper.CarMapper;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Controller
public class CarController {


    CarService carService;

    CarMapper carMapper;


    @GetMapping("/car")
    public String getCarList(Model model){
        List<Car> carList = carService.findAll();
        model.addAttribute("carList", carList);
        return "car/carList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add-car")
    public String getAddCarPage(Model model){
        CarDto carDto = new CarDto();
        model.addAttribute("carType", ControllerUtils.getCarTypes());
        model.addAttribute("carStatus", ControllerUtils.getCarStatuses());
        model.addAttribute("car" , carDto);
        return "car/addCar";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add-car")
    public String addCar(@Valid CarDto carDto,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
          return "car/addCar";
        }
        carService.createCar(carDto);
        return "redirect:/car";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/car/{car}")
    public String changeActivity(@PathVariable("car") Car car){
        carService.changeCarActivity(car);
        return "redirect:/car";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/car/update-car/{car}")
    public String getUpdateCarPage(Model model,
                                   @PathVariable("car") Car car){
        CarDto carDto = carMapper.mapToDto(car);
        model.addAttribute("carDto" , carDto);
        model.addAttribute("carType", ControllerUtils.getCarTypes());
        model.addAttribute("carId", car.getId());
        return "car/updateCar";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("car/update-car/{car}")
    public String updateCar(@PathVariable("car") Car car,
                            CarDto carDto){
        carService.updateCar(carDto,car);
        return "redirect:/car";

    }
}
