import axios from 'axios';


const FLIGHT_REST_API_URL = 'http://localhost:8080/flight';


class FlightService {

    getFlight(){
        axios.get(FLIGHT_REST_API_URL);
    }
}

export default new FlightService();