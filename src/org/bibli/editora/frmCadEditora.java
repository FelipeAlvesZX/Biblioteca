package org.bibli.editora;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.bibli.bd;

import dao.EditoraDAO;
import models.Editoras;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class frmCadEditora extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextField txtNome;
	JTextField txtEnde;
	JTextField txtTele;
	private frmConsEditora consEdi;
	private int id;
	private static bd objBD;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmCadEditora frame = new frmCadEditora(objBD);
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
	public frmCadEditora(bd objBD) {
		setTitle("Cadastrar Editoras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome da Editora:");
		lblNome.setBounds(10, 30, 104, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(129, 27, 212, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblEnde = new JLabel("Endereço da Editora:");
		lblEnde.setBounds(10, 70, 125, 14);
		contentPane.add(lblEnde);
		
		txtEnde = new JTextField();
		txtEnde.setBounds(129, 67, 212, 20);
		contentPane.add(txtEnde);
		txtEnde.setColumns(10);
		
		JLabel lblTele = new JLabel("Telefone da Editora:");
		lblTele.setBounds(10, 111, 125, 14);
		contentPane.add(lblTele);
		
		txtTele = new JTextField();
		txtTele.setBounds(129, 108, 125, 20);
		contentPane.add(txtTele);
		txtTele.setColumns(10);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        EditoraDAO editoraDAO = new EditoraDAO();
		        Editoras editora = new Editoras(txtNome.getText(), txtEnde.getText(), txtTele.getText());
		        try {
		            editoraDAO.Inserir(editora);
		            JOptionPane.showMessageDialog(null, "Editora cadastrada com sucesso!");
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Erro ao cadastrar editora: " + ex.getMessage());
		        }
		    }
		});
		btnCadastrar.setBounds(23, 192, 104, 23);
		contentPane.add(btnCadastrar);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarEditora();
            }
        });
		btnAtualizar.setBounds(192, 192, 89, 23);
		contentPane.add(btnAtualizar);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consEdi = new frmConsEditora(objBD);
				consEdi.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnSair);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnSair.setBounds(335, 192, 89, 23);
		contentPane.add(btnSair);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	private void atualizarEditora() {
        EditoraDAO editoraDAO = new EditoraDAO();
        Editoras editora = new Editoras(txtNome.getText(), txtEnde.getText(), txtTele.getText());
        editora.setId(this.id);

        try {
            editoraDAO.Alterar(editora); 
            JOptionPane.showMessageDialog(null, "Editora atualizada com sucesso!");

            frmConsEditora consEdi = new frmConsEditora(objBD);
            consEdi.setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar editora: " + ex.getMessage());
        }
    }
}
