package com.shakecream.app.views.client;

import com.shakecream.app.components.ProductItemCard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ChocolateSelectionView {

  public void show(Stage stage) {
    BorderPane root = new BorderPane();
    root.setStyle("-fx-background-color: #FAF6F2;");

    // --- HEADER COM TÍTULO CENTRALIZADO ---
    StackPane header = new StackPane();
    header.setPadding(new Insets(0, 30, 0, 30));
    header.setPrefHeight(80);
    header.setStyle("-fx-background-color: #B95C68;");

    Button btnVoltar = new Button("←  Voltar");
    btnVoltar.setStyle(
        "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18; -fx-font-family: 'Montserrat'; -fx-cursor: hand;");
    btnVoltar.setOnAction(e -> new FlavorSelectionView().show(stage));

    HBox leftBox = new HBox(btnVoltar);
    leftBox.setAlignment(Pos.CENTER_LEFT);
    leftBox.setPickOnBounds(false);

    Label lbTituloHeader = new Label("Chocolate");
    lbTituloHeader
        .setStyle("-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 26; -fx-font-weight: bold;");

    header.getChildren().addAll(lbTituloHeader, leftBox);
    root.setTop(header);

    // --- LISTA DE PRODUTOS ---
    VBox listContainer = new VBox(18);
    listContainer.setPadding(new Insets(40, 0, 40, 0));
    listContainer.setAlignment(Pos.TOP_CENTER);

    ProductItemCard cardTradicional = new ProductItemCard("Chocolate Tradicional", "Milk shake cremoso de chocolate",
        "R$ 13,00", "choc_tradicional.png");
    ProductItemCard cardOvomaltine = new ProductItemCard("Chocolate com Ovomaltine", "Com crocante de Ovomaltine",
        "R$ 15,00", "choc_ovomaltine.png");
    ProductItemCard cardBrownie = new ProductItemCard("Chocolate com Brownie", "Com pedaços de brownie", "R$ 17,00",
        "choc_brownie.png");
    ProductItemCard cardBelga = new ProductItemCard("Chocolate Belga", "Feito com chocolate belga", "R$ 18,00",
        "choc_belga.png");
    ProductItemCard cardNutella = new ProductItemCard("Chocolate com Nutella", "Com muita Nutella", "R$ 20,00",
        "choc_nutella.png");

    // ✨ CORREÇÃO AQUI: Adicionado o parâmetro "Chocolate" no final de cada chamada
    // show()
    cardTradicional.setOnAction(() -> new ProductDetailsView().show(stage, "Chocolate Tradicional", 13.00,
        "choc_tradicional.png", "Milk shake cremoso de chocolate", "Chocolate"));

    cardOvomaltine.setOnAction(() -> new ProductDetailsView().show(stage, "Chocolate com Ovomaltine", 15.00,
        "choc_ovomaltine.png", "Com crocante de Ovomaltine", "Chocolate"));

    cardBrownie.setOnAction(() -> new ProductDetailsView().show(stage, "Chocolate com Brownie", 17.00,
        "choc_brownie.png", "Com pedaços de brownie", "Chocolate"));

    cardBelga.setOnAction(() -> new ProductDetailsView().show(stage, "Chocolate Belga", 18.00, "choc_belga.png",
        "Feito com chocolate belga", "Chocolate"));

    cardNutella.setOnAction(() -> new ProductDetailsView().show(stage, "Chocolate com Nutella", 20.00,
        "choc_nutella.png", "Com muita Nutella", "Chocolate"));

    listContainer.getChildren().addAll(cardTradicional, cardOvomaltine, cardBrownie, cardBelga, cardNutella);

    ScrollPane scroll = new ScrollPane(listContainer);
    scroll.setFitToWidth(true);
    scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

    root.setCenter(scroll);
    stage.getScene().setRoot(root);
  }
}