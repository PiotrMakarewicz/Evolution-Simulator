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
import simulation.SimulationErrorException;
import simulation.StatsWatcher;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimulationThread extends Thread{
    private final Stage stage = new Stage();
    private final Simulation simulation;
    private final Scene simulationScene;
    private final Group root = new Group();
    private final StatsWatcher statsWatcher;
    private final SimulationCanvas canvas;
    private final Label statsLabel = new Label();
    private final AtomicBoolean paused = new AtomicBoolean(false);

    public void run() {
        Platform.runLater(this::displaySimulation);
    }
    public SimulationThread(Simulation simulation){
        this.simulation = simulation;
        this.simulationScene = new Scene(root, simulation.getWidth()*10+400, Math.max(simulation.getHeight()*10,400), Color.BLACK);
        this.statsWatcher = new StatsWatcher(simulation);
        this.canvas = new SimulationCanvas(simulation);
    }
    public void displaySimulation() {
        canvas.setLayoutX(400);
        statsLabel.setTextFill(Color.web("#FFFFFF"));
        statsLabel.setPadding(new Insets(5.0));

        root.getChildren().add(statsLabel);
        root.getChildren().add(canvas);
        stage.setScene(simulationScene);
        stage.setTitle("Evolution Simulator");
        stage.setResizable(false);

        simulation.start();
        canvas.drawBackground();
        canvas.update();
        stage.show();
        SimulationThread simulationThread = this;
        Task<Integer> task = new Task<>() {
            @Override protected Integer call() throws Exception {
                int iterations;
                for (iterations = 0; true; iterations++) {
                    if (isCancelled()) {
                        break;
                    }
                    Thread.sleep(100);
                    if(simulationThread.paused.get()){
                        continue;
                    }

                    Platform.runLater(()->{
                        try {
                            simulation.simulateOneDay();
                        } catch (SimulationErrorException e) {
                            e.printStackTrace();
                        }
                        canvas.update(); statsLabel.setText(statsWatcher.getSummary());});
                }
                return iterations;
            }
        };
        Thread th = new Thread(task);
        th.start();
    }
}
