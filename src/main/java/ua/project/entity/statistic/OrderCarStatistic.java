package ua.project.entity.statistic;

import lombok.*;
import ua.project.entity.Car;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderCarStatistic {
    private Car car;
    private long CostsSum;
    private double distanceSum;

    public OrderCarStatistic(double distanceSum) {
        this.distanceSum = distanceSum;
    }
}
