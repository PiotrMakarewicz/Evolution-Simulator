package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        SimulationThread t1 = new SimulationThread(ParametersLoader.load("parameters.json"));
        SimulationThread t2 = new SimulationThread(ParametersLoader.load("parameters.json"));
        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
