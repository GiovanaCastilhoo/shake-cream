package com.shakecream.app.views.client;

import com.shakecream.app.HelloApplication;
import com.shakecream.app.state.CarrinhoGlobal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class OrderConfirmationView {

    private final String COLOR_PRIMARY = "#B95C68";  
    private final String COLOR_BG = "#FAF6F2";        
    private final String COLOR_TEXT_DARK = "#4A3B37";  
    private final String COLOR_CORAL = "#F27B88";   

    // ✨ AGORA RECEBE A MENSAGEM CUSTOMIZADA DA FORMA DE PAGAMENTO SELECIONADA
    public void show(Stage stage, String statusMensagem) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BG + ";");

        // --- HEADER (CABEÇALHO) ---
        StackPane header = new StackPane();
        header.setPrefHeight(80);
        header.setStyle("-fx-background-color: " + COLOR_PRIMARY + ";");
        header.setPadding(new Insets(0, 40, 0, 40));

        Button btnSair = new Button("Sair  ⎋");
        btnSair.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20;" +
                        "-fx-font-family: 'Montserrat';" +
                        "-fx-cursor: hand;"
        );
        // Procure por essa linha dentro do botão btnSair na OrderConfirmationView e deixe assim:
        btnSair.setOnAction(e -> {
            CarrinhoGlobal.getInstancia().getItens().clear();
            new HelloApplication().show(stage); // Retorna com sucesso para a tela de nome/mesa!
        });
        StackPane.setAlignment(btnSair, Pos.CENTER_RIGHT);

        header.getChildren().add(btnSair);
        root.setTop(header);

        // --- CONTEÚDO CENTRAL ---
        VBox centerContent = new VBox(28);
        centerContent.setAlignment(Pos.CENTER);

        // Círculo Coral com o Checkmark
        StackPane circleCheck = new StackPane();
        circleCheck.setPrefSize(90, 90);
        circleCheck.setMaxSize(90, 90);
        circleCheck.setStyle(
                "-fx-background-color: " + COLOR_CORAL + ";" +
                        "-fx-background-radius: 100;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(242,123,136,0.2), 15, 0, 0, 5);"
        );

        Label lbCheck = new Label("✓");
        lbCheck.setStyle("-fx-text-fill: white; -fx-font-size: 42; -fx-font-weight: bold;");
        circleCheck.getChildren().add(lbCheck);

        // Textos de Confirmação
        VBox textContainer = new VBox(12);
        textContainer.setAlignment(Pos.CENTER);

        // ✨ LABEL DINÂMICA: Exibe o texto correto enviado pela tela de pagamento
        Label lbStatus = new Label(statusMensagem);
        lbStatus.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 18; -fx-text-fill: " + COLOR_TEXT_DARK + ";");

        Label lbAgradecimento = new Label("Obrigado! Seu pedido será preparado.");
        lbAgradecimento.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 36; -fx-text-fill: " + COLOR_TEXT_DARK + ";");

        textContainer.getChildren().addAll(lbStatus, lbAgradecimento);

        // Bloco do Tempo Estimado
        HBox tempoContainer = new HBox(10);
        tempoContainer.setAlignment(Pos.CENTER);

        Label lbAmpulheta = new Label("⏳");
        lbAmpulheta.setStyle("-fx-font-size: 22; -fx-text-fill: " + COLOR_TEXT_DARK + ";");

        Label lbTempoEstimado = new Label("Tempo estimado: 10 - 15 minutos.");
        lbTempoEstimado.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 18; -fx-text-fill: " + COLOR_TEXT_DARK + ";");

        tempoContainer.getChildren().addAll(lbAmpulheta, lbTempoEstimado);

        centerContent.getChildren().addAll(circleCheck, textContainer, tempoContainer);
        root.setCenter(centerContent);

        stage.getScene().setRoot(root);
    }
}