package org.bibli;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.bibli.autores.frmConsAut;
import org.bibli.editora.frmConsEditora;
import org.bibli.emprestimo.frmConsEmprestimo;
import org.bibli.livros.frmConsLivro;
import org.bibli.membros.frmConsMembro;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class frmMenu extends JFrame{

	private frmConsLivro consLivro;
	private frmConsMembro consMemb;
	private frmConsAut consAut;
	private frmConsEditora consEdi;
	private frmConsEmprestimo consEmp;
	private bd objBD;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmMenu window = new frmMenu();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public frmMenu() {
		initialize();
		this.objBD = new bd("biblioteca", "root", "root");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setTitle("Alpha Biblioteca");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblMensa = new JLabel("Bem-vindo a Alpha Biblioteca");
		lblMensa.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMensa.setBounds(107, 23, 226, 14);
		getContentPane().add(lblMensa);
		
		JButton btnConsLivro = new JButton("Consultar Livros");
		btnConsLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consLivro = new frmConsLivro(objBD);
				consLivro.setVisible(true);
			
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnConsLivro);
				if (currentFrame != null) {
					currentFrame.dispose();
				}
			}
		});
		btnConsLivro.setBounds(23, 87, 142, 23);
		getContentPane().add(btnConsLivro);
		
		JButton btnConsMembro = new JButton("Consultar Membros");
		btnConsMembro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consMemb = new frmConsMembro(objBD);
				consMemb.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnConsMembro);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnConsMembro.setBounds(276, 87, 148, 23);
		getContentPane().add(btnConsMembro);
		
		JButton btnConsEmpres = new JButton("Consultar emprestismo");
		btnConsEmpres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consEmp = new frmConsEmprestimo(objBD);
				consEmp.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnConsEmpres);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnConsEmpres.setBounds(42, 134, 171, 23);
		getContentPane().add(btnConsEmpres);
		
		JLabel lblDeseja = new JLabel("O que deseja Fazer?");
		lblDeseja.setBounds(153, 39, 132, 14);
		getContentPane().add(lblDeseja);
		
		JButton btnConsEditoras = new JButton("Consultar Editoras");
		btnConsEditoras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consEdi = new frmConsEditora(objBD);
				consEdi.setVisible(true);
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnConsEditoras);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnConsEditoras.setBounds(153, 168, 142, 23);
		getContentPane().add(btnConsEditoras);
		
		JButton btnConsAutor = new JButton("Consultar Autores");
		btnConsAutor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consAut = new frmConsAut(objBD);
				consAut.setVisible(true);
				
				
				JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnConsAutor);
		        if (currentFrame != null) {
		            currentFrame.dispose(); 
		        }
			}
		});
		btnConsAutor.setBounds(252, 134, 155, 23);
		getContentPane().add(btnConsAutor);
	}
	
	
}
