package pl.arekbednarz.flightapptask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
    public void addOne(@RequestBody Flight flight) throws ParseException {
        DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z",Locale.ENGLISH);
        Date date = inputFormat.parse(flight.getDepartureDate());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        flight.setDate(formatter.format(date));
        flightService.save(flight);
    }

    @GetMapping
    public ResponseEntity<List<Flight>> findAll(){
        return ResponseEntity.ok(flightService.findAll());
    }

    @GetMapping("/{code}/{date}")
    public ResponseEntity<?> findInformationByCityCodeAndDate(@PathVariable("code")String code, @PathVariable("date") String date) {
        ArrivalAirportIATACode arrivalAirportIATACode = ArrivalAirportIATACode.valueOf(code);
        DepartureAirportIATACode departureAirportIATACode = DepartureAirportIATACode.valueOf(code);

        List<Flight>allFlightInThisTimeToThisCity= flightService.findAllByArrivalAndDepartureDate(arrivalAirportIATACode, date);
        List<Flight>allFlightInThisTimeFromThisCity= flightService.findAllByDepartureAndAndDepartureDate(departureAirportIATACode, date);

        TreeMap<Integer,String> mapOfArrivalsAndFlightNumbers = getMapOfFlightsSumAndFlightsNumbers(allFlightInThisTimeToThisCity);
        TreeMap<Integer,String> mapOfDeparturesAndFlightNumbers = getMapOfFlightsSumAndFlightsNumbers(allFlightInThisTimeFromThisCity);

        List<Cargo> arrivalCargo = cargoService.allCargoWhereArrivalCityEquals(arrivalAirportIATACode);
        List<Cargo> departureCargo = cargoService.allCargoWhereDepartureCityEquals(departureAirportIATACode);

        String unit = arrivalCargo.get(0).getBaggage().stream().map(Baggage::getWeightUnit).findFirst().get().toString();

        int arrivalCargoSum =getCargoSum(arrivalCargo);

        int departureCargoSum = getCargoSum(departureCargo);

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode json = mapper.createObjectNode();
        json.put("Total arrivals in this day ",mapOfArrivalsAndFlightNumbers.firstEntry().getKey());
        json.put("Arriving flight numbers in this day",mapOfArrivalsAndFlightNumbers.firstEntry().getValue());
        json.put("Total departures in this day", mapOfDeparturesAndFlightNumbers.firstEntry().getKey());
        json.put("Departing flights numbers in this day"+code, mapOfDeparturesAndFlightNumbers.firstEntry().getValue());
        json.put("Cargo departure weight sum in this day", departureCargoSum+" "+unit);
        json.put("Cargo arrival weight sum in this day", arrivalCargoSum+" "+unit);

        return ResponseEntity.ok(json);
    }

    private int getCargoSum(List<Cargo> list) {
        return list.stream().map(c->c.getCargo().stream()
                .map(CargoLuggage::getPieces).mapToInt(Integer::intValue).sum() & c.getBaggage()
                .stream().map(Baggage::getPieces).mapToInt(Integer::intValue).sum()).mapToInt(Integer::intValue).sum();
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
