/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import BDD.UsuarioBDD;           // la clase que habla con la base de datos
import Vistas.UsuarioVista;     // la ventana
import modelo.UsuarioModelo;     // los datos del usuario
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */

// CONTROLADOR: ES EL CEREBRO DEL PROGRAMA
 //Aquí está toda la lógica: guardar, actualizar, eliminar, validar cédula, etc.
 
public class UsuarioControlador {
  //ATRIBUTOS
    private UsuarioModelo modelo;           // para guardar los datos del usuario
    private UsuarioVista vista;             // la ventana con botones y campos
    private UsuarioBDD baseDatos;           // para hablar con MySQL
    private int idUsuarioSeleccionado = 0;  // guarda el ID del usuario que estamos editando

    //  CONSTRUCTOR: SE EJECUTA CUANDO CREAMOS EL CONTROLADOR 
    public UsuarioControlador(UsuarioModelo modelo, UsuarioVista vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.baseDatos = new UsuarioBDD();  // conectamos con la base de datos

        // CONECTAMOS LOS BOTONES A SUS ACCIONES
        vista.getBtnNuevo().addActionListener(e -> botonNuevo());
        vista.getBtnGuardar().addActionListener(e -> botonGuardar());
        vista.getBtnActualizar().addActionListener(e -> botonActualizar());
        vista.getBtnEliminar().addActionListener(e -> botonEliminar());
        vista.getBtnBuscar().addActionListener(e -> botonBuscar());
       

        // CUANDO HACEMOS CLIC EN UNA FILA DE LA TABLA  CARGAR DATOS
        // Escuchar cuando el usuario hace clic en cualquier fila de la tabla
        vista.getTblUsuarios().getSelectionModel().addListSelectionListener(e -> {
            // Solo procesar si el clic ya terminó y hay una fila seleccionada
            if (!e.getValueIsAdjusting() && vista.getTblUsuarios().getSelectedRow() != -1) {//getSelectedRow(): para identificar específicamente qué registro de la tabla ha seleccionado el usuario
                // Obtener qué fila fue clickeada (0 = primera, 1 = segunda, etc.)
                int fila = vista.getTblUsuarios().getSelectedRow();
                // Cargar los datos de esa fila al formulario para editar
                cargarUsuarioParaEditar(fila);
            }
        });

        // CARGAR TODOS LOS USUARIOS AL INICIAR
        cargarTablaUsuarios();
    }

    // BOTÓN NUEVO  LIMPIA TODO PARA CREAR UNO NUEVO
    private void botonNuevo() {
        vista.limpiarCampos();
        idUsuarioSeleccionado = 0;
        vista.getBtnGuardar().setEnabled(true);// setEneabled(): controla si un componente puede o no ser usado por el usuario.
        vista.getBtnActualizar().setEnabled(false);
        vista.getBtnEliminar().setEnabled(false);
    }

    // BOTÓN GUARDAR  CREA UN USUARIO NUEVO
    private void botonGuardar() {
        if (!validarCampos()) return;

        modelo.setNombres(vista.getTxtNombres());
        modelo.setCedula(vista.getTxtCedula());
        modelo.setDireccion(vista.getTxtDireccion());
        modelo.setAlias(vista.getTxtAlias());
        modelo.setClave(vista.getTxtClave());
        modelo.setEdad(Integer.parseInt(vista.getTxtEdad()));

        baseDatos.insertarNuevoUsuario(modelo);
        cargarTablaUsuarios();
        botonNuevo();
        vista.mostrarMensaje("¡Usuario creado con éxito!");
    }

    // BOTÓN ACTUALIZAR  MODIFICA EL USUARIO SELECCIONADO
    private void botonActualizar() {
        if (idUsuarioSeleccionado == 0) {
            vista.mostrarMensaje("Seleccione un usuario de la tabla");
            return;
        }
        if (!validarCampos()) return;//Si la validación falló, sal del método y no actualices."

        modelo.setNombres(vista.getTxtNombres());
        modelo.setCedula(vista.getTxtCedula());
        modelo.setDireccion(vista.getTxtDireccion());
        modelo.setAlias(vista.getTxtAlias());
        modelo.setClave(vista.getTxtClave());
        modelo.setEdad(Integer.parseInt(vista.getTxtEdad()));

        baseDatos.actualizarUsuarioExistente(modelo, idUsuarioSeleccionado);
        cargarTablaUsuarios();
        botonNuevo();
        vista.mostrarMensaje("¡Usuario actualizado correctamente!");
    }

