package org.bibli.emprestimo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.bibli.bd;
import org.bibli.frmMenu;


import dao.EmprestimosDAO;
import dao.LivrosDAO;
import dao.MembrosDAO;
import models.Emprestimos;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;

public class frmConsEmprestimo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNmClient;
	private JTable tblEmp;
	private JButton btnSair;
	private frmCadEmprestimo cadEmp;
	private frmMenu frmmenu;
	private static bd objBD;
	private JButton btnExcluir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmConsEmprestimo frame = new frmConsEmprestimo(objBD);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param objBD 
	 */
	public frmConsEmprestimo(bd objBD) {
		setTitle("Consultar Emprestimos de Livros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 556, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome do Cliente:");
		lblNome.setBounds(10, 23, 132, 14);
		contentPane.add(lblNome);
		
		txtNmClient = new JTextField();
		txtNmClient.setBounds(152, 20, 267, 20);
		contentPane.add(txtNmClient);
		txtNmClient.setColumns(10);
		
		JButton btnPesq = new JButton("Pesquisar");
		btnPesq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = txtNmClient.getText().trim(); 
				mostrarEmprestimos(nome);
			}
		});
		btnPesq.setBounds(429, 19, 101, 23);
		contentPane.add(btnPesq);
		
		tblEmp = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Nome do Cliente");
		model.addColumn("Nome do Livro");
		model.addColumn("Data do Emprestimos");
		model.addColumn("Data da Devolucao");
		tblEmp.setModel(model);
		tblEmp.setBounds(10, 51, 520, 274);
		contentPane.add(tblEmp);
		
		JButton btnCad = new JButton("Cadastrar ");
		btnCad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadEmp = new frmCadEmprestimo(objBD);
				cadEmp.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnCad);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnCad.setBounds(23, 336, 101, 23);
		contentPane.add(btnCad);
		
		btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmmenu = new frmMenu();
				frmmenu.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnSair);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnSair.setBounds(428, 336, 89, 23);
		contentPane.add(btnSair);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int linhaSelecionada = tblEmp.getSelectedRow();
		        if (linhaSelecionada != -1) {
		            int id = (int) tblEmp.getValueAt(linhaSelecionada, 0); 
		            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este emprestimo de livro?", "Confirmação", JOptionPane.YES_NO_OPTION);
		            if (resposta == JOptionPane.YES_OPTION) {
		                try {
		                    EmprestimosDAO emprestimoDAO = new EmprestimosDAO();
		                    emprestimoDAO.Excluir(id);
		                    JOptionPane.showMessageDialog(null, "Emprestimo excluído com sucesso!");
		                    mostrarEmprestimos(""); 
		                } catch (SQLException | ClassNotFoundException ex) {
		                    JOptionPane.showMessageDialog(null, "Erro ao excluir emprestimo: " + ex.getMessage());
		                }
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Selecione um emprestimo para excluir.");
		        }
		    }
		});
		btnExcluir.setBounds(296, 336, 89, 23);
		contentPane.add(btnExcluir);
		
		JButton btnSele = new JButton("Editar");
		btnSele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirTelaCadastro();
			}
		});
		btnSele.setBounds(152, 336, 103, 23);
		contentPane.add(btnSele);
		
		mostrarEmprestimos("");
	}
	
	private void Excluir(int id) throws SQLException, ClassNotFoundException {
	    EmprestimosDAO emprestimoDAO = new EmprestimosDAO();
	    emprestimoDAO.Excluir(id);
	}

	private void mostrarEmprestimos(String nome) {
	    EmprestimosDAO emprestimosDAO = new EmprestimosDAO();
	    MembrosDAO membrosDAO = new MembrosDAO();
	    LivrosDAO livrosDAO = new LivrosDAO();
	    
	    try {
	        List<Emprestimos> emprestimosList = emprestimosDAO.pesquisarEmprestimos(nome);
	        DefaultTableModel model = (DefaultTableModel) tblEmp.getModel();
	        model.setRowCount(0); 

	        for (Emprestimos emprestimo : emprestimosList) {
	            String nomeMembro = membrosDAO.obterNomeMembro(emprestimo.getMembro_id());
	            String nomeLivro = livrosDAO.obterTituloLivro(emprestimo.getLivro_id());
	            
	            model.addRow(new Object[]{
	            	emprestimo.getId(),
	                nomeMembro, 
	                nomeLivro,   
	                emprestimo.getData_emprestimo(),
	                emprestimo.getData_devolucao()
	            });
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar empréstimos: " + e.getMessage());
	    }
	}

	private void abrirTelaCadastro() {
	    int linhaSelecionada = tblEmp.getSelectedRow();
	    if (linhaSelecionada != -1) {
	        String nomeCliente = (String) tblEmp.getValueAt(linhaSelecionada, 1);
	        String nomeLivro = (String) tblEmp.getValueAt(linhaSelecionada, 2);
	        String dtEmpr = (String) tblEmp.getValueAt(linhaSelecionada, 3);
	        String dtDev = (String) tblEmp.getValueAt(linhaSelecionada, 4);
	        int id = (int) tblEmp.getValueAt(linhaSelecionada, 0);

	        frmCadEmprestimo cadEmpr = new frmCadEmprestimo(objBD);
	        cadEmpr.setVisible(true);

	        cadEmpr.cbxNome.setSelectedItem(nomeCliente);
	        cadEmpr.cbxlivro.setSelectedItem(nomeLivro);
	        cadEmpr.txtDtEmpr.setText(dtEmpr);
	        cadEmpr.txtDtDev.setText(dtDev);
	        cadEmpr.setId(id);
	        
	    }
	}
}
