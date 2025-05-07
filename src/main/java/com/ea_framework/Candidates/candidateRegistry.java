package com.ea_framework.Candidates;

import java.util.HashMap;
import java.util.Map;

public class candidateRegistry {

    private static final Map<String, CandidateCreator> creators = new HashMap<>();

    public static void registerCandidate(String name, CandidateCreator creator) {
        creators.put(name.toLowerCase(), creator);
    }

    public static Candidate create(String name, String filePath) throws Exception {
        CandidateCreator creator = creators.get(name.toLowerCase());
        if (creator == null) {
            throw new IllegalArgumentException("Candidate type not registered: " + name);
        }
        return creator.create(name, filePath);
    }
}