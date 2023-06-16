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

public class DaoTarefa {
	
	public boolean inserir(Tarefa tarefa) throws SQLException {
				
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "insert into tarefas (descricao, prioridade, usuario_id) values(? , ? , ?);";
		PreparedStatement ps = conexao.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

		ps.setString(1, tarefa.getDescricao());
		ps.setInt(2, tarefa.getPrioridade());
		ps.setInt(3, tarefa.getUsuario().getId() );

		int linhasAfetadas = ps.executeUpdate();
		
		ResultSet r = ps.getGeneratedKeys();
		
		if( r.next() ) {
			int id = r.getInt(1);	
			tarefa.setId(id);
		}
		
		return (linhasAfetadas == 1 ? true : false);
	}

	public boolean atualizar(Tarefa tarefa) throws SQLException {
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "update tarefas set descricao = ?, prioridade = ? where id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, tarefa.getDescricao());
		ps.setInt(2, tarefa.getPrioridade());
		ps.setInt(3, tarefa.getId());
		
		if( ps.executeUpdate() == 1) {
			return true;
		}else {
			return false;
		}
	}

	public boolean excluir(int id) throws SQLException {
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "delete from tarefas where id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, id);
		
		if( ps.executeUpdate() == 1) {
			return true;
		}else {
			return false;
		}
	}

	public Tarefa buscar(int idBuscado) throws SQLException {
		
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from tarefas where id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idBuscado);
		
		ResultSet result = ps.executeQuery();
		
		Tarefa tarefa = null;
		
		if( result.next() ) {
			int id = result.getInt("id");
			String descricao = result.getString("descricao");
			int prioridade = result.getInt("prioridade");
			int idUsuario = result.getInt("usuario_id");
			
			DaoUsuario daoUser = new DaoUsuario();
			Usuario u = daoUser.buscarPorId(idUsuario);
			
			tarefa = new Tarefa(id, descricao, prioridade, u);
		}
		
		return tarefa;
	}

	public List<Tarefa> buscarTodas() throws SQLException {
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from tarefas";
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet result = ps.executeQuery();
		
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		
		DaoUsuario daoUser = new DaoUsuario();

		while( result.next() ) {
			int id = result.getInt("id");
			String descricao = result.getString("descricao");
			int prioridade = result.getInt("prioridade");
			int idUsuario = result.getInt("usuario_id");
			
			Usuario u = daoUser.buscarPorId(idUsuario);
			
			Tarefa t = new Tarefa(id, descricao, prioridade, u);
	
			tarefas.add(t);
		}
		
		return tarefas;
	}

	public List<Tarefa> pesquisarPorDescricao(String texto) throws SQLException {
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from tarefas where descricao like ? ";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%"+texto+"%");
		
		ResultSet result = ps.executeQuery();
		
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		
		DaoUsuario daoUser = new DaoUsuario();
		
		while( result.next() ) {
			int id = result.getInt("id");
			String descricao = result.getString("descricao");
			int prioridade = result.getInt("prioridade");
			int idUsuario = result.getInt("usuario_id");
			
			Usuario u = daoUser.buscarPorId(idUsuario);
			Tarefa t = new Tarefa(id, descricao, prioridade, u);
	
			tarefas.add(t);
		}
		
		return tarefas;
	}
	
	public List<Tarefa> tarefasPorUsuario(String email) throws SQLException {
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from tarefas left join usuarios on tarefas.usuario_id = usuarios.id where usuarios.email = ?;";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, email);
		
		ResultSet result = ps.executeQuery();
		
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		
		
		if( result.next() ) {			
			int idUser = result.getInt("usuario_id");
			String senha = result.getString("senha");
			
			Usuario usuario = new Usuario(idUser, email, senha);
			
			do {
				int id = result.getInt("id");
				String descricao = result.getString("descricao");
				int prioridade = result.getInt("prioridade");
				
				Tarefa t = new Tarefa(id, descricao, prioridade, usuario);
		
				tarefas.add(t);
			}while(result.next());
		}
		
		return tarefas;
	}
}
