package com.shakecream.app.views.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

import com.shakecream.app.components.AlertComponent;
import com.shakecream.app.models.Product;
import com.shakecream.app.services.ProductService;
import com.shakecream.app.ui.Theme;

public class AdminDashboardView {
    private final ProductService productService = new ProductService();
    private Stage stage;

    private BorderPane mainLayout;
    private VBox workspaceContainer;
    private VBox rowsContainer;
    private Label lbTituloTopHeader;

    // Estado de navigation do menu lateral
    private Button btnMenuMercadorias;
    private Button btnMenuCategorias;
    private Button btnMenuAdicionais;

    public void show(Stage stage) {
        this.stage = stage;
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + Theme.COLOR_BG_WORKSPACE + ";");

        // =========================================================================
        // 1. TOP HEADER (PAINEL SUPERIOR)
        // =========================================================================
        HBox topHeader = new HBox();
        topHeader.setPrefHeight(80);
        topHeader.setAlignment(Pos.CENTER_LEFT);
        topHeader.setPadding(new Insets(0, 40, 0, 30));
        topHeader.setStyle("-fx-background-color: " + Theme.COLOR_BG_DARK + ";");

        HBox logoBox = new HBox(10);
        logoBox.setPrefWidth(210);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        try {
            ImageView logoImg = new ImageView(
                    new Image(getClass().getResourceAsStream("/com/shakecream/app/logo.png")));
            logoImg.setFitHeight(60);
            logoImg.setPreserveRatio(true);
            logoBox.getChildren().add(logoImg);
        } catch (Exception e) {
            Label lbFallbackLogo = new Label("Shake & Cream");
            lbFallbackLogo.setStyle(
                    "-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 20;");
            logoBox.getChildren().add(lbFallbackLogo);
        }

        // Título centralizado
        lbTituloTopHeader = new Label("MERCADORIAS");
        lbTituloTopHeader.setStyle(
                "-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 24; -fx-font-weight: bold;");

        // Spacers para manter a simetria perfeita
        Region topSpacer1 = new Region();
        HBox.setHgrow(topSpacer1, Priority.ALWAYS);

        Region topSpacer2 = new Region();
        HBox.setHgrow(topSpacer2, Priority.ALWAYS);

        topHeader.getChildren().addAll(logoBox, topSpacer1, lbTituloTopHeader, topSpacer2);
        mainLayout.setTop(topHeader);

        // =========================================================================
        // 2. SIDEBAR (MENU LATERAL ESQUERDO)
        // =========================================================================
        VBox sidebar = new VBox(12);
        sidebar.setPrefWidth(240);
        sidebar.setPadding(new Insets(30, 15, 30, 15));
        sidebar.setStyle("-fx-background-color: " + Theme.COLOR_BG_DARK
                + "; -fx-border-color: #391100; -fx-border-width: 1 0 0 0;");

        btnMenuMercadorias = createSidebarButton("🛒  Mercadorias");
        btnMenuCategorias = createSidebarButton("📁  Categorias");
        btnMenuAdicionais = createSidebarButton("➕  Adicionais");

        Region sidebarSpacer = new Region();
        VBox.setVgrow(sidebarSpacer, Priority.ALWAYS);

        Button btnSair = new Button("🚪  Sair");
        btnSair.setMaxWidth(Double.MAX_VALUE);
        btnSair.setPrefHeight(40);
        btnSair.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #FFFFFF; -fx-font-family: 'Montserrat'; -fx-font-size: 20; -fx-alignment: center; -fx-cursor: hand;");
        btnSair.setOnAction(e -> new AdminLoginView().show(stage));

        sidebar.getChildren().addAll(btnMenuMercadorias, btnMenuCategorias, btnMenuAdicionais, sidebarSpacer, btnSair);
        mainLayout.setLeft(sidebar);

        // =========================================================================
        // 3. ÁREA DE TRABALHO PRINCIPAL (WORKSPACE)
        // =========================================================================
        workspaceContainer = new VBox(25);
        workspaceContainer.setPadding(new Insets(40, 50, 40, 50));
        workspaceContainer.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollWorkspace = new ScrollPane(workspaceContainer);
        scrollWorkspace.setFitToWidth(true);
        scrollWorkspace.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        mainLayout.setCenter(scrollWorkspace);

        // Configuração das ações de navegação do Menu
        btnMenuMercadorias.setOnAction(e -> alternarVisualizacaoMenu("MERCADORIAS"));
        btnMenuCategorias.setOnAction(e -> alternarVisualizacaoMenu("CATEGORIAS"));
        btnMenuAdicionais.setOnAction(e -> alternarVisualizacaoMenu("ADICIONAIS"));

