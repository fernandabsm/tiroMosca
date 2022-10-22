package com.tiromosca.network.controller;

import com.tiromosca.network.connection.client.PlayerClient;
import com.tiromosca.network.controller.util.TextFieldInputLimit;
import com.tiromosca.network.game.util.PlayerHolder;
import com.tiromosca.network.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserNameController implements Initializable {

    public AnchorPane dashboard;
    public TextField user_name_input;
    public Button play_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlayerClient playerClient = new PlayerClient();
        playerClient.connectToServer();

        PlayerHolder holder = PlayerHolder.getInstance();
        holder.setPlayer(playerClient);


        // limitar caracteres de entrada
        TextFieldInputLimit.limit_input(user_name_input, 15);

        play_button.setOnAction(event -> {
            try {
                playerClient.setUserName(user_name_input.getText());
                playerClient.sendUserName(user_name_input.getText() + '\n');

                Model.getInstance().getViewFactory().showGameLoadingWindow();
                dashboard.getScene().getWindow().hide();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
