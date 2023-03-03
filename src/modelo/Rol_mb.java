/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author PC
 */
public class Rol_mb {
    private int codigo;
    private String nombre;
    private String descripcion;
    private int codigo_permiso;

    public Rol_mb(int codigo, String nombre, String descripcion, int codigo_permiso) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo_permiso = codigo_permiso;
    }

    public Rol_mb() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigo_permiso() {
        return codigo_permiso;
    }

    public void setCodigo_permiso(int codigo_permiso) {
        this.codigo_permiso = codigo_permiso;
    }
    
    
}
