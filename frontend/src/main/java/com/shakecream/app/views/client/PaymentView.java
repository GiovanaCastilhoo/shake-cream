package com.shakecream.app.views.client;

import com.shakecream.app.models.Additional;
import com.shakecream.app.models.ItemCarrinho;
import com.shakecream.app.state.CarrinhoGlobal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentView {

    private final String COLOR_PRIMARY = "#B95C68";   // Vinho do Figma
    private final String COLOR_BG = "#FAF6F2";        // Fundo Creme
    private final String COLOR_TEXT_DARK = "#4A3B37";  // Marrom Escuro
    private final String COLOR_TEXT_MUTED = "#8E8A85"; // Cinza das Descrições

    private double subtotal = 0.0;
    private final double TAXA_SERVICO = 3.00;
    private double totalGeral = 0.0;

    private String formaPagamentoSelecionada = "credito";
    private Button btnConfirmarPedido;

    private StackPane cardCredito, cardDebito, cardDinheiro, cardPix;

    public void show(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BG + ";");

        List<ItemCarrinho> itensNoCarrinho = CarrinhoGlobal.getInstancia().getItens();
        subtotal = itensNoCarrinho.stream().mapToDouble(ItemCarrinho::getValorTotal).sum();
        totalGeral = subtotal > 0 ? subtotal + TAXA_SERVICO : 0.0;

        // --- HEADER ---
        StackPane header = new StackPane();
        header.setPrefHeight(80);
        header.setStyle("-fx-background-color: " + COLOR_PRIMARY + ";");
        header.setPadding(new Insets(0, 30, 0, 30));

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18; -fx-font-family: 'Montserrat'; -fx-cursor: hand;");
        btnVoltar.setOnAction(e -> new MyOrderView().show(stage));
        StackPane.setAlignment(btnVoltar, Pos.CENTER_LEFT);

        Label lbTituloHeader = new Label("Pagamento");
        lbTituloHeader.setStyle("-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 26; -fx-font-weight: bold;");

        header.getChildren().addAll(lbTituloHeader, btnVoltar);
        root.setTop(header);

        // --- CORPO CENTRAL ---
        HBox mainContent = new HBox(100);
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setPadding(new Insets(40, 60, 40, 60));

        VBox colEsquerda = new VBox(25);
        colEsquerda.setPrefWidth(500);
        colEsquerda.setAlignment(Pos.TOP_CENTER);

        VBox painelPedido = new VBox(15);
        painelPedido.setPadding(new Insets(25));
        painelPedido.setStyle("-fx-background-color: white; -fx-background-radius: 25; -fx-border-color: #E2DDD9; -fx-border-radius: 25;");

        Label lbSeuPedidoTitulo = new Label("Seu pedido");
        lbSeuPedidoTitulo.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 22; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        painelPedido.getChildren().add(lbSeuPedidoTitulo);

        VBox itensContainer = new VBox(14);
        for (ItemCarrinho item : itensNoCarrinho) {
            itensContainer.getChildren().add(criarLinhaItemResumo(item));
        }

        ScrollPane scrollItens = new ScrollPane(itensContainer);
        scrollItens.setFitToWidth(true);
        scrollItens.setPrefHeight(260);
        scrollItens.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        painelPedido.getChildren().add(scrollItens);

        VBox painelValores = new VBox(14);
        painelValores.setPadding(new Insets(25));
        painelValores.setStyle("-fx-background-color: white; -fx-background-radius: 25; -fx-border-color: #E2DDD9; -fx-border-radius: 25;");

        HBox rowSubtotal = criarRowPreco("Subtotal", "R$ " + String.format("%.2f", subtotal), false);
        HBox rowTaxa = criarRowPreco("Taxa de serviço", "R$ " + String.format("%.2f", subtotal > 0 ? TAXA_SERVICO : 0.0), false);

        Region separator = new Region();
        separator.setPrefHeight(1);
        separator.setStyle("-fx-background-color: #E2DDD9;");
        VBox.setMargin(separator, new Insets(8, 0, 8, 0));

        HBox rowTotal = criarRowPreco("Total", "R$ " + String.format("%.2f", totalGeral), true);

        painelValores.getChildren().addAll(rowSubtotal, rowTaxa, separator, rowTotal);
        colEsquerda.getChildren().addAll(painelPedido, painelValores);

        VBox colDireita = new VBox(25);
        colDireita.setPrefWidth(580);
        colDireita.setAlignment(Pos.TOP_LEFT);

        Label lbEscolhaTitulo = new Label("Escolha a forma de pagamento");
        lbEscolhaTitulo.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 22; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        colDireita.getChildren().add(lbEscolhaTitulo);

        GridPane gridPagamentos = new GridPane();
        gridPagamentos.setHgap(20);
        gridPagamentos.setVgap(20);

        cardCredito = criarCardPagamentoHorizontal("💳", "Cartão de Crédito", "Pagamento na maquininha", "credito");
        cardDebito = criarCardPagamentoHorizontal("💳", "Cartão de Débito", "Pagamento na maquininha", "debito");
        cardDinheiro = criarCardPagamentoHorizontal("💵", "Dinheiro", "Pagamento na entrega ou retirada do pedido", "dinheiro");
        cardPix = criarCardPagamentoHorizontal("✨", "Pix", "Pagamento instantâneo\nvia QR Code", "pix");

        gridPagamentos.add(cardCredito, 0, 0);
        gridPagamentos.add(cardDebito, 1, 0);
        gridPagamentos.add(cardDinheiro, 0, 1);
        gridPagamentos.add(cardPix, 1, 1);
        colDireita.getChildren().add(gridPagamentos);

        btnConfirmarPedido = new Button("🔒  Confirmar pedido  •  R$ " + String.format("%.2f", totalGeral));
        btnConfirmarPedido.setMaxWidth(Double.MAX_VALUE);
        btnConfirmarPedido.setPrefHeight(60);
        btnConfirmarPedido.setStyle("-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 18; -fx-background-radius: 18; -fx-cursor: hand;");
        btnConfirmarPedido.setOnAction(e -> finalizarFluxoPedido(stage));

        Label lbFooterSeguranca = new Label("🔒 Seus dados e pagamento estão protegidos.");
        lbFooterSeguranca.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 13; -fx-text-fill: " + COLOR_TEXT_MUTED + ";");

        VBox bottomActionsContainer = new VBox(15);
        bottomActionsContainer.setAlignment(Pos.CENTER);
        bottomActionsContainer.setMaxWidth(Double.MAX_VALUE);
        bottomActionsContainer.getChildren().addAll(btnConfirmarPedido, lbFooterSeguranca);
        colDireita.getChildren().add(bottomActionsContainer);

        mainContent.getChildren().addAll(colEsquerda, colDireita);
        root.setCenter(mainContent);

        atualizarEstiloCardsPagamento();
        stage.getScene().setRoot(root);
    }

    private HBox criarLinhaItemResumo(ItemCarrinho item) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(5, 0, 5, 0));

        StackPane frameImg = new StackPane();
        frameImg.setPrefSize(60, 60);
        frameImg.setStyle("-fx-background-color: #FAF6F2; -fx-background-radius: 12;");

        ImageView imgView = new ImageView();
        try {
            imgView.setImage(new Image(getClass().getResourceAsStream("/com/shakecream/app/" + item.getPathImagem())));
            imgView.setFitHeight(52);
            imgView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Imagem ausente: " + item.getPathImagem());
        }
        frameImg.getChildren().add(imgView);

        VBox boxDetalhes = new VBox(2);
        boxDetalhes.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(boxDetalhes, Priority.ALWAYS);

        Label lbNome = new Label(item.getQuantidade() + "x " + item.getProdutoNome());
        lbNome.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 15; -fx-text-fill: " + COLOR_TEXT_DARK + ";");

        String txtTam = item.getTamanho();
        if (txtTam.equals("P")) txtTam = "P - 300ml";
        else if (txtTam.equals("M")) txtTam = "M - 400ml";
        else if (txtTam.equals("G")) txtTam = "G - 500ml";

        Label lbComplementos = new Label(txtTam);
        lbComplementos.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 12; -fx-text-fill: " + COLOR_TEXT_MUTED + ";");
        boxDetalhes.getChildren().addAll(lbNome, lbComplementos);

        if (item.getAdicionaisEscolhidos() != null && !item.getAdicionaisEscolhidos().isEmpty()) {
            String strAdicionais = item.getAdicionaisEscolhidos().stream().map(Additional::getName).collect(Collectors.joining(", "));
            Label lbExtras = new Label("*" + strAdicionais);
            lbExtras.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 12; -fx-text-fill: " + COLOR_TEXT_MUTED + ";");
            boxDetalhes.getChildren().add(lbExtras);
        }

        if (item.getObservacao() != null && !item.getObservacao().trim().isEmpty()) {
            Label lbObs = new Label("Obs: \"" + item.getObservacao() + "\"");
            lbObs.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 12; -fx-text-fill: " + COLOR_PRIMARY + "; -fx-font-style: italic;");
            boxDetalhes.getChildren().add(lbObs);
        }

        Label lbPrecoItem = new Label("R$ " + String.format("%.2f", item.getValorTotal()));
        lbPrecoItem.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 15; -fx-text-fill: " + COLOR_TEXT_DARK + ";");

        row.getChildren().addAll(frameImg, boxDetalhes, lbPrecoItem);
        return row;
    }

    private HBox criarRowPreco(String label, String valor, boolean isTotal) {
        HBox row = new HBox();
        Label l1 = new Label(label);
        Label l2 = new Label(valor);

        if (isTotal) {
            l1.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 22; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
            l2.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 26; -fx-text-fill: " + COLOR_PRIMARY + ";");
        } else {
            l1.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 16; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
            l2.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        }

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        row.getChildren().addAll(l1, sp, l2);
        return row;
    }

    private StackPane criarCardPagamentoHorizontal(String unicodeIcon, String titulo, String descricao, String idForma) {
        StackPane cardRoot = new StackPane();
        cardRoot.setPrefSize(280, 155);
        cardRoot.setCursor(javafx.scene.Cursor.HAND);

        StackPane radioCircle = new StackPane();
        radioCircle.setPrefSize(16, 16);
        radioCircle.setMaxSize(16, 16);
        StackPane.setAlignment(radioCircle, Pos.TOP_LEFT);
        StackPane.setMargin(radioCircle, new Insets(14, 0, 0, 14));

        HBox contentLayout = new HBox(16);
        contentLayout.setAlignment(Pos.CENTER_LEFT);
        contentLayout.setPadding(new Insets(40, 15, 15, 15));

        Label lbIcon = new Label(unicodeIcon);
        // ✨ ALTERAÇÃO AQUI: Garante um tamanho mínimo de 60px para impedir o truncamento em "..."
        lbIcon.setStyle("-fx-font-size: 34; -fx-min-width: 60; -fx-alignment: center;");

        VBox textContainer = new VBox(4);
        textContainer.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(textContainer, Priority.ALWAYS);

        Label lbTituloCard = new Label(titulo);
        lbTituloCard.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 15; -fx-text-fill: " + COLOR_TEXT_DARK + ";");

        Label lbDescCard = new Label(descricao);
        lbDescCard.setWrapText(true);
        lbDescCard.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 12; -fx-text-fill: " + COLOR_TEXT_MUTED + "; -fx-line-spacing: 1.1;");

        textContainer.getChildren().addAll(lbTituloCard, lbDescCard);
        contentLayout.getChildren().addAll(lbIcon, textContainer);

        cardRoot.getChildren().addAll(radioCircle, contentLayout);

        cardRoot.setOnMouseClicked(e -> {
            formaPagamentoSelecionada = idForma;
            atualizarEstiloCardsPagamento();
        });

        return cardRoot;
    }

    private void atualizarEstiloCardsPagamento() {
        String estiloInativo = "-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #E2DDD9; -fx-border-radius: 20; -fx-border-width: 1;";
        String estiloAtivo = "-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: " + COLOR_PRIMARY + "; -fx-border-radius: 20; -fx-border-width: 2;";

        cardCredito.setStyle(formaPagamentoSelecionada.equals("credito") ? estiloAtivo : estiloInativo);
        cardDebito.setStyle(formaPagamentoSelecionada.equals("debito") ? estiloAtivo : estiloInativo);
        cardDinheiro.setStyle(formaPagamentoSelecionada.equals("dinheiro") ? estiloAtivo : estiloInativo);
        cardPix.setStyle(formaPagamentoSelecionada.equals("pix") ? estiloAtivo : estiloInativo);

        configurarIndicadorRadio((StackPane) cardCredito.getChildren().get(0), formaPagamentoSelecionada.equals("credito"));
        configurarIndicadorRadio((StackPane) cardDebito.getChildren().get(0), formaPagamentoSelecionada.equals("debito"));
        configurarIndicadorRadio((StackPane) cardDinheiro.getChildren().get(0), formaPagamentoSelecionada.equals("dinheiro"));
        configurarIndicadorRadio((StackPane) cardPix.getChildren().get(0), formaPagamentoSelecionada.equals("pix"));
    }

    private void configurarIndicadorRadio(StackPane container, boolean ativo) {
        container.getChildren().clear();
        if (ativo) {
            container.setStyle("-fx-border-color: " + COLOR_PRIMARY + "; -fx-border-radius: 50; -fx-border-width: 2;");
            Region dot = new Region();
            dot.setPrefSize(8, 8);
            dot.setMaxSize(8, 8);
            dot.setStyle("-fx-background-color: " + COLOR_PRIMARY + "; -fx-background-radius: 50;");
            container.getChildren().add(dot);
        } else {
            container.setStyle("-fx-border-color: #E2DDD9; -fx-border-radius: 50; -fx-border-width: 1.5;");
        }
    }

    private void finalizarFluxoPedido(Stage stage) {
        if (CarrinhoGlobal.getInstancia().getItens().isEmpty()) {
            return;
        }

        if (formaPagamentoSelecionada.equals("pix")) {
            new PixPaymentView().show(stage);
        } else if (formaPagamentoSelecionada.equals("credito")) {
            new OrderConfirmationView().show(stage, "Pedido confirmado!");
        } else if (formaPagamentoSelecionada.equals("debito")) {
            new OrderConfirmationView().show(stage, "Pedido confirmado!");
        } else if (formaPagamentoSelecionada.equals("dinheiro")) {
            new OrderConfirmationView().show(stage, "Pedido recebido! Pague no balcão ao retirar.");
        }
    }
}