/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BDD;
import java.sql.Connection;
import java.sql.PreparedStatement;// Import para consultas seguras a la base de datos evita inyección SQL
import java.sql.ResultSet;// Import para manejar resultados de consultas SELECT
import java.sql.SQLException;// Import para capturar errores específicos de MySQL
import java.util.ArrayList;// Import para crear listas dinámicas que pueden crecer
import java.util.List;// Import para usar el tipo List en variables y métodos
import modelo.UsuarioModelo;
/**
 *
 * @author Usuario
 */

public class UsuarioBDD {

    // 1. GUARDAR UN USUARIO NUEVO EN LA BASE DE DATOS
    public void insertarNuevoUsuario(UsuarioModelo usuario) {
        
        // Creamos un objeto para poder conectarnos a MySQL
        ConexionBDD objetoConexion = new ConexionBDD();
        
        // Intentamos conectar y guardamos la conexión en una variable
        Connection conexionActual = objetoConexion.conectar();

        // Si la conexión fue exitosa (no es null = todo bien)
        if (conexionActual != null) {
            
            try {
                // Consulta SQL para insertar un nuevo usuario
                // Los signos ? son lugares seguros donde pondremos los datos --JCOMBOBOX ROL
                String sentenciaSQL = "INSERT INTO usuario (nombres, cedula, direccion, alias, clave, edad, ) VALUES (?, ?, ?, ?, ?, ?)";
                
                // Preparamos la consulta (es más seguro que concatenar texto)
                PreparedStatement sentenciaPreparada = conexionActual.prepareStatement(sentenciaSQL);
                
                // Reemplazamos cada ? con el dato correcto del usuario
                sentenciaPreparada.setString(1, usuario.getNombres());     // 1er ? = nombres
                sentenciaPreparada.setString(2, usuario.getCedula());      // 2do ? = cédula
                sentenciaPreparada.setString(3, usuario.getDireccion());   // 3er ? = dirección
                sentenciaPreparada.setString(4, usuario.getAlias());       // 4to ? = alias
                sentenciaPreparada.setString(5, usuario.getClave());       // 5to ? = clave
                sentenciaPreparada.setInt(6, usuario.getEdad());           // 6to ? = edad (número)
          

                // Ejecutamos la inserción → guarda el usuario en la tabla
                sentenciaPreparada.executeUpdate();

                // Cerramos la consulta preparada
                sentenciaPreparada.close();
                
                // Cerramos la conexión a la base de datos
                conexionActual.close();

            } catch (SQLException error) {
                // Si hay error (ej: cédula repetida), lo mostramos en consola (no al usuario)
                System.out.println("Error al guardar usuario: " + error.getMessage());
            }
        }
    }

    // 2. ACTUALIZAR UN USUARIO QUE YA EXISTÍA
    public void actualizarUsuarioExistente(UsuarioModelo usuario, int idUsuario) {
        
        // Creamos el objeto para conectarnos
        ConexionBDD objetoConexion = new ConexionBDD();
        
        // Intentamos conectar
        Connection conexionActual = objetoConexion.conectar();

        // Si se conectó bien
        if (conexionActual != null) {
            try {
                // Consulta para modificar TODOS los campos del usuario con ese ID 
                String sentenciaSQL = "UPDATE usuario SET nombres=?, cedula=?, direccion=?, alias=?, clave=?, edad=?,  WHERE idusuarioPK=?";
                
                // Preparamos la consulta
                PreparedStatement sentenciaPreparada = conexionActual.prepareStatement(sentenciaSQL);
                
                // Reemplazamos los ? con los nuevos datos
                sentenciaPreparada.setString(1, usuario.getNombres());
                sentenciaPreparada.setString(2, usuario.getCedula());
                sentenciaPreparada.setString(3, usuario.getDireccion());
                sentenciaPreparada.setString(4, usuario.getAlias());
                sentenciaPreparada.setString(5, usuario.getClave());
                sentenciaPreparada.setInt(6, usuario.getEdad());
                sentenciaPreparada.setInt(7, idUsuario);  // este es el ID del usuario que queremos cambiar
                
                // Ejecutamos la actualización
                sentenciaPreparada.executeUpdate();

                // Cerramos todo
                sentenciaPreparada.close();
                conexionActual.close();

            } catch (SQLException error) {
                System.out.println("Error al actualizar usuario: " + error.getMessage());
            }
        }
    }

