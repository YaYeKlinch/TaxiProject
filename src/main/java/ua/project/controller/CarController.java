package ua.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.project.entity.Car;
import ua.project.services.car.CarService;

import java.util.List;

@Controller
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping("/car")
    public String getCarList(Model model){
        List<Car> carList = carService.findAll();
        model.addAttribute("carList", carList);
        return "car/carList";
    }
}
