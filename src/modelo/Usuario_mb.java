package modelo;

public class Usuario_mb {
    
    private int codigo; 
    private String usuario;
    private String password;
    private int codigo_rol;
    private String estado;
    private String cedula_persona;

    public Usuario_mb(int codigo, String usuario, String password, int codigo_rol, String estado, String cedula_persona) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.password = password;
        this.codigo_rol = codigo_rol;
        this.estado = estado;
        this.cedula_persona = cedula_persona;
    }

    public Usuario_mb() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public String getCedula_persona() {
        return cedula_persona;
    }

    public void setCedula_persona(String cedula_persona) {
        this.cedula_persona = cedula_persona;
    }

   
     
    

}

  
