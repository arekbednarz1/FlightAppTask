package pl.arekbednarz.flightapptask.service;

import pl.arekbednarz.flightapptask.entity.Cargo;
import pl.arekbednarz.flightapptask.entity.enums.ArrivalAirportIATACode;
import pl.arekbednarz.flightapptask.entity.enums.DepartureAirportIATACode;

import java.util.List;

public interface CargoService {


    List<Cargo> findAll();

    void save(Cargo cargo);

    List<Cargo> totalBaggageWeightInFlight(Integer flightNumber, String date);

    List<Cargo> allCargoWhereArrivalCityEquals(ArrivalAirportIATACode code);

    List<Cargo> allCargoWhereDepartureCityEquals(DepartureAirportIATACode code);



}
