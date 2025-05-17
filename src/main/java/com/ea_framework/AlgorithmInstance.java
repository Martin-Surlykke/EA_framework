package com.ea_framework;

import com.ea_framework.Descriptors.AlgorithmDescriptor;

public record AlgorithmInstance(String displayName, AlgorithmDescriptor descriptor) {
    @Override
    public String toString() {
        return displayName;
    }
}