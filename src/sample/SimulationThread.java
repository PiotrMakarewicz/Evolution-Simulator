package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simulation.Animal;
import simulation.Simulation;
import simulation.SimulationErrorException;
import simulation.StatsWatcher;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class SimulationThread extends Thread{
    private final Stage stage = new Stage();
    private final Simulation simulation;
    private final Scene scene;
    private final Group root = new Group();
    private final StatsWatcher statsWatcher;
    private final SimulationCanvas canvas;
    private final Label statsLabel = new Label();
    private final Button pauseButton;
    private final Button highlightButton;
    private final AtomicBoolean paused = new AtomicBoolean(false);
    private final AtomicBoolean dominatingAnimalsHighlighted = new AtomicBoolean(false);

    public void run() {
        Platform.runLater(this::displaySimulation);
    }
    public SimulationThread(Simulation simulation){
        this.simulation = simulation;
        this.scene = new Scene(root, simulation.getWidth()*10+400, Math.max(simulation.getHeight()*10,185), Color.BLACK);
        this.statsWatcher = new StatsWatcher(simulation);
        this.canvas = new SimulationCanvas(simulation);
        this.highlightButton = createHighlightButton();
        this.pauseButton = createPauseButton();
    }
    public void displaySimulation() {
        canvas.setLayoutX(400);
        statsLabel.setTextFill(Color.web("#FFFFFF"));
        statsLabel.setPadding(new Insets(5.0));

        root.getChildren().add(statsLabel);
        root.getChildren().add(canvas);
        root.getChildren().add(pauseButton);
        root.getChildren().add(highlightButton);

        stage.setScene(scene);
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
    private Button createHighlightButton(){
        Button button = new Button("Highlight dominating Genome");
        button.setLayoutX(90);
        button.setLayoutY(150);
        button.setVisible(false);
        button.setOnMouseClicked(mouseEvent -> {
            if(dominatingAnimalsHighlighted.get()){
                canvas.drawBackground();
                canvas.update();
                dominatingAnimalsHighlighted.set(false);
            }
            else {
                canvas.highlightLocations(
                        statsWatcher.getAnimalsWithDominatingGenome()
                                .stream().map(Animal::getLocation)
                                .collect(Collectors.toList()));
                this.dominatingAnimalsHighlighted.set(true);
            }
        });
        return button;
    }
    private Button createPauseButton(){
        Button button = new Button("RUN/PAUSE");
        button.setLayoutX(5);
        button.setLayoutY(150);
        button.setOnMouseClicked(mouseEvent -> { paused.set(!paused.get());
            this.highlightButton.setVisible(!this.highlightButton.isVisible());
        });
        return button;
    }


}
