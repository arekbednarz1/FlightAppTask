package pl.arekbednarz.flightapptask.controller;


import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.arekbednarz.flightapptask.entity.Cargo;
import pl.arekbednarz.flightapptask.entity.cargo.Baggage;
import pl.arekbednarz.flightapptask.entity.cargo.CargoLuggage;
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
    public void addOne(@RequestBody Cargo cargo) {
       cargo.setFlight(flightService.findByFlightId(cargo.getFlightId()));
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
    public ResponseEntity<?> findInformationByNumberAndDate(@PathVariable("number")Integer number, @PathVariable("date")String date) throws JSONException {
        List<Cargo> totalBaggageWeightInFlyList = cargoService.totalBaggageWeightInFlight(number,date);
        String unitBaggage = totalBaggageWeightInFlyList.get(0).getBaggage().stream().map(Baggage::getWeightUnit).findFirst().get().toString();
        int totalBaggage = totalBaggageWeightInFlyList.stream()
                .map(cargo -> cargo.getBaggage()
                        .stream().map(Baggage::getWeight)
                        .mapToInt(Integer::intValue).sum())
                .mapToInt(Integer::intValue).sum();

        String unitCargo = totalBaggageWeightInFlyList.get(0).getCargo().stream().map(CargoLuggage::getWeightUnit).findFirst().get().toString();

        int totalCargo = totalBaggageWeightInFlyList.stream()
                .map(cargo -> cargo.getCargo()
                        .stream().map(CargoLuggage::getWeight)
                        .mapToInt(Integer::intValue).sum())
                .mapToInt(Integer::intValue).sum();

        int totalWeight = totalBaggage+totalCargo;

        String json = new JSONObject()
                .put("Total baggage in this flight",totalBaggage+" "+unitBaggage)
                .put("Total cargo in this ",totalCargo+" "+unitCargo)
                .put("Total weight ", totalWeight + " " + unitBaggage)
                .toString();


        return ResponseEntity.ok(json);

    }

}
