package pl.arekbednarz.flightapptask.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.arekbednarz.flightapptask.entity.enums.ArrivalAirportIATACode;
import pl.arekbednarz.flightapptask.entity.enums.DepartureAirportIATACode;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private Integer flightNumber;

    private DepartureAirportIATACode departureAirportIATACode;

    private ArrivalAirportIATACode arrivalAirportIATACode;

    private String date;

    private String departureDate;

    @OneToOne
    private Cargo cargo;


    public Flight(Integer flightId, Integer flightNumber, DepartureAirportIATACode departure, ArrivalAirportIATACode arrival, String departureDate) throws ParseException {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureAirportIATACode = departure;
        this.arrivalAirportIATACode = arrival;
        this.departureDate = departureDate;
    }
}
