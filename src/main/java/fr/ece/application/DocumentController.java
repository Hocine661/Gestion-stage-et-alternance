package fr.ece.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Date;

import model.Document;
import model.Utilisateur;
import model.Declaration;
import services.DocumentService;
import services.UserSession;
import services.ServiceDeclaration;


public class DocumentController {

    private final DocumentService documentService = new DocumentService();
    private final ServiceDeclaration declarationService = new ServiceDeclaration();
    @FXML
    private TableView<Document> documentTable;
    @FXML
    private TableColumn<Document, String> typeColumn;
    @FXML
    private TableColumn<Document, String> cheminColumn;
    @FXML
    private TableColumn<Document, LocalDate> dateColumn;
    @FXML
    private TableColumn<Document, String> statusColumn;
    @FXML
    private TableColumn<Document, Void> actionColumn;

    @FXML
    private Button uploadButton;
    @FXML
    private Label selectedFileLabel;
    @FXML
    private Label declarationInfoLabel;
    @FXML
    private ChoiceBox<String> typeChoiceBox;

    private Declaration currentDeclaration;


    // Methodes pour gérer les document pour le role éleve, coté admin pas encore disponible, supprimé suite à de nombreau problemes notamment le télechargement

    @FXML
    public void initialize() {
        typeChoiceBox.setItems(FXCollections.observableArrayList("Convention de stage", "Attestation de fin de stage", "Autre"));
        typeChoiceBox.setValue("Convention de stage");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        cheminColumn.setCellValueFactory(new PropertyValueFactory<>("cheminFichier"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateDepot"));

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        loadDocuments();

        configureActionButtons();
    }

    private void loadDocuments() {
        Utilisateur currentUser = UserSession.getCurrentUser();
        if (currentUser == null) return;


        currentDeclaration = declarationService.findActiveDeclarationByEleveId(currentUser.getIdUtilisateur());

        if (currentDeclaration != null) {
            declarationInfoLabel.setText("Déclaration ID: " + currentDeclaration.getIdDeclaration() + " – Statut: " + currentDeclaration.getStatut());


            ObservableList<Document> documents = FXCollections.observableArrayList(
                    documentService.findDocumentsByDeclarationId(currentDeclaration.getIdDeclaration())
            );
            documentTable.setItems(documents);
            uploadButton.setDisable(false);

        } else {
            declarationInfoLabel.setText("Aucune déclaration active trouvée.");
            documentTable.setItems(FXCollections.observableArrayList());
            uploadButton.setDisable(true);
        }
    }


    @FXML
    private void handleUploadDocument() {
        if (currentDeclaration == null) {
            new Alert(Alert.AlertType.WARNING, "Veuillez d'abord déclarer un stage/alternance.").showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner le document à déposer");
        File selectedFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());

        if (selectedFile != null) {
            try {
                String uploadDir = "C:\\app_documents\\declaration_" + currentDeclaration.getIdDeclaration();
                File destinationDir = new File(uploadDir);
                if (!destinationDir.exists()) {
                    destinationDir.mkdirs();
                }

                String fileName = selectedFile.getName();
                File destinationFile = new File(destinationDir, fileName);
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Document newDocument = new Document();
                newDocument.setIdDeclaration(currentDeclaration.getIdDeclaration());
                newDocument.setType(typeChoiceBox.getValue());
                newDocument.setCheminFichier(destinationFile.getAbsolutePath());

                boolean success = documentService.insertDocument(newDocument); // Nécessite l'implémentation de DocumentDAO.insert()

                if (success) {
                    new Alert(Alert.AlertType.INFORMATION, "Document déposé avec succès!").showAndWait();
                    loadDocuments();
                } else {
                    destinationFile.delete();
                    new Alert(Alert.AlertType.ERROR, "Erreur lors de l'enregistrement en base de données. Fichier non enregistré.").showAndWait();
                }

            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors du déplacement du fichier : " + e.getMessage()).showAndWait();
            }
        }
    }


    private void configureActionButtons() {
        actionColumn.setCellFactory(tc -> new TableCell<Document, Void>() {
            final Button downloadBtn = new Button("Télécharger");
            final Button deleteBtn = new Button("Supprimer");
            final HBox pane = new HBox(5, downloadBtn, deleteBtn);

            {
                downloadBtn.setOnAction(event -> {
                    Document document = getTableView().getItems().get(getIndex());
                    handleDownload(document);
                });

                deleteBtn.setOnAction(event -> {
                    Document document = getTableView().getItems().get(getIndex());
                    handleDelete(document);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {

                    if (UserSession.getCurrentUser() == null || !"scolarité".equalsIgnoreCase(UserSession.getCurrentUser().getRole())) {
                        deleteBtn.setVisible(false);
                    } else {
                        deleteBtn.setVisible(true);
                    }
                    setGraphic(pane);
                }
            }
        });
    }

    private void handleDownload(Document document) {
        String filePath = document.getCheminFichier();
        File fileToOpen = new File(filePath);

        if (Desktop.isDesktopSupported() && fileToOpen.exists()) {
            try {
                Desktop.getDesktop().open(fileToOpen);

            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Erreur : Le système d'exploitation n'a pas pu ouvrir le fichier. Chemin non valide ou fichier manquant.").showAndWait();
                e.printStackTrace();
            }
        } else if (!fileToOpen.exists()) {
            new Alert(Alert.AlertType.ERROR, "Erreur : Fichier introuvable sur le disque. Chemin : " + filePath).showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "Erreur : La fonctionnalité Desktop n'est pas supportée par votre système.").showAndWait();
        }
    }

    private void handleDelete(Document document) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer ce document ?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();

    }

    @FXML
    private void closeView() {
        Stage stage = (Stage) documentTable.getScene().getWindow();
        stage.close();
    }
}
