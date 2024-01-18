package com.tiromosca.network.singleplayer.controller;

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

public class ActualResultController implements Initializable {

    public AnchorPane dashboard;
    public Text info_label;
    public Text shots_label;
    public Text flies_label;
    public Button go_button;
    public Text player_aim;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SmartGameHolder holder = SmartGameHolder.getInstance();
        SmartGame smartGame = holder.getSmartGame();
        String infoText;

        if (smartGame.isPlayerTime()) {
            smartGame.setPlayerTime(false);
            infoText = "Seu palpite foi: " + smartGame.getPlayerLastAttempt();
            go_button.setOnAction(event -> {
                try {
                    Model.getInstance().getSingleplayerViewFactory().showLoadingWindow();
                    dashboard.getScene().getWindow().hide();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            // Add a jogada ao historico
            var lastAttempt = smartGame.getPlayerLastAttempt();
            var playHistoryText = lastAttempt + " - "
                    + smartGame.getActualShotsResult() + ", "
                    + smartGame.getActualFliesResult();
            smartGame.getPlays().add(playHistoryText);
        } else {
            smartGame.setPlayerTime(true);
            infoText = "O palpite do oponente foi: " + smartGame.getMyLastAttempt();
            go_button.setOnAction(event -> {
                try {
                    Model.getInstance().getSingleplayerViewFactory().showActualMatchWindow();
                    dashboard.getScene().getWindow().hide();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }

        info_label.setText(infoText);
        shots_label.setText(smartGame.getActualShotsResult());
        flies_label.setText(smartGame.getActualFliesResult());
        player_aim.setText("Seu número é: " + smartGame.getValueOfPlayer());
    }
}
