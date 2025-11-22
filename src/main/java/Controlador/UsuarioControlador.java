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

    // === CONSTRUCTOR: SE EJECUTA CUANDO CREAMOS EL CONTROLADOR ===
    public UsuarioControlador(UsuarioModelo modelo, UsuarioVista vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.baseDatos = new UsuarioBDD();  // conectamos con la base de datos

        // CONECTAMOS LOS BOTONES A SUS ACCIONES
        vista.getBtnNuevo().addActionListener(e -> botonNuevo());
        vista.getBtnGuardar().addActionListener(e -> botonGuardar());
        vista.getBtnActualizar().addActionListener(e -> botonActualizar());
        vista.getBtnEliminar().addActionListener(e -> botonEliminar());
        vista.getBtnLimpiar().addActionListener(e -> botonLimpiar());

        // CUANDO HACEMOS CLIC EN UNA FILA DE LA TABLA → CARGAR DATOS
        vista.getTblUsuarios().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && vista.getTblUsuarios().getSelectedRow() != -1) {
                int fila = vista.getTblUsuarios().getSelectedRow();
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
        vista.getBtnGuardar().setEnabled(true);
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
        if (!validarCampos()) return;

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

    // BOTÓN LIMPIAR
    private void botonLimpiar() {
        botonNuevo();
    }

      // CUANDO HACEMOS CLIC EN UNA FILA  CARGAR DATOS PARA EDITAR
    private void cargarUsuarioParaEditar(int fila) {
        // TOMAMOS EL ID REAL DE LA PRIMERA COLUMNA DE LA TABLA
        idUsuarioSeleccionado = (int) vista.getTblUsuarios().getValueAt(fila, 0);

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

        for (UsuarioModelo u : baseDatos.obtenerTodosLosUsuarios()) {
            Object[] fila = {
                u.getId(),           
                u.getNombres(),
                u.getCedula(),
                u.getDireccion(),
                u.getEdad()
            };
            tabla.addRow(fila);
        }

        vista.actualizarTabla(tabla);
        
        // DECORACIÓN BONITA
        vista.getTblUsuarios().setBackground(new java.awt.Color(245, 250, 255));
        vista.getTblUsuarios().getTableHeader().setBackground(new java.awt.Color(0, 153, 153));
        vista.getTblUsuarios().getTableHeader().setForeground(java.awt.Color.WHITE);
        vista.getTblUsuarios().getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 14));
    }
    
    // VALIDAR CAMPOS + CÉDULA
    private boolean validarCampos() {
        if (vista.getTxtNombres().isEmpty() || vista.getTxtCedula().isEmpty() ||
            vista.getTxtDireccion().isEmpty() || vista.getTxtAlias().isEmpty() ||
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

    // MOSTRAR LA VENTANA
    public void iniciar() {
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        vista.getBtnActualizar().setEnabled(false);
        vista.getBtnEliminar().setEnabled(false);
    }

}
    

