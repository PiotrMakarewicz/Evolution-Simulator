package sample;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simulation.Simulation;
import simulation.SimulationErrorException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Simulation s1 = ParametersLoader.load("parameters.json");
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
        canvas.drawBackground();
        canvas.update();
        stage.show();
        Task<Integer> task = new Task<>() {
            @Override protected Integer call() throws Exception {
                int iterations;
                for (iterations = 0; true; iterations++) {
                    if (isCancelled()) {
                        break;
                    }
                    System.out.println("Simulation");
                    simulation.simulateOneDay();
                    System.out.println("CANVAS UPDATE start");
                    canvas.update();
                    System.out.println("CANVAS UPDATE done");
                    Thread.sleep(100);
                }
                return iterations;
            }
        };
        Thread th = new Thread(task);
        th.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
