package com.tiromosca.network.singleplayer.controller;

import com.tiromosca.network.controller.util.TextFieldInputLimit;
import com.tiromosca.network.model.Model;
import com.tiromosca.network.singleplayer.SmartGame;
import com.tiromosca.network.singleplayer.util.SmartGameHolder;
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
        SmartGame smartGame = new SmartGame();
        smartGame.chooseValue();
        smartGame.setNumOfVictoriesOfPlayer(0);
        smartGame.setNumOfVictoriesOfPC(0);
        smartGame.setPlayerTime(true);

        SmartGameHolder holder = SmartGameHolder.getInstance();
        holder.setSmartGame(smartGame);

        TextFieldInputLimit.limit_input(user_name_input, 15);

        play_button.setOnAction(event -> {
            try {
                smartGame.setPlayerUserName(user_name_input.getText());

                Model.getInstance().getSingleplayerViewFactory().showChooseValueWindow();
                dashboard.getScene().getWindow().hide();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