        // ✨ Inicializa aplicando as regras de destaque visual na aba corrente
        alternarVisualizacaoMenu("MERCADORIAS");

        stage.getScene().setRoot(mainLayout);
    }

    private Button createSidebarButton(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(50);
        btn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 15; -fx-alignment: center; -fx-cursor: hand;");
        return btn;
    }

    // ✨ CORREÇÃO CRÍTICA: Vincula o estado ativo de Mercadorias logo na
    // inicialização da tela
    private void alternarVisualizacaoMenu(String menuAtivo) {
        String estiloInativo = "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 15; -fx-alignment: center; -fx-cursor: hand;";
        String estiloAtivo = "-fx-background-color: " + Theme.COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 15; -fx-alignment: center; -fx-background-radius: 12; -fx-cursor: hand;";

        btnMenuMercadorias.setStyle(menuAtivo.equals("MERCADORIAS") ? estiloAtivo : estiloInativo);
        btnMenuCategorias.setStyle(menuAtivo.equals("CATEGORIAS") ? estiloAtivo : estiloInativo);
        btnMenuAdicionais.setStyle(menuAtivo.equals("ADICIONAIS") ? estiloAtivo : estiloInativo);

        lbTituloTopHeader.setText(menuAtivo);

        if (menuAtivo.equals("MERCADORIAS")) {
            renderizarLayoutMercadorias();
        } else if (menuAtivo.equals("CATEGORIAS")) {
            new AdminCategoryView().show((Stage) mainLayout.getScene().getWindow());
        } else if (menuAtivo.equals("ADICIONAIS")) {
            new AdminAdditionalView().show((Stage) mainLayout.getScene().getWindow());
        }
    }

    private void renderizarLayoutMercadorias() {
        workspaceContainer.getChildren().clear();

        BorderPane sectionHeader = new BorderPane();
        sectionHeader.setMaxWidth(1150);

        VBox txtTitleBox = new VBox(4);
        Label title = new Label("Gerenciar Mercadorias");
        title.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 26; -fx-text-fill: "
                + Theme.COLOR_TEXT_DARK + ";");
        Label subtitle = new Label("Cadastre e edite as mercadorias do cardápio");
        subtitle.setStyle(
                "-fx-font-family: 'Montserrat'; -fx-font-size: 14; -fx-text-fill: " + Theme.COLOR_TEXT_MUTED + ";");
        txtTitleBox.getChildren().addAll(title, subtitle);
        sectionHeader.setLeft(txtTitleBox);

        Button btnNovaMercadoria = new Button("+  Nova Mercadoria");
        btnNovaMercadoria.setPrefHeight(45);
        btnNovaMercadoria.setPadding(new Insets(0, 20, 0, 20));
        btnNovaMercadoria.setStyle("-fx-background-color: " + Theme.COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 12; -fx-cursor: hand;");
        btnNovaMercadoria.setOnAction(e -> handleBotaoNovoItem("MERCADORIA"));
        sectionHeader.setRight(btnNovaMercadoria);

        VBox customTableCard = new VBox();
        customTableCard.setMaxWidth(1150);
        customTableCard.setStyle(
                "-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #E2DDD9; -fx-border-radius: 20; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.02), 10, 0, 0, 5);");

        HBox tableHeaderRow = new HBox();
        tableHeaderRow.setPrefHeight(42);
        tableHeaderRow.setAlignment(Pos.CENTER_LEFT);
        tableHeaderRow
                .setStyle("-fx-background-color: " + Theme.COLOR_TABLE_TH + "; -fx-background-radius: 20 20 0 0;");
        tableHeaderRow.setPadding(new Insets(0, 25, 0, 25));

        Label thImagem = createTableHeaderLabel("Imagem", 140, Pos.CENTER);
        Label thNome = createTableHeaderLabel("Nome", 240, Pos.CENTER);
        Label thCategoria = createTableHeaderLabel("Categoria", 240, Pos.CENTER);
        Label thPreco = createTableHeaderLabel("Preço", 240, Pos.CENTER);
        Label thAcoes = createTableHeaderLabel("Ações", 240, Pos.CENTER);

        tableHeaderRow.getChildren().addAll(thImagem, thNome, thCategoria, thPreco, thAcoes);
        customTableCard.getChildren().add(tableHeaderRow);

        rowsContainer = new VBox();
        customTableCard.getChildren().add(rowsContainer);

        workspaceContainer.getChildren().addAll(sectionHeader, customTableCard);

        List<Product> productList = productService.getAll();

        updateTableProducts(productList);
    }

    public void updateTableProducts(List<Product> products) {
        if (rowsContainer == null)
            return;
        rowsContainer.getChildren().clear();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product == null)
                continue;

            HBox row = new HBox();
            row.setPrefHeight(62);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(5, 25, 5, 25));

            if (i < products.size() - 1) {
                row.setStyle("-fx-border-color: #F0EDE9; -fx-border-width: 0 0 1 0;");
            }

            // Coluna 1: Imagem
            StackPane imgCell = new StackPane();
            imgCell.setPrefWidth(140);
            imgCell.setAlignment(Pos.CENTER);
            try {
                ImageView img = new ImageView(new Image(getClass().getResourceAsStream(
                        "/com/shakecream/app/" + (product.getImageUrl() == null ? "choc_tradicional.png" : product.getImageUrl()))));
                img.setFitHeight(46);
                img.setPreserveRatio(true);
                imgCell.getChildren().add(img);
            } catch (Exception e) {
                Label placeholder = new Label("🖼️");
                imgCell.getChildren().add(placeholder);
            }

            // Coluna 2: Nome
            Label lblNome = new Label(product.getName());
            lblNome.setPrefWidth(240);
            lblNome.setAlignment(Pos.CENTER);
            lblNome.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 15; -fx-text-fill: " + Theme.COLOR_TEXT_DARK
                    + "; -fx-font-weight: 500;");

            // Coluna 3: Categoria
            Label lblCategoria = new Label(product.getCategoryName());
            lblCategoria.setPrefWidth(240);
            lblCategoria.setAlignment(Pos.CENTER);
            lblCategoria.setStyle(
                    "-fx-font-family: 'Montserrat'; -fx-font-size: 14; -fx-text-fill: " + Theme.COLOR_TEXT_DARK + ";");

            // Coluna 4: Preço
            Label lblPreco = new Label(String.format("R$ %.2f", product.getPrice()));
            lblPreco.setPrefWidth(240);
            lblPreco.setAlignment(Pos.CENTER);
            lblPreco.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 14; -fx-text-fill: "
                    + Theme.COLOR_TEXT_DARK + "; -fx-font-weight: bold;");

            // Coluna 5: Ações
            HBox cellAcoes = new HBox(12);
            cellAcoes.setPrefWidth(240);
            cellAcoes.setAlignment(Pos.CENTER);

            Button btnEditar = createTableActionButton("\u270F", Theme.COLOR_PRIMARY);
            Button btnDeletar = createTableActionButton("\uD83D\uDDD1", "#D9534F");

            btnEditar.setOnAction(e -> handleActionItemTable("EDITAR", product, stage));
            btnDeletar.setOnAction(e -> handleActionItemTable("DELETE", product, stage));

            cellAcoes.getChildren().addAll(btnEditar, btnDeletar);

            row.getChildren().addAll(imgCell, lblNome, lblCategoria, lblPreco, cellAcoes);
            rowsContainer.getChildren().add(row);
        }
    }

    private Label createTableHeaderLabel(String texto, double largura, Pos alinhamento) {
        Label label = new Label(texto);
        label.setPrefWidth(largura);
        label.setAlignment(alinhamento);
        label.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: "
                + Theme.COLOR_TEXT_DARK + ";");
        return label;
    }

    private Button createTableActionButton(String text, String borderColor) {
        Button btn = new Button(text);
        btn.setPrefSize(42, 32);
        btn.setStyle("-fx-background-color: transparent; -fx-border-color: " + borderColor
                + "; -fx-border-radius: 6; -fx-text-fill: " + borderColor
                + "; -fx-font-size: 14; -fx-min-width: 42; -fx-padding: 0; -fx-alignment: center; -fx-cursor: hand;");
        return btn;
    }

    private void handleBotaoNovoItem(String escopo) {
        if (escopo.equals("MERCADORIA")) {
            new AdminProductFormView().show((Stage) mainLayout.getScene().getWindow());
        }
    }

    private void handleDeleteProduct(Product product, Stage stage) {

        if (AlertComponent.showConfirmation("Excluir", "Tem certeza que deseja excluir?")) {
            try {
                productService.delete(product.getId());

                AlertComponent.showSuccess("Sucesso", "Mercadoria excluída com sucesso!");

                new AdminDashboardView().show(stage);

            } catch (Exception e) {
                AlertComponent.showError("Erro ao excluir", e.getMessage());
            }
        }
    }

    private void handleActionItemTable(String action, Product product,  Stage stage) {
        switch (action) {
            case "EDITAR":
                AdminProductFormView form = new AdminProductFormView(product);
                form.show((Stage) mainLayout.getScene().getWindow());
                break;
            case "DELETE":
                handleDeleteProduct(product, stage);
                break;
        }
    }

}