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
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class ConsultaClientes extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	Label lblConsulta = new Label("Consulta Clientes");

	Button btnImprimir = new Button("Imprimir PDF");

	JTable tablaClientes = new JTable();

	String [] titulos= {};

	DefaultTableModel modelo = new DefaultTableModel();

	// Base de Datos
	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/farmaciapr2?autoReconnect=true&useSSL=false";
	static String login = "admin";
	static String password = "Studium2018;";
	static String sentencia = "SELECT dniCliente AS 'DNI', nombreCliente AS 'Nombre', apellidosCliente AS 'Apellidos' FROM clientes ORDER BY 1;";
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
		Image miIcono = mipantalla.getImage("farmacia.png");
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
			JOptionPane.showMessageDialog(null, "No se ha cargado el driver debido a que no está disponible", "Error", JOptionPane.ERROR_MESSAGE);
		}

		catch (SQLException sqle)
		{
			JOptionPane.showMessageDialog(null, "Se ha producido un problema al acceder a la Base de Datos o similar", "Error", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "No se puede cerrar la conexión con la BD", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(btnImprimir.equals(arg0.getSource())){
			try {
				Document documento = new Document(PageSize.LETTER);
				PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream("ConsultaClientes.pdf"));
				documento.setMargins(50f, 50f, 50f, 50f);
				documento.open();

				Paragraph titulo = new Paragraph("**Consulta Clientes**", FontFactory.getFont(FontFactory.TIMES_ROMAN,18, Font.BOLD, BaseColor.BLACK));
				titulo.setAlignment(Paragraph.ALIGN_CENTER);
				documento.add(titulo);

				Paragraph saltolinea1 = new Paragraph();
				saltolinea1.add("\n\n");

				documento.add(saltolinea1);

				PdfPTable pdfTable = new PdfPTable(tablaClientes.getColumnCount());

				for (int i = 0; i < tablaClientes.getColumnCount(); i++) {
					pdfTable.addCell(tablaClientes.getColumnName(i));
				}

				// Extraer filas y columnas de la tabla
				for (int rows = 0; rows < tablaClientes.getRowCount(); rows++) {
					for (int cols = 0; cols < tablaClientes.getColumnCount(); cols++) {
						pdfTable.addCell(tablaClientes.getModel().getValueAt(rows, cols).toString());
					}
				}
				// Añadir la tabla
				documento.add(pdfTable);

				class HeaderFooterPageEvent extends PdfPageEventHelper {

					public void onEndPage(PdfWriter writer, Document document) {
						ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("By: Manuel Jesús Ojeda Salvador 1-DAW"), 150, 30, 0);
						ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Página " + document.getPageNumber()), 550, 30, 0);
					}
				}

				// Llamar a la clase que escribe en el final
				HeaderFooterPageEvent event = new HeaderFooterPageEvent(); 
				writer.setPageEvent(event);
				// Cerramos el objeto
				documento.close();
				JOptionPane.showMessageDialog(null, "Se imprimió la tabla Clientes en PDF", "Consulta Exportada", JOptionPane.INFORMATION_MESSAGE);
			}catch (DocumentException e) {
				JOptionPane.showMessageDialog(null, "Se ha producido un problema con el documento", "Error", JOptionPane.ERROR_MESSAGE);
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
				e.printStackTrace();
			}
			this.setVisible(false);
			new MenuPrincipal();
		}
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}