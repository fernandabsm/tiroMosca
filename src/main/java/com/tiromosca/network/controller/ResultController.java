package com.tiromosca.network.controller;

import com.tiromosca.network.connection.client.PlayerClient;
import com.tiromosca.network.game.util.PlayerHolder;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements Initializable {


    public AnchorPane dashboard;
    public Text result_label;
    public Text value_label;
    public Text your_victories_label;
    public Text opponent_victories_label;
    public Button play_again_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlayerHolder holder = PlayerHolder.getInstance();
        PlayerClient player = holder.getPlayer();
        var value = player.getOpponentAim();

        value_label.setText(value);
        your_victories_label.setText("Você: " + player.getNumOfVictories());
        opponent_victories_label.setText("Oponente: " + player.getNumOfOpponentVictories());

        if (player.getIsActualChampion()) {
            result_label.setText("Você ganhou!");
        } else {
            result_label.setText("Você perdeu!");
        }
    }
}
