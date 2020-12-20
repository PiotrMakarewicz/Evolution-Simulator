package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import simulation.Simulation;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("initialParamsWindow.fxml"));
        Parent root = loader.load();
        stage.setTitle("Evolution Simulator");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        InitialParamsWindowController controller = loader.getController();
        Button startButton = controller.getStartSimulationButton();
        startButton.addEventHandler(ActionEvent.ACTION,e -> {

        });
    }


    public static void main(String[] args) {
        launch(args);
        // Simulation simulation = new Simulation("Sysad",3,3,0.3,5,100,8, 100);
        // simulation.start();
        //launch(args);
    }
}
