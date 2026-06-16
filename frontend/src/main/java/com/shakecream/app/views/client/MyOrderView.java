package com.shakecream.app.views.client;

import com.shakecream.app.models.Additional;
import com.shakecream.app.models.ItemCarrinho;
import com.shakecream.app.state.CarrinhoGlobal;
import com.shakecream.app.ui.AdditionalItem;

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

public class MyOrderView {

    private final String COLOR_PRIMARY = "#B95C68"; // Vinho do Figma
    private final String COLOR_BG = "#FAF6F2";      // Fundo Creme suave
    private final String COLOR_TEXT_DARK = "#4A3B37";
    private final String COLOR_TEXT_MUTED = "#8E8A85";

    public void show(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BG + ";");

        // --- HEADER (CABEÇALHO) ---
        StackPane header = new StackPane();
        header.setPrefHeight(80);
        header.setStyle("-fx-background-color: " + COLOR_PRIMARY + ";");
        header.setPadding(new Insets(0, 30, 0, 30));

        // Botão Continuar Escolhendo à Esquerda
        Button btnContinuar = new Button("← Continuar escolhendo");
        btnContinuar.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18;" +
                        "-fx-font-family: 'Montserrat';" +
                        "-fx-cursor: hand;"
        );
        // Lógica de navegação: Retorna para a tela de seleção de categorias
        btnContinuar.setOnAction(e -> new CategorySelectionView().show(stage));
        StackPane.setAlignment(btnContinuar, Pos.CENTER_LEFT);

        // Título Centralizado do Cabeçalho
        Label lbTituloHeader = new Label("Meu pedido");
        lbTituloHeader.setStyle("-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 26; -fx-font-weight: bold;");

        header.getChildren().addAll(lbTituloHeader, btnContinuar);
        root.setTop(header);

        // --- CONTEÚDO CENTRAL: LISTA DE ITENS ADICIONADOS ---
        VBox listContainer = new VBox(18);
        listContainer.setPadding(new Insets(40, 0, 40, 0));
        listContainer.setAlignment(Pos.TOP_CENTER);
        listContainer.setStyle("-fx-background-color: transparent;");

        // Busca a lista persistida na memória global do aplicativo
        List<ItemCarrinho> itensNoCarrinho = CarrinhoGlobal.getInstancia().getItens();

        if (itensNoCarrinho.isEmpty()) {
            Label lbVazio = new Label("Seu carrinho está vazio!");
            lbVazio.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 18; -fx-text-fill: " + COLOR_TEXT_MUTED + "; -fx-font-weight: bold;");
            listContainer.getChildren().add(lbVazio);
        } else {
            for (ItemCarrinho item : itensNoCarrinho) {
                listContainer.getChildren().add(criarCardCarrinhoFigma(stage, item));
            }
        }

        ScrollPane scroll = new ScrollPane(listContainer);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-vbar-policy: as-needed; -fx-hbar-policy: never;");
        root.setCenter(scroll);

        // --- SEÇÃO INFERIOR: BOTÃO DE PAGAMENTO ---
        VBox bottomLayout = new VBox();
        bottomLayout.setAlignment(Pos.CENTER);
        bottomLayout.setPadding(new Insets(20, 0, 40, 0));

        Button btnPagamento = new Button("Pagamento");
        btnPagamento.setPrefSize(280, 55);
        btnPagamento.setStyle(
                "-fx-background-color: " + COLOR_PRIMARY + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Montserrat';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;" +
                        "-fx-background-radius: 20;" +
                        "-fx-cursor: hand;"
        );
        
        // Ação futura para a tela de encerramento/pagamento
        // Procure por essa linha por volta da linha 69 da sua MyOrderView e mude para:
        btnPagamento.setOnAction(e -> new PaymentView().show(stage));

        bottomLayout.getChildren().add(btnPagamento);
        root.setBottom(bottomLayout);

        stage.getScene().setRoot(root);
    }

    private HBox criarCardCarrinhoFigma(Stage stage, ItemCarrinho item) {
        HBox card = new HBox(25);
        card.setPrefHeight(120);
        card.setMaxWidth(950);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(15, 30, 15, 20));
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 25;" +
                        "-fx-border-color: #E0DDD9;" +
                        "-fx-border-radius: 25;" +
                        "-fx-border-width: 1;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.02), 10, 0, 0, 4);"
        );

        // 1. Container e Renderização da Imagem do Produto
        StackPane imgFrame = new StackPane();
        imgFrame.setPrefSize(90, 90);
        imgFrame.setStyle("-fx-background-color: #F4F0E6; -fx-background-radius: 18;");

        ImageView imgView = new ImageView();
        try {
            imgView.setImage(new Image(getClass().getResourceAsStream("/com/shakecream/app/" + item.getPathImagem())));
            imgView.setFitHeight(80);
            imgView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Erro ao renderizar imagem no carrinho: " + item.getPathImagem());
        }
        imgFrame.getChildren().add(imgView);

        // 2. Caixa de Informações (Nome, Volumetria e Adicionais)
        VBox textsBox = new VBox(4);
        textsBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(textsBox, Priority.ALWAYS);

        // Nome formatado com a quantidade (Ex: 1x Chocolate com Brownie)
        String prefixoQtd = item.getQuantidade() > 1 ? item.getQuantidade() + "x " : "";
        Label lbNome = new Label(prefixoQtd + item.getProdutoNome());
        lbNome.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 19; -fx-text-fill: " + COLOR_TEXT_DARK + ";");

        // Tratamento da volumetria baseada na escolha (P, M, G ou Único no caso de bebidas)
        String volumetria = item.getTamanho();
        if (volumetria.equalsIgnoreCase("P")) volumetria = "P - 300ml";
        else if (volumetria.equalsIgnoreCase("M")) volumetria = "M - 400ml";
        else if (volumetria.equalsIgnoreCase("G")) volumetria = "G - 500ml";

        Label lbTamanho = new Label(volumetria);
        lbTamanho.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 13; -fx-text-fill: " + COLOR_TEXT_MUTED + ";");

        textsBox.getChildren().addAll(lbNome, lbTamanho);

        // Caso o item contenha adicionais, renderiza a lista com o asterisco idêntico ao Figma
        if (item.getAdicionaisEscolhidos() != null && !item.getAdicionaisEscolhidos().isEmpty()) {
            String stringAdicionais = item.getAdicionaisEscolhidos().stream()
                    .map(Additional::getName)
                    .collect(Collectors.joining(", "));

            Label lbAdicionais = new Label("*" + stringAdicionais);
            lbAdicionais.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 13; -fx-text-fill: " + COLOR_TEXT_MUTED + ";");
            textsBox.getChildren().add(lbAdicionais);
        }

        // 3. Preço Total do Pedido
        Label lbPreco = new Label("R$ " + String.format("%.2f", item.getValorTotal()));
        lbPreco.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 19; -fx-text-fill: " + COLOR_TEXT_DARK + ";");

        // 4. Ícone de Lixeira (Botão de Remoção Atômica)
        Button btnDelete = new Button("🗑");
        btnDelete.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_TEXT_MUTED + "; -fx-font-size: 20; -fx-cursor: hand;");

        // Efeito Hover na Lixeira para refinamento visual
        btnDelete.setOnMouseEntered(e -> btnDelete.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_PRIMARY + "; -fx-font-size: 20; -fx-cursor: hand;"));
        btnDelete.setOnMouseExited(e -> btnDelete.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_TEXT_MUTED + "; -fx-font-size: 20; -fx-cursor: hand;"));

        btnDelete.setOnAction(e -> {
            // Remove o objeto da lista em tempo de execução e atualiza os elementos visuais na mesma hora
            CarrinhoGlobal.getInstancia().getItens().remove(item);
            show(stage);
        });

        card.getChildren().addAll(imgFrame, textsBox, lbPreco, btnDelete);
        return card;
    }
}