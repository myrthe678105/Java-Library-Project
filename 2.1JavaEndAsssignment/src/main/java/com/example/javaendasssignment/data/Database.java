package com.example.javaendasssignment.data;

import com.example.javaendasssignment.controller.CollectionController;
import com.example.javaendasssignment.model.Book;
import com.example.javaendasssignment.model.Status;
import com.example.javaendasssignment.model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();

    public static final Logger logger = Logger.getLogger(Database.class.getName());
    public List<Book> getBooks() {
        return books;
    }
    public List<User> getUsers() {return users;}


    public Database(){

    }
    //FILE IO FOR BOOKS
    public void SerializeCollectionFile() throws FileNotFoundException {
        try (FileOutputStream FIS = new FileOutputStream((new File("Books.dat")));
            ObjectOutputStream OIS = new ObjectOutputStream(FIS)) {
            for (Book book : books){
                OIS.writeObject(book);
            }
        } catch (IOException e){
            logger.log(Level.WARNING, "Something went wrong with serialisation");
        }
    }
    public void DeserializeCollectionFile() throws FileNotFoundException{
        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream("Books.dat"))) {
            while (true){
                try{
                    Book book = (Book)OIS.readObject();
                    books.add(book);
                } catch (EOFException endOfFile){
                    break;
                } catch (ClassNotFoundException e){
                    logger.log(Level.WARNING, "Class not found - deserialize");
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e){
            logger.log(Level.SEVERE, "Using backup Database");
            //books
            Book book1 = new Book(1, Status.yes, "Pride and Prejudice", "Jane Austen");
            books.add(book1);
            Book book2 = new Book(2, Status.yes, "The Red and the Black", "Stendhal");
            books.add(book2);
            Book book3 = new Book(3, Status.yes, "The History of Tom Jones, a Foundling", "HenryFielding");
            books.add(book3);
            Book book4 = new Book(4, Status.yes, "David Copperfield", "Charles Dickens");
            books.add(book4);
            Book book5 = new Book(5, Status.yes, "Moby-Dick", "Herman Melville");
            books.add(book5);
            Book book6 = new Book(6, Status.yes, "Wuthering Heights", "Emily Bronte");
            books.add(book6);
            Book book7 = new Book(7, Status.yes, "The Brothers Karamazov", "Dostoevsky");
            books.add(book7);
            Book book8 = new Book(8, Status.yes, "War and Peace", "Tolstoy");
            books.add(book8);
            Book book9 = new Book(9, Status.yes, "Harry potter and the Deathly Hallows", "J.K Rowling");
            books.add(book9);
            Book book10 = new Book(10, Status.yes, "The Hunger Games", "Suzanne Collins");
            books.add(book10);
        }
    }

    //FILE IO FOR MEMBERS
    public void SerializeMembersFile() throws FileNotFoundException {
        try (FileOutputStream FIS = new FileOutputStream(new File("Members.dat"));
             ObjectOutputStream OIS = new ObjectOutputStream(FIS)) {
            for (User user : users){
                OIS.writeObject(user);
            }
        } catch (IOException e){
            logger.log(Level.WARNING, "Something went wrong with serialisation");
        }
    }
    public void DeserializeMembersFile() throws FileNotFoundException{
        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream("Members.dat"))) {
            while (true){
                try{
                    User user = (User)OIS.readObject();
                    users.add(user);
                } catch (EOFException endOfFile){
                    break;
                } catch (ClassNotFoundException e){
                    logger.log(Level.WARNING, "Class not found - deserialize");
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e){
            logger.log(Level.SEVERE, "Using backup Database");
            //members
            User user1 = new User("emma", "emma1",1,"Emma", "Smith", LocalDate.of(1999, 12, 30));
            users.add(user1);
            User user2 = new User("peter", "peter1",2,"Peter", "Butler", LocalDate.of(2000, 3, 23));
            users.add(user2);
            User user3 = new User("jack", "jack1",3,"Jack", "Davies", LocalDate.of(2000, 1, 12));
            users.add(user3);
            User user4 = new User("amy", "amy1",4,"Amy", "Hill", LocalDate.of(2001, 8, 26));
            users.add(user4);
            User user5 = new User("diana", "diana1",5,"Diana", "Jones", LocalDate.of(1997, 10, 5));
            users.add(user5);
            User user6 = new User("carl", "carl1",6,"Carl", "Kelly", LocalDate.of(1999, 11, 9));
            users.add(user6);

        }
    }

}
