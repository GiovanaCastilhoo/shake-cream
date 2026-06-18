package com.shakecream.app.views.admin;

import com.shakecream.app.HelloApplication;
import com.shakecream.app.models.LoginResponse;
import com.shakecream.app.models.User;
import com.shakecream.app.services.AuthService;
import com.shakecream.app.utils.SessionStore;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminLoginView {

    private final String COLOR_PRIMARY = "#B95C68";
    private final String COLOR_INPUT_BG = "#F0F0F0"; // ✨ ALTERAÇÃO: Tom mais claro/esbranquiçado para os inputs
    private final String COLOR_TEXT_DARK = "#4A3B37";
    private final String COLOR_CARD_BG = "rgba(238, 233, 227, 0.92)";

    public void show(Stage stage) {
        StackPane root = new StackPane();

        // Configuração do plano de fundo idêntico à tela inicial
        try {
            Image imgFundo = new Image(getClass().getResourceAsStream("/com/shakecream/app/fundo.png"));
            BackgroundImage bImg = new BackgroundImage(
                    imgFundo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
            root.setBackground(new Background(bImg));
        } catch (Exception e) {
            root.setStyle("-fx-background-color: #2D1B18;");
        }

        // Card Central de Login Administrativo
        VBox card = new VBox(20);
        card.setMaxSize(420, 520);
        card.setPadding(new Insets(40, 45, 40, 45));
        card.setAlignment(Pos.CENTER);
        card.setStyle(
                "-fx-background-color: " + COLOR_CARD_BG + ";" +
                        "-fx-background-radius: 40;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 30, 0, 0, 15);");

        Label lbTitulo = new Label("Login");
        lbTitulo.setStyle("-fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 36; -fx-text-fill: "
                + COLOR_TEXT_DARK + ";");

        // Campos de entrada customizados com cast correto
        TextField txtUsuario = (TextField) createStyledField("Usuário", false);
        PasswordField txtSenha = (PasswordField) createStyledField("Senha", true);

        // Botão Entrar
        Button btnEntrar = new Button("Entrar");
        btnEntrar.setMaxWidth(Double.MAX_VALUE);
        btnEntrar.setPrefHeight(50);
        btnEntrar.setStyle("-fx-background-color: " + COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 16; -fx-background-radius: 12; -fx-cursor: hand;");
        btnEntrar.setOnAction(e -> AuthenticationAdmin(stage, txtUsuario.getText(), txtSenha.getText()));

        Label lbOu = new Label("ou");
        lbOu.setStyle("-fx-font-family: 'Montserrat'; -fx-text-fill: #9E9993; -fx-font-size: 13;");

        // Botão para retornar ao Totem do cliente
        Button btnVoltarTotem = new Button("Voltar para o acesso de atendente");
        btnVoltarTotem.setMaxWidth(Double.MAX_VALUE);
        btnVoltarTotem.setPrefHeight(45);
        btnVoltarTotem.setStyle(
                "-fx-background-color: transparent; -fx-border-color: #A09A94; -fx-border-radius: 12; -fx-text-fill: #7D7771; -fx-font-family: 'Montserrat'; -fx-font-size: 13; -fx-cursor: hand;");
        btnVoltarTotem.setOnAction(e -> new HelloApplication().show(stage));

        card.getChildren().addAll(lbTitulo, txtUsuario, txtSenha, btnEntrar, lbOu, btnVoltarTotem);
        root.getChildren().add(card);

        stage.getScene().setRoot(root);
    }

    private Control createStyledField(String prompt, boolean isPassword) {
        Control field = isPassword ? new PasswordField() : new TextField();
        if (isPassword) {
            ((PasswordField) field).setPromptText(prompt);
        } else {
            ((TextField) field).setPromptText(prompt);
        }
        field.setPrefHeight(50);
        field.setStyle(
                "-fx-background-color: " + COLOR_INPUT_BG + ";" +
                        "-fx-background-radius: 12;" +
                        "-fx-prompt-text-fill: #8E8A85;" +
                        "-fx-text-fill: " + COLOR_TEXT_DARK + ";" +
                        "-fx-padding: 0 15 0 15;" +
                        "-fx-font-family: 'Montserrat';" +
                        "-fx-font-size: 14;");
        return field;
    }

    private void AuthenticationAdmin(Stage stage, String usuario, String senha) {

        if (usuario.isEmpty() || senha.isEmpty()) {
            System.out.println("Erro: Credenciais vazias.");
            return;
        }

        AuthService authService = new AuthService();
        LoginResponse response = authService.login(usuario, senha);

        // ✅ primeiro valida
        if (response == null) {
            System.out.println("Login inválido.");
            return;
        }

        // depois usa
        User user = response.getUser();

        if (user == null) {
            System.out.println("User veio null do backend");
            return;
        }

        String token = response.getToken();

        System.out.println("TOKEN: " + token);
        System.out.println("ROLE DO USER: [" + user.getRole() + "]");

        if (!"ADMIN".equals(user.getRole())) {
            System.out.println("Acesso negado: não é admin.");
            return;
        }

        SessionStore.setToken(token);
        SessionStore.setRole(user.getRole());
        SessionStore.setUserId(user.getId());

        new AdminDashboardView().show(stage);
    }
}