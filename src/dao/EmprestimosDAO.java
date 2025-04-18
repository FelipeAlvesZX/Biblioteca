package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bibli.bd;

import models.Emprestimos;

public class EmprestimosDAO {
    
    private bd objBD;
    private Connection conexao;

    public void Inserir(Emprestimos e) throws SQLException, ClassNotFoundException {
        objBD = new bd("biblioteca", "root","root");
		conexao = objBD.conectaBD();

        String sql = "INSERT INTO emprestimos (membro_id, livro_id, data_emprestimo, data_devolucao) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setInt(1, e.getMembro_id());
        stmt.setInt(2, e.getLivro_id());
        stmt.setString(3, e.getData_emprestimo());
        if (e.getData_devolucao() == null) {
            stmt.setNull(4, java.sql.Types.DATE); 
        } else {
            stmt.setString(4, e.getData_devolucao());
        }
        
        stmt.execute();
        conexao.close();
    }

    public void Excluir(int id) throws SQLException, ClassNotFoundException {
        objBD = new bd("biblioteca", "root", "root");
		conexao = objBD.conectaBD();

        String sql = "DELETE FROM emprestimos WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();
        conexao.close();
    }

    public void Alterar(Emprestimos e) throws SQLException, ClassNotFoundException {
        objBD = new bd("biblioteca", "root", "root");
        conexao = objBD.conectaBD();

        PreparedStatement stmt = conexao.prepareStatement("UPDATE emprestimos SET membro_id = ?, livro_id = ?, data_emprestimo = ?, data_devolucao = ? WHERE id = ?");
               
        stmt.setInt(1, e.getMembro_id());
        stmt.setInt(2, e.getLivro_id());
        stmt.setString(3, e.getData_emprestimo());
        stmt.setString(4, e.getData_devolucao());
        stmt.setInt(5, e.getId());
               
        stmt.executeUpdate();
        conexao.close();
        }
    public List<Emprestimos> listarEmprestimos() throws SQLException, ClassNotFoundException {
        List<Emprestimos> emprestimosList = new ArrayList<>();
        objBD = new bd("biblioteca", "root", "root");
        conexao = objBD.conectaBD();

        String sql = "SELECT * FROM emprestimos";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Emprestimos emprestimos = new Emprestimos(rs.getInt("membroId"), rs.getInt("livrosId"), rs.getString("data_emprestimo"),rs.getString("data_devolucao"));
            emprestimos.setId(rs.getInt("id"));
            emprestimosList.add(emprestimos);
        }

        rs.close();
        stmt.close();
        conexao.close();

        return emprestimosList;
	}
    public List<Emprestimos> pesquisarEmprestimos(String nome) throws SQLException, ClassNotFoundException {
        List<Emprestimos> emprestimosList = new ArrayList<>();
        objBD = new bd("biblioteca", "root", "root");
        conexao = objBD.conectaBD();

        String sql = "SELECT e.id, e.membro_id, e.livro_id, e.data_emprestimo, e.data_devolucao, m.nome AS nome_membro, l.titulo AS nome_livro " +
                     "FROM emprestimos e " +
                     "INNER JOIN membros m ON e.membro_id = m.id " +
                     "INNER JOIN livros l ON e.livro_id = l.id " +
                     "WHERE m.nome LIKE ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, "%" + nome + "%");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Emprestimos emprestimos = new Emprestimos(rs.getInt("membro_id"), rs.getInt("livro_id"), rs.getString("data_emprestimo"), rs.getString("data_devolucao"));
            emprestimos.setId(rs.getInt("id"));
            emprestimosList.add(emprestimos);
        }

        rs.close();
        stmt.close();
        conexao.close();

        return emprestimosList;
    }
}