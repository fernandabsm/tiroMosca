package com.tiromosca.network.model;

import com.tiromosca.network.view.ViewFactory;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Model {
    private static Model model;
    private final ViewFactory viewFactory;

    private Model() {
        this.viewFactory = new ViewFactory();
    }

    public static synchronized Model getInstance() {
        if (Objects.isNull(model)) {
            model = new Model();
        }

        return model;
    }
}
