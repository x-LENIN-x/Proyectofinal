
package controlador;

import vista.*;

public class c_rol {
        public static v_rol vista;
    
     public c_rol(v_rol rol) {
        this.vista = rol;
        vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
     }
}
