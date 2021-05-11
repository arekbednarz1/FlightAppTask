package pl.arekbednarz.flightapptask.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.arekbednarz.flightapptask.entity.Cargo;
import pl.arekbednarz.flightapptask.entity.Flight;
import pl.arekbednarz.flightapptask.entity.cargo.Baggage;
import pl.arekbednarz.flightapptask.entity.cargo.CargoLuggage;
import pl.arekbednarz.flightapptask.entity.enums.ArrivalAirportIATACode;
import pl.arekbednarz.flightapptask.entity.enums.DepartureAirportIATACode;
import pl.arekbednarz.flightapptask.service.CargoService;
import pl.arekbednarz.flightapptask.service.FlightService;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private FlightService flightService;
    private CargoService cargoService;

    public FlightController(FlightService flightService, CargoService cargoService) {
        this.flightService = flightService;
        this.cargoService = cargoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOne(@RequestBody Flight flight) {
        flightService.save(flight);
    }

    @GetMapping
    public ResponseEntity<List<Flight>> findAll(){
        return ResponseEntity.ok(flightService.findAll());
    }

    @GetMapping("/{code}/{date}")
    public ResponseEntity<?> findInformationByCityCodeAndDate(@PathVariable("code")String code, @PathVariable("date")String date) throws JSONException {
        ArrivalAirportIATACode arrivalAirportIATACode = ArrivalAirportIATACode.valueOf(code);
        DepartureAirportIATACode departureAirportIATACode = DepartureAirportIATACode.valueOf(code);

        List<Flight>allFlightInThisTimeToThisCity= flightService.findAllByArrivalAndDepartureDate(arrivalAirportIATACode, date);
        List<Flight>allFlightInThisTimeFromThisCity= flightService.findAllByDepartureAndAndDepartureDate(departureAirportIATACode, date);

        TreeMap<Integer,String> mapOfArrivalsAndFlightNumbers = getMapOfFlightsSumAndFlightsNumbers(allFlightInThisTimeToThisCity);
        TreeMap<Integer,String> mapOfDeparturesAndFlightNumbers = getMapOfFlightsSumAndFlightsNumbers(allFlightInThisTimeFromThisCity);

        List<Cargo> arrivalCargo = cargoService.allCargoWhereArrivalCityEquals(arrivalAirportIATACode);
        List<Cargo> departureCargo = cargoService.allCargoWhereDepartureCityEquals(departureAirportIATACode);

        int arrivalCargoSum =getCargoSum(arrivalCargo);

        int departureCargoSum = getCargoSum(departureCargo);

        String json = new JSONObject()
                .put("Total arrivals to "+code,mapOfArrivalsAndFlightNumbers.firstEntry().getKey())
                .put("Arriving flight numbers",mapOfArrivalsAndFlightNumbers.firstEntry().getValue())
                .put("Total departures to "+code, mapOfDeparturesAndFlightNumbers.firstEntry().getKey())
                .put("Departing flights numbers "+code, mapOfDeparturesAndFlightNumbers.firstEntry().getValue())
                .put("Cargo departure weight sum", departureCargoSum)
                .put("Cargo arrival weight sum", arrivalCargoSum)
                .toString();

        return ResponseEntity.ok(json);
    }

    private int getCargoSum(List<Cargo> list) {
        int sum = list.stream().map(c->c.getCargo().stream()
                .map(CargoLuggage::getPieces).mapToInt(Integer::intValue).sum() & c.getBaggage()
                .stream().map(Baggage::getPieces).mapToInt(Integer::intValue).sum()).mapToInt(Integer::intValue).sum();
        return sum;
    }

    private TreeMap<Integer,String> getMapOfFlightsSumAndFlightsNumbers(List<Flight> list) {
        int totalArrivals = list.size();
        String flightNumbers = list.stream()
                .map(f->String.valueOf(f.getFlightNumber()))
                .collect(Collectors.joining(", "));
        TreeMap <Integer,String> returnMap = new TreeMap<>();
        returnMap.put(totalArrivals,flightNumbers);
        return returnMap;
    }
}
