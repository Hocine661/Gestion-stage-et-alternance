package fr.ece.application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField mdpField;

    @FXML
    private RadioButton eleveRadio;

    @FXML
    private RadioButton ScolaritéRadio;

    @FXML
    private Button inscriptionButton;
}