    // 3. ELIMINAR UN USUARIO POR SU ID
    public void eliminarUsuarioPorId(int idUsuario) {
        
        // Creamos objeto para conectar
        ConexionBDD objetoConexion = new ConexionBDD();
        
        // Intentamos conectar
        Connection conexionActual = objetoConexion.conectar();

        // Si la conexión funcionó
        if (conexionActual != null) {
            try {
                // Consulta para borrar al usuario que tenga este ID
                String sentenciaSQL = "DELETE FROM usuario WHERE idusuarioPK = ?";
                
                // Preparamos la consulta
                PreparedStatement sentenciaPreparada = conexionActual.prepareStatement(sentenciaSQL);
                
                // Reemplazamos el ? con el ID que queremos borrar
                sentenciaPreparada.setInt(1, idUsuario);

                // Ejecutamos el borrado
                sentenciaPreparada.executeUpdate();

                // Cerramos todo
                sentenciaPreparada.close();
                conexionActual.close();

            } catch (SQLException error) {
                System.out.println("Error al eliminar usuario: " + error.getMessage());
            }
        }
    }

    // 4. TRAER TODOS LOS USUARIOS PARA MOSTRARLOS EN LA TABLA
    public List<UsuarioModelo> obtenerTodosLosUsuarios() {
        
        // Lista donde guardaremos todos los usuarios que encontremos
        List<UsuarioModelo> listaUsuarios = new ArrayList<>();
        
        // Creamos objeto para conectar
        ConexionBDD objetoConexion = new ConexionBDD();
        
        // Intentamos conectar
        Connection conexionActual = objetoConexion.conectar();

        // Si se conectó bien
        if (conexionActual != null) {
            try {
                // Consulta que trae TODOS los usuarios de la tabla
                String sentenciaSQL = "SELECT * FROM usuario";
                
                // Preparamos la consulta
                PreparedStatement sentenciaPreparada = conexionActual.prepareStatement(sentenciaSQL);
                
                // Ejecutamos y guardamos los resultados
                ResultSet resultadoConsulta = sentenciaPreparada.executeQuery();

                // Leemos fila por fila
                while (resultadoConsulta.next()) {
                    // Creamos un objeto temporal para cada usuario
                    UsuarioModelo usuarioTemporal = new UsuarioModelo();
                    
                    // Guardamos TODOS los datos, incluido el ID real de la base de datos
                    usuarioTemporal.setId(resultadoConsulta.getInt("idusuarioPK"));
                    usuarioTemporal.setNombres(resultadoConsulta.getString("nombres"));
                    usuarioTemporal.setCedula(resultadoConsulta.getString("cedula"));
                    usuarioTemporal.setDireccion(resultadoConsulta.getString("direccion"));
                    usuarioTemporal.setAlias(resultadoConsulta.getString("alias"));
                    usuarioTemporal.setClave(resultadoConsulta.getString("clave"));
                    usuarioTemporal.setEdad(resultadoConsulta.getInt("edad"));
                    // Agregamos el usuario a la lista
                    listaUsuarios.add(usuarioTemporal);
                }

                // Cerramos todo
                resultadoConsulta.close();
                sentenciaPreparada.close();
                conexionActual.close();

            } catch (SQLException error) {
                System.out.println("Error al cargar usuarios: " + error.getMessage());
            }
        }
        
        // Devolvemos la lista completa
        return listaUsuarios;
    }

