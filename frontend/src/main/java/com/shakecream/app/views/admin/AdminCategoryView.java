package com.shakecream.app.views.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

import com.shakecream.app.models.Category;
import com.shakecream.app.services.CategoryService;

public class AdminCategoryView {

    private final CategoryService categoryService = new CategoryService();
    
    private final String COLOR_BG_DARK = "#391100";
    private final String COLOR_PRIMARY = "#B95C68";
    private final String COLOR_BG_WORKSPACE = "#FAF6F2";
    private final String COLOR_TABLE_TH = "#F3E2E3";
    private final String COLOR_TEXT_DARK = "#3C2C28";
    private final String COLOR_TEXT_MUTED = "#8E8A85";

    private VBox rowsContainer;

    public void show(Stage stage) {
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + COLOR_BG_WORKSPACE + ";");

        // =========================================================================
        // 1. TOP HEADER (CABEÇALHO SUPERIOR)
        // =========================================================================
        HBox topHeader = new HBox();
        topHeader.setPrefHeight(80);
        topHeader.setAlignment(Pos.CENTER_LEFT);
        topHeader.setPadding(new Insets(0, 40, 0, 30));
        topHeader.setStyle("-fx-background-color: " + COLOR_BG_DARK + ";");

        Button btnVoltarHeader = new Button("←  Voltar");
        btnVoltarHeader.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 16; -fx-cursor: hand;");
        btnVoltarHeader.setOnAction(e -> new AdminDashboardView().show(stage));

        Label lbTituloTopHeader = new Label("CATEGORIAS");
        lbTituloTopHeader.setStyle(
                "-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 22; -fx-font-weight: bold;");

        Region topSpacer1 = new Region();
        HBox.setHgrow(topSpacer1, Priority.ALWAYS);
        Region topSpacer2 = new Region();
        HBox.setHgrow(topSpacer2, Priority.ALWAYS);

        topHeader.getChildren().addAll(btnVoltarHeader, topSpacer1, lbTituloTopHeader, topSpacer2);
        mainLayout.setTop(topHeader);

        // =========================================================================
        // 2. SIDEBAR (MENU LATERAL ESQUERDO)
        // =========================================================================
        VBox sidebar = new VBox(12);
        sidebar.setPrefWidth(240);
        sidebar.setPadding(new Insets(30, 15, 30, 15));
        sidebar.setStyle(
                "-fx-background-color: " + COLOR_BG_DARK + "; -fx-border-color: #391100; -fx-border-width: 1 0 0 0;");

        Button btnMenuMercadorias = createSidebarButton("🛒  Mercadorias", false);
        Button btnMenuCategorias = createSidebarButton("📁  Categorias", true);
        Button btnMenuAdicionais = createSidebarButton("➕  Adicionais", false);

        btnMenuMercadorias.setOnAction(e -> new AdminDashboardView().show(stage));
        // Procure por esta linha e configure o clique:
        btnMenuAdicionais.setOnAction(e -> new AdminAdditionalView().show(stage));

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
        // 3. ÁREA DE TRABALHO (WORKSPACE DA TELA)
        // =========================================================================
        VBox workspaceContainer = new VBox(25);
        workspaceContainer.setPadding(new Insets(40, 50, 40, 50));
        workspaceContainer.setAlignment(Pos.TOP_CENTER);

        BorderPane sectionHeader = new BorderPane();
        // ✨ ALTERAÇÃO: Largura máxima da seção reduzida para 800px para ficar compacta
        sectionHeader.setMaxWidth(800);

