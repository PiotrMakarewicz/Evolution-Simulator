package application;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import simulation.Simulation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ParametersLoader {
    public static Simulation load(String path) throws IOException, ParseException, InvalidStartingParametersException {
        System.out.println(Path.of(path).toAbsolutePath());
        String string = Files.readString(Path.of(path));

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(string);
        return new Simulation(
                "simulation",
                ((Long) jsonObject.get("width")).intValue(),
                ((Long) jsonObject.get("height")).intValue(),
                (Double) jsonObject.get("jungleRatio"),
                (Double) jsonObject.get("moveEnergy"),
                (Double) jsonObject.get("plantEnergy"),
                ((Long) jsonObject.get("initialAnimals")).intValue(),
                (Double) jsonObject.get("initialEnergy")
        );
    }
}
