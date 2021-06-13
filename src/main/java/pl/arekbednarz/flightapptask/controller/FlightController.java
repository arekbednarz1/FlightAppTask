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
import pl.arekbednarz.flightapptask.exceptions.IncorrectFlightException;
import pl.arekbednarz.flightapptask.service.CargoService;
import pl.arekbednarz.flightapptask.service.FlightService;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/flight")
public class FlightController {

    private FlightService flightService;
    private CargoService cargoService;

    public FlightController(FlightService flightService, CargoService cargoService) {
        this.flightService = flightService;
        this.cargoService = cargoService;
    }


    @GetMapping("/ports")
    public ResponseEntity<List<ArrivalAirportIATACode>> allPorts() {
        return ResponseEntity.ok(Arrays.stream(ArrivalAirportIATACode.values()).collect(Collectors.toList()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOne(@RequestBody Flight flight) throws ParseException, IncorrectFlightException {
        if (flight.getFlightNumber()<1000 || flight.getFlightNumber()>9999){
            throw new IncorrectFlightException();
        }
        DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
        Date date = inputFormat.parse(flight.getDepartureDate());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        flight.setDate(formatter.format(date));
        flightService.save(flight);


    }

    @GetMapping
    public ResponseEntity<List<Flight>> findAll() {
        return ResponseEntity.ok(flightService.findAll());
    }

    @GetMapping("/{code}/{date}")
    public ResponseEntity<?> findInformationByCityCodeAndDate(@PathVariable("code") String code, @PathVariable("date") String date) throws IndexOutOfBoundsException {
        ArrivalAirportIATACode arrivalAirportIATACode = ArrivalAirportIATACode.valueOf(code);
        DepartureAirportIATACode departureAirportIATACode = DepartureAirportIATACode.valueOf(code);

        List<Flight> allFlightInThisTimeToThisCity = flightService.findAllByArrivalAndDepartureDate(arrivalAirportIATACode, date);
        List<Flight> allFlightInThisTimeFromThisCity = flightService.findAllByDepartureAndAndDepartureDate(departureAirportIATACode, date);

        if (allFlightInThisTimeFromThisCity==null && allFlightInThisTimeToThisCity==null){
            throw new IndexOutOfBoundsException();
        }
        TreeMap<Integer, String> mapOfArrivalsAndFlightNumbers = getMapOfFlightsSumAndFlightsNumbers(allFlightInThisTimeToThisCity);
        TreeMap<Integer, String> mapOfDeparturesAndFlightNumbers = getMapOfFlightsSumAndFlightsNumbers(allFlightInThisTimeFromThisCity);

        getIntegerStringTreeMap(mapOfArrivalsAndFlightNumbers);
        getIntegerStringTreeMap(mapOfDeparturesAndFlightNumbers);



        List<Cargo> arrivalCargo = cargoService.allCargoWhereArrivalCityEquals(arrivalAirportIATACode);
        List<Cargo> departureCargo = cargoService.allCargoWhereDepartureCityEquals(departureAirportIATACode);

        String unit = arrivalCargo.get(0).getBaggage().stream().map(Baggage::getWeightUnit).findFirst().get().toString();

        int arrivalCargoSum = getCargoSum(arrivalCargo);

        int departureCargoSum = getCargoSum(departureCargo);





        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("Total arrivals in this day ", mapOfArrivalsAndFlightNumbers.firstEntry().getKey());
        json.put("Arriving flight numbers in this day", mapOfArrivalsAndFlightNumbers.firstEntry().getValue());
        json.put("Total departures in this day", mapOfDeparturesAndFlightNumbers.firstEntry().getKey());
        json.put("Departing flights numbers in this day" + code, mapOfDeparturesAndFlightNumbers.firstEntry().getValue());
        json.put("Cargo departure weight sum in this day", departureCargoSum + " " + unit);
        json.put("Cargo arrival weight sum in this day", arrivalCargoSum + " " + unit);

        return ResponseEntity.ok(json);
    }




    private void getIntegerStringTreeMap(TreeMap<Integer, String> map) {
        if (map==null){
            map = new TreeMap<>();
            map.put(0,"0");
        }
    }


    private int getCargoSum(List<Cargo> list) {
        return list.stream().map(c -> c.getCargo().stream()
                .map(CargoLuggage::getPieces).mapToInt(Integer::intValue).sum() & c.getBaggage()
                .stream().map(Baggage::getPieces).mapToInt(Integer::intValue).sum()).mapToInt(Integer::intValue).sum();
    }



    private TreeMap<Integer, String> getMapOfFlightsSumAndFlightsNumbers(List<Flight> list) {
        int totalArrivals = list.size();
        String flightNumbers = list.stream()
                .map(f -> String.valueOf(f.getFlightNumber()))
                .collect(Collectors.joining(", "));
        TreeMap<Integer, String> returnMap = new TreeMap<>();
        returnMap.put(totalArrivals, flightNumbers);
        return returnMap;
    }






//    HANDLERS



    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleSQLException(SQLException e) {
        return new ResponseEntity<>("Flight with that number exist", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IndexOutOfBoundsException e) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("Flight with that preferences not exists","request mapping: flight/code/date");
        json.put("City code","3 big letters");
        json.put("Date pattern","dd-MM-yyyy");



        return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(IncorrectFlightException.class)
    public ResponseEntity<?> toShortOrToLongNumber(IncorrectFlightException e) {
        return new ResponseEntity<>("Wrong number, only 4-digits number", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}