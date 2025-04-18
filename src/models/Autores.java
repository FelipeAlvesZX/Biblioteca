package models;


public class Autores {

	int id;
	String nome;
	String data_nascimento;
	
	public Autores(String nome, String data_nascimento) {
		super();
		this.nome = nome;
		this.data_nascimento = data_nascimento;
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
	public String getData_nascimento() {
		return data_nascimento;
	}
	public void setData_nascimento(String data_nascimento) {
		this.data_nascimento = data_nascimento;
	}
	
	
	
}
