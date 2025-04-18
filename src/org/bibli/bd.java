package org.bibli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class bd {
	
	private Connection conexao;
	private Statement stmt;
	private boolean erro;
	private String msg;
	private String Banco, Usuario, Senha;
	

	public bd(String b, String u, String s) {
		this.Banco = "jdbc:mysql://localhost/" + b;
		this.Usuario = u;
		this.Senha = s;
		erro = false;
		msg = "";
	}

	public Connection conectaBD() throws ClassNotFoundException {
	    erro = false;
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conexao = DriverManager.getConnection(this.Banco, this.Usuario, this.Senha);
	        stmt = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    } catch (SQLException e) {
	        this.msg = "Erro de conexão com o banco de dados! \nErro: " + e.getMessage();
	        erro = true;
	    }
	    return conexao;
	}
	

	public boolean desconectaBD() {
		boolean sucesso = true;
		try {
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			this.msg = "Erro ao desconectar do banco de dados!";
			sucesso = false;
		}
		return sucesso;
	}

	public String mensagem() {
		return this.msg;
	}

	public boolean executarSQL(String sql) {
		int i = -1;
		this.erro = false;
		this.msg = "Operacao realizada com sucesso!";
		try {
			i = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			this.msg = "Erro na execucao da operacao!\nErro: " + e.getMessage();
			this.erro = true;
		}
		return erro;
	}
	
		public ResultSet consultar(String sql){
		ResultSet res = null;
		this.erro = false;
		this.msg = "Consulta Executada com Sucesso!";
		try {
			res = stmt.executeQuery(sql);
			
		} catch(Exception e) {
			this.msg = "Erro na execucao da consulta\nErro: " + e.getMessage();
			this.erro = true;
		}
		
		return res;
	}
	
}
