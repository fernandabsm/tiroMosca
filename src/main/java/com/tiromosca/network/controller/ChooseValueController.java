package com.tiromosca.network.controller;

import com.tiromosca.network.connection.client.PlayerClient;
import com.tiromosca.network.controller.util.TextFieldInputLimit;
import com.tiromosca.network.game.util.PlayerHolder;
import com.tiromosca.network.model.Model;
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
        PlayerHolder holder = PlayerHolder.getInstance();
        PlayerClient player = holder.getPlayer();

        // limitar caracteres de entrada
        TextFieldInputLimit.limit_input(value_input, 3);

        warning_message.setVisible(false);
        start_button.setOnAction(event -> {
            String value = value_input.getText();
            if (isValidValue(value)) {
                warning_message.setVisible(false);

                // caso seja um valor valido, enviar ao servidor
                System.out.println("ESCOLHI UM NOVO VALOR. ESSE VALOR EH: " + value);
                player.setPlayerAim(value);
                player.sendAim(value+'\n');

                try {
                    Model.getInstance().getViewFactory().showLoadingBetweenRoundsWindow();
                    dashboard.getScene().getWindow().hide();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ;
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
