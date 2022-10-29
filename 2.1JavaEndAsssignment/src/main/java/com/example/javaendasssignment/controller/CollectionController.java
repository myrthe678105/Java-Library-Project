package com.example.javaendasssignment.controller;

import com.example.javaendasssignment.Main;
import com.example.javaendasssignment.data.Database;
import com.example.javaendasssignment.model.Book;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CollectionController implements Initializable {
    private final Database database;

    @FXML
    TableView<Book> booksTableView;
    @FXML
    Label collectionViewErrorlbl;
    @FXML
    private TextField searchCollectiontxt;
    private ObservableList<Book> books;

    public static final Logger logger = Logger.getLogger(CollectionController.class.getName());
    public CollectionController(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        books = FXCollections.observableList(database.getBooks());
        booksTableView.setItems(books);
        searchCollectiontxt.textProperty().addListener((observable, oldValue, newValue) ->
                booksTableView.setItems(filterList(books, newValue)));
    }

    public ObservableList<Book> getCollection(){
        return books;
    }

    public void onDeleteItemBtn(ActionEvent event){
        //delete book from books
        if (!booksTableView.getSelectionModel().isEmpty()) { //checks something is selected
            books.remove(booksTableView.getSelectionModel().getSelectedItem());
            booksTableView.refresh(); // update table
            collectionViewErrorlbl.setText("Book deleted!");
        } else{
            collectionViewErrorlbl.setText("Please pick Item to delete");
        }

    }
    public void onEditItemBtn(ActionEvent event){
        //open collection dialog
        if (!booksTableView.getSelectionModel().isEmpty()){ //checks something is selected
            Book selectedBook = booksTableView.getSelectionModel().getSelectedItem();
            CollectionDialogController collectionDialogController = new CollectionDialogController(database, selectedBook);//passes selected book to controller
            Stage dialog = loadScene("CollectionDialogView.fxml", collectionDialogController);
            collectionDialogController.fillInText();
            dialog.showAndWait();
            books = collectionDialogController.getCollection();
            booksTableView.refresh();
        }else{
            collectionViewErrorlbl.setText("Please select Book to edit");
        }
    }
    public void onAddItemBtn(ActionEvent event) {
        //open collection dialog
        CollectionDialogController collectionDialogController = new CollectionDialogController(database);
        loadScene("CollectionDialogView.fxml", collectionDialogController).showAndWait();
        if (collectionDialogController.getBook() != null){
            books.add(collectionDialogController.getBook());
        }
        books = collectionDialogController.getCollection();
        booksTableView.refresh(); //update table

    }

    public Stage loadScene(String name, Object controller){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(name));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Add/Edit Item");
            return dialog;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not open collection dialog");
        }
        return null;
    }

    private ObservableList<Book> filterList(List<Book> list, String searchText){ //code from https://edencoding.com/search-bar-dynamic-filtering/
        List<Book> filteredList = new ArrayList<>();
        for (Book book : list){
            if(searchFindsOrder(book, searchText)) filteredList.add(book);
        }
        return FXCollections.observableList(filteredList);
    }
    private boolean searchFindsOrder(Book book, String searchText){
        return (book.getAuthor().toLowerCase().contains(searchText.toLowerCase())) ||
                (book.getTitle().toLowerCase().contains(searchText.toLowerCase()));
    }

}
