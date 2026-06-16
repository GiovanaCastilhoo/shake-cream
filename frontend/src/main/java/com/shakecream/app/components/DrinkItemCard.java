package com.shakecream.app.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class DrinkItemCard extends HBox {

    private final String COLOR_PRIMARY = "#B95C68";

    public DrinkItemCard(String nome, String preco, String imageName) {
        // Configurações da Linha (Card)
        this.setPrefHeight(100);
        this.setMaxWidth(900); // Ajuste conforme a largura da tela
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(10, 25, 10, 15));
        this.setSpacing(20);
        this.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-color: #E0DDD9;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-width: 1;"
        );

        // --- 1. CONTAINER DA IMAGEM ---
        StackPane imgContainer = new StackPane();
        imgContainer.setPrefSize(80, 80);
        imgContainer.setStyle("-fx-background-color: #F4F0E6; -fx-background-radius: 15;");

        ImageView imgView = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream("/com/shakecream/app/" + imageName));
            imgView.setImage(img);
            imgView.setFitHeight(60);
            imgView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem da bebida: " + imageName);
        }
        imgContainer.getChildren().add(imgView);

        // --- 2. INFORMAÇÕES (NOME E PREÇO) ---
        VBox infoBox = new VBox(5);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(infoBox, Priority.ALWAYS); // Faz este bloco ocupar o espaço central

        Label lbNome = new Label(nome);
        lbNome.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: 700; -fx-font-size: 18; -fx-text-fill: #4A3B37;");

        Label lbPreco = new Label(preco);
        lbPreco.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: 500; -fx-font-size: 14; -fx-text-fill: #8E8A85;");

        infoBox.getChildren().addAll(lbNome, lbPreco);

        // --- 3. BOTÃO ADICIONAR ---
        Button btnAdd = new Button("Adicionar +");
        btnAdd.setPrefSize(180, 45);
        btnAdd.setStyle(
                "-fx-background-color: " + COLOR_PRIMARY + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Montserrat';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;" +
                        "-fx-background-radius: 15;" +
                        "-fx-cursor: hand;"
        );

        // Ação do botão (Backend integrará aqui)
        btnAdd.setOnAction(e -> System.out.println("Adicionado: " + nome));

        this.getChildren().addAll(imgContainer, infoBox, btnAdd);
    }
}