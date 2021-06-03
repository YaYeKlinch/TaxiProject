package ua.project.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.project.controller.dto.CarDto;
import ua.project.controller.dto.FoundCarDto;
import ua.project.entity.Car;
import ua.project.entity.enums.CarStatus;
import ua.project.entity.enums.CarType;
import ua.project.services.car.CarService;
import ua.project.services.mapper.CarMapper;


import javax.validation.Valid;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class CarController {
    private static final Logger logger = (Logger) LogManager.getLogger(CarController.class);
    CarService carService;
    CarMapper carMapper;

    @GetMapping("/cars")
    public String getActiveCarsList(@RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size, Model model){
        logger.debug("requested /cars get method");
        Page<Car> cars = carService.findAllActiveCars(page, size);
        model.addAttribute("cars", cars);
        int totalPages = cars.getTotalPages();
        model.addAttribute("carStatus", ControllerUtils.getCarStatuses());
        ControllerUtils.pageNumberCounts(totalPages , model);
        logger.debug("returning activeCarList.html to user");
        return "car/activeCarList";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all-cars")
    public String getAllCarsList(@RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size, Model model){
        logger.debug("requested /all-cars get method");
        Page<Car> cars = carService.findAll(page, size);
        model.addAttribute("carList", cars);
        int totalPages = cars.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        logger.debug("returning carList.html to user");
        return "car/carList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add-car")
    public String getAddCarPage(Model model){
        logger.debug("requested /add-car get method");
        CarDto carDto = new CarDto();
        model.addAttribute("carType", ControllerUtils.getCarTypes());
        model.addAttribute("carStatus", ControllerUtils.getCarStatuses());
        model.addAttribute("car" , carDto);
        logger.debug("returning addCar.html to user");
        return "car/addCar";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add-car")
    public String addCar(@Valid CarDto carDto,
                         BindingResult bindingResult,
                         Model model){
        if(bindingResult.hasErrors()){
            logger.debug("carDto has errors , returning to addCar.html");
            model.addAttribute("carType", ControllerUtils.getCarTypes());
            model.addAttribute("car" , carDto);
          return "car/addCar";
        }
        carService.createCar(carDto);
        logger.debug("returning cars.html to user");
        return "redirect:/cars";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/car/{car}")
    public String changeActivity(@PathVariable("car") Car car){
        logger.debug("requested /car/" + car.getId() + " get method");
        carService.changeCarActivity(car);
        logger.debug("returning cars.html to user");
        return "redirect:/all-cars";
    }

    @GetMapping("/car/change-status/{car}")
    public String changeCarStatus(@PathVariable("car") Car car,
                CarStatus carStatus){
        logger.debug("requested /car/change-status/" + car.getId() + " get method");
        carService.changeCarStatus(car,carStatus);
        logger.debug("returning cars.html to user");
        return "redirect:/cars";
        }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/car/update-car/{car}")
    public String getUpdateCarPage(Model model,
                                   @PathVariable("car") Car car){
        logger.debug("requested /car/update-car/" + car.getId() + " get method");
        CarDto carDto = carMapper.mapToDto(car);
        model.addAttribute("carDto" , carDto);
        model.addAttribute("carType", ControllerUtils.getCarTypes());
        model.addAttribute("carId", car.getId());
        logger.debug("returning updateCar.html to user");
        return "car/updateCar";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("car/update-car/{car}")
    public String updateCar(@PathVariable("car") Car car,
                            @Valid CarDto carDto,
                            BindingResult bindingResult,
                            Model model){
        if(bindingResult.hasErrors()){
            logger.debug("carDto has errors , returning to updateCar.html");
            model.addAttribute("carType", ControllerUtils.getCarTypes());
            model.addAttribute("car" , carDto);
            return "car/updateCar";
        }
        carService.updateCar(carDto,car);
        logger.debug("returning carList.html to user");
        return "redirect:/all-cars";

    }
    @GetMapping("/find-car")
    public String getFindCarPage(Model model){
        logger.debug("requested /find-car get method");
        model.addAttribute("carType", ControllerUtils.getCarTypes());
        logger.debug("returning findCar.html to user");
        return "car/findCar";
    }

    @ResponseBody
    @RequestMapping(value = "/find-car-filter",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<FoundCarDto> findCars(@RequestParam("page") Optional<Integer> page,
                                             @RequestParam("size") Optional<Integer> size,
                                             @RequestParam("capacity")int capacity,
                                             @RequestParam("carType")CarType carType
                                    ){

        Page<Car> cars = carService.findCarsByTypeAndCapacity(page ,size , carType,capacity);
        if(cars.hasContent()){
            logger.debug("returning foundCarDto list to user");
            return new ResponseEntity<>(new FoundCarDto(cars, false), HttpStatus.OK);
        }

        Page<Car> alternateCars = carService.findCarsByCapacity(page,size,capacity);
        logger.debug("returning alternate foundCarDto list to user");
        return new ResponseEntity<>(new FoundCarDto(alternateCars, true), HttpStatus.OK);
    }
}
