package com.tiromosca.network.singleplayer.controller;

import com.tiromosca.network.controller.util.TextFieldInputLimit;
import com.tiromosca.network.game.util.GameMatchManager;
import com.tiromosca.network.model.Model;
import com.tiromosca.network.singleplayer.util.SmartGameHolder;
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
        warning_message.setVisible(false);

        SmartGameHolder holder = SmartGameHolder.getInstance();
        var valuePC = holder.getSmartGame().getMyValue();

        player_aim.setText("Seu número é: " + holder.getSmartGame().getValueOfPlayer());

        TextFieldInputLimit.limit_input(value_input, 3);

        go_button.setOnAction(event -> {
            var attempt = value_input.getText();
            holder.getSmartGame().setPlayerLastAttempt(attempt);
            if (isValidValue(attempt)) {
                warning_message.setVisible(false);
                var result = GameMatchManager.getResultOfAMatch(attempt, valuePC);
                var shot = result.get(0);
                var fly = result.get(1);
                var formattedResult = GameMatchManager.formatResultOfAMatch(shot, fly);

                holder.getSmartGame().setActualShotsResult(formattedResult.get(0));
                holder.getSmartGame().setActualFliesResult(formattedResult.get(1));

                if (fly == 3) {
                    try {
                        var numOfVictories = holder.getSmartGame().getNumOfVictoriesOfPlayer();
                        holder.getSmartGame().setNumOfVictoriesOfPlayer(numOfVictories + 1);
                        holder.getSmartGame().setPlayerTheActualChampion(true);
                        Model.getInstance().getSingleplayerViewFactory().showResultWindow();
                        dashboard.getScene().getWindow().hide();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Model.getInstance().getSingleplayerViewFactory().showActualResultWindow();
                        dashboard.getScene().getWindow().hide();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else  {
                warning_message.setVisible(true);
            }
        });

        play_history_button.setOnAction(event -> {
            try {
                Model.getInstance().getSingleplayerViewFactory().showPlayHistoryWindow();
                dashboard.getScene().getWindow().hide();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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
