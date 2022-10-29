package com.example.javaendasssignment.controller;

import com.example.javaendasssignment.data.Database;
import com.example.javaendasssignment.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MembersDialogController implements Initializable{
    @FXML
    private TextField usernametxt, firstNametxt, lastNametxt;

    @FXML
    private PasswordField passwordtxt, passwordConfirmationtxt;

    @FXML
    private DatePicker dateOfBirthtxt;
    @FXML
    private Button addMemberbtn;
    @FXML
    Label dialogErrorlbl;
    public static final Logger logger = Logger.getLogger(MembersDialogController.class.getName());

    User newMember;
    Boolean edit;
    private final Database database;
    private ObservableList<User> members;

    public User getMember(){
        return newMember;
    }
    public ObservableList<User> getMembers() {
        return FXCollections.observableList(members);
    }

    public MembersDialogController(Database database) {
        edit = false;
        this.database = database;
    }
    public MembersDialogController(Database database, User user) {
        edit = true;
        newMember = user;
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        members = FXCollections.observableArrayList(database.getUsers());
    }

    public void fillInText(){
        usernametxt.setText(newMember.getUsername());
        firstNametxt.setText(newMember.getFirstname());
        lastNametxt.setText(newMember.getLastname());
        dateOfBirthtxt.setValue(newMember.dateOfBirth);
        addMemberbtn.setText("Edit");
    }

    public void onAddMembersbtnClick(ActionEvent event){
        dialogErrorlbl.setText("");
        dialogErrorlbl.setTextFill(Color.RED);
        if (firstNametxt.getText().equals("") || lastNametxt.getText().equals("") || usernametxt.getText().equals("") || dateOfBirthtxt.getEditor().getText().equals("") || passwordtxt.getText().equals("") || passwordConfirmationtxt.getText().equals("")) {
            dialogErrorlbl.setText("Please fill in all fields!");
        }else if (!(passwordtxt.getText().equals(passwordConfirmationtxt.getText()))) {
            dialogErrorlbl.setText("Password's dont match");
        } else if(getDate(dateOfBirthtxt) == null){
            dialogErrorlbl.setText("Date is not Valid!");
        }else if(edit){ //edits member
            newMember.setFirstname(firstNametxt.getText());
            newMember.setLastname(lastNametxt.getText());
            newMember.setUsername(usernametxt.getText());
            newMember.setPassword(passwordConfirmationtxt.getText());
            newMember.setDateOfBirth(getDate(dateOfBirthtxt));
            for (User member : members){
                if (member.getIdentifier().equals(newMember.getIdentifier())){
                    member = newMember;
                }
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        } else { //new member
            newMember = new User(usernametxt.getText(), passwordConfirmationtxt.getText(), getNewID(), firstNametxt.getText(), lastNametxt.getText(), getDate(dateOfBirthtxt));
            members.add(newMember);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    public void onCancelbtnClick(ActionEvent event){
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();

    }
    public int getNewID(){
        return (members.get(members.size() -1).getIdentifier() + 1);
    }

    public LocalDate getDate(DatePicker datePicker){ //parsing string to localdate
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(datePicker.getEditor().getText(), formatter);
        } catch (Exception e){
            dialogErrorlbl.setText("Wrong input of Date");
            logger.log(Level.WARNING, "Wrong input of Date");
            return datePicker.getValue();
        }
    }


}
