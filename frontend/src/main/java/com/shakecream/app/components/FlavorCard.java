package com.shakecream.app.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class FlavorCard extends StackPane {

    private final String COLOR_PRIMARY = "#B95C68";

    public FlavorCard(String titulo, String descricao, String imagePath, String titleColor) {
        // Configurações do Card
        this.setPrefSize(420, 240);
        this.setMaxSize(420, 240);
        this.setCursor(Cursor.HAND);
        this.setPadding(new Insets(20));
        this.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 25;" +
                        "-fx-border-color: #E0DDD9;" +
                        "-fx-border-radius: 25;" +
                        "-fx-border-width: 1;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);"
        );

        // Layout Horizontal (Imagem + Textos)
        HBox mainLayout = new HBox(15);
        mainLayout.setAlignment(Pos.CENTER_LEFT);

        // Imagem
        ImageView imgView = new ImageView();
        try {
            imgView.setImage(new Image(getClass().getResourceAsStream("/com/shakecream/app/" + imagePath)));
            imgView.setFitWidth(160);
            imgView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem do sabor: " + imagePath);
        }

        // Textos (Título e Descrição)
        VBox textLayout = new VBox(5);
        textLayout.setAlignment(Pos.CENTER_LEFT);

        Label lbTitle = new Label(titulo);
        // Aplica Montserrat Medium (500) se estiver carregada, ou Bold como fallback
        lbTitle.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: 500; -fx-font-size: 26; -fx-text-fill: " + titleColor + ";");

        Label lbDesc = new Label(descricao);
        lbDesc.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 15; -fx-text-fill: #7D7771;");
        lbDesc.setWrapText(true);

        textLayout.getChildren().addAll(lbTitle, lbDesc);
        mainLayout.getChildren().addAll(imgView, textLayout);

        // Ícone de Seta (Canto Inferior Direito)
        StackPane arrowBtn = new StackPane();
        arrowBtn.setPrefSize(40, 40);
        arrowBtn.setMaxSize(40, 40);
        arrowBtn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 50;" +
                        "-fx-border-color: " + COLOR_PRIMARY + ";" +
                        "-fx-border-radius: 50;" +
                        "-fx-border-width: 1.5;"
        );
        Label arrow = new Label(">");
        arrow.setStyle("-fx-text-fill: " + COLOR_PRIMARY + "; -fx-font-weight: bold; -fx-font-size: 16;");
        arrowBtn.getChildren().add(arrow);

        // Posiciona a seta no canto inferior direito do StackPane principal
        StackPane.setAlignment(arrowBtn, Pos.BOTTOM_RIGHT);

        this.getChildren().addAll(mainLayout, arrowBtn);

        // Efeito de Hover
        this.setOnMouseEntered(e -> this.setStyle(this.getStyle() + "-fx-border-color: " + COLOR_PRIMARY + "; -fx-border-width: 2;"));
        this.setOnMouseExited(e -> this.setStyle(this.getStyle().replace("-fx-border-color: " + COLOR_PRIMARY + "; -fx-border-width: 2;", "-fx-border-color: #E0DDD9; -fx-border-width: 1;")));
    }

    public void setOnAction(Runnable action) {
        this.setOnMouseClicked(e -> action.run());
    }
}