package modelo;

public class Usuario_mb {
    String cedula; 
    String usuario;
    String password;
    int codigo_rol;
    String estado;

    public Usuario_mb(String cedula, String usuario, String password, int codigo_rol, String estado) {
        this.cedula = cedula;
        this.usuario = usuario;
        this.password = password;
        this.codigo_rol = codigo_rol;
        this.estado = estado;
    }

    public Usuario_mb() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCodigo_rol() {
        return codigo_rol;
    }

    public void setCodigo_rol(int codigo_rol) {
        this.codigo_rol = codigo_rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    

}

  
