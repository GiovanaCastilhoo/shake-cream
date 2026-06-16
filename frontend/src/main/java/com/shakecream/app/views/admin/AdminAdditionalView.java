package com.shakecream.app.views.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

import com.shakecream.app.components.AlertComponent;
import com.shakecream.app.components.HeaderComponent;
import com.shakecream.app.components.LayoutComponent;
import com.shakecream.app.models.Additional;
import com.shakecream.app.services.AdditionalService;

public class AdminAdditionalView {
    private final AdditionalService additionalService = new AdditionalService();
    private Additional additional;

    // Paleta de cores oficial unificada do projeto
    private final String COLOR_BG_DARK = "#391100"; // Marrom Escuro
    private final String COLOR_PRIMARY = "#B95C68"; // Rosa/Vinho ativo
    private final String COLOR_BG_WORKSPACE = "#FAF6F2"; // Fundo creme claro
    private final String COLOR_TABLE_TH = "#F3E2E3"; // Rosa claro do Th
    private final String COLOR_TEXT_DARK = "#3C2C28"; // Marrom escuro para fontes
    private final String COLOR_TEXT_MUTED = "#8E8A85"; // Cinza de descrições

    private VBox rowsContainer;

    public void show(Stage stage) {
        BorderPane mainLayout = LayoutComponent.createMainLayout(COLOR_BG_WORKSPACE);

        HBox topHeader = HeaderComponent.create(
                "CADASTRAR NOVO ADICIONAL",
                stage,
                () -> new AdminAdditionalView().show(stage), // ação do botão voltar
                COLOR_BG_DARK);

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
        Button btnMenuCategorias = createSidebarButton("📁  Categorias", false);
        Button btnMenuAdicionais = createSidebarButton("➕  Adicionais", true); // Ativo/Rosa

        // Configuração de cliques do Menu Lateral
        btnMenuMercadorias.setOnAction(e -> new AdminDashboardView().show(stage));
        btnMenuCategorias.setOnAction(e -> new AdminCategoryView().show(stage));

        Region sidebarSpacer = new Region();
        VBox.setVgrow(sidebarSpacer, Priority.ALWAYS);

        // Regra pedida: Botão Sair redireciona para o início do Login Admin
        Button btnSair = new Button("🚪  Sair");
        btnSair.setMaxWidth(Double.MAX_VALUE);
        btnSair.setPrefHeight(40);
        btnSair.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #FFFFFF; -fx-font-family: 'Montserrat'; -fx-font-size: 20; -fx-alignment: center; -fx-cursor: hand;");
        btnSair.setOnAction(e -> new AdminLoginView().show(stage));

        sidebar.getChildren().addAll(btnMenuMercadorias, btnMenuCategorias, btnMenuAdicionais, sidebarSpacer, btnSair);
        mainLayout.setLeft(sidebar);

        // =========================================================================
        // 3. ÁREA DE TRABALHO (WORKSPACE DA SEÇÃO DE ADICIONAIS)
        // =========================================================================
        VBox workspaceContainer = new VBox(25);
        workspaceContainer.setPadding(new Insets(40, 50, 40, 50));
        workspaceContainer.setAlignment(Pos.TOP_CENTER);

        BorderPane sectionHeader = new BorderPane();
        sectionHeader.setMaxWidth(850); // Largura compacta ideal para 3 colunas equilibradas

        VBox txtTitleBox = new VBox(4);
        Label title = new Label("Gerenciar Adicionais");
        title.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 26; -fx-text-fill: "
                + COLOR_TEXT_DARK + ";");
        Label subtitle = new Label("Cadastre e edite os adicionais do cardápio");
        subtitle.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 14; -fx-text-fill: " + COLOR_TEXT_MUTED + ";");
        txtTitleBox.getChildren().addAll(title, subtitle);
        sectionHeader.setLeft(txtTitleBox);

