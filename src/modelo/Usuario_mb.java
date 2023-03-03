package modelo;

public class Usuario_mb {
    private int codigo;
    private String cedula; 
    private String nombre;
    private String password;
    private int rol;
    private String estado;
    private int id_persona;
    

    public Usuario_mb() {
    }

    public Usuario_mb(int codigo, String cedula, String nombre, String password, int rol, String estado) {
        this.codigo = codigo;
        this.cedula = cedula;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    

}

  
