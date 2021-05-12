package pl.arekbednarz.flightapptask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.arekbednarz.flightapptask.entity.Cargo;
import pl.arekbednarz.flightapptask.entity.Flight;
import pl.arekbednarz.flightapptask.entity.enums.ArrivalAirportIATACode;
import pl.arekbednarz.flightapptask.entity.enums.DepartureAirportIATACode;

import java.util.Date;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {


    @Query("SELECT f from Flight f where f.arrivalAirportIATACode= ?1 and f.date= ?2")
    List<Flight> findAllByArrivalAirportIATACodeAndDepartureDate(ArrivalAirportIATACode code, String date);

    @Query("SELECT f from Flight f where f.departureAirportIATACode= ?1 and f.date= ?2")
    List<Flight> findAllByDepartureAirportIATACodeAndAndDepartureDate(DepartureAirportIATACode code, String date);


    @Transactional
    @Modifying
    @Query("UPDATE Flight f set f.cargo = ?2 where f.flightId= ?1")
    void update(Integer id,Cargo cargo);

    Flight findByFlightId(Integer id);

    Flight findByFlightNumber(int flightNumber);
}
