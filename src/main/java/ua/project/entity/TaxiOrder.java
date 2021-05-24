package ua.project.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "taxi_order")
@NoArgsConstructor
public class TaxiOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String departure;
    private String arrival;
    private int costs;
    private double distance;
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="car_id", nullable=false)
    private Car car;

    @Builder
    public TaxiOrder(String departure , String arrival, int costs , double distance){
        this.arrival = arrival;
        this.departure = departure;
        this.costs = costs;
        this.distance = distance;
    }
}
