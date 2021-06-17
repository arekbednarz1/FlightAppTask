import React from 'react';
import  './flightForm.css';
import FlightService from '../../services/FlightService';
import FlatButton from '@material-ui/core/Button/Button'


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
      console.log(response);
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


      
     




//     return (
//       <div>
//           <Dialog title={messages.todo_add || 'todo_add'} actions={actions} modal={false} open={todoDialog.open} onRequestClose={handleClose}>
//               <form>
//                   <TextField ref="todoText1" onChange={this.onChange} id='title' hintText={messages.todo_hint1 || 'todo_hint'} errorText={todoDialog.errorText} floatingLabelText={messages.todo_label1 || 'todo_label1'} fullWidth={true} />
//                   <TextField ref="todoText2" onChange={this.onChange} id='desc' hintText={messages.todo_hint2 || 'todo_hint'} errorText={todoDialog.errorText} floatingLabelText={messages.todo_label2 || 'todo_label2'} fullWidth={true} multiLine={true} rows={1} rowsMax={3} />
//                   <TextField ref="todoText3" onChange={this.onChange} id='type' hintText={messages.todo_hint3 || 'todo_hint'} 
//                   errorText={todoDialog.errorText} floatingLabelText={messages.todo_label3 || 'todo_label3'} fullWidth={true} />
//                   <RaisedButton label='ADD Photo' style={styles.button} labelPosition="before" containerElement="label"><input type='file' onChange={this.onChange} id='image' style={styles.exampleImageInput} /></RaisedButton>
//               </form>
//           </Dialog>
//       </div>
//   )
// }
// }









    return(
    <form >
          <div class="input-container">
    <i class="fa fa-plane icon"></i>
    <input className="input-field" type="text" placeholder="Flight Number" id="flightNumber" onChange={this.onChange} value={this.state.value} onChange={this.handleChange}/>
  </div>

  <div class="input-container">
    <i class="fa fa-plane-departure icon"></i>
    <input className="input-field" type="text" placeholder="Departure" onChange={this.onChange} id="departureAirportIATACode"/>
  </div>

  <div class="input-container">
    <i class="fa fa-plane-arrival icon"></i>
    <input className="input-field" type="text" placeholder="Arrival" onChange={this.onChange} id="arrivalAirportIATACode"/>
  </div>

  <div class="input-container">
    <i class="fas fa-plane-departure icon"></i>
    <input className="input-field" type="text" placeholder="Departure" onChange={this.onChange} id="departureDate"/>
  </div>
  < button onClick={this.handleSubmit} className="btn"/>
</form>

)
}
}
export default AddFlightForm;