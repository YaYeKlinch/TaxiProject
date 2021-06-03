package ua.project.entity;

import com.sun.istack.NotNull;
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

    @Column(nullable = false)
    @NotNull
    private String departure;

    @Column(nullable = false)
    @NotNull
    private String arrival;

    @Column(nullable = false)
    @NotNull
    private int costs;

    @Column(nullable = false)
    @NotNull
    private double distance;

    @Column(nullable = false)
    @NotNull
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
