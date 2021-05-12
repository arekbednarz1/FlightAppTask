package pl.arekbednarz.flightapptask.service.implementation;

import org.springframework.stereotype.Service;
import pl.arekbednarz.flightapptask.entity.Cargo;
import pl.arekbednarz.flightapptask.entity.Flight;
import pl.arekbednarz.flightapptask.entity.enums.ArrivalAirportIATACode;
import pl.arekbednarz.flightapptask.entity.enums.DepartureAirportIATACode;
import pl.arekbednarz.flightapptask.repository.FlightRepository;
import pl.arekbednarz.flightapptask.service.FlightService;

import java.util.List;

@Service
public class FlightServiceImp implements FlightService {

    private FlightRepository flightRepository;

    public FlightServiceImp(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public void save(Flight flight) {
        flightRepository.save(flight);
    }

    @Override
    public List<Flight> findAllByDepartureAndAndDepartureDate(DepartureAirportIATACode code, String date) {
        return flightRepository.findAllByDepartureAirportIATACodeAndAndDepartureDate(code, date);
    }

    @Override
    public List<Flight> findAllByArrivalAndDepartureDate(ArrivalAirportIATACode code, String date) {
        return flightRepository.findAllByArrivalAirportIATACodeAndDepartureDate(code, date);
    }

    @Override
    public Flight findByFlightId(Integer id) {
        return flightRepository.findByFlightId(id);
    }

    @Override
    public void update(Integer id, Cargo cargo) {
         flightRepository.update(id, cargo);
    }

    @Override
    public Flight findByFlightNumber(int flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }
}
