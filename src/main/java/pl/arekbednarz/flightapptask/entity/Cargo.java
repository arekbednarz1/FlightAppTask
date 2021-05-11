package pl.arekbednarz.flightapptask.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.arekbednarz.flightapptask.entity.cargo.Baggage;
import pl.arekbednarz.flightapptask.entity.cargo.CargoLuggage;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "cargo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Baggage> baggage;

    @OneToMany(mappedBy = "cargo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CargoLuggage> cargo;

    @OneToOne
    @NotNull
    private Flight flight;

    @Transient
    private Integer flightId;


    public Cargo(Integer id, List<Baggage> baggage, List<CargoLuggage> cargo, Integer flightId) {
        this.id = id;
        this.baggage = baggage;
        this.cargo = cargo;
        this.flightId = flightId;
    }
}
