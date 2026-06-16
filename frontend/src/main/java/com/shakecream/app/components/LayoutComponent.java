package com.shakecream.app.components;

import javafx.scene.layout.BorderPane;

public class LayoutComponent {

    public static BorderPane createMainLayout(String bgColor) {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: " + bgColor + ";");
        return layout;
    }
}