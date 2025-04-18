package models;

public class Livros {

	int id;
	String titulo;
	int ano_publicacao;
	int editora_id;
	public Livros(String titulo, int ano_publicacao, int editora_id) {
		super();
		this.titulo = titulo;
		this.ano_publicacao = ano_publicacao;
		this.editora_id = editora_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getAno_publicacao() {
		return ano_publicacao;
	}
	public void setAno_publicacao(int ano_publicacao) {
		this.ano_publicacao = ano_publicacao;
	}
	public int getEditora_id() {
		return editora_id;
	}
	public void setEditora_id(int editora_id) {
		this.editora_id = editora_id;
	}
	
	
	@Override
	public String toString() {
	    return this.titulo;
	}
}
