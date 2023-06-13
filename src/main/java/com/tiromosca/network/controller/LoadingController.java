package com.tiromosca.network.controller;

import com.tiromosca.network.connection.client.ConnectionListener;
import com.tiromosca.network.connection.client.PlayerClient;
import com.tiromosca.network.game.util.PlayerHolder;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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

        @Override
        public void run() {
            PlayerHolder holder = PlayerHolder.getInstance();
            PlayerClient playerClient = holder.getPlayer();

            playerClient.setConnectionListener(this);
            if (playerClient.getItsMyTimeToPlay()) {
                new Thread(playerClient::verifyOpponentConnection).start();
            } else {
                try {
                    sleep(2000);
                    try {
                        showChooseValueWindow();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public void itsTimeToPlay() {
            try {
                showChooseValueWindow();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
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
            stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/com/tiromosca/network/image/mosca-icone.png"))));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Tiro e Mosca");
            stage.show();
            dashboard.getScene().getWindow().hide();
        });
    }

    private void loadFont() throws FileNotFoundException {
        Font.loadFont(getClass().getResourceAsStream("/com/tiromosca/network/style/font/Fredoka-Regular.ttf"), 40);
    }

}
