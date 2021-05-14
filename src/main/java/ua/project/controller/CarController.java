package ua.project.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
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
import ua.project.services.mapper.impl.CarMapperImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CarController {

    @Autowired
    CarService carService;
    @Autowired
    CarMapper carMapper;


    @GetMapping("/car")
    public String getCarList(Model model){
        List<Car> carList = carService.findAll();
        model.addAttribute("carList", carList);
        return "car/carList";
    }

    @GetMapping("/add-car")
    public String getAddCarPage(Model model){
        CarDto carDto = new CarDto();
        model.addAttribute("carType", ControllerUtils.getCarTypes());
        model.addAttribute("carStatus", ControllerUtils.getCarStatuses());
        model.addAttribute("car" , carDto);
        return "car/addCar";
    }
    @PostMapping("/add-car")
    public String addCar(@Valid CarDto carDto,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
          return "car/addCar";
        }
        carService.createCar(carDto);
        return "redirect:/car";
    }
    @GetMapping("/car/{car}")
    public String changeActivity(@PathVariable("car") Car car){
        carService.changeCarActivity(car);
        return "redirect:/car";
    }

    @GetMapping("/car/update-car/{car}")
    public String getUpdateCarPage(Model model,
                                   @PathVariable("car") Car car){
        CarDto carDto = carMapper.mapToDto(car);
        model.addAttribute("carDto" , carDto);
        model.addAttribute("carType", ControllerUtils.getCarTypes());
        model.addAttribute("carId", car.getId());
        return "car/updateCar";
    }
    @PostMapping("car/update-car/{car}")
    public String updateCar(@PathVariable("car") Car car,
                            CarDto carDto){
        carService.updateCar(carDto,car);
        return "redirect:/car";

    }
}
