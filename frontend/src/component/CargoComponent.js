import React from 'react';
import CargoService from '../services/CargoService';

class CargoComponent extends React.Component {

    constructor(){
        this.state = {
            cargo:[]
        }
    }

    componentDidMount(){
        CargoService.getCargo().then((response) => {
            this.setState({cargo: response.data})
        });
    
    }

    render (){
        return(
            <div>
                <h1 className="text-center">Cargo list</h1>
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <td></td>
                        </tr>
                    </thead>
                </table>

            </div>
        )
    }
}

export default CargoComponent;