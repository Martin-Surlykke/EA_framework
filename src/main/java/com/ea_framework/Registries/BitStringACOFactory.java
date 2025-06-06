package com.ea_framework.Registries;

import com.ea_framework.ACOTypes.BitStringACOType;

@FunctionalInterface
public interface BitStringACOFactory {
    BitStringACOType create(double rho, double Q, double tauMin, double tauMax);
}