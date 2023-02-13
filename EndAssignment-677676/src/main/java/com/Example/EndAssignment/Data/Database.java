package com.Example.EndAssignment.Data;

import com.Example.EndAssignment.Model.Item;
import com.Example.EndAssignment.Model.Member;
import com.Example.EndAssignment.Model.User;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> users = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<Member> members = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<User> getUsers() {
        return users;
    }

    public Database() throws IOException {
        users.add(new User("MarkDH", "Mark De Haan", "Hello"));
        users.add(new User("PeterS", "Peter Stikker", "P@ssword123"));

        items.add(new Item(89, "No", "HTML and CSS", "Inholland Teacher", LocalDate.of(2022, 9, 29)));
        items.add(new Item(111, "Yes", "Da Vinci Code", "Dan Brown"));
        items.add(new Item(114, "Yes", "Memories of a Geisha", "Arthur Golden"));

        deserializeMembers();

    }

    public void serializeItems() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(new File("items.dat"));
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            for (Item item : items
            ) {
                oos.writeObject(item);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void serializeMembers() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(new File("members.dat"));
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            for (Member member : members
            ) {
                oos.writeObject(member);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deserializeItems() throws IOException {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());

        try (BufferedReader br = Files.newBufferedReader(file.toPath(),
                StandardCharsets.US_ASCII)) {
            String headerLine = br.readLine();
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(";");
                //file has code, title and author in this order
                Item item = new Item(attributes);
                items.add(item);
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void deserializeMembers() throws IOException {
        try (FileInputStream fis = new FileInputStream(new File("members.dat"));
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            while (true) {
                try {
                    Member member = (Member) ois.readObject();
                    members.add(member);
                } catch (EOFException eofException) {
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {

            members.add(new Member(1, "Sally", "Smith", LocalDate.of(1997, 04, 12)));
            members.add(new Member(2, "Jack", "Brown", LocalDate.of(1993, 07, 8)));
            members.add(new Member(3, "Lisa", "Jones", LocalDate.of(2000, 04, 29)));

        }
    }
}

