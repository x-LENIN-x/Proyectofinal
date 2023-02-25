/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import vista.*;
/**
 *
 * @author PC01
 */
public class c_mesero {
    public static v_mesero vista;

    public c_mesero(v_mesero mesero) {
         this.vista = mesero;
        vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
        
        
    }
    
    
}
