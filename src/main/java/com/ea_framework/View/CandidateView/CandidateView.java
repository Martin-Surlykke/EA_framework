package com.ea_framework.View.CandidateView;

import com.ea_framework.Candidates.Candidate;
import javafx.scene.Node;

public interface CandidateView<T extends Candidate> {
    Node getView();
    void update(T candidate);
}
