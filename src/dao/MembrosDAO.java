package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bibli.bd;

import models.Autores;
import models.Membros;

public class MembrosDAO {
    
    private bd objBD;
    private Connection conexao;

    public void Inserir(Membros m) throws SQLException, ClassNotFoundException {
        objBD = new bd("biblioteca", "root", "root");
		conexao = objBD.conectaBD();

        String sql = "INSERT INTO membros (nome, email, telefone, data_cadastro) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, m.getNome());
        stmt.setString(2, m.getEmail());
        stmt.setString(3, m.getTelefone());
        stmt.setString(4, m.getData_cadastro());
        
        stmt.execute();
        conexao.close();
    }

    public void Excluir(int id) throws SQLException, ClassNotFoundException {
        objBD = new bd("biblioteca", "root", "root");
		conexao = objBD.conectaBD();

        String sql = "DELETE FROM membros WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setInt(1, id);
        stmt.execute();
        conexao.close();
    }

    public void Alterar(Membros m) throws SQLException, ClassNotFoundException {
        objBD = new bd("biblioteca", "root", "root");
		conexao = objBD.conectaBD();

        String sql = "UPDATE membros SET nome = ?, email = ?, telefone = ?, data_cadastro = ? WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, m.getNome());
        stmt.setString(2, m.getEmail());
        stmt.setString(3, m.getTelefone());
        stmt.setString(4, m.getData_cadastro());
        stmt.setInt(5, m.getId());
        
        stmt.executeUpdate();
        conexao.close();
    }
    public List<Membros> listarMembros() throws SQLException, ClassNotFoundException {
        List<Membros> listMemb = new ArrayList<>();
        objBD = new bd("biblioteca", "root", "root");
        conexao = objBD.conectaBD();

        String sql = "SELECT * FROM membros";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
        	Membros membro = new Membros(rs.getString("nome"), rs.getString("email"), rs.getString("telefone"), rs.getString("data_cadastro"));
            membro.setId(rs.getInt("id"));
            listMemb.add(membro);
        }

        rs.close();
        stmt.close();
        conexao.close();

        return listMemb;
    }
    
    public List<Membros> pesquisarMembros(String nome) throws SQLException, ClassNotFoundException {
        List<Membros> listMemb = new ArrayList<>();
        objBD = new bd("biblioteca", "root", "root");
        conexao = objBD.conectaBD();

        String sql = "SELECT * FROM autores WHERE nome LIKE ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
	    stmt.setString(1, "%" + nome + "%");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
        	Membros membro = new Membros(rs.getString("nome"), rs.getString("email"), rs.getString("telefone"), rs.getString("data_cadastro"));
            membro.setId(rs.getInt("id"));
            listMemb.add(membro);
        }

        rs.close();
        stmt.close();
        conexao.close();

        return listMemb;
    }
    public String obterNomeMembro(int membroId) throws SQLException, ClassNotFoundException {
        String nome = null;
        objBD = new bd("biblioteca", "root", "root");
        conexao = objBD.conectaBD();

        String sql = "SELECT nome FROM membros WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, membroId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            nome = rs.getString("nome");
        }

        rs.close();
        stmt.close();
        conexao.close();

        return nome;
    }
    
   
}