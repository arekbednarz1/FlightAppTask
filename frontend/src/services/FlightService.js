import axios from 'axios';


const FLIGHT_REST_API_URL = 'http://localhost:8080/flight';

const CARGO_REST_API_URL = 'http://localhost:8080/cargo';


class FlightService {

    getFlight(){
       return axios.get(FLIGHT_REST_API_URL);
    }

    getCargo(){
        axios.get(CARGO_REST_API_URL);
    }
}

export default new FlightService();