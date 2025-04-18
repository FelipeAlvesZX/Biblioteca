package org.bibli.livros;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.bibli.bd;
import org.bibli.frmMenu;
import org.bibli.autores.frmCadAut;

import dao.AutoresDAO;
import dao.LivrosDAO;

import models.Livros;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class frmConsLivro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtLivro;
	private JTable tblLivro;
	private frmCadLivros cadLivro;
	private frmMenu frmmenu;
	private static bd objBD;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmConsLivro frame = new frmConsLivro(objBD);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmConsLivro(bd objBD) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 556, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblConsLivro = new JLabel("Consultar Livros");
		lblConsLivro.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsLivro.setBounds(227, 11, 133, 14);
		contentPane.add(lblConsLivro);
		
		txtLivro = new JTextField();
		txtLivro.setBounds(104, 36, 320, 20);
		contentPane.add(txtLivro);
		txtLivro.setColumns(10);
		
		tblLivro = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		tblLivro.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		        	cadLivro = new frmCadLivros(objBD);
					cadLivro.setVisible(true);
					
					JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(cadLivro);
			        if (currentFrame != null) {
			            currentFrame.dispose(); 
			        }
		            abrirTelaCadastro();
		        }
		    }
		});
		model.addColumn("ID");
	    model.addColumn("Titulo");
	    model.addColumn("Ano de Publicação");
	    model.addColumn("Editora");
	    tblLivro.setModel(model);
		tblLivro.setColumnSelectionAllowed(true);
		tblLivro.setBounds(10, 66, 520, 259);
		contentPane.add(tblLivro);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pesquisa = txtLivro.getText().trim(); 
		        mostrarLivros(pesquisa);
			}
		});
		btnPesquisar.setBounds(430, 35, 100, 23);
		contentPane.add(btnPesquisar);
		
		JButton btnVoltar = new JButton("Sair");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmmenu = new frmMenu();
				frmmenu.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnVoltar);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnVoltar.setBounds(398, 336, 100, 23);
		contentPane.add(btnVoltar);
		
		JLabel lblNome = new JLabel("Nome do Livro:");
		lblNome.setBounds(10, 38, 89, 14);
		contentPane.add(lblNome);
		
		JButton btnCad = new JButton("Cadastrar");
		btnCad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadLivro = new frmCadLivros(objBD);
				cadLivro.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnCad);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnCad.setBounds(32, 336, 118, 23);
		contentPane.add(btnCad);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int linhaSelecionada = tblLivro.getSelectedRow();
		        if (linhaSelecionada != -1) {
		            int id = (int) tblLivro.getValueAt(linhaSelecionada, 0); 
		            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este livro?", "Confirmação", JOptionPane.YES_NO_OPTION);
		            if (resposta == JOptionPane.YES_OPTION) {
		                try {
		                    LivrosDAO livrosDAO = new LivrosDAO();
		                    livrosDAO.Excluir(id);
		                    JOptionPane.showMessageDialog(null, "Livro excluído com sucesso!");
		                    mostrarLivros(""); 
		                } catch (SQLException | ClassNotFoundException ex) {
		                    JOptionPane.showMessageDialog(null, "Erro ao excluir livro: " + ex.getMessage());
		                }
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Selecione um livro para excluir.");
		        }
		    }
		});
		btnExcluir.setBounds(291, 336, 89, 23);
		contentPane.add(btnExcluir);
		
		JButton btnSele = new JButton("Editar");
		btnSele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirTelaCadastro(); 
			}
		});
		btnSele.setBounds(171, 336, 100, 23);
		contentPane.add(btnSele);
		
		mostrarLivros("");
	}
	
	private void Excluir(int id) throws SQLException, ClassNotFoundException {
	    LivrosDAO livroDAO = new LivrosDAO();
	    livroDAO.Excluir(id);
	}
	
	private void mostrarLivros(String pesquisa) {
	    LivrosDAO livrosDAO = new LivrosDAO();
	    try {
	    	List<Livros> livrosList;
	        if (pesquisa.isEmpty()) {
	        	livrosList = livrosDAO.listarLivros(); 
	        } else {
	        	livrosList = livrosDAO.pesquisarLivros(pesquisa); 
	        }
	        DefaultTableModel model = (DefaultTableModel) tblLivro.getModel();
	        model.setRowCount(0);

	        for (Livros livro : livrosList) {
	            model.addRow(new Object[]{livro.getId() ,livro.getTitulo(), livro.getAno_publicacao()});
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar livros: " + e.getMessage());
	    }
	}
	
	private void abrirTelaCadastro() {
	    int linhaSelecionada = tblLivro.getSelectedRow(); 
	    if (linhaSelecionada != -1) { 
	        int id = (int) tblLivro.getValueAt(linhaSelecionada, 0);
	        String titulo = (String) tblLivro.getValueAt(linhaSelecionada, 1); 
	        String ano = (String) tblLivro.getValueAt(linhaSelecionada, 2);
	        

	        frmCadLivros cadLivro = new frmCadLivros(objBD); 
	        cadLivro.setVisible(true); 

	        
	        cadLivro.txtTitulo.setText(titulo);
	        cadLivro.txtAno.setText(ano);
	        cadLivro.setId(id); 
	    } else {
	        JOptionPane.showMessageDialog(this, "Selecione um livro para editar."); 
	    }
	}
}
