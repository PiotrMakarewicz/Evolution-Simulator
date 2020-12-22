package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simulation.Simulation;
import simulation.StatsWatcher;

public class SimulationThread extends Thread{
    private final Stage stage = new Stage();
    private final Simulation simulation;
    public void run() {
        Platform.runLater(this::displaySimulation);
    }
    public SimulationThread(Simulation simulation){
        this.simulation = simulation;
    }
    public void displaySimulation() {
        final Group root = new Group();
        final double width = simulation.getWidth()*10+400;
        final double height = Math.max(simulation.getHeight()*10,400);
        final Scene simulationScene = new Scene(root, width, height, Color.BLACK);

        final StatsWatcher statsWatcher = new StatsWatcher(simulation);
        final SimulationCanvas canvas = new SimulationCanvas(simulation);
        canvas.setLayoutX(400);
        final Label statsLabel = new Label();
        statsLabel.setTextFill(Color.web("#FFFFFF"));
        statsLabel.setPadding(new Insets(5.0));

        root.getChildren().add(statsLabel);
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
                    simulation.simulateOneDay();
                    System.out.println("CANVAS UPDATE start");

                    Platform.runLater(canvas::update);
                    Platform.runLater(()->{statsLabel.setText(statsWatcher.getSummary());});

                    Thread.sleep(100);
                }
                return iterations;
            }
        };
        Thread th = new Thread(task);
        th.start();
    }
}
