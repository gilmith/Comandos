/**
 * @author xe02761 Jacobo Geada Ansino
 * Main de la clase que lanza comandos a un listado de maquinas pasadas en un cuadro de texto, separadas por 
 * intros
 * 
 */

package com.opermerc.comandos;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JSeparator;

import com.jcraft.jsch.JSchException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;


public class Ventana {

	private JFrame frmVentanaDeComandos;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JFileChooser comandos;
	private JScrollPane scroll;
	private LeerArchivo lectura;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana window = new Ventana();
					window.frmVentanaDeComandos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ventana() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. Contains, JLabel, JButton JScrollPane, JTextArea
	 * Inside the MouseActionListener execute the main loop with two threads.
	 */
	private void initialize() {
		frmVentanaDeComandos = new JFrame();
		frmVentanaDeComandos.setTitle("Ventana de comandos masivos");
		frmVentanaDeComandos.setBounds(100, 100, 450, 300);
		frmVentanaDeComandos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVentanaDeComandos.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblListadoDeMaquinas = new JLabel("Listado de maquinas a las que aplicar los comandos");
		frmVentanaDeComandos.getContentPane().add(lblListadoDeMaquinas);
		
		final JTextArea textArea = new JTextArea();
		textArea.setToolTipText("Mete la lista de las maquinas en las que va a ejecutar los comandos masivos");
		textArea.setLineWrap(true);
		textArea.setRows(8);
		scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frmVentanaDeComandos.getContentPane().add(scroll);
		
		JLabel lblTuClave = new JLabel("Tu clave");
		frmVentanaDeComandos.getContentPane().add(lblTuClave);
		passwordField = new JPasswordField();
		passwordField.setColumns(4);
		frmVentanaDeComandos.getContentPane().add(passwordField);
		
		JLabel lblClaveDeOper = new JLabel("Clave de oper");
		lblClaveDeOper.setVerticalAlignment(SwingConstants.TOP);
		frmVentanaDeComandos.getContentPane().add(lblClaveDeOper);
		passwordField_1 = new JPasswordField();
		passwordField_1.setColumns(4);
		frmVentanaDeComandos.getContentPane().add(passwordField_1);
		
		final JButton btnArchivoDeComandos = new JButton("Archivo de comandos");
		comandos = new JFileChooser();
		btnArchivoDeComandos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				comandos.showOpenDialog(btnArchivoDeComandos);
				File file =  comandos.getSelectedFile();
				lectura = new LeerArchivo(file); 
			}
		});
		
		JSeparator separator = new JSeparator();
		frmVentanaDeComandos.getContentPane().add(separator);	
		frmVentanaDeComandos.getContentPane().add(btnArchivoDeComandos);
		
		JButton btnEjecutar = new JButton("Ejecutar");
		//cambiar a ActionListener
		btnEjecutar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane jop = new JOptionPane();
				String[] listaMaquinas = textArea.getText().split("\\n");
				String userPass = String.valueOf(passwordField.getPassword());
				String operPass = String.valueOf(passwordField_1.getPassword());
				for(String l : listaMaquinas){
					try {
						Execution ex = new Execution();
						ex.exec(l, userPass, operPass, lectura.getHost());
						jop.showMessageDialog(frmVentanaDeComandos.getParent(), "FIN", "FINALIZADO", JOptionPane.INFORMATION_MESSAGE);
					} catch (InterruptedException | IOException | JSchException e1) {
						jop.showMessageDialog(frmVentanaDeComandos.getParent(), e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
						try {
							e1.printStackTrace(new PrintStream("C:\\" + System.getProperty("user.name") + "\\Gestion\\Log\\" + "logerror" + l + ".txt" ));
						} catch (FileNotFoundException e2) {
							e2.printStackTrace();
						}
						e1.printStackTrace();
					}
				}
			}
		});
	
			
		JButton btnSalir = new JButton("Salir");
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				System.exit(0);
			}
		});
		

		frmVentanaDeComandos.getContentPane().add(btnEjecutar);
		frmVentanaDeComandos.getContentPane().add(btnSalir);
	}
	
}
