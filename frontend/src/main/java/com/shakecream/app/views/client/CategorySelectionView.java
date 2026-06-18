package com.shakecream.app.views.client;

import com.shakecream.app.HelloApplication;
import com.shakecream.app.models.Category;
import com.shakecream.app.services.CategoryService;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class CategorySelectionView {

    private final CategoryService categoryService = new CategoryService();

    private final String COLOR_PRIMARY = "#B95C68";
    private final String COLOR_BG = "#FAF6F2";
    private final String COLOR_TEXT_DARK = "#4A3B37";
    private final String COLOR_TEXT_MUTED = "#8E8A85";

    public void start(Stage stage) {
        show(stage);
    }

    public void show(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BG + ";");

        StackPane header = new StackPane();
        header.setPrefHeight(80);
        header.setStyle("-fx-background-color: " + COLOR_PRIMARY + ";");
        header.setPadding(new Insets(0, 30, 0, 30));

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18; -fx-font-family: 'Montserrat'; -fx-cursor: hand;");
        btnVoltar.setOnAction(e -> new HelloApplication().show(stage));
        StackPane.setAlignment(btnVoltar, Pos.CENTER_LEFT);

        Button btnVerPedido = new Button("Ver pedido 🛒");
        btnVerPedido.setStyle("-fx-background-color: #F4EAE6; -fx-text-fill: " + COLOR_PRIMARY + "; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 8 18; -fx-cursor: hand;");
        btnVerPedido.setOnAction(e -> new MyOrderView().show(stage));
        StackPane.setAlignment(btnVerPedido, Pos.CENTER_RIGHT);

        header.getChildren().addAll(btnVoltar, btnVerPedido);
        root.setTop(header);

        VBox centerWrapper = new VBox(45);
        centerWrapper.setAlignment(Pos.CENTER);
        centerWrapper.setPadding(new Insets(10, 0, 0, 0));

        Label lbTituloPrincipal = new Label("Escolha uma categoria");
        lbTituloPrincipal.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 32; -fx-text-fill: " + COLOR_TEXT_DARK + ";");

        HBox cardsContainer = new HBox(40);
        cardsContainer.setAlignment(Pos.CENTER);

        List<Category> categories = categoryService.getCategories();

        if (categories != null && !categories.isEmpty()) {
            for (Category category : categories) {
                String imageName = category.getName().toLowerCase().contains("bebida") ? "bebidas_categoria.png" : "milkshake_categoria.png";
                String description = category.getName().toLowerCase().contains("bebida") ? "Opções para\nrefrescar" : "Escolha o seu sabor\nfavorito";

                StackPane card = criarCardCategoriaFigma(
                        category.getName(),
                        description,
                        imageName,
                        e -> {
                            if (category.getName().toLowerCase().contains("bebida")) {
                                new DrinkSelectionView().show(stage);
                            } else {
                                new FlavorSelectionView().show(stage);
                            }
                        }
                );
                cardsContainer.getChildren().add(card);
            }
        } else {
            StackPane cardMilkshakes = criarCardCategoriaFigma("Milk-Shakes", "Escolha o seu sabor\nfavorito", "milkshake_categoria.png", e -> new FlavorSelectionView().show(stage));
            StackPane cardBebidas = criarCardCategoriaFigma("Bebidas", "Opções para\nrefrescar", "bebidas_categoria.png", e -> new DrinkSelectionView().show(stage));
            cardsContainer.getChildren().addAll(cardMilkshakes, cardBebidas);
        }

        centerWrapper.getChildren().addAll(lbTituloPrincipal, cardsContainer);
        root.setCenter(centerWrapper);
        stage.getScene().setRoot(root);
    }

    private StackPane criarCardCategoriaFigma(String titulo, String descricao, String imageName, javafx.event.EventHandler<javafx.scene.input.MouseEvent> clickEvent) {
        StackPane cardRoot = new StackPane();
        cardRoot.setCursor(javafx.scene.Cursor.HAND);
        cardRoot.setStyle("-fx-background-color: white; -fx-background-radius: 30; -fx-border-color: #E2DDD9; -fx-border-radius: 30; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.02), 15, 0, 0, 6);");
        cardRoot.setPrefSize(460, 220);
        cardRoot.setMaxSize(460, 220);
        cardRoot.setOnMouseClicked(clickEvent);

        HBox internalLayout = new HBox(20);
        internalLayout.setAlignment(Pos.CENTER_LEFT);
        internalLayout.setPadding(new Insets(25));

        ImageView imgView = new ImageView();
        try {
            imgView.setImage(new Image(getClass().getResourceAsStream("/com/shakecream/app/" + imageName)));
            imgView.setFitHeight(150);
            imgView.setFitWidth(160);
            imgView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Erro: Imagem " + imageName + " não localizada.");
        }

        VBox textsBox = new VBox(6);
        textsBox.setAlignment(Pos.CENTER_LEFT);

        Label lbTituloCard = new Label(titulo);
        lbTituloCard.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 28; -fx-text-fill: " + COLOR_TEXT_DARK + ";");

        Label lbDescCard = new Label(descricao);
        lbDescCard.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 16; -fx-text-fill: " + COLOR_TEXT_MUTED + "; -fx-line-spacing: 2;");

        textsBox.getChildren().addAll(lbTituloCard, lbDescCard);
        internalLayout.getChildren().addAll(imgView, textsBox);

        Button btnSeta = new Button(">");
        btnSeta.setMouseTransparent(true);
        btnSeta.setStyle("-fx-background-color: white; -fx-border-color: " + COLOR_PRIMARY + "; -fx-border-radius: 50; -fx-background-radius: 50; -fx-text-fill: " + COLOR_PRIMARY + "; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 16; -fx-min-width: 42; -fx-max-width: 42; -fx-min-height: 42; -fx-max-height: 42;");

        cardRoot.getChildren().addAll(internalLayout, btnSeta);
        StackPane.setAlignment(btnSeta, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(btnSeta, new Insets(0, 25, 25, 0));

        cardRoot.setOnMouseEntered(e -> cardRoot.setStyle("-fx-background-color: white; -fx-background-radius: 30; -fx-border-radius: 30; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.02), 15, 0, 0, 6); -fx-border-color: " + COLOR_PRIMARY + "; -fx-border-width: 1.5;"));
        cardRoot.setOnMouseExited(e -> cardRoot.setStyle("-fx-background-color: white; -fx-background-radius: 30; -fx-border-radius: 30; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.02), 15, 0, 0, 6); -fx-border-color: #E2DDD9; -fx-border-width: 1;"));

        return cardRoot;
    }

    public static void main(String[] args) {
        javafx.application.Application.launch(HelloApplication.class, args);
    }
}