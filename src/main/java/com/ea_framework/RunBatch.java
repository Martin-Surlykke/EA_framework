package com.ea_framework;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.BatchConfig;
import com.ea_framework.Controllers.VisualizeController;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Runners.Runner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RunBatch {

    private final BatchConfig config;
    private final Stage stage;

    public RunBatch(BatchConfig config, Stage stage) {
        this.config = config;
        this.stage = stage;
    }

    @SuppressWarnings("unchecked")
    public void run() throws IOException {
        ProblemDescriptor<?, ?> rawProblem = config.getProblemDescriptor();
        AlgorithmDescriptor<?, ?, ?, ?> rawAlgo = config.getAlgorithmDescriptor();

        // You MUST ensure these match when you register them in the registry
        runTyped(
                (ProblemDescriptor<Object, Problem<Object>>) rawProblem,
                (AlgorithmDescriptor<Object, Problem<Object>, Algorithm<Object>, AlgorithmConfig>) rawAlgo
        );
    }

    private <S, P extends Problem<S>, A extends Algorithm<S>, C extends AlgorithmConfig>
    void runTyped(
            ProblemDescriptor<S, P> problemDescriptor,
            AlgorithmDescriptor<S, P, A, C> algorithmDescriptor
    ) throws IOException {

        // Load and configure problem
        P problem = problemDescriptor.getLoader().load(config.getInputStream());
        problem.setMaxIterations(config.getTermination());

        // Create algorithm
        C algoConfig = algorithmDescriptor.getConfigClass().cast(config.getAlgorithmConfig());
        A algorithm = algorithmDescriptor.create(algoConfig);

        // Load FXML & controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ea_framework/Visualizer.fxml"));
        Scene scene = new Scene(loader.load());

        VisualizeController<S> controller = loader.getController();
        controller.initialize(
                problem.getVisualizeView(),
                problem.getFitnessView(),
                problem.getConfigView(),
                problem.getStatView(),
                stage
        );

        stage.setScene(scene);
        stage.setTitle("Visualizer");
        stage.show();

        // Run it
        new Runner<S>().run(problem, algorithm, controller, config.getTermination());
    }
}
