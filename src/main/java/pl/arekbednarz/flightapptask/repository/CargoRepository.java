package pl.arekbednarz.flightapptask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.arekbednarz.flightapptask.entity.Cargo;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {


    @Query("select c from Cargo c where c.flight.flightNumber=?1 and c.flight.departureDate=?2 ")
    List<Cargo> totalBaggageWeightInFlight(Integer flightNumber, String date);

//    @Query("select c from Cargo c where c.flight.flightNumber=?1 and c.flight.departureDate=?2 ")
//    List<Cargo> totalCargoWeightInFlight(Integer flightNumber, String date);
//
//    String totalWeightInFlight(Integer flightNumber, String date);





}
