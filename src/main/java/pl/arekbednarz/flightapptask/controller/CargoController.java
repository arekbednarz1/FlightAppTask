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
import pl.arekbednarz.flightapptask.exceptions.IncorrectFlightException;
import pl.arekbednarz.flightapptask.service.CargoService;
import pl.arekbednarz.flightapptask.service.FlightService;

import java.util.List;

@RestController
@RequestMapping("/cargo")
public class CargoController {

    CargoService cargoService;
    FlightService flightService;

    public CargoController(CargoService cargoService, FlightService flightService) {
        this.cargoService = cargoService;
        this.flightService = flightService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOne(@RequestBody Cargo cargo) throws IncorrectFlightException {
        cargo.setFlight(flightService.findByFlightId(cargo.getFlightId()));
        if (cargo.getFlight()==null){
            throw new IncorrectFlightException();
        }
        cargo.getCargo().forEach(cargoLuggage -> cargoLuggage.setCargo(cargo));
        cargo.getBaggage().forEach(baggage -> baggage.setCargo(cargo));
        cargoService.save(cargo);
        flightService.update(cargo.getFlightId(),cargo);
    }

    @GetMapping
    public ResponseEntity<List<Cargo>> findAll(){
        return ResponseEntity.ok(cargoService.findAll());
    }

    @GetMapping("/{number}/{date}")
    public ResponseEntity<?> findInformationByNumberAndDate(@PathVariable("number")Integer number, @PathVariable("date")String date) throws IllegalArgumentException{
        Flight flight = flightService.findByFlightNumber(number);
        if (flight==null){
           throw new IllegalArgumentException();
        }
        String unitBaggage = "-";
        int totalBaggage = 0;
        String unitCargo = "-";
        int totalCargo = 0;
        List<Cargo> totalBaggageWeightInFlyList = cargoService.totalBaggageWeightInFlight(number,date);
        if (totalBaggageWeightInFlyList.size()>0) {

            unitBaggage = totalBaggageWeightInFlyList.get(0).getBaggage().stream()
                    .map(Baggage::getWeightUnit)
                    .findFirst().get().toString();

            totalBaggage = totalBaggageWeightInFlyList.stream()
                    .map(cargo -> cargo.getBaggage()
                            .stream().map(Baggage::getWeight)
                            .mapToInt(Integer::intValue).sum())
                    .mapToInt(Integer::intValue).sum();

            unitCargo = totalBaggageWeightInFlyList.get(0).getCargo().stream()
                    .map(CargoLuggage::getWeightUnit)
                    .findFirst().get().toString();

            totalCargo = totalBaggageWeightInFlyList.stream()
                    .map(cargo -> cargo.getCargo()
                            .stream().map(CargoLuggage::getWeight)
                            .mapToInt(Integer::intValue).sum())
                    .mapToInt(Integer::intValue).sum();
        }

        int totalWeight = totalBaggage+totalCargo;

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode json = mapper.createObjectNode();
        json.put("Total baggage in this flight",totalBaggage+" "+unitBaggage);
        json.put("Total cargo in this flight",totalCargo+" "+unitCargo);
        json.put("Total weight ", totalWeight + " " + unitBaggage);
        return ResponseEntity.ok(json);

    }



//HANDLERS


    @ExceptionHandler(IncorrectFlightException.class)
    public ResponseEntity<?> handleSQLException(IncorrectFlightException e) {
        return new ResponseEntity<>("Flight with that number not exist", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("Flight with that preferences not exists","request mapping: cargo/number/date");
        json.put("Flight number","4 digits");
        json.put("Date pattern","dd-MM-yyyy");


        return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
