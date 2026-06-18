package com.shakecream.app.views.client;

import com.shakecream.app.components.ProductItemCard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SpecialSelectionView {

        public void show(Stage stage, int categoryId) {
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
                btnVoltar.setOnAction(e -> new FlavorSelectionView().show(stage, categoryId));

                HBox leftBox = new HBox(btnVoltar);
                leftBox.setAlignment(Pos.CENTER_LEFT);
                leftBox.setPickOnBounds(false);

                Label lbTituloHeader = new Label("Especiais");
                lbTituloHeader.setStyle(
                                "-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 26; -fx-font-weight: bold;");

                header.getChildren().addAll(lbTituloHeader, leftBox);
                root.setTop(header);

                // --- LISTA DE PRODUTOS ---
                VBox listContainer = new VBox(18);
                listContainer.setPadding(new Insets(40, 0, 40, 0));
                listContainer.setAlignment(Pos.TOP_CENTER);

                // // Instanciação dos produtos especiais do cardápio
                // ProductItemCard cardNinhoNutella = new ProductItemCard("Kinder Bueno Shake",
                // "Kinder Bueno batido com Nutella original", "R$ 18,00", "kinder_bueno.png");
                // ProductItemCard cardOreoSupremo = new ProductItemCard("Ferrero Rocher Shake",
                // "Bombom de avelã com calda premium de chocolate", "R$ 17,00", "ferrero.png");
                // ProductItemCard cardKinderBueno = new ProductItemCard("Ovomaltine Supreme",
                // "Base super cremosa com Ovomaltine crocante", "R$ 20,00",
                // "ovomaltine_supreme.png");
                // ProductItemCard cardChurros = new ProductItemCard("Banoffee Shake",
                // "Doce de leite, banana fresca e biscoito", "R$ 16,00", "banoffee.png");

                // // Vinculando as ações e enviando o parâmetro contextual "Especiais" no final
                // cardNinhoNutella.setOnAction(() -> new ProductDetailsView().show(stage,
                // "Kinder Bueno Shake", 18.00,
                // "kinder_bueno.png", "Milk shake com chocolate Kinder Bueno", "Especiais"));

                // cardOreoSupremo.setOnAction(() -> new ProductDetailsView().show(stage,
                // "Ferrero Rocher Shake", 17.00,
                // "ferrero.png", "Milk shake inspirado no Ferrero", "Especiais"));

                // cardKinderBueno.setOnAction(() -> new ProductDetailsView().show(stage,
                // "Ovomaltine Supreme", 20.00,
                // "ovomaltine_supreme.png", "Milk shake de ovomaltine extra crocante",
                // "Especiais"));

                // cardChurros.setOnAction(() -> new ProductDetailsView().show(stage, "Banoffee
                // Shake", 16.00,
                // "banoffee.png", "Milk shake de banana com doce de leite", "Especiais"));

                // listContainer.getChildren().addAll(cardNinhoNutella, cardOreoSupremo,
                // cardKinderBueno, cardChurros);

                // ScrollPane scroll = new ScrollPane(listContainer);
                // scroll.setFitToWidth(true);
                // scroll.setStyle("-fx-background: transparent; -fx-background-color:
                // transparent;");

                // root.setCenter(scroll);
                stage.getScene().setRoot(root);
        }
}