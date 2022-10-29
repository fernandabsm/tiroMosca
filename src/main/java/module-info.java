module com.tiromosca.network {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.apache.commons.lang3;
    requires lombok;


    opens com.tiromosca.network to javafx.fxml;
    exports com.tiromosca.network;
    exports com.tiromosca.network.controller;
    exports com.tiromosca.network.singleplayer.controller;
}