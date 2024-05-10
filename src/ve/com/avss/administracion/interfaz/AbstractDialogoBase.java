package ve.com.avss.administracion.interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import ve.com.avss.ventas.util.Util;
import ve.com.avss.ventas.util.UtilFormas;

public abstract class AbstractDialogoBase extends JDialog{

	private static final long serialVersionUID = -7650428750960389329L;
	private static final Logger log = Logger.getLogger(AbstractDialogoBase.class);
	/** Command string para la accion de "Cancelar". */
	public static final String CMD_CANCEL = "cmd.cancel";
	/** Command string para la accion de "Aceptar". */
	public static final String CMD_OK = "cmd.ok";
	/** Command string para la accion de "Ocultar procesando". */
	public static final String CMD_PROCESS = "cmd.process";
	/** JFrame base del dialogo */ 
	protected JFrame parent = null;
	/** Indicador de acción. */
	protected boolean buttonFlag;
	protected boolean closeWindow = false;
	/** Botón aceptar. */
	protected JButton okButton = new JButton();
	/** Botón cancelar. */
	protected JButton cancelButton = new JButton();
	/*datos para la interfaz*/
	protected JPanel jContentPane = new JPanel(new BorderLayout());
	protected JPanel jContentPaneSuperior = new JPanel();
	protected JPanel jContentPaneCentral = new JPanel(new FlowLayout());
	protected JPanel jContentPaneInferior = new JPanel(new GridBagLayout());
	protected JPanel jContentPanederecho = new JPanel();
	protected JPanel jContentPaneIzquierdo = new JPanel();
	protected JLabel eNombreFormulario = new JLabel();
	/** Panel de Campos. */
	protected JPanel camposPanel = new JPanel();
	/** Panel de ayuda. */
	protected JLabel ayudaPanel = new JLabel();
	/** Panel de Botones. */
	protected JPanel botonesPanel = new JPanel();
	/*botones del panel*/
	protected JButton btnEmpresa= null;
	protected JButton btnRoles = null;
	protected JButton btnUsuario = null;
	
	
	protected JButton btnTipoPago = null;
	protected JButton btnPrecioDivisa = null;
	protected JButton btnClienteProveedor = null;
	
	public AbstractDialogoBase(JFrame parent) {
		
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
		
		eNombreFormulario.setText("");
		eNombreFormulario.setFont(new Font("Serif", Font.BOLD, 14));
		eNombreFormulario.setPreferredSize(new java.awt.Dimension(140, 30));
		eNombreFormulario.setHorizontalAlignment(SwingConstants.CENTER);

	}
	
	
	
	protected void armarBotones(String campo) {

		switch (campo) {
			case "empresa":
			
				btnEmpresa = new JButton();
				btnEmpresa.setName("btnEmpresa");
				btnEmpresa.setActionCommand("btnEmpresa");
				btnEmpresa.setOpaque(true);
				btnEmpresa.setText("Datos de Empresa");
				jContentPaneIzquierdo.add(btnEmpresa);
			
				break;
			case "roles":
			
				btnRoles = new JButton();
				btnRoles.setName("btnRoles");
				btnRoles.setActionCommand("btnRoles");
				btnRoles.setOpaque(true);
				btnRoles.setText("Roles de usuario");
				jContentPaneIzquierdo.add(btnRoles);
				
				break;
			case "usuario":

				btnUsuario= new JButton();
				btnUsuario.setName("btnUsuario");
				btnUsuario.setActionCommand("btnUsuario");
				btnUsuario.setOpaque(true);
				btnUsuario.setText("Usuarios");
				jContentPaneIzquierdo.add(btnUsuario);

				break;
			case "clienteProveedor":

				btnClienteProveedor = new JButton();
				btnClienteProveedor.setName("btnClienteProveedor");
				btnClienteProveedor.setActionCommand("btnClienteProveedor");
				btnClienteProveedor.setOpaque(true);
				btnClienteProveedor.setText("Cliente / Proveedor");
				jContentPaneIzquierdo.add(btnClienteProveedor);

				break;
			
			case "tipoPago":

				btnTipoPago = new JButton();
				btnTipoPago.setName("btnTipoPago");
				btnTipoPago.setActionCommand("btnTipoPago");
				btnTipoPago.setOpaque(true);
				btnTipoPago.setText("Tipo de pago");
				jContentPaneIzquierdo.add(btnTipoPago);

				break;
			
			case "precioDivisa":

				btnPrecioDivisa = new JButton();
				btnPrecioDivisa.setName("btnPrecioDivisa");
				btnPrecioDivisa.setActionCommand("btnPrecioDivisa");
				btnPrecioDivisa.setOpaque(true);
				btnPrecioDivisa.setText("Precio de divisa");
				jContentPaneIzquierdo.add(btnPrecioDivisa);

				break;
			
		

		default:
			break;
		}
}
	protected void construirPanelBotones(String textoPrimerBoton, String textoSegundoBoton) {

		// Botón Aceptar
		okButton = new JButton();
		okButton.setText(textoPrimerBoton);
		okButton.setActionCommand(CMD_OK);

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
						GridBagConstraints.WEST, new Insets(10, 0, 20, 0)));
	}
	/**
	 * Retorna el indicador de acción satisfactoria.
	 *
	 * @return boolean: Indicador de acción satisfactoria.
	 */
	protected abstract boolean actionOK();
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
				closeWindow = actionOK();
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
