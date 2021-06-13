import React from 'react';
import  './flightForm.css';
import FlightService from '../../services/FlightService';

class AddFlightForm extends React.Component {


  constructor(props){
  super(props)
  this.state = {
    newFlight:[]
  }
  }



    render(){

    return(
    <form >
          <h2>Register Form</h2>
          <div class="input-container">
    <i class="fa fa-plane icon"></i>
    <input class="input-field" type="text" placeholder="Flight Number" name="number"/>
  </div>

  <div class="input-container">
    <i class="fa fa-plane-departure icon"></i>
    <input class="input-field" type="password" placeholder="Departure" name="psw"/>
  </div>

  <div class="input-container">
    <i class="fa fa-plane-arrival icon"></i>
    <input class="input-field" type="text" placeholder="Arrival" name="email"/>
  </div>

  <div class="input-container">
    <i class="fas fa-plane-departure icon"></i>
    <input class="input-field" type="date" placeholder="Departure" name="psw"/>
  </div>

  <div class="input-container">
    <i class="fas fa-plane-departure icon"></i>
    <input class="input-field" type="datetime-local" placeholder="Departure" name="psw"/>
  </div>

  <button type="submit" class="btn">Register</button>
</form>

)
}

}export default AddFlightForm;