package fr.ece.application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeControllerAdmin {
    public class User{
        private String lastname, firstname, email;

        public User(String lastname, String firstname, String email) {
            this.lastname = lastname;
            this.firstname = firstname;
            this.email = email;
        }


        public String getLastname() {
            return lastname;
        }

        public String getFirstname() {
            return firstname;
        }

        public String getEmail() {
            return email;
        }
    }


    @FXML
    public TableView<User> userTable;
    @FXML
    public TableColumn<User, String > emailCell;
    @FXML
    public TableColumn<User, String > entrepriseCell;
    @FXML
    public TableColumn<User, String > dateCell;
    @FXML
    public TableColumn<User, String > typeCell;
    @FXML
    public TableColumn<User, String > validationCell;


    public void initialize(){
        User[] users = new User[]{
                new User("AERNOUTS", "Edouard", "edouard@ece.fr"),
                new User("RAMBA", "Cheik", "Cheik@ece.fr")
        };
        emailCell.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        entrepriseCell.setCellValueFactory(new PropertyValueFactory<User, String>("entreprise"));
        dateCell.setCellValueFactory(new PropertyValueFactory<User, String>("date"));
        typeCell.setCellValueFactory(new PropertyValueFactory<User, String>("type"));
        validationCell.setCellValueFactory(new PropertyValueFactory<User, String>("validation"));
        var list = FXCollections.observableArrayList(users);

        userTable.setItems(list);
    }
}
