package com.Example.EndAssignment.Controller;

import com.Example.EndAssignment.Data.Database;
import com.Example.EndAssignment.Model.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class AddMemberController {
    Database database;
    Member member;
    @FXML
    private TextField txtAddMemberFirstName;
    @FXML
    private TextField txtAddMemberLastName;
    @FXML
    Label lblErrorMessage;
    @FXML
    DatePicker dateTimePickerMemberDOB;
    @FXML
    Button btnAddMemberConfirmation;

    public AddMemberController(Database database) {
        this.database = database;
    }

    public AddMemberController(Database database, Member member) {
        this.database = database;
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public void setText() {
        btnAddMemberConfirmation.setText("Edit Member");
        txtAddMemberFirstName.setText(member.getFirstName());
        txtAddMemberLastName.setText(member.getLastName());
        dateTimePickerMemberDOB.setValue(member.getBirthday());
    }

    @FXML
    public void onClickCreateNewMember(ActionEvent actionEvent) {
        if ((txtAddMemberFirstName.getText().equals("")) || (txtAddMemberLastName.getText().equals("")) || (dateTimePickerMemberDOB.getEditor().getText().equals(""))) {
            lblErrorMessage.setText("Complete all fields before continuing");
        } else {
            //convert the user's input typed in to a date
            LocalDate dateOfBirth = convertDate(dateTimePickerMemberDOB.getEditor().getText());
            member = new Member(txtAddMemberFirstName.getText(), txtAddMemberLastName.getText(), dateOfBirth);
        }
        //TODO error lable if date is invalid
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onClickCancel(ActionEvent actionEvent) {
        member = null;
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    private LocalDate convertDate(String input){
            DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("ddMMyyyy");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d/M/yy");
            DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("d-M-yy");
            DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                try { return LocalDate.parse(input, formatter1);} catch (DateTimeParseException ignored) {}
                try { return LocalDate.parse(input, formatter2);} catch (DateTimeParseException ignored) {}
                try { return LocalDate.parse(input, formatter3);} catch (DateTimeParseException ignored) {}
                try { return LocalDate.parse(input, formatter4);} catch (DateTimeParseException ignored) {}

                return LocalDate.parse(input, defaultFormatter);
            }

    }

