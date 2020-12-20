package sample;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class InitialParamsWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Spinner<Integer> initialAnimalsSpinner;

    @FXML
    private Spinner<Integer> plantEnergySpinner;

    @FXML
    private Spinner<Integer> moveEnergySpinner;

    @FXML
    private Spinner<Double> jungleRatioSpinner;

    @FXML
    private Spinner<Integer> boardHeightSpinner;

    @FXML
    private Spinner<Integer> boardWidthSpinner;

    @FXML
    private Button startSimulationButton;

    @FXML
    void initialize() {
        assert initialAnimalsSpinner != null : "fx:id=\"initialAnimalsSpinner\" was not injected: check your FXML file 'initialParamsWindow.fxml'.";
        assert plantEnergySpinner != null : "fx:id=\"plantEnergySpinner\" was not injected: check your FXML file 'initialParamsWindow.fxml'.";
        assert moveEnergySpinner != null : "fx:id=\"moveEnergySpinner\" was not injected: check your FXML file 'initialParamsWindow.fxml'.";
        assert jungleRatioSpinner != null : "fx:id=\"jungleRatioSpinner\" was not injected: check your FXML file 'initialParamsWindow.fxml'.";
        assert boardHeightSpinner != null : "fx:id=\"boardHeightSpinner\" was not injected: check your FXML file 'initialParamsWindow.fxml'.";
        assert boardWidthSpinner != null : "fx:id=\"boardWidthSpinner\" was not injected: check your FXML file 'initialParamsWindow.fxml'.";
        assert startSimulationButton != null : "fx:id=\"startSimulationButton\" was not injected: check your FXML file 'initialParamsWindow.fxml'.";
        initialAnimalsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25,10));
        plantEnergySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100,50));
        moveEnergySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100,10));
        jungleRatioSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, 0.9,0.5,0.01));
        boardHeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100));
        boardWidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100));
    }
}

