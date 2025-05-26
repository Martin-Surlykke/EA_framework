package com.ea_framework;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.BatchConfig;
import com.ea_framework.Controllers.VisualizeController;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Runners.Runner;
import com.ea_framework.Views.VisualizeView.VisualizeView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class RunBatch {

    private final BatchConfig config;
    private final Stage stage;

    public RunBatch(BatchConfig config, Stage stage) {
        this.config = config;
        this.stage = stage;
    }

    public void run() throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Problem problem = config.resolveProblem();
        problem.setMaxIterations(config.getTermination());

        Map<String, String> meta = config.getMetaConfigs();

        if (meta.containsKey("alpha") && meta.containsKey("t0")) {
            try {
                double alpha = Double.parseDouble(meta.get("alpha"));
                double t0 = Double.parseDouble(meta.get("t0"));
                problem.setSimulatedAnnealingParams(alpha, t0);
            } catch (NumberFormatException e) {
                System.err.println("Invalid alpha or t0 format");
            }

        }
        AlgorithmConfig algoConfig = config.getAlgorithmConfig();
        Algorithm algorithm = config.getAlgorithmDescriptor().create(algoConfig);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ea_framework/Visualizer.fxml"));
        Scene scene = new Scene(loader.load());

        VisualizeController controller = loader.getController();

        VisualizeView visualizer = problem.getVisualizer();
        visualizer.applyConfig(algoConfig);


        controller.initialize(
                visualizer,
                problem.getFitnessView(),
                problem.getConfigView(),
                problem.getStatView(),
                stage
        );



        if (config.getVisualSelected()) {
            Thread thread = new Thread(()  -> {
                for (int i = 0; i < config.getTermination(); i++) {
                    algorithm.run(i);
                    controller.updateView(algorithm, i);
                    try {
                        Thread.sleep(100); // Adjust sleep time as needed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });  

        } else {
            for (int i = 0; i < config.getTermination(); i++) {
                algorithm.run(i);
            }
        }

        stage.setScene(scene);
        stage.setTitle("Visualizer");
        stage.show();

        new Runner().run(problem, algorithm, controller, config.getTermination(), config.getVisualSelected());

    }
}
