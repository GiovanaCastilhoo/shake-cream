package com.shakecream.app.views.admin;

import java.util.List;

import com.shakecream.app.HelloApplication;
import com.shakecream.app.components.AlertComponent;
import com.shakecream.app.models.Category;
import com.shakecream.app.models.Product;
import com.shakecream.app.services.CategoryService;
import com.shakecream.app.services.ProductService;
import com.shakecream.app.ui.Theme;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminProductFormView {

    private final ProductService productService = new ProductService();
    private final Product product;

    public AdminProductFormView() {
        this.product = null;
    }

    public AdminProductFormView(Product product) {
        this.product = product;
    }

    public void show(Stage stage) {
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + Theme.COLOR_BG_WORKSPACE + ";");

        // =========================================================================
        // 1. TOP HEADER (CABEÇALHO COM BOTÃO VOLTAR)
        // =========================================================================
        HBox topHeader = new HBox();
        topHeader.setPrefHeight(80);
        topHeader.setAlignment(Pos.CENTER_LEFT);
        topHeader.setPadding(new Insets(0, 40, 0, 30));
        topHeader.setStyle("-fx-background-color: " + Theme.COLOR_BG_DARK + ";");

        // Botão Voltar posicionado à esquerda igual ao Figma
        Button btnVoltarHeader = new Button("←  Voltar");
        btnVoltarHeader.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 16; -fx-cursor: hand;");
        btnVoltarHeader.setOnAction(e -> new AdminDashboardView().show(stage));

        // Título centralizado
        Label lbTituloTopHeader = new Label("CADASTRAR NOVA MERCADORIA");
        lbTituloTopHeader.setStyle(
                "-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-size: 22; -fx-font-weight: bold;");

        Region topSpacer1 = new Region();
        HBox.setHgrow(topSpacer1, Priority.ALWAYS);
        Region topSpacer2 = new Region();
        HBox.setHgrow(topSpacer2, Priority.ALWAYS);

        // O spacer força o título a ficar centralizado e o botão na ponta esquerda
        topHeader.getChildren().addAll(btnVoltarHeader, topSpacer1, lbTituloTopHeader, topSpacer2);
        mainLayout.setTop(topHeader);

        // =========================================================================
        // 2. SIDEBAR (MENU LATERAL ESQUERDO)
        // =========================================================================
        VBox sidebar = new VBox(12);
        sidebar.setPrefWidth(240);
        sidebar.setPadding(new Insets(30, 15, 30, 15));
        sidebar.setStyle("-fx-background-color: " + Theme.COLOR_BG_DARK
                + "; -fx-border-color: #391100; -fx-border-width: 1 0 0 0;");

        // "Mercadorias" continua ativo (rosa) pois estamos dentro do fluxo dele
        Button btnMenuMercadorias = new Button("🛒  Mercadorias");
        btnMenuMercadorias.setStyle("-fx-background-color: " + Theme.COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 15; -fx-alignment: center; -fx-background-radius: 12;");
        btnMenuMercadorias.setMaxWidth(Double.MAX_VALUE);
        btnMenuMercadorias.setPrefHeight(50);

        Button btnMenuCategorias = createSidebarButton("📁  Categorias");
        Button btnMenuAdicionais = createSidebarButton("➕  Adicionais");

        btnMenuMercadorias.setOnAction(e -> new AdminDashboardView().show(stage));
        btnMenuCategorias.setOnAction(e -> new AdminDashboardView().show(stage));
        btnMenuAdicionais.setOnAction(e -> new AdminDashboardView().show(stage));

        Region sidebarSpacer = new Region();
        VBox.setVgrow(sidebarSpacer, Priority.ALWAYS);

        Button btnSair = new Button("🚪  Sair");
        btnSair.setMaxWidth(Double.MAX_VALUE);
        btnSair.setPrefHeight(40);
        btnSair.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #FFFFFF; -fx-font-family: 'Montserrat'; -fx-font-size: 20; -fx-alignment: center; -fx-cursor: hand;");
        btnSair.setOnAction(e -> new HelloApplication().show(stage));

        sidebar.getChildren().addAll(btnMenuMercadorias, btnMenuCategorias, btnMenuAdicionais, sidebarSpacer, btnSair);
        mainLayout.setLeft(sidebar);

        // =========================================================================
        // 3. ÁREA DO FORMULÁRIO (WORKSPACE COMPACTO E PROPORCIONAL)
        // =========================================================================
        VBox workspaceContainer = new VBox(20);
        workspaceContainer.setPadding(new Insets(40, 50, 40, 50));
        workspaceContainer.setAlignment(Pos.TOP_CENTER);
        workspaceContainer.setMaxWidth(1150); // Sincronizado com a largura nobre do seu dashboard

        // Bloco de Títulos da Seção
        VBox txtTitleBox = new VBox(4);
        txtTitleBox.setMaxWidth(850);
        Label title = product == null ? new Label("+ Nova Mercadoria") : new Label("Editar");
        title.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 26; -fx-text-fill: "
                + Theme.COLOR_TEXT_DARK + ";");
        Label subtitle = new Label("Preencha as informações da mercadoria");
        subtitle.setStyle(
                "-fx-font-family: 'Montserrat'; -fx-font-size: 14; -fx-text-fill: " + Theme.COLOR_TEXT_MUTED + ";");
        txtTitleBox.getChildren().addAll(title, subtitle);

        // Layout Dividido: Coluna da Imagem (Esquerda) vs Coluna de Inputs (Direita)
        HBox formSplitLayout = new HBox(50);
        formSplitLayout.setAlignment(Pos.TOP_CENTER);
        formSplitLayout.setMaxWidth(850);
        formSplitLayout.setPadding(new Insets(20, 0, 0, 0));

        // --- COLUNA DA ESQUERDA: UPLOAD DE IMAGEM & CANCELAR ---
        VBox leftFormCol = new VBox(25);
        leftFormCol.setAlignment(Pos.TOP_CENTER);

        // Container Quadrado de Adicionar Imagem do Figma
        VBox imageUploadBox = new VBox(12);
        imageUploadBox.setPrefSize(280, 280);
        imageUploadBox.setAlignment(Pos.CENTER);
        imageUploadBox.setStyle(
                "-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #E2DDD9; -fx-border-width: 1.5; -fx-border-radius: 20; -fx-cursor: hand;");

        Label lblImageIcon = new Label("🖼️"); // Ícone central padrão figma
        lblImageIcon.setStyle("-fx-font-size: 48; -fx-text-fill: #C0BBB4;");
        Label lblImageText = new Label("Adicionar imagem");
        lblImageText.setStyle(
                "-fx-font-family: 'Montserrat'; -fx-font-size: 14; -fx-text-fill: " + Theme.COLOR_TEXT_MUTED + ";");
        imageUploadBox.getChildren().addAll(lblImageIcon, lblImageText);

        // Botão Cancelar estilo Outline (Fundo transparente/branco com contorno escuro)
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setPrefSize(280, 45);
        btnCancelar.setStyle(
                "-fx-background-color: white; -fx-border-color: #A09A94; -fx-border-radius: 18; -fx-background-radius: 18; -fx-text-fill: "
                        + Theme.COLOR_TEXT_DARK
                        + "; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> new AdminDashboardView().show(stage)); // Retorna limpando modificações

        leftFormCol.getChildren().addAll(imageUploadBox, btnCancelar);

        // --- COLUNA DA DIREITA: CAMPOS DO FORMULÁRIO ---
        VBox rightFormCol = new VBox(15);
        rightFormCol.setPrefWidth(500);
        HBox.setHgrow(rightFormCol, Priority.ALWAYS);

        // Campo 1: Nome da Mercadoria
        VBox boxNome = createFormFieldLabel("Nome da mercadoria");
        TextField txtNome = new TextField();
        txtNome.setPromptText("Ex: Chocolate");
        styleInputControl(txtNome);
        boxNome.getChildren().add(txtNome);

        VBox boxCategoria = createFormFieldLabel("Categoria");
        ComboBox<Category> cbCategoria = new ComboBox<>();

        cbCategoria.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });

        cbCategoria.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });

        cbCategoria.setMaxWidth(Double.MAX_VALUE);
        cbCategoria.setPrefHeight(45);
        cbCategoria.setStyle(
                "-fx-background-color: " + Theme.COLOR_INPUT_BG +
                        "; -fx-background-radius: 12;" +
                        "-fx-font-family: 'Montserrat';" +
                        "-fx-font-size: 14;" +
                        "-fx-text-fill: " + Theme.COLOR_TEXT_DARK + ";");

        boxCategoria.getChildren().add(cbCategoria);

        CategoryService service = new CategoryService();
        List<Category> categories = service.getCategories();

        cbCategoria.getItems().addAll(categories);

        // Campo 3: Descrição (TextArea para multiplas linhas)
        VBox boxDescricao = createFormFieldLabel("Descrição");
        TextArea txtDescricao = new TextArea();
        txtDescricao.setPromptText("Delicioso Milk Shake de chocolate...");
        txtDescricao.setPrefHeight(100);
        txtDescricao.setWrapText(true);
        styleInputControl(txtDescricao);
        boxDescricao.getChildren().add(txtDescricao);

        // Campo 4: Preço (R$)
        VBox boxPreco = createFormFieldLabel("Preço (R$)");
        TextField txtPreco = new TextField(); // Valor fixado figma
        txtPreco.setPromptText("---");
        styleInputControl(txtPreco);
        boxPreco.getChildren().add(txtPreco);

        if (product != null) {

            txtNome.setText(product.getName() != null ? product.getName() : "");

            txtDescricao.setText(
                    product.getDescription() != null
                            ? product.getDescription()
                            : "");

            txtPreco.setText(
                    product.getPrice() != null
                            ? String.valueOf(product.getPrice())
                            : "");

            if (product.getCategoryId() != null) {
                for (Category c : categories) {
                    if (c.getId().equals(product.getCategoryId())) {
                        cbCategoria.setValue(c);
                        break;
                    }
                }
            }
        }

        // Linha de Ação do Botão Salvar (Posicionado na extrema direita da coluna)
        HBox saveButtonRow = new HBox();
        saveButtonRow.setAlignment(Pos.CENTER_RIGHT);
        saveButtonRow.setPadding(new Insets(10, 0, 0, 0));

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setPrefSize(220, 45);
        btnSalvar.setStyle("-fx-background-color: " + Theme.COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 15; -fx-background-radius: 18; -fx-cursor: hand;");

        btnSalvar.setOnAction(e -> handleSaveProduct(
                txtNome.getText(), cbCategoria.getValue(), txtDescricao.getText(), txtPreco.getText(), stage));
        saveButtonRow.getChildren().add(btnSalvar);

        rightFormCol.getChildren().addAll(boxNome, boxCategoria, boxDescricao, boxPreco, saveButtonRow);
        formSplitLayout.getChildren().addAll(leftFormCol, rightFormCol);

        workspaceContainer.getChildren().addAll(txtTitleBox, formSplitLayout);

        ScrollPane scrollWorkspace = new ScrollPane(workspaceContainer);
        scrollWorkspace.setFitToWidth(true);
        scrollWorkspace.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        mainLayout.setCenter(scrollWorkspace);

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

    private VBox createFormFieldLabel(String textoLabel) {
        VBox box = new VBox(6);
        Label label = new Label(textoLabel);
        label.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: "
                + Theme.COLOR_TEXT_DARK + ";");
        box.getChildren().add(label);
        return box;
    }

    // Estilização unificada dos campos de entrada de texto na cor #F0F0F0 pedida
    private void styleInputControl(Control input) {
        input.setPrefHeight(45);
        input.setStyle(
                "-fx-background-color: " + Theme.COLOR_INPUT_BG + ";" +
                        "-fx-background-radius: 12;" +
                        "-fx-prompt-text-fill: #A5A19B;" +
                        "-fx-text-fill: " + Theme.COLOR_TEXT_DARK + ";" +
                        "-fx-padding: 8 15 8 15;" +
                        "-fx-font-family: 'Montserrat';" +
                        "-fx-font-size: 14;");
    }

    private void handleSaveProduct(String name, Category category, String description, String price, Stage stage) {
        String message;

        if (name == null || name.isEmpty() || price == null || price.isEmpty()) {
            AlertComponent.showWarning("Campos obrigatórios", "Preencha nome e preço.");
            return;
        }

        if (category == null) {
            AlertComponent.showWarning("Categoria obrigatória", "Selecione uma categoria.");
            return;
        }

        System.out.println("CATEGORY OBJ: " + category);
        System.out.println("CATEGORY ID: " + (category != null ? category.getId() : "NULL"));

        try {
            double convertPriceToDouble = Double.parseDouble(price.replace(",", "."));

            if (product == null) {

                Product newProduct = new Product();
                newProduct.setName(name);
                newProduct.setPrice(convertPriceToDouble);
                newProduct.setDescription(description);

                newProduct.setCategoryId(category.getId());

                productService.create(newProduct);
                message = "Produto salvo com sucesso!";
            } else {
                product.setName(name);
                product.setPrice(convertPriceToDouble);
                product.setDescription(description);

                product.setCategoryId(category.getId());

                productService.update(product.getId(), product);

                message = "Atualizado com sucesso!";
            }

            AlertComponent.showWarning("Sucesso", message);
            new AdminDashboardView().show(stage);

        } catch (NumberFormatException e) {
            AlertComponent.showWarning("Preço inválido", "Digite um valor válido. Ex: 2,50");

        } catch (Exception e) {
            AlertComponent.showWarning("Erro ao salvar", e.getMessage());
        }

    }
}