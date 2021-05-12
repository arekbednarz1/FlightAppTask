package pl.arekbednarz.flightapptask.exceptions;

public class IncorrectFlightException extends Exception{
    public IncorrectFlightException(String errorMessage){
        super(errorMessage);
    }

    public IncorrectFlightException() {

    }
}
