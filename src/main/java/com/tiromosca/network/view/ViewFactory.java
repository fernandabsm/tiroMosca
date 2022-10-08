package com.tiromosca.network.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RequiredArgsConstructor
public class ViewFactory {

    public void showGameTypeWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameType.fxml"));
        createStage(loader);
    }

    public void showGameLoadingWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Loading.fxml"));
        createStage(loader);
    }

    public void showChooseValueWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseValue.fxml"));
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
        stage.setScene(scene);
        stage.setTitle("Tiro e Mosca");
        stage.show();
    }
}