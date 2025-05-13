package com.ea_framework;

import java.util.Map;

public interface ConfigurableProvider {
    Map<OperatorType, Object> getOperatorConfigurations();
}
