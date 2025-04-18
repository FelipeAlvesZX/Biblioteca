package org.bibli.emprestimo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.bibli.bd;
import org.bibli.livros.frmCadLivros;

import dao.EmprestimosDAO;
import dao.LivrosDAO;
import dao.MembrosDAO;
import models.Emprestimos;
import models.Livros;
import models.Membros;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;

public class frmCadEmprestimo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNoeLivro;
	JTextField txtDtEmpr;
	JTextField txtDtDev;
	private frmConsEmprestimo consEmp;
	private frmCadLivros cadLivro;
	public JComponent btnCad;
	public JComponent btnAtualiza;
	public JComboBox<Membros> cbxNome;
	public JComboBox<Livros> cbxlivro;
	private int idEmprestimo;
	private int id;
	private static bd objBD;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmCadEmprestimo frame = new frmCadEmprestimo(objBD);
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
	public frmCadEmprestimo(bd objBD) {
		setTitle("Cadastrar Emprestimos de Livros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 278);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome do Cliente:");
		lblNome.setBounds(10, 29, 112, 14);
		contentPane.add(lblNome);
		
		JComboBox<Membros> cbxNome = new JComboBox<Membros>();
		MembrosDAO membrosDAO = new MembrosDAO();
	    try {
	        List<Membros> listMemb = membrosDAO.listarMembros(); 

	        for (Membros membros : listMemb) {
	            cbxNome.addItem(membros); 
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(null, "Erro ao carregar Membros: " + e.getMessage());
	    }
		cbxNome.setBounds(132, 25, 127, 22);
		contentPane.add(cbxNome);
		
		lblNoeLivro = new JLabel("Nome do Livro:");
		lblNoeLivro.setBounds(10, 69, 112, 14);
		contentPane.add(lblNoeLivro);
		
		JComboBox<Livros> cbxlivro = new JComboBox<Livros>();
		LivrosDAO livrosDAO = new LivrosDAO();
	    try {
	        List<Livros> livrosList = livrosDAO.listarLivros(); 

	        for (Livros livro : livrosList) {
	            cbxlivro.addItem(livro); 
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(null, "Erro ao carregar livros: " + e.getMessage());
	    }
		cbxlivro.setBounds(132, 65, 129, 22);
		contentPane.add(cbxlivro);
		
		JButton btnCadClient = new JButton("Cadastrar");
		btnCadClient.setBounds(274, 25, 104, 23);
		contentPane.add(btnCadClient);
		
		JButton btnCadLivro = new JButton("Cadastrar");
		btnCadLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadLivro = new frmCadLivros(objBD);
				cadLivro.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnCadLivro);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnCadLivro.setBounds(274, 65, 104, 23);
		contentPane.add(btnCadLivro);
		
		txtDtEmpr = new JTextField();
		txtDtEmpr.setBounds(132, 111, 104, 20);
		contentPane.add(txtDtEmpr);
		txtDtEmpr.setColumns(10);
		
		JLabel lblDtEmp = new JLabel("Data do Emprestimo:");
		lblDtEmp.setBounds(10, 114, 129, 14);
		contentPane.add(lblDtEmp);
		
		JLabel lblDevo = new JLabel("Data da Devolução:");
		lblDevo.setBounds(10, 147, 129, 14);
		contentPane.add(lblDevo);
		
		txtDtDev = new JTextField();
		txtDtDev.setBounds(132, 144, 86, 20);
		contentPane.add(txtDtDev);
		txtDtDev.setColumns(10);
		
		JButton btnCad = new JButton("Cadastrar");
		btnCad.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        EmprestimosDAO emprestimosDAO = new EmprestimosDAO();

		        Membros memSelecionado = (Membros) cbxNome.getSelectedItem();
		        int membroId = memSelecionado.getId();

		        Livros livroSelecionado = (Livros) cbxlivro.getSelectedItem();
		        int livrosId = livroSelecionado.getId();

		        Emprestimos emprestimo = new Emprestimos(membroId, livrosId, txtDtEmpr.getText(), txtDtDev.getText().isEmpty() ? null : txtDtDev.getText());

		        try {
		            emprestimosDAO.Inserir(emprestimo);
		            JOptionPane.showMessageDialog(null, "Empréstimo cadastrado com sucesso!");
		            txtDtEmpr.setText("");
		            txtDtDev.setText("");
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Erro ao cadastrar empréstimo: " + ex.getMessage());
		        }
		    }
		});
		btnCad.setBounds(10, 193, 98, 23);
		contentPane.add(btnCad);
		
		JButton btnAtualiza = new JButton("Atualizar");
		btnAtualiza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarEmprestimo();
            }
        });
		btnAtualiza.setBounds(182, 193, 89, 23);
		contentPane.add(btnAtualiza);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consEmp = new frmConsEmprestimo(objBD);
				consEmp.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnSair);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnSair.setBounds(331, 193, 89, 23);
		contentPane.add(btnSair);
	}
	
	public void setId(int id) {
        this.id = id;
    }
	
	private void atualizarEmprestimo() {
	    if (cbxNome.getSelectedItem() == null || cbxlivro.getSelectedItem() == null || txtDtEmpr.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos obrigatórios.");
	        return;
	    }

	    EmprestimosDAO emprestimosDAO = new EmprestimosDAO();
	    Emprestimos emprestimo = new Emprestimos(
	        ((Membros) cbxNome.getSelectedItem()).getId(),
	        ((Livros) cbxlivro.getSelectedItem()).getId(),
	        txtDtEmpr.getText(),
	        txtDtDev.getText().isEmpty() ? null : txtDtDev.getText()
	    );
	    emprestimo.setId(this.id);

	    try {  
	        emprestimosDAO.Alterar(emprestimo);
	        JOptionPane.showMessageDialog(null, "Empréstimo atualizado com sucesso!");
 
	        this.dispose();
	        frmConsEmprestimo consEmp = new frmConsEmprestimo(objBD);
	        consEmp.setVisible(true);
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(null, "Erro ao atualizar empréstimo: " + ex.getMessage());
	    } catch (ClassNotFoundException ex) {
	        JOptionPane.showMessageDialog(null, "Erro de classe não encontrada: " + ex.getMessage());
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
	    }
	}

}
