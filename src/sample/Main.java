package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
        Simulation s1 = new Simulation("Symulacja",15,15,0.1,2,50,8,80);
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
        canvas.update();
        stage.show();
        Task<Integer> task = new Task<>() {
            @Override protected Integer call() throws Exception {
                int iterations;
                for (iterations = 0; iterations < 100000; iterations++) {
                    if (isCancelled()) {
                        break;
                    }
                    simulation.simulateOneDay();
                    canvas.update();
                    System.out.println("CANVAS UPDATE");
                    Thread.sleep(50);
                }
                return iterations;
            }
        };
        Thread th = new Thread(task);
        th.start();


    }

    public static void main(String[] args) {
        launch(args);
        // Simulation simulation = new Simulation("Sysad",3,3,0.3,5,100,8, 100);
        // simulation.start();
        //launch(args);
    }
}
