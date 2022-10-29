package com.tiromosca.network.controller;

import com.tiromosca.network.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameTypeController implements Initializable {
    public Button multiplayer_button;
    public Button singleplayer_button;
    public AnchorPane dashboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        multiplayer_button.setOnAction(event -> {
            try {
                Model.getInstance().getMultiplayerViewFactory().showUserNameWindow();
                dashboard.getScene().getWindow().hide();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        singleplayer_button.setOnAction(event -> {
            try {
                Model.getInstance().getSingleplayerViewFactory().showUserNameWindow();
                dashboard.getScene().getWindow().hide();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