        VBox txtTitleBox = new VBox(4);
        Label title = new Label("Gerenciar Categorias");
        title.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 26; -fx-text-fill: "
                + COLOR_TEXT_DARK + ";");
        Label subtitle = new Label("Organize as categorias do cardápio");
        subtitle.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 14; -fx-text-fill: " + COLOR_TEXT_MUTED + ";");
        txtTitleBox.getChildren().addAll(title, subtitle);
        sectionHeader.setLeft(txtTitleBox);

        Button btnNovaCategoria = new Button("+  Nova categoria");
        btnNovaCategoria.setPrefHeight(45);
        btnNovaCategoria.setPadding(new Insets(0, 20, 0, 20));
        btnNovaCategoria.setStyle("-fx-background-color: " + COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 12; -fx-cursor: hand;");
        btnNovaCategoria.setOnAction(e -> handleNewCategoryTrigger());
        sectionHeader.setRight(btnNovaCategoria);

        // --- TABELA ESTILO CARD DE CATEGORIAS REDUZIDA ---
        VBox customTableCard = new VBox();
        // ✨ ALTERAÇÃO: Largura máxima do card reduzida para 800px
        customTableCard.setMaxWidth(800);
        customTableCard.setStyle(
                "-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #E2DDD9; -fx-border-radius: 20; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.02), 10, 0, 0, 5);");

        HBox tableHeaderRow = new HBox();
        tableHeaderRow.setPrefHeight(42);
        tableHeaderRow.setAlignment(Pos.CENTER_LEFT);
        tableHeaderRow.setStyle("-fx-background-color: " + COLOR_TABLE_TH + "; -fx-background-radius: 20 20 0 0;");
        tableHeaderRow.setPadding(new Insets(0, 25, 0, 25));

        // ✨ ALTERAÇÃO: Proporções das colunas redistribuídas para casar com os 800px
        Label thNomeCategoria = createTableHeaderLabel("Nome da categoria", 500, Pos.CENTER_LEFT);
        Label thAcoes = createTableHeaderLabel("Ações", 250, Pos.CENTER);

        tableHeaderRow.getChildren().addAll(thNomeCategoria, thAcoes);
        customTableCard.getChildren().add(tableHeaderRow);

        rowsContainer = new VBox();
        customTableCard.getChildren().add(rowsContainer);

        workspaceContainer.getChildren().addAll(sectionHeader, customTableCard);

        ScrollPane scrollWorkspace = new ScrollPane(workspaceContainer);
        scrollWorkspace.setFitToWidth(true);
        scrollWorkspace.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        mainLayout.setCenter(scrollWorkspace);

        List<Category> categoriesList = categoryService.getCategories();

        updateTableCategories(categoriesList);

        stage.getScene().setRoot(mainLayout);
    }

    public void updateTableCategories(List<Category> categories) {
        if (rowsContainer == null)
            return;
        rowsContainer.getChildren().clear();

        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            if (category == null)
                continue;

            HBox row = new HBox();
            row.setPrefHeight(62);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(5, 25, 5, 25));

            if (i < categories.size() - 1) {
                row.setStyle("-fx-border-color: #F0EDE9; -fx-border-width: 0 0 1 0;");
            }

            // ✨ ALTERAÇÃO: Sincronizado largura das células com a nova proporção (500px)
            Label lblNome = new Label(category.getName());
            lblNome.setPrefWidth(500);
            lblNome.setAlignment(Pos.CENTER_LEFT);
            lblNome.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 15; -fx-text-fill: " + COLOR_TEXT_DARK
                    + "; -fx-font-weight: 500; -fx-padding: 0 0 0 20;");

            // ✨ ALTERAÇÃO: Sincronizado largura do box de ações com a nova proporção
            // (250px)
            HBox cellAcoes = new HBox(12);
            cellAcoes.setPrefWidth(250);
            cellAcoes.setAlignment(Pos.CENTER);

            Button btnEditar = createTableActionButton("\u270F", COLOR_PRIMARY);

            btnEditar.setOnAction(e -> handleActionCategory("EDITAR", category));

            cellAcoes.getChildren().addAll(btnEditar);

            row.getChildren().addAll(lblNome, cellAcoes);
            rowsContainer.getChildren().add(row);
        }
    }

    private Button createSidebarButton(String texto, boolean ativo) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(50);

        String estiloInativo = "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 15; -fx-alignment: center; -fx-cursor: hand;";
        String estiloAtivo = "-fx-background-color: " + COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 15; -fx-alignment: center; -fx-background-radius: 12; -fx-cursor: hand;";

        btn.setStyle(ativo ? estiloAtivo : estiloInativo);
        return btn;
    }

    private Label createTableHeaderLabel(String texto, double largura, Pos alinhamento) {
        Label label = new Label(texto);
        label.setPrefWidth(largura);
        label.setAlignment(alinhamento);
        label.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: "
                + COLOR_TEXT_DARK + ";");
        if (alinhamento == Pos.CENTER_LEFT) {
            label.setStyle(label.getStyle() + "-fx-padding: 0 0 0 20;");
        }
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

    private void handleNewCategoryTrigger() {
        AdminCategoryFormView form = new AdminCategoryFormView();
        form.show((Stage) rowsContainer.getScene().getWindow());
    }

    private void handleActionCategory(String action, Category category) {

        switch (action) {

            case "EDITAR":
                openCategoryForm(category);
                break;
        }
    }

    private void openCategoryForm(Category category) {
        AdminCategoryFormView form = new AdminCategoryFormView(category);
        form.show((Stage) rowsContainer.getScene().getWindow());
    }

}