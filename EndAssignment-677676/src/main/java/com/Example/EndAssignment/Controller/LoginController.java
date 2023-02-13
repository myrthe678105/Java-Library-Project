package com.Example.EndAssignment.Controller;

import com.Example.EndAssignment.Start;
import com.Example.EndAssignment.Data.Database;
import com.Example.EndAssignment.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label errorMessageLabel;

    @FXML
    private Button LogInBtn;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private ArrayList<User> users;
    Database database;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            database = new Database();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        users = (ArrayList<User>) database.getUsers();

    }

    @FXML
    protected void onLoginClick(ActionEvent event) throws IOException {

        for (User user : users) {

            if (usernameField.getText().equals(user.getUsername()) && passwordField.getText().equals(user.getPassword())) {
                Stage stage = (Stage) LogInBtn.getScene().getWindow();
                stage.close();
                try {
                    //parse the user to the next window and parse the database
                    FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("MainPage.fxml"));
                    MainController mainController = new MainController(database, user);
                    fxmlLoader.setController(mainController);
                    Scene scene = new Scene(fxmlLoader.load());

                    stage.setTitle("Main");
                    stage.setScene(scene);
                    mainController.showUserName(user);
                    stage.show();
                } catch (IOException e) {
                    System.out.println(String.format("Error: %s", e.getMessage()));
                }
            } else {
                errorMessageLabel.setText("Incorrect username or password");
            }
        }
    }
}