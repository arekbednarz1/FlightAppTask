package pl.arekbednarz.flightapptask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.arekbednarz.flightapptask.entity.Cargo;
import pl.arekbednarz.flightapptask.entity.enums.ArrivalAirportIATACode;
import pl.arekbednarz.flightapptask.entity.enums.DepartureAirportIATACode;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {


    @Query("select c from Cargo c where c.flight.flightNumber=?1 and c.flight.date=?2 ")
    List<Cargo> totalBaggageWeightInFlight(Integer flightNumber, String date);

    @Query("select c from Cargo c where c.flight.arrivalAirportIATACode=?1")
    List<Cargo> allCargoWhereArrivalCityEquals(ArrivalAirportIATACode code);


    @Query("select c from Cargo c where c.flight.departureAirportIATACode=?1")
    List<Cargo> allCargoWhereDepartureCityEquals(DepartureAirportIATACode code);


}
