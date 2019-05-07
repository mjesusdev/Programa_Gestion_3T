package es.studium.programagestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
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
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ModificacionEmpleados extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	// Crear componentes
	Choice seleccionarEmpleado = new Choice();
	Button btnModificacion = new Button("Modificación");

	// Panel para el Choice y el botón
	Panel pnlChoice = new Panel();
	Panel pnlBoton = new Panel();

	// Crear diálogo
	Dialog DialogoMod = new Dialog(this, true);

	// Crear componentes
	Label lblNombre = new Label("Nombre:");
	TextField txtNombre = new TextField();
	Label lblApellidos = new Label("Apellidos");
	TextField txtApellidos = new TextField();
	Button btnRealizarModificacion = new Button("Modificación Empleados");
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

	ModificacionEmpleados()
	{
		setTitle("Modificación Empleados");
		colocarIcono();

		pnlChoice.add(seleccionarEmpleado);
		pnlBoton.add(btnModificacion);
		seleccionarEmpleado.add("Seleccione empleado a modificar");
		// Llamar al método que añade los empleados
		introducirEmpleados(seleccionarEmpleado);
		add(pnlChoice, "North");
		add(pnlBoton, "Center");	
		// Añadir Listeners
		addWindowListener(this);
		btnModificacion.addActionListener(this);

		// Diálogo donde se realiza la modificación
		DialogoMod.setTitle("Modificación Empleados");
		DialogoMod.setLayout(new GridLayout(4,2));
		DialogoMod.add(lblNombre);
		DialogoMod.add(txtNombre);
		DialogoMod.add(lblApellidos);
		DialogoMod.add(txtApellidos);
		DialogoMod.add(btnRealizarModificacion);
		DialogoMod.add(btnLimpiar);
		DialogoMod.setSize(300,250);
		DialogoMod.setResizable(false);
		DialogoMod.setLocationRelativeTo(null);
		DialogoMod.addWindowListener(this);
		btnRealizarModificacion.addActionListener(this);
		btnLimpiar.addActionListener(this);
		DialogoMod.setVisible(false);

		setSize(300,100);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public void colocarIcono() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		setIconImage(miIcono);
	}

	public static void introducirEmpleados(Choice seleccionarEmpleado) {
		sentencia = "SELECT * FROM empleados;";

		try{
			Class.forName(driver);				
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);
			while (rs.next()) {
				int idEmpleado = rs.getInt("idEmpleado");
				String nombreEmpleado = rs.getString("nombreEmpleado");
				String apellidosEmpleado = rs.getString("apellidosEmpleado");
				seleccionarEmpleado.add(idEmpleado + " " + nombreEmpleado + " " + apellidosEmpleado);
			}
		} 

		catch (ClassNotFoundException e) {
			System.out.println("Se produjo un error al cargar el Driver");
		}

		catch(SQLException e) {
			System.out.println("Se produjo un error al conectar con la BD");
		}

		// Llamar al método cierra la conexión con la BD
		desconectar();
	}

	public static void desconectar() {
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
		if (btnModificacion.equals(ae.getSource())) {
			this.setVisible(false);
			DialogoMod.setVisible(true);
		}

		else if (btnRealizarModificacion.equals(ae.getSource())) {
			String [] escogeridEmpleado = seleccionarEmpleado.getSelectedItem().split(" ");
			String idEmpleado = escogeridEmpleado[0];

			try{
				Class.forName(driver);				
				connection = DriverManager.getConnection(url, login, password);
				statement = connection.createStatement();
				sentencia = "DELETE * FROM empleados WHERE idEmpleado = '"+idEmpleado+"';";
				System.out.println(sentencia);
				statement.executeUpdate(sentencia);
			} 

			catch (ClassNotFoundException e) {
				System.out.println("Se produjo un error al cargar el Driver");
			}

			catch(SQLException e) {
				System.out.println("Se produjo un error al conectar con la BD");
			}

			desconectar();
			JOptionPane.showMessageDialog(null, "Modificación Correcta", "Éxito en la Modificación", JOptionPane.INFORMATION_MESSAGE);
		}

		if (btnLimpiar.equals(ae.getSource())) {
			// Seleccionar Nombre y limpiarlo
			txtNombre.selectAll();
			txtNombre.setText("");

			// Seleccionar Los Apellidos y limpiarlo
			txtApellidos.selectAll();
			txtApellidos.setText("");
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if (this.isActive()) {
			setVisible(false);
			new MenuPrincipal(null);
		}
		else if(DialogoMod.isActive()) {
			DialogoMod.setVisible(false);
			new ModificacionEmpleados();
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