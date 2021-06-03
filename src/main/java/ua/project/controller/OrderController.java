package ua.project.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import ua.project.entity.TaxiOrder;
import ua.project.entity.User;
import ua.project.entity.enums.CarStatus;
import ua.project.entity.statistic.OrderCarStatistic;
import ua.project.services.order.OrderService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class OrderController {
    private static final Logger logger = (Logger) LogManager.getLogger(OrderController.class);
    OrderService orderService;

    @GetMapping("/car/make-order/{car}")
    public String getOrderPage(Model model,
                               @PathVariable Car car){
        logger.debug("requested /car/make-order/"+car.getId() +"get method");
        if(!car.getCarStatus().equals(CarStatus.READY)){
            logger.debug("car with id "+car.getId() +" doesnt have status ready , redirect to /cars");
            return "redirect:/cars";
        }
        OrderDto orderDto = new OrderDto();
        model.addAttribute("order", orderDto);
        model.addAttribute("carId", car.getId());
        logger.debug("returning addOrder.html to user");
        return "order/addOrder";
    }
    @PostMapping("/car/make-order/{car}")
    public String makeOrder(@PathVariable("car") Car car,
                            @AuthenticationPrincipal User user,
                            @Valid OrderDto orderDto,
                            BindingResult bindingResult,
                            Model model){
        if(bindingResult.hasErrors()){
            logger.debug("orderDto has errors , returning to addOrder.html");
            model.addAttribute("order", orderDto);
            return "order/addOrder";
        }
        orderService.createOrder(orderDto, user, car);
        logger.debug("returning cars.html to user");
        return "redirect:/cars";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/orders")
    public String getOrdersPage(@RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                @RequestParam(value = "sort", required = false) String sortBy,
                                @RequestParam(value = "nameBy", required = false) String nameBy,
                                @RequestParam(value = "username", required = false) String username,
                                Model model){
        logger.debug("requested /orders get method");
        Sort sort = ControllerUtils.getSort(sortBy , nameBy , model);
        if (username==null){
            username = "";
        }
        model.addAttribute("username", username);
      //  String searchField = ControllerUtils.getSearchField(username, model);
        Page<TaxiOrder> orders = orderService.findAllByUsersUsername(page , size , sort,username);
        int totalPages = orders.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        model.addAttribute("orders", orders);
        logger.debug("returning orderList.html to user");
        return "order/orderList";
    }

    @GetMapping("/user-orders")
    public String getUsersOrdersPage(@RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                @RequestParam(value = "sort", required = false) String sortBy,
                                @RequestParam(value = "nameBy", required = false) String nameBy,
                                @AuthenticationPrincipal User user,
                                Model model){
        logger.debug("requested /user-orders get method");
        Sort sort = ControllerUtils.getSort(sortBy , nameBy , model);
        Page<TaxiOrder> orders = orderService.findAllUsersOrders(page , size , sort, user);
        int totalPages = orders.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        model.addAttribute("orders", orders);
        logger.debug("returning userOrdersList.html to user");
        return "order/userOrdersList";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/statistics")
    public String getStatisticsPage(@RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size,
                                    Model model){
        logger.debug("requested /statistics get method");
        Page<OrderCarStatistic> statistics= orderService.calculateStatistics(page,size);
        int totalPages = statistics.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        model.addAttribute("statistics", statistics);
        logger.debug("returning statistics.html to user");
        return "/order/statistics";
    }
}
