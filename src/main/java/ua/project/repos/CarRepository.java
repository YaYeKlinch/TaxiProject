package ua.project.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.entity.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

}
