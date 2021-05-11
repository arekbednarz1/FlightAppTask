package pl.arekbednarz.flightapptask.service;

import pl.arekbednarz.flightapptask.entity.Cargo;
import pl.arekbednarz.flightapptask.entity.Flight;

import java.util.List;

public interface CargoService {


    List<Cargo> findAll();

    void save(Cargo cargo);

    List<Cargo> totalBaggageWeightInFlight(Integer flightNumber, String date);

//    String totalCargoWeightInFlight(Integer flightNumber, String date);
//
//    String totalWeightInFlight(Integer flightNumber, String date);

}
