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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MembersController implements Initializable {
    private final Database database;

    @FXML
    private TableView<User> membersTableView;
    @FXML
    private Label membersErrorLbl;
    @FXML
    private TextField searchMembertxt;

    private ObservableList<User> users;

    public static final Logger logger = Logger.getLogger(MembersController.class.getName());
    public MembersController(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        users = FXCollections.observableList(database.getUsers());
        membersTableView.setItems(users);
        searchMembertxt.textProperty().addListener((observable, oldValue, newValue) ->
                membersTableView.setItems(filterList(users, newValue)));
    }

    public ObservableList<User> getMembers(){
        return users;
    }


    public void onDeleteMemberBtn(ActionEvent event){
        //delete user from users
        if (!membersTableView.getSelectionModel().isEmpty()) {
            users.remove(membersTableView.getSelectionModel().getSelectedItem());
            membersTableView.refresh(); // update table
            membersErrorLbl.setText("Member Deleted!");
        } else {
            membersErrorLbl.setText("Please pick member to delete");
        }
    }

    public void onEditMemberBtn(ActionEvent event){
        if (!membersTableView.getSelectionModel().isEmpty()){ //checks something is selected
            User selectedMember = membersTableView.getSelectionModel().getSelectedItem();
            MembersDialogController membersDialogController = new MembersDialogController(database, selectedMember);//passes selected member to controller
            Stage dialog = loadScene("MembersDialogView.fxml", membersDialogController);
            membersDialogController.fillInText();
            dialog.showAndWait();
            membersErrorLbl.setText("Member edited!");
            users = membersDialogController.getMembers();
            membersTableView.setItems(users);
        }else{
            membersErrorLbl.setText("Please select Member to edit");
        }

    }
    public void onAddMemberBtn(ActionEvent event){
        //open collection dialog
        MembersDialogController membersDialogController = new MembersDialogController(database);
        loadScene("MembersDialogView.fxml", membersDialogController).showAndWait();
        if (membersDialogController.getMember() != null) {
            users.add(membersDialogController.getMember());
        }
        membersErrorLbl.setText("Member added!");
        users = membersDialogController.getMembers();
        membersTableView.refresh(); //update table
    }

    public Stage loadScene(String name, Object controller){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(name));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Add/Edit Member");
            return dialog;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not open Member dialog");
            return null;
        }
    }
    private ObservableList<User> filterList(List<User> list, String searchText){ //code from https://edencoding.com/search-bar-dynamic-filtering/
        List<User> filteredList = new ArrayList<>();
        for (User user : list){
            if(searchFindsOrder(user, searchText)) filteredList.add(user);
        }
        return FXCollections.observableList(filteredList);
    }
    private boolean searchFindsOrder(User user, String searchText){
        return (user.getFirstname().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getLastname().toLowerCase().contains(searchText.toLowerCase()));
    }
}
