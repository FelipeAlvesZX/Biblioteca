package org.bibli.livros;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.bibli.bd;
import org.bibli.editora.frmCadEditora;

import dao.EditoraDAO;
import dao.LivrosDAO;
import models.Editoras;
import models.Livros;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;

public class frmCadLivros extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextField txtTitulo;
	JTextField txtAno;
	private frmConsLivro consLivro;
	private frmCadEditora cadEdi;
	public JComponent btnCad;
	public JComponent btnAtualiza;
	public JComboBox<Editoras> JCombBoxEditora;
	private int id;
	private static bd objBD;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmCadLivros frame = new frmCadLivros(objBD);
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
	public frmCadLivros(bd objBD) {
		setTitle("Cadastrar Livros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 253);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Titulo Do Livro:");
		lblTitulo.setBounds(10, 40, 85, 14);
		contentPane.add(lblTitulo);
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(138, 37, 181, 20);
		contentPane.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		JLabel lblAnoPubli = new JLabel("Ano De Publicação: ");
		lblAnoPubli.setBounds(10, 71, 117, 14);
		contentPane.add(lblAnoPubli);
		
		txtAno = new JTextField();
		txtAno.setBounds(138, 68, 86, 20);
		contentPane.add(txtAno);
		txtAno.setColumns(10);
		
		JLabel lblCadLivro = new JLabel("Cadastro um novo livro");
		lblCadLivro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCadLivro.setBounds(115, 11, 159, 14);
		contentPane.add(lblCadLivro);
		
		JLabel lblEditora = new JLabel("Editora:");
		lblEditora.setBounds(10, 109, 85, 14);
		contentPane.add(lblEditora);
		
		JComboBox<Editoras> JCombBoxEditora = new JComboBox<Editoras>();
		EditoraDAO editoraDAO = new EditoraDAO();
	    try {
	        List<Editoras> editorasList = editoraDAO.listarEditoras(); 
	        for (Editoras editora : editorasList) {
	            JCombBoxEditora.addItem(editora); 
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(null, "Erro ao carregar editoras: " + e.getMessage());
	    }
		JCombBoxEditora.setBounds(138, 105, 86, 22);
		contentPane.add(JCombBoxEditora);
		
		JButton btnCadEditora = new JButton("Cadastrar Editora");
		btnCadEditora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadEdi = new frmCadEditora(objBD);
				cadEdi.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnCadEditora);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnCadEditora.setBounds(251, 105, 137, 23);
		contentPane.add(btnCadEditora);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consLivro = new frmConsLivro(objBD);
				consLivro.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnSair);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnSair.setBounds(329, 179, 89, 23);
		contentPane.add(btnSair);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consLivro = new frmConsLivro(objBD);
				consLivro.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnConsultar);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnConsultar.setBounds(329, 36, 89, 23);
		contentPane.add(btnConsultar);
		
		JButton btnAtualiza = new JButton("Atualizar");
		btnAtualiza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarLivro();
            }
        });
		btnAtualiza.setBounds(187, 179, 89, 23);
		contentPane.add(btnAtualiza);
		
		JButton btnCad = new JButton("Cadastrar");
		btnCad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LivrosDAO livrosDAO = new LivrosDAO();
				Editoras editoraSelecionada = (Editoras) JCombBoxEditora.getSelectedItem();
		        int editoraId = editoraSelecionada.getId(); 
		        
		        Livros livro = new Livros(txtTitulo.getText(), Integer.parseInt(txtAno.getText()), editoraId);
		        try {
		            livrosDAO.Inserir(livro);
		            JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!");
		            txtTitulo.setText("");
		            txtAno.setText("");
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Erro ao cadastrar livro: " + ex.getMessage());
		        }
				
			}
		});
		btnCad.setBounds(10, 179, 96, 23);
		contentPane.add(btnCad);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	private void atualizarLivro() {
        LivrosDAO livrosDAO = new LivrosDAO();
        Editoras editoraSelecionada = (Editoras) JCombBoxEditora.getSelectedItem();
        int editoraId = editoraSelecionada.getId();

        Livros livro = new Livros(txtTitulo.getText(), Integer.parseInt(txtAno.getText()), editoraId);
        livro.setId(this.id); 

        try {
            livrosDAO.Alterar(livro); 
            JOptionPane.showMessageDialog(null, "Livro atualizado com sucesso!");

            frmConsLivro consLivro = new frmConsLivro(objBD);
            consLivro.setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar livro: " + ex.getMessage());
        }
	}
}
