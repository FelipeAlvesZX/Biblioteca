package models;


public class Membros {

	
	int id;
	String nome;
	String email;
	String telefone;
	String data_cadastro;
	public Membros(String nome, String email, String telefone, String data_cadastro) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.data_cadastro = data_cadastro;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getData_cadastro() {
		return data_cadastro;
	}
	public void setData_cadastro(String data_cadastro) {
		this.data_cadastro = data_cadastro;
	}
	
	@Override
	public String toString() {
	    return this.nome; 
	}
	
	
	
	
}
