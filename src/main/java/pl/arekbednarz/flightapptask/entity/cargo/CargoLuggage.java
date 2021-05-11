package pl.arekbednarz.flightapptask.entity.cargo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.arekbednarz.flightapptask.entity.Cargo;
import pl.arekbednarz.flightapptask.entity.Flight;
import pl.arekbednarz.flightapptask.entity.enums.WeightUnit;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CargoLuggage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer weight;

    private WeightUnit weightUnit;

    private Integer pieces;

    @ManyToOne
    private Cargo cargo;


}
