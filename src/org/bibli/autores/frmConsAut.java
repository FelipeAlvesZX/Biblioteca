package org.bibli.autores;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.bibli.bd;
import org.bibli.frmMenu;

import dao.AutoresDAO;
import models.Autores;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;

public class frmConsAut extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtAutor;
	private JTable tblAut;
	private JButton btnPesq;
	private frmMenu frmmenu;
	private frmCadAut cadAut;
	private static bd objBD;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmConsAut frame = new frmConsAut(objBD);
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
	public frmConsAut(bd objBD) {
		
		setTitle("Consultar Autores");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 478, 331);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 11, 46, 14);
		contentPane.add(lblNome);
		
		txtAutor = new JTextField();
		txtAutor.setBounds(53, 8, 268, 20);
		contentPane.add(txtAutor);
		txtAutor.setColumns(10);
		
		tblAut = new JTable();
		tblAut.setColumnSelectionAllowed(true);
		DefaultTableModel model = new DefaultTableModel();
		tblAut.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		        	cadAut = new frmCadAut(objBD);
					cadAut.setVisible(true);
					
					JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(cadAut);
			        if (currentFrame != null) {
			            currentFrame.dispose(); 
			        }
		            abrirTelaCadastro();
		        }
		    }
		});
		model.addColumn("ID");
	    model.addColumn("Nome");
	    model.addColumn("Data de Nascimento");
	    tblAut.setModel(model);
		tblAut.setBounds(10, 36, 442, 210);
		contentPane.add(tblAut);
		
		btnPesq = new JButton("Pesquisar");
		btnPesq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pesquisa = txtAutor.getText().trim(); 
		        mostrarAutores(pesquisa);
			}
		});
		btnPesq.setBounds(331, 7, 121, 23);
		contentPane.add(btnPesq);
		
		JButton btnCad = new JButton("Cadastrar");
		btnCad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadAut = new frmCadAut(objBD);
				cadAut.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnCad);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnCad.setBounds(10, 258, 97, 23);
		contentPane.add(btnCad);
		
		JButton btnSair = new JButton("Sair");
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
		btnSair.setBounds(363, 258, 89, 23);
		contentPane.add(btnSair);
		
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int linhaSelecionada = tblAut.getSelectedRow();
		        if (linhaSelecionada != -1) {
		            int id = (int) tblAut.getValueAt(linhaSelecionada, 0); 
		            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este autor?", "Confirmação", JOptionPane.YES_NO_OPTION);
		            if (resposta == JOptionPane.YES_OPTION) {
		                try {
		                    Excluir(id);
		                    JOptionPane.showMessageDialog(null, "Autor excluído com sucesso!");
		                    mostrarAutores(""); 
		                } catch (SQLException | ClassNotFoundException ex) {
		                    JOptionPane.showMessageDialog(null, "Erro ao excluir autor: " + ex.getMessage());
		                }
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Selecione um autor para excluir.");
		        }
		    }
		});
		btnExcluir.setBounds(244, 258, 89, 23);
		contentPane.add(btnExcluir);
		
		JButton btnSele = new JButton("Editar");
		btnSele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirTelaCadastro();
			}
		});
		btnSele.setBounds(117, 258, 104, 23);
		contentPane.add(btnSele);
		
		mostrarAutores("");
		
	}
	
	private void Excluir(int id) throws SQLException, ClassNotFoundException {
	    AutoresDAO autoresDAO = new AutoresDAO();
	    autoresDAO.Excluir(id);
	}

	private void mostrarAutores(String pesquisa) {
	    AutoresDAO autoresDAO = new AutoresDAO();
	    try {
	    	List<Autores> autoresList;
	        if (pesquisa.isEmpty()) {
	            autoresList = autoresDAO.listarAutores(); 
	        } else {
	            autoresList = autoresDAO.pesquisarAutores(pesquisa); 
	        }
	        DefaultTableModel model = (DefaultTableModel) tblAut.getModel();
	        model.setRowCount(0);

	        for (Autores autor : autoresList) {
	            model.addRow(new Object[]{autor.getId(), autor.getNome(), autor.getData_nascimento()});
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar autores: " + e.getMessage());
	    }
	}
	
	private void abrirTelaCadastro() {
	    int linhaSelecionada = tblAut.getSelectedRow();
	    if (linhaSelecionada != -1) {
	        String nomeAut = (String) tblAut.getValueAt(linhaSelecionada, 1);
	        String dataNascimento = (String) tblAut.getValueAt(linhaSelecionada, 2);
	        int id = (int) tblAut.getValueAt(linhaSelecionada, 0); 

	        frmCadAut cadAut = new frmCadAut(objBD);
	        cadAut.setVisible(true);

	        cadAut.txtNome.setText(nomeAut);
	        cadAut.txtData.setText(dataNascimento);
	        cadAut.setId(id); 
	    }
	}
	
}