    // 5. BUSCAR UN USUARIO POR SU ID (para cargarlo y editarlo)
    public UsuarioModelo buscarUsuarioPorId(int idBuscado) {
        
        // Creamos objeto para conectar
        ConexionBDD objetoConexion = new ConexionBDD();
        
        // Intentamos conectar
        Connection conexionActual = objetoConexion.conectar();

        // Si la conexión funcionó
        if (conexionActual != null) {
            try {
                // Consulta que busca solo el usuario con ese ID
                String sentenciaSQL = "SELECT * FROM usuario WHERE idusuarioPK = ?";
                
                // Preparamos la consulta
                PreparedStatement sentenciaPreparada = conexionActual.prepareStatement(sentenciaSQL);
                
                // Reemplazamos el ? con el ID que estamos buscando
                sentenciaPreparada.setInt(1, idBuscado);
                
                // Ejecutamos y guardamos el resultado
                ResultSet resultadoConsulta = sentenciaPreparada.executeQuery();

                // Si encontramos al usuario
                if (resultadoConsulta.next()) {
                    // Creamos un objeto con sus datos
                    UsuarioModelo usuarioEncontrado = new UsuarioModelo();
                    
                    usuarioEncontrado.setId(resultadoConsulta.getInt("idusuarioPK"));
                    usuarioEncontrado.setNombres(resultadoConsulta.getString("nombres"));
                    usuarioEncontrado.setCedula(resultadoConsulta.getString("cedula"));
                    usuarioEncontrado.setDireccion(resultadoConsulta.getString("direccion"));
                    usuarioEncontrado.setAlias(resultadoConsulta.getString("alias"));
                    usuarioEncontrado.setClave(resultadoConsulta.getString("clave"));
                    usuarioEncontrado.setEdad(resultadoConsulta.getInt("edad"));
                   
                    

                    // Cerramos todo
                    resultadoConsulta.close();
                    sentenciaPreparada.close();
                    conexionActual.close();
                    
                    // Devolvemos el usuario encontrado
                    return usuarioEncontrado;
                }

                // Si no encontró nada, cerramos todo
                resultadoConsulta.close();
                sentenciaPreparada.close();
                conexionActual.close();

            } catch (SQLException error) {
                System.out.println("Error al buscar usuario: " + error.getMessage());
            }
        }
        
        // Si no encontró nada o hubo error → devuelve null
        return null;
    }
    
    // BUSCAR usuario por cédula en la base de datos
    public UsuarioModelo buscarUsuarioPorCedula(String cedulaBuscada) {
        // Conectar a la base de datos
        ConexionBDD objetoConexion = new ConexionBDD();
        Connection conexionActual = objetoConexion.conectar();

        // Si la conexión funciona
        if (conexionActual != null) {
            try {
                // Consulta SQL para buscar por cédula
                String sentenciaSQL = "SELECT * FROM usuario WHERE cedula = ?";
                PreparedStatement sentenciaPreparada = conexionActual.prepareStatement(sentenciaSQL);

                // Reemplazar ? con la cédula que buscamos
                sentenciaPreparada.setString(1, cedulaBuscada);

                // Ejecutar consulta
                ResultSet resultadoConsulta = sentenciaPreparada.executeQuery();

                // Si encontró un usuario
                if (resultadoConsulta.next()) {
                    // Crear objeto usuario con los datos encontrados
                    UsuarioModelo usuarioEncontrado = new UsuarioModelo();
                    usuarioEncontrado.setId(resultadoConsulta.getInt("idusuarioPK"));
                    usuarioEncontrado.setNombres(resultadoConsulta.getString("nombres"));
                    usuarioEncontrado.setCedula(resultadoConsulta.getString("cedula"));
                    usuarioEncontrado.setDireccion(resultadoConsulta.getString("direccion"));
                    usuarioEncontrado.setAlias(resultadoConsulta.getString("alias"));
                    usuarioEncontrado.setClave(resultadoConsulta.getString("clave"));
                    usuarioEncontrado.setEdad(resultadoConsulta.getInt("edad"));
                  

                    // Cerrar conexiones
                    resultadoConsulta.close();
                    sentenciaPreparada.close();
                    conexionActual.close();

                    // Devolver usuario encontrado
                    return usuarioEncontrado;
                }

                // Cerrar conexiones si no encontró
                resultadoConsulta.close();
                sentenciaPreparada.close();
                conexionActual.close();

            } catch (SQLException error) {
                System.out.println("Error al buscar usuario: " + error.getMessage());
            }
        }

        // Si no encontró o hubo error
        return null;
    }
}
