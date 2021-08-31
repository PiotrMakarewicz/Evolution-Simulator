package application;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simulation.Animal;
import simulation.Simulation;
import simulation.exceptions.SimulationErrorException;
import simulation.stats.StatsWatcher;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class SimulationThread extends Thread{

    private final Simulation simulation;
    private final StatsWatcher statsWatcher;
    private SimulationCanvas canvas;
    private final AtomicBoolean paused = new AtomicBoolean(false);
    private final AtomicBoolean dominatingAnimalsHighlighted = new AtomicBoolean(false);

    private final Label statsLabel = new Label();
    private final Label selectedAnimalLabel = new Label();

    private final Button pauseButton = createPauseButton();
    private final Button highlightButton = createHighlightButton();
    private final Button endButton = createEndButton();
    private final Stage stage = new Stage();

    public void run() {
        Platform.runLater(this::displaySimulation);
    }

    public SimulationThread(Simulation simulation){
        this.simulation = simulation;
        this.statsWatcher = new StatsWatcher(simulation);
    }

    public void displaySimulation() {
        loadWindow();
        SimulationThread simulationThread = this;
        Task<Integer> task = new Task<>() {
            @Override protected Integer call() throws Exception {
                int iterations;
                for (iterations = 0; true; iterations++) {
                    if (isCancelled()) {
                        break;
                    }
                    Platform.runLater(()-> {
                        selectedAnimalLabel.setText(canvas.selectedAnimal == null ? "No selected animals." : "Selected animal:\n" + canvas.selectedAnimal.toString());
//                        canvas.drawSelectedAnimal();
                    });
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
                        canvas.update();
                        statsLabel.setText(statsWatcher.getDailyStatsAndUpdateSummarizer());
                    });
                }
                return iterations;
            }
        };
        Thread th = new Thread(task);
        th.start();
    }

    private void loadWindow() {
        final Group root = new Group();
        final Scene scene = new Scene(root, simulation.getWidth()*10+400, Math.max(simulation.getHeight()*10,350), Color.BLACK);

        canvas = new SimulationCanvas(simulation);
        canvas.setLayoutX(400);
        statsLabel.setTextFill(Color.web("#FFFFFF"));
        statsLabel.setLayoutX(5);
        statsLabel.setLayoutY(5);

        selectedAnimalLabel.setLayoutX(5);
        selectedAnimalLabel.setLayoutY(210);
        selectedAnimalLabel.setTextFill(Color.web("#FFFFFF"));
        root.getChildren().addAll(statsLabel,selectedAnimalLabel,canvas,pauseButton,highlightButton,endButton);

        stage.setScene(scene);
        stage.setTitle("Evolution Simulator");
        stage.setResizable(false);

        simulation.start();
        canvas.drawBackground();
        canvas.update();
        stage.show();
    }

    private Button createHighlightButton(){
        Button button = new Button("Highlight dominating Genome");
        button.setLayoutX(5);
        button.setLayoutY(180);
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
                dominatingAnimalsHighlighted.set(true);
            }
        });
        return button;
    }

    private Button createPauseButton(){
        Button button = new Button("RUN/PAUSE");
        button.setLayoutX(5);
        button.setLayoutY(150);
        button.setOnMouseClicked(mouseEvent -> {
            paused.set(!paused.get());
            highlightButton.setVisible(!highlightButton.isVisible());
            endButton.setVisible(!endButton.isVisible());
        });
        return button;
    }

    private Button createEndButton() {
        Button button = new Button("End simulation");
        button.setLayoutX(185);
        button.setLayoutY(180);
        button.setVisible(false);
        button.setOnMouseClicked(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,statsWatcher.summarizer.getSummary());
            alert.setHeaderText("Simulation finished!");
            alert.showAndWait();
            stage.close();}
        );
        return button;
    }


}
