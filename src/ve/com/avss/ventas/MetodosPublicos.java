package ve.com.avss.ventas;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import ve.com.avss.clientes.interfaz.DialogoClienteProveedor;
import ve.com.avss.empresa.BD.conectores.ConectorEmpresa;
import ve.com.avss.empresa.bean.DatosEmpresa;
import ve.com.avss.empresa.bean.DatosUsuario;
import ve.com.avss.empresa.interfaz.DialogoEmpresa;
import ve.com.avss.empresa.interfaz.DialogoUsuarios;
import ve.com.avss.ventas.util.CONSTANTES_INTERACCION;
import ve.com.avss.ventas.util.Util;

public class MetodosPublicos {

	private static final Logger log = Logger.getLogger(MetodosPublicos.class);
	private DatosUsuario datosUsuario = new DatosUsuario();
	private DatosEmpresa datosEmpresa = new DatosEmpresa();
	private ConectorEmpresa conectorEmpresa = new ConectorEmpresa();
	private int statusQuery;
	private JFrame parent = null;
	public MetodosPublicos(JFrame parent, DatosEmpresa datosEmpresa, DatosUsuario datosUsuario) {
		this.parent = parent;
		this.datosEmpresa = datosEmpresa;
		this.datosUsuario = datosUsuario;
		
	}
	/*metodo publico que muestra los 
	 * el dialogo de empresa donde se actualiza
	 * y muestra los datos de la empresa*/
	public void dialogoEmpresa() throws Exception {
		DialogoEmpresa dialogoEmpresa = null;
		int status = 0 ;
		try {
			dialogoEmpresa = new DialogoEmpresa(parent);
			switch (dialogoEmpresa.getActionCommand()) {
			case "registrar":
				conectorEmpresa.setDatosEmpresa(dialogoEmpresa.getDatosEmpresa());
				conectorEmpresa.registrarDatosEmpresa();
				break;
			case "actualizar":
				try {
					conectorEmpresa.setDatosEmpresa(dialogoEmpresa.getDatosEmpresa());
					status = conectorEmpresa.actualizarDatosEmpresa();
					if (status == 1) {
						JOptionPane.showMessageDialog(null, "Se actualizó los datos con éxito", CONSTANTES_INTERACCION.MENSAJE_APLICACION_DIALOGO, JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e) {
					log.info(e);
					throw new Exception(e.getMessage());
				}
				
				break;

			default:
				break;
			}
		} catch (Exception e) {
			log.info(e);
			throw new Exception(e.getMessage()+" empresa");
		}
		
		
		
		dialogoEmpresa = null;
	}
	
	
	
	
	
	/*metodo publico que muestra los 
	 * el dialogo que actualiza
	 * el precio de la divisa*/
	
	public void dialogoPrecioDivisa() throws Exception {
		conectorEmpresa.setDatosEmpresa(datosEmpresa);
		try {
			datosEmpresa = conectorEmpresa.obtenerDatosEmpresa();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos" + e,
					"Error en la operación", JOptionPane.ERROR_MESSAGE);
		}
		String montoDivisa = JOptionPane.showInputDialog(parent,
				"Ingrese el nuevo precio de la divisa ", datosEmpresa.getMontoDivisa());
		if (montoDivisa == null) {
			return;
		}
		if (!Util.isNumero(montoDivisa)) {
			JOptionPane.showMessageDialog(parent,"Ingrese un monto válido",CONSTANTES_INTERACCION.MENSAJE_APLICACION_DIALOGO,JOptionPane.WARNING_MESSAGE); 
			return;
		}
		
		datosEmpresa.setMontoDivisa(montoDivisa);
		conectorEmpresa.setDatosEmpresa(datosEmpresa);
		int statusQuery = 0;
		try {
			statusQuery = conectorEmpresa.actualizarPrecioDivisa();
			if (statusQuery == 1) {
				 JOptionPane.showMessageDialog(parent,"Se actualizó con exito el monto de la divisa",CONSTANTES_INTERACCION.MENSAJE_APLICACION_DIALOGO,JOptionPane.INFORMATION_MESSAGE); 
			}
		} catch (Exception e) {
			log.error("error "+e);
			throw new Exception(e.getMessage());
			
		}
		
		
		
		
	}
	
	
	
	
	/*metodo publico que muestra 
	 * el dialogo de usuarios donde se actualiza
	 * y muestra los datos de los usuarios*/
	public void dialogoUsuario() throws Exception {
		DialogoUsuarios dialogoUsuario = new DialogoUsuarios(parent);
		
		dialogoUsuario = null;
	}
	
	/*metodo publico que muestra 
	 * el dialogo de clientes proveedor donde se actualiza
	 * y muestra los datos de los clientes proveedor*/
	public void dialogoCliente() {
		DialogoClienteProveedor dialogoClienteProveedor = new DialogoClienteProveedor(parent);
		dialogoClienteProveedor = null;
	}
	
	
}
