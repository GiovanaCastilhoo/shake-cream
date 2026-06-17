package com.shakecream.app.views.client;

import com.shakecream.app.components.FlavorCard;
import com.shakecream.app.ui.Theme;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class FlavorSelectionView {

    public void show(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + Theme.COLOR_BG_WORKSPACE + ";");

        // Header Rosa
        HBox header = new HBox();
        header.setPadding(new Insets(0, 30, 0, 30));
        header.setPrefHeight(80);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: " + Theme.COLOR_PRIMARY + ";");

        Button btnVoltar = new Button("←  Voltar");
        btnVoltar.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18; -fx-font-family: 'Montserrat'; -fx-cursor: hand;");
        btnVoltar.setOnAction(e -> new CategorySelectionView().start(stage));

        header.getChildren().add(btnVoltar);
        root.setTop(header);

        // Conteúdo Central
        VBox centerContent = new VBox(30);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(20));

        Label lbTitulo = new Label("Escolha o sabor");
        lbTitulo.setStyle(
                "-fx-font-family: 'Montserrat'; -fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #3E2723;");

        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(30);
        grid.setAlignment(Pos.CENTER);

        FlavorCard cardChocolate = new FlavorCard("Chocolate", "Os clássicos\nque você ama", "chocolate.png",
                "#3E2723");
        FlavorCard cardMorango = new FlavorCard("Morango", "Frutados e\nirresistíveis", "morango.png", "#B95C68");
        FlavorCard cardBaunilha = new FlavorCard("Baunilha", "Leves e\ncremosos", "baunilha.png", "#3E2723");
        FlavorCard cardEspeciais = new FlavorCard("Especiais", "Combinações\nincríveis", "especiais.png", "#3E2723");
        cardChocolate.setOnAction(() -> new ChocolateSelectionView().show(stage));
        cardMorango.setOnAction(() -> new StrawberrySelectionView().show(stage));
        cardBaunilha.setOnAction(() -> new VanillaSelectionView().show(stage));
        cardEspeciais.setOnAction(() -> new SpecialSelectionView().show(stage));

        grid.add(cardChocolate, 0, 0);
        grid.add(cardMorango, 1, 0);
        grid.add(cardBaunilha, 0, 1);
        grid.add(cardEspeciais, 1, 1);

        centerContent.getChildren().addAll(lbTitulo, grid);
        root.setCenter(centerContent);

        Scene scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
        stage.setScene(scene);
    }
}