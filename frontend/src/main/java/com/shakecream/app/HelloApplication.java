package com.shakecream.app;

import com.shakecream.app.services.ClientService;
import com.shakecream.app.utils.SessionStore;
import com.shakecream.app.views.admin.AdminLoginView;
import com.shakecream.app.views.client.CategorySelectionView;
import com.shakecream.app.models.ClientLoginResponse;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private final ClientService clientService = new ClientService();
    // Definição de cores baseadas no protótipo
    private final String COLOR_PRIMARY = "#B95C68";
    // ✨ ALTERAÇÃO AQUI: Mudado de #E1DDD8 para #F0F0F0 para deixar os inputs
    // esbranquiçados e limpos
    private final String COLOR_INPUT_BG = "#F0F0F0";
    private final String COLOR_TEXT_DARK = "#4A3B37";
    private final String COLOR_CARD_BG = "rgba(238, 233, 227, 0.92)";

    @Override
    public void start(Stage stage) {
        // O método start apenas delega a inicialização para o método show
        show(stage);
    }

    public void show(Stage stage) {
        StackPane root = new StackPane();

        // Configuração do Fundo (Mantém proporção e preenche a tela)
        try {
            Image imgFundo = new Image(getClass().getResourceAsStream("/com/shakecream/app/fundo.png"));
            BackgroundImage bImg = new BackgroundImage(
                    imgFundo,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
            root.setBackground(new Background(bImg));
        } catch (Exception e) {
            root.setStyle("-fx-background-color: #2D1B18;");
        }

        // Container Central (Card)
        VBox card = new VBox(15);
        card.setMaxSize(420, 620);
        card.setPadding(new Insets(30, 45, 30, 45));
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle(
                "-fx-background-color: " + COLOR_CARD_BG + ";" +
                        "-fx-background-radius: 40;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 30, 0, 0, 15);");

        // Logo e Título
        VBox logoContainer = new VBox(-5);
        logoContainer.setAlignment(Pos.CENTER);
        try {
            Image imgLogo = new Image(getClass().getResourceAsStream("/com/shakecream/app/logo.png"));
            ImageView logoView = new ImageView(imgLogo);
            logoView.setFitHeight(160);
            logoView.setPreserveRatio(true);
            logoContainer.getChildren().add(logoView);
        } catch (Exception e) {
            Label lbLogo = new Label("SHAKE & CREAM");
            lbLogo.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
            logoContainer.getChildren().add(lbLogo);
        }

        Label lbInstrucao = new Label("Para começar, identifique-se");
        lbInstrucao.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: 500; -fx-font-size: 22; -fx-text-fill: "
                + COLOR_TEXT_DARK + ";");
        VBox.setMargin(lbInstrucao, new Insets(10, 0, 10, 0));

        // Campos de Entrada
        TextField txtNome = createStyledTextField("Seu nome", "👤");
        TextField txtMesa = createStyledTextField("Número da mesa", "🪑");

        // Botão Continuar
        Button btnContinuar = new Button("Continuar");
        btnContinuar.setMaxWidth(Double.MAX_VALUE);
        btnContinuar.setPrefHeight(50);
        btnContinuar.setStyle(
                "-fx-background-color: " + COLOR_PRIMARY + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;");
        btnContinuar.setOnAction(e -> handleContinuar(txtNome.getText(), txtMesa.getText(), stage));

        // Separador "ou"
        HBox separator = createSeparator();

        // Botão Administrador
        Button btnAdmin = new Button("Acessar como administrador");
        btnAdmin.setMaxWidth(Double.MAX_VALUE);
        btnAdmin.setPrefHeight(45);
        btnAdmin.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: #A09A94;" +
                        "-fx-border-radius: 12;" +
                        "-fx-text-fill: #7D7771;" +
                        "-fx-font-size: 13;" +
                        "-fx-cursor: hand;");
        btnAdmin.setOnAction(e -> handleAdminLogin(stage));

        // Rodapé (Frase com coração)
        HBox footer = new HBox(5);
        footer.setAlignment(Pos.CENTER);
        Text txtFooter = new Text("Escolha, monte e aproveite seu milk shake!");
        txtFooter.setStyle("-fx-font-size: 13; -fx-fill: " + COLOR_TEXT_DARK + "; -fx-font-style: italic;");
        Label heartIcon = new Label("♡");
        heartIcon.setStyle("-fx-text-fill: " + COLOR_TEXT_DARK + ";");
        footer.getChildren().addAll(heartIcon, txtFooter);
        VBox.setMargin(footer, new Insets(20, 0, 0, 0));

        // Montagem do layout
        card.getChildren().addAll(
                logoContainer,
                lbInstrucao,
                txtNome,
                txtMesa,
                btnContinuar,
                separator,
                btnAdmin,
                footer);

        root.getChildren().add(card);

        if (stage.getScene() == null) {
            Scene scene = new Scene(root, 1100, 750);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(root);
        }

        stage.setTitle("Shake & Cream - Cardápio Eletrônico");
        stage.setMaximized(true);
        stage.show();
    }

    private TextField createStyledTextField(String prompt, String icon) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefHeight(50);
        tf.setStyle(
                "-fx-background-color: " + COLOR_INPUT_BG + ";" +
                        "-fx-background-radius: 12;" +
                        "-fx-prompt-text-fill: #8E8A85;" +
                        "-fx-text-fill: " + COLOR_TEXT_DARK + ";" +
                        "-fx-padding: 0 15 0 15;" +
                        "-fx-font-size: 14;");
        return tf;
    }

    private HBox createSeparator() {
        HBox container = new HBox(10);
        container.setAlignment(Pos.CENTER);
        Region line1 = new Region();
        Region line2 = new Region();
        HBox.setHgrow(line1, Priority.ALWAYS);
        HBox.setHgrow(line2, Priority.ALWAYS);

        Label lblOu = new Label("ou");
        lblOu.setStyle("-fx-text-fill: #9E9993; -fx-font-size: 12;");

        container.getChildren().addAll(line1, lblOu, line2);
        container.setPadding(new Insets(5, 0, 5, 0));
        return container;
    }

    // --- MÉTODOS DE LÓGICA ---

    private void handleContinuar(String nome, String mesa, Stage stage) {

        if (nome.isEmpty() || mesa.isEmpty()) {
            System.out.println("Erro: Campos vazios.");
            return;
        }

        int tableNumber;

        try {
            tableNumber = Integer.parseInt(mesa);
        } catch (NumberFormatException e) {
            System.out.println("Mesa inválida.");
            return;
        }

        ClientLoginResponse clientLoginResponse = clientService.loginClient(nome, tableNumber);

        if (clientLoginResponse == null) {
            System.out.println("Erro ao criar sessão do cliente.");
            return;
        }

        // SALVA TOKEN (sessionId no client)
        SessionStore.setSessionId(clientLoginResponse.getSessionId());

        // CLIENT NÃO TEM USER → define fixo
        SessionStore.setRole("USER");
        SessionStore.setUserId(null);

        // opcional: guardar mesa
        SessionStore.setTableNumber(tableNumber);

        System.out.println("Cliente logado: " + nome);

        new CategorySelectionView().show(stage);
    }

    private void handleAdminLogin(Stage stage) {
        System.out.println("Navegando para área administrativa...");
        new AdminLoginView().show(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}