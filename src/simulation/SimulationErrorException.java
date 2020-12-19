package simulation;

public class SimulationErrorException extends Exception{
    public SimulationErrorException(String errorMessage, Simulation simulation) {
        super("Error in simulation " + simulation.getName() + errorMessage);
    }
}