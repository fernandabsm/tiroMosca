package com.tiromosca.network.controller;

import com.tiromosca.network.connection.client.PlayerClient;
import com.tiromosca.network.game.util.PlayerHolder;
import com.tiromosca.network.model.Model;
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
        PlayerHolder holder = PlayerHolder.getInstance();
        PlayerClient player = holder.getPlayer();
        String infoText;

        if (player.getItsMyTimeToPlay()) {
            player.setItsMyTimeToPlay(false);
            infoText = "Seu palpite foi: " + player.getLastAttempt();
            go_button.setOnAction(event -> {
                try {
                    Model.getInstance().getViewFactory().showLoadingBetweenRoundsWindow();
                    dashboard.getScene().getWindow().hide();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } else {
            player.setItsMyTimeToPlay(true);
            infoText = "O palpite do oponente foi: " + player.getOpponentAttempt();
            go_button.setOnAction(event -> {
                try {
                    Model.getInstance().getViewFactory().showActualMatchWindow();
                    dashboard.getScene().getWindow().hide();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }

        info_label.setText(infoText);
        shots_label.setText(player.getShots());
        flies_label.setText(player.getFlies());
        player_aim.setText("Seu número é: " + player.getPlayerAim());
    }
}
