package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import dao.DaoUsuario;
import entidades.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaInicialController implements Initializable{
	
	@FXML
	private BorderPane raizTelaInicial;
	
	@FXML
	private TableView<Usuario> tabelaUsuarios;
	
	@FXML
	private TableColumn<Usuario, Integer> colunaID;
	
	@FXML
	private TableColumn<Usuario, String> colunaEmail;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaID.setCellValueFactory( new PropertyValueFactory<>("id") );
		colunaEmail.setCellValueFactory( new PropertyValueFactory<>("email") );
		
		DaoUsuario daoUsuario = new DaoUsuario();
		try {
			List<Usuario> usuarios = daoUsuario.buscarTodos();
			
			tabelaUsuarios.setItems( FXCollections.observableArrayList( usuarios ) ); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void clickCadastrar() throws IOException {
		BorderPane fxml = new FXMLLoader(
				getClass().getResource("/views/TelaCadastro.fxml")).load();
		
		Scene cena = new Scene(fxml);
		
		Stage stage = (Stage) raizTelaInicial.getScene().getWindow();
		
		stage.setScene(cena);
	}

}