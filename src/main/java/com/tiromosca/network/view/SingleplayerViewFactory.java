package com.tiromosca.network.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SingleplayerViewFactory {

    public void showUserNameWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserNameSingleplayer.fxml"));
        createStage(loader);
    }

    public void showChooseValueWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseValueSingleplayer.fxml"));
        createStage(loader);
    }

    public void showActualMatchWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ActualMatchSingleplayer.fxml"));
        createStage(loader);
    }

    public void showLoadingWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadingBetweenRoundsSingleplayer.fxml"));
        createStage(loader);
    }

    public void showActualResultWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ActualResultSingleplayer.fxml"));
        createStage(loader);
    }

    public void showResultWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultSingleplayer.fxml"));
        createStage(loader);
    }


    private void loadFont() throws FileNotFoundException {
        File file = new File("src/main/resources/com/tiromosca/network/style/font/Fredoka-Regular.ttf");
        Font.loadFont(new FileInputStream(file), 40);
    }

    private void createStage(FXMLLoader loader) {
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
    }
}
