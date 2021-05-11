package pl.arekbednarz.flightapptask.service.implementation;

import org.springframework.stereotype.Service;
import pl.arekbednarz.flightapptask.entity.Cargo;
import pl.arekbednarz.flightapptask.entity.Flight;
import pl.arekbednarz.flightapptask.repository.BaggageRepository;
import pl.arekbednarz.flightapptask.repository.CargoLuggageRepository;
import pl.arekbednarz.flightapptask.repository.CargoRepository;
import pl.arekbednarz.flightapptask.service.CargoService;

import java.util.List;

@Service
public class CargoServiceImp implements CargoService {

    BaggageRepository baggageRepository;
    CargoLuggageRepository cargoLuggageRepository;
    CargoRepository cargoRepository;

    public CargoServiceImp(BaggageRepository baggageRepository, CargoLuggageRepository cargoLuggageRepository, CargoRepository cargoRepository) {
        this.baggageRepository = baggageRepository;
        this.cargoLuggageRepository = cargoLuggageRepository;
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

//    @Override
//    public String totalCargoWeightInFlight(Integer flightNumber, String date) {
//        return cargoRepository.totalCargoWeightInFlight(flightNumber,date);
//    }
//
//    @Override
//    public String totalWeightInFlight(Integer flightNumber, String date) {
//        return cargoRepository.totalWeightInFlight(flightNumber,date);
//    }
}
