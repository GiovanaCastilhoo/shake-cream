package com.shakecream.app;

import com.shakecream.app.views.client.CategorySelectionView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ShakeCream");

        Scene scene = new Scene(new Pane(), 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Abre o app direto na tela de categorias
        new CategorySelectionView().show(primaryStage);
    }
}