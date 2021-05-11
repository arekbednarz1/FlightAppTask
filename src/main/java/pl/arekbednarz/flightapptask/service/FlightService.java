package pl.arekbednarz.flightapptask.service;

import pl.arekbednarz.flightapptask.entity.Cargo;
import pl.arekbednarz.flightapptask.entity.Flight;
import pl.arekbednarz.flightapptask.entity.enums.ArrivalAirportIATACode;
import pl.arekbednarz.flightapptask.entity.enums.DepartureAirportIATACode;


import java.util.List;

public interface FlightService {

    List<Flight> findAll();

    void save(Flight flight);

    List<Flight> findAllByDepartureAndAndDepartureDate(DepartureAirportIATACode code, String date);

    List<Flight> findAllByArrivalAndDepartureDate(ArrivalAirportIATACode code, String date);

    Flight findByFlightId(Integer id);

    void update(Integer id, Cargo cargo);

}
