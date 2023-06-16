package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Tarefa;
import entidades.Usuario;

public class DaoUsuario {
	
	public boolean inserir(Usuario usuario) throws SQLException {
		
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "insert into usuarios (email, senha) values(? , sha2( ? , 256 ));";
		PreparedStatement ps = conexao.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

		ps.setString(1, usuario.getEmail());
		ps.setString(2, usuario.getSenha());

		int linhasAfetadas = ps.executeUpdate();
		
		ResultSet r = ps.getGeneratedKeys();
		
		if( r.next() ) {
			int id = r.getInt(1);	
			usuario.setId(id);
		}
		
		return (linhasAfetadas == 1 ? true : false);
	}
	
	public boolean atualizarDados(Usuario usuario) throws SQLException {
		return true;
	}
	
	public boolean atualizarSenha(Usuario usuario) throws SQLException {
		return true;
	}

	public boolean excluir(int id) throws SQLException {
		return true;
	}
	
	public Usuario buscarPorId(int idBuscado) throws SQLException {
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from usuarios where id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idBuscado);
		
		ResultSet result = ps.executeQuery();
		
		Usuario usuario = null;
		
		if( result.next() ) {
			int id = result.getInt("id");
			String email = result.getString("email");
			String senha = result.getString("senha");
			
			usuario = new Usuario(id, email, senha);
		}
		
		return usuario;
	}
	
	public Usuario buscarPorEmail(String email) throws SQLException {
		return null;
	}
	
	public List<Usuario> buscarTodos() throws SQLException {
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from usuarios";
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet result = ps.executeQuery();
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		while( result.next() ) {
			int id = result.getInt("id");
			String email = result.getString("email");
			String senha = result.getString("senha");
			
			Usuario usuario = new Usuario(id, email, senha);
	
			usuarios.add(usuario);
		}
		
		return usuarios;
	}
}
