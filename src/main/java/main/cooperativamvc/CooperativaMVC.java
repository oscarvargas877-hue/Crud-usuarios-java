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
        UsuarioModelo modelo= new UsuarioModelo();
        UsuarioVista vista = new UsuarioVista();
        UsuarioControlador controlador= new UsuarioControlador(modelo, vista);
        
        controlador.iniciar();
    }
}
