package ua.project.services.mapper.impl;

import org.springframework.stereotype.Component;
import ua.project.controller.dto.OrderDto;
import ua.project.entity.TaxiOrder;
import ua.project.services.mapper.OrderMapper;

@Component
public class OrderMapperImpl implements OrderMapper {
    @Override
    public TaxiOrder mapToEntity(OrderDto dto) {
        if(dto==null){
            return null;
        }
        double distance = dto.calculateDistance();
        TaxiOrder order = TaxiOrder.builder().departure(dto.getDeparture())
                .arrival(dto.getArrival())
                .costs(dto.calculateCosts(distance))
                .distance(distance)
                .build();
        return order;
    }
    @Override
    public OrderDto mapToDto(TaxiOrder entity) {
        return null;
    }
}
