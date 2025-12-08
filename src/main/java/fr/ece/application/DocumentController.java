package fr.ece.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
// Assurez-vous d'avoir un ServiceDeclaration avec la méthode findActiveDeclarationByEleveId
import services.ServiceDeclaration;


public class DocumentController {

    // ===== Dépendances Services =====
    private final DocumentService documentService = new DocumentService();
    private final ServiceDeclaration declarationService = new ServiceDeclaration(); // Nécessaire pour obtenir l'ID de la déclaration

    // ===== FXML View Elements (Réutilisez les noms du FXML) =====
    @FXML private TableView<Document> documentTable;
    @FXML private TableColumn<Document, String> typeColumn;
    @FXML private TableColumn<Document, String> cheminColumn; // Mappé à cheminFichier
    @FXML private TableColumn<Document, LocalDate> dateColumn;     // Mappé à dateDepot
    @FXML private TableColumn<Document, String> statusColumn; // Statut de vérification
    @FXML private TableColumn<Document, Void> actionColumn;

    @FXML private Button uploadButton;
    @FXML private Label selectedFileLabel;
    @FXML private Label declarationInfoLabel;
    // Ajout d'un contrôle pour sélectionner le type de document
    @FXML private ChoiceBox<String> typeChoiceBox;

    private Declaration currentDeclaration;

    @FXML
    public void initialize() {

        // 1. Initialiser les choix de types de documents
        typeChoiceBox.setItems(FXCollections.observableArrayList("Convention de stage", "Attestation de fin de stage", "Autre"));
        typeChoiceBox.setValue("Convention de stage");

        // 2. Initialiser les colonnes de la TableView (les noms entre guillemets sont les noms des getters dans Document.java)
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        cheminColumn.setCellValueFactory(new PropertyValueFactory<>("cheminFichier"));

        // DateColumn utilise java.util.Date, qui est dans votre modèle Document.java
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateDepot"));

        // NOTE: La colonne Statut n'est pas dans la BDD, elle doit être gérée par une CellFactory si vous voulez l'afficher
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("type")); // Utilisation temporaire du Type

        // 3. Charger les documents liés à la déclaration active
        loadDocuments();

        // 4. Configurer les boutons d'action (Télécharger/Supprimer)
        configureActionButtons();
    }

    /**
     * Charge les documents pour la déclaration active de l'utilisateur actuel.
     */
    private void loadDocuments() {
        Utilisateur currentUser = UserSession.getCurrentUser();
        if (currentUser == null) return;

        // Récupérer la déclaration active (méthode existante dans HomeEleveController)
        currentDeclaration = declarationService.findActiveDeclarationByEleveId(currentUser.getIdUtilisateur());

        if (currentDeclaration != null) {
            declarationInfoLabel.setText("Déclaration ID: " + currentDeclaration.getIdDeclaration() + " – Statut: " + currentDeclaration.getStatut());

            // Appelle la méthode findByDeclaration de DocumentDAO via le Service
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


    /**
     * Gère le processus d'upload d'un fichier et l'insertion en BDD.
     */
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
                // 1. Définir le répertoire de stockage physique (doit exister)
                String uploadDir = "C:\\app_documents\\declaration_" + currentDeclaration.getIdDeclaration();
                File destinationDir = new File(uploadDir);
                if (!destinationDir.exists()) {
                    destinationDir.mkdirs();
                }

                // 2. Copier le fichier dans le répertoire de destination
                String fileName = selectedFile.getName();
                File destinationFile = new File(destinationDir, fileName);
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // 3. Créer l'objet Document et l'insérer en BDD
                Document newDocument = new Document();
                newDocument.setIdDeclaration(currentDeclaration.getIdDeclaration());
                newDocument.setType(typeChoiceBox.getValue());
                newDocument.setCheminFichier(destinationFile.getAbsolutePath());
                // NOTE: La date de dépôt (java.util.Date) est gérée par le DAO ou par Date.from(Instant.now())
                // Pour simplifier, nous laissons le DAO gérer la date via 'NOW()'

                boolean success = documentService.insertDocument(newDocument); // Nécessite l'implémentation de DocumentDAO.insert()

                if (success) {
                    new Alert(Alert.AlertType.INFORMATION, "Document déposé avec succès!").showAndWait();
                    loadDocuments(); // Rafraîchir la liste
                } else {
                    // Supprimer le fichier copié si l'insertion BDD a échoué
                    destinationFile.delete();
                    new Alert(Alert.AlertType.ERROR, "Erreur lors de l'enregistrement en base de données. Fichier non enregistré.").showAndWait();
                }

            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors du déplacement du fichier : " + e.getMessage()).showAndWait();
            }
        }
    }

    /**
     * Configure les boutons Télécharger/Supprimer dans la colonne d'actions.
     */
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
                    // Masquer le bouton "Supprimer" si l'utilisateur n'est pas ADMIN
                    if (UserSession.getCurrentUser() == null || !"scolarité".equalsIgnoreCase(UserSession.getCurrentUser().getRole())) {
                        // On utilise "scolarité" d'après votre fichier register.fxml
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
        // Logique de téléchargement du fichier
        new Alert(Alert.AlertType.INFORMATION, "Fonctionnalité de téléchargement pour: " + document.getCheminFichier() + " (À implémenter!)").showAndWait();
        // Vous devrez utiliser Desktop.getDesktop().open(new File(document.getCheminFichier())); ou une méthode de copie.
    }

    private void handleDelete(Document document) {
        // Demande de confirmation
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer ce document ?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();

    }

    @FXML
    private void closeView() {
        Stage stage = (Stage) documentTable.getScene().getWindow();
        stage.close();
    }
}
