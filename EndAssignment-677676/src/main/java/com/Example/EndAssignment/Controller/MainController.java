package com.Example.EndAssignment.Controller;

import com.Example.EndAssignment.Start;
import com.Example.EndAssignment.Data.Database;
import com.Example.EndAssignment.Model.Item;
import com.Example.EndAssignment.Model.Member;
import com.Example.EndAssignment.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    Database database;
    User user;
    private ObservableList<Member> members;
    private ObservableList<Item> items;
    @FXML
    Label lblWelcome;
    @FXML
    Label lblInformation;
    @FXML
    Label lblInfoReceivingItem;
    @FXML
    TableView<Item> itemTableView;
    @FXML
    TableView<Member> memberTableView;
    @FXML
    Button btnAddItem;
    @FXML
    Button btnEditItem;
    @FXML
    Button btnDeleteItem;
    @FXML
    Button btnLendItem;
    @FXML
    Button btnReceiveItem;
    @FXML
    Button btnPayFine;
    @FXML
    Label lblLateFine;
    @FXML
    TextField txtItemCodeLending;
    @FXML
    TextField txtMemberIdLending;
    @FXML
    TextField txtItemCodeReceiving;

    public MainController(Database database, User user) {
        this.database = database;
        this.user = user;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        members = FXCollections.observableList(database.getMembers());
        items = FXCollections.observableList(database.getItems());
        itemTableView.setItems(items);
        memberTableView.setItems(members);
        btnPayFine.setVisible(false);
     }

    public void showUserName(User user) {
        this.user = user;
        lblWelcome.setText("Welcome " + user.getName());
    }

    @FXML
    public void onLendItemClick() {
        //check that the member ID inserted is valid before lending
        Member currentMember = null;
        for (Member m : members
        ) {
            if (Integer.parseInt(txtMemberIdLending.getText()) == m.getId()) {
                //member is valid and can continue
                currentMember = m;
            }
            if (currentMember == null) {
                //member does not exist and must be created before lending
                lblInformation.setText("Member does not exist");
            }

            for (Item item : items
            ) {
                if ((Integer.parseInt(txtItemCodeLending.getText()) == item.getItemCode()) && (item.getAvailability().equals("Yes"))) {
                    //Item changed to not available and today's date added to the item
                    lblInformation.setText("Item lent successfully");
                    item.setAvailability("No");
                    item.setLendingDate(LocalDate.now());
                    //once the item is lent a expectedReturnDate is set
                    item.setExpectedReturnDate(item.getLendingDate());
                }
            }
            itemTableView.refresh();
            txtItemCodeLending.clear();
            txtMemberIdLending.clear();
        }
    }

    @FXML
    private void onReceiveItemClick() throws IOException {
        //item is received and back to available
        int itemCode = Integer.parseInt(txtItemCodeReceiving.getText());
        for (Item item : items
        ) {
            if ((item.getItemCode() == itemCode) && (item.getAvailability().equals("No"))) {
                if (isItemLate(item))
                {
                    btnReceiveItem.setDisable(true);
                    btnPayFine.setVisible(true);
                    item.setLendingDate(null);
                }
                else {
                    lblInfoReceivingItem.setText("Item has been received successfully");
                    //item now available and lendingDate cleared
                    item.setAvailability("Yes");
                    item.setLendingDate(null);
                    itemTableView.refresh();
                }
            } else {
                lblInfoReceivingItem.setText("Item is currently not lent or not existing");
            }
            database.serializeItems();
            txtItemCodeReceiving.clear();
        }
    }

    @FXML
    private void onPayFineClick() {
        btnReceiveItem.setDisable(false);
lblLateFine.setText("");
lblInfoReceivingItem.setText("");
txtItemCodeReceiving.clear();
    }
