package ua.project.controller.dto.utility;

import ua.project.controller.dto.OrderDto;
import ua.project.controller.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DepartmentArrivalNotEqualValidator
        implements ConstraintValidator<DepartmentArrivalNotEqual, Object> {

    @Override
    public void initialize(DepartmentArrivalNotEqual constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        OrderDto order = (OrderDto) obj;
        return !order.getArrival().equals(order.getDeparture());
    }
}
