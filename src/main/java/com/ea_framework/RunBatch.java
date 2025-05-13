    package com.ea_framework;

    import com.ea_framework.Algorithms.Algorithm;
    import com.ea_framework.Configs.AlgorithmConfig;
    import com.ea_framework.Configs.BatchConfig;
    import com.ea_framework.Controllers.VisualizeController;
    import com.ea_framework.Descriptors.AlgorithmDescriptor;
    import com.ea_framework.Problems.Problem;
    import com.ea_framework.Runners.ProblemRunner;
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

        public void run() throws IOException {

            Problem problem = config.getProblem();
            problem.setMaxIterations(config.getTermination());

            Algorithm<?> algorithm = createAlgorithm();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ea_framework/Visualizer.fxml"));
            Scene scene = new Scene(loader.load());
            VisualizeController controller = loader.getController();

            var visualizeView = problem.getVisualizeView();
            var fitnessView = problem.getFitnessView();
            var configView = problem.getConfigView();
            var statView = problem.getStatView();

            controller.initialize(visualizeView, fitnessView, configView, statView, stage);
            stage.setScene(scene);
            stage.setTitle("Visualizer");
            stage.show();


            ProblemRunner<Problem, Algorithm<?>> runner =
                    (ProblemRunner<Problem, Algorithm<?>>) config.getAlgorithmDescriptor().getProblemRunner();

            System.out.println("[RunBatch] Invoking runner: " + runner.getClass().getSimpleName());
            runner.run(problem, algorithm, controller, config.getTermination());
        }

        private Algorithm<?> createAlgorithm() {
            AlgorithmConfig algoConfig = config.getAlgorithmConfig();
            AlgorithmDescriptor<?, ?> descriptor = config.getAlgorithmDescriptor();

            return descriptor.create(algoConfig);
        }

    }