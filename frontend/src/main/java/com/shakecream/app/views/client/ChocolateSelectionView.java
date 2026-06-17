package com.shakecream.app.views.client;

import com.shakecream.app.components.ProductItemCard;
import com.shakecream.app.models.Product;
import com.shakecream.app.services.ProductService;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class ChocolateSelectionView {

  private final ProductService productService = new ProductService();

  public void show(Stage stage) {
    BorderPane root = new BorderPane();
    root.setStyle("-fx-background-color: #FAF6F2;");

    // HEADER
    StackPane header = new StackPane();
    header.setPadding(new Insets(0, 30, 0, 30));
    header.setPrefHeight(80);
    header.setStyle("-fx-background-color: #B95C68;");

    Button btnVoltar = new Button("← Voltar");
    btnVoltar.setStyle(
        "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18; -fx-cursor: hand;");
    btnVoltar.setOnAction(e -> new FlavorSelectionView().show(stage));

    HBox leftBox = new HBox(btnVoltar);
    leftBox.setAlignment(Pos.CENTER_LEFT);

    Label titulo = new Label("Chocolate");
    titulo.setStyle("-fx-text-fill: white; -fx-font-size: 26; -fx-font-weight: bold;");

    header.getChildren().addAll(titulo, leftBox);
    root.setTop(header);

    // CONTAINER
    VBox listContainer = new VBox(18);
    listContainer.setPadding(new Insets(40));
    listContainer.setAlignment(Pos.TOP_CENTER);

    List<Product> products = productService.getAll();

    if (products == null || products.isEmpty()) {
      Label empty = new Label("Nenhum produto encontrado.");
      listContainer.getChildren().add(empty);
    } else {

      for (Product prod : products) {
        if (prod == null)
          continue;

        String category = prod.getCategoryName();

        System.out.println("CATEGORY RAW: [" + category + "]");

        if (category == null)
          continue;

        String name = prod.getName();

        if (name != null && name.toLowerCase().trim().startsWith("chocolate")) {

          String nome = prod.getName();
          String description = prod.getDescription();
          double preco = prod.getPrice();
          String imagem = prod.getImageUrl();

          ProductItemCard card = new ProductItemCard(
              nome,
              description,
              "R$ " + String.format("%.2f", preco),
              imagem);

          card.setOnAction(() -> new ProductDetailsView().show(
              stage,
              nome,
              preco,
              imagem,
              description,
              "Chocolate"));

          listContainer.getChildren().add(card);
        }
      }
    }

    ScrollPane scroll = new ScrollPane(listContainer);
    scroll.setFitToWidth(true);
    scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

    root.setCenter(scroll);

    if (stage.getScene() == null) {
      stage.setScene(new Scene(root, 900, 600));
    } else {
      stage.getScene().setRoot(root);
    }
  }
}