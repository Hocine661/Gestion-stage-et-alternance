package fr.ece.application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



import dao.DeclarationDAO;
import model.DeclarationView;

import javafx.collections.ObservableList;


public class ListeDeclartion {

    // ===== TableView & Columns =====
    @FXML
    private TableView<DeclarationView> userTable;

    @FXML
    private TableColumn<DeclarationView, String> emailCell;

    @FXML
    private TableColumn<DeclarationView, String> entrepriseCell;

    @FXML
    private TableColumn<DeclarationView, String> dateCell;

    @FXML
    private TableColumn<DeclarationView, String> typeCell;

    @FXML
    private TableColumn<DeclarationView, String> validationCell;

    // ===== DAO =====
    private DeclarationDAO declarationDAO = new DeclarationDAO();


    // =========================================================
    //   INITIALISATION AUTOMATIQUE AU CHARGEMENT DE LA PAGE
    // =========================================================
    @FXML
    public void initialize() {

        // ----- LIAISON COLONNES ↔ ATTRIBUTS DECLARATIONVIEW -----
        emailCell.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        entrepriseCell.setCellValueFactory(new PropertyValueFactory<>("entreprise"));
        dateCell.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        typeCell.setCellValueFactory(new PropertyValueFactory<>("type"));
        validationCell.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // ----- CHARGER LES DONNÉES -----
        loadTableData();
    }


    // =========================================================
    //   FONCTION QUI REMPLIT LE TABLEVIEW
    // =========================================================
    private void loadTableData() {

        // Récupérer la liste depuis le DAO
        ObservableList<DeclarationView> list =
                FXCollections.observableArrayList(declarationDAO.findAllForAdmin());

        // Injecter dans le tableau
        userTable.setItems(list);
    }
}

