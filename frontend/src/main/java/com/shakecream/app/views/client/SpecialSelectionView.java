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

public class SpecialSelectionView {

  private final ProductService productService = new ProductService();

  public void show(Stage stage, int categoryId) {

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
    btnVoltar.setOnAction(e -> new FlavorSelectionView().show(stage, categoryId));

    HBox leftBox = new HBox(btnVoltar);
    leftBox.setAlignment(Pos.CENTER_LEFT);
    leftBox.setPickOnBounds(false);

    Label title = new Label("Especiais");
    title.setStyle(
        "-fx-text-fill: white; -fx-font-size: 26; -fx-font-weight: bold;");

    header.getChildren().addAll(title, leftBox);
    root.setTop(header);

    // LISTA
    VBox listContainer = new VBox(18);
    listContainer.setPadding(new Insets(40, 0, 40, 0));
    listContainer.setAlignment(Pos.TOP_CENTER);

    List<Product> products = productService.getByCategoryId(categoryId);

    if (products == null || products.isEmpty()) {
      listContainer.getChildren().add(new Label("Nenhum produto encontrado."));
    } else {

      for (Product prod : products) {
        if (prod == null)
          continue;

        String name = prod.getName();
        if (name == null)
          continue;

        String n = name.toLowerCase().trim();

        boolean isEspecial = !n.startsWith("morango") &&
            !n.startsWith("chocolate") &&
            !n.startsWith("baunilha");

        if (!isEspecial)
          continue;

        String descricao = prod.getDescription();
        double preco = prod.getPrice();
        String imagem = prod.getImageUrl();

        String finalImagem = (imagem == null || imagem.isBlank())
            ? "morango_tradicional.png"
            : imagem;

        ProductItemCard card = new ProductItemCard(
            name,
            descricao,
            "R$ " + String.format("%.2f", preco),
            finalImagem);

        card.setOnAction(() -> new ProductDetailsView().show(
            stage,
            name,
            preco,
            finalImagem,
            descricao,
            "Especiais",
            prod.getCategoryId()));

        listContainer.getChildren().add(card);
      }
    }

    ScrollPane scroll = new ScrollPane(listContainer);
    scroll.setFitToWidth(true);
    scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

    root.setCenter(scroll);

    if (stage.getScene() == null) {
      stage.setScene(new javafx.scene.Scene(root, 900, 600));
    } else {
      stage.getScene().setRoot(root);
    }
  }
}