    // BOTÓN ELIMINAR
    private void botonEliminar() {
        if (idUsuarioSeleccionado == 0) {
            vista.mostrarMensaje("Seleccione un usuario para eliminar");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(null, 
            "¿Seguro que quiere eliminar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            baseDatos.eliminarUsuarioPorId(idUsuarioSeleccionado);
            cargarTablaUsuarios();
            botonNuevo();
            vista.mostrarMensaje("¡Usuario eliminado!");
        }
    }


      // CUANDO HACEMOS CLIC EN UNA FILA  CARGAR DATOS PARA EDITAR
    private void cargarUsuarioParaEditar(int fila) {
        // TOMAMOS EL ID REAL DE LA PRIMERA COLUMNA DE LA TABLA
        idUsuarioSeleccionado = (int) vista.getTblUsuarios().getValueAt(fila, 0);//getValueAt(fila, columna) te permite leer el contenido de cualquier celda de la tabla, sabiendo su posición de fila y columna.

        // BUSCAMOS EL USUARIO CON ESE ID REAL
        UsuarioModelo usuario = baseDatos.buscarUsuarioPorId(idUsuarioSeleccionado);

        if (usuario != null) {
            // CARGAMOS TODOS LOS DATOS EN LOS CAMPOS
            vista.setTxtId(String.valueOf(usuario.getId()));           
            vista.setTxtNombres(usuario.getNombres());
            vista.setTxtCedula(usuario.getCedula());
            vista.setTxtDireccion(usuario.getDireccion());
            vista.setTxtAlias(usuario.getAlias());
            vista.setTxtClave(usuario.getClave());
            vista.setTxtEdad(String.valueOf(usuario.getEdad()));

            // ACTIVAMOS BOTONES DE EDICIÓN
            vista.getBtnGuardar().setEnabled(false);
            vista.getBtnActualizar().setEnabled(true);
            vista.getBtnEliminar().setEnabled(true);
        }
    }

    // 
    // CARGAR TABLA CON EL ID REAL DE LA BASE DE DATOS
    private void cargarTablaUsuarios() {
        DefaultTableModel tabla = new DefaultTableModel();
        tabla.addColumn("ID");
        tabla.addColumn("Nombres");
        tabla.addColumn("Cédula");
        tabla.addColumn("Dirección");
        tabla.addColumn("Edad");
        tabla.addColumn("Alias");

        for (UsuarioModelo u : baseDatos.obtenerTodosLosUsuarios()) {
            Object[] fila = {
                u.getId(),           
                u.getNombres(),
                u.getCedula(),
                u.getDireccion(),
                u.getEdad(),
                u.getAlias()
            };
            tabla.addRow(fila);
        }

        vista.actualizarTabla(tabla);
        
        //AJUSTAR ANCHO DE COLUMNAS
        javax.swing.JTable tablaUsuarios = vista.getTblUsuarios();
        tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(40);   // ID
        tablaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(150);  // Nombres
        tablaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(100);  // Cédula
        tablaUsuarios.getColumnModel().getColumn(3).setPreferredWidth(200);  // Dirección
        tablaUsuarios.getColumnModel().getColumn(4).setPreferredWidth(50);   // Edad
        tablaUsuarios.getColumnModel().getColumn(5).setPreferredWidth(80);   // Alias
    
        
        // DECORACIÓN BONITA
        vista.getTblUsuarios().setBackground(new java.awt.Color(245, 250, 255));
        vista.getTblUsuarios().getTableHeader().setBackground(new java.awt.Color(0, 153, 153));
        vista.getTblUsuarios().getTableHeader().setForeground(java.awt.Color.WHITE);
        vista.getTblUsuarios().getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 14));
    }
    
    // VALIDAR CAMPOS + CÉDULA
    private boolean validarCampos() {
        if (vista.getTxtNombres().isEmpty() || vista.getTxtCedula().isEmpty() ||
            vista.getTxtDireccion().isEmpty() || vista.getTxtAlias().isEmpty() ||//.isEmpty(): asegura que todos los campos tengan datos antes de guardar?
            vista.getTxtClave().isEmpty() || vista.getTxtEdad().isEmpty()) {
            vista.mostrarMensaje("Todos los campos son obligatorios");
            return false;
        }
        if (!modelo.validarCedula(vista.getTxtCedula())) {
            vista.mostrarMensaje("¡Cédula inválida!");
            return false;
        }
        try {
            Integer.parseInt(vista.getTxtEdad());
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("La edad debe ser un número");
            return false;
        }
        return true;
    }
    
    // BOTÓN BUSCAR - Buscar usuario por cédula
        private void botonBuscar() {
            // Pedir cédula al usuario
            String cedulaABuscar = JOptionPane.showInputDialog("Ingrese cédula a buscar:");

            // Verificar que no canceló y no está vacío
            if (cedulaABuscar != null && !cedulaABuscar.isEmpty()) {
                // Buscar usuario en la base de datos
                UsuarioModelo usuario = baseDatos.buscarUsuarioPorCedula(cedulaABuscar);

                if (usuario != null) {
                    // Usuario encontrado - cargar en formulario
                    cargarUsuarioEnFormulario(usuario);
                    vista.mostrarMensaje(" Usuario encontrado: " + usuario.getNombres());
                } else {
                    // Usuario no encontrado
                    vista.mostrarMensaje(" Usuario con cédula " + cedulaABuscar + " no encontrado");
                }
            }
        }
        
        // Cargar usuario en formulario para editar
        private void cargarUsuarioEnFormulario(UsuarioModelo usuario) {
            // Guardar ID del usuario encontrado
            idUsuarioSeleccionado = usuario.getId();

            // Cargar todos los datos en los campos
            vista.setTxtId(String.valueOf(usuario.getId()));
            vista.setTxtNombres(usuario.getNombres());
            vista.setTxtCedula(usuario.getCedula());
            vista.setTxtDireccion(usuario.getDireccion());
            vista.setTxtAlias(usuario.getAlias());
            vista.setTxtClave(usuario.getClave());
            vista.setTxtEdad(String.valueOf(usuario.getEdad()));

            // Activar botones de edición
            vista.getBtnGuardar().setEnabled(false);
            vista.getBtnActualizar().setEnabled(true);
            vista.getBtnEliminar().setEnabled(true);
        }

    // MOSTRAR LA VENTANA
    public void iniciar() {
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);//centra la ventana en medio de la pantalla para que se vea más profesional."
        vista.getBtnActualizar().setEnabled(false);
        vista.getBtnEliminar().setEnabled(false);
    }

}
    

