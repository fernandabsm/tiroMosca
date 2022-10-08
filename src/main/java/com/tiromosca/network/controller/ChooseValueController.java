package com.tiromosca.network.controller;

import com.tiromosca.network.connection.client.PlayerClient;
import com.tiromosca.network.game.util.PlayerHolder;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooseValueController implements Initializable {
    public TextField value_input;
    public Button start_button;
    public Text warning_message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlayerHolder holder = PlayerHolder.getInstance();
        PlayerClient player = holder.getPlayer();
        warning_message.setVisible(false);
        start_button.setOnAction(event -> {
            String value = value_input.getText();
            if (isValidValue(value)) {
                warning_message.setVisible(false);
                // caso seja um valor valido, enviar ao servidor
                player.sendAim(value);
            } else {
                warning_message.setVisible(true);
            }

        });
    }

    private boolean isValidValue(String value) {
        if (value.length() != 3) {
            return false;
        }

        return value.charAt(0) != value.charAt(1) &&
                value.charAt(0) != value.charAt(2) &&
                value.charAt(1) != value.charAt(2);
    }
}
