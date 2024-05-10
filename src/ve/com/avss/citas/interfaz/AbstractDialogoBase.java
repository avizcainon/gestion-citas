package ve.com.avss.citas.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import ve.com.avss.clientes.BD.ejecutores.QueryClienteProveedor;
import ve.com.avss.clientes.bean.DatosClientesProveedor;
import ve.com.avss.clientes.interfaz.DialogoClienteProveedor;
import ve.com.avss.ventas.bean.DatosConfiguracion;
import ve.com.avss.ventas.util.AppJButton;
import ve.com.avss.ventas.util.CONSTANTES_INTERACCION;
import ve.com.avss.ventas.util.Util;
import ve.com.avss.ventas.util.UtilFormas;

public abstract class AbstractDialogoBase extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AbstractDialogoBase.class);
	/** Command string para la accion de "Cancelar". */
	public static final String CMD_CANCEL = "cmd.cancel";
	/** Command string para la accion de "Aceptar". */
	public static final String CMD_OK = "cmd.ok";
	/** Command string para la accion de "Aceptar". */
	public static final String CMD_NOTA_ENTREGA = "cmd.notaEntrega";
	/** Command string para la accion de "Ocultar procesando". */
	public static final String CMD_PROCESS = "cmd.process";
	/** JFrame base del dialogo */
	protected JFrame parent = null;
	/** Indicador de acción. */ 
	protected boolean buttonFlag;
	protected boolean closeWindow = false;
	/** Botón aceptar. */
	protected AppJButton okButton = null;
	/** Botón cancelar. */
	protected JButton cancelButton = new JButton();
	/** Botón nota de entrega de factura. */
	protected JButton notaEntregaButton = new JButton();
	/*datos para la interfaz*/
	protected JPanel jContentPane = new JPanel(new BorderLayout());
	protected JPanel jContentPaneSuperior = new JPanel();
	protected JPanel jContentPaneCentral = new JPanel(new FlowLayout());
	protected JPanel jContentPaneInferior = new JPanel(new GridBagLayout());
	
	
	/** Panel de Campos. */
	protected JPanel camposPanel = new JPanel();
	/** Panel de ayuda. */
	protected JLabel ayudaPanel = new JLabel();
	/** Panel de Botones. */
	protected JPanel botonesPanel = new JPanel();
	
	/** datos de tipo de pago */
	protected JPanel jPanelTipoPago = new JPanel();
	protected JLabel eFechaProx = new JLabel();
	protected JTextField fechaProx = null;
	
	protected JLabel eObservacionProx = new JLabel();
	protected JTextArea observacionProx = null;
	protected JScrollPane scrollPaneObservacionProx= null;
	
	/*datos clientes proveedor*/
	protected JPanel jPanelInformacion = new JPanel(new GridLayout(2,3));
	protected JPanel jPanelIdentificacion = new JPanel();
	protected JPanel jPanelNombre = new JPanel();
	protected JPanel jPanelApellido = new JPanel();
	protected JPanel jPanelTelefono = new JPanel();
	protected JPanel jPanelDireccion = new JPanel();
	protected JPanel jPanelTipoClienteProveedor = new JPanel();
	
	protected JLabel eIdentificacionCP = new JLabel();
	protected JTextField identificacionCP = null;
	protected JLabel eNombreCP = new JLabel();
	protected JTextField nombreCP = null;
	protected JLabel eApellidoCP = new JLabel();
	protected JTextField apellidoCP = null;
	protected JLabel eCorreoCP = new JLabel();
	protected JTextField correoCP = null;
	protected JLabel eTelefonoCP = new JLabel();
	protected JTextField telefonoCP = null;
	protected JLabel eDireccionCP = new JLabel();
	protected JTextField direccionCP = null;
	protected JLabel eTipoCP = new JLabel();
	protected JComboBox comboTipoCP = new JComboBox();
	
	
	/*Datos de productos*/
	protected JLabel eTablaCitas= new JLabel();
	protected JPanel jPanelTablaCitas = new JPanel(new FlowLayout());
	protected JPanel tablaCitas = new JPanel();
	protected DefaultTableModel modelTablaCitas = new DefaultTableModel();
	protected JTable tablaListaCitas = null;
	
	 
	/*datos buscar informacion*/
	protected JPanel jPanelBuscarInformacion= new JPanel();
	protected AppJButton btnGuardarCita = null;
	protected AppJButton btnProgramarCita = null;

	protected JTextArea textAreaObservacion = null;  
	protected JScrollPane scrollPaneObservacion= null;

	protected JComboBox comboTipoFactura = new JComboBox();
	protected BoxLayout boxlayoutBuscarInfomracion = null; 
	/*Datos de factura*/
	protected JLabel eNumeroFactura = new JLabel();
	protected JLabel eFechaFactura = new JLabel();
	protected JPanel jContentPaneDatosFactura = new JPanel();
	protected JLabel eOperadorFactura = new JLabel();
	
	 protected Map<String, String> listaTipoPagoId = new HashMap<String, String>();
	 protected Map<String, String> listaTipoFacturaId = new HashMap<String, String>();
	 protected JLabel eNombreFormulario = new JLabel();
	public AbstractDialogoBase(JFrame parent) {
		super(parent, true);
		log.info("Constructor");
		setIconImage(Util.getIconImage());
		try {
			this.parent = parent;

		} catch (Exception e) {
			return;
		}
		


		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				windowAction(CMD_CANCEL);
			}
		});
	}
	
	protected void armarCampos(String campo) {

		switch (campo) {
		case "fechaProx":
			
			fechaProx = new JTextField();
			fechaProx.setName("fechaProx");
			fechaProx.setBackground(Color.WHITE);
			fechaProx.setPreferredSize(new Dimension(110, 25));
			fechaProx.setText("");
			

			break;
			case "observacionProx":
			
			observacionProx = new JTextArea(10, 50);
			observacionProx.setName("fechaProx");
			observacionProx.setBackground(Color.WHITE);
			observacionProx.setPreferredSize(new Dimension(200, 110));
			observacionProx.setText("");
			observacionProx.setEditable(true);
			observacionProx.setFont(new Font("Courier New", Font.PLAIN, 17));
			scrollPaneObservacionProx = new JScrollPane(observacionProx);
			//scrollPaneObservacion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPaneObservacionProx.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			

			break;
		case "identificacionCP":
			identificacionCP = new JTextField();
			identificacionCP.setName("identificacion");
			identificacionCP.setBackground(Color.WHITE);
			identificacionCP.setPreferredSize(new Dimension(110, 25));
			identificacionCP.setOpaque(true);
			identificacionCP.setText("");

			break;
		case "nombreCP":
			nombreCP = new JTextField();
			nombreCP.setName("nombreCP");
			nombreCP.setBackground(Color.WHITE);
			nombreCP.setPreferredSize(new Dimension(110, 25));
			nombreCP.setOpaque(true);
			nombreCP.setText("");
			break;
		case "apellidoCP":
			apellidoCP = new JTextField();
			apellidoCP.setName("apellidoCP");
			apellidoCP.setBackground(Color.WHITE);
			apellidoCP.setPreferredSize(new Dimension(110, 25));
			apellidoCP.setOpaque(true);
			apellidoCP.setText("");
			break;
		case "telefonoCP":
			telefonoCP = new JTextField();
			telefonoCP.setName("telefonoCP");
			telefonoCP.setBackground(Color.WHITE);
			telefonoCP.setPreferredSize(new Dimension(110, 25));
			telefonoCP.setOpaque(true);
			telefonoCP.setText("");
			break;
		case "direccionCP":
			direccionCP = new JTextField();
			direccionCP.setName("direccionCP");
			direccionCP.setBackground(Color.WHITE);
			direccionCP.setPreferredSize(new Dimension(110, 25));
			direccionCP.setOpaque(true);
			direccionCP.setText("");
			break;
		case "tipoCP":
			comboTipoCP.setName("tipoCP");
			comboTipoCP.setBackground(Color.WHITE);
			comboTipoCP.setPreferredSize(new Dimension(100, 28));
			((JComboBox) comboTipoCP).addItem("");
			((JComboBox) comboTipoCP).addItem("Cliente");
			((JComboBox) comboTipoCP).addItem("Proveedor");
			break;
		
		case "guardarCita":
			btnGuardarCita = new AppJButton("primary", "subproceso");
			btnGuardarCita = btnGuardarCita.crearBoton();
			btnGuardarCita.setName("buscarDatosCliente");
			btnGuardarCita.setPreferredSize(new Dimension(CONSTANTES_INTERACCION.ANCHO_BOTON, CONSTANTES_INTERACCION.ALTO_BOTON));
			btnGuardarCita.setOpaque(true);
			btnGuardarCita.setText("Guardar cita");
			btnGuardarCita.setIcon(DatosConfiguracion.iBuscarClienteProveedor);
			btnGuardarCita.setHorizontalTextPosition(SwingConstants.CENTER);
			btnGuardarCita.setVerticalTextPosition(SwingConstants.BOTTOM);
			break;
		
		case "programarCita":
			btnProgramarCita = new AppJButton("primary", "subproceso");
			btnProgramarCita = btnGuardarCita.crearBoton();
			btnProgramarCita.setName("buscarDatosCliente");
			btnProgramarCita.setPreferredSize(new Dimension(CONSTANTES_INTERACCION.ANCHO_BOTON, CONSTANTES_INTERACCION.ALTO_BOTON));
			btnProgramarCita.setOpaque(true);
			btnProgramarCita.setText("Programar cita");
			btnProgramarCita.setIcon(DatosConfiguracion.iConfiguracion);
			btnProgramarCita.setHorizontalTextPosition(SwingConstants.CENTER);
			btnProgramarCita.setVerticalTextPosition(SwingConstants.BOTTOM);
			break;
		default:
			break;
		}
	}

	protected void armarEtiquetas(String campo) {

		switch (campo) {
		case "fechaProx":
			eFechaProx.setPreferredSize(new java.awt.Dimension(250, 15));
			eFechaProx.setText("Fecha próximo contacto");
			break;
		case "observacionProx":
			eObservacionProx.setPreferredSize(new java.awt.Dimension(250, 15));
			eObservacionProx.setText("Observacion próximo contacto");
			break;
		case "identificacionCP":
			eIdentificacionCP.setPreferredSize(new java.awt.Dimension(200, 15));
			eIdentificacionCP.setText("Identificación: ");
			break;
		case "nombreCP":
			eNombreCP.setPreferredSize(new java.awt.Dimension(200, 15));
			eNombreCP.setText("Nombre: ");
			break;
		case "apellidoCP":
			eApellidoCP.setPreferredSize(new java.awt.Dimension(200, 15));
			eApellidoCP.setText("Apellido: ");
			break;
		case "telefonoCP":
			eTelefonoCP.setPreferredSize(new java.awt.Dimension(200, 15));
			eTelefonoCP.setText("Teléfono: ");
			break;
		case "direccionCP":
			eDireccionCP.setPreferredSize(new java.awt.Dimension(200, 15));
			eDireccionCP.setText("Dirección: ");
			break;
		case "tipoCP":
			eTipoCP.setPreferredSize(new java.awt.Dimension(70, 15));
			eTipoCP.setText("Tipo");
			break;
		case "tablaProductos":
			eTablaCitas.setPreferredSize(new java.awt.Dimension(140, 15));
			eTablaCitas.setText("Productos");
			break;
		

		
		}

	}
	
	protected DatosClientesProveedor buscarRegistrarClienteProveedor(String identificacion) throws Exception {
		DatosClientesProveedor datosClienteProveedor = null;
		try {
			datosClienteProveedor = QueryClienteProveedor.obtenerClienteProveedor(identificacion);
		} catch (Exception e) {
			log.info(e);
			throw new Exception(e.getMessage());
		}
		if (datosClienteProveedor == null) {
			
			JOptionPane.showMessageDialog(parent, "No se encotró cliente con esa identificación", "Alerta",
					JOptionPane.WARNING_MESSAGE);
			int n = JOptionPane.showConfirmDialog(parent,
	                "¿Desea registrar al Cliente / Proveedor?" ,
	                CONSTANTES_INTERACCION.IMPRIMIR_DIALOGO,JOptionPane.YES_NO_OPTION);
			
			if (n == JOptionPane.NO_OPTION) {
				return datosClienteProveedor;
			}else {
				DialogoClienteProveedor dClienteProveedor = null;
				
				dClienteProveedor = new DialogoClienteProveedor(parent);
				identificacion = dClienteProveedor.getDatosClienteProveedor().getIdentificacion();
				
				if (!identificacion.equals("")) {
					datosClienteProveedor = QueryClienteProveedor.obtenerClienteProveedor(identificacion);
				}
				
				dClienteProveedor.removeAll();
				dClienteProveedor = null;
			}
			
			
		}
		return datosClienteProveedor;
		
		
		
	}
	
	protected String obtenerFechaActual() {
		log.info("obtenerFechaActual");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String fecha = dtf.format(LocalDateTime.now());
		 
		return fecha;
	}



	protected void construirPanelBotones(String textoPrimerBoton, String textoSegundoBoton) {

		// Botón Aceptar
		okButton = new AppJButton("", "");
		okButton = okButton.crearBoton();
		okButton.setText(textoPrimerBoton);
		okButton.setActionCommand(CMD_OK);
		okButton.setIcon(DatosConfiguracion.iAceptar);
		okButton.setEnabled(false);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				windowAction(event);
			}
		});
		okButton.setVerifyInputWhenFocusTarget(true);

		// Botón Cancelar
		cancelButton = new JButton();
		cancelButton.setText(textoSegundoBoton);
		cancelButton.setVerifyInputWhenFocusTarget(false);
		cancelButton.setActionCommand(CMD_CANCEL);
		cancelButton.setIcon(DatosConfiguracion.iCancelar);
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				windowAction(event);
			}
		});

		botonesPanel.setLayout(new GridBagLayout());
		botonesPanel.add(okButton,
				UtilFormas.setGridContraints("gridx=0,gridy=0,fill=" + +GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(10, 0, 20, 20)));
		botonesPanel.add(cancelButton,
				UtilFormas.setGridContraints("gridx=1,gridy=0,fill=" + +GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(10, 0, 20, 20)));
	}
	
	protected void construirPanelBotones(String textoBotones[]) {

		// Botón Aceptar
		okButton = new AppJButton("", "");
		okButton = okButton.crearBoton();
		okButton.setText(textoBotones[0]);
		okButton.setActionCommand(CMD_OK);
		okButton.setIcon(DatosConfiguracion.iAceptar);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				windowAction(event);
			}
		});
		okButton.setVerifyInputWhenFocusTarget(true);

		// Botón Cancelar
		cancelButton = new JButton();
		cancelButton.setText(textoBotones[1]);
		cancelButton.setVerifyInputWhenFocusTarget(false);
		cancelButton.setActionCommand(CMD_CANCEL);
		cancelButton.setIcon(DatosConfiguracion.iCancelar);

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				windowAction(event);
			}
		});
		
		// Botón actualizar
		notaEntregaButton = new JButton();
		notaEntregaButton.setText(textoBotones[2]);
		notaEntregaButton.setVerifyInputWhenFocusTarget(false);
		notaEntregaButton.setActionCommand(CMD_NOTA_ENTREGA);

		notaEntregaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				windowAction(event);
			}
		});

		botonesPanel.setLayout(new GridBagLayout());
		botonesPanel.add(okButton,
				UtilFormas.setGridContraints("gridx=0,gridy=0,fill=" + +GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(10, 0, 20, 20)));
