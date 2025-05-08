package com.ea_framework.MetaData;

import java.util.List;

public class BitString implements SearchSpaceMetaData {
    @Override
    public String getName() {
        return "BITSTRING";
    }

    @Override
    public List<String> getCompatibleProblems() {
        return List.of(
                "LEADINGONES",
                "ONE_MAX"
        );
    }
}
