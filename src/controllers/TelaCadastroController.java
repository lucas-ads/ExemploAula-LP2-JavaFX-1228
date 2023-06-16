package controllers;

import java.io.IOException;
import java.sql.SQLException;

import dao.DaoUsuario;
import entidades.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaCadastroController {
	
	@FXML
	private BorderPane raizTelaCadastro;
	
	@FXML
	private TextField textFieldEmail;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private TextField textFieldIdade;
	
	public void clickCadastrar() throws IOException{
		String email = textFieldEmail.getText().trim();
		String senha = passwordField.getText();
		int idade = Integer.parseInt( textFieldIdade.getText() );
		
		Usuario usuario = new Usuario(email, senha);
		
		DaoUsuario daoUsuario = new DaoUsuario();
		
		try {
			daoUsuario.inserir(usuario);
			
			clickCancelar();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Atenção");
			alert.setContentText("Erro ao cadastrar: " + e.getMessage());
			alert.show();
		}
	}
	
	public void clickCancelar() throws IOException {
		BorderPane fxml = new FXMLLoader(
				getClass().getResource("/views/TelaInicial.fxml")).load();
		
		Scene cena = new Scene(fxml);
		
		Stage stage = (Stage) raizTelaCadastro.getScene().getWindow();
		
		stage.setScene(cena);
	}
	
}
