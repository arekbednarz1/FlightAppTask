import React from 'react';
import  './flightForm.css';



const initialState = {
  flightNumber : '',
  departureAirportIATACode : '',
  arrivalAirportIATACode : '',
  departureDate : ''

}

function createFlight(data){
  return fetch('http://localhost:8080/flight', {
    method : 'POST',
    body : JSON.stringify(data),
    headers : {
      'Content-Type' : 'application/json'
    }
  }).then(response => {
    if(response.status >= 200 && response.status < 300){
      return response;
    }else{
      console.log('ERROR');
    }
  }).catch(err => err);
}


class AddFlightForm extends React.Component {
  
  
  constructor(props){
    super(props)
    this.state = initialState;
    this.onChange = this.onChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

  };

  onChange(e){
    if (e.target.id === 'flightNumber'){
      this.setState({flightNumber: e.target.value});
    }

    else if (e.target.id === 'departureAirportIATACode'){
      this.setState({departureAirportIATACode: e.target.value});
    }

    else if (e.target.id === 'arrivalAirportIATACode'){
      this.setState({arrivalAirportIATACode: e.target.value});
    }

    else if (e.target.id === 'departureDate'){
      this.setState({departureDate: e.target.value});
    }
    
  }

  handleSubmit(){
    const flight = {
      flightNumber : this.state.flightNumber,
      departureAirportIATACode : this.state.departureAirportIATACode,
      arrivalAirportIATACode : this.state.arrivalAirportIATACode,
      departureDate : this.state.departureDate

    }
    
    alert(flight.flightNumber);
    createFlight(flight);
    
  }
    render(){

    return(
    <form >
          <div className="input-container">
    <i class="fa fa-plane icon"></i>
    <input className="input-field" type="text" placeholder="Flight Number" id="flightNumber" onChange={this.onChange} value={this.state.value} />
  </div>

  <div className="input-container">
    <i className="fa fa-plane-departure icon"></i>
    <input className="input-field" type="text" placeholder="Departure" onChange={this.onChange} id="departureAirportIATACode"/>
  </div>

  <div className="input-container">
    <i className="fa fa-plane-arrival icon"></i>
    <input className="input-field" type="text" placeholder="Arrival" onChange={this.onChange} id="arrivalAirportIATACode"/>
  </div>

  <div className="input-container">
    <i className="fas fa-plane-departure icon"></i>
    <input className="input-field" type="text" placeholder="Departure" onChange={this.onChange} id="departureDate"/>
  </div>
  < button onClick={this.handleSubmit} className="btn">DODAJ</button>
</form>









)
}
}
export default AddFlightForm;