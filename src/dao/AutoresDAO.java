package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bibli.bd;

import models.Autores;

public class AutoresDAO {
	
	private bd objBD;
	private Connection conexao;
	
	public void Inserir(Autores a) throws SQLException, ClassNotFoundException {
		objBD = new bd("biblioteca", "root","root");
		conexao = objBD.conectaBD();
		
		String sql = "insert into autores values (?,?,?)";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		
		stmt.setInt(1, 0);
		stmt.setString(2, a.getNome());
		stmt.setString(3, a.getData_nascimento());
		
		stmt.execute();
		
		conexao.close();
		
	}
	
	public void Excluir(int id) throws SQLException, ClassNotFoundException {
	    objBD = new bd("biblioteca", "root", "root");
	    conexao = objBD.conectaBD();

	    String sql = "DELETE FROM autores WHERE id = ?";
	    PreparedStatement stmt = conexao.prepareStatement(sql);
	    stmt.setInt(1, id);
	    stmt.execute();

	    stmt.close();
	    conexao.close();
	}
	
	public void Alterar(Autores a) throws SQLException, ClassNotFoundException {
	    objBD = new bd("biblioteca", "root", "root");
	    conexao = objBD.conectaBD();
	    
	    String sql = "UPDATE autores SET nome = ?, data_nascimento = ? WHERE id = ?";
	    PreparedStatement stmt = conexao.prepareStatement(sql);
	    
	    stmt.setString(1, a.getNome());
	    stmt.setString(2, a.getData_nascimento());
	    stmt.setInt(3, a.getId());
	    
	    stmt.executeUpdate();
	    conexao.close();
	}
	public List<Autores> listarAutores() throws SQLException, ClassNotFoundException {
        List<Autores> autoresList = new ArrayList<>();
        objBD = new bd("biblioteca", "root", "root");
        conexao = objBD.conectaBD();

        String sql = "SELECT * FROM autores";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Autores autores = new Autores(rs.getString("nome"), rs.getString("data_nascimento"));
            autores.setId(rs.getInt("id"));
            autoresList.add(autores);
        }

        rs.close();
        stmt.close();
        conexao.close();

        return autoresList;
	}
	public List<Autores> pesquisarAutores(String nome) throws SQLException, ClassNotFoundException {
	    List<Autores> autoresList = new ArrayList<>();
	    objBD = new bd("biblioteca", "root", "root");
	    conexao = objBD.conectaBD();

	    String sql = "SELECT * FROM autores WHERE nome LIKE ?";
	    PreparedStatement stmt = conexao.prepareStatement(sql);
	    stmt.setString(1, "%" + nome + "%");

	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
	        Autores autor = new Autores(rs.getString("nome"), rs.getString("data_nascimento"));
	        autor.setId(rs.getInt("id"));
	        autoresList.add(autor);
	    }

	    rs.close();
	    stmt.close();
	    conexao.close();

	    return autoresList;
	}
	
}
