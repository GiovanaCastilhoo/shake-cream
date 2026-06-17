package com.shakecream.app.views.client;

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
import java.util.List;

public class DrinkSelectionView {

    private final ProductService productService = new ProductService();

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
        // Ajuste aqui caso o botão voltar das bebidas precise ir para a CategorySelectionView ao invés de sabores
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

        // Chamando o método .getAll() do Service
        List<Product> totalProducts = productService.getAll();

        if (totalProducts != null) {
            for (int i = 0; i < totalProducts.size(); i++) {
                Product prod = totalProducts.get(i);
                if (prod == null) continue;

                // Filtrando apenas por Bebidas
                if (prod.getCategoryName() != null && prod.getCategoryName().equalsIgnoreCase("Bebida")) {
                    
                    String nome = prod.getName();
                    String description = prod.getDescription();
                    double preco = prod.getPrice();
                    String imagem = prod.getImageUrl();

                    ProductItemCard card = new ProductItemCard(nome, description, "R$ " + String.format("%.2f", preco), imagem);

                    card.setOnAction(() ->
                            new ProductDetailsView().show(stage, nome, preco, imagem, description, "Bebida")
                    );

                    listContainer.getChildren().add(card);
                }
            }
        }

        ScrollPane scroll = new ScrollPane(listContainer);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        root.setCenter(scroll);
        stage.getScene().setRoot(root);
    }
}