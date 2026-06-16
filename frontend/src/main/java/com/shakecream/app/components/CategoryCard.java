package com.shakecream.app.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class CategoryCard extends StackPane {

    private final String COLOR_PRIMARY = "#B95C68";

    public CategoryCard(String titulo, String subTitulo, String imageIdentifier) {
        // Configurações do Container Principal do Card
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

        // --- LAYOUT INTERNO (Conteúdo: Imagem e Texto) ---
        HBox contentLayout = new HBox(15);
        contentLayout.setAlignment(Pos.CENTER_LEFT);

        // --- CONTAINER DA IMAGEM ---
        StackPane imageContainer = new StackPane();
        imageContainer.setMinWidth(180);

        try {
            // O imagePath deve ser o nome do arquivo, ex: "milkshake_categoria.png"
            String fullPath = "/com/shakecream/app/" + imageIdentifier;
            Image img = new Image(getClass().getResourceAsStream(fullPath));

            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(180);
            imgView.setPreserveRatio(true); // Mantém a proporção sem distorcer
            imgView.setSmooth(true);       // Melhora a qualidade ao redimensionar

            imageContainer.getChildren().add(imgView);
        } catch (Exception e) {
            // Caso o arquivo não seja encontrado, mostra um aviso visual simples
            Label lbErro = new Label("Imagem não\nencontrada");
            lbErro.setStyle("-fx-text-fill: #A09A94; -fx-font-size: 10; -fx-text-alignment: center;");
            imageContainer.getChildren().add(lbErro);
            System.err.println("Erro ao carregar imagem: " + imageIdentifier);
        }

        // Textos
        VBox textLayout = new VBox(5);
        textLayout.setAlignment(Pos.CENTER_LEFT);

        Label lbTitle = new Label(titulo);
        lbTitle.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #4A3B37;");

        Label lbSub = new Label(subTitulo);
        lbSub.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 14; -fx-text-fill: #7D7771;");
        lbSub.setWrapText(true);

        textLayout.getChildren().addAll(lbTitle, lbSub);
        contentLayout.getChildren().addAll(imageContainer, textLayout);

        // --- ÍCONE DA SETA (Ancorado no canto inferior direito) ---
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
        Label arrowIcon = new Label(">"); // Pode ser trocado por um ícone SVG no futuro
        arrowIcon.setStyle("-fx-text-fill: " + COLOR_PRIMARY + "; -fx-font-weight: bold; -fx-font-size: 16;");
        arrowBtn.getChildren().add(arrowIcon);

        // Alinhamento da seta no canto inferior direito dentro do StackPane global do card
        StackPane.setAlignment(arrowBtn, Pos.BOTTOM_RIGHT);

        // Adiciona tudo ao card
        this.getChildren().addAll(contentLayout, arrowBtn);

        // Feedback visual de Hover
        this.setOnMouseEntered(e -> this.setStyle(this.getStyle() + "-fx-border-color: " + COLOR_PRIMARY + "; -fx-border-width: 2;"));
        this.setOnMouseExited(e -> this.setStyle(this.getStyle().replace("-fx-border-color: " + COLOR_PRIMARY + "; -fx-border-width: 2;", "-fx-border-color: #E0DDD9; -fx-border-width: 1;")));
    }

    public void setOnAction(Runnable action) {
        this.setOnMouseClicked(e -> action.run());
    }
}