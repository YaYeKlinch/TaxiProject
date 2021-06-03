package ua.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ua.project.entity.enums.CarStatus;
import ua.project.entity.enums.CarType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "car")
@EqualsAndHashCode
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private int capacity;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private String photo;

    @Column(nullable = false)
    @NotNull
    private boolean active;

    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;

    @Enumerated(EnumType.STRING)
    private CarType carType;

    @JsonIgnore
    @OneToMany(mappedBy = "car" , fetch = FetchType.EAGER)
    private Set<TaxiOrder> orders;

    public Car() {
    }
    @Builder
    Car(int capacity , String name, String photo, CarStatus carStatus, CarType carType, boolean active){
        this.capacity = capacity;
        this.name=name;
        this.carStatus = carStatus;
        this.carType = carType;
        this.photo = photo;
        this.active= active;
    }



}
