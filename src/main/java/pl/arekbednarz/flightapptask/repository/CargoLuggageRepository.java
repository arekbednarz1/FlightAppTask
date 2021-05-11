package pl.arekbednarz.flightapptask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.arekbednarz.flightapptask.entity.cargo.CargoLuggage;

@Repository
public interface CargoLuggageRepository extends JpaRepository<CargoLuggage,Integer> {
}
