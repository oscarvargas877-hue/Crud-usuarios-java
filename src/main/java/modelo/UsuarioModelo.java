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
    private int id;
    private String nombres;
    private String cedula;
    private String direccion;
    private String alias;
    private String clave;
    private int edad;

    public UsuarioModelo() {
        
    }

    public UsuarioModelo( int id, String nombres, String cedula, String direccion, String alias, String clave, int edad) {
        this.id=id;
        this.nombres = nombres;
        this.cedula = cedula;
        this.direccion = direccion;
        this.alias = alias;
        this.clave = clave;
        this.edad = edad;
    }

 
    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
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
    //VALIDAR CEDULA
     public boolean validarCedula(String cedula) {
        int sumImpar = 0, sumPar = 0, sumTot = 0, digtFinal = 0;

        if (cedula == null || cedula.length() != 10) {
            return false; // Cédula nula o no tiene 10 dígitos
        }

        // Validar que todos los caracteres sean dígitos
        for (int i = 0; i < cedula.length(); i++) {
            if (!Character.isDigit(cedula.charAt(i))) {
                return false; // Contiene caracteres no numéricos
            }
        }

        for (int i = 0; i < cedula.length(); i++) {
            int digito = (cedula.charAt(i)) - '0'; // Convertir char a int
            if (i % 2 == 0) { // Posiciones pares (0-indexed) -> 0, 2, 4, 6, 8
                int mul = digito * 2;
                if (mul > 9) {
                    mul -= 9;
                }
                sumImpar += mul;
            } else if (i % 2 != 0 && i != 9) { // Posiciones impares (0-indexed) -> 1, 3, 5, 7
                sumPar += digito;
            } else if (i == 9) { // Última posición (0-indexed) -> 9
                digtFinal = digito;
            }
        }
        sumTot = sumImpar + sumPar;

        if (sumTot % 10 == 0) {
            return (digtFinal == 0); // Si la suma es múltiplo de 10, el dígito verificador debe ser 0
        } else {
            int digitoEsperado = 10 - (sumTot % 10);
            return (digtFinal == digitoEsperado); // Si no, debe coincidir con 10 - (resto)
        }
    }


    @Override
    public String toString() {
        return "DATOS DEL USUARIO" +"\n"+
                "Nombres:"+getNombres()+"\n"+
                "Cédula:"+getCedula()+"\n"+
                "Edad:"+getEdad()+"\n"+
                "Dirección:"+getDireccion()+"\n"+
                "Alias:"+getAlias()+"\n"+
                "Clave:"+getClave();}
    
}
