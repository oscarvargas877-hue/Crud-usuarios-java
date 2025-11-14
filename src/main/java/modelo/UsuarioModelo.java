/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Usuario
 */
public class UsuarioModelo {
    
 //ATRIBUTOS 
    private static int idUsuario;
    private String nombres;
    private String cedula;
    private String direccion;
    private String alias;
    private String clave;
    private int edad;

    public UsuarioModelo() {
        idUsuario++;
    }

    public UsuarioModelo( String nombres, String cedula, String direccion, String alias, String clave, int edad) {
        this.idUsuario = idUsuario++;
        this.nombres = nombres;
        this.cedula = cedula;
        this.direccion = direccion;
        this.alias = alias;
        this.clave = clave;
        this.edad = edad;
    }

    public static int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "DATOS DEL USUARIO" +"\n"+
                "Id:"+getIdUsuario()+"\n"+
                "Nombres:"+getNombres()+"\n"+
                "Cédula:"+getCedula()+"\n"+
                "Edad:"+getEdad()+"\n"+
                "Dirección:"+getDireccion()+"\n"+
                "Alias:"+getAlias()+"\n"+
                "Clave:"+getClave();}
    
}
