package com.tiromosca.network.controller;

import com.tiromosca.network.connection.client.ConnectionListener;
import com.tiromosca.network.connection.client.MatchListener;
import com.tiromosca.network.connection.client.PlayerClient;
import com.tiromosca.network.game.util.PlayerHolder;
import com.tiromosca.network.model.Model;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingBetweenRounds implements Initializable {
    public AnchorPane dashboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var loadingBetweenRoundsController = new LoadingBetweenRoundsThread();
        loadingBetweenRoundsController.start();
    }

    private class LoadingBetweenRoundsThread extends Thread implements ConnectionListener, MatchListener {

        @Override
        public void run() {
            PlayerHolder holder = PlayerHolder.getInstance();
            PlayerClient player = holder.getPlayer();

            if (player.getItsMyTimeToPlay()) {
                player.setConnectionListener(this);
                new Thread(player::verifyOpponentConnection).start();
            } else {
                player.setMatchListener(this);
                new Thread(player::getMatchResult).start();
            }
        }

        @Override
        public void itsTimeToPlay() {
            PlayerHolder holder = PlayerHolder.getInstance();
            PlayerClient player = holder.getPlayer();
            player.setMatchListener(this);

            showActualMatchWidow();
        }

        @Override
        public void haveAResult(String flies, String shots, int winner, int looser) {
            PlayerHolder holder = PlayerHolder.getInstance();
            PlayerClient player = holder.getPlayer();

            // Esses valores servirão para exibir o resultado do oponente na tela de resultado do round
            player.setFlies(flies);
            player.setShots(shots);

            showActualResultWindow();
        }
    }

    private void showActualMatchWidow() {
        Platform.runLater(() -> {
            try {
                Model.getInstance().getViewFactory().showActualMatchWindow();
                dashboard.getScene().getWindow().hide();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void showActualResultWindow() {
        Platform.runLater(() -> {
            try {
                Model.getInstance().getViewFactory().showActualResultWindow();
                dashboard.getScene().getWindow().hide();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

}
