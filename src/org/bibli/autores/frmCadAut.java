package org.bibli.autores;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.bibli.bd;
import dao.AutoresDAO;
import models.Autores;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;

public class frmCadAut extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextField txtNome;
	JTextField txtData;
	private frmConsAut consAut;
	public JButton btnCad;
	public JButton btnAtualiza;
	public Object btnExcluir;
	private int id;
	private static bd objBD;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmCadAut frame = new frmCadAut(objBD);
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
	public frmCadAut(bd objBD) {
		setTitle("Cadastrar Autor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 64, 46, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(137, 61, 201, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblData = new JLabel("Data de Nascimento:");
		lblData.setBounds(10, 107, 137, 14);
		contentPane.add(lblData);
		
		txtData = new JTextField();
		txtData.setBounds(138, 104, 86, 20);
		contentPane.add(txtData);
		txtData.setColumns(10);
		
		JButton btnCad = new JButton("Cadastrar");
		btnCad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AutoresDAO autoresDAO = new AutoresDAO();
				Autores autor = new Autores(txtNome.getText(), txtData.getText());
		        try {
		            autoresDAO.Inserir(autor);
		            JOptionPane.showMessageDialog(null, "Autor cadastrado com sucesso!");
		            txtNome.setText("");
		            txtData.setText("");
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Erro ao cadastrar Autor: " + ex.getMessage());
		        }
			}
		});
		btnCad.setBounds(23, 177, 98, 23);
		contentPane.add(btnCad);
		
		JButton btnAtualiza = new JButton("Atualizar");
		btnAtualiza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarAutor();
			}
		});
		btnAtualiza.setBounds(174, 177, 89, 23);
		contentPane.add(btnAtualiza);
		
		
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consAut = new frmConsAut(objBD);
				consAut.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnSair);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnSair.setBounds(329, 177, 89, 23);
		contentPane.add(btnSair);
		
		JLabel lblMens = new JLabel("Autor Desejado");
		lblMens.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMens.setHorizontalAlignment(SwingConstants.CENTER);
		lblMens.setBounds(127, 11, 164, 14);
		contentPane.add(lblMens);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	 private void atualizarAutor() {
	        AutoresDAO autoresDAO = new AutoresDAO();
	        Autores autor = new Autores(txtNome.getText(), txtData.getText());
	        autor.setId(this.id); 

	        try {
	            autoresDAO.Alterar(autor);
	            JOptionPane.showMessageDialog(null, "Autor atualizado com sucesso!");

	            frmConsAut consAut = new frmConsAut(objBD);
	            consAut.setVisible(true);
	            this.dispose();
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(null, "Erro ao atualizar autor: " + ex.getMessage());
	        }
	    }
	
}
