package com.tiromosca.network.controller;

import com.tiromosca.network.connection.client.MatchListener;
import com.tiromosca.network.connection.client.PlayerClient;
import com.tiromosca.network.controller.util.TextFieldInputLimit;
import com.tiromosca.network.game.util.PlayerHolder;
import com.tiromosca.network.model.Model;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActualMatchController implements Initializable {

    public AnchorPane dashboard;
    public TextField value_input;
    public Button go_button;
    public Text player_aim;
    public Text warning_message;
    public Button play_history_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var actualMatchController = new ActualMatchControllerThread();
        actualMatchController.start();
    }

    private class ActualMatchControllerThread extends Thread implements MatchListener {

        @Override
        public void run() {
            PlayerHolder holder = PlayerHolder.getInstance();
            PlayerClient player = holder.getPlayer();
            player.setMatchListener(this);

            warning_message.setVisible(false);
            player_aim.setText("Seu número é: " + player.getPlayerAim());

            // limitar caracteres de entrada
            TextFieldInputLimit.limit_input(value_input, 3);

            go_button.setOnAction(event -> {
                String value = value_input.getText();
                if (isValidValue(value)) {
                    warning_message.setVisible(false);

                    // caso seja um valor valido, enviar ao servidor
                    player.sendAttempt(value + '\n');

                    new Thread(player::getMatchResult).start();
                } else {
                    warning_message.setVisible(true);
                }
            });

            play_history_button.setOnAction(event -> {
                try {
                    Model.getInstance().getMultiplayerViewFactory().showPlayHistoryWindow();
                    dashboard.getScene().getWindow().hide();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }


        @Override
        public void haveAResult(String flies, String shots, int winner, int looser) {
            PlayerHolder holder = PlayerHolder.getInstance();
            PlayerClient player = holder.getPlayer();

            player.setFlies(flies);
            player.setShots(shots);

            // se nao ganhou nem perdeu, proxima rodada...
            if (winner == 0 && looser == 0) {
                showActualResultWidow();
            } else {
                // como esse eh o jogador que fez a jogada atual, ele soh pode ser o vencedor
                player.setIsActualChampion(true);
                var numOfVictories = player.getNumOfVictories() + 1;
                player.setNumOfVictories(numOfVictories);
                showFinalResultWidow();
            }
        }
    }

    private boolean isValidValue(String value) {
        if (value.length() != 3) {
            return false;
        }

        return value.charAt(0) != value.charAt(1) &&
                value.charAt(0) != value.charAt(2) &&
                value.charAt(1) != value.charAt(2);
    }

    private void showActualResultWidow() {
        Platform.runLater(() -> {
            try {
                Model.getInstance().getMultiplayerViewFactory().showActualResultWindow();
                dashboard.getScene().getWindow().hide();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void showFinalResultWidow() {
        Platform.runLater(() -> {
            try {
                Model.getInstance().getMultiplayerViewFactory().showResultWindow();
                dashboard.getScene().getWindow().hide();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