@FXML
private void onKeyTypedItemCodeReceiving()
{
    btnReceiveItem.setDisable(false);
    lblLateFine.setText("");
    btnPayFine.setVisible(false);
}
    private void calculateFine(Period timeBetweenLendingAndReceiving) {
        double fine = timeBetweenLendingAndReceiving.getDays() * 0.10;
        displayFine(fine);
    }

    private void displayFine(double fine) {
        lblLateFine.setText("Fine to pay: € " + fine);
    }

    private boolean isItemLate(Item item) {
        Period timeBetweenLendingAndReceiving = Period.between(item.getLendingDate(), LocalDate.now());
        if (timeBetweenLendingAndReceiving.getDays() > 21) {
            //Show the late item and the fine
            displayLateItemMessage(timeBetweenLendingAndReceiving);
            return true;
        }
        return false;
    }

    private void displayLateItemMessage(Period timeBetweenLendingAndReceiving) {
        lblInfoReceivingItem.setText("The item is " + (timeBetweenLendingAndReceiving.getDays() - 21) + "days late");
        calculateFine(timeBetweenLendingAndReceiving);
    }

    @FXML
    private void onClickImportItems() throws IOException {
        database.deserializeItems();
        itemTableView.refresh();
    }

    @FXML
    public void onClickAddItem(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("AddItem.fxml"));
            AddItemController addItemController = new AddItemController(database);
            fxmlLoader.setController(addItemController);
            Scene scene = new Scene(fxmlLoader.load());

            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Add Item");
            dialog.showAndWait();

            if (addItemController.getItem() != null) {
                Item i = (addItemController.getItem());
                //get last item from the list:
                Item last = items.get(items.size() - 1);
                i.setItemCode(last.getItemCode() + 1);
                items.add(i);
            }
            database.serializeItems();

        } catch (IOException e) {
            throw new IOException(e);
        }
    }


    @FXML
    public void onClickEditItem(ActionEvent event) throws IOException {
        try {
            Item itemToEdit = itemTableView.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("AddItem.fxml"));
            AddItemController addItemController = new AddItemController(database, itemToEdit);
            fxmlLoader.setController(addItemController);
            Scene scene = new Scene(fxmlLoader.load());

            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Edit Item");
            addItemController.setText();
            dialog.showAndWait();

            if (addItemController.getItem() != null) {
                Item i = (addItemController.getItem());
                i.setItemCode(itemToEdit.getItemCode());
                database.getItems().set(database.getItems().indexOf(itemToEdit), i);
            }

            itemTableView.refresh();
            database.serializeItems();

        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @FXML
    private void onClickDeleteItem(ActionEvent event) throws IOException {
        //select item from tableview and remove it from the list
        Item i = itemTableView.getSelectionModel().getSelectedItem();
        items.remove(i);
        database.serializeItems();
    }

    @FXML
    public void onClickAddMember(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("AddMember.fxml"));
            AddMemberController addMemberController = new AddMemberController(database);
            fxmlLoader.setController(addMemberController);
            Scene scene = new Scene(fxmlLoader.load());

            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Add Member");
            dialog.showAndWait();

            if (addMemberController.getMember() != null) {
                Member m = (addMemberController.getMember());
                //get last item from the list:
                Member last = members.get(members.size() - 1);
                m.setId(last.getId() + 1);
                members.add(m);
            }

        } catch (IOException e) {
            throw new IOException(e);
        }
        database.serializeMembers();
    }

    @FXML
    public void onClickEditMember(ActionEvent event) throws IOException {
        try {
            Member memberToEdit = memberTableView.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("AddMember.fxml"));
            AddMemberController addMemberController = new AddMemberController(database, memberToEdit);
            fxmlLoader.setController(addMemberController);
            Scene scene = new Scene(fxmlLoader.load());

            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Edit Member");
            addMemberController.setText();
            dialog.showAndWait();

            if (addMemberController.getMember() != null) {
                Member m = (addMemberController.getMember());
                m.setId(memberToEdit.getId());
                database.getMembers().set(database.getMembers().indexOf(memberToEdit), m);
            }
            memberTableView.refresh();

        } catch (IOException e) {
            throw new IOException(e);
        }
        database.serializeMembers();
    }

    @FXML
    private void onClickDeleteMember(ActionEvent event) throws IOException {
        //select member from tableview and remove it from the list
        Member m = memberTableView.getSelectionModel().getSelectedItem();
        members.remove(m);
        database.serializeMembers();
    }
}