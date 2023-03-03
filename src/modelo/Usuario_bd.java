
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Usuario_bd extends Usuario_mb{
    Conect conectar = new Conect();

    public Usuario_bd(int codigo, String cedula, String nombre, String password, int rol, String estado) {
        super(codigo, cedula, nombre, password, rol, estado);
    }


    public Usuario_bd() {
    }
    
      public List<Usuario_mb> mostrardatos() {

        try {
            List<Usuario_mb> lista = new ArrayList<Usuario_mb>();
            String sql = "SELECT * FROM usuario";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {

                Usuario_mb user = new Usuario_mb();

                user.setCodigo(rs.getInt("codigo"));
                user.setCedula(rs.getString("cedula"));
                user.setNombre(rs.getString("nombre"));
                user.setPassword(rs.getString("clave"));
                user.setRol(rs.getInt("rol"));
                user.setEstado(rs.getString("estado"));

                lista.add(user);
            }
            rs.close();
            return lista;
        } catch (SQLException e) {
            Logger.getLogger(Usuario_bd.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }//Fin mostrar datos
}
