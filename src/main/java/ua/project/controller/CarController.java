package ua.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.project.controller.dto.CarDto;
import ua.project.entity.Car;
import ua.project.services.car.CarService;
import ua.project.services.mapper.CarMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class CarController {


    CarService carService;

    CarMapper carMapper;

    @GetMapping("/cars")
    public String getActiveCarsList(@RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size, Model model){
        Page<Car> cars = carService.findAllActiveCars(page, size);
        model.addAttribute("cars", cars);
        int totalPages = cars.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        return "car/activeCarList";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all-cars")
    public String getAllCarsList(@RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size, Model model){
        Page<Car> cars = carService.findAll(page, size);
        model.addAttribute("carList", cars);
        int totalPages = cars.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
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
        return "redirect:/all-cars";
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
        return "redirect:/all-cars";

    }
}
