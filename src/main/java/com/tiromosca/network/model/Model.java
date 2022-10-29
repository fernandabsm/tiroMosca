package com.tiromosca.network.model;

import com.tiromosca.network.view.MultiplayerViewFactory;
import com.tiromosca.network.view.SingleplayerViewFactory;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Model {
    private static Model model;
    private final MultiplayerViewFactory multiplayerViewFactory;
    private final SingleplayerViewFactory singleplayerViewFactory;

    private Model() {
        this.multiplayerViewFactory = new MultiplayerViewFactory();
        this.singleplayerViewFactory = new SingleplayerViewFactory();
    }

    public static synchronized Model getInstance() {
        if (Objects.isNull(model)) {
            model = new Model();
        }

        return model;
    }
}
