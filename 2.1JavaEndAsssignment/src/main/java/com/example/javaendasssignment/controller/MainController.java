package com.example.javaendasssignment.controller;

import com.example.javaendasssignment.Main;
import com.example.javaendasssignment.data.Database;
import com.example.javaendasssignment.model.User;
import javafx.css.Style;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {
    @FXML
    VBox layout;
    private final Database database;

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView icon;
    @FXML
    private Button lendReceivebtn, collectionbtn, membersbtn;

    public static final Logger logger = Logger.getLogger(MainController.class.getName());

    public MainController(Database database) {
        this.database = database;
    }

    public void loadScene(String name, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(name));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load(), 500, 500);
            if (layout.getChildren().size() > 1)
                layout.getChildren().remove(1);
            layout.getChildren().add(scene.getRoot());//from the 'University project'
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not load initial scenes");
            throw new RuntimeException();
        }
    }

    public void onLendReceiveBtnClick(ActionEvent event) {
        loadScene("LendReceiveView.fxml", new LendReceiveController(database));
        collectionbtn.setStyle("-fx-background-color:   #465881; -fx-padding: 10px; -fx-text-fill: White");
        membersbtn.setStyle("-fx-background-color:   #465881; -fx-padding: 10px; -fx-text-fill: White");
        lendReceivebtn.setStyle("-fx-background-color:  #C9D1D3; -fx-padding: 10px; -fx-text-fill: black");

    }
    public void onMembersBtnClick(ActionEvent event) {
        loadScene("MembersView.fxml", new MembersController(database));
        collectionbtn.setStyle("-fx-background-color:   #465881; -fx-padding: 10px; -fx-text-fill: White");
        membersbtn.setStyle("-fx-background-color:  #C9D1D3; -fx-padding: 10px; -fx-text-fill: black");
        lendReceivebtn.setStyle("-fx-background-color:   #465881; -fx-padding: 10px; -fx-text-fill: White");

    }
    public void onCollectionBtnClick(ActionEvent event) {
        loadScene("CollectionView.fxml", new CollectionController(database));
        collectionbtn.setStyle("-fx-background-color:  #C9D1D3; -fx-padding: 10px; -fx-text-fill: black");
        membersbtn.setStyle("-fx-background-color:   #465881; -fx-padding: 10px; -fx-text-fill: White");
        lendReceivebtn.setStyle("-fx-background-color:   #465881; -fx-padding: 10px; -fx-text-fill: White");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadScene("LendReceiveView.fxml", new LendReceiveController(database));
        lendReceivebtn.setStyle("-fx-background-color:  #C9D1D3; -fx-padding: 10px; -fx-text-fill: black");
    }

    public void initData(User user){
        nameLabel.setText("Welcome " + user.getFirstname() + " "+ user.getLastname());
    }

}
