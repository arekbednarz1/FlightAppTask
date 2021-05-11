package pl.arekbednarz.flightapptask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.arekbednarz.flightapptask.entity.cargo.Baggage;

@Repository
public interface BaggageRepository extends JpaRepository<Baggage,Integer> {


}
