package ve.com.avss.administracion.interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

import ve.com.avss.clientes.BD.conectores.ConectorClienteProveedor;
import ve.com.avss.clientes.interfaz.DialogoClienteProveedor;
import ve.com.avss.empresa.BD.conectores.ConectorEmpresa;
import ve.com.avss.empresa.BD.conectores.ConectorUsuario;
import ve.com.avss.empresa.BD.ejecutores.QueryEmpresa;
import ve.com.avss.empresa.bean.DatosEmpresa;
import ve.com.avss.empresa.interfaz.DialogoEmpresa;
import ve.com.avss.empresa.interfaz.DialogoUsuarios;
import ve.com.avss.ventas.util.Util;

public class DialogoAdministracion extends AbstractDialogoBase{
	private static final Logger log = Logger.getLogger(DialogoAdministracion.class);
	private static final long serialVersionUID = -1555283450306856921L;
	private DatosEmpresa datosEmpresa = new DatosEmpresa();
	
	 
	
	public DialogoAdministracion(JFrame parent) {
		super(parent);
		construirPanelesCampos();
		construirAccionBotones();
		construirPanelBotones("Continuar", "Cancelar");
		eNombreFormulario.setText("Administración");
		jContentPaneSuperior.add(eNombreFormulario);
		jContentPane.add(jContentPaneSuperior, BorderLayout.NORTH);
		jContentPane.add(jContentPaneIzquierdo, BorderLayout.WEST);
		jContentPane.add(botonesPanel, BorderLayout.SOUTH);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(jContentPane);
		getContentPane().add(jScrollPane);
		pack();
		setAlwaysOnTop(false);
		setLocationRelativeTo(null);
		setTitle("Administración");
		setResizable(true);
		setVisible(true);
	}
	
	private void construirPanelesCampos() {

		String campo = "";

		/* armar botones de inicio */
			campo = "empresa";
			armarBotones(campo);
			campo = "roles";
			armarBotones(campo);
			campo = "usuario";
			armarBotones(campo);
			campo = "clienteProveedor";
			armarBotones(campo);
			campo = "producto";
			armarBotones(campo);
			campo = "categoria";
			armarBotones(campo);
			campo = "tipoPago";
			armarBotones(campo);
			campo = "precioDivisa";
			armarBotones(campo);
			

	}
	private void construirAccionBotones() {
		btnEmpresa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				DialogoEmpresa dEmpresa = null;
				try {
					dEmpresa = new DialogoEmpresa(parent);
					switch (dEmpresa.getActionCommand()) {
					case "registrar":
						QueryEmpresa.registrarEmpresa(dEmpresa.getDatosEmpresa());
						break;
					case "actualizar":
						QueryEmpresa.actualizarEmpresa(dEmpresa.getDatosEmpresa());
						break;
					default:
						break;
					}
				} catch (Exception e) {
					log.error("error "+e.getMessage());
					
				}
				
			}
		});
		
		
		btnUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				DialogoUsuarios dUsuario;
				try {
					dUsuario = new DialogoUsuarios(parent);
					switch (dUsuario.getActionCommand()) {
					case "registrar":
						ConectorUsuario conector = new ConectorUsuario();
						conector.setDatosUsuario(dUsuario.getDatosUsuario());
						int statusQuery = conector.registrarUsuario();
						System.out.println(statusQuery);
						break;
					case "actualizar":
						
						break;

					default:
						break;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
		});
		btnClienteProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				DialogoClienteProveedor dClienteProveedor = new DialogoClienteProveedor(null);
				ConectorClienteProveedor conector = new ConectorClienteProveedor();
				conector.setDatosClienteProveedor(dClienteProveedor.getDatosClienteProveedor());
				conector.registrarClienteProveedor();
			}
		});
		
		btnPrecioDivisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ConectorEmpresa conectorEmpresa = new ConectorEmpresa();
				conectorEmpresa.setDatosEmpresa(datosEmpresa);
				try {
					datosEmpresa = conectorEmpresa.obtenerDatosEmpresa();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos" + e,
							"Error en la operación", JOptionPane.ERROR_MESSAGE);
				}
				String montoDivisa = JOptionPane.showInputDialog(parent,
						"Ingrese el nuevo precio de la divisa ", datosEmpresa.getMontoDivisa());
				if (!Util.isNumero(montoDivisa)) {
					JOptionPane.showMessageDialog(parent,"Ingrese un monto válido","Alerta",JOptionPane.WARNING_MESSAGE); 
					return;
				}
				
				datosEmpresa.setMontoDivisa(montoDivisa);
				conectorEmpresa.setDatosEmpresa(datosEmpresa);
				int statusQuery = 0;
				try {
					statusQuery = conectorEmpresa.actualizarPrecioDivisa();
				} catch (Exception e) {
					log.error("error "+e.getMessage());
					
				}
				if (statusQuery == 1) {
					 JOptionPane.showMessageDialog(parent,"Se actualizó con exito el monto de la divisa","Alerta",JOptionPane.INFORMATION_MESSAGE); 
				}else {
					 JOptionPane.showMessageDialog(parent,"No se pudo actualizar el monto de la divisa, intente de nuevo","Alerta",JOptionPane.WARNING_MESSAGE); 
				}
			}
		});
	}
	@Override
	protected boolean actionOK() {
		// TODO Auto-generated method stub
		return false;
	}

}
