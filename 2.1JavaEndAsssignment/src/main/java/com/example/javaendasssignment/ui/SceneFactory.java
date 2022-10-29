package com.example.javaendasssignment.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import com.example.javaendasssignment.Main;

import java.io.IOException;

public class SceneFactory {

    private SceneFactory() {
    }

        public static Scene initScene(String filename, Object controller) throws IOException{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(filename));
            fxmlLoader.setController(controller);
            return new Scene(fxmlLoader.load());
        }
    }

