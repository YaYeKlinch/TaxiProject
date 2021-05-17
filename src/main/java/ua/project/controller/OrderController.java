package ua.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.project.controller.dto.OrderDto;
import ua.project.entity.Car;
import ua.project.entity.Order;
import ua.project.entity.User;
import ua.project.services.order.OrderService;

import javax.validation.Valid;
import java.util.Optional;

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
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/orders")
    public String getOrdersPage(@RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                @RequestParam(value = "sort", required = false) String sortBy,
                                @RequestParam(value = "nameBy", required = false) String nameBy,
                                Model model){
        Sort sort = ControllerUtils.getSort(sortBy , nameBy , model);
        Page<Order> orders = orderService.findAll(page , size , sort);
        int totalPages = orders.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @GetMapping("/user-orders")
    public String getUsersOrdersPage(@RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                @RequestParam(value = "sort", required = false) String sortBy,
                                @RequestParam(value = "nameBy", required = false) String nameBy,
                                @AuthenticationPrincipal User user,
                                Model model){
        Sort sort = ControllerUtils.getSort(sortBy , nameBy , model);
        Page<Order> orders = orderService.findAllUsersOrders(page , size , sort, user);
        int totalPages = orders.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        model.addAttribute("orders", orders);
        return "order/userOrdersList";
    }
}
