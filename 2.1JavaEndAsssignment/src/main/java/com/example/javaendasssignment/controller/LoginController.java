package com.example.javaendasssignment.controller;

import com.example.javaendasssignment.Main;
import com.example.javaendasssignment.data.Database;
import com.example.javaendasssignment.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



public class LoginController implements Initializable {

    @FXML
    private PasswordField passwordtxt;
    @FXML
    private TextField usernametxt;
    @FXML
    private Label loginErrorlbl;
    private ObservableList<User> users;
    private final Database database;



    public static final Logger logger = Logger.getLogger(LoginController.class.getName());

    public LoginController(Database database) {
    this.database = database;
    }

     private User matchUser(String username, String password){ // goes through list of users and check if username exists
         for (User user : users){
             if (username.equals(user.getUsername()) && password.equals(user.getPassword())){
                 return user;
             }

         }
        return null;
    }


    @FXML
    public void onLoginBtnClick(ActionEvent event) throws IOException{
        Stage thisStage = (Stage) usernametxt.getScene().getWindow();
        User loggingInUser = matchUser(usernametxt.getText(), passwordtxt.getText());
        if (usernametxt.getText().isEmpty() || passwordtxt.getText().isEmpty()){
            loginErrorlbl.setText("Please enter your username and password");
        } else if (loggingInUser != null) {
            try{
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Mainview.fxml"));
                MainController mainController = new MainController(database);
                fxmlLoader.setController(mainController);
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Library");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                thisStage.close(); //closes login
                mainController.initData(loggingInUser); //sends user to mainController
            } catch (IOException e){
                logger.log(Level.SEVERE, "Could not load form");
            }

        } else {
            loginErrorlbl.setText("Wrong username or password");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users = FXCollections.observableArrayList(database.getUsers());
    }
}