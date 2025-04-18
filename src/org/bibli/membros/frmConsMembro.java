package org.bibli.membros;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.bibli.bd;
import org.bibli.frmMenu;


import dao.MembrosDAO;
import models.Membros;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;

public class frmConsMembro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblMembro;
	private JTextField txtPesqMembro;
	private frmMenu frmmenu;
	private frmCadMembro cadMem;
	private static bd objBD;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmConsMembro frame = new frmConsMembro(objBD);
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
	public frmConsMembro(bd objBD) {
		setTitle("Consultar Membros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 556, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblConsMenbr = new JLabel("Membros Cadastrados");
		lblConsMenbr.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsMenbr.setBounds(195, 10, 163, 14);
		contentPane.add(lblConsMenbr);
		
		tblMembro = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		tblMembro.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		        	cadMem = new frmCadMembro(objBD);
		        	cadMem.setVisible(true);
					
					JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(cadMem);
			        if (currentFrame != null) {
			            currentFrame.dispose(); 
			        }
		            abrirTelaCadastro();
		        }
		    }
		});
		model.addColumn("ID");
	    model.addColumn("Nome");
	    model.addColumn("Email");
	    model.addColumn("Telefone");
	    model.addColumn("Data de Cadastro");
	    tblMembro.setModel(model);
		tblMembro.setColumnSelectionAllowed(true);
		tblMembro.setBounds(10, 66, 520, 259);
		contentPane.add(tblMembro);
		
		txtPesqMembro = new JTextField();
		txtPesqMembro.setBounds(86, 35, 297, 20);
		contentPane.add(txtPesqMembro);
		txtPesqMembro.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 38, 46, 14);
		contentPane.add(lblNome);
		
		JButton btnPesq = new JButton("Pesquisar");
		btnPesq.setBounds(393, 34, 118, 23);
		contentPane.add(btnPesq);
		
		JButton btnCad = new JButton("Cadastrar");
		btnCad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadMem = new frmCadMembro(objBD);
				cadMem.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnCad);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnCad.setBounds(26, 336, 111, 23);
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
		btnSair.setBounds(417, 336, 94, 23);
		contentPane.add(btnSair);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int linhaSelecionada = tblMembro.getSelectedRow();
		        if (linhaSelecionada != -1) {
		            int id = (int) tblMembro.getValueAt(linhaSelecionada, 0); 
		            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este Membro?", "Confirmação", JOptionPane.YES_NO_OPTION);
		            if (resposta == JOptionPane.YES_OPTION) {
		                try {
		                    Excluir(id);
		                    JOptionPane.showMessageDialog(null, "Membro excluído com sucesso!");
		                    mostrarMembros(""); 
		                } catch (SQLException | ClassNotFoundException ex) {
		                    JOptionPane.showMessageDialog(null, "Erro ao excluir membro: " + ex.getMessage());
		                }
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Selecione um membro para excluir.");
		        }
		    }
		});
		btnExcluir.setBounds(304, 336, 89, 23);
		contentPane.add(btnExcluir);
		
		JButton btnSele = new JButton("Selecionar");
		btnSele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirTelaCadastro();
			}
		});
		btnSele.setBounds(164, 336, 111, 23);
		contentPane.add(btnSele);
		
		mostrarMembros("");
	}
	private void Excluir(int id) throws SQLException, ClassNotFoundException {
	    MembrosDAO MembrosDAO = new MembrosDAO();
	    MembrosDAO.Excluir(id);
	}
	
	private void mostrarMembros(String pesquisa) {
	    MembrosDAO membrosDAO = new MembrosDAO();
	    try {
	    	List<Membros> listMemb;
	        if (pesquisa.isEmpty()) {
	        	listMemb = membrosDAO.listarMembros(); 
	        } else {
	        	listMemb = membrosDAO.pesquisarMembros(pesquisa); 
	        }
	        DefaultTableModel model = (DefaultTableModel) tblMembro.getModel();
	        model.setRowCount(0);

	        for (Membros membro : listMemb) {
	            model.addRow(new Object[]{membro.getId(), membro.getNome(), membro.getEmail(), membro.getTelefone() ,membro.getData_cadastro() });
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar autores: " + e.getMessage());
	    }
	}
	private void abrirTelaCadastro() {
		int linhaSelecionada = tblMembro.getSelectedRow();
	    if (linhaSelecionada != -1) {
	    	int id = (int) tblMembro.getValueAt(linhaSelecionada, 0); 
	        String nomeMem = (String) tblMembro.getValueAt(linhaSelecionada, 1);
	        String Email = (String) tblMembro.getValueAt(linhaSelecionada, 2);
	        String Telefone = (String) tblMembro.getValueAt(linhaSelecionada, 3);
	        String Data = (String) tblMembro.getValueAt(linhaSelecionada, 4);


	        frmCadMembro cadMem = new frmCadMembro(objBD);
	        cadMem.setVisible(true);
	        
	        cadMem.txtNome.setText(nomeMem);
	        cadMem.txtEmail.setText(Email);
	        cadMem.txtTelefone.setText(Telefone);
	        cadMem.txtData.setText(Data);
	        cadMem.setId(id); 
	    }
	}
}
