package ve.com.avss.empresa.BD.ejecutores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ve.com.avss.empresa.bean.DatosModulo;
import ve.com.avss.empresa.bean.DatosRol;
import ve.com.avss.ventas.BD.conectores.Conexion;

public class QueryRoles extends Conexion {
	private static final Logger log = Logger.getLogger(QueryRoles.class);
	protected static DatosRol datosRol = new DatosRol();
	
	public static int registrarRol() throws Exception {
		log.info("registrando rol");
		int statusQuery = 0;
		try {
			if (cn.isClosed()) {
	 			conectar();
	 		}
			preparedStatement = cn.prepareStatement(
					"INSERT INTO `tbl_roles`(`id_rol`, `desc_rol`, `id_empresa`) VALUES (null,?,?)");
			preparedStatement.setString(1, datosRol.getNombre());
			preparedStatement.setInt(2, 1);
			statusQuery = preparedStatement.executeUpdate();
			
			/*obtenemos el id del rol insertado*/
			int idRolInsertado = obtenerIdRol(datosRol.getNombre());
			datosRol.setId(idRolInsertado+"");
			log.info("registrando rol "+statusQuery);
			log.info("registrando rol "+idRolInsertado);
		} catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
		return statusQuery;
	}

	public static int registrarModuloRol() throws Exception {
		log.info("registrando modulos rol");
		int statusQuery = 0;
		try {
			if (cn.isClosed()) {
	 			conectar();
	 		}
			if (datosRol.getListaModulos().size() > 0) {
				for (int i = 0; i < datosRol.getListaModulos().size(); i++) {
					
						preparedStatement = cn.prepareStatement(
								"INSERT INTO `tbl_modulos_roles`(`id_modulo_rol`, `id_modulo`, `id_rol`, `activo_modulo_rol`) VALUES (null,?,?,?)");

						preparedStatement.setInt(1, new Integer(datosRol.getListaModulos().get(i).getId()));
						preparedStatement.setInt(2, new Integer(datosRol.getId()));
						preparedStatement.setInt(3, new Integer(datosRol.getListaModulos().get(i).getActivo()));
						statusQuery = preparedStatement.executeUpdate();
						log.info("registrando rol a modulo "+statusQuery);
					
				}

			}

		} catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
		return statusQuery;
	}
	public static List<DatosRol> obtenerRoles() throws Exception {
		log.info("obteniendo rol ");
		 List<DatosRol> listaRoles = new ArrayList<DatosRol>();
       try {
    	   if (cn.isClosed()) { 
    			conectar();
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT * FROM tbl_roles");
           
           while (resulSet.next()) {
        	   DatosRol rol = new DatosRol();
        	   rol.setId(resulSet.getString("id_rol"));
        	   rol.setNombre(resulSet.getString("desc_rol"));
           	listaRoles.add(rol);
           }
           
       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       return listaRoles;
	}
	
	public static List<DatosRol> obtenerRolesModulos() throws Exception {
		log.info("obtenerRolesModulos");
		 List<DatosRol> listaRoles = new ArrayList<DatosRol>();
		
      try {
   	   if (cn.isClosed()) { 
   			conectar();
   			
   		}
          statement = cn.createStatement();
          resulSet = statement.executeQuery("SELECT * FROM tbl_roles");
          DatosRol rol = null;
          while (resulSet.next()) { 
       	    rol = new DatosRol();
       	    rol.setId(resulSet.getString("id_rol"));
       	    rol.setNombre(resulSet.getString("desc_rol"));
       	   
		       	statement = cn.createStatement();
		       	ResultSet resulSetModulo = statement.executeQuery("SELECT tbl_modulos.*, tbl_roles.desc_rol, tbl_roles.id_rol,tbl_modulos_roles.activo_modulo_rol FROM tbl_modulos_roles INNER JOIN tbl_modulos ON tbl_modulos.id_modulo = tbl_modulos_roles.id_modulo INNER JOIN tbl_roles ON tbl_roles.id_rol = tbl_modulos_roles.id_rol WHERE tbl_roles.id_rol ="+resulSet.getString(1));
		        List<DatosModulo> listaModulosBD = new ArrayList<DatosModulo>();
		        while (resulSetModulo.next()) {
		        	  
		        	
		        	DatosModulo datosModulo = new DatosModulo();
		        	datosModulo.setId(resulSetModulo.getString("id_modulo"));
		        	datosModulo.setNombre(resulSetModulo.getString("desc_modulo"));
		        	datosModulo.setActivo(resulSetModulo.getString("activo_modulo_rol"));
		        	listaModulosBD.add(datosModulo);
		        
		        }
		        
		        rol.setListaModulos(listaModulosBD);
		        

          	
          	listaRoles.add(rol);
          }
          
      } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
      return listaRoles;
	}

	public static int obtenerIdRol(String nombreRol) throws Exception {
		log.info("obtenerIdRol");
		int idRol = 0;
		try {
			if (cn.isClosed()) {
	 			conectar();
	 		}
			statement = cn.createStatement();
			resulSet = statement.executeQuery("SELECT `id_rol` FROM `tbl_roles` WHERE desc_rol ='" + nombreRol + "'");

			while (resulSet.next()) {
				idRol = resulSet.getInt("id_rol");

			}

		} catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
		return idRol;
	}

	public static DatosRol getDatosRol() {
		return datosRol;
	}

	public static void setDatosRol(DatosRol datosRol) {
		QueryRoles.datosRol = datosRol;
	}



}
