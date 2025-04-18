package org.bibli.editora;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.bibli.bd;
import org.bibli.frmMenu;
import org.bibli.livros.frmCadLivros;

import dao.EditoraDAO;
import dao.LivrosDAO;
import models.Editoras;

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

public class frmConsEditora extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEdiNome;
	private JTable tblEditora;
	private frmMenu frmmenu;
	private frmCadEditora cadEdi;
	private int id;
	private static bd objBD;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmConsEditora frame = new frmConsEditora(objBD);
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
	public frmConsEditora(bd objBD) {
		setTitle("Consultar Editora");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 558, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome da Editora:");
		lblNome.setBounds(10, 21, 145, 14);
		contentPane.add(lblNome);
		
		txtEdiNome = new JTextField();
		txtEdiNome.setBounds(114, 18, 275, 20);
		contentPane.add(txtEdiNome);
		txtEdiNome.setColumns(10);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pesquisa = txtEdiNome.getText().trim(); 
		        mostrarEditoras(pesquisa);
			}
		});
		btnPesquisar.setBounds(399, 17, 133, 23);
		contentPane.add(btnPesquisar);
		
		tblEditora = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		tblEditora.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		        	cadEdi = new frmCadEditora(objBD);
					cadEdi.setVisible(true);
					
					JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(cadEdi);
			        if (currentFrame != null) {
			            currentFrame.dispose(); 
			        }
		            abrirTelaCadastro();
		        }
		    }
		});
		model.addColumn("ID");
	    model.addColumn("Nome");
	    model.addColumn("Endereço");
	    model.addColumn("Telefone");
	    tblEditora.setModel(model);
		tblEditora.setBounds(10, 46, 522, 245);
		contentPane.add(tblEditora);
		
		JButton btnCad = new JButton("Cadastrar");
		btnCad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadEdi = new frmCadEditora(objBD);
				cadEdi.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnCad);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnCad.setBounds(10, 302, 125, 23);
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
		btnSair.setBounds(443, 302, 89, 23);
		contentPane.add(btnSair);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int linhaSelecionada = tblEditora.getSelectedRow();
		        if (linhaSelecionada != -1) {
		            int id = (int) tblEditora.getValueAt(linhaSelecionada, 0); 
		            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esta editora?", "Confirmação", JOptionPane.YES_NO_OPTION);
		            if (resposta == JOptionPane.YES_OPTION) {
		                try {
		                    EditoraDAO editoraDAO = new EditoraDAO();
		                    editoraDAO.Excluir(id);
		                    JOptionPane.showMessageDialog(null, "Editora excluída com sucesso!");
		                    mostrarEditoras(""); 
		                } catch (SQLException | ClassNotFoundException ex) {
		                    JOptionPane.showMessageDialog(null, "Erro ao excluir editora: " + ex.getMessage());
		                }
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Selecione um editora para excluir.");
		        }
		    }
		});
		btnExcluir.setBounds(301, 302, 97, 23);
		contentPane.add(btnExcluir);
		
		JButton btnSele = new JButton("Selecionar");
		btnSele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirTelaCadastro();
			}
		});
		btnSele.setBounds(157, 302, 125, 23);
		contentPane.add(btnSele);
		
		mostrarEditoras("");
	}
	
	private void Excluir(int id) throws SQLException, ClassNotFoundException {
	    EditoraDAO editoraDAO = new EditoraDAO();
	    editoraDAO.Excluir(id);
	}
	
	private void mostrarEditoras(String pesquisa) {
	    EditoraDAO editoraDAO = new EditoraDAO();
	    try {
	    	List<Editoras> editorasList;
	        if (pesquisa.isEmpty()) {
	        	editorasList = editoraDAO.listarEditoras(); 
	        } else {
	        	editorasList = editoraDAO.pesquisarEditoras(pesquisa); 
	        }
	        DefaultTableModel model = (DefaultTableModel) tblEditora.getModel();
	        model.setRowCount(0);

	        for (Editoras editora : editorasList) {
	            model.addRow(new Object[]{editora.getId() ,editora.getNome(), editora.getEndereco(), editora.getTelefone()});
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar Editora: " + e.getMessage());
	    }
	}
	
	
	private void abrirTelaCadastro() {
		int linhaSelecionada = tblEditora.getSelectedRow();
	    if (linhaSelecionada != -1) {
	        String nome = (String)tblEditora.getValueAt(linhaSelecionada, 1);
	        String End = (String) tblEditora.getValueAt(linhaSelecionada, 2);
	        String tele = (String) tblEditora.getValueAt(linhaSelecionada, 3);
	        int id = (int) tblEditora.getValueAt(linhaSelecionada, 0); 

	        frmCadEditora cadEdi = new frmCadEditora(objBD);
	        cadEdi.setVisible(true);

	        cadEdi.txtNome.setText(nome);
	        cadEdi.txtEnde.setText(End);
	        cadEdi.txtTele.setText(tele);
	        cadEdi.setId(id);
	    }
	}
	
	
}
