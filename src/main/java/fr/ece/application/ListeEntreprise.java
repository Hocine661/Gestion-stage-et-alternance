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
        // Associer les colonnes aux propriétés de la classe Entreprise
        nomCell.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseCell.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tuteurCell.setCellValueFactory(new PropertyValueFactory<>("tuteur"));
        contactCell.setCellValueFactory(new PropertyValueFactory<>("contact"));

        // Charger les données de la base
        loadTableData();
    }

    private void loadTableData() {
        try {
            ObservableList<Entreprise> list =
                    FXCollections.observableArrayList(entrepriseDAO.findAll());
            userTable.setItems(list);

            // DEBUG : vérifier que les entreprises sont bien chargées
            list.forEach(e -> System.out.println("Entreprise chargée : " + e.getNom()));

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des entreprises : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
