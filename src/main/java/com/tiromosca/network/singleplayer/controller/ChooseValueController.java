package com.tiromosca.network.singleplayer.controller;

import com.tiromosca.network.model.Model;
import com.tiromosca.network.singleplayer.SmartGame;
import com.tiromosca.network.singleplayer.util.SmartGameHolder;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseValueController implements Initializable {
    public TextField value_input;
    public Button start_button;
    public Text warning_message;
    public AnchorPane dashboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        warning_message.setVisible(false);

        SmartGameHolder holder = SmartGameHolder.getInstance();
        var smartGame = holder.getSmartGame();

        start_button.setOnAction(event -> {
            String value = value_input.getText();
            if (isValidValue(value)) {
                warning_message.setVisible(false);
                smartGame.setValueOfPlayer(value);
                try {
                    Model.getInstance().getSingleplayerViewFactory().showActualMatchWindow();
                    dashboard.getScene().getWindow().hide();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
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
