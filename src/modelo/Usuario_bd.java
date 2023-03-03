/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class Usuario_bd extends Usuario_mb {

    Conect conectar = new Conect();

    public Usuario_bd() {
    }

    public Usuario_bd(String cedula, String usuario, String password, int codigo_rol, String estado) {
        super(cedula, usuario, password, codigo_rol, estado);
    }

    public List<Usuario_mb> mostrardatos() {

        try {
            List<Usuario_mb> lista = new ArrayList<>();
            String sql = "SELECT * FROM usuario";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {

                Usuario_mb user = new Usuario_mb();
                user.setCedula(rs.getString("cedula"));
                user.setUsuario(rs.getString("usuario"));
                user.setPassword(rs.getString("password"));
                user.setCodigo_rol(rs.getInt("codigo_rol"));
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

    public boolean insertar() {
        String sql = "INSERT INTO usuario(cedula,usuario,password,codigo_rol,estado)"
                + "VALUES ('"
                + getCedula() + "','"
                + getUsuario() + "','"
                + getPassword() + "','"
                + getCodigo_rol() + "','"
                + getEstado() + "')";

        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }//Fin de insertar

    public boolean modificar(String cedula) {
        String sql = "Update usuario set "
                + "cedula = '" + getCedula() + "', "
                + "usuario = '" + getUsuario() + "', "
                + "password = '" + getPassword() + "', "
                + "codigo_rol = '" + getCodigo_rol() + "', "
                + "estado = '" + getEstado() + "' "
                + " Where cedula = '" + getCedula() + "'";

        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("error al editar");
            return false;
        }
    }//Fin del modificar

    public boolean eliminar(String cedula) {
        String nsql = "Delete from usuario Where cedula = '" + cedula + "'";
        if (conectar.noQuery(nsql) == null) {
            return true;
        } else {
            JOptionPane.showConfirmDialog(null, "Error al eliminar");
            return false;
        }
    }//Fin de eliminar

    public boolean verificarExistente(String cedula) {

        try {
            List<Usuario_mb> lista = new ArrayList<>();
            String sql = "SELECT * FROM usuario where cedula= '" + cedula + "'";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {

                  Usuario_mb user = new Usuario_mb();
                user.setCedula(rs.getString("cedula"));
                user.setUsuario(rs.getString("usuario"));
                user.setPassword(rs.getString("password"));
                user.setCodigo_rol(rs.getInt("codigo_rol"));
                user.setEstado(rs.getString("estado"));

                lista.add(user);
            }
            rs.close();
            if (lista.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(Usuario_bd.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }

    }

  
}
