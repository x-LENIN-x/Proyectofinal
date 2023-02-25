
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class usuario_bd extends usuario_mb{
    Conect conectar = new Conect();

    public usuario_bd(int codigo, String cedula, String nombre, String password, int rol, int estado) {
        super(codigo, cedula, nombre, password, rol, estado);
    }

    public usuario_bd() {
    }
    
      public List<usuario_mb> mostrardatos() {

        try {
            List<usuario_mb> lista = new ArrayList<usuario_mb>();
            String sql = "SELECT * FROM usuario";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {

                usuario_mb user = new usuario_mb();

                user.setCodigo(rs.getInt("codigo"));
                user.setCedula(rs.getString("cedula"));
                user.setNombre(rs.getString("nombre"));
                user.setPassword(rs.getString("clave"));
                user.setRol(rs.getInt("rol"));
                user.setEstado(rs.getInt("estado"));

                lista.add(user);
            }
            rs.close();
            return lista;
        } catch (SQLException e) {
            Logger.getLogger(usuario_bd.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }//Fin mostrar datos
}
