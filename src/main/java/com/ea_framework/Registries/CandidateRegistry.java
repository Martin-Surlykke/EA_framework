package com.ea_framework.Registries;

import com.ea_framework.Configs.CandidateConfig;
import com.ea_framework.Creators.CandidateCreator;

import java.util.HashMap;
import java.util.Map;

public class CandidateRegistry {

    private static final Map<String, CandidateCreator<? extends CandidateConfig>> creators = new HashMap<>();

    public static void registerCandidate(String name, CandidateCreator<? extends CandidateConfig> creator) {
        creators.put(name.toLowerCase(), creator);
    }
}
