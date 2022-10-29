package com.tiromosca.network.singleplayer.controller;

import com.tiromosca.network.game.util.GameMatchManager;
import com.tiromosca.network.model.Model;
import com.tiromosca.network.singleplayer.util.SmartGameHolder;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoadingBetweenRoundsController implements Initializable {
    public AnchorPane dashboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var loadingBetweenRoundsController = new LoadingBetweenRoundsThread();
        loadingBetweenRoundsController.start();
    }

    private class LoadingBetweenRoundsThread extends Thread {

        @Override
        public void run() {
            try {
                SmartGameHolder holder = SmartGameHolder.getInstance();
                var result = holder.getSmartGame().startSmartGame();

                sleep(1000);
                if (result.get(1) == 3) {
                    var victoriesOfPc = holder.getSmartGame().getNumOfVictoriesOfPC();
                    holder.getSmartGame().setNumOfVictoriesOfPC(victoriesOfPc + 1);
                    showFinalResultWindow();
                } else {
                    var formattedResult = GameMatchManager.formatResultOfAMatch(result.get(0), result.get(1));

                    holder.getSmartGame().setActualShotsResult(formattedResult.get(0));
                    holder.getSmartGame().setActualFliesResult(formattedResult.get(1));

                    showActualResultWindow();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void showActualResultWindow() {
        Platform.runLater(() -> {
            try {
                Model.getInstance().getSingleplayerViewFactory().showActualResultWindow();
                dashboard.getScene().getWindow().hide();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void showFinalResultWindow() {
        Platform.runLater(() -> {
            try {
                Model.getInstance().getSingleplayerViewFactory().showResultWindow();
                dashboard.getScene().getWindow().hide();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

}
