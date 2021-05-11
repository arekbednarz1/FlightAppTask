package pl.arekbednarz.flightapptask.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.arekbednarz.flightapptask.entity.Flight;
import pl.arekbednarz.flightapptask.entity.enums.ArrivalAirportIATACode;
import pl.arekbednarz.flightapptask.entity.enums.DepartureAirportIATACode;
import pl.arekbednarz.flightapptask.service.FlightService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private FlightService flightService;


    public FlightController(FlightService flightService) {
        this.flightService = flightService;
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

    @GetMapping("arrival/{code}/{date}")
    public ResponseEntity<?> findAllFlightsArrivalByDate(@PathVariable("code")String code, @PathVariable("date")String date){
        ArrivalAirportIATACode arrivalAirportIATACode = ArrivalAirportIATACode.valueOf(code);
        List<Flight>allFlightInThisTimeFromThisCity= flightService.findAllByArrivalAndDepartureDate(arrivalAirportIATACode, date);
        int totalArrivals = allFlightInThisTimeFromThisCity.size();
        String flightNumbers =allFlightInThisTimeFromThisCity.stream()
                .map(f->String.valueOf(f.getFlightNumber()))
                .collect(Collectors.joining(", "));
        return ResponseEntity.ok("Total arrivals to "+code+" city: "+totalArrivals+ " flight numbers: "+flightNumbers);
    }


    @GetMapping("departure/{code}/{date}")
    public ResponseEntity <?> findAllFlightsDepartureByDate(@PathVariable("code")String code, @PathVariable("date")String date){
        DepartureAirportIATACode departureAirportIATACode = DepartureAirportIATACode.valueOf(code);
        List<Flight>allFlightInThisTimeInThisCity= flightService.findAllByDepartureAndAndDepartureDate(departureAirportIATACode, date);
        int totalDepartures =allFlightInThisTimeInThisCity.size();
        String flightNumbers =allFlightInThisTimeInThisCity.stream()
                .map(f->String.valueOf(f.getFlightNumber()))
                .collect(Collectors.joining(", "));
        return ResponseEntity.ok("Total departures to "+code+" city: "+totalDepartures+ " flight numbers: "+flightNumbers);

    }







}
