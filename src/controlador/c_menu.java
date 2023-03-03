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
        

        
        
    }
    private void mostrarNuevoUsuario() {
        V_usuario usuario=new V_usuario();
        C_usuario cusu=new C_usuario(usuario);
    }

    private void salir() {
        V_inicio ini=new V_inicio();
        C_inicio in=new C_inicio(ini);
    }


            
}
