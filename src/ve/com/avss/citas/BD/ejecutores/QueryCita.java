package ve.com.avss.citas.BD.ejecutores;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ve.com.avss.citas.bean.DatosCitas;
import ve.com.avss.ventas.BD.conectores.Conexion;

public class QueryCita extends Conexion{
	private static final Logger log = Logger.getLogger(QueryCita.class);
	//private static DatosCitas datosCita = new DatosCitas();
	public static int registrarCita(DatosCitas datosCita) throws Exception {
		log.info("registrarCita");
		int statusQuery = 0;
        try {
        	if (cn.isClosed()) {
     			conectar();
     		}
        	preparedStatement = cn.prepareStatement("INSERT INTO `tbl_citas`(`id_cita`, `observacion_cita`, `fecha_cita`, `status_cita`,`id_cliente_cita`, `id_usuario_cita`) VALUES (null,?,?,?,?,?)");
             
        	preparedStatement.setString(1, datosCita.getObservacion());
        	preparedStatement.setString(2, datosCita.getFecha());
        	preparedStatement.setString(3, datosCita.getStatus());
        	preparedStatement.setString(4, datosCita.getIdCliente());
        	preparedStatement.setString(5, datosCita.getIdUsuario());
        	
        	 
        	statusQuery = preparedStatement.executeUpdate();
            log.info("registrarCita"+resultadoQuery);
        } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
        log.info("registrarCita - fin");
        return statusQuery;
	}
	
	public static int actualizarCita(DatosCitas datosCita) throws Exception {
		log.info("actualizarCita");
		int statusQuery = 0;
        try {
        	if (cn.isClosed()) {
     			conectar();
     		}
        	preparedStatement = cn.prepareStatement("UPDATE tbl_citas SET observacion_cita = '"+datosCita.getObservacion()+"', fecha_cita = '"+datosCita.getFecha()+"', status_cita = '"+datosCita.getStatus()+"'  WHERE id_cita = '"+datosCita.getIdCita()+"'");
             
        	statusQuery = preparedStatement.executeUpdate();
            log.info("actualizarCita"+statusQuery);
        } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
        log.info("actualizarCita - fin");
        return statusQuery;
	}
	
