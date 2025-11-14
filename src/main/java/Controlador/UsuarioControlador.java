/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vistas.UsuarioVista;
import modelo.UsuarioModelo;

/**
 *
 * @author Usuario
 */
public class UsuarioControlador {
  //ATRIBUTOS
    private UsuarioModelo modelo;
    private UsuarioVista vista;
    
    //CONSTRUCTORES

    public UsuarioControlador() {
    }

    public UsuarioControlador(UsuarioModelo modelo, UsuarioVista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }
    //MÉTODOS
    public void generarUsuario(){
        //RECUPERAR LA INFORMACIÓN DEL FRONTEND
        String nombre=vista.getCampoNombre();
        String cedula=vista.getCampoCedula();
        String direccion=vista.getCampoDireccion();
        int edad=vista.getCampoEdad();
        String alias=vista.getCampoAlias();
        String clave=vista.getCampoClave();
        //COMPROBAR LOS DATOS INGRESADOS POR EL USUARIO
        //OR u O ->||
        //AND o y -> &&
        if(nombre.isEmpty()||cedula.isEmpty()||direccion.isEmpty()||alias.isEmpty()||
                clave.isEmpty()){
            vista.mostrarMensaje("TODOS LOS CAMPOS SON OBLIGATORIOS ");
            return;
        }
        if (edad <= 0) {
            vista.mostrarMensaje("LA EDAD DEBE SER MAYOR A 0");
            return;
        }
    //  SI TODO ESTÁ CORRECTA INICIALIZAMOS EL MODELO
        UsuarioModelo nuevoUsuario=new UsuarioModelo( nombre, cedula, direccion, alias, clave, edad);
    
        vista.setCampoResultado(nuevoUsuario.toString());
    }
     public void iniciar() {
        // 1. Asignar el Controlador como oyente a los botones de la Vista
        vista.getCampoCrear().addActionListener(e -> generarUsuario());
        //pv.getBtnListar().addActionListener(e -> actualizarListaPersonas());

        // 2. Mostrar la Vista
        vista.setVisible(true);
        //actualizarListaPersonas(); // Carga inicial
}

}
    

