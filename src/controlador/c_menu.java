/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import vista.*;
import controlador.*;

public class c_menu {
     public static v_menu vista;

    public c_menu(v_menu menu) {
        this.vista = menu;
        vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
        this.vista.getLink_nuevousuario().addActionListener(e->mostrarNuevoUsuario());
        this.vista.getLink_rolusuario().addActionListener(e->roles());


        
    }
    private void mostrarNuevoUsuario() {
        V_usuario usuario=new V_usuario();
        c_usuario cusu=new c_usuario(usuario);
    }

    private void salir() {
        v_incio ini=new v_incio();
        c_inicio in=new c_inicio(ini);
    }

    private void roles() {
        v_rol rol=new v_rol();
        c_rol r=new c_rol(rol);
    }
            
}
