package com.tiromosca.network.singleplayer.controller;

import com.tiromosca.network.connection.client.PlayerClient;
import com.tiromosca.network.game.util.PlayerHolder;
import com.tiromosca.network.model.Model;
import com.tiromosca.network.singleplayer.SmartGame;
import com.tiromosca.network.singleplayer.util.SmartGameHolder;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements Initializable {

    public AnchorPane dashboard;
    public Text result_label;
    public Text value_label;
    public Text your_victories_label;
    public Text opponent_victories_label;
    public Button play_again_button;
    public Button over_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SmartGameHolder holder = SmartGameHolder.getInstance();
        SmartGame smartGame = holder.getSmartGame();
        var value = smartGame.getMyValue();

        value_label.setText(value);
        your_victories_label.setText(smartGame.getPlayerUserName() + ": " + smartGame.getNumOfVictoriesOfPlayer());
        opponent_victories_label.setText("PC : " + smartGame.getNumOfVictoriesOfPC());

        if (smartGame.isPlayerTheActualChampion()) {
            result_label.setText("Você ganhou!");
        } else {
            result_label.setText("Você perdeu!");
        }

        play_again_button.setOnAction(event -> {
            try {
                smartGame.setHasFly(false);
                smartGame.setFoundNumber1(false);
                smartGame.setFoundNumber2(false);
                smartGame.setFirstPlayToFoundFirstDigit(true);
                smartGame.setFirstPlayToFoundSecondDigit(true);
                smartGame.setFirstPlayToFoundThirdDigit(true);
                smartGame.setNumOfFlies(0);
                smartGame.setFirstDigit(0);
                smartGame.setSecondDigit(1);
                smartGame.setThirdDigit(9);

                Model.getInstance().getSingleplayerViewFactory().showChooseValueWindow();
                dashboard.getScene().getWindow().hide();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
