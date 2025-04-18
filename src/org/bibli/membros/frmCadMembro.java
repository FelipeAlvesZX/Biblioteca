package org.bibli.membros;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.bibli.bd;

import dao.MembrosDAO;
import models.Membros;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class frmCadMembro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNome;
	JTextField txtNome;
	JTextField txtEmail;
	private JLabel lblEmail;
	JTextField txtTelefone;
	JTextField txtData;
	private frmConsMembro consMem;
	public Object btnCad;
	public Object btnAtualiza;
	private int id;
	private static bd objBD;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmCadMembro frame = new frmCadMembro(objBD);
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
	public frmCadMembro(bd objBD) {
		setTitle("Cadastrar Membros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 22, 46, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(117, 19, 198, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(117, 58, 198, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 61, 46, 14);
		contentPane.add(lblEmail);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(10, 95, 57, 14);
		contentPane.add(lblTelefone);
		
		txtTelefone = new JTextField();
		txtTelefone.setBounds(117, 92, 113, 20);
		contentPane.add(txtTelefone);
		txtTelefone.setColumns(10);
		
		JLabel lblDtCad = new JLabel("Data De Cadastro:");
		lblDtCad.setBounds(10, 133, 113, 14);
		contentPane.add(lblDtCad);
		
		txtData = new JTextField();
		txtData.setBounds(117, 130, 86, 20);
		contentPane.add(txtData);
		txtData.setColumns(10);
		
		JButton btnCad = new JButton("Cadastrar");
		btnCad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MembrosDAO membrosDAO = new MembrosDAO();
		        Membros membro = new Membros(txtNome.getText(), txtEmail.getText(), txtTelefone.getText(), txtData.getText());
		        try {
		            membrosDAO.Inserir(membro);
		            JOptionPane.showMessageDialog(null, "Membro cadastrado com sucesso!");
		            txtNome.setText("");
		            txtEmail.setText("");
		            txtTelefone.setText("");
		            txtData.setText("");
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Erro ao cadastrar membro: " + ex.getMessage());
		        }
			}
		});
		btnCad.setBounds(10, 179, 113, 23);
		contentPane.add(btnCad);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consMem = new frmConsMembro(objBD);
				consMem.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnSair);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnSair.setBounds(335, 179, 89, 23);
		contentPane.add(btnSair);
		
		JButton btnAtualiza = new JButton("Atualizar");
		btnAtualiza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarMembro();
            }
        });
		btnAtualiza.setBounds(163, 179, 89, 23);
		contentPane.add(btnAtualiza);
	}

	public void setId(int id) {
		this.id = id;
	}
	
	 private void atualizarMembro() {
	        MembrosDAO membrosDAO = new MembrosDAO();
	        Membros membro = new Membros(txtNome.getText(), txtEmail.getText(), txtTelefone.getText(), txtData.getText());
	        membro.setId(this.id); 

	        try {
	            membrosDAO.Alterar(membro);
	            JOptionPane.showMessageDialog(null, "Membro atualizado com sucesso!");

	            frmConsMembro consMem = new frmConsMembro(objBD);
	            consMem.setVisible(true);
	            this.dispose();
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(null, "Erro ao atualizar membro: " + ex.getMessage());
	        }
	    }
}
