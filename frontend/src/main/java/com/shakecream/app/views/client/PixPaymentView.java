package com.shakecream.app.views.client;

import com.shakecream.app.HelloApplication;
import com.shakecream.app.models.ItemCarrinho;
import com.shakecream.app.state.CarrinhoGlobal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class PixPaymentView {

    private final String COLOR_PRIMARY = "#B95C68"; // Vinho do Figma
    private final String COLOR_BG = "#FAF6F2"; // Fundo Creme
    private final String COLOR_TEXT_DARK = "#4A3B37"; // Marrom Escuro
    private final String COLOR_TEXT_MUTED = "#8E8A85"; // Cinza das Descrições

    public void show(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BG + ";");

        List<ItemCarrinho> itensNoCarrinho = CarrinhoGlobal.getInstancia().getItens();
        double subtotal = itensNoCarrinho.stream().mapToDouble(ItemCarrinho::getValorTotal).sum();
        double totalGeral = subtotal > 0 ? subtotal + 3.00 : 0.0; // Subtotal + taxa de serviço

        StackPane header = new StackPane();
        header.setPrefHeight(80);
        header.setStyle("-fx-background-color: " + COLOR_PRIMARY + ";");
        header.setPadding(new Insets(0, 40, 0, 40));

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18; -fx-font-family: 'Montserrat'; -fx-cursor: hand;");
        btnVoltar.setOnAction(e -> new PaymentView().show(stage));
        StackPane.setAlignment(btnVoltar, Pos.CENTER_LEFT);

        Label lbTituloHeader = new Label("Pagamento via Pix");
        lbTituloHeader.setStyle(
                "-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 24; -fx-font-weight: bold;");
        StackPane.setAlignment(lbTituloHeader, Pos.CENTER);

        Button btnSair = new Button("Sair  ⎋");
        btnSair.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18; -fx-font-family: 'Montserrat'; -fx-cursor: hand;");
        btnSair.setOnAction(e -> {
            CarrinhoGlobal.getInstancia().getItens().clear();
            new HelloApplication().show(stage);
        });
        StackPane.setAlignment(btnSair, Pos.CENTER_RIGHT);

        header.getChildren().addAll(btnVoltar, lbTituloHeader, btnSair);
        root.setTop(header);

        VBox centerContent = new VBox(25);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(30));

        Label lbInstrucao = new Label("Abra o app do seu banco e escaneie o QR Code:");
        lbInstrucao.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: "
                + COLOR_TEXT_DARK + ";");

        StackPane cardQRCode = new StackPane();
        cardQRCode.setPrefSize(300, 300);
        cardQRCode.setMaxSize(300, 300);
        cardQRCode.setStyle(
                "-fx-background-color: white; -fx-background-radius: 25; -fx-border-color: #E2DDD9; -fx-border-radius: 25; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 15, 0, 0, 6);");

        ImageView qrView = new ImageView();
        try {
            qrView.setImage(new Image(getClass().getResourceAsStream("/com/shakecream/app/qrcode.png")));
            qrView.setFitHeight(250);
            qrView.setPreserveRatio(true);
            cardQRCode.getChildren().add(qrView);
        } catch (Exception e) {
            Label lbPlaceholder = new Label("[ Imagem do QR Code ]\nsalvar em resources/com/shakecream/app/qrcode.png");
            lbPlaceholder.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 12; -fx-text-fill: "
                    + COLOR_TEXT_MUTED + "; -fx-alignment: center;");
            cardQRCode.getChildren().add(lbPlaceholder);
        }

        Label lbValorTotal = new Label("Valor do Pix: R$ " + String.format("%.2f", totalGeral));
        lbValorTotal.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 28; -fx-text-fill: "
                + COLOR_PRIMARY + ";");

        Button btnSimularAprovacao = new Button("Simular Aprovação do Pix");
        btnSimularAprovacao.setPrefHeight(50);
        btnSimularAprovacao.setPrefWidth(260);
        btnSimularAprovacao.setStyle("-fx-background-color: #E2DDD9; -fx-text-fill: " + COLOR_TEXT_DARK
                + "; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 12; -fx-cursor: hand;");
        btnSimularAprovacao.setOnAction(e -> new OrderConfirmationView().show(stage, "Pedido confirmado via Pix!"));

        centerContent.getChildren().addAll(lbInstrucao, cardQRCode, lbValorTotal, btnSimularAprovacao);
        root.setCenter(centerContent);

        stage.getScene().setRoot(root);
    }
}