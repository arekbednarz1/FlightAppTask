package pl.arekbednarz.flightapptask.service.implementation;

import org.springframework.stereotype.Service;
import pl.arekbednarz.flightapptask.entity.Cargo;
import pl.arekbednarz.flightapptask.entity.enums.ArrivalAirportIATACode;
import pl.arekbednarz.flightapptask.entity.enums.DepartureAirportIATACode;
import pl.arekbednarz.flightapptask.repository.CargoRepository;
import pl.arekbednarz.flightapptask.service.CargoService;

import java.util.List;

@Service
public class CargoServiceImp implements CargoService {


    CargoRepository cargoRepository;

    public CargoServiceImp(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @Override
    public List<Cargo> findAll() {
        return cargoRepository.findAll();
    }

    @Override
    public void save(Cargo cargo) {
        cargoRepository.save(cargo);

    }

    @Override
    public List<Cargo> totalBaggageWeightInFlight(Integer flightNumber, String date) {
        return cargoRepository.totalBaggageWeightInFlight(flightNumber,date) ;
    }

    @Override
    public List<Cargo> allCargoWhereArrivalCityEquals(ArrivalAirportIATACode code) {
        return cargoRepository.allCargoWhereArrivalCityEquals(code);
    }

    @Override
    public List<Cargo> allCargoWhereDepartureCityEquals(DepartureAirportIATACode code) {
        return cargoRepository.allCargoWhereDepartureCityEquals(code);
    }


}
