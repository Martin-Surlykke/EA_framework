package com.ea_framework.View.InfoViews;

import javafx.scene.Node;

public interface infoView <T>{

    Node getView();

    void update(T record);

}