//		botonesPanel.add(notaEntregaButton,
//				UtilFormas.setGridContraints("gridx=1,gridy=0,fill=" + +GridBagConstraints.HORIZONTAL,
//						GridBagConstraints.WEST, new Insets(10, 0, 20, 20)));
		botonesPanel.add(cancelButton,
				UtilFormas.setGridContraints("gridx=2,gridy=0,fill=" + +GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(10, 0, 20, 20)));
	}

	/**
	 * Retorna el indicador de acción satisfactoria.
	 *
	 * @return boolean: Indicador de acción satisfactoria.
	 */
	protected abstract boolean actionOK();
	/**
	 * Retorna el indicador de acción satisfactoria.
	 *
	 * @return boolean: Indicador de acción satisfactoria.
	 */
	protected abstract boolean actionOK(String actionCommand);
	/**
	 * Retorna el indicador de acción tomada.
	 *
	 * @return boolean: Indicador de acción tomada.
	 */
	public boolean isButtonFlag() {
		return buttonFlag;
	}
	/**
	 * Selección de opción.
	 *
	 * @param actionCommand Acción a realizar.
	 */
	public void windowAction(Object actionCommand) {

		try {
			String cmd = null;

			if (actionCommand != null) {
				if (actionCommand instanceof ActionEvent) {
					cmd = ((ActionEvent) actionCommand).getActionCommand();
				} else {
					cmd = actionCommand.toString();
				}
			}
			if (cmd.equals(CMD_CANCEL)) {
				closeWindow = true;
				buttonFlag = false;
			} else if (cmd.equals(CMD_OK)) {
				closeWindow = actionOK(CMD_OK);
				buttonFlag = closeWindow;
			}else if (cmd.equals(CMD_NOTA_ENTREGA)) {
				closeWindow = actionOK(CMD_NOTA_ENTREGA);
				buttonFlag = closeWindow;
			}
			

		} finally {
			if (closeWindow) {
				removeAll();
				dispose();
			}
		}
	}

}
