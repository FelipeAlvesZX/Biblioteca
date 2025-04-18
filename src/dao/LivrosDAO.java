package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bibli.bd;
import models.Livros;

public class LivrosDAO {
    
    private bd objBD;
    private Connection conexao;

    public void Inserir(Livros l) throws SQLException, ClassNotFoundException {
        objBD = new bd("biblioteca", "root", "root");
		conexao = objBD.conectaBD();
		
        String sql = "INSERT INTO livros (titulo, ano_publicacao, editora_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, l.getTitulo());
        stmt.setInt(2, l.getAno_publicacao());
        stmt.setInt(3, l.getEditora_id());
        
        stmt.execute();
        conexao.close();
    }

    public void Excluir(int id) throws SQLException, ClassNotFoundException {
        objBD = new bd("biblioteca", "root", "root");
        conexao = objBD.conectaBD();

        String sql = "DELETE FROM livros WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setInt(1, id);
        stmt.execute();
        conexao.close();
    }

    public void Alterar(Livros l) throws SQLException, ClassNotFoundException {
        objBD = new bd("biblioteca", "root", "root");
		conexao = objBD.conectaBD();

        String sql = "UPDATE livros SET titulo = ?, ano_publicacao = ?, editora_id = ? WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, l.getTitulo());
        stmt.setInt(2, l.getAno_publicacao());
        stmt.setInt(3, l.getEditora_id());
        stmt.setInt(4, l.getId());
        
        stmt.executeUpdate();
        conexao.close();
    }
    
    public List<Livros> listarLivros() throws SQLException, ClassNotFoundException {
        List<Livros> livrosList = new ArrayList<>();
        objBD = new bd("biblioteca", "root", "root");
        conexao = objBD.conectaBD();

        String sql = "SELECT * FROM livros";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Livros livro = new Livros(rs.getString("titulo"), rs.getInt("ano_publicacao"), rs.getInt("editora_id"));
            livro.setId(rs.getInt("id"));
            livrosList.add(livro);
        }

        rs.close();
        stmt.close();
        conexao.close();

        return livrosList;
    }
    
    public List<Livros> pesquisarLivros(String nome) throws SQLException, ClassNotFoundException {
        List<Livros> livrosList = new ArrayList<>();
        objBD = new bd("biblioteca", "root", "root");
        conexao = objBD.conectaBD();

        String sql = "SELECT * FROM livros WHERE titulo LIKE ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, "%" + nome + "%");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Livros livro = new Livros(rs.getString("titulo"), rs.getInt("ano_publicacao"), rs.getInt("editora_id"));
            livro.setId(rs.getInt("id"));
            livrosList.add(livro);
        }

        rs.close();
        stmt.close();
        conexao.close();

        return livrosList;
    }
    
    public String obterTituloLivro(int livroId) throws SQLException, ClassNotFoundException {
        String titulo = null;
        objBD = new bd("biblioteca", "root", "root");
        conexao = objBD.conectaBD();

        String sql = "SELECT titulo FROM livros WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, livroId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            titulo = rs.getString("titulo");
        }

        rs.close();
        stmt.close();
        conexao.close();

        return titulo;
    }
    

}