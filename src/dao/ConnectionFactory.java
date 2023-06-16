package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private ConnectionFactory() {}
	
	private static Connection conexao = null;
	
	public static Connection getConexao() throws SQLException {
		
		if(conexao == null) {
			String dbURL = "jdbc:mysql://localhost:3306/tasksdb1228";
			String username = "root";
			String senha = "";
			
			ConnectionFactory.conexao = DriverManager.getConnection(dbURL, username, senha);
		}
		
		return conexao;
	}
	
}
