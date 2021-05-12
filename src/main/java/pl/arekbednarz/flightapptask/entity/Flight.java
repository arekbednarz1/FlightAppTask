package pl.arekbednarz.flightapptask.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Size;
import pl.arekbednarz.flightapptask.entity.enums.ArrivalAirportIATACode;
import pl.arekbednarz.flightapptask.entity.enums.DepartureAirportIATACode;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flightId;

    @Column(unique = true)
    @Size(min = 1000,max = 9999)
    private Integer flightNumber;

    private DepartureAirportIATACode departureAirportIATACode;

    private ArrivalAirportIATACode arrivalAirportIATACode;

    private String date;

    private String departureDate;

    @OneToOne
    private Cargo cargo;


    public Flight(Integer flightId, Integer flightNumber, DepartureAirportIATACode departure, ArrivalAirportIATACode arrival, String departureDate) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureAirportIATACode = departure;
        this.arrivalAirportIATACode = arrival;
        this.departureDate = departureDate;
    }
}
