import React from 'react';
import FlightService from '../services/FlightService';

class FlightComponent extends React.Component {

    constructor(props){
        super(props)
        this.state = {
            flights:[]
        }
    }

    componentDidMount(){
        FlightService.getFlight().then((response) => {
            this.setState({flights: response.data})
        });
    
    }

    render (){
        return(
            <div>
                <h3 className="text-center">Flight list</h3>
            <div className="row">
                <table className="table table-striped table bordered">
                    <thead>
                        <tr>
                            <td>id</td>
                            <td>Flight number</td>
                            <td>Departure Ariport</td>
                            <td>Arrival Airport</td>
                            <td>Date</td>
                            <td>Departure Date</td>
                            <td>Cargo id</td>
                            <td>Details</td>

                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.flights.map(
                                flight =>
                                <tr key = {flight.flightId}>
                                    <td>{flight.flightId}</td>
                                    <td>{flight.flightNumber}</td>
                                    <td>{flight.departureAirportIATACode}</td>
                                    <td>{flight.arrivalAirportIATACode}</td>
                                    <td>{flight.date}</td>
                                    <td>{flight.departureDate}</td>
                                    <td>{flight.cargo}</td>
                                    <td></td>
                                </tr>
                            )
                        }
                    </tbody>
                
                </table>
                </div>
            </div>
        )
    }
}

export default FlightComponent;