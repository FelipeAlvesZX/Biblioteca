package models;

public class Emprestimos {

	int id;
	int membro_id;
	int livro_id;
	String data_emprestimo;
	String data_devolucao;
	public Emprestimos(int membro_id, int livro_id, String data_emprestimo, String data_devolucao) {
		super();
		this.membro_id = membro_id;
		this.livro_id = livro_id;
		this.data_emprestimo = data_emprestimo;
		this.data_devolucao = data_devolucao;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMembro_id() {
		return membro_id;
	}
	public void setMembro_id(int membro_id) {
		this.membro_id = membro_id;
	}
	public int getLivro_id() {
		return livro_id;
	}
	public void setLivro_id(int livro_id) {
		this.livro_id = livro_id;
	}
	public String getData_emprestimo() {
		return data_emprestimo;
	}
	public void setData_emprestimo(String data_emprestimo) {
		this.data_emprestimo = data_emprestimo;
	}
	public String getData_devolucao() {
		return data_devolucao;
	}
	public void setData_devolucao(String data_devolucao) {
		this.data_devolucao = data_devolucao;
	}
	
	
	
}
