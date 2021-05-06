package ua.project.entity;

import lombok.Getter;
import lombok.Setter;
import ua.project.entity.enums.CarStatus;
import ua.project.entity.enums.CarType;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int capacity;

    private String photo;

    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;

    @Enumerated(EnumType.STRING)
    private CarType carType;



}
