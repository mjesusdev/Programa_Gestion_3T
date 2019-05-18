package es.studium.programagestion;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ConsultaProductos extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	Label lblConsulta = new Label("Consulta Productos");

	Button btnImprimir = new Button("Imprimir PDF");

	JTable tablaProductos = new JTable();

	String [] titulos= {};

	DefaultTableModel modelo = new DefaultTableModel();

	// Base de Datos
	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/farmaciapr2?autoReconnect=true&useSSL=false";
	static String login = "admin";
	static String password = "Studium2018;";
	static String sentencia = "SELECT idProducto AS 'Nº', contenidototalProducto AS 'Contenido', nombreProducto AS 'Nombre',\r\n" + 
			"marcaProducto AS 'Marca', CONCAT(precioProducto, '€') AS 'Precio', DATE_FORMAT(fechacaducidadProducto,'%d/%m/%Y') AS 'Fecha de Caducidad' FROM productos;";
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet rs = null;

	ConsultaProductos()
	{
		setTitle("Consulta Productos");
		// Llamar al método que coloca el icono a la ventana
		colocarIcono();
		setLayout(new FlowLayout());
		add(lblConsulta);
		tablaProductos = new JTable(rellenarTabla(),titulos);
		tablaProductos.setModel(modelo);
		add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
		add(btnImprimir);
		setSize(510,590);
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

		finally
		{
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
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(btnImprimir.equals(arg0.getSource())){
			try {
				Document documento = new Document(PageSize.LETTER);
				PdfWriter.getInstance(documento, new FileOutputStream("ConsultaProductos.pdf"));
				documento.setMargins(50f, 50f, 50f, 50f);
				documento.open();

				Paragraph titulo = new Paragraph("**Consulta Productos**", FontFactory.getFont(FontFactory.TIMES_ROMAN,18, Font.BOLD, 
						BaseColor.BLACK));
				titulo.setAlignment(Paragraph.ALIGN_CENTER);
				documento.add(titulo);

				Paragraph saltolinea1 = new Paragraph();
				saltolinea1.add("\n\n");

				documento.add(saltolinea1);

				PdfPTable pdfTable = new PdfPTable(tablaProductos.getColumnCount());

				// Añadir nombre de las columnas
				for (int i = 0; i < tablaProductos.getColumnCount(); i++) {
					pdfTable.addCell(tablaProductos.getColumnName(i));
				}

				// Extraer filas y columnas de la tabla
				for (int rows = 0; rows < tablaProductos.getRowCount(); rows++) {
					for (int cols = 0; cols < tablaProductos.getColumnCount(); cols++) {
						pdfTable.addCell(tablaProductos.getModel().getValueAt(rows, cols).toString());
					}
				}
				// Añadir la tabla
				documento.add(pdfTable);

				Paragraph saltolinea2 = new Paragraph();
				saltolinea2.add("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				documento.add(saltolinea2);

				Paragraph author = new Paragraph("By: Manuel Jesús Ojeda Salvador 1-DAW");
				author.setAlignment(Paragraph.ALIGN_CENTER);
				documento.add(author);

				// Cerramos el objeto
				documento.close();
				
				JOptionPane.showMessageDialog(null, "Se imprimió la tabla Productos en PDF", "Consulta Exportada", JOptionPane.INFORMATION_MESSAGE);
			}catch (DocumentException e) {
				e.printStackTrace();
			}catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Problemas con el Fichero, puede ser que este abierto por otro programa o algo por el estilo", "Error Fatal", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if(this.isActive()){
			Guardar_Movimientos gm = new Guardar_Movimientos();
			try {
				gm.registrar("administrador]" + "["+sentencia+"");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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