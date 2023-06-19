package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.dto.dtoRestaurant.RestaurantResponse;
import peaksoft.entity.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
        boolean existsByName(String name);
}