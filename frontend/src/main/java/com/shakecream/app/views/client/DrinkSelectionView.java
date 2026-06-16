package com.shakecream.app.views.client;

import com.shakecream.app.components.ProductItemCard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DrinkSelectionView {

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
        btnVoltar.setOnAction(e -> new CategorySelectionView().show(stage));

        HBox leftBox = new HBox(btnVoltar);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        leftBox.setPickOnBounds(false);

        Label lbTituloHeader = new Label("Bebidas");
        lbTituloHeader.setStyle("-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 26; -fx-font-weight: bold;");

        header.getChildren().addAll(lbTituloHeader, leftBox);
        root.setTop(header);

        // --- LISTA DE PRODUTOS ---
        VBox listContainer = new VBox(18);
        listContainer.setPadding(new Insets(40, 0, 40, 0));
        listContainer.setAlignment(Pos.TOP_CENTER);

        ProductItemCard cardAguaSemGas = new ProductItemCard("Água Sem Gás — 500 ml", "Água mineral natural sem gás", "R$ 2,50", "agua_sem.png");
        ProductItemCard cardAguaComGas = new ProductItemCard("Água Com Gás — 500 ml", "Água mineral natural com gás", "R$ 3,00", "agua_com.png");
        ProductItemCard cardCocaLata = new ProductItemCard("Coca Cola Lata — 350 ml", "Refrigerante Coca Cola lata gelada", "R$ 5,00", "coca.png");
        ProductItemCard cardGuaranaLata = new ProductItemCard("Guaraná Antarctica Lata — 350 ml", "Refrigerante Guaraná Antarctica lata", "R$ 5,00", "guarana.png");
        ProductItemCard cardH2oLimoneto = new ProductItemCard("Refrigerante H2o Limoneto — 500 ml", "Bebida levemente gaseificada sabor limão", "R$ 7,00", "h2o.png");
        ProductItemCard cardSucoNaturalOne = new ProductItemCard("Suco de laranja Natural One — 900 ml", "Suco de laranja 100% natural", "R$ 12,00", "suco_laranja.png");

        // INTEGRAÇÃO REALIZADA: Passando 'true' como 6º parâmetro para sinalizar que o item é uma bebida
        cardAguaSemGas.setOnAction(() ->
                new ProductDetailsView().show(stage, "Água Sem Gás — 500 ml", 2.50, "agua_sem.png", "Água mineral natural sem gás", true)
        );

        cardAguaComGas.setOnAction(() ->
                new ProductDetailsView().show(stage, "Água Com Gás — 500 ml", 3.00, "agua_com.png", "Água mineral natural com gás", true)
        );

        cardCocaLata.setOnAction(() ->
                new ProductDetailsView().show(stage, "Coca Cola Lata — 350 ml", 5.00, "coca.png", "Refrigerante Coca Cola lata gelada", true)
        );

        cardGuaranaLata.setOnAction(() ->
                new ProductDetailsView().show(stage, "Guaraná Antarctica Lata — 350 ml", 5.00, "guarana.png", "Refrigerante Guaraná Antarctica lata", true)
        );

        cardH2oLimoneto.setOnAction(() ->
                new ProductDetailsView().show(stage, "Refrigerante H2o Limoneto — 500 ml", 7.00, "h2o.png", "Bebida levemente gaseificada sabor limão", true)
        );

        cardSucoNaturalOne.setOnAction(() ->
                new ProductDetailsView().show(stage, "Suco de laranja Natural One — 900 ml", 12.00, "suco_laranja.png", "Suco de laranja 100% natural", true)
        );

        listContainer.getChildren().addAll(
                cardAguaSemGas, cardAguaComGas, cardCocaLata,
                cardGuaranaLata, cardH2oLimoneto, cardSucoNaturalOne
        );

        ScrollPane scroll = new ScrollPane(listContainer);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        root.setCenter(scroll);
        stage.getScene().setRoot(root);
    }
}