package ve.com.avss.citas.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;

import com.toedter.calendar.JCalendar;

import ve.com.avss.citas.BD.ejecutores.QueryCita;
import ve.com.avss.citas.bean.DatosCitas;
import ve.com.avss.clientes.bean.DatosClientesProveedor;
import ve.com.avss.empresa.BD.conectores.ConectorEmpresa;
import ve.com.avss.empresa.bean.DatosEmpresa;
import ve.com.avss.empresa.bean.DatosUsuario;
import ve.com.avss.ventas.util.UtilFormas;

public class DialogoCitasCliente extends AbstractDialogoBase {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DialogoCitasCliente.class);
	private List<DatosCitas> listaCitas = new ArrayList<DatosCitas>();

	private DatosUsuario datosUsuario = new DatosUsuario();
	private DatosClientesProveedor datosClienteProveedor = new DatosClientesProveedor();
	private DatosEmpresa datosEmpresa = new DatosEmpresa();
	
	private ConectorEmpresa conectorEmpresa = new ConectorEmpresa(); 
	
	private int rowSeleccionada = 0;
	private String actionCommand = "";
	
	public DialogoCitasCliente(JFrame parent, DatosClientesProveedor datosCliente,DatosUsuario datosUsuario) throws Exception { 
		super(parent);
		log.info("Constructor - construyendo paneles e interfaz");
		this.datosEmpresa = datosEmpresa; 
		this.datosUsuario = datosUsuario;
		this.datosClienteProveedor = datosCliente;
		contruirPanelesCampos();
		construirTablaCitas();
		cargarListaCitas();
		construirPanelBotones("Cerrar", "Cerrar");
		//establecer datos del cliente
		
		nombreCP.setText(datosCliente.getNombre());
		nombreCP.setEnabled(false);
		apellidoCP.setText(datosCliente.getApellido());
		apellidoCP.setEnabled(false);
		identificacionCP.setText(datosCliente.getIdentificacion());
		identificacionCP.setEnabled(false);
		telefonoCP.setText(datosCliente.getTelefono());
		telefonoCP.setEnabled(false);
		direccionCP.setText(datosCliente.getDireccion());
		direccionCP.setEnabled(false);
		comboTipoCP.setSelectedItem(datosCliente.getTipoClienteProveedor());
		comboTipoCP.setEnabled(false);
		
		/*Datos de la factura*/
		//datosFactura = conectorFactura.obtenerIdMaximoFactura();
//		comboTipoFactura.setSelectedItem("Compra");
//		comboTipoFactura.setEnabled(false);
//		jContentPaneDatosFactura.setLayout(new BoxLayout(jContentPaneDatosFactura, BoxLayout.Y_AXIS));
//		eNumeroFactura.setText("Factura N° "+datosFactura.getCodigo());
//		eFechaFactura.setText("Fecha "+obtenerFechaActual()); 
//		eOperadorFactura.setText("Atendido por: "+this.datosUsuario.getUsuario());
//		jContentPaneDatosFactura.add(eNumeroFactura);
//		jContentPaneDatosFactura.add(eFechaFactura);
//		jContentPaneDatosFactura.add(eOperadorFactura);
		/*datos de la empresa*/
		this.datosEmpresa = conectorEmpresa.obtenerDatosEmpresa();
		/* se agregan los datos del panel de informacion */
		int pos = 0;
		jPanelIdentificacion.add(eIdentificacionCP); 
		jPanelIdentificacion.add(identificacionCP);
		jPanelInformacion.add(jPanelIdentificacion,
				UtilFormas.setGridContraints(
						"gridx=" + pos + ",gridy=0,gridwidth=1," + "fill=" + GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(0, 0, 0, 0)));
		++pos;
		jPanelNombre.add(eNombreCP);
		jPanelNombre.add(nombreCP);
		jPanelInformacion.add(jPanelNombre,
				UtilFormas.setGridContraints(
						"gridx=" + pos + ",gridy=0,gridwidth=1," + "fill=" + GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(0, 0, 0, 0)));
		++pos;
		jPanelApellido.add(eApellidoCP);
		jPanelApellido.add(apellidoCP);
		jPanelInformacion.add(jPanelApellido,
				UtilFormas.setGridContraints(
						"gridx=" + pos + ",gridy=0,gridwidth=2," + "fill=" + GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(0, 0, 0, 0)));
		pos = 0;
		jPanelTelefono.add(eTelefonoCP);
		jPanelTelefono.add(telefonoCP);
		jPanelInformacion.add(jPanelTelefono,
				UtilFormas.setGridContraints(
						"gridx=" + pos + ",gridy=1,gridwidth=2," + "fill=" + GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(0, 0, 0, 0)));

		++pos;
		jPanelDireccion.add(eDireccionCP);
		jPanelDireccion.add(direccionCP);
		jPanelInformacion.add(jPanelDireccion,
				UtilFormas.setGridContraints(
						"gridx=" + pos + ",gridy=1,gridwidth=2," + "fill=" + GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(0, 0, 0, 0)));
		++pos;
		jPanelTipoClienteProveedor.add(eTipoCP);
		jPanelTipoClienteProveedor.add(comboTipoCP);
		jPanelInformacion.add(jPanelTipoClienteProveedor,
				UtilFormas.setGridContraints(
						"gridx=" + pos + ",gridy=1,gridwidth=2," + "fill=" + GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(0, 0, 0, 0)));

//		JPanel jPanelCombo = new JPanel();
//		jPanelCombo.add(comboTipoFactura);
		
		//jPanelBuscarInformacion.add(jPanelCombo);
		jPanelBuscarInformacion.add(btnGuardarCita);
		jPanelBuscarInformacion.add(btnProgramarCita);
		
		
		jPanelBuscarInformacion.setPreferredSize(new Dimension(200, 600));
		//jPanelBuscarInformacion.setBackground(Color.RED);
		btnGuardarCita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				log.info("guarda cita");
				
				if (tablaListaCitas.getSelectedRows().length == 0) {
					 JOptionPane.showMessageDialog(parent,"Debe seleccionar una cita","Alerta",JOptionPane.WARNING_MESSAGE); 
					return;
				}
				String idCita = tablaListaCitas.getValueAt(rowSeleccionada, 0).toString();
				String observacion = textAreaObservacion.getText();
			    String fecha = tablaListaCitas.getValueAt(rowSeleccionada, 1).toString();
//			    tablaListaCitas.setValueAt(textAreaObservacion.getText(),rowSeleccionada , 3);
				 
				//JOptionPane.showMessageDialog(parent,idCita+" "+fecha+" "+observacion,"Alerta",JOptionPane.WARNING_MESSAGE); 
				 
				try {
					DatosCitas datosCita = new DatosCitas();
					datosCita.setIdCita(idCita);
					datosCita.setObservacion(observacion);
					datosCita.setFecha(fecha);
					datosCita.setStatus("0");
					int status = QueryCita.actualizarCita(datosCita);
					if (status == 1) {
						
					}else {
						
					}
					cargarListaCitas();
					
				} catch (Exception e) {
					log.error("error "+e.getMessage());
				}
				log.info("guardar cita - fin");
			}
		});

		btnProgramarCita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				log.info("programar cita");
				
				 
				try {
				//JOptionPane.showMessageDialog(parent,"Logica para programar cita","Alerta",JOptionPane.WARNING_MESSAGE); 
				
				JPanel panel = new JPanel(new GridLayout(3, 1));

				JPanel panelFechaProx = new JPanel();
				panelFechaProx.add(eFechaProx);
				panel.add(panelFechaProx);
				JCalendar calendario = new JCalendar();
				calendario.setTodayButtonVisible(true);
				calendario.setTodayButtonText("Hoy Día");
				panel.add(calendario);

				JPanel panelObservacionProx = new JPanel();
				
				panelObservacionProx.add(eObservacionProx);
				panelObservacionProx.add(scrollPaneObservacionProx);
				panel.add(panelObservacionProx);

				

				int resp = JOptionPane.showConfirmDialog(parent, panel, "Programar cita", JOptionPane.YES_NO_OPTION);
				if (resp  == 1) {
					return;
				}
				Date date = calendario.getDate();
				java.sql.Date sDate = new java.sql.Date(date.getTime());
					
//					JOptionPane.showMessageDialog(parent,sDate
//							+" - "+calendario.getDate(),"Alerta",JOptionPane.WARNING_MESSAGE); 
					DatosCitas datosCita = new DatosCitas();
					datosCita.setFecha(sDate+"");
					datosCita.setObservacion(observacionProx.getText());
					datosCita.setIdCliente(datosClienteProveedor.getIdBD());
					datosCita.setIdUsuario(1+"");
					datosCita.setStatus("1");
					QueryCita.registrarCita(datosCita);
					
					cargarListaCitas();
				} catch (Exception e) {
					log.error("error "+e.getMessage());
				}
				log.info("programar cita - fin");
			}
		});
		
		jContentPaneSuperior.add(jPanelInformacion);
		jContentPaneSuperior.add(jContentPaneDatosFactura);
		//jPanelTablaCitas.setBackground(Color.GREEN);
		jPanelTablaCitas.add(tablaCitas,
				UtilFormas.setGridContraints("gridx=0,gridy=0,gridwidth=2," + "fill=" + GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(0, 0, 0, 0)));


		jContentPaneCentral.add(jPanelTablaCitas);
		
		
		
		textAreaObservacion = new JTextArea(10, 50);
		textAreaObservacion.setName("observacion");
		textAreaObservacion.setBackground(Color.WHITE);
		textAreaObservacion.setPreferredSize(new Dimension(200, 110));
		textAreaObservacion.setText("");
		textAreaObservacion.setEditable(true);
		textAreaObservacion.setAutoscrolls(true);
		textAreaObservacion.setFont(new Font("Courier New", Font.PLAIN, 17));
		scrollPaneObservacion = new JScrollPane(textAreaObservacion);
		scrollPaneObservacion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneObservacion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    jContentPaneCentral.add(scrollPaneObservacion);
		//jContentPaneCentral.setBackground(Color.BLUE);

		jContentPane.add(jContentPaneSuperior, BorderLayout.NORTH);
		jContentPane.add(jContentPaneCentral, BorderLayout.CENTER);
		jContentPane.add(botonesPanel, BorderLayout.SOUTH);
		jContentPane.add(jPanelBuscarInformacion, BorderLayout.EAST);

		JScrollPane jScrollPane = new JScrollPane();

		jScrollPane.setViewportView(jContentPane);

		getContentPane().add(jScrollPane);
		pack();
		setAlwaysOnTop(false);
		setLocationRelativeTo(null);
		setTitle("Citas de Clientes");
		setResizable(true);
		setVisible(true);
	}

	private void cargarListaCitas() {
		log.info("cargarListaCitas");
		limpiartabla();
		try {
			listaCitas = QueryCita.obtenerListaCitas(datosClienteProveedor.getIdentificacion());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < listaCitas.size(); i++) { 


			modelTablaCitas.addRow(new Object[] { listaCitas.get(i).getIdCita(),listaCitas.get(i).getFecha(), listaCitas.get(i).getUsuarioUsuario(),
					listaCitas.get(i).getObservacion() });
			

		}
		
		tablaListaCitas.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
	            if (me.getClickCount() == 1 ||me.getClickCount() == 2) {     // to detect doble click events
	            	log.info("seleccionando en lista de citas");
	            	
	               JTable target = (JTable)me.getSource();
	               int row = target.getSelectedRow(); // select a row
	               rowSeleccionada = row;
	              
	               String observacion = tablaListaCitas.getValueAt(row, 3).toString();
	               textAreaObservacion.setText(observacion);
	              okButton.setEnabled(false);
	               log.info("seleccionando en lista de citas - fin "+tablaListaCitas.getValueAt(row, 0).toString());
	              //JOptionPane.showMessageDialog(null,row+" "+column+" "+ table.getValueAt(row, column)); // get the value of a row and column.

	              
	            }
	         }
	      });
		log.info("cargarListaCitas - fin");
	}


	private void construirTablaCitas() {
		log.info("construirTablaCitas");
		// Column Names
		String[] nombreColumnaProductos = {"id", "Fecha", "Usuario", "Observación" };

		// inicializamos el JTable
		modelTablaCitas.setColumnIdentifiers(nombreColumnaProductos);
		tablaListaCitas = new JTable();
		tablaListaCitas.setModel(modelTablaCitas);
		//tablaListaCitas.setBounds(60, 60, 60, 60);
		/*ocualtamos la columna id*/
		TableColumn column = tablaListaCitas.getColumnModel().getColumn(0);
	    column.setMinWidth(0);
	    column.setMaxWidth(0);
	    column.setWidth(0);
	    column.setPreferredWidth(0);

		// agregamos al JScrollPane
		JScrollPane sp = new JScrollPane(tablaListaCitas);
		tablaCitas.add(sp);
		log.info("construirTablaCitas - fin");
	}

	


	private void contruirPanelesCampos() {

		String campo = "";

		/* armar campo identificacion */
		campo = "identificacionCP";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo nombre */
		campo = "nombreCP";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo apellido */
		campo = "apellidoCP";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo correo */
		campo = "correoCP";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo telefono */
		campo = "telefonoCP";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo usuario */
		campo = "direccionCP";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo tipo de cliente proveedor */
		campo = "tipoCP";
		armarEtiquetas(campo);
		armarCampos(campo);
		
		/* armar campo tipo factura */
		campo = "guardarCita";
		armarCampos(campo);
		/* armar campo tipo factura */
		campo = "programarCita";
		armarCampos(campo);
		/* armar campo tabla de productos */
		campo = "tablaProductos";
		armarEtiquetas(campo);
		
		/* armar campo fecha */
		campo = "fechaProx";
		armarEtiquetas(campo);
		armarCampos(campo);
		
		/* armar campo observacion */
		campo = "observacionProx";
		armarEtiquetas(campo);
		armarCampos(campo);


		boxlayoutBuscarInfomracion = new BoxLayout(jPanelBuscarInformacion, BoxLayout.Y_AXIS);
		jPanelBuscarInformacion.setLayout(boxlayoutBuscarInfomracion);
		//jPanelBuscarInformacion.setBorder(new EmptyBorder(new Insets(10, 5, 5, 10)));

	}
	

	
	
	
	@Override
	protected boolean actionOK(String actionCommandButton) {
		log.info("registrar");
		if (identificacionCP.getText().equals("")) {
			identificacionCP.setBackground(Color.CYAN);
			identificacionCP.requestFocusInWindow();
			return false;
		}
		if (comboTipoFactura.getSelectedItem().equals("")) {
			comboTipoFactura.setBackground(Color.CYAN);
			comboTipoFactura.setPopupVisible(true);
			return false;
		}
		
		if (tablaListaCitas.getRowCount() == 0) {
			JOptionPane.showMessageDialog(null, "Debe agregar al menos 1 producto","Mensaje de la aplicación", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		

		
		
		
		actionCommand = "registrar";
		log.info("registrar - fin");
		return true;
	}

	@Override
	protected boolean actionOK() {
		log.info("registrar");
		if (identificacionCP.getText().equals("")) {
			identificacionCP.setBackground(Color.CYAN);
			identificacionCP.requestFocusInWindow();
			return false;
		}
		if (comboTipoFactura.getSelectedItem().equals("")) {
			comboTipoFactura.setBackground(Color.CYAN);
			comboTipoFactura.setPopupVisible(true);
			return false;
		}

		
	
		
		actionCommand = "registrar";
		log.info("registrar - fin");
		return true;
	}
	
	private void limpiartabla(){
		DefaultTableModel temp = (DefaultTableModel) tablaListaCitas.getModel();
	    int filas = tablaListaCitas.getRowCount();

	    for (int a = 0; filas > a; a++) {
	        temp.removeRow(0);
	    }
	}
	
	
	

	public DatosUsuario getDatosUsuario() {
		return datosUsuario;
	}

	public void setDatosUsuario(DatosUsuario datosUsuario) {
		this.datosUsuario = datosUsuario;
	}

	public DatosEmpresa getDatosEmpresa() {
		return datosEmpresa;
	}




	public void setDatosEmpresa(DatosEmpresa datosEmpresa) {
		this.datosEmpresa = datosEmpresa;
	}

	public List<DatosCitas> getListaCitas() {
		return listaCitas;
	}

	public void setListaCitas(List<DatosCitas> listaCitas) {
		this.listaCitas = listaCitas;
	}

	public String getActionCommand() {
		return actionCommand;
	}

	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}

	

}
