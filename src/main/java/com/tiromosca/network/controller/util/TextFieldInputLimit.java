package com.tiromosca.network.controller.util;

import javafx.scene.control.TextField;

public class TextFieldInputLimit {

    public static void limit_input(TextField input, int maxLength) {
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (input.getText().length() > maxLength) {
                String s = input.getText().substring(0, maxLength);
                input.setText(s);
            }
        });
    }
}
