package com.ea_framework.Views.InfoViews;

import javafx.scene.Node;

public interface infoView <T>{

    Node getView();

    void update(T record);

}