	public static List<DatosCitas> obtenerListaCitas(String buscar) throws Exception {
		log.info("obtenerListaCitas");
		 List<DatosCitas> listaCitas = new ArrayList<DatosCitas>();
       try {
    	   if (cn.isClosed()) {
    			conectar(); 
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT tbl_citas.id_cita, tbl_citas.observacion_cita, tbl_citas.fecha_cita, tbl_cliente_proveedor.id_cliente, tbl_cliente_proveedor.nombre_cliente_proveedor,tbl_cliente_proveedor.apellido_cliente_proveedor, tbl_usuarios.id_usuario, tbl_usuarios.nombre_usuario, tbl_usuarios.apellido_usuario, tbl_usuarios.usuario_usuario FROM tbl_citas INNER JOIN tbl_cliente_proveedor ON tbl_cliente_proveedor.id_cliente = tbl_citas.id_cliente_cita INNER JOIN tbl_usuarios ON tbl_usuarios.id_usuario = tbl_citas.id_usuario_cita WHERE tbl_cliente_proveedor.cedula_cliente_proveedor ='" + buscar +"' ORDER BY tbl_citas.fecha_cita DESC");
           
           while (resulSet.next()) {
        	   DatosCitas cita = new DatosCitas();
           		cita.setIdCita(resulSet.getString("id_cita"));
           		cita.setObservacion(resulSet.getString("observacion_cita"));
           		cita.setFecha(resulSet.getString("fecha_cita"));
           		cita.setIdCliente(resulSet.getString("id_cliente"));
           		cita.setNombreCliente(resulSet.getString("nombre_cliente_proveedor"));
           		cita.setApellidoCliente(resulSet.getString("apellido_cliente_proveedor"));
           		cita.setIdUsuario(resulSet.getString("id_usuario"));
           		cita.setNombreUsuario(resulSet.getString("nombre_usuario"));
           		cita.setUsuarioUsuario(resulSet.getString("usuario_usuario"));
           		
           		listaCitas.add(cita);
           }
           
       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       log.info("obtenerListaCitas - fin");
       return listaCitas;
	}
	
	public static List<DatosCitas> obtenerListaCitasFecha(String buscar) throws Exception {
		log.info("obtenerListaCitas");
		 List<DatosCitas> listaCitas = new ArrayList<DatosCitas>();
       try {
    	   if (cn.isClosed()) {
    			conectar(); 
    		}
           statement = cn.createStatement();
           if (buscar.length() > 0) {
        	   resulSet = statement.executeQuery("SELECT tbl_citas.id_cita, tbl_citas.observacion_cita, tbl_citas.fecha_cita, tbl_cliente_proveedor.id_cliente, tbl_cliente_proveedor.nombre_cliente_proveedor,tbl_cliente_proveedor.apellido_cliente_proveedor, tbl_cliente_proveedor.cedula_cliente_proveedor,tbl_usuarios.id_usuario, tbl_usuarios.nombre_usuario, tbl_usuarios.apellido_usuario, tbl_usuarios.usuario_usuario FROM tbl_citas INNER JOIN tbl_cliente_proveedor ON tbl_cliente_proveedor.id_cliente = tbl_citas.id_cliente_cita INNER JOIN tbl_usuarios ON tbl_usuarios.id_usuario = tbl_citas.id_usuario_cita WHERE tbl_citas.fecha_cita ='" + buscar +"' AND tbl_citas.status_cita ='" + 1 +"'  ORDER BY tbl_citas.fecha_cita DESC");
           }else {
        	   resulSet = statement.executeQuery("SELECT tbl_citas.id_cita, tbl_citas.observacion_cita, tbl_citas.fecha_cita, tbl_cliente_proveedor.id_cliente, tbl_cliente_proveedor.nombre_cliente_proveedor,tbl_cliente_proveedor.apellido_cliente_proveedor, tbl_cliente_proveedor.cedula_cliente_proveedor,tbl_usuarios.id_usuario, tbl_usuarios.nombre_usuario, tbl_usuarios.apellido_usuario, tbl_usuarios.usuario_usuario FROM tbl_citas INNER JOIN tbl_cliente_proveedor ON tbl_cliente_proveedor.id_cliente = tbl_citas.id_cliente_cita INNER JOIN tbl_usuarios ON tbl_usuarios.id_usuario = tbl_citas.id_usuario_cita WHERE tbl_citas.status_cita ='" + 1 +"'  ORDER BY tbl_citas.fecha_cita DESC");
           }
          
           
           while (resulSet.next()) {
        	   DatosCitas cita = new DatosCitas();
          		cita.setIdCita(resulSet.getString("id_cita"));
          		cita.setFecha(resulSet.getString("fecha_cita"));
          		cita.setObservacion(resulSet.getString("observacion_cita"));
          		cita.setIdCliente(resulSet.getString("id_cliente"));
          		cita.setNombreCliente(resulSet.getString("nombre_cliente_proveedor"));
          		cita.setApellidoCliente(resulSet.getString("apellido_cliente_proveedor"));
          		cita.setIdUsuario(resulSet.getString("id_usuario"));
          		cita.setCedulaCliente(resulSet.getString("cedula_cliente_proveedor"));
          		cita.setNombreUsuario(resulSet.getString("nombre_usuario"));
          		cita.setApellidoUsuario(resulSet.getString("apellido_usuario"));
          		cita.setUsuarioUsuario(resulSet.getString("usuario_usuario"));
          		listaCitas.add(cita);
           }
           
       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       log.info("obtenerListaCitas - fin");
       return listaCitas;
	}
	
	public static List<DatosCitas> obtenerCitasPendientes() throws Exception {
		log.info("obtenerCitasPendientes");
		 List<DatosCitas> listaCitas = new ArrayList<DatosCitas>();
       try {
    	   if (cn.isClosed()) {
    			conectar(); 
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT tbl_citas.id_cita, tbl_citas.observacion_cita, tbl_citas.fecha_cita, tbl_cliente_proveedor.id_cliente, tbl_cliente_proveedor.nombre_cliente_proveedor,tbl_cliente_proveedor.apellido_cliente_proveedor, tbl_cliente_proveedor.cedula_cliente_proveedor,tbl_usuarios.id_usuario, tbl_usuarios.nombre_usuario, tbl_usuarios.apellido_usuario, tbl_usuarios.usuario_usuario FROM tbl_citas INNER JOIN tbl_cliente_proveedor ON tbl_cliente_proveedor.id_cliente = tbl_citas.id_cliente_cita INNER JOIN tbl_usuarios ON tbl_usuarios.id_usuario = tbl_citas.id_usuario_cita  ORDER BY tbl_citas.fecha_cita DESC");
           
           while (resulSet.next()) {
        	   DatosCitas cita = new DatosCitas();
           		cita.setIdCita(resulSet.getString("id_cita"));
           		cita.setFecha(resulSet.getString("fecha_cita"));
           		cita.setObservacion(resulSet.getString("observacion_cita"));
           		cita.setIdCliente(resulSet.getString("id_cliente"));
           		cita.setNombreCliente(resulSet.getString("nombre_cliente_proveedor"));
           		cita.setApellidoCliente(resulSet.getString("apellido_cliente_proveedor"));
           		cita.setIdUsuario(resulSet.getString("id_usuario"));
           		cita.setCedulaCliente(resulSet.getString("cedula_cliente_proveedor"));
           		cita.setNombreUsuario(resulSet.getString("nombre_usuario"));
           		cita.setApellidoUsuario(resulSet.getString("apellido_usuario"));
           		cita.setUsuarioUsuario(resulSet.getString("usuario_usuario"));
           		listaCitas.add(cita);
           }
           
       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       log.info("obtenerListaProducto - fin");
       return listaCitas;
	}
	



	

}
