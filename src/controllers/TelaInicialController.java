package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaInicialController {
	
	@FXML
	private BorderPane raizTelaInicial;
	
	public void clickCadastrar() throws IOException {
		BorderPane fxml = new FXMLLoader(
				getClass().getResource("/views/TelaCadastro.fxml")).load();
		
		Scene cena = new Scene(fxml);
		
		Stage stage = (Stage) raizTelaInicial.getScene().getWindow();
		
		stage.setScene(cena);
	}
	
}