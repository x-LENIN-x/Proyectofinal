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
public class Rol_bd extends Rol_mb {
    Conect conectar = new Conect();

    public Rol_bd(int codigo, String nombre, String descripcion, int codigo_permiso) {
        super(codigo, nombre, descripcion, codigo_permiso);
    }

    public Rol_bd() {
    }
    
     public List<Rol_mb> mostrardatos() {

        try {
            List<Rol_mb> lista = new ArrayList<>();
            String sql = "SELECT * FROM rol";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {

                Rol_mb rol = new Rol_mb();
                rol.setCodigo(rs.getInt("codigo"));
                rol.setNombre(rs.getString("nombre"));
                rol.setDescripcion(rs.getString("descripcion"));
                rol.setCodigo_permiso(rs.getInt("codigo_permiso"));


                lista.add(rol);
            }
            rs.close();
            return lista;
        } catch (SQLException e) {
            Logger.getLogger(Rol_bd.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }//Fin mostrar datos
      
     public boolean insertar() {
        String sql = "INSERT INTO rol(codigo,nombre,descripcion,codigo_permiso)"
                + "VALUES ('"
                + getCodigo() + "','"
                + getNombre() + "','"
                + getDescripcion() + "','"
                + getCodigo_permiso() + "')";

        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }//Fin de insertar

    public boolean modificar(int codigo) { 
        String sql = "Update rol set "
                + "codigo = '" + getCodigo() + "', "
                + "nombre = '" + getNombre() + "', "
                + "descripcion = '" + getDescripcion()+ "', "
                + "codigo_permiso = '" + getCodigo_permiso() + "' "
                + " Where codigo = '" + getCodigo() + "'";

        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("error al editar");
            return false;
        }
    }//Fin del modificar

    public boolean eliminar(int codigo) {
        String nsql = "Delete from rol Where codigo = '" + codigo + "'";
        if (conectar.noQuery(nsql) == null) {
            return true;
        } else {
            JOptionPane.showConfirmDialog(null, "Error al eliminar");
            return false;
        }
    }//Fin de eliminar
    
    

    public boolean verificarExistente(int codigo) {
        
        
        
        try {
            List<Rol_mb> lista = new ArrayList<>();
            String sql = "SELECT * FROM rol where codigo= '" + codigo + "'";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {

                Rol_mb rol = new Rol_mb();
                rol.setCodigo(rs.getInt("codigo"));
                rol.setNombre(rs.getString("nombre"));
                rol.setDescripcion(rs.getString("descripcion"));
                rol.setCodigo_permiso(rs.getInt("codigo_permiso"));


                lista.add(rol);
            }
            rs.close();
              if (lista.isEmpty()) {
                 return false;
             }else{
                 return true;
             }
        } catch (SQLException e) {
            Logger.getLogger(Rol_bd.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }

    }  
    
    
     public List<Rol_mb> mostrardatoscodigo(int codigo) {

        try {
           List<Rol_mb> lista = new ArrayList<>();
            String sql = "SELECT * FROM rol where codigo= '" + codigo + "'";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {

                Rol_mb rol = new Rol_mb();
                rol.setCodigo(rs.getInt("codigo"));
                rol.setNombre(rs.getString("nombre"));
                rol.setDescripcion(rs.getString("descripcion"));
                rol.setCodigo_permiso(rs.getInt("codigo_permiso"));


                lista.add(rol);
            }
            rs.close();
            return lista;
        } catch (SQLException e) {
            Logger.getLogger(Rol_bd.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }//Fin mostrar datos
     
      public List<Rol_mb> mostrardatosnombre(String ti) {

     try {
           List<Rol_mb> lista = new ArrayList<>();
            String sql = "SELECT * FROM rol where nombre= '" + ti + "'";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {

                Rol_mb rol = new Rol_mb();
                rol.setCodigo(rs.getInt("codigo"));
                rol.setNombre(rs.getString("nombre"));
                rol.setDescripcion(rs.getString("descripcion"));
                rol.setCodigo_permiso(rs.getInt("codigo_permiso"));


                lista.add(rol);
            }
            rs.close();
            return lista;
        } catch (SQLException e) {
            Logger.getLogger(Rol_bd.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }//Fin mostrar datos 
}
