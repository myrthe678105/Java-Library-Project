package com.example.javaendasssignment;

import com.example.javaendasssignment.controller.LoginController;
import com.example.javaendasssignment.data.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {
    Database database;

    @Override
    public void start(Stage stage) throws IOException {
        database = new Database();
        database.DeserializeCollectionFile();
        database.DeserializeMembersFile();
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login-View.fxml"));
        LoginController loginController = new LoginController(database);
        fxmlLoader.setController(loginController);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws FileNotFoundException {
        database.SerializeCollectionFile();
        database.SerializeMembersFile();
    }

}