package es.studium.programagestion;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ConsultaClientes extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	Label lblConsulta = new Label("Consulta Clientes");

	Button btnImprimir = new Button("Imprimir");

	JTable tablaClientes = new JTable();

	String [] titulos= {};

	DefaultTableModel modelo = new DefaultTableModel();

	// Base de Datos
	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/farmaciapr2?autoReconnect=true&useSSL=false";
	static String login = "admin";
	static String password = "Studium2018;";
	static String sentencia = "SELECT dniCliente AS 'DNI', nombreCliente AS 'Nombre', apellidosCliente AS 'Apellidos'\r\n" + 
			"FROM clientes ORDER BY 1;";
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet rs = null;

	ConsultaClientes()
	{
		setTitle("Consulta Clientes");
		// Llamar al método que coloca el icono a la ventana
		colocarIcono();
		setLayout(new FlowLayout());
		add(lblConsulta);
		tablaClientes = new JTable(rellenarTabla(),titulos);
		tablaClientes.setModel(modelo);
		// No editar la tabla
		tablaClientes.setEnabled(false);
		add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
		add(btnImprimir);
		setSize(500,600);
		setLocationRelativeTo(null);
		addWindowListener(this);
		btnImprimir.addActionListener(this);
		setResizable(false);
		setVisible(true);
	}

	public void colocarIcono() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		setIconImage(miIcono);
	}

	public Object[][] rellenarTabla() {
		try 
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();						
			rs = statement.executeQuery(sentencia);

			ResultSetMetaData rsMd = rs.getMetaData();
			// Guardar en una variable las columnas que hay
			int cantidadColumnas = rsMd.getColumnCount();

			// Bucle para ir de 1 hasta las columnas que existen
			for (int i=1;i<=cantidadColumnas;i++) {
				// Añadir los títulos de las columnas
				modelo.addColumn(rsMd.getColumnLabel(i));
			}

			while (rs.next()) {
				Object [] fila = new Object[cantidadColumnas];
				for (int i=0;i<cantidadColumnas;i++) {
					// Coger los objetos de la bd	
					fila[i] = rs.getObject(i+1);
				}
				// Añadir las columnas
				modelo.addRow(fila);
			}
		}

		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1: "+cnfe.getMessage());
		}

		catch (SQLException sqle)
		{
			System.out.println("Error 2: "+sqle.getMessage());
		}
		desconectar();
		return null;
	}
	
	public static void desconectar() {
		try
		{
			if(connection!=null)
			{
				connection.close();
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error 3: "+e.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(btnImprimir.equals(arg0.getSource())){
			try {
				String prueba = btnImprimir.getLabel();
				System.out.println(prueba);
				//Se obtiene el objeto PrintJob
				PrintJob pjob = this.getToolkit().getPrintJob(this, prueba, null);
				//Se obtiene el objeto graphics sobre el que pintar
				Graphics pg = pjob.getGraphics();
				//Se fija la fuente de caracteres con la que escribir
				pg.setFont(new Font("Arial",Font.PLAIN,12));
				//Se escribe el mensaje del Cuadro de Texto indicando
				//posición con respecto a la parte superior izquierda
				pg.drawString(prueba,100,100);
				//Se finaliza la página
				pg.dispose();
				//Se hace que la impresora termine el trabajo y suelte la página
				pjob.end();
			}catch(NullPointerException npe) {
				npe.printStackTrace();
			}
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if(this.isActive()){
			this.setVisible(false);
			new MenuPrincipal(null);
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