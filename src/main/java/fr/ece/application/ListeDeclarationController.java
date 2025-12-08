package fr.ece.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.DeclarationView;
import services.ServiceDeclaration;

public class ListeDeclarationController {

    @FXML private TableView<DeclarationView> tableView;

    @FXML private TableColumn<DeclarationView, String> emailCol;
    @FXML private TableColumn<DeclarationView, String> entrepriseCol;
    @FXML private TableColumn<DeclarationView, Object> dateDebutCol;
    @FXML private TableColumn<DeclarationView, Object> dateFinCol;
    @FXML private TableColumn<DeclarationView, String> typeCol;
    @FXML private TableColumn<DeclarationView, String> statutCol;
    @FXML private TableColumn<DeclarationView, Void> commentaireCol;

    private final ServiceDeclaration service = new ServiceDeclaration();

    @FXML
    public void initialize() {

        // Liaison colonnes → attributs du modèle
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        entrepriseCol.setCellValueFactory(new PropertyValueFactory<>("entreprise"));
        dateDebutCol.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateFinCol.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        statutCol.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // Convertir statut en ComboBox modifiable
        statutCol.setCellFactory(col -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>();

            {
                comboBox.getItems().addAll("en attente", "validé", "refusé");
                comboBox.setOnAction(e -> {
                    DeclarationView d = getTableView().getItems().get(getIndex());
                    String newStatus = comboBox.getValue();
                    d.setStatut(newStatus);
                    service.updateStatut(d.getIdDeclaration(), newStatus);
                });
            }

            @Override
            protected void updateItem(String statut, boolean empty) {
                super.updateItem(statut, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    comboBox.setValue(statut);
                    setGraphic(comboBox);
                }
            }
        });

        // Ajouter bouton commentaire
        addCommentButton();

        // Charger données
        loadData();
    }

    private void addCommentButton() {
        commentaireCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<DeclarationView, Void> call(TableColumn<DeclarationView, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Commentaire");

                    {
                        btn.setOnAction(e -> {
                            DeclarationView d = getTableView().getItems().get(getIndex());
                            TextInputDialog dialog = new TextInputDialog();
                            dialog.setTitle("Ajouter un commentaire");
                            dialog.setHeaderText("Commentaire pour : " + d.getEmail());
                            dialog.setContentText("Votre commentaire :");

                            dialog.showAndWait().ifPresent(comment -> {
                                service.addComment(d.getIdDeclaration(), comment);
                            });
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else setGraphic(btn);
                    }
                };
            }
        });
    }

    private void loadData() {
        ObservableList<DeclarationView> data =
                FXCollections.observableArrayList(service.getAllDeclarations());
        tableView.setItems(data);
    }
}

