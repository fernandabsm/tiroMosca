package com.tiromosca.network.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ViewFactory {

    public void showGameTypeWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameType.fxml"));
        createStage(loader);
    }

    public void showUserNameWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserName.fxml"));
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

    public void showActualMatchWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ActualMatch.fxml"));
        createStage(loader);
    }

    public void showLoadingBetweenRoundsWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadingBetweenRounds.fxml"));
        createStage(loader);
    }

    public void showActualResultWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ActualResult.fxml"));
        createStage(loader);
    }

    public void showResultWindow() throws FileNotFoundException {
        loadFont();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Result.fxml"));
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
