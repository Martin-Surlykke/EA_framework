package com.ea_framework.Configs;

public class BatchConfigBuilder {
    private final BatchConfig config = new BatchConfig();

    public BatchConfigBuilder setSearchSpace(String space) {
        config.setSearchSpace(space);
        return this;
    }

    public BatchConfigBuilder setProblem(String problem) {
        config.setProblem(problem);
        return this;
    }

    public BatchConfigBuilder setAlgorithm(String algorithm) {
        config.setAlgorithm(algorithm);
        return this;
    }

    public BatchConfigBuilder setRepeats(int repeats) {
        config.setRepeats(repeats);
        return this;
    }

    public BatchConfigBuilder withSetting(String key, Object value) {
        config.getAlgorithmConfig().put(key, value);
        return this;
    }

    public BatchConfig build() {
        return config;
    }
}
