package ua.project.controller;

import ua.project.entity.enums.CarStatus;
import ua.project.entity.enums.CarType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerUtils {
    public static List<String> getCarTypes(){
        return Stream.of(CarType.values())
                .map(CarType::name)
                .collect(Collectors.toList());
    }
    public static List<String> getCarStatuses(){
        return Stream.of(CarStatus.values())
                .map(CarStatus::name)
                .collect(Collectors.toList());
    }
}
