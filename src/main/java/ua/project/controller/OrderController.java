package ua.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.project.controller.dto.OrderDto;
import ua.project.entity.Car;
import ua.project.entity.User;
import ua.project.services.order.OrderService;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class OrderController {
    OrderService orderService;

    @GetMapping("/car/make-order/{car}")
    public String getOrderPage(Model model,
                               @PathVariable Car car){
        OrderDto orderDto = new OrderDto();
        model.addAttribute("order", orderDto);
        model.addAttribute("carId", car.getId());
        return "order/addOrder";
    }
    @PostMapping("/car/make-order/{car}")
    public String makeOrder(@PathVariable("car") Car car,
                            @AuthenticationPrincipal User user,
                            @Valid OrderDto orderDto,
                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "order/addOrder";
        }
        orderService.createOrder(orderDto, user, car);
        return "redirect:/car";
    }
}
