package fr.ece.application;

import dao.EntrepriseDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Entreprise;

public class ListeEntreprise {

    @FXML
    private TableView<Entreprise> userTable;

    @FXML
    private TableColumn<Entreprise, String> nomCell;

    @FXML
    private TableColumn<Entreprise, String> adresseCell;

    @FXML
    private TableColumn<Entreprise, String> tuteurCell;

    @FXML
    private TableColumn<Entreprise, String> contactCell;

    private EntrepriseDAO entrepriseDAO = new EntrepriseDAO();

    @FXML
    public void initialize() {
        nomCell.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseCell.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tuteurCell.setCellValueFactory(new PropertyValueFactory<>("tuteur"));
        contactCell.setCellValueFactory(new PropertyValueFactory<>("contact"));
        
        loadTableData();
    }

    private void loadTableData() {

        ObservableList<Entreprise> list =
                FXCollections.observableArrayList(entrepriseDAO.findAll()); // TEST : Affiche toutes les entreprises
        userTable.setItems(list);


    }
}
