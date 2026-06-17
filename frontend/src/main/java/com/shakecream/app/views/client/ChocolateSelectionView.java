package com.shakecream.app.views.client;

import java.util.List;

import com.shakecream.app.components.ProductItemCard;
import com.shakecream.app.models.Product;
import com.shakecream.app.services.ProductService;

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
    lbTituloHeader.setStyle(
        "-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 26; -fx-font-weight: bold;");

    header.getChildren().addAll(lbTituloHeader, leftBox);
    root.setTop(header);

    // --- LISTA DE PRODUTOS ---
    VBox listContainer = new VBox(18);
    listContainer.setPadding(new Insets(40, 0, 40, 0));
    listContainer.setAlignment(Pos.TOP_CENTER);

    ProductService productService = new ProductService();
    List<Product> products = productService.getAll();
    for (Product product : products) {

      ProductItemCard card = new ProductItemCard(
          product.getName(),
          product.getDescription(),
          "R$ " + String.format("%.2f", product.getPrice()).replace(".", ","),
          product.getImageUrl());

      card.setOnAction(() -> new ProductDetailsView().show(
          stage,
          product.getName(),
          product.getPrice(),
          product.getImageUrl(),
          product.getDescription(),
          product.getCategoryName()
      ));

      listContainer.getChildren().add(card);
    }

    ScrollPane scroll = new ScrollPane(listContainer);
    scroll.setFitToWidth(true);
    scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

    root.setCenter(scroll);
    stage.getScene().setRoot(root);
  }
}