package com.tiromosca.network.controller;

import com.tiromosca.network.connection.client.ConnectionListener;
import com.tiromosca.network.connection.client.PlayerClient;
import com.tiromosca.network.game.util.PlayerHolder;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {

    public AnchorPane dashboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var loadingThread = new LoadingControllerThread();
        loadingThread.start();
    }

    private class LoadingControllerThread extends Thread implements ConnectionListener {

        private final Boolean haveTwoPlayers = false;

        @Override
        public void run() {
            PlayerHolder holder = PlayerHolder.getInstance();
            PlayerClient playerClient = holder.getPlayer();

            playerClient.setConnectionListener(this);
            if (playerClient.getItsMyTimeToPlay()) {
                synchronized (haveTwoPlayers) {
                    new Thread(playerClient::verifyOpponentConnection).start();
                    try {
                        haveTwoPlayers.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                showChooseValueWindow();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void itsTimeToPlay() {
            synchronized (this.haveTwoPlayers) {
                this.haveTwoPlayers.notifyAll();
            }
        }
    }

    public void showChooseValueWindow() throws FileNotFoundException {
        Platform.runLater(() -> {
            try {
                loadFont();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tiromosca/network/view/ChooseValue.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Tiro e Mosca");
            stage.show();
            dashboard.getScene().getWindow().hide();
        });
    }

    private void loadFont() throws FileNotFoundException {
        File file = new File("src/main/resources/com/tiromosca/network/style/font/Fredoka-Regular.ttf");
        Font.loadFont(new FileInputStream(file), 40);
    }

}
