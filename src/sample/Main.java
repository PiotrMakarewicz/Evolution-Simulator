package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simulation.Simulation;
import simulation.SimulationErrorException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Simulation s1 = new Simulation("Symulacja",50,50,0.2,12,22,13,200);
        displaySimulation(s1,stage);
    }

    public void displaySimulation(Simulation simulation, Stage stage) throws InterruptedException, SimulationErrorException {
        Group root = new Group();
        double width = simulation.getWidth()*10;
        double height = simulation.getHeight()*10;
        Scene simulationScene = new Scene(root, width, height, Color.BLACK);

        final SimulationCanvas canvas = new SimulationCanvas(simulation);

        root.getChildren().add(canvas);
        stage.setScene(simulationScene);
        stage.setResizable(false);
        simulation.start();
        stage.show();

        for (int i = 0; i<100; i++){
            canvas.update();
            Thread.sleep(1000);
            simulation.simulateOneDay();
        }

    }

    public static void main(String[] args) {
        launch(args);
        // Simulation simulation = new Simulation("Sysad",3,3,0.3,5,100,8, 100);
        // simulation.start();
        //launch(args);
    }
}
