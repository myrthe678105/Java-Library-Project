package com.Example.EndAssignment.Controller;

import com.Example.EndAssignment.Data.Database;
import com.Example.EndAssignment.Model.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AddItemController {
    Database database;
    Item item;

    public Item getItem() {
        return item;
    }

    @FXML
    private TextField txtAddItemTitle;
    @FXML
    private TextField txtAddItemAuthor;
    @FXML
    Label lblErrorMessage;
    @FXML
    Button btnAddItemConfirmation;
    public AddItemController(Database database) {
        this.database = database;
    }

    public AddItemController(Database database, Item item){
        //this is the ctor for the edit item
        this.database= database;
        this.item= item;
    }
    public void setText()
    {
        btnAddItemConfirmation.setText("Edit Item");
        txtAddItemTitle.setText(item.getTitle());
        txtAddItemAuthor.setText(item.getAuthor());
    }

    @FXML
    public void onClickCreateNewItem(ActionEvent actionEvent) {
        if ((txtAddItemAuthor.getText().equals("")) || txtAddItemTitle.getText().equals("")) {
            lblErrorMessage.setText("Enter both title and Author");
        } else {
                item = new Item(txtAddItemTitle.getText(), txtAddItemAuthor.getText());

            }
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.close();
    }

    @FXML
    public void onClickCancel(ActionEvent actionEvent) {
        item = null;
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


}