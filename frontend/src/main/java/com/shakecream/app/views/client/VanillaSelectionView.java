package com.shakecream.app.views.client;

import com.shakecream.app.components.ProductItemCard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VanillaSelectionView {

    public void show(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #FAF6F2;");

        // --- HEADER COM TÍTULO CENTRALIZADO ---
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

        Label lbTituloHeader = new Label("Baunilha");
        lbTituloHeader.setStyle("-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 26; -fx-font-weight: bold;");

        header.getChildren().addAll(lbTituloHeader, leftBox);
        root.setTop(header);

        // --- LISTA DE PRODUTOS ---
        VBox listContainer = new VBox(18);
        listContainer.setPadding(new Insets(40, 0, 40, 0));
        listContainer.setAlignment(Pos.TOP_CENTER);

        ProductItemCard cardBaunilhaTrad = new ProductItemCard("Baunilha Tradicional", "Clássico sabor baunilha", "R$ 13,00", "baunilha_tradicional.png");
        ProductItemCard cardBaunilhaOreo = new ProductItemCard("Baunilha com Oreo", "Com pedaços de Oreo", "R$ 15,00", "baunilha_oreo.png");
        ProductItemCard cardBaunilhaCaramelo = new ProductItemCard("Baunilha com Caramelo", "Com calda de caramelo", "R$ 16,00", "baunilha_caramelo.png");
        ProductItemCard cardBaunilhaChoc = new ProductItemCard("Baunilha com Chocolate", "Baunilha com chocolate", "R$ 16,00", "baunilha_chocolate.png");

        // ✨ CORREÇÃO AQUI: Adicionado o parâmetro "Baunilha" no final de cada chamada show()
        cardBaunilhaTrad.setOnAction(() ->
                new ProductDetailsView().show(stage, "Baunilha Tradicional", 13.00, "baunilha_tradicional.png", "Clássico sabor baunilha", "Baunilha")
        );

        cardBaunilhaOreo.setOnAction(() ->
                new ProductDetailsView().show(stage, "Baunilha com Oreo", 15.00, "baunilha_oreo.png", "Com pedaços de Oreo", "Baunilha")
        );

        cardBaunilhaCaramelo.setOnAction(() ->
                new ProductDetailsView().show(stage, "Baunilha com Caramelo", 16.00, "baunilha_caramelo.png", "Com calda de caramelo", "Baunilha")
        );

        cardBaunilhaChoc.setOnAction(() ->
                new ProductDetailsView().show(stage, "Baunilha com Chocolate", 16.00, "baunilha_chocolate.png", "Baunilha com chocolate", "Baunilha")
        );

        listContainer.getChildren().addAll(cardBaunilhaTrad, cardBaunilhaOreo, cardBaunilhaCaramelo, cardBaunilhaChoc);

        ScrollPane scroll = new ScrollPane(listContainer);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        root.setCenter(scroll);
        stage.getScene().setRoot(root);
    }
}