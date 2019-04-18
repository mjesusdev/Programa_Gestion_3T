package es.studium.programagestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ModificacionClientes extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;
	
	// Crear componentes
	static Choice seleccionarCliente = new Choice();
	Button btnMod = new Button("Modificación");
	
	// Panel para el Choice y el botón
	Panel pnlChoice = new Panel();
	Panel pnlBoton = new Panel();
	
	// Crear diálogo
	Dialog DialogoMod = new Dialog(this, true);
	
	// Crear componentes
	Label lblNombre = new Label("Nombre:");
	TextField txt = new TextField();
	Label lblApellidos = new Label("Apellidos");
	TextField txtApellidos = new TextField();
	Label lblDNI = new Label("DNI:");
	TextField txtDNI = new TextField();
	Button btnModificacion = new Button("Modificar Cliente");
	Button btnLimpiar = new Button("Limpiar");
	
	// Base De datos
	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/farmaciapr2?autoReconnect=true&useSSL=false";
	static String login = "admin";
	static String password = "Studium2018;";
	static String sentencia = null;
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet rs = null;
	
	ModificacionClientes()
	{
		// Título e icono
		setTitle("Modificación Clientes");
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		setIconImage(miIcono);
		
		// Añadir elementos
		pnlChoice.add(seleccionarCliente);
		pnlBoton.add(btnMod);
		seleccionarCliente.add("Seleccione cliente a modificar");
		add(pnlChoice, "North");
		add(pnlBoton, "Center");
		
		// Llamar al método que selecciona los Clientes
		introducirClientes();
		
		// Añadir Windowlistener
		addWindowListener(this);
		// Añadir un Actionlistener al botón de Modificación 
		btnMod.addActionListener(this);
		btnLimpiar.addActionListener(this);
		btnModificacion.addActionListener(this);
		
		// Diálogo donde se realiza la modificación
		DialogoMod.setTitle("Modificación Clientes");
		DialogoMod.setLayout(new GridLayout(4,2));
		DialogoMod.add(lblNombre);
		DialogoMod.add(txt);
		DialogoMod.add(lblApellidos);
		DialogoMod.add(txtApellidos);
		DialogoMod.add(lblDNI);
		DialogoMod.add(txtDNI);
		DialogoMod.add(btnModificacion);
		DialogoMod.add(btnLimpiar);
		DialogoMod.setSize(300,250);
		DialogoMod.setResizable(false);
		DialogoMod.setLocationRelativeTo(null);
		DialogoMod.addWindowListener(this);
		DialogoMod.setVisible(false);
		
		setSize(300,150);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	public static void introducirClientes() {
		sentencia = "SELECT * FROM clientes;";

		try{
			Class.forName(driver);				
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);
			while (rs.next()) {
				String nombreCliente = rs.getString("nombreCliente");
				String apellidosCliente = rs.getString("apellidosCliente");
				String dniCliente = rs.getString("dniCliente");
				seleccionarCliente.add(nombreCliente + " " + apellidosCliente + " " + "(" + dniCliente + ")");
			}
		} 

		catch (ClassNotFoundException e) {
			System.out.println("Se produjo un error al cargar el Driver");
		}

		catch(SQLException e) {
			System.out.println("Se produjo un error al conectar con la BD");
		}

		try {
			if (connection!=null) {
				connection.close();
			}	
		}catch(SQLException sqle) {
			System.out.println("No se puede cerrar la conexión con la Base de Datos");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String Nombre = txt.getText();
		String Apellidos = txtApellidos.getText();
		String DNI = txtDNI.getText();
		
		if (btnModificacion.equals(ae.getSource())) {
			// Si el contenido de los tres TEXT FIELD están vacíos que lanze la ventana de ALTA INCORRECTA
			if (Nombre.equals("") |  (Apellidos.equals("")) | (DNI.equals(""))){
				JOptionPane.showMessageDialog(null, "Tienes que introducir todos los datos.", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(null, "Modificación Correcta", "Éxito en la Modificación", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (btnLimpiar.equals(ae.getSource())) {
			// Seleccionar Nombre y limpiarlo
			txt.selectAll();
			txt.setText("");
			
			// Seleccionar Los Apellidos y limpiarlo
			txtApellidos.selectAll();
			txtApellidos.setText("");
			
			// Seleccionar DNI y Limpiarlo
			txtDNI.selectAll();
			txtDNI.setText("");
		}
		
		if (btnMod.equals(ae.getSource())) {
			this.setVisible(false);
			DialogoMod.setVisible(true);
		}
		
		/*
		if (btnAceptar.equals(ae.getSource())) {
			Guardar_Movimientos f = new Guardar_Movimientos();
			try {
				f.registrar("admin]" + "[Modificación Clientes");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		*/
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		if (this.isActive()) {
			setVisible(false);
			new MenuPrincipal(null);
		}
		else if(DialogoMod.isActive()) {
			DialogoMod.setVisible(false);
			new ModificacionClientes();
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}
}