package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import simulation.Simulation;
import simulation.SimulationErrorException;
import simulation.StatsWatcher;

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
