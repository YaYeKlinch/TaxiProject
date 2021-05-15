package ua.project.controller.dto;

import com.sun.istack.NotNull;
import lombok.*;

import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private static final double COST_PER_METER = 2;
    private static final double START_PRICE = 4000;
    @NotNull
    private String departure;
    @NotNull
    private String arrival;
    public double calculateDistance(){
        Random random = new Random();
        return random.nextInt(10000)+100;
    }
    public int calculateCosts(double distance){
        return (int) (START_PRICE + distance*COST_PER_METER);
    }
}
