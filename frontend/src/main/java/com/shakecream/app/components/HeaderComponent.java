package com.shakecream.app.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HeaderComponent {

    public static HBox create(String titulo, Stage stage, Runnable onBackAction, String bgColor) {

        HBox topHeader = new HBox();
        topHeader.setPrefHeight(80);
        topHeader.setAlignment(Pos.CENTER_LEFT);
        topHeader.setPadding(new Insets(0, 40, 0, 30));
        topHeader.setStyle("-fx-background-color: " + bgColor + ";");

        // Botão voltar
        Button btnVoltarHeader = new Button("←  Voltar");
        btnVoltarHeader.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 16; -fx-cursor: hand;");

        btnVoltarHeader.setOnAction(e -> {
            if (onBackAction != null) {
                onBackAction.run();
            }
        });

        // Título
        Label lbTituloTopHeader = new Label(titulo);
        lbTituloTopHeader.setStyle(
                "-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 22; -fx-font-weight: bold;");

        Region topSpacer1 = new Region();
        HBox.setHgrow(topSpacer1, Priority.ALWAYS);

        Region topSpacer2 = new Region();
        HBox.setHgrow(topSpacer2, Priority.ALWAYS);

        topHeader.getChildren().addAll(btnVoltarHeader, topSpacer1, lbTituloTopHeader, topSpacer2);

        return topHeader;
    }
}