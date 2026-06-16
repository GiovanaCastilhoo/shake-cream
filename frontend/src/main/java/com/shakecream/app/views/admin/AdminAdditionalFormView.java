package com.shakecream.app.views.admin;

import com.shakecream.app.components.AlertComponent;
import com.shakecream.app.models.Additional;
import com.shakecream.app.services.AdditionalService;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminAdditionalFormView {

        private final AdditionalService additionalService = new AdditionalService();

        private final Additional additional;

        private final String COLOR_BG_DARK = "#391100"; // Marrom Escuro
        private final String COLOR_PRIMARY = "#B95C68"; // Rosa/Vinho ativo
        private final String COLOR_BG_WORKSPACE = "#FAF6F2"; // Fundo creme claro
        private final String COLOR_TEXT_DARK = "#3C2C28"; // Marrom escuro para fontes
        private final String COLOR_TEXT_MUTED = "#8E8A85"; // Cinza de descrições
        private final String COLOR_INPUT_BG = "#F0F0F0"; // Cor padrão dos inputs

        public AdminAdditionalFormView() {
                this.additional = null;
        }

        public AdminAdditionalFormView(Additional additional) {
                this.additional = additional;
        }

        public void show(Stage stage) {
                BorderPane mainLayout = new BorderPane();
                mainLayout.setStyle("-fx-background-color: " + COLOR_BG_WORKSPACE + ";");

                // =========================================================================
                // 1. TOP HEADER (CABEÇALHO SUPERIOR - CENTRALIZADO COM O CARD)
                // =========================================================================
                HBox topHeader = new HBox();
                topHeader.setPrefHeight(80);
                topHeader.setAlignment(Pos.CENTER_LEFT);
                topHeader.setPadding(new Insets(0, 40, 0, 30));
                topHeader.setStyle("-fx-background-color: " + COLOR_BG_DARK + ";");

                // Ação Voltar: Retorna para a listagem de adicionais
                Button btnVoltarHeader = new Button("←  Voltar");
                btnVoltarHeader.setStyle(
                                "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 16; -fx-cursor: hand;");
                btnVoltarHeader.setOnAction(e -> new AdminAdditionalView().show(stage));

                // ✨ ALTERADO AQUI: Título atualizado para a nova identificação padronizada
                Label lbTituloTopHeader = new Label("CADASTRAR NOVO ADICIONAL");
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
                Button btnMenuCategorias = createSidebarButton("📁  Categorias", false);
                Button btnMenuAdicionais = createSidebarButton("➕  Adicionais", true); // Ativo (Rosa)

                btnMenuMercadorias.setOnAction(e -> new AdminDashboardView().show(stage));
                btnMenuCategorias.setOnAction(e -> new AdminCategoryView().show(stage));
                btnMenuAdicionais.setOnAction(e -> new AdminAdditionalView().show(stage));

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
                // 3. ÁREA DO FORMULÁRIO (CENTRALIZAÇÃO ABSOLUTA MILIMÉTRICA)
                // =========================================================================
                StackPane contentArea = new StackPane();
                contentArea.setStyle("-fx-background-color: " + COLOR_BG_WORKSPACE + ";");
                mainLayout.setCenter(contentArea);

                // Container do Card (Eixo central perfeito)
                VBox workspaceContainer = new VBox();
                workspaceContainer.setPadding(new Insets(60, 0, 40, 0));
                workspaceContainer.setAlignment(Pos.TOP_CENTER);

                VBox formCard = new VBox(20);
                formCard.setMaxWidth(600);
                formCard.setPadding(new Insets(35));
                formCard.setStyle(
                                "-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #E2DDD9; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.02), 10, 0, 0, 5);");

                // Títulos Internos do Card
                VBox txtTitleBox = new VBox(4);
                Label title = additional == null ? new Label("+ Novo Adicional") : new Label("Editar");
                title.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 26; -fx-text-fill: "
                                + COLOR_TEXT_DARK + ";");
                Label subtitle = new Label("Preencha as informações do novo adicional");
                subtitle.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 14; -fx-text-fill: " + COLOR_TEXT_MUTED
                                + ";");
                txtTitleBox.getChildren().addAll(title, subtitle);

                // Campo 1: Nome do Adicional
                VBox boxNome = new VBox(6);
                Label labelNome = new Label("Nome do adicional");
                labelNome.setStyle(
                                "-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: "
                                                + COLOR_TEXT_DARK + ";");
                TextField txtNome = new TextField();
                txtNome.setPromptText("Ex: Oreo");
                styleInputControl(txtNome);
                boxNome.getChildren().addAll(labelNome, txtNome);

                // Campo 2: Preço (R$)
                VBox boxPreco = new VBox(6);
                Label labelPreco = new Label("Preço (R$)");
                labelPreco.setStyle(
                                "-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: "
                                                + COLOR_TEXT_DARK + ";");
                TextField txtPreco = new TextField();
                txtPreco.setPromptText("Ex: 2,50");
                styleInputControl(txtPreco);
                boxPreco.getChildren().addAll(labelPreco, txtPreco);

                if (additional != null) {
                        txtNome.setText(additional.getName());
                        txtPreco.setText(String.valueOf(additional.getPrice()));
                }

                Region formSpacer = new Region();
                formSpacer.setPrefHeight(10);

                // Linha de Botões (Cancelar e Salvar)
                HBox actionsRow = new HBox(15);
                actionsRow.setAlignment(Pos.CENTER_RIGHT);

                Button btnCancelar = new Button("Cancelar");
                btnCancelar.setPrefSize(160, 45);
                btnCancelar.setStyle(
                                "-fx-background-color: white; -fx-border-color: #A09A94; -fx-border-radius: 18; -fx-background-radius: 18; -fx-text-fill: "
                                                + COLOR_TEXT_DARK
                                                + "; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-cursor: hand;");
                btnCancelar.setOnAction(e -> new AdminAdditionalView().show(stage)); // Retorna sem salvar

                Button btnSalvar = new Button("Salvar");
                btnSalvar.setPrefSize(160, 45);
                btnSalvar.setStyle("-fx-background-color: " + COLOR_PRIMARY
                                + "; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 15; -fx-background-radius: 18; -fx-cursor: hand;");
                btnSalvar.setOnAction(e -> handleSaveAdditional(txtNome.getText(), txtPreco.getText(), stage));

                actionsRow.getChildren().addAll(btnCancelar, btnSalvar);

                // Montagem do Card
                formCard.getChildren().addAll(txtTitleBox, boxNome, boxPreco, formSpacer, actionsRow);
                workspaceContainer.getChildren().add(formCard);

                // ScrollPane de controle de tamanho absoluto
                ScrollPane scrollWorkspace = new ScrollPane(workspaceContainer);
                scrollWorkspace.setFitToWidth(true);
                scrollWorkspace.setStyle(
                                "-fx-background: transparent; -fx-background-color: transparent; -fx-vbar-policy: as-needed; -fx-hbar-policy: never;");
                scrollWorkspace.setMaxWidth(650);

                // Aplicação do Overlay absoluto (O card centra na tela e a sidebar flutua na
                // esquerda)
                contentArea.getChildren().addAll(scrollWorkspace, sidebar);
                StackPane.setAlignment(scrollWorkspace, Pos.TOP_CENTER);
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

        private void styleInputControl(TextField input) {
                input.setPrefHeight(45);
                input.setStyle(
                                "-fx-background-color: " + COLOR_INPUT_BG + ";" +
                                                "-fx-background-radius: 12;" +
                                                "-fx-prompt-text-fill: #A5A19B;" +
                                                "-fx-text-fill: " + COLOR_TEXT_DARK + ";" +
                                                "-fx-padding: 8 15 8 15;" +
                                                "-fx-font-family: 'Montserrat';" +
                                                "-fx-font-size: 14;");
        }

        private void handleSaveAdditional(String name, String price, Stage stage) {
                String message;

                if (name == null || name.isEmpty() || price == null || price.isEmpty()) {
                        AlertComponent.showWarning("Campos obrigatórios", "Preencha nome e preço.");
                        return;
                }

                try {
                        double convertPriceToDouble = Double.parseDouble(price.replace(",", "."));

                        if (additional == null) {

                                Additional newAdditional = new Additional();
                                newAdditional.setName(name);
                                newAdditional.setPrice(convertPriceToDouble);

                                additionalService.create(newAdditional);
                                message = "Adicional salvo com sucesso!";

                        } else {
                                additional.setName(name);
                                additional.setPrice(convertPriceToDouble);

                                additionalService.update(additional.getId(), additional);
                                message = "Atualizado com sucesso!";
                        }

                        AlertComponent.showWarning("Sucesso", message);
                        new AdminAdditionalView().show(stage);

                } catch (NumberFormatException e) {
                        AlertComponent.showWarning("Preço inválido", "Digite um valor válido. Ex: 2,50");

                } catch (Exception e) {
                        AlertComponent.showWarning("Erro ao salvar", e.getMessage());
                }
        }
}