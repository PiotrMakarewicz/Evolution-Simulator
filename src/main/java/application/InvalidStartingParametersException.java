package application;

public class InvalidStartingParametersException extends Exception {
    public InvalidStartingParametersException(String errorMessage){
        super(errorMessage);
    }
}
