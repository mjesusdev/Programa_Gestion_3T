package es.studium.programagestion;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
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
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class ConsultaEmpleados extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	Label lblConsulta = new Label("Consulta Empleados");

	Button btnImprimir = new Button("Imprimir PDF");

	JTable tablaEmpleados = new JTable();

	String [] titulos= {};

	DefaultTableModel modelo = new DefaultTableModel();

	// Base de Datos
	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/farmaciapr2?autoReconnect=true&useSSL=false";
	static String login = "admin";
	static String password = "Studium2018;";
	static String sentencia = "SELECT idEmpleado AS 'N� Empleado', nombreEmpleado AS 'Nombre', apellidosEmpleado AS 'Apellidos' FROM empleados;";
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet rs = null;

	ConsultaEmpleados()
	{
		setTitle("Consulta Empleados");
		colocarIcono();
		setLayout(new FlowLayout());
		add(lblConsulta);
		tablaEmpleados = new JTable(rellenarTabla(),titulos);
		tablaEmpleados.setModel(modelo);
		tablaEmpleados.setEnabled(false);
		add(new JScrollPane(tablaEmpleados), BorderLayout.CENTER);
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
				// A�adir los t�tulos de las columnas
				modelo.addColumn(rsMd.getColumnLabel(i));
			}

			while (rs.next()) {
				Object [] fila = new Object[cantidadColumnas];
				for (int i=0;i<cantidadColumnas;i++) {
					// Coger los objetos de la bd	
					fila[i] = rs.getObject(i+1);
				}
				// A�adir las columnas
				modelo.addRow(fila);
			}
		}

		catch (ClassNotFoundException cnfe)
		{
			JOptionPane.showMessageDialog(null, "No se ha cargado el driver debido a que no est� disponible", "Error", JOptionPane.ERROR_MESSAGE);
		}

		catch (SQLException sqle)
		{
			JOptionPane.showMessageDialog(null, "Se ha producido un problema al acceder a la Base de Datos o similar", "Error", JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "No se puede cerrar la conexi�n con la BD", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(btnImprimir.equals(arg0.getSource())){
			try {
				Document documento = new Document(PageSize.LETTER);
				PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream("ConsultaEmpleados.pdf"));
				documento.setMargins(50f, 50f, 50f, 50f);
				documento.open();

				Paragraph titulo = new Paragraph("**Consulta Empleados**", FontFactory.getFont(FontFactory.TIMES_ROMAN,18, Font.BOLD, BaseColor.BLACK));
				titulo.setAlignment(Paragraph.ALIGN_CENTER);
				documento.add(titulo);

				Paragraph saltolinea1 = new Paragraph();
				saltolinea1.add("\n\n");

				documento.add(saltolinea1);

				PdfPTable pdfTable = new PdfPTable(tablaEmpleados.getColumnCount());

				for (int i = 0; i < tablaEmpleados.getColumnCount(); i++) {
					pdfTable.addCell(tablaEmpleados.getColumnName(i));
				}

				// Extraer filas y columnas de la tabla
				for (int rows = 0; rows < tablaEmpleados.getRowCount(); rows++) {
					for (int cols = 0; cols < tablaEmpleados.getColumnCount(); cols++) {
						pdfTable.addCell(tablaEmpleados.getModel().getValueAt(rows, cols).toString());
					}
				}
				// A�adir la tabla
				documento.add(pdfTable);

				class HeaderFooterPageEvent extends PdfPageEventHelper {

					public void onEndPage(PdfWriter writer, Document document) {
						ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("By: Manuel Jes�s Ojeda Salvador 1-DAW"), 150, 30, 0);
						ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("P�gina " + document.getPageNumber()), 550, 30, 0);
					}
				}

				// Llamar a la clase que escribe en el final
				HeaderFooterPageEvent event = new HeaderFooterPageEvent(); 
				writer.setPageEvent(event);

				// Cerramos el objeto
				documento.close();

				JOptionPane.showMessageDialog(null, "Se imprimi� la tabla Empleados en PDF", "Consulta Exportada", JOptionPane.INFORMATION_MESSAGE);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.setVisible(false);
			new MenuPrincipal();
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