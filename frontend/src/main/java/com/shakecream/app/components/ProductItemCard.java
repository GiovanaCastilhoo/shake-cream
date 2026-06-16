package com.shakecream.app.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ProductItemCard extends HBox {

    private final String COLOR_PRIMARY = "#B95C68";
    private final Button btnAdd;
    private Runnable clickAction; // Callback seguro para gerenciar a navegação

    public ProductItemCard(String nome, String descricao, String preco, String imageName) {
        // --- CONFIGURAÇÕES DO CARD ---
        this.setPrefHeight(120);
        this.setMaxWidth(950);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(15, 25, 15, 20));
        this.setSpacing(25);
        this.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 25;" +
                        "-fx-border-color: #E0DDD9;" +
                        "-fx-border-radius: 25;" +
                        "-fx-border-width: 1;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 10, 0, 0, 5);"
        );

        // --- 1. IMAGEM DO PRODUTO ---
        StackPane imgContainer = new StackPane();
        imgContainer.setPrefSize(90, 90);
        imgContainer.setStyle("-fx-background-color: #F4F0E6; -fx-background-radius: 15;");

        ImageView imgView = new ImageView();
        try {
            imgView.setImage(new Image(getClass().getResourceAsStream("/com/shakecream/app/" + imageName)));
            imgView.setFitHeight(80);
            imgView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Erro: Imagem " + imageName + " não encontrada no diretório de recursos.");
        }
        imgContainer.getChildren().add(imgView);

        // --- 2. TEXTOS ---
        VBox infoBox = new VBox(3);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        Label lbNome = new Label(nome);
        lbNome.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: 700; -fx-font-size: 19; -fx-text-fill: #4A3B37;");

        Label lbDesc = new Label(descricao);
        lbDesc.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 15; -fx-text-fill: #8E8A85;");

        Label lbPreco = new Label(preco);
        lbPreco.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: 700; -fx-font-size: 15; -fx-text-fill: " + COLOR_PRIMARY + ";");
        VBox.setMargin(lbPreco, new Insets(5, 0, 0, 0));

        infoBox.getChildren().addAll(lbNome, lbDesc, lbPreco);

        // --- 3. BOTÃO ADICIONAR ---
        btnAdd = new Button("Adicionar +");
        btnAdd.setPrefSize(180, 50);
        btnAdd.setStyle(
                "-fx-background-color: " + COLOR_PRIMARY + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Montserrat';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 15;" +
                        "-fx-background-radius: 18;" +
                        "-fx-cursor: hand;"
        );

        // Vincula os cliques de forma unificada e segura
        this.setOnMouseClicked(e -> executarAcao());
        btnAdd.setOnAction(e -> executarAcao());

        this.getChildren().addAll(imgContainer, infoBox, btnAdd);
        this.setCursor(javafx.scene.Cursor.HAND);
    }

    // Define a ação externa (Abrir a tela de detalhes)
    public void setOnAction(Runnable action) {
        this.clickAction = action;
    }

    private void executarAcao() {
        if (this.clickAction != null) {
            this.clickAction.run();
        }
    }
}