package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simulation.Simulation;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent loader = FXMLLoader.load(getClass().getResource("initialParamsWindow.fxml"));
        stage.setTitle("Evolution Simulator");
        stage.setScene(new Scene(loader));
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
        // Simulation simulation = new Simulation("Sysad",3,3,0.3,5,100,8, 100);
        // simulation.start();
        //launch(args);
    }
}
