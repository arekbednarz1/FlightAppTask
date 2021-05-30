import React from 'react';
import FlightService from '../services/FlightService';

class CargoComponent extends React.Component {

    constructor(){
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
                <h1 className="text-center">Flight list</h1>
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <td>id</td>
                            <td>baggageId</td>
                            <td>id</td>

                        </tr>
                    </thead>
                </table>

            </div>
        )
    }
}

export default CargoComponent;