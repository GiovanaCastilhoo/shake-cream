package com.shakecream.app.views.client;

import com.shakecream.app.components.ProductItemCard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StrawberrySelectionView {

    public void show(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #FAF6F2;");

        // --- HEADER ---
        StackPane header = new StackPane();
        header.setPadding(new Insets(0, 30, 0, 30));
        header.setPrefHeight(80);
        header.setStyle("-fx-background-color: #B95C68;");

        Button btnVoltar = new Button("←  Voltar");
        btnVoltar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18; -fx-font-family: 'Montserrat'; -fx-cursor: hand;");
        btnVoltar.setOnAction(e -> new FlavorSelectionView().show(stage));

        HBox leftBox = new HBox(btnVoltar);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        leftBox.setPickOnBounds(false);

        Label lbTituloHeader = new Label("Morango");
        lbTituloHeader.setStyle("-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 26; -fx-font-weight: bold;");

        header.getChildren().addAll(lbTituloHeader, leftBox);
        root.setTop(header);

        // --- LISTA DE PRODUTOS ---
        VBox listContainer = new VBox(18);
        listContainer.setPadding(new Insets(40, 0, 40, 0));
        listContainer.setAlignment(Pos.TOP_CENTER);

        // 1. Instanciação dos 4 produtos exatos do seu protótipo do Figma
        ProductItemCard cardTradicional = new ProductItemCard("Morango Tradicional", "Milk shake de morango natural", "R$ 13,00", "morango_tradicional.png");
        ProductItemCard cardNutella = new ProductItemCard("Morango com Nutella", "Morango com Nutella", "R$ 15,00", "morango_nutella.png");
        ProductItemCard cardLeiteNinho = new ProductItemCard("Morango com Leite Ninho", "Morango com Leite em pó", "R$ 17,00", "morango_ninho.png");
        ProductItemCard cardChocolate = new ProductItemCard("Morango com Chocolate", "Morango com chocolate", "R$ 18,00", "morango_chocolate.png");

        // 2. CORREÇÃO AQUI: Adicionado o parâmetro "Morango" no final de cada chamada show()
        cardTradicional.setOnAction(() ->
                new ProductDetailsView().show(stage, "Morango Tradicional", 13.00, "morango_tradicional.png", "Milk shake de morango natural", "Morango")
        );

        cardNutella.setOnAction(() ->
                new ProductDetailsView().show(stage, "Morango com Nutella", 15.00, "morango_nutella.png", "Morango com Nutella", "Morango")
        );

        cardLeiteNinho.setOnAction(() ->
                new ProductDetailsView().show(stage, "Morango com Leite Ninho", 17.00, "morango_ninho.png", "Morango com Leite em pó", "Morango")
        );

        cardChocolate.setOnAction(() ->
                new ProductDetailsView().show(stage, "Morango com Chocolate", 18.00, "morango_chocolate.png", "Morango com chocolate", "Morango")
        );

        // 3. Adiciona os 4 cards restaurados ao container de exibição
        listContainer.getChildren().addAll(cardTradicional, cardNutella, cardLeiteNinho, cardChocolate);

        ScrollPane scroll = new ScrollPane(listContainer);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        root.setCenter(scroll);
        stage.getScene().setRoot(root);
    }
}