import React from 'react';
import FlightService from '../services/FlightService';

class CargoComponent extends React.Component {

    constructor(){
        this.state = {
            cargo:[]
        }
    }

    componentDidMount(){
        FlightService.getCargo().then((response) => {
            this.setState({cargo: response.data})
        });
    
    }

    render (){
        return(
            <div>
                <h2 className="text-center">Cargo list</h2>
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