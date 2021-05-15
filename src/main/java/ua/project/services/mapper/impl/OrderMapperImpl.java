package ua.project.services.mapper.impl;

import ua.project.controller.dto.OrderDto;
import ua.project.entity.Order;
import ua.project.services.mapper.OrderMapper;

public class OrderMapperImpl implements OrderMapper {
    @Override
    public Order mapToEntity(OrderDto dto) {
        if(dto==null){
            return null;
        }
        double distance = dto.calculateDistance();
        Order order = Order.builder().departure(dto.getDeparture())
                .arrival(dto.getArrival())
                .costs(dto.calculateCosts(distance))
                .distance(distance)
                .build();
        return order;
    }
    @Override
    public OrderDto mapToDto(Order entity) {
        return null;
    }
}
