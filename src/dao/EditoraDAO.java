package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bibli.bd;

import models.Editoras;

public class EditoraDAO {
    
    private bd objBD;
    private Connection conexao;

    public void Inserir(Editoras e) throws SQLException, ClassNotFoundException {
        objBD = new bd("biblioteca", "root", "root");
		conexao = objBD.conectaBD();
		String sql = "INSERT INTO editoras (nome, endereco, telefone) VALUES (?, ?, ?)";
		PreparedStatement stmt = conexao.prepareStatement(sql);
	        
		stmt.setString(1, e.getNome());
		stmt.setString(2, e.getEndereco());
		stmt.setString(3, e.getTelefone());
	        
		stmt.execute();
		stmt.close(); 
		conexao.close();
        
    }

    public void Excluir(int id) throws SQLException, ClassNotFoundException {
        objBD = new bd("biblioteca", "root", "root");
		conexao = objBD.conectaBD();
		String sql = "DELETE FROM editoras WHERE id = ?";
		PreparedStatement stmt = conexao.prepareStatement(sql);
        
		stmt.setInt(1, id);
		stmt.execute();
		stmt.close(); 
		conexao.close();

         
    }

    public void Alterar(Editoras e) throws SQLException, Exception {
        objBD = new bd("biblioteca", "root", "root");
       	conexao = objBD.conectaBD();

		String sql = "UPDATE editoras SET nome = ?, endereco = ?, telefone = ? WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);	        
	    stmt.setString(1, e.getNome());
	    stmt.setString(2, e.getEndereco());
	    stmt.setString(3, e.getTelefone());
	    stmt.setInt(4, e.getId());
	        
	    stmt.executeUpdate();
	    stmt.close(); 
	    conexao.close(); 

    }
    
    public List<Editoras> listarEditoras() throws SQLException, ClassNotFoundException {
        List<Editoras> editorasList = new ArrayList<>();
        objBD = new bd("biblioteca", "root", "root");
        conexao = objBD.conectaBD();

        String sql = "SELECT * FROM editoras";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Editoras editora = new Editoras(rs.getString("nome"), rs.getString("endereco"), rs.getString("telefone"));
            editora.setId(rs.getInt("id"));
            editorasList.add(editora);
        }

        rs.close();
        stmt.close();
        conexao.close();

        return editorasList;
    }
    
    public List<Editoras> pesquisarEditoras(String nome) throws SQLException, ClassNotFoundException {
	    List<Editoras> editorasList = new ArrayList<>();
	    objBD = new bd("biblioteca", "root", "root");
	    conexao = objBD.conectaBD();

	    String sql = "SELECT * FROM editoras WHERE nome LIKE ?";
	    PreparedStatement stmt = conexao.prepareStatement(sql);
	    stmt.setString(1, "%" + nome + "%");

	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
	    	Editoras editora = new Editoras(rs.getString("nome"), rs.getString("endereco"), rs.getString("telefone"));
	    	editora.setId(rs.getInt("id"));
	        editorasList.add(editora);
	    }

	    rs.close();
	    stmt.close();
	    conexao.close();

	    return editorasList;
	}
    
    public String obterNmEdi(int editoraId) throws SQLException, ClassNotFoundException {
        String nmEditora = null;
        conexao = objBD.conectaBD();
        String sql = "SELECT nome FROM editoras WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, editoraId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            nmEditora = rs.getString("nome");
        }
        rs.close();
        stmt.close();
        conexao.close();
        return nmEditora;
    }
}