/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import vista.*;
import controlador.*;

/**
 *
 * @author PC01
 */
public class c_menu {
     public static v_menu vista;

    public c_menu(v_menu menu) {
        this.vista = menu;
        vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
       // this.vista.getLinkPedido().addActionListener(e->mostrarMesero());

        
    }
    
    public void mostrarMesero(){
          v_mesero vista = new v_mesero();
          c_mesero co = new c_mesero(vista);
    }
            
}