        Button btnNovoAdicional = new Button("+  Novo Adicional");
        btnNovoAdicional.setPrefHeight(45);
        btnNovoAdicional.setPadding(new Insets(0, 20, 0, 20));
        btnNovoAdicional.setStyle("-fx-background-color: " + COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 12; -fx-cursor: hand;");
        btnNovoAdicional.setOnAction(e -> handleActionAdditional("NOVO", additional, stage));
        sectionHeader.setRight(btnNovoAdicional);

        // --- TABELA ESTILO CARD DE ADICIONAIS ---
        VBox customTableCard = new VBox();
        customTableCard.setMaxWidth(850);
        customTableCard.setStyle(
                "-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #E2DDD9; -fx-border-radius: 20; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.02), 10, 0, 0, 5);");

        HBox tableHeaderRow = new HBox();
        tableHeaderRow.setPrefHeight(42);
        tableHeaderRow.setAlignment(Pos.CENTER_LEFT);
        tableHeaderRow.setStyle("-fx-background-color: " + COLOR_TABLE_TH + "; -fx-background-radius: 20 20 0 0;");
        tableHeaderRow.setPadding(new Insets(0, 25, 0, 25));

        // Divisão proporcional e limpa para as 3 colunas
        Label thNome = createTableHeaderLabel("Nome", 400, Pos.CENTER_LEFT);
        Label thPreco = createTableHeaderLabel("Preço", 200, Pos.CENTER);
        Label thAcoes = createTableHeaderLabel("Ações", 200, Pos.CENTER);

        tableHeaderRow.getChildren().addAll(thNome, thPreco, thAcoes);
        customTableCard.getChildren().add(tableHeaderRow);

        rowsContainer = new VBox();
        customTableCard.getChildren().add(rowsContainer);

        workspaceContainer.getChildren().addAll(sectionHeader, customTableCard);

        ScrollPane scrollWorkspace = new ScrollPane(workspaceContainer);
        scrollWorkspace.setFitToWidth(true);
        scrollWorkspace.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        mainLayout.setCenter(scrollWorkspace);

        List<Additional> additonalsList = additionalService.getAdditionals();

        updateTableAdditionals(additonalsList, stage);

        stage.getScene().setRoot(mainLayout);
    }

    public void updateTableAdditionals(List<Additional> additionals, Stage stage) {
        if (rowsContainer == null)
            return;
        rowsContainer.getChildren().clear();

        for (int i = 0; i < additionals.size(); i++) {
            Additional additional = additionals.get(i);
            if (additional == null)
                continue;

            HBox row = new HBox();
            row.setPrefHeight(62);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(5, 25, 5, 25));

            if (i < additionals.size() - 1) {
                row.setStyle("-fx-border-color: #F0EDE9; -fx-border-width: 0 0 1 0;");
            }

            // Coluna 1: Nome do Adicional
            Label lblNome = new Label(additional.name);
            lblNome.setPrefWidth(400);
            lblNome.setAlignment(Pos.CENTER_LEFT);
            lblNome.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 15; -fx-text-fill: " + COLOR_TEXT_DARK
                    + "; -fx-font-weight: 500; -fx-padding: 0 0 0 20;");

            // Coluna 2: Preço Formatado
            Label lblPreco = new Label(String.format("R$ %.2f", additional.price));
            lblPreco.setPrefWidth(200);
            lblPreco.setAlignment(Pos.CENTER);
            lblPreco.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 14; -fx-text-fill: " + COLOR_TEXT_DARK
                    + "; -fx-font-weight: bold;");

            // Coluna 3: Ações do CRUD
            HBox cellAcoes = new HBox(12);
            cellAcoes.setPrefWidth(200);
            cellAcoes.setAlignment(Pos.CENTER);

            Button btnEditar = createTableActionButton("\u270F", COLOR_PRIMARY);
            Button btnDeletar = createTableActionButton("\uD83D\uDDD1", "#D9534F");

            btnEditar.setOnAction(e -> handleActionAdditional("EDITAR", additional, stage));
            btnDeletar.setOnAction(e -> handleActionAdditional("DELETAR", additional, stage));

            cellAcoes.getChildren().addAll(btnEditar, btnDeletar);

            row.getChildren().addAll(lblNome, lblPreco, cellAcoes);
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

    private void handleDeleteAdditional(Additional additional, Stage stage) {

        if (AlertComponent.showConfirmation("Excluir", "Tem certeza que deseja excluir?")) {
            try {
                additionalService.delete(additional.getId());

                AlertComponent.showSuccess("Sucesso", "Adicional excluído com sucesso!");

                new AdminAdditionalView().show(stage);

            } catch (Exception e) {
                AlertComponent.showError("Erro ao excluir", e.getMessage());
            }
        }
    }

    private void handleActionAdditional(String action, Additional additional, Stage stage) {
        switch (action) {
            case "NOVO":
                openAdditionalForm(additional);
                break;
            case "EDITAR":
                openAdditionalForm(additional);
                break;
            case "DELETAR":
                handleDeleteAdditional(additional, stage);
                break;
        }

    }

    private void openAdditionalForm(Additional additional) {
        AdminAdditionalFormView form = new AdminAdditionalFormView(additional);
        form.show((Stage) rowsContainer.getScene().getWindow());
    }
}