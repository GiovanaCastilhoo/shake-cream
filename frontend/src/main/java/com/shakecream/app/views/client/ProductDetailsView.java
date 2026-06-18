package com.shakecream.app.views.client;

import com.shakecream.app.models.Additional;
import com.shakecream.app.models.ItemCarrinho;
import com.shakecream.app.services.AdditionalService;
import com.shakecream.app.state.CarrinhoGlobal;
import com.shakecream.app.ui.AdditionalItem;
import com.shakecream.app.ui.Theme;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailsView {

        private final AdditionalService additionalService = new AdditionalService();

        private int quantidade = 1;
        private double precoProdutoBase;
        private double acrescimoTamanho = -1.0;
        private String tamanhoSelecionado = "";
        private final List<Additional> todosAdicionais = new ArrayList<>();
        private boolean ehBebida = false;
        private String pathImagem;
        private String categoriaSabor = "Geral";
        private int categoryId;

        private Label lbQuantidade;
        private Label lbPrecoTotal;
        private TextArea txtObs;
        private StackPane rootStack;

        private VBox cardP, cardM, cardG;
        private HBox flowAdicionaisRapidos;

        public void show(Stage stage, String nomeProduto, double precoBase, String pathImagem, String descricaoProduto,
                        String categoriaSabor, int categoryId) {
                this.categoryId = categoryId;
                this.ehBebida = false;
                this.categoriaSabor = categoriaSabor;
                this.acrescimoTamanho = -1.0;
                this.tamanhoSelecionado = "";
                renderizarTela(stage, nomeProduto, precoBase, pathImagem, descricaoProduto);
        }

        public void show(Stage stage, String nomeProduto, double precoBase, String pathImagem,
                        String descricaoProduto) {
                show(stage, nomeProduto, precoBase, pathImagem, descricaoProduto, "Geral", categoryId);
        }

        public void show(Stage stage, String nomeProduto, double precoBase, String pathImagem, String descricaoProduto,
                        boolean isBebida) {
                this.ehBebida = isBebida;
                this.categoriaSabor = isBebida ? "Bebida" : "Geral";
                if (this.ehBebida) {
                        this.acrescimoTamanho = 0.00;
                        this.tamanhoSelecionado = "Único";
                }
                renderizarTela(stage, nomeProduto, precoBase, pathImagem, descricaoProduto);
        }

        private void renderizarTela(Stage stage, String nomeProduto, double precoBase, String pathImagem,
                        String descricaoProduto) {
                this.precoProdutoBase = precoBase;
                this.pathImagem = pathImagem;
                this.rootStack = new StackPane();

                BorderPane mainLayout = new BorderPane();
                mainLayout.setStyle("-fx-background-color: " + Theme.COLOR_BG_WORKSPACE + ";");

                StackPane header = new StackPane();
                header.setPrefHeight(80);
                header.setStyle("-fx-background-color: " + Theme.COLOR_PRIMARY + ";");
                header.setPadding(new Insets(0, 30, 0, 30));

                Button btnVoltar = new Button("←  Voltar");
                btnVoltar.setStyle(
                                "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18; -fx-font-family: 'Montserrat'; -fx-cursor: hand;");
                btnVoltar.setOnAction(e -> voltarParaTelaOrigem(stage));
                StackPane.setAlignment(btnVoltar, Pos.CENTER_LEFT);

                Label lbTituloHeader = new Label("Detalhes do Pedido");
                lbTituloHeader.setStyle(
                                "-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 24; -fx-font-weight: bold;");

                header.getChildren().addAll(lbTituloHeader, btnVoltar);
                mainLayout.setTop(header);

                HBox contentBox = new HBox(55);
                contentBox.setPadding(new Insets(40, 60, 40, 60));
                contentBox.setAlignment(Pos.CENTER);

                VBox colEsquerda = new VBox(25);
                colEsquerda.setAlignment(Pos.CENTER);

                StackPane imgWrapper = new StackPane();
                imgWrapper.setPrefSize(320, 380);
                imgWrapper.setStyle(
                                "-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #E0DDD9; -fx-border-radius: 20; -fx-border-width: 1;");

                ImageView imgView = new ImageView();
                try {
                        imgView.setImage(
                                        new Image(getClass().getResourceAsStream("/com/shakecream/app/" + pathImagem)));
                        imgView.setFitHeight(340);
                        imgView.setPreserveRatio(true);
                } catch (Exception e) {
                        System.err.println("Imagem " + pathImagem + " não encontrada.");
                }
                imgWrapper.getChildren().add(imgView);

                HBox counterBox = new HBox(20);
                counterBox.setAlignment(Pos.CENTER);
                counterBox.setStyle(
                                "-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #E0DDD9; -fx-border-radius: 12; -fx-padding: 8 15;");
                counterBox.setMaxWidth(180);

                Button btnMinus = new Button("-");
                btnMinus.setStyle(
                                "-fx-background-color: transparent; -fx-font-family: 'Montserrat'; -fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: "
                                                + Theme.COLOR_TEXT_DARK + "; -fx-cursor: hand; -fx-min-width: 35;");
                btnMinus.setOnAction(e -> {
                        if (quantidade > 1) {
                                quantidade--;
                                atualizarValores();
                        }
                });

                lbQuantidade = new Label("1");
                lbQuantidade.setStyle(
                                "-fx-font-family: 'Montserrat'; -fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: "
                                                + Theme.COLOR_TEXT_DARK + ";");

                Button btnPlus = new Button("+");
                btnPlus.setStyle(
                                "-fx-background-color: transparent; -fx-font-family: 'Montserrat'; -fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: "
                                                + Theme.COLOR_TEXT_DARK + "; -fx-cursor: hand; -fx-min-width: 35;");
                btnPlus.setOnAction(e -> {
                        quantidade++;
                        atualizarValores();
                });

                counterBox.getChildren().addAll(btnMinus, lbQuantidade, btnPlus);

                lbPrecoTotal = new Label("R$ 0,00");
                lbPrecoTotal.setStyle(
                                "-fx-font-family: 'Montserrat'; -fx-font-size: 36; -fx-font-weight: bold; -fx-text-fill: "
                                                + Theme.COLOR_PRIMARY + ";");

                Button btnAdicionarAoCarrinho = new Button("ADICIONAR");
                btnAdicionarAoCarrinho.setPrefSize(280, 55);
                btnAdicionarAoCarrinho.setStyle("-fx-background-color: " + Theme.COLOR_PRIMARY
                                + "; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 16; -fx-background-radius: 14; -fx-cursor: hand;");
                btnAdicionarAoCarrinho.setOnAction(e -> processarESalvarPedido(stage, nomeProduto));

                colEsquerda.getChildren().addAll(imgWrapper, counterBox, lbPrecoTotal, btnAdicionarAoCarrinho);

                VBox colDireita = new VBox(20);
                colDireita.setPrefWidth(550);

                Label lbNome = new Label(nomeProduto);
                lbNome.setWrapText(true);
                lbNome.setMaxWidth(550);
                lbNome.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 32; -fx-font-weight: bold; -fx-text-fill: "
                                + Theme.COLOR_TEXT_DARK + ";");

                Label lbDescricao = new Label(descricaoProduto);
                lbDescricao
                                .setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 15; -fx-text-fill: "
                                                + Theme.COLOR_TEXT_MUTED + ";");

                Label lbPrecoBase = new Label("R$ " + String.format("%.2f", precoProdutoBase));
                lbPrecoBase.setStyle(
                                "-fx-font-family: 'Montserrat'; -fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: "
                                                + Theme.COLOR_PRIMARY + ";");

                colDireita.getChildren().addAll(lbNome, lbDescricao, lbPrecoBase);

                if (!ehBebida) {
                        Label lbTituloTamanho = new Label("ESCOLHA O TAMANHO *");
                        lbTituloTamanho
                                        .setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: "
                                                        + Theme.COLOR_TEXT_MUTED + ";");

                        HBox boxTamanhosLayout = new HBox(15);
                        cardP = renderSizeCard("P", "300 ml", 0.00);
                        cardM = renderSizeCard("M", "400 ml", 3.00);
                        cardG = renderSizeCard("G", "500 ml", 6.00);
                        boxTamanhosLayout.getChildren().addAll(cardP, cardM, cardG);

                        Label lbTituloAdicionais = new Label("ADICIONAIS (opcional)");
                        lbTituloAdicionais
                                        .setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: "
                                                        + Theme.COLOR_TEXT_MUTED + ";");

                        flowAdicionaisRapidos = new HBox(12);
                        flowAdicionaisRapidos.setAlignment(Pos.CENTER_LEFT);
                        inicializarBancoAdicionais();
                        renderAdicionaisFigmaMenu();

                        Label lbTituloObs = new Label("OBSERVAÇÕES (opcional)");
                        lbTituloObs
                                        .setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: "
                                                        + Theme.COLOR_TEXT_MUTED + ";");

                        txtObs = new TextArea();
                        txtObs.setPromptText("Ex: Sem açúcar, pouca cobertura...");
                        txtObs.setPrefHeight(90);
                        txtObs.setStyle("-fx-control-inner-background: white; -fx-font-family: 'Montserrat'; -fx-font-size: 14; -fx-background-radius: 12; -fx-border-color: #E0DDD9; -fx-border-radius: 12;");

                        colDireita.getChildren().addAll(lbTituloTamanho, boxTamanhosLayout, lbTituloAdicionais,
                                        flowAdicionaisRapidos, lbTituloObs, txtObs);
                }

                contentBox.getChildren().addAll(colEsquerda, colDireita);
                mainLayout.setCenter(contentBox);

                rootStack.getChildren().add(mainLayout);
                stage.getScene().setRoot(rootStack);
                atualizarValores();
        }

        private VBox renderSizeCard(String label, String ml, double valorExtra) {
                VBox box = new VBox(4);
                box.setAlignment(Pos.CENTER);
                box.setPrefSize(120, 95);
                box.setCursor(javafx.scene.Cursor.HAND);
                box.setStyle("-fx-background-color: white; -fx-border-color: #E0DDD9; -fx-border-radius: 15; -fx-background-radius: 15;");

                Label l1 = new Label(label);
                l1.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 18;");
                Label l2 = new Label(ml);
                l2.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 12; -fx-text-fill: " + Theme.COLOR_TEXT_MUTED
                                + ";");
                Label l3 = new Label("R$ " + String.format("%.2f", valorExtra));
                l3.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 12; -fx-text-fill: " + Theme.COLOR_TEXT_DARK
                                + ";");

                box.getChildren().addAll(l1, l2, l3);

                box.setOnMouseClicked(e -> {
                        tamanhoSelecionado = label;
                        acrescimoTamanho = valorExtra;

                        cardP.setStyle("-fx-background-color: white; -fx-border-color: #E0DDD9; -fx-border-radius: 15; -fx-background-radius: 15;");
                        cardM.setStyle("-fx-background-color: white; -fx-border-color: #E0DDD9; -fx-border-radius: 15; -fx-background-radius: 15;");
                        cardG.setStyle("-fx-background-color: white; -fx-border-color: #E0DDD9; -fx-border-radius: 15; -fx-background-radius: 15;");

                        box.setStyle("-fx-background-color: #F8D7DA; -fx-border-color: " + Theme.COLOR_PRIMARY
                                        + "; -fx-border-radius: 15; -fx-background-radius: 15;");
                        atualizarValores();
                });

                return box;
        }

        private void renderAdicionaisFigmaMenu() {
                flowAdicionaisRapidos.getChildren().clear();

                if (!todosAdicionais.isEmpty()) {
                        if (todosAdicionais.size() > 0)
                                flowAdicionaisRapidos.getChildren()
                                                .add(createAdicionalHorizontalCard(todosAdicionais.get(0)));
                        if (todosAdicionais.size() > 1)
                                flowAdicionaisRapidos.getChildren()
                                                .add(createAdicionalHorizontalCard(todosAdicionais.get(1)));
                }

                VBox cardMais = new VBox();
                cardMais.setAlignment(Pos.CENTER);
                cardMais.setPrefSize(115, 75);
                cardMais.setCursor(javafx.scene.Cursor.HAND);
                cardMais.setStyle(
                                "-fx-background-color: white; -fx-background-radius: 15; -fx-border-color: #E0DDD9; -fx-border-radius: 15;");

                Label lbMais = new Label("+ MAIS");
                lbMais.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: "
                                + Theme.COLOR_TEXT_DARK + ";");

                cardMais.getChildren().add(lbMais);

                cardMais.setOnMouseClicked(e -> abrirMenuScrollVerticalAdicionais());
                flowAdicionaisRapidos.getChildren().add(cardMais);
        }

        private StackPane createAdicionalHorizontalCard(Additional ad) {
                StackPane container = new StackPane();
                VBox box = new VBox(3);
                box.setAlignment(Pos.CENTER);
                box.setPrefSize(125, 75);
                box.setStyle(ad.isSelecionado()
                                ? "-fx-background-color: #F8D7DA; -fx-border-color: " + Theme.COLOR_PRIMARY
                                                + "; -fx-background-radius: 15; -fx-border-radius: 15;"
                                : "-fx-background-color: white; -fx-border-color: #E0DDD9; -fx-background-radius: 15; -fx-border-radius: 15;");

                Label l1 = new Label(ad.getName().toUpperCase());
                l1.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 11;");
                Label l2 = new Label("R$ " + String.format("%.2f", ad.getPrice()));
                l2.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 11; -fx-text-fill: " + COLOR_TEXT_MUTED
                                + ";");
                box.getChildren().addAll(l1, l2);

                Label checkMark = new Label("✓");
                checkMark.setStyle("-fx-text-fill: " + Theme.COLOR_PRIMARY
                                + "; -fx-font-weight: bold; -fx-font-size: 14;");
                checkMark.setVisible(ad.isSelecionado());
                StackPane.setAlignment(checkMark, Pos.TOP_LEFT);
                StackPane.setMargin(checkMark, new Insets(6, 0, 0, 10));

                container.getChildren().addAll(box, checkMark);
                container.setCursor(javafx.scene.Cursor.HAND);

                container.setOnMouseClicked(e -> {
                        ad.setSelecionado(!ad.isSelecionado());
                        atualizarValores();
                        renderAdicionaisFigmaMenu();
                });

                return container;
        }

        private void abrirMenuScrollVerticalAdicionais() {
                VBox glassPane = new VBox();
                glassPane.setStyle("-fx-background-color: rgba(0,0,0,0.4);");
                glassPane.setAlignment(Pos.CENTER);

                VBox modalContainer = new VBox(15);
                modalContainer.setMaxSize(450, 500);
                modalContainer.setPadding(new Insets(25));
                modalContainer.setStyle("-fx-background-color: white; -fx-background-radius: 20;");

                Label titleModal = new Label("Selecione os Adicionais");
                titleModal.setStyle(
                                "-fx-font-family: 'Montserrat'; -fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: "
                                                + Theme.COLOR_TEXT_DARK + ";");

                VBox listContent = new VBox(10);
                for (Additional ad : todosAdicionais) {
                        HBox row = new HBox(15);
                        row.setAlignment(Pos.CENTER_LEFT);
                        row.setPadding(new Insets(8));
                        row.setStyle("-fx-border-color: #F0EDE9; -fx-border-width: 0 0 1 0;");

                        CheckBox cb = new CheckBox();
                        cb.setSelected(ad.isSelecionado());

                        Label name = new Label(ad.getName());
                        name.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 14; -fx-font-weight: bold;");
                        Region sp = new Region();
                        HBox.setHgrow(sp, Priority.ALWAYS);
                        Label pr = new Label("+ R$ " + String.format("%.2f", ad.getPrice()));
                        pr.setStyle("-fx-font-family: 'Montserrat'; -fx-text-fill: " + COLOR_PRIMARY + ";");

                        row.getChildren().addAll(cb, name, sp, pr);
                        row.setCursor(javafx.scene.Cursor.HAND);
                        row.setOnMouseClicked(e -> {
                                cb.setSelected(!cb.isSelected());
                                ad.setSelecionado(cb.isSelected());
                        });
                        cb.setOnAction(e -> ad.setSelecionado(cb.isSelected()));

                        listContent.getChildren().add(row);
                }

                ScrollPane scrollModal = new ScrollPane(listContent);
                scrollModal.setFitToWidth(true);
                scrollModal.setStyle("-fx-background: white; -fx-background-color: white;");

                Button btnSalvarModal = new Button("CONFIRMAR SELEÇÃO");
                btnSalvarModal.setMaxWidth(Double.MAX_VALUE);
                btnSalvarModal.setPrefHeight(45);
                btnSalvarModal.setStyle("-fx-background-color: " + Theme.COLOR_PRIMARY
                                + "; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-background-radius: 10; -fx-cursor: hand;");
                btnSalvarModal.setOnAction(e -> {
                        rootStack.getChildren().remove(glassPane);
                        atualizarValores();
                        renderAdicionaisFigmaMenu();
                });

                modalContainer.getChildren().addAll(titleModal, scrollModal, btnSalvarModal);
                glassPane.getChildren().add(modalContainer);
                rootStack.getChildren().add(glassPane);
        }

        private void atualizarValores() {
                double extraTam = (acrescimoTamanho == -1.0) ? 0.00 : acrescimoTamanho;
                double somaAdicionais = todosAdicionais.stream().filter(Additional::isSelecionado)
                                .mapToDouble(Additional::getPrice).sum();
                double total = (precoProdutoBase + extraTam + somaAdicionais) * quantidade;

                lbQuantidade.setText(String.valueOf(quantidade));
                lbPrecoTotal.setText(String.format("R$ %.2f", total));
        }

        private void voltarParaTelaOrigem(Stage stage) {
                if (ehBebida || "Bebida".equalsIgnoreCase(categoriaSabor)) {
                        new DrinkSelectionView().show(stage, categoryId);
                } else {
                        switch (categoriaSabor.toLowerCase()) {
                                case "morango":
                                        new com.shakecream.app.views.client.StrawberrySelectionView().show(stage);
                                        break;
                                case "chocolate":
                                        new com.shakecream.app.views.client.ChocolateSelectionView().show(stage);
                                        break;
                                case "baunilha":
                                        new com.shakecream.app.views.client.VanillaSelectionView().show(stage);
                                        break;
                                case "especiais":
                                        new com.shakecream.app.views.client.SpecialSelectionView().show(stage);
                                        break;
                                default:
                                        new com.shakecream.app.views.client.FlavorSelectionView().show(stage);
                                        break;
                        }
                }
        }

        private void processarESalvarPedido(Stage stage, String nomeProduto) {
                if (!ehBebida) {
                        if (tamanhoSelecionado.isEmpty()) {
                                exibirAlertaAviso("Tamanho Obrigatório",
                                                "Por favor, selecione um tamanho (P, M ou G) antes de prosseguir.");
                                return;
                        }
                }

                List<Additional> extrasSalvos = todosAdicionais.stream().filter(Additional::isSelecionado)
                                .collect(Collectors.toList());
                double extraTamCalculo = (acrescimoTamanho == -1.0) ? 0.00 : acrescimoTamanho;
                double precoFinalCalculado = (precoProdutoBase + extraTamCalculo
                                + extrasSalvos.stream().mapToDouble(Additional::getPrice).sum()) * quantidade;

                String observacaoSalva = (txtObs != null) ? txtObs.getText() : "";

                ItemCarrinho novoPedido = new ItemCarrinho(nomeProduto, tamanhoSelecionado, extrasSalvos,
                                observacaoSalva, quantidade, precoFinalCalculado, pathImagem);
                CarrinhoGlobal.getInstancia().adicionarItem(novoPedido);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText(null);
                alert.setContentText(nomeProduto + " foi adicionado e salvo com sucesso!");
                alert.showAndWait();

                new com.shakecream.app.views.client.CategorySelectionView().show(stage);
        }

        private void exibirAlertaAviso(String titulo, String mensagem) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(titulo);
                alert.setHeaderText(null);
                alert.setContentText(mensagem);
                alert.showAndWait();
        }

        private void inicializarBancoAdicionais() {
                if (todosAdicionais.isEmpty()) {
                        try {
                                List<Additional> adicionaisDoBanco = additionalService.getAdditionals();
                                if (adicionaisDoBanco != null) {
                                        todosAdicionais.addAll(adicionaisDoBanco);
                                }
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }
        }
}