package com.tiromosca.network.controller;

import com.tiromosca.network.connection.client.PlayerClient;
import com.tiromosca.network.game.util.PlayerHolder;
import com.tiromosca.network.model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PlayHistoryController implements Initializable {

    @FXML
    public ListView<String> listView;
    public AnchorPane dashboard;
    public Button close_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlayerHolder holder = PlayerHolder.getInstance();
        PlayerClient player = holder.getPlayer();

        listView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/tiromosca/network/style/ListView.css")).toExternalForm());

        listView.setCellFactory(new Callback<>() {

            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<>() {

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            BorderPane borderPane = new BorderPane();

                            Label label = new Label(item);

                            File file = new File("src/main/resources/com/tiromosca/network/style/font/Fredoka-Regular.ttf");
                            try {
                                Font font = Font.loadFont(new FileInputStream(file), 16);
                                label.setFont(font);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }

                            borderPane.setLeft(label);
                            BorderPane.setMargin(label, new Insets(0, 0, 0, 10));

                            setGraphic(borderPane);
                            listView.setMouseTransparent(true);
                        }
                    }
                };
            }
        });

        listView.getItems().removeAll();
        listView.getItems().addAll(player.getPlays());

        close_button.setOnAction(event -> {
            try {
                Model.getInstance().getMultiplayerViewFactory().showActualMatchWindow();
                dashboard.getScene().getWindow().hide();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
