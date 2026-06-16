package com.shakecream.app.views.admin;

import com.shakecream.app.components.AlertComponent;
import com.shakecream.app.models.Category;
import com.shakecream.app.services.CategoryService;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminCategoryFormView {

        private final CategoryService categoryService = new CategoryService();

        private final Category category;

        private final String COLOR_BG_DARK = "#391100"; // Marrom Escuro
        private final String COLOR_PRIMARY = "#B95C68"; // Rosa/Vinho ativo
        private final String COLOR_BG_WORKSPACE = "#FAF6F2"; // Fundo creme claro
        private final String COLOR_TEXT_DARK = "#3C2C28"; // Marrom escuro para fontes
        private final String COLOR_TEXT_MUTED = "#8E8A85"; // Cinza de descrições
        private final String COLOR_INPUT_BG = "#F0F0F0"; // Cor padrão dos inputs

        // 🔥 CREATE
        public AdminCategoryFormView() {
                this.category = null;
        }

        // 🔥 EDIT
        public AdminCategoryFormView(Category category) {
                this.category = category;
        }

        public void show(Stage stage) {
                BorderPane mainLayout = new BorderPane();
                mainLayout.setStyle("-fx-background-color: " + COLOR_BG_WORKSPACE + ";");

                // =========================================================================
                // 1. TOP HEADER (CABEÇALHO SUPERIOR - 100% CENTRALIZADO)
                // =========================================================================
                HBox topHeader = new HBox();
                topHeader.setPrefHeight(80);
                topHeader.setAlignment(Pos.CENTER_LEFT);
                topHeader.setPadding(new Insets(0, 40, 0, 30));
                topHeader.setStyle("-fx-background-color: " + COLOR_BG_DARK + ";");

                Button btnVoltarHeader = new Button("←  Voltar");
                btnVoltarHeader.setStyle(
                                "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 16; -fx-cursor: hand;");
                btnVoltarHeader.setOnAction(e -> new AdminCategoryView().show(stage));

                Label lbTituloTopHeader = new Label("CADASTRAR NOVA CATEGORIA");
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
                sidebar.setMaxWidth(240);
                sidebar.setPadding(new Insets(30, 15, 30, 15));
                sidebar.setStyle(
                                "-fx-background-color: " + COLOR_BG_DARK
                                                + "; -fx-border-color: #391100; -fx-border-width: 1 0 0 0;");

                Button btnMenuMercadorias = createSidebarButton("🛒  Mercadorias", false);
                Button btnMenuCategorias = createSidebarButton("📁  Categorias", true);
                Button btnMenuAdicionais = createSidebarButton("➕  Adicionais", false);

                btnMenuMercadorias.setOnAction(e -> new AdminDashboardView().show(stage));
                btnMenuCategorias.setOnAction(e -> new AdminCategoryView().show(stage));

                Region sidebarSpacer = new Region();
                VBox.setVgrow(sidebarSpacer, Priority.ALWAYS);

                Button btnSair = new Button("🚪  Sair");
                btnSair.setMaxWidth(Double.MAX_VALUE);
                btnSair.setPrefHeight(40);
                btnSair.setStyle(
                                "-fx-background-color: transparent; -fx-text-fill: #FFFFFF; -fx-font-family: 'Montserrat'; -fx-font-size: 20; -fx-alignment: center; -fx-cursor: hand;");
                btnSair.setOnAction(e -> new AdminLoginView().show(stage));

                sidebar.getChildren().addAll(btnMenuMercadorias, btnMenuCategorias, btnMenuAdicionais, sidebarSpacer,
                                btnSair);

                // =========================================================================
                // 3. ÁREA DE TRABALHO COESA (CENTRALIZAÇÃO ABSOLUTA COM OVERLAY)
                // =========================================================================
                StackPane contentArea = new StackPane();
                contentArea.setStyle("-fx-background-color: " + COLOR_BG_WORKSPACE + ";");
                mainLayout.setCenter(contentArea);

                // Container onde o card vai se alinhar estritamente pelo meio da tela
                VBox workspaceContainer = new VBox();
                workspaceContainer.setPadding(new Insets(60, 0, 40, 0));
                workspaceContainer.setAlignment(Pos.TOP_CENTER);

                // Card Branco estruturado
                VBox formCard = new VBox(20);
                formCard.setMaxWidth(600);
                formCard.setPadding(new Insets(35));
                formCard.setStyle(
                                "-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #E2DDD9; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.02), 10, 0, 0, 5);");

                // Títulos internos
                VBox txtTitleBox = new VBox(4);
                Label title = category == null ? new Label("+ Nova Categoria") : new Label("Editar");
                title.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 26; -fx-text-fill: "
                                + COLOR_TEXT_DARK + ";");
                Label subtitle = new Label("Preencha o nome identificador para o cardápio");
                subtitle.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 14; -fx-text-fill: " + COLOR_TEXT_MUTED
                                + ";");
                txtTitleBox.getChildren().addAll(title, subtitle);

                // Campo de entrada
                VBox boxInput = new VBox(8);
                Label labelNome = new Label("Nome da categoria");
                labelNome.setStyle(
                                "-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: "
                                                + COLOR_TEXT_DARK + ";");

                TextField txtNome = new TextField();
                txtNome.setPromptText("Ex: Milk Shakes");
                txtNome.setPrefHeight(45);
                txtNome.setStyle(
                                "-fx-background-color: " + COLOR_INPUT_BG + ";" +
                                                "-fx-background-radius: 12;" +
                                                "-fx-prompt-text-fill: #A5A19B;" +
                                                "-fx-text-fill: " + COLOR_TEXT_DARK + ";" +
                                                "-fx-padding: 8 15 8 15;" +
                                                "-fx-font-family: 'Montserrat';" +
                                                "-fx-font-size: 14;");
                boxInput.getChildren().addAll(labelNome, txtNome);

                if (category != null) {
                        txtNome.setText(category.getName());
                }

                Region formSpacer = new Region();
                formSpacer.setPrefHeight(10);

                // Botões de Ação
                HBox actionsRow = new HBox(15);
                actionsRow.setAlignment(Pos.CENTER_RIGHT);

                Button btnCancelar = new Button("Cancelar");
                btnCancelar.setPrefSize(160, 45);
                btnCancelar.setStyle(
                                "-fx-background-color: white; -fx-border-color: #A09A94; -fx-border-radius: 18; -fx-background-radius: 18; -fx-text-fill: "
                                                + COLOR_TEXT_DARK
                                                + "; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-cursor: hand;");
                btnCancelar.setOnAction(e -> new AdminCategoryView().show(stage));

                Button btnSalvar = new Button("Salvar");
                btnSalvar.setPrefSize(160, 45);
                btnSalvar.setStyle("-fx-background-color: " + COLOR_PRIMARY
                                + "; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 15; -fx-background-radius: 18; -fx-cursor: hand;");
                btnSalvar.setOnAction(e -> handleSaveCategory(txtNome.getText(), stage));

                actionsRow.getChildren().addAll(btnCancelar, btnSalvar);
                formCard.getChildren().addAll(txtTitleBox, boxInput, formSpacer, actionsRow);
                workspaceContainer.getChildren().add(formCard);

                // Monta a árvore de componentes de forma independente dentro do StackPane
                contentArea.getChildren().addAll(workspaceContainer, sidebar);

                // Aplica as âncoras absolutas: o container centraliza 100% na tela e a sidebar
                // flutua na esquerda
                StackPane.setAlignment(workspaceContainer, Pos.TOP_CENTER);
                StackPane.setAlignment(sidebar, Pos.TOP_LEFT);

                stage.getScene().setRoot(mainLayout);
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

        private void handleSaveCategory(String name, Stage stage) {
                String message;

                if (name == null || name.isBlank()) {
                        AlertComponent.showWarning("Campo obrigatório", "Preencha o nome.");
                        return;
                }

                if (category == null) {
                        // CREATE
                        Category newCat = new Category();
                        newCat.setName(name);

                        categoryService.create(newCat);
                        message = "Categoria salva com sucesso!";

                } else {
                        // UPDATE
                        category.setName(name);

                        categoryService.update(category.getId(), category);
                        message = "Atualizada com sucesso!";
                }

                AlertComponent.showWarning("Sucesso", message);
                new AdminCategoryView().show(stage);
        }
}