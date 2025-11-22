/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main.cooperativamvc;

import Controlador.UsuarioControlador;
import Vistas.UsuarioVista;
import modelo.UsuarioModelo;

/**
 *
 * @author Usuario
 */
public class CooperativaMVC {

    public static void main(String[] args) {
    // 1. Creamos el modelo (los datos vacíos al inicio)
        UsuarioModelo modelo = new UsuarioModelo();
        
        // 2. Creamos la vista (la ventana)
        UsuarioVista vista = new UsuarioVista();
        
        // 3. Creamos el controlador (el cerebro) y le pasamos modelo + vista
        UsuarioControlador controlador = new UsuarioControlador(modelo, vista);
        
        // 4. Iniciamos todo (muestra la ventana y carga la tabla)
        controlador.iniciar();
        
        // 5. Centramos la ventana en la pantalla (queda bonito)
        vista.setLocationRelativeTo(null);
        
        // ¡LA CONEXIÓN YA SE HACE AUTOMÁTICAMENTE DENTRO DEL CONTROLADOR!
        // No necesitas crear ConexionBDD aquí → UsuarioBDD ya lo hace solo
    }
}
